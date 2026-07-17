package dev.pnyx.service;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.pnyx.core.api.DeliberationApi;
import dev.pnyx.core.domain.panel.Panel;
import dev.pnyx.core.domain.panel.PanelSpec;
import dev.pnyx.core.domain.proposal.ProposalId;
import dev.pnyx.core.domain.proposal.ProposalPanelLocked;
import dev.pnyx.core.domain.proposal.ProposalSubmitted;
import dev.pnyx.core.domain.skill.ProgressCallback;
import dev.pnyx.core.domain.skill.ReviewProgress;
import dev.pnyx.core.domain.skill.SkillRoleDescriptor;
import dev.pnyx.core.domain.skill.SkillReviewerRole;
import dev.pnyx.core.domain.skill.SkillSelectionResult;
import dev.pnyx.core.domain.skill.ToolResult;
import dev.pnyx.core.spi.EventStoreSpi;
import dev.pnyx.core.spi.SkillExecutorSpi;
import dev.pnyx.core.spi.SkillSelectionSpi;
import dev.pnyx.core.spi.SkillToolProviderSpi;
import dev.pnyx.config.PnyxProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implements {@link dev.pnyx.core.api.DeliberationApi} — skill-panel assembly and review execution.
 * <p>
 * Orchestrates the deliberation flow defined in {@code ../docs/20_Protocol_Core/PANEL_SELECTION.md}:
 * selects skill roles, executes reviews via executors, and tracks progress through the
 * review lifecycle. Each review result includes a derived {@link dev.pnyx.core.domain.skill.ConfidenceRecord}
 * per {@code ../docs/60_Skills/CONFIDENCE_AND_SCORING.md}.
 *
 * @see ../docs/20_Protocol_Core/PANEL_SELECTION.md
 * @see ../docs/60_Skills/SKILLS.md
 * @see ../docs/60_Skills/CONFIDENCE_AND_SCORING.md
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeliberationService implements DeliberationApi {

    private final SkillExecutorSpi skillExecutor;
    private final EventStoreSpi eventStore;
    private final SkillToolProviderSpi toolProvider;
    private final SkillSelectionSpi skillSelection;
    private final PnyxProperties properties;
    private final ObjectMapper objectMapper;
    private final Map<ProposalId, Map<String, ReviewProgress>> progressStore = new ConcurrentHashMap<>();

    @Override
    @Async
    public void runPanel(ProposalId proposalId) {
        var events = eventStore.readStream(proposalId.value());
        if (events.isEmpty()) {
            throw new IllegalArgumentException("Proposal not found: " + proposalId);
        }

        // Extract the actual proposal fields from the first event's payload so
        // both the AI panel selection and the downstream reviewers see the real
        // proposal content (not placeholder strings).
        var payload = events.getFirst().eventPayload();
        var title = extractField(payload, "title");
        var problem = extractField(payload, "problem");
        var action = extractField(payload, "proposedAction");

        var evt = new ProposalSubmitted(proposalId,
            title != null ? title : "Proposal " + proposalId.value().toString().substring(0, 8),
            problem != null ? problem : "(problem not recorded)",
            action != null ? action : null);

        var availableRoles = buildRoleDescriptors();
        var maxPanelSize = properties.getSkillPanel().getMaxPanelSize();
        var selection = skillSelection.selectSkills(
            evt.title(), evt.problem(), evt.proposedAction(), availableRoles);

        logSelection(selection, maxPanelSize);

        // Resolve AI-selected role IDs to enums, then cap at max-panel-size as a safety net
        List<SkillReviewerRole> selectedRoles = resolveRoles(selection.selectedRoles());
        if (selectedRoles.size() > maxPanelSize) {
            log.warn("Skill selection returned {} roles, capping to max-panel-size={}",
                selectedRoles.size(), maxPanelSize);
            selectedRoles = selectedRoles.subList(0, maxPanelSize);
        }

        Map<String, ReviewProgress> progress = new ConcurrentHashMap<>();
        selectedRoles.forEach(r -> progress.put(r.id(), ReviewProgress.pending(r.id())));
        progressStore.put(proposalId, progress);

        selectedRoles.parallelStream().forEach(role -> executeReview(proposalId, evt, role, progress));
        log.info("Deliberation complete for proposal {} — {} reviews assembled (max={})",
            proposalId, selectedRoles.size(), maxPanelSize);

        // After all reviews complete, create and lock a formal Panel aggregate
        var selectedRoleIds = selectedRoles.stream().map(SkillReviewerRole::id).toList();
        var panelId = UUID.randomUUID();
        var panelSpec = new PanelSpec(
            selectedRoleIds, Math.min(maxPanelSize, selectedRoleIds.size()), List.of("ai"));
        var panel = new Panel(panelId, proposalId, panelSpec);
        panel.lock();
        eventStore.append(panelId, panel.uncommittedEvents());
        log.info("Panel {} locked for proposal {} with {} roles", panelId, proposalId, selectedRoleIds.size());

        // Also append a ProposalPanelLocked event to the proposal stream to advance its state
        var panelLockedEvent = new ProposalPanelLocked(proposalId, selectedRoleIds);
        eventStore.append(proposalId.value(), List.of(panelLockedEvent));
    }

    @Override
    public Optional<ProgressView> getProgress(ProposalId proposalId) {
        var progress = progressStore.get(proposalId);
        if (progress == null) { return Optional.empty(); }

        long completed = progress.values().stream()
            .filter(p -> p.status() == ReviewProgress.ReviewStatus.COMPLETED
                         || p.status() == ReviewProgress.ReviewStatus.FAILED).count();

        Map<String, ReviewProgressView> reviews = new LinkedHashMap<>();
        progress.forEach((role, p) ->
            reviews.put(role,
                new ReviewProgressView(role, p.status().name(),
                    p.currentStep(), p.detail(), p.startedAt().toString())));

        String status = completed == progress.size() ? "COMPLETED" : "RUNNING";
        return Optional.of(new ProgressView(proposalId, status, reviews));
    }

    private void executeReview(ProposalId proposalId, ProposalSubmitted evt,
                                SkillReviewerRole role,
                                Map<String, ReviewProgress> progress) {
        try {
            progress.put(role.id(), progress.get(role.id()).running());

            var tools = toolProvider.toolsForRole(role);

            var review = skillExecutor.review(evt, role, tools,
                createProgressCallback(role, progress));

            progress.put(role.id(), progress.get(role.id()).completed(review.summary()));
            log.info("Deliberation complete for role {} on proposal {}", role, proposalId);
        } catch (Exception e) {
            log.error("Deliberation failed for role {} on proposal {}", role, proposalId, e);
            progress.put(role.id(), progress.get(role.id()).failed(e.getMessage()));
        }
    }

    private ProgressCallback createProgressCallback(SkillReviewerRole role,
                                                     Map<String, ReviewProgress> progress) {
        return new ProgressCallback() {
            @Override
            public void onTurnStart(String r, int turn) {
                progress.put(role.id(), new ReviewProgress(role.id(),
                    ReviewProgress.ReviewStatus.RUNNING, "Turn " + turn, turn, null,
                    progress.get(role.id()).startedAt(), Instant.now()));
            }

            @Override
            public void onToolCallStart(String r, String toolName, String args) {
                progress.put(role.id(), progress.get(role.id()).toolCall(toolName, args));
            }

            @Override
            public void onToolCallResult(String r, String toolName, ToolResult result) {
                progress.put(role.id(), new ReviewProgress(role.id(),
                    ReviewProgress.ReviewStatus.ANALYZING,
                    "Analyzing " + toolName + " result",
                    progress.get(role.id()).turn(), null,
                    progress.get(role.id()).startedAt(), Instant.now()));
            }

            @Override
            public void onStatus(String r, String message) {
                progress.put(role.id(), new ReviewProgress(role.id(),
                    ReviewProgress.ReviewStatus.RUNNING, message,
                    progress.get(role.id()).turn(), null,
                    progress.get(role.id()).startedAt(), Instant.now()));
            }
        };
    }

    private List<SkillRoleDescriptor> buildRoleDescriptors() {
        // Use the configured available-skill-roles pool (default: all 7 canonical).
        // This is what the AI sees as the "available" set when picking the panel.
        var availableIds = properties.getSkillPanel().getAvailableSkillRoles();
        return availableIds.stream()
            .map(id -> SkillReviewerRole.findById(id))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(role -> new SkillRoleDescriptor(
                role.id(), role.description(),
                toolProvider.toolsForRole(role).stream()
                    .map(t -> t.name())
                    .toList()))
            .toList();
    }

    /**
     * Pulls a top-level string field out of a proposal-event JSON payload.
     * Returns {@code null} if the field is absent or the payload is not valid JSON.
     */
    private String extractField(String json, String field) {
        try {
            var node = objectMapper.readTree(json);
            var value = node.get(field);
            return value != null && !value.isNull() ? value.asText() : null;
        } catch (JsonProcessingException e) {
            log.warn("Could not parse event payload for field {}: {}", field, e.getMessage());
            return null;
        }
    }

    private List<SkillReviewerRole> resolveRoles(List<String> roleIds) {
        // Only allow roles that are in the configured available pool (defense in depth)
        var availableIds = properties.getSkillPanel().getAvailableSkillRoles();
        return roleIds.stream()
            .filter(availableIds::contains)
            .map(id -> SkillReviewerRole.findById(id))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .distinct()
            .toList();
    }

    private void logSelection(SkillSelectionResult selection, int maxPanelSize) {
        if (selection.selectedRoles().isEmpty()) {
            log.warn("Skill selection returned no roles, deliberation will have no reviewers");
        } else if (selection.selectedRoles().size() < 2) {
            log.warn("Skill selection returned very few ({}) roles: {}",
                selection.selectedRoles().size(), selection.selectedRoles());
        } else {
            log.info("Skill selection picked {} of max {} roles: {}",
                selection.selectedRoles().size(), maxPanelSize, selection.selectedRoles());
        }
        if (!selection.proposedNewSkills().isEmpty()) {
            selection.proposedNewSkills().forEach(p ->
                log.warn("Proposed new skill '{}' ({}) was NOT executed — only existing skills run. Reason: {}",
                    p.name(), p.description(), p.reason()));
        }
    }
}
