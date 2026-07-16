package dev.pnyx.core.domain.proposal;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ProposalTest {

    private static final String TITLE = "Title";
    private static final String PROBLEM = "A substantive problem statement for testing";
    private static final String ACTION = "Action";
    private static final String POLICY = "policy";

    @Test
    void shouldStartInDraftState() {
        var result = Proposal.create(
            "School energy upgrades",
            "High energy costs in public schools",
            "Create municipal fund"
        );
        assertTrue(result.isSuccess());
        Proposal proposal = result.orElseThrow();
        assertThat(proposal.state()).isEqualTo(ProposalState.DRAFT);
    }

    @Test
    void shouldTransitionFromDraftToSubmitted() {
        Proposal proposal = Proposal.create(TITLE, PROBLEM, ACTION).orElseThrow();
        var result = proposal.submit();
        assertTrue(result.isSuccess());
        Proposal submitted = result.orElseThrow();
        assertThat(submitted.state()).isEqualTo(ProposalState.SUBMITTED);
    }

    @Test
    void shouldEmitProposalSubmittedEvent() {
        Proposal proposal = Proposal.create(TITLE, PROBLEM, ACTION).orElseThrow();
        Proposal submitted = proposal.submit().orElseThrow();
        var events = submitted.uncommittedEvents();
        assertThat(events).hasSize(1);
        assertThat(events.getFirst()).isInstanceOf(ProposalSubmitted.class);
    }

    @Test
    void shouldClearUncommittedEventsAfterRehydration() {
        Proposal proposal = Proposal.create(TITLE, PROBLEM, ACTION).orElseThrow();
        Proposal submitted = proposal.submit().orElseThrow();
        Proposal rehydrated = Proposal.rehydrate(submitted.proposalId(),
            submitted.state(), submitted.title(), submitted.problem(),
            submitted.proposedAction(), submitted.classification());
        assertThat(rehydrated.uncommittedEvents()).isEmpty();
    }

    // --- Problem-before-solution gate (PROTOCOL.md invariant 7.9) ---

    @Test
    void shouldAllowCreationWithoutProposedAction() {
        Proposal proposal = Proposal.create(TITLE, "A real problem that needs solving", null).orElseThrow();
        assertThat(proposal.proposedAction()).isNull();
        assertThat(proposal.problem()).isEqualTo("A real problem that needs solving");
    }

    @Test
    void shouldRejectBlankProblemStatement() {
        var result = Proposal.create(TITLE, "  ", ACTION);
        assertTrue(result.isFailure());
        assertInstanceOf(ProposalError.BlankProblem.class, ((dev.pnyx.core.domain.result.Result.Failure<?, ?>) result).error());
    }

    @Test
    void shouldRejectProblemIdenticalToSolution() {
        var result = Proposal.create(TITLE, "Do the thing properly and correctly", "Do the thing properly and correctly");
        assertTrue(result.isFailure());
        assertInstanceOf(ProposalError.ProblemEqualsSolution.class, ((dev.pnyx.core.domain.result.Result.Failure<?, ?>) result).error());
    }

    @Test
    void shouldRejectProblemTooShort() {
        var result = Proposal.create(TITLE, "Too short", ACTION);
        assertTrue(result.isFailure());
        assertInstanceOf(ProposalError.ProblemTooShort.class, ((dev.pnyx.core.domain.result.Result.Failure<?, ?>) result).error());
    }

    @Test
    void shouldRejectProblemIdenticalToTitle() {
        var result = Proposal.create("Same text for both title and problem", "Same text for both title and problem", ACTION);
        assertTrue(result.isFailure());
        assertInstanceOf(ProposalError.ProblemEqualsTitle.class, ((dev.pnyx.core.domain.result.Result.Failure<?, ?>) result).error());
    }

    @Test
    void shouldRejectProblemPhrasedAsQuestion() {
        var result = Proposal.create(TITLE, "Why is the system broken?", ACTION);
        assertTrue(result.isFailure());
        assertInstanceOf(ProposalError.ProblemIsQuestion.class, ((dev.pnyx.core.domain.result.Result.Failure<?, ?>) result).error());
    }

    @Test
    void shouldAcceptSubstantiveProblemStatement() {
        var result = Proposal.create(TITLE, "Public schools have outdated heating systems that waste energy", ACTION);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldRequireProblemDefinitionBeforeClassification() {
        Proposal proposal = Proposal.create(TITLE, PROBLEM, ACTION).orElseThrow();
        Proposal submitted = proposal.submit().orElseThrow();
        Proposal pending = submitted.markIntakeValid().orElseThrow();

        var result = pending.classify(POLICY, POLICY, true);
        assertTrue(result.isFailure());
        assertInstanceOf(ProposalError.MissingProblemDefinition.class, ((dev.pnyx.core.domain.result.Result.Failure<?, ?>) result).error());
    }

    @Test
    void shouldClassifyAfterProblemDefinition() {
        Proposal proposal = Proposal.create(TITLE, PROBLEM, ACTION).orElseThrow();
        Proposal submitted = proposal.submit().orElseThrow();
        Proposal defined = submitted.defineProblem().orElseThrow();
        Proposal pending = defined.markIntakeValid().orElseThrow();
        Proposal classified = pending.classify(POLICY, POLICY, true).orElseThrow();

        assertThat(classified.state()).isEqualTo(ProposalState.CLASSIFIED);
        assertThat(classified.problemDefinition()).isNotNull();
    }

    // --- Canonical public state export (STATE_MACHINE.md §4.5) ---

    @Test
    void shouldReturnNullCanonicalPublicStateForDraft() {
        Proposal proposal = Proposal.create(TITLE, PROBLEM, ACTION).orElseThrow();
        assertThat(proposal.canonicalPublicState()).isNull();
    }

    @Test
    void shouldReturnIntakeForSubmitted() {
        Proposal proposal = Proposal.create(TITLE, PROBLEM, ACTION).orElseThrow();
        Proposal submitted = proposal.submit().orElseThrow();
        assertThat(submitted.canonicalPublicState())
            .isEqualTo(CanonicalPublicState.INTAKE);
    }

    @Test
    void shouldReturnProblemDefinedAfterClassification() {
        Proposal proposal = Proposal.create(TITLE, PROBLEM, ACTION).orElseThrow();
        Proposal submitted = proposal.submit().orElseThrow();
        Proposal defined = submitted.defineProblem().orElseThrow();
        Proposal pending = defined.markIntakeValid().orElseThrow();
        Proposal classified = pending.classify(POLICY, POLICY, true).orElseThrow();
        assertThat(classified.canonicalPublicState())
            .isEqualTo(CanonicalPublicState.PROBLEM_DEFINED);
    }

    @Test
    void shouldMapAllStatesToCanonicalPublicStates() {
        for (ProposalState state : ProposalState.values()) {
            if (state == ProposalState.DRAFT) {
                assertThat(state.canonicalPublicState())
                    .as("DRAFT should not be exported")
                    .isNull();
            } else {
                assertThat(state.canonicalPublicState())
                    .as("State %s should map to a canonical public state", state)
                    .isNotNull();
            }
        }
    }
}
