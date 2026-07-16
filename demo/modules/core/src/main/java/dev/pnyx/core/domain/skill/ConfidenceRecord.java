package dev.pnyx.core.domain.skill;

/**
 * Structured confidence derivation record.
 * <p>
 * Per {@code CONFIDENCE_AND_SCORING.md}, confidence is derived from explicit component scores
 * and penalties, not from a self-reported number by the executor. This record captures the
 * inputs that produce the {@link ConfidenceBand}.
 * <p>
 * For the prototype, the scoring is simplified (banded rather than fully weighted), but the
 * structure is in place to evolve toward the full scoring model.
 */
public record ConfidenceRecord(
    ConfidenceBand derivedConfidence,
    String confidenceExplanation,
    boolean reviewRequired,
    boolean humanReviewRequired,
    boolean readinessBlocked
) {
    /**
     * Derives a confidence band from heuristic signals available in the prototype.
     * <p>
     * This is a simplified derivation for the prototype. The full scoring model
     * (CONFIDENCE_AND_SCORING.md §7-9) uses weighted component scores and penalties.
     *
     * @param hasCitations       whether the review includes citations
     * @param hasUnknowns        whether the review explicitly declares unknowns
     * @param hasFindings        whether the review has substantive findings
     * @param hasRisks           whether the review identifies risks
     * @param toolResultsUsed    number of tool results used (0 = none)
     * @return a derived confidence record
     */
    public static ConfidenceRecord derive(
        boolean hasCitations, boolean hasUnknowns, boolean hasFindings,
        boolean hasRisks, int toolResultsUsed) {

        int score = 0;
        if (hasCitations) { score += 2; }
        if (hasUnknowns) { score += 1; }
        if (hasFindings) { score += 1; }
        if (hasRisks) { score += 1; }
        if (toolResultsUsed > 0) { score += 2; }

        ConfidenceBand band;
        boolean reviewRequired = false;
        boolean humanReviewRequired = false;
        boolean readinessBlocked = false;

        if (score >= 6) {
            band = ConfidenceBand.HIGH;
        } else if (score >= 3) {
            band = ConfidenceBand.MEDIUM;
            reviewRequired = true;
        } else if (score >= 1) {
            band = ConfidenceBand.LOW;
            reviewRequired = true;
            humanReviewRequired = true;
        } else {
            band = ConfidenceBand.INSUFFICIENT;
            readinessBlocked = true;
            humanReviewRequired = true;
        }

        String explanation = String.format(
            "Derived confidence: %s (score=%d, citations=%s, unknowns=%s, "
            + "findings=%s, risks=%s, toolResults=%d)",
            band, score, hasCitations, hasUnknowns, hasFindings, hasRisks, toolResultsUsed);

        return new ConfidenceRecord(band, explanation, reviewRequired,
            humanReviewRequired, readinessBlocked);
    }

    /**
     * Returns a default confidence record for mock/fallback reviews.
     */
    public static ConfidenceRecord defaultForMock() {
        return new ConfidenceRecord(ConfidenceBand.MEDIUM,
            "Mock review — confidence is derived, not self-reported",
            true, false, false);
    }
}