package dev.pnyx.infrastructure.projection;

import dev.pnyx.core.domain.proposal.ProposalId;
import java.time.Instant;

/**
 * A single entry in a proposal's event-sourced timeline.
 *
 * @see ../docs/80_Runtime/EVENT_MODEL.md
 */
public record ProposalTimelineEntry(
    ProposalId proposalId,
    String eventType,
    String summary,
    String occurredAt
) {}