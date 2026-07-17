package dev.pnyx.endpoint.rest;

import dev.pnyx.core.api.ProposalApi;
import dev.pnyx.core.api.ProposalApi.CreateParticipationPlanCommand;
import dev.pnyx.core.api.ProposalApi.IssueCivicReceiptCommand;
import dev.pnyx.core.api.ProposalApi.IssueExecutionMandateCommand;
import dev.pnyx.core.api.ProposalApi.IssueParticipationAuditCommand;
import dev.pnyx.core.api.ProposalApi.SubmitProposalCommand;
import dev.pnyx.core.domain.proposal.ProposalId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST endpoint for proposal submission and retrieval.
 *
 * @see ../docs/80_Runtime/API_SPEC.md
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/proposals")
public class ProposalController {

    private final ProposalApi proposalApi;

    /**
     * Submits a new proposal.
     *
     * @param command the submission command
     * @return the created proposal view
     */
    @PostMapping
    public ResponseEntity<ProposalApi.ProposalView> submit(
        @RequestBody SubmitProposalCommand command) {
        return ResponseEntity.ok(proposalApi.submit(command));
    }

    /**
     * Retrieves a proposal by its ID.
     *
     * @param id the proposal UUID
     * @return the proposal view, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProposalApi.ProposalView> get(@PathVariable UUID id) {
        return proposalApi.findById(new ProposalId(id))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a participation plan for a proposal.
     * <p>
     * Per {@code ../docs/45_Participation/PARTICIPATION_MODEL.md §6}.
     *
     * @param id      the proposal UUID
     * @param command the plan creation command (proposalId is overridden from the path)
     * @return the created plan view
     * @see ../docs/45_Participation/PARTICIPATION_MODEL.md
     */
    @PostMapping("/{id}/participation/plan")
    public ResponseEntity<ProposalApi.ParticipationPlanView> createParticipationPlan(
        @PathVariable UUID id,
        @RequestBody CreateParticipationPlanCommand command) {
        var cmd = new ProposalApi.CreateParticipationPlanCommand(
            new ProposalId(id),
            command.selectedModes(),
            command.expectedBarriers(),
            command.missingPerspectives(),
            command.compensationRules(),
            command.accessibilityMeasures(),
            command.auditCriteria(),
            command.actorId());
        return ResponseEntity.ok(proposalApi.createParticipationPlan(cmd));
    }

