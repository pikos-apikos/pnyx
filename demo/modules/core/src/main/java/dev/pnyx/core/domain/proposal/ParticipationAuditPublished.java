package dev.pnyx.core.domain.proposal;

/**
 * Event emitted when a Participation Audit is published for a proposal.
 * <p>
 * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.4}, the guard on
 * {@code PUBLIC_REVIEW_OPEN → READY_FOR_DECISION} requires a Participation
 * Audit to be available with classification-appropriate depth. This event
 * records the audit publication.
 * <p>
 * Depth is proportional: full for high-impact proposals, lightweight for other
 * non-trivial proposals. Trivial proposals are exempt via the trivial shortcut.
 *
 * @see ../docs/45_Participation/PARTICIPATION_MODEL.md
 * @see ../docs/80_Runtime/STATE_MACHINE.md
 */
public record ParticipationAuditPublished(
    ProposalId proposalId
) implements ProposalEvent {}
