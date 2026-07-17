# STATE_MACHINE

## 1. Purpose

This document defines the canonical lifecycle of proposals, challenges, meta-proposals, and emergency actions as explicit state machines.

Its purpose is to ensure that:
- the system behaves predictably,
- legitimacy conditions are enforced as transitions,
- no proposal can silently skip required stages,
- state changes remain auditable and contestable,
- policy, governance, constitutional, and emergency paths are mechanically distinguishable.

This document must be read together with:
- `GOVERNANCE.md`
- `ARCHITECTURE.md`
- `PROTOCOL.md`
- `AUDIT_LOG.md`
- `INVARIANTS.md`
- `META_GOVERNANCE.md`

---

## 2. State Machine Principles

### 2.1 Explicit-state requirement
No proposal or governance object may exist in an implied or hidden state.
Every meaningful lifecycle position must be represented by an explicit state.

### 2.2 Transition-by-event requirement
A state change must occur only through a logged transition event.
No state may change through silent mutation.

### 2.3 Guarded-transition requirement
A transition is valid only if all required guards are satisfied.
Guards may include:
- required fields present,
- correct layer classification,
- panel quorum satisfied,
- required skill classes present,
- minimum evidence state achieved,
- mandatory packet published,
- review window opened or closed,
- challenge status resolved,
- threshold reached,
- epoch constraints satisfied.

### 2.4 Layer-sensitive lifecycle
Not all objects use the same path.
The system distinguishes:
- ordinary proposal lifecycle,
- meta-governance lifecycle,
- challenge lifecycle,
- emergency lifecycle,
- post-decision review lifecycle.

### 2.5 No bypass rule
Any transition that skips a required prior stage is invalid unless a narrowly defined emergency rule explicitly allows it.
Even then, the exception must be logged and later reviewed.

---

## 3. Object Types Covered

This document defines state machines for:
- `Proposal`
- `MetaProposal`
- `Challenge`
- `EmergencyAction`
- `ReviewCase`

---

## 4. Proposal State Machine

## 4.1 Canonical states

### `DRAFT`
Proposal exists but is not yet submitted into the public civic loop.

### `SUBMITTED`
Proposal has been formally entered and received a stable identifier.
Initial intake validation has passed.

### `CLASSIFICATION_PENDING`
The system is determining:
- proposal layer,
- non-trivial vs trivial status,
- initial scope,
- whether emergency or meta handling is required.

### `REQUIRES_CLARIFICATION`
Proposal cannot proceed because scope, semantics, affected domain, or required fields are insufficient.

### `CLASSIFIED`
Proposal has a recorded layer and procedural path.

### `PARTICIPATION_DESIGN_PENDING`
A Participation Plan is being produced for a non-trivial proposal.
The plan maps affected scopes, expected participation barriers, missing perspectives, participation modes, compensation and accessibility requirements, and audit criteria.
Trivial proposals bypass this state through the trivial shortcut (see section 5).
The plan is versioned and remains revisable after this state is exited; the state certifies that a first complete plan exists, not that participation design is finished.
See `45_Participation/PARTICIPATION_MODEL.md`.

### `PANEL_SELECTION_PENDING`
Panel construction has begun but is not yet locked.

### `PANEL_LOCKED`
A valid panel has been selected and recorded.
Minimum quorum and required classes are satisfied.

### `EVIDENCE_ASSEMBLY`
Relevant evidence, constraints, and inputs are being collected.

### `DELIBERATION_ACTIVE`
The panel is producing structured outputs, disagreement, routing analysis, and impact notes.

### `PACKET_DRAFTING`
The civic packet is being compiled from deliberative outputs.

### `PACKET_PUBLISHED`
The canonical civic packet has been published for public visibility.

### `PARTICIPANT_BODY_FORMATION`
A participant body required by the Participation Plan is being formed before public review opens.
Entered only when the plan requires a formed body — a civic jury, a sortition body, or an invited affected-party body; otherwise skipped.
This state certifies formation of the deliberation and judgment body only; participation as a whole spans intake through monitoring.
The participant body must not be confused with the skill panel: skills analyse, participants deliberate and judge.
See `45_Participation/PARTICIPATION_MODEL.md`.

### `PUBLIC_REVIEW_OPEN`
Public review window is open.
Challenges, comments, and objections may be filed according to permissions.

### `CHALLENGED`
At least one valid challenge has been registered that affects validity, evidence sufficiency, panel composition, classification, routing, or packet integrity.

