# REVALIDATION_POLICY

**Status:** Normative  
**Scope:** Continuous revalidation, drift detection, scope reduction, suspension, re-entry  
**Depends on:** `SYSTEM_PATCH_v1.md`, `EXECUTOR_MODEL.md`, `CONFIDENCE_AND_SCORING.md`, `EXECUTOR_RANKING.md`, `MODEL_INCLUSION_SANDBOX.md`  
**Related:** `EVIDENCE_PACKET.md`, `SCHEMAS.md`, `HUMAN_EXPERT_PROTOCOL.md`

---

## 1. Purpose

This document defines the **Revalidation Policy** of the system.

Its purpose is to ensure that executor trust remains conditional, evidence-based, and continuously reviewable after admission.

The system must not assume that an executor that once performed well will continue to perform well indefinitely. Models change, workflows drift, human experts vary, retrieval layers evolve, incentives shift, and governance conditions change.

Revalidation exists so that civic authority remains bounded over time.

---

## 2. Canonical Rule

> Admission is not permanent trust.  
> Every admitted executor remains subject to continuous or periodic revalidation.

No executor should retain civic authority solely because it passed an earlier inclusion process.

---

## 3. Why Revalidation Exists

Revalidation exists to prevent the following failures:

- static trust in changing model versions,
- silent drift after system updates,
- gradual decline in citation or evidence discipline,
- worsening jurisdiction mismatch,
- chronic overconfidence emerging only in production,
- human reviewer inconsistency over time,
- hidden changes in retrieval, orchestration, or validation layers,
- executor capture through institutional or provider concentration,
- continued live use after repeated challenge or reversal signals.

A civic system should not ask only:

- “Was this executor ever admitted?”

It must also ask:

- “Is this executor still performing acceptably, in scope, under current conditions?”

---

## 4. Revalidation as a Governance Layer

Revalidation is not only an operational maintenance task.
It is part of governance.

It determines:

- whether an executor remains admitted,
- whether its scope should narrow or expand,
- whether extra review is now required,
- whether ranking remains justified,
- whether suspension or re-entry is necessary.

Without revalidation, ranking decays into stale memory and admission decays into ritual.

---

## 5. What Revalidation Applies To

Revalidation applies to all admitted executor classes where continued participation matters, including:

- AI executors,
- human expert executors,
- human panels,
- hybrid workflows,
- replicated execution configurations,
- synthesis workflows,
- validation or verification sub-workflows where those materially affect civic outputs.

The system should not assume only models drift.
Operational human workflows can drift too.

---

## 6. Revalidation Objects

The canonical object of revalidation is the **executor-in-context**.

Revalidation should therefore be scoped by dimensions such as:

- executor identity,
- version,
- skill,
- domain,
- jurisdiction,
- proposal class,
- risk scope,
- execution mode.

A failure or change in one context should not automatically imply identical consequences in all contexts, though severe failures may justify broader action.

---

## 7. Revalidation Triggers

The system should support multiple types of revalidation triggers.

### 7.1 Time-Based Triggers
Periodic revalidation after a defined interval.

Examples:

- every N days,
- after a review cycle,
- after a defined production volume.

### 7.2 Event-Based Triggers
Triggered by meaningful changes such as:

- new model version,
- workflow redesign,
- retrieval layer change,
- validation logic change,
- governance rule change,
- new jurisdiction expansion,
- change in human panel composition.

### 7.3 Performance-Based Triggers
Triggered by observed deterioration such as:

- rising hallucination rate,
- declining citation discipline,
- worsening calibration,
- higher reversal rate,
- increased challenge rate,
- jurisdiction mapping errors,
- repeated schema failure.

### 7.4 Risk-Based Triggers
Triggered when the executor begins handling higher-stakes work than before.

Examples:

- moving from low-risk to high-risk proposals,
- moving into constitutional review,
- moving into emergency cases,
- moving into rights-sensitive domains.

### 7.5 Governance-Based Triggers
Triggered by civic oversight or formal challenge.

Examples:

- citizen challenge to analytic integrity,
- audit finding,
- anti-capture review,
- conflict-of-interest concern,
- formal governance motion to review an executor.

---

## 8. Revalidation Modes

The system should allow multiple revalidation modes.

### 8.1 Light Revalidation
A limited review used when changes are minor and observed performance remains stable.

Examples:

- spot checks,
- targeted benchmark subset,
- production sample audit,
- narrow calibration check.

### 8.2 Full Revalidation
A comprehensive review similar to or overlapping with sandbox admission.

Examples:

- full benchmark rerun,
- adversarial stress retest,
- scope review,
- ranking reset or recalculation,
- shadow mode re-entry.

### 8.3 Emergency Revalidation
An accelerated review used when severe risk signals appear.

Examples:

- possible use of invalid law,
- major hidden conflict,
- severe post-update regression,
- repeated high-stakes failure.

