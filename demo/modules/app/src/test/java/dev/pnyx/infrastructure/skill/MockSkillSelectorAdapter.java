package dev.pnyx.infrastructure.skill;

import dev.pnyx.core.domain.skill.SkillRoleDescriptor;
import dev.pnyx.core.domain.skill.SkillSelectionResult;
import dev.pnyx.core.spi.SkillSelectionSpi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mock skill selector that uses all available roles for review panel composition.
 * Activated when no {@link AiSkillSelectorAdapter} bean is present (no API key).
 */
@Component
@ConditionalOnMissingBean(AiSkillSelectorAdapter.class)
public class MockSkillSelectorAdapter implements SkillSelectionSpi {

    @Override
    public SkillSelectionResult selectSkills(String title, String problem,
                                              String proposedAction,
                                              List<SkillRoleDescriptor> availableRoles) {
        List<String> allIds = availableRoles.stream()
            .map(SkillRoleDescriptor::id)
            .toList();
        return new SkillSelectionResult(allIds, List.of(),
            "Mock: using all available roles");
    }
}
