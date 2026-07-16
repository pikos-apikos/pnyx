package dev.pnyx.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pnyx.core.api.ExecutionApi;
import dev.pnyx.core.common.ContentHash;
import dev.pnyx.core.common.ExecutionTaskStatus;
import dev.pnyx.core.common.PublicObjectType;
import dev.pnyx.core.common.PublicSchemaVersion;
import dev.pnyx.core.domain.execution.TaskAdded;
import dev.pnyx.core.domain.execution.TaskTransitioned;
import dev.pnyx.core.domain.execution.ExecutionTaskId;
import dev.pnyx.core.domain.proposal.DecisionId;
import dev.pnyx.core.spi.EventStoreSpi;
import dev.pnyx.core.spi.PublicStoreSpi;
import dev.pnyx.config.PnyxProperties;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExecutionServiceTest {

    @Test
    void shouldAddTaskAndPublishStablePublicArtifactValues() {
        StubPublicStore publicStore = new StubPublicStore();
        StubEventStore eventStore = new StubEventStore();
        ExecutionService service = new ExecutionService(eventStore, publicStore, new ObjectMapper());
        DecisionId decisionId = DecisionId.generate();
        ExecutionApi.TaskView task = new ExecutionApi.TaskView(ExecutionTaskId.generate(), "Confirm vendor timeline",
            ExecutionTaskStatus.IN_PROGRESS, "implementation-team", "2026-06-01",
            "Collect delivery dates and publish the evidence link.", List.of("https://example.test/evidence"));

        var fork = service.addTask(decisionId, task);

        assertThat(fork.tasks()).contains(task);
        assertThat(publicStore.lastJson).contains("\"status\":\"in_progress\"");
        assertThat(publicStore.lastJson).contains("\"pathType\":\"institutional_action\"");
        assertThat(publicStore.lastJson).contains("https://example.test/evidence");
        assertThat(publicStore.lastJson).doesNotContain("IN_PROGRESS");
        assertThat(publicStore.lastJson).doesNotContain("INSTITUTIONAL_ACTION");
        assertThat(publicStore.manifestUpdated).isTrue();
        
        assertThat(eventStore.appendedEvents).isNotEmpty();
        assertThat(eventStore.appendedEvents.getLast()).isInstanceOf(TaskAdded.class);
    }

    @Test
    void shouldTransitionTaskAndPublishEvent() {
        StubPublicStore publicStore = new StubPublicStore();
        StubEventStore eventStore = new StubEventStore();
        ExecutionService service = new ExecutionService(eventStore, publicStore, new ObjectMapper());
        DecisionId decisionId = DecisionId.generate();
        
        ExecutionApi.TaskView task = new ExecutionApi.TaskView(ExecutionTaskId.generate(), "Test Task",
            ExecutionTaskStatus.PLANNED, "owner", null, "summary", List.of());
        service.addTask(decisionId, task);
        eventStore.appendedEvents.clear(); // clear addTask event
        
        var result = service.transitionTask(decisionId, task.taskId(), ExecutionTaskStatus.IN_PROGRESS, "Started work");
        
        assertThat(result.task().status()).isEqualTo(ExecutionTaskStatus.IN_PROGRESS);
        assertThat(eventStore.appendedEvents).hasSize(1);
        assertThat(eventStore.appendedEvents.getFirst()).isInstanceOf(TaskTransitioned.class);
        TaskTransitioned event = (TaskTransitioned) eventStore.appendedEvents.getFirst();
        assertThat(event.previousStatus()).isEqualTo(ExecutionTaskStatus.PLANNED);
        assertThat(event.newStatus()).isEqualTo(ExecutionTaskStatus.IN_PROGRESS);
        assertThat(event.reason()).isEqualTo("Started work");
    }

    @Test
    void shouldRejectInvalidTransitions() {
        StubPublicStore publicStore = new StubPublicStore();
        StubEventStore eventStore = new StubEventStore();
        ExecutionService service = new ExecutionService(eventStore, publicStore, new ObjectMapper());
        DecisionId decisionId = DecisionId.generate();
        
        ExecutionApi.TaskView task = new ExecutionApi.TaskView(ExecutionTaskId.generate(), "Test Task",
            ExecutionTaskStatus.PLANNED, "owner", null, "summary", List.of());
        service.addTask(decisionId, task);
        
        assertThatThrownBy(() -> service.transitionTask(decisionId, task.taskId(), ExecutionTaskStatus.COMPLETED, "Done"))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Invalid transition");
    }

    private static final class StubEventStore implements EventStoreSpi {
        public final List<Object> appendedEvents = new ArrayList<>();

        @Override
        public void append(UUID streamId, List<?> events) {
            appendedEvents.addAll(events);
        }

        @Override
        public List<StoredEvent> readStream(UUID streamId) {
            return List.of();
        }
    }

    private static final class StubPublicStore implements PublicStoreSpi {
        private String lastJson;
        private boolean manifestUpdated;

        @Override
        public ContentHash write(PublicObjectType objectType, PublicSchemaVersion schemaVersion,
                                 String createdBy, String canonicalJson) {
            assertThat(objectType).isEqualTo(PublicObjectType.IMPLEMENTATION_FORK);
            assertThat(schemaVersion).isEqualTo(PublicSchemaVersion.IMPLEMENTATION_FORK_V1);
            lastJson = canonicalJson;
            return ContentHash.of("0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef");
        }

        @Override
        public String read(ContentHash hash) {
            return lastJson;
        }

        @Override
        public void updateManifest(String networkName, ContentHash objectHash,
                                   PublicObjectType objectType, String location) {
            assertThat(networkName).isEqualTo("test-network");
            assertThat(objectType).isEqualTo(PublicObjectType.IMPLEMENTATION_FORK);
            assertThat(location).contains(objectHash.hexDigest());
            manifestUpdated = true;
        }

        @Override
        public List<IndexEntry> listByType(PublicObjectType objectType) {
            return List.of();
        }
    }
}