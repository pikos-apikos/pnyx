package dev.pnyx.core.domain.proposal;

/**
 * Event emitted when the public review period is opened.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public record PublicReviewOpened(
    ProposalId proposalId
) implements ProposalEvent {}
