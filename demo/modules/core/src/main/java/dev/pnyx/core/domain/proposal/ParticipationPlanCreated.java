package dev.pnyx.core.domain.proposal;

/**
 * Event emitted when a Participation Plan is created for a proposal.
 * <p>
 * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.4}, the exit guard from
 * {@code PARTICIPATION_DESIGN_PENDING} requires a first complete versioned
 * Participation Plan to exist. This event records that transition.
 *
 * @see ../docs/45_Participation/PARTICIPATION_MODEL.md
 * @see ../docs/80_Runtime/STATE_MACHINE.md
 */
public record ParticipationPlanCreated(
    ProposalId proposalId
) implements ProposalEvent {}
