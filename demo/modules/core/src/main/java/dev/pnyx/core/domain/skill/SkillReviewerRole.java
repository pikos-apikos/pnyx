package dev.pnyx.core.domain.skill;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Supported skill-panel reviewer roles.
 * <p>
 * The mandatory baseline classes for non-trivial proposals are defined in
 * {@code SKILLS.md §5.1} and {@code PANEL_SELECTION.md §5.2}:
 * <ul>
 *   <li>{@code rights_constitutional}</li>
 *   <li>{@code implementation_feasibility}</li>
 *   <li>{@code economic_resource}</li>
 *   <li>{@code anti_capture_audit}</li>
 *   <li>{@code adversarial_critique}</li>
 * </ul>
 * The legacy role IDs (legal-reviewer, economic-reviewer, etc.) are retained for backward
 * compatibility with prompt files and persisted data, but each role now also carries the
 * canonical skill class identifier from the specification.
 */
public enum SkillReviewerRole {
    LEGAL_REVIEWER("legal-reviewer", "rights_constitutional",
        "Legal, rights, and constitutional compliance analysis"),
    ECONOMIC_REVIEWER("economic-reviewer", "economic_resource",
        "Economic impact, cost-benefit, and resource analysis"),
    SOCIAL_REVIEWER("social-reviewer", "local_impact",
        "Social impact and community effects analysis"),
    TECHNICAL_REVIEWER("technical-reviewer", "implementation_feasibility",
        "Technical feasibility and implementation analysis"),
    RISK_REVIEWER("risk-reviewer", "evidence_quality",
        "Risk assessment and evidence-quality analysis"),
    ANTI_CAPTURE_REVIEWER("anti-capture-reviewer", "anti_capture_audit",
        "Anti-capture, dependency, and power-concentration analysis"),
    ADVERSARIAL_CRITIC_REVIEWER("adversarial-critic-reviewer", "adversarial_critique",
        "Adversarial critique — strongest counter-case and hidden-premise stress-testing");

    private final String roleId;
    private final String skillClassName;
    private final String descriptionText;

    SkillReviewerRole(String roleId, String skillClassName, String descriptionText) {
        this.roleId = roleId;
        this.skillClassName = skillClassName;
        this.descriptionText = descriptionText;
    }

    public String id() {
        return roleId;
    }

    /**
     * Returns the canonical skill class identifier from the specification
     * (e.g. {@code rights_constitutional}, {@code anti_capture_audit}).
     *
     * @return the skill class identifier
     */
    public String skillClass() {
        return skillClassName;
    }

    public String description() {
        return descriptionText;
    }

    /**
     * Resolves a role by its string ID, throwing if not found.
     *
     * @param id the role identifier string
     * @return the matching role
     * @throws IllegalArgumentException if no role matches
     */
    public static SkillReviewerRole fromId(String id) {
        return Arrays.stream(values())
            .filter(role -> role.roleId.equals(id))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown skill reviewer role: " + id));
    }

    /**
     * Finds a role by its string ID, returning empty if not found.
     *
     * @param id the role identifier string
     * @return the matching role, or empty
     */
    public static Optional<SkillReviewerRole> findById(String id) {
        return Arrays.stream(values())
            .filter(role -> role.roleId.equals(id))
            .findFirst();
    }

    /**
     * Returns all defined panel roles. The active subset is selected at runtime by
     * {@code AiSkillSelectorAdapter} based on the proposal, constrained by the
     * configured available pool and max panel size.
     *
     * @return all available panel roles
     */
    public static List<SkillReviewerRole> panelRoles() {
        return List.of(values());
    }

    /**
     * Returns the mandatory baseline roles required for every non-trivial proposal
     * (SKILLS.md §5.1, PANEL_SELECTION.md §5.2).
     *
     * @return the mandatory role list
     */
    public static List<SkillReviewerRole> mandatoryRoles() {
        return List.of(
            LEGAL_REVIEWER,
            TECHNICAL_REVIEWER,
            ECONOMIC_REVIEWER,
            ANTI_CAPTURE_REVIEWER,
            ADVERSARIAL_CRITIC_REVIEWER);
    }
}