package dev.pnyx.infrastructure.skill;

import dev.pnyx.core.domain.skill.SkillReviewerRole;
import dev.pnyx.core.spi.SkillPromptProviderSpi;
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
 * Loads skill-panel review prompts from YAML configuration.
 * <p>
 * Per {@code ../docs/60_Skills/SKILLS.md §4}, each skill role has a system prompt that
 * defines its reasoning approach, evaluation criteria, and output schema. Prompts are loaded
 * from {@code data/prompts/skill-panel.yml} and hot-reloaded on access.
 *
 * @see ../docs/60_Skills/SKILLS.md
 * @see ../docs/60_Skills/EXECUTOR_MODEL.md
 */
@Component
@RequiredArgsConstructor
public class SkillPrompts implements SkillPromptProviderSpi {

    private static final String PROMPT_FILE = "skill-panel.yml";

    private final PnyxProperties properties;

    @PostConstruct
    public void validatePrompts() {
        loadPrompts();
    }

    @Override
    public String systemPrompt(SkillReviewerRole role) {
        return loadPrompts().systemPrompt(role);
    }

    @Override
    public String userPrompt(String title, String problem, String proposedAction) {
        return loadPrompts().userPrompt()
            .replace("{{title}}", title)
            .replace("{{problem}}", problem)
            .replace("{{proposedAction}}", proposedAction != null ? proposedAction : "(not yet specified)");
    }

    private SkillPromptDocument loadPrompts() {
        Path promptFile = Path.of(properties.getPromptPath(), PROMPT_FILE);
        if (!Files.isRegularFile(promptFile)) {
            throw new IllegalStateException("Prompt file is missing: " + promptFile);
        }

        try (InputStream input = Files.newInputStream(promptFile)) {
            Object loaded = new Yaml().load(input);
            if (!(loaded instanceof Map<?, ?> root)) {
                throw new IllegalStateException("Prompt file must contain a YAML object: " + promptFile);
            }
            return SkillPromptDocument.from(root, promptFile);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read prompt file: " + promptFile, e);
        }
    }

    private record SkillPromptDocument(Map<String, String> systemPrompts, String userPrompt) {
        private static SkillPromptDocument from(Map<?, ?> root, Path promptFile) {
            Object systemPrompts = root.get("system-prompts");
            if (!(systemPrompts instanceof Map<?, ?> systemPromptMap)) {
                throw new IllegalStateException("Prompt file must define system-prompts: " + promptFile);
            }

            Map<String, String> prompts = systemPromptMap.entrySet().stream()
                .collect(java.util.stream.Collectors.toMap(
                    entry -> String.valueOf(entry.getKey()),
                    entry -> String.valueOf(entry.getValue())
                ));

            String userPrompt = requiredText(root.get("user-prompt"), "user-prompt", promptFile);
            SkillReviewerRole.panelRoles().forEach(role ->
                requiredText(prompts.get(role.id()), "system-prompts." + role.id(), promptFile));
            return new SkillPromptDocument(prompts, userPrompt);
        }

        private String systemPrompt(SkillReviewerRole role) {
            return systemPrompts.get(role.id());
        }

        private static String requiredText(Object value, String key, Path promptFile) {
            if (!(value instanceof String text) || text.isBlank()) {
                throw new IllegalStateException("Prompt entry is missing or blank: " + key + " in " + promptFile);
            }
            return text;
        }
    }
}
