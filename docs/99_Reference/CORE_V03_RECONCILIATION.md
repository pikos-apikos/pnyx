# CORE_V03_RECONCILIATION

**Status:** Decision record
**Predecessor:** `CORE_V02_RECONCILIATION.md`
**System version produced:** 2.3

---

## 1. Purpose

This document records the reconciliation between the specification suite (System Version 2.2) and an external draft titled **"PNyx Core v0.3"** — the evolved successor of the "PNyx Core v0.2" draft reconciled in `CORE_V02_RECONCILIATION.md`.

The method is unchanged from the v0.2 reconciliation:

- the suite remains authoritative;
- the v0.3 text is deliberately **not** imported;
- contributions the suite lacks are adopted selectively, under suite terminology;
- conflicts are decided and recorded here.

The v0.2 record (§6.2) already stated that any future compact "PNyx Core" document must be a *distillation of the suite, not a parallel spec*. v0.3 is again a parallel spec — it re-litigates several conflicts decided in the v0.2 record. Those decisions are **reaffirmed**, not silently reversed.

---

## 2. Comparison Summary

A four-track comparison (participation; state model and civic loop; specialist intelligence and LLM governance; economics, Civic Commons IP, and governance-of-PNyx) found:

- **v0.3's genuine contribution is Part III: participation as a first-class subsystem.** This answers the suite's own recorded gap (`STRUCTURAL_CRITIQUE.md` §9: no sortition, no compensated participation, no delegated review, no structural feature that changes *who* participates). The suite has the theory (`POLITICAL_ECONOMY.md` §7–8) but no normative mechanics. Nothing in `SYSTEM_PATCH_v1/v2` covers this; Part III is effectively the subject matter of a third system patch.
- Outside participation, v0.3 is a **strict shallow subset** of the suite: its specialist sections (~150 lines) omit everything in the ~7,700-line skills layer that makes specialists governable (admission, ranking, revalidation, derived confidence, citation admissibility, human-expert governance, epistemic failure modes); its economics omits challenge-capacity funding, donor-condition voiding, concentration caps, and treasury operational integrity; it has no classification layer, no emergency machinery, and no bootstrap discipline.
- v0.3's state model is the 16-state canonical public vocabulary adopted from v0.2 (`STATE_MACHINE.md` §4.5.1) **plus two new states** (`PARTICIPATION_DESIGNED`, `PARTICIPANT_BODY_READY`).
- v0.3 contains internal defects that adoption must repair rather than inherit; see §6.

---

## 3. Conflict Decisions

