# EVENT_MODEL

> **Note:** This document has been updated to reflect the normative rules in [SYSTEM_PATCH_v2](../99_Reference/SYSTEM_PATCH_v2.md).

## 1. Purpose

This document defines the event model of the system.

Its purpose is to make explicit:
- which actions enter the system as commands,
- which facts are recorded as events,
- which read models are derived from those events,
- which ordering guarantees matter,
- which invariants must be enforced at write time,
- and how the runtime remains reconstructable from append-only traces.

This document complements:
- `SCHEMAS.md`
- `API_SPEC.md`
- `STATE_MACHINE.md`
- `AUDIT_LOG.md`
- `INVARIANTS.md`
- `TREASURY.md`
- `EMERGENCY_ENFORCEMENT.md`
- `PROTOTYPE_PLAN.md`

The event model is not only a logging strategy.
It is the execution memory of the system.

---

## 2. Core Principle

Commands request change.
Events record change.
Read models expose current understanding.

The runtime must prefer:
- explicit command boundaries,
- explicit event facts,
- append-only event recording,
- replayable reconstruction,
- derived views instead of hidden mutable truth.

If an important change cannot be represented as a command and an event,
it is either underspecified or happening invisibly.

---

## 3. Event-Sourced Posture

The system should treat the append-only event stream as the canonical runtime history.

This means:
- important state is reconstructed from event history,
- mutable views are projections, not sovereign truth,
- corrections are appended, not silently overwritten,
- shutdown, treasury, packet, and challenge history remain linked,
- auditability and runtime history use the same backbone where feasible.

This does **not** require every convenience cache or UI state to be event-sourced.
It requires every governance-significant change to be event-traceable.

---

## 4. Event Model Layers

The event model should distinguish at least four layers.

### 4.1 Domain Commands
Intentions submitted by actors or services.

Examples:
- submit proposal,
- classify proposal,
- select panel,
- publish packet,
- submit challenge,
- authorize release.

### 4.2 Domain Events
Accepted facts that alter the system's canonical history.

Examples:
- proposal submitted,
- proposal classified,
- panel selected,
- packet published,
- challenge submitted,
- release authorized.

### 4.3 Projections / Read Models
Queryable current or summarized views.

Examples:
- current proposal status,
- current packet,
- current challenge list,
- community dashboard,
- public audit timeline.

### 4.4 Operational Events
Events about runtime management, operators, incidents, replay status, or projection rebuilds.

Operational events matter,
but they must not be confused with domain events.

---

## 5. Command Rules

A command should:
- express requested intent,
- include actor and epoch context,
- target a bounded subject,
- be validated before acceptance,
- produce either rejection or one or more domain events,
- remain idempotent where replay or retry is possible.

A command is not a fact.
It is a request to attempt a state transition.

Commands should not mutate read models directly.

---

## 6. Event Rules

An event should:
- record that something happened,
- be immutable once written,
- be timestamped and sequenced,
- include subject linkage,
- include actor or service provenance,
- carry enough payload to reconstruct meaning,
- never depend on undocumented side channels,
- be safe to replay.

Events should describe facts,
not hoped-for outcomes.

Good example:
- `PacketPublished`

Bad example:
- `MaybePacketImproved`

---

## 7. Projection Rules

A projection should:
- be derived from canonical events,
- be rebuildable,
- never become the only source of truth,
- be discardable and recoverable,
- clearly identify its freshness and projection version where relevant.

Projection corruption is serious,
but projection corruption is different from event-stream corruption.

The event model must preserve that distinction.

---

## 8. Event Stream Structure

The canonical event stream should support:

- total ordering or partition ordering with explicit rules,
- sequence numbers,
- trace references,
- previous-event linkage or chained integrity proof where used,
- replay windows,
- subject-level event filtering,
- actor-level event filtering,
- epoch-aware querying.

The stream does not need to be globally serialized by one machine in all deployments,
but ordering guarantees must be explicit.

---

## 9. Aggregate Boundaries

The system should define aggregate-like boundaries for write validation.

Likely aggregates include:
- Proposal
- Packet
- Challenge
- ReviewCase
- Skill
- EvaluationSuite
- TreasuryPartition
- PublicIPArtifact
- EmergencyRecord
- ShutdownReview
- ParticipationPlan
- ParticipantBody
- ParticipationAudit
- CivicReceipt
- ParticipationPolicy
- AttentionAllocationPolicy
- AttentionDelegation

