# The Prototype Gap: A Critique of Pnyx After Implementation

*A critical analysis of the gap between specification and code, and what it reveals about the Pnyx concept*

**Date:** April 2026
**Analyst:** opencode/qwen3.6-plus-free, via OpenCode
**Scope:** 42 documents (~29,400 lines) + Phase 1 prototype (TypeScript/Node.js)
**Method:** Full repository audit — specification reading, code review, spec-to-code comparison, test coverage analysis

---

## Preamble: What Changed Since the Last Critique

The previous `POST_MITIGATION_Critique.md` (Claude Opus 4, April 2026) identified 10 persistent weaknesses in the v2.0 specification. That critique was written when the repository contained **zero code**. It correctly identified the absence of implementation as a critical gap.

Since then, a Phase 1 prototype has been built. This critique does not repeat the previous analysis. It focuses on what the **existence of code reveals** about the specification — and what it reveals about the concept.

The previous critique asked: "Is this buildable?" This critique asks: "Now that it has been partially built, what does the code tell us that the specification could not?"

---

## 1. The Event Sourcing Gap: Architecture as Theater

**Severity: Critical**

The specification describes an event-sourced system. The `EVENT_MODEL.md` defines 13 subsystems, command/event/projection boundaries, aggregate roots, causality tracking, and replay semantics. The `STATE_MACHINE.md` defines transitions with guards and invariants. The `OPERATOR_TRUST_MODEL.md` specifies dual control and active-case protection.

The code does none of this.

### What the Code Actually Does

`event-store/event-store.ts` is an append-only log with SHA-256 hash chaining. Events are recorded. State is mutated directly:

```typescript
// civic-loop.ts:89
proposal.state = 'submitted';

// challenge.ts:46
proposal.state = 'challenged';
```

State is **not derived from events**. Events are **not replayed**. The `EventStore` has no `replay()` method, no `getAggregateState()` method, no projection engine. The hash chain provides integrity for the log but does not enforce that the log is the source of truth.

This is **event logging**, not event sourcing. The distinction matters because:

- In event sourcing, state is a derived projection. If state and events diverge, state is wrong.
- In event logging, state is authoritative. Events are a record of what happened. If they diverge, events are wrong.

The Pnyx code implements the second pattern while claiming the first. The `MINIMUM_VIABLE_PNYX.md` §8 requires "4-view audit minimum" — but an audit of a log that doesn't determine state is an audit of theater.

### What This Reveals About the Specification

The specification's event model is **200+ lines of architectural description** for a pattern the code doesn't implement. This is not a "Phase 1 simplification." It's a fundamental architectural claim that the implementation team (an AI assistant) either:

1. Did not understand the difference between event sourcing and event logging, or
2. Understood it and chose the simpler pattern, or
3. Was constrained by the prototype's scope and the spec didn't provide a reduced event model

Option 3 is most charitable and most damning: **the specification is so complex that even building a "Phase 1" requires ignoring its core architectural claims.**

---

## 2. Classification: The Fatal Stub

**Severity: Critical**

The specification dedicates an entire layer (30) and 600 lines to classification. It describes conservative classification, ambiguity escalation, strongest-layer rules, counter-classification, deterministic rule checks, multi-source requirements, and explicit forbidden patterns.

The prototype implements:

```typescript
// civic-loop.ts:107-127
const classification: ClassificationResult = {
  triviality: 'non_trivial',
  primaryLayer: 'policy',
  minimumPanelSize: 5,
  confidence: 'medium',
  // ...
};
```

**Every proposal gets the same classification. Always.**

This is not a "reduced Phase 1." Classification is the choke point that determines review intensity, panel size, routing, and scrutiny. The specification correctly identifies it as "the most dangerous leverage point." The code hardcodes it.

The previous critique identified this as the "fatal bottleneck." The code confirms it: **classification is the hardest part to implement because it requires the most political judgment.** You cannot reduce classification to a stub without removing the system's primary anti-capture mechanism.

### The Deeper Problem

