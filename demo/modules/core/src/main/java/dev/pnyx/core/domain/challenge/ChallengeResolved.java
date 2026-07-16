package dev.pnyx.core.domain.challenge;

import dev.pnyx.core.domain.proposal.ProposalId;
import java.time.Instant;
import java.util.UUID;

/**
 * Event emitted when a challenge is resolved.
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public record ChallengeResolved(
    UUID challengeId,
    ProposalId proposalId,
    String resolution,
    String rationale,
    Instant occurredAt
) implements ChallengeEvent {}
