package dev.pnyx.service;

import dev.pnyx.core.domain.proposal.ProposalId;
import dev.pnyx.core.spi.EventStoreSpi;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AuditServiceTest {

    @Test
    void shouldVerifyValidHashChain() {
        ProposalId streamId = ProposalId.generate();
        String firstPayload = "{\"event\":\"submitted\"}";
        String firstHash = hash(firstPayload, null);
        String secondPayload = "{\"event\":\"closed\"}";
        String secondHash = hash(secondPayload, firstHash);
        AuditService service = new AuditService(new StubEventStore(List.of(
            storedEvent(streamId.value(), 1, firstPayload, firstHash, null),
            storedEvent(streamId.value(), 2, secondPayload, secondHash, firstHash)
        )));

        var entries = service.stream(streamId);

        assertThat(entries).hasSize(2);
        assertThat(entries).allSatisfy(entry -> {
            assertThat(entry.hashValid()).isTrue();
            assertThat(entry.verificationMessage()).isEqualTo("OK");
        });
    }

    @Test
    void shouldReportBrokenHashChain() {
        ProposalId streamId = ProposalId.generate();
        String firstPayload = "{\"event\":\"submitted\"}";
        String firstHash = hash(firstPayload, null);
        String secondPayload = "{\"event\":\"closed\"}";
        AuditService service = new AuditService(new StubEventStore(List.of(
            storedEvent(streamId.value(), 1, firstPayload, firstHash, null),
            storedEvent(streamId.value(), 2, secondPayload, hash(secondPayload, "sha256:other"), "sha256:other")
        )));

        var entries = service.stream(streamId);

        assertThat(entries).hasSize(2);
        assertThat(entries.get(0).hashValid()).isTrue();
        assertThat(entries.get(1).hashValid()).isFalse();
        assertThat(entries.get(1).verificationMessage()).isEqualTo("previous hash mismatch");
    }

    private EventStoreSpi.StoredEvent storedEvent(UUID streamId, long version, String payload,
                                                  String contentHash, String previousHash) {
        return new EventStoreSpi.StoredEvent(streamId, version, "TestEvent", payload,
            contentHash, previousHash, "2026-05-28T00:00:00Z");
    }

    private static String hash(String payload, String previousHash) {
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

    private record StubEventStore(List<EventStoreSpi.StoredEvent> events) implements EventStoreSpi {

        @Override
        public void append(UUID streamId, List<?> events) {
            // no-op for read-only test
        }

        @Override
        public List<StoredEvent> readStream(UUID streamId) {
            return events;
        }
    }
}