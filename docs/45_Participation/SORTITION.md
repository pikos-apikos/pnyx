# SORTITION

## 1. Purpose

This document defines how participant bodies are formed by verifiable random selection.

Sortition exists to place bounded civic responsibility in the hands of ordinary participants
when open participation alone would be dominated, unrepresentative, or capturable.

This document specifies:
- when sortition should be considered,
- the integrity requirements of the eligible-population registry,
- randomness and verifiability requirements,
- stratification constraints,
- the `SortitionConfiguration` and `SortitionResult` artifacts,
- replacement, opt-out, and support rules,
- failure signals,
- how a scope may lawfully decline to use sortition.

Sortition selects participant bodies.
It does not select skill panels.
Panel assembly is defined in `20_Protocol_Core/PANEL_SELECTION.md`,
and the participant body must never be confused with the skill panel
(see `PARTICIPATION_MODEL.md`).

Adopted from external Core v0.3 §16.4 under `99_Reference/CORE_V03_RECONCILIATION.md` §4.1,
with the hardening recorded there (§6.8).

---

## 2. Design Stance

Random selection is not automatically legitimate.

A draw is only as trustworthy as:
- the list it draws from,
- the randomness it draws with,
- the rules that shape the outcome after the draw,
- the support given to those selected.

The central claim of this document is:

**A sortition over a gameable roll is theater.**

Most sortition attacks do not touch the random number.
They touch the registry, the stratification rules, or the replacement chain.
This document therefore treats the eligible-population registry as the **primary capture surface**
and the draw mechanics as the easier, secondary problem.

---

## 3. Definition

A **sortition** is a protocol-governed random selection of a participant body from a declared eligible population, bound to:
- a specific proposal or standing civic function,
- a specific civic scope,
- a specific framework and parameter epoch,
- a committed registry snapshot,
- a declared selection method,
- a declared mandate, term, and judgment authority.

A sortition is not an informal lottery.
It is a protocol object with:
- a configuration artifact published before the draw,
- a result artifact published after the draw,
- public verifiability of the path between them,
- challengeability at every stage.

---

## 4. When Sortition Should Be Considered

Sortition SHOULD be considered when:
- open participation in a scope is repeatedly dominated by a narrow group,
- representative diversity materially affects legitimacy,
- concentrated interests may capture open participation channels,
- a bounded deliberative body must examine evidence in depth,
- ordinary participants should carry bounded civic responsibility rather than delegate it entirely.

Sortition MAY form all or part of a participant body.
Hybrid bodies (sortition plus targeted invitation plus open channels) are defined in `PARTICIPATION_MODEL.md`.

Sortition is NOT required for every proposal.
Whether a case uses sortition is declared in its `ParticipationPlan`,
proportional to classification (see `PARTICIPATION_MODEL.md` and `CORE_V03_RECONCILIATION.md` conflict 2).

---

## 5. The Eligible-Population Registry

### 5.1 The registry is the primary capture surface

Whoever controls who is *in the pool* controls the outcome distribution of every draw,
regardless of how honest the randomness is.

The registry therefore carries integrity obligations at least as strong as the draw itself.

### 5.2 Declared eligibility criteria

The eligible population MUST be defined by declared criteria bound to:
- civic scope,
- standing rules per `40_Identity/IDENTITY_AND_MEMBERSHIP.md`,
- the applicable framework and parameter epoch.

Eligibility criteria MUST be published before enrollment is evaluated.
Undeclared or retroactively adjusted criteria invalidate the selection.

### 5.3 Append-only change log

Every registry change — enrollment, removal, eligibility change, correction — MUST be recorded in an append-only log with:
- the rule under which the change was made,
- the actor or process that made it,
- the time of the change.

Silent registry edits are a protocol violation.

### 5.4 Pre-draw snapshot commitment

Before any randomness is revealed, the registry MUST be frozen into a snapshot
and a content-hash commitment to that snapshot MUST be published
(commitment object per `40_Identity/CRYPTOGRAPHIC_MODEL.md` §6.2).

The draw is valid only against the committed snapshot.
A draw whose snapshot commitment was published after seed revelation is invalid.

### 5.5 Registry challengeability

The registry MUST be challengeable in both directions:
- **exclusion challenge**: a participant claims wrongful omission,
- **inclusion challenge**: a participant or auditor claims wrongful presence (ineligible entries, duplicates, synthetic identities).

Challenge standing may be proven privately per `CRYPTOGRAPHIC_MODEL.md` §5.6.
Challenge outcomes MUST be logged against the registry change log.

