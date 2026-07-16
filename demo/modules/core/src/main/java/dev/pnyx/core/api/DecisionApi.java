package dev.pnyx.core.api;

import java.util.UUID;
import dev.pnyx.core.domain.proposal.ProposalId;

/**
 * Driving port for recording public decisions on proposals.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/PROTOCOL.md §7}, a decision represents the conclusion of the
 * deliberation process. It records an outcome (approve, reject, remand, etc.) and produces a
 * public decision artifact.
 *
 * @see ../docs/80_Runtime/API_SPEC.md
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public interface DecisionApi {

    /**
     * Read model describing the decision outcome recorded for a proposal.
     */
    record DecisionView(ProposalId proposalId, String outcome, String decisionAt) { }

    /**
     * Records a public judgment for the proposal on behalf of an actor.
     *
     * @param proposalId the proposal being decided
     * @param outcome    the decision outcome (e.g. approve, reject, remand)
     * @param actorId    the deciding actor
     * @return the recorded decision view
     */
    DecisionView recordJudgment(ProposalId proposalId, String outcome, String actorId);
}
