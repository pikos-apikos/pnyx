package dev.pnyx.endpoint.html;

import dev.pnyx.core.api.DeliberationApi;
import dev.pnyx.core.api.ProposalApi;
import dev.pnyx.core.domain.participation.AuditDepth;
import dev.pnyx.core.domain.participation.ParticipationMode;
import dev.pnyx.core.domain.proposal.ProposalId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

/**
 * Handles Thymeleaf and HTMX proposal screens.
 *
 * @see ../docs/80_Runtime/API_SPEC.md
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/proposals")
public class ProposalViewController {

    private static final String REDIRECT_PROPOSALS = "redirect:/proposals/";
    private static final String WEB_UI_ACTOR = "web-ui";
    private static final String PROPOSAL_ID_PARAM = "proposalId";
    private static final String ACTION_PARAM = "action";
    private static final String PROPOSALS_PATH = "/proposals/";

    private final ProposalApi proposalApi;
    private final DeliberationApi deliberationApi;

    /**
     * Returns the proposal creation form fragment.
     *
     * @return the proposal form template name
     */
    @GetMapping("/new")
    public String newForm() {
        return "proposal-form";
    }

    /**
     * Submits form input and redirects to the proposal detail page.
     * The proposed action is optional per PROTOCOL.md invariant 7.9 (problem-before-solution gate).
     *
     * @param title           proposal title
     * @param problem         problem statement
     * @param proposedAction  optional proposed action
     * @return redirect URL to the proposal detail page
     */
    @PostMapping
    public String submit(@RequestParam String title,
                         @RequestParam String problem,
                         @RequestParam(required = false) String proposedAction) {
        var cmd = new ProposalApi.SubmitProposalCommand(title, problem,
            proposedAction, "did:pnyx:anonymous");
        var result = proposalApi.submit(cmd);
        return REDIRECT_PROPOSALS + result.id().value();
    }

    /**
     * Renders the detail page for an existing proposal, including all v0.3 artifacts.
     *
     * @param id    the proposal UUID
     * @param model Spring MVC model
     * @return the proposal detail template name, or redirect if not found
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable UUID id, Model model) {
        var proposal = proposalApi.findById(new ProposalId(id));
        if (proposal.isEmpty()) { return "redirect:/"; }
        model.addAttribute("proposal", proposal.get());

        // Load v0.3 participation artifacts
        var plan = proposalApi.getParticipationPlan(new ProposalId(id));
        plan.ifPresent(p -> model.addAttribute("participationPlan", p));

        var audit = proposalApi.getParticipationAudit(new ProposalId(id));
        audit.ifPresent(a -> model.addAttribute("participationAudit", a));

        // Load execution artifacts
        var mandate = proposalApi.getExecutionMandate(new ProposalId(id));
        mandate.ifPresent(m -> model.addAttribute("executionMandate", m));

        // Load monitoring & learning artifacts
        var monitoring = proposalApi.getMonitoringRecord(new ProposalId(id));
        monitoring.ifPresent(m -> model.addAttribute("monitoringRecord", m));

        var learning = proposalApi.getLearningArtifact(new ProposalId(id));
        learning.ifPresent(l -> model.addAttribute("learningArtifact", l));

        // Computed flags for conditional display
        String state = proposal.get().state();
        boolean showParticipation = !"DRAFT".equals(state) && !"SUBMITTED".equals(state);
        boolean showAuditForm = Set.of("PUBLIC_REVIEW_OPEN", "CHALLENGED", "REVIEW_REPAIR").contains(state)
            && model.getAttribute("participationAudit") == null;
        boolean showReceiptForm = showParticipation;
        boolean showExecution = Set.of("ROUTED", "EXECUTION_AUTHORIZED", "EXECUTION_ACTIVE",
            "EXECUTION_PAUSED", "EXECUTION_COMPLETED", "MONITORING_ACTIVE", "LEARNING_PUBLISHED",
            "POST_REVIEW_OPEN", "CLOSED").contains(state);
        boolean showMonitoring = Set.of("EXECUTION_COMPLETED", "MONITORING_ACTIVE",
            "LEARNING_PUBLISHED", "POST_REVIEW_OPEN", "CLOSED").contains(state);
        boolean showLearning = Set.of("MONITORING_ACTIVE", "LEARNING_PUBLISHED",
            "POST_REVIEW_OPEN", "CLOSED").contains(state);
        boolean showDeliberation = Set.of("PANEL_LOCKED", "EVIDENCE_ASSEMBLY", "DELIBERATION_ACTIVE",
            "PACKET_DRAFTING", "PACKET_PUBLISHED").contains(state);
        boolean showActions = Set.of("READY_FOR_DECISION", "DECISION_OPEN").contains(state);

        model.addAttribute("showParticipation", showParticipation);
        model.addAttribute("showAuditForm", showAuditForm);
        model.addAttribute("showReceiptForm", showReceiptForm);
        model.addAttribute("showExecution", showExecution);
        model.addAttribute("showMonitoring", showMonitoring);
        model.addAttribute("showLearning", showLearning);
        model.addAttribute("showDeliberation", showDeliberation);
        model.addAttribute("showActions", showActions);

        return "proposal-detail";
    }

    /**
     * Returns a lightweight status fragment for polling deliberation state.
     *
     * @param id the proposal UUID
     * @return HTML fragment with review progress
     */
    @GetMapping("/{id}/deliberation-status")
    @ResponseBody
    public String deliberationStatus(@PathVariable UUID id) {
        var progress = deliberationApi.getProgress(new ProposalId(id));
        if (progress.isEmpty()) { return "<p style='color:#9e9e9e'>No review in progress.</p>"; }

        var p = progress.get();
        int estimatedSize = 140 + p.reviews().size() * 120;
        StringBuilder html = new StringBuilder(estimatedSize);
        html.append("<h3>AI Review Panel Progress</h3>");

        for (var entry : p.reviews().entrySet()) {
            var r = entry.getValue();
            String icon = switch (r.status()) {
                case "PENDING" -> "○";
                case "RUNNING", "TOOL_CALL", "ANALYZING" -> "<span class='spinner'></span>";
                case "COMPLETED" -> "✓";
                case "FAILED" -> "✗";
                default -> "?";
            };
            String detail = r.currentStep() != null ? " — " + r.currentStep() : "";
            String roleCell = icon + " " + r.role();
            html.append("<div class='review-row'><span class='review-role'>")
                .append(roleCell)
                .append("</span><span class='review-status status-")
                .append(r.status()).append("'>")
                .append(r.status()).append(detail).append("</span></div>");
        }

        return html.toString();
    }

    // ─── v0.3 Participation Artifact Handlers ───

    /**
     * Creates a Participation Plan for the proposal.
     * Transitions from PARTICIPATION_DESIGN_PENDING to PANEL_SELECTION_PENDING.
     */
    @PostMapping("/{id}/participation/plan")
    public String createParticipationPlan(@PathVariable UUID id,
                                           @RequestParam List<String> selectedModes,
                                           @RequestParam(required = false) List<String> expectedBarriers,
                                           @RequestParam(required = false) List<String> missingPerspectives,
                                           @RequestParam(required = false) String compensationRules,
                                           @RequestParam(required = false) List<String> accessibilityMeasures,
                                           @RequestParam String auditCriteria) {
        var modes = selectedModes.stream()
            .map(String::toUpperCase)
            .map(m -> {
                try { return ParticipationMode.valueOf(m); }
                catch (IllegalArgumentException e) { return ParticipationMode.OPEN; }
            })
            .toList();

        var cmd = new ProposalApi.CreateParticipationPlanCommand(
            new ProposalId(id), modes,
            expectedBarriers != null ? expectedBarriers : List.of(),
            missingPerspectives != null ? missingPerspectives : List.of(),
            compensationRules != null ? compensationRules : "",
            accessibilityMeasures != null ? accessibilityMeasures : List.of(),
            auditCriteria,
            WEB_UI_ACTOR);
        proposalApi.createParticipationPlan(cmd);
        return REDIRECT_PROPOSALS + id;
    }

    /**
     * Issues a Participation Audit for the proposal.
     * Satisfies the PUBLIC_REVIEW_OPEN → READY_FOR_DECISION guard.
     */
    @PostMapping("/{id}/participation/audit")
    public String issueParticipationAudit(@PathVariable UUID id,
                                           @RequestParam String depth,
                                           @RequestParam String auditorRef,
                                           @RequestParam(required = false) String auditorConflictDeclaration,
                                           @RequestParam String findings,
                                           @RequestParam(required = false) String limitations) {
        var auditDepth = AuditDepth.valueOf(depth.toUpperCase(Locale.ROOT));
        var cmd = new ProposalApi.IssueParticipationAuditCommand(
            new ProposalId(id), auditDepth, auditorRef,
            auditorConflictDeclaration != null ? auditorConflictDeclaration : "",
            findings != null ? findings : "",
            limitations != null ? limitations : "",
            WEB_UI_ACTOR);
        proposalApi.issueParticipationAudit(cmd);
        return REDIRECT_PROPOSALS + id;
    }

    /**
     * Issues a Civic Receipt for a participant action on the proposal.
     */
    @PostMapping("/{id}/participation/receipt")
    public String issueCivicReceipt(@PathVariable UUID id,
                                     @RequestParam String participantRef,
                                     @RequestParam String actionType) {
        var cmd = new ProposalApi.IssueCivicReceiptCommand(
            new ProposalId(id), participantRef, actionType, WEB_UI_ACTOR);
        proposalApi.issueCivicReceipt(cmd);
        return REDIRECT_PROPOSALS + id;
    }

    // ─── Execution Mandate Handler ───

    /**
     * Issues an Execution Mandate for the proposal.
     * Transitions from ROUTED to EXECUTION_AUTHORIZED.
     */
    @PostMapping("/{id}/execution/mandate")
    public String issueExecutionMandate(@PathVariable UUID id,
                                         @RequestParam String authorizedActor,
                                         @RequestParam(required = false) List<String> permittedActions,
                                         @RequestParam(required = false) List<String> prohibitedActions,
                                         @RequestParam(required = false) String resourceAllocation,
                                         @RequestParam(required = false) List<String> successCriteria,
                                         @RequestParam(required = false) List<String> failureCriteria,
                                         @RequestParam(required = false) List<String> monitoringObligations,
                                         @RequestParam(required = false) List<String> rollbackConditions) {
        var cmd = new ProposalApi.IssueExecutionMandateCommand(
            new ProposalId(id), authorizedActor,
            permittedActions != null ? permittedActions : List.of(),
            prohibitedActions != null ? prohibitedActions : List.of(),
            resourceAllocation != null ? resourceAllocation : "",
            successCriteria != null ? successCriteria : List.of(),
            failureCriteria != null ? failureCriteria : List.of(),
            monitoringObligations != null ? monitoringObligations : List.of(),
            rollbackConditions != null ? rollbackConditions : List.of(),
            WEB_UI_ACTOR);
        proposalApi.issueExecutionMandate(cmd);
        return REDIRECT_PROPOSALS + id;
    }

    // ─── Monitoring Handler ───

    /**
     * Starts monitoring execution outcomes for the proposal.
     * Transitions from EXECUTION_COMPLETED to MONITORING_ACTIVE.
     */
    @PostMapping("/{id}/monitoring/start")
    public String startMonitoring(@PathVariable UUID id,
                                   @RequestParam String responsibleParty,
                                   @RequestParam String monitoringStartDate,
                                   @RequestParam String monitoringEndDate,
                                   @RequestParam(required = false) List<String> milestones,
                                   @RequestParam(required = false) List<String> metrics,
                                   @RequestParam String reportingFrequency) {
        var startDate = Instant.parse(monitoringStartDate + "T00:00:00Z");
        var endDate = Instant.parse(monitoringEndDate + "T00:00:00Z");
        var cmd = new ProposalApi.StartMonitoringCommand(
            new ProposalId(id), startDate, endDate,
            milestones != null ? milestones : List.of(),
            metrics != null ? metrics : List.of(),
            reportingFrequency, responsibleParty, WEB_UI_ACTOR);
        proposalApi.startMonitoring(cmd);
        return REDIRECT_PROPOSALS + id;
    }

    // ─── Learning Handler ───

    /**
     * Publishes a Learning Artifact for the proposal.
     * Transitions from MONITORING_ACTIVE to LEARNING_PUBLISHED.
     */
    @PostMapping("/{id}/learning/publish")
    public String publishLearning(@PathVariable UUID id,
                                   @RequestParam String publishedBy,
                                   @RequestParam(required = false) List<String> lessonsLearned,
                                   @RequestParam(required = false) List<String> successFactors,
                                   @RequestParam(required = false) List<String> failureFactors,
                                   @RequestParam(required = false) List<String> recommendations,
                                   @RequestParam(required = false) List<String> applicableContexts) {
        var cmd = new ProposalApi.PublishLearningCommand(
            new ProposalId(id),
            lessonsLearned != null ? lessonsLearned : List.of(),
            successFactors != null ? successFactors : List.of(),
            failureFactors != null ? failureFactors : List.of(),
            recommendations != null ? recommendations : List.of(),
            applicableContexts != null ? applicableContexts : List.of(),
            publishedBy, WEB_UI_ACTOR);
        proposalApi.publishLearning(cmd);
        return REDIRECT_PROPOSALS + id;
    }

    // ─── Fragment Endpoints (HTMX) ───

    /**
     * Returns the Participation Plan form fragment (HTML, no layout).
     */
    @GetMapping("/{id}/fragments/participation-plan")
    public String participationPlanFragment(@PathVariable UUID id, Model model) {
        model.addAttribute(PROPOSAL_ID_PARAM, id);
        model.addAttribute(ACTION_PARAM, PROPOSALS_PATH + id + "/participation/plan");
        return "fragments/artifacts :: participation-plan-form";
    }

    /**
     * Returns the Participation Audit form fragment (HTML, no layout).
     */
    @GetMapping("/{id}/fragments/participation-audit")
    public String participationAuditFragment(@PathVariable UUID id, Model model) {
        model.addAttribute(PROPOSAL_ID_PARAM, id);
        model.addAttribute(ACTION_PARAM, PROPOSALS_PATH + id + "/participation/audit");
        return "fragments/artifacts :: participation-audit-form";
    }

    /**
     * Returns the Execution Mandate form fragment (HTML, no layout).
     */
    @GetMapping("/{id}/fragments/execution-mandate")
    public String executionMandateFragment(@PathVariable UUID id, Model model) {
        model.addAttribute(PROPOSAL_ID_PARAM, id);
        model.addAttribute(ACTION_PARAM, PROPOSALS_PATH + id + "/execution/mandate");
        return "fragments/artifacts :: execution-mandate-form";
    }

    /**
     * Returns the Monitoring form fragment (HTML, no layout).
     */
    @GetMapping("/{id}/fragments/monitoring")
    public String monitoringFragment(@PathVariable UUID id, Model model) {
        model.addAttribute(PROPOSAL_ID_PARAM, id);
        model.addAttribute(ACTION_PARAM, PROPOSALS_PATH + id + "/monitoring/start");
        return "fragments/artifacts :: monitoring-form";
    }

    /**
     * Returns the Learning Artifact form fragment (HTML, no layout).
     */
    @GetMapping("/{id}/fragments/learning")
    public String learningFragment(@PathVariable UUID id, Model model) {
        model.addAttribute(PROPOSAL_ID_PARAM, id);
        model.addAttribute(ACTION_PARAM, PROPOSALS_PATH + id + "/learning/publish");
        return "fragments/artifacts :: learning-form";
    }
}
