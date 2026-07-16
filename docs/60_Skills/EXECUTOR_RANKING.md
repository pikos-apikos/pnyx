# EXECUTOR_RANKING

**Status:** Normative  
**Scope:** Contextual ranking, performance memory, routing influence, downgrade and suspension signals  
**Depends on:** `SYSTEM_PATCH_v1.md`, `EXECUTOR_MODEL.md`, `EVIDENCE_PACKET.md`, `CONFIDENCE_AND_SCORING.md`  
**Related:** `MODEL_INCLUSION_SANDBOX.md`, `REVALIDATION_POLICY.md`, `SCHEMAS.md`, `HUMAN_EXPERT_PROTOCOL.md`

---

## 1. Purpose

This document defines how the system ranks executors.

Its purpose is to prevent the system from making simplistic judgments such as:

- “this model is good,”
- “this expert is trusted,”
- “this provider is reliable,”
- “this panel performed well once, therefore it should keep authority.”

The system must instead maintain a **structured performance memory** that evaluates executors in context.

Ranking exists so that civic analysis is assigned, reviewed, and escalated on the basis of observed performance rather than reputation alone.

---

## 2. Canonical Rule

> Executors must be ranked in context, not in the abstract.

No executor should be treated as universally strong or weak across all skills, domains, jurisdictions, proposal classes, and versions.

---

## 3. Why Ranking Exists

Ranking exists because a civic system must remember how executors behave over time.

Without ranking, the system becomes vulnerable to:

- static trust in changing models,
- invisible performance drift,
- overreliance on prestigious but miscalibrated experts,
- repeated assignment to poor performers,
- inability to scope or downgrade executors intelligently,
- weak routing for high-risk cases.

Ranking makes the executor layer governable.

---

## 4. What Ranking Is and Is Not

### 4.1 Ranking is a contextual performance signal
Ranking is a structured assessment of how suitable an executor is for a given kind of civic work under specific conditions.

### 4.2 Ranking is not moral status
Ranking does not declare that an executor is “good” in a general sense.

### 4.3 Ranking is not permanent trust
Ranking must remain revisable as:

- versions change,
- performance drifts,
- evidence behavior shifts,
- benchmark suites improve,
- governance priorities evolve.

### 4.4 Ranking is advisory but consequential
Ranking should not fully replace rule-based admissibility, but it must influence assignment, verification, escalation, and suspension.

---

## 5. Ranking Unit

The canonical ranking unit should be an **executor-in-context** record.

At minimum, context should include:

- executor identity,
- version,
- skill,
- proposal class,
- domain,
- jurisdiction,
- risk scope,
- execution mode.

This means the system ranks not “Executor X” in general, but something closer to:

- Executor X, version 4, for feasibility review, in municipal governance, in Greece, on medium-risk implementation proposals.

This granularity is necessary for honest governance.

---

## 6. Context Dimensions

The system should support ranking across dimensions such as:

### 6.1 Skill
Examples:

- rights review,
- constitutional review,
- feasibility review,
- economic/resource review,
- anti-capture review,
- adversarial critique.

### 6.2 Domain
Examples:

- local administration,
- public finance,
- housing,
- environmental regulation,
- digital infrastructure,
- procurement,
- emergency response.

### 6.3 Jurisdiction
Examples:

- municipal,
- regional,
- national,
- EU-level,
- multi-layer legal context.

### 6.4 Proposal Class
Examples:

- trivial,
- non-trivial,
- emergency,
- constitutional,
- rights-sensitive,
- advisory,
- hybrid execution route.

### 6.5 Risk Level
Examples:

- low-risk,
- medium-risk,
- high-risk,
- system-changing.

### 6.6 Execution Mode
Examples:

- solo,
- verified,
- dual,
- hybrid,
- escalated.

---

## 7. Ranking Inputs

Ranking should draw from multiple sources of evidence.

### 7.1 Sandbox Benchmark Performance
Pre-production performance in the inclusion sandbox.

### 7.2 Production Packet Quality
Observed quality of Evidence Packets in live or shadow operation.

### 7.3 Calibration Performance
How well confidence tracks later review outcomes.

### 7.4 Challenge and Reversal History
How often outputs are challenged, corrected, downgraded, or reversed.

### 7.5 Evidence Discipline
Quality of sourcing, traceability, jurisdiction fit, and unknowns disclosure.

### 7.6 Adversarial Robustness
Performance under stress tests, adversarial cases, and conflict-rich proposals.

### 7.7 Stability across Reruns or Repeats
Whether repeated execution remains coherent, traceable, and appropriately scoped.

### 7.8 Governance and Conduct Signals
Including conflict-of-interest issues, independence failures, schema noncompliance, or unexplained behavior drift.

