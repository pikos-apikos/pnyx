package dev.pnyx.core.domain.proposal;

/**
 * Event emitted when deliberation is activated after evidence assembly.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public record DeliberationActivated(
    ProposalId proposalId
) implements ProposalEvent {}
