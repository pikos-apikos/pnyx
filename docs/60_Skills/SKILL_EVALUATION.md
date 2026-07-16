# SKILL_EVALUATION

## 1. Purpose

This document defines how skills are tested, scored, promoted, and demoted.

Its purpose is to prevent the skill layer from becoming a hallucination loop in which:
- attractive outputs are mistaken for reliable outputs,
- value alignment is claimed without evidence,
- regressions are ignored,
- critique is informal and unrepeatable,
- defaults are granted on taste rather than test evidence.

This document complements:
- `SKILLS.md`
- `SKILL_REGISTRY.md`
- `SKILL_ECONOMICS.md`
- `EVIDENCE.md`
- `PACKET_FORMAT.md`
- `MINIMUM_VIABLE_PNYX.md`

A skill may be useful before it is trusted.
Evaluation is the process by which usefulness is separated from public reliability.

---

## 2. Core Principle

A skill must be evaluated against tasks, properties, failures, and regressions.

Evaluation must not ask only:
- Did the answer look good?
- Did the reviewer agree with the tone?
- Did it sound aligned?

Evaluation must also ask:
- Did the skill preserve dissent?
- Did it state unknowns?
- Did it avoid false certainty?
- Did it surface capture risk when relevant?
- Did it preserve packet shape?
- Did it misroute?
- Did it hide trade-offs?
- Did it regress on previously solved cases?

A skill without a repeatable evaluation loop is not a trusted public reasoning instrument.

---

## 3. Evaluation Tiers

## 3.1 Tier 1 — Exploratory Evaluation
Used for early template testing.

Requires:
- a small task set,
- basic property checks,
- visible failure notes,
- versioning,
- no default trust implication.

## 3.2 Tier 2 — Regression-Bound Evaluation
Used for evaluated skills.

Requires:
- fixed task suite,
- adversarial tasks,
- scored output properties,
- regression reruns on every version,
- promotion criteria,
- failure ledger.

## 3.3 Tier 3 — Governance-Grade Evaluation
Used for default or high-consequence skills.

Requires:
- extended task coverage,
- class-specific challenge cases,
- longitudinal drift checks,
- comparative baselines,
- incident-informed reruns,
- demotion triggers,
- public reviewability.

---

## 4. Evaluation Objects

The main evaluation objects are:

- `SkillVersion`
- `EvaluationSuite`
- `EvaluationRun`
- `TaskCase`
- `TaskProperty`
- `FailureEvent`
- `RegressionRecord`
- `PromotionDecision`
- `DemotionDecision`

These objects should be represented in registry and audit surfaces where appropriate.

---

## 5. Task Suite Design

Every serious skill must be tested on a stable task suite.

A task suite should include:
- routine tasks,
- ambiguous tasks,
- adversarial tasks,
- under-specified tasks,
- high-conflict tasks,
- tasks where the correct behavior is to say “unknown”,
- tasks where minority harms should be surfaced,
- tasks where routing pressure must be disclosed.

Task suites should be:
- versioned,
- class-aware,
- reproducible,
- resistant to cherry-picking.

The purpose of the suite is not to prove perfection.
It is to make failures legible and comparable.

---

## 6. Expected Properties

Tasks should define expected properties, not only expected final answers.

Typical expected properties include:
- preserves dissent,
- names unknowns,
- avoids certainty laundering,
- states reversibility where relevant,
- identifies capture risk where relevant,
- respects packet section ordering,
- distinguishes evidence from value judgment,
- avoids silent route recommendation,
- escalates when classification is ambiguous,
- identifies materially affected parties.

A skill may fail even when its answer sounds plausible,
if it violates required public reasoning properties.

---

## 7. Task Schema

Each `TaskCase` should contain at least:
- `TaskId`
- `SkillClass`
- `TaskType`
- `InputContext`
- `ExpectedProperties`
- `ForbiddenBehaviors`
- `DifficultyLabel`
- `AdversarialFlag`
- `EvaluationNotes`

Where useful, a task may also include:
- expected packet skeleton,
- known ambiguity markers,
- known likely hallucination traps,
- baseline comparisons.

---

## 8. Forbidden Behaviors

Evaluation must track forbidden behaviors explicitly.

Typical forbidden behaviors include:
- fabricated certainty,
- missing dissent where dissent is expected,
- masking distributional conflict,
- silent omission of minority harms,
- unjustified route recommendation,
- packet corruption,
- ungrounded legal or constitutional claims,
- false evidence attribution,
- smoothing conflict into false consensus,
- failing to mark insufficient evidence.

A skill version with repeated forbidden behaviors must not be promoted.

---

## 9. Multi-Pass Evaluation

Evaluation should not rely on one judge alone.

A healthy evaluation loop may include:
- rule-based checks,
- property-specific heuristics,
- critic skill passes,
- cross-model critique,
- spot human review,
- packet-structure validation.

No single layer is sufficient by itself.

The goal is not perfect objectivity.
The goal is repeatable adversarial pressure.

---

## 10. Cross-Model Testing

Where feasible, the same skill should be tested on multiple underlying models.

This is useful because it reveals:
- skill portability,
- model sensitivity,
- brittle prompt assumptions,
- false impressions of robustness.

Cross-model testing is especially important for:
- template-tier skills,
- publicly distributed skills,
- skills intended for broad local-community reuse.

A skill that works only on one model family is not invalid,
but that dependence must be visible.

---

## 11. Loop-Based Skill Hardening

A valid bootstrap pattern is a multi-step critique loop such as:

1. Apply skill
2. Critique output
3. Update output
4. Re-critique
5. Re-update

This loop may use:
- the same model,
- different models,
- specialist critic skills,
- human reviewers.

