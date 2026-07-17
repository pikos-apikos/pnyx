# CORE_V02_RECONCILIATION

## 1. Purpose

This document records the reconciliation between the existing specification suite (docs `10_Constitutional` – `95_Emergency`, System Version 2.1) and an external draft titled **"PNyx Core v0.2"** — a condensed, self-contained civic-protocol core written independently of the suite.

The full v0.2 text is deliberately **not** imported into the repository.
The decision was to keep the suite authoritative, align its internal inconsistencies, and adopt the v0.2 contributions that the suite lacked.
This document is the decision record.

A companion narrative/vision text ("Η Ανάβαση της Πνύκας") was reviewed at the same time and is explicitly **out of scope** here (see section 6).

---

## 2. Comparison Summary

A three-track comparison (lifecycle/states/artifacts; specialist intelligence and LLM governance; identity/economics/governance/emergency) found:

- The suite is a **superset** of v0.2 in nearly every area: threat and operator modeling, skills machinery (sandbox admission, revalidation, derived confidence, executor ranking, skill economics, task suites), treasury structure, emergency regime, bootstrap discipline, hash-chained audit, event sourcing, and content-addressed public storage have no v0.2 counterpart.
- v0.2 nevertheless contributed **four things the suite lacked**: a canonical public state vocabulary with a mandatory export mapping; a problem-before-solution gate; the tail of the civic loop (monitoring → outcome → learning) as first-class states and artifacts; and several artifacts/invariants (execution mandate, judgment configuration, authoring modes, the personal-data economic invariant).
- The comparison also exposed **internal inconsistencies of the suite itself**: four non-identical proposal-state enumerations (`STATE_MACHINE.md`, `PROTOCOL.md`, `DATA_MODEL.md`, and the Java `ProposalState`) and three incompatible evidence-type enums (`EVIDENCE.md`, `SCHEMAS.md`, `DATA_MODEL.md`).

---

## 3. Conflict Decisions

| # | Conflict | Decision | Applied where |
|---|---|---|---|
| 1 | Classification: v0.2 has no classification stage; the suite treats it as a constitutional chokepoint | **Suite wins.** Classification stays load-bearing; v0.2's omission also contradicts the project's own vision text | No change needed |
| 2 | Problem-before-solution: v0.2 mandates a validated Problem Definition before any solution is promoted; the suite bound problem and solution together at creation | **v0.2 wins.** Gate adopted at specification level | `PROTOCOL.md` invariant 7.9 + Stage A/D; `DATA_MODEL.md` §5.25; `SCHEMAS.md` §25.1; deviation noted in `STATE_MACHINE.md` §4.6.1 |
| 3 | "Evidence Packet" means a skill's analytic output (suite) vs a primary evidence artifact (v0.2) | **Suite keeps its term.** Terminology guard added so future documents cannot reintroduce the ambiguity | `EVIDENCE.md` §4.8; note in `DATA_MODEL.md` §5.13 |
| 4 | Evidence taxonomy: three incompatible enums in the suite; an eight-way taxonomy in v0.2 | **Unified.** One canonical taxonomy (10 primary classes with subtypes), defined in exactly one place; other documents reference it | `EVIDENCE.md` §5 (canonical); `SCHEMAS.md` §12.1 and `DATA_MODEL.md` §5.13 now reference it |
| 5 | Execution paths: organizational forms (v0.2) vs delivery mechanism (suite) | **Merged as two orthogonal axes**: mechanism (`market/state/hybrid/advisory/defer`) × executor form (`public_institutional/commons/venture/cooperative/contracted/hybrid`) | `PROTOCOL.md` Stage L; `ROUTING.md` §5, §15; `DATA_MODEL.md` §5.21, §7 |
| 6 | Licensing: v0.2 lists open-core and delayed release as ordinary menu options; the suite is anti-enclosure | **Hybrid per IP class.** Both patterns prohibited for Commons-Core and Civic Utility; permitted for Applied Service and Local/Experimental only with declared public return and (for delayed release) a binding release schedule | `PUBLIC_IP_MODEL.md` §7.1 |
| 7 | Panel minimum: v0.2 "high-impact SHOULD use ≥5 skills"; suite: MUST for every non-trivial proposal with mandatory class coverage | **Suite wins.** Stricter and structurally enforced | No change needed |

---

## 4. Adopted from v0.2

