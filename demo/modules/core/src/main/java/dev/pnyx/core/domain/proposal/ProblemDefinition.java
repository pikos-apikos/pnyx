package dev.pnyx.core.domain.proposal;

import java.util.List;
import dev.pnyx.core.domain.result.Result;

/**
 * Validated definition of the problem a proposal addresses, produced before any solution
 * is promoted.
 * <p>
 * Per {@code PROTOCOL.md} invariant 7.9: "No proposal may complete classification without a
 * validated {@code ProblemDefinition} artifact. The problem is defined before a solution,
 * funding request, or execution route is promoted. A proposed solution is a distinct field;
 * it may be absent at submission and must never substitute for the problem statement."
 * <p>
 * See {@code DATA_MODEL.md §5.25} for the full field specification.
 */
public record ProblemDefinition(
    String problemStatement,
    String affectedPublicInterest,
    List<String> observableConditions,
    String timeHorizon,
    List<String> knownConstraints,
    List<String> unresolvedQuestions,
    List<String> evaluationCriteria,
    List<String> exclusionBoundaries
) {

    /**
     * Validates that the problem statement is non-blank and independent of any proposed solution.
     *
     * @param problemStatement the raw problem text
     * @param proposedAction    the proposed solution (may be null)
     * @return a Result containing the validated {@code ProblemDefinition} or a {@code ProposalError}
     */
    public static Result<ProblemDefinition, ProposalError> validate(String problemStatement, String proposedAction) {
        if (problemStatement == null || problemStatement.isBlank()) {
            return Result.failure(new ProposalError.BlankProblem());
        }
        if (proposedAction != null && !proposedAction.isBlank()
            && problemStatement.trim().equalsIgnoreCase(proposedAction.trim())) {
            return Result.failure(new ProposalError.ProblemEqualsSolution());
        }
        return Result.success(new ProblemDefinition(
            problemStatement,
            null,
            List.of(),
            null,
            List.of(),
            List.of(),
            List.of(),
            List.of()));
    }
}
