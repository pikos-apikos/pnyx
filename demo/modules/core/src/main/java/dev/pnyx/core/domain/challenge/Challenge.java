package dev.pnyx.core.domain.challenge;

import dev.pnyx.core.domain.proposal.ProposalId;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Challenge aggregate.
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public class Challenge {
    private final UUID id;
    private final ProposalId proposalId;
    private final String reason;
    private final String challengerId;
    private boolean resolved;
    private boolean rejected;
    private final List<ChallengeEvent> pendingEvents = new ArrayList<>();

    public Challenge(UUID id, ProposalId proposalId, String reason, String challengerId) {
        this.id = id;
        this.proposalId = proposalId;
        this.reason = reason;
        this.challengerId = challengerId;
        this.resolved = false;
        this.rejected = false;
        
        this.pendingEvents.add(new ChallengeSubmitted(id, proposalId, reason, challengerId, Instant.now()));
    }

    /**
     * Resolves the challenge with a resolution and rationale.
     *
     * @param resolution the resolution outcome
     * @param rationale  justification for the resolution
     * @throws IllegalStateException if already resolved or rejected
     */
    public void resolve(String resolution, String rationale) {
        if (resolved || rejected) {
            throw new IllegalStateException("Challenge is already resolved or rejected");
        }
        this.resolved = true;
        this.pendingEvents.add(new ChallengeResolved(id, proposalId, resolution, rationale, Instant.now()));
    }

    /**
     * Rejects the challenge as invalid.
     *
     * @param rejectionReason why the challenge was rejected
     * @throws IllegalStateException if already resolved or rejected
     */
    public void rejectAsInvalid(String rejectionReason) {
        if (resolved || rejected) {
            throw new IllegalStateException("Challenge is already resolved or rejected");
        }
        this.rejected = true;
        this.pendingEvents.add(new ChallengeRejectedAsInvalid(id, proposalId, rejectionReason, Instant.now()));
    }

    public List<ChallengeEvent> uncommittedEvents() {
        return Collections.unmodifiableList(pendingEvents);
    }
    
    public UUID getId() { return id; }
    public ProposalId getProposalId() { return proposalId; }
    public String getReason() { return reason; }
    public String getChallengerId() { return challengerId; }
    public boolean isResolved() { return resolved; }
    public boolean isRejected() { return rejected; }
}
