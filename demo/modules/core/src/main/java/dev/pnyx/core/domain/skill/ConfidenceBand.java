package dev.pnyx.core.domain.skill;

/**
 * Derived confidence band for a skill output.
 * <p>
 * Per {@code CONFIDENCE_AND_SCORING.md §2}: "Confidence must be derived from protocol-defined
 * signals. It must not be treated as a free-form self-assessment by the executor."
 * <p>
 * The band is computed from evidence coverage, source quality, jurisdiction fit, freshness,
 * claim traceability, unknowns disclosure, and schema completeness — not from a raw
 * self-reported number.
 */
public enum ConfidenceBand {
    /**
     * High confidence — standard progression when risk level is not exceptional
     * and no mandatory human review rule applies.
     */
    HIGH,
    /**
     * Moderate confidence — typically triggers extra review, targeted verification,
     * or adversarial check.
     */
    MEDIUM,
    /**
     * Low confidence — usually triggers mandatory human review, replication,
     * or delay pending clarification.
     */
    LOW,
    /**
     * Insufficient basis — the packet should not advance as if it were simply low confidence.
     * It is blocked or incomplete.
     */
    INSUFFICIENT
}