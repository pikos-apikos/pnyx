package dev.pnyx.core.domain.proposal;

/**
 * Marker interface for all proposal domain events that can be persisted in the event stream.
 * <p>
 * Per {@code ../docs/80_Runtime/EVENT_MODEL.md §2}: Commands request change. Events record change.
 * Read models expose current understanding. This interface and its permitted subtypes are the
 * event records for the proposal aggregate.
 * <p>
 * Each event carries a {@link ProposalId} and a timestamp, enabling full event-sourced
 * reconstruction of the proposal's lifecycle.
 *
 * @see ../docs/80_Runtime/EVENT_MODEL.md
 * @see ../docs/80_Runtime/STATE_MACHINE.md
 */
public sealed interface ProposalEvent permits
    ProposalSubmitted,
    ProposalClassified,
    ProposalPanelLocked,
    PacketPublished,
    DecisionMade,
    ProposalClosed,
    ClassificationRecorded,
    IntakeValidated,
    ProposalRequiresClarification,
    PanelSelectionStarted,
    EvidenceAssembled,
    DeliberationActivated,
    PacketDrafted,
    PublicReviewOpened,
    ReviewClosed,
    DecisionOpened,
    ProposalApproved,
    ProposalRejected,
    ProposalDeferred,
    ProposalInvalidated,
    ParticipationPlanCreated,
    ParticipantBodyFormed,
    ParticipationAuditPublished,
    ExecutionMandateIssued,
    MonitoringStarted,
    LearningPublished {

    ProposalId proposalId();
}
