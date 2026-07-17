package dev.pnyx.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pnyx.core.api.ValidationApi;
import dev.pnyx.core.domain.proposal.ProposalId;
import dev.pnyx.core.spi.EventStoreSpi;
import dev.pnyx.core.spi.ValidationExecutorSpi;
import dev.pnyx.core.spi.ValidationExecutorSpi.ValidationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implements {@link dev.pnyx.core.api.ValidationApi} — proposal validation orchestration.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/PROTOCOL.md §4}, validation is the first gate in the proposal
 * lifecycle. This service attempts AI-driven validation first, falling back to rule-based static
 * validation per {@link dev.pnyx.infrastructure.validation.StaticValidationExecutor}.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 * @see ../docs/60_Skills/SKILLS.md
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationService implements ValidationApi {

    private final EventStoreSpi eventStore;
    private final ObjectMapper objectMapper;
    private final List<ValidationExecutorSpi> executors;

    @Override
    public ValidationResultView validate(ProposalId proposalId) {
        var events = eventStore.readStream(proposalId.value());
        if (events.isEmpty()) {
            throw new IllegalArgumentException("Proposal not found: " + proposalId);
        }

        String payload = events.getFirst().eventPayload();
        String title = extractField(payload, "title");
        String problem = extractField(payload, "problem");
        String action = extractField(payload, "proposedAction");

        ValidationResult result = null;
        for (ValidationExecutorSpi executor : executors) {
            try {
                result = executor.validate(proposalId, title, problem, action);
                break;
            } catch (Exception e) {
                log.warn("Validation failed with executor {}: {}", executor.getClass().getSimpleName(), e.getMessage());
            }
        }
        
        if (result == null) {
            throw new IllegalStateException("All validation executors failed");
        }
        
        return new ValidationResultView(
            result.isDiscussable(),
            String.join("\n", result.clarifyingQuestions()),
            null,
            null,
            null
        );
    }

    private String extractField(String json, String field) {
        try {
            var node = objectMapper.readTree(json);
            var value = node.get(field);
            return value != null ? value.asText() : null;
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
