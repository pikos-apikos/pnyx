# PNyx ZK Civic Credentials

**Status:** Draft subsystem specification  
**Target:** PNyx Core v0.3  
**Classification:** Deferred cryptographic infrastructure  
**Pronunciation:** PNyx is pronounced "p-nix"

---

## 1. Purpose

This document defines a privacy-preserving credential subsystem for PNyx.

Its purpose is to allow a person to prove that they have legitimate civic standing and permission to perform a specific civic action without publishing:

* their legal identity;
* their address;
* the private basis of their civic standing;
* their complete participation history;
* a permanent global citizen identifier.

The subsystem connects confidential civic onboarding with publicly auditable participation.

Its central invariant is:

> A private person may perform a publicly auditable civic action without becoming a globally traceable civic identity.

This document defines the architectural requirements of the subsystem. It does not claim that a complete cryptographic construction has already been formally specified, implemented, audited or proven secure for the PNyx threat model.

---

## 2. Scope

The subsystem covers:

* private verification of civic standing;
* distributed issuance of holder-bound civic credentials;
* scope-specific proofs of eligibility;
* constrained-action uniqueness;
* purpose-bound nullifiers;
* credential expiry and revocation;
* public verification receipts;
* integration with existing PNyx participation artifacts;
* governance of verifiers, issuers and cryptographic policies.

The subsystem does not define:

* the legal criteria for civic standing;
* the complete identity-verification process of each jurisdiction;
* a mandatory proof system, signature scheme or elliptic curve;
* a mandatory anonymous transport network;
* the complete implementation of sortition, delegation or formed bodies;
* sanctions for duplicate or invalid civic actions;
* a global identity registry.

Eligibility remains a public governance rule.

Identity evidence remains private.

---

## 3. Design principles

### 3.1 Identity is not standing

PNyx distinguishes:

* **Identity:** the person behind a credential.
* **Civic standing:** the legitimate relationship between a person and a civic scope.
* **Authorization:** permission to perform a specific civic action.
* **Provenance:** the legitimate civic position, role or artifact from which a claim entered public memory.
* **Public attribution:** an optional public identity or role chosen or required for a particular artifact.
* **Cryptographic continuity:** proof that multiple actions originate from the same accountable role where such continuity is required.

A proof of civic standing must not automatically reveal legal identity.

### 3.2 The basis of standing remains private

A person may have legitimate civic standing through residence, work, property, care responsibility, family relationship, public function or another lawful basis.

PNyx does not need to know which basis applies.

The system needs only a proof that:

```text
Valid standing exists
for civic scope X
under policy Y
during epoch Z.
```

The private reason for that standing must not enter public memory.

### 3.3 No global civic identifier

The subsystem must not create a stable identifier that allows observers to correlate all actions performed by the same person.

Uniqueness must exist only inside a declared civic action domain.

### 3.4 Public rules, private evidence

The following must be public:

* eligibility policies;
* issuer policies;
* verifier policies;
* proof-system versions;
* verification rules;
* revocation rules;
* public receipts;
* audit results.

The following must remain private:

* identity documents;
* address;
* the reason a person has standing;
* holder secrets;
* hidden credentials;
* raw onboarding evidence.

### 3.5 Threshold trust

No single organization should be able to issue a valid consequential civic credential unilaterally.

Issuance should require a threshold of independently governed issuers.

---

## 4. Architectural precedent

The subsystem is informed by blind threshold credential and compact e-cash architectures.

A relevant architectural precedent is the zk-nym model used by Nym:

```text
authorized payment
→ blind threshold issuance
→ locally aggregated private credential
→ locally derived unlinkable tickets
→ duplicate-spend detection
```

PNyx adapts the pattern as:

```text
authorized civic standing
→ blind threshold issuance
→ locally aggregated civic credential
→ scope-specific civic action proofs
→ duplicate-action detection
```

The similarity is architectural, not semantic.

Nym proves a right to consume network resources.

PNyx must prove legitimate civic standing and permission to participate in a specific civic scope, loop and action.

Nym is not a protocol dependency, governance authority or sovereign component of PNyx.

---

## 5. Roles

### 5.1 Citizen

The citizen:

* generates and controls the holder secret;
* stores the civic credential;
* produces purpose-bound civic action proofs;
* selectively discloses only the attributes required by policy;
* protects recovery material;
* may challenge incorrect rejection or revocation.

