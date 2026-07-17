package dev.pnyx.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.pnyx.config.PnyxProperties;
import dev.pnyx.core.api.DeliberationApi;
import dev.pnyx.core.api.ProposalApi;
import dev.pnyx.core.api.ValidationApi;
import dev.pnyx.core.common.ContentHash;
import dev.pnyx.core.common.PublicObjectType;
import dev.pnyx.core.common.PublicSchemaVersion;
import dev.pnyx.core.domain.proposal.ClassificationResult;
import dev.pnyx.core.domain.result.Result;
import dev.pnyx.core.domain.proposal.ProposalError;
import dev.pnyx.core.domain.proposal.Proposal;
import dev.pnyx.core.domain.proposal.ProposalId;
import dev.pnyx.core.domain.proposal.ProposalState;
import dev.pnyx.core.domain.proposal.ParticipationAuditPublished;
import dev.pnyx.core.domain.proposal.ParticipationPlanCreated;
import dev.pnyx.core.domain.proposal.ParticipantBodyFormed;
import dev.pnyx.core.domain.proposal.ProposalSubmitted;
import dev.pnyx.core.domain.execution.ExecutionMandate;
import dev.pnyx.core.domain.execution.LearningArtifact;
import dev.pnyx.core.domain.execution.MonitoringRecord;
import dev.pnyx.core.domain.participation.AuditDepth;
import dev.pnyx.core.domain.participation.CivicReceipt;
import dev.pnyx.core.domain.participation.CivicReceiptStatus;
import dev.pnyx.core.domain.participation.ParticipationAudit;
import dev.pnyx.core.domain.participation.ParticipationMode;
import dev.pnyx.core.domain.participation.ParticipationPlan;
import dev.pnyx.core.spi.EventStoreSpi;
import dev.pnyx.core.spi.PublicStoreSpi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Implements {@link dev.pnyx.core.api.ProposalApi} — proposal submission and lifecycle management.
 * <p>
 * Orchestrates the command → event → public artifact flow defined in
 * {@code ../docs/20_Protocol_Core/PROTOCOL.md §6}. Submits a proposal by creating the aggregate,
 * appending to the event store, and writing the public artifact.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 * @see ../docs/80_Runtime/API_SPEC.md
 */
@Slf4j
@Service
@SuppressWarnings("PMD.GodClass")
public class ProposalService implements ProposalApi {

    private final EventStoreSpi eventStore;
    private final PublicStoreSpi publicStore;
    private final DeliberationApi deliberationApi;
    private final ValidationApi validationApi;
    private final ObjectMapper objectMapper;
    private final PnyxProperties properties;

    private static final String TYPE_FIELD = "type";
    private static final String SCHEMA_FIELD = "schema";
    private static final String TITLE_FIELD = "title";
    private static final String PROPOSAL_ID_FIELD = "proposal_id";
    private static final String BODY_FIELD = "body";
    private static final String CREATED_AT_FIELD = "created_at";
    private static final String JSON_SUFFIX = ".json";
    private static final String STATUS_ACTIVE = "active";
    private static final String MONTHLY = "monthly";
    private static final String MONITORING_END_DATE = "monitoring_end_date";
    private static final String STATUS_FIELD = "status";


    private record CanonicalProposalObject(
        String type,
        String schema,
        String created_at,
        UUID proposal_id,
        String canonical_public_state,
        String classification,
        Map<String, String> body
    ) { }

    public ProposalService(EventStoreSpi eventStore, PublicStoreSpi publicStore,
                           DeliberationApi deliberationApi, ValidationApi validationApi,
                           ObjectMapper objectMapper, PnyxProperties properties) {
        this.eventStore = eventStore;
        this.publicStore = publicStore;
        this.deliberationApi = deliberationApi;
        this.validationApi = validationApi;
        this.objectMapper = objectMapper
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        this.properties = properties;
    }

