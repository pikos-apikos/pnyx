package dev.pnyx.core.domain.participation;

import dev.pnyx.core.domain.proposal.ProposalId;

/**
 * Verifiable record that a participant's civic action was accepted and recorded.
 * <p>
 * Per {@code ../docs/45_Participation/PARTICIPATION_MODEL.md §12} and
 * {@code ../docs/90_Information/DATA_MODEL.md §5.47}: after a participant performs
 * a material civic action, the system should provide a Civic Receipt allowing
 * the participant to verify that the action was accepted, which case received
 * it, and whether it was included in an aggregate.
 * <p>
 * Disclosure default is <strong>private proof, public aggregate</strong>: the
 * receipt must not reveal how the participant acted unless the participant
 * chooses disclosure or the process requires public attribution.
 * <p>
 * MVP floor ({@code ../docs/70_Bootstrap/MINIMUM_VIABLE_PNYX.md §4.11}): where
 * cryptographic uniqueness proofs are deferred per §5.1, the receipt requirement
 * may be satisfied by a logged, challengeable equivalent. This record is that
 * equivalent — a signed inclusion proof the participant can retrieve.
 *
 * @see ../docs/45_Participation/PARTICIPATION_MODEL.md
 * @see ../docs/90_Information/DATA_MODEL.md
 */
public record CivicReceipt(
    ProposalId proposalId,
    String participantRef,
    String actionType,
    String policyAppliedRef,
    CivicReceiptStatus status,
    String inclusionProofRef
) {

    /**
     * Compact constructor validating required fields.
     *
     * @throws IllegalArgumentException if any required field is null/blank
     */
    public CivicReceipt {
        if (proposalId == null) {
            throw new IllegalArgumentException("proposalId must not be null");
        }
        if (participantRef == null || participantRef.isBlank()) {
            throw new IllegalArgumentException("participantRef must not be blank");
        }
        if (actionType == null || actionType.isBlank()) {
            throw new IllegalArgumentException("actionType must not be blank");
        }
        if (status == null) {
            throw new IllegalArgumentException("status must not be null");
        }
        if (inclusionProofRef == null) {
            inclusionProofRef = "";
        }
        if (policyAppliedRef == null) {
            policyAppliedRef = "";
        }
    }
}
