# PROTOCOL

> **Note:** This document has been updated to reflect the normative rules in [SYSTEM_PATCH_v1](../99_Reference/SYSTEM_PATCH_v1.md) and [SYSTEM_PATCH_v2](../99_Reference/SYSTEM_PATCH_v2.md).


## 1. Purpose

This document defines the canonical protocol by which civic proposals move through the system.

It specifies:
- the required stages of processing,
- the state transitions allowed at each stage,
- the mandatory outputs produced before a proposal may advance,
- the stricter rules for governance, constitutional, and framework-changing proposals,
- the protocol invariants that prevent ad hoc mutation of legitimacy-critical parameters.

This is a procedural specification, not a UI specification.
It describes what the system must do, not how a particular interface renders it.

---

## 2. Scope

This protocol governs:
- ordinary policy proposals,
- governance proposals,
- constitutional proposals,
- framework-change proposals,
- emergency requests,
- review and challenge flows,
- post-decision audit and review.

It does not fully specify identity issuance, cryptographic proof mechanisms, transport protocols, or product UX.
Those belong to adjacent runtime documents.

---

## 3. Protocol Principles

The protocol must preserve the following properties:
- **human sovereignty**: no model or orchestrator acquires decision legitimacy,
- **visible reasoning**: every meaningful transition produces an auditable trace,
- **plural deliberation**: non-trivial proposals require a minimum panel of five relevant skills,
- **prospective-only change**: legitimacy-critical parameters may not be retuned for an active case,
- **structured disagreement**: the protocol must preserve dissent and unknowns,
- **anti-capture**: hidden leverage points must be minimized and logged,
- **revisability without bypass**: frameworks may be revised only through the civic loop itself.

---

## 4. Canonical Protocol Objects

The protocol assumes the following first-class objects exist:
- `Proposal`
- `ProposalRevision`
- `ProblemDefinition`
- `Framework`
- `ParameterSet`
- `ClassificationResult`
- `ParticipationPlan`
- `PanelSpec`
- `Panel`
- `SkillRun`
- `EvidencePacket`
- `AdversarialSynthesis`
- `BriefingPacket`
- `ParticipantBody`
- `DeliberationWindow`
- `ParticipationAudit`
- `DecisionEvent`
- `DecisionRecord`
- `ExecutionRoute`
- `ExecutionMandate`
- `MonitoringRule`
- `MonitoringEvent`
- `MonitoringReport`
- `OutcomeRecord`
- `LearningRecord`
- `AuditRecord`
- `Challenge`
- `ReviewRecord`

These are logical objects. Their concrete storage format is defined in `DATA_MODEL.md`.

---

## 5. Governance Layers

Every proposal must be assigned exactly one primary governance layer:
- `policy`
- `governance`
- `constitutional`

A proposal may also carry the flags:
- `framework_change`
- `emergency_request`
- `advisory_only`

The assigned layer determines:
- the minimum review window,
- the approval threshold,
- whether extended adversarial review is required,
- whether meta-governance safeguards apply,
- whether activation must be delayed until the next parameter epoch.

Layer classification is a load-bearing protocol act and must be logged.

---

## 6. State Machine Overview

The canonical proposal states are defined in exactly one place: `../80_Runtime/STATE_MACHINE.md` §4.1.
This document does not maintain its own state list.
The stable public-facing vocabulary is the canonical public state export defined in `STATE_MACHINE.md` §4.5.

The protocol stages in this document map onto the canonical states as follows:

