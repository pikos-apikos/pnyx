package dev.pnyx.core.domain.participation;

/**
 * Depth of a Participation Audit, proportional to proposal classification.
 * <p>
 * Per {@code ../docs/45_Participation/PARTICIPATION_MODEL.md §11} and the
 * classification binding in {@code ../docs/99_Reference/CORE_V03_RECONCILIATION.md}
 * conflict 2: {@code FULL} for high-impact proposals, {@code LIGHTWEIGHT} for
 * other non-trivial proposals. Trivial proposals are exempt via the trivial
 * shortcut ({@code ../docs/80_Runtime/STATE_MACHINE.md §5}).
 *
 * @see ../docs/45_Participation/PARTICIPATION_MODEL.md
 */
public enum AuditDepth {
    FULL("full"),
    LIGHTWEIGHT("lightweight");

    private final String code;

    AuditDepth(String code) {
        this.code = code;
    }

    /**
     * Stable storage code for the audit depth.
     *
     * @return the depth code string
     */
    public String value() {
        return code;
    }
}
