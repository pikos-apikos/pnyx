package dev.pnyx.core.domain.proposal;

import java.util.UUID;

/**
 * Event recorded when a proposal receives a classification label and governance layer assignment.
 * <p>
 * Classification determines the proposal's routing layer, required panel composition, and
 * legitimacy threshold. Per {@code ../docs/30_Classification/CLASSIFICATION.md}, a classified
 * proposal may proceed to panel assembly.
 *
 * @param classification the classification label
 * @param layer          the governance layer assignment
 * @param isNonTrivial   whether the proposal is flagged as non-trivial
 * @see ../docs/30_Classification/CLASSIFICATION.md
 */
public record ProposalClassified(
    ProposalId proposalId,
    String classification,
    String layer,
    boolean isNonTrivial
) implements ProposalEvent { }
