package dev.pnyx.core.domain.execution;

import dev.pnyx.core.common.ExecutionTaskStatus;
import java.util.UUID;

/**
 * Event recorded when an execution task transitions between statuses.
 * <p>
 * Valid transitions: PLANNED → IN_PROGRESS → BLOCKED → COMPLETED.
 * COMPLETED is terminal — per {@code ../docs/80_Runtime/STATE_MACHINE.md §3}, completed execution
 * tasks may not be reopened. Invalid transitions are rejected at the domain level.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 * @see ../docs/80_Runtime/STATE_MACHINE.md
 */
public record TaskTransitioned(
    ExecutionTaskId taskId,
    ExecutionTaskStatus previousStatus,
    ExecutionTaskStatus newStatus,
    String reason
) implements ExecutionTaskEvent {
}
