# MINIMUM_VIABLE_PNYX

## 1. Purpose

This document defines the minimum deployable form of the Pnyx system.

Its purpose is not to replace the full constitutional and architectural specification.
Its purpose is to define the smallest runtime that can:
- preserve the core anti-capture logic,
- remain publicly legible,
- operate with limited institutional capacity,
- avoid pretending to be a mature constitutional order before it is one.

The minimum viable form is therefore a **bootstrap runtime profile**, not a reduced philosophy.
The constitution remains broader than the first deployment.

**Principle:**
Do not shrink the constitution. Shrink the initial runtime.

---

## 2. What Minimum Viable Means

A minimum viable Pnyx is the smallest system that can:
- accept public proposals,
- classify them conservatively,
- route all non-trivial matters through plural review,
- publish auditable civic packets,
- record decisions in an append-only audit stream,
- prevent the most dangerous forms of silent procedural drift,
- expose bootstrap debt instead of hiding it.

It does **not** need, on day one, to implement every advanced safeguard in full institutional depth.
It must only implement enough of them to avoid becoming a disguised discretionary operator system.

---

## 3. Core Distinction

The system must distinguish between:

### 3.1 Constitutional Core
The principles and invariants that must already hold.

### 3.2 Bootstrap Runtime
The limited first deployment profile.

### 3.3 Full Civic Runtime
The mature target system with wider role separation, stronger automation, broader audit tooling, richer cryptographic privacy, and larger-scale public participation.

The bootstrap runtime is legitimate only if it declares itself incomplete.

---

## 4. Minimum Non-Negotiable Elements

The following are mandatory even in the smallest viable deployment.

### 4.1 Public Proposal Intake
The system must accept proposals through a public, logged intake path.
No hidden proposal channel may exist for governance-affecting matters.

### 4.2 Conservative Classification
Every proposal must be classified before review.
Ambiguity must escalate upward, not downward.
If classification confidence is low, the stronger review path applies.

### 4.3 Non-Trivial Proposal Protection
Every non-trivial proposal must go through a plural skill panel.
No single-skill output may carry deliberative legitimacy.

### 4.4 Minimum Deliberative Quorum
The full constitutional minimum remains a panel of five relevant skills for non-trivial proposals.
Bootstrap may reduce scope, but not silently redefine legitimacy.
If the deployment cannot support five relevant skill roles, the affected proposal class must remain advisory-only or deferred.

### 4.5 Public Civic Packet
Every non-trivial proposal must produce a public packet containing at minimum:
- proposal summary,
- strongest case in favor,
- strongest case against,
- unknowns,
- evidence sufficiency note,
- capture-risk note,
- reversibility note,
- proposed route,
- applicable epoch and thresholds.

### 4.6 Append-Only Audit Record
Every material action must be recorded in a canonical append-only audit stream.
No silent edits, silent reclassification, silent override, or silent emergency action may be permitted.

### 4.7 Bootstrap Debt Visibility
Every bootstrap shortcut, concentration, missing safeguard, or temporary dependency must be explicitly declared as bootstrap debt.

### 4.8 Future-Only Meta Change
No threshold, timing window, routing rule, classification rule, or framework parameter may be changed for an already-active case.

### 4.9 Emergency Non-Normalization
Emergency action must remain exceptional, public, time-bounded, and review-triggering.
No emergency path may become the hidden normal operating system.

### 4.10 Public Challenge Path
There must be a visible path to challenge classification, routing, panel integrity, packet sufficiency, and governance-affecting operator actions.

### 4.11 Participation Floor
Even the smallest viable deployment must support:
- a `ParticipationPlan` for every non-trivial proposal, proportional in depth to classification,
- a per-case `ParticipationAudit` before decision-readiness, with depth proportional to classification (full audit for high-impact proposals per the classification binding in `../99_Reference/CORE_V03_RECONCILIATION.md` conflict 2),
- a verifiable participation receipt or equivalent verification that a contribution was recorded (`CivicReceipt` or a declared equivalent).

Open participation alone must not be represented as proof of representative participation.
Sortition, delegation of attention, and cryptographic uniqueness proofs are deferred per §5.1 and §5.5, not required by this floor.

---

## 5. What Can Be Deferred

The following may be partially implemented, simplified, or staged later, provided the simplification is explicit and logged.

### 5.1 Advanced Cryptographic Privacy
Zero-knowledge proof infrastructure, unlinkable credential systems, advanced verifier networks, and privacy-preserving uniqueness proofs for civic actions may be staged later if the bootstrap system clearly declares the temporary privacy debt and narrows scope accordingly.
Where uniqueness proofs are deferred, the `CivicReceipt` requirement of §4.11 may be satisfied by a logged, challengeable equivalent, and the gap must be entered as bootstrap debt.

