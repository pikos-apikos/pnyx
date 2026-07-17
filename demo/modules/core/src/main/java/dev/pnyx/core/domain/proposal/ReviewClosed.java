package dev.pnyx.core.domain.proposal;

/**
 * Event emitted when the public review period is closed and the proposal
 * is ready for a decision.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public record ReviewClosed(
    ProposalId proposalId
) implements ProposalEvent {}
