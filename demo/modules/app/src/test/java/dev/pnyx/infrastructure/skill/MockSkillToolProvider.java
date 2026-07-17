package dev.pnyx.infrastructure.skill;

import dev.pnyx.core.domain.skill.SkillReviewerRole;
import dev.pnyx.core.domain.skill.SkillTool;
import dev.pnyx.core.domain.skill.ToolResult;
import dev.pnyx.core.spi.SkillToolProviderSpi;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Mock tool provider that returns predefined tools and results for skill-panel testing.
 * Used when no real AI executor is configured.
 */
@Component
public class MockSkillToolProvider implements SkillToolProviderSpi {

    private static final Map<SkillReviewerRole, List<SkillTool>> ROLE_TOOLS = Map.of(
        SkillReviewerRole.LEGAL_REVIEWER, List.of(
            new SkillTool("lookup_legislation",
                "Search for relevant legislation by query. Returns matching laws with summaries.",
                """
                {"type":"object","properties":{"query":{"type":"string","description":"Search query for legislation"},"jurisdiction":{"type":"string","description":"Jurisdiction code e.g. EU, US, UK"}},"required":["query"]}"""),
            new SkillTool("validate_jurisdiction",
                "Check which authority has jurisdiction over the proposal domain.",
                """
                {"type":"object","properties":{"domain":{"type":"string","description":"Domain e.g. public_schools, energy, transport"},"location":{"type":"string","description":"Location description"}},"required":["domain"]}""")
        ),
        SkillReviewerRole.ECONOMIC_REVIEWER, List.of(
            new SkillTool("estimate_cost",
                "Estimate the financial cost of a proposed action.",
                """
                {"type":"object","properties":{"action":{"type":"string","description":"Description of the proposed action"},"scale":{"type":"string","description":"Scale e.g. municipal, regional, national"}},"required":["action","scale"]}"""),
            new SkillTool("analyze_budget_impact",
                "Analyze budget impact including funding sources and economic trade-offs.",
                """
                {"type":"object","properties":{"action":{"type":"string","description":"Description of the proposed action"},"budget":{"type":"string","description":"Budget context"}},"required":["action"]}""")
        ),
        SkillReviewerRole.TECHNICAL_REVIEWER, List.of(
            new SkillTool("assess_feasibility",
                "Assess technical feasibility of an implementation plan.",
                """
                {"type":"object","properties":{"plan":{"type":"string","description":"Implementation plan description"},"context":{"type":"string","description":"Technical context"}},"required":["plan"]}"""),
            new SkillTool("check_infrastructure",
                "Check existing infrastructure requirements and gaps.",
                """
                {"type":"object","properties":{"domain":{"type":"string","description":"Domain e.g. buildings, energy, IT"},"location":{"type":"string","description":"Location description"}},"required":["domain"]}""")
        ),
        SkillReviewerRole.RISK_REVIEWER, List.of(
            new SkillTool("detect_abuse_vector",
                "Identify potential abuse vectors in a proposal.",
                """
                {"type":"object","properties":{"proposal":{"type":"string","description":"Proposal summary"},"context":{"type":"string","description":"Governance context"}},"required":["proposal"]}"""),
            new SkillTool("assess_irreversibility",
                "Assess how reversible a decision would be once implemented.",
                """
                {"type":"object","properties":{"action":{"type":"string","description":"Description of the action"},"resources":{"type":"string","description":"Resources involved"}},"required":["action"]}""")
        ),
        SkillReviewerRole.SOCIAL_REVIEWER, List.of(),
        SkillReviewerRole.ANTI_CAPTURE_REVIEWER, List.of(
            new SkillTool("map_dependency_chain",
                "Map the dependency chain and identify chokepoints in a proposal.",
                """
                {"type":"object","properties":{"proposal":{"type":"string","description":"Proposal summary"},"scope":{"type":"string","description":"Scope of analysis e.g. funding, infrastructure, governance"}},"required":["proposal"]}"""),
            new SkillTool("detect_concentration_risk",
                "Detect power concentration and enclosure risks in a proposal.",
                """
                {"type":"object","properties":{"proposal":{"type":"string","description":"Proposal summary"},"actors":{"type":"string","description":"Key actors and their roles"}},"required":["proposal"]}""")
        ),
        SkillReviewerRole.ADVERSARIAL_CRITIC_REVIEWER, List.of(
            new SkillTool("build_counter_case",
                "Construct the strongest counter-case against a proposal.",
                """
                {"type":"object","properties":{"proposal":{"type":"string","description":"Proposal summary"},"assumptions":{"type":"string","description":"Key assumptions to challenge"}},"required":["proposal"]}"""),
            new SkillTool("identify_hidden_premises",
                "Identify hidden premises and unstated assumptions in a proposal.",
                """
                {"type":"object","properties":{"proposal":{"type":"string","description":"Proposal text"},"framing":{"type":"string","description":"How the proposal is framed"}},"required":["proposal"]}""")
        )
    );

    @Override
    public List<SkillTool> toolsForRole(SkillReviewerRole role) {
        return ROLE_TOOLS.getOrDefault(role, List.of());
    }

