# PROTOTYPE_PLAN

> **Note:** This document has been updated to reflect the normative rules in [SYSTEM_PATCH_v2](../99_Reference/SYSTEM_PATCH_v2.md).

## 1. Purpose

This document defines the first implementation path for the system.

Its purpose is to convert the specification from a purely architectural and constitutional artifact into a testable prototype program.

The prototype is not intended to prove the full system.
It is intended to answer a narrower set of questions:

- Can the core civic loop run end-to-end?
- Can a proposal be processed into a useful civic packet?
- Can plural skill outputs produce better public reasoning than a single assistant?
- Can the audit surface remain understandable at small scale?
- Can challenge and review pathways work without collapsing into operator discretion?
- Can a small founding group use the system without the system becoming pure theater?
- Can the system generate enough usefulness to justify continued development?

This document complements:
- `MINIMUM_VIABLE_PNYX.md`
- `GOVERNANCE.md`
- `ARCHITECTURE.md`
- `PROTOCOL.md`
- `STATE_MACHINE.md`
- `API_SPEC.md`
- `SKILL_EVALUATION.md`
- `BOOTSTRAP_PARAMETERS.md`
- `CONSTITUTIONAL_BOOTSTRAP.md`

The prototype is a learning instrument.
It must not be mistaken for constitutional completion.

---

## 2. Core Principle

Prototype before enlargement.

The system should not attempt to implement the full constitutional machine in the first build.
It should implement the smallest runtime that can honestly test:
- usefulness,
- legibility,
- challengeability,
- epistemic plurality,
- operator restraint,
- and bootstrap viability.

The prototype must preserve core invariants.
It may defer full subsystem depth.

---

## 3. Prototype Goals

The prototype should test five primary hypotheses.

### 3.1 Packet Usefulness Hypothesis
A plural skill loop produces civic packets that are more useful, more honest about uncertainty, and more challengeable than a single-model summary.

### 3.2 Workflow Integrity Hypothesis
A proposal can move through intake, classification, paneling, packet generation, challenge, revision, and publication without hidden operator shortcuts.

### 3.3 Audit Legibility Hypothesis
A small group can understand what happened from the public and auditor audit views without reading raw logs.

### 3.4 Skill Hardening Hypothesis
Tier 1 and Tier 2 skills can be iteratively improved through evaluation without drifting into pure prompt folklore.

### 3.5 Adoption Hypothesis
A small real community will actually use the system for bounded advisory output if it solves a real problem better than existing informal methods.

A prototype that fails these hypotheses should not be scaled by rhetoric.

---

## 4. What the Prototype Is

The first prototype should be:

- advisory-first,
- narrow in scope,
- low in treasury complexity,
- explicit about bootstrap debt,
- auditable,
- manually supervised where necessary,
- hostile to silent automation,
- designed for iteration and deletion.

The prototype is a **small civic reasoning machine**,
not yet a full sovereign civic runtime.

---

## 5. What the Prototype Is Not

The first prototype is not:

- a full constitutional state machine deployment,
- a complete cryptographic identity system,
- a production treasury,
- a full market/state execution engine,
- a mass-participation platform,
- a final legitimacy proof,
- a proof that anti-capture is solved,
- a substitute for community formation.

The prototype must resist prestige inflation.

---

## 6. Prototype Scope

The recommended initial scope is:

- one bounded community or working group,
- one or two issue domains,
- advisory outputs only,
- no binding votes,
- no constitutional changes,
- no real emergency governance power,
- no irreversible public decisions,
- no large financial commitments.

Good first domains are those where:
- people already have recurring civic coordination pain,
- the problem is real but not existential,
- comparison with current practice is possible,
- packet quality matters,
- challenge and revision are meaningful.

Examples might include:
- local budgeting discussion support,
- neighborhood issue prioritization,
- cooperative policy drafting,
- volunteer association governance support,
- public proposal analysis for an existing community.

---

## 7. Minimum Runtime Surface

The first prototype should implement only the following minimal runtime surface:

1. proposal intake
2. non-trivial/trivial classification
3. basic policy/governance distinction
4. panel selection with minimum 5-skill quorum for non-trivial proposals
5. executor abstraction and basic sandbox flow
6. skill execution and schema-validated Evidence Packet generation
7. citation and source handling
8. derived confidence and routing based on confidence and risk
9. packet publication
10. challenge submission
11. packet revision or reaffirmation
12. append-only audit trail with claim/source traceability
13. readable audit views
14. minimal ranking store and skill evaluation harness
15. human-review integration and basic operator controls

Everything else should be deferred unless needed for the actual pilot.

---

## 8. Deferred Subsystems

The following should normally be deferred from prototype v1 unless they are directly required by the pilot:

- full treasury partitions
- full public IP revenue routing
- cryptographic identity proofs
- verifier network
- advanced public funding mechanisms
- formal emergency enforcement automation
- deep market/state execution routing
- extensive meta-governance
- high-granularity treasury challenge flows
- full constitutional review apparatus

