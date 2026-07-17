package dev.pnyx.core.domain.execution;

import dev.pnyx.core.domain.proposal.CanonicalPublicState;
import dev.pnyx.core.domain.proposal.ExecutionMandateIssued;
import dev.pnyx.core.domain.proposal.Proposal;
import dev.pnyx.core.domain.proposal.ProposalError;
import dev.pnyx.core.domain.proposal.ProposalId;
import dev.pnyx.core.domain.proposal.ProposalState;
import dev.pnyx.core.domain.result.Result;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExecutionMandateTest {

    private static final String TITLE = "Test Proposal";
    private static final String PROBLEM = "A substantive problem requiring execution authorization";
    private static final String ACTION = "Proposed action";
    private static final String ACTOR = "city-council";

    private Proposal createRoutedProposal() {
        var draft = Proposal.create(TITLE, PROBLEM, ACTION).orElseThrow();
        var submitted = draft.submit().orElseThrow();
        var defined = submitted.defineProblem().orElseThrow();
        var intakeValid = defined.markIntakeValid().orElseThrow();
        var classified = intakeValid.classify("policy", "Core", false).orElseThrow();
        var designStarted = classified.startParticipationDesign().orElseThrow();
        var panelStarted = designStarted.startPanelSelection().orElseThrow();
        var panelLocked = panelStarted.lockPanel(List.of("technical-reviewer")).orElseThrow();
        var evidenceStarted = panelLocked.startEvidenceAssembly().orElseThrow();
        var deliberation = evidenceStarted.activateDeliberation().orElseThrow();
        var drafting = deliberation.draftPacket().orElseThrow();
        var published = drafting.publishPacket(
            new dev.pnyx.core.common.ContentHash("sha256:0000000000000000000000000000000000000000000000000000000000000000"))
            .orElseThrow();
        var reviewOpened = published.openPublicReview().orElseThrow();
        var reviewClosed = reviewOpened.closeReview().orElseThrow();
        var decisionOpened = reviewClosed.openDecision().orElseThrow();
        var approved = decisionOpened.approve().orElseThrow();
        var routingStarted = approved.startRouting().orElseThrow();
        return routingStarted.completeRouting().orElseThrow();
    }

    private ExecutionMandate createValidMandate(ProposalId proposalId) {
        return new ExecutionMandate(
            proposalId,
            ACTOR,
            List.of("procure_equipment", "hire_contractors"),
            List.of("exceed_budget", "violate_zoning"),
            "$500,000 from general fund",
            List.of("installation_complete", "training_delivered"),
            List.of("budget_overrun_20pct", "community_opposition"),
            List.of("monthly_progress_report", "audit_at_50pct"),
            List.of("unspent_funds_returned", "equipment_removable"),
            Instant.now());
    }

    @Test
    void shouldCreateExecutionMandateWithValidFields() {
        var proposal = createRoutedProposal();
        var mandate = createValidMandate(proposal.proposalId());

        assertThat(mandate.proposalId()).isEqualTo(proposal.proposalId());
        assertThat(mandate.authorizedActor()).isEqualTo(ACTOR);
        assertThat(mandate.permittedActions()).containsExactly("procure_equipment", "hire_contractors");
        assertThat(mandate.resourceAllocation()).isEqualTo("$500,000 from general fund");
        assertThat(mandate.issuedAt()).isNotNull();
    }

    @Test
    void shouldRejectMandateWithBlankAuthorizedActor() {
        var id = ProposalId.generate();
        assertThatThrownBy(() -> new ExecutionMandate(
            id, "  ", List.of(), List.of(), null, List.of(), List.of(), List.of(), List.of(), Instant.now()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("authorizedActor");
    }

    @Test
    void shouldRejectMandateWithNullAuthorizedActor() {
        var id = ProposalId.generate();
        assertThatThrownBy(() -> new ExecutionMandate(
            id, null, List.of(), List.of(), null, List.of(), List.of(), List.of(), List.of(), Instant.now()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("authorizedActor");
    }

    @Test
    void shouldRejectMandateWithNullProposalId() {
        assertThatThrownBy(() -> new ExecutionMandate(
            null, ACTOR, List.of(), List.of(), null, List.of(), List.of(), List.of(), List.of(), Instant.now()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("proposalId");
    }

    @Test
    void shouldRejectMandateWithNullIssuedAt() {
        var id = ProposalId.generate();
        assertThatThrownBy(() -> new ExecutionMandate(
            id, ACTOR, List.of(), List.of(), null, List.of(), List.of(), List.of(), List.of(), null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("issuedAt");
    }

    @Test
    void shouldMakeDefensiveCopyOfLists() {
        var id = ProposalId.generate();
        var actions = new java.util.ArrayList<>(List.of("action1"));
        var mandate = new ExecutionMandate(
            id, ACTOR, actions, List.of(), null, List.of(), List.of(), List.of(), List.of(), Instant.now());
        actions.add("action2");
        assertThat(mandate.permittedActions()).containsExactly("action1");
    }

    @Test
    void shouldTransitionFromRoutedToExecutionAuthorized() {
        var proposal = createRoutedProposal();
        assertThat(proposal.state()).isEqualTo(ProposalState.ROUTED);

        var mandate = createValidMandate(proposal.proposalId());
        var result = proposal.authorizeExecution(mandate);

        assertTrue(result.isSuccess());
        Proposal authorized = result.orElseThrow();
        assertThat(authorized.state()).isEqualTo(ProposalState.EXECUTION_AUTHORIZED);
    }

    @Test
    void shouldRejectAuthorizationFromWrongState() {
        var draft = Proposal.create(TITLE, PROBLEM, ACTION).orElseThrow();
        var mandate = createValidMandate(draft.proposalId());
        var result = draft.authorizeExecution(mandate);

        assertTrue(result.isFailure());
        var error = ((Result.Failure<Proposal, ProposalError>) result).error();
        assertInstanceOf(ProposalError.WrongState.class, error);
    }

    @Test
    void shouldRejectNullMandate() {
        var proposal = createRoutedProposal();
        var result = proposal.authorizeExecution(null);

        assertTrue(result.isFailure());
        var error = ((Result.Failure<Proposal, ProposalError>) result).error();
        assertInstanceOf(ProposalError.InvalidExecutionMandate.class, error);
    }

    @Test
    void shouldEmitExecutionMandateIssuedEvent() {
        var proposal = createRoutedProposal();
        var mandate = createValidMandate(proposal.proposalId());
        var result = proposal.authorizeExecution(mandate);

        assertTrue(result.isSuccess());
        Proposal authorized = result.orElseThrow();
        var events = authorized.uncommittedEvents();
        assertThat(events).isNotEmpty();
        assertThat(events.getLast()).isInstanceOf(ExecutionMandateIssued.class);
        var evt = (ExecutionMandateIssued) events.getLast();
        assertThat(evt.proposalId()).isEqualTo(proposal.proposalId());
        assertThat(evt.authorizedActor()).isEqualTo(ACTOR);
    }

    @Test
    void shouldMapExecutionAuthorizedToCanonicalPublicState() {
        var proposal = createRoutedProposal();
        var mandate = createValidMandate(proposal.proposalId());
        var result = proposal.authorizeExecution(mandate);

        assertTrue(result.isSuccess());
        Proposal authorized = result.orElseThrow();
        assertThat(authorized.canonicalPublicState())
            .isEqualTo(CanonicalPublicState.EXECUTION_AUTHORIZED);
    }

    @Test
    void shouldStartExecutionFromAuthorizedState() {
        var proposal = createRoutedProposal();
        var mandate = createValidMandate(proposal.proposalId());
        var authorized = proposal.authorizeExecution(mandate).orElseThrow();

        var execResult = authorized.startExecution();
        assertTrue(execResult.isSuccess());
        assertThat(execResult.orElseThrow().state()).isEqualTo(ProposalState.EXECUTION_ACTIVE);
    }
}