---

## 8. Core Ranking Metrics

The system should track at least the following families of metrics.

### 8.1 Evidence Quality Metrics
Examples:

- evidence coverage,
- source quality,
- claim traceability,
- jurisdiction relevance,
- legal currency,
- source diversity where relevant.

### 8.2 Confidence and Calibration Metrics
Examples:

- confidence accuracy,
- overconfidence rate,
- underconfidence rate,
- confidence dispersion quality,
- appropriate use of uncertainty.

### 8.3 Review Outcome Metrics
Examples:

- validation pass rate,
- human verifier agreement,
- appellate survival rate,
- reversal rate,
- correction rate.

### 8.4 Adversarial Integrity Metrics
Examples:

- omission under adversarial pressure,
- minority-view preservation,
- resistance to false consensus,
- capture-risk detection quality.

### 8.5 Procedural Reliability Metrics
Examples:

- schema compliance,
- timeliness,
- abstention quality,
- fallback appropriateness,
- reproducibility where expected.

---

## 9. Ranking Outputs

A ranking system should produce outputs useful for governance and routing.

Examples:

- contextual score,
- confidence in the rank itself,
- rank band,
- risk suitability profile,
- approved scope,
- preferred review mode,
- escalation tendency,
- downgrade warnings,
- revalidation priority.

The system should avoid simplistic public leaderboards detached from context.

---

## 10. Rank Bands

The protocol may use broad rank bands such as:

- **Band 1: Strongly preferred in scope**
- **Band 2: Admissible with normal oversight**
- **Band 3: Admissible only with extra review**
- **Band 4: Restricted or probationary**
- **Band 5: Suspended or not admissible**

These bands are illustrative. The exact mapping may vary by governance rules and scoring version.

---

## 11. Ranking and Assignment

Ranking should influence assignment decisions.

Examples:

- higher-ranked executors in a given context may be preferred for first-pass execution,
- lower-ranked but admissible executors may be used in shadow mode,
- probationary executors may be limited to low-risk cases,
- weakly ranked executors may only serve as secondary reviewers,
- high-risk proposals may require at least one strongly ranked executor plus an independent verifier.

Ranking should inform assignment, but should not override hard eligibility or mandatory human-review rules.

---

## 12. Ranking and Review Routing

Ranking should also affect review depth.

Examples:

- a lower-ranked executor may automatically trigger human verification,
- a newly admitted version may trigger replication,
- an executor with recent calibration drift may trigger adversarial review,
- an executor with strong contextual performance may still require escalation in constitutional cases.

Ranking is therefore not only about selection, but about supervision intensity.

---

## 13. Ranking and Versioning

### 13.1 Version-specific memory
A meaningful version change should create a new ranking track or a reset/partial reset in trust assumptions.

### 13.2 No blind inheritance
A new major version should not inherit top ranking by default merely because an earlier version performed well.

### 13.3 Transitional handling
Version transitions may be handled through:

- shadow mode,
- provisional rank caps,
- restricted domain use,
- accelerated revalidation.

---

## 14. Ranking for Human Experts and Panels

Human executors should also be ranked under compatible principles.

Relevant factors may include:

- citation discipline,
- legal and jurisdiction fit,
- minority-note quality,
- calibration of caution,
- appeal survival,
- conflict-of-interest compliance,
- timeliness and completeness,
- consistency across related cases.

Ranking must not be designed only around machine characteristics.

---

## 15. Ranking for Hybrid and Replicated Execution

Hybrid and replicated execution modes may have their own ranking records.

Examples:

- AI + human verifier pair,
- two-provider adversarial pair,
- panel + external critic,
- AI retrieval + human legal synthesis workflow.

Sometimes the combined workflow is what should be ranked, not only its components in isolation.

---

## 16. Positive and Negative Signals

### 16.1 Positive signals
Examples:

- strong evidence discipline,
- reliable jurisdiction mapping,
- low overconfidence,
- good performance under challenge,
- appropriate abstention when under-evidenced,
- preservation of minority views,
- stable output quality after updates.

### 16.2 Negative signals
Examples:

- repeated unsupported claims,
- chronic overconfidence,
- high reversal rate,
- hidden assumptions,
- source laundering through weak references,
- jurisdiction mismatch,
- schema failure,
- provider concentration risk when independence is required,
- drift after version change.

---

## 17. Ranking Confidence

The system should also represent **confidence in the ranking itself**.

This matters because some executors will have:

- rich historical data,
- sparse historical data,
- unstable recent data,
- cross-context data that may not transfer well.

An executor with limited but promising data should not be treated the same as one with long, well-validated performance history.

Ranking confidence may influence whether the system uses the rank aggressively or conservatively.