| # | Conflict | Decision | Applied where |
|---|---|---|---|
| 1 | "Civic Case" vs `Proposal` as the canonical container | **Suite wins (reaffirmed).** Decided in v0.2 reconciliation §5; v0.3 reasserts the term without new argument | No change; terminology map §7 |
| 2 | Classification: v0.3 (like v0.2) has no classification stage and hangs its strongest MUSTs on an undefined "high-impact" predicate (~10 uses) | **Suite wins (reaffirmed, v0.2 conflict 1).** The undefined predicate would be a new hidden chokepoint — the exact capture pattern `CLASSIFICATION.md` exists to block. v0.3's "high-impact" is **bound to classification output**: a proposal is *high-impact* iff classified `non_trivial` **and** (primary layer `governance` or `constitutional`, **or** minimum panel size ≥ 7, **or** `framework_change`). v0.3's "significant" maps to any `non_trivial` proposal | Binding recorded here; used by `PROTOCOL.md`, `INVARIANTS.md`, `45_Participation/` |
| 3 | Panel minimum: v0.3 §40 "high-impact SHOULD use ≥5 skills" | **Suite wins (reaffirmed, v0.2 conflict 7).** MUST ≥5 for every non-trivial proposal with mandatory class coverage, rule-bound escalation to 7/9 | No change |
| 4 | Adversarial review waivable: v0.3 §55 permits `JUDGMENT_READY` when adversarial review is "complete **or explicitly waived**" — contradicting v0.3's own §43 MUST | **Rejected.** Adversarial synthesis remains a mandatory packet-publication guard (`STATE_MACHINE.md` §4.4) with no waiver path. Compressed *windows* under the emergency regime (`95_Emergency/`) remain the only lawful acceleration; the review itself is never waived | No change |
| 5 | Licensing: v0.3 §66 "the protocol does not mandate one universal license… it mandates prior disclosure" | **Suite wins (reaffirmed, v0.2 conflict 6).** Disclosure-only licensing permits declared enclosure of commons-produced assets. The per-class gradation stands: open-core and delayed release prohibited for Commons-Core and Civic Utility (`PUBLIC_IP_MODEL.md` §7.1) | No change |
| 6 | "Civic Commons IP" as a single flat concept vs the suite's four Public IP classes | **Suite wins.** The class system carries the licensing rules; flattening it deletes them. "Civic Commons IP" is recorded as an alias for the commons-owned portion of the Public IP model | `PUBLIC_IP_MODEL.md` (alias note) |
| 7 | Treasury: v0.3 §67 "MAY operate or integrate with a Civic Treasury" (optional, unpartitioned) vs the suite's mandatory partitioned treasury with custody separation and reconciliation | **Suite wins.** v0.3 is internally inconsistent here: its treasury is optional while the obligations that require one (§65 public return, §92.4 no hidden movement) are mandatory | No change |
| 8 | Confidence: v0.3 §39 lists "confidence" as a self-reported specialist output | **Suite wins.** Derived-not-declared rule stands (`CONFIDENCE_AND_SCORING.md` §9.1). Self-declared confidence was already identified as a code-side violation in v0.2 reconciliation §6.1 | No change |
| 9 | Skill taxonomy: v0.3 §38 examples are domain-shaped (Legal, Economic Impact, Environmental…) with no mandatory-class concept | **Suite wins.** Function-shaped classes with mandatory `adversarial_critique` and `anti_capture_audit` coverage stand (`SKILLS.md` §5). Note: the code enum `SkillReviewerRole` already leans toward the domain shape and misses both mandatory classes — a pre-existing follow-up (v0.2 record §6.1) that v0.3 must not be read as legitimizing | No change; follow-up §8 |
| 10 | Skill admission: v0.3 treats a *declared* Skill Definition as sufficient to be a specialist (§37, §90.2) | **Suite wins.** Declaration without evaluation is a rubber stamp. Registry admission, sandbox phases, ranking, and revalidation stand (`SKILL_REGISTRY.md`, `MODEL_INCLUSION_SANDBOX.md`, `EXECUTOR_RANKING.md`, `REVALIDATION_POLICY.md`) | No change |
| 11 | MVP floor: v0.3 §96 makes 26 items MUST for any compatible MVP, including sortition-capable participant bodies, civic receipts, and privacy-preserving uniqueness — contradicting `MINIMUM_VIABLE_PNYX.md` §5 deferrals and the `BOOTSTRAP_REALITY_CHECK.md` complexity budget | **Hybrid.** The suite keeps staged deployment ("shrink the runtime, not the constitution"). A *participation floor* is added to the MVP (Participation Plan, per-case Participation Audit, verifiable receipt-or-equivalent); sortition, delegation of attention, and ZK uniqueness remain deferred with explicit debt notes. v0.3 §97's own tiering pattern ("extended participation compatibility") is adopted as the mechanism | `MINIMUM_VIABLE_PNYX.md`; `45_Participation/PARTICIPATION_MODEL.md` (compatibility tiers) |
| 12 | Governance Health: v0.3 §33 treats it as normative SHOULD-publish protocol surface; the suite's stronger `GOVERNANCE_HEALTH.md` sits in `99_Reference/` with status "Proposal" | **v0.3 wins on placement, suite wins on content.** `GOVERNANCE_HEALTH.md` is promoted to the new normative participation layer and extended with delegation-concentration, compensation-distribution, and sortition-integrity metrics | Moved to `45_Participation/GOVERNANCE_HEALTH.md` |
| 13 | Participation audit scope: v0.3 §32/§91.2 require the audit for *high-impact* cases only; v0.3 §55 requires it unconditionally before `JUDGMENT_READY` | **Resolved by proportionality.** A Participation Audit is required before decision-readiness for every non-trivial proposal; its required depth scales with classification (full audit for high-impact per conflict 2 binding; lightweight audit otherwise). Trivial proposals are exempt via the existing trivial shortcut | `PROTOCOL.md`; `45_Participation/PARTICIPATION_MODEL.md` |
| 14 | State semantics: v0.3 §50/§53 gates are "MAY enter when" — permissions, not guards | **Suite semantics win.** The two adopted states receive MUST-form guard checklists in `STATE_MACHINE.md` §4.4 style; milestone/"MAY" framing survives only in the export vocabulary description | `STATE_MACHINE.md` |

