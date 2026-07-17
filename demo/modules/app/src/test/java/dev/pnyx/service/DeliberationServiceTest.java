package dev.pnyx.service;

import dev.pnyx.core.api.DeliberationApi;
import dev.pnyx.core.domain.proposal.ProposalEvent;
import dev.pnyx.core.domain.skill.AiReview;
import dev.pnyx.core.domain.skill.ProgressCallback;
import dev.pnyx.core.domain.skill.SkillRoleDescriptor;
import dev.pnyx.core.domain.skill.SkillReviewerRole;
import dev.pnyx.core.domain.skill.SkillSelectionResult;
import dev.pnyx.core.domain.skill.SkillTool;
import dev.pnyx.core.domain.skill.ToolResult;
import dev.pnyx.core.domain.proposal.ProposalId;
import dev.pnyx.core.spi.EventStoreSpi;
import dev.pnyx.core.spi.SkillExecutorSpi;
import dev.pnyx.core.spi.SkillSelectionSpi;
import dev.pnyx.core.spi.SkillToolProviderSpi;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

class DeliberationServiceTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String LEGAL_REVIEWER = "legal-reviewer";
    private static final String RISK_REVIEWER = "risk-reviewer";
    private static final String ECONOMIC_REVIEWER = "economic-reviewer";
    private static final String SOCIAL_REVIEWER = "social-reviewer";
    private static final String TECHNICAL_REVIEWER = "technical-reviewer";
    private static final String TITLE = "title";
    private static final String PROBLEM = "problem";
    private static final String ACTION = "action";

    private static dev.pnyx.config.PnyxProperties defaultTestProperties() {
        var props = new dev.pnyx.config.PnyxProperties();
        // Default available roles = all 7; max panel size = 4 (test config)
        props.getSkillPanel().setAvailableSkillRoles(List.of(
            LEGAL_REVIEWER, ECONOMIC_REVIEWER, SOCIAL_REVIEWER,
            TECHNICAL_REVIEWER, RISK_REVIEWER,
            "anti-capture-reviewer", "adversarial-critic-reviewer"));
        return props;
    }

    @Test
    void shouldUseDynamicallySelectedRolesInsteadOfStaticPanel() {
        var stubSelector = new StubSkillSelector(List.of(LEGAL_REVIEWER, RISK_REVIEWER));
        var stubEventStore = new StubEventStore(TITLE, PROBLEM, ACTION);
        var stubTools = StubToolProvider.INSTANCE;
        var stubExecutor = StubSkillExecutor.INSTANCE;

        var service = new DeliberationService(
            stubExecutor, stubEventStore, stubTools, stubSelector,
            defaultTestProperties(), OBJECT_MAPPER);
        ProposalId proposalId = ProposalId.generate();
        service.runPanel(proposalId);

        var progress = service.getProgress(proposalId);
        assertThat(progress).isPresent();
        assertThat(progress.get().reviews()).containsOnlyKeys(
            LEGAL_REVIEWER, RISK_REVIEWER);
        assertThat(progress.get().reviews()).doesNotContainKey(ECONOMIC_REVIEWER);
        assertThat(progress.get().reviews()).doesNotContainKey(SOCIAL_REVIEWER);
        assertThat(progress.get().reviews()).doesNotContainKey(TECHNICAL_REVIEWER);
    }

    @Test
    void shouldLogProposedNewSkills() {
        var stubSelector = new StubSkillSelector(
            List.of(LEGAL_REVIEWER),
            List.of(new SkillSelectionResult.SkillProposal(
                "climate-reviewer", "Climate impact analysis",
                "Proposal involves environmental spending")));
        var stubEventStore = new StubEventStore(TITLE, PROBLEM, ACTION);
        var stubTools = StubToolProvider.INSTANCE;
        var stubExecutor = StubSkillExecutor.INSTANCE;

        var service = new DeliberationService(
            stubExecutor, stubEventStore, stubTools, stubSelector,
            defaultTestProperties(), OBJECT_MAPPER);
        ProposalId proposalId = ProposalId.generate();
        service.runPanel(proposalId);

        var progress = service.getProgress(proposalId);
        assertThat(progress).isPresent();
        assertThat(progress.get().reviews()).containsOnlyKeys(LEGAL_REVIEWER);
    }

    @Test
    void shouldIgnoreUnknownRoleIdsInSelection() {
        var stubSelector = new StubSkillSelector(
            List.of(LEGAL_REVIEWER, "nonexistent-role", RISK_REVIEWER));
        var stubEventStore = new StubEventStore(TITLE, PROBLEM, ACTION);
        var stubTools = StubToolProvider.INSTANCE;
        var stubExecutor = StubSkillExecutor.INSTANCE;

        var service = new DeliberationService(
            stubExecutor, stubEventStore, stubTools, stubSelector,
            defaultTestProperties(), OBJECT_MAPPER);
        ProposalId proposalId = ProposalId.generate();
        service.runPanel(proposalId);

        var progress = service.getProgress(proposalId);
        assertThat(progress).isPresent();
        assertThat(progress.get().reviews()).containsOnlyKeys(
            LEGAL_REVIEWER, RISK_REVIEWER);
    }

    @Test
    void shouldCapSelectedRolesAtMaxPanelSize() {
        // AI returns 5 roles, but max-panel-size is 4 — only first 4 should run
        var stubSelector = new StubSkillSelector(List.of(
            LEGAL_REVIEWER, ECONOMIC_REVIEWER, SOCIAL_REVIEWER,
            TECHNICAL_REVIEWER, RISK_REVIEWER));
        var stubEventStore = new StubEventStore(TITLE, PROBLEM, ACTION);
        var stubTools = StubToolProvider.INSTANCE;
        var stubExecutor = StubSkillExecutor.INSTANCE;
        var props = new dev.pnyx.config.PnyxProperties();
        props.getSkillPanel().setAvailableSkillRoles(List.of(
            LEGAL_REVIEWER, ECONOMIC_REVIEWER, SOCIAL_REVIEWER,
            TECHNICAL_REVIEWER, RISK_REVIEWER,
            "anti-capture-reviewer", "adversarial-critic-reviewer"));
        props.getSkillPanel().setMaxPanelSize(3);

        var service = new DeliberationService(
            stubExecutor, stubEventStore, stubTools, stubSelector,
            props, OBJECT_MAPPER);
        ProposalId proposalId = ProposalId.generate();
        service.runPanel(proposalId);

        var progress = service.getProgress(proposalId);
        assertThat(progress).isPresent();
        assertThat(progress.get().reviews()).hasSize(3);
        assertThat(progress.get().reviews()).containsOnlyKeys(
            LEGAL_REVIEWER, ECONOMIC_REVIEWER, SOCIAL_REVIEWER);
    }

    @Test
    void shouldPassRealProposalContentToSkillSelector() {
        // The AI panel selector must see the actual title/problem/action from the
        // event payload, not placeholder strings — otherwise it cannot make
        // informed reviewer choices.
        var stubSelector = new CapturingSkillSelector();
        var stubEventStore = new StubEventStore(
            "Municipal solar co-op",
            "Public schools spend 18% of operating budget on electricity.",
            "Establish bulk-purchased solar panel program.");
        var stubTools = StubToolProvider.INSTANCE;
        var stubExecutor = StubSkillExecutor.INSTANCE;

        var service = new DeliberationService(
            stubExecutor, stubEventStore, stubTools, stubSelector,
            defaultTestProperties(), OBJECT_MAPPER);
        service.runPanel(ProposalId.generate());

        assertThat(stubSelector.receivedTitle()).isEqualTo("Municipal solar co-op");
        assertThat(stubSelector.receivedProblem())
            .isEqualTo("Public schools spend 18% of operating budget on electricity.");
        assertThat(stubSelector.receivedAction())
            .isEqualTo("Establish bulk-purchased solar panel program.");
    }

    private record StubSkillSelector(List<String> selectedRoles,
                                      List<SkillSelectionResult.SkillProposal> proposedNewSkills)
            implements SkillSelectionSpi {

        StubSkillSelector(List<String> selectedRoles) {
            this(selectedRoles, List.of());
        }

        @Override
        public SkillSelectionResult selectSkills(String title, String problem,
                                                  String proposedAction,
                                                  List<SkillRoleDescriptor> availableRoles) {
            assertThat(availableRoles).isNotEmpty();
            return new SkillSelectionResult(selectedRoles, proposedNewSkills,
                "Test selection rationale");
        }
    }

    /**
     * Variant that records the arguments it was called with so tests can assert
     * that the service passed the real proposal content to the selector.
     */
    private static final class CapturingSkillSelector implements SkillSelectionSpi {
        private final AtomicReference<String> title = new AtomicReference<>();
        private final AtomicReference<String> problem = new AtomicReference<>();
        private final AtomicReference<String> action = new AtomicReference<>();

        @Override
        public SkillSelectionResult selectSkills(String title, String problem,
                                                  String proposedAction,
                                                  List<SkillRoleDescriptor> availableRoles) {
            this.title.set(title);
            this.problem.set(problem);
            this.action.set(proposedAction);
            return new SkillSelectionResult(List.of(LEGAL_REVIEWER), List.of(),
                "captured");
        }

        String receivedTitle() { return title.get(); }
        String receivedProblem() { return problem.get(); }
        String receivedAction() { return action.get(); }
    }

    private static class StubEventStore implements EventStoreSpi {
        private final String title;
        private final String problem;
        private final String action;
        private final List<Object> appendedEvents = new ArrayList<>();

        StubEventStore(String title, String problem, String action) {
            this.title = title;
            this.problem = problem;
            this.action = action;
        }

        @Override
        public void append(UUID streamId, List<?> events) {
            appendedEvents.addAll(events);
        }

        @Override
        public List<StoredEvent> readStream(UUID streamId) {
            return List.of(new StoredEvent(streamId, 1, "ProposalSubmitted",
                "{\"title\":\"" + title + "\",\"problem\":\"" + problem
                    + "\",\"proposedAction\":\"" + action + "\"}",
                "sha256:abc", null, Instant.now().toString()));
        }
    }

    private static final class StubToolProvider implements SkillToolProviderSpi {
        static final StubToolProvider INSTANCE = new StubToolProvider();

        @Override
        public List<SkillTool> toolsForRole(SkillReviewerRole role) {
            return List.of();
        }

        @Override
        public ToolResult executeTool(SkillReviewerRole role, String toolName,
                                       String argumentsJson) {
            return ToolResult.failure(toolName, "not implemented");
        }
    }

    private static final class StubSkillExecutor implements SkillExecutorSpi {
        static final StubSkillExecutor INSTANCE = new StubSkillExecutor();

        @Override
        public AiReview review(ProposalEvent event, SkillReviewerRole role,
                                List<SkillTool> tools, ProgressCallback callback) {
            if (callback != null) { callback.onComplete(role.id(), null); }
            return AiReview.create(ProposalId.generate(), role.id(),
                role.id() + " review summary", List.of("finding 1"),
                List.of(), List.of(), List.of(), List.of(),
                0);
        }
    }
}
