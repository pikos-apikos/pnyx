package dev.pnyx.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.pnyx.core.api.ExecutionApi;
import dev.pnyx.core.common.ExecutionPathType;
import dev.pnyx.core.common.ExecutionTaskStatus;
import dev.pnyx.core.common.PublicObjectType;
import dev.pnyx.core.common.PublicSchemaVersion;
import dev.pnyx.core.domain.execution.ExecutionTaskId;
import dev.pnyx.core.domain.execution.TaskAdded;
import dev.pnyx.core.domain.execution.TaskTransitioned;
import dev.pnyx.core.domain.proposal.DecisionId;
import dev.pnyx.core.spi.EventStoreSpi;
import dev.pnyx.core.spi.PublicStoreSpi;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Implements {@link dev.pnyx.core.api.ExecutionApi} — implementation fork and task tracking.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/ROUTING.md}, post-decision execution follows a routing path.
 * Forks track sets of tasks representing the implementation plan. Each task status transition
 * is event-sourced per {@code ../docs/80_Runtime/EVENT_MODEL.md}.
 *
 * @see ../docs/20_Protocol_Core/ROUTING.md
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 * @see ../docs/80_Runtime/EVENT_MODEL.md
 */
@Service
@RequiredArgsConstructor
public class ExecutionService implements ExecutionApi {

  private final EventStoreSpi eventStore;
  private final PublicStoreSpi publicStore;
  private final ObjectMapper objectMapper;
  private final Map<DecisionId, ForkView> forksByDecision = new ConcurrentHashMap<>();

  private record PublicImplementationForkObject(String schema, UUID forkId,
                                                UUID decisionId, String createdAt,
                                                List<PublicPathObject> paths,
                                                List<PublicTaskObject> tasks) { }

  private record PublicPathObject(String pathType, String title, String status) { }

  private record PublicTaskObject(UUID taskId, String title, String status,
                                  String owner, String dueDate, String summary,
                                  List<String> evidenceLinks) { }

  @Override
  public ForkView createFork(DecisionId decisionId, List<PathView> paths) {
    var fork = newFork(decisionId, paths);
    forksByDecision.put(decisionId, fork);
    
    List<TaskAdded> events = fork.tasks().stream()
        .map(t -> new TaskAdded(t.taskId(), decisionId, t.title(), t.owner(), t.dueDate(), t.summary(), t.evidenceLinks(), t.status()))
        .toList();
    eventStore.append(decisionId.value(), events);
    
    publishFork(fork);
    return fork;
  }

  @Override
  public ForkView addTask(DecisionId decisionId, TaskView task) {
    ForkView current = findFork(decisionId);
    var tasks = new ArrayList<>(current.tasks());
    tasks.add(task);
    var updated = new ForkView(current.forkId(), current.decisionId(), current.paths(), List.copyOf(tasks));
    forksByDecision.put(decisionId, updated);
    
    eventStore.append(decisionId.value(), List.of(new TaskAdded(task.taskId(), decisionId, task.title(), task.owner(), task.dueDate(), task.summary(), task.evidenceLinks(), task.status())));
    
    publishFork(updated);
    return updated;
  }

  @Override
  public ForkView findFork(DecisionId decisionId) {
    return forksByDecision.computeIfAbsent(decisionId, id -> {
      var fork = newFork(id, defaultPaths());
      
      List<TaskAdded> events = fork.tasks().stream()
          .map(t -> new TaskAdded(t.taskId(), id, t.title(), t.owner(), t.dueDate(), t.summary(), t.evidenceLinks(), t.status()))
          .toList();
      eventStore.append(id.value(), events);
      
      publishFork(fork);
      return fork;
    });
  }

