package dev.pnyx.core.spi;

/**
 * Driven port for loading validation prompts from YAML.
 * <p>
 * Validation prompts define the criteria for proposal completeness and discussability checks
 * defined in {@code ../docs/20_Protocol_Core/PROTOCOL.md §4}.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public interface ValidationPromptProviderSpi {
    String systemPrompt();
    String userPrompt(String title, String problem, String proposedAction);
}
