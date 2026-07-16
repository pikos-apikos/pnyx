package dev.pnyx.core.domain.execution;

import dev.pnyx.core.domain.proposal.ProposalId;

import java.time.Instant;
import java.util.List;

/**
 * Learning Artifact capturing lessons, success/failure factors, and recommendations
 * after monitoring execution outcomes.
 * <p>
 * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.6.3}, after the monitoring phase,
 * the system publishes learning artifacts that capture what was learned from the
 * proposal's execution, enabling systemic improvement.
 * <p>
 * Per {@code ../docs/90_Information/DATA_MODEL.md}, the learning artifact is a public
 * artifact that is content-addressed and stored alongside the proposal.
 *
 * @see ../docs/80_Runtime/STATE_MACHINE.md
 * @see ../docs/90_Information/DATA_MODEL.md
 */
public record LearningArtifact(
    ProposalId proposalId,
    List<String> lessonsLearned,
    List<String> successFactors,
    List<String> failureFactors,
    List<String> recommendations,
    List<String> applicableContexts,
    Instant publishedAt,
    String publishedBy
) {

    /**
     * Compact constructor validating required fields.
     *
     * @throws IllegalArgumentException if proposalId is null, publishedBy is blank,
     *         or publishedAt is null
     */
    public LearningArtifact {
        if (proposalId == null) {
            throw new IllegalArgumentException("proposalId must not be null");
        }
        if (publishedBy == null || publishedBy.isBlank()) {
            throw new IllegalArgumentException("publishedBy must not be blank");
        }
        if (publishedAt == null) {
            throw new IllegalArgumentException("publishedAt must not be null");
        }
        lessonsLearned = lessonsLearned != null ? List.copyOf(lessonsLearned) : List.of();
        successFactors = successFactors != null ? List.copyOf(successFactors) : List.of();
        failureFactors = failureFactors != null ? List.copyOf(failureFactors) : List.of();
        recommendations = recommendations != null ? List.copyOf(recommendations) : List.of();
        applicableContexts = applicableContexts != null ? List.copyOf(applicableContexts) : List.of();
    }
}
