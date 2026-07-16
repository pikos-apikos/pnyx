package dev.pnyx.infrastructure.skill;

import dev.pnyx.config.PnyxProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidationPromptsTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldLoadUpdatedPromptFileWithoutRestart() throws IOException {
        writePromptFile("Initial system", "Initial user {{title}}");
        ValidationPrompts prompts = new ValidationPrompts(properties());

        assertThat(prompts.systemPrompt()).isEqualTo("Initial system");
        assertThat(prompts.userPrompt("Title A", "Problem A", "Action A"))
            .isEqualTo("Initial user Title A");

        writePromptFile("Updated system", "Updated user {{problem}} {{proposedAction}}");

        assertThat(prompts.systemPrompt()).isEqualTo("Updated system");
        assertThat(prompts.userPrompt("Title B", "Problem B", "Action B"))
            .isEqualTo("Updated user Problem B Action B");
    }

    @Test
    void shouldRejectMissingPromptFile() {
        ValidationPrompts prompts = new ValidationPrompts(properties());

        assertThatThrownBy(prompts::validatePrompts)
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Prompt file is missing");
    }

    @Test
    void shouldRejectBlankRequiredPrompt() throws IOException {
        writePromptFile("", "User {{title}}");
        ValidationPrompts prompts = new ValidationPrompts(properties());

        assertThatThrownBy(prompts::validatePrompts)
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("system-prompt");
    }

    private PnyxProperties properties() {
        PnyxProperties properties = new PnyxProperties();
        properties.setPromptPath(tempDir.toString());
        return properties;
    }

    private void writePromptFile(String systemPrompt, String userPrompt) throws IOException {
        Files.writeString(tempDir.resolve("validation.yml"), """
            system-prompt: |-
              %s
            user-prompt: |-
              %s
            """.formatted(systemPrompt, userPrompt));
    }
}