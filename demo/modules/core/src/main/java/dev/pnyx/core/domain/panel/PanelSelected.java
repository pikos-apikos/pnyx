package dev.pnyx.core.domain.panel;

import dev.pnyx.core.domain.proposal.ProposalId;
import java.time.Instant;
import java.util.UUID;

/**
 * Event emitted when a panel is tentatively selected before lock.
 *
 * @see ../docs/20_Protocol_Core/PANEL_SELECTION.md
 */
public record PanelSelected(
    UUID panelId,
    ProposalId proposalId,
    Instant occurredAt
) implements PanelEvent {}
