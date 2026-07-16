package dev.pnyx.endpoint.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.pnyx.core.api.ExecutionApi;
import dev.pnyx.core.domain.proposal.DecisionId;

import lombok.RequiredArgsConstructor;

/**
 * REST endpoint for implementation fork tracking.
 *
 * @see ../docs/80_Runtime/API_SPEC.md
 * @see ../docs/20_Protocol_Core/ROUTING.md
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/proposals/{decisionId}/execution")
public class ExecutionController {

    private final ExecutionApi executionApi;

    /**
     * Returns the current implementation fork for a decision.
     *
     * @param decisionId the decision UUID
     * @return the current fork view
     */
    @GetMapping
    public ResponseEntity<ExecutionApi.ForkView> fork(@PathVariable UUID decisionId) {
        return ResponseEntity.ok(executionApi.findFork(new DecisionId(decisionId)));
    }

    /**
     * Creates an implementation fork with one or more execution paths.
     *
     * @param decisionId the decision UUID
     * @param paths      the execution paths to include
     * @return the created fork view
     */
    @PostMapping
    public ResponseEntity<ExecutionApi.ForkView> createFork(
        @PathVariable UUID decisionId,
        @RequestBody List<ExecutionApi.PathView> paths) {
        return ResponseEntity.ok(executionApi.createFork(new DecisionId(decisionId), paths));
    }

    /**
     * Adds a task to the current implementation fork.
     *
     * @param decisionId the decision UUID
     * @param task       the task to add
     * @return the updated fork view
     */
    @PostMapping("/tasks")
    public ResponseEntity<ExecutionApi.ForkView> addTask(
        @PathVariable UUID decisionId,
        @RequestBody ExecutionApi.TaskView task) {
        return ResponseEntity.ok(executionApi.addTask(new DecisionId(decisionId), task));
    }
}