Commands should validate against the current known state of the relevant aggregate boundary before producing events.

This does not require classic DDD dogma.
It requires bounded write logic.

---

## 10. Proposal Lifecycle Commands and Events

### Commands
- `SubmitProposal`
- `CreateProposalVersion`
- `WithdrawProposal`
- `ArchiveProposal`

### Events
- `ProposalSubmitted`
- `ProposalVersionCreated`
- `ProposalWithdrawn`
- `ProposalArchived`
- `ProposalRejectedAsInvalid`

The proposal stream should allow reconstruction of:
- original proposal text,
- version history,
- current active version,
- status changes,
- linked classification, packets, and challenges.

---

## 11. Classification Commands and Events

### Commands
- `ClassifyProposal`
- `CounterClassifyProposal`
- `LockClassification`
- `ChallengeClassification`
- `ResolveClassificationChallenge`

### Events
- `ProposalClassified`
- `ProposalCounterClassified`
- `ClassificationLocked`
- `ClassificationChallenged`
- `ClassificationChallengeResolved`
- `ClassificationSuperseded`

No classification correction should erase the earlier classification event.

---

## 12. Panel Selection Commands and Events

### Commands
- `SelectPanel`
- `LockPanel`
- `ChallengePanelSelection`
- `InvalidatePanelSelection`
- `ReselectPanel`

### Events
- `PanelSelected`
- `PanelLocked`
- `PanelSelectionChallenged`
- `PanelSelectionInvalidated`
- `PanelReselected`

The panel event stream should make visible:
- required classes,
- actual selected skills,
- concentration flags,
- invalidation and reselection history.

---

## 13. Skill and Executor Runtime Commands and Events

### Commands
- `RunSkill`
- `RecordSkillFailure`
- `SuspendSkill`
- `RetireSkill`
- `PromoteSkill`
- `DemoteSkill`
- `AdmitExecutor`
- `ScopeExecutor`
- `DowngradeExecutor`
- `SuspendExecutor`
- `RevalidateExecutor`

### Events
- `SkillRunStarted`
- `SkillRunCompleted`
- `SkillRunFailed`
- `SkillSuspended`
- `SkillRetired`
- `SkillPromoted`
- `SkillDemoted`
- `ExecutorAdmitted`
- `ExecutorScoped`
- `ExecutorDowngraded`
- `ExecutorSuspended`
- `ExecutorRevalidated`

The event stream must make visible whether a packet relied on:
- suspended skills,
- stale skills,
- weakly evaluated skills,
- model-dependent skills,
- downgraded or suspended executors.

---

## 14. Packet Commands and Events

### Commands
- `SynthesizePacket`
- `PublishPacket`
- `PublishEvidencePacket`
- `RevisePacket`
- `ReaffirmPacket`
- `ArchivePacket`

### Events
- `PacketSynthesized`
- `PacketPublished`
- `EvidencePacketPublished`
- `PacketRevised`
- `PacketReaffirmed`
- `PacketArchived`

Packets are append-only through versions.
A `PacketRevised` event must never imply hidden replacement of the earlier packet.

---

## 15. Challenge Commands and Events

### Commands
- `SubmitChallenge`
- `OpenReviewCase`
- `ResolveChallenge`
- `RejectChallengeAsInvalid`
- `EscalateChallenge`
- `FileChallengeAgainstAnalyticBasis`

### Events
- `ChallengeSubmitted`
- `ReviewCaseOpened`
- `ChallengeResolved`
- `ChallengeRejectedAsInvalid`
- `ChallengeEscalated`
- `ReviewCaseClosed`
- `ChallengeFiledAgainstAnalyticBasis`

Challenge history must remain readable as a timeline,
not merely as final status.

---

## 16. Evaluation and Evidence Commands and Events

### Commands
- `CreateEvaluationSuite`
- `AddTaskCase`
- `RunEvaluation`
- `RecordFailureEvent`
- `PromoteSkillVersion`
- `DemoteSkillVersion`
- `ValidateCitation`
- `TriggerConfidenceCap`
- `RequireHumanReview`
- `TriggerReplication`
- `InvalidateSource`
- `DiscloseConflictOfInterest`

