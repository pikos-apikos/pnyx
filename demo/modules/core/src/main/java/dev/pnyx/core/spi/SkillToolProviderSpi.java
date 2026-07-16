package dev.pnyx.core.spi;

import dev.pnyx.core.domain.skill.SkillTool;
import dev.pnyx.core.domain.skill.SkillReviewerRole;
import dev.pnyx.core.domain.skill.ToolResult;
import java.util.List;

/**
 * Driven port exposing tools available to skill reviewers.
 * <p>
 * Per {@code ../docs/60_Skills/EXECUTOR_MODEL.md §3}, each skill role may have access to a set of
 * named tools for evidence gathering and structured reasoning. This SPI provides role-specific
 * tool configurations and handles tool execution.
 *
 * @see ../docs/60_Skills/EXECUTOR_MODEL.md
 */
public interface SkillToolProviderSpi {

    List<SkillTool> toolsForRole(SkillReviewerRole role);

    ToolResult executeTool(SkillReviewerRole role, String toolName, String argumentsJson);
}