---

## 4. Adopted from v0.3

### 4.1 Participation subsystem (the principal adoption)

A new normative layer `45_Participation/` is created (between Identity and Economics), containing:

| Document | Content adopted (v0.3 source) |
|---|---|
| `PARTICIPATION_MODEL.md` | Participation as system not interface (§13); citizen formation (§14); participation modes — open, affected-party, targeted invitation, civic jury, institutional, monitoring (§16); Participation Plan (§17); participant body formation and the body≠panel rule (§18); compensated participation (§19); civic translation and accessibility (§20); humane interface norms (§21); Participation Audit (§32); participation compatibility tiers (§96-floor/§97-extended) |
| `SORTITION.md` | Sortition requirements (§16.4) — hardened: bound to `CRYPTOGRAPHIC_MODEL.md` verifiable-randomness primitives; the **eligible-population registry is treated as the primary capture surface** with its own integrity, challenge, and audit rules |
| `ATTENTION_AND_REACH.md` | Speech ≠ reach and the eight-function separation (§22); attention as public resource (§23); Reach Decisions (§24); Attention Budget (§25) — hardened with declared unit, owner, and breach consequence; Civic Briefs with information/invitation/required-action distinction (§26); Delegation of Attention (§27–29) — scoped, time-limited, revocable, non-transferable, with concentration monitoring; intake-flooding/queueing rule (absent from v0.3; required by `THREAT_MODEL.md` discipline) |

Proportionality rule adopted throughout: participation-design effort scales with classification tier; trivial proposals use the existing trivial shortcut (`STATE_MACHINE.md` §5) and never instantiate participation machinery.

### 4.2 Canonical public states

Two states are added to the canonical public vocabulary (`STATE_MACHINE.md` §4.5.1), which becomes 18 primary states:

- `PARTICIPATION_DESIGNED` — after `PROBLEM_DEFINED`, before `EVIDENCE_OPEN`;
- `PARTICIPANT_BODY_READY` — after `ADVERSARIAL_REVIEW`, before `DELIBERATION`.

Two internal states are added (`STATE_MACHINE.md` §4.1) with MUST-form guards (§4.4):

- `PARTICIPATION_DESIGN_PENDING` — between `CLASSIFIED` and `PANEL_SELECTION_PENDING`; required for non-trivial proposals;
- `PARTICIPANT_BODY_FORMATION` — between `PACKET_PUBLISHED` and `PUBLIC_REVIEW_OPEN`; entered only when the Participation Plan requires a formed body (jury, sortition, invited affected-party body); otherwise skipped.

Export mapping (§4.5.3) uses the existing phase-label semantics with minimal remapping: `PARTICIPATION_DESIGN_PENDING` → `PARTICIPATION_DESIGNED`; `PARTICIPANT_BODY_FORMATION` → `PARTICIPANT_BODY_READY`; all other mappings unchanged.

> **Meta-governance note (required by `STATE_MACHINE.md` §4.5.2):** changing the canonical public vocabulary is a meta-governance change. It is made here as a logged founder-phase decision and entered in the bootstrap debt register category of decisions requiring first-constitutional-review ratification.

### 4.3 New artifacts

