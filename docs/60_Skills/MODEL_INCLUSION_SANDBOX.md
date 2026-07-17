# MODEL_INCLUSION_SANDBOX

**Status:** Normative  
**Scope:** Executor admission, dry-run evaluation, shadow mode, staged production entry  
**Depends on:** `SYSTEM_PATCH_v1.md`, `EXECUTOR_MODEL.md`, `EVIDENCE_PACKET.md`, `CONFIDENCE_AND_SCORING.md`, `EXECUTOR_RANKING.md`  
**Related:** `REVALIDATION_POLICY.md`, `TASK_SUITE_v0.md`, `SCHEMAS.md`, `HUMAN_EXPERT_PROTOCOL.md`

---

## 1. Purpose

This document defines the **Model Inclusion Sandbox**.

Its purpose is to ensure that no AI model, human executor workflow, hybrid executor, or replicated execution pattern is allowed into the live civic pathway merely because it is available, impressive, popular, or technically integrated.

Before an executor gains live protocol participation, it must first demonstrate bounded competence inside a controlled dry-run environment that tests its usefulness under civic conditions.

The sandbox is therefore not a demo environment. It is an **admission environment**.

---

## 2. Canonical Rule

> No executor should enter live civic analysis without prior sandbox evaluation, scoped admission, and observable performance under controlled conditions.

Availability is not admissibility.

---

## 3. Why the Sandbox Exists

The sandbox exists to prevent the following failures:

- giving civic authority to untested executors,
- mistaking general intelligence for civic reliability,
- trusting models that perform well on prompts but fail on full proposals,
- allowing hidden hallucination, poor sourcing, or weak jurisdiction handling into live process,
- letting version updates silently inherit legitimacy,
- admitting executors without adversarial stress testing,
- failing to compare AI and human execution under shared output constraints.

A civic system should not ask:

- “Can this model answer questions?”

It must ask:

- “Can this executor perform a bounded civic role under evidence, routing, and audit constraints?”

---

## 4. What the Sandbox Is and Is Not

### 4.1 The sandbox is an admission track
It evaluates whether an executor may enter a live civic role, and under what scope.

### 4.2 The sandbox is not a trust shortcut
Strong sandbox performance does not grant permanent or unlimited trust.

### 4.3 The sandbox is not only for AI models
It applies to all executor classes where admission or revalidation is required, including:

- AI executors,
- human expert workflows,
- human panels where standardized output is required,
- hybrid workflows,
- replicated or adversarial reviewer configurations.

### 4.4 The sandbox tests civic usefulness, not only raw accuracy
The sandbox is designed around protocol fitness, not abstract benchmark vanity.

---

## 5. Admission Goals

The sandbox should determine at least the following:

- whether the executor can produce valid Evidence Packets,
- whether it can stay within scope,
- whether it can cite and trace claims,
- whether it handles jurisdiction correctly,
- whether it discloses unknowns honestly,
- whether its confidence behavior is calibrated,
- whether it behaves acceptably under adversarial stress,
- whether it should be admitted, restricted, shadowed, or rejected,
- whether it is fit only for specific skills, domains, or risk levels.

---

## 6. Sandbox Object of Evaluation

The sandbox evaluates **executors-in-context** rather than abstract systems.

That means evaluation should be scoped by dimensions such as:

- executor identity,
- version,
- skill,
- domain,
- jurisdiction,
- proposal class,
- risk scope,
- execution mode.

The sandbox should not assume that good performance in one context transfers automatically to all others.

---

## 7. Test Substrate

The sandbox should test executors on **full civic artifacts** rather than isolated prompts whenever possible.

Relevant artifacts include:

- full proposals,
- proposal revisions,
- intake records,
- classification records,
- mandate definitions,
- source bundles,
- legal context bundles,
- public comments,
- challenges,
- adversarial critiques,
- synthetic but realistic execution conditions.

The more the sandbox resembles real civic flow, the more meaningful admission becomes.

