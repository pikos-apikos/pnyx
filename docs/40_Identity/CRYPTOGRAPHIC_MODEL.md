# CRYPTOGRAPHIC_MODEL

## 1. Purpose

This document defines the cryptographic model for privacy-preserving civic participation.
It specifies the cryptographic objects, trust assumptions, proof flows, failure modes, and integration points required for identity, membership, eligibility, uniqueness, and vote/privacy protections.

It does **not** define civic legitimacy by itself.
Cryptography constrains disclosure and verification.
It does not replace governance, public trust, or institutional independence.

---

## 2. Design Goals

The cryptographic layer exists to support the civic system under the following goals:

- prove eligibility without revealing unnecessary identity data,
- prove membership without exposing full personal records,
- prevent double-claim and double-use where uniqueness matters,
- minimize correlatable identifiers across flows,
- allow selective disclosure rather than full disclosure,
- keep verification public, deterministic, and bounded,
- separate issuance trust from runtime verification power,
- preserve challengeability and auditability without exposing private data.

---

## 3. Non-Goals

The cryptographic layer does not attempt to:

- solve the entire political legitimacy problem,
- eliminate institutional trust,
- remove the need for human and civic review,
- guarantee that issuers or verifiers are honest by default,
- make all actions anonymous under all conditions,
- conceal public governance decisions that must remain public,
- substitute for anti-capture governance.

---

## 4. Core Principles

### 4.1 Minimal Disclosure
Only the minimum information required for a given civic action may be disclosed.

### 4.2 Purpose-Bound Proofs
A proof must be valid only for a defined purpose, scope, and policy epoch.

### 4.3 Unlinkability by Default
Two valid actions by the same participant should not be linkable unless the protocol explicitly requires linkage.

### 4.4 Verifier Minimalism
Verifiers should learn whether a claim is valid, not the hidden data underlying the claim.

### 4.5 Epoch Binding
Proof semantics must be bound to a specific framework/parameter epoch so that changing rules cannot silently reinterpret old proofs.

### 4.6 No Silent Correlation
No stable identifier may be exposed to the runtime unless that identifier is explicitly part of the protocol contract.

### 4.7 Cryptography Is Subordinate to Governance
A valid proof may establish cryptographic validity, but governance rules still determine whether the action is politically admissible.

---

## 5. Primary Use Cases

The model is intended to support the following classes of civic proof:

1. **Membership proof**
   - prove that a participant belongs to an eligible civic set.

2. **Uniqueness proof**
   - prove that a participant has not already exercised a one-person/one-action right in a given scope.

3. **Eligibility proof**
   - prove that a participant satisfies a rule such as residency, age, jurisdiction, or verified role.

4. **Selective attribute proof**
   - prove a property of a record without revealing the full record.

5. **Delegation or authorization proof**
   - where permitted, prove that a participant may act within a bounded role or mandate.

6. **Private challenge submission**
   - prove standing to challenge without forcing unnecessary public exposure.

7. **Receipt or inclusion proof**
   - prove that an action was accepted, recorded, or counted according to protocol.

---

## 6. Cryptographic Objects

The system should treat the following as first-class objects.

### 6.1 Credential
A cryptographically rooted statement issued under a defined policy, attesting to one or more participant properties.

### 6.2 Commitment
A binding cryptographic commitment to hidden data, used so the data can later be proven about without being revealed.

### 6.3 Nullifier
A one-time, scope-bound anti-reuse value derived so that the same right cannot be exercised twice within a defined domain.

### 6.4 Proof
A zero-knowledge or equivalent proof showing that a hidden statement satisfies a public verification rule.

### 6.5 Verification Key / Parameters
The public cryptographic material required to verify proofs for a given circuit or proof system.

### 6.6 Proof Request
A runtime-defined request specifying:
- proof purpose,
- required claims,
- scope,
- epoch binding,
- accepted verification rules.

### 6.7 Verification Record
The public record that a proof was checked, accepted or rejected, and tied to a specific purpose and epoch.

### 6.8 Revocation Record
A record indicating that a previously issued credential or proof basis is no longer valid for future use.

### 6.9 Inclusion Commitment
A commitment showing that a credential basis belongs to a recognized issuer set, registry, or accumulator.

---

## 7. Claim Types

The cryptographic model supports claims of the following forms:

- **set membership**: "I belong to this eligible set"
- **attribute threshold**: "my hidden attribute satisfies this rule"
- **jurisdictional eligibility**: "I am eligible in this civic scope"
- **non-reuse**: "I have not already used this right in this scope"
- **delegated authority**: "I hold a valid bounded authorization"
- **temporal validity**: "the claim was valid at the required epoch/time"
- **revocation absence**: "the underlying credential has not been revoked"

Claims must be explicit and typed.
No proof may rely on an implicit semantic interpretation.

---

## 8. Trust Assumptions

The system must state its trust assumptions openly.

