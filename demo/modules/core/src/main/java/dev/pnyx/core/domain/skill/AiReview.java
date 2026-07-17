package dev.pnyx.core.domain.skill;

import java.util.List;
import java.util.UUID;
import dev.pnyx.core.domain.proposal.ProposalId;

/**
 * Result returned by a skill-panel reviewer for a proposal event.
 * <p>
 * Per {@code ../docs/60_Skills/SKILLS.md §2}, skills do not vote, do not rule, and do not possess
 * legitimacy. Their role is limited and instrumental — they exist to improve public reasoning,
 * not to substitute for the public. The {@code AiReview} record is the output contract for
 * a single skill's assessment.
 *
 * @see ../docs/60_Skills/SKILLS.md
 * @see ../docs/60_Skills/CONFIDENCE_AND_SCORING.md
 * @see ../docs/60_Skills/CITATION_AND_SOURCING_POLICY.md
 */
public record AiReview(
    ProposalId proposalId,
    String role,
    String summary,
    List<String> findings,
    List<String> risks,
    List<String> missingInformation,
    List<String> assumptions,
    List<String> citations,
    ConfidenceRecord confidenceRecord
) {
    /**
     * Convenience accessor for the derived confidence band.
     *
     * @return the derived confidence from the {@link ConfidenceRecord}
     */
    public ConfidenceBand confidence() {
        return confidenceRecord.derivedConfidence();
    }

    /**
     * Factory method that derives confidence automatically from review content signals.
     * <p>
     * Per {@code ../docs/60_Skills/CONFIDENCE_AND_SCORING.md §2}, confidence must be derived
     * from protocol-defined signals, not self-reported by the executor.
     *
     * @param proposalId        the proposal being reviewed
     * @param role              the reviewer role identifier
     * @param summary           the review summary
     * @param findings          list of substantive findings
     * @param risks             list of identified risks
     * @param missingInformation list of acknowledged unknowns
     * @param assumptions       list of assumptions made
     * @param citations         list of citations
     * @param toolResultsUsed   number of tool results used (0 = none)
     * @return a new {@code AiReview} with derived confidence record
     */
    public static AiReview create(
        ProposalId proposalId, String role, String summary,
        List<String> findings, List<String> risks,
        List<String> missingInformation, List<String> assumptions,
        List<String> citations, int toolResultsUsed) {

        var record = ConfidenceRecord.derive(
            citations != null && !citations.isEmpty(),
            missingInformation != null && !missingInformation.isEmpty(),
            findings != null && !findings.isEmpty(),
            risks != null && !risks.isEmpty(),
            toolResultsUsed);

        return new AiReview(proposalId, role, summary, findings, risks,
            missingInformation, assumptions, citations, record);
    }
}