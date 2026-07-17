# HUMAN_EXPERT_PROTOCOL

**Status:** Normative  
**Scope:** Human expert participation, eligibility, duties, conflicts, routing, review, auditability  
**Depends on:** `SYSTEM_PATCH_v1.md`, `EXECUTOR_MODEL.md`, `EVIDENCE_PACKET.md`, `CONFIDENCE_AND_SCORING.md`, `EXECUTOR_RANKING.md`, `REVALIDATION_POLICY.md`  
**Related:** `MODEL_INCLUSION_SANDBOX.md`, `CITATION_AND_SOURCING_POLICY.md`, `SCHEMAS.md`

---

## 1. Purpose

This document defines the **Human Expert Protocol**.

Its purpose is to ensure that human expertise is integrated into the civic process as a **formal, auditable, challengeable, protocol-bound execution layer**.

Human experts must not appear in the system merely as:

- informal overrides when AI is distrusted,
- prestige-based authorities,
- opaque reviewers whose reasoning is accepted without inspection,
- emergency substitutes outside the civic loop.

Instead, human experts must operate inside the same civic discipline that governs all executors:

- bounded mandate,
- explicit jurisdiction,
- evidence-backed reasoning,
- structured output,
- declared uncertainty,
- conflict disclosure,
- auditability,
- challengeability.

---

## 2. Canonical Rule

> Human experts are first-class executors within the protocol, not extra-procedural authorities.

Their involvement increases legitimacy only when it remains structured, inspectable, and constrained by the civic process.

---

## 3. Why This Protocol Exists

This protocol exists to prevent two opposite failures.

### 3.1 First failure: AI-only civic dependence
A civic system becomes brittle if it cannot escalate to humans when machine outputs are weak, under-evidenced, conflicted, or high-risk.

### 3.2 Second failure: ungoverned human authority
A civic system also becomes brittle if human experts are inserted as opaque saviors whose authority rests on prestige, institutional status, or rhetorical force rather than protocol discipline.

The Human Expert Protocol exists to avoid both failures by making human expertise:

- available,
- bounded,
- comparable,
- ranked,
- reviewable.

---

## 4. What Counts as a Human Expert Executor

A human expert executor is a human actor or structured human group authorized to perform one or more civic skills under protocol rules.

Examples may include:

- legal experts,
- constitutional scholars,
- public finance experts,
- administrative practitioners,
- domain specialists,
- civic auditors,
- local context specialists,
- expert panels,
- adversarial reviewers,
- appeal reviewers.

Human expertise should be defined by role and admissible scope, not by title alone.

---

## 5. Human Executor Classes

The system should support multiple classes of human participation.

### 5.1 Single Human Expert
One identified expert performs a bounded skill.

### 5.2 Human Expert Panel
A small structured panel performs a skill jointly or in plural form.

### 5.3 Human Verifier
A human expert verifies, critiques, or approves defined aspects of another executor’s output.

### 5.4 Human Adversarial Reviewer
A human expert is tasked not with confirming the main line of reasoning, but with stress-testing it.

### 5.5 Human Appeal Reviewer
A human expert or panel reviews challenged or escalated outputs.

### 5.6 Hybrid Human Role
A human expert participates inside a hybrid workflow, such as:

- AI evidence retrieval + human legal synthesis,
- AI draft + human validation,
- human report + AI traceability check.

---

## 6. Human Expertise as Scoped Admissibility

Human experts should not be treated as globally authoritative.

Their admissibility should be scoped by dimensions such as:

- skill,
- domain,
- jurisdiction,
- proposal class,
- risk level,
- execution mode,
- review role.

A human expert may be admissible for one context and inadmissible or insufficiently qualified for another.

---

## 7. Eligibility Requirements

A human expert should satisfy explicit eligibility requirements before admission.

Possible requirements include:

- demonstrated domain competence,
- relevant jurisdiction competence,
- ability to work under structured output requirements,
- willingness to disclose conflicts of interest,
- acceptance of audit and challengeability,
- acceptable independence profile where required,
- ability to meet timing and procedural obligations,
- successful sandboxing or workflow evaluation where applicable.

Eligibility must not rely solely on informal reputation.

---

## 8. Admission and Registration

Human experts should be registered as formal executors.

A human expert record should include, where applicable:

- executor ID,
- executor class,
- identity or verified institutional identity,
- domain scope,
- jurisdiction scope,
- approved skills,
- review roles,
- conflict-of-interest disclosures,
- institutional affiliations,
- ranking profile,
- revalidation status,
- current restrictions or probation notes.

The system should not depend on ad hoc personal selection without trace.

---

## 9. Mandate Discipline

Human experts must receive a bounded mandate.

A valid mandate should define at least:

- what proposal or proposal section is under review,
- which skill is being executed,
- the relevant jurisdiction,
- the questions to answer,
- the evidence burden,
- the deadline,
- the expected output schema,
- the role in routing  
  such as primary review, verification, adversarial review, appeal review.

