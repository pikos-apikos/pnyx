package dev.pnyx.infrastructure.skill;

import dev.pnyx.core.domain.skill.SkillReviewerRole;
import dev.pnyx.config.PnyxProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SkillPromptsTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldLoadUpdatedPromptFileWithoutRestart() throws IOException {
        writePromptFile("Initial legal prompt", "Initial user {{title}}");
        SkillPrompts prompts = new SkillPrompts(properties());

        assertThat(prompts.systemPrompt(SkillReviewerRole.LEGAL_REVIEWER))
            .isEqualTo("Initial legal prompt");
        assertThat(prompts.userPrompt("Title A", "Problem A", "Action A"))
            .isEqualTo("Initial user Title A");

        writePromptFile("Updated legal prompt", "Updated user {{problem}} {{proposedAction}}");

        assertThat(prompts.systemPrompt(SkillReviewerRole.LEGAL_REVIEWER))
            .isEqualTo("Updated legal prompt");
        assertThat(prompts.userPrompt("Title B", "Problem B", "Action B"))
            .isEqualTo("Updated user Problem B Action B");
    }

    @Test
    void shouldRejectMissingPromptFile() {
        SkillPrompts prompts = new SkillPrompts(properties());

        assertThatThrownBy(prompts::validatePrompts)
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Prompt file is missing");
    }

    @Test
    void shouldRejectBlankRequiredPrompt() throws IOException {
        writePromptFile("", "User {{title}}");
        SkillPrompts prompts = new SkillPrompts(properties());

        assertThatThrownBy(prompts::validatePrompts)
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("system-prompts.legal-reviewer");
    }

    private PnyxProperties properties() {
        PnyxProperties properties = new PnyxProperties();
        properties.setPromptPath(tempDir.toString());
        return properties;
    }

    private void writePromptFile(String legalPrompt, String userPrompt) throws IOException {
        Files.writeString(tempDir.resolve("skill-panel.yml"), """
            system-prompts:
              legal-reviewer: |-
                %s
              economic-reviewer: economic prompt
              social-reviewer: social prompt
              technical-reviewer: technical prompt
              risk-reviewer: risk prompt
              anti-capture-reviewer: anti-capture prompt
              adversarial-critic-reviewer: adversarial-critic prompt
            user-prompt: |-
              %s
            """.formatted(legalPrompt.indent(4).stripTrailing(), userPrompt));
    }
}