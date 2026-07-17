package dev.pnyx.core.domain.panel;

import dev.pnyx.core.domain.proposal.ProposalId;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Event emitted when a panel is locked, freezing seat assignments.
 *
 * @see ../docs/20_Protocol_Core/PANEL_SELECTION.md §5
 */
public record PanelLocked(
    UUID panelId,
    ProposalId proposalId,
    List<String> skillRoles,
    Instant occurredAt
) implements PanelEvent {}
