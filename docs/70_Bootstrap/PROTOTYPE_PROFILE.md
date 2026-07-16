# PROTOTYPE_PROFILE

> **Note:** This document has been updated to reflect the normative rules in [SYSTEM_PATCH_v2](../99_Reference/SYSTEM_PATCH_v2.md).

## 1. Purpose

This document defines the condensed operational profile of the first prototype.

Its purpose is to provide a short implementation-facing reference answering:
- what the prototype includes,
- what it excludes,
- what the first pilot looks like,
- what the minimal runtime surface is,
- what the first team must actually build,
- and what counts as success, pause, simplification, or stop.

This is the compact companion to:
- `PROTOTYPE_PLAN.md`
- `MINIMUM_VIABLE_PNYX.md`
- `BOOTSTRAP_REALITY_CHECK.md`
- `COMMUNITY_FORMATION.md`
- `TASK_SUITE_v0.md`
- `SCHEMAS.md`
- `EVENT_MODEL.md`
- `READ_MODELS.md`

The prototype profile is a working runtime card, not a constitutional document.

---

## 2. Core Principle

Build the smallest credible civic loop.

The first prototype exists to test:
- packet usefulness,
- procedural visibility,
- challengeability,
- evaluation discipline,
- and repeat use by one real community.

It does **not** exist to prove the full theory.

---

## 3. Prototype Form

The prototype is:

- advisory-first
- single-community or single-working-group
- narrow-domain
- low-finance-surface
- manually supervised where needed
- hostile to silent intervention
- event-traceable
- packet-centric
- repeat-cycle oriented

The prototype is **not**:
- binding governance
- constitutional authority
- mass participation infrastructure
- production treasury
- production identity subsystem
- full emergency runtime
- final anti-capture proof

---

## 4. First Pilot Shape

### Community Profile
- one bounded real group
- real recurring proposal pain
- enough trust for one imperfect cycle
- enough disagreement to make packets useful
- at least one likely challenger
- manageable reputational stakes

### Issue Domain
Use one or two issue classes only, such as:
- local prioritization
- cooperative rule review
- proposal comparison
- volunteer or member-body policy drafting
- public comment synthesis for a bounded community

### Output Mode
- advisory packet only
- no binding vote
- no irreversible decision power
- no treasury-governed distributional commitments in v0

---

## 5. Minimal Runtime Surface

The prototype includes exactly these core surfaces:

1. proposal intake
2. classification
3. panel selection
4. simplified executor classes and basic sandbox suite
5. skill execution and reduced Evidence Packet schema generation
6. limited source validation and banded confidence scoring
7. routing based on confidence and risk, with mandatory human review for selected case classes
8. packet publication
9. challenge submission
10. challenge resolution / packet revision
11. append-only audit events with claim/source traceability
12. readable audit views
13. manual ranking bootstrap and skill evaluation harness
14. visible operator actions

If a new feature does not strengthen one of these,
it is probably outside prototype scope.

---

## 6. Deferred Surface

The prototype defers:

- production treasury automation
- public IP monetization flows
- cryptographic identity proofs
- verifier network
- full market/state execution engine
- advanced emergency enforcement automation
- broad meta-governance runtime
- federation and multi-community scaling
- governance-grade skill procurement

Deferral is part of the design.
It is not incompleteness by accident.

---

## 7. v0 Service Map

The prototype v0 should contain the following services or bounded modules:

### Core
- `proposal-service`
- `classification-service`
- `panel-service`
- `packet-service`
- `challenge-service`

### Trust / Traceability
- `event-store`
- `audit-view-service`
- `operator-log-service`

### Skill Layer
- `skill-registry`
- `skill-runner`
- `evaluation-service`

### UI Surface
- `proposal-intake-ui`
- `packet-view-ui`
- `challenge-ui`
- `audit-summary-ui`
- `operator-workbench-ui`

This may be implemented as fewer deployable apps so long as boundaries remain visible.

---

## 8. v0 Skill Set

The initial live skill panel should default to:

- rights / constitutional caution
- adversarial critique
- implementation / feasibility
- evidence discipline
- anti-capture / power analysis

Optional additions if justified by pilot:
- local context
- economic/resource analysis
- clarity/translation

### Skill Policy
- default prototype skills should be Tier 2 where feasible
- Tier 1 skills may be used only if clearly labeled
- all live skills must have version IDs
- all live skills must run through `TASK_SUITE_v0.md` or explicitly declared subset
- no hidden prompt drift

---

## 9. v0 Canonical Objects

Prototype implementation must support these minimum canonical objects:

- `Proposal`
- `ClassificationRecord`
- `PanelSelectionRecord`
- `Skill`
- `SkillVersion`
- `SkillRun`
- `SkillOutput`
- `Packet`
- `Challenge`
- `AuditEvent`
- `EvaluationRun`
- `OperatorAction`
- `PilotDomainRecord`

These form the minimum runtime truth surface.

---

## 10. v0 Event Surface

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

This is the minimum live event backbone.

---

## 11. v0 Read Models

### Public-Facing
- `CitizenPacketView`
- `CitizenChangeView`
- `CitizenAuditSummaryView`

