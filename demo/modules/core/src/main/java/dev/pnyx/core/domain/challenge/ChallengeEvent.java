package dev.pnyx.core.domain.challenge;

import dev.pnyx.core.domain.proposal.ProposalId;
import java.time.Instant;
import java.util.UUID;

/**
 * Events related to the Challenge aggregate.
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public sealed interface ChallengeEvent permits ChallengeSubmitted, ChallengeResolved, ChallengeRejectedAsInvalid {
    UUID challengeId();
    ProposalId proposalId();
    Instant occurredAt();
}
