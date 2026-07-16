# EVIDENCE_PACKET

**Status:** Normative  
**Scope:** Skill outputs, evidence requirements, traceability, confidence inputs, public inspection  
**Depends on:** `SYSTEM_PATCH_v1.md`, `EXECUTOR_MODEL.md`  
**Related:** `CONFIDENCE_AND_SCORING.md`, `CITATION_AND_SOURCING_POLICY.md`, `SCHEMAS.md`, `HUMAN_EXPERT_PROTOCOL.md`

---

## 1. Purpose

This document defines the **Evidence Packet** as the canonical output unit for non-trivial civic analysis.

Its purpose is to ensure that analytic outputs are not treated as legitimate merely because they were produced by an AI model, a human expert, or a formal panel. A civic analysis becomes procedurally meaningful only when it is expressed in a structured, inspectable, challengeable, and auditable form.

The Evidence Packet is that form.

It binds together:

- the mandate being answered,
- the proposal being analyzed,
- the jurisdiction and legal scope,
- the source base,
- the material claims,
- the reasoning summary,
- the declared unknowns,
- the recommendation,
- the confidence inputs and traceability metadata.

---

## 2. Canonical Rule

> No non-trivial skill output is complete unless it is expressed as a valid Evidence Packet or a schema-compatible equivalent.

A narrative explanation, model opinion, expert memo, or synthesis note is not sufficient on its own.

---

## 3. Why the Evidence Packet Exists

The Evidence Packet exists to prevent the following failures:

- uncited recommendations,
- opaque summaries,
- hidden jurisdiction assumptions,
- unsupported legal claims,
- false certainty,
- source omission,
- unchallengeable expert authority,
- non-comparable outputs across executors,
- poor auditability in downstream civic review.

The system should never need to ask only:

- “What did the executor conclude?”

It must also be able to ask:

- “What evidence supports that conclusion?”
- “Which claims are cited and which are inferred?”
- “What is unknown?”
- “What jurisdiction applies?”
- “How strong is the evidentiary basis?”
- “What should trigger review or challenge?”

---

## 4. Applicability

### 4.1 Mandatory use
Evidence Packets are mandatory for:

- non-trivial proposals,
- rights-sensitive analysis,
- legal or constitutional analysis,
- budget/resource review,
- feasibility review,
- anti-capture review,
- adversarial critique,
- emergency justification review,
- any analysis that can materially influence public judgment or execution routing.

### 4.2 Optional simplification for trivial matters
For trivial or low-risk matters, the protocol may allow reduced forms, provided that:

- the reduction is explicitly permitted by classification rules,
- the reduced form remains auditable,
- any affected claim can still be traced to its source basis,
- escalation can require a full Evidence Packet.

---

## 5. Packet Design Principles

### 5.1 Evidence before recommendation
A recommendation must emerge from the packet rather than substitute for it.

### 5.2 Traceability over rhetorical fluency
A less polished but fully traceable packet is better than a persuasive but opaque one.

### 5.3 Structured uncertainty
Unknowns, ambiguities, missing evidence, and conflicting sources must be explicitly represented.

### 5.4 Executor neutrality
The packet format must be compatible with AI, human, panel, and hybrid executors.

### 5.5 Public inspectability
The packet must support downstream publication, challenge, and audit without requiring hidden private reasoning.

### 5.6 Jurisdiction sensitivity
The packet must make explicit the territorial and legal frame under which the analysis was conducted.

---

## 6. Core Packet Sections

A valid Evidence Packet should contain the following sections.

### 6.1 Packet Header
Identifies the packet itself.

Minimum fields:

- `packet_id`
- `proposal_id`
- `skill_id`
- `executor_id`
- `executor_class`
- `executor_version` where applicable
- `created_at`
- `schema_version`
- `packet_status`

### 6.2 Mandate Definition
Defines what question the packet is answering.

Minimum fields:

- `mandate_summary`
- `mandate_scope`
- `questions_to_answer`
- `out_of_scope_items`
- `classification_context`

