package dev.pnyx.core.domain.proposal;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import dev.pnyx.core.domain.execution.ExecutionMandate;
import dev.pnyx.core.domain.execution.LearningArtifact;
import dev.pnyx.core.domain.execution.MonitoringRecord;
import dev.pnyx.core.domain.result.Result;
import dev.pnyx.core.common.ContentHash;

/**
 * Event-sourced proposal aggregate enforcing valid lifecycle transitions.
 * <p>
 * Per {@code PROTOCOL.md} invariant 7.9, a proposal may be submitted with a problem statement
 * but without a proposed action. The proposed action is a distinct field that may be absent at
 * submission and must never substitute for the problem statement. Classification may not
 * complete without a validated {@link ProblemDefinition}.
 * <p>See {@code ../docs/20_Protocol_Core/PROTOCOL.md} for the full proposal lifecycle specification.
 * See {@code ../docs/80_Runtime/STATE_MACHINE.md} for state machine definitions.
 * See {@code ../docs/80_Runtime/INVARIANTS.md} for non-negotiable system invariants.
 */
public final class Proposal {

  private final ProposalId id;
  private final ProposalState currentState;
  private final String titleText;
  private final String problemStatement;
  private final String action;
  private final ProblemDefinition definition;
  private final ClassificationResult classificationResult;
  private final List<ProposalEvent> pendingEvents;

  private Proposal(ProposalId id, ProposalState currentState, String titleText,
                   String problemStatement, String action,
                   ProblemDefinition definition, ClassificationResult classificationResult,
                   List<ProposalEvent> pendingEvents) {
    this.id = id;
    this.currentState = currentState;
    this.titleText = titleText;
    this.problemStatement = problemStatement;
    this.action = action;
    this.definition = definition;
    this.classificationResult = classificationResult;
    this.pendingEvents = pendingEvents;
  }

  /**
   * Creates a draft proposal with a new aggregate identifier.
   * <p>
   * The proposed action may be {@code null} — per PROTOCOL.md invariant 7.9, a proposal
   * may be submitted with only a problem statement. The solution is a distinct field.
   *
   * @param title           proposal title
   * @param problem         problem statement (required, must be non-blank)
   * @param proposedAction  proposed solution (optional, may be null)
   * @return a Result with the draft proposal or an error
   * @see ../docs/20_Protocol_Core/PROTOCOL.md
   */
  public static Result<Proposal, ProposalError> create(String title, String problem, String proposedAction) {
    if (title == null || title.isBlank()) {
      return Result.failure(new ProposalError.BlankTitle());
    }
    if (problem == null || problem.isBlank()) {
      return Result.failure(new ProposalError.BlankProblem());
    }
    // Problem-before-solution gate (PROTOCOL.md §49.1)
    if (problem.trim().length() < 20) {
      return Result.failure(new ProposalError.ProblemTooShort());
    }
    if (problem.trim().equalsIgnoreCase(title.trim())) {
      return Result.failure(new ProposalError.ProblemEqualsTitle());
    }
    if (problem.trim().endsWith("?")) {
      return Result.failure(new ProposalError.ProblemIsQuestion());
    }
    if (proposedAction != null && !proposedAction.isBlank()
        && problem.trim().equalsIgnoreCase(proposedAction.trim())) {
      return Result.failure(new ProposalError.ProblemEqualsSolution());
    }
    var id = ProposalId.generate();
    return Result.success(new Proposal(id, ProposalState.DRAFT, title, problem, proposedAction,
                        null, null, new ArrayList<>()));
  }

  /**
   * Rebuilds a proposal snapshot from persisted state without adding new events.
   *
   * @param proposalId     the proposal identifier
   * @param state          current proposal state
   * @param title          proposal title
   * @param problem        problem statement
   * @param proposedAction proposed solution
   * @param classification classification result
   * @return a rehydrated proposal instance
   */
  public static Proposal rehydrate(ProposalId proposalId, ProposalState state,
                                   String title, String problem, String proposedAction,
                                   ClassificationResult classification) {
    return new Proposal(proposalId, state, title, problem, proposedAction,
                        null, classification, new ArrayList<>());
  }