Deferral is not abandonment.
It is scope honesty.

---

## 9. Prototype Architecture Profile

The recommended implementation profile is:

### 9.1 Core Services
- proposal service
- classification service
- panel selection service
- packet generation service
- audit event store
- audit view service
- challenge service
- skill registry and evaluation service

### 9.2 Skill Layer
- Tier 1 and Tier 2 skills only
- a small fixed set of classes
- cross-model testing where affordable
- no governance-grade overclaim

### 9.3 Human Oversight
- explicit operator actions
- explicit reviewer actions
- visible intervention markers
- no hidden manual edits

### 9.4 Storage
- append-only event stream
- derived read models for packets and audit views
- versioned skill definitions
- versioned packet records

### 9.5 Interfaces
- simple proposal intake UI or form
- packet view
- challenge submission view
- audit timeline view
- operator dashboard with strong visibility boundaries

---

## 10. Prototype Skill Set

The prototype should begin with a small fixed skill set, likely:

- rights / constitutional caution
- adversarial critic
- implementation / feasibility
- evidence discipline
- anti-capture / power analysis

Optional sixth or seventh skills may include:
- economic/resource analysis
- local impact/contextualizer
- clarity/translation aid

The prototype should prefer:
- forkable skills,
- public prompts or scaffolds,
- model diversity where possible,
- explicit evaluation status.

The skill question in prototype is not "best model."
It is "best observable epistemic behavior under bounded cost."

---

## 11. Skill Evaluation in Prototype

Every prototype skill should have:

- version ID,
- tier label,
- supported model notes,
- small task suite,
- failure ledger,
- regression rerun before promotion,
- known limitations,
- challenge channel.

Prototype default skills should be Tier 2 where feasible.
Tier 1 may be allowed for experimentation but should be clearly labeled as not publicly trusted by default.

---

## 12. Prototype Data and Event Model

At minimum, the prototype should implement the following core objects:

- `Proposal`
- `ClassificationRecord`
- `PanelSelectionRecord`
- `SkillRun`
- `Packet`
- `Challenge`
- `AuditEvent`
- `SkillVersion`
- `EvaluationRun`
- `OperatorAction`

Each object should be:
- versioned where relevant,
- traceable,
- linked to the audit timeline,
- reconstructable for review.

---

## 13. Prototype State Flow

The minimal state flow should be:

`Draft -> Submitted -> Classified -> Paneled -> PacketGenerated -> Published -> Challenged? -> Revised or Reaffirmed -> Archived`

Optional side paths:
- `RejectedAsInvalid`
- `EscalatedForStrongerReview`
- `Withdrawn`
- `MarkedInsufficientEvidence`

The first prototype should keep the state graph small enough to test in practice.

---

## 14. Success Metrics

The prototype should be judged against explicit metrics.

### 14.1 Usefulness
- users report packet helped understanding
- users identify at least one disagreement or unknown they would otherwise have missed
- packet is used in actual discussion

### 14.2 Procedural Integrity
- no silent edits
- all revisions visible
- all operator interventions recorded
- no active-case retuning

### 14.3 Challengeability
- challenges are submitted by real users
- challenge path is understandable
- challenged packets visibly change or are publicly reaffirmed with rationale

### 14.4 Skill Quality
- regression scores improve or stabilize
- severe failure classes decline
- model-specific fragility becomes visible

### 14.5 Adoption
- repeat use across more than one cycle
- at least one community asks to use the system again
- users prefer packets over ad hoc chat or thread chaos

---

## 15. Failure Metrics

The prototype should be considered failing if one or more of the following occur repeatedly:

- packets are not used in real discussion,
- users find the packet harder to understand than the source issue,
- challenge paths go unused because they are too complex,
- operator interventions become routine and opaque,
- the same small insider group controls all meaningful actions,
- skill outputs collapse into false consensus,
- audit views are ignored because they are unreadable,
- skill evaluation is not maintained,
- the prototype requires more explanation than value,
- no real community wants a second cycle.

A prototype that fails should be simplified, redesigned, or stopped.

---

## 16. Pilot Community Criteria

The first pilot community should ideally have:

- a real recurring decision or proposal problem,
- enough trust to try a new process,
- enough disagreement to make the packet useful,
- enough patience for visible iteration,
- not so much scale that failure becomes reputationally catastrophic,
- at least one motivated challenger,
- at least one skeptical participant,
- a practical reason to care about better reasoning output.

The wrong first pilot is one chosen for prestige rather than fit.

---

## 17. Community Formation Strategy

The prototype should not assume community already exists.
It should deliberately form one around a concrete need.

Recommended path:
1. identify a small real group with recurring proposal pain,
2. run one advisory cycle on a live issue,
3. compare output against their normal process,
4. collect structured feedback,
5. revise the runtime,
6. repeat with the same group before expanding.

Adoption grows from repeated usefulness, not from constitutional prose.

---

## 18. Prototype Team Shape

A realistic first team may include:

- 1 product/runtime integrator,
- 1 backend/event model engineer,
- 1 UI or workflow engineer,
- 1 skill/evaluation engineer,
- 1 civic process designer or researcher,
- 1 part-time operator/auditor function,
- optional community facilitator.

Small teams may combine roles,
but role concentration must be explicit.

The prototype should remain small enough that the team can actually learn from the build.

---

## 19. Prototype Implementation Phases

### Phase 0 — Paper Reduction
Reduce the full spec into:
- core invariants,
- prototype states,
- minimal object schemas,
- first skill set,
- first audit views,
- first pilot domain.

Deliverable:
- prototype profile sheet.

### Phase 1 — Internal Simulation
Use synthetic proposals and internal users to run:
- classification,
- panel selection,
- packet generation,
- challenge flow,
- audit visibility.

Deliverable:
- internal failure log.

### Phase 2 — Closed Pilot
Run with one real community on one bounded issue domain.

Deliverables:
- real packet set,
- real challenge traces,
- user feedback,
- operator log,
- skill regression record.

### Phase 3 — Repeatability Test
Run a second and third cycle with the same community or a second similar one.

Deliverables:
- comparative metrics,
- revised runtime surface,
- adoption evidence or failure evidence.

Only after this should broader scaling be considered.

---

## 20. Prototype Artifacts

The prototype should produce the following artifacts:

- `PrototypeProfile`
- `PilotDomainDefinition`
- `SkillSet_v0`
- `TaskSuite_v0`
- `EvaluationReport_v0`
- `PacketSet_v0`
- `ChallengeLog_v0`
- `AuditViewSet_v0`
- `OperatorInterventionLog_v0`
- `PilotRetrospective_v0`

If these artifacts do not exist, the prototype is not generating learning.

---

## 21. Human-in-the-Loop Boundaries

The prototype may rely on humans more than the final architecture,
but this must remain visible.

Allowed prototype practices:
- manual panel confirmation,
- human packet review before publication,
- manual challenge triage,
- manual evaluation scoring where automation is immature.

Forbidden prototype practices:
- silent packet rewriting,
- undocumented prompt changes mid-cycle,
- invisible operator routing,
- hidden case-specific threshold changes,
- manually deleting unfavorable outputs,
- fake automation claims.

Prototype honesty matters more than automation prestige.

---

## 22. Prototype Technical Choices

The prototype should prefer:
- boring infrastructure,
- visible logs,
- small service boundaries,
- explicit versioning,
- reversible deployment,
- simple read models,
- local or controlled-model execution where feasible,
- low operational fragility.

The prototype should avoid:
- premature microservice explosion,
- unnecessary cryptographic complexity,
- over-abstracted plugin systems,
- full-scale treasury automation,
- pretending scale requirements before scale exists.

Build the smallest machine that can reveal the real problems.

---

## 23. Prototype Review Discipline

Every prototype cycle should include a structured review asking:

- What part of the packet was genuinely useful?
- What part confused people?
- Where did skills collapse into sameness?
- What did challengers catch that the panel missed?
- What did operators have to do manually?
- Which invariant came under pressure?
- What should be removed from the runtime surface?
- What needs deeper specification?
- What needs code instead of prose?

The review should not grade itself on elegance.
It should grade itself on what failed in contact with use.

---

## 24. Exit, Pause, and Stop Conditions

The prototype must define conditions for:
- continuing,
- pausing,
- simplifying,
- stopping.

Reasons to pause or stop include:
- no meaningful community use,
- repeated opaque operator intervention,
- audit unreadability,
- no challenge participation,
- skill evaluation rot,
- burnout in the founding team,
- unresolved concentration in practice,
- legal or reputational risk beyond prototype scope.

Stopping is not failure.
Continuing without signal is failure.

---

## 25. Cross-References

This document imposes requirements on:

### 25.1 MINIMUM_VIABLE_PNYX
Must define the prototype's non-negotiable constitutional floor.

### 25.2 SKILL_EVALUATION
Must provide the first real test harness for prototype skills.

### 25.3 AUDIT_VIEWS
Must provide at least one citizen-facing and one auditor-facing view.

### 25.4 API_SPEC
Must be reduced to the minimal prototype surface.

### 25.5 STATE_MACHINE
Must define the reduced prototype state flow.

### 25.6 BOOTSTRAP_PARAMETERS
Must define which prototype parameters remain hard-fixed.

### 25.7 EXECUTOR_MODEL
Must define the executor abstraction and sandbox flow.

### 25.8 EVIDENCE_PACKET
Must define the schema for evidence-backed outputs.

### 25.9 CONFIDENCE_AND_SCORING
Must define derived confidence and routing based on confidence and risk.

---

## 26. Closing Principle

A governance specification becomes credible only when it survives limited reality.

The first prototype should therefore aim not to look complete,
but to learn honestly.

Its task is simple:
- build the smallest credible loop,
- run it on a real problem,
- record what breaks,
- improve what matters,
- and refuse to confuse successful prose with successful governance.