### `REVIEW_REPAIR`
The proposal is undergoing repair after challenge.
This may include panel rerun, evidence supplementation, reclassification, or packet correction.

### `READY_FOR_DECISION`
All required review conditions are satisfied, the review window is closed, and no blocking challenge remains unresolved.

### `DECISION_OPEN`
Formal decision process has begun.
This may be vote, ratification, approval window, or layer-specific threshold mechanism.

### `APPROVED`
Proposal has passed the relevant threshold.

### `REJECTED`
Proposal failed to pass the relevant threshold.

### `DEFERRED`
Proposal is neither approved nor rejected because required evidence, conditions, or timing are insufficient.

### `ROUTING_PENDING`
Proposal is approved but execution route is not yet finalized or activated.

### `ROUTED`
Proposal has been assigned a valid execution route.

### `EXECUTION_ACTIVE`
Execution has begun through the assigned route.

### `EXECUTION_PAUSED`
Execution has been paused due to challenge, dependency failure, legal concern, audit concern, or emergency review.

### `EXECUTION_COMPLETED`
Execution has reached its declared terminal operational state.

### `POST_REVIEW_OPEN`
Outcome review window is open.
The system evaluates reversibility, observed effects, capture risks, and audit consistency.

### `CLOSED`
All required procedural and review obligations have completed.
The object remains auditable but inactive.

### `INVALIDATED`
Proposal lifecycle was procedurally corrupted or later deemed invalid due to broken invariants, illegitimate transition, fraud, undisclosed conflict, invalid routing, or other fatal defect.

---

## 4.2 Canonical ordinary path

The ordinary non-trivial path is:

`DRAFT`
→ `SUBMITTED`
→ `CLASSIFICATION_PENDING`
→ `CLASSIFIED`
→ `PARTICIPATION_DESIGN_PENDING`
→ `PANEL_SELECTION_PENDING`
→ `PANEL_LOCKED`
→ `EVIDENCE_ASSEMBLY`
→ `DELIBERATION_ACTIVE`
→ `PACKET_DRAFTING`
→ `PACKET_PUBLISHED`
→ (`PARTICIPANT_BODY_FORMATION` when the Participation Plan requires a formed body)
→ `PUBLIC_REVIEW_OPEN`
→ `READY_FOR_DECISION`
→ `DECISION_OPEN`
→ (`APPROVED` | `REJECTED` | `DEFERRED`)
→ if approved: `ROUTING_PENDING` → `ROUTED` → `EXECUTION_ACTIVE`
→ (`EXECUTION_COMPLETED` | `EXECUTION_PAUSED`)
→ `MONITORING_ACTIVE`
→ `LEARNING_PUBLISHED`
→ `POST_REVIEW_OPEN`
→ `CLOSED`

---

## 4.3 Allowed transitions

### From `DRAFT`
- to `SUBMITTED`
- to terminal deletion outside the civic loop only if not yet submitted

### From `SUBMITTED`
- to `CLASSIFICATION_PENDING`
- to `REQUIRES_CLARIFICATION`

### From `CLASSIFICATION_PENDING`
- to `REQUIRES_CLARIFICATION`
- to `CLASSIFIED`
- to `INVALIDATED` if intake fraud or fatal defect is discovered

### From `REQUIRES_CLARIFICATION`
- to `CLASSIFICATION_PENDING`
- to `INVALIDATED` if abandoned or fraudulent

### From `CLASSIFIED`
- to `PARTICIPATION_DESIGN_PENDING` for non-trivial proposals
- to `INVALIDATED` if classification was impossible due to fraud or incoherence

### From `PARTICIPATION_DESIGN_PENDING`
- to `PANEL_SELECTION_PENDING`
- to `REQUIRES_CLARIFICATION`
- to `INVALIDATED`

### From `PANEL_SELECTION_PENDING`
- to `PANEL_LOCKED`
- to `REQUIRES_CLARIFICATION`
- to `INVALIDATED`

### From `PANEL_LOCKED`
- to `EVIDENCE_ASSEMBLY`
- to `CHALLENGED`

### From `EVIDENCE_ASSEMBLY`
- to `DELIBERATION_ACTIVE`
- to `CHALLENGED`
- to `DEFERRED`

### From `DELIBERATION_ACTIVE`
- to `PACKET_DRAFTING`
- to `CHALLENGED`
- to `DEFERRED`

### From `PACKET_DRAFTING`
- to `PACKET_PUBLISHED`
- to `CHALLENGED`

