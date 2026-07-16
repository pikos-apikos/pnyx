# CLASSIFICATION

## 1. Purpose

This document defines how proposals are classified before panel assembly, deliberation, routing, and decision.

Classification is a load-bearing protocol act.
It determines:
- which governance layer applies,
- whether a proposal is trivial or non-trivial,
- whether framework-change safeguards apply,
- whether emergency handling is even permissible,
- whether escalation above the minimum panel is required,
- which review windows, thresholds, and audit obligations bind the case.

The purpose of this document is to prevent classification from becoming a hidden sovereignty layer.

---

## 2. Core Principle

Classification may shape the process.
It may not secretly decide the outcome.

No single classifier, operator, prompt, vendor, or convenience heuristic may unilaterally determine the effective legitimacy path of a proposal.

Classification must therefore be:
- explicit,
- structured,
- challengeable,
- auditable,
- bounded by fixed rules,
- resistant to downgrade abuse,
- resistant to silent reclassification,
- subject to adversarial and external checks where required.

---

## 3. Classification Objects

The classification layer produces or consumes the following first-class objects:
- `ClassificationInput`
- `ClassificationResult`
- `ClassificationRationale`
- `ClassificationEvidence`
- `SpilloverAssessment`
- `EscalationAssessment`
- `ClassificationChallenge`
- `ClassificationReview`
- `ClassificationEpochBinding`

These are protocol objects.
Their storage forms are defined in `DATA_MODEL.md` and their runtime transitions in `STATE_MACHINE.md`.

---

## 4. What Classification Must Decide

Every submitted proposal must produce a classification result that answers, at minimum:

1. Is the proposal `trivial` or `non_trivial`?
2. What is the primary governance layer?
   - `policy`
   - `governance`
   - `constitutional`
3. Does the proposal imply `framework_change`?
4. Is it `advisory_only` or potentially binding?
5. Is emergency handling `ineligible`, `eligible`, or `required_for_integrity_only`?
6. What is the minimum panel size?
   - `0`
   - `5`
   - `7`
   - `9`
7. Are constitutional spillover risks present?
8. Are routing consequences material enough to require elevated review?
9. Does the proposal exceed bootstrap scope?
10. Is additional review required before panel lock?

Classification must not remain implicit.

---

## 5. Classification Principles

### 5.1 Safety-Biased Ambiguity Handling
If a proposal is meaningfully ambiguous between two stronger paths,
it must be classified into the stronger path or escalated for review.

Ambiguity must not default downward.

### 5.2 No Silent Downgrade
A proposal may not be silently downgraded from:
- constitutional to governance,
- governance to policy,
- non-trivial to trivial,
- framework-changing to ordinary,
- contestable routing to routine routing.

Any downgrade requires explicit rationale and is auto-challengeable.

### 5.3 Spillover Matters
Classification must evaluate not only direct wording,
but foreseeable spillover into:
- rights,
- membership,
- thresholds,
- routing,
- minority protections,
- anti-capture constraints,
- operator discretion,
- future precedents.

### 5.4 Classification Is Prospective
The classification result binds the case unless challenged, invalidated, or re-reviewed through protocol.
It may not be casually altered mid-case.

### 5.5 Classification Is Not Aesthetic Interpretation
Rewording, formatting, technical framing, or euphemistic language must not reduce the effective scrutiny of a proposal.

---

## 6. Classification Inputs

### 6.1 Mandatory Impact Assessments
Before any classification can occur, a proposal must undergo a Mandatory Impact Assessment if it meets baseline triggers for:
- affected population size,
- resource commitment or treasury impact,
- cross-domain dependencies.

This assessment must be completed and attached to the proposal before the classifier evaluates it. Classification without a required Impact Assessment is invalid.

### 6.2 Structured Inputs
Classification must use structured inputs.
At minimum:
- proposal title,
- proposal body,
- claimed purpose,
- requested change,
- affected domains,
- affected populations,
- claimed urgency,
- claimed layer by proposer,
- resource implications if known,
- reversibility note if known,
- prior linked proposals or precedents if known,
- completed Impact Assessment (if required).

The classifier may also use:
- applicable framework epoch,
- bootstrap parameter set,
- known scope exclusions,
- challenge history,
- duplicate cluster context,
- prior decisions with materially similar structure.

