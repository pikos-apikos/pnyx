package dev.pnyx.endpoint.html;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.pnyx.core.api.ExecutionApi;
import dev.pnyx.core.domain.proposal.DecisionId;

import lombok.RequiredArgsConstructor;

/**
 * Handles Thymeleaf implementation tracking screens.
 *
 * @see ../docs/80_Runtime/API_SPEC.md
 * @see ../docs/20_Protocol_Core/ROUTING.md
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/execution")
public class ExecutionViewController {

    private final ExecutionApi executionApi;

    /**
     * Renders implementation paths for a decision identifier.
     *
     * @param decisionId the decision UUID
     * @param model      Spring MVC model
     * @return the execution tracker template name
     */
    @GetMapping("/{decisionId}")
    public String tracker(@PathVariable UUID decisionId, Model model) {
        model.addAttribute("fork", executionApi.findFork(new DecisionId(decisionId)));
        return "execution-tracker";
    }
}
