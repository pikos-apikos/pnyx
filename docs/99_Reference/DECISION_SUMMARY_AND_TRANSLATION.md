# DECISION_SUMMARY_AND_TRANSLATION.md

*A first-class translation layer for bridging the gap between auditability and accessibility*

---

## 1. Principle

**Auditability is not the same as accessibility.**

The PNyx system produces extraordinarily detailed audit trails: append-only event logs, cryptographic integrity chains, structured civic packets with semantic fields, 30+ specialized read views. These outputs are precise. They are not accessible.

A motivated citizen with weeks of training can reconstruct what happened and why from the audit log. A tired citizen after a long shift cannot. This is not a documentation problem. It is a structural problem. The system's complexity is necessary for its precision, but that precision is only available to those who can absorb the complexity.

The gap between what the system produces (legible data) and what citizens understand (comprehensible meaning) is the gap this document addresses.

---

## 2. Decision Summary Requirement

Every decided proposal **MUST** produce a short public summary.

This is not optional. It is not a nice-to-have. It is a mandatory output of the decision process, as structural as the full civic packet.

### 2.1 Required Fields

Every decision summary MUST contain the following fields:

```json
{
  "summary": {
    "proposal_title": "string — plain-language title of the proposal",
    "what_was_decided": "string — one to three sentences stating the decision",
    "core_disagreement": "string — one to two sentences on the central point of contention",
    "strongest_objection": "string — one to two sentences on the most significant objection that was not adopted",
    "what_remains_unknown": "string — one to two sentences on what the panel explicitly identified as uncertain or not fully evaluated",
    "what_happens_next": "string — two to three sentences on the immediate consequences and next steps",
    "decision_date": "ISO 8601 date",
    "decision_outcome": "enum: adopted / rejected / deferred / advisory_only",
    "classification_tier": "enum: trivial / non_trivial / policy / governance / constitutional"
  }
}
```

### 2.2 Publication Requirement

| Classification Tier | Summary Publication Deadline |
|--------------------|---------------------------|
| Constitutional | Within 48 hours of decision |
| Governance | Within 72 hours of decision |
| Policy | Within 5 days of decision |
| Non-trivial | Within 7 days of decision |
| Trivial | Within 7 days of decision |

If the deadline is missed, the delay is logged publicly as a governance health metric.

### 2.3 Attribution

The summary MUST be attributed to a named individual or role, not to an anonymous process. The attester confirms the summary accurately reflects the decision record. This creates accountability for summarization quality.

### 2.4 Challenge

A decision summary can be challenged on accuracy grounds:

- Does the summary distort the decision outcome?
- Does it misrepresent the core disagreement?
- Does it suppress the strongest objection?
- Does it conceal unknowns that were documented in the full packet?

Challenges to summaries are reviewed by the same panel that produced the original deliberation, or by a designated summary-review skill.

---

## 3. Plain-Language Constraint

The decision summary must be understandable by a motivated non-specialist.

### 3.1 Readability Standard

The summary MUST meet the following constraints:

| Constraint | Requirement |
|------------|-------------|
| Reading level | No higher than 8th-grade equivalent (Flesch-Kincaid) |
| Sentence length | Average under 25 words |
| Jargon | No technical terms without plain-language definitions in the summary itself |
| Abbreviations | First use must spell out the full term |
| Legal references | Must include plain-language paraphrase |

### 3.2 What This Means Practically

A citizen reading a decision summary should be able to answer:

- Did the proposal pass or fail?
- What were the people on each side arguing about?
- What were the most important concerns raised?
- What does this mean for me or my community?
- Where can I go if I want to learn more or challenge this?

A summary that requires reading the full packet to answer these questions is not a summary. It is a table of contents.

### 3.3 Compliance Checking

The summary SHOULD be checked for plain-language compliance before publication. This can be automated (reading level scores, jargon detection) or human-reviewed (volunteer civic reviewers).

A summary that fails plain-language checks is not published until corrected. This is a quality gate, not a suggestion.

---

## 4. Translation Layer

The system SHOULD support civic translators — community intermediaries who convert decisions into forms accessible to specific affected communities.

### 4.1 What Civic Translation Is

Civic translation is the adaptation of decision summaries (and full packets where needed) into:

- Community-specific language and framing
- Local context and implications
- Cultural resonance and relevant examples
- Simplified routing for community-specific concerns
- Alternative format outputs (audio, video, visual summaries)

Civic translation is distinct from summarization. A summary compresses the full record. A translation adapts it for a specific audience.

### 4.2 Why Translation Is Structural

The same decision affects different communities differently. A budget prioritization decision means different things to an urban resident, a rural resident, a small business owner, and a service provider. A translation layer ensures that community-specific implications are surfaced, not lost in aggregate analysis.

This addresses the participation inequality problem documented in `POLITICAL_ECONOMY.md`. If PNyx outputs are only accessible to people who already understand the system, the system reproduces existing power structures. Translation is the structural mechanism for making the system accessible to people who haven't yet learned its grammar.

### 4.3 Translation Budget

The treasury SHOULD allocate a dedicated translation partition:

```
TranslationFund:
  purpose: civic translation and accessibility
  sources: public donations, institutional grants, conditional public funding
  governance: community translator applications reviewed by a translation panel
  accountability: translators publish their credentials, community feedback, and correction rates
```

Funded translators are not employees. They are community members with demonstrated reach who apply for project-specific or ongoing translation grants.

### 4.4 Translator Credentials

Civic translators are not required to be neutral. They are required to be attributed. Every translation must disclose:

- Who performed the translation
- What community or audience the translation is targeted at
- What biases or affiliations the translator holds relevant to the subject matter
- Whether the translation was funded and by whom

This creates accountability without requiring neutrality. A translator with a known bias produces a more honest output than a translator pretending to have no perspective.

### 4.5 Community-Specific Outputs

Funded translators MAY produce:

| Output Type | Description |
|-------------|-------------|
| **Community summaries** | Decision summaries adapted for specific community language and context |
| **Local impact analyses** | What the decision means for specific neighborhoods, industries, or demographics |
| **FAQ documents** | Common questions about the decision, answered in plain language |
| **Video/audio summaries** | Multilingual spoken summaries for communities with low literacy |
| **Visual formats** | Infographics, flowcharts, or maps showing impacts geographically |

These outputs exist in addition to, not instead of, the canonical decision summary. The canonical summary is the authoritative record. Community translations are derivative works that serve accessibility.

---

## 5. Summary Failure as Governance Failure

**If decisions are formally auditable but not publicly understandable, the system is not fully functioning.**

This is a structural principle, not an aspirational statement.

### 5.1 What "Not Fully Functioning" Means

A PNyx deployment where:

- Decision summaries are published late or not at all
- Summaries fail plain-language checks
- Civic translation is unfunded or absent
- Citizens cannot answer the five questions in §3.2

...is a PNyx deployment that has failed its accessibility obligation. It is producing auditability without accessibility. This is the "legitimacy theater" failure mode the system warns against in `THREAT_MODEL.md`.

### 5.2 Measuring the Failure

The decision-summary publication rate and the summary publication delay are tracked in the governance health dashboard (per `45_Participation/GOVERNANCE_HEALTH.md §2.5`). Chronic summary failure is an erosion trigger.

But the erosion trigger is a lagging indicator. Summary failure is a governance failure the moment it becomes systemic, not the moment a metric crosses a threshold.

### 5.3 Why This Is Not Optional

The specification often distinguishes between "must" and "should" and "may." For decision summaries:

- Publication of summaries: **MUST** — hard requirement
- Plain-language compliance: **MUST** — quality gate
- Civic translation: **SHOULD** — structural recommendation with budget allocation required

The MUST items are non-negotiable. If they cannot be met, the system has a governance health failure that must be addressed before expansion.

### 5.4 The Accountability Chain

Responsibility for decision summaries flows through:

1. **Panel members** — produce the deliberation record that summaries are derived from
2. **Summary author** — converts deliberation record into plain-language summary
3. **Attester** — confirms summary accurately reflects deliberation record
4. **Governance health monitor** — tracks publication rate, delay, and challenge frequency
5. **Structural review** — triggered by chronic summary failure

If summaries are failing, accountability is traced up this chain to identify where the breakdown is occurring.

---

## 6. Relationship to Other Documents

This document addresses the accountability theater problem identified in `STRUCTURAL_CRITIQUE.md §6` and the participation inequality analysis in `POLITICAL_ECONOMY.md §7`.

It complements:

- `45_Participation/GOVERNANCE_HEALTH.md` — tracks summary publication rate and accessibility metrics as governance health indicators
- `80_Runtime/READ_MODELS.md` — defines the CitizenPacketView projection that surfaces summaries
- `90_Information/PACKET_FORMAT.md` — defines the full packet format of which summaries are a constrained subset
- `STRUCTURAL_CRITIQUE_SUGGESTIONS.md` — proposes 200-word decision summaries as first-class outputs (this document formalizes that proposal)

---

## 7. Open Questions

1. **Who is the summary author?** The specification requires attribution but does not specify whether summaries are produced by skill providers, operators, or a dedicated summary role. Operating experience should determine the best fit.

2. **What is the minimum viable translation budget?** The specification recommends translation funding but does not specify a minimum. Comparable civic technology projects suggest 5-10% of operating budget is a reasonable starting point.

3. **How is translation quality evaluated?** Translation is inherently interpretive. A community may feel a translation misrepresents them while the translation is technically accurate. Community feedback mechanisms and correction rates should be tracked, but the definition of translation quality remains contested.

4. **What happens when the summary and the full record disagree?** The summary is a compressed representation. Disagreements between summary and full record should be resolved in favor of the full record, with summaries corrected and challenges logged.

---

*Purpose: Formalize decision summaries as mandatory outputs and civic translation as a structural layer*
*Companion to: STRUCTURAL_CRITIQUE.md, 45_Participation/GOVERNANCE_HEALTH.md, POLITICAL_ECONOMY.md*
*Status: Proposal*
*Last Updated: April 2026*
