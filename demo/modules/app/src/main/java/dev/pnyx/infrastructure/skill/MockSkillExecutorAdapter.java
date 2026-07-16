package dev.pnyx.infrastructure.skill;

import dev.pnyx.core.domain.proposal.ProposalEvent;
import dev.pnyx.core.domain.proposal.ProposalSubmitted;
import dev.pnyx.core.domain.skill.AiReview;
import dev.pnyx.core.domain.skill.ProgressCallback;
import dev.pnyx.core.domain.skill.SkillReviewerRole;
import dev.pnyx.core.domain.skill.SkillTool;
import dev.pnyx.core.domain.skill.ToolResult;
import dev.pnyx.core.spi.SkillExecutorSpi;
import dev.pnyx.core.spi.SkillToolProviderSpi;
import dev.pnyx.infrastructure.publicstore.CanonicalJsonSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Default {@link SkillExecutorSpi} that runs mock reviews using predefined tool results.
 * <p>
 * Active when no real AI executor (e.g., {@code SpringAiSkillExecutorAdapter}) is configured.
 * Simulates agentic tool-calling review loops for all skill roles with realistic mock data.
 *
 * @see ../docs/60_Skills/SKILLS.md
 */
@Component
@ConditionalOnMissingBean(name = "springAiSkillExecutorAdapter")
@RequiredArgsConstructor
public class MockSkillExecutorAdapter implements SkillExecutorSpi {

    private final SkillToolProviderSpi toolProvider;
    private final CanonicalJsonSerializer serializer;

    private record ToolQuery(String query) {}

    @Override
    public AiReview review(ProposalEvent event, SkillReviewerRole role,
                           List<SkillTool> tools, ProgressCallback callback) {
        if (!(event instanceof ProposalSubmitted p)) {
            throw new IllegalArgumentException("Can only review ProposalSubmitted events");
        }

        List<SkillTool> roleTools = tools.isEmpty() ? toolProvider.toolsForRole(role) : tools;

        var reviewId = p.proposalId();

        if (callback != null) {
            callback.onTurnStart(role.id(), 1);
            callback.onStatus(role.id(), "Analyzing proposal: " + p.title());
        }

        int toolCalls = 0;
        for (var tool : roleTools) {
            toolCalls++;
            String argumentsJson = serializer.canonicalize(new ToolQuery(p.title()));
            if (callback != null) {
                callback.onToolCallStart(role.id(), tool.name(), argumentsJson);
            }

            ToolResult result = toolProvider.executeTool(role, tool.name(), argumentsJson);

            if (callback != null) {
                callback.onToolCallResult(role.id(), tool.name(), result);
                callback.onStatus(role.id(), "Processed " + tool.name() + " result");
            }
        }

        if (callback != null) {
            callback.onStatus(role.id(), "Synthesizing review from " + toolCalls + " tool calls");
        }

        String toolContext = toolCalls > 0 ? " (used " + toolCalls + " tools)" : "";
        var review = AiReview.create(
            reviewId, role.id(),
            "Mock " + role.id() + " analysis of: " + p.title() + toolContext,
            List.of("Finding: structural analysis complete", "Finding: domain knowledge applied"),
            List.of("Risk: incomplete data may affect confidence"),
            List.of("Cost estimates not provided in proposal"),
            List.of("Standard governance assumptions apply"),
            List.of("https://example.gov/reference-" + role.id()),
            toolCalls);

        if (callback != null) {
            callback.onComplete(role.id(), review);
        }

        return review;
    }
}
