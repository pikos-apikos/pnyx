package dev.pnyx.infrastructure.skill;

import dev.pnyx.core.spi.SkillSelectionPromptProviderSpi;
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
 * Loads skill-selection prompts from YAML configuration.
 * <p>
 * Selection prompts guide the AI-driven panel composition defined in
 * {@code ../docs/20_Protocol_Core/PANEL_SELECTION.md §5}. The prompt describes available roles,
 * mandatory skill classes, and selection criteria.
 *
 * @see ../docs/20_Protocol_Core/PANEL_SELECTION.md
 * @see ../docs/60_Skills/SKILLS.md
 */
@Component
@RequiredArgsConstructor
public class SkillSelectionPrompts implements SkillSelectionPromptProviderSpi {

    private static final String PROMPT_FILE = "skill-selection.yml";

    private final PnyxProperties properties;

    @PostConstruct
    public void validatePrompts() {
        loadPrompts();
    }

    @Override
    public String systemPrompt() {
        return loadPrompts().system;
    }

    @Override
    public String userPrompt(String title, String problem, String proposedAction,
                             String availableSkillsJson) {
        return loadPrompts().user
            .replace("{{title}}", title)
            .replace("{{problem}}", problem)
            .replace("{{proposedAction}}", proposedAction != null ? proposedAction : "(not yet specified)")
            .replace("{{availableSkills}}", availableSkillsJson);
    }

    private PromptDocument loadPrompts() {
        Path promptFile = Path.of(properties.getPromptPath(), PROMPT_FILE);
        if (!Files.isRegularFile(promptFile)) {
            throw new IllegalStateException("Prompt file is missing: " + promptFile);
        }

        try (InputStream input = Files.newInputStream(promptFile)) {
            Object loaded = new Yaml().load(input);
            if (!(loaded instanceof Map<?, ?> root)) {
                throw new IllegalStateException(
                    "Prompt file must contain a YAML object: " + promptFile);
            }
            return new PromptDocument(
                requiredText(root.get("system"), "system", promptFile),
                requiredText(root.get("user"), "user", promptFile));
        } catch (IOException e) {
            throw new IllegalStateException(
                "Could not read prompt file: " + promptFile, e);
        }
    }

    private static String requiredText(Object value, String key, Path promptFile) {
        if (!(value instanceof String text) || text.isBlank()) {
            throw new IllegalStateException(
                "Prompt entry is missing or blank: " + key + " in " + promptFile);
        }
        return text;
    }

    private record PromptDocument(String system, String user) { }
}
