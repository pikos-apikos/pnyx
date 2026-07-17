# API_SPEC

## 1. Purpose

This document defines the external and internal API surface of the civic system.
The API exists to expose controlled access to the protocol, not to bypass it.

All API behavior is subordinate to:
- `GOVERNANCE.md`
- `ARCHITECTURE.md`
- `PROTOCOL.md`
- `STATE_MACHINE.md`
- `INVARIANTS.md`
- `PERMISSIONS.md`
- `AUDIT_LOG.md`

The API may submit commands, read projections, publish packets, and append audit events through authorized flows.
It may not mutate protected state outside the protocol.

---

## 2. API Design Principles

The API must preserve the following principles:

- command/query separation,
- append-only auditability for all meaningful state changes,
- no silent mutation of published records,
- no active-case parameter retuning,
- no direct state transition outside allowed guards,
- no hidden override channel,
- no implicit legitimacy from API access.

The API is an interface to the machine.
It is not a sovereign layer.

---

## 3. API Boundaries

The API surface is divided into four boundary types:

### 3.1 Public Read API
Read-only access to public proposals, packets, audit records, routing outcomes, challenges, and review status.

### 3.2 Civic Action API
User-initiated actions such as proposal submission, comment submission, challenge submission, vote participation, and review requests.

### 3.3 Governance Control API
Restricted commands for protocol-bound operations such as panel formation, packet publication, routing finalization, epoch activation, or emergency declaration.
These commands remain subject to permissions, state guards, and audit requirements.

### 3.4 Internal System API
Internal machine-to-machine commands, event publication, projection rebuild triggers, notification delivery, and verification services.
These do not possess independent authority.

---

## 4. Canonical Resource Types

The API operates on the following first-class resource types:

- `Proposal`
- `MetaProposal`
- `Challenge`
- `EmergencyAction`
- `ReviewCase`
- `SkillOutput`
- `PanelSelection`
- `RoutingRecord`
- `CivicPacket`
- `AuditEvent`
- `FrameworkEpoch`
- `ParameterEpoch`
- `VoteSession`
- `Comment`
- `EvidenceRecord`
- `ParticipationPlan`
- `ParticipantBody`
- `TargetedInvitation`
- `SortitionConfiguration`
- `SortitionResult`
- `AttentionDelegation`
- `ReachDecision`
- `CivicBrief`
- `CivicReceipt`
- `ParticipationAudit`
- `GovernanceHealthReport`

Each resource must have:
- canonical identifier,
- current state,
- creation timestamp,
- latest visible version,
- immutable audit lineage.

---

## 5. Command / Query Separation

### 5.1 Commands
Commands attempt to change state.
They are permission-checked, invariant-checked, and audit-producing.

Commands must:
- be explicit,
- target a known resource,
- declare actor identity and role context,
- fail closed on ambiguity,
- produce either a rejection or an auditable result.

### 5.2 Queries
Queries never change state.
They return current projections, published views, histories, or derived summaries.

Queries may expose:
- latest visible state,
- historical timeline,
- audit streams,
- packet views,
- routing outcomes,
- challenge status,
- vote status,
- framework and parameter epochs.

---

## 6. Common Command Envelope

Every command must carry a standard envelope.

```json
{
  "command_id": "uuid",
  "command_type": "string",
  "resource_type": "string",
  "resource_id": "string|null",
  "actor_id": "string",
  "actor_role": "string",
  "submitted_at": "timestamp",
  "expected_epoch": {
    "framework_epoch": "string|null",
    "parameter_epoch": "string|null"
  },
  "payload": {},
  "idempotency_key": "string"
}
```

Rules:
- `command_id` must be globally unique.
- `idempotency_key` must prevent duplicate effects.
- `expected_epoch` must bind the command to the ruleset under which it was submitted.
- commands lacking required epoch context must fail where epoch binding is mandatory.

---

## 7. Common Result Envelope

Every command result must return a standard result envelope.

```json
{
  "command_id": "uuid",
  "accepted": true,
  "resource_type": "string",
  "resource_id": "string",
  "result_state": "string",
  "audit_event_id": "string|null",
  "rejection_code": "string|null",
  "rejection_reason": "string|null",
  "visible_version": "integer|null"
}
```

A rejected command must not partially mutate protected state.

---

## 8. Canonical Commands

### 8.1 Proposal Commands

#### `SubmitProposal`
Create a new ordinary proposal.

Payload:
- title
- summary
- body
- proposal_scope
- proposer_statement
- requested_action
- optional evidence attachments

Effects:
- creates `Proposal`
- enters intake state
- emits `ProposalSubmitted`
- appends audit event

#### `AmendProposalDraft`
Modify a draft-stage proposal before lock.

