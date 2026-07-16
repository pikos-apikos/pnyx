# INVARIANTS

## 1. Purpose

This document defines the non-negotiable invariants of the system.
An invariant is a condition that must remain true across all ordinary, bootstrap, emergency, and meta-governance flows.

Invariants are not preferences, heuristics, or defaults.
They are system integrity constraints.

No implementation optimization, political convenience, or procedural shortcut may silently violate them.

---

## 2. Invariant Classes

The system recognizes the following invariant classes:
- legitimacy invariants,
- audit invariants,
- deliberation invariants,
- evidence invariants,
- routing invariants,
- temporal invariants,
- meta-governance invariants,
- emergency invariants,
- anti-capture invariants,
- outcome invariants,
- economic invariants,
- participation invariants.

A violation in any invariant class is a system integrity event.

---

## 3. Legitimacy Invariants

### 3.1 No single-skill legitimacy
No non-trivial proposal may derive civic legitimacy from a single skill output.

### 3.2 Minimum plural review
Every non-trivial proposal must undergo at least the minimum deliberative quorum defined by the current valid framework epoch.
During bootstrap, this minimum is five relevant skills.

### 3.3 Human sovereignty
No model, panel, skill, or orchestrator may become the final sovereign source of legitimacy.
Human civic judgment remains final.

### 3.4 No legitimacy by optimization
Efficiency, prediction quality, speed, or technical coherence do not by themselves create legitimacy.

### 3.5 No simulated participation
A simulated citizen population is not public participation.
Model-generated personas, synthetic surveys, or simulated citizens may not be represented as actual civic participation, and a model-generated consensus is not consent.

### 3.6 No model substitute for absent participation
Model output may not stand in for the participation of people who were absent.
Absence must be recorded as absence, not filled in by machine inference about what the absent would have said.

---

## 4. Audit Invariants

### 4.1 No silent edit
No proposal, packet, routing record, skill output, or audit record may be silently edited after publication or binding use.
Corrections must appear as explicit append events.

### 4.2 Append-only audit trail
Audit records must be append-only at the event level.
Deletion-in-place is forbidden for audit events.

### 4.3 No unlogged override
No override, reroute, suspension, emergency activation, or meta-governance intervention may occur without an audit event.

### 4.4 Packet traceability
Every published packet must be traceable to the proposal version, panel composition, routing record, evidence status, and relevant epoch bindings.

---

## 5. Deliberation Invariants

### 5.1 Structured plurality
Every non-trivial proposal must produce structured plurality rather than a single compressed authority output.

### 5.2 Visible dissent
Meaningful dissent must remain visible in the civic packet and audit trail.
Dissent may not be collapsed into a false consensus summary.

### 5.3 Required class coverage
A valid panel must satisfy the required skill classes defined by the active framework.
A panel missing required class coverage is invalid for final packet publication.

### 5.4 No hidden panel substitution
A skill or panel member may not be silently substituted after panel lock.
Any replacement must trigger explicit revalidation under the panel selection rules.

---

## 6. Evidence Invariants

### 6.1 No certainty laundering
Model fluency, stylistic confidence, or narrative coherence must not be treated as evidence.

### 6.2 Unknown must remain unknown
If the evidence status is unknown or insufficient, the system must say so explicitly.
Unknown may not be rewritten as supported for convenience.

### 6.3 Claim-evidence separation
Claims, inferences, judgments, and evidence must remain distinguishable in packet and audit structures.

### 6.4 No self-validation by model output
A skill output cannot validate itself merely by being internally consistent or repeated across similar models.

### 6.5 No hidden reasoning as provenance
Raw hidden model reasoning is not required and may not be treated as canonical public provenance.
Provenance rests on declared skills, executors, inputs, sources, and auditable outputs — not on chain-of-thought transcripts.

---

## 7. Routing Invariants

### 7.1 No route without justification
No proposal may be routed to market, state, hybrid, advisory_only, or defer_pending_evidence without an explicit routing rationale.

### 7.2 No hidden route drift
A proposal may not silently drift from one execution route to another after packet publication.
Route changes require explicit contest, rerouting, or review events.