However, critique loops are not enough by themselves.

A critique loop becomes evaluation only when it is:
- task-bound,
- scored,
- versioned,
- replayable,
- regression-bound.

Without those conditions, the loop is only iterative editing.

---

## 12. Scoring Dimensions

Skills may be scored across dimensions such as:

- dissent fidelity,
- uncertainty honesty,
- packet integrity,
- evidence discipline,
- routing discipline,
- capture sensitivity,
- minority-harm surfacing,
- clarity without distortion,
- classification caution,
- model portability.

Scoring should not collapse into a single beauty score.

A skill may be strong in clarity and weak in dissent fidelity.
Those must remain separable.

---

## 13. Failure Ledger

Every meaningful evaluation system must maintain a failure ledger.

Failure categories may include:
- `HallucinatedCertainty`
- `MissedDissent`
- `WeakCritique`
- `RoutingDrift`
- `PacketCorruption`
- `ValueMisalignment`
- `MinorityHarmOmission`
- `FalseConsensusCompression`
- `EvidenceOverclaim`
- `ClassificationUnderEscalation`

The ledger should track:
- frequency,
- severity,
- recurrence,
- affected task classes,
- whether the issue is new or regressed.

A skill that “usually works” but repeatedly fails the same constitutional property is not stable.

---

## 14. Regression Policy

Every skill version after the first must rerun against a regression suite.

The regression suite should include:
- previously failed tasks,
- previously ambiguous tasks,
- representative baseline tasks,
- highest-severity failure tasks,
- portability-sensitive tasks.

A new version must not be promoted if it fixes one problem while silently reintroducing another in a critical area.

Regression discipline is the minimum price of public trust.

---

## 15. Promotion Rules

A skill may be promoted from:

### 15.1 Template → Evaluated
Only if it:
- completes a defined task suite,
- passes minimum property checks,
- has a visible failure ledger,
- is versioned,
- has no repeated severe forbidden behavior.

### 15.2 Evaluated → Governance-Grade
Only if it:
- passes extended suites,
- survives regression reruns,
- shows acceptable challenge history,
- documents model dependence,
- provides portability posture,
- has no unresolved critical constitutional failure class,
- is usable within public packet standards.

Promotion must be explicit and auditable.

---

## 16. Demotion Rules

A skill may be demoted if:
- regressions appear in critical properties,
- repeated severe failure classes recur,
- portability collapses,
- model dependence becomes hidden,
- challenge findings remain unresolved,
- packet integrity becomes unstable,
- evaluation evidence becomes stale.

A demotion is not a punishment.
It is a correction of trust level.

---

## 17. Human Review Thresholds

Some failure classes require human or explicitly elevated review before promotion or retention.

These include:
- constitutional misread,
- silent certainty laundering,
- repeated omission of affected groups,
- repeated route overreach,
- unstable handling of ambiguity,
- repeated failure to preserve dissent.

The purpose is not to privilege human taste.
It is to catch failures whose institutional cost is too high for automatic averaging.

---

## 18. Bootstrap Evaluation Profile

Bootstrap should favor:
- smaller but stable task suites,
- property scoring over broad benchmark mythology,
- fast feedback with explicit failure logging,
- cross-model comparison where affordable,
- strict regression discipline before default use.

Bootstrap should avoid:
- vague “seems better” promotion,
- charisma-based skill adoption,
- overfitting to one founder’s style,
- premature governance-grade claims.

The bootstrap question is not:
- Is this perfect?

It is:
- Is this legibly better, testably stable, and bounded enough to trust at this tier?

---

## 19. Public Distribution and Community Forks

Publicly released skills should carry:
- tier label,
- supported model notes,
- known weaknesses,
- task coverage summary,
- last evaluation version,
- challenge channel,
- portability notes.

Community forks are encouraged,
but forks must not inherit trust level automatically.

A fork is a new skill lineage until it earns evaluation status.

---

## 20. Evaluation Artifacts

The system should preserve these artifacts where possible:
- task suites,
- scoring schemas,
- failure ledgers,
- regression histories,
- model compatibility notes,
- benchmark summaries,
- promotion and demotion decisions.

These artifacts are part of the public reasoning commons.

Without them, skill trust collapses back into reputation.

---

## 21. Cross-References

This document imposes requirements on:

### 21.1 SKILL_REGISTRY
Must record skill tier, evaluation status, and promotion history.

### 21.2 PACKET_FORMAT
Must define packet properties that can be validated by task suites.

### 21.3 EVIDENCE
Must inform evidence-discipline checks.

### 21.4 MINIMUM_VIABLE_PNYX
Must define the minimum evaluation burden for default bootstrap skills.

### 21.5 AUDIT_VIEWS
Should expose evaluation status, recent regressions, and failure classes in readable form.

---

## 22. Failure Signals

The following indicate that the evaluation layer is failing:

- skills promoted without stable task suites,
- outputs judged by vibe rather than property checks,
- critique loops with no scoring or regression control,
- repeated severe failures treated as “edge cases,”
- one model’s style mistaken for general skill robustness,
- no difference between exploratory and governance-grade trust,
- public defaults with stale evaluation evidence.

Repeated failure signals require evaluation redesign, not just more tasks.

---

## 23. Closing Principle

A skill should not be trusted because it sounds aligned.
It should be trusted only to the degree that it has survived structured, adversarial, repeatable evaluation.

Agility is valuable.
But without a defined feedback loop, agility becomes accelerated hallucination.

The system should therefore make skill evolution:
- fast enough to learn,
- strict enough to regress-test,
- plural enough to criticize,
- and explicit enough that trust grows only when evidence does.