### 6.3 Proposal Reference
Identifies the proposal and the specific text or version under review.

Minimum fields:

- `proposal_title`
- `proposal_version`
- `proposal_snapshot_hash` or equivalent stable reference
- `relevant_sections_under_review`

### 6.4 Jurisdiction and Geographic Scope
States where and under what legal frame the analysis applies.

Minimum fields:

- `geographic_scope`
- `jurisdiction_layers`
- `primary_jurisdiction_assumed`
- `jurisdiction_rationale`
- `overlapping_jurisdiction_notes`
- `jurisdiction_uncertainties`

### 6.5 Legal and Regulatory Context
Captures the applicable rule environment.

Minimum fields:

- `applicable_laws`
- `applicable_regulations`
- `administrative_framework`
- `court_or_case_references` where relevant
- `legal_interpretation_notes`
- `legal_currency_checked_at`

### 6.6 Source Register
Lists the public evidence base used.

Minimum fields:

- `sources[]`

Each source entry should support fields such as:

- `source_id`
- `title`
- `source_type`
- `issuer_or_author`
- `publication_date`
- `retrieved_at`
- `jurisdiction_relevance`
- `legal_or_institutional_weight`
- `url_or_reference`
- `language`
- `notes`

### 6.7 Evidence Summary
Provides a structured summary of the most important evidentiary findings.

Minimum fields:

- `key_findings[]`
- `evidence_gaps[]`
- `source_conflicts[]`
- `important_contextual_constraints[]`

### 6.8 Material Claims Register
The packet must identify material claims individually rather than burying them inside prose.

Each material claim should support fields such as:

- `claim_id`
- `claim_text`
- `claim_type`  
  such as fact / interpretation / inference / assumption / forecast
- `supporting_source_ids[]`
- `contradicting_source_ids[]`
- `traceability_status`
- `claim_strength`
- `notes`

### 6.9 Reasoning Summary
A plain-language synthesis of how the evidence supports the packet’s overall assessment.

Minimum fields:

- `reasoning_summary`
- `key_tradeoffs`
- `most_supportive_reasoning`
- `most_constraining_reasoning`
- `why_alternative_readings_exist`

This section explains the reasoning, but does not replace the claims register.

### 6.10 Unknowns and Uncertainties
This section is mandatory.

Minimum fields:

- `unknowns[]`
- `ambiguities[]`
- `missing_data[]`
- `uncertain_legal_points[]`
- `confidence_limitations[]`

### 6.11 Contested Points and Adversarial Notes
The packet must preserve structured disagreement where relevant.

Minimum fields:

- `contested_points[]`
- `minority_or_dissent_notes[]`
- `adversarial_risks[]`
- `capture_risk_notes[]`

### 6.12 Recommendation
The packet must include a bounded, procedural recommendation.

Minimum fields:

- `recommendation_type`  
  such as support / oppose / revise / delay / escalate / insufficient basis
- `recommendation_summary`
- `conditions_for_support`
- `conditions_for_rejection`
- `required_follow_up`
- `recommended_review_route`

### 6.13 Confidence Inputs
The packet must include the components used for confidence derivation.

Minimum fields:

- `evidence_coverage_score`
- `source_quality_score`
- `jurisdiction_relevance_score`
- `freshness_score`
- `claim_traceability_score`
- `contradiction_penalty`
- `unknowns_disclosure_score`
- `schema_completeness_score`
- `derived_confidence`
- `confidence_explanation`

Detailed computation belongs in `CONFIDENCE_AND_SCORING.md`.

### 6.14 Review and Routing Signals
The packet must communicate whether additional review should be triggered.

Minimum fields:

- `review_required`
- `review_reason_codes[]`
- `human_review_required`
- `replication_recommended`
- `challenge_risk_level`
- `blocking_issues[]`

### 6.15 Provenance and Audit Metadata
The packet must expose how it was produced.

Minimum fields:

- `execution_mode`
- `input_artifacts[]`
- `retrieval_method_notes`
- `validation_checks[]`
- `conflict_of_interest_disclosures[]`
- `independence_notes`
- `audit_notes`

