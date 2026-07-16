package dev.pnyx.core.domain.proposal;

import java.util.UUID;

/**
 * Event recorded when a proposal reaches its terminal closed state.
 * <p>
 * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §3}, the CLOSED state is a terminal absorbing
 * state in the proposal lifecycle. No further transitions are permitted.
 *
 * @see ../docs/80_Runtime/STATE_MACHINE.md
 */
public record ProposalClosed(
    ProposalId proposalId
) implements ProposalEvent { }
