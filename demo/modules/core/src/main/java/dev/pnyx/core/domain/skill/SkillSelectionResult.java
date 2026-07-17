package dev.pnyx.core.domain.skill;

import java.util.List;

/**
 * Result of an LLM-driven skill selection for a proposal.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/PANEL_SELECTION.md §5.2}, non-trivial proposals require
 * at minimum five mandatory skill classes. The AI-driven selector produces a recommended
 * panel composition that the system evaluates for quorum and diversity constraints before
 * locking the panel.
 *
 * @param selectedRoles     IDs of existing roles to include in the panel (5-8)
 * @param proposedNewSkills roles the LLM believes would help but don't exist yet
 * @param rationale         why these roles were chosen
 * @see ../docs/20_Protocol_Core/PANEL_SELECTION.md
 * @see ../docs/60_Skills/SKILLS.md
 */
public record SkillSelectionResult(
    List<String> selectedRoles,
    List<SkillProposal> proposedNewSkills,
    String rationale
) {
    /**
     * A proposed new skill role that does not yet exist in the registry.
     *
     * @param name        short name for the proposed skill
     * @param description what the skill would evaluate
     * @param reason      why the LLM believes this skill is needed
     */
    public record SkillProposal(String name, String description, String reason) { }
}