| Stage | Canonical states (`STATE_MACHINE.md` §4.1) |
|---|---|
| A — Draft | `DRAFT` |
| B — Submission | `SUBMITTED` |
| C — Intake Validation | `CLASSIFICATION_PENDING`, `REQUIRES_CLARIFICATION` |
| D — Classification | `CLASSIFICATION_PENDING` → `CLASSIFIED` |
| D2 — Participation Design | `PARTICIPATION_DESIGN_PENDING` (non-trivial proposals; trivial shortcut skips) |
| E — Panel Specification | `PANEL_SELECTION_PENDING` |
| F — Panel Lock | `PANEL_LOCKED` |
| G — Skill Analysis | `EVIDENCE_ASSEMBLY`, `DELIBERATION_ACTIVE` |
| H — Adversarial Synthesis | `PACKET_DRAFTING` |
| I — Briefing Publication | `PACKET_DRAFTING` → `PACKET_PUBLISHED` |
| I2 — Participant Body Formation | `PARTICIPANT_BODY_FORMATION` (only when the Participation Plan requires a formed body) |
| J — Public Deliberation Window | `PUBLIC_REVIEW_OPEN` (with `CHALLENGED`, `REVIEW_REPAIR`) |
| K — Decision Event | `READY_FOR_DECISION` → `DECISION_OPEN` → (`APPROVED` \| `REJECTED` \| `DEFERRED`) |
| L — Execution Routing | `ROUTING_PENDING` → `ROUTED` |
| M — Audit Publication | cross-cutting; audit events accompany every transition |
| N — Post-Decision Review | `EXECUTION_ACTIVE` / `EXECUTION_PAUSED` / `EXECUTION_COMPLETED` → `POST_REVIEW_OPEN` → `CLOSED` |
| O — Execution Monitoring | `EXECUTION_ACTIVE`, `EXECUTION_COMPLETED` (exported as `MONITORING`) |
| P — Outcome Recording | `POST_REVIEW_OPEN` (exported as `OUTCOME_RECORDED`) |
| Q — Learning Publication | precedes `CLOSED` (exported as `LEARNING_PUBLISHED`) |

Not every proposal reaches every stage.
Emergency and advisory flows may terminate earlier.

---

## 7. Global Protocol Invariants

The following invariants must hold across all flows.

### 7.1 Immutable Proposal Identity
A proposal receives a stable identifier at submission.
Substantive changes after submission create `ProposalRevision` objects, not silent mutation.

### 7.2 Logged Classification
No proposal may reach panel assembly without a logged `ClassificationResult`.

### 7.3 Minimum Deliberative Quorum
Any non-trivial proposal must have a panel of at least five relevant skills.

### 7.4 Required Skill Classes
A non-trivial panel must include at least:
- rights / constitutional,
- implementation / feasibility,
- economic / resource,
- anti-capture / audit,
- adversarial critique.

### 7.5 Briefing Before Decision
No public decision event may open before publication of a `BriefingPacket`.

### 7.6 Audit Trace Completeness
Every meaningful transition must append to the `AuditRecord`.

### 7.7 Prospective Parameter Change Only
Any change to review windows, thresholds, skill requirements, escalation criteria, emergency rules, or audit minimums must apply only to future proposals.

### 7.8 Emergency Is Never Silent Default
An emergency path must be explicit, time-bounded, and auto-reviewed.

### 7.9 Problem Before Solution
No proposal may complete classification without a validated `ProblemDefinition` artifact (see `../90_Information/DATA_MODEL.md`).
The problem is defined before a solution, funding request, or execution route is promoted.
A proposed solution is a distinct field; it may be absent at submission and must never substitute for the problem statement.

### 7.10 Participation Audit Before Decision
No non-trivial proposal may reach decision readiness without an available `ParticipationAudit` artifact (see `../45_Participation/PARTICIPATION_MODEL.md`).
The required audit depth scales with classification: full audit for high-impact proposals per the classification binding in `../99_Reference/CORE_V03_RECONCILIATION.md` conflict 2, lightweight audit otherwise — the proportionality resolution of conflict 13.
The audit does not require demographic mirroring; it requires transparent awareness of who was eligible, informed, present, and absent.
See `../80_Runtime/INVARIANTS.md` §14 (participation invariants) and `../99_Reference/CORE_V03_RECONCILIATION.md`.

---

## 8. Ordinary Proposal Protocol

### 8.1 Stage A — Draft
**Input:** raw civic text or structured submission.

