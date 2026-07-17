package dev.pnyx.core.domain.panel;

import java.util.List;
import java.util.Objects;

/**
 * Specification for a panel's composition and rules.
 *
 * @see ../docs/20_Protocol_Core/PANEL_SELECTION.md
 */
public record PanelSpec(
    List<String> requiredSkillClasses,
    int minQuorum,
    List<String> allowedExecutorTypes
) {
    /**
     * Validates required fields and constraints.
     */
    public PanelSpec {
        Objects.requireNonNull(requiredSkillClasses, "requiredSkillClasses must not be null");
        if (requiredSkillClasses.isEmpty()) {
            throw new IllegalArgumentException("requiredSkillClasses must not be empty");
        }
        if (minQuorum <= 0) {
            throw new IllegalArgumentException("minQuorum must be greater than 0");
        }
        Objects.requireNonNull(allowedExecutorTypes, "allowedExecutorTypes must not be null");
    }
}
