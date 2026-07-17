package dev.pnyx.core.domain.proposal;

/**
 * Event emitted when intake validation passes.
 *
 * {@code ../docs/20_Protocol_Core/PROTOCOL.md §8.3}
 */
public record IntakeValidated(
    ProposalId proposalId
) implements ProposalEvent {}
