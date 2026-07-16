# BOOTSTRAP_DEBT_REGISTER

**Status:** Living document  
**Created:** 2026-07-16  
**Last updated:** 2026-07-16  
**Authority:** Founder-phase decision log  
**Review obligation:** First constitutional review (CONSTITUTIONAL_BOOTSTRAP.md §18)

---

## 1. Purpose

This register records every bootstrap shortcut, temporary concentration of power, deferred safeguard, and founding exception that deviates from the full constitutional specification.

Per CONSTITUTIONAL_BOOTSTRAP.md §19:
> "The system must maintain a bootstrap debt register. This register records: every founding shortcut, every temporary concentration of power, every single-provider dependency, every unreviewed classification asymmetry, every temporarily narrowed membership condition, every opaque element tolerated for startup reasons."

Each debt item must include:
- **What it is** — the specific deviation from full specification
- **Why it exists** — the bootstrap constraint that necessitates it
- **Who authorized it** — the decision authority
- **When it expires** — the review epoch or trigger condition
- **What review path retires it** — the mechanism for resolution
- **What happens if not retired** — the consequence of indefinite deferral

This register is a transparency mechanism. It prevents bootstrap debt from becoming permanent structure through silence or drift.

---

## 2. Debt Items

### 2.1 Canonical Vocabulary Extension

**What:** The canonical public state vocabulary was extended from 16 to 18 primary states (`PARTICIPATION_DESIGNED`, `PARTICIPANT_BODY_READY`) by the v0.3 reconciliation (CORE_V03_RECONCILIATION.md §4.2). This is a meta-governance change made as a logged founder-phase decision.

**Why:** The participation subsystem required explicit representation of participation design and participant body formation phases. The extension was necessary to support the v0.3 participation floor without breaking backward compatibility with the existing 16-state vocabulary.

**Who authorized:** Founder-phase decision, logged in CORE_V03_RECONCILIATION.md §4.2 meta-governance note.

**When it expires:** First constitutional review. The vocabulary extension is entered in the bootstrap debt register category of decisions requiring first-constitutional-review ratification (STATE_MACHINE.md §4.5.2).

**What review path retires it:** The first constitutional review (CONSTITUTIONAL_BOOTSTRAP.md §18) must explicitly ratify, amend, or reject the vocabulary extension. The review must determine whether the two new states should be retained, modified, or removed from the canonical vocabulary.

**What happens if not retired:** If the first constitutional review is indefinitely delayed, the bootstrap is considered failed (CONSTITUTIONAL_BOOTSTRAP.md §21). The vocabulary extension remains a founder-phase decision without constitutional legitimacy. The system cannot claim constitutional maturity until this debt is resolved.

**Current status:** Active. Awaiting first constitutional review.

**References:**
- CORE_V03_RECONCILIATION.md §4.2
- STATE_MACHINE.md §4.5.1, §4.5.2
- CONSTITUTIONAL_BOOTSTRAP.md §18

---

### 2.2 Sortition Machinery

**What:** Verifiable random selection (sortition) for participant body formation is deferred. The system does not yet implement the full sortition infrastructure specified in SORTITION.md, including eligible-population registry, snapshot commitment, randomness verification, stratification rules, and replacement chains.

**Why:** Sortition requires cryptographic primitives, registry infrastructure, and governance processes that exceed bootstrap capacity. The MVP prioritizes open participation and targeted invitation over random selection (MINIMUM_VIABLE_PNYX.md §5.1, §5.5).

**Who authorized:** Founder-phase decision, logged in MINIMUM_VIABLE_PNYX.md §5.5 and CORE_V03_RECONCILIATION.md conflict 11.

**When it expires:** Post-bootstrap phase, after first constitutional review confirms that the participation floor is operational and the registry infrastructure is available.

**What review path retires it:** Implementation of SORTITION.md §5-9 infrastructure, including:
- Eligible-population registry with change log
- Snapshot commitment mechanism
- Verifiable randomness primitives (CRYPTOGRAPHIC_MODEL.md §5.6)
- Stratification and balancing rules
- Replacement and opt-out procedures
- Privacy protections for selected participants

**What happens if not retired:** If sortition remains deferred indefinitely, the system cannot claim full participation compatibility (MINIMUM_VIABLE_PNYX.md §4.11). Proposals requiring formed participant bodies (civic juries, sortition bodies) cannot be processed. The participation subsystem remains incomplete.

