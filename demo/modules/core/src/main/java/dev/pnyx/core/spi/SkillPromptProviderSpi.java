package dev.pnyx.core.spi;

import dev.pnyx.core.domain.skill.SkillReviewerRole;

/**
 * Driven port that supplies hot-reloadable prompt text for skill executors.
 * <p>
 * Per {@code ../docs/60_Skills/SKILLS.md §4}, prompts are versioned, hot-reloaded configuration
 * files that define system instructions for each skill role.
 *
 * @see ../docs/60_Skills/SKILLS.md
 */
public interface SkillPromptProviderSpi {

    /**
     * Returns the system prompt configured for a reviewer role.
     *
     * @param role the reviewer role to look up
     * @return the system prompt string
     */
    String systemPrompt(SkillReviewerRole role);

    /**
     * Returns the user prompt for a proposal review request.
     *
     * @param title          proposal title
     * @param problem        proposal problem statement
     * @param proposedAction proposal action description
     * @return the user prompt string
     */
    String userPrompt(String title, String problem, String proposedAction);
}