    @Override
    public ProposalView submit(SubmitProposalCommand command) {
        var result = Proposal.create(command.title(), command.problem(), command.proposedAction());
        if (result.isFailure()) {
            throw new IllegalArgumentException("Failed to create proposal: " + ((Result.Failure<?, ?>) result).error());
        }
        Proposal proposal = result.orElseThrow();
        
        var submitResult = proposal.submit();
        if (submitResult.isFailure()) {
            throw new IllegalStateException("Failed to submit proposal: " + ((Result.Failure<?, ?>) submitResult).error());
        }
        Proposal submitted = submitResult.orElseThrow();

        var defineResult = submitted.defineProblem();
        if (defineResult.isFailure()) {
            throw new IllegalStateException("Failed to define problem: " + ((Result.Failure<?, ?>) defineResult).error());
        }
        Proposal defined = defineResult.orElseThrow();

        var intakeResult = defined.markIntakeValid();
        if (intakeResult.isFailure()) {
            throw new IllegalStateException("Failed to mark intake valid: " + ((Result.Failure<?, ?>) intakeResult).error());
        }
        Proposal intakeValid = intakeResult.orElseThrow();

        var classifyResult = intakeValid.classify("General", "Core", false);
        if (classifyResult.isFailure()) {
            throw new IllegalStateException("Failed to classify: " + ((Result.Failure<?, ?>) classifyResult).error());
        }
        Proposal classified = classifyResult.orElseThrow();

        var events = classified.uncommittedEvents();
        eventStore.append(classified.proposalId().value(), events);

        ProposalSubmitted evt = (ProposalSubmitted) events.stream()
            .filter(e -> e instanceof ProposalSubmitted)
            .findFirst()
            .orElseThrow();
            
        String json = serializeProposal(classified, evt, classified.proposalId().value());
        var hash = publicStore.write(PublicObjectType.PROPOSAL, PublicSchemaVersion.PROPOSAL_V1,
            command.actorId(), json);
        // Add to the public index so dashboards and listings can find the proposal.
        publicStore.updateManifest(properties.getNetwork(), hash,
            PublicObjectType.PROPOSAL, "objects/proposal/" + hash.hexDigest() + JSON_SUFFIX);

        var validation = validationApi.validate(classified.proposalId());
        log.info("Validation for {}: isValid={}",
            classified.proposalId(), validation.isValid());

        if (validation.isValid()) {
            deliberationApi.runPanel(classified.proposalId());
        } else {
            log.info("Proposal {} not valid — skipping deliberation", classified.proposalId());
        }

        return new ProposalView(classified.proposalId(), classified.title(),
            classified.state().name(), 
            classified.classification() != null ? classified.classification().governanceLayer() : null, 
            Instant.now().toString(),
            new ValidationView(validation.isValid(),
                List.of(),
                validation.feedback() != null ? List.of(validation.feedback()) : List.of(),
                List.of()),
            classified.problem(),
            classified.proposedAction());
    }

    /**
     * Advances a proposal through its lifecycle by applying the next valid transition.
     *
     * @param id the proposal to advance
     */
    public void advanceProposal(ProposalId id) {
        Proposal proposal = rebuildProposal(id);
        
        Result<Proposal, ProposalError> result = null;
        
        switch (proposal.state()) {
            case SUBMITTED -> {
                var defResult = proposal.defineProblem();
                if (defResult.isSuccess()) {
                    result = defResult.orElseThrow().markIntakeValid();
                }
            }
            case CLASSIFICATION_PENDING -> { result = proposal.classify("General", "Core", false); }
            case CLASSIFIED -> { result = proposal.startParticipationDesign(); }
            case PARTICIPATION_DESIGN_PENDING -> {
                // Auto-create a minimal default plan for the MVP auto-advance flow.
                // Explicit plan creation uses the createParticipationPlan API method.
                var defaultPlan = new ParticipationPlan(id, 1,
                    List.of(ParticipationMode.OPEN), List.of(), List.of(),
                    "none", List.of(), "lightweight", STATUS_ACTIVE);
                writeParticipationPlanArtifact(defaultPlan, "system");
                eventStore.append(id.value(), List.of(new ParticipationPlanCreated(id)));
                result = proposal.startPanelSelection();
            }
            case PANEL_SELECTION_PENDING -> { result = proposal.lockPanel(List.of("technical-reviewer")); }
            case PANEL_LOCKED -> { result = proposal.startEvidenceAssembly(); }
            case EVIDENCE_ASSEMBLY -> { result = proposal.activateDeliberation(); }
            case DELIBERATION_ACTIVE -> { result = proposal.draftPacket(); }
            case PACKET_DRAFTING -> { result = proposal.publishPacket(new ContentHash("sha256:0000000000000000000000000000000000000000000000000000000000000000")); }
            case PACKET_PUBLISHED -> { result = proposal.openPublicReview(); }
            case PUBLIC_REVIEW_OPEN -> { result = proposal.closeReview(); }
            case READY_FOR_DECISION -> { result = proposal.openDecision(); }
            case DECISION_OPEN -> { result = proposal.approve(); }
            case APPROVED -> { result = proposal.startRouting(); }
            case ROUTING_PENDING -> { result = proposal.completeRouting(); }
            case ROUTED -> { result = proposal.startExecution(); }
            case EXECUTION_AUTHORIZED -> { result = proposal.startExecution(); }
            case EXECUTION_ACTIVE -> { result = proposal.completeExecution(); }
            case EXECUTION_COMPLETED -> {
                // Transition to monitoring — the service layer starts monitoring
                // with a default monitoring record when auto-advancing.
                var record = new MonitoringRecord(
                    id, Instant.now(), null, List.of(), List.of(),
                    MONTHLY, "system", STATUS_ACTIVE);
                result = proposal.startMonitoring(record);
            }
            case MONITORING_ACTIVE -> {
                // Auto-advance publishes a default learning artifact
                var artifact = new LearningArtifact(
                    id, List.of(), List.of(), List.of(), List.of(), List.of(),
                    Instant.now(), "system");
                result = proposal.publishLearning(artifact);
            }
            case LEARNING_PUBLISHED -> { result = proposal.openPostReview(); }
            case POST_REVIEW_OPEN -> { result = proposal.close(); }
            default -> log.info("No automatic transition for state {}", proposal.state());
        }
        
        if (result != null && result.isSuccess()) {
            Proposal advanced = result.orElseThrow();
            eventStore.append(id.value(), advanced.uncommittedEvents());
        }
    }

