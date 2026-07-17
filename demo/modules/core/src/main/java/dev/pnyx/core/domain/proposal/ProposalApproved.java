package dev.pnyx.core.domain.proposal;

/**
 * Event emitted when a proposal is approved.
 *
 * {@code ../docs/20_Protocol_Core/PROTOCOL.md §8.11}
 */
public record ProposalApproved(
    ProposalId proposalId
) implements ProposalEvent {}
