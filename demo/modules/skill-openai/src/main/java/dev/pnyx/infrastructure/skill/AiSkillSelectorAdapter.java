package dev.pnyx.infrastructure.skill;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pnyx.core.domain.skill.SkillRoleDescriptor;
import dev.pnyx.core.domain.skill.SkillSelectionResult;
import dev.pnyx.core.domain.skill.SkillSelectionResult.SkillProposal;
import dev.pnyx.core.spi.SkillSelectionPromptProviderSpi;
import dev.pnyx.core.spi.SkillSelectionSpi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Spring AI implementation of {@link dev.pnyx.core.spi.SkillSelectionSpi} using OpenAI.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/PANEL_SELECTION.md §5}, dynamically selects which skill roles
 * compose a proposal's review panel. Uses LLM-driven analysis of proposal content to recommend
 * role composition and propose new skill classes if gaps are identified.
 * <p>
 * The maximum number of roles the model may select is configured in
 * {@link SkillPanelProperties#maxPanelSize}; both the system and user prompts receive
 * this as a {@code {{maxPanelSize}}} substitution.
 * A safety cap is also applied in {@link dev.pnyx.service.DeliberationService}.
 *
 * @see ../docs/20_Protocol_Core/PANEL_SELECTION.md
 * @see ../docs/60_Skills/SKILLS.md
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AiSkillSelectorAdapter implements SkillSelectionSpi {

    private final ChatClient.Builder chatClientBuilder;
    private final SkillSelectionPromptProviderSpi prompts;
    private final ObjectMapper objectMapper;
    private final SkillPanelProperties skillPanelProperties;

    private record AiSelectionResponse(
        List<String> selectedRoles,
        List<AiSkillProposal> proposedNewSkills,
        String rationale
    ) { }

    private record AiSkillProposal(String name, String description, String reason) { }

    @Override
    public SkillSelectionResult selectSkills(String title, String problem,
                                              String proposedAction,
                                              List<SkillRoleDescriptor> availableRoles) {
        try {
            String availableSkillsJson = objectMapper.writeValueAsString(availableRoles);
            String maxSizeStr = String.valueOf(skillPanelProperties.getMaxPanelSize());
            String systemPrompt = prompts.systemPrompt()
                .replace("{{maxPanelSize}}", maxSizeStr);
            String userPrompt = prompts.userPrompt(title, problem, proposedAction,
                availableSkillsJson)
                .replace("{{maxPanelSize}}", maxSizeStr);

            String response = chatClientBuilder.build().prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .call()
                .content();

            AiSelectionResponse ai = objectMapper.readValue(response,
                AiSelectionResponse.class);

            // Defense in depth: cap the result here too, in case the model returns more
            List<String> selected = ai.selectedRoles() != null
                ? ai.selectedRoles().stream().limit(skillPanelProperties.getMaxPanelSize()).toList()
                : List.of();

            List<SkillProposal> proposals = ai.proposedNewSkills() != null
                ? ai.proposedNewSkills().stream()
                    .map(p -> new SkillProposal(p.name(), p.description(), p.reason()))
                    .toList()
                : List.of();

            log.info("Skill selection: selectedRoles={} (max={}), proposedNewSkills={}, rationale={}",
                selected, skillPanelProperties.getMaxPanelSize(), proposals.size(),
                ai.rationale() != null ? ai.rationale().substring(0,
                    Math.min(80, ai.rationale().length())) : "none");

            return new SkillSelectionResult(selected, proposals,
                ai.rationale() != null ? ai.rationale() : "");
        } catch (JsonProcessingException e) {
            log.warn("Failed to parse skill selection response, falling back to all available roles", e);
            return fallback(availableRoles);
        } catch (Exception e) {
            log.warn("Skill selection failed, falling back to all available roles", e);
            return fallback(availableRoles);
        }
    }

    private SkillSelectionResult fallback(List<SkillRoleDescriptor> availableRoles) {
        // Cap the fallback at max-panel-size too
        List<String> allIds = availableRoles.stream()
            .map(SkillRoleDescriptor::id)
            .limit(skillPanelProperties.getMaxPanelSize())
            .toList();
        return new SkillSelectionResult(allIds, List.of(),
            "Fallback: using first " + skillPanelProperties.getMaxPanelSize() + " available roles");
    }
}