**Minimum required fields:**
- title,
- problem statement,
- requested change (optional at draft; must remain distinct from the problem statement),
- affected scope,
- proposer identity or eligibility proof,
- whether the proposal is claimed to be urgent.

**Output:** `Proposal(DRAFT)`

### 8.2 Stage B — Submission
The draft becomes a submitted proposal.
Submission freezes the initial proposal body and assigns:
- proposal id,
- submission timestamp,
- originating civic context,
- current parameter epoch,
- current framework epoch.

**Output:** `Proposal(SUBMITTED)`

### 8.3 Stage C — Intake Validation
The intake layer checks for:
- minimum completeness,
- malformed or self-contradictory requests,
- duplicate or near-duplicate proposals,
- scope mismatch,
- bootstrap scope exclusion,
- invalid urgency claims.

A proposal may be:
- accepted,
- rejected,
- returned for revision,
- merged with an existing proposal cluster.

Rejected proposals remain traceable and auditable.

**Output:** `Proposal(CLASSIFICATION_PENDING)`, `Proposal(REQUIRES_CLARIFICATION)`, or `Proposal(INVALIDATED)` for fraudulent or fatally defective intake

### 8.4 Stage D — Classification
Classification may not complete without a validated `ProblemDefinition` artifact (invariant 7.9).
If the problem cannot be stated independently of the proposed solution, the proposal returns for revision.

The system determines:
- governance layer,
- whether it changes active frameworks,
- whether constitutional spillover is likely,
- whether the proposal is advisory or binding,
- whether emergency routing is permissible,
- whether panel escalation above five is required.

**Output:** `ClassificationResult` (with a reference to the validated `ProblemDefinition`)

Required fields:
- layer,
- rationale,
- spillover notes,
- emergency eligibility,
- bootstrap-compatibility flag,
- escalation recommendation,
- required executor type,
- minimum evidence burden,
- admissible review paths.

### 8.4b Stage D2 — Participation Design
Every non-trivial proposal produces a `ParticipationPlan` before panel specification.
Trivial proposals skip this stage through the trivial shortcut; participation-design effort scales with classification tier.

The plan must define:
- affected civic scopes and eligible participant population,
- expected participation barriers and missing or underrepresented perspectives,
- participation modes (open, affected-party, targeted invitation, civic jury or sortition body, institutional, monitoring),
- whether a formed participant body is required for deliberation and judgment,
- compensation and accessibility requirements,
- civic translation requirements,
- participation windows and expected time commitment,
- participation-audit criteria.

The plan is versioned and may be revised as evidence and impact understanding evolve; every revision preserves history.
Content requirements are defined in `../45_Participation/PARTICIPATION_MODEL.md`.

**Output:** `ParticipationPlan`

### 8.5 Stage E — Panel Specification
The orchestration layer maps the classification result to a `PanelSpec`.

A valid `PanelSpec` must define:
- required skill classes,
- allowed executor types per skill,
- optional additional classes,
- minimum quorum,
- whether comparative multi-run is required,
- evidence sufficiency requirements,
- admissibility and independence requirements,
- fallback and escalation rules,
- whether minority-rights review is mandatory,
- whether local-impact review is mandatory.

**Output:** `PanelSpec`

### 8.6 Stage F — Panel Lock
The system instantiates the panel from the skill registry.

Panel lock freezes:
- seat count,
- selected skill versions,
- input packet references,
- active parameter set,
- active framework set,
- timing rules.

After panel lock, legitimacy-critical parameters may not be altered for the active proposal.

**Output:** `Panel(panel_locked)`

### 8.7 Stage G — Skill Analysis
Each skill receives:
- proposal text,
- allowed context bundle,
- evidence bundle,
- governing framework references,
- output schema,
- deadline,
- run id.

Each skill must return a valid `EvidencePacket` (or schema-compatible equivalent) containing:
- plain-language summary,
- core judgment,
- supporting reasons with claim-to-source traceability,
- explicit uncertainties and evidence gaps,
- objections or constraints,
- rights / capture / feasibility / evidence notes as relevant,
- derived confidence score based on explicit signals,
- specific citations for all material claims,
- explicit `unknown` where warranted.