    private Proposal rebuildProposal(ProposalId id) {
        var stored = eventStore.readStream(id.value());
        if (stored.isEmpty()) {
            throw new IllegalArgumentException("Proposal not found");
        }

        String title = null;
        String problem = null;
        String proposedAction = null;
        ProposalState state = ProposalState.DRAFT;
        ClassificationResult classification = null;

        for (var event : stored) {
            try {
                var node = objectMapper.readTree(event.eventPayload());
                String type = event.eventType();
                
                if ("ProposalSubmitted".equals(type)) {
                    state = ProposalState.SUBMITTED;
                    title = node.has(TITLE_FIELD) ? node.get(TITLE_FIELD).asText() : null;
                    problem = node.has("problem") ? node.get("problem").asText() : null;
                    proposedAction = node.has("proposedAction") && !node.get("proposedAction").isNull() ? node.get("proposedAction").asText() : null;
                } else if ("IntakeValidated".equals(type)) {
                    state = ProposalState.CLASSIFICATION_PENDING;
                } else if ("ClassificationRecorded".equals(type)) {
                    state = ProposalState.CLASSIFIED;
                    if (node.has("classification")) {
                        classification = parseClassification(node.get("classification"));
                    }
                } else if ("PanelSelectionStarted".equals(type)) {
                    state = ProposalState.PANEL_SELECTION_PENDING;
                } else if ("ProposalPanelLocked".equals(type)) {
                    state = ProposalState.PANEL_LOCKED;
                } else if ("EvidenceAssembled".equals(type)) {
                    state = ProposalState.EVIDENCE_ASSEMBLY;
                } else if ("DeliberationActivated".equals(type)) {
                    state = ProposalState.DELIBERATION_ACTIVE;
                } else if ("PacketDrafted".equals(type)) {
                    state = ProposalState.PACKET_DRAFTING;
                } else if ("PacketPublished".equals(type)) {
                    state = ProposalState.PACKET_PUBLISHED;
                } else if ("PublicReviewOpened".equals(type)) {
                    state = ProposalState.PUBLIC_REVIEW_OPEN;
                } else if ("ReviewClosed".equals(type)) {
                    state = ProposalState.READY_FOR_DECISION;
                } else if ("DecisionOpened".equals(type)) {
                    state = ProposalState.DECISION_OPEN;
                } else if ("ProposalApproved".equals(type)) {
                    state = ProposalState.APPROVED;
                } else if ("ProposalRejected".equals(type)) {
                    state = ProposalState.REJECTED;
                } else if ("ProposalClosed".equals(type)) {
                    state = ProposalState.CLOSED;
                } else if ("ProposalInvalidated".equals(type)) {
                    state = ProposalState.INVALIDATED;
                } else if ("ExecutionMandateIssued".equals(type)) {
                    state = ProposalState.EXECUTION_AUTHORIZED;
                } else if ("ParticipationPlanCreated".equals(type)) {
                    // Informational event — plan data is in the public store.
                    // State transition to PANEL_SELECTION_PENDING happens via PanelSelectionStarted.
                } else if ("ParticipantBodyFormed".equals(type)) {
                    state = ProposalState.PUBLIC_REVIEW_OPEN;
                } else if ("ParticipationAuditPublished".equals(type)) {
                    // Informational event — audit data is in the public store.
                    // The audit is checked as a guard before READY_FOR_DECISION.
                } else if ("MonitoringStarted".equals(type)) {
                    state = ProposalState.MONITORING_ACTIVE;
                } else if ("LearningPublished".equals(type)) {
                    state = ProposalState.LEARNING_PUBLISHED;
                }
            } catch (Exception e) {
                log.warn("Failed to parse event payload", e);
            }
        }

        return Proposal.rehydrate(id, state, title, problem, proposedAction, classification);
    }

    private static ClassificationResult parseClassification(com.fasterxml.jackson.databind.JsonNode cNode) {
        var governanceLayer = cNode.has("governanceLayer")
            ? cNode.get("governanceLayer").asText() : null;
        if (governanceLayer == null || governanceLayer.isBlank()) {
            governanceLayer = "Core";
        }
        var rationale = cNode.has("rationale")
            ? cNode.get("rationale").asText() : null;
        if (rationale == null || rationale.isBlank()) {
            rationale = "Classified automatically";
        }
        return new ClassificationResult(
            governanceLayer,
            cNode.has("nonTrivial") && cNode.get("nonTrivial").asBoolean(),
            rationale,
            Optional.empty(), false, Optional.empty()
        );
    }

