package dev.pnyx.core.spi;

/**
 * Driven port that supplies hot-reloadable prompt text for skill selection.
 * <p>
 * Selection prompts guide the AI-driven panel composition process defined in
 * {@code ../docs/20_Protocol_Core/PANEL_SELECTION.md}.
 *
 * @see ../docs/20_Protocol_Core/PANEL_SELECTION.md
 */
public interface SkillSelectionPromptProviderSpi {

    String systemPrompt();

    String userPrompt(String title, String problem, String proposedAction,
                      String availableSkillsJson);
}
