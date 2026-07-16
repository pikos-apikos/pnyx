package dev.pnyx.infrastructure.projection;

import dev.pnyx.core.domain.proposal.ProposalId;
import dev.pnyx.core.spi.EventStoreSpi;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Event-sourced projection that builds a chronological timeline of a proposal's lifecycle.
 * <p>
 * Reads events from the canonical event store and maps them to human-readable timeline entries.
 *
 * @see ../docs/80_Runtime/READ_MODELS.md
 * @see ../docs/80_Runtime/EVENT_MODEL.md
 */
@Component
public class ProposalTimelineProjection {

    private final EventStoreSpi eventStore;

    public ProposalTimelineProjection(EventStoreSpi eventStore) {
        this.eventStore = eventStore;
    }

    /**
     * Builds a chronological timeline of events for the given proposal.
     *
     * @param proposalId the proposal to build a timeline for
     * @return ordered list of timeline entries
     */
    public List<ProposalTimelineEntry> buildTimeline(ProposalId proposalId) {
        var stored = eventStore.readStream(proposalId.value());
        return stored.stream()
            .map(e -> new ProposalTimelineEntry(
                proposalId,
                e.eventType(),
                summarizeEvent(e.eventType()),
                e.occurredAt() != null ? e.occurredAt() : ""))
            .toList();
    }

    private String summarizeEvent(String eventType) {
        return switch (eventType) {
            case "ProposalSubmitted" -> "Proposal submitted for review";
            case "IntakeValidated" -> "Intake validation passed";
            case "ClassificationRecorded" -> "Proposal classified";
            case "PanelSelectionStarted" -> "Panel selection begun";
            case "ProposalPanelLocked" -> "Review panel locked";
            case "PacketPublished" -> "Evidence packet published";
            case "DecisionMade" -> "Decision recorded";
            case "ProposalApproved" -> "Proposal approved";
            case "ProposalRejected" -> "Proposal rejected";
            case "ProposalDeferred" -> "Proposal deferred";
            case "ProposalClosed" -> "Proposal closed";
            case "ProposalInvalidated" -> "Proposal invalidated";
            default -> "Event: " + eventType;
        };
    }
}