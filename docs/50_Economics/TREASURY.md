# TREASURY

## 1. Purpose

This document defines the treasury subsystem of the civic system.

Its purpose is to operationalize the funding model by specifying:
- how funds are received,
- how funds are partitioned,
- how releases are authorized,
- how reserves are protected,
- how emergency spending is bounded,
- how public return is routed,
- how treasury conduct remains auditable and challengeable,
- and how financial administration does not silently become governance power.

This document complements:
- `FUNDING_MODEL.md`
- `PUBLIC_IP_MODEL.md`
- `AUDIT_LOG.md`
- `AUDIT_VIEWS.md`
- `EMERGENCY_ENFORCEMENT.md`
- `OPERATOR_TRUST_MODEL.md`
- `MINIMUM_VIABLE_PNYX.md`

The treasury is not a sovereign organ.
It is a constrained financial execution layer serving the civic system.

---

## 2. Core Principle

The treasury may sustain the system.
It may not govern the system.

This means:
- money may finance capacity,
- money may support continuity,
- money may enable pilots, challenges, and commons,
- but treasury control may not silently decide civic outcomes,
- donor size may not become political weight,
- financial convenience may not override constitutional boundaries.

Treasury logic must remain subordinate to:
- the values layer,
- the protocol,
- anti-capture doctrine,
- public auditability,
- challengeability,
- and explicit authorization rules.

---

## 3. Treasury Functions

The treasury exists to perform the following functions:

1. receive funds,
2. classify funds,
3. partition funds by purpose,
4. authorize releases,
5. preserve reserves,
6. route revenue from public IP,
7. maintain auditable ledgers,
8. surface readable public finance views,
9. support emergency continuity under strict bounds,
10. preserve exit and migration capacity.

The treasury does not:
- classify proposals,
- determine routing outcomes,
- admit skills,
- approve constitutional interpretation,
- change governance thresholds,
- assign legitimacy.

---

## 4. Treasury Objects

The treasury subsystem should at minimum recognize the following objects:

- `TreasuryPartition`
- `TreasuryAccount`
- `FundingSource`
- `FundingReceipt`
- `RestrictionSet`
- `AllocationDecision`
- `ReleaseAuthorization`
- `PaymentInstruction`
- `ReserveRule`
- `EmergencyTreasuryAction`
- `RevenueRoutingRecord`
- `TreasuryReconciliation`
- `TreasuryChallenge`
- `TreasuryEpoch`

These objects should be canonically represented in the financial audit trail.

---

## 5. Treasury Partitions

The treasury should maintain separate purpose-bound partitions.

At minimum:
- `CoreTreasury`
- `ChallengeTreasury`
- `SkillCommonsTreasury`
- `LocalPilotTreasury`
- `EmergencyReserve`
- `MigrationAndExitReserve`

Additional partitions may exist if explicitly declared and auditable.

Every partition must define:
- purpose,
- allowed inflows,
- allowed outflows,
- release authority rules,
- reserve minimum if applicable,
- concentration thresholds,
- visibility rules,
- cross-partition transfer rules.

Partitioning prevents the financial layer from silently flattening civic priorities.

---

## 6. Treasury Accounts and Custody

Each treasury partition may be implemented through one or more accounts or wallets depending on legal and technical context.

Custody design must answer:
- who can view balances,
- who can initiate transfers,
- who can approve transfers,
- who can reconcile statements,
- who can certify settlement,
- who can rotate credentials,
- who can pause outgoing flows during incident response.

Custody must never collapse into one actor wherever avoidable.

The treasury must prefer:
- multi-party custody,
- auditable signing,
- role separation,
- recoverable control,
- visible account mapping,
- and no shadow accounts.

---

## 7. Allowed Inflows

The treasury may receive inflows from:
- recurring member contributions,
- public donations,
- crowdfunding campaigns,
- grants,
- cooperative dues,
- local pooled funds,
- public procurement income,
- service revenue from non-sovereign tools,
- public IP revenue,
- challenge bounties,
- benchmark support funds,
- lawful reserve yields under strict conditions.

