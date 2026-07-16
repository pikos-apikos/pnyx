package dev.pnyx.core.domain.skill;

import java.time.Instant;

/**
 * Tracks the progress of skill-panel reviews for a proposal.
 * <p>
 * Per {@code ../docs/60_Skills/SKILLS.md §4.1}, a panel assembles a set of reviewer roles that
 * each produce a review. This record tracks which roles have completed, which are pending,
 * and optionally the produced reviews for completed roles.
 *
 * @see ../docs/60_Skills/SKILLS.md
 */
public record ReviewProgress(
    String role,
    ReviewStatus status,
    String currentStep,
    int turn,
    String detail,
    Instant startedAt,
    Instant updatedAt
) {
    /** Possible states for a single reviewer's progress through the skill pipeline. */
    public enum ReviewStatus {
        PENDING,
        RUNNING,
        TOOL_CALL,
        ANALYZING,
        COMPLETED,
        FAILED
    }

    /**
     * Creates a new pending progress entry for a role.
     *
     * @param role the reviewer role
     * @return a new PENDING progress record
     */
    public static ReviewProgress pending(String role) {
        return new ReviewProgress(role, ReviewStatus.PENDING, null, 0,
            null, Instant.now(), Instant.now());
    }

    /**
     * Transitions to RUNNING status.
     *
     * @return updated progress record
     */
    public ReviewProgress running() {
        return new ReviewProgress(role, ReviewStatus.RUNNING, "Starting review...",
            turn, detail, startedAt, Instant.now());
    }

    /**
     * Transitions to TOOL_CALL status with the tool name and arguments.
     *
     * @param toolName the tool being called
     * @param args     the arguments passed to the tool
     * @return updated progress record
     */
    public ReviewProgress toolCall(String toolName, String args) {
        return new ReviewProgress(role, ReviewStatus.TOOL_CALL,
            "Calling " + toolName + "...", turn, args, startedAt, Instant.now());
    }

    /**
     * Transitions to ANALYZING status.
     *
     * @return updated progress record
     */
    public ReviewProgress analyzing() {
        return new ReviewProgress(role, ReviewStatus.ANALYZING,
            "Analyzing results...", turn, detail, startedAt, Instant.now());
    }

    /**
     * Transitions to COMPLETED status with a summary.
     *
     * @param summary the completion summary
     * @return updated progress record
     */
    public ReviewProgress completed(String summary) {
        return new ReviewProgress(role, ReviewStatus.COMPLETED, summary,
            turn, detail, startedAt, Instant.now());
    }

    /**
     * Transitions to FAILED status with an error message.
     *
     * @param error the failure description
     * @return updated progress record
     */
    public ReviewProgress failed(String error) {
        return new ReviewProgress(role, ReviewStatus.FAILED, error,
            turn, detail, startedAt, Instant.now());
    }
}
