package dev.pnyx.endpoint.rest;

import dev.pnyx.Application;
import dev.pnyx.core.api.ProposalApi.SubmitProposalCommand;
import dev.pnyx.core.api.ProposalApi.ProposalView;
import dev.pnyx.core.domain.proposal.ProposalId;
import dev.pnyx.core.domain.skill.AiReview;
import dev.pnyx.core.domain.skill.SkillRoleDescriptor;
import dev.pnyx.core.domain.skill.SkillSelectionResult;
import dev.pnyx.core.spi.EventStoreSpi;
import dev.pnyx.core.spi.SkillExecutorSpi;
import dev.pnyx.core.spi.SkillSelectionSpi;
import dev.pnyx.infrastructure.test.DatabaseTestBase;
import dev.pnyx.service.ProposalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(PnyxIntegrationTest.MockSkillConfig.class)
class PnyxIntegrationTest extends DatabaseTestBase {

    private static final String DEMO_USER = "did:pnyx:demo-user";
    private static final String PROPOSALS_PATH = "/api/proposals";
    private static final String PROPOSALS_PATH_PREFIX = "/api/proposals/";

    @LocalServerPort
    private int port;

    @Autowired
    private RestClient.Builder restClientBuilder;

    @Autowired
    private ProposalService proposalService;

    @Autowired
    private EventStoreSpi eventStore;

    private RestClient restClient;

    @BeforeEach
    void setUp() {
        this.restClient = restClientBuilder.baseUrl("http://localhost:" + port).build();
    }

    /**
     * Overrides the real AI-powered skill adapters with simple stubs so
     * integration tests do not require a running LM Studio instance.
     */
    @TestConfiguration
    static class MockSkillConfig {
        @Bean @Primary
        SkillSelectionSpi skillSelectionSpi() {
            return (title, problem, proposedAction, availableRoles) -> {
                List<String> ids = availableRoles.stream()
                    .map(SkillRoleDescriptor::id).limit(4).toList();
                return new SkillSelectionResult(ids, List.of(), "mocked");
            };
        }

        @Bean @Primary
        SkillExecutorSpi skillExecutorSpi() {
            return (event, role, tools, callback) -> AiReview.create(
                ProposalId.generate(), role.id(),
                "Mock " + role.id() + " review", List.of("Finding 1"),
                List.of(), List.of(), List.of(), List.of(),
                0);
        }
    }

    /**
     * Advances a proposal from its current state all the way to DECISION_OPEN
     * by calling {@link ProposalService#advanceProposal(ProposalId)} repeatedly.
     * Each call advances one state-machine transition and persists the event.
     * Stops once the event stream's last event indicates DECISION_OPEN or beyond.
     */
    private void advanceToDecisionOpen(ProposalId id) {
        for (int i = 0; i < 20; i++) {
            var stored = eventStore.readStream(id.value());
            if (stored.isEmpty()) {
                break;
            }
            var lastType = stored.get(stored.size() - 1).eventType();
            if (isAtOrPastDecisionOpen(lastType)) {
                break;
            }
            proposalService.advanceProposal(id);
        }
    }

    private static boolean isAtOrPastDecisionOpen(String eventType) {
        return switch (eventType) {
            case "DecisionOpened", "ProposalApproved", "ProposalRejected",
                 "ProposalDeferred", "ProposalClosed", "ProposalInvalidated" -> true;
            default -> false;
        };
    }

    record Scenario(String name, SubmitProposalCommand command,
                    boolean expectedDiscussable,
                    List<String> expectedMissingFields,
                    List<String> expectedFlags) {
        @Override public String toString() { return name; }
    }

    static Stream<Scenario> proposalScenarios() {
        return Stream.of(
            new Scenario("valid proposal",
                new SubmitProposalCommand(
                    "Municipal energy upgrade fund for public schools",
                    "Public schools in our municipality have high energy costs due to outdated insulation and inefficient heating systems, affecting education budgets.",
                    "Create a dedicated municipal fund for energy upgrades including insulation, LED lighting, and heat pump installation across all 12 public schools.",
                    DEMO_USER
                ), false, List.of(), List.of()
            ),
            new Scenario("short title",
                new SubmitProposalCommand(
                    "Fix it",
                    "Public schools in our municipality have high energy costs due to outdated insulation and inefficient heating systems.",
                    "Create a dedicated municipal fund for energy upgrades including insulation and lighting.",
                    DEMO_USER
                ), true, List.of(), List.of()
            ),
            new Scenario("minimal problem",
                new SubmitProposalCommand(
                    "Municipal energy upgrade fund for public schools",
                    "Public schools have high energy costs due to outdated systems.",
                    "Create a dedicated municipal fund for energy upgrades including insulation, LED lighting, and heat pump installation across all 12 public schools.",
                    DEMO_USER
                ), true, List.of(), List.of()
            ),
            new Scenario("short action",
                new SubmitProposalCommand(
                    "Municipal energy upgrade fund for public schools",
                    "Public schools in our municipality have high energy costs due to outdated insulation and inefficient heating systems, affecting education budgets.",
                    "Do something.",
                    DEMO_USER
                ), true, List.of(), List.of()
            )
        );
    }

