package dev.pnyx.infrastructure.projection;

import dev.pnyx.core.domain.proposal.ProposalId;
import dev.pnyx.core.spi.EventStoreSpi;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Projection that summarizes published packets for a proposal.
 * <p>
 * Reads packet-related events from the event store to build a
 * summary of published evidence packets.
 *
 * @see ../docs/90_Information/PACKET_FORMAT.md
 */
@Component
public class PacketSummaryProjection {

    private final EventStoreSpi eventStore;

    public PacketSummaryProjection(EventStoreSpi eventStore) {
        this.eventStore = eventStore;
    }

    /**
     * Returns true if the proposal has at least one published packet.
     *
     * @param proposalId the proposal to inspect
     * @return true if a published packet exists
     */
    public boolean hasPublishedPacket(ProposalId proposalId) {
        var stored = eventStore.readStream(proposalId.value());
        return stored.stream().anyMatch(e -> "PacketPublished".equals(e.eventType()));
    }

    /**
     * Returns the number of published packets for the proposal.
     *
     * @param proposalId the proposal to inspect
     * @return the published packet count
     */
    public long publishedPacketCount(ProposalId proposalId) {
        var stored = eventStore.readStream(proposalId.value());
        return stored.stream().filter(e -> "PacketPublished".equals(e.eventType())).count();
    }
}