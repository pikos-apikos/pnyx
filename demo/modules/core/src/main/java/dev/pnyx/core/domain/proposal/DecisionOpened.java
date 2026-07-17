package dev.pnyx.core.domain.proposal;

/**
 * Event emitted when the decision period is opened for a proposal.
 * <p>
 * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4}, this event transitions
 * the proposal to {@link ProposalState#DECISION_OPEN}, after which an
 * approve, reject, or defer decision may be recorded.
 *
 * @see ../docs/80_Runtime/STATE_MACHINE.md
 */
public record DecisionOpened(
    ProposalId proposalId
) implements ProposalEvent {}