The holder secret should be generated locally and should not be known to issuers, verifiers or civic ingress services.

### 5.2 Standing verifier

A standing verifier determines whether a person satisfies a public `StandingVerificationPolicy`.

The verifier may temporarily process private identity evidence.

The verifier must not:

* publish that evidence;
* publish the basis of standing;
* observe the citizen's later civic actions;
* create a permanent public identity record;
* independently issue a complete credential unless explicitly permitted by a low-risk bootstrap profile.

### 5.3 Issuer quorum

The issuer quorum consists of independently governed credential issuers.

Each issuer:

* verifies the issuance authorization;
* validates the applicable policy and epoch;
* confirms that the authorization has not already been consumed;
* produces a blinded partial signature or equivalent credential share;
* records an accountable issuance event without learning the final credential.

A threshold of issuer shares is required to create a valid civic credential.

### 5.4 Civic ingress

The civic ingress receives:

* the proposed civic action;
* the applicable policy reference;
* the civic action proof;
* the nullifier, where uniqueness is required.

It verifies the action and emits a public verification result.

The civic ingress must not receive the original identity evidence.

### 5.5 Public auditor

A public auditor verifies:

* policy consistency;
* issuer quorum membership;
* verifier authorization;
* threshold rules;
* proof-system versions;
* verification receipts;
* nullifier uniqueness;
* revocation commitments;
* governance changes;
* challenges and resolutions.

The auditor must be able to audit the system without access to legal identity evidence.

---

## 6. Relationship with existing PNyx artifacts

The credential subsystem complements the existing participation model. It does not replace it.

### 6.1 ParticipationPlan

The `ParticipationPlan` defines:

* who is eligible to participate;
* which civic scope applies;
* which action types are available;
* whether uniqueness is required;
* how many actions are permitted;
* whether public attribution is required;
* which verification policy applies.

The `ParticipationPlan` references the applicable credential and verification policies.

It does not contain private credentials or identity evidence.

### 6.2 StandingVerificationPolicy

The `StandingVerificationPolicy` defines:

* the eligibility condition;
* the civic scope;
* authorized verifier classes;
* acceptable evidence categories;
* privacy and retention requirements;
* appeal procedures;
* issuance authorization format;
* policy version and validity period.

This policy determines how standing is established privately.

### 6.3 CivicCredentialPolicy

The `CivicCredentialPolicy` defines:

* credential class;
* permitted civic scopes;
* private and selectively disclosed attributes;
* issuance rules;
* expiry;
* epoch rotation;
* revocation semantics;
* recovery rules;
* permitted civic action types;
* holder-binding requirements;
* cryptographic profile.

### 6.4 IssuerQuorumPolicy

The `IssuerQuorumPolicy` defines:

* authorized issuers;
* quorum identifier;
* issuance threshold;
* issuer governance;
* issuer replacement;
* key rotation;
* compromise procedures;
* accountability and audit requirements.

### 6.5 CivicReceipt

The existing `CivicReceipt` remains the general public receipt for a civic action.

It records that an action entered the Civic Loop under a declared policy and verification result.

The ZK subsystem does not create a competing top-level receipt type.

Instead, the `CivicReceipt` may contain a `verification` component based on the `CivicActionVerificationReceipt` schema.

Conceptually:

```text
CivicReceipt
├── action reference
├── artifact hash
├── participation policy reference
├── public provenance
└── verification
    └── CivicActionVerificationReceipt
```

### 6.6 CivicActionVerificationReceipt

The `CivicActionVerificationReceipt` is the cryptographic verification component of a `CivicReceipt`.

It records:

* which proof policy was applied;
* which scope and action domain were verified;
* whether the proof was valid;
* whether the nullifier was unique;
* which cryptographic profile was used;
* which canonical action hash was authorized.

It must not reveal the identity or private credential of the participant.

### 6.7 ParticipationAudit

The `ParticipationAudit` aggregates and evaluates:

* valid and rejected verification receipts;
* uniqueness enforcement;
* issuer and verifier activity;
* policy compliance;
* revocation events;
* challenge outcomes;
* participation accessibility;
* observed privacy failures;
* concentration or censorship risks.

The audit works from public receipts and accountable governance records.