---

## 8. Test Case Families

The sandbox should maintain a diverse case suite.

### 8.1 Intake Cases
Tests whether the executor understands proposal completeness, scope mismatches, and invalid urgency claims.

### 8.2 Classification Cases
Tests whether the executor correctly identifies governance layer, triviality, emergency status, and review implications.

### 8.3 Jurisdiction Cases
Tests locality, overlapping jurisdiction, legal applicability, and cross-layer governance reasoning.

### 8.4 Legal and Rights Cases
Tests constitutional sensitivity, rights framing, legal ambiguity recognition, and escalation discipline.

### 8.5 Feasibility and Implementation Cases
Tests operational realism, implementation constraints, and evidence-backed assessment of practical effects.

### 8.6 Budget and Resource Cases
Tests cost reasoning, public finance grounding, budget assumptions, and resource constraint realism.

### 8.7 Anti-Capture Cases
Tests whether the executor detects disproportionate benefit, institutional capture risk, hidden concentration, or manipulation vectors.

### 8.8 Adversarial Cases
Tests whether the executor resists false consensus, missing-source traps, misleading framing, prompt injection equivalents, and rhetorical manipulation.

### 8.9 Emergency Cases
Tests whether the executor handles urgency without erasing audit, evidence, or post-hoc accountability.

### 8.10 Trivial vs Non-Trivial Cases
Tests whether the executor scales its burden properly rather than over- or under-processing.

### 8.11 Public Challenge Cases
Tests whether the executor can respond appropriately when evidence, jurisdiction, or synthesis is challenged.

### 8.12 Synthesis Cases
Tests whether the executor, if acting in a synthesis role, preserves disagreement instead of flattening it.

---

## 9. Case Sources

The sandbox may use a mixture of:

- historical public cases,
- curated civic simulations,
- synthetic adversarial cases,
- jurisdiction-specific test proposals,
- replayed prior proposal packets,
- gold-annotated cases where available,
- live-shadow comparisons against non-binding real proposals.

The system should not rely on a single source of test realism.

---

## 10. Gold Standards and Non-Gold Cases

### 10.1 Gold-annotated cases
Where feasible, the sandbox should maintain cases with carefully reviewed expected outputs or evaluation anchors.

These may include:

- correct jurisdiction mapping,
- known relevant legal basis,
- expected evidence classes,
- known omitted-risk traps,
- expected escalation conditions.

### 10.2 Open or contested cases
Some cases should remain deliberately open-ended so the system evaluates judgment quality rather than answer memorization.

Both case types are necessary.

---

## 11. Evaluation Dimensions

Executors should be evaluated along multiple dimensions.

### 11.1 Evidence Completeness
Did the executor gather and use enough relevant evidence for the mandate?

### 11.2 Citation Discipline
Are claims actually traceable to sources rather than decorated with irrelevant references?

### 11.3 Jurisdiction Fit
Did the executor identify the correct legal/geographic frame and avoid importing inapplicable assumptions?

### 11.4 Legal Currency
Did it use current law, regulation, and administrative context where required?

### 11.5 Claim Traceability
Are material claims separately represented and supported?

### 11.6 Unknowns Discipline
Did the executor acknowledge what it could not justify?

### 11.7 Confidence Calibration
Did its confidence signals match the actual evidentiary strength and later review outcome?

### 11.8 Adversarial Robustness
Did it resist misleading framing, missing evidence traps, and false consensus pressure?

### 11.9 Minority-View Preservation
Did it preserve relevant dissent or contrary evidence when required?

### 11.10 Schema Compliance
Did it produce a valid Evidence Packet or equivalent required structure?

### 11.11 Stability Across Repeats
Was the output acceptably stable across reruns or controlled repeats where stability matters?

### 11.12 Procedural Usefulness
Did the output help the civic loop route the case correctly?

---

## 12. Negative Evaluation Signals

The sandbox should explicitly record harmful behaviors such as:

- hallucinated laws,
- uncited material claims,
- wrong jurisdiction,
- stale legal references,
- fake completeness,
- omission of contrary evidence,
- false urgency confirmation,
- overconfidence under evidence scarcity,
- rhetorical overreach,
- inability to abstain when appropriate,
- consensus-smoothing in synthesis,
- brittle behavior under adversarial variation.

---

## 13. Executor Comparability

The sandbox must support comparison across executor classes under shared civic constraints.

This does not mean forcing identical behavior, but it does mean evaluating comparable outputs on dimensions such as:

- evidence quality,
- traceability,
- routing usefulness,
- calibration,
- challengeability,
- auditability.

The sandbox should not privilege machine executors merely because they are easier to score at scale.

---

## 14. Phases of Admission

Admission should be staged.

### 14.1 Registration Phase
The candidate executor is registered with declared identity, class, version, scope, and dependencies.

### 14.2 Baseline Sandbox Phase
The executor runs on a core suite of representative cases.

### 14.3 Adversarial Sandbox Phase
The executor runs on stress cases designed to surface failure under pressure.

### 14.4 Shadow Mode Phase
The executor is exposed to live-like or real proposals without affecting actual decisions.

### 14.5 Limited Production Scope
The executor is allowed into narrowly scoped live participation under explicit oversight.

### 14.6 Expanded Scope or Restriction
Based on observed performance, the executor may be expanded, capped, downgraded, or rejected.

---

## 15. Admission Outcomes

A sandbox evaluation should produce explicit outcomes.

Examples:

- `admit_in_scope`
- `admit_with_mandatory_verification`
- `admit_shadow_only`
- `restrict_to_low_risk`
- `restrict_to_specific_skill`
- `require_retraining_or_reconfiguration`
- `reject`
- `suspend_pending_review`

Admission outcomes must be scoped, not generic.

---

## 16. Scope of Admission

Admission should specify at least:

- approved skills,
- approved domains,
- approved jurisdictions,
- risk limits,
- required review mode,
- whether independent replication is required,
- whether human verification is mandatory,
- whether the executor may synthesize or only produce first-pass packets.

This prevents overextension from narrow success.

---

## 17. Shadow Mode

### 17.1 Purpose
Shadow mode tests the executor on real or realistic proposal flow without letting its outputs bind the civic process.

### 17.2 Benefits
Shadow mode helps reveal:

- scaling issues,
- unstable behavior,
- unexpected routing mistakes,
- poor performance under real evidence noise,
- failure to generalize from curated benchmarks.

### 17.3 Shadow comparison
Where possible, shadow outputs should be compared against:

- admitted executor outputs,
- human review,
- later real outcomes,
- challenge behavior,
- validation and audit findings.

---

## 18. Rejection and Restriction Logic

The sandbox must not be biased toward admission.

A candidate should be restricted or rejected when it shows patterns such as:

- repeated hallucination,
- weak citation discipline,
- systematic jurisdiction mismatch,
- poor calibration,
- severe fragility under adversarial cases,
- inability to produce valid structured outputs,
- hidden dependency concentration where independence is required,
- inability to preserve uncertainty honestly.

A civic sandbox is useful only if it can say no.

---

## 19. Version Sensitivity

A new model version, major workflow change, or retrieval architecture change should normally trigger sandbox re-entry or partial revalidation.

The system should not assume continuity across meaningful changes in:

- model weights,
- prompting logic,
- orchestration pipeline,
- retrieval layer,
- validation layer,
- human review protocol,
- external data dependencies.

---

## 20. Human and Hybrid Executors in the Sandbox

Human and hybrid executors should also be testable in the sandbox when the system requires standardized civic output.

Possible evaluation objects include:

- a human legal review workflow,
- a human panel process,
- an AI-first draft plus human verifier workflow,
- a two-executor adversarial pair,
- a retrieval-plus-synthesis hybrid chain.

The sandbox therefore evaluates not only models, but operational review patterns.