**Current status:** Deferred. Bootstrap runtime uses open participation and targeted invitation only.

**References:**
- MINIMUM_VIABLE_PNYX.md §5.1, §5.5
- SORTITION.md §5-9
- PARTICIPATION_MODEL.md §4.4
- CORE_V03_RECONCILIATION.md conflict 11

---

### 2.3 Delegation of Attention

**What:** The delegation of attention mechanism (ATTENTION_AND_REACH.md §8) is deferred. Citizens cannot temporarily delegate review or attention to trusted delegates. The system does not implement delegation tracking, concentration monitoring, or revocation mechanisms.

**Why:** Delegation requires identity infrastructure, delegation contracts, and concentration monitoring that exceed bootstrap capacity. The MVP prioritizes direct participation over delegated review (MINIMUM_VIABLE_PNYX.md §5.5).

**Who authorized:** Founder-phase decision, logged in MINIMUM_VIABLE_PNYX.md §5.5 and CORE_V03_RECONCILIATION.md conflict 11.

**When it expires:** Post-bootstrap phase, after identity infrastructure and delegation contracts are operational.

**What review path retires it:** Implementation of ATTENTION_AND_REACH.md §8 infrastructure, including:
- Delegation contract creation and tracking
- Scope-specific and purpose-specific delegation rules
- Time-limited and revocable delegation
- Concentration monitoring per delegate per scope
- Non-transferability enforcement

**What happens if not retired:** If delegation remains deferred indefinitely, citizens with limited time or expertise cannot participate through trusted intermediaries. The participation subsystem cannot support bounded civic attention or delegated review.

**Current status:** Deferred. Bootstrap runtime requires direct participation.

**References:**
- MINIMUM_VIABLE_PNYX.md §5.5
- ATTENTION_AND_REACH.md §8
- CORE_V03_RECONCILIATION.md conflict 11

---

### 2.4 Cryptographic Uniqueness Proofs

**What:** Zero-knowledge proof infrastructure for privacy-preserving uniqueness is deferred. The system does not implement ZK proofs for civic action uniqueness, unlinkable credentials, or advanced verifier networks. CivicReceipt (PARTICIPATION_MODEL.md §12) uses a logged, challengeable equivalent instead of cryptographic uniqueness proofs.

**Why:** ZK proof infrastructure requires advanced cryptographic primitives, verifier networks, and privacy-preserving credential systems that exceed bootstrap capacity. The MVP narrows scope to plain credentials with explicit privacy debt tracking (MINIMUM_VIABLE_PNYX.md §5.1, CRYPTOGRAPHIC_MODEL.md §19).

**Who authorized:** Founder-phase decision, logged in MINIMUM_VIABLE_PNYX.md §5.1 and CRYPTOGRAPHIC_MODEL.md §19.

**When it expires:** Post-bootstrap phase, after cryptographic infrastructure and verifier networks are operational.

**What review path retires it:** Implementation of CRYPTOGRAPHIC_MODEL.md §5.7 infrastructure, including:
- Zero-knowledge proof primitives
- Unlinkable credential systems
- Privacy-preserving uniqueness proofs for civic actions
- Verifier network infrastructure

**What happens if not retired:** If cryptographic uniqueness remains deferred indefinitely, the system cannot guarantee one-valid-civic-action-per-eligible-credential without revealing participant identity. The privacy-preserving participation model is incomplete. Participants must trust the log-based equivalent rather than cryptographic guarantees.

**Current status:** Deferred. Bootstrap runtime uses logged, challengeable equivalents per PARTICIPATION_MODEL.md §12.

**References:**
- MINIMUM_VIABLE_PNYX.md §5.1
- CRYPTOGRAPHIC_MODEL.md §5.7, §19
- PARTICIPATION_MODEL.md §12
- CORE_V03_RECONCILIATION.md conflict 11

---

### 2.5 Advanced Cryptographic Privacy

**What:** Advanced cryptographic privacy mechanisms beyond basic content hashing are deferred. The system does not implement hidden civic credentials, scope-specific nullifiers, or privacy-preserving standing proofs. Participant identity is protected through disclosure policies rather than cryptographic anonymity.

