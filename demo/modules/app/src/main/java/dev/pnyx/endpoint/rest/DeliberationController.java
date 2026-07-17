package dev.pnyx.endpoint.rest;

import dev.pnyx.core.api.DeliberationApi;
import dev.pnyx.core.domain.proposal.ProposalId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST endpoint for starting and monitoring skill-panel deliberation.
 *
 * @see ../docs/80_Runtime/API_SPEC.md
 * @see ../docs/20_Protocol_Core/PANEL_SELECTION.md
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/proposals/{id}/deliberation")
public class DeliberationController {

    private final DeliberationApi deliberationApi;

    /**
     * Returns current deliberation progress for a proposal.
     *
     * @param id the proposal UUID
     * @return the progress view, or 404 if no deliberation started
     */
    @GetMapping
    public ResponseEntity<DeliberationApi.ProgressView> getProgress(@PathVariable UUID id) {
        return deliberationApi.getProgress(new ProposalId(id))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
