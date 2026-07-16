package dev.pnyx.infrastructure.projection;

import dev.pnyx.core.domain.proposal.ProposalId;
import dev.pnyx.core.domain.proposal.ProposalState;
import dev.pnyx.core.domain.proposal.ProposalEvent;
import dev.pnyx.core.spi.EventStoreSpi;
import org.springframework.stereotype.Component;
import java.util.Optional;

/**
 * Event-sourced projection that determines the current state of a proposal
 * by replaying its event stream.
 *
 * @see ../docs/80_Runtime/READ_MODELS.md
 */
@Component
public class CurrentProposalProjection {

    private final EventStoreSpi eventStore;

    public CurrentProposalProjection(EventStoreSpi eventStore) {
        this.eventStore = eventStore;
    }

    /**
     * Returns the current state of the proposal by replaying its event stream.
     *
     * @param proposalId the proposal to inspect
     * @return the current proposal state
     */
    public ProposalState getCurrentState(ProposalId proposalId) {
        var stored = eventStore.readStream(proposalId.value());
        if (stored.isEmpty()) { return ProposalState.DRAFT; }

        var state = ProposalState.DRAFT;
        for (var event : stored) {
            state = deriveState(event.eventType(), state);
        }
        return state;
    }

    /**
     * Returns the canonical public state for export.
     *
     * @param proposalId the proposal to inspect
     * @return the canonical public state, or empty if DRAFT
     */
    public Optional<dev.pnyx.core.domain.proposal.CanonicalPublicState> getCanonicalPublicState(ProposalId proposalId) {
        var state = getCurrentState(proposalId);
        if (state == ProposalState.DRAFT) { return Optional.empty(); }
        return Optional.ofNullable(state.canonicalPublicState());
    }

    private ProposalState deriveState(String eventType, ProposalState previous) {
        return switch (eventType) {
            case "ProposalSubmitted" -> ProposalState.SUBMITTED;
            case "IntakeValidated" -> ProposalState.CLASSIFICATION_PENDING;
            case "ClassificationRecorded" -> ProposalState.CLASSIFIED;
            case "PanelSelectionStarted" -> ProposalState.PANEL_SELECTION_PENDING;
            case "ProposalPanelLocked" -> ProposalState.PANEL_LOCKED;
            case "PacketPublished" -> ProposalState.PACKET_PUBLISHED;
            case "ProposalApproved" -> ProposalState.APPROVED;
            case "ProposalRejected" -> ProposalState.REJECTED;
            case "ProposalDeferred" -> ProposalState.DEFERRED;
            case "ProposalClosed" -> ProposalState.CLOSED;
            case "ProposalInvalidated" -> ProposalState.INVALIDATED;
            default -> previous;
        };
    }
}