Rules:
- only allowed before panel lock or publication threshold defined by protocol,
- must create a new visible version if draft is publicly visible,
- never silently rewrites history.

#### `WithdrawProposal`
Withdraw a proposal where permitted.

Rules:
- cannot erase prior audit lineage,
- may be disallowed after certain publication or vote states.

### 8.2 Meta Proposal Commands

#### `SubmitMetaProposal`
Create a governance or constitutional change proposal.

Payload must include:
- target framework or parameter set,
- change statement,
- meta impact assessment,
- rationale,
- proposed activation conditions.

Effects:
- creates `MetaProposal`
- binds to current epochs
- emits `MetaProposalSubmitted`

#### `ScheduleFutureEpochActivation`
Schedule approved framework or parameter changes for future activation.

Rules:
- never valid for active-case retuning,
- requires approved meta path,
- must include activation boundary.

### 8.3 Challenge Commands

#### `SubmitChallenge`
Open a challenge against a proposal, route, packet, panel, evidence handling, or process violation.

Payload:
- target resource
- challenge type
- reason
- requested remedy
- optional supporting references

Effects:
- creates `Challenge`
- emits `ChallengeSubmitted`
- appends audit event

#### `ResolveChallenge`
Close a challenge through dismissal, sustain, rerun, invalidation, or escalation.

Rules:
- resolution reason is mandatory,
- cannot erase original challenge.

### 8.4 Panel Commands

#### `RequestPanelFormation`
Trigger panel selection for a non-trivial proposal.

#### `LockPanelSelection`
Freeze the selected panel for the current review cycle.

Rules:
- must satisfy quorum and class requirements,
- invalid if required skill classes are missing.

#### `InvalidatePanelSelection`
Invalidate and rerun panel selection where allowed.

Rules:
- requires logged justification,
- cannot be used to silently tune for outcome.

### 8.5 Skill Output Commands

#### `SubmitSkillOutput`
Append a skill output tied to a panel slot and proposal version.

Rules:
- output must be versioned,
- must reference evidence posture,
- must not overwrite prior output silently.

#### `ChallengeSkillOutput`
Open a process or evidence challenge against a skill output.

### 8.6 Packet Commands

#### `PublishCivicPacket`
Publish a civic packet for deliberation, challenge, or vote.

Rules:
- requires required sections,
- requires current routing status,
- requires dissent and unknowns where applicable,
- publication creates immutable visible version.

#### `SupersedeCivicPacket`
Publish a new packet version while preserving prior versions.

Rules:
- must record supersession reason,
- never deletes prior packet.

### 8.7 Routing Commands

#### `ProposeRoute`
Submit provisional route recommendation.

#### `FinalizeRoute`
Finalize route as `market`, `state`, `hybrid`, `advisory_only`, or `defer_pending_evidence`.

Rules:
- route must include justification,
- rights and anti-capture checks must be present,
- hidden route drift is forbidden.

#### `ChallengeRoute`
Submit challenge to routing decision.

### 8.8 Vote Commands

#### `OpenVoteSession`
Open a vote session for an eligible packet.

Rules:
- requires correct state,
- requires packet visibility,
- must bind to visible packet version and epochs.

#### `CastVote`
Submit a vote from an eligible participant.

Rules:
- one valid vote per actor per session unless protocol defines replaceable pre-close votes,
- all vote writes must be auditable.

#### `CloseVoteSession`
Close voting and trigger outcome calculation.

### 8.9 Emergency Commands

#### `DeclareEmergencyAction`
Open a narrow emergency path.

Rules:
- must include emergency basis,
- must include expiry,
- must include mandatory ex post review schedule,
- cannot normalize into ordinary flow.

#### `ExpireEmergencyAction`
Automatically or explicitly end emergency state.

#### `ReviewEmergencyAction`
Open ex post review case.

### 8.10 Review Commands

#### `OpenReviewCase`
Open a post-decision review.

#### `ResolveReviewCase`
Resolve through affirm, modify prospectively, invalidate, or escalate.

### 8.11 Participation Commands

Governed by `45_Participation/PARTICIPATION_MODEL.md`, `SORTITION.md`, and `ATTENTION_AND_REACH.md`.

#### `CreateParticipationPlan`
Create the per-case Participation Plan for a non-trivial proposal.

Rules:
- versioned; revisions via `ReviseParticipationPlan` preserve history,
- required before panel selection for non-trivial proposals.

#### `IssueTargetedInvitation`
Issue a declared invitation to a person, group, or role.

Rules:
- must declare why, what perspective is sought, how invitees were identified, and compensation status.

#### `ConfigureSortition` / `ExecuteSortitionDraw`
Configure and execute a verifiable random selection.