It does not require access to raw onboarding evidence.

### 6.8 Nullifier projection

The nullifier store is not a standalone civic artifact equivalent to a proposal or judgment.

It is a deterministic public or publicly auditable projection derived from accepted verification receipts.

Its purpose is to detect duplicate constrained actions inside a declared nullifier domain.

Depending on the threat model, the projection may be:

* fully public;
* transparently committed;
* distributed across ingress services;
* stored as an append-only accumulator;
* represented through epoch commitments.

The implementation profile must define the exact storage model.

---

## 7. Credential lifecycle

Credential lifecycle states are separate from proposal and Civic Loop lifecycle states.

They must not be inserted into the canonical proposal state machine.

The credential lifecycle is:

```text
ISSUANCE_REQUESTED
        ↓
STANDING_VERIFIED
        ↓
ISSUANCE_AUTHORIZED
        ↓
PARTIAL_CREDENTIALS_ISSUED
        ↓
CREDENTIAL_ACTIVE
        ├── CREDENTIAL_SUSPENDED
        ├── CREDENTIAL_EXPIRED
        ├── CREDENTIAL_REVOKED
        └── CREDENTIAL_REPLACED
```

### 7.1 ISSUANCE_REQUESTED

The citizen begins a credential request and creates the holder-controlled cryptographic commitment.

### 7.2 STANDING_VERIFIED

An authorized standing verifier has determined that the citizen satisfies the applicable standing policy.

This is a private protocol state.

It must not become a public identity record.

### 7.3 ISSUANCE_AUTHORIZED

A valid issuance authorization has been produced for a specific:

* credential class;
* civic scope;
* policy;
* epoch;
* expiry;
* blinded holder commitment.

### 7.4 PARTIAL_CREDENTIALS_ISSUED

A sufficient set of issuers has produced valid credential shares or blinded partial signatures.

### 7.5 CREDENTIAL_ACTIVE

The citizen has locally aggregated a valid credential that may be used under its applicable policies.

### 7.6 CREDENTIAL_SUSPENDED

The credential is temporarily unusable while a challenge, compromise investigation or governance process is active.

### 7.7 CREDENTIAL_EXPIRED

The validity period has ended.

### 7.8 CREDENTIAL_REVOKED

The credential has been invalidated under an authorized revocation process.

### 7.9 CREDENTIAL_REPLACED

A new credential supersedes the previous credential following recovery, rotation or policy migration.

Credential replacement must not automatically create a public link between old and new civic actions.

---

## 8. Civic action verification outcomes

Action verification outcomes are not credential lifecycle states.

They are recorded inside the verification component of a `CivicReceipt`.

Recommended outcomes include:

```text
ACCEPTED
REJECTED_INVALID_PROOF
REJECTED_DUPLICATE_NULLIFIER
REJECTED_EXPIRED_CREDENTIAL
REJECTED_REVOKED_CREDENTIAL
REJECTED_WRONG_SCOPE
REJECTED_WRONG_ACTION_TYPE
REJECTED_POLICY_MISMATCH
REJECTED_EPOCH_MISMATCH
REJECTED_ARTIFACT_HASH_MISMATCH
REJECTED_UNSUPPORTED_CRYPTOGRAPHIC_PROFILE
```

A rejected action should still produce an accountable rejection receipt where doing so does not create a privacy or denial-of-service vulnerability.

---

## 9. Private onboarding and issuance

The recommended onboarding flow is:

```text
private standing verification
        ↓
issuance authorization
        ↓
distributed blind issuance
        ↓
local credential aggregation
        ↓
credential activation
```

### 9.1 Holder secret generation

The citizen device generates a high-entropy holder secret.

The secret must remain under citizen control.

A credential should be cryptographically bound to the holder secret so that it cannot function as an unrestricted transferable bearer token.

### 9.2 Standing verification

The standing verifier confirms that the citizen satisfies the public eligibility policy.

The verifier must collect only the evidence necessary for the policy.

Evidence retention must be:

* minimized;
* explicitly governed;
* time-bounded;
* auditable;
* subject to deletion rules.

### 9.3 Issuance authorization

After successful verification, the verifier produces an issuance authorization bound to:

```text
credential_class
civic_scope
standing_policy_id
credential_policy_id
issuer_quorum_id
issuance_epoch
expiry_epoch
blinded_holder_commitment
authorization_nonce
```