### From `PACKET_PUBLISHED`
- to `PARTICIPANT_BODY_FORMATION` when the Participation Plan requires a formed body
- to `PUBLIC_REVIEW_OPEN`
- to `CHALLENGED`

### From `PARTICIPANT_BODY_FORMATION`
- to `PUBLIC_REVIEW_OPEN`
- to `CHALLENGED`
- to `DEFERRED` if the declared formation process cannot complete

### From `PUBLIC_REVIEW_OPEN`
- to `READY_FOR_DECISION`
- to `CHALLENGED`
- to `DEFERRED`

### From `CHALLENGED`
- to `REVIEW_REPAIR`
- to `INVALIDATED`

### From `REVIEW_REPAIR`
- to `PARTICIPATION_DESIGN_PENDING`
- to `PANEL_SELECTION_PENDING`
- to `EVIDENCE_ASSEMBLY`
- to `PACKET_DRAFTING`
- to `PACKET_PUBLISHED`
- to `PUBLIC_REVIEW_OPEN`
- to `INVALIDATED`
- to `DEFERRED`

### From `READY_FOR_DECISION`
- to `DECISION_OPEN`
- to `DEFERRED`

### From `DECISION_OPEN`
- to `APPROVED`
- to `REJECTED`
- to `DEFERRED`
- to `INVALIDATED` if the decision process broke a hard invariant

### From `APPROVED`
- to `ROUTING_PENDING`
- directly to `POST_REVIEW_OPEN` for advisory or non-executing results

### From `ROUTING_PENDING`
- to `ROUTED`
- to `CHALLENGED`
- to `DEFERRED`

### From `ROUTED`
- to `EXECUTION_ACTIVE`
- to `EXECUTION_PAUSED`
- to `CHALLENGED`

### From `EXECUTION_ACTIVE`
- to `EXECUTION_PAUSED`
- to `EXECUTION_COMPLETED`
- to `POST_REVIEW_OPEN` for early outcome review where applicable

### From `EXECUTION_PAUSED`
- to `EXECUTION_ACTIVE`
- to `POST_REVIEW_OPEN`
- to `INVALIDATED`

### From `EXECUTION_COMPLETED`
- to `MONITORING_ACTIVE`
- to `POST_REVIEW_OPEN`

### From `MONITORING_ACTIVE`
- to `LEARNING_PUBLISHED`
- to `POST_REVIEW_OPEN`

### From `LEARNING_PUBLISHED`
- to `POST_REVIEW_OPEN`

### From `REJECTED`
- to `POST_REVIEW_OPEN`
- to `CLOSED`

### From `DEFERRED`
- to `CLASSIFICATION_PENDING`
- to `PANEL_SELECTION_PENDING`
- to `EVIDENCE_ASSEMBLY`
- to `PUBLIC_REVIEW_OPEN`
- to `CLOSED`

### From `POST_REVIEW_OPEN`
- to `CLOSED`
- to `CHALLENGED`
- to `INVALIDATED`

### From `INVALIDATED`
- no forward transition except archival closure mechanics

### From `CLOSED`
- no ordinary forward transition
- a new proposal or review case must be opened instead

---

## 4.4 Mandatory guards by stage

### `PARTICIPATION_DESIGN_PENDING` → `PANEL_SELECTION_PENDING`
Requires:
- a first complete versioned Participation Plan exists,
- expected participation barriers and missing or underrepresented perspectives recorded,
- participation modes selected,
- compensation and accessibility requirements declared,
- participation-audit criteria declared,
- plan creation recorded in audit log.

### `PANEL_SELECTION_PENDING` → `PANEL_LOCKED`
Requires:
- correct layer classification,
- non-trivial determination if applicable,
- minimum panel size satisfied,
- required skill classes satisfied,
- no known unresolved exclusion of mandatory class,
- panel selection recorded in audit log.

### `PACKET_DRAFTING` → `PACKET_PUBLISHED`
Requires:
- required sections present,
- dissent sections included where applicable,
- unknowns and insufficiencies explicitly represented,
- routing analysis present if routing-relevant,
- capture-risk note present,
- reversibility note present,
- packet version assigned.

### `PARTICIPANT_BODY_FORMATION` → `PUBLIC_REVIEW_OPEN`
Requires:
- all invitations declared in the Participation Plan issued,
- sortition completed and verifiable, where used,
- declared roles and conflicts of interest recorded,
- accessibility measures available,
- civic briefs and required translations available,
- participant-body limitations declared,
- body formation recorded in audit log.

