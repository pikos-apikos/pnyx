package dev.pnyx.core.domain.proposal;

import java.util.UUID;

/**
 * Event recorded when a proposal is submitted for validation and skill-panel review.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/PROTOCOL.md §6}, submission is the transition from
 * DRAFT to SUBMITTED. The event carries the title, problem statement, and optional proposed
 * action that together form the proposal's initial public artifact.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public record ProposalSubmitted(
    ProposalId proposalId,
    String title,
    String problem,
    String proposedAction
) implements ProposalEvent { }