Every inflow must be recorded with:
- source category,
- amount or structured band,
- partition destination,
- applicable restrictions,
- concentration indicators,
- campaign linkage where relevant,
- donor anonymity posture where relevant,
- settlement reference.

No inflow may be received into the treasury without audit traceability.

---

## 8. Restricted and Unrestricted Funds

The treasury must distinguish at least between:

### 8.1 Unrestricted Funds
General-purpose support within the constitutional and partition rules.

### 8.2 Purpose-Restricted Funds
Funds limited to:
- a named partition,
- a campaign purpose,
- a pilot,
- a benchmark,
- a challenge pool,
- a support function,
- a public infrastructure artifact.

### 8.3 Invalid Restrictions
Restrictions that attempt to control:
- governance outcome,
- classification result,
- routing result,
- registry admission,
- challenge suppression,
- emergency privilege,
- constitutional interpretation.

Invalid restrictions make the restricted condition unenforceable and may make the inflow rejectable.

---

## 9. Allocation Logic

Allocation is the act of moving treasury capacity from general or partitioned balance into an approved use envelope.

An allocation decision must state:
- source partition,
- destination purpose,
- amount or band,
- justification,
- authorizing rule,
- whether it is baseline, campaign, milestone, reserve, or emergency linked,
- expected public return,
- expected review surface,
- challenge window if applicable.

Allocation is not the same as spending.
It is a prior decision that prepares bounded spending capacity.

---

## 10. Release Logic

A release is the authorized execution of a treasury outflow.

Every release should include:
- `ReleaseId`
- source partition
- destination recipient or account class
- amount
- release reason
- linked allocation decision
- authorization set
- settlement method
- whether it is one-time or milestone-based
- supporting documentation reference
- audit linkage

No release may occur without a linked authorization chain.

---

## 11. Authorization Model

Treasury releases must follow role-separated authorization.

At minimum, the following roles should remain distinct where feasible:
- requester,
- reviewer,
- approver,
- executor,
- reconciler,
- auditor.

No single actor should both:
- request and approve,
- approve and reconcile,
- reconcile and close a challenge,
- execute and certify treasury correctness.

Bootstrap may have thinner separation,
but every concentration must be declared as treasury governance debt.

---

## 12. Spending Classes

Treasury outflows should be classified into spending classes such as:

- `CoreOperations`
- `PublicVisibility`
- `ChallengeAndAppeals`
- `SkillDevelopment`
- `SkillEvaluation`
- `TranslationAndAccess`
- `LocalPilotSupport`
- `BenchmarkCommons`
- `MigrationAndExit`
- `LegalDefense`
- `EmergencyContainment`
- `MaintenanceAndHosting`
- `ContributorCompensation`
- `ParticipantCompensation`
- `PublicIPStewardship`

Every class should have:
- eligibility rules,
- documentation expectations,
- spending review profile,
- visibility expectations.

This prevents “operations” from becoming a bucket for anything convenient.

`ParticipantCompensation` is distinct from `ContributorCompensation`.
It pays for civic participation time — not for work products.

It covers, for civic participants such as sortition bodies, invited affected groups, and monitoring participants:
- attendance,
- preparation,
- evidence review,
- deliberation,
- care expenses,
- transport,
- lost working time.

Participant compensation flows through the same release-authorization and `FundingReceipt` rails as every other outflow,
and each payment references a `ParticipantCompensationRecord`
(see `45_Participation/PARTICIPATION_MODEL.md`).

Participant compensation must be independent of the participant's position, vote, or agreement,
and independent of the final decision.
Compensation pays for time and civic work.
It must not purchase agreement.

---

## 13. Treasury and Public IP Revenue

Revenue from public IP must enter the treasury through explicit routing rules.

Every such revenue event must record:
- source artifact,
- public IP classification,
- licensing posture,
- revenue channel,
- gross amount,
- fees or deductions where relevant,
- net amount,
- destination partitions,
- contributor compensation share if any,
- maintenance share if any,
- public-return share.