Human expertise without mandate discipline can easily expand into unbounded authority.

---

## 10. Output Obligations

Human experts must produce structured outputs that are compatible with the system’s analytic layer.

For non-trivial cases, this normally means an `Evidence Packet` or schema-compatible equivalent.

Minimum obligations include:

- mandate response,
- jurisdiction statement,
- legal and factual basis,
- source citations,
- material claims,
- reasoning summary,
- unknowns and ambiguities,
- recommendation,
- confidence inputs where applicable,
- conflict-of-interest disclosure.

A free-form memo is not sufficient unless explicitly transformed into a compatible structured artifact.

---

## 11. Citation and Evidence Discipline

Human expertise does not exempt anyone from source discipline.

Human experts must:

- cite material claims,
- distinguish fact from interpretation,
- distinguish law from commentary,
- identify assumptions,
- expose missing evidence,
- avoid replacing evidence with authority signaling.

The protocol should reject the logic:

- “this is credible because an expert said it.”

Instead it should require:

- “this is inspectable because the expert showed the basis.”

---

## 12. Uncertainty Obligations

Human experts must explicitly state:

- what is unknown,
- what is disputed,
- where law is ambiguous,
- where evidence is incomplete,
- where the recommendation depends on assumptions,
- where a different expert could reasonably disagree.

Human confidence should not be accepted as inherently better calibrated than model confidence. It must still be structurally disclosed and reviewable.

---

## 13. Conflict of Interest Protocol

### 13.1 Mandatory disclosure
Human experts must disclose relevant conflicts of interest before or at the time of execution.

Examples may include:

- financial interest,
- employment or consulting relationship,
- partisan or institutional alignment where materially relevant,
- direct stake in proposal outcome,
- prior public advocacy strongly tied to the case,
- close involvement in drafting the proposal under review.

### 13.2 Scope of disclosure
The disclosure standard should be broad enough to capture meaningful risk, but not so broad that it makes participation impossible.

### 13.3 Effect of conflict
A disclosed conflict may lead to:

- full exclusion,
- limited role,
- mandatory co-review,
- adversarial counter-review,
- public notation,
- procedural challenge rights.

### 13.4 Non-disclosure as a serious defect
Undeclared material conflict should be treated as a serious breach and may trigger invalidation, downgrade, suspension, or formal review.

---

## 14. Independence Requirements

Where independence is required, the system should define what counts as meaningful independence for human executors.

Possible dimensions include:

- different institutional affiliation,
- lack of supervisory dependency,
- lack of direct stake in outcome,
- absence of prior co-authorship or co-drafting role where relevant,
- adversarial review by a non-aligned expert.

Merely using different names is not enough if the dependency chain is functionally the same.

---

## 15. When Human Review Is Mandatory

The protocol should define classes of cases that require human involvement.

These may include:

- constitutional interpretation,
- rights-restricting proposals,
- emergency justification review,
- low-confidence machine outputs,
- high-confidence disagreement among packets,
- novel legal domains,
- major public contestation,
- severe evidence conflict,
- suspected capture or manipulation,
- appeal or post-decision review.

Human involvement may be primary, secondary, adversarial, or appellate depending on the case.

---

## 16. Human Review Roles in Routing

Human experts may enter the civic process at multiple stages.

Examples:

- as primary skill executor,
- as verifier of AI output,
- as adversarial critic,
- as escalation reviewer,
- as synthesis constraint reviewer,
- as appeal reviewer,
- as post-decision audit reviewer.

The system should explicitly record which role was played.

---

## 17. Human Panels

### 17.1 When panels are appropriate
Panels are especially useful for:

- constitutional questions,
- highly contested legal interpretation,
- high-stakes rights cases,
- complex multi-domain review,
- appeal and re-review.

### 17.2 Panel output forms
A panel may produce:

- a joint packet,
- a majority packet plus minority note,
- multiple parallel packets,
- a structured consensus and dissent map.

### 17.3 Dissent preservation
Panels must preserve meaningful dissent rather than forcing artificial consensus.

A panel that erases disagreement may reduce civic visibility rather than improve it.

---

## 18. Human Verifiers and Adversarial Reviewers

A human verifier should have a bounded checking role, such as:

- jurisdiction correctness,
- legal applicability,
- source adequacy,
- coherence of recommendation,
- missing unknowns,
- missed contrary evidence.

A human adversarial reviewer should be empowered to ask:

- What has been omitted?
- What is overstated?
- What conflict has been softened?
- Where is the capture risk?
- Where does the confidence exceed the evidence?

These are distinct roles and should not be collapsed.

---

## 19. Comparison with AI Executors

Human experts are not automatically superior to AI executors in every dimension.

Human experts may be stronger in:

- legal nuance,
- institutional context,
- local reality,
- principled interpretation,
- contested judgment.

But they may be weaker in:

- throughput,
- consistency,
- repeatability,
- hidden bias control,
- structured traceability if the protocol is weak.

The protocol should therefore compare human and AI executors under shared civic criteria rather than assume one class is categorically better.