    /**
     * Retrieves the participation plan for a proposal.
     *
     * @param id the proposal UUID
     * @return the plan view, or 404 if not found
     * @see ../docs/45_Participation/PARTICIPATION_MODEL.md
     */
    @GetMapping("/{id}/participation/plan")
    public ResponseEntity<ProposalApi.ParticipationPlanView> getParticipationPlan(@PathVariable UUID id) {
        return proposalApi.getParticipationPlan(new ProposalId(id))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Issues a participation audit for a proposal.
     * <p>
     * Per {@code ../docs/45_Participation/PARTICIPATION_MODEL.md §11}.
     *
     * @param id      the proposal UUID
     * @param command the audit command (proposalId is overridden from the path)
     * @return the created audit view
     * @see ../docs/45_Participation/PARTICIPATION_MODEL.md
     */
    @PostMapping("/{id}/participation/audit")
    public ResponseEntity<ProposalApi.ParticipationAuditView> issueParticipationAudit(
        @PathVariable UUID id,
        @RequestBody IssueParticipationAuditCommand command) {
        var cmd = new ProposalApi.IssueParticipationAuditCommand(
            new ProposalId(id),
            command.depth(),
            command.auditorRef(),
            command.auditorConflictDeclaration(),
            command.findings(),
            command.limitations(),
            command.actorId());
        return ResponseEntity.ok(proposalApi.issueParticipationAudit(cmd));
    }

    /**
     * Retrieves the participation audit for a proposal.
     *
     * @param id the proposal UUID
     * @return the audit view, or 404 if not found
     * @see ../docs/45_Participation/PARTICIPATION_MODEL.md
     */
    @GetMapping("/{id}/participation/audit")
    public ResponseEntity<ProposalApi.ParticipationAuditView> getParticipationAudit(@PathVariable UUID id) {
        return proposalApi.getParticipationAudit(new ProposalId(id))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Issues a civic receipt for a participant action on a proposal.
     * <p>
     * Per {@code ../docs/45_Participation/PARTICIPATION_MODEL.md §12}.
     *
     * @param id      the proposal UUID
     * @param command the receipt command (proposalId is overridden from the path)
     * @return the created receipt view
     * @see ../docs/45_Participation/PARTICIPATION_MODEL.md
     */
    @PostMapping("/{id}/participation/receipt")
    public ResponseEntity<ProposalApi.CivicReceiptView> issueCivicReceipt(
        @PathVariable UUID id,
        @RequestBody IssueCivicReceiptCommand command) {
        var cmd = new ProposalApi.IssueCivicReceiptCommand(
            new ProposalId(id),
            command.participantRef(),
            command.actionType(),
            command.actorId());
        return ResponseEntity.ok(proposalApi.issueCivicReceipt(cmd));
    }

    /**
     * Issues an Execution Mandate for a routed proposal.
     * <p>
     * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.6.2}, the mandate provides
     * explicit authorization with defined constraints, resources, and monitoring
     * obligations before active execution begins.
     *
     * @param id      the proposal UUID
     * @param command the mandate issuance command (proposalId is overridden from the path)
     * @return the created mandate view
     * @see ../docs/80_Runtime/STATE_MACHINE.md
     * @see ../docs/90_Information/DATA_MODEL.md
     */
    @PostMapping("/{id}/execution/mandate")
    public ResponseEntity<ProposalApi.ExecutionMandateView> issueExecutionMandate(
        @PathVariable UUID id,
        @RequestBody IssueExecutionMandateCommand command) {
        var cmd = new ProposalApi.IssueExecutionMandateCommand(
            new ProposalId(id),
            command.authorizedActor(),
            command.permittedActions(),
            command.prohibitedActions(),
            command.resourceAllocation(),
            command.successCriteria(),
            command.failureCriteria(),
            command.monitoringObligations(),
            command.rollbackConditions(),
            command.actorId());
        return ResponseEntity.ok(proposalApi.issueExecutionMandate(cmd));
    }

    /**
     * Retrieves the Execution Mandate for a proposal.
     *
     * @param id the proposal UUID
     * @return the mandate view, or 404 if not found
     */
    @GetMapping("/{id}/execution/mandate")
    public ResponseEntity<ProposalApi.ExecutionMandateView> getExecutionMandate(@PathVariable UUID id) {
        return proposalApi.getExecutionMandate(new ProposalId(id))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // ─── Monitoring and Learning (STATE_MACHINE.md §4.6.3) ───

    /**
     * Starts monitoring execution outcomes for a proposal.
     * <p>
     * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.6.3}, this transitions
     * the proposal from {@code EXECUTION_COMPLETED} to {@code MONITORING_ACTIVE}.
     *
     * @param id      the proposal UUID
     * @param command the monitoring start command
     * @return the created monitoring record view
     */
    @PostMapping("/{id}/monitoring/start")
    public ResponseEntity<ProposalApi.MonitoringRecordView> startMonitoring(
        @PathVariable UUID id,
        @RequestBody ProposalApi.StartMonitoringCommand command) {
        var cmd = new ProposalApi.StartMonitoringCommand(
            new ProposalId(id),
            command.monitoringStartDate(),
            command.monitoringEndDate(),
            command.milestones(),
            command.metrics(),
            command.reportingFrequency(),
            command.responsibleParty(),
            command.actorId());
        return ResponseEntity.ok(proposalApi.startMonitoring(cmd));
    }

    /**
     * Retrieves the monitoring record for a proposal.
     *
     * @param id the proposal UUID
     * @return the monitoring record view, or 404 if not found
     */
    @GetMapping("/{id}/monitoring")
    public ResponseEntity<ProposalApi.MonitoringRecordView> getMonitoringRecord(@PathVariable UUID id) {
        return proposalApi.getMonitoringRecord(new ProposalId(id))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Publishes learning artifacts after monitoring.
     * <p>
     * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.6.3}, this transitions
     * the proposal from {@code MONITORING_ACTIVE} to {@code LEARNING_PUBLISHED}.
     *
     * @param id      the proposal UUID
     * @param command the publish learning command
     * @return the created learning artifact view
     */
    @PostMapping("/{id}/learning/publish")
    public ResponseEntity<ProposalApi.LearningArtifactView> publishLearning(
        @PathVariable UUID id,
        @RequestBody ProposalApi.PublishLearningCommand command) {
        var cmd = new ProposalApi.PublishLearningCommand(
            new ProposalId(id),
            command.lessonsLearned(),
            command.successFactors(),
            command.failureFactors(),
            command.recommendations(),
            command.applicableContexts(),
            command.publishedBy(),
            command.actorId());
        return ResponseEntity.ok(proposalApi.publishLearning(cmd));
    }

    /**
     * Retrieves the learning artifact for a proposal.
     *
     * @param id the proposal UUID
     * @return the learning artifact view, or 404 if not found
     */
    @GetMapping("/{id}/learning")
    public ResponseEntity<ProposalApi.LearningArtifactView> getLearningArtifact(@PathVariable UUID id) {
        return proposalApi.getLearningArtifact(new ProposalId(id))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
