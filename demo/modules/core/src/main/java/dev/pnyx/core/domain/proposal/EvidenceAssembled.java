package dev.pnyx.core.domain.proposal;

/**
 * Event emitted when evidence assembly begins after the panel is locked.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public record EvidenceAssembled(
    ProposalId proposalId
) implements ProposalEvent {}