| Adoption | Applied where |
|---|---|
| Canonical public state vocabulary (16 primary + 7 supporting states) and mandatory export mapping; internal states remain a runtime vocabulary, the public vocabulary is a meta-governance contract | `STATE_MACHINE.md` §4.5 (new); export rule reflected in `PUBLIC_STORAGE_MODEL.md` (proposal object) and `DATA_MODEL.md` §5.4 |
| Single-source state list: `PROTOCOL.md` and `DATA_MODEL.md` no longer maintain their own (divergent) state enumerations; both reference `STATE_MACHINE.md` §4.1 | `PROTOCOL.md` §6 (now a stage→state mapping table); `DATA_MODEL.md` §5.4, §7 |
| Problem Definition artifact + gate | see conflict 2 |
| Tail of the loop: monitoring stages and artifacts (`MonitoringRule`, `MonitoringEvent`, `MonitoringReport`), `OutcomeRecord` ("must not rewrite original expectations"), `LearningRecord` | `PROTOCOL.md` Stages O/P/Q (new); `DATA_MODEL.md` §5.27–§5.31; `SCHEMAS.md` §25.4–§25.6; `INVARIANTS.md` §12 |
| `ExecutionMandate` — explicit bounded authority (~20 fields); "no decision creates authority beyond its mandate" | `DATA_MODEL.md` §5.26; `SCHEMAS.md` §25.3; referenced from `PROTOCOL.md` Stage L and `ROUTING.md` §5 |
| `JudgmentConfiguration` artifact — the judgment method, quorum, thresholds, and binding status declared before any decision opens, epoch-bound rather than chosen ad hoc | `SCHEMAS.md` §25.2 |
| Authoring modes (human / model / collaborative / institutional / automated / sensor / imported / unknown) as a common artifact field, protecting the epistemic feedback loop | `SCHEMAS.md` §3.8 + common fields §3.1 |
| Civic scope taxonomy (neighborhood → Earth; hierarchical / overlapping / federated / temporary) + aggregation traceability rule | `DATA_MODEL.md` §5.1; `IDENTITY_AND_MEMBERSHIP.md` §4.1 |
| "No personal-data economy" as an explicit **economic** invariant (previously only operator-side in `OPERATOR_TRUST_MODEL.md` §16) | `INVARIANTS.md` §13.1; `FUNDING_MODEL.md` §5, §15 |
| Outcome invariants: no completed loop without outcome; no rewriting of expectations; models never the sole monitor (normative strength aligned to MUST NOT) | `INVARIANTS.md` §12; `PROTOCOL.md` Stage O |
| Consolidated Core Change Proposal schema (adds migration impact and economic impact as mandatory fields of meta-proposals) | `GOVERNANCE.md` §15.1 |

---

## 5. Rejected or Not Needed

- **v0.2's 16-state model as the internal lifecycle** — rejected; the suite's 26 internal states are richer (panel selection, packet drafting, challenge/repair). The 16-state model survives as the *export* vocabulary only.
- **v0.2's "Civic Case" term** — not adopted; the suite's canonical container remains `Proposal`.
- **v0.2's licensing menu as-is** — see conflict 6.
- **"No correction without history" as a new invariant** — not added; already covered by `INVARIANTS.md` §4.1 (no silent edit) and §4.2 (append-only audit).
- **v0.2's conditional panel rule** — see conflict 7.
- **Importing the v0.2 document itself** — the suite plus this record supersedes it.

---

## 6. Recorded Follow-Ups (out of scope here)

1. **Code alignment** (the runtime is currently a third variant of the spec):
    - `ProposalState` (Java) ↔ canonical public state export mapping (`STATE_MACHINE.md` §4.5) — including the deviations recorded in §4.6 (problem gate, monitoring/learning states — `EXECUTION_AUTHORIZED` is resolved as of v0.4);
    - `SkillReviewerRole` now includes `adversarial_critique` and `anti_capture_audit` (resolved in commit a1f2611);
    - problem-before-solution gate in `Proposal.create` (currently binds title/problem/proposedAction in one step);
    - confidence is model-declared (raw double), contradicting `CONFIDENCE_AND_SCORING.md`'s derived-not-declared rule.
2. **A possible future compact "PNyx Core" document** — the idea already seeded in `STRUCTURAL_CRITIQUE_SUGGESTIONS.md`; if written, it must be a distillation of the suite (v0.3), not a parallel spec.
3. **Vision text integration** ("Η Ανάβαση της Πνύκας") — decided separately; critique on record: it must acknowledge classification, include at least one example where the process works and the outcome is still bitter, and turn its capture-suspicion toward its own intake machinery.

---

## 7. Verification Performed

- No secondary document maintains its own full proposal-state list; `PROTOCOL.md` and `DATA_MODEL.md` reference `STATE_MACHINE.md` §4.1.
- The canonical↔internal mapping in `STATE_MACHINE.md` §4.5.3 covers all 26 internal states.
- The evidence-class taxonomy is defined once (`EVIDENCE.md` §5) and referenced elsewhere.
- Each new artifact (ProblemDefinition, ExecutionMandate, MonitoringRule/Event/Report, OutcomeRecord, LearningRecord, JudgmentConfiguration) exists in both `DATA_MODEL.md` and `SCHEMAS.md` under consistent names.
- `00_INDEX_AND_MAP.md` lists this document and reflects the changed sections.