### `PUBLIC_REVIEW_OPEN` → `READY_FOR_DECISION`
Requires:
- minimum review window elapsed,
- no blocking unresolved challenge,
- packet still current,
- no detected silent change,
- threshold class already determined,
- a Participation Audit available — full depth for high-impact proposals per the classification binding in `CORE_V03_RECONCILIATION.md` conflict 2, lightweight otherwise.

### `DECISION_OPEN` → `APPROVED`
Requires:
- correct decision mechanism opened,
- threshold satisfied,
- no active-case parameter mutation,
- no blocking invariants broken,
- result logged.

### `APPROVED` → `ROUTING_PENDING`
Requires:
- execution relevance present,
- route determination still needed,
- no advisory-only classification.

### `ROUTING_PENDING` → `ROUTED`
Requires:
- routing record complete,
- route permitted by current framework,
- no unresolved routing challenge,
- hard stops satisfied.

---

## 4.5 Canonical public state export

Internal proposal states are an implementation vocabulary.
They may be refined, split, or extended as the runtime evolves.

Public memory requires a stable vocabulary that does not change when internal mechanics change.
This section defines the canonical public state vocabulary and the mandatory export mapping.

### 4.5.1 Canonical public states

Primary:

- `INTAKE`
- `CLARIFICATION`
- `PROBLEM_DEFINED`
- `PARTICIPATION_DESIGNED`
- `EVIDENCE_OPEN`
- `SPECIALIST_ANALYSIS`
- `ADVERSARIAL_REVIEW`
- `PARTICIPANT_BODY_READY`
- `DELIBERATION`
- `JUDGMENT_READY`
- `JUDGMENT_COMPLETE`
- `EXECUTION_PATH_SELECTED`
- `EXECUTION_AUTHORIZED`
- `EXECUTING`
- `MONITORING`
- `OUTCOME_RECORDED`
- `LEARNING_PUBLISHED`
- `CLOSED`

Supporting:

- `SUSPENDED`
- `REOPENED`
- `REJECTED`
- `MERGED`
- `DUPLICATE`
- `INVALIDATED`
- `SUPERSEDED`

### 4.5.2 Export rule

Every public export, read model, or artifact bundle that exposes proposal lifecycle position must include the canonical public state derived from the mapping below.

Internal states may be exposed additionally, but never instead of the canonical state.

A change to internal states is a runtime change.
A change to the canonical public vocabulary is a meta-governance change.

> The vocabulary was extended from 16 to 18 primary states (`PARTICIPATION_DESIGNED`, `PARTICIPANT_BODY_READY`) by the v0.3 reconciliation. This is a meta-governance change made as a logged founder-phase decision; it is entered in the bootstrap debt register (`BOOTSTRAP_DEBT_REGISTER.md` §2.1) and requires first-constitutional-review ratification. See `CORE_V03_RECONCILIATION.md` §4.2.

See `PUBLIC_STORAGE_MODEL.md` for how canonical state appears in exported public objects.

### 4.5.3 Mapping table