### 5.2 Full Audit Projection Suite
The bootstrap system does not need the full family of audit views.
It needs only the minimum readable projections required for civic visibility and incident review.

### 5.3 Broad Institutional Differentiation
In bootstrap, founders, operators, auditors, stewards, and reviewers may not yet be fully differentiated institutions.
This concentration must be declared, logged, minimized, and scheduled for review.

### 5.4 Registry Depth
Skill registry governance may begin with a narrower admission process than the mature system, provided provenance, dependency, and single-provider debt are explicit.

### 5.5 Large-Scale Participation Features
Wide-scale federation, sophisticated public replay tooling, and large-scale dispute infrastructure may be deferred.
The following participation mechanisms may also be deferred, each as an explicitly declared bootstrap debt item:
- sortition (`SortitionConfiguration` / `SortitionResult`),
- delegation of attention (`AttentionDelegation`),
- formed participant bodies beyond open participation (`ParticipantBody`, `TargetedInvitation`).

Deferral does not lift the participation floor of §4.11.

### 5.6 Rich Routing Taxonomy
The full routing logic may exist on paper while the runtime initially supports only a narrower operational subset.
For example, the bootstrap runtime may support:
- advisory_only,
- defer_pending_evidence,
- hybrid,
with limited direct state or market execution power.

### 5.7 Economic Subsystem
A deployment may remain civic-only and defer the economic subsystem entirely.
A civic-only deployment must not claim economic compatibility.

A deployment claiming economic compatibility must support, at minimum:
- funding linked to a defined public problem and an execution mandate (`AllocationDecision`, `ReleaseAuthorization`),
- auditable treasury transactions (`FundingReceipt` for every release),
- declared ownership and licensing before any IP-creating execution begins (`LicenseRecord`, `ContributorAgreement`, per `../50_Economics/PUBLIC_IP_MODEL.md`),
- a `PublicReturnReport` closing each material public-value obligation,
- compensation rules separated from judgment authority, including participant compensation independent of position or vote,
- economic conflict disclosures (`EconomicConflictDisclosure`).

This staging rule follows the v0.3 reconciliation (`../99_Reference/CORE_V03_RECONCILIATION.md` §4.5, conflict 11).

---

## 6. Minimum Runtime Profile

The minimum viable runtime should begin with a deliberately narrow deployment profile.

### 6.1 Narrow Scope
The bootstrap system should initially govern only a limited class of matters.
Examples:
- advisory civic prioritization,
- local project ranking,
- public recommendation generation,
- bounded resource allocation within pre-approved limits,
- framework rehearsals without binding constitutional effect.

### 6.2 Limited Binding Power
Not every valid output needs to be immediately binding.
The bootstrap runtime may begin with:
- advisory outputs,
- recommendatory packets,
- soft-binding routing,
- review-triggering flags,
- public scorecards.

### 6.3 Strong Visibility, Small Surface
The initial deployment should prefer:
- high transparency,
- low scope,
- high auditability,
- low irreversible power.

### 6.4 Default to Reversible Decisions
The bootstrap runtime should prefer reversible, bounded, or pilot-like decisions over irreversible constitutional or infrastructure commitments.

---

## 7. Minimum Required Documents for Bootstrap Deployment

A deployment may not claim to be a minimum viable Pnyx unless at least the following documents are present and active:
- GOVERNANCE.md
- ARCHITECTURE.md
- PROTOCOL.md
- THREAT_MODEL.md
- INVARIANTS.md
- CLASSIFICATION.md
- PANEL_SELECTION.md
- AUDIT_LOG.md
- PACKET_FORMAT.md
- OPERATOR_TRUST_MODEL.md
- CONSTITUTIONAL_BOOTSTRAP.md
- BOOTSTRAP_PARAMETERS.md

These define the minimum constitutional, procedural, audit, operator, and bootstrap trust constraints.

---

## 8. Minimum Audit Views

The bootstrap runtime may simplify the audit projection layer, but it must still provide at least:

### 8.1 Public Timeline View
Shows the chronological flow of the case.

### 8.2 Decision Record View
Shows the proposal, classification, panel, packet, route, threshold, and final outcome.

### 8.3 Operator Action View
Shows governance-affecting operator actions, emergency actions, and any runtime-affecting interventions.

### 8.4 Incident / Challenge View
Shows challenges, escalations, invalidations, and unresolved integrity concerns.

Anything less risks producing unreadable truth or hidden intervention.

---

## 9. Minimum Skill Profile

