package dev.pnyx.core.domain.skill;

import java.util.List;

/**
 * Describes an available skill role for the AI-driven panel selection prompt.
 * <p>
 * Per {@code ../docs/60_Skills/SKILLS.md §5.1}, each skill role carries a canonical class
 * identifier (e.g. {@code rights_constitutional}), a human-readable description, and
 * a set of named tools available to the executor. This descriptor is passed to the
 * selection prompt to inform dynamic panel assembly.
 *
 * @see ../docs/60_Skills/SKILLS.md
 * @see ../docs/20_Protocol_Core/PANEL_SELECTION.md
 */
public record SkillRoleDescriptor(
    String id,
    String description,
    List<String> toolNames
) { }
