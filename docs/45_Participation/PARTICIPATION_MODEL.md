# PARTICIPATION_MODEL

## 1. Purpose

This document defines participation as a normative subsystem of the protocol:
who can enter a civic process,
how the process is designed to reach them,
how participant bodies are formed,
how participation time is compensated,
how contributions become verifiable,
and how participation quality is audited before any decision is taken.

It operationalizes the diagnosis of `50_Economics/POLITICAL_ECONOMY.md` §7–8 and §15–16:
open channels amplify the already-advantaged,
attention is a scarce political resource,
and challenge rights without usability are decoration.

This document was introduced by the Core v0.3 reconciliation
(`99_Reference/CORE_V03_RECONCILIATION.md` §4.1)
and repairs the defects of the external v0.3 draft recorded there (§6).

Mechanics for sortition are specified in `SORTITION.md`.
Mechanics for attention, reach, briefs, and delegation are specified in `ATTENTION_AND_REACH.md`.
Governance-health metrics are specified in `GOVERNANCE_HEALTH.md`.

---

## 2. Design Stance

Participation is institutional infrastructure,
not an interface affordance.

It begins when the system decides what enters the process,
where it is routed,
who is informed,
who is invited,
what explanation they receive,
and how much time and support they are given.
It does not begin when a voting button appears.

Participation is a **transversal concern within the single proposal lifecycle**.
It is not a second state machine.
One lifecycle remains sovereign
(`80_Runtime/STATE_MACHINE.md`);
participation obligations attach to its stages and guards.

The statement "everyone was allowed to participate"
must never be treated as proof that participation was fair or representative.
Procedural openness without countermeasures is participation laundering
(`POLITICAL_ECONOMY.md` §7).

---

## 3. Core Principles

### 3.1 Open door is not equality

Open access is a floor, not an achievement.
A participation process must identify who is likely absent
and take declared, auditable measures against identified barriers,
or record why it did not.

### 3.2 Participation forms citizens

The system does not wait for the ideal citizen.
Bounded, supported, repeatable civic practice —
reading a brief,
reviewing evidence,
serving on a body,
verifying an outcome —
is how civic capacity is produced.
Participation design should therefore prefer
bounded responsibility with real support
over unbounded invitations with none.

Participation must not require expert knowledge,
unlimited time,
or institutional confidence
as entry conditions.

### 3.3 Proportionality by classification

Participation-design effort scales with the classification of the proposal
(`30_Classification/CLASSIFICATION.md`):

- **trivial** proposals use the trivial shortcut (`STATE_MACHINE.md` §5)
  and never instantiate participation machinery;
- **non-trivial** proposals require a `ParticipationPlan` and a Participation Audit;
- **high-impact** proposals require the full plan and a full-depth audit.

**High-impact binding** (reconciliation conflict 2):
a proposal is *high-impact* if and only if it is classified `non_trivial`
**and** at least one of the following holds:
- primary layer is `governance` or `constitutional`,
- minimum panel size is 7 or greater,
- `framework_change` is set.

No participation obligation in this layer may hang on an undefined impact predicate.

### 3.4 Participant body is not the skill panel

Skills analyse.
Participants deliberate and judge under the applicable civic rules.

A `ParticipantBody` must never be confused with a `Panel`
(`20_Protocol_Core/PANEL_SELECTION.md`).
A person may belong to both,
but each role and each conflict must be declared.

### 3.5 Participation spans the lifecycle

Participation does not end when a decision opens,
and does not begin there either.
It includes intake, clarification, evidence submission,
public review, challenge,
decision,
and monitoring and outcome verification after execution begins.

The `PARTICIPANT_BODY_READY` canonical state certifies only
the formation of the deliberation and judgment body;
it does not certify participation quality across the lifecycle.
That is the Participation Audit's job.

---

## 4. Participation Modes

A proposal's `ParticipationPlan` may combine multiple modes.

### 4.1 Open participation

Any participant satisfying the applicable scope and standing rules
(`40_Identity/IDENTITY_AND_MEMBERSHIP.md`)
may contribute:
comments, evidence supplements, objections, challenges, lived experience.

Open participation is valuable for surfacing signals and unexpected knowledge.
It must not be assumed to produce a representative body.

### 4.2 Affected-party participation

