package dev.pnyx.infrastructure.skill;

import dev.pnyx.core.domain.skill.SkillReviewerRole;

/**
 * Configuration for skill-panel review roles.
 * <p>
 * Exposes available reviewer roles for template rendering and selection prompts.
 * Per {@code ../docs/60_Skills/SKILLS.md §5.1}, roles map to canonical skill classes
 * that define the panel's analytical coverage.
 *
 * @see ../docs/60_Skills/SKILLS.md
 */
public record SkillReviewConfig(
    SkillReviewerRole role,
    int maxIterations,
    boolean enabledTools) {

    /**
     * Creates a {@code SkillReviewConfig} with defaults for the given role.
     *
     * @param role the reviewer role to configure
     * @return a new config with role-specific defaults (max 5 iterations, tools enabled)
     */
    public static SkillReviewConfig forRole(SkillReviewerRole role) {
        return switch (role) {
            case LEGAL_REVIEWER, ECONOMIC_REVIEWER, TECHNICAL_REVIEWER,
                 SOCIAL_REVIEWER, RISK_REVIEWER,
                 ANTI_CAPTURE_REVIEWER, ADVERSARIAL_CRITIC_REVIEWER ->
                new SkillReviewConfig(role, 5, true);
        };
    }
}