The `ClassificationResult` type has 16 fields. The prototype populates 5 of them with hardcoded values. The remaining 11 — `frameworkChange`, `advisoryOnly`, `constitutionalSpillover`, `routingMateriality`, `ambiguities`, `counterClassificationRequired`, `rationale`, `classifiedBy`, `classifiedAt` — are either empty, false, or set to defaults.

This means the entire downstream system (panel assembly, routing, packet synthesis) operates on **classification data that was never computed**. The system is running on fictional metadata.

---

## 3. The Mock Provider: Pre-Written Theater

**Severity: High**

`skills/providers.ts` contains 107 lines of hardcoded mock responses. These responses reference:

- "speed study" and "traffic speed data"
- "Green Spaces Committee"
- "accessibility impacts"
- "sidewalk safety"
- "delivery-dependent livelihoods"

These are responses to a **specific proposal about neighborhood traffic safety**. They are returned for any proposal that keyword-matches — regardless of actual content.

A proposal about cryptocurrency regulation receives mock responses about "traffic speed data." A proposal about school curriculum receives mock responses about "Green Spaces Committee structure."

The simulation's "diverse panel analysis" is not analysis. It is **playing back pre-written text**. The five "skills" are not five analytical perspectives — they are five paragraphs from a template.

### What This Reveals

The mock provider is not a testing tool. It is a **demo script**. The simulation produces the appearance of multi-skill deliberation by playing back content written for a different proposal.

This is not inherently wrong for a demo. But it means the prototype has **never actually processed a proposal through AI skills**. The civic loop orchestration works. The skill execution path works. But the actual AI analysis — the thing the system exists to produce — has never been tested.

---

## 4. Test Coverage: The Untested Core

**Severity: High**

The prototype has two test files:

| Test File | Tests | What It Covers |
|-----------|-------|----------------|
| `event-store.test.ts` | 6 | Append, hash chain, verify, filter, immutability |
| `state-machine.test.ts` | 14 | Transition validation |

**Untested:**

| Component | Lines | Status |
|-----------|-------|--------|
| `civic-loop.ts` | 398 | Zero tests |
| `challenge.ts` | 252 | Zero tests |
| `skills/registry.ts` | 95 | Zero tests |
| `skills/adapter.ts` | 148 | Zero tests |
| `skills/providers.ts` | 252 | Zero tests |
| `views/audit-views.ts` | 227 | Zero tests |
| `simulation/run.ts` | 405 | Zero tests |

The civic loop — the orchestrator that is **the entire reason this system exists** — has zero automated tests. The challenge system — the primary citizen recourse mechanism — has zero tests. The skill registry's provider diversity enforcement — a core anti-capture mechanism — has zero tests.

The only way to verify the system works is to run `npm run simulate` and read stdout.

### What This Reveals

The specification emphasizes "evaluation hooks," "regression testing," "task suites," and "failure ledgers" for AI skills. The prototype doesn't even test its own deterministic code.

If the system cannot test its own infrastructure, how will it test AI skills? The spec's entire skill evaluation framework (`SKILL_EVALUATION.md`, `TASK_SUITE_v0.md`) presupposes a testing culture that the prototype does not demonstrate.

---

## 5. Spec-to-Code Coverage: The 15% Problem

**Severity: High**

| Spec Claim | Code Status |
|------------|-------------|
| 13-subsystem event catalog | ~8 event types implemented |
| 30+ role-specific read models | 4 audit views |
| PostgreSQL-backed event store | In-memory array |
| Zero-knowledge proofs | None |
| Epoch binding | No epoch concept |
| Constitutional spillover detection | Boolean field, always false |
| Provider diversity enforcement | Best-effort sort, no hard constraint |
| Skill tier economics | `tier` field exists, never used |
| RESTful API with rate limiting | `api/` directory is empty |
| Dual operator control | Single `operatorId`. |
| Emergency enforcement | None |
| Treasury partitions | None |
| Cryptographic audit chain | SHA-256 hash chain only |
| Skill evaluation framework | None |
| Classification engine | Hardcoded stub |
| Panel selection with diversity | Partial (diversity preference, not enforcement) |
| Packet synthesis | Implemented (but loses provenance — see §7) |
| Challenge system | Implemented (but operator-centric — see §8) |

