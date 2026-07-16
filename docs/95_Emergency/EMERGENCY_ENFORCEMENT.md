# EMERGENCY_ENFORCEMENT

## 1. Purpose

This document defines how emergency powers are technically, procedurally, and institutionally constrained.

Its purpose is not to justify emergency authority.
Its purpose is to prevent emergency authority from becoming:
- a hidden permanent operating mode,
- a shortcut around the civic loop,
- a classification bypass,
- an operator convenience,
- a cover for active-case retuning,
- a route for power normalization.

This document complements:
- `GOVERNANCE.md`
- `THREAT_MODEL.md`
- `OPERATOR_TRUST_MODEL.md`
- `INVARIANTS.md`
- `STATE_MACHINE.md`
- `API_SPEC.md`
- `MINIMUM_VIABLE_PNYX.md`

---

## 2. Core Principle

An emergency path may exist only as a narrow exception under conditions that make ordinary deliberative timing materially unsafe.

Emergency authority is legitimate only if it is:
- explicitly bounded,
- minimally sufficient,
- independently visible,
- technically expiring,
- review-triggering,
- rollback-aware,
- incapable of silently mutating into normal governance.

The system must assume that every emergency path will be tempted toward expansion.
The enforcement model must therefore constrain not only emergency decisions,
but the emergency mechanism itself.

---

## 3. What Counts as Emergency

A condition may qualify as emergency only if delayed action would likely cause serious and time-sensitive harm that cannot reasonably be reduced through the ordinary path.

Emergency qualification requires at least one of:
- imminent safety risk,
- imminent security breach,
- imminent infrastructure failure,
- imminent identity integrity failure,
- imminent treasury loss or seizure,
- imminent destruction of auditability,
- imminent denial of participation at meaningful scale.

The following do **not** qualify by themselves:
- political embarrassment,
- low popularity,
- implementation inconvenience,
- reputational pressure,
- vendor delay,
- ordinary budget strain,
- controversial public reaction,
- electoral timing,
- strategic desire for speed.

An emergency declaration must state:
- the immediate harm,
- the reason ordinary timing is inadequate,
- the minimum action required,
- the maximum allowed scope,
- the expiration condition.

---

## 4. Emergency Classes

The system distinguishes emergency classes so that authority is not over-broad.

### 4.1 Runtime Integrity Emergency
For threats to:
- append-only audit continuity,
- active state integrity,
- canonical packet integrity,
- runtime authenticity,
- mirror divergence,
- key compromise.

### 4.2 Participation Continuity Emergency
For threats to:
- identity issuance continuity,
- proof verification continuity,
- access surface availability,
- large-scale exclusion due to infrastructure loss.

### 4.3 Treasury Defense Emergency
For imminent unlawful loss, seizure, or diversion of funds.

### 4.4 Safety Containment Emergency
For urgent, bounded actions required to prevent ongoing material harm from already-running system behavior.

No emergency class may be used as a general license to rewrite governance, routing, or constitutional rules.

---

## 5. What Emergency Authority May Do

Emergency authority may only:
- pause a vulnerable subsystem,
- switch to a declared backup path,
- rotate a compromised key under dual control,
- isolate a corrupted registry entry,
- freeze a payout or release pending verification,
- temporarily restrict a specific compromised interface,
- publish a forced visibility notice,
- trigger an immediate incident packet,
- invoke a bounded fallback already declared in advance.

Emergency authority must prefer:
- containment over redesign,
- pause over mutation,
- visibility over secrecy,
- reversibility over expansion.

---

## 6. What Emergency Authority May Never Do

Emergency authority must never:
- rewrite the values layer,
- lower scrutiny thresholds,
- downgrade proposal classification,
- alter active-case review windows,
- change vote rules,
- modify routing rules beyond pre-declared emergency fallbacks,
- suppress dissent records,
- delete audit events,
- add hidden redactions,
- alter skill registry composition except to isolate a specifically compromised skill,
- grant itself renewal power,
- convert emergency actions into permanent defaults without ordinary or meta-governance review.

Any such act is an emergency abuse event.

---

## 7. Emergency Preconditions

No emergency action may execute unless the following are satisfied:

1. **Declared emergency class**
2. **Bounded action description**
3. **Scope identifier**
4. **Named expiry rule**
5. **Independent co-authorization where required**
6. **Immediate audit append**
7. **Forced public incident visibility**
8. **Automatic review case creation**

Where one of these cannot be satisfied, the action must default to the least expansive safe containment available.

---

## 8. Technical Expiry

Emergency power must be technically bound to expiry.

