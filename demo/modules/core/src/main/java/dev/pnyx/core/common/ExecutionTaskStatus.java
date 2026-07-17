package dev.pnyx.core.common;

/**
 * Lifecycle status for individual execution tasks.
 * <p>
 * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §3}, tasks follow a strict transition sequence:
 * PLANNED → IN_PROGRESS → BLOCKED → COMPLETED. COMPLETED is terminal.
 *
 * @see ../docs/80_Runtime/STATE_MACHINE.md
 * @see ../docs/20_Protocol_Core/ROUTING.md
 */
public enum ExecutionTaskStatus {
    PLANNED("planned"),
    IN_PROGRESS("in_progress"),
    BLOCKED("blocked"),
    COMPLETED("completed");

    private final String code;

    ExecutionTaskStatus(String code) {
        this.code = code;
    }

    /**
     * Stable value written into public execution artifacts.
     *
     * @return the status code string
     */
    public String value() {
        return code;
    }
}