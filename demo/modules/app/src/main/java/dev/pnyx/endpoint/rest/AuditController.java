package dev.pnyx.endpoint.rest;

import dev.pnyx.core.api.AuditApi;
import dev.pnyx.core.domain.proposal.ProposalId;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST endpoint for exposing proposal audit streams.
 *
 * @see ../docs/80_Runtime/API_SPEC.md
 * @see ../docs/90_Information/AUDIT_LOG.md
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/audit")
public class AuditController {

    private final AuditApi auditApi;

    /**
     * Returns the full audit stream for a proposal.
     *
     * @param proposalId the proposal UUID
     * @return ordered list of audit entries
     */
    @GetMapping("/{proposalId}")
    public List<AuditApi.AuditEntry> stream(@PathVariable UUID proposalId) {
        return auditApi.stream(new ProposalId(proposalId));
    }
}
