package dev.pnyx.core.domain.skill;

/**
 * Callback interface invoked when a skill executor completes a review or reports progress.
 * <p>
 * Per {@code ../docs/60_Skills/EXECUTOR_MODEL.md}, skill execution is asynchronous and may
 * involve multiple tool calls. The callback pattern allows the runtime to track completion
 * status per role without blocking the executor.
 *
 * @see ../docs/60_Skills/EXECUTOR_MODEL.md
 */
@FunctionalInterface
public interface ProgressCallback {

    void onTurnStart(String role, int turn);

    default void onToolCallStart(String role, String toolName, String arguments) {}

    default void onToolCallResult(String role, String toolName, ToolResult result) {}

    default void onStatus(String role, String message) {}

    default void onComplete(String role, AiReview review) {}

    default void onError(String role, Throwable error) {}
}
