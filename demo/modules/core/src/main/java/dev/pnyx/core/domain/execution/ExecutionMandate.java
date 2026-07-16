package dev.pnyx.core.domain.execution;

import dev.pnyx.core.domain.proposal.ProposalId;

import java.time.Instant;
import java.util.List;

/**
 * Execution Mandate issued when an approved proposal transitions from
 * {@code ROUTED} (path selected) to {@code EXECUTION_AUTHORIZED}.
 * <p>
 * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.6.2}, the mandate defines
 * the constraints, resources, and monitoring obligations under which the
 * approved execution may proceed. Route assignment ({@code ROUTED}) alone is
 * implicit authorization; an explicit mandate provides auditable governance.
 * <p>
 * Per {@code ../docs/90_Information/DATA_MODEL.md}, the mandate is a public
 * artifact that is content-addressed and stored alongside the proposal.
 *
 * @see ../docs/80_Runtime/STATE_MACHINE.md
 * @see ../docs/90_Information/DATA_MODEL.md
 */
public record ExecutionMandate(
    ProposalId proposalId,
    String authorizedActor,
    List<String> permittedActions,
    List<String> prohibitedActions,
    String resourceAllocation,
    List<String> successCriteria,
    List<String> failureCriteria,
    List<String> monitoringObligations,
    List<String> rollbackConditions,
    Instant issuedAt
) {

    /**
     * Compact constructor validating required fields.
     *
     * @throws IllegalArgumentException if proposalId is null, authorizedActor is blank,
     *         or issuedAt is null
     */
    public ExecutionMandate {
        if (proposalId == null) {
            throw new IllegalArgumentException("proposalId must not be null");
        }
        if (authorizedActor == null || authorizedActor.isBlank()) {
            throw new IllegalArgumentException("authorizedActor must not be blank");
        }
        if (issuedAt == null) {
            throw new IllegalArgumentException("issuedAt must not be null");
        }
        permittedActions = permittedActions != null ? List.copyOf(permittedActions) : List.of();
        prohibitedActions = prohibitedActions != null ? List.copyOf(prohibitedActions) : List.of();
        successCriteria = successCriteria != null ? List.copyOf(successCriteria) : List.of();
        failureCriteria = failureCriteria != null ? List.copyOf(failureCriteria) : List.of();
        monitoringObligations = monitoringObligations != null ? List.copyOf(monitoringObligations) : List.of();
        rollbackConditions = rollbackConditions != null ? List.copyOf(rollbackConditions) : List.of();
    }
}
