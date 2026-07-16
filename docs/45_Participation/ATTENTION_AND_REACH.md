# ATTENTION_AND_REACH

**Layer:** 45_Participation
**Status:** Normative
**Origin:** Adopted from PNyx Core v0.3 §21–29 via `99_Reference/CORE_V03_RECONCILIATION.md` (§4.1), hardened per that record's §6.7, §6.10, §6.11
**Depends on:** `50_Economics/POLITICAL_ECONOMY.md` §8 (theory), `30_Classification/CLASSIFICATION.md`, `10_Constitutional/THREAT_MODEL.md` §6.1, `80_Runtime/INVARIANTS.md`, `95_Emergency/EMERGENCY_ENFORCEMENT.md`
**Companions:** `PARTICIPATION_MODEL.md`, `GOVERNANCE_HEALTH.md` (this layer)

---

## 1. Purpose

Speech is not reach.

The right to submit a civic contribution does not include the right to amplification, top placement, repeated exposure, priority routing, or interruption of unrelated civic processes.

`POLITICAL_ECONOMY.md` §8 establishes that attention is a political resource: actors who can set agendas, frame urgency, flood channels, or overwhelm review possess real political leverage without holding office. This document operationalizes that analysis. It defines how civic attention is allocated, how reach is granted and audited, how attention capacity is bounded, and how attention may be delegated without becoming a shadow form of political concentration.

The governing principle:

> Attention is a bounded public resource governed by public rules. It is not engagement fuel.

---

## 2. The Eight Civic Functions

A PNyx implementation MUST distinguish and separately govern:

1. **submission** — placing a contribution into the system;
2. **routing** — directing it to a scope and process;
3. **indexing** — making it findable;
4. **prioritization** — ordering it against competing demands;
5. **recommendation** — suggesting it to specific participants;
6. **notification** — actively interrupting participants with it;
7. **amplification** — extending its audience beyond default visibility;
8. **judgment eligibility** — admitting it as input to a decision.

### 2.1 Separation rule

Possessing one function confers none of the others.

A valid submission is entitled to routing, indexing, and audit presence. It is not entitled to prioritization, recommendation, notification, or amplification. Judgment eligibility is governed solely by standing, classification, and the applicable `JudgmentConfiguration` — never by reach.

### 2.2 No function bundling

No actor, policy, or automated system may be granted a bundle of these functions under a single undifferentiated permission. Permission grants follow `PERMISSIONS.md` conventions: explicit, stage-bound, logged.

---

## 3. Attention Allocation Policy

Each civic scope MUST operate under a declared `AttentionAllocationPolicy`.

### 3.1 Policy properties

The policy MUST be:

- public;
- versioned;
- epoch-bound (see `TREASURY.md` epoch discipline and `INVARIANTS.md` §8 temporal invariants);
- stable for active cases — no mid-case retuning;
- reproducible: any allocation outcome MUST be derivable from the policy version plus declared inputs.

### 3.2 Permitted factors

Allocation MAY consider:

- civic scope;
- classification result (the existing prioritization backbone — `CLASSIFICATION.md`);
- demonstrated impact;
- affected population;
- urgency (only as constrained by §9);
- evidence strength;
- time sensitivity;
- neglected or underrepresented issues;
- legal obligations;
- public-resource exposure;
- random rotation;
- diversity of subject matter;
- remaining attention budget (§5).

### 3.3 Combination rule

A factor list alone is not a policy.

The policy MUST declare either:

- explicit factor weights, or
- a deterministic ordering (lexicographic priority of factors),

such that two implementers applying the same policy version to the same declared inputs reach the same allocation. A policy that permits post-hoc justification of any allocation from an open-ended factor menu is invalid.

### 3.4 Prohibited bases

Allocation MUST NOT be based solely on:

- clicks;
- reactions;
- outrage;
- repetition;
- popularity;
- purchased promotion;
- institutional prestige.

### 3.5 No purchased reach