| Artifact (canonical suite name) | v0.3 source | Defined in |
|---|---|---|
| `ParticipationPolicy` (scope-level defaults, epoch-bound) | §75 Participation Policy | `DATA_MODEL.md`, `SCHEMAS.md`, `45_Participation/PARTICIPATION_MODEL.md` |
| `ParticipationPlan` (per-case; absorbs scope/impact map, barrier assessment, accessibility plan, translation requirements) | §17, §75 | same |
| `ParticipantBody` | §18, §75 | same |
| `TargetedInvitation` | §16.3, §75 | same |
| `SortitionConfiguration`, `SortitionResult` | §16.4, §75 | same + `SORTITION.md` |
| `CivicBrief` | §26, §75 | same + `ATTENTION_AND_REACH.md` |
| `AttentionAllocationPolicy` | §23, §75 | same |
| `ReachDecision` | §24, §75 | same |
| `AttentionDelegation` | §27–29, §75 | same |
| `CivicReceipt` (accepted/included/corrected/superseded lifecycle; built on `CRYPTOGRAPHIC_MODEL.md` §5.7 inclusion proofs; disclosure default: **private proof, public aggregate**) | §31, §75 | same |
| `ParticipantCompensationRecord` (distinct from contributor compensation; paid via existing treasury release + `FundingReceipt` rails) | §19, §68, §75 | same + `TREASURY.md` |
| `ParticipationAudit` (per-case, pre-decision gate; performed under a declared auditor permission class with conflict rules; challengeable) | §32, §75 | same |
| `GovernanceHealthReport` | §33, §75 | `45_Participation/GOVERNANCE_HEALTH.md` |
| `PublicReturnReport` (closes the Public Value Loop; the suite mandated public return but had no closing artifact) | §65, §77, §104 | `DATA_MODEL.md`, `SCHEMAS.md`, `PUBLIC_IP_MODEL.md` |
| `ReplicationRecord` (declared overlap: model, provider, sources, context, methodology, cross-influence) | §41, §76 | `DATA_MODEL.md`, `SCHEMAS.md`, `EXECUTOR_MODEL.md` |
| `LicenseRecord`, `ContributorAgreement`, `EconomicConflictDisclosure` | §66, §77 | `DATA_MODEL.md`, `SCHEMAS.md`, `PUBLIC_IP_MODEL.md` |

### 4.4 New invariants (`INVARIANTS.md`)

Economic (§13, from v0.3 §92): no public production without declared ownership; no public value without public return; no hidden treasury movement; no commercial expansion without renewed authority; no purchased civic priority (anchor for v0.3 §69.3 — machinery in `ATTENTION_AND_REACH.md`).

Epistemic/specialist (from v0.3 §71–73): raw hidden reasoning is not canonical public provenance; a simulated citizen population is not public participation; model output is not a substitute for absent participation.

Participation (new class, from v0.3 §91, deduplicated against existing invariants): no open-door claim of equality; no high-impact decision without Participation Audit; no reach without a public rule; no urgency without declared reason (elevated from `POLITICAL_ECONOMY.md` §20.5); no delegation without scope and revocation; no compensation tied to position; no duplicate civic action where uniqueness is required; no summary without source continuity; no engagement metric as legitimacy.

### 4.5 Schema and field-level adoptions

- `JudgmentConfiguration` (`SCHEMAS.md` §25.2) extended with: tie handling, challenge process, abstention treatment, conflict-of-interest rules (v0.3 §56).
- Statement-status labeling for reuse — verified / corrected / disproven / unresolved; prediction vs observed; simulated vs actual participation — extending authoring modes (`SCHEMAS.md` §3.8; v0.3 §73).
- Skill Definition fields: temporal scope, skill-level conflict-of-interest policy reference, explicit out-of-mandate questions (`SKILLS.md`, `SKILL_REGISTRY.md`; v0.3 §38).
- Executor roles: civic translator and participation-accessibility assistant added as recognized skill roles (v0.3 §70).
- Core Change Proposal (`GOVERNANCE.md` §15.1) field merge: adds participation design and governance risk (v0.3 §93); **retains** reversibility/rollback path, which v0.3 omits.
- MVP (`MINIMUM_VIABLE_PNYX.md`): economic MVP section (v0.3 §98 checklist and its "MAY remain civic-only, MUST NOT claim economic compatibility" rule); participation floor per conflict 11.
- `TREASURY.md`/`FUNDING_MODEL.md`/`GRANTS_AND_BOUNTIES.md`: `ParticipantCompensation` spending class; compensation independent of position or vote (anti-vote-buying firewall, v0.3 §69.2/§91.7).
- `PUBLIC_IP_MODEL.md`: mandate-time disclosure extended with contributor rights, participant rights, attribution requirements, derivative-work conditions, abandonment/transfer rules (v0.3 §66).

