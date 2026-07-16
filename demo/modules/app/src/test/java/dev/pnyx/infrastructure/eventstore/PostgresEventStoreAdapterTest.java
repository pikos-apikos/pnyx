package dev.pnyx.infrastructure.eventstore;

import dev.pnyx.core.domain.proposal.Proposal;
import dev.pnyx.core.spi.EventStoreSpi;
import dev.pnyx.infrastructure.test.DatabaseTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class PostgresEventStoreAdapterTest extends DatabaseTestBase {

    @Autowired
    private EventStoreSpi eventStore;

    @Test
    void shouldAppendAndReadEvents() {
        var proposal = Proposal.create("Test", "A substantive problem statement for testing", "Action").orElseThrow();
        var submitted = proposal.submit().orElseThrow();
        var events = submitted.uncommittedEvents();
        var streamId = submitted.proposalId();

        eventStore.append(streamId.value(), events);

        List<EventStoreSpi.StoredEvent> stored = eventStore.readStream(streamId.value());
        assertThat(stored).hasSize(1);
        assertThat(stored.get(0).eventType()).isEqualTo("ProposalSubmitted");
        assertThat(stored.get(0).contentHash()).startsWith("sha256:");
    }

    @Test
    void shouldChainHashesForMultiEventStream() {
        var proposal = Proposal.create("Test", "A substantive problem statement for testing", "Action").orElseThrow();
        var p1 = proposal.submit().orElseThrow();
        var p2 = p1.defineProblem().orElseThrow().markIntakeValid().orElseThrow();
        var streamId = p2.proposalId();

        eventStore.append(streamId.value(), p2.uncommittedEvents());

        List<EventStoreSpi.StoredEvent> stored = eventStore.readStream(streamId.value());
        assertThat(stored).hasSize(2);
        assertThat(stored.get(0).previousHash()).isNull();
        assertThat(stored.get(1).previousHash()).isEqualTo(stored.get(0).contentHash());
    }
}