**Why:** Advanced privacy mechanisms require ZK infrastructure, nullifier systems, and standing proof protocols that exceed bootstrap capacity. The MVP uses plain credentials with explicit privacy debt tracking (MINIMUM_VIABLE_PNYX.md §5.1).

**Who authorized:** Founder-phase decision, logged in MINIMUM_VIABLE_PNYX.md §5.1.

**When it expires:** Post-bootstrap phase, after ZK infrastructure is operational (see debt item 2.4).

**What review path retires it:** Implementation of CRYPTOGRAPHIC_MODEL.md §5 infrastructure, including:
- Hidden civic credentials
- Scope-specific nullifiers
- Privacy-preserving standing proofs
- Unlinkable participation mechanisms

**What happens if not retired:** If advanced privacy remains deferred indefinitely, participants cannot prove standing or uniqueness without revealing identity. The privacy-preserving participation model is incomplete. Participants must rely on disclosure policies rather than cryptographic guarantees.

**Current status:** Deferred. Bootstrap runtime uses disclosure policies and pseudonymous references.

**References:**
- MINIMUM_VIABLE_PNYX.md §5.1
- CRYPTOGRAPHIC_MODEL.md §5
- PARTICIPATION_MODEL.md §12

---

### 2.6 Formed Participant Bodies Beyond Open Participation

**What:** The system does not yet implement formed participant bodies beyond open participation. While the ParticipationPlan (PARTICIPATION_MODEL.md §6) can declare affected-party groups, targeted invitations, and civic juries, the runtime does not enforce body formation, invitation tracking, or body-specific judgment authority.

**Why:** Formed participant bodies require invitation infrastructure, body formation tracking, and judgment authority delegation that exceed bootstrap capacity. The MVP prioritizes open participation over formed bodies (MINIMUM_VIABLE_PNYX.md §5.5).

**Who authorized:** Founder-phase decision, logged in MINIMUM_VIABLE_PNYX.md §5.5 and CORE_V03_RECONCILIATION.md conflict 11.

**When it expires:** Post-bootstrap phase, after participation infrastructure is operational.

**What review path retires it:** Implementation of PARTICIPATION_MODEL.md §10 infrastructure, including:
- ParticipantBody artifact creation and tracking
- TargetedInvitation issuance and response tracking
- Body-specific judgment authority delegation
- Body formation audit and certification

**What happens if not retired:** If formed bodies remain unimplemented indefinitely, the system cannot support civic juries, sortition bodies, or invited affected-party bodies. Proposals requiring formed bodies cannot be processed. The participation subsystem remains limited to open participation.

**Current status:** Partially implemented. The domain types exist (ParticipationPlan, ParticipationAudit, CivicReceipt) but formed body enforcement is deferred.

**References:**
- MINIMUM_VIABLE_PNYX.md §5.5
- PARTICIPATION_MODEL.md §10
- CORE_V03_RECONCILIATION.md conflict 11

---

### 2.7 Privacy-Preserving Threshold Civic Credentials

**What:** The ZK civic credential subsystem for privacy-preserving standing verification and constrained-action uniqueness is deferred. The system does not implement blind threshold issuance, scope-specific nullifiers, holder-bound credentials, or privacy-preserving verification receipts. The full specification is defined in ZK_CIVIC_CREDENTIALS.md but the cryptographic infrastructure is not yet operational.

**Why:** The subsystem requires formal threat modeling, credential construction selection, threshold key infrastructure, holder-binding design, proof circuits, nullifier storage, privacy-preserving revocation, recovery mechanisms, security audits, and governance of issuers and verifiers. These exceed bootstrap capacity.

**Who authorized:** Founder-phase decision, logged in ZK_CIVIC_CREDENTIALS.md §21.

**When it expires:** After cryptographic specification, implementation, external audit, and governance approval. The exit criteria are defined in ZK_CIVIC_CREDENTIALS.md §21.

**What review path retires it:** Completion of all exit criteria in ZK_CIVIC_CREDENTIALS.md §21, including:
- Complete cryptographic specification
- Documented threat model
- Reproducible implementation
- Demonstrated threshold issuance
- Demonstrated nullifier uniqueness
- Tested cross-domain unlinkability
- Tested expiry and revocation
- Tested recovery
- Tested issuer replacement
- External cryptographic review
- Governance approval for consequential use

