package dev.pnyx.core.domain.proposal;

/**
 * Event emitted when a deliberation packet is drafted.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public record PacketDrafted(
    ProposalId proposalId
) implements ProposalEvent {}
