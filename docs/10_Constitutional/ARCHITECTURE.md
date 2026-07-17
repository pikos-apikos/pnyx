# ARCHITECTURE

> **Note:** This document has been updated to reflect the normative rules in [SYSTEM_PATCH_v1](../99_Reference/SYSTEM_PATCH_v1.md), [SYSTEM_PATCH_v2](../99_Reference/SYSTEM_PATCH_v2.md), and [ARCHITECTURE_PATCH_v1](../99_Reference/ARCHITECTURE_PATCH_v1.md).

## 1. Purpose

This architecture exists to make direct democratic self-government operational under conditions of complexity, scale, and cognitive overload.

It is designed to:
- preserve human sovereignty,
- reduce the cognitive cost of participation,
- structure disagreement without collapsing it into opaque authority,
- route decisions toward appropriate execution channels,
- remain auditable, revisable, and resistant to capture.

The architecture is not a machine for replacing the citizen.
It is a civic reasoning system that helps a people govern.

---

## 2. Architectural Stance

This system should be understood as a layered civic machine.

The system does not orchestrate model opinions.
It orchestrates bounded civic analysis performed by admissible executors under evidence, routing, and audit constraints.

It has:
- a **values layer** that constrains legitimacy,
- a **reasoning layer** that transforms proposals into civic-grade judgment objects,
- an **execution layer** that implements approved outcomes,
- an **audit layer** that preserves visibility and contestability,
- a **meta-governance layer** that allows the system to revise itself without bypassing itself.

The architecture therefore separates:
- sovereignty from computation,
- deliberation from execution,
- execution from legitimacy,
- adaptation from arbitrary mutation.

---

## 3. Top-Level System Map

The architecture can be read as the following flow:

**Citizen / civic body**  
→ **proposal intake**  
→ **scope and layer classification**  
→ **executor panel assembly**  
→ **structured deliberation and adversarial review**  
→ **public briefing generation**  
→ **public deliberation / vote / decision**  
→ **execution routing**  
→ **audit and post-decision review**  
→ **knowledge and framework updates through recursive civic governance**

The system is therefore not a single model and not a single institution.
It is a pipeline of constrained transformations.

---

## 4. Core Design Principles

### 4.1 Human Sovereignty
Final legitimacy belongs to citizens, not to models, administrators, or execution engines.

### 4.2 Visible Reasoning
Every meaningful transformation should leave a trace that can be reviewed, challenged, or replayed.

### 4.3 Plural Skill Deliberation
Non-trivial proposals must be examined by a plural panel rather than a single authoritative output. Plurality accounts for executor independence, source independence, adversarial review, human involvement, and real disagreement preservation.

### 4.4 Structured Disagreement
Conflict is surfaced and organized, not hidden.

### 4.5 Anti-Capture by Design
The system assumes capture pressure is normal and designs around that assumption.

### 4.6 Recursive Revision
The architecture may revise its own frames, but only through stricter meta-procedures.

### 4.7 Prospective Change Only
Core parameters may be changed only for future cases, never ad hoc for an active case.

### 4.8 Executor Abstraction
The architecture must not hard-bind civic analysis to model invocations. A dedicated layer separates Skill Contract, Executor Implementation, Execution Mode, and Review Route.

### 4.9 Evidence as a First-Class Object
Evidence retrieval, source validation, claim traceability, and Evidence Packet production are core architectural concerns.

### 4.10 Computed Confidence
Confidence is produced by a scoring component, not trusted as a raw executor field.

### 4.11 Dynamic Routing
The architecture supports re-routing, escalation, blocking, replication, and mandatory human review based on protocol rules.

### 4.12 Human Participation
Human experts, panels, and hybrid workflows are supported as native execution forms.

### 4.13 Deep Auditability
The architecture preserves who executed what, under which mandate, with which sources, claims, confidence basis, and review consequences.

---

## 5. Architectural Layers

### 5.1 Intake and Classification Layer
Responsible for:
- proposal intake,
- completeness checks,
- classification,
- urgency and triviality detection,
- determination of evidence burden,
- routing preconditions.

