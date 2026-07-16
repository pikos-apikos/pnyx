package dev.pnyx.core.domain.challenge;

import dev.pnyx.core.domain.proposal.ProposalId;
import java.time.Instant;
import java.util.UUID;

/**
 * Event emitted when a challenge is submitted.
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public record ChallengeSubmitted(
    UUID challengeId,
    ProposalId proposalId,
    String reason,
    String challengerId,
    Instant occurredAt
) implements ChallengeEvent {}