---

## 7. Source Classes

The system should distinguish among source classes because not all sources carry the same civic weight.

Typical source classes may include:

- constitutional text,
- statutes and regulations,
- court decisions,
- administrative acts,
- official public datasets,
- public budgets and accounts,
- public procurement data,
- institutional reports,
- academic literature,
- standards and technical guidance,
- credible public investigative materials,
- public consultation records.

The packet should not flatten all sources into a generic list.

---

## 8. Claim Traceability Rules

### 8.1 Material claims must be separately represented
A packet should identify the claims that matter for civic judgment.

### 8.2 Traceability categories
Each claim should be classed as one of the following:

- directly supported,
- indirectly supported through multi-source inference,
- contested,
- assumption-based,
- currently unsupported.

### 8.3 Unsupported claims
Unsupported material claims must not be hidden inside narrative prose.
They should either:

- be removed,
- be downgraded,
- be explicitly marked as uncertain,
- or trigger review.

### 8.4 Forecast claims
Predictions and feasibility claims should state their basis, assumptions, and uncertainty.
Forecast-like claims should not be disguised as established fact.

---

## 9. Jurisdiction Rules

### 9.1 Explicit jurisdiction declaration
A packet must never silently assume jurisdiction.

### 9.2 Layered jurisdiction
Where multiple layers apply, the packet should distinguish among them, such as:

- local,
- regional,
- national,
- supranational,
- treaty or international constraints.

### 9.3 Jurisdiction mismatch
If the available evidence does not cleanly match the relevant jurisdiction, the packet must say so and reduce confidence accordingly.

### 9.4 Geographic context
Where public policy is materially shaped by geography, local administrative structure, or demographic conditions, the packet should reflect that context explicitly.

---

## 10. Legal Context Rules

### 10.1 Current law sensitivity
Where law materially matters, the packet should state when legal currency was checked.

### 10.2 Legal interpretation humility
If the packet contains legal interpretation rather than direct citation alone, it should say so explicitly.

### 10.3 Constitutional sensitivity
For constitutional or rights-impacting matters, the packet should surface:

- the relevant rights frame,
- the legal basis for restriction or permission,
- the proportionality or necessity questions where applicable,
- the need for human review if interpretation is contested.

---

## 11. Unknowns and Missing Evidence

### 11.1 Mandatory disclosure
Unknowns are not optional. They are part of civic honesty.

### 11.2 Types of uncertainty
Packets should distinguish among:

- unavailable data,
- conflicting sources,
- ambiguous law,
- incomplete jurisdiction mapping,
- uncertain implementation effects,
- unresolved adversarial risks.

### 11.3 Missing evidence consequences
If material evidence is missing, the packet should not compensate with rhetorical certainty.
It should instead:

- mark the gap,
- lower derived confidence,
- recommend review or delay where appropriate,
- preserve the issue for public scrutiny.

---

## 12. Recommendation Rules

### 12.1 Recommendations must be bounded
Recommendations should be procedural and conditional, not grandiose or absolute.

### 12.2 Recommendation classes
A packet may recommend outcomes such as:

- support,
- support with conditions,
- revise and resubmit,
- delay pending evidence,
- oppose,
- escalate for human review,
- insufficient basis for recommendation.

### 12.3 No recommendation without stated basis
A recommendation must reference the key supporting findings, key constraints, and major unknowns that shaped it.

---

## 13. Confidence Inputs and Routing

### 13.1 Confidence belongs to the packet, not to the ego of the executor
Confidence should be derived from traceable indicators.

### 13.2 Packet-level role in routing
An Evidence Packet should carry enough structured signals for the protocol to decide whether the case may proceed normally or must escalate.

### 13.3 Low-confidence handling
Low-confidence packets should not be silently averaged into confidence through synthesis. They should remain visible and may trigger:

- human review,
- independent replication,
- broader evidence retrieval,
- challenge window emphasis,
- refusal to mark readiness.

---

## 14. Public Publication Rules