| Internal state | Canonical public state | Note |
|---|---|---|
| `DRAFT` | not exported | Not yet in the public civic loop |
| `SUBMITTED` | `INTAKE` | |
| `CLASSIFICATION_PENDING` | `INTAKE` | |
| `REQUIRES_CLARIFICATION` | `CLARIFICATION` | |
| `CLASSIFIED` | `PROBLEM_DEFINED` | See deviation 4.6.1 |
| `PARTICIPATION_DESIGN_PENDING` | `PARTICIPATION_DESIGNED` | Phase label; see deviation 4.6.4 |
| `PANEL_SELECTION_PENDING` | `EVIDENCE_OPEN` | Case preparation phase |
| `PANEL_LOCKED` | `EVIDENCE_OPEN` | |
| `EVIDENCE_ASSEMBLY` | `EVIDENCE_OPEN` | |
| `DELIBERATION_ACTIVE` | `SPECIALIST_ANALYSIS` | Panel analysis, not public deliberation |
| `PACKET_DRAFTING` | `ADVERSARIAL_REVIEW` | Adversarial synthesis occurs here |
| `PACKET_PUBLISHED` | `DELIBERATION` | Public phase begins |
| `PARTICIPANT_BODY_FORMATION` | `PARTICIPANT_BODY_READY` | Phase label; entered directly upon packet publication when a formed body is required; see deviation 4.6.4 |
| `PUBLIC_REVIEW_OPEN` | `DELIBERATION` | |
| `CHALLENGED` | `DELIBERATION` | Contest is part of public deliberation |
| `REVIEW_REPAIR` | `DELIBERATION` | |
| `READY_FOR_DECISION` | `JUDGMENT_READY` | |
| `DECISION_OPEN` | `JUDGMENT_READY` | Judgment being formed |
| `APPROVED` | `JUDGMENT_COMPLETE` | |
| `REJECTED` | `REJECTED` | |
| `DEFERRED` | `SUSPENDED` | |
| `ROUTING_PENDING` | `JUDGMENT_COMPLETE` | Path not yet selected |
| `ROUTED` | `EXECUTION_PATH_SELECTED` | |
| `EXECUTION_AUTHORIZED` | `EXECUTION_AUTHORIZED` | |
| `EXECUTION_ACTIVE` | `EXECUTING` | |
| `EXECUTION_PAUSED` | `SUSPENDED` | |
| `EXECUTION_COMPLETED` | `MONITORING` | Monitoring phase begins |
| `MONITORING_ACTIVE` | `MONITORING` | Active monitoring phase |
| `LEARNING_PUBLISHED` | `LEARNING_PUBLISHED` | Learning artifacts published |
| `POST_REVIEW_OPEN` | `OUTCOME_RECORDED` | Outcome record produced here |
| `CLOSED` | `CLOSED` | |
| `INVALIDATED` | `INVALIDATED` | |

Supporting canonical states without a dedicated internal proposal state:

- `MERGED` and `DUPLICATE` are intake-resolution results recorded as events, not proposal states.
- `REOPENED` is expressed through the ReviewCase machine.
- `SUPERSEDED` is expressed through versioning events such as `ClassificationSuperseded`.

For proposals that lawfully skip an internal state (the trivial shortcut skips `PARTICIPATION_DESIGN_PENDING`; proposals whose Participation Plan requires no formed body skip `PARTICIPANT_BODY_FORMATION`), the corresponding canonical states simply never appear in the export sequence.

---

## 4.6 Known deviations from the canonical public model

These deviations are recorded deliberately.
They must not be resolved silently; each requires either a runtime change or an explicit decision to keep the deviation.

### 4.6.1 Problem definition gate
`CLASSIFIED` is exported as `PROBLEM_DEFINED`, but the current runtime does not yet enforce a problem-before-solution gate: `Proposal.create` binds title, problem, and proposed action in a single step, and classification does not verify that a validated Problem Definition artifact exists.
`PROTOCOL.md` now requires the gate at the specification level.
Code alignment is a recorded follow-up; until it lands, `PROBLEM_DEFINED` guarantees classification, not a validated problem definition.

### 4.6.2 Execution authorization
`EXECUTION_AUTHORIZED` has been implemented as an internal state (`EXECUTION_AUTHORIZED` in `ProposalState`).
An explicit Execution Mandate (see `DATA_MODEL.md`) is now required after route assignment:
`ROUTED` → `EXECUTION_AUTHORIZED` (via `authorizeExecution`), then → `EXECUTING` (via `startExecution`).
The canonical export mapping: `ROUTED` → `EXECUTION_PATH_SELECTED`, `EXECUTION_AUTHORIZED` → `EXECUTION_AUTHORIZED`.

### 4.6.3 Monitoring and learning
`MONITORING` and `LEARNING_PUBLISHED` are implemented as dedicated internal states.
`EXECUTION_COMPLETED` transitions to `MONITORING_ACTIVE` (exported as `MONITORING`),
then to `LEARNING_PUBLISHED` (exported as `LEARNING_PUBLISHED`),
then to `POST_REVIEW_OPEN` (exported as `OUTCOME_RECORDED`).

The monitoring and learning artifacts in `DATA_MODEL.md` define what must be produced;
`MonitoringRecord` and `LearningArtifact` domain types with corresponding
`MonitoringStarted` and `LearningPublished` events implement the artifact production.

Transition rules:
- `EXECUTION_COMPLETED` → `MONITORING_ACTIVE` (via `startMonitoring`)
- `MONITORING_ACTIVE` → `LEARNING_PUBLISHED` (via `publishLearning`)
- `MONITORING_ACTIVE` → `POST_REVIEW_OPEN` (direct, for early outcome review)
- `LEARNING_PUBLISHED` → `POST_REVIEW_OPEN` (via `openPostReview`)