The authorization must not expose the holder's identity to the issuer quorum unless the applicable governance policy explicitly requires issuer visibility.

### 9.4 Blind threshold issuance

Each issuer validates:

* verifier authority;
* policy version;
* authorization freshness;
* authorization uniqueness;
* civic scope;
* credential class;
* issuance epoch;
* quorum policy.

Each issuer returns a blinded partial credential.

No issuer should learn the final credential or later action nullifiers.

### 9.5 Local aggregation

The citizen device combines a threshold of valid partial credentials into the final credential.

The final credential:

* remains private;
* is not written to public storage;
* is not included in public artifacts;
* is not sent as a complete object to civic ingress services.

---

## 10. Credential attributes

A civic credential may contain or commit to:

* holder secret;
* credential class;
* authorized civic scope;
* standing policy identifier;
* credential policy identifier;
* issuance epoch;
* expiry epoch;
* revocation handle;
* action capability set;
* selectively disclosed role attributes;
* cryptographic profile version.

Attributes should be classified as:

### 10.1 Private attributes

Never disclosed directly.

Examples:

* holder secret;
* private standing basis;
* internal revocation witness;
* credential randomness.

### 10.2 Selectively disclosed attributes

Disclosed only when required by an action policy.

Examples:

* age threshold satisfied;
* membership in an authorized civic body;
* authorization for a specific public role;
* eligibility for a specific sortition pool.

### 10.3 Public policy references

Visible because they define how the proof was verified.

Examples:

* policy identifiers;
* civic scope;
* action type;
* epoch;
* cryptographic profile.

---

## 11. Civic action proofs

For each constrained civic action, the citizen derives a separate proof.

The proof must be bound to:

```text
protocol_version
civic_scope
loop_id
action_type
participation_policy_id
credential_policy_id
epoch
canonical_artifact_hash
```

The proof demonstrates only the claims required by the applicable policy.

Typical claims include:

```text
The prover possesses a valid credential.

The credential authorizes participation in this civic scope.

The credential is active in the current epoch.

The action type is permitted.

The proof is bound to this exact artifact hash.

The nullifier was correctly derived.

The credential is not revoked.

The holder has not already performed this constrained action.
```

The proof must not disclose:

* legal identity;
* address;
* the basis of standing;
* the complete credential;
* unrelated attributes;
* action history from other domains.

---

## 12. Scope-specific nullifiers

A nullifier provides constrained uniqueness.

It must never become a universal citizen identifier.

A conceptual nullifier domain is:

```text
NullifierDomain {
  protocolVersion,
  civicScope,
  loopId,
  actionType,
  participationPolicyId,
  epoch
}
```

A conceptual construction is:

```text
nullifier = PRF(
    holder_secret,
    canonical_encode(NullifierDomain)
)
```

This expression is illustrative and non-normative.

The final construction must undergo formal cryptographic design and review.

The protocol-level requirements are:

* the same credential produces the same nullifier in the same domain;
* different domains produce unlinkable nullifiers;
* the holder secret cannot be recovered from a nullifier;
* observers cannot calculate nullifiers for other actions;
* replay is detectable;
* duplicate constrained actions are detectable;
* nullifiers are bound to a declared policy and epoch;
* no global cross-scope correlation identifier is created.

A policy may define uniqueness as:

* once per proposal;
* once per public judgment;
* once per review round;
* once per delegation period;
* once per sortition invitation;
* once per funding decision;
* another explicitly governed constraint.

---

## 13. Public verification receipts

A successful or accountable failed verification produces a `CivicActionVerificationReceipt`.

The receipt should contain:

```text
receipt_id
protocol_version
verification_policy_id
standing_policy_id
credential_policy_id
issuer_quorum_policy_id
cryptographic_profile_id
civic_scope
loop_id
action_type
epoch
canonical_artifact_hash
nullifier
verification_outcome
verification_timestamp
ingress_identifier
revocation_commitment_reference
```

Optional fields may include:

```text
rejection_reason
challenge_reference
proof_size
verification_duration
issuer_epoch
verifier_epoch
```

The receipt must not contain:

```text
legal_identity
identity_document
address
standing_basis
holder_secret
complete_credential
raw_onboarding_evidence
global_participant_identifier
cross_scope_tracking_identifier
```

