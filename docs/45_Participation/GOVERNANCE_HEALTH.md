# GOVERNANCE_HEALTH.md

*A governance health monitoring layer for detecting slow erosion and systemic deterioration*

**Status:** Normative
**Layer:** 45_Participation

> **Promotion note.** This document was promoted from `99_Reference/` (status "Proposal") to the normative participation layer, and extended with delegation-concentration, compensation-distribution, and sortition-integrity metrics, by decision of the v0.3 reconciliation (`99_Reference/CORE_V03_RECONCILIATION.md`, conflict 12). The metric set defined here stands; the external Core v0.3 draft's §33 did **not** replace it.

---

## 1. Why This File Exists

Not all governance failure appears as acute emergency.

Some failure appears as slow erosion:

- Decisions taking longer quarter over quarter, with no single breach large enough to trigger alarm
- A small set of actors consistently clustering proposals just below classification thresholds
- Affected communities gradually participating less, while a narrow expert class dominates
- Emergency powers invoked repeatedly for routine matters, each invocation individually defensible
- Challenges becoming ritual rather than meaningful, with low overturn rates

These patterns don't trigger emergency enforcement. They are individually defensible. Their cumulative effect is the hollowing out of governance while the formal structure remains intact.

PNyx therefore requires a **governance-health layer** distinct from emergency enforcement. Emergency enforcement handles acute crises. Governance health handles slow deterioration.

---

## 2. Governance Health Dashboard

The system **SHALL** maintain a public governance-health dashboard, updated at least weekly, with the following metrics.

Dashboard metrics are periodically published into `GovernanceHealthReport` artifacts (see §2.9).

### 2.1 Latency Metrics

| Metric | Description |
|--------|-------------|
| **Average proposal resolution time by class** | Mean time from submission to decision, broken down by classification tier (trivial, non-trivial, policy, governance, constitutional) |
| **Latency budget breach rate** | Percentage of proposals exceeding the latency budget for their classification tier |
| **Sustained latency deterioration flag** | Triggered when mean resolution time increases by more than 20% quarter-over-quarter for two consecutive quarters |

### 2.2 Classification Metrics

| Metric | Description |
|--------|-------------|
| **Classification challenge rate** | Percentage of proposals receiving at least one classification challenge |
| **Classification overturn rate** | Percentage of challenged classifications that are modified or overturned |
| **Near-threshold classification clustering** | Count and percentage of proposals classified within 10% of a threshold boundary, tracked per actor |
| **Layer distribution** | Distribution of proposals across constitutional / governance / policy / non-trivial / trivial tiers over time |

### 2.3 Participation Metrics

| Metric | Description |
|--------|-------------|
| **Actor concentration by proposal volume** | Herfindahl-Hirschman Index (HHI) of proposal submission, measured quarterly |
| **Participation concentration** | HHI of active deliberation participants (those who submit reviews, challenges, or evidence) |
| **Affected-community participation rate** | For proposals with identified affected communities, the participation rate of members of those communities |
| **First-time participant retention** | Percentage of first-time participants who submit a second proposal or review within 90 days |

### 2.4 Emergency Metrics

| Metric | Description |
|--------|-------------|
| **Emergency invocation frequency** | Count of emergency routing activations per quarter |
| **Emergency renewal rate** | Percentage of emergency actions that are renewed at expiry |
| **Emergency-to-normal ratio** | Ratio of emergency-routed proposals to ordinarily-routed proposals over time |
| **Repeat emergency category** | Count of proposals in the same classification category that have been emergency-routed more than twice |

### 2.5 Output Quality Metrics

| Metric | Description |
|--------|-------------|
| **Decision-summary publication rate** | Percentage of decided proposals with a published 200-word decision summary within 7 days of decision |
| **Unknowns field population rate** | Percentage of packets where the unknowns field contains more than the minimum required text |
| **Minority view substantiveness** | Average length and specificity of minority view fields across decided proposals |
| **Challenge outcome distribution** | Distribution of challenge outcomes (upheld / partially upheld / overturned / withdrawn) |

