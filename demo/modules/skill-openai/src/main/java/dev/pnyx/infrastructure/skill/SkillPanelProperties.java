package dev.pnyx.infrastructure.skill;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for the AI skill-panel selection subsystem.
 * <p>
 * Binds to the {@code pnyx.skill-panel} prefix. The maximum panel size caps
 * how many skill roles the AI may select when assembling a review panel.
 *
 * @see ../docs/20_Protocol_Core/PANEL_SELECTION.md
 */
@Getter
@Setter
@Component
@ConfigurationProperties("pnyx.skill-panel")
public class SkillPanelProperties {

    /** Maximum number of skill roles the AI may select. Default: 4. */
    private int maxPanelSize = 4;
}
