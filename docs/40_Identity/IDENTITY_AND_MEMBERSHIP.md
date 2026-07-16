# IDENTITY_AND_MEMBERSHIP

## 1. Purpose

This document defines the civic identity and membership model of the system.
It specifies what must be provable about a participant, what must remain hidden, how membership is established and challenged, and how identity-related power is prevented from becoming a permanent control surface.

It does **not** treat identity as the source of legitimacy.
Citizenship, membership, and eligibility are conditions of participation.
They do not replace the public process, the values layer, or civic judgment.

---

## 2. Design Goals

The identity and membership layer exists to:

- establish who may participate in a given civic scope,
- support one-person/one-right constraints where required,
- preserve privacy and minimize disclosure,
- prevent duplicate issuance and duplicate use,
- separate proof of eligibility from unnecessary revelation of personhood,
- make membership challengeable without making participants permanently trackable,
- keep issuance, verification, and runtime authority institutionally distinct,
- allow membership to evolve across epochs without silently invalidating past participation.

---

## 3. Non-Goals

This layer does not attempt to:

- define the entire moral basis of belonging,
- eliminate all trust in issuers, verifiers, or institutions,
- make every civic action anonymous under every condition,
- reduce political membership to a single database field,
- force a universal identity model across all civic scopes,
- solve coercion, bribery, or social intimidation by cryptography alone.

---

## 4. Core Principles

### 4.1 Membership Is Scope-Bound
Membership must always be defined relative to a civic scope.
There is no abstract valid member independent of jurisdiction, role, or rule context.

Civic scopes span the full range from neighborhood to Earth:
`neighborhood`, `community`, `municipal`, `regional`, `national`, `transnational`, `continental`, `global` (plus `custom`).

Scopes may be:
- hierarchical (nested),
- overlapping,
- federated,
- temporary or issue-specific.

The same membership principles apply at every scale; scale changes the machinery, not the principle.
Aggregation across scopes must preserve source-scope traceability, policy differences, minority positions, and uncertainty (see `../90_Information/DATA_MODEL.md` §5.1).

### 4.2 Minimal Disclosure
A participant should prove only what is required for the civic action.
The system should avoid exposing full identity when membership or eligibility is sufficient.

### 4.3 Separation of Identity Functions
Issuance, verification, challenge review, and runtime participation should not collapse into one actor or one control surface.

### 4.4 Uniqueness Where Necessary, Privacy Where Possible
The system may require uniqueness for some actions, but it should not convert uniqueness into global traceability.

### 4.5 No Silent Exclusion
A participant may not be excluded by hidden identity rules, silent registry edits, or unlogged revocation semantics.

### 4.6 Epoch Binding
Membership, eligibility criteria, and accepted proof forms must be bound to explicit framework and parameter epochs.

### 4.7 Membership Is Challengeable
Membership decisions must be reviewable, including wrongful inclusion, wrongful exclusion, stale credentials, or scope mismatch.

---

## 5. Identity vs Membership vs Eligibility

The system must keep these concepts distinct.

### 5.1 Identity
Identity refers to the underlying person or entity whose standing may be relevant to participation.
The full identity should not be exposed to runtime systems unless explicitly required by protocol and governance.

### 5.2 Membership
Membership means belonging to a recognized civic set for a defined scope.
Examples:
- member of a municipality,
- member of a district,
- member of a professional body where role-bound governance applies,
- member of a bootstrap steward set,
- member of a recognized verifier pool.

### 5.3 Eligibility
Eligibility means satisfying the conditions for a specific action.
Examples:
- eligible to deliberate,
- eligible to vote,
- eligible to challenge,
- eligible to verify,
- eligible to hold a temporary civic role.

A participant may be a member of a scope without being eligible for every action in that scope.

---

## 6. Civic Identity Objects

The following are first-class identity and membership objects.

### 6.1 Person Basis
A non-public basis from which recognized membership or eligibility credentials may be issued.
The runtime should not require direct access to this basis.

### 6.2 Membership Credential
A credential attesting that a participant belongs to a defined civic set under a defined epoch and policy.

### 6.3 Eligibility Credential
A credential or proof attesting that a participant satisfies a specific action condition.

### 6.4 Role Credential
A bounded credential for temporary or procedural roles such as verifier, steward, operator, or auditor where such roles are allowed.

### 6.5 Revocation Entry
A recorded event stating that a credential basis or issued credential is no longer valid for future use.

### 6.6 Challenge Standing Proof
A proof that a participant has standing to contest an inclusion, exclusion, or membership-related decision without disclosing more than necessary.

### 6.7 Membership Policy Epoch
The versioned rule set defining what counts as valid membership and valid proof for a scope.

---

## 7. Membership Classes

The system should explicitly type membership classes.
Common classes include:

- **jurisdictional membership**: belonging to a civic territory or municipality,
- **residency membership**: belonging by place of residence,
- **citizenship membership**: belonging by civic status under broader political rules,
- **role-bound membership**: belonging to a specific procedural or institutional set,
- **transitional/bootstrap membership**: temporary membership under founding constraints,
- **verifier membership**: recognized standing within the verifier network,
- **observer/non-member access**: permitted read-only or non-binding participation without membership rights.

Membership classes must not be silently conflated.

---

## 8. Actions That May Require Identity-Related Proof

The system may require proof for:

- entering a deliberative scope,
- casting a binding vote,
- asserting one-person/one-action uniqueness,
- submitting a standing-sensitive challenge,
- receiving a role assignment,
- acting as verifier or steward,
- participating in bootstrap-only founding procedures,
- accessing restricted but auditable internal review paths.

The required proof must be proportional to the action.

---

## 9. Privacy Contract

The identity layer must enforce a privacy contract aligned with `CRYPTOGRAPHIC_MODEL.md`.

