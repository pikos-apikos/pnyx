package dev.pnyx.infrastructure.skill;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

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

/**
 * Mock AI skill executor that returns deterministic reviews without calling a real LLM.
 * Activated when no {@link SpringAiSkillExecutorAdapter} bean is present (no API key).
 *
 * @see ../docs/60_Skills/SKILLS.md
 */
@Component
@ConditionalOnMissingBean(SpringAiSkillExecutorAdapter.class)
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

    // Simulate agentic loop: think → call tool → get result → think → conclude
    if (callback != null) {
      callback.onTurnStart(role.id(), 1);
      callback.onStatus(role.id(), "Analyzing proposal: " + p.title());
    }

    // Simulate tool calls if available
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

    // Build mock review with tool call context
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
