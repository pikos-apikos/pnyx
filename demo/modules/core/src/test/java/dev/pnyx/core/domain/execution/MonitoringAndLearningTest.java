package dev.pnyx.core.domain.execution;

import dev.pnyx.core.domain.proposal.CanonicalPublicState;
import dev.pnyx.core.domain.proposal.LearningPublished;
import dev.pnyx.core.domain.proposal.MonitoringStarted;
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

class MonitoringAndLearningTest {

    private static final String TITLE = "Test Proposal";
    private static final String PROBLEM = "A substantive problem requiring execution and monitoring";
    private static final String ACTION = "Proposed action";
    private static final String RESPONSIBLE_PARTY = "oversight-committee";
    private static final String PUBLISHED_BY = "review-panel";
    private static final String MONTHLY = "monthly";
    private static final String ACTIVE = "active";
    private static final String LESSON1 = "lesson1";
    private static final String LESSON2 = "lesson2";

    private Proposal createCompletedExecutionProposal() {
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
        var routed = routingStarted.completeRouting().orElseThrow();
        var executing = routed.startExecution().orElseThrow();
        return executing.completeExecution().orElseThrow();
    }

    private MonitoringRecord createValidMonitoringRecord(ProposalId proposalId) {
        return new MonitoringRecord(
            proposalId,
            Instant.now(),
            null,
            List.of("milestone1", "milestone2"),
            List.of("metric1", "metric2"),
            MONTHLY,
            RESPONSIBLE_PARTY,
            ACTIVE);
    }

    private LearningArtifact createValidLearningArtifact(ProposalId proposalId) {
        return new LearningArtifact(
            proposalId,
            List.of(LESSON1, LESSON2),
            List.of("success1"),
            List.of("failure1"),
            List.of("recommendation1"),
            List.of("context1"),
            Instant.now(),
            PUBLISHED_BY);
    }

    // ─── MonitoringRecord domain type tests ───

    @Test
    void shouldCreateMonitoringRecordWithValidFields() {
        var proposal = createCompletedExecutionProposal();
        var record = createValidMonitoringRecord(proposal.proposalId());

        assertThat(record.proposalId()).isEqualTo(proposal.proposalId());
        assertThat(record.responsibleParty()).isEqualTo(RESPONSIBLE_PARTY);
        assertThat(record.milestones()).containsExactly("milestone1", "milestone2");
        assertThat(record.metrics()).containsExactly("metric1", "metric2");
        assertThat(record.reportingFrequency()).isEqualTo(MONTHLY);
        assertThat(record.status()).isEqualTo(ACTIVE);
        assertThat(record.monitoringStartDate()).isNotNull();
    }

    @Test
    void shouldRejectMonitoringRecordWithBlankResponsibleParty() {
        var id = ProposalId.generate();
        assertThatThrownBy(() -> new MonitoringRecord(
            id, Instant.now(), null, List.of(), List.of(), MONTHLY, "  ", ACTIVE))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("responsibleParty");
    }

    @Test
    void shouldRejectMonitoringRecordWithNullResponsibleParty() {
        var id = ProposalId.generate();
        assertThatThrownBy(() -> new MonitoringRecord(
            id, Instant.now(), null, List.of(), List.of(), MONTHLY, null, ACTIVE))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("responsibleParty");
    }

    @Test
    void shouldRejectMonitoringRecordWithNullProposalId() {
        assertThatThrownBy(() -> new MonitoringRecord(
            null, Instant.now(), null, List.of(), List.of(), MONTHLY, RESPONSIBLE_PARTY, ACTIVE))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("proposalId");
    }

    @Test
    void shouldRejectMonitoringRecordWithNullStartDate() {
        var id = ProposalId.generate();
        assertThatThrownBy(() -> new MonitoringRecord(
            id, null, null, List.of(), List.of(), MONTHLY, RESPONSIBLE_PARTY, ACTIVE))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("monitoringStartDate");
    }

    @Test
    void shouldMakeDefensiveCopyOfMonitoringRecordLists() {
        var id = ProposalId.generate();
        var milestones = new java.util.ArrayList<>(List.of("m1"));
        var record = new MonitoringRecord(
            id, Instant.now(), null, milestones, List.of(), MONTHLY, RESPONSIBLE_PARTY, ACTIVE);
        milestones.add("m2");
        assertThat(record.milestones()).containsExactly("m1");
    }

