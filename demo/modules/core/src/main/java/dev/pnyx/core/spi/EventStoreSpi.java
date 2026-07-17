package dev.pnyx.core.spi;

import java.util.List;
import java.util.UUID;

/**
 * Driven port for append-only proposal event persistence.
 * <p>
 * Per {@code ../docs/80_Runtime/EVENT_MODEL.md}, the event store is the system's append-only journal.
 * All domain events are persisted as hash-chained, canonical JSON records. The table is a
 * disposable read-model — canonical truth lives in the public artifact store.
 *
 * @see ../docs/80_Runtime/EVENT_MODEL.md
 * @see ../docs/90_Information/AUDIT_LOG.md
 * @see ../docs/80_Runtime/INVARIANTS.md
 */
public interface EventStoreSpi {

    /**
     * A single stored event in the append-only event stream.
     *
     * @param streamId      aggregate UUID
     * @param streamVersion monotonically increasing version per stream
     * @param eventType     fully-qualified domain event class name
     * @param eventPayload  canonical JSON payload
     * @param contentHash   SHA-256 of the canonical JSON payload
     * @param previousHash  SHA-256 of the prior event (null for version 1)
     * @param occurredAt    ISO-8601 timestamp of the event
     */
    record StoredEvent(UUID streamId, long streamVersion,
                       String eventType, String eventPayload,
                       String contentHash, String previousHash,
                       String occurredAt) { }

    /**
     * Appends new domain events to the stream for an aggregate.
     *
     * @param streamId the aggregate stream to append to
     * @param events   domain events to persist
     */
    void append(UUID streamId, List<?> events);

    /**
     * Reads the complete ordered event stream for an aggregate.
     *
     * @param streamId the aggregate stream to read
     * @return ordered list of stored events
     */
    List<StoredEvent> readStream(UUID streamId);
}
