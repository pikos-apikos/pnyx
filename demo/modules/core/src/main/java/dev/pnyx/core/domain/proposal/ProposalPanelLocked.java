package dev.pnyx.core.domain.proposal;

import java.util.List;
import java.util.UUID;

/**
 * Event recorded when a skill panel is locked for a proposal, preventing further role changes.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/PANEL_SELECTION.md §5}, panel lock freezes the set of reviewers
 * and triggers evidence assembly and deliberation phases.
 *
 * @see ../docs/20_Protocol_Core/PANEL_SELECTION.md
 */
public record ProposalPanelLocked(
    ProposalId proposalId,
    List<String> skillRoles
) implements ProposalEvent { }
