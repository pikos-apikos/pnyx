package dev.pnyx.core.domain.packet;

import dev.pnyx.core.domain.proposal.ProposalId;
import java.time.Instant;
import java.util.UUID;

/**
 * Event emitted when a packet is published.
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public record PacketPublished(
    UUID packetId,
    ProposalId proposalId,
    String packetHash,
    Instant occurredAt
) implements PacketEvent {}
