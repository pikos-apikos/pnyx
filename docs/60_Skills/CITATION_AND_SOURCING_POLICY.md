# CITATION_AND_SOURCING_POLICY

**Status:** Normative  
**Scope:** Source admissibility, citation discipline, claim traceability, evidentiary quality, public inspectability  
**Depends on:** `SYSTEM_PATCH_v1.md`, `EVIDENCE_PACKET.md`, `CONFIDENCE_AND_SCORING.md`, `HUMAN_EXPERT_PROTOCOL.md`  
**Related:** `EXECUTOR_MODEL.md`, `EXECUTOR_RANKING.md`, `SCHEMAS.md`

---

## 1. Purpose

This document defines the **Citation and Sourcing Policy** of the system.

Its purpose is to ensure that civic analysis is not treated as legitimate merely because it is well written, expert-sounding, confident, or procedurally complete in appearance.

The system must be able to distinguish between:

- claims grounded in relevant public evidence,
- claims grounded in weak or mismatched sources,
- claims that are reasonable but inferential,
- claims that remain unresolved,
- claims that should not be allowed to carry civic weight at all.

This policy therefore defines:

- what counts as an admissible source,
- how claims must be cited,
- how source quality affects confidence,
- how conflicting or missing evidence must be handled,
- what public visibility is required for civic legitimacy.

---

## 2. Canonical Rule

> No material civic claim should carry procedural weight unless its evidentiary basis is visible, classifiable, and challengeable.

Citations are not decorative references. They are part of the legitimacy structure of the system.

---

## 3. Why This Policy Exists

This policy exists to prevent several recurring failures:

- source laundering through impressive-looking but weak references,
- uncited material claims hidden inside fluent prose,
- wrong-jurisdiction sourcing,
- outdated law presented as current,
- public inability to inspect the basis of recommendations,
- human prestige replacing evidence,
- model fabrication masked by generic references,
- false pluralism built from the same weak evidentiary base.

A civic system should not ask only:

- “Did the executor cite something?”

It must also ask:

- “Was the cited source relevant?”
- “Was it authoritative enough for this kind of claim?”
- “Was it current?”
- “Was it in the correct jurisdiction?”
- “Does it actually support the claim being made?”

---

## 4. Policy Objectives

This policy aims to ensure that citations and sources are:

- relevant,
- jurisdictionally appropriate,
- current where currency matters,
- weighted by civic significance,
- linked to specific claims,
- comparable across executors,
- inspectable by the public,
- usable for scoring, challenge, and audit.

---

## 5. Source Admissibility

### 5.1 Admissible source principle
A source is admissible when it is appropriate to the type of claim being made and can be evaluated for relevance, origin, and evidentiary weight.

### 5.2 Admissibility is contextual
A source may be admissible for one purpose and weak or inadmissible for another.

Examples:

- a news report may be useful for situational context but insufficient for a legal claim,
- an academic paper may inform policy forecasting but not replace binding law,
- a municipal budget document may support resource claims in one locality but not another,
- advocacy material may reveal stakeholder framing but not serve as neutral proof of effect.

### 5.3 No source is automatically sufficient by format alone
The fact that something is “official,” “published,” or “expert-written” does not by itself make it sufficient for every claim.

---

## 6. Source Classes

The system should classify sources into distinct families because civic weight varies significantly across them.

Possible source classes include:

### 6.1 Constitutional and Foundational Legal Texts
Examples:

- constitutions,
- foundational charters,
- treaty-level obligations,
- supranational rights frameworks.

### 6.2 Statutes and Regulations
Examples:

- laws,
- decrees,
- regulations,
- implementing rules,
- ministerial decisions.

### 6.3 Judicial and Quasi-Judicial Materials
Examples:

- court decisions,
- constitutional rulings,
- administrative tribunal decisions,
- ombudsman findings where relevant.

### 6.4 Administrative and Institutional Documents
Examples:

- circulars,
- policy manuals,
- agency guidance,
- official notices,
- procurement records,
- public consultation outputs.

### 6.5 Public Finance and Operational Records
Examples:

- approved budgets,
- spending reports,
- staffing tables,
- implementation schedules,
- audit reports.