---

## 20. Ranking and Performance Memory

Human experts and panels must accumulate structured performance memory.

Relevant signals may include:

- evidence quality,
- citation discipline,
- jurisdiction fit,
- calibration,
- verifier agreement,
- appeal survival,
- challenge rate,
- reversal rate,
- dissent quality,
- timeliness,
- conflict compliance.

Prestige alone should not substitute for ranking.

---

## 21. Revalidation of Human Experts

Human experts must remain subject to periodic or triggered revalidation.

Triggers may include:

- repeated challenge or reversal,
- emerging conflict of interest,
- change in institutional ties,
- decline in citation discipline,
- poor calibration,
- late or incomplete outputs,
- expansion into new jurisdictions or new skills.

Human authority must remain reviewable over time.

---

## 22. Sandbox and Workflow Testing

Where the system standardizes human participation into formal workflows, those workflows should be testable in sandbox or dry-run conditions.

Examples:

- a human legal review process,
- a panel dissent workflow,
- a human verifier checklist,
- a hybrid AI-plus-human packet production flow.

The sandbox is not only for models. It is also for human review patterns.

---

## 23. Timing and Capacity Constraints

Human participation introduces scarcity and time costs.

Therefore the protocol should define:

- when human review is mandatory,
- when it is discretionary,
- response windows,
- fallback rules for unavailability,
- priority rules for high-risk cases,
- whether panels may be replaced by single experts in narrow emergency circumstances,
- how delay affects procedural readiness.

Human legitimacy should not depend on pretending humans are infinitely available.

---

## 24. Compensation and Incentive Design

If human experts are compensated, the system should define principles that reduce distortion.

Possible concerns include:

- pay tied to speed over quality,
- incentives to confirm dominant narratives,
- hidden institutional sponsorship,
- overuse of a small prestige class,
- underfunded review leading to superficial outputs.

Compensation design belongs partly in governance, but the protocol should recognize that incentives affect civic analysis quality.

---

## 25. Public Visibility and Legitimacy

The public should be able to know enough about human participation to assess legitimacy.

At minimum, the system should preserve visibility into:

- whether a human expert or panel was used,
- what role they played,
- whether conflicts were disclosed,
- whether dissent existed,
- whether their output met evidence standards,
- whether later challenge materially changed the case.

The public should not be asked to trust unnamed wisdom in the dark.

---

## 26. Audit Requirements

The audit trail should preserve at least:

- `executor_id`
- `executor_class`
- `human_role`
- `approved_skills[]`
- `approved_jurisdictions[]`
- `mandate_id`
- `proposal_id`
- `conflict_of_interest_disclosures[]`
- `independence_notes`
- `output_artifact_ids[]`
- `review_role`
- `ranking_snapshot`
- `revalidation_status`
- `challenge_events[]`
- `appeal_events[]`
- `decision_impact_notes`
- `created_at`

Human expertise must be auditable at the same seriousness as machine execution.

---

## 27. Failure Modes

Common failure modes include:

- prestige substituting for evidence,
- free-form memos replacing structured outputs,
- undisclosed institutional conflict,
- pseudo-independent experts from the same dependency cluster,
- forced consensus that erases dissent,
- human review used as decorative validation rather than real scrutiny,
- slow human bottlenecks without routing rules,
- assuming human confidence is inherently reliable,
- informal restoration of suspended experts,
- hidden concentration of influence in a small expert class.

The system should explicitly defend against these patterns.

---

## 28. Minimum Canonical Fields

A human expert protocol record should minimally support fields such as:

- `executor_id`
- `executor_class`
- `human_role`
- `identity_or_institution`
- `approved_skills[]`
- `approved_domains[]`
- `approved_jurisdictions[]`
- `review_roles[]`
- `conflict_of_interest_disclosures[]`
- `institutional_affiliations[]`
- `ranking_profile`
- `revalidation_due_at`
- `current_restrictions[]`
- `mandate_id`
- `required_output_schema`
- `output_artifact_ids[]`
- `independence_requirements`
- `review_mode`
- `notes`

Detailed field typing belongs in `SCHEMAS.md`.

---

## 29. Canonical Operating Rules

1. Human experts are protocol-bound executors, not extra-procedural authorities.  
2. Human participation does not waive evidence, citation, or schema requirements.  
3. Human expertise must be scoped by skill, domain, jurisdiction, and role.  
4. Conflict-of-interest disclosure is mandatory where materially relevant.  
5. Independence requirements must be meaningful, not cosmetic.  
6. High-risk cases may require human primary review, verification, or appeal.  
7. Human panels must preserve dissent when it matters.  
8. Human experts are rankable and revalidatable.  
9. Human workflows may be sandboxed and audited.  
10. Civic legitimacy increases when human expertise is visible, bounded, and challengeable.

---

## 30. Closing Principle

> Human expertise should not enter the civic process as opaque authority.  
> It should enter as disciplined judgment: sourced, bounded, declared, reviewable, and accountable. 
