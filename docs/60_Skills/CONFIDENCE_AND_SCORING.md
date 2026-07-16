# CONFIDENCE_AND_SCORING

**Status:** Normative  
**Scope:** Confidence derivation, evidence scoring, routing triggers, calibration, review escalation  
**Depends on:** `SYSTEM_PATCH_v1.md`, `EXECUTOR_MODEL.md`, `EVIDENCE_PACKET.md`  
**Related:** `EXECUTOR_RANKING.md`, `MODEL_INCLUSION_SANDBOX.md`, `REVALIDATION_POLICY.md`, `SCHEMAS.md`

---

## 1. Purpose

This document defines how the system computes and uses **confidence**.

Its purpose is to prevent a civic system from confusing:

- stylistic fluency with reliability,
- self-reported certainty with evidentiary strength,
- confident tone with justified judgment,
- consensus-like synthesis with actual robustness.

In this system, confidence is not an emotional signal, a rhetorical posture, or an unstructured model estimate.

Confidence is a **derived procedural score** based on evidence quality, traceability, jurisdiction fit, contradiction handling, uncertainty disclosure, and other review-relevant signals.

---

## 2. Canonical Rule

> Confidence must be derived from protocol-defined signals.  
> It must not be treated as a free-form self-assessment by the executor.

Executors may provide explanatory notes about uncertainty, but the authoritative confidence used for routing must be computed from explicit inputs.

---

## 3. Why This Reform Exists

This reform exists because civic systems are especially vulnerable to false confidence.

The following failures are unacceptable:

- an AI model sounding certain without evidentiary basis,
- a human expert overstating certainty without disclosing missing evidence,
- a synthesis layer masking disagreement under a single polished summary,
- weakly sourced packets moving forward because they “look complete,”
- high-risk proposals proceeding without triggered review because confidence was informally guessed.

The confidence layer exists to make analytic trust:

- explicit,
- inspectable,
- challengeable,
- comparable across executors,
- usable for routing and escalation.

---

## 4. What Confidence Means Here

In this system, confidence means:

> the degree to which the protocol has reason to treat an analytic output as sufficiently grounded, traceable, jurisdictionally fit, complete, and procedurally reliable for the current civic stage.

This is narrower and more useful than a general notion of truth.

Confidence does **not** mean:

- certainty that the conclusion is correct,
- moral correctness,
- political desirability,
- immunity from future revision,
- consensus among all reviewers.

---

## 5. Confidence Objects

Confidence may be computed at multiple levels.

### 5.1 Claim-level confidence
Confidence attached to an individual material claim.

### 5.2 Packet-level confidence
Confidence attached to a full Evidence Packet.

### 5.3 Skill-result confidence
Confidence attached to the complete result of one skill execution.

### 5.4 Synthesis confidence
Confidence attached to the synthesized representation of multiple packets.

### 5.5 Readiness confidence
Confidence that procedural sufficiency has been reached for the next civic stage.

These layers should not be collapsed into one number without explanation.

---

## 6. Design Principles

### 6.1 Evidence over tone
The system should not reward certainty theater.

### 6.2 Penalties for hidden weakness
Missing sources, jurisdiction mismatch, contradiction suppression, and schema gaps must reduce confidence.

### 6.3 Explicit uncertainty is a positive behavior
Declaring uncertainty when appropriate should improve trust calibration rather than be treated as weakness.

### 6.4 Confidence is operational
Confidence exists partly to drive routing, not to decorate outputs.

### 6.5 Confidence is contextual
The same numeric confidence may not justify the same action in a trivial municipal matter and a constitutional rights case.

### 6.6 Confidence must remain challengeable
Any actor inspecting the packet should be able to ask why the score is what it is.

---

## 7. Core Confidence Inputs

The system should derive packet-level confidence from explicit component scores.

### 7.1 Evidence Coverage Score
Measures whether the packet meaningfully addresses the mandate and covers the major required questions.

Questions include:

- Were the key issues addressed?
- Were important dimensions omitted?
- Was the proposal analyzed at the correct scope?

### 7.2 Source Quality Score
Measures the quality and civic weight of the sources used.

Factors may include:

- primary vs secondary source,
- legal or institutional authority,
- public verifiability,
- methodological credibility,
- relevance to the question at hand.

### 7.3 Jurisdiction Relevance Score
Measures whether the source base and reasoning match the correct legal and geographic context.

Factors may include:

- correct jurisdiction selection,
- explicit handling of overlapping jurisdictions,
- degree of locality relevance,
- absence of imported but inapplicable legal assumptions.

### 7.4 Freshness / Legal Currency Score
Measures whether the packet relies on current or still-valid materials where recency matters.

This is especially important for:

- current law,
- regulations,
- budget data,
- institutional decisions,
- implementation constraints,
- emergency contexts.

### 7.5 Claim Traceability Score
Measures whether material claims are individually linked to evidence, assumptions, or explicit inferences.

### 7.6 Contradiction Handling Score or Penalty
Measures whether opposing evidence or conflicting sources were surfaced rather than ignored.