### Events
- `EvaluationSuiteCreated`
- `TaskCaseAdded`
- `EvaluationRunCompleted`
- `FailureEventRecorded`
- `SkillVersionPromoted`
- `SkillVersionDemoted`
- `CitationValidationFailed`
- `ConfidenceCapTriggered`
- `HumanReviewRequired`
- `ReplicationTriggered`
- `SourceInvalidated`
- `ConflictOfInterestDisclosed`

The evaluation stream should support:
- regression history,
- failure class recurrence,
- suite evolution,
- promotion and demotion rationale.

---

## 17. Treasury Commands and Events

### Commands
- `ReceiveFunds`
- `AllocateFunds`
- `AuthorizeRelease`
- `ExecuteRelease`
- `TransferBetweenPartitions`
- `FreezeTreasuryAction`
- `ReconcileTreasury`

### Events
- `FundingReceived`
- `FundsAllocated`
- `ReleaseAuthorized`
- `ReleaseExecuted`
- `PartitionTransferExecuted`
- `TreasuryActionFrozen`
- `TreasuryReconciled`
- `TreasuryAnomalyDetected`

The treasury event stream must allow reconstruction of:
- inflows,
- allocations,
- releases,
- partition balances,
- concentration signals,
- reconciliation anomalies.

---

## 18. Public IP Commands and Events

### Commands
- `ClassifyPublicIPArtifact`
- `AssignStewardship`
- `RoutePublicIPRevenue`
- `TransferPublicIPStewardship`
- `ArchivePublicIPArtifact`

### Events
- `PublicIPArtifactClassified`
- `PublicIPStewardshipAssigned`
- `PublicIPRevenueRouted`
- `PublicIPStewardshipTransferred`
- `PublicIPArtifactArchived`

Public IP events must preserve traceability from:
proposal -> artifact -> licensing -> revenue -> treasury routing.

---

## 19. Community and Pilot Commands and Events

### Commands
- `RegisterCommunity`
- `CreatePilotDomain`
- `UpdatePilotStatus`
- `RecordPilotRetrospective`
- `ClosePilotDomain`

### Events
- `CommunityRegistered`
- `PilotDomainCreated`
- `PilotStatusUpdated`
- `PilotRetrospectiveRecorded`
- `PilotDomainClosed`

Pilot events are important because prototype truth depends on repeatable history,
not founder memory.

---

## 20. Operator Commands and Events

### Commands
- `LogOperatorAction`
- `PauseRuntime`
- `ResumeRuntime`
- `ApplyVisibleManualIntervention`

### Events
- `OperatorActionLogged`
- `RuntimePaused`
- `RuntimeResumed`
- `ManualInterventionApplied`

There must be no operator path outside the event model for governance-significant intervention.

---

## 21. Emergency Commands and Events

### Commands
- `DeclareEmergency`
- `AuthorizeEmergencyAction`
- `ExpireEmergency`
- `CloseEmergency`
- `FlagEmergencyAbuse`

### Events
- `EmergencyDeclared`
- `EmergencyActionAuthorized`
- `EmergencyActionExecuted`
- `EmergencyExpired`
- `EmergencyClosed`
- `EmergencyAbuseFlagged`

Emergency events must remain tightly bounded and linked to review cases.

---

## 22. Shutdown Commands and Events

### Commands
- `OpenShutdownReview`
- `DecidePause`
- `DecideWindDown`
- `DecideDissolution`
- `DecideTransfer`
- `ArchiveSystem`

### Events
- `ShutdownReviewOpened`
- `PauseDecided`
- `WindDownDecided`
- `DissolutionDecided`
- `StewardshipTransferDecided`
- `SystemArchived`

A shutdown event stream must preserve:
- why stopping happened,
- what happened to active cases,
- what happened to funds,
- what happened to archives and public IP.

---

## 23. Participation Commands and Events

