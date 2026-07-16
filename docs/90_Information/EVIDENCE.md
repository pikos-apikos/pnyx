# EVIDENCE

## 1. Purpose

This document defines how evidence is represented, classified, challenged, and carried through the civic reasoning loop.

Its purpose is:
- to prevent unsupported claims from acquiring procedural legitimacy,
- to distinguish evidence from interpretation,
- to preserve uncertainty where certainty is not warranted,
- to ensure that disagreement over facts is visible rather than silently flattened,
- to make evidence handling auditable across proposal, governance, and constitutional layers.

Evidence handling is not an optional helper function.
It is a core legitimacy function.

---

## 2. Core Principle

The system must not present uncertain claims as settled facts.

Every material claim entering the civic process must be handled as one of the following:
- supported,
- disputed,
- insufficiently evidenced,
- unknown,
- not evidentiary in nature.

A proposal may proceed under uncertainty,
but it may not hide uncertainty.

---

## 3. Scope

This document applies to:
- proposals,
- framework changes,
- routing decisions,
- public briefings,
- audit records,
- challenges and reruns,
- emergency justifications,
- post-decision reviews.

It applies to both human-submitted and machine-produced material.

---

## 4. Definitions

### 4.1 Evidence
Evidence is any material offered in support of a factual, causal, predictive, legal, operational, or evaluative claim.

### 4.2 Claim
A claim is any statement that can affect deliberation, routing, voting, implementation, legitimacy, or review.

### 4.3 Interpretation
Interpretation is a structured reading of evidence.
Interpretation is not itself evidence,
though it may be supported or undermined by evidence.

### 4.4 Assertion
An assertion is a claim presented without adequate evidentiary support.
Assertions may enter the system,
but must not be upgraded to factual status without review.

### 4.5 Unknown
Unknown means the system does not currently possess sufficient grounds to classify a claim as supported or refuted.

### 4.6 Insufficient Evidence
Insufficient evidence means relevant support has been attempted or referenced,
but does not reach the minimum standard required for the claim's importance.

### 4.7 Disputed Evidence
Disputed evidence means credible sources or analyses materially conflict,
and the conflict is not yet resolved to a degree sufficient for singular presentation.

### 4.8 Terminology guard: EvidenceItem vs EvidencePacket
These are different objects and must not be confused:
- an **EvidenceItem** is a primary piece of evidence (this document; `DATA_MODEL.md` §5.13; `SCHEMAS.md` §12.1),
- an **EvidencePacket** is the structured *analytic output of a skill run* (`SCHEMAS.md` §9.5; `PROTOCOL.md` Stage G) — it references evidence, it is not itself evidence.

Any future document that uses "evidence packet" to mean primary evidence is in error.

---

## 5. Evidence Classes

This section is the single canonical definition of the evidence-class taxonomy.
`SCHEMAS.md` and `DATA_MODEL.md` reference this list and do not maintain their own copies.

Evidence must be tagged by class.
A single claim may rely on multiple evidence classes.

Canonical primary classes and their subtypes:

| Canonical class | Subtypes |
|---|---|
| `legal_text` | constitutional text, statute, regulation |
| `policy_text` | adopted policy, active framework text |
| `institutional_record` | public administrative data, budgetary / financial record, procurement record, operational log |
| `empirical_structured` | technical measurement, dataset, empirical study |
| `empirical_unstructured` | local observational input, photograph, audio / video record |
| `testimony` | direct testimony, local testimony, public consultation input |
| `expert_judgment` | expert analysis, professional certification |
| `historical_record` | historical precedent, prior case outcome |
| `forecast_simulation` | forecast, simulation, scenario model |
| `model_generated_non_self_validating` | model inference, model synthesis |

An evidence record should carry the canonical class and may carry a subtype for precision.

No evidence class is universally supreme.
Relevance depends on claim type.

This taxonomy is one of four distinct dimensions that must not be conflated:
- **evidence class** (origin — this section),
- **claim type** (section 6),
- **evidence status** (section 8),
- **confidence level** (section 9).

---

## 6. Claim Types

Claims should also be tagged by type.

Primary claim types include:
- factual claim,
- causal claim,
- predictive claim,
- legal claim,
- feasibility claim,
- cost claim,
- rights-impact claim,
- dependency claim,
- capture-risk claim,
- reversibility claim,
- precedent claim,
- value judgment.

Evidence adequacy must be assessed relative to claim type,
not by raw volume alone.

---

## 7. Evidence Record

Each material claim should carry an evidence record containing at minimum:
- claim identifier,
- claim text,
- claim type,
- evidence class,
- source or origin,
- date or effective period where relevant,
- confidence level,
- status,
- interpretation note,
- contradiction note where applicable,
- reviewer or reviewing skill,
- audit reference.

No important claim should be left floating without traceable evidentiary status.

---

## 8. Evidence Statuses

Every material claim must be assigned one primary status:
- supported,
- partially supported,
- disputed,
- insufficient evidence,
- unknown,
- inapplicable as evidence,
- superseded.

Status changes must be auditable.

---

## 9. Confidence Levels

Confidence must be represented explicitly,
not implied rhetorically.

Suggested confidence scale:
- very low,
- low,
- moderate,
- high,
- very high.

Confidence is not truth.
It is a structured estimate of evidentiary strength relative to claim importance and available review.

---

## 10. Minimum Rules

### 10.1 No Silent Upgrade
A claim may not move from assertion to supported status without recorded evidentiary review.

### 10.2 No Forced Certainty
A skill, panel, or packet may not collapse unknown or disputed claims into singular fact language for convenience.

### 10.3 No Evidence Laundering Through Repetition
Repeated model output, repeated public assertion, or repeated briefing language does not itself increase evidentiary weight.

