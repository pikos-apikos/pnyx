package dev.pnyx.core.domain.proposal;

/**
 * Event emitted when panel selection begins.
 *
 * @see ../docs/20_Protocol_Core/PANEL_SELECTION.md
 */
public record PanelSelectionStarted(
    ProposalId proposalId
) implements ProposalEvent {}
