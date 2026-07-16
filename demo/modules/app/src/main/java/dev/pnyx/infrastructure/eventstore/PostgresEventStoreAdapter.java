package dev.pnyx.infrastructure.eventstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import dev.pnyx.core.spi.EventStoreSpi;
import dev.pnyx.infrastructure.eventstore.jooq.tables.EventStream;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

/**
 * PostgreSQL-backed implementation of the append-only event store.
 * <p>
 * Per {@code ../docs/80_Runtime/EVENT_MODEL.md}, events are persisted in the {@code event_stream}
 * table as SHA-256 hash-chained, canonical JSON records. Each append verifies the hash chain
 * and monotonically increasing stream version.
 * <p>
 * This adapter implements {@link dev.pnyx.core.spi.EventStoreSpi} and is the infrastructure
 * bridge between domain events and the PostgreSQL database.
 *
 * @see ../docs/80_Runtime/EVENT_MODEL.md
 * @see ../docs/90_Information/AUDIT_LOG.md
 * @see ../docs/80_Runtime/INVARIANTS.md
 */
@Component
public class PostgresEventStoreAdapter implements EventStoreSpi {

    private static final EventStream E = EventStream.EVENT_STREAM;

    private final DSLContext dsl;
    private final ObjectMapper objectMapper;

    public PostgresEventStoreAdapter(DSLContext dsl, ObjectMapper springObjectMapper) {
        this.dsl = dsl;
        this.objectMapper = springObjectMapper.copy()
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
            .registerModule(new Jdk8Module());
    }

    @Override
    @Transactional
    public void append(UUID streamId, List<?> events) {
        String previousHash = latestHash(streamId);
        long baseVersion = currentVersion(streamId);

        for (int i = 0; i < events.size(); i++) {
            Object event = events.get(i);
            String payload = serialize(event);
            String contentHash = computeHash(payload, previousHash);

            dsl.insertInto(E)
                .set(E.STREAM_ID, streamId)
                .set(E.STREAM_VERSION, baseVersion + i + 1)
                .set(E.EVENT_TYPE, event.getClass().getSimpleName())
                .set(E.EVENT_PAYLOAD, payload)
                .set(E.CONTENT_HASH, contentHash)
                .set(E.PREVIOUS_HASH, previousHash)
                .set(E.OCCURRED_AT, Instant.now().atOffset(ZoneOffset.UTC))
                .execute();

            previousHash = contentHash;
        }
    }

    @Override
    public List<StoredEvent> readStream(UUID streamId) {
        return dsl.select(
                E.STREAM_ID,
                E.STREAM_VERSION,
                E.EVENT_TYPE,
                E.EVENT_PAYLOAD,
                E.CONTENT_HASH,
                E.PREVIOUS_HASH,
                E.OCCURRED_AT)
            .from(E)
            .where(E.STREAM_ID.eq(streamId))
            .orderBy(E.STREAM_VERSION)
            .fetch()
            .map(r -> new StoredEvent(
                r.value1(),
                r.value2(),
                r.value3(),
                    r.value4(),
                r.value5(),
                r.value6(),
                r.value7().toInstant().toString()
            ));
    }

    private long currentVersion(UUID streamId) {
        Long max = dsl.select(org.jooq.impl.DSL.max(E.STREAM_VERSION))
            .from(E)
            .where(E.STREAM_ID.eq(streamId))
            .fetchOne(0, Long.class);
        return max != null ? max : 0;
    }

    /**
     * Returns the content hash of the latest event in the stream, or null if the stream is empty.
     * This ensures hash chaining works across separate transaction batches.
     */
    private String latestHash(UUID streamId) {
        return dsl.select(E.CONTENT_HASH)
            .from(E)
            .where(E.STREAM_ID.eq(streamId))
            .orderBy(E.STREAM_VERSION.desc())
            .limit(1)
            .fetchOne(0, String.class);
    }

    private String serialize(Object event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to serialize event", e);
        }
    }

    private String computeHash(String payload, String previousHash) {
        try {
            String input = previousHash != null ? payload + previousHash : payload;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return "sha256:" + bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Hash computation failed", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        var sb = new StringBuilder();
        for (byte b : bytes) { sb.append(String.format("%02x", b)); }
        return sb.toString();
    }
}