The receipt is included in or referenced by the corresponding `CivicReceipt`.

---

## 14. Revocation, expiry and recovery

The subsystem must distinguish:

* credential expiry;
* policy expiry;
* issuer-key compromise;
* verifier misconduct;
* citizen loss of holder secret;
* citizen device loss;
* voluntary credential replacement;
* lawful revocation;
* emergency suspension;
* cryptographic profile deprecation.

### 14.1 Expiry

Credentials should have bounded validity.

Short-lived credentials reduce long-term revocation complexity but increase re-issuance load and possible correlation risks.

The implementation profile must balance these concerns.

### 14.2 Revocation

The Core specification does not mandate one cryptographic revocation mechanism.

Permitted implementation profiles may use:

* cryptographic accumulators;
* epoch-based revocation commitments;
* short-lived credentials;
* privacy-preserving revocation lists;
* verifier-local revocation witnesses;
* hybrid mechanisms.

The chosen mechanism must preserve the principle that the verifier learns only whether the credential is currently valid for the requested action.

### 14.3 Recovery

Recovery must not silently create a permanent identity-to-action map.

Recovery procedures should define:

* proof of entitlement to replacement;
* invalidation or expiry of the old credential;
* whether public-role continuity is required;
* whether old and new credentials remain unlinkable;
* appeal and fraud procedures;
* emergency recovery limitations.

### 14.4 Accountable public roles

Some public roles may require continuity and attribution.

In those cases, PNyx may use a separate accountable role credential.

This must not force ordinary private civic participation to become publicly attributable.

---

## 15. Transport privacy

Zero-knowledge credentials do not automatically hide network metadata.

The credential layer proves:

* standing;
* authorization;
* uniqueness;
* policy compliance.

It does not independently hide:

* IP address;
* timing;
* traffic volume;
* browser fingerprint;
* device fingerprint;
* attachment metadata;
* writing style;
* voluntary self-disclosure.

Anonymous transport is therefore an optional implementation capability.

Possible transports include:

```text
Mixnet transport
Tor transport
Institutional relay transport
Community relay transport
Direct transport
Future privacy transport
```

These are non-normative implementation options.

PNyx Core must remain transport-agnostic.

No external network, token, company or transport provider may control civic eligibility or the Civic Loop.

---

## 16. Threat model

The implementation must consider at least:

* malicious standing verifiers;
* colluding verifiers and issuers;
* malicious issuer quorum members;
* compromised issuer keys;
* malicious civic ingress services;
* nullifier suppression;
* fabricated duplicate detection;
* correlation across epochs;
* network-level traffic analysis;
* credential theft;
* device compromise;
* coerced credential use;
* verifier censorship;
* mass arbitrary revocation;
* governance capture;
* Sybil issuance;
* recovery abuse;
* side-channel leakage;
* writing-style deanonymization.

The threat model must distinguish:

* cryptographic anonymity;
* pseudonymity;
* network privacy;
* content privacy;
* legal confidentiality;
* social anonymity.

The subsystem must not claim stronger privacy than it can demonstrate.

---

## 17. Governance requirements

Governance must specify:

* who may define standing policy;
* who may verify standing;
* how standing verifiers are authorized;
* who may become an issuer;
* issuer quorum threshold;
* issuer diversity requirements;
* key generation and rotation;
* issuer removal;
* verifier removal;
* policy versioning;
* emergency procedures;
* challenge and appeal procedures;
* recovery procedures;
* revocation authority;
* audit requirements;
* cryptographic upgrade procedures.

The issuer quorum must not silently redefine civic membership.

The standing verifier must not silently expand evidence collection.

The civic ingress must not silently change uniqueness rules.

Every consequential policy change must produce a public, versioned artifact.

---

## 18. Artifact schemas

### 18.1 StandingVerificationPolicy

```text
StandingVerificationPolicy {
  policyId
  schemaVersion
  civicScope
  eligibilityRule
  authorizedVerifierClasses[]
  acceptedEvidenceCategories[]
  prohibitedEvidenceCategories[]
  retentionPolicy
  issuanceAuthorizationProfile
  appealPolicy
  validFrom
  validUntil
  governanceArtifactRef
}
```

### 18.2 CivicCredentialPolicy