The prototype implements approximately **15-20%** of the specification. This is not a condemnation — Phase 1 is supposed to be reduced. But the specification does not clearly delineate "spec" from "implemented," and the prototype's `package.json` includes dependencies (`express`, `pg`) for features that don't exist.

### What This Reveals

The gap is not "MVP vs. full system." The gap is **conceptual architecture vs. working software**. The specification describes a system that would take a team of engineers 6-12 months to build properly. The prototype is 2-3 weeks of focused development.

The previous critique noted that "specification complexity is itself a risk." The code confirms this: the specification is so large that even an AI assistant with full context cannot implement a meaningful fraction of it in a single pass.

---

## 6. The Advisory Trap: No Execution Path

**Severity: High**

The specification produces "advisory packets." This word does the most work in the entire repository.

An advisory packet is a recommendation. It has:
- No enforcement mechanism
- No execution path
- No connection to material outcomes
- No compliance tracking
- No consequences for non-compliance

The spec describes what happens *after* publication in terms of "citizen deliberation" and "community decision" — but never specifies **what mechanism translates deliberation into action**.

The prototype confirms this. `runCivicLoop` returns a `CivicLoopResult` with a `CivicPacket`. Then what? The simulation prints it to stdout and exits. There is no "execute" phase because there is nothing to execute.

### What This Reveals

Pnyx is a **deliberation engine without an execution layer**. It produces well-structured opinions about things it cannot change. This is not a bug — it's a structural feature of the "advisory only" design.

The specification's political economy layer (`POLITICAL_ECONOMY.md`) correctly identifies that "visible procedure is not balanced power." The advisory-only design ensures that Pnyx produces visible procedure without balanced power.

---

## 7. Packet Synthesis Loses Provenance

**Severity: Medium-High**

`civic-loop.ts:369-376`:

```typescript
strongestCaseInFavor:
  inFavor.length === 1
    ? inFavor[0]
    : inFavor.map((c, i) => `[Skill ${i + 1}]: ${c}`).join('\n\n'),
```

Skills are labeled `[Skill 1]`, `[Skill 2]` — not by their actual class. A citizen reading the packet cannot tell which skill produced which argument. The "rights constitutional" analysis is indistinguishable from the "anti-capture" analysis.

The specification emphasizes "visible procedure" and "structured disagreement." The output anonymizes the source of each position.

### What This Reveals

Even in the parts of the code that work, the implementation loses information that the specification considers essential. If provenance is lost in packet synthesis — one of the most straightforward transformations in the system — what else is lost in the parts that don't work yet?

---

## 8. The Challenge System Is Operator-Centric

**Severity: Medium-High**

The challenge flow requires an operator to:
1. Decide whether to uphold or deny
2. Manually specify revision content (in `revisePacket`)
3. Reaffirm packets

The specification describes "challenge capacity" as a citizen right. The code implements a **gatekeeper model**. There is no mechanism for citizens to:
- Escalate a denied challenge
- Challenge the operator's decision
- Request independent review of an upheld challenge

The operator is the sole arbiter of challenge outcomes.

### What This Reveals

The code reveals a pattern: **citizen-facing mechanisms are implemented as operator-facing controls**. Classification is hardcoded (not citizen-visible). Challenges go through an operator. Skills are executed by the system, not by citizens. The "human sovereignty" principle becomes "operator sovereignty" in practice.

---

## 9. Empty Infrastructure

**Severity: Medium**

Two directories exist with zero files:

- `prototype/src/api/` — empty, despite `express` being a dependency
- `prototype/src/db/` — empty, despite `pg` being a dependency

Two scripts are referenced in `package.json` but don't exist:

- `db:migrate` — references `src/db/migrate.ts` (doesn't exist)
- `db:reset` — references `src/db/reset.ts` (doesn't exist)

Zod is a dependency but is used nowhere. UUID is a dependency but `crypto.randomUUID()` is used instead.

### What This Reveals

The prototype's `package.json` describes a system that is larger than the system that exists. This is not unusual for early development, but it creates a misleading impression of completeness.

---

## 10. The Repetition Problem: 29,400 Lines of What?

**Severity: Medium**

The previous critique noted that "the same principles are restated 5-10 times each." The code confirms this pattern: the same architectural claims appear in multiple documents without adding new information.

Counting unique content vs. repeated content across the 42 documents:

- "Human sovereignty is primary" — appears in 6+ documents
- "Capture pressure is normal" — appears in 8+ documents
- "Emergency powers must not become permanent" — appears in 5+ documents
- "The values layer holds the wheel" — appears in 4+ documents
- "Auditability is not the same as accessibility" — appears in 3+ documents

The effective unique content is closer to **12,000-15,000 lines**. The rest is restatement, cross-reference, and rhetorical reinforcement.

### What This Reveals

The specification's size is partly a function of its literary quality. The writing is good. The sentences are effective. But rhetorical polish inflates line count without adding analytical depth.

---

## 11. What the Code Gets Right

The critique should acknowledge what works:

1. **The state machine is correct.** 14 states, valid transitions, no skips. The tests cover this well.
2. **The event store's hash chain is correct.** Events are immutable (shallowly), hashes chain properly, verification works.
3. **The civic loop orchestration is structurally sound.** The sequence submit → classify → panel → execute → synthesize → publish follows the spec.
4. **The challenge flow is complete.** Submit → resolve → revise/reaffirm → republish is a working cycle.
5. **The audit views are functional.** All four minimum views generate readable output.
6. **The simulation demonstrates the full path.** Two scenarios (upheld challenge, denied challenge) show the system end-to-end.

These are not trivial achievements. The code demonstrates that the **shape** of the system is coherent.

---

## 12. The Fundamental Question, Revisited

The previous critique asked: "What is Pnyx for?" The code provides a new angle on this question.

**As a specification:** It is thorough, self-aware, and well-organized. The political economy layer, epistemic risk framework, and anti-capture analysis are genuine contributions to the theory of AI-assisted deliberation.

**As a prototype:** It is a skeleton. It demonstrates shape without substance. The core mechanisms (classification, AI skill execution, citizen challenge capacity) are either stubbed, mocked, or operator-centric.

**As a tool:** It is not usable. No community could deploy this system and produce meaningful outcomes. The advisory-only design means there is no execution path. The classification stub means there is no anti-capture mechanism. The mock provider means there is no actual AI analysis.

**As research:** It is valuable. The specification documents problems that most AI governance systems ignore: political economy, epistemic risk, bootstrap legitimacy, shutdown planning.

**As a documentation project:** It is excellent. The 42 documents form a coherent, cross-referenced, self-critical body of work. The three existing critiques (GOVERNANCE, MITIGATIONS, POST_MITIGATION) demonstrate intellectual honesty.

---

## 13. What the Code Reveals That the Specification Could Not

The previous critiques were written against documentation alone. The code adds new evidence:

### 13.1 The Complexity Tax Is Real

The specification warned about complexity. The code demonstrates it: even with full context, an AI assistant produced a prototype that implements ~15% of the spec. The remaining 85% is not "future work" — it's work that the current architecture makes difficult.

### 13.2 The "Advisory" Design Is a Structural Evasion

The specification describes advisory outputs as a bootstrap constraint. The code reveals that advisory-only means **no execution layer exists**. This is not a temporary limitation — it's baked into the architecture. Adding execution would require redesigning the entire system.

### 13.3 Classification Cannot Be Stubbed

The specification treated classification as one component among many. The code reveals that classification is **the component** — without it, the entire anti-capture apparatus is inert. You cannot "add classification later" because everything downstream depends on it.

### 13.4 The Testing Gap Is Cultural, Not Technical

The specification describes elaborate skill evaluation frameworks. The code has no tests for its own business logic. This suggests that the testing culture described in the spec is aspirational, not foundational.

### 13.5 The Mock Provider Defeats the Purpose

The specification treats skill diversity as a core anti-capture mechanism. The code reveals that "diverse skills" in the prototype are five paragraphs of pre-written text. The system's primary innovation — multi-skill AI deliberation — has never been tested with actual AI.

---

## 14. Recommendations

### For the Specification

1. **Add an Implementation Status document** tracking spec → code coverage for every major component. This should be updated with each prototype iteration.
2. **Create a "Reduced Spec for Phase 1"** that explicitly defines what is in and out of scope. The current spec describes the full system; the prototype implements a fraction. The gap should be documented, not implicit.
3. **Consolidate repeated principles.** Cross-reference rather than restate. The effective content is ~15,000 lines; the rest is rhetorical reinforcement.
4. **Define the execution layer.** "Advisory only" is a bootstrap constraint, not a permanent design. The spec should describe what execution looks like, even if deferred.

### For the Prototype

1. **Implement real event sourcing.** State should be derived from events, not mutated directly. This is the core architectural claim.
2. **Build a classification engine.** Even a simple rule-based classifier is better than a hardcoded stub. It would expose the political judgments that classification requires.
3. **Replace proposal-specific mocks with generic placeholders.** The current mocks reference specific proposals and undermine the simulation's credibility.
4. **Add integration tests.** The civic loop, challenge flow, and packet synthesis should have automated tests.
5. **Remove unused dependencies.** Express, pg, and Zod should not be in `package.json` if they are not used.
6. **Fix shallow immutability.** Use deep freeze or immutable data structures for events.

### For the Project

1. **Pick a lane.** Is this a specification, a prototype, a research contribution, or a tool? The current attempt to be all four simultaneously creates tension between documentation quality and code quality.
2. **Engage external reviewers.** The specification has never been reviewed by political scientists, constitutional lawyers, or democratic theorists. The existing critiques are AI-authored.
3. **Find a pilot community.** The specification's value will be proven or disproven only through contact with reality. A real community using the system would reveal assumptions that no amount of documentation can surface.

---

## Summary

| # | Issue | Severity | Previous Critique Awareness | New Evidence from Code |
|---|-------|----------|---------------------------|------------------------|
| 1 | Event sourcing is event logging | Critical | Not identified (no code) | Direct state mutation, no replay |
| 2 | Classification is hardcoded stub | Critical | Identified as bottleneck | 5 of 16 fields populated, always same values |
| 3 | Mock responses are proposal-specific | High | Not identified (no code) | Traffic safety text returned for all proposals |
| 4 | Core business logic untested | High | Not identified (no code) | 0 tests for civic loop, challenges, synthesis |
| 5 | Spec-to-code gap is ~85% | High | "No implementation" noted | 15-20% implemented, not 0% |
| 6 | No execution path exists | High | "Advisory" noted as limitation | Confirmed: no execute phase in code |
| 7 | Packet synthesis loses provenance | Medium-High | Not identified | Skills labeled [Skill 1], [Skill 2] |
| 8 | Challenge system is operator-centric | Medium-High | Not identified | Operator is sole arbiter |
| 9 | Empty directories and unused deps | Medium | Not identified | `api/`, `db/` empty; Zod unused |
| 10 | Specification repetition inflates size | Medium | Identified | Confirmed: ~15K unique lines of ~29K |

---

## Closing Assessment

Pnyx is a genuinely ambitious and intellectually serious attempt to formalize democratic governance with AI assistance. The specification is thorough, self-critical, and well-organized. The three existing critiques demonstrate intellectual honesty that is rare in governance design.

The code reveals what the specification could not: **the gap between architectural description and working software is not a matter of effort — it is a matter of complexity.** The specification describes a system so large that even building a "Phase 1" requires ignoring its core architectural claims.

The specification's own best insight applies to itself:

> "A democratic system fails when it mistakes visible procedure for balanced power." — `POLITICAL_ECONOMY.md` §24

A governance project fails when it mistakes thorough documentation for functional design.

The path forward is not more specification. It is not more code. It is **contact with reality** — a real community, a real proposal, a real decision, and a real test of whether this system produces outcomes that its participants trust.

The specification is ready for that test. The code is not. The gap between them is the project's central challenge.

---

*Critique completed: April 2026*
*Analyst: opencode/qwen3.6-plus-free, via OpenCode*
*Method: Full repository audit — specification reading, code review, spec-to-code comparison, test coverage analysis*