### 4.6.4 Participation states
`PARTICIPATION_DESIGN_PENDING` and `PARTICIPANT_BODY_FORMATION` — and their canonical exports `PARTICIPATION_DESIGNED` and `PARTICIPANT_BODY_READY` — exist at the specification level only.
The Java runtime (`ProposalState`) implements neither internal state, and the canonical export mapping in code does not include the two new public states.
This is recorded bootstrap debt; see `CORE_V03_RECONCILIATION.md` §8.1.
Until code alignment lands, exported lifecycles omit both states and the participation guards in §4.4 are not mechanically enforced.

---

## 5. Trivial Proposal Shortcut

Some proposals may be classified as trivial.
These may use a reduced path:

`DRAFT`
→ `SUBMITTED`
→ `CLASSIFICATION_PENDING`
→ `CLASSIFIED`
→ `PACKET_PUBLISHED`
→ `PUBLIC_REVIEW_OPEN`
→ `READY_FOR_DECISION`
→ `DECISION_OPEN`
→ (`APPROVED` | `REJECTED` | `DEFERRED`)
→ `CLOSED`

This shortcut is valid only if:
- the proposal is explicitly classified as trivial,
- no non-trivial trigger is present,
- no required plural panel condition applies,
- no challenge reclassifies the proposal as non-trivial.

The shortcut skips `PARTICIPATION_DESIGN_PENDING` and `PARTICIPANT_BODY_FORMATION`: trivial proposals never instantiate participation machinery (see `45_Participation/PARTICIPATION_MODEL.md`).

If challenged successfully, the proposal must return to `CLASSIFICATION_PENDING` or `PANEL_SELECTION_PENDING`.

---

## 6. MetaProposal State Machine

Meta-proposals modify frameworks, parameters, thresholds, review windows, routing rules, panel rules, or other governance mechanics.
They must never alter active cases retroactively.

## 6.1 Canonical states

### `META_DRAFT`
Draft meta-proposal not yet submitted.

### `META_SUBMITTED`
Meta-proposal formally entered.

### `META_CLASSIFICATION_PENDING`
The system is determining whether the change is governance-level or constitutional-level and what meta impact class applies.

### `META_IMPACT_ASSESSMENT`
Formal meta-impact analysis is being produced.
This includes:
- affected framework objects,
- transition risk,
- backward compatibility,
- activation timing,
- anti-capture analysis.

### `META_PANEL_SELECTION_PENDING`
Meta panel construction underway.

### `META_PANEL_LOCKED`
Meta panel locked at required size, typically 5/7/9 depending on impact.

### `META_DELIBERATION_ACTIVE`
Structured deliberation on framework change is active.

### `META_PACKET_DRAFTING`
Meta civic packet is being compiled.

### `META_PACKET_PUBLISHED`
Meta packet published.

### `META_PUBLIC_REVIEW_OPEN`
Public review window for the meta change is open.

### `META_CHALLENGED`
A valid challenge exists against the meta proposal.

### `META_REPAIR`
Repair or rerun is active.

### `META_READY_FOR_DECISION`
All review conditions are satisfied.

### `META_DECISION_OPEN`
Formal meta decision is underway.

### `META_APPROVED`
The meta proposal passed the required threshold.

### `META_REJECTED`
The meta proposal failed the required threshold.

### `ACTIVATION_PENDING`
The change has been approved but is not yet active.
It awaits future epoch activation.

### `ACTIVATED`
New framework or parameter epoch is active for future cases only.

### `META_CLOSED`
Lifecycle complete.

### `META_INVALIDATED`
Meta process was fatally defective.

---

## 6.2 Canonical meta path

`META_DRAFT`
→ `META_SUBMITTED`
→ `META_CLASSIFICATION_PENDING`
→ `META_IMPACT_ASSESSMENT`
→ `META_PANEL_SELECTION_PENDING`
→ `META_PANEL_LOCKED`
→ `META_DELIBERATION_ACTIVE`
→ `META_PACKET_DRAFTING`
→ `META_PACKET_PUBLISHED`
→ `META_PUBLIC_REVIEW_OPEN`
→ `META_READY_FOR_DECISION`
→ `META_DECISION_OPEN`
→ (`META_APPROVED` | `META_REJECTED`)
→ if approved: `ACTIVATION_PENDING` → `ACTIVATED`
→ `META_CLOSED`

---

## 6.3 Meta-specific guards