### 5.6 Selection-window freeze

Registry changes made inside a declared selection window are invalid for that draw.
They take effect only for subsequent snapshots.

The selection window — from snapshot commitment to result publication — MUST be declared in the `SortitionConfiguration`.

### 5.7 Anomaly signals

The following MUST be treated as integrity anomalies and surfaced to audit
(and to sortition-integrity metrics in `GOVERNANCE_HEALTH.md`):
- bulk enrollment shortly before a snapshot,
- unusual eligibility churn preceding a selection window,
- removals concentrated in an identifiable group,
- repeated correction of the same entries across draws,
- registry growth inconsistent with the scope's population.

Anomalies do not automatically invalidate a draw,
but an uninvestigated anomaly blocks certification of the result
(see §9.4).

---

## 6. Randomness Requirements

### 6.1 Accepted mechanisms

The random draw MUST use a mechanism whose output is:
- unpredictable before the commitment phase completes,
- unbiasable by any single party after commitments are made,
- reproducibly verifiable by the public afterward.

Acceptable constructions include commit-reveal over multiple independent contributors
(built on `CRYPTOGRAPHIC_MODEL.md` §6.2 commitments)
and verifiable-random-function schemes,
subject to the accepted-proof-system decisions of `CRYPTOGRAPHIC_MODEL.md` §24.

### 6.2 Seed governance

No single party — including the operator — may control the seed.

The `SortitionConfiguration` MUST declare:
- the seed sources,
- the contributor set and its independence basis,
- the commitment and reveal schedule,
- the fallback rule if a contributor withholds a reveal.

A withheld reveal MUST NOT allow the withholding party to bias the outcome;
mechanisms MUST fail toward re-run, not toward discretionary seed substitution.

### 6.3 Public verifiability

Any observer MUST be able to verify the draw from public data alone:
- the registry snapshot commitment (§5.4),
- the revealed seed material,
- the declared algorithm and parameters,
- the published result.

Verification MUST NOT require access to private witness data,
hidden member records,
or operator infrastructure.

---

## 7. Stratification and Balancing

### 7.1 Stratification is a secondary capture surface

Stratification (balancing by declared attributes) can be legitimate —
and it can quietly re-introduce the selective control that randomness removed.

### 7.2 Constraints

Stratification and balancing rules, if used:
- MUST be declared in the `SortitionConfiguration` before the snapshot commitment,
- MUST state the attributes used and the justification for each,
- MUST NOT use attributes that cannot be stated publicly,
- MUST be themselves challengeable,
- MUST NOT be altered between snapshot and result.

A stratified draw whose strata definitions changed mid-window is invalid.

### 7.3 No perfect-mirror requirement

Stratification does not promise demographic mirroring,
and the absence of perfect mirroring is not by itself a defect
(see `PARTICIPATION_MODEL.md`, Participation Audit).
The obligation is declared method, not statistical perfection.

---

## 8. SortitionConfiguration Artifact

A `SortitionConfiguration` MUST be published before the registry snapshot commitment and MUST declare:

- proposal or civic function the body serves,
- civic scope,
- eligible-population definition and its eligibility criteria reference,
- registry snapshot commitment (hash) once frozen,
- selection window (§5.6),
- selection method, algorithm, and parameters,
- seed governance per §6.2,
- body size and quorum,
- stratification or balancing rules per §7, or their explicit absence,
- replacement rules (§10.2),
- opt-out rules (§10.1),
- privacy protections for selected participants (§11),
- compensation reference (`ParticipantCompensationRecord` policy, see `PARTICIPATION_MODEL.md` and `50_Economics/TREASURY.md`),
- accessibility support commitments,
- term, mandate, and judgment authority of the resulting body,
- verification instructions.

Schema: `90_Information/SCHEMAS.md`; entity: `90_Information/DATA_MODEL.md`.

---

## 9. SortitionResult Artifact

### 9.1 Required content

A `SortitionResult` MUST record:
- the `SortitionConfiguration` it executes,
- the registry snapshot commitment used,
- the revealed seed material and its verification data,
- the draw proof or reproduction procedure,
- the selected set — pseudonymous where the configuration's privacy rules require,
- the decline/replacement chain (§10.2) in draw order,
- the resulting `ParticipantBody` reference,
- verification instructions executable by a member of the public.

### 9.2 Immutability

A published `SortitionResult` is immutable.
Corrections follow the suite's append-only correction rules
(`90_Information/AUDIT_LOG.md`); the original result remains visible.

### 9.3 Linkage

