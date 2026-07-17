package dev.pnyx.core.domain.proposal;

/**
 * Event emitted when intake flags a proposal as needing clarification.
 *
 * {@code ../docs/20_Protocol_Core/PROTOCOL.md §8.3}
 */
public record ProposalRequiresClarification(
    ProposalId proposalId,
    String reason
) implements ProposalEvent {}