Human experts acting as executors must adhere to these exact same protocol-bound artifact requirements rather than producing free-form extra-procedural exceptions.

**Output:** one or more `EvidencePacket` objects.

### 8.8 Stage H — Adversarial Synthesis
The synthesis step does not replace individual outputs.
It creates a structured conflict map.

Required synthesis fields:
- strongest case in favor,
- strongest case against,
- unresolved unknowns and evidence gaps,
- minority view and preserved disagreement,
- capture-risk note,
- confidence dispersion across packets,
- reversibility note,
- implementation note,
- evidence sufficiency note,
- recommended execution route,
- reason for route recommendation.

**Output:** `AdversarialSynthesis`

### 8.9 Stage I — Briefing Publication
The public briefing packet must include:
- proposal summary,
- full proposal text,
- classification,
- panel composition (skills and executor types),
- key arguments and their claim/source basis,
- dissent,
- evidence status,
- thresholds and timing,
- execution implications,
- audit links.

No decision may proceed without a published briefing.

**Output:** `BriefingPacket`

### 8.9b Stage I2 — Participant Body Formation
Entered only when the `ParticipationPlan` requires a formed participant body — a civic jury, a sortition body, or an invited affected-party body.

Formation must complete the declared process:
- declared invitations issued,
- sortition completed and verifiable where used (see `../45_Participation/SORTITION.md`),
- roles and conflicts of interest recorded,
- accessibility measures and required translations available,
- participant-body limitations declared.

The participant body must not be confused with the skill panel: skills analyse, participants deliberate and judge.
Formation does not require every invited person to accept; it requires transparent completion of the declared process.

**Output:** `ParticipantBody`

### 8.10 Stage J — Public Deliberation Window
The proposal enters a time-bounded public scrutiny phase.

During this phase the system may accept:
- public comments,
- challenges,
- evidence supplements,
- requests for clarification,
- requests for rerun under valid procedural grounds.

The deliberation window duration is read from the active `ParameterSet` and may not be altered for the active proposal.

**Output:** `DeliberationWindow(open)`

### 8.11 Stage K — Decision Event
When the deliberation window closes, the system computes decision readiness.

Checks include:
- quorum,
- threshold rule,
- evidentiary sufficiency,
- completion of mandatory human review where required (e.g., low confidence, high conflict, high rights impact, constitutional cases, emergency justification),
- any required multi-stage approvals,
- any constitutional confirmation requirement,
- whether unresolved procedural challenges block decision,
- for non-trivial proposals, availability of a `ParticipationAudit` at the depth required by classification (invariant 7.10).

**Output:** `DecisionEvent` and `DecisionRecord`

### 8.12 Stage L — Execution Routing
An execution path is defined along two orthogonal axes.
Both must be recorded.

**Mechanism** (how the outcome is delivered):
- market thread,
- state thread,
- hybrid thread,
- advisory-only outcome,
- deferred execution pending external condition.

**Executor form** (what kind of actor delivers it):
- public or institutional actor,
- civic commons (open, commons-based production),
- public-interest venture,
- cooperative or community-owned structure,
- contracted private or nonprofit executor,
- hybrid form.

Execution route must record:
- mechanism and executor form,
- why this combination fits the values layer,
- why the other combinations were not chosen,
- what implementation boundaries apply,
- what review triggers reopen the case.

Where execution follows, the route is completed by an `ExecutionMandate` (see `../90_Information/DATA_MODEL.md`) that binds the executor to explicit permitted actions, resources, milestones, and public-return obligations.
No approval creates authority beyond its mandate.

**Output:** `ExecutionRoute` (and `ExecutionMandate` where execution follows)

### 8.13 Stage M — Audit Publication
A complete `AuditRecord` is published or updated.