**What happens if not retired:** If the ZK credential subsystem remains deferred indefinitely, the system cannot claim anonymous, unlinkable, or privacy-preserving consequential participation. Bootstrap may use simpler standing-authorization mechanisms with explicit limitations. The system cannot make strong claims about privacy-preserving participation until this debt is resolved.

**Current status:** Deferred. Bootstrap uses simpler standing-authorization mechanisms with explicit limitations per ZK_CIVIC_CREDENTIALS.md §21.

**References:**
- ZK_CIVIC_CREDENTIALS.md §21
- CRYPTOGRAPHIC_MODEL.md §5
- PARTICIPATION_MODEL.md §12
- MINIMUM_VIABLE_PNYX.md §5.1

---

## 3. Debt Summary

| Debt Item | Category | Status | Review Trigger |
|-----------|----------|--------|----------------|
| Canonical vocabulary extension | Meta-governance | Active | First constitutional review |
| Sortition machinery | Participation | Deferred | Post-bootstrap, after registry infrastructure |
| Delegation of attention | Participation | Deferred | Post-bootstrap, after identity infrastructure |
| Cryptographic uniqueness proofs | Privacy | Deferred | Post-bootstrap, after ZK infrastructure |
| Advanced cryptographic privacy | Privacy | Deferred | Post-bootstrap, after ZK infrastructure |
| Formed participant bodies | Participation | Partial | Post-bootstrap, after participation infrastructure |
| Privacy-preserving threshold civic credentials | Privacy | Deferred | Post-bootstrap, after cryptographic audit and governance approval |

---

## 4. Review Schedule

Per CONSTITUTIONAL_BOOTSTRAP.md §18, the first constitutional review must:
- Occur on a fixed published schedule
- Be impossible to cancel unilaterally
- Include bootstrap debt review
- Include founder privilege review
- Include classification and registry review
- Include operator trust review
- Include review of who was excluded from the initial public
- Include a decision on which bootstrap exceptions continue, end, or escalate

**Current status:** First constitutional review schedule not yet published. This is itself a bootstrap debt item requiring resolution.

---

## 5. Debt Escalation

If any debt item is not retired by its review trigger, the following escalation path applies:

1. **Public disclosure** — The debt status must be published in the next Governance Health Report (GOVERNANCE_HEALTH.md §2.9)
2. **Challenge window** — Any participant may challenge the indefinite deferral through the standard challenge mechanism
3. **Constitutional review acceleration** — Indefinite deferral triggers acceleration of the first constitutional review
4. **Bootstrap failure declaration** — If the first constitutional review is indefinitely delayed, the bootstrap is considered failed (CONSTITUTIONAL_BOOTSTRAP.md §21)

---

## 6. Debt Retirement

A debt item is retired when:
- The full specification infrastructure is operational
- The first constitutional review explicitly ratifies the deviation as no longer necessary, OR
- The deviation is resolved through implementation of the full specification

Retired debt items are moved to Section 7 (Retired Debts) with the retirement date and mechanism.

---

## 7. Retired Debts

*No debts retired yet.*

---

## 8. Amendment History

| Date | Amendment | Authority |
|------|-----------|-----------|
| 2026-07-16 | Initial register created with 6 debt items | Founder-phase decision |
| 2026-07-16 | Added debt item 2.7: Privacy-preserving threshold civic credentials | Founder-phase decision |

---

## 9. References

- CONSTITUTIONAL_BOOTSTRAP.md §18-23 (First constitutional review, bootstrap debt register, expiry and transition, failed bootstrap)
- MINIMUM_VIABLE_PNYX.md §5 (Deferred features and bootstrap constraints)
- CORE_V03_RECONCILIATION.md §4.2, conflict 11 (Canonical vocabulary extension, participation deferrals)
- STATE_MACHINE.md §4.5.2 (Meta-governance change note)
- PARTICIPATION_MODEL.md §12-13 (Civic receipts, compatibility tiers)
- SORTITION.md §5-9 (Sortition infrastructure)
- ATTENTION_AND_REACH.md §8 (Delegation of attention)
- CRYPTOGRAPHIC_MODEL.md §5, §19 (Cryptographic primitives, bootstrap shortcuts)
- ZK_CIVIC_CREDENTIALS.md §21 (Privacy-preserving threshold civic credentials, exit criteria)