Rules:
- registry snapshot must be committed before any randomness is revealed,
- draw proof and verification material are mandatory outputs,
- registry mutation inside a selection window is `invalid_state_transition`.

#### `FormParticipantBody`
Record the formed body under the active plan version.

#### `DelegateAttention` / `RevokeAttentionDelegation`
Create or revoke a scoped, time-limited attention delegation.

Rules:
- revocation is always available to the delegator,
- delegation never consumes the delegator's civic action.

#### `GrantReach`
Apply a reach decision under the active `AttentionAllocationPolicy`.

Rules:
- must reference the policy version and reason,
- material grants produce a public `ReachDecision`.

#### `PublishCivicBrief`
Publish a periodic scope-level brief.

#### `OpenParticipationAudit` / `PublishParticipationAudit`
Open and publish the pre-decision Participation Audit.

Rules:
- required before decision-readiness for non-trivial proposals,
- auditor conflicts must be declared,
- published audits are challengeable.

---

## 9. Canonical Queries

### 9.1 Proposal Queries
- `GetProposalById`
- `ListProposals`
- `GetProposalHistory`
- `GetProposalState`

### 9.2 Packet Queries
- `GetPacketById`
- `GetLatestPacketForProposal`
- `GetPacketVersions`

### 9.3 Audit Queries
- `GetAuditEventById`
- `ListAuditEventsForResource`
- `ListAuditEventsByActor`
- `StreamAuditEvents`

### 9.4 Routing Queries
- `GetRoutingRecord`
- `GetRoutingHistory`

### 9.5 Challenge and Review Queries
- `GetChallengeById`
- `ListChallengesForResource`
- `GetReviewCaseById`

### 9.6 Epoch Queries
- `GetActiveFrameworkEpoch`
- `GetActiveParameterEpoch`
- `ListEpochHistory`

### 9.7 Vote Queries
- `GetVoteSession`
- `GetVoteOutcome`
- `ListVoteSessionsForProposal`

### 9.8 Participation Queries
- `GetParticipationPlanForProposal`
- `GetParticipantBody`
- `ListInvitationsForProposal`
- `GetSortitionResult` (with verification material)
- `ListMyAttentionDelegations` (delegator-only)
- `ListReachDecisions` (public)
- `GetCivicBriefForScope`
- `GetMyCivicReceipt` (holder-only; public queries see aggregates)
- `GetParticipationAuditForProposal`
- `GetGovernanceHealthReport`

---

## 10. Endpoint Shape

A REST-like surface may be exposed as follows.

### 10.1 Public Read Endpoints
- `GET /proposals`
- `GET /proposals/{proposal_id}`
- `GET /proposals/{proposal_id}/history`
- `GET /proposals/{proposal_id}/packet`
- `GET /packets/{packet_id}`
- `GET /packets/{packet_id}/versions`
- `GET /audit/{event_id}`
- `GET /resources/{resource_type}/{resource_id}/audit`
- `GET /resources/{resource_type}/{resource_id}/challenges`
- `GET /epochs/framework/current`
- `GET /epochs/parameters/current`
- `GET /votes/{vote_session_id}`
- `GET /proposals/{proposal_id}/participation-plan`
- `GET /proposals/{proposal_id}/participant-body`
- `GET /proposals/{proposal_id}/participation-audit`
- `GET /sortition/{sortition_id}/result`
- `GET /scopes/{scope_id}/civic-briefs`
- `GET /scopes/{scope_id}/reach-decisions`
- `GET /scopes/{scope_id}/governance-health`

### 10.2 Action Endpoints
- `POST /commands/submit-proposal`
- `POST /commands/amend-proposal-draft`
- `POST /commands/withdraw-proposal`
- `POST /commands/submit-meta-proposal`
- `POST /commands/submit-challenge`
- `POST /commands/request-panel-formation`
- `POST /commands/lock-panel-selection`
- `POST /commands/submit-skill-output`
- `POST /commands/publish-civic-packet`
- `POST /commands/finalize-route`
- `POST /commands/open-vote-session`
- `POST /commands/cast-vote`
- `POST /commands/declare-emergency-action`
- `POST /commands/open-review-case`
- `POST /commands/create-participation-plan`
- `POST /commands/issue-targeted-invitation`
- `POST /commands/execute-sortition-draw`
- `POST /commands/delegate-attention`
- `POST /commands/revoke-attention-delegation`
- `POST /commands/publish-participation-audit`

Holder-only endpoints (authenticated, private):
- `GET /me/attention-delegations`
- `GET /me/civic-receipts/{receipt_id}`

Endpoints are illustrative.
The command contract is normative.

---

## 11. Event Model Interface

All accepted commands must emit domain events.

