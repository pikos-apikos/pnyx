package dev.pnyx.core.domain.proposal;

import java.util.Optional;

/**
 * Represents the result of a proposal classification.
 *
 * @see ../docs/30_Classification/CLASSIFICATION.md
 */
public record ClassificationResult(
    String governanceLayer,
    boolean nonTrivial,
    String rationale,
    Optional<String> spilloverNotes,
    boolean emergencyEligible,
    Optional<String> escalationRecommendation
) {
    /**
     * Validates required fields are not null or blank.
     */
    public ClassificationResult {
        if (governanceLayer == null || governanceLayer.isBlank()) {
            throw new IllegalArgumentException("governanceLayer must not be null or blank");
        }
        if (rationale == null || rationale.isBlank()) {
            throw new IllegalArgumentException("rationale must not be null or blank");
        }
    }
}
