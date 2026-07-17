package dev.pnyx.core.domain.skill;

/**
 * Result of executing a single skill tool.
 * <p>
 * Per {@code ../docs/60_Skills/EXECUTOR_MODEL.md §3}, tool execution returns either a successful
 * output payload or a structured failure with an error message. Tools that fail may trigger
 * retry, fallback, or escalation depending on the executor's fault-tolerance policy.
 *
 * @see ../docs/60_Skills/EXECUTOR_MODEL.md
 * @see ../docs/60_Skills/CONFIDENCE_AND_SCORING.md
 */
public record ToolResult(
    String toolName,
    boolean success,
    String content,
    String error
) {
    public static ToolResult success(String toolName, String content) {
        return new ToolResult(toolName, true, content, null);
    }

    public static ToolResult failure(String toolName, String error) {
        return new ToolResult(toolName, false, null, error);
    }
}