Classification may not rely on hidden, undeclared context.

---

## 7. Trivial vs Non-Trivial

### 7.1 Trivial Proposal
A proposal is trivial only when it is conclusively limited to:
- clerical correction,
- formatting-only change,
- wording-only cleanup without semantic effect,
- internal technical maintenance without civic, governance, routing, rights, or constitutional consequence.

### 7.2 Non-Trivial Proposal
A proposal is non-trivial if at least one of the following applies:
- it changes a rule, right, obligation, or access condition,
- it commits public resources or changes allocation priorities,
- it materially affects execution routing,
- it introduces non-negligible capture, exclusion, or dependency risk,
- it affects more than one domain, group, or community,
- it is difficult or costly to reverse,
- it contains significant uncertainty or conflicting trade-offs,
- it operates at governance or constitutional layer,
- it creates precedent likely to shape future decisions.

### 7.3 Burden of Triviality
The burden is on the classifier to justify triviality.

Triviality must be demonstrated,
not presumed.

---

## 8. Governance Layer Classification

Every non-trivial proposal must be assigned exactly one primary layer.

### 8.1 Policy Layer
Use `policy` only when the proposal:
- does not materially alter framework rules,
- does not change constitutional guarantees,
- does not change thresholds, timings, permissions, or classification rules,
- does not restructure who counts or who is protected,
- remains within ordinary execution scope.

### 8.2 Governance Layer
Use `governance` when the proposal changes:
- procedures,
- routing criteria,
- panel rules,
- evidence handling,
- review windows,
- thresholds,
- permissions,
- protocol behavior,
- execution controls,
- audit visibility or review structure.

### 8.3 Constitutional Layer
Use `constitutional` when the proposal changes or materially risks changing:
- rights guarantees,
- equality of civic standing,
- anti-capture rails,
- membership boundaries,
- legitimacy conditions,
- sovereignty principles,
- foundational value priorities,
- emergency scope,
- meta-governance ceilings,
- the ability of the system to revise itself.

### 8.4 Strongest-Layer Rule
If a proposal plausibly spans multiple layers,
it must be classified at the strongest applicable layer unless a formal split is created.

---

## 9. Additional Classification Flags

Every classification result must also evaluate these flags.

### 9.1 `framework_change`
True if the proposal changes any framework object,
including:
- governance documents,
- bootstrap parameter classes,
- epoch-bound legitimacy parameters,
- skill class requirements,
- routing categories,
- emergency constraints,
- meta-governance rules.

### 9.2 `advisory_only`
True only when the proposal is explicitly non-binding and cannot be interpreted as binding by downstream execution.

### 9.3 `constitutional_spillover`
True if the proposal does not directly claim constitutional change but likely affects rights, civic standing, anti-capture resilience, or legitimacy rules.

### 9.4 `routing_materiality`
True if route choice itself could materially alter public outcomes, dependencies, or concentration risk.

### 9.5 `bootstrap_scope_violation`
True if the proposal exceeds the currently allowed bootstrap domain.

### 9.6 `emergency_eligible`
True only if the proposal concerns system-integrity preservation or an already-defined emergency class.
No policy convenience claim is sufficient.

---

## 10. Escalation Levels

Classification must set the minimum deliberative level.

### 10.1 `0`
Only valid for conclusively trivial proposals.
No panel.
Still logged and challengeable.

### 10.2 `5`
Default minimum for non-trivial proposals.

### 10.3 `7`
Required when one or more of the following applies:
- cross-domain impact,
- high uncertainty,
- meaningful routing controversy,
- significant resource implications,
- visible public contestation,
- constitutional spillover,
- elevated capture risk.

### 10.4 `9`
Required when one or more of the following applies:
- explicit constitutional-layer change,
- major framework revision,
- bootstrap parameter reclassification,
- emergency normalization risk,
- high-impact membership or legitimacy change,
- irreversible or near-irreversible systemic effect.

### 10.5 Fixed Bootstrap Escalation
During bootstrap,
escalation rules must be fixed ex ante and may not be case-tuned.

