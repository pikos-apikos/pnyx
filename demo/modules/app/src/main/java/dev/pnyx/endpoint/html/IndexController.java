package dev.pnyx.endpoint.html;

import dev.pnyx.core.api.ProposalApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Serves the landing page for the HTML interface.
 * <p>
 * Lists recent proposals via {@link ProposalApi}, which reads the public artifact
 * index. Proposals are returned newest-first.
 *
 * @see ../docs/70_Bootstrap/PROTOTYPE_PROFILE.md
 */
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final ProposalApi proposalApi;

    /**
     * Renders the landing page with the most recent proposals.
     *
     * @param model Spring MVC model
     * @return the index template name
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("proposals", proposalApi.listRecent());
        return "index";
    }
}
