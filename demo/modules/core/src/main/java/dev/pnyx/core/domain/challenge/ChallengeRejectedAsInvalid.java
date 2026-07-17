package dev.pnyx.core.domain.challenge;

import dev.pnyx.core.domain.proposal.ProposalId;
import java.time.Instant;
import java.util.UUID;

/**
 * Event emitted when a challenge is rejected as invalid.
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public record ChallengeRejectedAsInvalid(
    UUID challengeId,
    ProposalId proposalId,
    String reason,
    Instant occurredAt
) implements ChallengeEvent {}