### 6.6 Official Public Data and Statistics
Examples:

- census data,
- statistical authority releases,
- municipal open data,
- geospatial records,
- official registries.

### 6.7 Academic and Research Materials
Examples:

- peer-reviewed research,
- research institute reports,
- technical assessments,
- policy evaluations.

### 6.8 Standards and Technical Guidance
Examples:

- engineering standards,
- technical codes,
- public health guidance,
- industry safety norms where publicly relevant.

### 6.9 Journalistic and Investigative Materials
Examples:

- credible investigative reporting,
- corroborated public reporting,
- public document-based journalism.

### 6.10 Stakeholder and Advocacy Materials
Examples:

- NGO reports,
- civic association submissions,
- business association memos,
- union or sectoral statements.

### 6.11 Public Testimony and Consultation Inputs
Examples:

- public comments,
- hearing transcripts,
- consultation submissions,
- citizen complaint records.

The system should not flatten these classes into a single undifferentiated “sources[]” bucket in evaluation logic, even if implementation stores them in one list.

---

## 7. Source Quality Dimensions

Source quality should be evaluated along multiple dimensions.

### 7.1 Authority
How much formal or evidentiary weight the source carries for the claim type.

### 7.2 Relevance
How directly the source speaks to the specific question or claim.

### 7.3 Jurisdiction Fit
Whether the source belongs to or validly applies to the relevant legal and geographic scope.

### 7.4 Currency
Whether the source is current where recency matters.

### 7.5 Verifiability
Whether the source can be inspected, retrieved, and checked by others.

### 7.6 Methodological Credibility
Especially for empirical and forecast claims.

### 7.7 Independence
Whether multiple sources are genuinely independent or merely echo one another.

### 7.8 Specificity
Whether the source actually supports the exact claim, rather than only vaguely adjacent points.

These dimensions should inform confidence and ranking, not remain rhetorical observations.

---

## 8. Claim Types and Citation Burden

Different claim types require different citation burdens.

### 8.1 Legal Claims
Claims about legality, authority, obligation, prohibition, or constitutional compatibility should normally cite binding or directly relevant legal materials.

### 8.2 Factual Claims
Claims about current conditions, budgets, numbers, institutions, or implementation status should normally cite direct records or credible data sources.

### 8.3 Interpretive Claims
Interpretations should cite the underlying source materials plus, where helpful, interpretive authorities or established reasoning.

### 8.4 Forecast and Feasibility Claims
Predictions should cite evidence for assumptions, comparable cases, technical constraints, and uncertainty.

### 8.5 Normative or Value-Framed Claims
Value judgments may not always be “provable” in the same way, but when they materially depend on empirical assumptions or legal framing, those dependencies must still be cited.

The more procedurally load-bearing the claim, the stronger the citation burden.

---

## 9. Material Claims Must Be Cited

### 9.1 Material claim definition
A material claim is any claim that could influence:

- recommendation,
- routing,
- readiness,
- rights analysis,
- legality judgment,
- budget implication,
- capture-risk assessment,
- public interpretation of the proposal.

### 9.2 Citation requirement
Material claims should be linked to:

- one or more supporting sources,
- one or more contradicting sources where relevant,
- explicit inference notes if the claim is not directly stated in a source,
- assumptions if the claim depends on unverified conditions.

### 9.3 Unsupported material claims
Unsupported material claims should not silently retain civic force.
They should be:

- removed,
- downgraded,
- marked uncertain,
- or treated as a review trigger.

---

## 10. Citation Granularity

Citations should be granular enough to support challenge and inspection.

Appropriate granularity may include:

- source-level citation,
- section-level citation,
- article or clause citation,
- paragraph or page citation where available,
- dataset field reference,
- decision number or record identifier.

A citation that points only to a broad site or institution when a narrower reference is available is often insufficient.

---

## 11. Claim-to-Source Traceability

### 11.1 Core requirement
The system should preserve traceability from claim to source, not only from packet to source bundle.

### 11.2 Traceability categories
Each material claim should be classed as one of:

- directly supported,
- supported by synthesis across multiple sources,
- interpretive,
- assumption-based,
- contested,
- currently unsupported.

### 11.3 Inference notes
Where a claim is not stated directly in a source but is inferred, the packet should say so.