---

## 18. Sparse Data and Cold Start Handling

The system must define how to treat new or sparsely observed executors.

Options include:

- sandbox-only status,
- shadow mode,
- low-risk-only assignment,
- mandatory verification,
- capped maximum rank until sufficient evidence accumulates.

The system should avoid both naive optimism and permanent exclusion due to data sparsity.

---

## 19. Drift and Downgrade Logic

Ranking must react to deterioration.

Triggers may include:

- rising overconfidence,
- declining traceability,
- increased reversal rate,
- repeated legal currency mistakes,
- worsening adversarial robustness,
- unexplained performance instability,
- challenge clusters in a particular domain or jurisdiction.

Downgrade logic should be explicit and auditable.

---

## 20. Suspension Signals

An executor may be suspended when ranking and governance signals indicate that continued use is unsafe.

Potential triggers include:

- severe repeated hallucination,
- hidden or undeclared conflicts,
- major benchmark regression,
- sustained jurisdictional failure,
- systematic suppression of contrary evidence,
- inability to produce valid Evidence Packets,
- failure after major version change,
- compromised independence conditions.

Suspension may be global or context-scoped.

---

## 21. Governance of Ranking

Ranking must itself be governed.

Governance must define:

- who maintains ranking logic,
- who audits rank behavior,
- who may challenge a rank,
- how often ranking updates occur,
- whether updates are continuous or batched,
- how ranking changes affect active cases,
- how public-facing rank information is presented,
- how bias or capture in the ranking system is detected.

An opaque ranking layer can itself become a capture vector.

---

## 22. Prospective-Only Effects

Changes in ranking logic or a specific executor’s rank should normally apply prospectively.

Active cases should not be destabilized by silent midstream changes unless the protocol defines explicit emergency conditions such as:

- severe newly discovered executor defect,
- disqualifying conflict of interest,
- invalidated legal basis,
- governance-authorized suspension.

---

## 23. Public Transparency

The full internal rank computation may not always need public exposure, but the system should publish enough information to support civic trust.

At minimum, public-facing transparency should preserve:

- whether the executor was admitted,
- whether the executor was operating in or near its approved scope,
- whether the executor was probationary or strongly ranked,
- whether extra review was triggered by ranking,
- whether later correction materially affected the executor’s standing.

The public should not be asked to trust invisible assignment logic.

---

## 24. Audit Requirements

The audit trail should preserve at least:

- ranking version,
- contextual rank used,
- rank confidence,
- assignment consequences,
- review consequences,
- downgrade or suspension signals considered,
- later outcome linkage.

This makes it possible to inspect whether ranking improved or distorted governance.

---

## 25. Failure Modes

Common ranking failure modes include:

- global leaderboards detached from context,
- letting prestige replace evidence,
- ranking by speed rather than civic quality,
- failing to separate version histories,
- allowing historical performance to mask current drift,
- rewarding confidence theater,
- penalizing honest abstention,
- opaque downgrade decisions,
- hidden bias toward dominant providers or institutions.

The system should explicitly defend against these patterns.

---

## 26. Minimum Canonical Fields

A contextual ranking record should minimally support fields such as:

- `ranking_record_id`
- `executor_id`
- `executor_version`
- `executor_class`
- `skill_id`
- `domain_scope`
- `jurisdiction_scope`
- `proposal_class`
- `risk_scope`
- `execution_mode`
- `ranking_version`
- `contextual_score`
- `rank_band`
- `rank_confidence`
- `evidence_quality_metrics`
- `calibration_metrics`
- `review_outcome_metrics`
- `adversarial_metrics`
- `procedural_reliability_metrics`
- `challenge_rate`
- `reversal_rate`
- `downgrade_signals[]`
- `suspension_signals[]`
- `approved_scope_notes`
- `review_requirements`
- `last_updated_at`
- `revalidation_due_at`
- `notes`

Detailed field typing belongs in `SCHEMAS.md`.

---

## 27. Canonical Operating Rules

1. Executors are ranked in context, not globally.  
2. Ranking is performance memory, not reputation memory.  
3. Ranking is revisable and version-sensitive.  
4. Ranking influences assignment and review depth.  
5. Ranking does not override hard admissibility or mandatory human review rules.  
6. Sparse data must be handled explicitly.  
7. Downgrades and suspensions must be auditable.  
8. Human, AI, hybrid, and replicated executors must all be rankable under compatible principles.  
9. Ranking logic itself must be governed.  
10. Civic trust requires that assignment logic not remain invisible.

---

## 28. Closing Principle

> The system should remember not who appeared impressive,  
> but who performed well, under which conditions, with what evidence behavior, and with what limits.