  @Override
  public TransitionResult transitionTask(DecisionId decisionId, ExecutionTaskId taskId, ExecutionTaskStatus newStatus, String reason) {
    ForkView current = findFork(decisionId);
    
    TaskView taskToUpdate = current.tasks().stream()
        .filter(t -> t.taskId().equals(taskId))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Task not found: " + taskId));
        
    ExecutionTaskStatus oldStatus = taskToUpdate.status();
    
    boolean valid = switch (oldStatus) {
        case PLANNED -> newStatus == ExecutionTaskStatus.IN_PROGRESS || newStatus == ExecutionTaskStatus.BLOCKED;
        case IN_PROGRESS -> newStatus == ExecutionTaskStatus.BLOCKED || newStatus == ExecutionTaskStatus.COMPLETED;
        case BLOCKED -> newStatus == ExecutionTaskStatus.IN_PROGRESS;
        case COMPLETED -> false;
    };
    
    if (!valid) {
        throw new IllegalStateException("Invalid transition from " + oldStatus + " to " + newStatus);
    }
    
    TaskView updatedTask = new TaskView(taskToUpdate.taskId(), taskToUpdate.title(), newStatus,
        taskToUpdate.owner(), taskToUpdate.dueDate(), taskToUpdate.summary(), taskToUpdate.evidenceLinks());
        
    List<TaskView> updatedTasks = current.tasks().stream()
        .map(t -> t.taskId().equals(taskId) ? updatedTask : t)
        .toList();
        
    ForkView updatedFork = new ForkView(current.forkId(), current.decisionId(), current.paths(), updatedTasks);
    forksByDecision.put(decisionId, updatedFork);
    
    eventStore.append(decisionId.value(), List.of(new TaskTransitioned(taskId, oldStatus, newStatus, reason)));
    
    publishFork(updatedFork);
    return new TransitionResult(updatedFork, updatedTask);
  }

  private ForkView newFork(DecisionId decisionId, List<PathView> paths) {
    return new ForkView(UUID.randomUUID(), decisionId, paths, defaultTasks());
  }

  private List<PathView> defaultPaths() {
    return List.of(
      new PathView(ExecutionPathType.INSTITUTIONAL_ACTION, "Submit to municipality", ExecutionTaskStatus.PLANNED),
      new PathView(ExecutionPathType.PUBLIC_INTEREST_VENTURE, "Open-source dashboard", ExecutionTaskStatus.IN_PROGRESS)
    );
  }

  private List<TaskView> defaultTasks() {
    return List.of(
      new TaskView(ExecutionTaskId.generate(), "Assign implementation steward", ExecutionTaskStatus.PLANNED,
        "civic-ops", null, "Identify an accountable owner for post-decision follow-through.", List.of()),
      new TaskView(ExecutionTaskId.generate(), "Publish first progress evidence", ExecutionTaskStatus.PLANNED,
        "civic-ops", null, "Attach the first public evidence link once implementation starts.", List.of())
    );
  }

  private void publishFork(ForkView fork) {
    String canonicalJson;
    try {
        canonicalJson = objectMapper.writeValueAsString(new PublicImplementationForkObject(
          PublicSchemaVersion.IMPLEMENTATION_FORK_V1.value(), fork.forkId(), fork.decisionId().value(),
          Instant.now().toString(), publicPaths(fork.paths()), publicTasks(fork.tasks())));
    } catch (JsonProcessingException e) {
        throw new IllegalStateException("Failed to serialize fork", e);
    }
    var hash = publicStore.write(PublicObjectType.IMPLEMENTATION_FORK,
      PublicSchemaVersion.IMPLEMENTATION_FORK_V1, "did:pnyx:system", canonicalJson);
    publicStore.updateManifest("test-network", hash, PublicObjectType.IMPLEMENTATION_FORK,
      "objects/implementation_fork/" + hash.hexDigest() + ".json");
  }

  private List<PublicPathObject> publicPaths(List<PathView> paths) {
    return paths.stream()
      .map(path -> new PublicPathObject(path.pathType().value(), path.title(), path.status().value()))
      .toList();
  }

  private List<PublicTaskObject> publicTasks(List<TaskView> tasks) {
    return tasks.stream()
      .map(task -> new PublicTaskObject(task.taskId().value(), task.title(), task.status().value(),
        task.owner(), task.dueDate(), task.summary(), task.evidenceLinks()))
      .toList();
  }
}
