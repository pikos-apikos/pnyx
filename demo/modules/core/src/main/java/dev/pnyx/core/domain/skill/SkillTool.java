package dev.pnyx.core.domain.skill;

/**
 * Describes a named tool available to a skill executor for gathering evidence or reasoning.
 * <p>
 * Per {@code ../docs/60_Skills/EXECUTOR_MODEL.md}, executors use tools to produce structured
 * outputs — each tool has a name, description, and JSON schema defining its input parameters.
 * Tools form the evidence-gathering interface between skill executors and the system.
 *
 * @see ../docs/60_Skills/EXECUTOR_MODEL.md
 */
public record SkillTool(
    String name,
    String description,
    String parametersJsonSchema
) { }