Financial resources MUST NOT directly or indirectly determine civic priority or amplification. This is the machinery behind the *no purchased civic priority* invariant (`INVARIANTS.md`, economic invariants). Material sponsors and financial interests behind any content receiving reach MUST be disclosed. Purchased amplification routed through third parties (astroturf intermediaries, coordinated inauthentic submission) is a capture attempt under `THREAT_MODEL.md` §5.4 and a failure signal under §11.

---

## 4. Reach Decisions

### 4.1 Definition

A Reach Decision is any material grant of prioritization, recommendation, notification, or amplification beyond a scope's default visibility rules.

A Reach Decision is *material* when it targets a non-trivial proposal, expands audience beyond the originating scope, uses interruption (notification), or is contested. High-impact cases (per the classification binding in `CORE_V03_RECONCILIATION.md` conflict 2) always produce material Reach Decisions.

### 4.2 ReachDecision artifact

Every material Reach Decision MUST produce or reference a `ReachDecision` artifact recording:

- what content received reach;
- civic scope;
- `AttentionAllocationPolicy` version applied;
- reason, in terms of the policy's declared factors;
- duration;
- target audience;
- urgency evidence, where urgency was a factor (§9);
- automated systems involved, with executor provenance;
- appeal or correction path.

Schema: `SCHEMAS.md`; entity: `DATA_MODEL.md`.

### 4.3 Auditability

Material Reach Decisions are first-class auditable events. They MUST appear in the audit trail (`AUDIT_LOG.md`) and be queryable per scope and per period. A reach grant that cannot be traced to a policy version and declared inputs is an invariant violation.

---

## 5. Attention Budget

A civic scope MUST declare an Attention Budget as part of its `ParticipationPolicy` (see `PARTICIPATION_MODEL.md`).

### 5.1 Declared unit

The budget MUST be expressed in at least one declared, measurable unit, such as:

- maximum simultaneous high-priority cases per scope;
- estimated citizen-hours requested per period;
- notification frequency caps per participant per period;
- deliberative-body capacity;
- review-window density limits.

An unquantified budget ("we will be mindful of attention") is not a budget.

### 5.2 Declared owner

The budget is owned by the scope's governance under its `ParticipationPolicy`. Changes to the budget are policy changes: versioned, epoch-bound, prospective only.

### 5.3 Breach consequence

When a scope's budget is exhausted:

- new high-priority activations MUST queue rather than override;
- queueing order follows the `AttentionAllocationPolicy` (§3), not arrival order or pressure;
- the only lawful bypass is the emergency regime (`95_Emergency/EMERGENCY_ENFORCEMENT.md`), which carries its own expiry and ex-post review;
- every breach and every queue event is logged and surfaced in the scope's `GovernanceHealthReport`.

### 5.4 Rationale

More participation requests do not create more democracy. They create fatigue, random judgment, or withdrawal. A scope that floods its citizens is failing even if every individual case is legitimate.

---

## 6. Intake Flooding Defense

Attention can be attacked from the intake side. `THREAT_MODEL.md` §6.1 names spam, duplicate proposal flooding, urgency laundering, and bureaucratic exhaustion as primary intake-layer threats; §5.10 names the Bureaucratic Exhaustion Attacker. Reach governance that ignores intake is half a defense.

### 6.1 Admission rules

Intake MUST apply:

- rate limits per civic credential, declared in the `ParticipationPolicy`;
- duplicate clustering and a merge duty — near-duplicate submissions are consolidated, with all sources preserved and traceable;
- structured-field requirements per `THREAT_MODEL.md` §6.1 controls;
- flood anomaly detection — abnormal submission volume, coordinated timing, or template similarity raises a logged anomaly signal.

### 6.2 Flooding confers nothing

Volume is not evidence and repetition is not reach. A flooded topic MUST NOT gain prioritization, recommendation, notification, or amplification on account of volume. Cross-reference: *no engagement metric as legitimacy* (`INVARIANTS.md`, participation invariants).

