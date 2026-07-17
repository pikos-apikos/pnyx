package dev.pnyx.core.api;

import java.util.List;
import java.util.UUID;
import dev.pnyx.core.domain.proposal.ProposalId;

/**
 * Driving port for reading proposal audit trails.
 * <p>
 * Per {@code ../docs/90_Information/AUDIT_LOG.md}, every meaningful state change produces an
 * auditable trace. This interface provides stream-based access to the event-sourced audit log
 * for proposals and governance actions.
 *
 * @see ../docs/80_Runtime/API_SPEC.md
 * @see ../docs/90_Information/AUDIT_LOG.md
 */
public interface AuditApi {

    /**
     * Decoded audit entry with hash-chain integrity status.
     *
     * @param streamId             aggregate UUID
     * @param version              stream version number
     * @param eventType            domain event class name
     * @param eventPayload         canonical JSON payload
     * @param contentHash          SHA-256 of the payload
     * @param previousHash         SHA-256 of the preceding event
     * @param occurredAt           ISO-8601 timestamp
     * @param hashValid            whether the hash chain is intact
     * @param verificationMessage  human-readable integrity result
     */
    record AuditEntry(UUID streamId, long version, String eventType,
                      String eventPayload, String contentHash,
                      String previousHash, String occurredAt,
                      boolean hashValid, String verificationMessage) { }

    /**
     * Streams persisted audit entries for the given proposal.
     *
     * @param proposalId the proposal to audit
     * @return ordered list of audit entries
     */
    List<AuditEntry> stream(ProposalId proposalId);
}
