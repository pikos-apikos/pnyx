package dev.pnyx.infrastructure.skill;

import dev.pnyx.core.domain.proposal.ProposalEvent;
import dev.pnyx.core.domain.proposal.ProposalSubmitted;
import dev.pnyx.core.domain.skill.AiReview;
import dev.pnyx.core.domain.skill.ProgressCallback;
import dev.pnyx.core.domain.skill.SkillReviewerRole;
import dev.pnyx.core.domain.skill.SkillTool;
import dev.pnyx.core.domain.skill.ToolResult;
import dev.pnyx.core.spi.SkillExecutorSpi;
import dev.pnyx.core.spi.SkillPromptProviderSpi;
import dev.pnyx.core.spi.SkillToolProviderSpi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

/**
 * Spring AI implementation of {@link dev.pnyx.core.spi.SkillExecutorSpi} using OpenAI.
 * <p>
 * Per {@code ../docs/60_Skills/EXECUTOR_MODEL.md}, skill executors receive a proposal event and
 * a reviewer role, execute available tools, and produce a structured review. This adapter
 * uses Spring AI's {@code ChatClient.Builder} to invoke OpenAI models with role-specific
 * prompts.
 *
 * @see ../docs/60_Skills/EXECUTOR_MODEL.md
 * @see ../docs/60_Skills/SKILLS.md
 */
@Component
@ConditionalOnProperty(name = "pnyx.ai.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class SpringAiSkillExecutorAdapter implements SkillExecutorSpi {

    private final ChatClient.Builder chatClientBuilder;
    private final SkillPromptProviderSpi prompts;
    private final SkillToolProviderSpi toolProvider;
    private final ObjectMapper objectMapper;

    private record ToolQuery(String query) {}

    @Override
    public AiReview review(ProposalEvent event, SkillReviewerRole role,
                           List<SkillTool> tools, ProgressCallback callback) {
        if (!(event instanceof ProposalSubmitted p)) {
            throw new IllegalArgumentException(
                "Can only review ProposalSubmitted events, got: " + event.getClass().getSimpleName());
        }

        List<SkillTool> roleTools = tools.isEmpty() ? toolProvider.toolsForRole(role) : tools;
        SkillReviewConfig config = SkillReviewConfig.forRole(role);

        if (callback != null) { callback.onTurnStart(role.id(), 1); }

        // Simulate tool calls if tools are available
        StringBuilder toolResultsContext = new StringBuilder();
        if (!roleTools.isEmpty() && config.enabledTools()) {
            for (var tool : roleTools) {
                if (callback != null) { callback.onToolCallStart(role.id(), tool.name(),
                    tool.description()); }

                ToolResult result = toolProvider.executeTool(role, tool.name(),
                    serializeToolQuery(new ToolQuery(p.title())));
                toolResultsContext.append("\n### ").append(tool.name())
                    .append(" result:\n").append(result.content()).append('\n');

                if (callback != null) { callback.onToolCallResult(role.id(), tool.name(), result); }
            }
            if (callback != null) { callback.onStatus(role.id(),
                "Processed " + roleTools.size() + " tool results"); }
        }

        // Build prompt with tool results as context
        String systemPrompt = prompts.systemPrompt(role);
        String userPrompt = prompts.userPrompt(p.title(), p.problem(), p.proposedAction());

        if (!toolResultsContext.isEmpty()) {
            userPrompt += "\n\nThe following tool results are available for your analysis. "
                + "Use them to inform your structured review.\n" + toolResultsContext;
        }

        if (callback != null) { callback.onStatus(role.id(), "Calling model..."); }

        String response = chatClientBuilder.build().prompt()
            .system(systemPrompt)
            .user(userPrompt)
            .call()
            .content();

        AiReview review = parseReview(p, role, response);
        if (callback != null) { callback.onComplete(role.id(), review); }
        return review;
    }

    private AiReview parseReview(ProposalSubmitted p, SkillReviewerRole role, String response) {
        if (response == null) {
            return AiReview.create(p.proposalId(), role.id(),
                "Review could not be completed",
                List.of(), List.of(), List.of(), List.of(), List.of(),
                0);
        }
        var findings = extractList(response, "findings");
        var risks = extractList(response, "risks");
        var missingInfo = extractList(response, "missing_information");
        var citations = extractList(response, "citations");
        return AiReview.create(p.proposalId(), role.id(),
            extractSection(response, "summary"),
            findings,
            risks,
            missingInfo,
            extractList(response, "assumptions"),
            citations,
            0);
    }

    private String extractSection(String response, String section) {
        int start = response.toLowerCase(Locale.ROOT).indexOf("\"" + section + "\"");
        if (start == -1) { return response.substring(0, Math.min(100, response.length())); }
        return response.substring(Math.min(start + section.length() + 4, response.length()),
            Math.min(start + 500, response.length())).trim();
    }

    private List<String> extractList(String response, String section) {
        return List.of(extractSection(response, section));
    }

    private String serializeToolQuery(ToolQuery query) {
        try {
            return objectMapper.writeValueAsString(query);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Could not serialize skill tool query", e);
        }
    }
}