This may be implemented partly as a penalty.

### 7.7 Unknowns Disclosure Score
Measures whether the packet clearly states what remains uncertain, missing, disputed, or under-evidenced.

### 7.8 Schema Completeness Score
Measures whether the required packet structure is present and usable.

### 7.9 Replication Agreement Signal
Where multiple independent executors worked on the same skill, the system may use agreement/disagreement signals.

This must not erase principled disagreement, but it may contribute to routing.

### 7.10 Historical Calibration Signal
Where appropriate, the system may incorporate past calibration performance of the executor in similar contexts.

This should be used carefully and transparently.

---

## 8. Optional Additional Inputs

Depending on the skill and proposal class, the system may also use:

- legal interpretation stability signal,
- adversarial robustness score,
- minority-view preservation score,
- challenge history signal,
- source diversity score,
- execution provenance reliability,
- validator agreement signal,
- evidence retrieval completeness signal.

These should remain subordinate to the core requirements.

---

## 9. Confidence Derivation Model

### 9.1 Derived, not declared
The confidence score used by the protocol should be computed from the relevant component scores and penalties.

### 9.2 Transparent formula family
The exact formula may evolve, but it should belong to an explicit, versioned scoring family.

The formula should be:

- published or auditable,
- versioned,
- challengeable,
- stable enough to avoid arbitrary shifts mid-case.

### 9.3 Weighted composition
Confidence may be computed as a weighted composition of component scores, plus penalties and guardrails.

Illustrative structure:

`derived_confidence = f(coverage, source_quality, jurisdiction_fit, freshness, traceability, unknowns_disclosure, schema_completeness, replication_signal) - penalties`

The exact function belongs in implementation and schema layers, but this document defines the normative logic.

### 9.4 Guardrails over pure averages
A high average should not rescue a packet that fails critical dimensions.

Examples:

- strong prose cannot rescue no legal basis,
- broad coverage cannot rescue wrong jurisdiction,
- many sources cannot rescue no claim traceability,
- strong agreement cannot rescue shared low-quality sourcing.

Critical failures should cap or collapse confidence.

---

## 10. Critical Failure Caps

The system should define confidence caps or hard stops for severe defects.

Examples of cap-triggering defects:

- missing jurisdiction declaration,
- no source basis for material claims,
- absent unknowns section,
- major schema failure,
- use of outdated law in a legally sensitive packet,
- unresolved contradiction in a central claim,
- conflict of interest not disclosed where required.

Such defects should prevent a packet from achieving high confidence regardless of other scores.

---

## 11. Confidence Bands

The system should map derived confidence into policy-relevant bands.

Illustrative bands:

- **Band A: High confidence**
- **Band B: Moderate confidence**
- **Band C: Low confidence**
- **Band D: Insufficient basis / blocked**

These bands should be paired with routing consequences rather than treated as descriptive only.

The exact numeric thresholds may vary by scoring version and proposal class.

---

## 12. Routing and Escalation Logic

Confidence must affect civic routing.

### 12.1 High confidence
High confidence may allow standard progression when:

- risk level is not exceptional,
- no mandatory human review rule applies,
- no severe unresolved challenge is pending,
- no constitutional blocker exists.

### 12.2 Moderate confidence
Moderate confidence should typically trigger one or more of:

- extra review,
- targeted verification,
- adversarial check,
- broader evidence retrieval,
- extended scrutiny emphasis.

### 12.3 Low confidence
Low confidence should usually trigger:

- mandatory human review,
- replication,
- delay pending clarification,
- refusal to mark procedural readiness.

### 12.4 Insufficient basis
A packet that falls into an insufficient-basis state should not advance as if it were simply low confidence. It should be treated as blocked or incomplete.

---

## 13. Risk-Sensitive Overrides

Confidence alone is not enough.

Certain case types require stronger routing regardless of score.

Examples:

- constitutional interpretation,
- severe rights restrictions,
- emergency justification,
- high capture-risk proposals,
- major budgetary consequences,
- novel legal domains,
- unusually high public contestation.

For such cases, even strong confidence may still require:

- human review,
- dual execution,
- adversarial review,
- extended publication or challenge conditions.

---

## 14. Claim-Level Scoring

A packet may contain claims with very different evidentiary strength.

The system should therefore support claim-level scoring for material claims.

Factors may include:

- direct source support,
- source authority,
- contradiction intensity,
- legal interpretive ambiguity,
- forecast uncertainty,
- dependence on assumptions.

A packet should not appear stronger than its weakest material dependency where that dependency is central to the recommendation.

---

## 15. Confidence at Synthesis Stage

### 15.1 No false confidence by averaging
The synthesis layer must not create artificial confidence by averaging across packets that disagree fundamentally.

### 15.2 Disagreement-aware synthesis
Synthesis confidence should account for:

- packet confidence distribution,
- degree of agreement on central claims,
- source overlap vs source independence,
- unresolved contradictions,
- minority reasoning that remains strong.

### 15.3 Confidence dispersion matters
A wide spread between packets may itself be a routing signal.