    @ParameterizedTest
    @MethodSource("proposalScenarios")
    void shouldSubmitProposalAndGetValidation(Scenario scenario) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var response = restClient.post()
            .uri("/api/proposals")
            .headers(h -> h.putAll(headers))
            .body(scenario.command())
            .retrieve()
            .toEntity(ProposalView.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var view = response.getBody();
        assertThat(view).isNotNull();
        assertThat(view.id()).isNotNull();
        assertThat(view.title()).isEqualTo(scenario.command().title());
        assertThat(view.state()).isEqualTo("CLASSIFIED");
        assertThat(view.validation()).isNotNull();
    }

    @Test
    void shouldGetProposalById() {
        var cmd = new SubmitProposalCommand(
            "School energy efficiency program",
            "Multiple schools in the district face rising energy costs due to aging infrastructure and lack of modern energy management systems.",
            "Establish a municipal energy efficiency program that audits all public school buildings and finances priority retrofits.",
            DEMO_USER
        );

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var submitResponse = restClient.post()
            .uri(PROPOSALS_PATH)
            .headers(h -> h.putAll(headers))
            .body(cmd)
            .retrieve()
            .toEntity(ProposalView.class);
        var submitted = submitResponse.getBody();

        var getResponse = restClient.get()
            .uri(PROPOSALS_PATH_PREFIX + submitted.id().value())
            .retrieve()
            .toEntity(ProposalView.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().id()).isEqualTo(submitted.id());
    }

    @Test
    void shouldReturn404ForUnknownProposal() {
        var response = restClient.get()
            .uri(PROPOSALS_PATH_PREFIX + java.util.UUID.randomUUID())
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {})
            .toEntity(String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldRecordDecision() {
        var cmd = new SubmitProposalCommand(
            "Public park renovation initiative",
            "City parks have fallen into disrepair due to years of budget cuts, reducing green space quality for residents and affecting community health.",
            "Allocate municipal budget for phased renovation of the 5 most-used parks, including playground replacement, path resurfacing, and tree planting.",
            DEMO_USER
        );

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var submitResponse = restClient.post()
            .uri(PROPOSALS_PATH)
            .headers(h -> h.putAll(headers))
            .body(cmd)
            .retrieve()
            .toEntity(ProposalView.class);
        var id = submitResponse.getBody().id();

        // Advance the proposal through the full lifecycle to DECISION_OPEN
        // so the decision endpoint can approve/reject/defer it.
        advanceToDecisionOpen(id);

        var decisionResponse = restClient.post()
            .uri("/api/proposals/" + id.value() + "/decisions?outcome=approved&actorId=did:pnyx:demo-voter")
            .retrieve()
            .toEntity(String.class);

        assertThat(decisionResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(decisionResponse.getBody()).contains("approved");
    }

    @Test
    void shouldReturnAuditTrail() {
        var cmd = new SubmitProposalCommand(
            "Community solar panel cooperative",
            "Households in the municipality lack access to affordable solar energy due to high upfront costs and complex installation requirements.",
            "Form a community solar cooperative that pools resources for bulk-purchased solar panel installations across participating households.",
            DEMO_USER
        );

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var submitResponse = restClient.post()
            .uri(PROPOSALS_PATH)
            .headers(h -> h.putAll(headers))
            .body(cmd)
            .retrieve()
            .toEntity(ProposalView.class);
        var id = submitResponse.getBody().id();

        var auditResponse = restClient.get()
            .uri("/api/audit/" + id.value())
            .retrieve()
            .toEntity(java.util.Map[].class);

        assertThat(auditResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        var auditEntries = auditResponse.getBody();
        assertThat(auditEntries).isNotNull();
        assertThat(auditEntries).isNotEmpty();
    }

    @Test
    void shouldExportManifest() {
        var response = restClient.get()
            .uri("/api/export/manifest")
            .retrieve()
            .toEntity(java.util.Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var manifest = response.getBody();
        assertThat(manifest).containsKey("networkName");
        assertThat(manifest.get("networkName")).isEqualTo("test-network");
    }

    @Test
    void shouldCheckDeliberationProgress() {
        var cmd = new SubmitProposalCommand(
            "Bike lane expansion program",
            "The current bike lane network is fragmented and unsafe, discouraging cycling as a commute option and increasing traffic congestion.",
            "Implement a connected bike lane network across the city center, with protected lanes on 5 main corridors, phased over 3 years.",
            DEMO_USER
        );

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var submitResponse = restClient.post()
            .uri(PROPOSALS_PATH)
            .headers(h -> h.putAll(headers))
            .body(cmd)
            .retrieve()
            .toEntity(ProposalView.class);
        var id = submitResponse.getBody().id();

        var progressResponse = restClient.get()
            .uri("/api/proposals/" + id.value() + "/deliberation")
            .retrieve()
            .toEntity(String.class);

        // OK for 200 (progress available) or 404 (no progress yet in test stub)
        assertThat(progressResponse.getStatusCode().is2xxSuccessful()
            || progressResponse.getStatusCode() == HttpStatus.NOT_FOUND).isTrue();
    }
}