At minimum, every emergency action must carry:
- `EmergencyId`
- `EmergencyClass`
- `IssuedAt`
- `ExpiresAt`
- `EmergencyScope`
- `EmergencyAuthoritySet`
- `FallbackReference`
- `ReviewCaseId`

The runtime must refuse execution of emergency authority beyond `ExpiresAt`.

Expiry must not depend solely on operator honesty.

---

## 9. Expiry Enforcement Mechanisms

At least one structural expiry mechanism must exist in every deployment profile.
Preferred mechanisms include:

- time-bound signed emergency tokens,
- expiring capability grants,
- pre-registered fallback scripts with automatic invalidation,
- hardware-backed key expiry where available,
- threshold-controlled renewal prohibition,
- time-locked privilege rollback,
- automatic state-machine transition from `EmergencyActive` to `EmergencyExpired`.

A deployment may use different tooling,
but it must produce the same constitutional effect:
**emergency authority ends even if operators prefer that it not end.**

---

## 10. Renewal Prohibition and Reissue Rules

Emergency authority must not self-renew.

A new emergency action after expiry requires:
- a new declaration,
- a new audit chain entry,
- a new visibility notice,
- a new review linkage,
- independent justification that the condition still exists.

Repeated reissue is itself a threat signal.

The system must track:
- frequency of reissue,
- class repetition,
- operator repetition,
- scope repetition,
- proximity to unresolved review findings.

High repetition requires escalation to meta-review or constitutional review depending on severity.

---

## 11. Dual Control and Separation

Emergency execution must be protected by role separation.

No single actor may both:
- declare and authorize,
- authorize and execute,
- execute and certify closure,
- close and review,
- issue and suppress incident visibility.

At minimum, emergency control must separate:
- declaring authority,
- execution authority,
- audit recording authority,
- closure certification authority,
- review authority.

Bootstrap deployments may have thinner role separation,
but every concentration must be logged as emergency governance debt.

---

## 12. Forced Visibility

Every emergency action must create an immediate public-facing incident record unless doing so would itself directly worsen the narrowly defined threat.

Even in rare delayed-disclosure cases:
- the existence of delayed visibility must be logged,
- the delay reason must be specific,
- delayed publication must auto-trigger at the earliest safe moment,
- delayed visibility may not delete canonical records.

No secret emergency may become normalized emergency.

---

## 13. Incident Packet Requirement

Every emergency action must generate an incident packet containing:
- emergency class,
- triggering condition,
- time of declaration,
- exact scope,
- affected components,
- authorized actions,
- expiry time,
- fallback reference,
- known risks,
- expected restoration path,
- responsible review case,
- dissent if any,
- uncertainty note if evidence is incomplete.

This packet must be stable, versioned, and linked to the canonical audit trail.

---

## 14. State Machine Requirements

The state machine must include explicit emergency states.

At minimum:
- `EmergencyDeclared`
- `EmergencyAuthorized`
- `EmergencyActive`
- `EmergencyExpired`
- `EmergencyClosed`
- `EmergencyUnderReview`
- `EmergencyAbuseFlagged`

The state machine must reject:
- invisible emergency execution,
- emergency extension without reissue,
- emergency closure without review linkage,
- emergency actions after expiry,
- emergency normalization into baseline config.

---

## 15. Interaction with Active Cases

Emergency authority must not silently alter live proposals, live challenges, or live meta-proposals.

If an emergency affects an active case, the system must:
- mark the case as impacted,
- record the exact effect,
- freeze or reroute only through declared emergency rules,
- preserve the original case history,
- require post-emergency restoration or revalidation.

Emergency cannot be a back door for active-case outcome manipulation.

---

## 16. Emergency Fallbacks Must Be Pre-Declared

The system should define in advance:
- backup registries,
- backup mirrors,
- backup key rotation procedures,
- backup packet publication surfaces,
- backup verification surfaces,
- backup treasury pause mechanisms.

Improvisation may sometimes be necessary.
But the more fallback logic is pre-declared,
the less room there is for emergency opportunism.

---

## 17. Rollback and Restoration

Emergency action must include an explicit restoration path.

Restoration must answer:
- what returns automatically after expiry,
- what remains paused pending review,
- what requires explicit revalidation,
- what data integrity checks must run,
- what user-facing notice must persist,
- what commitments must be reconciled,
- what temporary measures must be removed.

Emergency powers that have no restoration path are structurally suspect.

---

## 18. Post-Emergency Review

Every emergency action must trigger mandatory review.