  /**
   * Moves a draft proposal to submitted and records a submission event.
   *
   * @return submitted proposal or error if not in DRAFT state
   */
  public Result<Proposal, ProposalError> submit() {
    if (this.currentState != ProposalState.DRAFT) {
      return Result.failure(new ProposalError.WrongState(ProposalState.DRAFT, this.currentState));
    }
    var events = new ArrayList<>(this.pendingEvents);
    events.add(new ProposalSubmitted(id, titleText, problemStatement, action));
    return Result.success(new Proposal(id, ProposalState.SUBMITTED, titleText, problemStatement, action,
                        definition, classificationResult, events));
  }

  /**
   * Validates the problem definition, enforcing the problem-before-solution gate
   * (PROTOCOL.md invariant 7.9). Must be called before {@link #classify}.
   *
   * @return a new proposal with the validated problem definition
   */
  public Result<Proposal, ProposalError> defineProblem() {
    if (this.currentState != ProposalState.SUBMITTED) {
      return Result.failure(new ProposalError.WrongState(ProposalState.SUBMITTED, this.currentState));
    }
    return ProblemDefinition.validate(problemStatement, action).map(validated -> 
      new Proposal(id, currentState, titleText, problemStatement, action,
                   validated, classificationResult, pendingEvents)
    );
  }

