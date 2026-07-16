package dev.pnyx.core.domain.proposal;

/**
 * Event emitted when a proposal is invalidated.
 *
 * {@code ../docs/80_Runtime/STATE_MACHINE.md §4.1}
 */
public record ProposalInvalidated(
    ProposalId proposalId,
    String reason
) implements ProposalEvent {}
