package dev.pnyx.core.domain.panel;

import dev.pnyx.core.domain.proposal.ProposalId;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * The Panel aggregate root.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/PANEL_SELECTION.md}, a panel is a locked set of skill
 * assignments bound to a specific proposal and epoch.
 *
 * @see ../docs/20_Protocol_Core/PANEL_SELECTION.md
 */
public class Panel {
    private final UUID id;
    private final ProposalId proposalRef;
    private final PanelSpec panelSpec;
    private boolean locked;
    private final List<PanelEvent> pendingEvents = new ArrayList<>();

    public Panel(UUID id, ProposalId proposalRef, PanelSpec panelSpec) {
        this.id = id;
        this.proposalRef = proposalRef;
        this.panelSpec = panelSpec;
        this.locked = false;
        
        this.pendingEvents.add(new PanelSelected(id, proposalRef, Instant.now()));
    }

    /**
     * Locks the panel, preventing further modifications.
     *
     * @throws IllegalStateException if already locked
     */
    public void lock() {
        if (this.locked) {
            throw new IllegalStateException("Panel is already locked");
        }
        this.locked = true;
        this.pendingEvents.add(new PanelLocked(id, proposalRef, panelSpec.requiredSkillClasses(), Instant.now()));
    }

    public List<PanelEvent> uncommittedEvents() {
        return Collections.unmodifiableList(pendingEvents);
    }
    
    public UUID panelId() { return id; }
    public ProposalId proposalId() { return proposalRef; }
    public PanelSpec spec() { return panelSpec; }
    public boolean isLocked() { return locked; }
}