    @Override
    public List<ProposalSummary> listRecent() {
        var entries = publicStore.listByType(PublicObjectType.PROPOSAL);
        List<ProposalSummary> summaries = new ArrayList<>(entries.size());
        for (var entry : entries) {
            try {
                var json = publicStore.read(entry.hash());
                if (json == null) { continue; }
                var node = objectMapper.readTree(json);

                // proposal_id may be top-level or nested in body
                String idStr = textOrNull(node.get(PROPOSAL_ID_FIELD));
                if (idStr == null) { idStr = textOrNull(node.path(BODY_FIELD).get(PROPOSAL_ID_FIELD)); }
                if (idStr == null) { continue; }
                UUID uuid;
                try {
                    uuid = UUID.fromString(idStr);
                } catch (IllegalArgumentException e) {
                    continue;
                }
                ProposalId id = new ProposalId(uuid);

                String title = textOrNull(node.path(BODY_FIELD).get(TITLE_FIELD));
                if (title == null) { title = textOrNull(node.get(TITLE_FIELD)); }
                if (title == null) { title = "(untitled)"; }

                String state = textOrNull(node.get("canonical_public_state"));
                if (state == null) { state = textOrNull(node.get("state")); }
                if (state == null) { state = "UNKNOWN"; }

                String createdAt = textOrNull(node.get(CREATED_AT_FIELD));
                if (createdAt == null) { createdAt = entry.publishedAt(); }

                summaries.add(new ProposalSummary(id, title, state, createdAt));
            } catch (Exception e) {
                log.debug("Skipping malformed index entry: {}", e.getMessage());
            }
        }
        return summaries;
    }

    private static String textOrNull(com.fasterxml.jackson.databind.JsonNode node) {
        if (node == null || node.isNull() || !node.isValueNode()) { return null; }
        String s = node.asText();
        return (s == null || s.isBlank()) ? null : s;
    }