### 8.1 Issuer Trust
Credential issuers are trusted only to the extent defined by governance and verifier policy.
Issuers may be independent, plural, and challengeable.

### 8.2 Verifier Trust
Verifiers are trusted to execute verification faithfully, log the result, and not bypass protocol.
They are **not** trusted with hidden participant data beyond what the protocol discloses.

### 8.3 Ceremony / Setup Trust
If the proof system requires a trusted setup, the system must document:
- who participated,
- what assumptions remain,
- what compromise would mean,
- how parameters are rotated or replaced.

### 8.4 Client-Side Trust
Participants must trust their local proof-generation environment enough not to exfiltrate secrets.
This is a real trust surface and may not be hidden behind abstract protocol language.

### 8.5 Governance Trust
Cryptography assumes the surrounding governance correctly defines eligible issuers, accepted proof types, revocation processes, and scope semantics.

---

## 9. Scope and Domain Separation

All proofs, nullifiers, and verification requests must be domain-separated.

Domain separation must at minimum bind:
- system identity,
- action type,
- civic scope,
- relevant proposal or vote scope where applicable,
- framework epoch,
- parameter epoch,
- proof purpose.

This prevents reuse of the same proof artifact across incompatible contexts.

---

## 10. Nullifier Design Requirements

Where uniqueness matters, the protocol must produce a nullifier or equivalent anti-reuse artifact.

A valid nullifier scheme must satisfy:
- one participant cannot derive multiple accepted nullifiers for the same right/scope,
- the nullifier does not reveal the participant identity,
- the nullifier cannot be reused across different scopes unless explicitly intended,
- nullifier collisions are computationally infeasible,
- the runtime can check reuse without learning private witness data.

A nullifier must be bound to a clearly defined scope such as:
- one proposal,
- one vote,
- one challenge window,
- one membership assertion per epoch.

---

## 11. Replay Resistance

The system must protect against replay of proofs, not only duplicate identity issuance.

Replay resistance may require:
- nonce-bound proof requests,
- one-time challenge binding,
- scope-bound nullifiers,
- expiry windows,
- proof request identifiers,
- acceptance logs checked before commit.

A proof valid once in one context must not automatically remain valid in a second context unless the policy explicitly allows it.

---

## 12. Revocation and Expiry

The cryptographic model must define how a credential stops being valid.

A revocation model must specify:
- who may revoke,
- under what grounds,
- whether revocation is immediate or epoch-bounded,
- how verifiers learn revocation state,
- how disputes over wrongful revocation are handled,
- whether historical proofs remain valid for past actions.

Expiry and revocation are distinct:
- **expiry** means time-bounded validity ended,
- **revocation** means validity was withdrawn before expiry or irrespective of expiry.

Historical governance must not be silently rewritten by future revocation unless the protocol explicitly says so.

---

## 13. Proof Lifecycle

The canonical proof lifecycle is:

1. participant receives or holds a credential basis,
2. runtime generates a proof request,
3. participant or trusted local device derives proof witness,
4. proof is generated locally,
5. verifier checks proof using accepted public parameters,
6. verifier checks nullifier/reuse conditions where required,
7. verifier emits a verification record,
8. runtime admits or rejects the civic action,
9. audit log records the accepted public facts of verification.

The runtime must never require the hidden witness itself.

---

## 14. Verification Contract

A verifier may only output a bounded result set such as:
- valid,
- invalid,
- expired,
- revoked,
- duplicate-use detected,
- unsupported-proof-type,
- verification-unavailable.

The verifier should not output hidden raw attributes unless the protocol explicitly requires selective disclosure of a public field.

Every verification result must record:
- proof purpose,
- proof type,
- scope,
- epoch binding,
- accepted/rejected status,
- failure class where relevant,
- nullifier status where relevant,
- verifier identity/class,
- timestamp.

---

## 15. Privacy Requirements

### 15.1 No Raw Identity in Core Runtime
The governance runtime should not store raw identity records where a proof is sufficient.

### 15.2 Selective Disclosure Over Full Disclosure
Where a property can be proven without revealing the full source record, the proof path is preferred.

### 15.3 Cross-Context Correlation Prohibition
The system must not reuse stable participant identifiers across civic actions unless explicitly required by governance and justified in public.

### 15.4 Hidden Witness Protection
Witness data, proving keys where sensitive, and participant secrets must remain outside public audit surfaces.

### 15.5 Public/Private Boundary Clarity
What is public, what is private, and what is pseudonymous must be specified for each flow.

---

## 16. Public Auditability Without Public Exposure

The system must make proof **verification** auditable without making the underlying **private witness** public.

This means public observers should be able to inspect:
- what proof type was required,
- which verification rule was used,
- whether acceptance occurred,
- whether the action consumed a uniqueness slot,
- whether the verifier behaved according to policy.

Public observers should **not** be able to infer the witness from the audit record.

---

