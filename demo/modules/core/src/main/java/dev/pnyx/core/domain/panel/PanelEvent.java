package dev.pnyx.core.domain.panel;

import dev.pnyx.core.domain.proposal.ProposalId;
import java.time.Instant;
import java.util.UUID;

/**
 * Sealed interface for all panel domain events.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/PANEL_SELECTION.md}, a panel is a locked set of skill
 * assignments bound to a specific proposal and epoch. Every panel state change produces
 * an event implementing this interface.
 *
 * @see ../docs/20_Protocol_Core/PANEL_SELECTION.md
 * @see ../docs/80_Runtime/EVENT_MODEL.md
 */
public sealed interface PanelEvent permits PanelSelected, PanelLocked {
    
    /**
     * The panel (stream) this event belongs to.
     *
     * @return the panel UUID
     */
    UUID panelId();
    
    /**
     * The proposal this panel is assembled for.
     *
     * @return the proposal identifier
     */
    ProposalId proposalId();
}
