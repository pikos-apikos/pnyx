package dev.pnyx.core.api;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import dev.pnyx.core.domain.participation.AuditDepth;
import dev.pnyx.core.domain.participation.CivicReceiptStatus;
import dev.pnyx.core.domain.participation.ParticipationMode;
import dev.pnyx.core.domain.proposal.ProposalId;

/**
 * Driving port for proposal submission and lookup use cases.
 * <p>
 * Per {@code ../docs/80_Runtime/API_SPEC.md}, the API exists to expose controlled access to the
 * protocol, not to bypass it. Use cases on this interface are the driving ports into the
 * proposal submission and lifecycle management subsystem.
 *
 * @see ../docs/80_Runtime/API_SPEC.md
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public interface ProposalApi {

    /**
     * Command carrying the public proposal text and submitting actor.
     */
    record SubmitProposalCommand(String title, String problem,
                                 String proposedAction, String actorId) { }

    /**
     * Read model returned to API and UI callers after proposal operations.
     */
    record ProposalView(ProposalId id, String title, String state,
                        String classification, String createdAt,
                        ValidationView validation,
                        String problem, String proposedAction) { }

    /**
     * Embedded validation summary shown with a proposal view.
     */
    record ValidationView(boolean isDiscussable, List<String> missingFields,
                          List<String> clarifyingQuestions, List<String> flags) { }

    /**
     * Submits a proposal and returns its initial read model.
     *
     * @param command the submission command with title, problem, action and actor
     * @return the created proposal view
     */
    ProposalView submit(SubmitProposalCommand command);

    /**
     * Finds a proposal read model by aggregate identifier.
     *
     * @param id the proposal identifier
     * @return the proposal view if found
     */
    Optional<ProposalView> findById(ProposalId id);

    /**
     * Lightweight summary of a proposal for the dashboard listing.
     * Backed by the public artifact store (authoritative public truth).
     */
    record ProposalSummary(ProposalId id, String title, String state, String createdAt) { }

    /**
     * Lists the most recent proposal summaries, backed by the public artifact index.
     * Ordered by creation timestamp, newest first.
     *
     * @return list of recent proposal summaries
     */
    List<ProposalSummary> listRecent();

    // ─── Participation floor (MVP §4.11) ───

    /**
     * Command to create a Participation Plan for a non-trivial proposal.
     * <p>
     * Per {@code ../docs/45_Participation/PARTICIPATION_MODEL.md §6}.
     */
    record CreateParticipationPlanCommand(
        ProposalId proposalId,
        List<ParticipationMode> selectedModes,
        List<String> expectedBarriers,
        List<String> missingPerspectives,
        String compensationRules,
        List<String> accessibilityMeasures,
        String auditCriteria,
        String actorId
    ) { }

    /**
     * Read model for a Participation Plan.
     */
    record ParticipationPlanView(
        ProposalId proposalId,
        int versionNo,
        List<String> selectedModes,
        List<String> expectedBarriers,
        List<String> missingPerspectives,
        String auditCriteria,
        String status
    ) { }

    /**
     * Creates a Participation Plan for a proposal, transitioning it from
     * {@code PARTICIPATION_DESIGN_PENDING} to {@code PANEL_SELECTION_PENDING}.
     *
     * @param command the plan creation command
     * @return the created plan view
     */
    ParticipationPlanView createParticipationPlan(CreateParticipationPlanCommand command);

    /**
     * Retrieves the Participation Plan for a proposal.
     *
     * @param proposalId the proposal identifier
     * @return the plan view if found
     */
    Optional<ParticipationPlanView> getParticipationPlan(ProposalId proposalId);

    /**
     * Command to issue a Participation Audit before decision-readiness.
     * <p>
     * Per {@code ../docs/45_Participation/PARTICIPATION_MODEL.md §11}.
     */
    record IssueParticipationAuditCommand(
        ProposalId proposalId,
        AuditDepth depth,
        String auditorRef,
        String auditorConflictDeclaration,
        String findings,
        String limitations,
        String actorId
    ) { }

    /**
     * Read model for a Participation Audit.
     */
    record ParticipationAuditView(
        ProposalId proposalId,
        String depth,
        String auditorRef,
        String findings,
        String limitations,
        String challengeableStatus
    ) { }

    /**
     * Issues a Participation Audit for a proposal, satisfying the
     * {@code PUBLIC_REVIEW_OPEN → READY_FOR_DECISION} guard.
     *
     * @param command the audit issuance command
     * @return the created audit view
     */
    ParticipationAuditView issueParticipationAudit(IssueParticipationAuditCommand command);

    /**
     * Retrieves the Participation Audit for a proposal.
     *
     * @param proposalId the proposal identifier
     * @return the audit view if found
     */
    Optional<ParticipationAuditView> getParticipationAudit(ProposalId proposalId);

    /**
     * Command to issue a Civic Receipt for a participant action.
     * <p>
     * Per {@code ../docs/45_Participation/PARTICIPATION_MODEL.md §12}. MVP allows
     * a logged, challengeable equivalent per {@code MINIMUM_VIABLE_PNYX.md §5.1}.
     */
    record IssueCivicReceiptCommand(
        ProposalId proposalId,
        String participantRef,
        String actionType,
        String actorId
    ) { }

    /**
     * Read model for a Civic Receipt.
     */
    record CivicReceiptView(
        ProposalId proposalId,
        String participantRef,
        String actionType,
        String status,
        String inclusionProofRef
    ) { }

    /**
     * Issues a Civic Receipt for a participant action on a proposal.
     *
     * @param command the receipt issuance command
     * @return the created receipt view
     */
    CivicReceiptView issueCivicReceipt(IssueCivicReceiptCommand command);

    // ─── Execution Mandate (STATE_MACHINE.md §4.6.2) ───

    /**
     * Command to issue an Execution Mandate for an approved proposal.
     * <p>
     * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.6.2}, the mandate provides
     * explicit authorization with defined constraints, resources, and monitoring
     * obligations before active execution begins.
     */
    record IssueExecutionMandateCommand(
        ProposalId proposalId,
        String authorizedActor,
        List<String> permittedActions,
        List<String> prohibitedActions,
        String resourceAllocation,
        List<String> successCriteria,
        List<String> failureCriteria,
        List<String> monitoringObligations,
        List<String> rollbackConditions,
        String actorId
    ) { }

    /**
     * Read model for an Execution Mandate.
     */
    record ExecutionMandateView(
        ProposalId proposalId,
        String authorizedActor,
        List<String> permittedActions,
        String resourceAllocation,
        String issuedAt
    ) { }

    /**
     * Issues an Execution Mandate for a proposal, transitioning it from
     * {@code ROUTED} to {@code EXECUTION_AUTHORIZED}.
     *
     * @param command the mandate issuance command
     * @return the created mandate view
     */
    ExecutionMandateView issueExecutionMandate(IssueExecutionMandateCommand command);

    /**
     * Retrieves the Execution Mandate for a proposal.
     *
     * @param proposalId the proposal identifier
     * @return the mandate view if found
     */
    Optional<ExecutionMandateView> getExecutionMandate(ProposalId proposalId);

    // ─── Monitoring and Learning (STATE_MACHINE.md §4.6.3) ───

    /**
     * Command to start monitoring execution outcomes.
     * <p>
     * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.6.3}, monitoring is entered
     * after execution completion and defines milestones, metrics, reporting
     * frequency, and responsible party.
     */
    record StartMonitoringCommand(
        ProposalId proposalId,
        Instant monitoringStartDate,
        Instant monitoringEndDate,
        List<String> milestones,
        List<String> metrics,
        String reportingFrequency,
        String responsibleParty,
        String actorId
    ) { }

    /**
     * Command to publish learning artifacts after monitoring.
     * <p>
     * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.6.3}, learning publication
     * captures lessons, success/failure factors, and recommendations for
     * systemic improvement.
     */
    record PublishLearningCommand(
        ProposalId proposalId,
        List<String> lessonsLearned,
        List<String> successFactors,
        List<String> failureFactors,
        List<String> recommendations,
        List<String> applicableContexts,
        String publishedBy,
        String actorId
    ) { }

    /**
     * Read model for a Monitoring Record.
     */
    record MonitoringRecordView(
        ProposalId proposalId,
        String responsibleParty,
        String status,
        String monitoringStartDate,
        String monitoringEndDate
    ) { }

    /**
     * Read model for a Learning Artifact.
     */
    record LearningArtifactView(
        ProposalId proposalId,
        List<String> lessonsLearned,
        String publishedBy,
        String publishedAt
    ) { }

    /**
     * Starts monitoring execution outcomes, transitioning from
     * {@code EXECUTION_COMPLETED} to {@code MONITORING_ACTIVE}.
     *
     * @param command the monitoring start command
     * @return the created monitoring record view
     */
    MonitoringRecordView startMonitoring(StartMonitoringCommand command);

    /**
     * Retrieves the monitoring record for a proposal.
     *
     * @param proposalId the proposal identifier
     * @return the monitoring record view if found
     */
    Optional<MonitoringRecordView> getMonitoringRecord(ProposalId proposalId);

    /**
     * Publishes learning artifacts after monitoring, transitioning from
     * {@code MONITORING_ACTIVE} to {@code LEARNING_PUBLISHED}.
     *
     * @param command the publish learning command
     * @return the created learning artifact view
     */
    LearningArtifactView publishLearning(PublishLearningCommand command);

    /**
     * Retrieves the learning artifact for a proposal.
     *
     * @param proposalId the proposal identifier
     * @return the learning artifact view if found
     */
    Optional<LearningArtifactView> getLearningArtifact(ProposalId proposalId);
}