For example:

- one high-confidence support packet,
- one high-confidence opposition packet,

should not automatically yield medium-confidence synthesis. It may instead trigger explicit conflict escalation.

---

## 16. Calibration

### 16.1 Definition
Calibration means the relation between claimed or derived confidence and later review outcomes.

### 16.2 Why calibration matters
A well-calibrated executor is more trustworthy than an overconfident executor with similar raw output quality.

### 16.3 Calibration evaluation
Executors should be evaluated on patterns such as:

- when they express uncertainty, was it justified?
- when they receive high derived confidence, do later reviews confirm that?
- do they systematically understate or overstate ambiguity?

### 16.4 Calibration as ranking input
Calibration performance should feed executor ranking and revalidation.

---

## 17. Penalty Design

Penalties should be explicit and meaningful.

Typical penalties may include:

- unsupported material claim penalty,
- wrong-jurisdiction penalty,
- stale-law penalty,
- source-conflict suppression penalty,
- missing-unknowns penalty,
- schema-missing-fields penalty,
- inconsistency penalty,
- undeclared conflict-of-interest penalty,
- adversarial-fragility penalty.

Penalty design should avoid both arbitrariness and cosmetic scoring.

---

## 18. Confidence Explanation Requirement

Every packet should include a human-readable explanation of why its derived confidence is what it is.

This explanation should summarize:

- strengths,
- weaknesses,
- decisive penalties or caps,
- review implications.

A numeric score without explanation is not sufficient for civic legitimacy.

---

## 19. Public Inspectability

The public-facing briefing should preserve enough of the confidence basis to support scrutiny.

At minimum, citizens and reviewers should be able to see:

- derived confidence band,
- key supporting strengths,
- main evidence gaps,
- major unresolved contradictions,
- whether the case triggered escalation,
- whether human review was required.

The public should not be asked to trust confidence as a hidden internal score.

---

## 20. Comparative Use across Executors

The scoring model must be applicable across executor classes.

That means:

- AI-generated packets,
- human expert packets,
- panel packets,
- hybrid packets,

should all be scorable on comparable dimensions.

The system may allow executor-specific sub-signals, but the core confidence basis must remain cross-executor.

---

## 21. Contextual Sensitivity

Confidence should be interpreted in context.

The protocol may therefore define different routing thresholds by:

- governance layer,
- rights impact,
- proposal class,
- emergency status,
- jurisdiction sensitivity,
- execution route.

Example:

A confidence level sufficient for a low-impact municipal implementation note may be insufficient for a constitutional rights restriction.

---

## 22. Scoring Versioning

The scoring model itself must be versioned.

A scoring version should define:

- component set,
- weights,
- penalties,
- caps,
- bands,
- routing defaults.

Scoring changes must be prospective only for active cases, unless the protocol explicitly defines emergency correction conditions.

---

## 23. Audit Requirements

The audit record should preserve at least:

- scoring version used,
- component scores,
- penalties applied,
- caps triggered,
- derived confidence result,
- routing decision influenced by confidence,
- later review outcome if available.

This allows the system to inspect whether the scoring layer itself is behaving well.

---

## 24. Failure Modes

Common failure modes include:

- relying on a single opaque confidence number,
- rewarding fluency instead of traceability,
- letting high source count mask low source quality,
- allowing averages to erase critical blockers,
- hiding disagreement inside synthesis,
- treating historical ranking as a substitute for current evidence,
- using confidence without routing consequences,
- penalizing honest uncertainty more than false certainty.

The system should explicitly defend against these patterns.

---

## 25. Minimum Canonical Fields

A minimal confidence record linked to a packet should support fields such as:

- `packet_id`
- `scoring_version`
- `evidence_coverage_score`
- `source_quality_score`
- `jurisdiction_relevance_score`
- `freshness_score`
- `claim_traceability_score`
- `unknowns_disclosure_score`
- `schema_completeness_score`
- `replication_agreement_signal`
- `historical_calibration_signal` where applicable
- `penalties[]`
- `caps_triggered[]`
- `derived_confidence`
- `confidence_band`
- `confidence_explanation`
- `review_required`
- `review_reason_codes[]`
- `human_review_required`
- `replication_recommended`
- `readiness_blocked`

Detailed field typing belongs in `SCHEMAS.md`.

---

## 26. Canonical Operating Rules

1. Confidence is derived, not declared.  
2. Confidence must be evidence-grounded and traceability-aware.  
3. Critical failures cap or block confidence.  
4. Honest uncertainty is not a defect; hidden uncertainty is.  
5. Confidence must affect routing.  
6. High confidence does not override mandatory human review rules.  
7. Synthesis may not create false confidence by averaging disagreement away.  
8. Calibration matters for executor trust.  
9. The scoring model itself must be auditable and versioned.  
10. The public must be able to inspect the basis of confidence.

---

## 27. Closing Principle

> Civic confidence should not mean “the executor sounded sure.”  
> It should mean “the system can show why this judgment is as strong, weak, or limited as it is.” 