People or groups materially affected by a proposal
must have a **visible route into the process**.

That route may include:
- direct invitation,
- protected testimony (private challenge submission,
  `40_Identity/CRYPTOGRAPHIC_MODEL.md` §5),
- compensated review,
- accessible briefing,
- dedicated response windows,
- representation through accountable roles.

Affected-party status does not determine the judgment.
It creates a stronger, auditable obligation to hear and preserve relevant experience.
Packets already carry a participation-inequality note
(`POLITICAL_ECONOMY.md` §20.2);
the plan must state how identified affected parties were actually reached.

### 4.3 Targeted invitation

A process may invite specific people or groups whose knowledge,
experience,
or systematic absence is relevant.

Every targeted invitation must produce a `TargetedInvitation` artifact declaring:
- why the invitation was issued,
- what perspective or experience is sought,
- how invitees were identified,
- whether participation is compensated,
- whether the invitee holds any special judgment authority (default: none),
- acceptance, decline, or non-response.

Targeted invitation must not be used to manufacture predetermined consent.
Invitation patterns are an audit surface:
systematically inviting friendly voices is a capture pattern.

### 4.4 Sortition

A participant body may be formed wholly or partly by verifiable random selection.
Requirements, cryptographic binding,
and the eligible-population registry threat model
are specified in `SORTITION.md`.

Sortition should be considered when:
- participation is repeatedly dominated by a narrow group,
- representative diversity matters,
- concentrated interests may capture open participation,
- a bounded deliberative body must examine evidence in depth.

### 4.5 Civic jury and deliberative bodies

A civic jury combines sortition or mixed selection,
open public evidence,
skill-panel outputs,
facilitated deliberation,
and a defined judgment mandate.

Its composition, powers, limitations,
and relationship to the wider public
must be declared in the `ParticipationPlan` before formation begins.

### 4.6 Institutional and role-based participation

Institutions and accountable civic roles may participate
where their responsibilities or knowledge are relevant
(`IDENTITY_AND_MEMBERSHIP.md` §7).

Institutional status must not give claims greater truth value.
Institutional claims pass through the same evidence discipline as any other
(`90_Information/EVIDENCE.md`).

### 4.7 Monitoring participation

Citizens and affected groups may participate after decision through:
- progress review,
- evidence submission against milestones,
- issue reporting,
- outcome evaluation,
- public-return verification (`50_Economics/PUBLIC_IP_MODEL.md` §9).

The `ParticipationPlan` should state which monitoring roles are open
and whether they are compensated.

---

## 5. ParticipationPolicy

A `ParticipationPolicy` defines scope-level participation defaults.

It is epoch-bound
(`70_Bootstrap/BOOTSTRAP_PARAMETERS.md` discipline):
it applies prospectively,
never retunes an active case,
and changes to it are meta-governance events.

A `ParticipationPolicy` declares at minimum:
- civic scope,
- default participation modes by classification tier,
- default participation windows,
- compensation eligibility defaults,
- accessibility baseline,
- civic-translation baseline,
- delegation rules in force (`ATTENTION_AND_REACH.md`),
- uniqueness requirements by action type
  (`CRYPTOGRAPHIC_MODEL.md`, `IDENTITY_AND_MEMBERSHIP.md` §13),
- audit depth defaults.

---

## 6. ParticipationPlan

### 6.1 When required

Every non-trivial proposal must have a `ParticipationPlan`
before panel selection begins.
Producing it is the exit condition of the
`PARTICIPATION_DESIGN_PENDING` internal state
(`STATE_MACHINE.md` §4.1, §4.4).

Trivial proposals never require one.

### 6.2 Required content

A `ParticipationPlan` must define:
- affected civic scopes,
- eligible participant population,
- selected participation modes,
- expected participation barriers
  (time poverty, care responsibilities, disability, language, literacy,
  digital and geographic access, institutional distrust, intimidation),
- known missing or underrepresented perspectives,
- open participation channels,
- targeted invitation rules, if used,
- sortition rules, if used (per `SORTITION.md`),
- compensation rules (per §7),
- accessibility measures,
- civic-translation requirements (per §8),
- delegation rules in force,
- participation windows and expected time commitment,
- judgment authority of each participant group,
- conflict-of-interest handling,
- privacy and standing requirements,
- participation-audit criteria (per §11).