Minimum fields:
- proposal id,
- revisions,
- classification,
- panel and skill versions,
- argument map,
- dissent,
- timing windows,
- threshold used,
- decision outcome,
- execution route,
- post-decision review conditions.

### 8.14 Stage N — Post-Decision Review
After execution begins or concludes, the proposal may enter review on triggers such as:
- rights impact deviation,
- execution failure,
- new evidence or source invalidation,
- legal change,
- severe miscalibration or executor defect,
- jurisdiction mismatch,
- capture-risk escalation,
- constitutional challenge,
- sunset or scheduled review.

**Output:** `ReviewRecord`

### 8.15 Stage O — Execution Monitoring
Every execution mandate is monitored against its declared obligations.

Monitoring must rely on deterministic, inspectable mechanisms wherever possible:
- deadlines and milestone events,
- budget and treasury entries,
- signed deliveries and procurement records,
- operational metrics and compliance checks.

Each observed obligation produces a `MonitoringEvent` recording expected value, observed value, evidence, deviation, and escalation status.
Monitoring rules define acceptable deviation, warning conditions, automatic suspension triggers, and when renewed judgment is required.

Models may summarize monitoring data.
They MUST NOT be the sole authority deciding whether an objective commitment was satisfied.

**Output:** `MonitoringEvent` stream and periodic `MonitoringReport`

### 8.16 Stage P — Outcome Recording
The loop is not closed by a decision; it is closed by reality.

An `OutcomeRecord` captures what actually happened:
- original objective and expected result,
- observed result and evidence,
- costs, benefits, harms, and unintended effects,
- confidence and unresolved questions,
- recommendation for closure, continuation, correction, or reopening.

The outcome record MUST NOT rewrite the original expectations.
A proposal without an outcome record — or a documented inability to determine one — is an incomplete civic loop.

**Output:** `OutcomeRecord`

### 8.17 Stage Q — Learning Publication
Before closure, the case publishes what was learned:
- expected-versus-actual comparison,
- what was predicted and what occurred,
- what failed and what was corrected,
- what future cases and future models should know.

**Output:** `LearningRecord`

---

## 9. Framework-Change Protocol

Framework-change proposals follow the ordinary protocol with additional constraints.

### 9.1 Trigger Conditions
A proposal is marked `framework_change` if it modifies:
- timing rules,
- thresholds,
- panel requirements,
- classification rules,
- emergency rules,
- audit minimums,
- skill registry governance,
- other legitimacy-critical processing logic.

### 9.2 Additional Required Outputs
The panel must also produce:
- constitutional impact note,
- meta-capture note,
- incentive distortion note,
- migration note,
- prospective activation note,
- parameter interaction note.

### 9.3 Additional Safeguards
Framework-change proposals require:
- stronger adversarial review,
- longer public scrutiny,
- higher approval thresholds,
- no same-case activation,
- activation at next parameter epoch only,
- explicit rollback plan where possible.

### 9.4 Recursive Constraint
A framework change may not bypass the current framework in order to change the framework.

---

## 10. Emergency Protocol

Emergency requests are exceptional protocol paths.

### 10.1 Emergency Eligibility
An emergency request must explicitly claim:
- immediate harm risk,
- narrow scope,
- time sensitivity,
- why ordinary timing would fail.

### 10.2 Emergency Classification
The classifier must determine:
- whether emergency treatment is valid,
- what normal safeguards remain mandatory,
- when auto-expiry occurs,
- which ex post review is required.

### 10.3 Emergency Minimums
Even in emergency mode, the protocol still requires:
- logged classification,
- explicit scope,
- explicit time bound,
- audit trace and evidence discipline (elevated status never bypasses these),
- post-hoc review,
- no silent conversion into ordinary rule.

### 10.4 Expiry
Emergency outcomes must expire automatically unless ratified through the ordinary or meta-governance path.

---

## 11. Challenge and Rerun Protocol

Challenges are formal procedural objections, not generic disagreement.