  /**
   * Marks the proposal as having passed intake validation.
   *
   * @return proposal in CLASSIFICATION_PENDING or error if not in SUBMITTED state
   */
  public Result<Proposal, ProposalError> markIntakeValid() {
      if (this.currentState != ProposalState.SUBMITTED) {
          return Result.failure(new ProposalError.WrongState(ProposalState.SUBMITTED, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      events.add(new IntakeValidated(id));
      return Result.success(new Proposal(id, ProposalState.CLASSIFICATION_PENDING, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Requests clarification from the submitter for a submitted proposal.
   *
   * @param reason why clarification is needed
   * @return proposal in REQUIRES_CLARIFICATION or error if not in SUBMITTED state
   */
  public Result<Proposal, ProposalError> requestClarification(String reason) {
      if (this.currentState != ProposalState.SUBMITTED) {
          return Result.failure(new ProposalError.WrongState(ProposalState.SUBMITTED, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      events.add(new ProposalRequiresClarification(id, reason));
      return Result.success(new Proposal(id, ProposalState.REQUIRES_CLARIFICATION, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Applies validation classification to a submitted proposal.
   * <p>
   * Per PROTOCOL.md invariant 7.9, classification may not complete without a validated
   * {@link ProblemDefinition}. Call {@link #defineProblem()} first.
   *
   * @param classificationLabel the classification label
   * @param layer          the governance layer
   * @param isNonTrivial   whether the proposal is non-trivial
   * @return classified proposal or error if not in CLASSIFICATION_PENDING state
   */
  public Result<Proposal, ProposalError> classify(String classificationLabel, String layer, boolean isNonTrivial) {
      if (this.currentState != ProposalState.CLASSIFICATION_PENDING) {
          return Result.failure(new ProposalError.WrongState(ProposalState.CLASSIFICATION_PENDING, this.currentState));
      }
      if (definition == null) {
          return Result.failure(new ProposalError.MissingProblemDefinition());
      }
      var result = new ClassificationResult(layer, isNonTrivial, classificationLabel, Optional.empty(), false, Optional.empty());
      var events = new ArrayList<>(this.pendingEvents);
      events.add(new ClassificationRecorded(id, result));
      return Result.success(new Proposal(id, ProposalState.CLASSIFIED, titleText, problemStatement, action,
                          definition, result, events));
  }

  /**
   * Begins participation design for a classified non-trivial proposal.
   * <p>
   * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.1}, a Participation Plan is
   * produced during this state. Trivial proposals bypass this state via the
   * trivial shortcut (§5).
   *
   * @return proposal in PARTICIPATION_DESIGN_PENDING or error if not in CLASSIFIED state
   * @see ../docs/45_Participation/PARTICIPATION_MODEL.md
   */
  public Result<Proposal, ProposalError> startParticipationDesign() {
      if (this.currentState != ProposalState.CLASSIFIED) {
          return Result.failure(new ProposalError.WrongState(ProposalState.CLASSIFIED, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      return Result.success(new Proposal(id, ProposalState.PARTICIPATION_DESIGN_PENDING, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Begins panel selection for a proposal that has completed participation design.
   * <p>
   * Accepts both {@code CLASSIFIED} (backward-compatible direct path) and
   * {@code PARTICIPATION_DESIGN_PENDING} (the v0.3 normal path for non-trivial
   * proposals). The service layer enforces the Participation Plan guard before
   * calling this method from {@code PARTICIPATION_DESIGN_PENDING}.
   *
   * @return proposal in PANEL_SELECTION_PENDING or error if not in an accepted state
   */
  public Result<Proposal, ProposalError> startPanelSelection() {
      if (this.currentState != ProposalState.CLASSIFIED
          && this.currentState != ProposalState.PARTICIPATION_DESIGN_PENDING) {
          return Result.failure(new ProposalError.WrongStates(
              List.of(ProposalState.CLASSIFIED, ProposalState.PARTICIPATION_DESIGN_PENDING), this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      events.add(new PanelSelectionStarted(id));
      return Result.success(new Proposal(id, ProposalState.PANEL_SELECTION_PENDING, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Locks the selected skill panel for a proposal.
   *
   * @param skillRoles the list of assigned skill role IDs
   * @return proposal in PANEL_LOCKED or error if not in PANEL_SELECTION_PENDING state
   */
  public Result<Proposal, ProposalError> lockPanel(List<String> skillRoles) {
      if (this.currentState != ProposalState.PANEL_SELECTION_PENDING) {
          return Result.failure(new ProposalError.WrongState(ProposalState.PANEL_SELECTION_PENDING, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      events.add(new ProposalPanelLocked(id, skillRoles));
      return Result.success(new Proposal(id, ProposalState.PANEL_LOCKED, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Begins assembly of the evidence packet from skill-panel reviews.
   *
   * @return proposal in EVIDENCE_ASSEMBLY or error if not in PANEL_LOCKED state
   */
  public Result<Proposal, ProposalError> startEvidenceAssembly() {
      if (this.currentState != ProposalState.PANEL_LOCKED) {
          return Result.failure(new ProposalError.WrongState(ProposalState.PANEL_LOCKED, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      events.add(new EvidenceAssembled(id));
      return Result.success(new Proposal(id, ProposalState.EVIDENCE_ASSEMBLY, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Activates deliberation on the assembled evidence.
   *
   * @return proposal in DELIBERATION_ACTIVE or error if not in EVIDENCE_ASSEMBLY state
   */
  public Result<Proposal, ProposalError> activateDeliberation() {
      if (this.currentState != ProposalState.EVIDENCE_ASSEMBLY) {
          return Result.failure(new ProposalError.WrongState(ProposalState.EVIDENCE_ASSEMBLY, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      events.add(new DeliberationActivated(id));
      return Result.success(new Proposal(id, ProposalState.DELIBERATION_ACTIVE, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Begins drafting the public evidence packet.
   *
   * @return proposal in PACKET_DRAFTING or error if not in DELIBERATION_ACTIVE state
   */
  public Result<Proposal, ProposalError> draftPacket() {
      if (this.currentState != ProposalState.DELIBERATION_ACTIVE) {
          return Result.failure(new ProposalError.WrongState(ProposalState.DELIBERATION_ACTIVE, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      events.add(new PacketDrafted(id));
      return Result.success(new Proposal(id, ProposalState.PACKET_DRAFTING, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Publishes the evidence packet with its content hash.
   *
   * @param packetHash the content hash of the packet
   * @return proposal in PACKET_PUBLISHED or error if not in PACKET_DRAFTING state
   */
  public Result<Proposal, ProposalError> publishPacket(ContentHash packetHash) {
      if (this.currentState != ProposalState.PACKET_DRAFTING) {
          return Result.failure(new ProposalError.WrongState(ProposalState.PACKET_DRAFTING, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      events.add(new PacketPublished(id, packetHash));
      return Result.success(new Proposal(id, ProposalState.PACKET_PUBLISHED, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Opens the proposal for public review.
   * <p>
   * Accepts both {@code PACKET_PUBLISHED} (when no formed body is required —
   * the MVP default per {@code ../docs/70_Bootstrap/MINIMUM_VIABLE_PNYX.md §5.5})
   * and {@code PARTICIPANT_BODY_FORMATION} (when body formation is complete).
   *
   * @return proposal in PUBLIC_REVIEW_OPEN or error if not in an accepted state
   */
  public Result<Proposal, ProposalError> openPublicReview() {
      if (this.currentState != ProposalState.PACKET_PUBLISHED
          && this.currentState != ProposalState.PARTICIPANT_BODY_FORMATION) {
          return Result.failure(new ProposalError.WrongStates(
              List.of(ProposalState.PACKET_PUBLISHED, ProposalState.PARTICIPANT_BODY_FORMATION), this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      events.add(new PublicReviewOpened(id));
      return Result.success(new Proposal(id, ProposalState.PUBLIC_REVIEW_OPEN, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Begins participant body formation after packet publication.
   * <p>
   * Entered only when the Participation Plan requires a formed body — a civic
   * jury, a sortition body, or an invited affected-party body. In the MVP
   * ({@code ../docs/70_Bootstrap/MINIMUM_VIABLE_PNYX.md §5.5}), formed bodies are
   * deferred, so this method is not called in the bootstrap runtime.
   *
   * @return proposal in PARTICIPANT_BODY_FORMATION or error if not in PACKET_PUBLISHED
   * @see ../docs/45_Participation/PARTICIPATION_MODEL.md
   */
  public Result<Proposal, ProposalError> formParticipantBody() {
      if (this.currentState != ProposalState.PACKET_PUBLISHED) {
          return Result.failure(new ProposalError.WrongState(ProposalState.PACKET_PUBLISHED, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      return Result.success(new Proposal(id, ProposalState.PARTICIPANT_BODY_FORMATION, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Completes participant body formation and moves to public review.
   * <p>
   * The service layer enforces the formation guard (invitations issued,
   * sortition verifiable, accessibility available) before calling this.
   *
   * @return proposal in PUBLIC_REVIEW_OPEN or error if not in PARTICIPANT_BODY_FORMATION
   */
  public Result<Proposal, ProposalError> completeBodyFormation() {
      if (this.currentState != ProposalState.PARTICIPANT_BODY_FORMATION) {
          return Result.failure(new ProposalError.WrongState(ProposalState.PARTICIPANT_BODY_FORMATION, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      events.add(new ParticipantBodyFormed(id));
      return Result.success(new Proposal(id, ProposalState.PUBLIC_REVIEW_OPEN, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Records a challenge received during public review.
   *
   * @return proposal in CHALLENGED state (no event added, existing events preserved)
   */
  public Result<Proposal, ProposalError> receiveChallenge() {
      if (this.currentState != ProposalState.PUBLIC_REVIEW_OPEN) {
          return Result.failure(new ProposalError.WrongState(ProposalState.PUBLIC_REVIEW_OPEN, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      return Result.success(new Proposal(id, ProposalState.CHALLENGED, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Starts a repair process for a challenged review.
   *
   * @return proposal in REVIEW_REPAIR or error if not in CHALLENGED state
   */
  public Result<Proposal, ProposalError> startReviewRepair() {
      if (this.currentState != ProposalState.CHALLENGED) {
          return Result.failure(new ProposalError.WrongState(ProposalState.CHALLENGED, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      return Result.success(new Proposal(id, ProposalState.REVIEW_REPAIR, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Closes public review and moves the proposal to decision-ready state.
   *
   * @return proposal in READY_FOR_DECISION or error if not in PUBLIC_REVIEW_OPEN or CHALLENGED
   */
   public Result<Proposal, ProposalError> closeReview() {
        if (this.currentState != ProposalState.PUBLIC_REVIEW_OPEN && this.currentState != ProposalState.CHALLENGED) {
            return Result.failure(new ProposalError.WrongStates(
                List.of(ProposalState.PUBLIC_REVIEW_OPEN, ProposalState.CHALLENGED), this.currentState));
        }
        var events = new ArrayList<>(this.pendingEvents);
        events.add(new ReviewClosed(id));
        return Result.success(new Proposal(id, ProposalState.READY_FOR_DECISION, titleText, problemStatement, action,
                            definition, classificationResult, events));
    }

  /**
   * Opens the decision phase for a proposal.
   *
   * @return proposal in DECISION_OPEN or error if not in READY_FOR_DECISION state
   */
  public Result<Proposal, ProposalError> openDecision() {
      if (this.currentState != ProposalState.READY_FOR_DECISION) {
          return Result.failure(new ProposalError.WrongState(ProposalState.READY_FOR_DECISION, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      events.add(new DecisionOpened(id));
      return Result.success(new Proposal(id, ProposalState.DECISION_OPEN, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Approves the proposal during the decision phase.
   *
   * @return proposal in APPROVED or error if not in DECISION_OPEN state
   */
  public Result<Proposal, ProposalError> approve() {
      if (this.currentState != ProposalState.DECISION_OPEN) {
          return Result.failure(new ProposalError.WrongState(ProposalState.DECISION_OPEN, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      events.add(new ProposalApproved(id));
      return Result.success(new Proposal(id, ProposalState.APPROVED, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Rejects the proposal with a reason.
   *
   * @param reason why the proposal was rejected
   * @return proposal in REJECTED or error if not in DECISION_OPEN state
   */
  public Result<Proposal, ProposalError> reject(String reason) {
      if (this.currentState != ProposalState.DECISION_OPEN) {
          return Result.failure(new ProposalError.WrongState(ProposalState.DECISION_OPEN, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      events.add(new ProposalRejected(id, reason));
      return Result.success(new Proposal(id, ProposalState.REJECTED, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Defers the proposal with a reason.
   *
   * @param reason why the proposal was deferred
   * @return proposal in DEFERRED or error if not in DECISION_OPEN state
   */
  public Result<Proposal, ProposalError> defer(String reason) {
      if (this.currentState != ProposalState.DECISION_OPEN) {
          return Result.failure(new ProposalError.WrongState(ProposalState.DECISION_OPEN, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      events.add(new ProposalDeferred(id, reason));
      return Result.success(new Proposal(id, ProposalState.DEFERRED, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Begins routing an approved proposal for implementation.
   *
   * @return proposal in ROUTING_PENDING or error if not in APPROVED state
   */
  public Result<Proposal, ProposalError> startRouting() {
      if (this.currentState != ProposalState.APPROVED) {
          return Result.failure(new ProposalError.WrongState(ProposalState.APPROVED, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      return Result.success(new Proposal(id, ProposalState.ROUTING_PENDING, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Completes routing with a chosen execution path.
   *
   * @return proposal in ROUTED or error if not in ROUTING_PENDING state
   */
  public Result<Proposal, ProposalError> completeRouting() {
      if (this.currentState != ProposalState.ROUTING_PENDING) {
          return Result.failure(new ProposalError.WrongState(ProposalState.ROUTING_PENDING, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      return Result.success(new Proposal(id, ProposalState.ROUTED, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

    /**
     * Issues an Execution Mandate, transitioning the proposal from
     * {@code ROUTED} to {@code EXECUTION_AUTHORIZED}.
     * <p>
     * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.6.2}, the mandate provides
     * explicit authorization with defined constraints, resources, and monitoring
     * obligations before active execution begins. Route assignment alone is
     * implicit authorization; the mandate makes it auditable.
     *
     * @param mandate the execution mandate with constraints and obligations
     * @return proposal in EXECUTION_AUTHORIZED or error if not in ROUTED state
     * @see ../docs/80_Runtime/STATE_MACHINE.md
     * @see ../docs/90_Information/DATA_MODEL.md
     */
    public Result<Proposal, ProposalError> authorizeExecution(ExecutionMandate mandate) {
        if (this.currentState != ProposalState.ROUTED) {
            return Result.failure(new ProposalError.WrongState(ProposalState.ROUTED, this.currentState));
        }
        if (mandate == null || mandate.authorizedActor() == null || mandate.authorizedActor().isBlank()) {
            return Result.failure(new ProposalError.InvalidExecutionMandate());
        }
        var events = new ArrayList<>(this.pendingEvents);
        events.add(new ExecutionMandateIssued(id, mandate.authorizedActor()));
        return Result.success(new Proposal(id, ProposalState.EXECUTION_AUTHORIZED, titleText, problemStatement, action,
                            definition, classificationResult, events));
    }

    /**
     * Begins active execution of the authorized proposal.
     *
     * @return proposal in EXECUTION_ACTIVE or error if not in ROUTED or EXECUTION_AUTHORIZED state
     */
    public Result<Proposal, ProposalError> startExecution() {
        if (this.currentState != ProposalState.ROUTED
            && this.currentState != ProposalState.EXECUTION_AUTHORIZED) {
            return Result.failure(new ProposalError.WrongStates(
                List.of(ProposalState.ROUTED, ProposalState.EXECUTION_AUTHORIZED), this.currentState));
        }
      var events = new ArrayList<>(this.pendingEvents);
      return Result.success(new Proposal(id, ProposalState.EXECUTION_ACTIVE, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Marks execution as completed.
   *
   * @return proposal in EXECUTION_COMPLETED or error if not in EXECUTION_ACTIVE state
   */
  public Result<Proposal, ProposalError> completeExecution() {
      if (this.currentState != ProposalState.EXECUTION_ACTIVE) {
          return Result.failure(new ProposalError.WrongState(ProposalState.EXECUTION_ACTIVE, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      return Result.success(new Proposal(id, ProposalState.EXECUTION_COMPLETED, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

    /**
     * Begins monitoring of execution outcomes.
     * <p>
     * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.6.3}, this transitions the
     * proposal from {@code EXECUTION_COMPLETED} to {@code MONITORING_ACTIVE},
     * establishing a formal monitoring phase with defined milestones, metrics,
     * and reporting obligations.
     *
     * @param record the monitoring record defining what and how to monitor
     * @return proposal in MONITORING_ACTIVE or error if not in EXECUTION_COMPLETED state
     * @see ../docs/80_Runtime/STATE_MACHINE.md
     */
    public Result<Proposal, ProposalError> startMonitoring(MonitoringRecord record) {
        if (this.currentState != ProposalState.EXECUTION_COMPLETED) {
            return Result.failure(new ProposalError.WrongState(ProposalState.EXECUTION_COMPLETED, this.currentState));
        }
        if (record == null || record.responsibleParty() == null || record.responsibleParty().isBlank()) {
            return Result.failure(new ProposalError.InvalidMonitoringRecord());
        }
        var events = new ArrayList<>(this.pendingEvents);
        events.add(new MonitoringStarted(id, record.responsibleParty()));
        return Result.success(new Proposal(id, ProposalState.MONITORING_ACTIVE, titleText, problemStatement, action,
                            definition, classificationResult, events));
    }

    /**
     * Publishes learning artifacts after monitoring.
     * <p>
     * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.6.3}, this transitions the
     * proposal from {@code MONITORING_ACTIVE} to {@code LEARNING_PUBLISHED},
     * recording lessons learned, success/failure factors, and recommendations
     * for systemic improvement.
     *
     * @param artifact the learning artifact with lessons and recommendations
     * @return proposal in LEARNING_PUBLISHED or error if not in MONITORING_ACTIVE state
     * @see ../docs/80_Runtime/STATE_MACHINE.md
     */
    public Result<Proposal, ProposalError> publishLearning(LearningArtifact artifact) {
        if (this.currentState != ProposalState.MONITORING_ACTIVE) {
            return Result.failure(new ProposalError.WrongState(ProposalState.MONITORING_ACTIVE, this.currentState));
        }
        if (artifact == null || artifact.publishedBy() == null || artifact.publishedBy().isBlank()) {
            return Result.failure(new ProposalError.InvalidLearningArtifact());
        }
        var events = new ArrayList<>(this.pendingEvents);
        events.add(new LearningPublished(id, artifact.publishedBy()));
        return Result.success(new Proposal(id, ProposalState.LEARNING_PUBLISHED, titleText, problemStatement, action,
                            definition, classificationResult, events));
    }

  /**
   * Pauses active execution.
   *
   * @return proposal in EXECUTION_PAUSED or error if not in EXECUTION_ACTIVE state
   */
  public Result<Proposal, ProposalError> pauseExecution() {
      if (this.currentState != ProposalState.EXECUTION_ACTIVE) {
          return Result.failure(new ProposalError.WrongState(ProposalState.EXECUTION_ACTIVE, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      return Result.success(new Proposal(id, ProposalState.EXECUTION_PAUSED, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Resumes a paused execution.
   *
   * @return proposal in EXECUTION_ACTIVE or error if not in EXECUTION_PAUSED state
   */
  public Result<Proposal, ProposalError> resumeExecution() {
      if (this.currentState != ProposalState.EXECUTION_PAUSED) {
          return Result.failure(new ProposalError.WrongState(ProposalState.EXECUTION_PAUSED, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      return Result.success(new Proposal(id, ProposalState.EXECUTION_ACTIVE, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Opens a post-execution review.
   * <p>
   * Per {@code ../docs/80_Runtime/STATE_MACHINE.md §4.6.3}, post-review can be
   * entered from {@code EXECUTION_COMPLETED}, {@code MONITORING_ACTIVE}, or
   * {@code LEARNING_PUBLISHED}, allowing flexible outcome review placement.
   *
   * @return proposal in POST_REVIEW_OPEN or error if not in an accepted state
   */
  public Result<Proposal, ProposalError> openPostReview() {
      if (this.currentState != ProposalState.EXECUTION_COMPLETED
          && this.currentState != ProposalState.MONITORING_ACTIVE
          && this.currentState != ProposalState.LEARNING_PUBLISHED) {
          return Result.failure(new ProposalError.WrongStates(
              List.of(ProposalState.EXECUTION_COMPLETED, ProposalState.MONITORING_ACTIVE, ProposalState.LEARNING_PUBLISHED),
              this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      return Result.success(new Proposal(id, ProposalState.POST_REVIEW_OPEN, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Closes the proposal lifecycle. Terminal state — prevents further transitions.
   *
   * @return proposal in CLOSED or error if already CLOSED or INVALIDATED
   */
  public Result<Proposal, ProposalError> close() {
      if (this.currentState == ProposalState.CLOSED || this.currentState == ProposalState.INVALIDATED) {
          return Result.failure(new ProposalError.WrongState(null, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      events.add(new ProposalClosed(id));
      return Result.success(new Proposal(id, ProposalState.CLOSED, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Invalidates the proposal with a reason. Terminal state.
   *
   * @param reason why the proposal was invalidated
   * @return proposal in INVALIDATED or error if already CLOSED or INVALIDATED
   */
  public Result<Proposal, ProposalError> invalidate(String reason) {
      if (this.currentState == ProposalState.CLOSED || this.currentState == ProposalState.INVALIDATED) {
          return Result.failure(new ProposalError.WrongState(null, this.currentState));
      }
      var events = new ArrayList<>(this.pendingEvents);
      events.add(new ProposalInvalidated(id, reason));
      return Result.success(new Proposal(id, ProposalState.INVALIDATED, titleText, problemStatement, action,
                          definition, classificationResult, events));
  }

  /**
   * Returns events produced since the aggregate was created or rehydrated.
   *
   * @return unmodifiable list of pending domain events
   */
  public List<ProposalEvent> uncommittedEvents() {
    return Collections.unmodifiableList(pendingEvents);
  }

  public ProposalId proposalId() {
    return id;
  }

  public ProposalState state() {
    return currentState;
  }

  /**
   * Returns the canonical public state for export (STATE_MACHINE.md §4.5).
   * Returns {@code null} for {@code DRAFT} (not exported).
   *
   * @return the canonical public state, or null if DRAFT
   */
  public CanonicalPublicState canonicalPublicState() {
    return currentState.canonicalPublicState();
  }

  public String title() {
    return titleText;
  }

  public String problem() {
    return problemStatement;
  }

  public String proposedAction() {
    return action;
  }

  public ProblemDefinition problemDefinition() {
    return definition;
  }

  public ClassificationResult classification() {
    return classificationResult;
  }
}