### 6.3 Absorbed concerns

The plan absorbs, as sections rather than separate artifacts:
scope-and-impact mapping,
barrier assessment,
accessibility planning,
and civic-translation requirements
(reconciliation record §5 — separate objects would double bookkeeping
for no audit gain).

### 6.4 Versioning and revision

The plan is versioned and revisable.
It must be revisited when the answers to
"who is affected, who is present, who is absent"
materially change —
including after evidence assembly reveals new affected groups.

The `PARTICIPATION_DESIGNED` canonical state certifies that a
**first complete plan exists**,
not that participation design is finished
(reconciliation record §6.5).
Revisions append;
history is preserved
(`80_Runtime/INVARIANTS.md` no-silent-edit).

---

## 7. Compensated Participation

### 7.1 Rationale

Meaningful participation costs time,
and time has unequal economic cost across the population.
Uncompensated "open" participation is a regressive filter.

### 7.2 What may be compensated

- attendance and preparation,
- evidence review,
- deliberation,
- accessibility needs,
- care expenses,
- transport,
- lost working time,
- monitoring work.

Compensation should be considered especially for:
sortition bodies,
affected groups,
participants with care responsibilities,
economically excluded participants,
and long or technically demanding reviews.

### 7.3 Rules

Compensation rules must be:
- declared in advance in the `ParticipationPlan`,
- equal or transparently differentiated,
- auditable,
- **independent of the participant's position, vote, or agreement**,
- **independent of the final judgment**.

Compensation pays for time and civic work.
It must not purchase agreement.
Correlation between compensation and expressed position
is a capture failure signal
(`50_Economics/FUNDING_MODEL.md`, `GRANTS_AND_BOUNTIES.md`).

### 7.4 Mechanics

Participant compensation is a distinct spending class,
`ParticipantCompensation`,
separate from contributor compensation
(`50_Economics/TREASURY.md`).
It is paid through the existing release-authorization and `FundingReceipt` rails.

Every payment produces a `ParticipantCompensationRecord` linking:
participant (at the permitted disclosure level),
proposal,
participation role,
basis of calculation,
and treasury release reference.

Concrete rates are epoch parameters
(`BOOTSTRAP_PARAMETERS.md`);
they are not set by this document
(reconciliation record §8.3).

---

## 8. Civic Translation and Accessibility

Legal access is not meaningful access when the material cannot be understood.

For every non-trivial proposal,
the citizen-facing surface must include a plain-language rendering
(`80_Runtime/READ_MODELS.md` citizen views).
For high-impact proposals,
the `ParticipationPlan` must state which translations,
formats,
and accessibility measures are provided.

Translation and simplification must preserve links to:
- the canonical artifacts,
- the evidence,
- skill outputs,
- uncertainty,
- dissent.

Simplification must not become distortion.
No summary without source continuity
(`INVARIANTS.md`, participation invariants).
Important information must not exist only in expert or machine-readable form.

---

## 9. Humane Interface Norms

The participation surface must not be optimized for:
engagement time,
repeated checking,
outrage,
impulsive reaction,
or virality.

It should prefer:
- bounded participation sessions,
- low frequency and high significance,
- clear deadlines with explicit "why now" explanations,
- visible unresolved questions,
- reflection periods before judgment where appropriate,
- clear completion states.

**No urgency without a declared reason.**
Compressed participation or review windows are lawful only through
the emergency regime
(`95_Emergency/EMERGENCY_ENFORCEMENT.md`),
with the beneficiary-of-speed disclosure required by
`POLITICAL_ECONOMY.md` §20.5.
The adversarial-review requirement itself is never waived
(reconciliation record, conflict 4).

Engagement metrics —
clicks, reactions, viewing time, message volume —
are never evidence of civic legitimacy.

---

## 10. Participant Body Formation

### 10.1 Body types

A `ParticipantBody` may be:
- the open civic public (default; no formation gate),
- an affected-party group,
- a sortition body (`SORTITION.md`),
- a civic jury,
- an institutional body,
- a monitoring group,
- a hybrid.

### 10.2 Formation gate

When the `ParticipationPlan` requires a **formed body**
(jury, sortition body, invited affected-party body),
the proposal passes through the
`PARTICIPANT_BODY_FORMATION` internal state
between packet publication and public review
(`STATE_MACHINE.md` §4.1).

