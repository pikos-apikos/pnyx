# SYSTEM_PATCH_v1

**Status:** Normative patch  
**Scope:** System-wide  
**Applies to:** Protocol, architecture, governance, skill/executor layer, evaluation, inclusion  
**Supersedes assumptions:** Any prior reading that treats AI models as inherently trusted civic reasoners or as the sole valid executors of civic analysis

---

## 1. Purpose

This patch updates the foundational logic of the system from an **AI-centric deliberation model** to a **protocol-governed, evidence-backed, executor-agnostic civic system**.

The goal is not to remove AI from the architecture. The goal is to ensure that:

- civic legitimacy does not depend on unconditional trust in models,
- analytic outputs are grounded in public evidence,
- human experts can participate as first-class executors,
- confidence is derived from evidence quality rather than self-reported certainty,
- executor participation is earned through admission, ranking, and continuous revalidation.

This patch should be read as a **normative override** wherever earlier documents can be interpreted as giving models privileged epistemic authority by default.

---

## 2. Core Shift

### Previous implicit tendency
The system could be read as if civic analysis is primarily performed by AI skills, with legitimacy flowing from orchestration, plurality, and structured disagreement among models.

### New canonical reading
The system is legitimate only when analysis is:

- procedurally bounded,
- evidence-backed,
- source-cited,
- challengeable,
- auditable,
- executable by different classes of qualified actors.

In other words:

> Pnyx is not AI-governed.  
> Pnyx is protocol-governed, evidence-backed, and executor-agnostic.

---

## 3. New System Principles

### 3.1 No analysis without evidence
No skill output is valid merely because an AI system produced it.

All non-trivial analysis must be grounded in a structured evidence packet containing relevant public sources, legal context, jurisdictional scope, and explicit references for material claims.

### 3.2 No recommendation without traceability
Recommendations must be traceable to cited reasons, cited sources, declared unknowns, and explicit constraints.

Any recommendation lacking sufficient claim-to-source traceability is procedurally weak and may require escalation or rejection.

### 3.3 Skill is a role, not a model
A skill is a **mandated analytic function** within the civic process.
It is not identical to a specific model, provider, or agent implementation.

A skill may be executed by:

- an AI model,
- a human expert,
- a human panel,
- a hybrid executor,
- a replicated multi-executor configuration.

### 3.4 Executor legitimacy is earned, not presumed
Executors do not receive trust merely by identity, vendor, or prior inclusion.
They earn constrained trust through:

- benchmarked performance,
- evidence discipline,
- calibration quality,
- challengeability,
- schema compliance,
- continuous revalidation.

### 3.5 Human sovereignty includes epistemic fallback
Human sovereignty is not limited to the final vote.
It also includes the right of the system to route analysis toward human experts when machine outputs are weak, conflicted, under-evidenced, or high-risk.

### 3.6 Structured disagreement remains mandatory
The system must continue to preserve minority views, unresolved conflicts, adversarial critique, and anti-capture analysis.
Evidence-backed outputs do not eliminate disagreement; they make disagreement auditable.

---

## 4. Canonical Concepts Introduced by This Patch

### 4.1 Skill
A protocol-defined analytic role with a clear mandate.

Examples:

- Rights / Constitutional Review
- Economic / Resource Review
- Implementation / Feasibility Review
- Anti-Capture / Audit Review
- Adversarial Critique

### 4.2 Executor
The actor or system that performs a skill.

Examples:

- AI model version
- human domain expert
- expert panel
- AI + human reviewer pair
- independent replicated reviewers

### 4.3 Evidence Packet
The mandatory structured output that grounds a skill result.

An Evidence Packet must contain at minimum:

- mandate
- proposal identifier
- jurisdiction / geographic scope
- applicable legal and regulatory context
- cited public documents and sources
- claim-to-source traceability
- assumptions
- unknowns
- contested points
- recommendation
- evidence-based confidence score

### 4.4 Executor Registry
A registry of admissible executors, versions, domains, and performance history.

