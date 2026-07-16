package dev.pnyx.core.domain.participation;

import dev.pnyx.core.common.ContentHash;
import dev.pnyx.core.domain.proposal.*;
import dev.pnyx.core.domain.result.Result;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParticipationFloorTest {

    private static final String TITLE = "Test proposal";
    private static final String PROBLEM = "A substantive problem statement for participation testing";
    private static final String ACTION = "Test action";

    // -------------------------------------------------------
    // Helper methods
    // -------------------------------------------------------

    private static Proposal createDraftProposal() {
        return Proposal.create(TITLE, PROBLEM, ACTION).orElseThrow();
    }

    private static Proposal createClassifiedProposal() {
        return Proposal.create(TITLE, PROBLEM, ACTION)
            .orElseThrow().submit()
            .orElseThrow().defineProblem()
            .orElseThrow().markIntakeValid()
            .orElseThrow().classify("General", "Core", false).orElseThrow();
    }

    private static Proposal createPacketPublishedProposal() {
        return createClassifiedProposal()
            .startParticipationDesign().orElseThrow()
            .startPanelSelection().orElseThrow()
            .lockPanel(List.of("technical-reviewer")).orElseThrow()
            .startEvidenceAssembly().orElseThrow()
            .activateDeliberation().orElseThrow()
            .draftPacket().orElseThrow()
            .publishPacket(new ContentHash("sha256:0000000000000000000000000000000000000000000000000000000000000000")).orElseThrow();
    }

    // -------------------------------------------------------
    // 1. ParticipationPlan domain record
    // -------------------------------------------------------

    @Test
    void shouldCreateParticipationPlanWithOpenMode() {
        var plan = new ParticipationPlan(ProposalId.generate(), 1,
            List.of(ParticipationMode.OPEN), List.of("time_poverty"), List.of(),
            "none", List.of("plain_language_summary"), "lightweight", "active");
        assertThat(plan.selectedModes()).contains(ParticipationMode.OPEN);
        assertThat(plan.satisfiesMvpFloor()).isTrue();
    }

    @Test
    void shouldRejectPlanWithEmptyModes() {
        assertThatThrownBy(() -> new ParticipationPlan(ProposalId.generate(), 1,
            List.of(), List.of(), List.of(), "none", List.of(), "lightweight", "active"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectPlanWithBlankAuditCriteria() {
        assertThatThrownBy(() -> new ParticipationPlan(ProposalId.generate(), 1,
            List.of(ParticipationMode.OPEN), List.of(), List.of(), "none", List.of(), "", "active"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    // -------------------------------------------------------
    // 2. ParticipationAudit domain record
    // -------------------------------------------------------

    @Test
    void shouldCreateAuditWithFullDepth() {
        var audit = new ParticipationAudit(ProposalId.generate(), AuditDepth.FULL,
            "role:auditor-1", "no conflicts", "findings text", "limitations text", "open");
        assertThat(audit.depth()).isEqualTo(AuditDepth.FULL);
        assertThat(audit.allowsDecisionReadiness()).isTrue();
    }

    @Test
    void shouldBlockDecisionReadinessWhenChallengeUpheld() {
        var audit = new ParticipationAudit(ProposalId.generate(), AuditDepth.LIGHTWEIGHT,
            "role:auditor-1", "", "", "", "upheld_reopened");
        assertThat(audit.allowsDecisionReadiness()).isFalse();
    }

    // -------------------------------------------------------
    // 3. CivicReceipt domain record
    // -------------------------------------------------------

    @Test
    void shouldCreateCivicReceiptWithIncludedStatus() {
        var receipt = new CivicReceipt(ProposalId.generate(), "pseudo:participant-1",
            "evidence_submission", "", CivicReceiptStatus.INCLUDED, "sha256:abc123");
        assertThat(receipt.status()).isEqualTo(CivicReceiptStatus.INCLUDED);
    }

    // -------------------------------------------------------
    // 4. Proposal state transitions for participation
    // -------------------------------------------------------

    @Test
    void shouldTransitionFromClassifiedToParticipationDesignPending() {
        var proposal = createClassifiedProposal();
        var result = proposal.startParticipationDesign();
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.orElseThrow().state()).isEqualTo(ProposalState.PARTICIPATION_DESIGN_PENDING);
    }

    @Test
    void shouldTransitionFromParticipationDesignPendingToPanelSelection() {
        var proposal = createClassifiedProposal().startParticipationDesign().orElseThrow();
        var result = proposal.startPanelSelection();
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.orElseThrow().state()).isEqualTo(ProposalState.PANEL_SELECTION_PENDING);
    }

    @Test
    void shouldRejectStartParticipationDesignFromWrongState() {
        var proposal = createDraftProposal();
        var result = proposal.startParticipationDesign();
        assertThat(result.isFailure()).isTrue();
    }

    // -------------------------------------------------------
    // 5. Canonical public state mapping for new states
    // -------------------------------------------------------

    @Test
    void shouldMapParticipationDesignPendingToCanonical() {
        assertThat(ProposalState.PARTICIPATION_DESIGN_PENDING.canonicalPublicState())
            .isEqualTo(CanonicalPublicState.PARTICIPATION_DESIGNED);
    }

    @Test
    void shouldMapParticipantBodyFormationToCanonical() {
        assertThat(ProposalState.PARTICIPANT_BODY_FORMATION.canonicalPublicState())
            .isEqualTo(CanonicalPublicState.PARTICIPANT_BODY_READY);
    }

    // -------------------------------------------------------
    // 6. Participant body formation transitions
    // -------------------------------------------------------

    @Test
    void shouldFormParticipantBodyFromPacketPublished() {
        var proposal = createPacketPublishedProposal();
        var result = proposal.formParticipantBody();
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.orElseThrow().state()).isEqualTo(ProposalState.PARTICIPANT_BODY_FORMATION);
    }

    @Test
    void shouldCompleteBodyFormationToPublicReviewOpen() {
        var proposal = createPacketPublishedProposal().formParticipantBody().orElseThrow();
        var result = proposal.completeBodyFormation();
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.orElseThrow().state()).isEqualTo(ProposalState.PUBLIC_REVIEW_OPEN);
    }
}