### 5.2 Skill Contract Layer
Defines:
- what each skill must answer,
- expected output shape,
- scope and constraints,
- required review mode,
- evidence burden.

### 5.3 Executor Registry and Eligibility Layer
Responsible for:
- admitted executors,
- scope constraints,
- version tracking,
- conflict or independence flags,
- admissibility lookup,
- ranking and revalidation status.

### 5.4 Execution Orchestration Layer
Responsible for binding:
- skill,
- executor,
- execution mode,
- deadline,
- evidence burden,
- review obligations,
- fallback rules.

### 5.5 Evidence and Citation Layer
Responsible for:
- evidence retrieval (law, regulations, decisions, datasets, budgets, reports),
- source registration,
- source validation (existence, retrievability, metadata coherence, jurisdiction match, staleness, claim-support plausibility),
- claim-to-source mapping,
- legal/jurisdiction context binding,
- Evidence Packet construction.

### 5.6 Confidence and Scoring Layer
Responsible for:
- component scoring (evidence coverage, source quality, jurisdiction relevance, freshness, claim traceability, unknowns disclosure, schema completeness, contradiction penalties, replication signals),
- caps and penalties,
- banding,
- review triggers (review_required, human_review_required, replication_recommended, readiness_blocked),
- readiness-related scoring outputs.

### 5.7 Synthesis and Conflict Preservation Layer
Responsible for:
- synthesizing multiple packets,
- preserving conflict (strongest pro/con cases, minority views, unresolved legal/factual conflict, evidence gaps, confidence dispersion, capture-risk notes, independence limitations),
- avoiding artificial consensus.

### 5.8 Review Routing and Escalation Layer
Responsible for:
- human review routing,
- replication triggering,
- adversarial review,
- challenge handling,
- readiness blocking,
- appeal routing.

### 5.9 Public Briefing and Transparency Layer
Responsible for:
- producing public-facing packet views,
- exposing source basis,
- preserving challengeability,
- supporting public scrutiny.

### 5.10 Ranking, Sandbox, and Revalidation Layer
Responsible for:
- inclusion sandbox,
- shadow mode,
- contextual ranking,
- drift detection,
- downgrade and suspension,
- re-entry logic.

### 5.11 Audit and Memory Layer
Responsible for:
- event history,
- packet history,
- claim/source traceability,
- executor history,
- ranking/revalidation history,
- decision and post-decision review trace.

### 5.12 Meta-Governance Layer
This layer governs changes to the architecture itself.
It includes framework proposals, parameter revisions, skill registry updates, timing rule changes, threshold changes, classification rule changes, and emergency rule changes.
It must operate through the same civic loop, under stricter safeguards.

---

## 6. Canonical System Objects

The architecture should treat the following as first-class objects:

### 6.1 Proposal
A bounded civic request for change, action, or review.

### 6.2 Framework
A higher-order rule that structures how proposals are processed.

### 6.3 Skill & Executor
- **Skill:** A protocol-defined analytic role with a mandate.
- **Executor:** The actor or system authorized to perform a skill (AI, human, hybrid).

### 6.4 Panel
A temporary assembly of executors assigned to specific skills, instantiated for a proposal.

### 6.5 Evidence Packet & Confidence Record
The structured, source-cited output produced by an executor, accompanied by a procedurally derived confidence score.

### 6.6 Briefing
A public-facing deliberation package derived from the panel outputs.

### 6.7 Decision Record
The formal output of a completed civic process.

### 6.8 Audit Record
The trace object that preserves the procedural memory of the process.

### 6.9 Parameter Set
The currently active thresholds, timing windows, escalation criteria, and quorum rules.

Treating these as explicit objects prevents governance logic from disappearing into hidden implementation details.

---

## 7. Executor Abstraction

The architecture supports the following separation:

### 7.1 Skill Contract
Defines what must be done.

### 7.2 Executor Record
Defines who or what may do it.

### 7.3 Execution Binding
Defines who was selected for a specific case.

### 7.4 Execution Mode
Defines whether the case is solo, verified, dual, hybrid, escalated, or panel-based.