Inference is legitimate when transparent. Hidden inference is a problem.

---

## 12. Jurisdictional Sourcing Rules

### 12.1 Correct jurisdiction first
Sources should be selected with the relevant jurisdiction in mind from the start.

### 12.2 Imported source caution
Sources from another jurisdiction may be useful for analogy, comparative learning, or implementation reference, but they should not be silently treated as binding or directly applicable.

### 12.3 Overlapping jurisdiction
Where multiple jurisdiction layers matter, the packet should distinguish them rather than citing one layer as if it fully resolved the issue.

### 12.4 Jurisdiction mismatch consequences
Wrong-jurisdiction sourcing should reduce confidence and may require review or invalidate specific claims.

---

## 13. Currency and Staleness Rules

### 13.1 Recency-sensitive claims
Where law, regulations, budgets, public decisions, or implementation conditions change over time, sources must be checked for currency.

### 13.2 Historical sources
Older sources may remain valuable for background, precedent, or historical context, but should not be treated as current authority without explicit justification.

### 13.3 Legal currency
If a legal claim depends on current law, the packet should indicate when current-law status was checked.

### 13.4 Staleness consequences
Stale sources used as current authority should reduce confidence and may trigger review or invalidation.

---

## 14. Source Diversity and Independence

### 14.1 Diversity principle
Multiple sources are helpful only when they add informational value rather than repeating the same dependency chain.

### 14.2 False plurality risk
The system should detect cases where many citations merely repeat:

- the same underlying report,
- the same press release,
- the same legal memo,
- the same provider-generated summary.

### 14.3 Independence value
Independent corroboration should count positively, especially in contested or adversarial cases.

---

## 15. Conflicting Sources

### 15.1 Conflict visibility
When relevant sources conflict, the packet should not hide or smooth over the conflict.

### 15.2 Required behavior
The executor should:

- identify the conflict,
- explain its procedural significance,
- indicate which source is being weighted more heavily and why,
- reduce confidence if appropriate,
- preserve the issue for challenge or review where material.

### 15.3 No silent cherry-picking
Selecting only convenient sources in the presence of visible relevant conflict is a serious defect.

---

## 16. Weak, Indirect, and Contextual Sources

Not all claims can be supported by direct binding material.

In such cases, the executor may use weaker or indirect sources, but must clearly label their role.

Examples:

- background context,
- comparative practice,
- hypothesis generation,
- forecast support,
- implementation analogy.

Weak sources may still be useful. The error is not using them; the error is overstating them.

---

## 17. Testimony, Consultation, and Citizen Input

Citizen and stakeholder inputs may be evidentially relevant even when they are not formally authoritative.

They may help reveal:

- local conditions,
- implementation friction,
- lived effects,
- overlooked harms,
- capture patterns,
- public contestation.

However, such inputs should be classified appropriately and not confused with binding legal or empirical authority.

---

## 18. Human Expert Citations

Human experts must follow the same citation discipline as other executors.

They must not substitute:

- personal confidence,
- status,
- institutional prestige,
- “common knowledge in the field,”

for explicit evidentiary grounding when the claim is material.

Human expertise may improve interpretation, but it does not erase the citation burden.

---

## 19. AI Executor Citations

AI executors require especially strong citation discipline because they are prone to:

- fabricated references,
- generic but unhelpful source mentions,
- false precision,
- source/claim mismatch,
- summary without traceability.

AI-generated citations should therefore be subject to:

- stricter validation,
- source existence checks where feasible,
- claim-support checks where material,
- heightened penalty for fabricated or mismatched references.

---

## 20. Citation Validation

The system should validate citations at multiple levels.

Possible validation checks include:

- source exists,
- source is retrievable,
- citation metadata is coherent,
- cited provision or section exists,
- claim actually matches cited support,
- source jurisdiction is appropriate,
- source is not obviously stale for the claim type,
- source is not duplicated under cosmetic variation.

A packet with invalid citations should not be treated as merely incomplete prose; it is epistemically compromised.

---

## 21. Public Inspectability

### 21.1 Public visibility requirement
The public briefing should preserve enough sourcing structure for meaningful inspection.

