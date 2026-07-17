package dev.pnyx.infrastructure.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pnyx.core.spi.ValidationExecutorSpi;
import dev.pnyx.core.spi.ValidationPromptProviderSpi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import dev.pnyx.core.domain.proposal.ProposalId;

/**
 * Spring AI implementation of {@link dev.pnyx.core.spi.ValidationExecutorSpi} using OpenAI.
 * <p>
 * Per {@code ../docs/60_Skills/EXECUTOR_MODEL.md}, validation executors assess proposal completeness,
 * discussability, and problem-solution independence. This adapter uses Spring AI's
 * {@code ChatClient.Builder} to invoke an OpenAI model with validation prompts from
 * {@link dev.pnyx.infrastructure.skill.ValidationPrompts}.
 *
 * @see ../docs/60_Skills/EXECUTOR_MODEL.md
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 * @see ../docs/60_Skills/SKILLS.md
 */
@Slf4j
@Component
@Order(1)
@ConditionalOnProperty(name = "pnyx.ai.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class AiValidationExecutorAdapter implements ValidationExecutorSpi {

    private final ChatClient.Builder chatClientBuilder;
    private final ValidationPromptProviderSpi prompts;
    private final ObjectMapper objectMapper;

    private record AiValidationResponse(
        boolean isDiscussable,
        List<String> missingFields,
        List<String> clarifyingQuestions,
        List<String> flags,
        double confidence,
        String reasoning
    ) { }

    @Override
    public ValidationResult validate(ProposalId proposalId, String title, String problem, String action) {
        if (title == null || problem == null) {
            return new ValidationResult(proposalId, false,
                List.of("missing_required_fields"),
                List.of("Please provide a title and problem description. A proposed action is optional (PROTOCOL.md invariant 7.9)."),
                List.of("incomplete"));
        }

        String response = chatClientBuilder.build().prompt()
            .system(prompts.systemPrompt())
            .user(prompts.userPrompt(title, problem, action))
            .call()
            .content();

        try {
            AiValidationResponse ai = objectMapper.readValue(response, AiValidationResponse.class);

            log.info("AI validation for {}: discussable={}, missing={}, questions={}, flags={}, confidence={}",
                proposalId, ai.isDiscussable(), ai.missingFields(),
                ai.clarifyingQuestions(), ai.flags(), ai.confidence());

            return new ValidationResult(
              proposalId,
              ai.isDiscussable(),
              ai.missingFields(),
              ai.clarifyingQuestions(),
              ai.flags()
            );
        } catch (JsonProcessingException e) {
            log.warn("Failed to parse AI validation response: {}", e.getMessage());
            throw new IllegalStateException("Failed to parse AI validation response", e);
        }
    }
}