### 11.1 Valid Challenge Grounds
Examples:
- misclassification,
- missing required skill class,
- invalid evidence handling,
- hidden parameter mutation,
- audit incompleteness,
- conflict-of-interest in panel assembly,
- emergency abuse,
- briefing omission of dissent.

### 11.2 Challenge Outcomes
A challenge may result in:
- no action,
- annotation only,
- partial rerun,
- full rerun,
- suspension,
- voiding of a decision.

### 11.3 Rerun Rules
A rerun must create new objects rather than overwrite prior outputs:
- new `Panel` or `SkillRun` ids,
- new `BriefingPacket` revision,
- preserved prior audit history.

---

## 12. Protocol Message Contracts

The following logical message contracts are required between layers.

### 12.1 Intake Request
```json
{
  "proposal_id": "prop_...",
  "title": "...",
  "problem_statement": "...",
  "requested_change": "...",
  "scope": "municipal|regional|national|other",
  "claimed_urgency": false,
  "origin_context_id": "ctx_...",
  "submitted_at": "ISO-8601"
}
```

### 12.2 Classification Result
```json
{
  "proposal_id": "prop_...",
  "layer": "policy|governance|constitutional",
  "framework_change": false,
  "emergency_eligible": false,
  "advisory_only": false,
  "escalation_level": 5,
  "rationale": ["..."],
  "spillover_notes": ["..."]
}
```

### 12.3 Panel Spec
```json
{
  "proposal_id": "prop_...",
  "required_skill_classes": [
    "rights_constitutional",
    "implementation_feasibility",
    "economic_resource",
    "anti_capture_audit",
    "adversarial_critique"
  ],
  "optional_skill_classes": ["local_impact"],
  "minimum_quorum": 5,
  "multi_run_required": false,
  "evidence_mode": "standard"
}
```

### 12.4 Skill Output
```json
{
  "skill_run_id": "run_...",
  "proposal_id": "prop_...",
  "skill_class": "anti_capture_audit",
  "judgment": "...",
  "reasons": ["..."],
  "constraints": ["..."],
  "unknowns": ["..."],
  "confidence": "low|medium|high",
  "references": ["..."]
}
```

### 12.5 Briefing Packet
```json
{
  "briefing_id": "brief_...",
  "proposal_id": "prop_...",
  "summary": "...",
  "classification": {"layer": "policy"},
  "panel": {"size": 5},
  "arguments_for": ["..."],
  "arguments_against": ["..."],
  "unknowns": ["..."],
  "minority_view": ["..."],
  "route_recommendation": "market|state|hybrid|advisory",
  "audit_record_id": "audit_..."
}
```

---

## 13. Parameter Epochs

Legitimacy-critical parameters should be versioned by epoch.

A proposal binds to the parameter epoch active at submission.
That binding persists through the life of the proposal unless the proposal is withdrawn and resubmitted.

Parameters that should be epoch-versioned include:
- review windows,
- thresholds,
- skill class requirements,
- classification rules,
- emergency limits,
- audit minimums,
- bootstrap scope limits.

---

## 14. Protocol Failure Modes

The protocol must explicitly handle the following failures:
- insufficient panel formation,
- missing required evidence packet,
- synthesis collapse into false consensus,
- contradictory classification,
- invalid timing state,
- audit write failure,
- execution route ambiguity,
- emergency misuse,
- challenge backlog.

Each failure must produce a visible status rather than silent degradation.

---

## 15. Minimum Implementation Priorities

A minimal deployable implementation of this protocol should support:
1. stable proposal objects,
2. classification,
3. minimum five-skill panel assembly,
4. logged evidence packets,
5. structured briefing publication,
6. fixed review windows,
7. decision recording,
8. audit publication,
9. challenge intake,
10. scheduled review.

Everything else may be layered on later.

---

## 16. Closing Rule

The protocol exists to make civic judgment procedural without making it opaque.

It should transform:
- raw demand into bounded proposal,
- bounded proposal into structured deliberation,
- structured deliberation into public judgment,
- public judgment into constrained execution,
- constrained execution into auditable civic memory.