### 14.1 Publicly inspectable subset
The system should define a public-facing packet view that preserves civic inspectability without exposing unnecessary sensitive internals.

### 14.2 What should be visible
At minimum, public publication should preserve:

- mandate summary,
- jurisdiction,
- key sources,
- key findings,
- material claims,
- unknowns,
- contested points,
- recommendation,
- derived confidence,
- review and challenge signals.

### 14.3 What may be restricted
Operational details may be partially restricted where necessary for security, privacy, or anti-abuse reasons, but such restriction must not destroy civic inspectability.

---

## 15. Comparison across Executors

The Evidence Packet must make outputs comparable across executor types.

That means a packet produced by:

- an AI system,
- a human expert,
- a human panel,
- a hybrid pipeline,

should still be comparable on dimensions such as:

- evidence completeness,
- source quality,
- jurisdiction fit,
- uncertainty discipline,
- recommendation basis,
- routing implications.

Without such comparability, ranking and revalidation become weak.

---

## 16. Validation Rules

A packet should be schema-validated before it can count as complete.

Validation should test at least:

- required fields present,
- source list integrity,
- claim-to-source references resolvable,
- jurisdiction fields populated,
- recommendation present,
- unknowns section present,
- confidence inputs present,
- no prohibited empty placeholders for material fields.

A packet that fails validation should not silently proceed as if valid.

---

## 17. Failure Modes

Common failure modes include:

- polished prose with no claims register,
- recommendation without evidence basis,
- legal claims without current-law grounding,
- source lists that do not actually support the stated claims,
- confidence without derivation inputs,
- hidden assumptions about geography or law,
- omission of opposing evidence,
- over-aggregation that erases minority concerns,
- incompatible packet shapes across executors.

The protocol should detect and penalize these failures.

---

## 18. Reduced Forms and Escalation

Where reduced forms are allowed for low-risk matters, the protocol should still preserve a path to full packet expansion.

Escalation to a full Evidence Packet should be triggered by conditions such as:

- public challenge,
- rights sensitivity,
- evidence dispute,
- legal ambiguity,
- low confidence,
- unusual implementation complexity,
- anti-capture concern.

---

## 19. Minimum Canonical Schema

A minimal canonical packet schema should support fields such as:

- `packet_id`
- `proposal_id`
- `skill_id`
- `executor_id`
- `executor_class`
- `executor_version`
- `created_at`
- `schema_version`
- `mandate_summary`
- `proposal_version`
- `proposal_snapshot_hash`
- `geographic_scope`
- `jurisdiction_layers`
- `primary_jurisdiction_assumed`
- `applicable_laws[]`
- `sources[]`
- `key_findings[]`
- `material_claims[]`
- `reasoning_summary`
- `unknowns[]`
- `contested_points[]`
- `capture_risk_notes[]`
- `recommendation_type`
- `recommendation_summary`
- `evidence_coverage_score`
- `source_quality_score`
- `jurisdiction_relevance_score`
- `freshness_score`
- `claim_traceability_score`
- `contradiction_penalty`
- `unknowns_disclosure_score`
- `schema_completeness_score`
- `derived_confidence`
- `review_required`
- `human_review_required`
- `replication_recommended`
- `validation_checks[]`
- `conflict_of_interest_disclosures[]`
- `audit_notes`

Detailed typed schemas belong in `SCHEMAS.md`.

---

## 20. Canonical Operating Rules

1. No non-trivial analysis without an Evidence Packet.  
2. No recommendation without a stated evidence basis.  
3. No material legal or factual claim without traceability.  
4. No silent jurisdiction assumption.  
5. No hidden uncertainty.  
6. No confidence without derivation inputs.  
7. No packet passes as complete without schema validation.  
8. No executor class is exempt from packet discipline.  
9. Public scrutiny must be able to inspect the packet basis.  
10. The packet is part of civic legitimacy, not just storage.

---

## 21. Closing Principle

> The civic process should not ask the public to trust conclusions in the dark.  
> It should ask executors to show their basis, expose their limits, and make judgment inspectable.