The bootstrap runtime does not need a large or mature skill ecosystem.
It does need enough structured plurality to avoid interpretive monopoly.

The minimum useful panel role set for non-trivial matters is:
- rights / constitutional,
- adversarial critique,
- implementation / feasibility,
- resource / economic,
- anti-capture / audit.

A bootstrap system may rely on a narrow provider base only if:
- provider concentration is explicit,
- illusory diversity is prohibited,
- substitution plans are recorded,
- single-provider dependency is logged as constitutional debt,
- affected proposal classes are scope-limited accordingly.

---

## 10. Minimum Classification Profile

The classification layer must at minimum distinguish:
- trivial,
- non_trivial,
- policy,
- governance,
- constitutional,
- emergency.

The bootstrap runtime may simplify subcategories, but it may not remove the distinction between ordinary and framework-affecting change.

Any uncertainty must move the proposal upward into stronger scrutiny.

---

## 11. Minimum Meta-Governance Profile

The bootstrap runtime does not need a rich meta-governance apparatus, but it must enforce these minimum rules:
- no active-case retuning,
- no retroactive threshold changes,
- no silent framework edits,
- no founder self-ratification,
- no uncited constitutional shortcuts,
- no perpetual bootstrap by neglect.

All framework changes must bind to a future epoch.

---

## 12. Minimum Operator Controls

Even in bootstrap, the runtime may not rest on pure trust.
At minimum it must have:
- logged governance-affecting deployments,
- separation between ordinary operation and framework-affecting action,
- dual acknowledgement for framework changes where institutionally possible,
- visible emergency activation,
- post-action review trigger,
- prohibition of shadow runtime or undeclared model substitution.

If strong dual control is not yet institutionally available, the absence must be logged as bootstrap debt.

---

## 13. Minimum Emergency Profile

The minimum viable system should treat emergency powers as a last-resort containment path.

At minimum:
- emergency scope must be explicitly declared,
- emergency action must automatically create a review case,
- emergency status must expire by default,
- emergency outputs must not silently convert into ordinary governance,
- emergency use frequency must be publicly visible.

Where technical auto-expiry is unavailable, the system must declare this as emergency enforcement debt.

---

## 14. Minimum Public Legibility

The bootstrap system must be understandable by non-experts at the level of action, not merely at the level of aspiration.

A participant should be able to answer:
- what is being proposed,
- how it was classified,
- who reviewed it,
- what the strongest disagreement is,
- what remains unknown,
- what route is proposed,
- what changed,
- how to challenge it.

If the public cannot answer these questions, the system is not yet minimally viable.

---

## 15. What Minimum Viability Rejects

The minimum viable system rejects the following shortcuts:
- founder self-legitimation,
- silent parameter tuning,
- single-skill legitimacy,
- unreadable audit cores with no public view,
- undeclared operator intervention,
- hidden provider concentration,
- emergency normalization,
- rhetorical participation without challenge rights,
- claiming constitutional maturity before institutional maturity exists.

---

## 16. Failure Conditions

A bootstrap deployment is not minimum viable if any of the following hold:
- non-trivial proposals pass through single-skill or opaque review,
- framework-affecting changes occur without future-epoch binding,
- operator interventions are materially unlogged,
- public packets omit dissent or unknowns,
- emergency actions do not trigger review,
- bootstrap debt is concealed,
- classification ambiguity defaults downward,
- the system claims legitimacy it has not yet earned.

---

## 17. Exit Criteria from Minimum Viable Profile

The bootstrap runtime should evolve only when the following are materially true:
- broader role differentiation exists,
- the challenge path is active in practice,
- audit views are usable at growing scale,
- skill supply is no longer effectively monopolistic,
- bootstrap debt is shrinking rather than accumulating,
- emergency use is rare and review-complete,
- public participation is not purely symbolic,
- framework revisions occur through the declared meta loop.

Expansion of scope without expansion of safeguards is invalid growth.

---

## 18. Relationship to Full System

Minimum viable Pnyx is not a separate ideology.
It is a staged deployment profile of the same constitutional system.

It exists so that:
- anti-capture design can begin before full institutional maturity,
- bootstrap concentration can be made explicit rather than denied,
- public reasoning can begin under constraint,
- the system can learn without pretending to be complete.

The mature system may become more powerful only by becoming more distributed, more challengeable, and less dependent on founding concentration.

---

## 19. Closing Principle

The first duty of a minimum viable Pnyx is not to simulate a finished democracy.
Its first duty is to create a small, visible, challengeable, auditable civic machine that can survive long enough to hand more of itself to the public.

A bootstrap runtime is acceptable only if it openly behaves like unfinished constitutional infrastructure.
