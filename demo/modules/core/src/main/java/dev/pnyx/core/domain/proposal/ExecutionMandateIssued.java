package dev.pnyx.core.domain.proposal;

/**
 * Event emitted when an Execution Mandate is issued for an approved proposal.
 * <p>
 * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.6.2}, the mandate transitions
 * the proposal from {@code ROUTED} (path selected) to {@code EXECUTION_AUTHORIZED},
 * providing explicit authorization with defined constraints, resources, and
 * monitoring obligations before active execution begins.
 * <p>
 * The full mandate payload is stored as a public artifact; this event records
 * the transition in the event stream.
 *
 * @see ../docs/80_Runtime/STATE_MACHINE.md
 * @see ../docs/90_Information/DATA_MODEL.md
 */
public record ExecutionMandateIssued(
    ProposalId proposalId,
    String authorizedActor
) implements ProposalEvent {}