### 6.3 Flood versus surge

A genuine surge of distinct affected participants is not flooding. The distinguishing evidence is:

- distinct valid credentials with standing in the affected scope (uniqueness per `CRYPTOGRAPHIC_MODEL.md` nullifiers);
- distinct content rather than template repetition;
- correspondence with an observable triggering event.

A surge so evidenced is a legitimate allocation factor under §3.2 (affected population, demonstrated impact). The distinction MUST be made by declared rule, not moderator intuition, and is challengeable.

### 6.4 Exhaustion attacks

Challenge spam and procedural-objection flooding are handled per `THREAT_MODEL.md` §6.1 (asynchronous batching, stratified cost nullifiers). Anti-flooding controls MUST NOT be tuned so aggressively that they suppress legitimate dissent or first-time participants; false-positive rates are a `GOVERNANCE_HEALTH.md` metric.

---

## 7. Civic Briefs

### 7.1 Briefs, not feeds

A civic scope SHOULD deliver attention through periodic Civic Briefs rather than a continuous engagement feed. A PNyx interface MUST NOT optimize for engagement time, repeated checking, outrage, impulsive reaction, or infinite scrolling.

### 7.2 Brief content

A `CivicBrief` MAY include: what changed; why it matters now; cases needing attention with expected time commitment; open participation opportunities; current decisions; execution changes; unresolved risks; recently recorded outcomes.

### 7.3 Action-class separation

Every item in a brief MUST be labeled as exactly one of:

- **information** — no action requested;
- **invitation** — optional participation opportunity;
- **optional review** — open window, participation useful but not required;
- **required action** — the participant holds a role or membership obligating response;
- **urgent action** — bound by §9 urgency discipline.

### 7.4 Source continuity

Briefs MUST link to canonical artifacts and MUST preserve visible dissent, uncertainty, and unresolved questions. A brief is a projection, never a replacement of the record. Cross-reference: *no summary without source continuity* (`INVARIANTS.md`, participation invariants); redaction rules per `AUDIT_VIEWS.md`.

### 7.5 Bounded sessions

Briefs SHOULD support bounded participation: clear deadlines, explicit "why now" explanations, visible completion states, and reflection periods before judgment where the classification result permits.

---

## 8. Delegation of Attention

A citizen MAY temporarily delegate attention — reading updates, reviewing evidence, following a technical subject, monitoring a mandate, producing recommendations — to a trusted person, group, or declared civic role.

This extends the bounded delegation model of `PERMISSIONS.md` §10 into the attention layer.

### 8.1 Delegation requirements

An `AttentionDelegation` MUST be:

- scope-specific;
- purpose-specific;
- time-limited with automatic expiry;
- revocable at any moment by the delegator;
- auditable by the delegator (the delegate's outputs are inspectable);
- non-transferable unless explicitly permitted at creation.

Permanent or universal delegation MUST NOT be the default and SHOULD NOT be offered as a one-click option.

### 8.2 Attention is not judgment

Delegated attention is not delegated sovereignty.

A delegate may read, summarize, monitor, and recommend. The delegate MUST NOT cast the delegator's civic action unless the applicable `JudgmentConfiguration` (`SCHEMAS.md` §25.2) explicitly permits judgment delegation for that decision — a separate, stronger grant.

### 8.3 Uniqueness firewall

Delegation of attention never consumes the delegator's civic action or nullifier (`CORE_V03_RECONCILIATION.md` §6.10).

Only the delegator's own action, or an explicitly authorized judgment delegation under §8.2, spends the delegator's uniqueness for a given action type, scope, and loop. A delegate's brief, summary, or recommendation has no standing as the delegator's participation. Cross-reference: *no duplicate civic action where uniqueness is required* and *a simulated citizen population is not public participation* (`INVARIANTS.md`).

### 8.4 The delegator retains

- inspection of the delegate's outputs;
- revocation without notice period;
- direct participation at any time, overriding any recommendation;
- change of delegate.

### 8.5 Concentration monitoring

Concentrated delegated attention is agenda-setting power. It MUST be measured, not assumed benign.

- The share of a scope's active delegations held by each delegate is a standing `GOVERNANCE_HEALTH.md` metric.
- A single delegate exceeding a declared concentration threshold in a scope (set in the `ParticipationPolicy`; the bootstrap default and its rationale live in `BOOTSTRAP_PARAMETERS.md`) MUST be flagged in the scope's `GovernanceHealthReport`.
- Policy responses MAY include: delegation caps, shortened expiry for new delegations to that delegate, mandatory disclosure of the delegate's interests, additional review of the delegate's summaries, and rotation prompts to delegators.

Delegation concentration MUST NOT silently become institutional authority.

### 8.6 Misalignment signal

Where delegators subsequently act directly, the divergence between a delegate's recommendations and the delegators' own actions SHOULD be computed as a health signal. A delegate whose recommendations consistently diverge from their delegators' revealed positions is either misaligned or captured; the signal is diagnostic, visible to affected delegators, and never an automatic sanction.

### 8.7 Delegates are not media properties

Aggregating delegated attention MUST NOT confer any of the eight functions of §2 beyond what each individual delegation grants. A delegate with ten thousand delegators has ten thousand reading obligations, not a broadcast license.

---

## 9. Urgency Discipline

> No urgency without a declared reason.

### 9.1 Default

Participation and review windows are set by classification result and the applicable policies. Urgency is not a mood; it is a claim requiring evidence.

### 9.2 Lawful urgency

A reduced window or urgency-based reach grant is lawful only when:

- the urgent condition is declared in the case record;
- supporting evidence is visible;
- the reduction and its extent are logged;
- the grant is time-bound;
- retrospective review is mandatory.

For emergency-eligible cases, the exclusive mechanism is the emergency regime (`95_Emergency/EMERGENCY_ENFORCEMENT.md`), with its structural expiry and ex-post review. Outside that regime, urgency MAY shorten windows only within the bounds the classification result and `ParticipationPolicy` permit, and every such exception is challengeable.

### 9.3 Urgency laundering

Manufacturing urgency to compress scrutiny is a named intake-layer attack (`THREAT_MODEL.md` §6.1). Repeated urgency claims from the same source with weak evidence MUST raise an anomaly signal and MAY reduce that source's urgency credibility under declared policy.

---

## 10. Artifacts and Events

Defined in `DATA_MODEL.md` and `SCHEMAS.md`; events in `EVENT_MODEL.md`:

| Artifact | Role |
|---|---|
| `AttentionAllocationPolicy` | scope-level, epoch-bound allocation rules (§3) |
| `ReachDecision` | audit record of material reach grants (§4) |
| `CivicBrief` | periodic bounded attention delivery (§7) |
| `AttentionDelegation` | scoped, revocable attention delegation (§8) |

Attention-budget declarations live inside the scope's `ParticipationPolicy` (`PARTICIPATION_MODEL.md`); budget breaches, queue events, flood anomalies, concentration flags, and urgency exceptions are logged events surfaced in the `GovernanceHealthReport`.

---

## 11. Failure Signals

The attention layer is failing when:

- reach correlates with spending, prestige, or operator preference rather than declared policy factors;
- purchased amplification appears laundered through third-party intermediaries;
- neglected scopes are systematically starved while active scopes absorb the budget;
- delegation aggregators function as unaccountable shadow media, setting agendas across scopes;
- notification fatigue rises — participation quality falls as notification volume grows;
- urgency exceptions become routine rather than exceptional;
- flood defenses suppress first-time or dissenting participants (false-positive exclusion);
- reach decisions exist that cannot be reproduced from policy plus declared inputs.

Each signal is a `GOVERNANCE_HEALTH.md` concern; persistent signals warrant a structural review of the scope's policies.

---

## 12. Closing Principle

The system does not owe every voice an audience. It owes every voice a fair, inspectable, unpurchasable path to one.