### 4.5 Contextual Ranking
A ranking system that evaluates executor performance not in the abstract, but in context:

- by skill,
- by domain,
- by jurisdiction,
- by task type,
- by version.

### 4.6 Inclusion Sandbox
A dry-run environment where candidate executors are tested on full civic cases before being allowed into live protocol paths.

---

## 5. Protocol-Level Changes

### 5.1 Submission and intake remain unchanged in purpose
The system still transforms raw civic demand into bounded processable proposals.
However, downstream legitimacy no longer depends on model plurality alone. It depends on evidence-backed execution and auditable review.

### 5.2 Classification now influences review routing more strongly
Classification must continue to determine governance layer, urgency, triviality, and thresholds.
In addition, classification must now influence:

- required executor type,
- minimum evidence burden,
- whether human review is mandatory,
- whether independent replication is required,
- whether sandbox-certified executors only may participate.

### 5.3 Panel specification must include executors, not only skills
The panel lock must specify not only which skills are required, but also:

- executor type per skill,
- eligibility criteria,
- conflict-of-interest disclosure requirements,
- independence and replication requirements,
- fallback routing rules,
- review escalation conditions,
- admissible evidence classes.

The system must be able to express configurations such as:

- Rights Review -> human legal panel
- Feasibility Review -> AI executor + human verifier
- Economic Review -> AI executor from approved registry
- Adversarial Critique -> independent executor not sharing provider lineage with the others

### 5.4 Skill execution now means Evidence Packet production
A skill is not complete when it produces a summary or opinion.
A skill is complete only when it returns a valid Evidence Packet under the required schema.

### 5.5 Synthesis must preserve conflict and expose evidence structure
The synthesis layer must not smooth disagreements into a false consensus.
It must preserve:

- strongest case in favor,
- strongest case against,
- minority views,
- unresolved factual disputes,
- unresolved legal ambiguities,
- capture-risk notes,
- confidence disparities across packets,
- evidence gaps and source conflicts.

### 5.6 Public briefing must include source visibility
The public briefing packet must expose the source basis of the analysis in a way that supports inspection, challenge, and procedural critique.

The public must be able to ask at least:

- Which sources support this claim?
- Is the jurisdiction correct?
- Is the cited law current?
- Was contrary evidence omitted?
- Did the executor overstate confidence?

### 5.7 Decision readiness now includes evidentiary sufficiency
A proposal is not ready merely because the deliberation clock closed.
Readiness must include procedural sufficiency such as:

- valid panel completion,
- threshold and quorum rules,
- minimum evidence standards met,
- mandatory review completed,
- unresolved blockers appropriately classified,
- challenges handled or time-bounded according to protocol.

### 5.8 Post-decision review becomes evidence-aware
Re-entry into review may be triggered not only by execution failure or constitutional challenge, but also by:

- source invalidation,
- legal change,
- evidence omission,
- executor misconduct,
- severe confidence miscalibration,
- later-discovered jurisdiction mismatch.

---

## 6. Evidence and Sourcing Requirements

### 6.1 Public source grounding
For non-trivial proposals, executors must use relevant public documents where applicable, including:

- laws and regulations,
- administrative decisions,
- court decisions,
- public budgets,
- official datasets,
- public policy reports,
- geographic and jurisdictional records,
- relevant public standards or institutional guidance.

### 6.2 Jurisdiction sensitivity
The same proposal may yield different valid analyses depending on locality, region, nation, or supranational legal layer.

The executor must explicitly state:

- which jurisdiction is assumed,
- why that jurisdiction applies,
- whether overlapping jurisdictions exist,
- whether uncertainty about jurisdiction affects the recommendation.

### 6.3 Citation discipline
Material claims should be source-traceable.
The system should distinguish between:

- cited fact,
- cited interpretation,
- inference from multiple sources,
- assumption,
- unresolved uncertainty.

