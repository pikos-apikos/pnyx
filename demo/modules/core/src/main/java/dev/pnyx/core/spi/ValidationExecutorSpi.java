package dev.pnyx.core.spi;

import java.util.List;
import java.util.UUID;
import dev.pnyx.core.domain.proposal.ProposalId;

/**
 * Driven port for proposal validation execution.
 * <p>
 * Per {@code ../docs/60_Skills/SKILLS.md §3}, validation is a gate that assesses completeness,
 * discussability, and problem-solution independence. The executor may be an AI model
 * ({@code AiValidationExecutorAdapter}) or a rule-based fallback ({@code StaticValidationExecutor}).
 *
 * @see ../docs/60_Skills/SKILLS.md
 * @see ../docs/60_Skills/EXECUTOR_MODEL.md
 */
public interface ValidationExecutorSpi {
    ValidationResult validate(ProposalId proposalId, String title, String problem, String action);

    /**
     * Result of a validation gate check on a proposal.
     *
     * @param proposalId          the validated proposal
     * @param isDiscussable       whether the proposal passes the gate
     * @param missingFields       fields that must be filled before deliberation
     * @param clarifyingQuestions questions for the submitter
     * @param flags               semantic or spam flags detected
     */
    record ValidationResult(
        ProposalId proposalId,
        boolean isDiscussable,
        List<String> missingFields,
        List<String> clarifyingQuestions,
        List<String> flags
    ) { }
}