The result MUST be linked from the proposal record and the `ParticipationPlan`
so that the body's origin is inspectable wherever the body's outputs appear.

### 9.4 Certification

A result may be certified for use only when:
- the draw verifies against the committed snapshot,
- the selection window contained no invalid registry changes,
- open registry challenges material to the draw are resolved,
- anomaly signals (§5.7) are investigated or explicitly risk-accepted with rationale.

---

## 10. Opt-Out, Decline, and Replacement

### 10.1 Freedom to decline

Selection is an invitation to bounded responsibility, not conscription.

A selected participant MUST be able to decline:
- without penalty,
- without loss of standing,
- without public exposure of the reason.

### 10.2 Replacement rules

Replacements MUST come from the same verified draw order
(a ranked overflow of the same snapshot and seed),
not from discretionary substitution.

Each replacement MUST be recorded in the decline/replacement chain of the `SortitionResult`.

A replacement chain that exhausts the drawn overflow triggers a re-run under a new configuration,
not manual completion of the body.

### 10.3 Decline-rate visibility

Aggregate decline rates — not individual reasons — MUST be visible to the Participation Audit,
because systematic decline is itself a barrier signal
(time poverty, distrust, inadequate compensation; see `50_Economics/POLITICAL_ECONOMY.md`).

---

## 11. Privacy of Selected Participants

Selected participants are exposed to pressure precisely because their judgment matters.

The configuration MUST declare, per body:
- whether members are publicly named, pseudonymous, or named only after the term ends,
- what protections apply against lobbying, harassment, and inducement during the term,
- what the members' own disclosure obligations are (conflicts of interest, per `PARTICIPATION_MODEL.md`).

Pseudonymity of members MUST NOT reduce the public verifiability of the draw (§6.3):
the public verifies that *the process* selected fairly,
not *who* the individuals are.

Standing and uniqueness of selected members may be proven privately
per `CRYPTOGRAPHIC_MODEL.md` §5.1–5.3.

---

## 12. Selected-Participant Support

Random selection alone does not create legitimacy.

A selected participant MUST receive:
- enough time, with participation windows declared in advance,
- accessible information: the civic brief, translations, and evidence access per `PARTICIPATION_MODEL.md`,
- compensation under the declared, position-independent rules
  (`ParticipantCompensationRecord`; anti-vote-buying firewall per `50_Economics/FUNDING_MODEL.md`),
- accessibility support as committed in the configuration,
- freedom to dissent, abstain, and record minority positions.

A body whose members were selected fairly but supported inadequately
is a participation failure and MUST be recorded as such in the Participation Audit.

---

## 13. Failure Signals

The following are sortition failure signals,
feeding `GOVERNANCE_HEALTH.md` sortition-integrity metrics:

- registry anomalies (§5.7) recurring across draws,
- draws that cannot be independently verified,
- seed contributors repeatedly withholding reveals,
- stratification rules changing between draws without governance rationale,
- decline rates high enough that replacement chains routinely exhaust,
- the same participants recurring across bodies at rates inconsistent with the draw,
- selected bodies systematically failing to complete their mandate,
- compensation arriving late or not at all.

Persistent failure signals SHOULD trigger review of the scope's `ParticipationPolicy`,
not silent continuation.

---

## 14. Disabling Sortition

A scope MAY operate without sortition only through an explicit declaration in its `ParticipationPolicy`,
stating:
- that sortition is disabled,
- why,
- what substitute mechanism addresses domination and representativeness risks,
- when the decision will be reviewed.

Silence is not a valid disabling.
An implementation claiming extended participation compatibility MUST support
either verifiable sortition or such an explicit disabling policy
(see `PARTICIPATION_MODEL.md` compatibility tiers).

---

## 15. Bootstrap Constraints

During bootstrap, sortition is deferred debt, not a hidden absence:

- `70_Bootstrap/MINIMUM_VIABLE_PNYX.md` defers sortition machinery; the deferral MUST be recorded as bootstrap debt with a review epoch,
- a bootstrap deployment MUST NOT claim sortition-based legitimacy for bodies formed by convenience or invitation,
- early draws MAY use simpler randomness ceremonies, but §5 registry obligations apply from the first draw — a small honest pool is recoverable, a gamed pool is not,
- bootstrap shortcuts expire per `CRYPTOGRAPHIC_MODEL.md` §19 discipline.

---

## 16. Closing Principle

The random number is the easy part.

Sortition earns legitimacy when the pool is honest,
the draw is public,
the shaping rules are declared,
the selected can afford to serve,
and the ones who decline reveal — in aggregate — where participation still costs too much.
