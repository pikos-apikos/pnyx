package dev.pnyx.core.domain.proposal;

/**
 * Canonical public state vocabulary for proposal lifecycle exports.
 * <p>
 * Defined in {@code STATE_MACHINE.md §4.5}. Internal {@link ProposalState} values are an
 * implementation vocabulary that may evolve; this enum is the stable public contract used in
 * exported public objects, read models, and mirrors. A change to this vocabulary is a
 * meta-governance change, not a runtime change.
 * <p>
 * Every public export, read model, or artifact bundle that exposes proposal lifecycle position
 * must include the canonical public state derived via {@link ProposalState#canonicalPublicState()}.
 */
public enum CanonicalPublicState {
    // Primary states
    INTAKE,
    CLARIFICATION,
    PROBLEM_DEFINED,
    PARTICIPATION_DESIGNED,
    EVIDENCE_OPEN,
    SPECIALIST_ANALYSIS,
    ADVERSARIAL_REVIEW,
    PARTICIPANT_BODY_READY,
    DELIBERATION,
    JUDGMENT_READY,
    JUDGMENT_COMPLETE,
    EXECUTION_PATH_SELECTED,
    EXECUTION_AUTHORIZED,
    EXECUTING,
    MONITORING,
    OUTCOME_RECORDED,
    LEARNING_PUBLISHED,
    CLOSED,

    // Supporting states
    SUSPENDED,
    REOPENED,
    REJECTED,
    MERGED,
    DUPLICATE,
    INVALIDATED,
    SUPERSEDED
}