A meta proposal may not transition to `ACTIVATED` unless:
- activation occurs prospectively,
- activation epoch is recorded,
- no active case is retroactively mutated,
- transition notes are present,
- rollback rules are defined when required,
- all bootstrap hard-fixed restrictions are respected if bootstrap is still active.

---

## 7. Challenge State Machine

Challenges are first-class objects.
A challenge is not just a comment; it is a procedural event that can block, reroute, invalidate, or trigger repair.

## 7.1 Challenge states

### `CHALLENGE_DRAFT`
Challenge drafted but not yet filed.

### `CHALLENGE_FILED`
Challenge has been filed and recorded.

### `ADMISSIBILITY_PENDING`
The system is checking whether the challenge meets filing conditions.

### `ADMITTED`
Challenge is valid for review.

### `DISMISSED`
Challenge failed admissibility or lacked standing, evidence, or relevance.

### `UNDER_REVIEW`
Challenge is being evaluated.

### `UPHELD`
Challenge succeeded.
The parent object must enter repair, rerun, pause, or invalidation.

### `DENIED`
Challenge was reviewed and rejected.

### `REMEDIATION_PENDING`
A successful challenge is awaiting remediation implementation.

### `CHALLENGE_CLOSED`
Challenge lifecycle complete.

---

## 7.2 Challenge path

`CHALLENGE_DRAFT`
→ `CHALLENGE_FILED`
→ `ADMISSIBILITY_PENDING`
→ (`ADMITTED` | `DISMISSED`)
→ if admitted: `UNDER_REVIEW`
→ (`UPHELD` | `DENIED`)
→ if upheld: `REMEDIATION_PENDING`
→ `CHALLENGE_CLOSED`

---

## 7.3 Challenge effects on parent objects

### If challenge is upheld against a proposal before decision
Parent may transition to:
- `REVIEW_REPAIR`
- `PANEL_SELECTION_PENDING`
- `EVIDENCE_ASSEMBLY`
- `PACKET_DRAFTING`
- `INVALIDATED`

### If challenge is upheld during execution
Parent may transition to:
- `EXECUTION_PAUSED`
- `POST_REVIEW_OPEN`
- `INVALIDATED`

### If challenge is upheld against a meta proposal
Parent may transition to:
- `META_REPAIR`
- `META_PANEL_SELECTION_PENDING`
- `META_IMPACT_ASSESSMENT`
- `META_INVALIDATED`

---

## 8. EmergencyAction State Machine

Emergency actions are exceptional and must never silently merge into the ordinary path.

## 8.1 Emergency states

### `EMERGENCY_DECLARED`
Emergency condition has been formally declared.

### `EMERGENCY_ADMISSIBILITY_PENDING`
The system is validating whether emergency criteria are actually satisfied.

### `EMERGENCY_ACCEPTED`
Emergency status is valid.

### `EMERGENCY_REJECTED`
Emergency status is invalid.

### `EMERGENCY_ACTION_AUTHORIZED`
A narrow emergency action has been authorized.

### `EMERGENCY_ACTION_ACTIVE`
Emergency action is currently operating.

### `EMERGENCY_EXPIRING`
Time-bound emergency authority is nearing expiry.

### `EMERGENCY_EXPIRED`
Emergency authority has automatically expired.

### `EMERGENCY_REVIEW_OPEN`
Ex post review is open.

### `EMERGENCY_CONFIRMED`
Ex post review confirms the emergency pathway was valid.

### `EMERGENCY_INVALIDATED`
Emergency pathway is found abusive, defective, or illegitimate.

### `EMERGENCY_CLOSED`
Emergency lifecycle complete.

---

## 8.2 Emergency path

`EMERGENCY_DECLARED`
→ `EMERGENCY_ADMISSIBILITY_PENDING`
→ (`EMERGENCY_ACCEPTED` | `EMERGENCY_REJECTED`)
→ if accepted: `EMERGENCY_ACTION_AUTHORIZED`
→ `EMERGENCY_ACTION_ACTIVE`
→ `EMERGENCY_EXPIRING`
→ `EMERGENCY_EXPIRED`
→ `EMERGENCY_REVIEW_OPEN`
→ (`EMERGENCY_CONFIRMED` | `EMERGENCY_INVALIDATED`)
→ `EMERGENCY_CLOSED`

---

## 8.3 Emergency-specific guards

Emergency transitions require:
- explicit emergency classification,
- narrow scope,
- time limit,
- logged authority basis,
- logged justification,
- mandatory ex post review,
- no normalization into ordinary governance.

No emergency action may transition directly into permanent framework activation without a separate meta-governance process.