    @Override
    public Optional<ProposalView> findById(ProposalId id) {
        try {
            Proposal proposal = rebuildProposal(id);
            String classification = proposal.classification() != null
                ? proposal.classification().governanceLayer() : null;
            var noValidation = new ValidationView(false, List.of(), List.of(), List.of());
            return Optional.of(new ProposalView(id, proposal.title(),
                proposal.state().name(), classification,
                Instant.now().toString(), noValidation,
                proposal.problem(), proposal.proposedAction()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    // ─── Participation floor stubs (MVP §4.11) ───

    @Override
    public ParticipationPlanView createParticipationPlan(CreateParticipationPlanCommand command) {
        ProposalId id = command.proposalId();
        Proposal proposal = rebuildProposal(id);
        if (proposal.state() != ProposalState.PARTICIPATION_DESIGN_PENDING) {
            throw new IllegalStateException(
                "Proposal must be in PARTICIPATION_DESIGN_PENDING, was " + proposal.state());
        }
        var plan = new ParticipationPlan(
            id, 1,
            command.selectedModes(),
            command.expectedBarriers(),
            command.missingPerspectives(),
            command.compensationRules(),
            command.accessibilityMeasures(),
            command.auditCriteria(),
            STATUS_ACTIVE);
        writeParticipationPlanArtifact(plan, command.actorId());
        eventStore.append(id.value(), List.of(new ParticipationPlanCreated(id)));

        var panelResult = proposal.startPanelSelection();
        if (panelResult.isSuccess()) {
            eventStore.append(id.value(), panelResult.orElseThrow().uncommittedEvents());
        }
        return toPlanView(plan);
    }

    @Override
    public Optional<ParticipationPlanView> getParticipationPlan(ProposalId proposalId) {
        var entries = publicStore.listByType(PublicObjectType.PARTICIPATION_PLAN);
        for (var entry : entries) {
            try {
                var json = publicStore.read(entry.hash());
                if (json == null) { continue; }
                var node = objectMapper.readTree(json);
                var pidNode = node.path(PROPOSAL_ID_FIELD);
                if (pidNode.isTextual() && pidNode.asText().equals(proposalId.value().toString())) {
                    return Optional.of(parsePlanView(node));
                }
            } catch (Exception e) {
                log.debug("Skipping malformed plan entry: {}", e.getMessage());
            }
        }
        return Optional.empty();
    }

    @Override
    public ParticipationAuditView issueParticipationAudit(IssueParticipationAuditCommand command) {
        ProposalId id = command.proposalId();
        var audit = new ParticipationAudit(
            id,
            command.depth(),
            command.auditorRef(),
            command.auditorConflictDeclaration(),
            command.findings(),
            command.limitations(),
            "open");
        writeParticipationAuditArtifact(audit, command.actorId());
        eventStore.append(id.value(), List.of(new ParticipationAuditPublished(id)));
        return toAuditView(audit);
    }

    @Override
    public Optional<ParticipationAuditView> getParticipationAudit(ProposalId proposalId) {
        var entries = publicStore.listByType(PublicObjectType.PARTICIPATION_AUDIT);
        for (var entry : entries) {
            try {
                var json = publicStore.read(entry.hash());
                if (json == null) { continue; }
                var node = objectMapper.readTree(json);
                var pidNode = node.path(PROPOSAL_ID_FIELD);
                if (pidNode.isTextual() && pidNode.asText().equals(proposalId.value().toString())) {
                    return Optional.of(parseAuditView(node));
                }
            } catch (Exception e) {
                log.debug("Skipping malformed audit entry: {}", e.getMessage());
            }
        }
        return Optional.empty();
    }

    @Override
    public CivicReceiptView issueCivicReceipt(IssueCivicReceiptCommand command) {
        ProposalId id = command.proposalId();
        var receipt = new CivicReceipt(
            id,
            command.participantRef(),
            command.actionType(),
            "",
            CivicReceiptStatus.INCLUDED,
            "sha256:" + UUID.randomUUID().toString().replace("-", ""));
        writeCivicReceiptArtifact(receipt, command.actorId());
        return toReceiptView(receipt);
    }

    @Override
    public ExecutionMandateView issueExecutionMandate(IssueExecutionMandateCommand command) {
        ProposalId id = command.proposalId();
        Proposal proposal = rebuildProposal(id);
        var mandate = new ExecutionMandate(
            id,
            command.authorizedActor(),
            command.permittedActions(),
            command.prohibitedActions(),
            command.resourceAllocation(),
            command.successCriteria(),
            command.failureCriteria(),
            command.monitoringObligations(),
            command.rollbackConditions(),
            Instant.now());
        var result = proposal.authorizeExecution(mandate);
        if (result.isFailure()) {
            var error = ((Result.Failure<Proposal, ProposalError>) result).error();
            throw new IllegalStateException("Failed to authorize execution: " + error);
        }
        Proposal authorized = result.orElseThrow();
        eventStore.append(id.value(), authorized.uncommittedEvents());
        writeExecutionMandateArtifact(mandate, command.actorId());
        return toMandateView(mandate);
    }

    @Override
    public Optional<ExecutionMandateView> getExecutionMandate(ProposalId proposalId) {
        var entries = publicStore.listByType(PublicObjectType.EXECUTION_MANDATE);
        for (var entry : entries) {
            try {
                var json = publicStore.read(entry.hash());
                if (json == null) { continue; }
                var node = objectMapper.readTree(json);
                var pidNode = node.path(PROPOSAL_ID_FIELD);
                if (pidNode.isTextual() && pidNode.asText().equals(proposalId.value().toString())) {
                    return Optional.of(parseMandateView(node));
                }
            } catch (Exception e) {
                log.debug("Skipping malformed mandate entry: {}", e.getMessage());
            }
        }
        return Optional.empty();
    }

    @Override
    public MonitoringRecordView startMonitoring(StartMonitoringCommand command) {
        ProposalId id = command.proposalId();
        Proposal proposal = rebuildProposal(id);
        var record = new MonitoringRecord(
            id,
            command.monitoringStartDate(),
            command.monitoringEndDate(),
            command.milestones(),
            command.metrics(),
            command.reportingFrequency(),
            command.responsibleParty(),
            STATUS_ACTIVE);
        var result = proposal.startMonitoring(record);
        if (result.isFailure()) {
            var error = ((Result.Failure<Proposal, ProposalError>) result).error();
            throw new IllegalStateException("Failed to start monitoring: " + error);
        }
        var monitored = result.orElseThrow();
        eventStore.append(id.value(), monitored.uncommittedEvents());
        writeMonitoringRecordArtifact(record, command.actorId());
        return toMonitoringRecordView(record);
    }

    @Override
    public Optional<MonitoringRecordView> getMonitoringRecord(ProposalId proposalId) {
        var entries = publicStore.listByType(PublicObjectType.MONITORING_RECORD);
        for (var entry : entries) {
            try {
                var json = publicStore.read(entry.hash());
                if (json == null) { continue; }
                var node = objectMapper.readTree(json);
                var pidNode = node.path(PROPOSAL_ID_FIELD);
                if (!pidNode.isTextual() || !pidNode.asText().equals(proposalId.value().toString())) {
                    // Also check nested body
                    var bodyPid = node.path(BODY_FIELD).path(PROPOSAL_ID_FIELD);
                    if (!bodyPid.isTextual() || !bodyPid.asText().equals(proposalId.value().toString())) {
                        continue;
                    }
                }
                return Optional.of(parseMonitoringRecordView(node));
            } catch (Exception e) {
                log.debug("Skipping malformed monitoring record entry: {}", e.getMessage());
            }
        }
        return Optional.empty();
    }

    @Override
    public LearningArtifactView publishLearning(PublishLearningCommand command) {
        ProposalId id = command.proposalId();
        Proposal proposal = rebuildProposal(id);
        var artifact = new LearningArtifact(
            id,
            command.lessonsLearned(),
            command.successFactors(),
            command.failureFactors(),
            command.recommendations(),
            command.applicableContexts(),
            Instant.now(),
            command.publishedBy());
        var result = proposal.publishLearning(artifact);
        if (result.isFailure()) {
            var error = ((Result.Failure<Proposal, ProposalError>) result).error();
            throw new IllegalStateException("Failed to publish learning: " + error);
        }
        var published = result.orElseThrow();
        eventStore.append(id.value(), published.uncommittedEvents());
        writeLearningArtifactArtifact(artifact, command.actorId());
        return toLearningArtifactView(artifact);
    }

    @Override
    public Optional<LearningArtifactView> getLearningArtifact(ProposalId proposalId) {
        var entries = publicStore.listByType(PublicObjectType.LEARNING_ARTIFACT);
        for (var entry : entries) {
            try {
                var json = publicStore.read(entry.hash());
                if (json == null) { continue; }
                var node = objectMapper.readTree(json);
                var pidNode = node.path(PROPOSAL_ID_FIELD);
                if (!pidNode.isTextual() || !pidNode.asText().equals(proposalId.value().toString())) {
                    // Also check nested body
                    var bodyPid = node.path(BODY_FIELD).path(PROPOSAL_ID_FIELD);
                    if (!bodyPid.isTextual() || !bodyPid.asText().equals(proposalId.value().toString())) {
                        continue;
                    }
                }
                return Optional.of(parseLearningArtifactView(node));
            } catch (Exception e) {
                log.debug("Skipping malformed learning artifact entry: {}", e.getMessage());
            }
        }
        return Optional.empty();
    }

    private void writeMonitoringRecordArtifact(MonitoringRecord record, String actorId) {
        try {
            var body = new java.util.LinkedHashMap<String, Object>();
            body.put(PROPOSAL_ID_FIELD, record.proposalId().value().toString());
            body.put("monitoring_start_date", record.monitoringStartDate().toString());
            if (record.monitoringEndDate() != null) {
                body.put(MONITORING_END_DATE, record.monitoringEndDate().toString());
            }
            body.put("milestones", record.milestones());
            body.put("metrics", record.metrics());
            body.put("reporting_frequency", record.reportingFrequency());
            body.put("responsible_party", record.responsibleParty());
            body.put(STATUS_FIELD, record.status());
            var obj = new java.util.LinkedHashMap<String, Object>();
            obj.put(TYPE_FIELD, PublicObjectType.MONITORING_RECORD.value());
            obj.put(SCHEMA_FIELD, PublicSchemaVersion.MONITORING_RECORD_V1.value());
            obj.put(CREATED_AT_FIELD, Instant.now().toString());
            obj.put(PROPOSAL_ID_FIELD, record.proposalId().value().toString());
            obj.put(BODY_FIELD, body);
            String json = objectMapper.writeValueAsString(obj);
            var hash = publicStore.write(PublicObjectType.MONITORING_RECORD,
                PublicSchemaVersion.MONITORING_RECORD_V1, actorId, json);
            publicStore.updateManifest(properties.getNetwork(), hash,
                PublicObjectType.MONITORING_RECORD,
                "objects/monitoring_record/" + hash.hexDigest() + JSON_SUFFIX);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize monitoring record", e);
        }
    }

    private void writeLearningArtifactArtifact(LearningArtifact artifact, String actorId) {
        try {
            var body = new java.util.LinkedHashMap<String, Object>();
            body.put(PROPOSAL_ID_FIELD, artifact.proposalId().value().toString());
            body.put("lessons_learned", artifact.lessonsLearned());
            body.put("success_factors", artifact.successFactors());
            body.put("failure_factors", artifact.failureFactors());
            body.put("recommendations", artifact.recommendations());
            body.put("applicable_contexts", artifact.applicableContexts());
            body.put("published_at", artifact.publishedAt().toString());
            body.put("published_by", artifact.publishedBy());
            var obj = new java.util.LinkedHashMap<String, Object>();
            obj.put(TYPE_FIELD, PublicObjectType.LEARNING_ARTIFACT.value());
            obj.put(SCHEMA_FIELD, PublicSchemaVersion.LEARNING_ARTIFACT_V1.value());
            obj.put(CREATED_AT_FIELD, Instant.now().toString());
            obj.put(PROPOSAL_ID_FIELD, artifact.proposalId().value().toString());
            obj.put(BODY_FIELD, body);
            String json = objectMapper.writeValueAsString(obj);
            var hash = publicStore.write(PublicObjectType.LEARNING_ARTIFACT,
                PublicSchemaVersion.LEARNING_ARTIFACT_V1, actorId, json);
            publicStore.updateManifest(properties.getNetwork(), hash,
                PublicObjectType.LEARNING_ARTIFACT,
                "objects/learning_artifact/" + hash.hexDigest() + JSON_SUFFIX);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize learning artifact", e);
        }
    }

    private MonitoringRecordView toMonitoringRecordView(MonitoringRecord record) {
        return new MonitoringRecordView(
            record.proposalId(),
            record.responsibleParty(),
            record.status(),
            record.monitoringStartDate().toString(),
            record.monitoringEndDate() != null ? record.monitoringEndDate().toString() : null);
    }

    private LearningArtifactView toLearningArtifactView(LearningArtifact artifact) {
        return new LearningArtifactView(
            artifact.proposalId(),
            artifact.lessonsLearned(),
            artifact.publishedBy(),
            artifact.publishedAt().toString());
    }

    private MonitoringRecordView parseMonitoringRecordView(com.fasterxml.jackson.databind.JsonNode node) {
        var body = node.path(BODY_FIELD);
        return new MonitoringRecordView(
            new ProposalId(UUID.fromString(body.path(PROPOSAL_ID_FIELD).asText())),
            body.path("responsible_party").asText(),
            body.path(STATUS_FIELD).asText(),
            body.path("monitoring_start_date").asText(),
            body.has(MONITORING_END_DATE) && !body.path(MONITORING_END_DATE).isNull()
                ? body.path(MONITORING_END_DATE).asText() : null);
    }

    private LearningArtifactView parseLearningArtifactView(com.fasterxml.jackson.databind.JsonNode node) {
        var body = node.path(BODY_FIELD);
        return new LearningArtifactView(
            new ProposalId(UUID.fromString(body.path(PROPOSAL_ID_FIELD).asText())),
            parseStringList(body, "lessons_learned"),
            body.path("published_by").asText(),
            body.path("published_at").asText());
    }

    private void writeParticipationPlanArtifact(ParticipationPlan plan, String actorId) {
        try {
            var body = new java.util.LinkedHashMap<String, Object>();
            body.put(PROPOSAL_ID_FIELD, plan.proposalId().value().toString());
            body.put("version_no", plan.versionNo());
            body.put("selected_modes", plan.selectedModes().stream().map(ParticipationMode::value).toList());
            body.put("expected_barriers", plan.expectedBarriers());
            body.put("missing_perspectives", plan.missingPerspectives());
            body.put("compensation_rules", plan.compensationRules());
            body.put("accessibility_measures", plan.accessibilityMeasures());
            body.put("audit_criteria", plan.auditCriteria());
            body.put(STATUS_FIELD, plan.status());
            var obj = new java.util.LinkedHashMap<String, Object>();
            obj.put(TYPE_FIELD, PublicObjectType.PARTICIPATION_PLAN.value());
            obj.put(SCHEMA_FIELD, PublicSchemaVersion.PARTICIPATION_PLAN_V1.value());
            obj.put(CREATED_AT_FIELD, Instant.now().toString());
            obj.put(BODY_FIELD, body);
            String json = objectMapper.writeValueAsString(obj);
            var hash = publicStore.write(PublicObjectType.PARTICIPATION_PLAN,
                PublicSchemaVersion.PARTICIPATION_PLAN_V1, actorId, json);
            publicStore.updateManifest(properties.getNetwork(), hash,
                PublicObjectType.PARTICIPATION_PLAN,
                "objects/participation_plan/" + hash.hexDigest() + JSON_SUFFIX);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize participation plan", e);
        }
    }

    private void writeParticipationAuditArtifact(ParticipationAudit audit, String actorId) {
        try {
            var body = new java.util.LinkedHashMap<String, Object>();
            body.put(PROPOSAL_ID_FIELD, audit.proposalId().value().toString());
            body.put("depth", audit.depth().value());
            body.put("auditor_ref", audit.auditorRef());
            body.put("auditor_conflict_declaration", audit.auditorConflictDeclaration());
            body.put("findings", audit.findings());
            body.put("limitations", audit.limitations());
            body.put("challengeable_status", audit.challengeableStatus());
            var obj = new java.util.LinkedHashMap<String, Object>();
            obj.put(TYPE_FIELD, PublicObjectType.PARTICIPATION_AUDIT.value());
            obj.put(SCHEMA_FIELD, PublicSchemaVersion.PARTICIPATION_AUDIT_V1.value());
            obj.put(CREATED_AT_FIELD, Instant.now().toString());
            obj.put(BODY_FIELD, body);
            String json = objectMapper.writeValueAsString(obj);
            var hash = publicStore.write(PublicObjectType.PARTICIPATION_AUDIT,
                PublicSchemaVersion.PARTICIPATION_AUDIT_V1, actorId, json);
            publicStore.updateManifest(properties.getNetwork(), hash,
                PublicObjectType.PARTICIPATION_AUDIT,
                "objects/participation_audit/" + hash.hexDigest() + JSON_SUFFIX);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize participation audit", e);
        }
    }

    private void writeCivicReceiptArtifact(CivicReceipt receipt, String actorId) {
        try {
            var body = new java.util.LinkedHashMap<String, Object>();
            body.put(PROPOSAL_ID_FIELD, receipt.proposalId().value().toString());
            body.put("participant_ref", receipt.participantRef());
            body.put("action_type", receipt.actionType());
            body.put("policy_applied_ref", receipt.policyAppliedRef());
            body.put(STATUS_FIELD, receipt.status().value());
            body.put("inclusion_proof_ref", receipt.inclusionProofRef());
            var obj = new java.util.LinkedHashMap<String, Object>();
            obj.put(TYPE_FIELD, PublicObjectType.CIVIC_RECEIPT.value());
            obj.put(SCHEMA_FIELD, PublicSchemaVersion.CIVIC_RECEIPT_V1.value());
            obj.put(CREATED_AT_FIELD, Instant.now().toString());
            obj.put(BODY_FIELD, body);
            String json = objectMapper.writeValueAsString(obj);
            var hash = publicStore.write(PublicObjectType.CIVIC_RECEIPT,
                PublicSchemaVersion.CIVIC_RECEIPT_V1, actorId, json);
            publicStore.updateManifest(properties.getNetwork(), hash,
                PublicObjectType.CIVIC_RECEIPT,
                "objects/civic_receipt/" + hash.hexDigest() + JSON_SUFFIX);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize civic receipt", e);
        }
    }

    private void writeExecutionMandateArtifact(ExecutionMandate mandate, String actorId) {
        try {
            var body = new java.util.LinkedHashMap<String, Object>();
            body.put(PROPOSAL_ID_FIELD, mandate.proposalId().value().toString());
            body.put("authorized_actor", mandate.authorizedActor());
            body.put("permitted_actions", mandate.permittedActions());
            body.put("prohibited_actions", mandate.prohibitedActions());
            body.put("resource_allocation", mandate.resourceAllocation());
            body.put("success_criteria", mandate.successCriteria());
            body.put("failure_criteria", mandate.failureCriteria());
            body.put("monitoring_obligations", mandate.monitoringObligations());
            body.put("rollback_conditions", mandate.rollbackConditions());
            body.put("issued_at", mandate.issuedAt().toString());
            var obj = new java.util.LinkedHashMap<String, Object>();
            obj.put(TYPE_FIELD, PublicObjectType.EXECUTION_MANDATE.value());
            obj.put(SCHEMA_FIELD, PublicSchemaVersion.EXECUTION_MANDATE_V1.value());
            obj.put(CREATED_AT_FIELD, Instant.now().toString());
            obj.put(BODY_FIELD, body);
            String json = objectMapper.writeValueAsString(obj);
            var hash = publicStore.write(PublicObjectType.EXECUTION_MANDATE,
                PublicSchemaVersion.EXECUTION_MANDATE_V1, actorId, json);
            publicStore.updateManifest(properties.getNetwork(), hash,
                PublicObjectType.EXECUTION_MANDATE,
                "objects/execution_mandate/" + hash.hexDigest() + JSON_SUFFIX);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize execution mandate", e);
        }
    }

    private ExecutionMandateView toMandateView(ExecutionMandate mandate) {
        return new ExecutionMandateView(
            mandate.proposalId(),
            mandate.authorizedActor(),
            mandate.permittedActions(),
            mandate.resourceAllocation(),
            mandate.issuedAt().toString());
    }

    private ExecutionMandateView parseMandateView(com.fasterxml.jackson.databind.JsonNode node) {
        var body = node.path(BODY_FIELD);
        return new ExecutionMandateView(
            new ProposalId(UUID.fromString(body.path(PROPOSAL_ID_FIELD).asText())),
            body.path("authorized_actor").asText(),
            parseStringList(body, "permitted_actions"),
            body.path("resource_allocation").asText(),
            body.path("issued_at").asText());
    }

    private ParticipationPlanView toPlanView(ParticipationPlan plan) {
        return new ParticipationPlanView(
            plan.proposalId(),
            plan.versionNo(),
            plan.selectedModes().stream().map(ParticipationMode::value).toList(),
            plan.expectedBarriers(),
            plan.missingPerspectives(),
            plan.auditCriteria(),
            plan.status());
    }

    private ParticipationAuditView toAuditView(ParticipationAudit audit) {
        return new ParticipationAuditView(
            audit.proposalId(),
            audit.depth().value(),
            audit.auditorRef(),
            audit.findings(),
            audit.limitations(),
            audit.challengeableStatus());
    }

    private CivicReceiptView toReceiptView(CivicReceipt receipt) {
        return new CivicReceiptView(
            receipt.proposalId(),
            receipt.participantRef(),
            receipt.actionType(),
            receipt.status().value(),
            receipt.inclusionProofRef());
    }

    private ParticipationPlanView parsePlanView(com.fasterxml.jackson.databind.JsonNode node) {
        var body = node.path(BODY_FIELD);
        return new ParticipationPlanView(
            new ProposalId(UUID.fromString(body.path(PROPOSAL_ID_FIELD).asText())),
            body.path("version_no").asInt(),
            parseStringList(body, "selected_modes"),
            parseStringList(body, "expected_barriers"),
            parseStringList(body, "missing_perspectives"),
            body.path("audit_criteria").asText(),
            body.path(STATUS_FIELD).asText());
    }

    private ParticipationAuditView parseAuditView(com.fasterxml.jackson.databind.JsonNode node) {
        var body = node.path(BODY_FIELD);
        return new ParticipationAuditView(
            new ProposalId(UUID.fromString(body.path(PROPOSAL_ID_FIELD).asText())),
            body.path("depth").asText(),
            body.path("auditor_ref").asText(),
            body.path("findings").asText(),
            body.path("limitations").asText(),
            body.path("challengeable_status").asText());
    }

    private static List<String> parseStringList(com.fasterxml.jackson.databind.JsonNode parent, String field) {
        var arr = parent.path(field);
        if (!arr.isArray()) { return List.of(); }
        var list = new ArrayList<String>();
        for (var item : arr) { list.add(item.asText()); }
        return list;
    }

    private String serializeProposal(Proposal proposal, ProposalSubmitted evt, UUID id) {
        try {
            var body = new java.util.LinkedHashMap<String, String>();
            body.put("title", evt.title());
            body.put("problem", evt.problem());
            if (evt.proposedAction() != null && !evt.proposedAction().isBlank()) {
                body.put("proposed_action", evt.proposedAction());
            }
            var obj = new CanonicalProposalObject(
                PublicObjectType.PROPOSAL.value(), PublicSchemaVersion.PROPOSAL_V1.value(),
                Instant.now().toString(), id,
                proposal.canonicalPublicState() != null ? proposal.canonicalPublicState().name() : null,
                proposal.classification() != null ? proposal.classification().governanceLayer() : null,
                body);
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize proposal", e);
        }
    }
}