### 7.5 Review Mode
Defines what oversight is mandatory after execution.

---

## 8. Minimum Deliberative Panel

For any non-trivial proposal, the system should require a minimum panel of five relevant skills.

Five is the minimum deliberative quorum.
It is large enough to generate structured plurality and small enough to avoid excessive coordination drag.

A baseline five-skill panel should normally include:
- rights / constitutional,
- implementation / feasibility,
- economic / resource,
- anti-capture / audit,
- adversarial critique.

Escalation to seven or more skills may occur when:
- the proposal crosses multiple domains,
- uncertainty is high,
- constitutional spillover is possible,
- implementation complexity is high,
- affected population is broad,
- external dependency risk is high.

Single-evidence packets may exist for exploration, but a plural panel of independent executors is required as the basis of non-trivial public decision.

---

## 9. Civic Reasoning Pipeline

A canonical pipeline for ordinary proposals should be:

1. proposal creation,  
2. intake validation,  
3. governance-layer classification,  
4. required skill-class determination,  
5. panel instantiation,  
6. parallel skill analysis,  
7. adversarial synthesis,  
8. public briefing generation,  
9. public deliberation window,  
10. decision event,  
11. execution routing,  
12. audit publication,  
13. post-decision review.

Framework-change proposals follow the same path under stricter rules.

---

## 10. Execution Architecture

### 10.1 Market Thread
Used where decentralized experimentation, voluntary coordination, competition, or local initiative are more aligned with the values layer.

### 10.2 State Thread
Used where universality, rights protection, coordinated baseline guarantees, or anti-capture intervention are necessary.

### 10.3 Hybrid Thread
Used where public guarantees and decentralized execution must coexist.

The architecture should explicitly record why one execution thread was selected over another.

---

## 11. Anti-Capture Architecture

Anti-capture should not be a single check.
It should be embedded across layers.

### 11.1 Intake Anti-Capture
Proposals should record who benefits, who bears cost, and whether chokepoints are created.

### 11.2 Panel Anti-Capture
A required anti-capture skill must be present in every non-trivial panel.

### 11.3 Classification Anti-Capture
The system must prevent proposals with constitutional effect from being disguised as ordinary policy.

### 11.4 Execution Anti-Capture
Execution paths should be reviewed for dependency creation, enclosure of knowledge, and concentration of operational control.

### 11.5 Meta-Governance Anti-Capture
Rules about timing, thresholds, escalation, and emergency powers must be protected from ad hoc tuning.

### 11.6 Anti-Exhaustion Defenses
To prevent the system from being halted by bureaucratic exhaustion (e.g., challenge spam), the architecture implements:
- **Zero-Knowledge Stratified Cost Nullifiers (ZK-SCN):** A cryptographic mechanism that imposes a quadratically increasing cost on repeated actions (like challenges) within an epoch. Users prove they are paying the correct cost for their Nth action slot without revealing their identity or linking their actions, preserving the 'Unlinkability by Default' principle.
- **Event-Driven State Transitions (Asynchronous Batching):** Challenges are collected over an epoch and synthesized all at once, preventing synchronous halting of the civic loop.

This architecture assumes that hidden leverage points are the main site of capture.

---

## 12. Bootstrap Architecture

Bootstrap should begin with limited scale but strong procedural integrity.

The architecture should assume a small initial human core, assisted by civic AI tools, operating over a narrow decision domain.

Bootstrap should therefore prioritize:
- narrow initial scope,
- fixed baseline rules,
- high transparency,
- strong audit,
- reversible outcomes,
- limited execution power,
- visible disagreement,
- explicit post-decision review.

The goal of bootstrap is not full institutional completeness.
It is to prove that the civic loop can function without premature capture or cognitive collapse.

---

## 13. Bootstrap-Fixed Parameters

During bootstrap, certain parameters should be fixed ex ante and publicly known.
These should not be changed ad hoc while an active proposal is moving through the system.

They include:
- minimum panel size,
- required skill classes,
- escalation criteria between policy, governance, and constitutional layers,
- review window durations,
- approval thresholds,
- audit-record minimum fields,
- evidence sufficiency rules,
- emergency-path activation rules,
- scope limits of bootstrap,
- rules for changing the parameters themselves.

