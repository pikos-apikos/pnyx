package dev.pnyx.endpoint;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Provides stable entry points for the operator-facing Protocol Workbench.
 * <p>
 * The citizen and operator surfaces intentionally reuse the same public read models.
 * The workbench route selects the protocol-heavy presentation without creating a
 * second civic lifecycle or a second source of truth.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 * @see ../docs/80_Runtime/API_SPEC.md
 * @see ../docs/90_Information/PUBLIC_STORAGE_MODEL.md
 */
@Controller
public class WorkbenchController {

    /**
     * Opens the workbench version of the proposal dashboard.
     *
     * @return redirect to the dashboard with the workbench surface selected
     */
    @GetMapping("/workbench")
    public String workbenchHome() {
        return "redirect:/?surface=workbench";
    }

    /**
     * Opens the protocol-heavy view for one proposal while preserving the existing
     * proposal controller and read-model assembly.
     *
     * @param proposalId proposal aggregate identifier
     * @return redirect to the existing proposal detail with workbench presentation
     */
    @GetMapping("/workbench/proposals/{proposalId}")
    public String proposalWorkbench(@PathVariable UUID proposalId) {
        return "redirect:/proposals/" + proposalId + "?view=workbench";
    }
}
