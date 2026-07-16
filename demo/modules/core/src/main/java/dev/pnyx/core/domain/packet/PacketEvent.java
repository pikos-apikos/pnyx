package dev.pnyx.core.domain.packet;

import dev.pnyx.core.domain.proposal.ProposalId;
import java.time.Instant;
import java.util.UUID;

/**
 * Events related to the Packet aggregate.
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public sealed interface PacketEvent permits PacketPublished, PacketRevised {
    UUID packetId();
    ProposalId proposalId();
    Instant occurredAt();
}