Examples:
- `ProposalSubmitted`
- `ProposalAmended`
- `ProposalWithdrawn`
- `MetaProposalSubmitted`
- `PanelFormationRequested`
- `PanelLocked`
- `PanelInvalidated`
- `SkillOutputSubmitted`
- `CivicPacketPublished`
- `RouteFinalized`
- `ChallengeSubmitted`
- `ChallengeResolved`
- `VoteSessionOpened`
- `VoteCast`
- `VoteSessionClosed`
- `EmergencyActionDeclared`
- `EmergencyActionExpired`
- `ReviewCaseOpened`
- `ReviewCaseResolved`
- `FrameworkEpochScheduled`
- `FrameworkEpochActivated`
- `ParameterEpochScheduled`
- `ParameterEpochActivated`
- `ParticipationPlanCreated`
- `SortitionDrawExecuted`
- `ParticipantBodyFormed`
- `AttentionDelegated`
- `ReachGranted`
- `CivicReceiptIssued`
- `ParticipationAuditPublished`

The full participation command/event catalog is defined in `EVENT_MODEL.md` section 23.

Events are immutable and append-only.
Events are the source of audit and projection rebuilds.

---

## 12. Error Classes

Errors must be explicit and machine-readable.

Canonical rejection classes include:
- `permission_denied`
- `invalid_state_transition`
- `missing_required_field`
- `missing_required_skill_class`
- `epoch_mismatch`
- `invariant_violation`
- `resource_not_found`
- `duplicate_command`
- `challenge_required_first`
- `insufficient_evidence`
- `packet_not_publishable`
- `vote_not_open`
- `emergency_not_permitted`
- `meta_shortcut_forbidden`

No protected failure may degrade into silent success.

---

## 13. Idempotency and Replay

All state-changing commands must be idempotent.

Rules:
- duplicate command submission with same `idempotency_key` must not duplicate side effects,
- event replay must rebuild the same durable state,
- projections may be rebuilt from the event stream,
- rejected commands may be logged but must not mutate protected resources.

---

## 14. Visibility and Redaction

The default posture is public visibility for civic-state outputs.

However, the API may expose redacted views where protocol permits.
Redaction rules must satisfy:
- redaction reason is declared,
- redaction scope is minimal,
- audit lineage remains visible,
- redaction must not hide the existence of the action itself.

There must be no secret decision channel hidden behind redaction.

---

## 15. Authentication and Actor Binding

Every action endpoint must bind to an authenticated actor context.

Actor context must include:
- actor identifier,
- actor type,
- role or membership status,
- permission scope,
- relevant epoch visibility,
- optional delegation context where explicitly allowed.

Authentication proves actor identity.
It does not itself grant legitimacy beyond defined permissions.

---

## 16. Epoch Binding

Commands that depend on framework or parameter rules must bind to the active epochs at submission time.

Rules:
- commands may not silently hop across epochs,
- approved meta changes activate only on future epoch boundaries,
- active cases remain governed by the epochs under which they were validly locked unless protocol explicitly defines migration.

Epoch mismatch must produce explicit rejection or migration flow.

---

## 17. Rate and Abuse Controls

The system may apply anti-abuse controls to API usage.
These may include:
- rate limiting,
- duplicate suppression,
- challenge spam throttling,
- comment flood controls,
- suspicious automation detection.

Such controls must never silently alter substantive outcome.
Anti-abuse is operational defense, not legitimacy logic.

---

## 18. Bootstrap Constraints

During bootstrap, the API must be stricter than later phases.

Bootstrap requirements include:
- narrower set of accepted command types,
- hard-fixed permission boundaries,
- hard-fixed routing categories,
- hard-fixed challenge classes,
- mandatory audit append for every accepted command,
- no unpublished fast-path write operations,
- no active-case modification of review windows or thresholds.

Bootstrap favors clarity over convenience.

---

## 19. Forbidden API Patterns

The following patterns are forbidden:

- direct database mutation outside evented command handling,
- hidden admin route that bypasses protocol states,
- packet overwrite without version publication,
- route replacement without routing audit event,
- emergency declaration without expiry and review hook,
- single-skill publication for non-trivial proposals,
- parameter mutation for an already active case,
- silent deletion of challenge, packet, audit, or vote records,
- private endpoint that changes public-state legitimacy without public audit trace.

---

## 20. Minimal Initial Surface

A minimal deployable API may initially support only:
- proposal submission,
- proposal reading,
- challenge submission,
- panel formation request,
- skill output submission,
- packet publication,
- route finalization,
- vote session open/close,
- audit event read,
- epoch read.

All additional functionality must preserve the same invariants.

---

## 21. Closing Principle

The API must expose the civic machine without becoming a hidden ruler of it.

It may carry commands.
It may not carry sovereignty.