### 9.1 The runtime should prefer proofs of properties over disclosure of raw records.

### 9.2 Stable cross-context identifiers must not be exposed unless explicitly required.

### 9.3 A proof valid for one scope must not silently become a tracking handle for another scope.

### 9.4 Auditability must preserve the fact that verification occurred without forcing disclosure of the hidden witness.

### 9.5 Membership challenges may require protected review channels so that a participant is not punished merely for contesting exclusion.

---

## 10. Issuance Model

The system must define how membership and eligibility credentials are issued.

Issuance should be:

- plural where possible,
- bounded by public policy,
- logged at the level of issuance events and policy basis,
- reviewable for wrongful denial or wrongful grant,
- institutionally separate from runtime vote counting or routing.

Issuers may include:
- recognized civic authorities,
- accredited independent verifiers,
- threshold-based verifier networks,
- temporary bootstrap authorities with explicit sunset and debt.

No single issuer should become a silent monopoly over all civic membership unless this is explicitly declared as a temporary bootstrap exception.

---

## 11. Verification Model

Verification should confirm that a participant satisfies the required claim for the action and epoch.

Verification must not automatically reveal:
- full identity,
- full residency records,
- full credential history,
- prior participation outside the allowed scope linkage.

Verification results must be:
- deterministic within the accepted proof system,
- bound to a policy epoch,
- logged as verification records,
- challengeable if based on stale policy or invalid revocation state.

---

## 12. Revocation and Expiry

Membership and eligibility are not necessarily permanent.

The system must distinguish:
- **natural expiry**: a credential ends at a declared time or epoch,
- **policy expiry**: a proof form is no longer accepted for future actions,
- **membership change**: the participant no longer belongs to the relevant civic set,
- **for-cause revocation**: a credential was wrongly issued or compromised,
- **temporary suspension**: participation is paused pending review.

Revocation rules must be:
- explicit,
- epoch-bound,
- publicly defined,
- non-retroactive unless the governing framework explicitly allows retroactive invalidation for fraud or system compromise.

---

## 13. Uniqueness and Anti-Duplicate Guarantees

Where the civic action requires one-person/one-right semantics, the identity layer must work with the cryptographic layer to ensure:

- one person cannot validly claim multiple participations in the same protected scope,
- the anti-duplicate mechanism does not create global traceability,
- duplicate use attempts are detectable within the relevant scope,
- detection does not require disclosure of the full identity witness.

The system should prefer scope-bound nullifier or equivalent designs over reusable global identifiers.

---

## 14. Membership Challenges

Membership-related decisions must be challengeable.

Challenge classes include:

- wrongful exclusion,
- wrongful inclusion,
- stale or invalid revocation,
- scope misclassification,
- denial of standing,
- duplicate-identity suspicion,
- fraudulent issuance,
- verifier misconduct,
- bootstrap membership abuse.

A membership challenge must produce:
- the contested decision,
- the relevant policy epoch,
- the claimed harm,
- the protected evidence channel if private review is required,
- the review path and allowed outcomes.

---

## 15. Role Separation and Anti-Capture Requirements

The following concentrations are forbidden unless explicitly declared as bootstrap debt:

- issuer + verifier + operator in the same uncontrolled actor,
- identity registry maintainer + vote runtime operator in the same uncontrolled actor,
- membership classifier + challenge adjudicator in the same uncontrolled actor,
- single-provider identity issuance with no public debt declaration,
- silent interoperability agreements that create cross-scope correlation.

Identity infrastructure is a major capture surface and must be treated as such.

---

## 16. Bootstrap Identity Rules

During bootstrap, the system may temporarily rely on narrower issuer sets, simpler proof forms, or partially trusted enrollment pathways.

If so, the system must explicitly record:

- what shortcut exists,
- why it exists,
- what additional trust it assumes,
- what risks it creates,
- when it must be reviewed,
- what exit path removes it.

Bootstrap identity shortcuts must never be mistaken for normal constitutional equilibrium.

---

## 17. Public Guarantees

Every participant should be able to know:

- what membership class applies,
- what evidence or proof is required,
- what issuer or verifier set is recognized,
- what revocation rules apply,
- how uniqueness is enforced,
- how to challenge wrongful exclusion,
- whether any bootstrap debt is still active.

No participant should be subject to opaque identity obligations.

---

## 18. Failure Modes

The identity and membership layer must assume the following failure modes are realistic:

- wrongful exclusion through stale registries,
- wrongful inclusion through corrupt issuance,
- silent correlation across civic scopes,
- duplicate issuance or duplicate proof acceptance,
- vendor capture of identity tooling,
- role concentration around issuer/verifier/operator functions,
- unverifiable revocation events,
- politically motivated membership narrowing,
- bootstrap shortcuts hardening into permanent identity hierarchy.

These are governance failures as much as technical ones.

---

## 19. Required Integration Points

This document must remain consistent with:

- `CRYPTOGRAPHIC_MODEL.md` for proofs, commitments, nullifiers, and verification semantics,
- `VERIFIER_NETWORK.md` for institutional verifier structure,
- `PROTOCOL.md` for when proofs are required in lifecycle transitions,
- `DATA_MODEL.md` for membership and challenge objects,
- `PERMISSIONS.md` for action rights bound to membership and eligibility,
- `CONSTITUTIONAL_BOOTSTRAP.md` for founding and transitional exceptions.

---

## 20. Normative Summary

The system should know enough to decide whether a participant may act,
but not more than it needs to know.

Membership should be provable without becoming a surveillance rail.
Uniqueness should be enforceable without becoming a permanent identifier.
Issuance should be accountable without becoming a monopoly.
Challenge should be possible without forcing self-exposure.

Identity exists to support civic participation,
not to domesticate it.
