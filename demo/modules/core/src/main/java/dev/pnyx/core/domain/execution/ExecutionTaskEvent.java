package dev.pnyx.core.domain.execution;

/**
 * Sealed interface for execution task domain events.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/PROTOCOL.md §8}, post-decision execution forks are tracked as
 * event-sourced task streams. Each task transition (PLANNED → IN_PROGRESS → BLOCKED → COMPLETED)
 * produces a domain event that is appended to the event store.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 * @see ../docs/80_Runtime/EVENT_MODEL.md
 */
public sealed interface ExecutionTaskEvent permits TaskAdded, TaskTransitioned {
}