### 10.4 No Single-Source Elevation for Material Claims
Material claims in non-trivial proposals should not rely on a single unchallenged source where plural review is reasonably possible.

### 10.5 No Hidden Evidence Hierarchy
The system must not silently treat one source class as dominant across all claim types.

---

## 11. Materiality Threshold

Not every sentence in a proposal requires full evidence handling.
Evidence discipline applies to material claims.

A claim is material when it can affect:
- proposal classification,
- panel selection,
- routing,
- thresholds,
- civic judgment,
- execution design,
- legitimacy,
- rights analysis,
- emergency invocation,
- post-decision review.

Non-material background statements may be summarized more lightly,
but should not be used to justify a decision path without upgrade into material review.

---

## 12. Evidence Requirements by Layer

### 12.1 Policy Layer
Evidence must be sufficient to support informed public judgment and execution routing.

### 12.2 Governance Layer
Evidence must additionally support claims about procedural effect, institutional incentives, and systemic consequences.

### 12.3 Constitutional Layer
Evidence must be strongest where claims touch rights, legitimacy, anti-capture protections, and long-horizon institutional design.

The deeper the layer,
the stronger the evidentiary discipline required.

---

## 13. Treatment of Unknowns

Unknown is a valid outcome.
The system must preserve it explicitly.

Unknown should be used when:
- evidence is missing,
- evidence is immature,
- relevant conflict remains unresolved,
- predictive confidence is weak,
- source quality is materially uncertain,
- the proposal depends on assumptions not yet testable.

Unknown is preferable to false precision.

---

## 14. Insufficient Evidence Handling

When evidence is insufficient,
the system should do one or more of the following:
- mark the claim accordingly,
- reduce confidence,
- escalate to additional review,
- narrow the proposal scope,
- convert the proposal into a reversible experiment,
- prohibit use of the claim as decisive justification,
- defer the proposal if the unsupported claim is load-bearing.

Insufficient evidence does not automatically kill a proposal,
but it constrains what the proposal may legitimately claim.

---

## 15. Contradiction Handling

When evidence materially conflicts,
the system must not hide the contradiction.

It should record:
- what conflicts,
- which sources or classes are involved,
- whether the conflict concerns facts, interpretation, or prediction,
- whether the conflict is likely resolvable,
- how the contradiction affects confidence,
- whether escalation or adversarial review is required.

Public packets should expose important contradictions in plain language.

---

## 16. Predictive Claims

Predictive claims require special discipline.

They should include where possible:
- prediction horizon,
- assumptions,
- uncertainty band,
- reversibility implication,
- failure consequence,
- whether the claim is model-derived, expert-derived, or historically inferred.

Predictions must not be presented as retrospective facts.

---

## 17. Model-Produced Content

Model output is not self-validating evidence.

It may function as:
- synthesis,
- interpretation,
- hypothesis generation,
- contradiction detection,
- draft briefing material,
- scenario exploration.

It must not automatically count as primary support for material factual claims.

Any material claim generated by a model must be classified independently through the evidence process.

---

## 18. Public Input as Evidence

Public consultation input may constitute evidence of:
- lived experience,
- local conditions,
- perceived harms,
- implementation friction,
- distributional effects,
- acceptability concerns.

It does not automatically establish:
- universal factual truth,
- legal interpretation,
- technical feasibility,
- aggregate impact.

Public input should be neither romanticized nor dismissed.
It is a valid evidence class with bounded scope.

---

## 19. Rights and Constitutional Claims

Claims involving rights, equal standing, exclusion, coercion, surveillance, dependency, or anti-capture safeguards require explicit evidentiary handling.

Such claims should not be buried under generic feasibility or efficiency language.

Where rights-impact claims remain disputed,
the packet must say so directly.

---

## 20. Evidence and Routing

Routing toward market, state, or hybrid execution must include evidentiary support for the routing rationale.

Routing claims should address where relevant:
- decentralization feasibility,
- universality requirement,
- exclusion risk,
- concentration risk,
- dependency risk,
- enforcement requirement,
- reversibility,
- administrative burden.

No routing decision should rest on ideology alone.

---

## 21. Evidence and Audit

The audit trail must record at minimum:
- material claims used,
- evidence status at decision time,
- confidence assignments,
- unresolved disputes,
- unknowns preserved,
- claims excluded from decisive use due to insufficient support,
- later corrections or superseding evidence.

Evidence review is incomplete if it cannot be reconstructed after the fact.

---

## 22. Corrections and Supersession

Evidence records may be corrected,
but never silently rewritten.

When evidence changes,
the system must append:
- what changed,
- why it changed,
- who or what triggered review,
- whether the earlier decision was materially affected,
- whether rerun, challenge, or post-decision review is required.

Supersession does not erase the historical record.

---

## 23. Bootstrap Rules

During bootstrap,
evidence handling should be stricter rather than looser.

Bootstrap rules should include:
- explicit evidence classes,
- explicit unknown state,
- no silent source promotion,
- mandatory contradiction notes for important claims,
- mandatory confidence tagging for routing and rights-impact claims,
- no ad hoc lowering of evidence requirements for an active case.

Evidence parameters may later be revised,
but only prospectively through the civic loop.

---

## 24. Failure Conditions

Evidence handling is considered failed when:
- a material claim is treated as settled without recorded support,
- disputed claims are presented as singular facts,
- model output is laundered into factual authority,
- unknowns are removed for rhetorical neatness,
- critical evidence is hidden behind summary language,
- routing relies on unsupported assumptions,
- later corrections cannot be traced.

Such failures should be challengeable and auditable.

---

## 25. Design Principle

The system should prefer explicit uncertainty over illegible confidence.

Its duty is not to simulate certainty.
Its duty is to preserve enough evidentiary honesty for legitimate public judgment.