### 7.3 Rights and capture hard stops
A route forbidden by active rights protections or anti-capture hard stops is invalid even if operationally convenient.

---

## 8. Temporal Invariants

### 8.1 No active-case retuning
Thresholds, review windows, panel rules, evidence rules, route criteria, or other governance parameters may not be changed ad hoc for an already active case.

### 8.2 Prospective activation only
Framework and parameter changes apply only through future epoch activation rules.
They do not retroactively mutate already bound cases.

### 8.3 Bound timing visibility
Every proposal must expose the timing regime under which it is processed, including applicable review windows, escalation deadlines, and challenge windows.

---

## 9. Meta-Governance Invariants

### 9.1 The loop may revise itself but never bypass itself
Framework-changing proposals must pass through the civic loop under stricter meta-governance safeguards.

### 9.2 No forbidden shortcut
No constitutional, governance, or bootstrap parameter change may be introduced through wording-only, clerical, emergency, or implementation-only channels.

### 9.3 Meta impact disclosure
Every meta proposal must disclose what it changes, what layer it affects, what epochs it binds, and what systemic risks it introduces.

---

## 10. Emergency Invariants

### 10.1 Emergency is exceptional
Emergency pathways must remain narrow, time-bounded, explicitly logged, and reviewable.

### 10.2 No silent normalization of emergency
An emergency path may not silently become a standard governance path.

### 10.3 Mandatory ex post review
Every emergency action must trigger review under the ordinary or meta-governance process defined by the active framework.

---

## 11. Anti-Capture Invariants

### 11.1 No hidden concentration of interpretive power
No single actor, skill provider, model runtime, parameter owner, or orchestration surface may become an opaque choke point for civic reasoning.

### 11.2 Replaceability of cognitive components
Skills and reasoning components must remain replaceable, challengeable, and version-visible.

### 11.3 No monopoly over civic memory
No model or service may become the exclusive runtime of civic memory, routing logic, or public reasoning.

### 11.4 Public reasoning over black-box closure
Where a choice must be made between opaque convenience and auditable public reasoning, the system must prefer auditable public reasoning.

---

## 12. Outcome Invariants

### 12.1 No completed loop without outcome
No proposal may close without an `OutcomeRecord` or a documented inability to determine one.
A decision without outcome tracking is an incomplete civic loop.

### 12.2 No rewriting of expectations
An outcome record must be evaluated against the originally recorded objective and expected result.
The original expectations may not be rewritten to match what happened.

### 12.3 No model as sole monitor
Models may summarize monitoring data.
They may not be the sole authority deciding whether an objective commitment was satisfied.
Long-term monitoring must rest on deterministic, inspectable mechanisms.

---

## 13. Economic Invariants

### 13.1 No personal-data economy
Personal civic data may not become an economic asset of the system.
The system may not fund itself through sale, behavioral exploitation, or undisclosed commercial use of personal civic data.
This binds the economic layer directly; the operator-side surveillance prohibition (`OPERATOR_TRUST_MODEL.md`) is not a substitute for it.

### 13.2 Funding does not purchase governance power
No donor, investor, contractor, licensee, or sponsor acquires civic authority, votes, standing, or policy control by providing resources.
The detailed rules live in `../50_Economics/FUNDING_MODEL.md` and `../50_Economics/TREASURY.md`; this invariant makes them integrity constraints.

### 13.3 No public production without declared ownership
Execution that may create intellectual property may not begin without declared ownership and licensing rules.
Classification and disclosure rules live in `../50_Economics/PUBLIC_IP_MODEL.md`.

### 13.4 No public value without public return
Material value created through public participation, public funding, public authority, or public civic memory must generate an explicit, measurable public return.
The loop is closed by a `PublicReturnReport` (`../90_Information/SCHEMAS.md`, `../90_Information/DATA_MODEL.md`); a return that is asserted but never reported has not been returned.

### 13.5 No hidden treasury movement
Every movement of public funds or revenues must produce a deterministic, auditable record and pass reconciliation under `../50_Economics/TREASURY.md`.
Numbers without reconciliation are theater, not accountability.

