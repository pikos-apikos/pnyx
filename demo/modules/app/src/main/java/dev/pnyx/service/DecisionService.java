package dev.pnyx.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.pnyx.core.api.DecisionApi;
import dev.pnyx.core.domain.proposal.DecisionMade;
import dev.pnyx.core.domain.proposal.Proposal;
import dev.pnyx.core.domain.proposal.ProposalEvent;
import dev.pnyx.core.domain.proposal.ProposalId;
import dev.pnyx.core.domain.proposal.ProposalState;
import dev.pnyx.core.domain.result.Result;
import dev.pnyx.core.spi.EventStoreSpi;
import dev.pnyx.core.common.PublicObjectType;
import dev.pnyx.core.common.PublicSchemaVersion;
import dev.pnyx.core.spi.PublicStoreSpi;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Implements {@link dev.pnyx.core.api.DecisionApi} — decision recording and public artifact creation.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/PROTOCOL.md §7}, a decision locks the execution path and
 * produces a public decision artifact in the content-addressed store.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 * @see ../docs/90_Information/PUBLIC_STORAGE_MODEL.md
 */
@Service
@RequiredArgsConstructor
public class DecisionService implements DecisionApi {

    private final EventStoreSpi eventStore;
    private final PublicStoreSpi publicStore;
    private final ObjectMapper objectMapper;

    private record PublicDecisionObject(String schema, UUID proposalId,
                                        String outcome, String actorId,
                                        String decidedAt) { }

    @Override
    public DecisionView recordJudgment(ProposalId proposalId, String outcome,
                                       String actorId) {
        if (!"approved".equals(outcome) && !"rejected".equals(outcome)
            && !"deferred".equals(outcome)) {
            throw new IllegalArgumentException("Invalid outcome: " + outcome);
        }
        
        // Replay the event stream to determine the proposal's real current state.
        // Rehydrating at the actual state lets the aggregate's own guards enforce
        // the lifecycle invariant: approve/reject/defer require DECISION_OPEN.
        // Per ../docs/80_Runtime/STATE_MACHINE.md §4, a decision may only be recorded
        // once the proposal has reached DECISION_OPEN via the full lifecycle.
        var currentState = readProposalState(proposalId);
        var proposal = Proposal.rehydrate(
            proposalId, currentState,
            "Proposal " + proposalId.value().toString().substring(0, 8),
            "Problem", "Action", null);
        
        List<ProposalEvent> newEvents;
        if ("approved".equals(outcome)) {
            var result = proposal.approve();
            if (result.isFailure()) {
                throw new IllegalStateException(
                    "Cannot approve proposal " + proposalId + " in state " + currentState + ": " + result);
            }
            newEvents = result.orElseThrow().uncommittedEvents();
        } else if ("rejected".equals(outcome)) {
            var result = proposal.reject("Rejected by " + actorId);
            if (result.isFailure()) {
                throw new IllegalStateException(
                    "Cannot reject proposal " + proposalId + " in state " + currentState + ": " + result);
            }
            newEvents = result.orElseThrow().uncommittedEvents();
        } else {
            var result = proposal.defer("Deferred by " + actorId);
            if (result.isFailure()) {
                throw new IllegalStateException(
                    "Cannot defer proposal " + proposalId + " in state " + currentState + ": " + result);
            }
            newEvents = result.orElseThrow().uncommittedEvents();
        }
        
        // Append events and write public artifact (keep existing behavior)
        eventStore.append(proposalId.value(), newEvents);
        
        var decidedAt = Instant.now().toString();
        String decisionJson;
        try {
            decisionJson = objectMapper.writeValueAsString(new PublicDecisionObject(
                PublicSchemaVersion.DECISION_V1.value(), proposalId.value(), outcome, actorId, decidedAt));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize decision", e);
        }
        var hash = publicStore.write(PublicObjectType.DECISION,
            PublicSchemaVersion.DECISION_V1, actorId, decisionJson);
        publicStore.updateManifest("test-network", hash, PublicObjectType.DECISION,
            "objects/decision/" + hash.hexDigest() + ".json");

        return new DecisionView(proposalId, outcome, decidedAt);
    }

    /**
     * Reads the event stream for a proposal and determines its current state.
     * <p>
     * Per {@code ../docs/80_Runtime/STATE_MACHINE.md}, the last event in the stream
     * determines the proposal's state. Returns {@link ProposalState#DRAFT} if the
     * stream is empty.
     */
    private ProposalState readProposalState(ProposalId proposalId) {
        var events = eventStore.readStream(proposalId.value());
        if (events.isEmpty()) {
            return ProposalState.DRAFT;
        }
        var lastEvent = events.get(events.size() - 1);
        return switch (lastEvent.eventType()) {
            case "ProposalSubmitted" -> ProposalState.SUBMITTED;
            case "IntakeValidated" -> ProposalState.CLASSIFICATION_PENDING;
            case "ProposalRequiresClarification" -> ProposalState.REQUIRES_CLARIFICATION;
            case "ClassificationRecorded" -> ProposalState.CLASSIFIED;
            case "PanelSelectionStarted" -> ProposalState.PANEL_SELECTION_PENDING;
            case "ProposalPanelLocked" -> ProposalState.PANEL_LOCKED;
            case "EvidenceAssembled" -> ProposalState.EVIDENCE_ASSEMBLY;
            case "DeliberationActivated" -> ProposalState.DELIBERATION_ACTIVE;
            case "PacketDrafted" -> ProposalState.PACKET_DRAFTING;
            case "PacketPublished" -> ProposalState.PACKET_PUBLISHED;
            case "PublicReviewOpened" -> ProposalState.PUBLIC_REVIEW_OPEN;
            case "ReviewClosed" -> ProposalState.READY_FOR_DECISION;
            case "DecisionOpened" -> ProposalState.DECISION_OPEN;
            case "ProposalApproved" -> ProposalState.APPROVED;
            case "ProposalRejected" -> ProposalState.REJECTED;
            case "ProposalDeferred" -> ProposalState.DEFERRED;
            case "ProposalClosed" -> ProposalState.CLOSED;
            case "ProposalInvalidated" -> ProposalState.INVALIDATED;
            default -> ProposalState.DRAFT;
        };
    }
}