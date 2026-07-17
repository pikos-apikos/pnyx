package dev.pnyx.core.spi;

import dev.pnyx.core.domain.skill.SkillRoleDescriptor;
import dev.pnyx.core.domain.skill.SkillSelectionResult;

import java.util.List;

/**
 * Driven port for dynamically selecting which skill-panel roles
 * should review a proposal.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/PANEL_SELECTION.md §5}, a selector dynamically chooses which
 * skill roles compose a proposal's review panel based on the proposal's content, classification,
 * and governance layer. The selector may propose new skill classes if existing ones are insufficient.
 *
 * @see ../docs/20_Protocol_Core/PANEL_SELECTION.md
 * @see ../docs/60_Skills/SKILLS.md
 */
public interface SkillSelectionSpi {

    /**
     * Given a proposal and the available skill pool, returns which roles
     * should participate in deliberation.
     *
     * @param title          proposal title
     * @param problem        proposal problem statement
     * @param proposedAction proposal action description
     * @param availableRoles the pool of available skill roles
     * @return selection result with recommended roles and any proposed new skills
     */
    SkillSelectionResult selectSkills(String title, String problem, String proposedAction,
                                      List<SkillRoleDescriptor> availableRoles);
}
