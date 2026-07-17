package dev.pnyx.core.domain.participation;

/**
 * Participation modes available for a civic case, per the v0.3 participation model.
 * <p>
 * Per {@code ../docs/45_Participation/PARTICIPATION_MODEL.md §4}, a Participation Plan
 * selects one or more modes proportional to the proposal's classification tier.
 * The MVP floor ({@code ../docs/70_Bootstrap/MINIMUM_VIABLE_PNYX.md §4.11}) requires
 * at least {@link #OPEN} participation; sortition, delegation, and formed bodies
 * are deferred per §5.5.
 *
 * @see ../docs/45_Participation/PARTICIPATION_MODEL.md
 * @see ../docs/70_Bootstrap/MINIMUM_VIABLE_PNYX.md
 */
public enum ParticipationMode {
    OPEN("open"),
    AFFECTED_PARTY("affected_party"),
    TARGETED_INVITATION("targeted_invitation"),
    SORTITION("sortition"),
    CIVIC_JURY("civic_jury"),
    INSTITUTIONAL("institutional"),
    MONITORING("monitoring"),
    HYBRID("hybrid");

    private final String code;

    ParticipationMode(String code) {
        this.code = code;
    }

    /**
     * Stable storage code for the mode.
     *
     * @return the mode code string
     */
    public String value() {
        return code;
    }
}
