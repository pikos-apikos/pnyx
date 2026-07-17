package dev.pnyx.core.spi;

import dev.pnyx.core.domain.skill.AiReview;
import dev.pnyx.core.domain.skill.ProgressCallback;
import dev.pnyx.core.domain.skill.SkillReviewerRole;
import dev.pnyx.core.domain.skill.SkillTool;
import dev.pnyx.core.domain.proposal.ProposalEvent;
import java.util.List;

/**
 * Driven port for invoking AI or mock skill reviewers.
 * <p>
 * Per {@code ../docs/60_Skills/EXECUTOR_MODEL.md}, a skill executor receives a proposal event and
 * a reviewer role, executes available tools, and produces a structured {@link dev.pnyx.core.domain.skill.AiReview}.
 * The executor abstracts over AI model providers and mock/stub implementations.
 *
 * @see ../docs/60_Skills/EXECUTOR_MODEL.md
 * @see ../docs/60_Skills/SKILLS.md
 */
public interface SkillExecutorSpi {

    AiReview review(ProposalEvent proposal, SkillReviewerRole role,
                    List<SkillTool> tools, ProgressCallback callback);

    default AiReview review(ProposalEvent proposal, SkillReviewerRole role) {
        return review(proposal, role, List.of(), null);
    }
}
