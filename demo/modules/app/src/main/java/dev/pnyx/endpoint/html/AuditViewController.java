package dev.pnyx.endpoint.html;

import dev.pnyx.core.api.AuditApi;
import dev.pnyx.core.domain.proposal.ProposalId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * Handles Thymeleaf audit trail inspection screens.
 *
 * @see ../docs/80_Runtime/API_SPEC.md
 * @see ../docs/90_Information/AUDIT_LOG.md
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/audit")
public class AuditViewController {

    private final AuditApi auditApi;

    /**
     * Renders an audit lookup page or a stream's hash verification entries.
     *
     * @param streamId optional proposal UUID to look up
     * @param model    Spring MVC model
     * @return the audit template name
     */
    @GetMapping
    public String index(@RequestParam(required = false) UUID streamId, Model model) {
        model.addAttribute("streamId", streamId);
        if (streamId != null) {
            model.addAttribute("entries", auditApi.stream(new ProposalId(streamId)));
        }
        return "audit";
    }
}
