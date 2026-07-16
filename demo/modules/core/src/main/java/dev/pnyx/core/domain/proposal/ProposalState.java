package dev.pnyx.core.domain.proposal;

/**
 * Lifecycle states used by the proposal aggregate state machine.
 * <p>
 * The canonical list of internal states is defined here and referenced by
 * {@code STATE_MACHINE.md §4.1}. Other documents ({@code PROTOCOL.md §6},
 * {@code DATA_MODEL.md §5.4/§7}) reference this enum rather than maintaining divergent copies.
 * <p>
 * For public exports, use {@link #canonicalPublicState()} which maps to the stable
 * {@link CanonicalPublicState} vocabulary defined in {@code STATE_MACHINE.md §4.5}.
 */
public enum ProposalState {
    DRAFT,
    SUBMITTED,
    CLASSIFICATION_PENDING,
    REQUIRES_CLARIFICATION,
    CLASSIFIED,
    PARTICIPATION_DESIGN_PENDING,
    PANEL_SELECTION_PENDING,
    PANEL_LOCKED,
    EVIDENCE_ASSEMBLY,
    DELIBERATION_ACTIVE,
    PACKET_DRAFTING,
    PACKET_PUBLISHED,
    PARTICIPANT_BODY_FORMATION,
    PUBLIC_REVIEW_OPEN,
    CHALLENGED,
    REVIEW_REPAIR,
    READY_FOR_DECISION,
    DECISION_OPEN,
    APPROVED,
    REJECTED,
    DEFERRED,
    ROUTING_PENDING,
    ROUTED,
    EXECUTION_AUTHORIZED,
    EXECUTION_ACTIVE,
    EXECUTION_PAUSED,
    EXECUTION_COMPLETED,
    MONITORING_ACTIVE,
    LEARNING_PUBLISHED,
    POST_REVIEW_OPEN,
    CLOSED,
    INVALIDATED;

    /**
     * Maps this internal state to the canonical public state vocabulary
     * (STATE_MACHINE.md §4.5.3).
     * <p>
     * {@code DRAFT} is not exported (not yet in the public civic loop).
     *
     * @return the canonical public state, or {@code null} for {@code DRAFT}
     */
    public CanonicalPublicState canonicalPublicState() {
        return switch (this) {
            case DRAFT -> null;
            case SUBMITTED, CLASSIFICATION_PENDING -> CanonicalPublicState.INTAKE;
            case REQUIRES_CLARIFICATION -> CanonicalPublicState.CLARIFICATION;
            case CLASSIFIED -> CanonicalPublicState.PROBLEM_DEFINED;
            case PARTICIPATION_DESIGN_PENDING -> CanonicalPublicState.PARTICIPATION_DESIGNED;
            case PANEL_SELECTION_PENDING, PANEL_LOCKED, EVIDENCE_ASSEMBLY -> CanonicalPublicState.EVIDENCE_OPEN;
            case DELIBERATION_ACTIVE -> CanonicalPublicState.SPECIALIST_ANALYSIS;
            case PACKET_DRAFTING -> CanonicalPublicState.ADVERSARIAL_REVIEW;
            case PACKET_PUBLISHED -> CanonicalPublicState.DELIBERATION;
            case PARTICIPANT_BODY_FORMATION -> CanonicalPublicState.PARTICIPANT_BODY_READY;
            case PUBLIC_REVIEW_OPEN, CHALLENGED, REVIEW_REPAIR -> CanonicalPublicState.DELIBERATION;
            case READY_FOR_DECISION, DECISION_OPEN -> CanonicalPublicState.JUDGMENT_READY;
            case APPROVED -> CanonicalPublicState.JUDGMENT_COMPLETE;
            case REJECTED -> CanonicalPublicState.REJECTED;
            case DEFERRED -> CanonicalPublicState.SUSPENDED;
            case ROUTING_PENDING -> CanonicalPublicState.JUDGMENT_COMPLETE;
            case ROUTED -> CanonicalPublicState.EXECUTION_PATH_SELECTED;
            case EXECUTION_AUTHORIZED -> CanonicalPublicState.EXECUTION_AUTHORIZED;
            case EXECUTION_ACTIVE -> CanonicalPublicState.EXECUTING;
            case EXECUTION_PAUSED -> CanonicalPublicState.SUSPENDED;
            case EXECUTION_COMPLETED, MONITORING_ACTIVE -> CanonicalPublicState.MONITORING;
            case LEARNING_PUBLISHED -> CanonicalPublicState.LEARNING_PUBLISHED;
            case POST_REVIEW_OPEN -> CanonicalPublicState.OUTCOME_RECORDED;
            case CLOSED -> CanonicalPublicState.CLOSED;
            case INVALIDATED -> CanonicalPublicState.INVALIDATED;
        };
    }
}