Review must determine:
- whether the emergency qualification was valid,
- whether the scope was minimal,
- whether less expansive containment was possible,
- whether visibility rules were respected,
- whether expiry functioned correctly,
- whether any side effects require rollback,
- whether repeated emergency pattern indicates design failure,
- whether abuse indicators are present.

A review may classify the event as:
- valid and contained,
- valid but over-broad,
- procedurally defective,
- abuse,
- unresolved due to insufficient evidence.

---

## 19. Emergency Abuse Indicators

The following indicate likely abuse or normalization pressure:

- repeated reissue of same class and scope,
- emergency framing near politically sensitive decisions,
- delayed publication without narrow justification,
- emergency use to avoid classification escalation,
- emergency use to bypass routing friction,
- emergency use to mute dissent,
- emergency use coinciding with operator convenience,
- incomplete restoration after expiry,
- emergency measures left in place pending “later review,”
- repeated claims that ordinary governance is too slow.

Repeated indicators require redesign, not just incident logging.

---

## 20. Bootstrap Emergency Profile

Bootstrap may require narrower but more brittle emergency pathways.

Therefore bootstrap must:
- keep emergency classes minimal,
- pre-declare fewer but clearer fallbacks,
- restrict emergency actions to containment and pause where possible,
- treat every emergency concentration as governance debt,
- publish a bootstrap emergency debt register,
- require early review of the emergency subsystem itself.

Bootstrap is not a reason to loosen expiry or visibility.
It is a reason to reduce emergency surface.

---

## 21. Emergency Ledger and Metrics

The system must maintain an emergency ledger including:
- count by class,
- count by operator set,
- average duration,
- expiry failures,
- review outcomes,
- reissue frequency,
- delayed-visibility frequency,
- unresolved restorations,
- abuse findings.

This ledger must be queryable through audit views.

A system that uses emergency often is not merely “responsive.”
It may be under-designed, under-governed, or partially captured.

---

## 22. Enforcement Failure Modes and Physical Bypass

The system must acknowledge the "Delusion of Perfect Procedure": the reality that technical enforcement mechanisms (state machines, cryptographic locks, automated expiry) can themselves fail, be compromised, or be physically bypassed by operators during a true survival crisis.

If the technical enforcement layer fails or is bypassed:
- **State Machine Bugs:** If the state machine fails to enforce expiry or allows unauthorized transitions, the canonical audit log must still record the anomalous state. The system relies on out-of-band social consensus and mirror divergence to detect the failure.
- **Infrastructure Failure:** If the infrastructure hosting the enforcement logic collapses, emergency actions may revert to manual, out-of-band coordination. Such actions are technically illegitimate but may be practically necessary. They must be retroactively recorded and subjected to constitutional review once infrastructure is restored.
- **Physical Bypass:** If operators physically bypass cryptographic locks (e.g., accessing raw database volumes, extracting keys from HSMs under duress), the system cannot technically prevent this. However, the bypass must destroy the cryptographic integrity of the canonical chain, triggering immediate mirror divergence and alerting the broader participant network to the breach.

The system does not pretend it can technically prevent a sufficiently determined physical bypass. Instead, it ensures that any such bypass is catastrophically loud, destroys the illusion of procedural legitimacy, and forces a social-layer crisis resolution.

---

## 23. Cross-References

This document imposes requirements on:

### 23.1 GOVERNANCE
Emergency provisions must be treated as exceptions, not alternate default channels.

### 23.2 INVARIANTS
Emergency normalization and emergency bypass must be invariant violations.

### 23.3 STATE_MACHINE
Emergency transitions and expiry states must be canonical.

### 23.4 API_SPEC
Emergency endpoints must require bounded signed authority and auto-review linkage.

### 23.5 OPERATOR_TRUST_MODEL
Emergency keys and execution authority must remain role-separated.

### 23.6 AUDIT_LOG and AUDIT_VIEWS
Every emergency event must be visible in canonical and public-facing audit surfaces.

---

## 24. Failure Conditions

The emergency subsystem is constitutionally failed if any of the following becomes normal:
- emergency authority outlives expiry,
- emergency events lack public traceability,
- emergency reissue substitutes for structural repair,
- emergency actions mutate governance rules,
- emergency review is routinely deferred,
- emergency incidents are readable only by insiders,
- emergency authority becomes a standard implementation shortcut.

In such cases, emergency design must be treated as captured.

---

## 25. Closing Principle

Emergency powers are most dangerous when they appear reasonable, temporary, and necessary.

This system must therefore assume that emergency abuse will often arrive not as dramatic tyranny,
but as routine exception management.

The task of emergency enforcement is not only to log emergency.
It is to ensure that emergency cannot quietly become government.
