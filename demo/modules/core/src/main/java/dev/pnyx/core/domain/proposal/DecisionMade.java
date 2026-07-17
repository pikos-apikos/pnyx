package dev.pnyx.core.domain.proposal;

import java.util.UUID;

/**
 * Event recorded when a decision is rendered on a proposal.
 * <p>
 * A decision represents the outcome of the deliberation process and produces a public
 * decision artifact. Per {@code ../docs/20_Protocol_Core/PROTOCOL.md}, a decision transitions
 * the proposal to execution or closure.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 * @see ../docs/90_Information/PUBLIC_STORAGE_MODEL.md
 */
public record DecisionMade(
  ProposalId proposalId,
  String outcome
) implements ProposalEvent {}
