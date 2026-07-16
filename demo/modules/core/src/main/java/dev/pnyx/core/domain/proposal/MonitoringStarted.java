package dev.pnyx.core.domain.proposal;

/**
 * Event emitted when monitoring begins after execution completion.
 * <p>
 * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.6.3}, this transitions the
 * proposal from {@code EXECUTION_COMPLETED} to {@code MONITORING_ACTIVE},
 * establishing a formal monitoring phase for execution outcomes.
 * <p>
 * The full monitoring record payload is stored as a public artifact; this event
 * records the transition in the event stream.
 *
 * @see ../docs/80_Runtime/STATE_MACHINE.md
 * @see ../docs/90_Information/DATA_MODEL.md
 */
public record MonitoringStarted(
    ProposalId proposalId,
    String responsibleParty
) implements ProposalEvent {}