Public IP revenue must not be routed informally.

---

## 14. Treasury and Challenge Capacity

The treasury must protect challenge capacity.

This means:
- challenge infrastructure must have dedicated funding visibility,
- challenge and appeal pathways must not depend entirely on operator goodwill,
- the treasury must not silently starve critical challenge functions,
- budget convenience must not weaken contestability.

A system that funds operators but not challengers is financially captured even if the ledgers are neat.

---

## 15. Reserve Policy

The treasury should maintain reserves for:
- operational continuity,
- emergency containment,
- migration and exit,
- public visibility continuity,
- critical incident recovery,
- legal defense tied to system integrity,
- core challenge continuity.

Each reserve should define:
- target minimum,
- allowed uses,
- replenishment logic,
- release threshold,
- visibility requirements,
- depletion warnings.

A reserve is not idle excess.
It is anti-fragility.

---

## 16. Cross-Partition Transfers

Transfers between partitions should be exceptional and explicit.

Every cross-partition transfer must state:
- source partition,
- destination partition,
- reason,
- temporary or permanent nature,
- restoration plan if temporary,
- why normal partition funding was inadequate,
- why the transfer does not undermine the purpose of the source partition.

Cross-partition transfers must not become a hidden way to raid challenge or exit capacity.

---

## 17. Emergency Treasury Actions

Emergency treasury actions are allowed only under the emergency enforcement rules.

They may include:
- temporary payout freeze,
- reserve release,
- compromised channel shutdown,
- urgent migration payment,
- critical visibility infrastructure preservation,
- identity continuity spending,
- containment of unlawful treasury diversion.

Emergency treasury actions may not:
- become open-ended discretionary pools,
- reclassify ordinary spending as emergency convenience,
- bypass incident traceability,
- silently alter partition rules.

Every emergency treasury action must be:
- incident-linked,
- bounded,
- expiring,
- review-linked,
- restoration-aware.

---

## 18. Treasury Epochs

Treasury rules should be epoch-bound where needed.

A `TreasuryEpoch` may define:
- active partition structure,
- donor concentration thresholds,
- reserve targets,
- release rules,
- public disclosure posture,
- campaign matching rules,
- public IP routing tables.

Epoch changes must be:
- prospective,
- auditable,
- challengeable,
- never active-case retuned.

Financial rules are governance levers.
They must not be changed midstream for convenience.

---

## 19. Reconciliation

Treasury reconciliation is mandatory.

Reconciliation should verify:
- receipts against actual settlement,
- releases against authorizations,
- balances against expected partition totals,
- reserve minimums,
- routing correctness,
- unresolved settlement anomalies,
- cross-partition transfer correctness,
- donor concentration flags.

Reconciliation must occur on a declared schedule.

A treasury that publishes numbers without reconciliation publishes theater.

---

## 20. Treasury Challenges

Treasury decisions must be challengeable.

Treasury challenges may concern:
- invalid restriction acceptance,
- suspicious donor concentration,
- improper partition use,
- opaque allocation,
- unauthorized release,
- reserve misuse,
- emergency abuse,
- public IP revenue misrouting,
- hidden cross-partition transfers,
- contributor compensation conflicts.

A treasury challenge must preserve:
- the disputed action,
- supporting records,
- challenge reasoning,
- review path,
- resolution status,
- corrective action if required.

---

## 21. Public Finance Views

Treasury behavior must be visible through readable public and auditor views.

At minimum, the public should be able to inspect:
- inflow categories,
- partition balances or bands,
- major allocation classes,
- challenge funding visibility,
- reserve posture,
- donor concentration indicators,
- public IP revenue summaries,
- emergency treasury usage,
- unresolved treasury challenges.

Readable views must remain linked to canonical traces.

---

## 22. Privacy and Disclosure Balance

Treasury transparency must balance privacy and anti-capture.