---

## 5. Rejected or Not Needed

- **The v0.3 document itself** — not imported; the suite plus this record supersedes it.
- **"Civic Case"** — reaffirmed rejection (conflict 1).
- **The §55 adversarial-review waiver** (conflict 4).
- **Disclosure-only licensing** (conflict 5).
- **The flat declared-skill specialist model** (conflict 10) and the SHOULD-strength panel rule (conflict 3).
- **v0.3 §96's 26-MUST MVP floor as written** (conflict 11).
- **v0.3 §33 replacing `GOVERNANCE_HEALTH.md`** — the suite version is stronger; only placement and three metric families are adopted (conflict 12).
- **`FundingProposal` / `FundingDecision` as new artifacts** — already decomposed in the suite as `AllocationDecision` + `ReleaseAuthorization`; a funding request originating from the civic loop is an ordinary `Proposal` routed to funding. Mapping recorded in §7.
- **`HumanValidationRecord`** — covered by `HumanExpertRecord` + `ReviewRoutingSignal` (`SCHEMAS.md` §24.8–24.9).
- **`AccessibilityPlan`, `CivicTranslation`, `ScopeAndImpactMap`, `ParticipationBarrierAssessment` as separate artifacts** — folded into `ParticipationPlan` sections and `CivicBrief`; separate objects would double bookkeeping for no audit gain (`PAPER_ARCHITECTURE_Critique.md` discipline).
- **`CivicStandingProof` / nullifier record as new artifacts** — already specified in `CRYPTOGRAPHIC_MODEL.md` and `IDENTITY_AND_MEMBERSHIP.md`; participation docs reference them.
- **v0.3's Participation Loop as a second first-class loop** — adopted as a *transversal concern* documented in `PARTICIPATION_MODEL.md`, not as a parallel state machine; one lifecycle remains sovereign.

---

## 6. Defects in v0.3 Repaired During Adoption

Recorded so future readers do not treat the v0.3 text as more authoritative than the suite's rendering of it:

1. §43 (adversarial review MUST) contradicts §55 (waivable) — repaired by conflict 4.
2. §32 (audit high-impact only) contradicts §55 (audit unconditional) — repaired by conflict 13.
3. "High-impact" undefined but load-bearing — repaired by conflict 2 binding.
4. §50/§53 "MAY enter when" gates are permissions, not guards — repaired by conflict 14.
5. `PARTICIPATION_DESIGNED` framed as a one-way milestone for an explicitly iterative activity (§6, §17) — repaired: the Participation Plan is versioned and revisable after the state is reached; the state certifies that a *first complete plan* exists, not that design is finished.
6. `PARTICIPANT_BODY_READY` certifies only the deliberation/judgment body although participation spans intake→monitoring — the state is scoped accordingly in its definition.
7. Attention Budget with no unit, owner, or breach consequence; §23's eleven MAY-factors with no combination rule — repaired in `ATTENTION_AND_REACH.md`.
8. Sortition without a threat model for the eligible-population registry — repaired in `SORTITION.md`.
9. Participation Audit without an auditor, permission class, or challenge path — repaired in `PARTICIPATION_MODEL.md` + `PERMISSIONS.md`.
10. Delegation × uniqueness interaction unspecified (does a delegate consume the delegator's nullifier?) — repaired: delegation of attention never consumes the delegator's civic action; only the delegator's own action does.
11. No intake-flooding defense despite `THREAT_MODEL.md` treating channel-flooding as a primary attack — repaired in `ATTENTION_AND_REACH.md`.
12. Optional treasury vs mandatory auditability — repaired by conflict 7.

---

## 7. Terminology and Artifact Map

| v0.3 term | Suite term |
|---|---|
| Civic Case | `Proposal` |
| Specialist | skill (role) + executor (performer), per `EXECUTOR_MODEL.md` §2.3 |
| Skill Definition Artifact | `Skill` + `SkillVersion` (registry entries) |
| Specialist Assignment Artifact | `PanelSeat` + executor binding |
| Specialist Analysis Artifact | `SkillOutput` (Evidence Packet) |
| Panel Synthesis Artifact | `AdversarialSynthesis` / `PacketSynthesisRecord` |
| Adversarial Review Artifact | `adversarial_critique` seat output + `ChallengeEvent` |
| Replication Artifact | `ReplicationRecord` (new) |
| Human Validation Artifact | `HumanExpertRecord` + `ReviewRoutingSignal` |
| Model Contribution Record | `SkillRun` metadata + `ExecutorRecord` + authoring modes |
| Civic Commons IP | commons-owned classes of the Public IP model |
| Civic Treasury | partitioned treasury (`CoreTreasury`, `ChallengeTreasury`, …) |
| Civic Treasury Policy Artifact | `TreasuryEpoch` |
| Treasury Transaction Artifact | `ReleaseAuthorization` + `FundingReceipt` |
| Revenue Allocation Artifact | `RevenueRoutingRecord` |
| Funding Proposal / Funding Decision Artifact | `Proposal` (funding-routed) / `AllocationDecision` + `ReleaseAuthorization` |
| Delegation of Attention Artifact | `AttentionDelegation` (new) |
| Participation Compensation Artifact | `ParticipantCompensationRecord` (new) |
| Governance Health Report Artifact | `GovernanceHealthReport` (new) |
| high-impact | classification binding, conflict 2 |
| significant | `non_trivial` |

---

## 8. Recorded Follow-Ups (out of scope here)

1. **Code alignment** (extends the v0.2 record §6.1 list, none of which is yet resolved):
    - `ProposalState` (Java) lacks internal states for the two new canonical states; `CanonicalPublicState` and its exhaustive mapping switch need extension;
     - two of the three v0.2 deviations remain open (problem gate in `Proposal.create`, monitoring/learning states); `EXECUTION_AUTHORIZED` is resolved (see STATE_MACHINE.md §4.6.2);
    - `SkillReviewerRole` now includes `adversarial_critique` and `anti_capture_audit` (resolved in commit a1f2611);
    - confidence still model-declared in code;
    - the participation runtime (plans, bodies, sortition, receipts, audits, delegation, attention) has **zero code surface** — it is a subsystem to be built, not a patch.
2. **First-constitutional-review ratification** of the canonical-vocabulary change (§4.2 meta-governance note) — recorded in `BOOTSTRAP_DEBT_REGISTER.md` §2.1.
3. **Compensation rate-setting policy** — `PARTICIPATION_MODEL.md` defines the rules' shape; concrete rates are now set as epoch parameters in `BOOTSTRAP_PARAMETERS.md` §7.6 (resolved).
4. **Privacy-preserving civic credentials** — `ZK_CIVIC_CREDENTIALS.md` defines the subsystem specification but remains deferred infrastructure (recorded in `BOOTSTRAP_DEBT_REGISTER.md` §2.7). The bootstrap runtime uses simpler standing-authorization mechanisms with explicit limitations. Privacy-preserving uniqueness proofs are not part of the initial operational baseline.

---

## 9. Verification Performed

- Every adopted artifact exists in both `DATA_MODEL.md` and `SCHEMAS.md` under the same name.
- No secondary document maintains its own proposal-state list; the canonical↔internal mapping in `STATE_MACHINE.md` §4.5.3 remains total over all internal states, including the two new ones.
- No unmapped "Civic Case" or "Civic Commons IP" usage outside this record.
- Every v0.3 §91/§92 invariant is either present in `INVARIANTS.md` or explicitly rejected here.
- `00_INDEX_AND_MAP.md` reflects the new `45_Participation/` layer, the moved `GOVERNANCE_HEALTH.md`, and the version 2.3 changelog.
