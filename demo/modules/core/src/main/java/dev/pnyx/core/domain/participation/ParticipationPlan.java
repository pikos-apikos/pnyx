package dev.pnyx.core.domain.participation;

import java.util.List;

import dev.pnyx.core.domain.proposal.ProposalId;

/**
 * Per-proposal participation design, proportional to classification.
 * <p>
 * Per {@code ../docs/45_Participation/PARTICIPATION_MODEL.md §6} and
 * {@code ../docs/90_Information/DATA_MODEL.md §5.38}: a non-trivial proposal must
 * have an active ParticipationPlan before panel selection begins — this is the
 * exit condition of the {@code PARTICIPATION_DESIGN_PENDING} internal state
 * ({@code ../docs/80_Runtime/STATE_MACHINE.md §4.4}).
 * <p>
 * The plan is versioned and revisable; {@code PARTICIPATION_DESIGNED} certifies
 * only that a first complete plan exists, not that participation design is
 * finished. Revisions create new versions that supersede the prior plan.
 * <p>
 * MVP floor ({@code ../docs/70_Bootstrap/MINIMUM_VIABLE_PNYX.md §4.11}): at minimum
 * {@link ParticipationMode#OPEN} participation with declared barriers and audit
 * criteria. Sortition, delegation, and formed bodies are deferred per §5.5.
 *
 * @see ../docs/45_Participation/PARTICIPATION_MODEL.md
 * @see ../docs/90_Information/DATA_MODEL.md
 * @see ../docs/80_Runtime/STATE_MACHINE.md
 */
public record ParticipationPlan(
    ProposalId proposalId,
    int versionNo,
    List<ParticipationMode> selectedModes,
    List<String> expectedBarriers,
    List<String> missingPerspectives,
    String compensationRules,
    List<String> accessibilityMeasures,
    String auditCriteria,
    String status
) {

    /**
     * Compact constructor validating MVP floor requirements.
     *
     * @throws IllegalArgumentException if proposalId is null, modes are empty,
     *         or audit criteria are blank
     */
    public ParticipationPlan {
        if (proposalId == null) {
            throw new IllegalArgumentException("proposalId must not be null");
        }
        if (selectedModes == null || selectedModes.isEmpty()) {
            throw new IllegalArgumentException("selectedModes must not be empty");
        }
        if (auditCriteria == null || auditCriteria.isBlank()) {
            throw new IllegalArgumentException("auditCriteria must not be blank");
        }
        List<ParticipationMode> modes = List.copyOf(selectedModes);
        List<String> barriers = expectedBarriers != null
            ? List.copyOf(expectedBarriers) : List.of();
        List<String> perspectives = missingPerspectives != null
            ? List.copyOf(missingPerspectives) : List.of();
        List<String> accessibility = accessibilityMeasures != null
            ? List.copyOf(accessibilityMeasures) : List.of();
        selectedModes = modes;
        expectedBarriers = barriers;
        missingPerspectives = perspectives;
        accessibilityMeasures = accessibility;
    }

    /**
     * Checks whether this plan satisfies the MVP participation floor.
     * <p>
     * Per {@code ../docs/70_Bootstrap/MINIMUM_VIABLE_PNYX.md §4.11}, the floor
     * requires at least open participation with declared audit criteria.
     *
     * @return true if the plan meets the MVP floor
     */
    public boolean satisfiesMvpFloor() {
        return selectedModes.contains(ParticipationMode.OPEN)
            && auditCriteria != null && !auditCriteria.isBlank();
    }
}
