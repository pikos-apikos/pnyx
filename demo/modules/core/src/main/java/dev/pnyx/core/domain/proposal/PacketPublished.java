package dev.pnyx.core.domain.proposal;

import java.util.UUID;

import dev.pnyx.core.common.ContentHash;

/**
 * Event recorded when a deliberation evidence packet is published for public review.
 * <p>
 * Per {@code ../docs/90_Information/EVIDENCE_PACKET.md}, the packet bundles all reviews, evidence,
 * and findings into a content-addressed, versioned public artifact.
 *
 * @see ../docs/90_Information/EVIDENCE_PACKET.md
 * @see ../docs/90_Information/PUBLIC_STORAGE_MODEL.md
 */
public record PacketPublished(
    ProposalId proposalId,
    ContentHash packetHash
) implements ProposalEvent { }
