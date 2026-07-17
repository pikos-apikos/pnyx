package dev.pnyx.core.domain.proposal;

/**
 * Event emitted when learning artifacts are published after monitoring.
 * <p>
 * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.6.3}, this transitions the
 * proposal from {@code MONITORING_ACTIVE} to {@code LEARNING_PUBLISHED},
 * marking the publication of lessons learned, success/failure factors, and
 * recommendations for systemic improvement.
 * <p>
 * The full learning artifact payload is stored as a public artifact; this event
 * records the transition in the event stream.
 *
 * @see ../docs/80_Runtime/STATE_MACHINE.md
 * @see ../docs/90_Information/DATA_MODEL.md
 */
public record LearningPublished(
    ProposalId proposalId,
    String publishedBy
) implements ProposalEvent {}
