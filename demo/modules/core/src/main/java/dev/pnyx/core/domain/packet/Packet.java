package dev.pnyx.core.domain.packet;

import dev.pnyx.core.domain.proposal.ProposalId;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Aggregate root for a Packet.
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public class Packet {
    private final UUID packetId;
    private final ProposalId proposalRef;
    private BriefingPacket briefingContent;
    private boolean publishedFlag;
    private int versionNumber;
    private final List<PacketEvent> pendingEvents;

    public Packet(UUID packetId, ProposalId proposalRef, BriefingPacket briefingContent) {
        this.packetId = packetId;
        this.proposalRef = proposalRef;
        this.briefingContent = briefingContent;
        this.publishedFlag = false;
        this.versionNumber = 1;
        this.pendingEvents = new ArrayList<>();
    }

    /**
     * Publishes the packet, making it visible.
     *
     * @throws IllegalStateException if already published
     */
    public void publish() {
        if (this.publishedFlag) {
            throw new IllegalStateException("Packet is already published");
        }
        this.publishedFlag = true;
        this.pendingEvents.add(new PacketPublished(this.packetId, this.proposalRef, "hash-placeholder", Instant.now()));
    }

    /**
     * Revises an already-published packet with new briefing content.
     *
     * @param reason      why the packet is being revised
     * @param newBriefing the updated briefing content
     * @throws IllegalStateException if the packet hasn't been published yet
     */
    public void revise(String reason, BriefingPacket newBriefing) {
        if (!this.publishedFlag) {
            throw new IllegalStateException("Cannot revise an unpublished packet");
        }
        this.versionNumber++;
        this.briefingContent = newBriefing;
        this.pendingEvents.add(new PacketRevised(this.packetId, this.proposalRef, "hash-placeholder", this.versionNumber, reason, Instant.now()));
    }

    public List<PacketEvent> uncommittedEvents() {
        return Collections.unmodifiableList(this.pendingEvents);
    }

    public UUID id() { return packetId; }
    public ProposalId proposalId() { return proposalRef; }
    public BriefingPacket briefingPacket() { return briefingContent; }
    public boolean published() { return publishedFlag; }
    public int version() { return versionNumber; }
}
