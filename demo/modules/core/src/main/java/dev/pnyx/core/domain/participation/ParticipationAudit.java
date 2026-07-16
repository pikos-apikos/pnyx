package dev.pnyx.core.domain.participation;

import dev.pnyx.core.domain.proposal.ProposalId;

/**
 * Per-case participation audit, required before decision-readiness.
 * <p>
 * Per {@code ../docs/45_Participation/PARTICIPATION_MODEL.md §11} and
 * {@code ../docs/90_Information/DATA_MODEL.md §5.49}: a Participation Audit is
 * required before every non-trivial proposal reaches decision readiness
 * ({@code ../docs/80_Runtime/STATE_MACHINE.md §4.4} guard on
 * {@code PUBLIC_REVIEW_OPEN → READY_FOR_DECISION}).
 * <p>
 * Depth is proportional to classification: {@link AuditDepth#FULL} for
 * high-impact proposals per the classification binding in
 * {@code ../docs/99_Reference/CORE_V03_RECONCILIATION.md} conflict 2,
 * {@link AuditDepth#LIGHTWEIGHT} for other non-trivial proposals. Trivial
 * proposals are exempt via the trivial shortcut.
 * <p>
 * The auditor must not hold a conflicting role on the same proposal. An upheld
 * challenge reopens the audit via a superseding record; history is preserved.
 *
 * @see ../docs/45_Participation/PARTICIPATION_MODEL.md
 * @see ../docs/90_Information/DATA_MODEL.md
 * @see ../docs/80_Runtime/STATE_MACHINE.md
 */
public record ParticipationAudit(
    ProposalId proposalId,
    AuditDepth depth,
    String auditorRef,
    String auditorConflictDeclaration,
    String findings,
    String limitations,
    String challengeableStatus
) {

    /**
     * Compact constructor validating required fields.
     *
     * @throws IllegalArgumentException if proposalId, depth, auditorRef, or
     *         challengeableStatus are null/blank
     */
    public ParticipationAudit {
        if (proposalId == null) {
            throw new IllegalArgumentException("proposalId must not be null");
        }
        if (depth == null) {
            throw new IllegalArgumentException("depth must not be null");
        }
        if (auditorRef == null || auditorRef.isBlank()) {
            throw new IllegalArgumentException("auditorRef must not be blank");
        }
        if (challengeableStatus == null || challengeableStatus.isBlank()) {
            throw new IllegalArgumentException("challengeableStatus must not be blank");
        }
        if (auditorConflictDeclaration == null) {
            auditorConflictDeclaration = "";
        }
        if (findings == null) {
            findings = "";
        }
        if (limitations == null) {
            limitations = "";
        }
    }

    /**
     * Checks whether this audit is published and not under an open challenge.
     * <p>
     * An audit with {@code challengeableStatus = "open"} is published but
     * may still be challenged. A proposal may proceed to decision-readiness
     * with an open-status audit; an upheld challenge ({@code upheld_reopened})
     * blocks progression until resolved.
     *
     * @return true if the audit allows decision-readiness
     */
    public boolean allowsDecisionReadiness() {
        return !"upheld_reopened".equals(challengeableStatus);
    }
}