Emergency revalidation may run in parallel with temporary scope reduction or suspension.

---

## 9. Signals Used in Revalidation

Revalidation should draw on multiple signals rather than one headline metric.

### 9.1 Evidence Quality Signals
Examples:

- evidence coverage trends,
- source quality behavior,
- claim traceability quality,
- unknowns disclosure discipline,
- legal currency behavior.

### 9.2 Confidence and Calibration Signals
Examples:

- overconfidence rate,
- underconfidence rate,
- confidence band reliability,
- dispersion behavior under disagreement,
- calibration by case type.

### 9.3 Review Outcome Signals
Examples:

- verifier agreement,
- challenge survival,
- appeal survival,
- correction rate,
- reversal rate,
- readiness-block frequency.

### 9.4 Procedural Reliability Signals
Examples:

- schema compliance,
- timeliness,
- abstention appropriateness,
- scope discipline,
- fallback appropriateness.

### 9.5 Adversarial and Capture Signals
Examples:

- adversarial robustness drift,
- omission of contrary evidence,
- consensus smoothing under conflict,
- provider concentration concern,
- non-independent replication masquerading as plurality.

---

## 10. Version Change Policy

### 10.1 Meaningful change breaks naive continuity
A meaningful change in executor behavior or dependency should trigger revalidation.

### 10.2 Relevant changes may include

- model weight update,
- prompt/orchestration redesign,
- retrieval pipeline modification,
- source access change,
- validator change,
- expert pool change,
- workflow staffing change,
- scoring model dependency change.

### 10.3 Transitional trust controls
Until revalidation completes, the system may apply controls such as:

- shadow mode,
- provisional rank cap,
- reduced domain scope,
- mandatory human verification,
- exclusion from high-risk cases.

---

## 11. Periodic Revalidation Windows

The system should define regular windows for revalidation.

Windows may vary by:

- executor class,
- proposal risk,
- historical stability,
- scope breadth,
- ranking confidence,
- rights sensitivity.

Examples:

- more frequent review for probationary or rapidly changing AI executors,
- less frequent but still mandatory review for stable human panels,
- immediate review after expansion into new jurisdictions or new skills.

---

## 12. Drift Detection

### 12.1 Drift as a first-class concern
Drift is not an implementation nuisance. It is a governance risk.

### 12.2 Types of drift
The system should detect at least:

- evidence-quality drift,
- citation-discipline drift,
- jurisdiction-fit drift,
- legal-currency drift,
- calibration drift,
- schema-compliance drift,
- adversarial-robustness drift,
- independence drift,
- human process drift.

### 12.3 Drift response
Detected drift may lead to:

- monitoring,
- targeted revalidation,
- rank downgrade,
- scope narrowing,
- mandatory verification,
- suspension.

---

## 13. Scope Revalidation

Revalidation should not only ask whether the executor remains admissible. It should ask whether the current scope remains justified.

Possible outcomes include:

- keep current scope,
- narrow jurisdiction scope,
- narrow domain scope,
- restrict to lower risk,
- remove synthesis authority,
- require co-review,
- expand scope based on strong performance.

Scope is part of trust.

---

## 14. Revalidation Outcomes

Revalidation should produce explicit, auditable outcomes.

Examples:

- `maintain_in_scope`
- `maintain_with_extra_review`
- `narrow_scope`
- `require_shadow_mode`
- `require_full_resandboxing`
- `downgrade_rank`
- `temporary_suspend`
- `revoke_admission`
- `expand_scope`

The system should not leave executor status in an ambiguous informal state.

---

## 15. Suspension and Safe Degradation

### 15.1 Suspension
An executor may be suspended when the system cannot responsibly continue using it while uncertainty is resolved.

### 15.2 Safe degradation
The system should define how to continue operating when an executor is suspended.

Possible responses include:

- reroute to stronger-ranked executor,
- require human review,
- fall back to narrower execution mode,
- delay high-risk cases,
- temporarily remove synthesis privileges.

A resilient system must degrade safely rather than continue blindly.

---

## 16. Re-entry after Suspension or Revocation

An executor that has been suspended or revoked may re-enter only through a defined process.

Re-entry may require:

- root-cause explanation,
- version or workflow change,
- targeted benchmark rerun,
- adversarial retest,
- shadow mode,
- governance approval,
- provisional limited scope.

Re-entry should not occur through informal restoration of trust.

---

## 17. Human Executors and Revalidation

Human experts and panels should also be periodically reviewed.

Relevant revalidation signals may include:

- citation discipline,
- legal/jurisdiction fit,
- consistency across cases,
- appeal outcomes,
- minority-note quality,
- conflict-of-interest compliance,
- timeliness,
- change in institutional ties,
- change in expertise relevance.

The policy must not assume that only AI systems need scrutiny.