### 21.2 Minimum visible elements
At minimum, the public should be able to see:

- key sources,
- source class,
- jurisdiction relevance,
- claim-to-source links for material claims,
- declared unknowns,
- major source conflicts,
- which claims are inferential rather than directly supported.

### 21.3 Restricted publication
Some source details may require protection for privacy, security, or anti-abuse reasons, but restriction should not erase the public’s ability to understand the analytic basis.

---

## 22. Sourcing and Confidence

Source and citation quality must affect confidence.

Relevant effects include:

- strong source quality increasing confidence,
- wrong-jurisdiction sources lowering confidence,
- poor traceability lowering confidence,
- conflict suppression triggering penalty,
- stale law capping confidence,
- independent corroboration improving robustness.

Sourcing is therefore not only documentary. It is computationally and procedurally relevant.

---

## 23. Sourcing and Ranking

Executor ranking should incorporate sourcing behavior.

Examples:

- citation accuracy,
- source quality selection,
- jurisdictional discipline,
- conflict disclosure,
- avoidance of source laundering,
- stability of traceability across cases.

An executor that sounds persuasive but cites poorly should not rank highly.

---

## 24. Review Triggers from Citation Defects

Certain sourcing defects should trigger review, downgrade, or blocking.

Examples:

- fabricated source,
- material claim without support,
- wrong-jurisdiction legal citation,
- current-law claim based on stale source,
- omitted contrary binding source,
- excessive dependence on one weak source,
- public invisibility of load-bearing evidence.

Some defects may justify immediate packet rejection.

---

## 25. Citation Style and Identifier Discipline

The system should adopt a stable citation style or citation schema so that outputs remain comparable and machine-checkable.

The style should support, where relevant:

- source identifier,
- title,
- issuer or author,
- publication date,
- retrieval date,
- jurisdiction tag,
- source class,
- pinpoint reference,
- canonical URL or record locator,
- language tag.

Formatting flexibility is acceptable, but the underlying metadata discipline should remain stable.

---

## 26. Audit Requirements

The audit layer should preserve at least:

- source register,
- source classifications,
- claim-to-source mappings,
- validation results,
- stale-source flags,
- conflict-source flags,
- inference markers,
- public-visibility decisions,
- citation-related review triggers,
- later corrections or invalidations.

The sourcing layer itself must be auditable.

---

## 27. Failure Modes

Common failure modes include:

- decorative citation without real support,
- citing broad websites instead of the relevant clause or record,
- source laundering through secondary summaries,
- wrong-jurisdiction citation used as if binding,
- stale law treated as current,
- hiding contrary sources,
- many citations with low independence,
- human authority replacing documentation,
- AI hallucinated references,
- public publication that hides the real evidence basis.

The system should explicitly defend against these patterns.

---

## 28. Minimum Canonical Fields

A citation and sourcing record should minimally support fields such as:

- `source_id`
- `source_class`
- `title`
- `issuer_or_author`
- `publication_date`
- `retrieved_at`
- `jurisdiction_tag`
- `authority_level`
- `currency_status`
- `verifiability_status`
- `canonical_locator`
- `language`
- `claim_links[]`
- `contradiction_links[]`
- `validation_checks[]`
- `staleness_flags[]`
- `independence_notes`
- `public_visibility_status`
- `notes`

Detailed field typing belongs in `SCHEMAS.md`.

---

## 29. Canonical Operating Rules

1. Citations are part of civic legitimacy, not decorative references.  
2. Material claims must be traceable to sources, inference notes, or declared assumptions.  
3. Source admissibility is contextual and claim-dependent.  
4. Wrong-jurisdiction sourcing is a serious defect.  
5. Stale sources must not masquerade as current authority.  
6. Conflicting relevant sources must be surfaced, not hidden.  
7. Human and AI executors are both bound by citation discipline.  
8. Citation defects must affect confidence, routing, and ranking.  
9. Public scrutiny must be able to inspect the evidentiary basis of civic analysis.  
10. The system must prefer traceable truthfulness over polished opacity.

---

## 30. Closing Principle

> A civic claim should not gain force because it was spoken fluently.  
> It should gain force only when its sources are fit, its support is visible, and its limits are honestly declared.
