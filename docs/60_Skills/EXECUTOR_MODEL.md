# EXECUTOR_MODEL

**Status:** Normative  
**Scope:** Skill execution, eligibility, routing, admission, governance  
**Depends on:** `SYSTEM_PATCH_v1.md`  
**Related:** `EVIDENCE_PACKET.md`, `CONFIDENCE_AND_SCORING.md`, `EXECUTOR_RANKING.md`, `MODEL_INCLUSION_SANDBOX.md`, `HUMAN_EXPERT_PROTOCOL.md`

---

## 1. Purpose

This document defines the **Executor Model** of the system.

Its purpose is to separate:

- the **analytic role** that must be performed in the civic process, from
- the **actor or system** that performs it.

This separation is necessary so that the civic process does not depend on unconditional trust in a particular model, vendor, institution, or expert class.

The system must be able to say:

- **what analysis is required**,
- **who is allowed to perform it**,
- **under what conditions**,
- **with what evidentiary burden**,
- **with what review and fallback rules**.

---

## 2. Core Distinction

### 2.1 Skill
A **Skill** is a protocol-defined analytic function.

A skill defines:

- mandate,
- scope,
- required outputs,
- constraints,
- review obligations,
- expected evidence burden.

A skill does **not** define who or what executes it.

### 2.2 Executor
An **Executor** is the actor or system authorized to perform a skill.

An executor may be:

- an AI model,
- a human expert,
- a human expert panel,
- a hybrid AI+human workflow,
- a replicated multi-executor configuration.

### 2.3 Canonical rule

> Skills define required civic functions.  
> Executors are replaceable implementations of those functions.

This rule is foundational.

---

## 3. Why the Executor Model Exists

The Executor Model exists because a civic system must avoid the following failures:

- treating model output as legitimate by default,
- binding protocol authority to a vendor or model family,
- informal human overrides outside the protocol,
- unclear fallback paths when machine analysis is weak,
- inability to compare different execution modes,
- inability to suspend or downgrade poor performers without rewriting the protocol.

The Executor Model ensures that:

- analysis remains protocol-bound,
- evidence standards remain stable across implementations,
- human and machine actors can be compared under shared rules,
- trust is conditional and revisable.

---

## 4. Executor Classes

The system should support at least the following executor classes.

### 4.1 AI Executor
A machine-based executor, usually a specific model version or controlled agentic pipeline.

Typical properties:

- versioned,
- benchmarkable,
- high throughput,
- variable calibration,
- may require strict sourcing controls and adversarial review.

### 4.2 Human Expert Executor
A single qualified human reviewer operating under protocol constraints.

Typical properties:

- domain-aware,
- context-sensitive,
- slower,
- may introduce personal bias,
- must disclose conflicts and follow output schema.

### 4.3 Human Panel Executor
A structured small group of human experts producing a joint or plural output.

Typical properties:

- useful for rights-sensitive or constitutionally complex cases,
- may preserve internal dissent,
- higher legitimacy cost and operational cost,
- may require explicit minority-note handling.

### 4.4 Hybrid Executor
A composite execution mode where machine and human components are both formal parts of the result.

Examples:

- AI draft + human validation,
- AI evidence retrieval + human legal interpretation,
- human report + AI traceability checker.

### 4.5 Replicated / Dual Executor
Two or more independent executors perform the same skill in parallel.

Examples:

- AI vs AI from different provider lineages,
- AI + human,
- human panel + adversarial external reviewer.

This class is especially useful when:

- stakes are high,
- evidence is contested,
- capture risk is high,
- calibration is uncertain.

---

## 5. Executor Identity

Every executor admitted into the system must have a stable protocol identity.

An executor identity should include, where applicable:

- executor ID,
- executor class,
- implementation name,
- provider or institutional origin,
- version,
- declared domain scope,
- jurisdiction scope,
- admission status,
- ranking history,
- review history,
- suspension or downgrade history.

The system must not rely on informal human memory or undocumented operational assumptions about executors.

---

## 6. Eligibility and Admissibility

### 6.1 Admission required
No executor should participate in live civic analysis without explicit admission.

### 6.2 Admissibility is scoped
Admission is not global trust.
Admission should be scoped by:

- skill,
- proposal type,
- risk level,
- domain,
- jurisdiction,
- execution mode,
- version.

An executor may be admissible for one context and inadmissible for another.

### 6.3 Required admission properties
To be admissible, an executor should have:

- schema compatibility,
- evidence packet capability,
- citation discipline,
- acceptable calibration,
- acceptable hallucination/error profile,
- routing compatibility,
- auditability,
- challengeability,
- no unresolved exclusion condition.

### 6.4 Admission is provisional
Admission should be understood as conditional and revisable.
It is permission to participate under constraints, not recognition of inherent epistemic authority.

---

## 7. Exclusion Conditions

An executor should be excluded, suspended, or scoped down when it shows patterns such as:

- repeated unsupported claims,
- systematic citation failure,
- poor legal or jurisdictional mapping,
- chronic overconfidence,
- unstable behavior after version change,
- inability to preserve minority views where required,
- excessive provider concentration risk,
- hidden conflict of interest,
- refusal or inability to comply with output schema,
- adversarial brittleness in benchmarked conditions.

Exclusion may be temporary, scoped, or full depending on severity.

---

## 8. Executor Obligations

Every executor must operate under explicit protocol obligations.

### 8.1 Output obligation
The executor must return the required structured output for the assigned skill, normally an `Evidence Packet` or compatible schema.

### 8.2 Traceability obligation
The executor must make material claims traceable to sources, assumptions, or explicit inferences.

### 8.3 Uncertainty obligation
The executor must declare unknowns, ambiguities, contested interpretations, and evidence gaps.

### 8.4 Scope discipline obligation
The executor must not silently drift beyond its mandate, jurisdiction, or approved role.

### 8.5 Challengeability obligation
The executor output must be inspectable and open to procedural challenge.

### 8.6 Independence obligation
Where independence is required, the executor must satisfy separation requirements such as different provider lineage, institution, panel composition, or reviewer chain.

---

## 9. Executor Rights within the Protocol

To function fairly and predictably, executors should also have constrained protocol rights.

### 9.1 Right to a clear mandate
Executors must receive a bounded task definition.

### 9.2 Right to declared scope
Executors must know the jurisdiction, proposal class, deadlines, and required burden.

### 9.3 Right to abstain or flag inability
Executors must be able to declare that they cannot responsibly complete the task due to insufficient evidence, scope mismatch, or conflict of interest.

### 9.4 No right to bypass review
Executors do not have a right to skip mandatory audit, replication, or human review.

---

## 10. Binding between Skill and Executor

A skill assignment is valid only when the system binds:

- the required skill,
- the chosen executor,
- the execution mode,
- the evidence burden,
- the review route,
- the fallback rule,
- the deadline and timing constraints.

This binding should occur at panel specification time or equivalent protocol stage.

The binding must be visible in the audit trail.

---

## 11. Execution Modes

The system should explicitly represent execution modes rather than hiding them behind implementation detail.

### 11.1 Solo execution
One executor performs one skill.

### 11.2 Verified execution
One executor performs the skill and another verifies defined aspects of the output.

### 11.3 Dual execution
Two independent executors perform the same skill in parallel.

### 11.4 Layered execution
Different parts of one skill are distributed across executors.

Example:

- AI retrieves sources,
- human reviews legal applicability,
- AI checks schema completeness,
- adversarial reviewer challenges omissions.

### 11.5 Escalated execution
A first-pass executor performs the skill, but the result is automatically routed upward due to risk, low confidence, challenge, or conflict.

### 11.6 Replication Record requirement
Whenever verified, dual, or otherwise replicated execution is invoked for verification,
the run must produce a `ReplicationRecord` (`../90_Information/SCHEMAS.md` §24)
declaring the overlap between the executions across:

- model family,
- provider,
- sources consulted,
- context supplied,
- methodology,

and whether one output influenced another.

Replication without a declared overlap record must not be presented as independent agreement.

---

## 12. Review Routing by Executor Type

Executor type must affect review routing.

Examples:

- AI-only outputs may require stronger citation checks,
- human-only outputs may require conflict disclosure and dissent preservation,
- hybrid outputs may require clear attribution of which component produced which claims,
- replicated outputs may trigger synthesis rules that preserve disagreements rather than averaging them away.

The protocol should never treat all executor types as operationally identical.

---

## 13. Mandatory Human Involvement Cases

The system should define classes of cases where a human expert or human panel must be involved.

These may include:

- constitutional interpretation,
- severe rights restrictions,
- emergency justification review,
- novel legal domains,
- low-confidence machine outputs,
- major public contestation,
- severe evidence conflict,
- high capture-risk proposals.

Human involvement may take different forms:

- primary executor,
- verifier,
- escalation reviewer,
- appeal reviewer,
- adversarial reviewer.

