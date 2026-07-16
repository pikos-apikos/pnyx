package dev.pnyx.infrastructure.publicstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.pnyx.core.common.ExecutionPathType;
import dev.pnyx.core.common.ExecutionTaskStatus;
import dev.pnyx.core.common.PublicObjectType;
import dev.pnyx.core.common.PublicSchemaVersion;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SchemaValidationTest {

    private static final String TYPE = "type";
    private static final String SCHEMA = "schema";
    private static final String CREATED_AT = "created_at";
    private static final String PROPOSAL_ID = "proposal_id";
    private static final String BODY = "body";
    private static final String TITLE = "title";
    private static final String UNCHECKED = "unchecked";
    private static final String STATUS = "status";
    private static final String DECISION_ID = "decision_id";
    private static final String APPROVED = "approved";

    private final ObjectMapper mapper = new ObjectMapper()
        .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    private final CanonicalJsonSerializer serializer = new CanonicalJsonSerializer();

    @Test
    void shouldValidateCompleteProposalSchema() throws Exception {
        var json = mapper.writeValueAsString(validProposal());
        var obj = mapper.readValue(json, Map.class);

        assertThat(obj).containsKeys(TYPE, SCHEMA, CREATED_AT, PROPOSAL_ID, BODY);
        assertThat(obj.get(TYPE)).isEqualTo("proposal");
        assertThat(obj.get(SCHEMA)).isEqualTo("proposal.v1");

        @SuppressWarnings(UNCHECKED)
        var body = (Map<String, Object>) obj.get(BODY);
        assertThat(body).containsKeys(TITLE, "problem", "proposed_action");
    }

    @Test
    void shouldValidateCompleteDecisionSchema() throws Exception {
        var json = mapper.writeValueAsString(validDecision());
        var obj = mapper.readValue(json, Map.class);

        assertThat(obj).containsKeys(TYPE, SCHEMA, DECISION_ID,
            PROPOSAL_ID, "outcome", "rationale", "evidence_packet_hash", "skill_panel_votes");
        assertThat(obj.get(TYPE)).isEqualTo("decision");
        assertThat(obj.get(SCHEMA)).isEqualTo("decision.v1");

        @SuppressWarnings(UNCHECKED)
        var votes = (Map<String, Object>) obj.get("skill_panel_votes");
        assertThat(votes).containsKeys("legal-reviewer", "economic-reviewer",
            "social-reviewer", "technical-reviewer", "risk-reviewer");
    }

    @Test
    void shouldValidateCompleteImplementationForkSchema() throws Exception {
        var json = mapper.writeValueAsString(validFork());
        var obj = mapper.readValue(json, Map.class);

        assertThat(obj).containsKeys(TYPE, SCHEMA, "fork_id", DECISION_ID, "paths", "tasks");
        assertThat(obj.get(TYPE)).isEqualTo("implementation_fork");
        assertThat(obj.get(SCHEMA)).isEqualTo("implementation_fork.v1");

        @SuppressWarnings(UNCHECKED)
        var paths = (List<Map<String, Object>>) obj.get("paths");
        assertThat(paths).hasSize(2);
        assertThat(paths.getFirst()).containsKeys("pathType", TITLE, STATUS);

        @SuppressWarnings(UNCHECKED)
        var tasks = (List<Map<String, Object>>) obj.get("tasks");
        assertThat(tasks).hasSize(2);
        assertThat(tasks.getFirst()).containsKeys(
            "task_id", TITLE, STATUS, "owner", "due_date", "summary", "evidence_links");
    }

    @Test
    void shouldRejectInvalidSchemaEnum() {
        assertThatThrownBy(() -> {
            PublicSchemaVersion.valueOf("INVALID_V1");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldCanonicalizeAndRoundTripProposal() throws Exception {
        var json = mapper.writeValueAsString(validProposal());
        String canonical = serializer.canonicalize(json);

        var obj = mapper.readValue(canonical, Map.class);
        assertThat(obj).containsKeys(TYPE, SCHEMA, CREATED_AT, PROPOSAL_ID, BODY);

        String back = mapper.writeValueAsString(obj);
        var obj2 = mapper.readValue(back, Map.class);
        assertThat(obj2.get(PROPOSAL_ID)).isEqualTo(obj.get(PROPOSAL_ID));
    }

    @Test
    void shouldVerifyKnownSchemaAndObjectTypeEnums() {
        assertThat(PublicSchemaVersion.PROPOSAL_V1.value()).isEqualTo("proposal.v1");
        assertThat(PublicSchemaVersion.PROPOSAL_V1.objectType()).isEqualTo(PublicObjectType.PROPOSAL);
        assertThat(PublicSchemaVersion.DECISION_V1.value()).isEqualTo("decision.v1");
        assertThat(PublicSchemaVersion.DECISION_V1.objectType()).isEqualTo(PublicObjectType.DECISION);
        assertThat(PublicSchemaVersion.IMPLEMENTATION_FORK_V1.value()).isEqualTo("implementation_fork.v1");
        assertThat(PublicSchemaVersion.IMPLEMENTATION_FORK_V1.objectType())
            .isEqualTo(PublicObjectType.IMPLEMENTATION_FORK);
    }

    @Test
    void shouldEnforceValidPathTypes() {
        assertThat(ExecutionPathType.INSTITUTIONAL_ACTION.value()).isEqualTo("institutional_action");
        assertThat(ExecutionPathType.PUBLIC_INTEREST_VENTURE.value()).isEqualTo("public_interest_venture");
        assertThat(ExecutionPathType.COMMUNITY_ACTION.value()).isEqualTo("community_action");
    }

    @Test
    void shouldEnforceValidTaskStatuses() {
        assertThat(ExecutionTaskStatus.PLANNED.value()).isEqualTo("planned");
        assertThat(ExecutionTaskStatus.IN_PROGRESS.value()).isEqualTo("in_progress");
        assertThat(ExecutionTaskStatus.BLOCKED.value()).isEqualTo("blocked");
        assertThat(ExecutionTaskStatus.COMPLETED.value()).isEqualTo("completed");
    }

    private static Map<String, Object> validProposal() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TITLE, "Replace diesel buses with electric buses");
        body.put("problem", "City school bus fleet produces 300 tons of CO2 annually.");
        body.put("proposed_action", "Allocate $2.1M for electric buses and charging stations.");

        Map<String, Object> p = new LinkedHashMap<>();
        p.put(TYPE, "proposal");
        p.put(SCHEMA, "proposal.v1");
        p.put(CREATED_AT, "2026-05-15T10:00:00Z");
        p.put(PROPOSAL_ID, "00000000-0000-0000-0000-000000000001");
        p.put(BODY, body);
        return p;
    }

    private static Map<String, Object> validDecision() {
        Map<String, Object> votes = new LinkedHashMap<>();
        votes.put("legal-reviewer", APPROVED);
        votes.put("economic-reviewer", APPROVED);
        votes.put("social-reviewer", APPROVED);
        votes.put("technical-reviewer", APPROVED);
        votes.put("risk-reviewer", "approved_with_notes");

        Map<String, Object> d = new LinkedHashMap<>();
        d.put(TYPE, "decision");
        d.put(SCHEMA, "decision.v1");
        d.put(CREATED_AT, "2026-05-20T14:00:00Z");
        d.put(DECISION_ID, UUID.randomUUID().toString());
        d.put(PROPOSAL_ID, UUID.randomUUID().toString());
        d.put("outcome", "approved");
        d.put("rationale", "Cross-sector support with viable funding.");
        d.put("evidence_packet_hash",
            "sha256:0000000000000000000000000000000000000000000000000000000000000001");
        d.put("skill_panel_votes", votes);
        return d;
    }

    private static Map<String, Object> validFork() {
        List<Map<String, Object>> paths = List.of(
            Map.of("pathType", "institutional_action",
                TITLE, "Procurement", STATUS, "in_progress"),
            Map.of("pathType", "community_action",
                TITLE, "Outreach", STATUS, "planned"));

        List<Map<String, Object>> tasks = List.of(
            Map.of("task_id", UUID.randomUUID().toString(),
                TITLE, "Issue RFP", STATUS, "completed",
                "owner", "procurement-dept", "due_date", "2026-06-01",
                "summary", "RFP published.", "evidence_links", List.of()),
            Map.of("task_id", UUID.randomUUID().toString(),
                TITLE, "Install chargers", STATUS, "in_progress",
                "owner", "facilities-dept", "due_date", "2026-07-15",
                "summary", "Site surveyed.", "evidence_links", List.of()));

        Map<String, Object> f = new LinkedHashMap<>();
        f.put(TYPE, "implementation_fork");
        f.put(SCHEMA, "implementation_fork.v1");
        f.put(CREATED_AT, "2026-05-21T09:00:00Z");
        f.put("fork_id", UUID.randomUUID().toString());
        f.put(DECISION_ID, UUID.randomUUID().toString());
        f.put("paths", paths);
        f.put("tasks", tasks);
        return f;
    }
}