---

## 18. Hybrid and Workflow Revalidation

Hybrid workflows should be revalidated both as combined systems and, where appropriate, at component level.

Examples:

- AI retrieval + human synthesis,
- AI draft + human verifier,
- panel review + AI traceability check,
- dual-provider synthesis chain.

Sometimes the failure lies not in a component alone, but in the interaction pattern.

---

## 19. Relationship to Ranking

Revalidation and ranking must interact closely.

Revalidation should feed:

- contextual ranking updates,
- rank confidence changes,
- downgrade signals,
- suspension signals,
- scope adjustments,
- revalidation priority.

Ranking without revalidation becomes stale. Revalidation without ranking becomes memoryless.

---

## 20. Relationship to Sandbox

When revalidation reveals severe uncertainty or major change, the executor may need to re-enter sandbox-style evaluation.

Examples:

- major version change,
- severe regression,
- expansion into a new domain,
- expansion into a new jurisdiction,
- repeated failure under adversarial review.

The sandbox is therefore not only for first admission. It is also a recovery and re-entry mechanism.

---

## 21. Governance Responsibilities

Governance must define:

- who triggers revalidation,
- who reviews the results,
- which signals are authoritative,
- what thresholds cause downgrade or suspension,
- how emergency revalidation is authorized,
- how disputes over revalidation are handled,
- how prospective-only effects are preserved,
- how public transparency is maintained.

A hidden revalidation process can become a capture mechanism.

---

## 22. Prospective-Only Principle

Changes in executor standing should normally apply prospectively.

Active cases should not be silently re-scored or destabilized because a rank or revalidation status changed later.

Exceptions may exist for severe conditions such as:

- discovered disqualifying conflict,
- invalid legal foundation,
- critical safety risk,
- governance-authorized emergency suspension.

Such exceptions must be explicit and auditable.

---

## 23. Public Transparency

The full technical details of revalidation need not always be public, but the civic system should publish enough to support legitimacy.

At minimum, public-facing transparency should preserve:

- whether an executor remains admitted,
- whether it is probationary,
- whether it recently underwent revalidation,
- whether its scope changed,
- whether it was suspended or downgraded,
- whether extra review is required due to revalidation results.

The public should not depend on invisible trust maintenance.

---

## 24. Audit Requirements

The audit trail should preserve at least:

- `revalidation_event_id`
- `executor_id`
- `executor_version`
- `revalidation_type`
- `trigger_type`
- `trigger_reason`
- `review_scope`
- `signals_used`
- `findings`
- `ranking_effects`
- `scope_effects`
- `admission_effects`
- `required_follow_up`
- `decided_at`
- `decided_by`
- `revalidation_due_at`

Revalidation must itself be inspectable.

---

## 25. Failure Modes

Common failure modes of revalidation include:

- treating admission as permanent,
- reviewing only after obvious disaster,
- ignoring human workflow drift,
- hiding version changes behind the same executor identity,
- allowing high historical rank to mask current deterioration,
- using vague downgrade logic,
- revalidating too rarely for fast-changing systems,
- destabilizing active cases without explicit emergency rule,
- opaque restoration after suspension,
- capture of the revalidation authority itself.

The system should explicitly defend against these patterns.

---

## 26. Minimum Canonical Fields

A revalidation record should minimally support fields such as:

- `revalidation_event_id`
- `executor_id`
- `executor_version`
- `executor_class`
- `revalidation_type`
- `trigger_type`
- `trigger_reason`
- `skill_scope`
- `domain_scope`
- `jurisdiction_scope`
- `proposal_risk_scope`
- `signals_used`
- `signal_summaries`
- `detected_drifts[]`
- `rank_before`
- `rank_after`
- `admission_status_before`
- `admission_status_after`
- `scope_before`
- `scope_after`
- `required_review_mode_after`
- `revalidation_outcome`
- `follow_up_actions[]`
- `decided_at`
- `decided_by`
- `next_revalidation_due_at`
- `notes`

Detailed field typing belongs in `SCHEMAS.md`.

---

## 27. Canonical Operating Rules

1. Admission is conditional and continuously reviewable.  
2. Revalidation applies to all executor classes where civic authority matters.  
3. Revalidation is contextual, not purely global.  
4. Version or workflow changes may break prior trust assumptions.  
5. Drift detection is a governance requirement, not an optional optimization.  
6. Revalidation must be able to narrow scope, not only revoke or preserve it.  
7. Suspension must support safe degradation of the civic process.  
8. Re-entry after suspension must be formal and auditable.  
9. Revalidation and ranking must inform each other.  
10. Trust maintenance must not remain invisible to the public.

---

## 28. Closing Principle

> Civic authority should not be granted once and then forgotten.  
> It should remain limited, observed, challengeable, and renewable only under continued evidence of responsible performance. 