### 13.6 No commercial expansion without renewed authority
Economic success does not expand an executor's public mandate.
Any expansion of scope, authority, or resource claim requires renewed civic authorization through the ordinary loop.

### 13.7 No purchased civic priority
Money may not directly purchase reach, amplification, routing priority, notification prominence, or judgment eligibility.
The attention and reach machinery lives in `../45_Participation/ATTENTION_AND_REACH.md`.

---

## 14. Participation Invariants

Participation mechanics live in `../45_Participation/`; these invariants make their core rules integrity constraints.

### 14.1 No open-door claim of equality
Open access may not be represented as proof of equal or representative participation.
"Everyone was allowed to participate" is not evidence that participation was fair.

### 14.2 No high-impact decision without participation audit
Every non-trivial proposal requires a `ParticipationAudit` before decision-readiness, with depth proportional to classification.
High-impact proposals (per the classification binding in `../99_Reference/CORE_V03_RECONCILIATION.md` conflict 2) require a full audit; trivial proposals are exempt via the trivial shortcut.

### 14.3 No reach without a public rule
Material civic amplification, prioritization, or notification must be governed by a declared, inspectable policy and produce an auditable `ReachDecision`.

### 14.4 No urgency without declared reason
Reduced participation or review windows require a declared justification, visible supporting evidence, and retrospective review.
This elevates the bootstrap-phase rule of `../50_Economics/POLITICAL_ECONOMY.md` §20.5 to a standing invariant.

### 14.5 No delegation without scope and revocation
Delegation of attention must be scope-specific, purpose-specific, time-limited, revocable, and auditable by the delegator.
Permanent or universal delegation may not be a default.

### 14.6 No compensation tied to position
Participant compensation may not depend on agreement, vote, expressed position, or the final judgment.
Compensation pays for time and civic work; it may not purchase agreement.
The funding-side counterpart is §13.2.

### 14.7 No duplicate civic action where uniqueness is required
Where policy defines one action per eligible participant, uniqueness must be enforceable — privacy-preserving where the cryptographic layer is deployed (`../40_Identity/CRYPTOGRAPHIC_MODEL.md`).

### 14.8 No summary without source continuity
Civic briefs, translations, and syntheses must preserve links to canonical sources, dissent, and uncertainty.
Simplification may not become distortion by dropping what it summarizes.

### 14.9 No engagement metric as legitimacy
Clicks, reactions, viewing time, or message volume may not be treated as evidence of civic legitimacy.

Deduplication notes: purchased reach is §13.7; funding-based capture is §13.2; simulated participation is §3.5–§3.6; module sovereignty is covered by §3.3 and §9.

---

## 15. Cross-Cutting Binding Rules

### 15.1 Invariants bind all layers
These invariants bind policy, governance, constitutional, bootstrap, emergency, and implementation layers.

### 15.2 Lower layers cannot override higher integrity rules
Implementation detail, UI behavior, model configuration, or operator convenience cannot override an invariant.

### 15.3 Violations are first-class events
Any known or suspected invariant violation must itself be logged as an auditable event.

---

## 16. Violation Handling

When an invariant is violated or credibly suspected to be violated, the system must support:
- detection,
- explicit logging,
- affected artifact identification,
- case invalidation or suspension where required,
- corrective replay or rerun where possible,
- escalation if the violation affects governance or constitutional integrity.

A system that cannot represent its own integrity failures is not governable.

---

## 17. Minimal Canonical Set

The following canonical rules must never be broken:
- no silent edit,
- no unlogged override,
- no single-skill legitimacy,
- no certainty laundering,
- no active-case retuning,
- no hidden route drift,
- no emergency normalization,
- no framework bypass,
- no hidden concentration of interpretive power,
- no completed loop without outcome,
- no personal-data economy,
- no public value without public return,
- no high-impact decision without participation audit,
- no simulated participation presented as real participation.

---

## 18. Closing Principle

The system may evolve, but its evolution must not destroy the conditions that make public judgment legitimate, auditable, and resistant to capture.

Invariants exist so that adaptability does not become disguise for arbitrary power.
