package dev.pnyx.infrastructure.validation;

import dev.pnyx.core.domain.proposal.ProposalId;
import dev.pnyx.core.spi.ValidationExecutorSpi;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Rule-based fallback validation executor.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/PROTOCOL.md §4}, validation is the first gate a proposal
 * must pass. This executor applies static rules (field length checks, spam detection, blank
 * detection) when the AI-driven executor is unavailable or fails.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
@Component
@Order(2)
public class StaticValidationExecutor implements ValidationExecutorSpi {

    @Override
    public ValidationResult validate(ProposalId proposalId, String title, String problem, String action) {
        List<String> missingFields = new ArrayList<>();
        List<String> clarifyingQuestions = new ArrayList<>();
        List<String> flags = new ArrayList<>();

        if (title == null || title.length() < 10) { missingFields.add("title_too_short"); }
        if (problem == null || problem.length() < 30) { missingFields.add("problem_too_short"); }
        // proposedAction is optional per PROTOCOL.md invariant 7.9 (problem-before-solution gate)
        if (action != null && action.length() < 20) { missingFields.add("action_too_short"); }

        if (title != null && (title.contains("!!!") || title.contains("$$$"))) {
            flags.add("potential_spam");
        }
        if (problem != null && !problem.toLowerCase(Locale.ROOT).contains("cost")) {
            clarifyingQuestions.add("What is the estimated cost or budget?");
        }
        if (action != null && !action.toLowerCase(Locale.ROOT).contains("responsible")) {
            clarifyingQuestions.add("Which authority or department is responsible?");
        }

        return new ValidationResult(proposalId, missingFields.isEmpty(),
            List.copyOf(missingFields), List.copyOf(clarifyingQuestions),
            List.copyOf(flags));
    }
}
