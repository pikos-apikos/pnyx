package dev.pnyx.core.domain.execution;

import dev.pnyx.core.common.ExecutionTaskStatus;
import java.util.List;
import java.util.UUID;
import dev.pnyx.core.domain.proposal.DecisionId;

/**
 * Event recorded when a task is added to an execution fork.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/PROTOCOL.md §8}, a fork is a set of tasks representing
 * the implementation plan for an approved proposal. Each task has a title, description,
 * assigned party, and initial PLANNED status.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 * @see ../docs/80_Runtime/STATE_MACHINE.md
 */
public record TaskAdded(
    ExecutionTaskId taskId,
    DecisionId decisionId,
    String title,
    String owner,
    String dueDate,
    String summary,
    List<String> evidenceLinks,
    ExecutionTaskStatus status
) implements ExecutionTaskEvent {
}