For example:
- small contributors may be disclosed in structured anonymous bands where appropriate,
- large concentration-relevant contributors require stronger visibility,
- legally sensitive data may be protected,
- but secrecy must not hide influence concentration.

Privacy rules must never become a shell for donor opacity.

---

## 23. Donor and Patron Risk Controls

The treasury must actively detect and surface:
- dominant donor dependency,
- source-class dependency,
- repeated strategic conditionality,
- campaign bundling that simulates plurality,
- vendor subsidy hiding inside service discounts,
- contributor influence claims tied to funding size.

A lawful patron may still create constitutional risk.

Where concentration rises, the treasury may trigger:
- warnings,
- cooling review,
- intake caps,
- partition restrictions,
- stronger disclosure,
- public dependency notices.

---

## 24. Treasury Metrics

The system should track treasury health metrics such as:
- share of recurring small contributions,
- donor concentration ratios,
- reserve coverage length,
- challenge-funding ratio,
- public IP revenue share,
- local pilot funding ratio,
- cross-partition transfer frequency,
- emergency treasury action frequency,
- unresolved reconciliation anomalies,
- dependency on top funding source.

Metrics do not replace judgment.
They make structural drift legible.

---

## 25. Treasury Failure Signals

The following indicate likely treasury capture or instability:

- one funder dominates core continuity,
- challenge capacity is repeatedly underfunded,
- reserves are drained into routine operations,
- cross-partition transfers become normal,
- emergency treasury actions become habitual,
- finance views are readable but non-reconcilable,
- public IP revenue disappears into undifferentiated operations,
- no one can explain how the treasury would survive one major withdrawal,
- allocation logic systematically favors operators over challengers,
- treasury complexity becomes understandable only to insiders.

Repeated failure signals require redesign, not just fundraising.

---

## 26. Bootstrap Treasury Profile

Bootstrap should favor:
- few partitions, clearly defined,
- low burn,
- visible reserve discipline,
- many small recurring contributions where possible,
- simple public finance views,
- strict challenge funding floor,
- minimal emergency treasury surface,
- explicit declaration of any major donor dependence.

Bootstrap should avoid:
- excessive financial engineering,
- opaque fiscal complexity,
- staff-heavy fixed obligations before demand is proven,
- dependence on one sponsor treated as normal,
- using treasury scale as a substitute for civic legitimacy.

Bootstrap finance should be boring, visible, and survivable.

---

## 27. Governance Boundaries

The treasury must never silently absorb governance roles.

The treasury may not:
- select panels,
- decide classification outcomes,
- change review windows,
- choose market/state routing,
- override public packets,
- weaken challenge rights,
- suppress audit visibility,
- declare constitutional necessity based on available money.

Financial scarcity is real.
It must be surfaced explicitly, not smuggled into governance logic.

---

## 28. Cross-References

This document imposes requirements on:

### 28.1 FUNDING_MODEL
Must define the normative treasury boundaries and source classes.

### 28.2 PUBLIC_IP_MODEL
Must define public IP revenue routing and stewardship-linked flows.

### 28.3 AUDIT_LOG
Must preserve canonical financial events.

### 28.4 AUDIT_VIEWS
Must expose readable treasury projections.

### 28.5 EMERGENCY_ENFORCEMENT
Must bound emergency treasury actions.

### 28.6 OPERATOR_TRUST_MODEL
Must constrain custody and authorization concentration.

### 28.7 MINIMUM_VIABLE_PNYX
Must define the minimum bootstrap treasury surface.

---

## 29. Closing Principle

A democratic treasury should be financially competent without becoming politically sovereign.

Its job is to:
- receive support,
- preserve continuity,
- fund commons,
- protect challenge,
- route public return,
- and keep the civic system materially alive.

Its failure mode is to become the place where money quietly decides what politics only appears to decide.

The treasury must therefore remain:
- partitioned,
- bounded,
- role-separated,
- reserve-aware,
- auditable,
- challengeable,
- and constitutionally subordinate.