### Commands
- `CreateParticipationPlan`
- `ReviseParticipationPlan`
- `IssueTargetedInvitation`
- `AcceptInvitation`
- `DeclineInvitation`
- `ConfigureSortition`
- `CommitRegistrySnapshot`
- `ExecuteSortitionDraw`
- `VerifySortitionDraw`
- `ChallengeSortition`
- `FormParticipantBody`
- `DissolveParticipantBody`
- `DelegateAttention`
- `RevokeAttentionDelegation`
- `GrantReach`
- `AppealReachDecision`
- `PublishCivicBrief`
- `IssueCivicReceipt`
- `SupersedeCivicReceipt`
- `OpenParticipationAudit`
- `PublishParticipationAudit`
- `ChallengeParticipationAudit`
- `AuthorizeParticipantCompensation`
- `RecordCompensationPayment`

### Events
- `ParticipationPlanCreated`
- `ParticipationPlanRevised`
- `TargetedInvitationIssued`
- `InvitationAccepted`
- `InvitationDeclined`
- `SortitionConfigured`
- `RegistrySnapshotCommitted`
- `SortitionDrawExecuted`
- `SortitionDrawVerified`
- `SortitionChallenged`
- `ParticipantBodyFormed`
- `ParticipantBodyDissolved`
- `AttentionDelegated`
- `AttentionDelegationRevoked`
- `AttentionDelegationExpired`
- `ReachGranted`
- `ReachExpired`
- `ReachDecisionAppealed`
- `CivicBriefPublished`
- `CivicReceiptIssued`
- `CivicReceiptSuperseded`
- `ParticipationAuditOpened`
- `ParticipationAuditPublished`
- `ParticipationAuditChallenged`
- `ParticipantCompensationAuthorized`
- `ParticipantCompensationPaid`

The participation event stream must allow reconstruction of:
- who was eligible, informed, invited, and selected,
- how the participant body was formed and under which plan version,
- every material reach and delegation decision,
- audit history before decision-readiness,
- compensation flows independent of position or vote.

Aggregate note: `ParticipationPlan`, `ParticipantBody`, `ParticipationAudit`, and `CivicReceipt` validate under the owning `Proposal` aggregate; `ParticipationPolicy`, `AttentionAllocationPolicy`, and `AttentionDelegation` are scope-level aggregates independent of any single proposal.

Privacy note: `CivicReceiptIssued` payloads follow the private-proof / public-aggregate rule (`CORE_V03_RECONCILIATION.md` §4.3); the public projection records that a receipt exists and is included in an aggregate, never how the participant acted (see section 31).

See `45_Participation/PARTICIPATION_MODEL.md`, `45_Participation/SORTITION.md`, and `45_Participation/ATTENTION_AND_REACH.md` for the governing rules.

---

## 24. Write-Side Validation

Before a command produces events, validation should check:
- actor permissions,
- subject existence,
- aggregate current state,
- epoch compatibility,
- invariant compliance,
- idempotency,
- prerequisite events or statuses,
- forbidden active-case retuning,
- shutdown or pause status where relevant.

Validation failure should produce a structured rejection,
not silent ignore.

---

## 25. Idempotency and Retries

Commands likely to be retried must include idempotency keys.

Idempotency rules should ensure:
- duplicate submissions do not create duplicate domain facts,
- retried publishes do not produce duplicate packet versions,
- retried treasury releases do not duplicate disbursement,
- replay of already-applied commands is detectable.

Idempotency is especially important for:
- intake,
- funding receipts,
- releases,
- packet publication,
- emergency actions.

---

## 26. Ordering and Causality

The event model must preserve enough causal structure to answer:
- what happened first,
- what event justified the next event,
- which command caused which event,
- which epoch and state were active at that time.

Useful mechanisms include:
- global or partition sequence numbers,
- causation IDs,
- correlation IDs,
- trace refs,
- previous-event hashes,
- subject-specific version counters.

Without causal structure, replay becomes narrative guesswork.

---

## 27. Event Immutability and Corrections

Events are immutable.
Mistakes are corrected by new events.

Examples:
- `ClassificationSuperseded`
- `PacketRevised`
- `TreasuryAnomalyDetected`
- `ReviewCaseReopened`
- `AuditCorrectionAppended`

No correction event may erase the original event from canonical history.

---

## 28. Projection Types

The system should define at least these projection classes:

### 28.1 Current-State Projections
Examples:
- current proposal status,
- current active packet,
- current challenge status.