### Internal / Reviewer
- `CurrentProposalView`
- `CurrentChallengeView`
- `ReviewCaseView`
- `OperatorQueueView`

### Audit / Pilot
- `ProposalTimelineView`
- `OperatorInterventionView`
- `SkillEvaluationView`
- `PilotDomainView`

If a view is not needed for the pilot, do not build it yet.

---

## 12. State Flow

The reduced prototype state flow is:

`Draft -> Submitted -> Classified -> Paneled -> PacketGenerated -> Published -> Challenged? -> Revised or Reaffirmed -> Archived`

Optional exit states:
- `RejectedAsInvalid`
- `EscalatedForStrongerReview`
- `Withdrawn`
- `MarkedInsufficientEvidence`

The state graph must remain small enough to inspect manually.

---

## 13. Human-in-the-Loop Boundaries

Allowed in prototype:
- manual panel confirmation
- manual packet review before publication
- manual challenge triage
- manual evaluation scoring
- manual pilot retrospective synthesis

Forbidden in prototype:
- silent packet rewriting
- undocumented prompt changes mid-cycle
- hidden operator routing
- ad hoc threshold changes on live cases
- fake automation claims
- deletion of unfavorable output traces

Prototype honesty outranks automation appearance.

---

## 14. Complexity Budget

The prototype should stay within a visible complexity budget.

Suggested v0 ceilings:
- 1 pilot community
- 1–2 issue domains
- 5–7 live skills
- 10 live command types
- 10 live event types
- 8–10 active read models
- 1 evaluation suite (`TASK_SUITE_v0`)
- 1 challenge path
- 1 operator queue
- 0 production treasury workflows

When a feature request arrives, ask:
- what prototype hypothesis does this test,
- what complexity does it add,
- what complexity does it remove,
- and can it wait until after cycle two?

---

## 15. Team Shape

Recommended bootstrap team:

- 1 product/runtime integrator
- 1 backend/event model engineer
- 1 UI/workflow engineer
- 1 skill/evaluation engineer
- 1 civic process/facilitation lead
- 1 part-time operator/auditor function

Some roles may overlap,
but overlap must be declared as bootstrap debt.

---

## 16. First Build Sequence

### Phase 0 — Reduction
Produce:
- final prototype profile
- first pilot domain record
- first live skill list
- first read model list
- first event list

### Phase 1 — Internal Simulation
Implement:
- intake
- classification
- panel selection
- packet generation
- challenge path
- audit summary
- evaluation harness

Run on synthetic cases before live community use.

### Phase 2 — Closed Pilot
Run with one real community and one issue class.

### Phase 3 — Repeatability Check
Run second and third cycles before expanding scope.

---

## 17. Success Signals

The prototype is succeeding if:

- the packet is used in real discussion
- at least one real challenge is submitted
- at least one packet revision happens visibly
- users report improved clarity over current process
- packets surface disagreement people say they would otherwise have missed
- operator interventions stay visible and bounded
- skill regression evidence improves or stabilizes
- the same group wants another cycle

Success does not require scale.
It requires repeated usefulness.

---

## 18. Failure Signals

The prototype is failing if:

- packets are not used in actual discussion
- challenge is theoretically available but practically unused
- operator rescue becomes routine
- audit views are ignored because they are unreadable
- skills drift without evaluation discipline
- founders remain the only serious users
- the packet takes more effort than the current process with no visible gain
- no real group wants cycle two
- the runtime becomes more impressive than useful

Failure should trigger:
- simplification,
- pilot reset,
- pause,
- or stop.

---

## 19. Non-Negotiables

Even in prototype, the following remain non-negotiable:

- no silent edits
- no unlogged operator interventions
- no active-case retuning
- no single-skill legitimacy for non-trivial proposals
- no packet publication without provenance
- no revision without visible change notice
- no evaluation-free default trust claim
- no founder narrative inflation

These are the minimum constitutional honesty conditions of v0.

---

## 20. Prototype Deliverables

The first live prototype should produce:

- `PrototypeProfile`
- `PilotDomainRecord`
- `SkillSet_v0`
- `TaskSuite_v0`
- `EvaluationReport_v0`
- `PacketSet_v0`
- `ChallengeLog_v0`
- `AuditViewSet_v0`
- `OperatorInterventionLog_v0`
- `PilotRetrospective_v0`

If these artifacts do not exist, the prototype is not learning in a reusable way.

---

## 21. Go / No-Go Gates

### Build Gate
Proceed only if:
- one real pilot community exists
- one concrete issue class exists
- minimum team exists
- minimum runtime scope is agreed

### Pilot Gate
Proceed only if:
- internal simulation completed
- packet generation works
- challenge path works
- operator actions are visible
- evaluation baseline exists

### Repeatability Gate
Proceed only if:
- at least two cycles completed
- one community returned
- packet was actually referenced
- challenge was actually used
- founder load is survivable

No gate, no scale.

---

## 22. Closing Principle

This profile exists to keep the first build smaller than the theory.

The prototype wins by being:
- narrow,
- visible,
- repeatable,
- challengeable,
- and genuinely useful to one real community.

Everything else can come later.

If the first build cannot satisfy this profile,
the correct move is not more grandeur.
It is less system, more reality.
