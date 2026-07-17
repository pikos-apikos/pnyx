package dev.pnyx.core.domain.execution;

import java.util.UUID;

/**
 * Strongly-typed identifier for an execution task within an implementation fork.
 * <p>
 * Prevents {@link java.util.UUID} / {@link String} confusion at the type level.
 * Each task in a fork is uniquely identified by an {@code ExecutionTaskId}, enabling
 * independent status tracking and event sourcing per task.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public record ExecutionTaskId(UUID value) {

    /**
     * Validates that the wrapped UUID is not null.
     */
    public ExecutionTaskId {
        if (value == null) {
            throw new IllegalArgumentException("ExecutionTaskId value must not be null");
        }
    }

    public static ExecutionTaskId generate() {
        return new ExecutionTaskId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