    // ─── LearningArtifact domain type tests ───

    @Test
    void shouldCreateLearningArtifactWithValidFields() {
        var proposal = createCompletedExecutionProposal();
        var artifact = createValidLearningArtifact(proposal.proposalId());

        assertThat(artifact.proposalId()).isEqualTo(proposal.proposalId());
        assertThat(artifact.lessonsLearned()).containsExactly(LESSON1, LESSON2);
        assertThat(artifact.successFactors()).containsExactly("success1");
        assertThat(artifact.failureFactors()).containsExactly("failure1");
        assertThat(artifact.recommendations()).containsExactly("recommendation1");
        assertThat(artifact.applicableContexts()).containsExactly("context1");
        assertThat(artifact.publishedBy()).isEqualTo(PUBLISHED_BY);
        assertThat(artifact.publishedAt()).isNotNull();
    }

    @Test
    void shouldRejectLearningArtifactWithBlankPublishedBy() {
        var id = ProposalId.generate();
        assertThatThrownBy(() -> new LearningArtifact(
            id, List.of(), List.of(), List.of(), List.of(), List.of(), Instant.now(), "  "))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("publishedBy");
    }

    @Test
    void shouldRejectLearningArtifactWithNullPublishedBy() {
        var id = ProposalId.generate();
        assertThatThrownBy(() -> new LearningArtifact(
            id, List.of(), List.of(), List.of(), List.of(), List.of(), Instant.now(), null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("publishedBy");
    }

    @Test
    void shouldRejectLearningArtifactWithNullProposalId() {
        assertThatThrownBy(() -> new LearningArtifact(
            null, List.of(), List.of(), List.of(), List.of(), List.of(), Instant.now(), PUBLISHED_BY))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("proposalId");
    }

    @Test
    void shouldMakeDefensiveCopyOfLearningArtifactLists() {
        var id = ProposalId.generate();
        var lessons = new java.util.ArrayList<>(List.of(LESSON1));
        var artifact = new LearningArtifact(
            id, lessons, List.of(), List.of(), List.of(), List.of(), Instant.now(), PUBLISHED_BY);
        lessons.add("lesson2");
        assertThat(artifact.lessonsLearned()).containsExactly(LESSON1);
    }

    // ─── State machine transition: EXECUTION_COMPLETED → MONITORING_ACTIVE ───

    @Test
    void shouldTransitionFromExecutionCompletedToMonitoringActive() {
        var proposal = createCompletedExecutionProposal();
        assertThat(proposal.state()).isEqualTo(ProposalState.EXECUTION_COMPLETED);

        var record = createValidMonitoringRecord(proposal.proposalId());
        var result = proposal.startMonitoring(record);

        assertTrue(result.isSuccess());
        Proposal monitored = result.orElseThrow();
        assertThat(monitored.state()).isEqualTo(ProposalState.MONITORING_ACTIVE);
    }

    @Test
    void shouldRejectMonitoringStartFromWrongState() {
        var draft = Proposal.create(TITLE, PROBLEM, ACTION).orElseThrow();
        var record = createValidMonitoringRecord(draft.proposalId());
        var result = draft.startMonitoring(record);

        assertTrue(result.isFailure());
        var error = ((Result.Failure<Proposal, ProposalError>) result).error();
        assertInstanceOf(ProposalError.WrongState.class, error);
    }

    @Test
    void shouldEmitMonitoringStartedEvent() {
        var proposal = createCompletedExecutionProposal();
        var record = createValidMonitoringRecord(proposal.proposalId());
        var result = proposal.startMonitoring(record);

        assertTrue(result.isSuccess());
        Proposal monitored = result.orElseThrow();
        var events = monitored.uncommittedEvents();
        assertThat(events).isNotEmpty();
        assertThat(events.getLast()).isInstanceOf(MonitoringStarted.class);
        var evt = (MonitoringStarted) events.getLast();
        assertThat(evt.proposalId()).isEqualTo(proposal.proposalId());
        assertThat(evt.responsibleParty()).isEqualTo(RESPONSIBLE_PARTY);
    }

    // ─── State machine transition: MONITORING_ACTIVE → LEARNING_PUBLISHED ───

    @Test
    void shouldTransitionFromMonitoringActiveToLearningPublished() {
        var proposal = createCompletedExecutionProposal();
        var record = createValidMonitoringRecord(proposal.proposalId());
        var monitored = proposal.startMonitoring(record).orElseThrow();
        assertThat(monitored.state()).isEqualTo(ProposalState.MONITORING_ACTIVE);

        var artifact = createValidLearningArtifact(monitored.proposalId());
        var result = monitored.publishLearning(artifact);

        assertTrue(result.isSuccess());
        Proposal published = result.orElseThrow();
        assertThat(published.state()).isEqualTo(ProposalState.LEARNING_PUBLISHED);
    }

    @Test
    void shouldRejectLearningPublicationFromWrongState() {
        var proposal = createCompletedExecutionProposal();
        var artifact = createValidLearningArtifact(proposal.proposalId());
        var result = proposal.publishLearning(artifact);

        assertTrue(result.isFailure());
        var error = ((Result.Failure<Proposal, ProposalError>) result).error();
        assertInstanceOf(ProposalError.WrongState.class, error);
    }

    @Test
    void shouldEmitLearningPublishedEvent() {
        var proposal = createCompletedExecutionProposal();
        var record = createValidMonitoringRecord(proposal.proposalId());
        var monitored = proposal.startMonitoring(record).orElseThrow();
        var artifact = createValidLearningArtifact(monitored.proposalId());
        var result = monitored.publishLearning(artifact);

        assertTrue(result.isSuccess());
        Proposal published = result.orElseThrow();
        var events = published.uncommittedEvents();
        assertThat(events).isNotEmpty();
        assertThat(events.getLast()).isInstanceOf(LearningPublished.class);
        var evt = (LearningPublished) events.getLast();
        assertThat(evt.proposalId()).isEqualTo(monitored.proposalId());
        assertThat(evt.publishedBy()).isEqualTo(PUBLISHED_BY);
    }

    // ─── Canonical public state mapping ───

    @Test
    void shouldMapMonitoringActiveToMonitoringCanonicalState() {
        var proposal = createCompletedExecutionProposal();
        var record = createValidMonitoringRecord(proposal.proposalId());
        var monitored = proposal.startMonitoring(record).orElseThrow();

        assertThat(monitored.canonicalPublicState())
            .isEqualTo(CanonicalPublicState.MONITORING);
    }

    @Test
    void shouldMapLearningPublishedToLearningPublishedCanonicalState() {
        var proposal = createCompletedExecutionProposal();
        var record = createValidMonitoringRecord(proposal.proposalId());
        var monitored = proposal.startMonitoring(record).orElseThrow();
        var artifact = createValidLearningArtifact(monitored.proposalId());
        var published = monitored.publishLearning(artifact).orElseThrow();

        assertThat(published.canonicalPublicState())
            .isEqualTo(CanonicalPublicState.LEARNING_PUBLISHED);
    }

    // ─── Post-review from new states ───

    @Test
    void shouldOpenPostReviewFromLearningPublished() {
        var proposal = createCompletedExecutionProposal();
        var record = createValidMonitoringRecord(proposal.proposalId());
        var monitored = proposal.startMonitoring(record).orElseThrow();
        var artifact = createValidLearningArtifact(monitored.proposalId());
        var published = monitored.publishLearning(artifact).orElseThrow();

        var result = published.openPostReview();
        assertTrue(result.isSuccess());
        assertThat(result.orElseThrow().state()).isEqualTo(ProposalState.POST_REVIEW_OPEN);
    }

    @Test
    void shouldRejectNullMonitoringRecord() {
        var proposal = createCompletedExecutionProposal();
        var result = proposal.startMonitoring(null);

        assertTrue(result.isFailure());
        var error = ((Result.Failure<Proposal, ProposalError>) result).error();
        assertInstanceOf(ProposalError.InvalidMonitoringRecord.class, error);
    }

    @Test
    void shouldRejectNullLearningArtifact() {
        var proposal = createCompletedExecutionProposal();
        var record = createValidMonitoringRecord(proposal.proposalId());
        var monitored = proposal.startMonitoring(record).orElseThrow();
        var result = monitored.publishLearning(null);

        assertTrue(result.isFailure());
        var error = ((Result.Failure<Proposal, ProposalError>) result).error();
        assertInstanceOf(ProposalError.InvalidLearningArtifact.class, error);
    }
}