### 10.6 Structural Non-Discretionary Rules
To prevent the 'Classification Bottleneck' vulnerability where sophisticated actors game the classification layer, certain proposal types completely remove discretion from the classifier.

The following triggers automatically force escalation and strict classification, regardless of the classifier's assessment:
- Any proposal touching the treasury over a defined threshold ($X) automatically escalates to level 7 or higher.
- Any proposal altering core constitutional rights or framework parameters automatically escalates to level 9.
- Any proposal affecting more than a defined percentage of the user base automatically requires a Mandatory Impact Assessment and escalates to level 7.

These structural rules bypass classifier discretion and bind the case deterministically.

---

## 11. Multi-Source Classification

### 11.1 No Single-Source Legitimacy
No non-trivial proposal may rely on a single hidden classifier output.

### 11.2 Required Components
For non-trivial proposals,
a valid classification must include:
- a structured primary classification,
- a rationale object,
- a spillover assessment,
- a deterministic rule check,
- either adversarial counter-classification or random independent review.

### 11.3 Counter-Classification
A counter-classification is an independently generated classification pass whose role is to detect:
- unjustified downgrades,
- omitted flags,
- hidden constitutional spillover,
- under-escalation,
- triviality laundering.

### 11.4 External or Randomized Check
For governance, constitutional, framework-changing, or high-dispute proposals,
the system should require one of:
- external review by an independent review pool,
- randomized secondary classification,
- public pre-panel classification challenge window.

The aim is not external sovereignty.
The aim is exogenous friction against internal convenience capture.

---

## 12. Classification Workflow

### 12.1 Intake Preparation
Proposal enters classification only after intake completeness checks.

### 12.2 Primary Classification Pass
The classifier produces:
- trivial/non-trivial result,
- primary layer,
- flags,
- escalation level,
- rationale,
- confidence,
- unresolved ambiguities.

### 12.3 Deterministic Rule Validation
A rules layer checks whether the result violates fixed constraints,
including:
- impossible triviality,
- impossible policy classification,
- forbidden emergency eligibility,
- missing spillover assessment,
- invalid bootstrap scope handling.

### 12.4 Counter-Classification or Independent Review
If required by class or rule,
a second pass must evaluate the same proposal independently.

### 12.5 Disagreement Resolution
If classification passes disagree materially,
the case must not silently choose the weaker result.
It must instead:
- escalate,
- enter classification review,
- or adopt the stronger classification path provisionally.

### 12.6 Result Lock
Once validated,
the classification result is bound to the proposal revision,
framework epoch,
and parameter epoch.

No silent reclassification is permitted after lock.

---

## 13. Classification Confidence

Classification confidence may be recorded,
but confidence does not override safeguards.

Allowed values:
- `low`
- `medium`
- `high`

Rules:
- low confidence cannot justify weaker review,
- high confidence cannot erase challengeability,
- uncertainty must increase scrutiny rather than reduce it.

---

## 14. Required Classification Fields

A valid `ClassificationResult` must contain at minimum:
- `proposal_id`
- `proposal_revision_id`
- `framework_epoch`
- `parameter_epoch`
- `triviality`
- `primary_layer`
- `framework_change`
- `advisory_only`
- `constitutional_spillover`
- `routing_materiality`
- `bootstrap_scope_violation`
- `emergency_eligibility`
- `minimum_panel_size`
- `classification_confidence`
- `rationale`
- `ambiguities`
- `classified_by`
- `classified_at`
- `counter_classification_required`
- `review_required_before_panel_lock`

Optional but recommended:
- `precedent_refs`
- `affected_domains`
- `affected_groups`
- `estimated_reversibility`
- `resource_materiality`

---

## 15. Challenge and Review

### 15.1 Challengeable Surface
Every non-trivial classification is challengeable.
Every trivial classification is also challengeable.

### 15.2 Valid Challenge Grounds
A classification challenge may be raised for:
- unjustified triviality,
- governance downgrade,
- constitutional spillover omission,
- omitted framework-change flag,
- under-escalation,
- false advisory-only designation,
- improper emergency eligibility,
- bootstrap scope evasion,
- materially incomplete rationale,
- contradictory classification fields.

### 15.3 Review Effects
A valid classification challenge may cause:
- reclassification,
- escalation,
- panel invalidation,
- panel rerun,
- packet withdrawal,
- decision suspension,
- post-decision review if discovered late.