    @Override
    public ToolResult executeTool(SkillReviewerRole role, String toolName, String argumentsJson) {
        return switch (toolName) {
            case "lookup_legislation" ->
                ToolResult.success(toolName, """
                    {"provisions":[{"title":"Energy Efficiency Act 2023 §7","summary":"Mandates energy efficiency standards for public buildings including schools. Sets targets for insulation, lighting, and heating upgrades by 2030.","url":"https://example.gov/eea-2023-s7"},{"title":"Municipal Infrastructure Fund Regulations §3","summary":"Authorizes municipal governments to establish dedicated infrastructure funds. Allows earmarked funding for public school building improvements.","url":"https://example.gov/mifr-s3"}],"totalFound":2}""");
            case "validate_jurisdiction" ->
                ToolResult.success(toolName, """
                    {"jurisdiction":"Municipal Education Authority","legalBasis":"Education Act §14.b","notes":"Municipality has authority over public school building maintenance and can establish dedicated improvement funds per Local Governance Act §22"}""");
            case "estimate_cost" ->
                ToolResult.success(toolName, """
                    {"estimates":{"low":500000,"medium":1200000,"high":2500000},"currency":"EUR","breakdown":{"insulation":"40%","lighting":"25%","heating":"35%"},"confidence":0.65,"notes":"Based on average costs for 10-school municipal district"}""");
            case "analyze_budget_impact" ->
                ToolResult.success(toolName, """
                    {"fundingSources":["municipal budget allocation","energy savings reinvestment","regional infrastructure grant"],"annualBudgetImpact":{"year1":1200000,"year2":300000,"year3":150000},"roiYears":7,"notes":"Positive ROI after 7 years from energy savings. Upfront costs reduced by 30% if combined with existing renovation cycles."}""");
            case "assess_feasibility" ->
                ToolResult.success(toolName, """
                    {"rating":"MEDIUM_HIGH","factors":{"technology":"proven and widely available","expertise":"requires certified energy auditors","timeline":"6-18 months typical for 10-school district","dependencies":["contractor availability","school calendar scheduling"]},"riskLevel":"LOW","notes":"Standard energy retrofit technologies. Main risk is scheduling around school terms."}""");
            case "check_infrastructure" ->
                ToolResult.success(toolName, """
                    {"currentState":{"buildingAge":"1970s-2000s","insulationType":"minimal or degraded","lightingType":"predominantly fluorescent","heatingSystem":"gas boilers, mixed efficiency"},"gaps":["no building energy management system","no real-time consumption monitoring"],"recommendations":["Add BMS during retrofit","Install smart meters","Consider solar PV integration"]}""");
            case "detect_abuse_vector" ->
                ToolResult.success(toolName, """
                    {"vectors":[{"category":"procurement_bias","risk":"MEDIUM","description":"Contractor selection may favor connected firms. Mitigation: public tender with transparent scoring."},{"category":"scope_creep","risk":"LOW","description":"Minimal risk as scope is well-defined. Fund is earmarked for specific upgrades."},{"category":"capture_by_intermediaries","risk":"MEDIUM","description":"Energy service companies may extract excessive margins. Mitigation: fixed-price contracts with audit clauses."}],"overallRisk":"MEDIUM"}""");
            case "assess_irreversibility" ->
                ToolResult.success(toolName, """
                    {"reversibility":"HIGH","reversalCost":"LOW","lockInRisk":"LOW","notes":"Fund can be discontinued or redirected annually. Physical upgrades are long-term assets that improve regardless of future policy changes. No irreversible commitments."}""");
            case "map_dependency_chain" ->
                ToolResult.success(toolName, """
                    {"dependencies":[{"node":"municipal_budget","type":"funding","criticality":"HIGH","alternatives":["regional_grants","energy_savings_reinvestment"]},{"node":"contractor_market","type":"execution","criticality":"MEDIUM","alternatives":["in-house_maintenance_crew"]}],"chokepoints":["municipal_budget_approval"],"enclosureRisk":"LOW","notes":"No single actor controls the full chain. Budget approval is the main bottleneck but has institutional alternatives."}""");
            case "detect_concentration_risk" ->
                ToolResult.success(toolName, """
                    {"concentrationAreas":[{"area":"procurement","risk":"MEDIUM","description":"Limited number of qualified energy retrofit contractors in the region."},{"area":"funding","risk":"LOW","description":"Multiple funding sources available."}],"interpretivePowerRisk":"LOW","notes":"No single actor gains disproportionate interpretive or operational control. Main risk is contractor market thinness, mitigable through public tender."}""");
            case "build_counter_case" ->
                ToolResult.success(toolName, """
                    {"counterCase":"The proposal addresses real energy inefficiency but assumes a dedicated fund is the best mechanism. Alternatives like performance-based contracts or integrated maintenance budgets may achieve the same goals without creating a new bureaucratic entity. The fund may also create perverse incentives to prioritize visible upgrades over structural efficiency.","hiddenPremises":["dedicated_fund_is_optimal","municipal_capacity_exists","upgrades_will_translate_to_savings"],"weakestPoint":"No evidence that a dedicated fund outperforms existing budget mechanisms for this scope."}""");
            case "identify_hidden_premises" ->
                ToolResult.success(toolName, """
                    {"hiddenPremises":[{"premise":"Energy upgrades are the primary lever for cost reduction","challenge":"Behavioral and operational changes may yield faster returns"},{"premise":"Municipal management is the appropriate governance level","challenge":"Regional or school-district level may have better expertise"},{"premise":"Upfront investment will be recovered through savings","challenge":"Recovery depends on energy price stability and building lifespan"}],"framingBias":"Solution-first framing: the proposal leads with a fund mechanism rather than starting from the problem analysis.","notes":"The proposal would benefit from a problem-first restructuring before solution commitment."}""");
            default ->
                ToolResult.failure(toolName, "Tool '" + toolName + "' does not exist for role '" + role.id() + "'. Available tools: " + toolsForRole(role).stream().map(SkillTool::name).toList());
        };
    }
}