Its exit guard (MUST-form, per `STATE_MACHINE.md` §4.4) requires:
- required invitations issued and responses recorded,
- sortition completed and verifiable, if used,
- selected participants notified,
- accessibility measures available,
- compensation terms accepted,
- standing proofs available at the required disclosure level,
- conflicts recorded,
- civic briefs and translations available,
- declared body limitations recorded.

Formation does not require every invited person to accept.
It requires transparent completion of the declared formation process.

When no formed body is required,
the state is skipped and the open public is the participant body.

### 10.3 Certification scope

The `PARTICIPANT_BODY_READY` canonical state certifies
**only** that the declared deliberation/judgment body was formed as specified.
It does not certify representativeness
(that is the audit's finding to make)
and does not cover participation in other lifecycle stages.

---

## 11. Participation Audit

### 11.1 When required, and at what depth

A `ParticipationAudit` is required
**before every non-trivial proposal reaches decision readiness**
(`READY_FOR_DECISION` guard, `STATE_MACHINE.md` §4.4;
`20_Protocol_Core/PROTOCOL.md`).

Depth is proportional (reconciliation conflict 13):
- **high-impact** (per §3.3 binding): full audit,
- other non-trivial: lightweight audit
  (participation summary, barrier check, anomaly check),
- trivial: exempt.

### 11.2 Auditor

The audit is performed under a declared
**participation-audit permission class**
(`80_Runtime/PERMISSIONS.md`).

The auditor:
- must be identified as a role (not necessarily as a legal identity),
- must not hold a conflicting role on the same proposal
  (proposer, panel seat, operator executing the case, body member),
- must declare conflicts,
- may be human, hybrid, or model-assisted —
  but a model must not be the sole auditor
  (consistent with `INVARIANTS.md` model-as-sole-monitor prohibition).

### 11.3 What the audit examines

- who was eligible,
- who was informed,
- who participated,
- who was selected and who declined,
- which groups were absent, especially affected groups,
- barriers encountered and measures taken,
- accessibility and translation delivery,
- compensation delivery and independence (§7.3),
- sortition integrity, if used (`SORTITION.md`),
- delegation concentration (`ATTENTION_AND_REACH.md`),
- abnormal participation patterns
  (flooding, brigading, synthetic contributions),
- uniqueness and standing guarantees
  (`IDENTITY_AND_MEMBERSHIP.md` §13),
- whether material perspectives were systematically excluded.

### 11.4 What the audit is not

The audit does not require perfect demographic mirroring.
It requires transparent awareness of participation quality and limitations,
recorded where deciders and the public can see it
before judgment.

Audit findings are diagnostic evidence.
They do not by themselves decide the case,
but an audit finding of material systematic exclusion
is a valid ground for challenge.

### 11.5 Challengeability

Like classification,
the Participation Audit is challengeable:
- for rubber-stamping,
- for auditor conflict,
- for ignored exclusion evidence,
- for wrong depth tier.

A upheld challenge reopens the audit,
not silently,
with history preserved.

---

## 12. Civic Receipt

After a participant performs a material civic action,
the system should issue a `CivicReceipt`.

The receipt allows the participant to verify:
- the action was accepted,
- which proposal received it,
- which action type was recorded,
- which policy applied,
- whether the action remains valid,
- whether it was **included in an aggregate**,
- whether it was corrected, rejected, or superseded.

Lifecycle: `accepted` → `included` (→ `corrected` | `superseded` | `rejected`).

The receipt is built on the receipt/inclusion-proof class of
`CRYPTOGRAPHIC_MODEL.md` §5.

**Disclosure default: private proof, public aggregate.**
The receipt must not reveal how the participant acted
unless the participant chooses disclosure
or the process requires public attribution
(e.g., accountable roles).

Delegation of attention never consumes the delegator's civic action,
nullifier,
or uniqueness budget;
only the delegator's own action does
(reconciliation record §6.10; `ATTENTION_AND_REACH.md`).

---

## 13. Compatibility Tiers

### 13.1 Participation floor (MVP)

A minimum viable deployment must support
(`70_Bootstrap/MINIMUM_VIABLE_PNYX.md`):
- a `ParticipationPlan` for every non-trivial proposal,
- a per-proposal `ParticipationAudit` before decision readiness,
  at proportional depth,
- a verifiable participation receipt **or documented equivalent verification**.

### 13.2 Extended participation compatibility

An implementation claiming extended participation compatibility
must additionally support:
- targeted invitations with `TargetedInvitation` artifacts,
- verifiable sortition, or an explicit policy disabling it,
- compensated participation workflows (§7),
- accessibility planning and civic translation (§8),
- an `AttentionAllocationPolicy` and auditable `ReachDecision`s,
- revocable `AttentionDelegation` with concentration monitoring,
- standing and uniqueness proofs,
- governance-health reporting (`GOVERNANCE_HEALTH.md`).

### 13.3 Deferral is debt, not denial

Deferred items are bootstrap debt:
recorded,
visible,
and scheduled —
never silently treated as out of scope
(`70_Bootstrap/CONSTITUTIONAL_BOOTSTRAP.md`).

An implementation must not claim extended participation compatibility
while any of §13.2 is deferred.

---

## 14. Artifacts and Events

Participation artifacts
(schemas in `90_Information/SCHEMAS.md`,
entities in `90_Information/DATA_MODEL.md`):

| Artifact | Defined by |
|---|---|
| `ParticipationPolicy` | §5 |
| `ParticipationPlan` | §6 |
| `ParticipantBody` | §10 |
| `TargetedInvitation` | §4.3 |
| `SortitionConfiguration`, `SortitionResult` | `SORTITION.md` |
| `CivicBrief` | `ATTENTION_AND_REACH.md` |
| `AttentionAllocationPolicy` | `ATTENTION_AND_REACH.md` |
| `ReachDecision` | `ATTENTION_AND_REACH.md` |
| `AttentionDelegation` | `ATTENTION_AND_REACH.md` |
| `CivicReceipt` | §12 |
| `ParticipantCompensationRecord` | §7.4 |
| `ParticipationAudit` | §11 |
| `GovernanceHealthReport` | `GOVERNANCE_HEALTH.md` |

Participation actions that materially affect public influence —
invitation, selection, body lock, delegation, revocation,
receipt issuance, reach grants, audit publication —
must produce events in the event model
(`80_Runtime/EVENT_MODEL.md`)
and appear in the appropriate read models
(`80_Runtime/READ_MODELS.md`).

---

## 15. Invariants

The following must hold
(canonical statements in `80_Runtime/INVARIANTS.md`):

- open access is never represented as proof of equal or representative participation,
- no high-impact decision without a full Participation Audit,
- no non-trivial decision without a Participation Audit at proportional depth,
- no reach without a public rule,
- no urgency without declared reason and beneficiary disclosure,
- no delegation without scope, expiry, and revocation,
- no compensation tied to position, vote, or outcome,
- no duplicate civic action where uniqueness is required,
- no summary without source continuity,
- no engagement metric as legitimacy,
- a simulated citizen population is never actual participation,
- model output is never a substitute for absent participation.

---

## 16. Failure Signals

Participation-layer capture or decay is indicated by:

- **participation laundering** — open channels cited as fairness
  while the same narrow group dominates every case,
- **audit rubber-stamping** — audits that never find exclusion
  across many heterogeneous cases,
- **invitation skew** — targeted invitations repeatedly favoring
  aligned voices,
- **compensation-position correlation** — compensated participants
  systematically agreeing with the funder or operator,
- **chronic affected-party absence** — affected groups identified in plans
  but absent in outcomes, case after case,
- **fatigue flooding** — more simultaneous participation demands
  than the scope's attention budget supports
  (`ATTENTION_AND_REACH.md`),
- **receipt opacity** — participants unable to verify what happened
  to their contribution,
- **synthetic participation** — model-generated personas or surveys
  entering the record as citizen input.

Each signal is diagnostic input to `GOVERNANCE_HEALTH.md`
and valid grounds for challenge.

---

## 17. Closing Principle

The participation layer exists so that
"the public decided"
cannot be said cheaply.

It does not guarantee that every voice appears.
It guarantees that who appeared,
who was reached,
who was paid,
who was absent,
and who was excluded
are recorded facts —
visible before judgment,
auditable after it,
and never replaced by the assertion that the door was open.
