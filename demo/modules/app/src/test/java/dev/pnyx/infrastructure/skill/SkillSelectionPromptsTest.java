package dev.pnyx.infrastructure.skill;

import dev.pnyx.config.PnyxProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SkillSelectionPromptsTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldLoadAndSubstituteSkillSelectionPrompts() throws IOException {
        writePromptFile("You are a proposal triage system.",
            "User: {{title}} {{problem}} {{proposedAction}} Skills: {{availableSkills}}");
        var prompts = new SkillSelectionPrompts(properties());

        String system = prompts.systemPrompt();
        assertThat(system).isEqualTo("You are a proposal triage system.");

        String user = prompts.userPrompt("Test Title", "Test Problem",
            "Test Action", "[{\"id\":\"legal-reviewer\"}]");
        assertThat(user).contains("Test Title");
        assertThat(user).contains("legal-reviewer");
        assertThat(user).contains("Test Action");
    }

    @Test
    void shouldRejectMissingPromptFile() {
        var prompts = new SkillSelectionPrompts(properties());

        assertThatThrownBy(prompts::validatePrompts)
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("missing");
    }

    @Test
    void shouldRejectBlankSystemPrompt() throws IOException {
        writePromptFile("", "User: {{title}}");
        var prompts = new SkillSelectionPrompts(properties());

        assertThatThrownBy(prompts::validatePrompts)
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("blank");
    }

    @Test
    void shouldRejectBlankUserPrompt() throws IOException {
        writePromptFile("System prompt", "");
        var prompts = new SkillSelectionPrompts(properties());

        assertThatThrownBy(prompts::validatePrompts)
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("blank");
    }

    private PnyxProperties properties() {
        var props = new PnyxProperties();
        props.setPromptPath(tempDir.toString());
        return props;
    }

    private void writePromptFile(String system, String user) throws IOException {
        Files.writeString(tempDir.resolve("skill-selection.yml"), """
            system: |-
              %s
            user: |-
              %s
            """.formatted(system, user));
    }
}