### 2.6 Delegation Concentration Metrics

| Metric | Description |
|--------|-------------|
| **Delegate attention share** | For each delegate, the share of active `AttentionDelegation` records held per civic scope, measured monthly (see `45_Participation/ATTENTION_AND_REACH.md`) |
| **Delegation concentration index** | HHI of active attention delegations per scope |
| **Delegation renewal-without-inspection rate** | Percentage of delegations renewed without the delegator having accessed any delegate output during the prior term |
| **Delegation revocation rate** | Percentage of delegations revoked before expiry, tracked per delegate |

### 2.7 Compensation Distribution Metrics

| Metric | Description |
|--------|-------------|
| **Participant-compensation distribution** | Distribution of `ParticipantCompensation` spending (see `50_Economics/TREASURY.md`) across participant groups, scopes, and participation modes |
| **Compensation-position correlation** | Statistical correlation between compensation received and positions taken (votes, review stances) by compensated participants; any material correlation is a capture failure signal (see `50_Economics/FUNDING_MODEL.md`) |
| **Compensation coverage rate** | Percentage of eligible participants (sortition bodies, affected groups, long reviews) who actually received declared compensation |
| **Compensation claim burden** | Median time and steps from participation to compensation settlement |

### 2.8 Sortition Integrity Metrics

| Metric | Description |
|--------|-------------|
| **Pre-draw registry churn** | Rate of additions/removals in the eligible-population registry in the window preceding a selection draw, versus baseline churn (see `45_Participation/SORTITION.md`) |
| **Draw verification failure rate** | Percentage of sortition draws whose public verification fails or cannot be completed |
| **Opt-out and replacement rate** | Percentage of selected participants who decline or are replaced, per draw and per demographic stratum where lawfully measurable |
| **Repeat-selection anomaly** | Frequency of the same individuals or clusters being selected across draws beyond statistical expectation |

### 2.9 Governance Health Report

Dashboard metrics are published, at least quarterly and per civic scope, into a `GovernanceHealthReport` artifact (schema in `90_Information/SCHEMAS.md`; entity in `90_Information/DATA_MODEL.md`).

Reports are **diagnostic evidence, not judgment**:

- A report **MUST NOT** reduce governance health to a single legitimacy score.
- Metrics inform structural review (§4); they do not themselves constitute or replace public judgment.
- Reports preserve links to the underlying read models and measurement methods so results are independently reproducible.

---

## 3. Erosion Triggers

The following patterns trigger **automatic structural review** rather than individual incident response:

### 3.1 Sustained Latency Deterioration

**Trigger**: Mean resolution time increases by more than 20% quarter-over-quarter for two consecutive quarters, for any classification tier above trivial.

**Response**: Structural review of the bottleneck layer. Is the delay in classification, panel assembly, deliberation, or decision execution? Is the delay structural or incidental?

**Exit condition**: Latency returns to within 10% of the prior baseline for two consecutive quarters.

### 3.2 Classification Gaming Pattern

**Trigger**: Any set of actors comprising fewer than 5 entities accounts for more than 40% of near-threshold proposals, and this concentration persists for two consecutive quarters.

**Response**: Structural review of classification boundary definitions. Are the boundaries correctly placed? Are sophisticated actors exploiting a structural loophole?

**Exit condition**: Actor concentration falls below 30% for two consecutive quarters, or a classification rule amendment is ratified through the meta-governance process.

### 3.3 Challenge Ritualization

**Trigger**: Classification challenge rate exceeds 15% but overturn rate remains below 5% for three consecutive quarters.

**Response**: Structural review of the challenge path. Are challenges being used for delay rather than genuine correction? Are challenge review panels exhibiting capture?

**Exit condition**: Challenge overturn rate rises above 10%, or challenge rate falls below 10%, for two consecutive quarters.

