package dev.pnyx.core.domain.proposal;

/**
 * Event emitted when a participant body formation is completed.
 * <p>
 * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.4}, the exit guard from
 * {@code PARTICIPANT_BODY_FORMATION} requires all invitations declared in the
 * Participation Plan to be issued, sortition to be completed (where used), and
 * accessibility measures to be available. This event records that transition.
 * <p>
 * In the MVP ({@code ../docs/70_Bootstrap/MINIMUM_VIABLE_PNYX.md §5.5}), formed
 * participant bodies beyond open participation are deferred, so this event
 * is not emitted in the bootstrap runtime. The state and event exist for
 * specification completeness.
 *
 * @see ../docs/45_Participation/PARTICIPATION_MODEL.md
 * @see ../docs/80_Runtime/STATE_MACHINE.md
 */
public record ParticipantBodyFormed(
    ProposalId proposalId
) implements ProposalEvent {}