## 17. Failure Modes

The cryptographic layer must explicitly model at least the following failure modes:

- stolen credential material,
- malicious client proof generation environment,
- compromised issuer,
- compromised verifier,
- broken or poisoned trusted setup,
- side-channel leakage,
- proof system implementation bug,
- incorrect circuit/policy binding,
- nullifier scope bug,
- revocation desynchronization,
- denial of proof generation for legitimate participants,
- forced identity disclosure outside protocol,
- downgrade to weaker proof requirements.

Each failure class must map to:
- detectability,
- containment mechanism,
- recovery path,
- governance implications.

---

## 18. Downgrade Resistance

The system must resist silent downgrade from privacy-preserving proofs to full disclosure or weaker checks.

A downgrade-sensitive action must not proceed by default if:
- the required proof system is unavailable,
- the verifier cannot check the required proof,
- the runtime attempts to replace a proof with a manual assertion,
- the action would newly expose raw identifying data.

Any downgrade path must be:
- explicitly governed,
- separately logged,
- challengeable,
- rare,
- non-default.

---

## 19. Bootstrap Constraints

During bootstrap, the cryptographic layer may begin with simpler or narrower proof coverage, but the following constraints apply:

- bootstrap cryptographic limitations must be explicit,
- any privacy debt must be documented,
- no stronger privacy claim may be advertised than the system actually provides,
- temporary trusted parties must be named,
- bootstrap shortcuts must expire or be reviewed at a defined future epoch,
- bootstrap proofs must not silently become permanent constitutional assumptions.

Bootstrap may justify a narrower proof scope.
It does not justify hidden trust.

---

## 20. Relationship to Identity and Membership

The cryptographic layer provides proof machinery.
It does not define by itself:
- who is a valid member,
- what residency means,
- what jurisdiction counts,
- what role-based authorization is legitimate,
- when revocation is normatively justified.

Those questions belong to:
- governance,
- identity and membership rules,
- verifier network design,
- constitutional constraints.

---

## 21. Relationship to Verifier Network

Cryptography does not eliminate verifier politics.

A verifier network specification must define:
- verifier admission,
- verifier independence,
- verifier quorum where required,
- jurisdiction and scope,
- logging duties,
- conflict-of-interest constraints,
- suspension and replacement,
- appeal and dispute mechanisms.

The cryptographic layer assumes such a network exists.
It does not replace it.

---

## 22. Recommended Proof Categories by Civic Action

### 22.1 Proposal Submission
- membership proof,
- eligibility proof where jurisdiction matters.

### 22.2 Challenge Submission
- standing proof,
- scope proof,
- optionally uniqueness/nullifier if only one challenge per role/scope is allowed.

### 22.3 Voting
- uniqueness proof,
- membership proof,
- eligibility proof.

### 22.4 Private Review Access
- role/standing proof,
- bounded authorization proof.

### 22.5 Emergency Override Invocation
- strongly scoped authorization proof,
- higher audit requirements,
- non-anonymous operator accountability unless constitutionally prohibited.

---

## 23. Cryptographic Invariants

The following invariants must hold:

1. No proof may be accepted outside a declared purpose and scope.
2. No nullifier may be reused within its bound domain.
3. No accepted proof may be reinterpreted under a different epoch.
4. No verifier may demand hidden witness data if the protocol does not require it.
5. No raw identity disclosure may silently replace a proof requirement.
6. No proof acceptance may occur without a verification record.
7. No revocation rule may silently invalidate historical governance records unless explicitly defined.
8. No stable cross-context identifier may leak into the runtime by default.
9. No privacy claim may exceed the actual guarantees of the deployed proof system.
10. No cryptographic shortcut may bypass governance-layer admissibility checks.

---

## 24. Open Design Questions

The following questions must be answered in downstream documents:

- Which proof system(s) are accepted initially?
- Is trusted setup acceptable, and under what public conditions?
- What issuer model exists during bootstrap?
- What revocation mechanism is operationally realistic?
- What device model is assumed for participant-side proof generation?
- Which actions require anonymity, pseudonymity, or attributable authorization?
- How are disputes over wrongful exclusion cryptographically and institutionally handled?
- How are lost credentials, key rotation, and recovery managed without destroying uniqueness guarantees?

---

## 25. Minimal Output Contract for Downstream Specs

The downstream system must be able to answer, for each proof-requiring action:

- what is being proven,
- to whom it is being proven,
- with what privacy guarantee,
- under what scope,
- under what epoch,
- how duplicate use is prevented,
- how revocation is checked,
- how the result is logged,
- how the participant challenges failure,
- what trust assumptions remain.

---

## 26. Closing Principle

The cryptographic layer exists to make civic verification narrower, more private, and harder to capture.
It should reduce unnecessary exposure without creating magical claims of trustlessness.

A proof may show that a participant satisfies a rule.
It does not prove that the rule itself is just.