Changes to these parameters may occur only:
- through the civic loop,
- prospectively,
- with explicit meta-governance review,
- under thresholds proportional to their systemic impact.

The system may revise its clock, but never while the vote is already running.

---

## 14. Emergency Architecture

Emergency pathways may exist, but they must be tightly constrained.

An emergency path should be:
- narrow in scope,
- time-bounded,
- publicly logged,
- automatically reviewable after activation,
- unable to silently become a normal governance route.

Emergency authority must not become the hidden default operating system.

---

## 15. Public Knowledge Architecture

The system should accumulate public knowledge without silently converting that knowledge into unaccountable rule.

This requires separation between:
- public deliberation memory,
- skill updates,
- framework changes,
- current active rules.

A knowledge upgrade is not automatically a governance upgrade.
Any meaningful change to skills, prompts, or routing logic that affects public outcomes must remain governable.

---

## 16. Recursive Civic Governance

Framework-change proposals should move through the same architecture they seek to alter, but under stricter safeguards.

This implies:
- the same intake and classification discipline,
- the same panel logic,
- stronger adversarial review,
- longer scrutiny windows,
- higher approval thresholds,
- explicit constitutional impact analysis,
- prospective-only activation.

The loop may revise itself, but never bypass itself.

---

## 17. Mutable vs Fixed Components

To avoid both chaos and rigidity, the architecture should distinguish between three categories.

### 17.1 Hard-Fixed During Bootstrap
- quorum and panel minimum,
- escalation rules,
- review windows,
- approval thresholds,
- audit minimum,
- emergency limits,
- parameter-change rules.

### 17.2 Soft-Fixed During Bootstrap
- default skill templates,
- public briefing structure,
- evidence weighting heuristics,
- default routing heuristics.

### 17.3 Adaptive
- language and presentation,
- summarization style,
- discovery and browsing aids,
- accessibility tooling,
- non-decisive helper flows.

This distinction allows the system to evolve at the edge while remaining stable at the load-bearing core.

---

## 18. Failure Modes

The architecture should explicitly anticipate failure.
Key failure modes include:
- skill/model conflation,
- evidence-blind orchestration,
- fabricated or weak citations passing unnoticed,
- confidence theater,
- synthesis smoothing away disagreement,
- wrong-jurisdiction analysis,
- hidden human conflicts of interest,
- benchmark-blind admission,
- static trust in drifting executors,
- challenge spam halting the loop,
- public opacity about analytic basis,
- single-executor authority drift and vendor capture,
- false plurality (shared dependencies posing as independent review),
- ungrounded confidence (certainty theater),
- unsupported material claims,
- missing evidence or jurisdiction mismatch,
- hidden parameter tuning,
- classification capture,
- emergency-path normalization,
- audit decay,
- execution drift beyond approved mandate,
- identity centralization,
- knowledge enclosure,
- public disengagement due to complexity,
- false consensus produced by summarization.

A serious architecture names its failure modes before it names its ideals.

---

## 19. Minimal Deployable Architecture

A minimal first deployment should contain at least:
- civic interface,
- proposal intake,
- classification engine,
- five-skill panel orchestration,
- structured deliberation output,
- public briefing publication,
- decision recording,
- audit logging,
- limited execution routing,
- bootstrap-fixed parameter registry.

Anything beyond that is useful, but not foundational.

---

## 20. Closing Principle

> The architecture should not merely connect models to proposals.  
> It should enforce disciplined civic analysis through admissible executors, evidence-bound outputs, derived confidence, non-blocking review, and auditable public reasoning.

This architecture is designed to make complexity governable without converting complexity into opaque rule by experts, markets, bureaucracies, or machines.

It compresses expertise without privatizing sovereignty.
It structures disagreement without abolishing politics.
It allows institutional learning without allowing silent self-mutation.

The values layer holds the wheel.
The citizen remains sovereign.
The executor panel assists.
The audit preserves memory.
The loop remains revisable without becoming self-destructive.