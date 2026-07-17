package dev.pnyx.core.domain.participation;

/**
 * Lifecycle status of a Civic Receipt.
 * <p>
 * Per {@code ../docs/45_Participation/PARTICIPATION_MODEL.md §12}, the receipt
 * lifecycle is: {@code ACCEPTED} → {@code INCLUDED} (→ {@code CORRECTED} |
 * {@code SUPERSEDED} | {@code REJECTED}).
 * <p>
 * The MVP floor ({@code ../docs/70_Bootstrap/MINIMUM_VIABLE_PNYX.md §4.11}) allows
 * a "logged, challengeable equivalent" when cryptographic uniqueness proofs are
 * deferred per §5.1.
 *
 * @see ../docs/45_Participation/PARTICIPATION_MODEL.md
 */
public enum CivicReceiptStatus {
    ACCEPTED("accepted"),
    INCLUDED("included"),
    CORRECTED("corrected"),
    SUPERSEDED("superseded"),
    REJECTED("rejected");

    private final String code;

    CivicReceiptStatus(String code) {
        this.code = code;
    }

    /**
     * Stable storage code for the receipt status.
     *
     * @return the status code string
     */
    public String value() {
        return code;
    }
}
