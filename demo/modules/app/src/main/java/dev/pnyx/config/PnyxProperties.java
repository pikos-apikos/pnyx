package dev.pnyx.config;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for the Pnyx prototype.
 * <p>
 * Controls public storage path, network identifier, skill-panel selection
 * parameters, and other runtime parameters. Per {@code ../docs/70_Bootstrap/PROTOTYPE_PLAN.md},
 * these properties configure the prototype's deployment environment.
 * <p>
 * The {@code skill-panel} nested block configures AI-driven panel selection:
 * the available role pool and the maximum panel size cap.
 *
 * @see ../docs/70_Bootstrap/PROTOTYPE_PLAN.md
 * @see ../docs/60_Skills/PANEL_SELECTION.md
 */
@Getter
@Setter
@Component
@ConfigurationProperties("pnyx")
public class PnyxProperties {

    private String publicStoragePath;
    private String promptPath;
    private String network;
    private SkillPanel skillPanel = new SkillPanel();

    /**
     * Nested configuration for the AI skill-panel selection.
     */
    @Getter
    @Setter
    public static class SkillPanel {
        private List<String> availableSkillRoles = List.of();
        private int maxPanelSize = 4;
    }
}