```text
CivicCredentialPolicy {
  policyId
  schemaVersion
  credentialClass
  permittedCivicScopes[]
  privateAttributes[]
  selectivelyDisclosedAttributes[]
  allowedActionTypes[]
  holderBindingProfile
  cryptographicProfileId
  issuanceEpoch
  expiryRule
  revocationProfile
  recoveryProfile
  governanceArtifactRef
}
```

### 18.3 IssuerQuorumPolicy

```text
IssuerQuorumPolicy {
  policyId
  schemaVersion
  quorumId
  issuerSet[]
  threshold
  keyGenerationProfile
  keyRotationPolicy
  issuerReplacementPolicy
  compromisePolicy
  auditPolicy
  validFrom
  validUntil
  governanceArtifactRef
}
```

### 18.4 CivicActionVerificationReceipt

```text
CivicActionVerificationReceipt {
  receiptId
  schemaVersion
  protocolVersion
  civicScope
  loopId
  actionType
  epoch
  participationPolicyId
  standingPolicyId
  credentialPolicyId
  issuerQuorumPolicyId
  cryptographicProfileId
  canonicalArtifactHash
  nullifier?
  verificationOutcome
  rejectionReason?
  revocationCommitmentRef?
  ingressIdentifier
  verificationTimestamp
  challengeRef?
}
```

### 18.5 CredentialLifecycleEvent

Credential lifecycle events are accountable infrastructure records.

They must not expose citizen identity.

```text
CredentialLifecycleEvent {
  eventId
  schemaVersion
  credentialClass
  lifecycleState
  policyId
  issuerQuorumId
  epoch
  publicCommitment?
  reasonCode?
  timestamp
  governanceRef?
}
```

A lifecycle event must not contain a stable holder identifier unless an implementation profile proves that the identifier cannot be used to correlate civic actions.

---

## 19. Data model

The subsystem introduces the following logical entities:

### StandingVerificationPolicy

Cardinality:

```text
one civic scope
→ many policy versions
```

Relationships:

```text
ParticipationPlan
→ references one StandingVerificationPolicy version

StandingVerificationPolicy
→ authorizes one or more verifier classes
```

### CivicCredentialPolicy

Cardinality:

```text
one credential class
→ many policy versions
```

Relationships:

```text
CivicCredentialPolicy
→ references one cryptographic profile

CivicCredentialPolicy
→ references one or more allowed action policies

CivicCredentialPolicy
→ references one revocation profile
```

### IssuerQuorumPolicy

Cardinality:

```text
one quorum
→ many quorum epochs
```

Relationships:

```text
IssuerQuorumPolicy
→ contains many issuers

Credential issuance
→ requires one active quorum policy

One issuer
→ may participate in many quorum epochs
```

### CivicReceipt

Relationships:

```text
one accepted civic action
→ one CivicReceipt

one CivicReceipt
→ zero or one CivicActionVerificationReceipt component
```

The verification component is optional because some low-risk or bootstrap participation profiles may not use the ZK credential subsystem.

### Nullifier domain

Relationships:

```text
one ParticipationPlan
→ defines zero or more NullifierDomains

one accepted constrained action
→ produces exactly one nullifier per required domain

one nullifier
→ may appear in only one accepted receipt inside its domain
```

### ParticipationAudit

Relationships:

```text
one ParticipationAudit
→ evaluates many CivicReceipts

one ParticipationAudit
→ evaluates many verification receipts

one ParticipationAudit
→ references relevant policy versions and issuer epochs
```

---

## 20. Schema versioning and public storage

All public artifacts defined by this subsystem must follow the same canonical rules as other PNyx artifacts:

* explicit schema version;
* canonical serialization;
* deterministic content hashing;
* provenance record;
* immutable historical versions;
* public governance references;
* append-only correction history;
* compatibility and migration rules.

Private credentials and raw onboarding evidence are not public artifacts.

Public storage may contain:

* policy artifacts;
* verifier and issuer governance records;
* public keys;
* epoch commitments;
* verification receipts;
* revocation commitments;
* audit reports;
* challenge and appeal artifacts.

Public storage must not contain:

* private holder credentials;
* holder secrets;
* identity documents;
* the private basis of standing;
* complete private revocation witnesses.

---

## 21. Bootstrap debt

