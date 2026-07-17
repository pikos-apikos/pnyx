package dev.pnyx.core.api;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import dev.pnyx.core.domain.proposal.ProposalId;

/**
 * Driving port for running and observing skill-panel deliberation.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/PANEL_SELECTION.md}, deliberation assembles a skill panel,
 * executes reviews, produces an evidence packet, and tracks progress through the review
 * lifecycle.
 *
 * @see ../docs/80_Runtime/API_SPEC.md
 * @see ../docs/20_Protocol_Core/PANEL_SELECTION.md
 * @see ../docs/60_Skills/SKILLS.md
 */
public interface DeliberationApi {

    /**
     * Public evidence packet assembled from skill-panel reviews.
     */
    record PacketView(ProposalId proposalId, String packetHash,
                      List<ReviewView> reviews, String status) { }

    /**
     * Individual reviewer output included in an evidence packet.
     */
    record ReviewView(String role, String summary, List<String> findings,
                      double confidence, String modelName) { }

    /**
     * Aggregate progress snapshot for all reviewers working on a proposal.
     */
    record ProgressView(ProposalId proposalId, String status,
                        Map<String, ReviewProgressView> reviews) { }

    /**
     * Progress snapshot for a single reviewer role.
     */
    record ReviewProgressView(String role, String status, String currentStep,
                              String detail, String startedAt) { }

    /**
     * Starts skill-panel deliberation for a proposal.
     *
     * @param proposalId the proposal to deliberate on
     */
    void runPanel(ProposalId proposalId);

    /**
     * Returns the latest known deliberation progress, if any.
     *
     * @param proposalId the proposal to query
     * @return progress snapshot, or empty if none started
     */
    Optional<ProgressView> getProgress(ProposalId proposalId);
}
