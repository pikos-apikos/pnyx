package dev.pnyx.core.domain.execution;

import dev.pnyx.core.domain.proposal.ProposalId;

import java.time.Instant;
import java.util.List;

/**
 * Monitoring Record defining what to monitor during the execution outcome phase.
 * <p>
 * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.6.3}, after execution completes,
 * the system enters a monitoring phase with defined milestones, metrics, and
 * reporting obligations. This record captures the monitoring plan.
 * <p>
 * Per {@code ../docs/90_Information/DATA_MODEL.md}, the monitoring record is a public
 * artifact that is content-addressed and stored alongside the proposal.
 *
 * @see ../docs/80_Runtime/STATE_MACHINE.md
 * @see ../docs/90_Information/DATA_MODEL.md
 */
public record MonitoringRecord(
    ProposalId proposalId,
    Instant monitoringStartDate,
    Instant monitoringEndDate,
    List<String> milestones,
    List<String> metrics,
    String reportingFrequency,
    String responsibleParty,
    String status
) {

    /**
     * Compact constructor validating required fields.
     *
     * @throws IllegalArgumentException if proposalId is null, responsibleParty is blank,
     *         or monitoringStartDate is null
     */
    public MonitoringRecord {
        if (proposalId == null) {
            throw new IllegalArgumentException("proposalId must not be null");
        }
        if (responsibleParty == null || responsibleParty.isBlank()) {
            throw new IllegalArgumentException("responsibleParty must not be blank");
        }
        if (monitoringStartDate == null) {
            throw new IllegalArgumentException("monitoringStartDate must not be null");
        }
        milestones = milestones != null ? List.copyOf(milestones) : List.of();
        metrics = metrics != null ? List.copyOf(metrics) : List.of();
        status = status != null ? status : "active";
    }
}
