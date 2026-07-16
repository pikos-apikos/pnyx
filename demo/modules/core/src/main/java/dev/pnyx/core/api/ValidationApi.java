package dev.pnyx.core.api;

import java.util.UUID;
import dev.pnyx.core.domain.proposal.ProposalId;

/**
 * Driving port for AI-assisted proposal discussability validation.
 * <p>
 * Validation screens proposals for completeness and discussability before deliberation.
 * Per {@code ../docs/20_Protocol_Core/PROTOCOL.md §4}, validation is the first gate a proposal
 * must pass to proceed from submission toward classification.
 *
 * @see ../docs/80_Runtime/API_SPEC.md
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public interface ValidationApi {

    /**
     * Validates whether a proposal is ready for civic discussion.
     *
     * @param proposalId the proposal to validate
     * @return validation result with feedback and suggestions
     */
    ValidationResultView validate(ProposalId proposalId);

    /**
     * Result of a proposal discussability check.
     *
     * @param isValid            whether the proposal passes the validation gate
     * @param feedback           human-readable validation feedback
     * @param suggestedTitle     AI-suggested improvement for the title
     * @param suggestedProblem   AI-suggested improvement for the problem statement
     * @param suggestedAction    AI-suggested improvement for the proposed action
     */
    record ValidationResultView(
        boolean isValid,
        String feedback,
        String suggestedTitle,
        String suggestedProblem,
        String suggestedAction
    ) {}
}
