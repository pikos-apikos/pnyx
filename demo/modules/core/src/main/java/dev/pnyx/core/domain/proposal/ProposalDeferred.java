package dev.pnyx.core.domain.proposal;

/**
 * Event emitted when a proposal is deferred.
 *
 * {@code ../docs/20_Protocol_Core/PROTOCOL.md §8.11}
 */
public record ProposalDeferred(
    ProposalId proposalId,
    String reason
) implements ProposalEvent {}