### 3.4 Affected-Community Withdrawal

**Trigger**: Affected-community participation rate for a defined community falls below 25% of the participation rate of non-affected participants for three consecutive quarters.

**Response**: Structural review of accessibility. Is the participation process too complex for affected communities? Are there barriers to entry that don't apply to technically sophisticated participants?

**Exit condition**: Affected-community participation rate rises to within 50% of non-affected participation for two consecutive quarters.

### 3.5 Summary Publication Lapse

**Trigger**: Decision-summary publication rate falls below 80% for two consecutive quarters, or average summary publication delay exceeds 14 days.

**Response**: Structural review of the summarization process. Is summarization being treated as optional? Is the summary quality adequate?

**Exit condition**: Publication rate exceeds 90% and average delay under 10 days for two consecutive quarters.

### 3.6 Procedural Abandonment

**Trigger**: More than 10% of proposals in a given classification tier are withdrawn, deferred, or routed to advisory_only status within 30 days of submission for two consecutive quarters.

**Response**: Structural review of whether the system is appropriately scoped. Are proposals being submitted that should route elsewhere? Is the classification routing driving abandonment?

**Exit condition**: Abandonment rate falls below 5% for two consecutive quarters.

### 3.7 Delegation Concentration

**Trigger**: A single delegate holds more than 20% of active attention delegations in a civic scope, or the five largest delegates together hold more than 50%, for two consecutive quarters.

**Response**: Structural review of the delegation layer. Is concentration driven by genuine trust, by informational dependence, or by capture? Responses may include delegation caps, shorter expiry, mandatory disclosure of delegate interests, or rotation rules (see `45_Participation/ATTENTION_AND_REACH.md`).

**Exit condition**: No delegate exceeds 15% and the top five hold under 40% for two consecutive quarters, or a delegation-policy amendment is ratified through the meta-governance process.

### 3.8 Compensation-Position Correlation

**Trigger**: A statistically material correlation between participant compensation and positions taken persists across two consecutive measurement periods, or compensation flows to a participant group deviate more than 3× from its participation share without declared differentiation.

**Response**: Structural review of compensation administration. Is compensation being used to purchase agreement (a direct violation of the anti-vote-buying firewall — see `50_Economics/FUNDING_MODEL.md`)? Is undeclared differentiation occurring?

**Exit condition**: Correlation falls below the material threshold for two consecutive periods, or the compensation policy is amended and re-ratified.

### 3.9 Sortition Integrity Degradation

**Trigger**: Pre-draw registry churn exceeds 3× baseline in the window before any selection draw; or draw verification failure occurs at all; or repeat-selection anomaly exceeds statistical expectation at 99% confidence.

**Response**: Structural review of the sortition pipeline, prioritizing the eligible-population registry — the primary capture surface (see `45_Participation/SORTITION.md`). Draw verification failure additionally suspends reliance on the affected draw until resolved.

**Exit condition**: Registry churn returns to baseline, all draws in a quarter verify publicly, and no repeat-selection anomaly persists.

---

## 4. Structural Review Lane

Structural review is neither ordinary governance nor emergency response. It is a distinct process:

### 4.1 Characteristics

| Property | Description |
|----------|-------------|
| **Time bound** | 30-day review window, extendable once by 15 days with public justification |
| **Public** | All review inputs, deliberations, and conclusions are public |
| **Adversarially reviewed** | Review panel must include adversarial_critique |
| **System-focused** | Reviews system conditions, not individual proposal outcomes |
| **Non-retrospective** | Structural review does not reverse individual decisions; it addresses patterns |

### 4.2 Review Triggers

Structural review is initiated automatically by erosion triggers (§3) or manually by:

- Any operator with documented concern
- Petition of 50 or more civic token holders
- Request from an affected community

### 4.3 Review Output

The structural review produces:

1. **Condition assessment**: Is the observed pattern a genuine governance health issue or an artifact of measurement?
2. **Root cause analysis**: What structural factor is producing the pattern?
3. **Remediation recommendation**: What specific changes would address the root cause?
4. **Accountability note**: If the pattern resulted from operator action or omission, who is accountable and what is the remediation?

### 4.4 Relationship to Emergency Enforcement

| Property | Emergency Enforcement | Structural Review |
|----------|---------------------|-------------------|
| Trigger | Acute crisis | Slow erosion pattern |
| Timescale | Immediate | 30-45 days |
| Scope | Specific proposals | System conditions |
| Reversal | Can reverse decisions | Cannot reverse decisions |
| Override | Can bypass normal governance | Cannot bypass normal governance |
| Review | Ex-post review required | Constitutes the review |

---

## 5. Anti-Normalization Rule

**Emergency enforcement tooling SHALL NOT be the only mechanism for detecting governance failure.**

This rule means:

1. **Emergency is not governance health.** A system that handles acute crises well but has no slow-deterioration detection is still failing. The absence of emergency invocations does not mean governance is healthy.

2. **Governance health monitoring is mandatory.** The dashboard in §2 must be maintained even when emergency enforcement is inactive. Deterioration that doesn't trigger emergency can still trigger structural review.

3. **Slow erosion is the more dangerous failure mode.** Acute crises are visible. Gradual hollowing out is invisible until the structure collapses. The governance health layer exists precisely because emergency enforcement cannot see slow erosion.

4. **Normal governance includes health monitoring.** Governance health is not a separate subsystem. It is a required function of ordinary governance operation. Treating it as optional creates the conditions for the slow emergency it is designed to prevent.

---

## 6. Relationship to Other Documents

This document addresses the failure mode identified in `99_Reference/STRUCTURAL_CRITIQUE.md` §7 ("Emergency Resolves the Wrong Crisis") and `99_Reference/POST_MITIGATION_Critique.md` §6 ("Emergency Enforcement Reality").

It complements:

- `95_Emergency/EMERGENCY_ENFORCEMENT.md` — handles acute crises
- `80_Runtime/READ_MODELS.md` — provides the technical infrastructure for health dashboards
- `99_Reference/STRUCTURAL_CRITIQUE.md` — documents the slow erosion problem this layer addresses
- `45_Participation/PARTICIPATION_MODEL.md` — the participation machinery whose quality §2.3 and §2.6–2.8 measure
- `45_Participation/SORTITION.md` — sortition pipeline and registry integrity rules behind §2.8/§3.9
- `45_Participation/ATTENTION_AND_REACH.md` — delegation-of-attention rules behind §2.6/§3.7
- `50_Economics/TREASURY.md`, `50_Economics/FUNDING_MODEL.md` — participant-compensation rails and failure signals behind §2.7/§3.8

---

## 7. Implementation Notes

### 7.1 Dashboard Access

The governance health dashboard is a public read model. No credentials are required to view it. It is designed for accessibility:

- Summary view: one page showing key metrics with trend indicators
- Detail view: full metric breakdown by classification tier, actor, and time period
- Raw data view: downloadable data for independent analysis

### 7.2 Trigger Calibration

The thresholds in §3 are initial calibrations. They should be adjusted based on operating experience:

- Thresholds that trigger too often create review fatigue
- Thresholds that trigger too rarely miss genuine deterioration
- The calibration process itself should be subject to structural review

### 7.3 Structural Review Cost

Structural reviews consume deliberation capacity. Excessive structural review is itself a governance health problem. The system should track structural review frequency and treat a rising review rate as a potential health metric.

---

*Purpose: Address the slow erosion failure mode that emergency enforcement cannot detect*
*Companion to: EMERGENCY_ENFORCEMENT.md, STRUCTURAL_CRITIQUE.md, PARTICIPATION_MODEL.md*
*Status: Normative*
*Last Updated: July 2026*