---

## 14. Conflict of Interest and Independence

### 14.1 Human conflict handling
Human executors must disclose relevant conflicts of interest, institutional relationships, and stake in the outcome where applicable.

### 14.2 Machine-side conflict analogs
Non-human executors may also create structural conflict risks, including:

- provider concentration,
- shared training/data lineage,
- common orchestrator bias,
- common retrieval layer bias,
- shared policy filters that distort plurality.

### 14.3 Independence requirements
When independence is required, the system should require meaningful separation, not cosmetic variation.

Different prompt wrappers around the same underlying dependency chain should not automatically count as independent reviewers.

Where independence is claimed for replicated executions,
the claim must be backed by the `ReplicationRecord` overlap declaration (§11.6);
an independence claim with undeclared overlap is itself a challengeable defect.

---

## 15. Ranking and Performance Memory

Executors must accumulate structured performance history.

This history should include:

- performance by skill,
- performance by jurisdiction,
- calibration behavior,
- evidence quality behavior,
- challenge rate,
- correction rate,
- reversal rate,
- benchmark results,
- production review results,
- version transitions.

This performance memory supports contextual ranking and routing decisions.

The system must rank executors **in context**, not as universally superior or inferior.

---

## 16. Versioning and Drift

### 16.1 Version awareness
A versioned executor is not the same executor after meaningful change.

### 16.2 Version change consequences
Major version changes should trigger revalidation, shadow mode, or scope reduction until performance is re-established.

### 16.3 Drift monitoring
Executors should be monitored for drift in:

- citation discipline,
- legal mapping,
- calibration,
- schema compliance,
- adversarial robustness,
- consistency across reruns.

A drift-sensitive system is safer than a static trust model.

---

## 17. Sandbox Admission Path

All candidate executors should pass through an inclusion path before live use.

A typical path may include:

1. registration,
2. declared scope,
3. benchmark execution,
4. adversarial tests,
5. evidence-packet validation,
6. calibration review,
7. shadow mode,
8. limited production scope,
9. re-ranking after observed performance.

No executor should move directly from availability to full civic authority.

---

## 18. Fallback and Replacement Logic

The system must support executor replacement without rewriting the civic process.

Fallback logic should define:

- what happens if an executor abstains,
- what happens if an executor fails schema validation,
- what happens if a required human expert is unavailable,
- what happens if confidence is too low,
- what happens if replication yields severe disagreement,
- what happens if a previously admitted executor is suspended mid-process.

Fallbacks must be procedural, not ad hoc.

---

## 19. Audit Requirements

The audit layer should record at least:

- which skill was required,
- which executor was selected,
- why that executor was eligible,
- which execution mode was used,
- whether replication or verification occurred,
- whether challenges were raised,
- whether escalation occurred,
- whether the executor later proved miscalibrated or defective.

The executor layer itself must be auditable.

---

## 20. Governance Responsibilities

Governance must explicitly decide:

- who admits executors,
- who defines admissibility criteria,
- who maintains benchmark suites,
- who audits ranking logic,
- who reviews conflicts of interest,
- who authorizes suspensions and re-entry,
- how human expert pools are formed,
- how diversity and independence are preserved.

Without executor governance, proposal governance is incomplete.

---

## 21. Minimum Canonical Schema

Every executor record should minimally support fields such as:

- `executor_id`
- `executor_class`
- `implementation_name`
- `provider_or_institution`
- `version`
- `admission_status`
- `approved_skills`
- `approved_domains`
- `approved_jurisdictions`
- `risk_scope`
- `required_review_mode`
- `independence_constraints`
- `ranking_profile`
- `revalidation_due_at`
- `suspension_state`
- `notes`

Detailed schemas belong in `SCHEMAS.md` and related documents.

---

## 22. Canonical Operating Rules

1. No skill is inherently bound to a model.  
2. No executor is trusted by identity alone.  
3. Admission is scoped, conditional, and revisable.  
4. Executors must produce evidence-backed, challengeable outputs.  
5. Human experts are formal executors, not informal patches.  
6. High-risk cases may require human or replicated execution.  
7. Version change may invalidate prior trust assumptions.  
8. Ranking must be contextual.  
9. Fallbacks must be procedural.  
10. The executor layer is part of governance, not merely implementation.

---

## 23. Closing Principle

> The system should govern analysis the way it governs power:  
> through explicit roles, constrained authority, admissible evidence, auditable conduct, and revisable trust.