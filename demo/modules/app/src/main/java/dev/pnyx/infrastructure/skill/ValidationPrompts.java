package dev.pnyx.infrastructure.skill;

import dev.pnyx.core.spi.ValidationPromptProviderSpi;
import dev.pnyx.config.PnyxProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * Loads validation prompts from YAML configuration.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/PROTOCOL.md §4}, validation prompts define the criteria
 * for assessing proposal completeness, discussability, and problem-solution independence.
 * Prompts are hot-reloaded on access and fail at startup if missing or blank.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 * @see ../docs/60_Skills/SKILLS.md
 */
@Component
@RequiredArgsConstructor
public class ValidationPrompts implements ValidationPromptProviderSpi {

    private static final String PROMPT_FILE = "validation.yml";

    private final PnyxProperties properties;

    @PostConstruct
    public void validatePrompts() {
        loadPrompts();
    }

    @Override
    public String systemPrompt() {
        return loadPrompts().systemPrompt();
    }

    @Override
    public String userPrompt(String title, String problem, String proposedAction) {
        return loadPrompts().userPrompt()
            .replace("{{title}}", title)
            .replace("{{problem}}", problem)
            .replace("{{proposedAction}}", proposedAction);
    }

    private ValidationPromptDocument loadPrompts() {
        Path promptFile = Path.of(properties.getPromptPath(), PROMPT_FILE);
        if (!Files.isRegularFile(promptFile)) {
            throw new IllegalStateException("Prompt file is missing: " + promptFile);
        }

        try (InputStream input = Files.newInputStream(promptFile)) {
            Object loaded = new Yaml().load(input);
            if (!(loaded instanceof Map<?, ?> root)) {
                throw new IllegalStateException("Prompt file must contain a YAML object: " + promptFile);
            }
            return ValidationPromptDocument.from(root, promptFile);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read prompt file: " + promptFile, e);
        }
    }

    private record ValidationPromptDocument(String systemPrompt, String userPrompt) {
        private static ValidationPromptDocument from(Map<?, ?> root, Path promptFile) {
            return new ValidationPromptDocument(
                requiredText(root.get("system-prompt"), "system-prompt", promptFile),
                requiredText(root.get("user-prompt"), "user-prompt", promptFile)
            );
        }

        private static String requiredText(Object value, String key, Path promptFile) {
            if (!(value instanceof String text) || text.isBlank()) {
                throw new IllegalStateException("Prompt entry is missing or blank: " + key + " in " + promptFile);
            }
            return text;
        }
    }
}
