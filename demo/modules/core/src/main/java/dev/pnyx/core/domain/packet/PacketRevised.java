package dev.pnyx.core.domain.packet;

import dev.pnyx.core.domain.proposal.ProposalId;
import java.time.Instant;
import java.util.UUID;

/**
 * Event emitted when a packet is revised.
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public record PacketRevised(
    UUID packetId,
    ProposalId proposalId,
    String packetHash,
    int version,
    String reason,
    Instant occurredAt
) implements PacketEvent {}