### 28.2 Timeline Projections
Examples:
- full proposal timeline,
- treasury timeline,
- emergency timeline.

### 28.3 Audit Projections
Examples:
- public decision record,
- auditor drill-down,
- operator action view.

### 28.4 Analytical Projections
Examples:
- repeated failure classes,
- dependency concentration,
- challenge usage frequency,
- community repeat-use metrics.

Analytical projections are useful but must never be mistaken for canonical facts.

---

## 29. Projection Rebuilds

Every important projection should be rebuildable from events.

Projection rebuild processes should:
- declare projection version,
- declare source event range,
- record rebuild time,
- surface rebuild failures,
- avoid mutating canonical event history.

A projection rebuild failure is serious.
It is not the same as losing the event stream.

---

## 30. Event Retention and Archival

The event model must support:
- active retention,
- archival retention,
- read-only archival mode,
- shutdown archival continuity,
- lawful privacy-aware redaction overlays where required.

Canonical events should be retained as long as required for:
- auditability,
- treasury traceability,
- packet history,
- challenge history,
- shutdown honesty,
- public IP provenance.

Retention rules must not become disguised deletion of inconvenient history.

---

## 31. Event Privacy Boundaries

Some event payloads may include sensitive or privacy-constrained fields.

The event model should therefore distinguish:
- canonical internal payload,
- public redacted projection,
- auditor-visible payload where lawful,
- privacy-restricted fragments.

Privacy handling must not break causality or render public events meaningless.

The public must still be able to know that something happened,
even if some payload details are protected.

---

## 32. Event Failure Modes

The system should explicitly watch for these event-model failure modes:
- missing events for meaningful actions,
- projections diverging from event history,
- duplicate fact creation,
- out-of-order writes where order matters,
- untraceable operator actions,
- event schema drift without versioning,
- hidden mutable state outside event history,
- replay instability,
- redaction logic that conceals governance-significant facts.

These are runtime integrity failures, not just engineering issues.

---

## 33. Prototype Profile

For prototype v1, the minimal live event surface should include:

### Commands
- `SubmitProposal`
- `ClassifyProposal`
- `SelectPanel`
- `RunSkill`
- `SynthesizePacket`
- `PublishPacket`
- `SubmitChallenge`
- `ResolveChallenge`
- `LogOperatorAction`
- `RunEvaluation`

### Events
- `ProposalSubmitted`
- `ProposalClassified`
- `PanelSelected`
- `SkillRunCompleted`
- `PacketPublished`
- `ChallengeSubmitted`
- `ChallengeResolved`
- `OperatorActionLogged`
- `EvaluationRunCompleted`

Everything else may be documented now and implemented later.

---

## 34. Cross-References

This document imposes requirements on:

### 34.1 SCHEMAS
Must define canonical payload shapes for commands, events, and projections.

### 34.2 API_SPEC
Must map command and query interfaces onto this event model.

### 34.3 STATE_MACHINE
Must define which commands are valid in which states.

### 34.4 AUDIT_LOG
Must preserve event traceability and append-only guarantees.

### 34.5 READ_MODELS
Should define the projection surface derived from these events.

### 34.6 TREASURY
Must treat financial actions as domain events, not side effects.

### 34.7 SHUTDOWN_AND_DISSOLUTION
Must end in explicit shutdown events and archival posture.

### 34.8 EXECUTOR_MODEL
Must define the executor lifecycle events.

### 34.9 EVIDENCE_PACKET
Must define the evidence packet publication events.

### 34.10 CONFIDENCE_AND_SCORING
Must define the confidence cap and human review trigger events.

### 34.11 REVALIDATION_POLICY
Must define the revalidation and downgrade events.

### 34.12 PARTICIPATION LAYER
`45_Participation/PARTICIPATION_MODEL.md`, `SORTITION.md`, and `ATTENTION_AND_REACH.md` must define the rules whose enforcement the participation commands and events in section 23 record.

---

## 35. Closing Principle

A civic runtime becomes trustworthy when its important changes become replayable facts instead of hidden mutations.

The event model exists to ensure that:
- intent is explicit,
- change is recorded,
- views are derived,
- corrections are appended,
- and history remains reconstructable even when current projections are wrong.

The system should therefore fear not only bad decisions,
but invisible state.