---

## 21. Benchmark Governance

The sandbox case suite must itself be governed.

Governance should define:

- who curates the cases,
- how often cases are refreshed,
- how jurisdictions are represented,
- how adversarial cases are created,
- how benchmark leakage is mitigated,
- how public or private the suite is,
- how gold standards are maintained,
- how the suite evolves without destabilizing comparability.

If the benchmark suite is weak, the admission layer becomes weak.

---

## 22. Benchmark Leakage and Gaming

The system should assume that executors may eventually adapt to known benchmarks.

Therefore the sandbox should include a mix of:

- stable baseline cases,
- rotating hidden cases,
- adversarially refreshed cases,
- live-shadow observations.

The goal is not secrecy alone, but resistance to performative overfitting.

---

## 23. Scoring and Ranking Consequences

Sandbox results should feed:

- admission status,
- contextual ranking initialization,
- required oversight level,
- scope caps,
- revalidation priority,
- downgrade and suspension watchlists.

The sandbox is not separate from the ranking layer; it is one of its foundations.

---

## 24. Audit Requirements

Every sandbox evaluation should preserve an audit trail including:

- candidate identity,
- version,
- declared scope,
- case suite version,
- scoring version,
- case-level results,
- detected failure modes,
- admission decision,
- scope limits,
- required follow-up,
- reviewer or evaluator notes where applicable.

Admission itself must be auditable.

---

## 25. Public Transparency

The full benchmark internals may not always be public, especially where gaming risk is material.

However, the civic system should publish enough information to support legitimacy, such as:

- whether an executor was admitted,
- the general scope of admission,
- whether it is probationary,
- whether human verification is required,
- whether recent revalidation occurred,
- whether material restrictions or downgrades exist.

Civic legitimacy should not depend on a secret admission priesthood.

---

## 26. Failure Modes of the Sandbox Itself

The sandbox can fail in several ways.

Examples:

- overfitting to narrow benchmark cases,
- testing prompt ability rather than civic role performance,
- ignoring jurisdiction complexity,
- evaluating only AI and not human workflows,
- rewarding speed or polish over traceability,
- failing to include adversarial cases,
- allowing admission without shadow mode,
- weak or outdated gold standards,
- opaque rejection logic,
- capture of the benchmark curation process.

The sandbox layer must itself be governable and reviewable.

---

## 27. Minimum Canonical Fields

A sandbox evaluation record should minimally support fields such as:

- `sandbox_evaluation_id`
- `executor_id`
- `executor_version`
- `executor_class`
- `declared_scope`
- `case_suite_version`
- `scoring_version`
- `evaluated_skills[]`
- `evaluated_domains[]`
- `evaluated_jurisdictions[]`
- `evaluated_risk_scope`
- `case_results[]`
- `aggregate_scores`
- `detected_failure_modes[]`
- `calibration_summary`
- `admission_outcome`
- `admission_scope`
- `required_review_mode`
- `shadow_mode_required`
- `revalidation_due_at`
- `evaluator_notes`
- `created_at`

Detailed field typing belongs in `SCHEMAS.md`.

---

## 28. Canonical Operating Rules

1. No live civic participation without prior sandbox evaluation.  
2. Sandbox evaluation is scoped, not global.  
3. Full civic artifacts matter more than isolated prompt tests.  
4. Adversarial testing is mandatory for serious admission.  
5. Strong sandbox performance does not eliminate the need for shadow mode and revalidation.  
6. Human and hybrid executors are also subject to structured admission where applicable.  
7. Admission outcomes must be explicit and auditable.  
8. Version changes may require sandbox re-entry.  
9. The benchmark suite itself must be governed.  
10. The sandbox exists to protect civic legitimacy, not to celebrate model capability.

---

## 29. Closing Principle

> An executor should not receive civic authority because it is available.  
> It should receive limited, reviewable authority only after proving that it can operate inside the discipline of the civic process. 
