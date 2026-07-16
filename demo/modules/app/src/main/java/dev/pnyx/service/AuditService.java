package dev.pnyx.service;

import dev.pnyx.core.api.AuditApi;
import dev.pnyx.core.domain.proposal.ProposalId;
import dev.pnyx.core.spi.EventStoreSpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implements {@link dev.pnyx.core.api.AuditApi} — audit trail access.
 * <p>
 * Provides stream-based access to the event-sourced audit log. Per
 * {@code ../docs/90_Information/AUDIT_LOG.md}, every meaningful state change produces an auditable
 * trace encoded as a SHA-256 hash-chained event stream.
 *
 * @see ../docs/90_Information/AUDIT_LOG.md
 * @see ../docs/80_Runtime/EVENT_MODEL.md
 */
@Service
@RequiredArgsConstructor
public class AuditService implements AuditApi {

    private final EventStoreSpi eventStore;

    @Override
    public List<AuditEntry> stream(ProposalId proposalId) {
        List<EventStoreSpi.StoredEvent> events = eventStore.readStream(proposalId.value());
        List<AuditEntry> entries = new ArrayList<>();
        String expectedPreviousHash = null;
        for (EventStoreSpi.StoredEvent event : events) {
            String expectedContentHash = computeHash(event.eventPayload(), event.previousHash());
            boolean previousHashValid = equalsNullable(expectedPreviousHash, event.previousHash());
            boolean contentHashValid = expectedContentHash.equals(event.contentHash());
            boolean valid = previousHashValid && contentHashValid;
            entries.add(new AuditEntry(event.streamId(), event.streamVersion(), event.eventType(),
                event.eventPayload(), event.contentHash(), event.previousHash(), event.occurredAt(),
                valid, verificationMessage(previousHashValid, contentHashValid)));
            expectedPreviousHash = event.contentHash();
        }
        return entries;
    }

    private String verificationMessage(boolean previousHashValid, boolean contentHashValid) {
        if (previousHashValid && contentHashValid) { return "OK"; }
        if (!previousHashValid && !contentHashValid) { return "previous hash and content hash mismatch"; }
        if (!previousHashValid) { return "previous hash mismatch"; }
        return "content hash mismatch";
    }

    private boolean equalsNullable(String expected, String actual) {
        return expected == null ? actual == null : expected.equals(actual);
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
