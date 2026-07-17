package dev.pnyx.core.domain.proposal;

import java.util.List;

/**
 * Sealed error types for the proposal aggregate.
 * Each record represents a distinct business rule violation.
 * <p>
 * These error types correspond to business rule violations defined in
 * {@code ../docs/20_Protocol_Core/PROTOCOL.md} and {@code ../docs/80_Runtime/INVARIANTS.md}.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 * @see ../docs/80_Runtime/INVARIANTS.md
 */
public sealed interface ProposalError {
    /** Proposal problem statement is blank. */
    record BlankProblem() implements ProposalError {}
    /** Problem and proposed action are identical. */
    record ProblemEqualsSolution() implements ProposalError {}
    /** Operation not allowed in the current state. */
    record WrongState(ProposalState expected, ProposalState actual) implements ProposalError {}
    /** Proposal lacks a problem definition. */
    record MissingProblemDefinition() implements ProposalError {}
    /** Proposal title is blank. */
    record BlankTitle() implements ProposalError {}
    /** Operation not allowed — state must be one of several expected states. */
    record WrongStates(List<ProposalState> expected, ProposalState actual) implements ProposalError {}
    /** Participation Plan does not exist for a non-trivial proposal. */
    record MissingParticipationPlan() implements ProposalError {}
    /** Participation Audit has not been published before decision-readiness. */
    record MissingParticipationAudit() implements ProposalError {}
    /** Problem statement is too short to be substantive (minimum 20 characters). */
    record ProblemTooShort() implements ProposalError {}
    /** Problem statement is identical to the title. */
    record ProblemEqualsTitle() implements ProposalError {}
    /** Problem statement is phrased as a question (ends with '?'). */
    record ProblemIsQuestion() implements ProposalError {}
    /** Execution Mandate is invalid (missing required fields). */
    record InvalidExecutionMandate() implements ProposalError {}
    /** Monitoring Record is invalid (missing required fields). */
    record InvalidMonitoringRecord() implements ProposalError {}
    /** Learning Artifact is invalid (missing required fields). */
    record InvalidLearningArtifact() implements ProposalError {}
}
