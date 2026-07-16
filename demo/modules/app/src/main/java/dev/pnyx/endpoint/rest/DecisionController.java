package dev.pnyx.endpoint.rest;

import dev.pnyx.core.api.DecisionApi;
import dev.pnyx.core.domain.proposal.ProposalId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST endpoint for recording proposal decisions.
 *
 * @see ../docs/80_Runtime/API_SPEC.md
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/proposals/{id}/decisions")
public class DecisionController {

    private final DecisionApi decisionApi;

    /**
     * Records a judgment on a proposal.
     *
     * @param id      the proposal UUID
     * @param outcome the decision outcome
     * @param actorId the deciding actor
     * @return the recorded decision view
     */
    @PostMapping
    public ResponseEntity<DecisionApi.DecisionView> decide(
        @PathVariable UUID id,
        @RequestParam String outcome,
        @RequestParam(defaultValue = "did:pnyx:anonymous") String actorId) {
        return ResponseEntity.ok(decisionApi.recordJudgment(new ProposalId(id), outcome, actorId));
    }
}