### 6.4 Missing evidence handling
When evidence is weak, missing, contradictory, or outdated, the system must not simulate certainty.
Instead it must:

- declare the gap,
- reduce derived confidence,
- escalate when required,
- preserve the issue in the briefing and audit record.

---

## 7. Confidence and Scoring Reform

### 7.1 Confidence is not self-reported certainty
Confidence must not be treated as a free-text self-assessment by the executor.

Confidence must be derived, wholly or primarily, from protocol-defined scoring components.

### 7.2 Evidence-based scoring dimensions
The system should compute confidence using dimensions such as:

- evidence coverage score,
- source quality score,
- jurisdiction relevance score,
- legal currency / freshness score,
- claim traceability score,
- contradiction penalty,
- unknowns disclosure quality,
- schema completeness,
- replication agreement or disagreement signal where applicable.

### 7.3 Confidence as a routing trigger
Confidence is not decorative metadata.
It must affect routing.

Examples:

- high confidence + low conflict -> standard path
- medium confidence -> additional review
- low confidence -> mandatory human review
- high rights impact or constitutional risk -> mandatory human review regardless of score
- source conflict + high claimed certainty -> audit flag

### 7.4 Calibration matters more than bravado
Executors should be evaluated partly on calibration quality:

- when they are uncertain, do they say so?
- when evidence is strong, do they identify why?
- when later checked, was their confidence proportionate?

---

## 8. Human Experts as First-Class Executors

### 8.1 Equal protocol status
Human experts are not an informal fallback outside the system.
They are formal executors within the system.

### 8.2 Common schema requirement
Human expert outputs must follow the same or a compatible schema as machine-generated outputs, including:

- source citations,
- reasoning summary,
- declared unknowns,
- recommendation,
- confidence components where applicable,
- conflict of interest disclosure.

### 8.3 Mandatory human routing cases
The protocol should define classes of cases where human review is mandatory, such as:

- constitutional interpretation,
- severe rights impact,
- emergency justification review,
- low-confidence machine outputs,
- high public contestation,
- novel legal domains,
- suspected manipulation or capture.

### 8.4 Human experts do not bypass audit
Human participation increases legitimacy but does not exempt outputs from sourcing, auditing, or procedural challenge.

---

## 9. Ranking and Registry Reform

### 9.1 No static trust in models
Because models evolve, drift, and change versions, no executor should be treated as permanently reliable.

### 9.2 Contextual ranking
Ranking must be contextual.
The system should track executor performance by:

- executor identity,
- version,
- skill,
- proposal type,
- jurisdiction,
- domain,
- challenge rate,
- reversal rate,
- evidence quality behavior,
- calibration behavior.

### 9.3 Ranking is advisory but consequential
Ranking should inform:

- panel selection,
- whether dual review is needed,
- whether an executor is limited to low-risk tasks,
- whether an executor is suspended or downgraded.

### 9.4 Version changes require revalidation
A new major executor version should not inherit full production trust automatically.
Version changes should trigger partial or full revalidation depending on risk profile.

---

## 10. Inclusion Sandbox and Admission Policy

### 10.1 Admission before production use
New executors must not enter the live civic path without prior evaluation.

### 10.2 Sandbox scope
The inclusion sandbox should test executors on full civic pathways, not only isolated prompts.

The sandbox should include:

- intake cases,
- classification cases,
- rights-sensitive cases,
- budget/resource cases,
- implementation cases,
- anti-capture and adversarial cases,
- emergency and non-emergency cases,
- multi-jurisdiction cases,
- trivial and non-trivial proposals,
- realistic full proposal packets.

### 10.3 Evaluation dimensions
Admission tests should measure at least:

- evidence completeness,
- citation discipline,
- hallucination rate,
- legal/jurisdiction mismatch rate,
- adversarial robustness,
- minority-view preservation,
- calibration quality,
- schema compliance,
- stability across reruns,
- synthesis usefulness.

### 10.4 Progressive admission
Admission should be staged:

- sandbox only,
- shadow mode,
- limited production scope,
- expanded production eligibility,
- continuous revalidation.

### 10.5 Suspension and downgrade
Executors may be downgraded, scoped down, or suspended if they show:

- repeated hallucination,
- poor calibration,
- evidence omission,
- systematic jurisdictional error,
- unstable behavior after version change,
- failure under adversarial review.

---

## 11. Governance Consequences

### 11.1 Governance must oversee executors, not only proposals
Governance must include rules for:

- who may admit executors,
- who may challenge their inclusion,
- who audits rankings,
- how benchmark suites evolve,
- how human experts are selected,
- how conflicts of interest are disclosed,
- how version changes are handled.

### 11.2 Capture resistance extends to the executor layer
A civic system can be captured not only through proposals but through the analytic layer itself.
Therefore anti-capture safeguards must apply to:

- vendor concentration,
- homogeneous model families,
- opaque expert selection,
- hidden conflicts of interest,
- synthetic consensus generation,
- politically or economically aligned review clusters.

### 11.3 Appeals and challenges must reach the analytic basis
Users and citizens must be able to challenge not only decisions, but also the analytic basis of those decisions, including:

- missing evidence,
- wrong jurisdiction,
- low-quality sources,
- non-independent reviewers,
- overconfident synthesis,
- omitted minority reasoning.

---

## 12. Architectural Consequences

This patch implies that the architecture should explicitly support:

- executor abstraction,
- evidence retrieval and citation pipelines,
- schema-validated Evidence Packets,
- confidence scoring engines,
- contextual ranking storage,
- sandbox evaluation flows,
- human executor workflows,
- review escalation routing,
- audit trail preservation at claim/source level.

Any architecture that still assumes "skill = model call" should be considered incomplete under this patch.

---

## 13. What This Patch Does Not Change

This patch does **not** abolish:

- the Civic Loop,
- structured deliberation,
- public briefing,
- adversarial synthesis,
- auditability,
- emergency handling,
- prospective-only governance changes,
- revisability under protocol.

Instead, it strengthens them by making the analytic substrate more inspectable, more plural, and less trust-fragile.

---

## 14. Migration Guidance

This patch should be propagated into the system through targeted updates to existing documents and the creation of new normative files.

### 14.1 Existing files that should be patched
At minimum:

- \`PROTOCOL.md\`
- \`ARCHITECTURE.md\`
- \`GOVERNANCE.md\`
- \`AI_EPISTEMIC_RISK.md\`
- \`SCHEMAS.md\`
- \`TASK_SUITE_v0.md\`
- any file that currently treats skills as model-bound agents

### 14.2 New files introduced or required
At minimum:

- \`EXECUTOR_MODEL.md\`
- \`EVIDENCE_PACKET.md\`
- \`CONFIDENCE_AND_SCORING.md\`
- \`EXECUTOR_RANKING.md\`
- \`MODEL_INCLUSION_SANDBOX.md\`
- \`REVALIDATION_POLICY.md\`
- \`CITATION_AND_SOURCING_POLICY.md\`
- \`HUMAN_EXPERT_PROTOCOL.md\`

---

## 15. Canonical Summary

This patch establishes the following canonical system reading:

1. Civic analysis is not inherently legitimate because AI produced it.
2. Skills are protocol roles, not model identities.
3. Executors may be AI, human, or hybrid.
4. Non-trivial outputs must be evidence-backed and source-cited.
5. Confidence must be derived from evidence quality and process signals.
6. Executor trust must be conditional, ranked, and continuously revalidated.
7. New executors must pass through a sandboxed admission path.
8. Human review is a formal part of the system, not an external patch.
9. Structured disagreement remains essential.
10. The system remains civic, auditable, and procedurally bounded only if its analytic layer is itself governed.

---

## 16. Closing Principle

> No analysis without evidence.  
> No recommendation without citations.  
> No executor trusted by identity alone.  
> No civic legitimacy without challengeable, auditable reasoning.