### 15.4 No Penalty for Escalation Challenge
The system must not penalize actors merely for seeking stronger scrutiny in good faith.

---

## 16. Forbidden Classification Patterns

The following are forbidden:
- classifying by UI wording alone,
- using hidden undeclared heuristics,
- silent downgrade of stronger paths,
- declaring triviality without rationale,
- classifying governance changes as policy because the operator prefers speed,
- suppressing framework-change flags to avoid meta-governance,
- using emergency eligibility as a fast-track convenience tool,
- reclassifying after panel lock without formal challenge or review,
- splitting one proposal into weaker fragments solely to avoid stronger scrutiny,
- merging unlike proposals in ways that dilute constitutional consequence.

---

## 17. Bootstrap Rules

During bootstrap,
classification must be stricter, not looser.

### 17.1 Hard-Fixed Classification Levers
The following must be bootstrap hard-fixed:
- triviality criteria,
- policy/governance/constitutional boundary rules,
- framework-change trigger rules,
- minimum escalation rules,
- emergency eligibility rules,
- bootstrap scope exclusions,
- classification challenge windows.

### 17.2 No Ad Hoc Reclassification
No active bootstrap case may have its classification parameters retuned.

### 17.3 Mandatory Independent Check for Strong Cases
During bootstrap,
every governance,
constitutional,
or framework-changing proposal should require independent counter-classification or randomized review.

### 17.4 Stronger-Path Bias
If bootstrap ambiguity remains unresolved,
the proposal must take the stronger scrutiny path or remain pending.

---

## 18. Audit Requirements

Every classification action must append to the audit log.

At minimum, the audit record must include:
- proposal id and revision id,
- submitted text hash,
- classifier identity or class,
- framework epoch,
- parameter epoch,
- result fields,
- rationale,
- ambiguity notes,
- rule validation outcome,
- counter-classification status,
- challenge status,
- lock timestamp,
- any later invalidation or reclassification event.

Deleted or overwritten classification history is forbidden.

---

## 19. Runtime and Operator Constraints

Operators may run classification services.
They may not silently change classification behavior.

Any governance-affecting change to:
- classifier logic,
- prompts,
- rules,
- spillover heuristics,
- escalation mapping,
- challenge thresholds,
- emergency eligibility logic,
- duplicate clustering relevant to classification,
must be treated as a governance-affecting change under `OPERATOR_TRUST_MODEL.md`.

No active case may be reclassified through undeclared runtime drift.

---

## 20. Minimal Result Schema

```json
{
  "proposal_id": "prop_...",
  "proposal_revision_id": "prev_...",
  "framework_epoch": "fw_...",
  "parameter_epoch": "param_...",
  "triviality": "trivial|non_trivial",
  "primary_layer": "policy|governance|constitutional",
  "framework_change": false,
  "advisory_only": false,
  "constitutional_spillover": false,
  "routing_materiality": true,
  "bootstrap_scope_violation": false,
  "emergency_eligibility": "ineligible|eligible|required_for_integrity_only",
  "minimum_panel_size": 5,
  "classification_confidence": "low|medium|high",
  "rationale": ["..."],
  "ambiguities": ["..."],
  "counter_classification_required": true,
  "review_required_before_panel_lock": false,
  "classified_by": "classifier_or_review_pool_id",
  "classified_at": "ISO-8601"
}
```

---

## 21. Classification Invariants

The following must always hold:
- no proposal may reach panel assembly without a logged classification result,
- no non-trivial proposal may be classed as trivial without affirmative justification,
- no ambiguous proposal may silently default to the weaker path,
- no classification result may be silently replaced,
- no active case may be reclassified by undeclared runtime change,
- no governance or constitutional proposal may bypass counter-classification or equivalent friction during bootstrap,
- no framework-changing proposal may be treated as ordinary policy,
- no emergency eligibility may be granted for convenience.

---

## 22. Closing Principle

Classification is not a clerical pre-step.
It is one of the main constitutional choke points of the system.

The system must therefore assume that classification will be gamed,
and must design the classification layer so that disagreement, escalation, auditability, and exogenous friction are normal parts of correct operation.