This subsystem is recorded as deferred infrastructure.

### Debt item

**Title:** Privacy-preserving threshold civic credentials

**Reason for deferral:**

The subsystem requires:

* formal threat modelling;
* credential construction selection;
* threshold key infrastructure;
* holder-binding design;
* proof circuits or equivalent proof statements;
* nullifier storage;
* privacy-preserving revocation;
* recovery mechanisms;
* policy and schema integration;
* security audits;
* governance of issuers and verifiers.

### Bootstrap behaviour

Before this debt is resolved, PNyx may use a simpler, explicitly documented standing-authorization mechanism for low-risk pilots.

The bootstrap mechanism must disclose its limitations.

It must not claim:

* cryptographic anonymity;
* threshold issuance;
* cross-action unlinkability;
* private revocation;
* protection from network correlation.

### Exit criteria

The debt may be considered resolved only after:

* a complete cryptographic specification exists;
* the threat model is documented;
* the implementation is reproducible;
* threshold issuance is demonstrated;
* nullifier uniqueness is demonstrated;
* cross-domain unlinkability is tested;
* expiry and revocation are tested;
* recovery is tested;
* issuer replacement is tested;
* external cryptographic review is complete;
* governance approves consequential use.

---

## 22. Priority relative to other deferred capabilities

The recommended implementation order is:

```text
1. Deterministic civic artifacts and public receipts
2. Basic standing authorization
3. Participation plans and participation audits
4. Formed bodies and sortition infrastructure
5. Delegation constraints and revocation
6. Experimental privacy-preserving civic credentials
7. Audited threshold credential deployment
8. Consequential-use deployment
```

Experimental work may begin earlier.

The ZK credential subsystem must not become a blocker for the first functional Civic Loop.

It must become a requirement before PNyx makes strong claims about anonymous, unlinkable or privacy-preserving consequential participation.

---

## 23. MVP acceptance criteria

A research MVP should demonstrate:

* one civic scope;
* one standing-verification policy;
* at least three independent issuers;
* a threshold smaller than the total issuer set;
* successful blind threshold issuance;
* failure below the issuance threshold;
* a holder-bound private credential;
* one constrained civic action type;
* one scope-specific nullifier domain;
* proof verification without identity disclosure;
* deterministic duplicate-action rejection;
* unlinkability across at least two domains;
* credential expiry;
* revocation enforcement;
* credential replacement;
* public verification receipts;
* integration with `CivicReceipt`;
* integration with `ParticipationPlan`;
* integration with `ParticipationAudit`;
* documented transport and metadata limitations.

A consequential-use MVP additionally requires:

* external cryptographic audit;
* governance approval;
* operational key ceremonies;
* issuer diversity;
* recovery drills;
* issuer compromise drills;
* public monitoring;
* formal incident procedures.

---

## 24. Canonical integration requirements

When this document enters the canonical PNyx Core corpus:

1. `00_INDEX_AND_MAP.md` must include `ZK_CIVIC_CREDENTIALS.md` under the identity, authorization and cryptographic infrastructure layer.

2. The dependency graph must include:

```text
ParticipationPlan
→ StandingVerificationPolicy
→ CivicCredentialPolicy
→ IssuerQuorumPolicy
→ CivicActionVerificationReceipt
→ CivicReceipt
→ ParticipationAudit
```

3. `BOOTSTRAP_DEBT_REGISTER.md` must include the debt item defined in Section 21.

4. `SCHEMAS.md` must include or reference the schemas defined in Section 18.

5. `DATA_MODEL.md` must include or reference the entities and relationships defined in Section 19.

6. `CORE_V03_RECONCILIATION.md` must record that privacy-preserving uniqueness proofs remain deferred infrastructure and are not part of the initial operational baseline.

7. Existing proposal lifecycle states must remain unchanged.

8. Credential lifecycle states must be documented only as subsystem infrastructure states.

---

## 25. Final invariant

The subsystem must preserve all of the following simultaneously:

```text
The person remains private.

The standing is valid.

The action is authorized.

The constrained action is unique.

The artifact is public.

The verification is auditable.

The system does not create a global civic identity.
```

This is the intended relationship between privacy and public accountability in PNyx:

> Private person.
> Verifiable civic standing.
> Constrained authorization.
> Public artifact.
> Auditable memory.