---

## 9. ReviewCase State Machine

Review cases evaluate completed or paused outcomes.
They are used for reversibility, audit correction, learning, and systemic adjustment.

## 9.1 Review states

### `REVIEW_OPENED`
Review has been formally opened.

### `REVIEW_EVIDENCE_PENDING`
Outcome evidence is being collected.

### `REVIEW_ACTIVE`
Review deliberation is underway.

### `REVIEW_PACKET_PUBLISHED`
Review packet has been published.

### `REVIEW_DECISION_OPEN`
Review decision process has started.

### `REVIEW_COMPLETED`
Review concluded without requiring invalidation.

### `REVIEW_ESCALATED`
Review discovered a matter that requires challenge, meta-governance, or invalidation.

### `REVIEW_CLOSED`
Review case closed.

---

## 9.2 Review path

`REVIEW_OPENED`
→ `REVIEW_EVIDENCE_PENDING`
→ `REVIEW_ACTIVE`
→ `REVIEW_PACKET_PUBLISHED`
→ `REVIEW_DECISION_OPEN`
→ (`REVIEW_COMPLETED` | `REVIEW_ESCALATED`)
→ `REVIEW_CLOSED`

---

## 10. Invalid Transitions

The following are always invalid unless an explicitly defined and logged emergency rule allows them:

- `DRAFT` → `DECISION_OPEN`
- `SUBMITTED` → `APPROVED`
- `CLASSIFIED` → `DECISION_OPEN`
- `PANEL_SELECTION_PENDING` → `DECISION_OPEN`
- `PANEL_LOCKED` → `APPROVED`
- `DELIBERATION_ACTIVE` → `APPROVED`
- `PACKET_DRAFTING` → `DECISION_OPEN`
- `PUBLIC_REVIEW_OPEN` → `APPROVED`
- `APPROVED` → `EXECUTION_ACTIVE` without valid routing where routing is required
- `META_APPROVED` → `ACTIVATED` without future epoch binding
- any active proposal state → changed threshold regime mid-case
- any active proposal state → changed panel minimum mid-case
- any object → silent state mutation without audit event

If an invalid transition occurs, the relevant object must enter `INVALIDATED`, `META_INVALIDATED`, or equivalent remediation state unless the violation is corrected through a formally logged repair process.

---

## 11. State Invariants

### 11.1 Packet invariant
No proposal may enter `PUBLIC_REVIEW_OPEN`, `READY_FOR_DECISION`, or `DECISION_OPEN` without a published canonical packet.

### 11.2 Panel invariant
No non-trivial proposal may enter deliberative legitimacy states without satisfying minimum panel requirements.

### 11.3 Review-window invariant
No proposal may enter `READY_FOR_DECISION` before the required review window has elapsed, unless a tightly scoped emergency rule explicitly permits abbreviated timing.

### 11.4 Prospective meta invariant
No meta change may become active for an already-running case.

### 11.5 Append-only audit invariant
Every transition must produce an audit event referencing:
- prior state,
- next state,
- transition event,
- actor or mechanism,
- timestamp,
- governing epoch.

### 11.6 Emergency expiry invariant
Emergency authority must expire automatically unless renewed through a separately valid process.

---

## 12. Transition Event Taxonomy

Typical transition events include:
- `proposal_submitted`
- `classification_completed`
- `clarification_requested`
- `panel_locked`
- `evidence_assembled`
- `deliberation_completed`
- `packet_published`
- `review_window_opened`
- `challenge_filed`
- `challenge_upheld`
- `repair_completed`
- `decision_opened`
- `proposal_approved`
- `proposal_rejected`
- `route_assigned`
- `execution_started`
- `execution_paused`
- `execution_completed`
- `review_opened`
- `review_escalated`
- `meta_approved`
- `epoch_activated`
- `emergency_declared`
- `emergency_expired`
- `object_invalidated`
- `object_closed`

This taxonomy may expand, but new event types must not weaken invariants or introduce silent transition paths.

---

## 13. Minimal Runtime Interpretation

At runtime, the state machine should be treated as:
- a validation contract for all write operations,
- a generator of audit events,
- a source of UI visibility,
- a guardrail for orchestration,
- a basis for replay and forensic analysis.

The runtime must reject any write request that implies an invalid transition.

---

## 14. Closing Principle

The civic loop is not only a sequence of ideas.
It is a sequence of valid states.

If the system cannot say exactly where a proposal is,
it cannot honestly claim to govern it.
