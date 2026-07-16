package dev.pnyx.core.api;

import java.util.List;
import java.util.UUID;
import dev.pnyx.core.domain.proposal.DecisionId;
import dev.pnyx.core.domain.execution.ExecutionTaskId;

import dev.pnyx.core.common.ExecutionPathType;
import dev.pnyx.core.common.ExecutionTaskStatus;

/**
 * Driving port for implementation tracking after a decision is made.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/ROUTING.md}, post-decision execution follows a chosen routing
 * path. This interface supports fork creation, task management, and status transitions for
 * implementation tracking.
 *
 * @see ../docs/80_Runtime/API_SPEC.md
 * @see ../docs/20_Protocol_Core/ROUTING.md
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public interface ExecutionApi {

    /**
     * Public read model for an implementation fork created after a decision.
     */
    record ForkView(UUID forkId, DecisionId decisionId, List<PathView> paths, List<TaskView> tasks) { }

    /**
     * Implementation route that can be tracked after approval.
     */
    record PathView(ExecutionPathType pathType, String title, ExecutionTaskStatus status) { }

    /**
     * Task-level progress item for a selected implementation route.
     */
    record TaskView(ExecutionTaskId taskId, String title, ExecutionTaskStatus status,
                    String owner, String dueDate, String summary, List<String> evidenceLinks) { }

    /**
     * Creates and publishes a new implementation fork for a decision.
     *
     * @param decisionId the decision to fork from
     * @param paths      implementation routes to track
     * @return the created fork view
     */
    ForkView createFork(DecisionId decisionId, List<PathView> paths);

    /**
     * Adds an implementation task to the current fork for a decision.
     *
     * @param decisionId the decision whose fork to update
     * @param task       the task to add
     * @return the updated fork view
     */
    ForkView addTask(DecisionId decisionId, TaskView task);

    /**
     * Returns the latest fork known for a decision.
     *
     * @param decisionId the decision to look up
     * @return the latest fork view
     */
    ForkView findFork(DecisionId decisionId);

    /**
     * Transitions a task to a new status.
     */
    record TransitionResult(ForkView fork, TaskView task) { }

    TransitionResult transitionTask(DecisionId decisionId, ExecutionTaskId taskId, ExecutionTaskStatus newStatus, String reason);
}
