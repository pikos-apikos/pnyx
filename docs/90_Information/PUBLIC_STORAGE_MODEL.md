# PUBLIC_STORAGE_MODEL.md

## Purpose

This document defines the public storage model for PNyx.

PNyx is designed as a **public-file-first civic system**.

The goal is to make civic memory inspectable, portable, mirrorable, forkable, and recoverable without depending on a private database or a single hosted application.

The core rule is:

> **Everything that shapes public judgment must be public.**

This does not mean that every private human detail must be exposed.

It means that every proposal, argument, review, evidence packet, decision, implementation path, execution update, and audit event that influences civic judgment must exist as a public artifact.

---

## Core principle

PNyx treats public files as the source of truth.

The database is not the source of truth.

The database is a derived read model.

```text
public civic objects
  → signed manifests
  → importers
  → read models
  → UI / API / search / analytics
```

If the database disappears, the system should be able to rebuild its state from public objects and manifests.

If the main server disappears, mirrors should still be able to preserve and serve the public corpus.

---

## Why public-file-first?

Traditional civic platforms usually keep truth inside a backend database.

That creates several problems:

* the public cannot easily inspect the full history
* database admins become hidden custodians of civic memory
* migrations can rewrite or erase context
* APIs can selectively expose only part of the record
* shutdown of the platform can destroy the civic archive
* future researchers cannot easily reconstruct public reasoning
* AI systems cannot reliably learn from structured civic artifacts

PNyx takes the opposite approach.

Public civic memory should be:

* exportable
* content-addressed
* signed
* append-only
* mirrorable
* independently verifiable
* rebuildable into many different read models

---

## Storage model summary

PNyx separates storage into three layers:

```text
1. Canonical public objects
2. Public manifests
3. Derived read models
```

### 1. Canonical public objects

Immutable files containing civic artifacts.

Examples:

* proposal
* validation report
* AI review
* human expert review
* evidence item
* argument
* challenge
* decision
* implementation fork
* execution update
* audit event

These are the primary civic record.

### 2. Public manifests

Signed index files that link objects together.

Manifests define what objects are part of a network, epoch, proposal thread, decision, or execution path.

They make public memory discoverable.

### 3. Derived read models

Databases, search indexes, graph views, vector indexes, and analytics tables built from public objects.

These are disposable.

They can be rebuilt.

They are useful for performance and usability, but they are not the canonical record.

---

## Object storage layout

A simple local prototype may store objects like this:

```text
data/public/
├─ objects/
│  ├─ proposal/
│  ├─ proposal_revision/
│  ├─ validation_report/
│  ├─ ai_review/
│  ├─ human_review/
│  ├─ evidence_item/
│  ├─ argument/
│  ├─ challenge/
│  ├─ decision/
│  ├─ implementation_fork/
│  ├─ implementation_path/
│  ├─ execution_task/
│  ├─ execution_update/
│  ├─ audit_event/
│  ├─ participation_policy/
│  ├─ participation_plan/
│  ├─ participant_body/
│  ├─ targeted_invitation/
│  ├─ sortition_configuration/
│  ├─ sortition_result/
│  ├─ civic_brief/
│  ├─ attention_allocation_policy/
│  ├─ reach_decision/
│  ├─ attention_delegation/
│  ├─ civic_receipt/
│  ├─ participant_compensation_record/
│  ├─ participation_audit/
│  └─ governance_health_report/
├─ manifests/
│  ├─ latest.json
│  ├─ epochs/
│  ├─ proposals/
│  ├─ decisions/
│  └─ mirrors.json
└─ indexes/
   ├─ proposals.jsonl
   ├─ decisions.jsonl
   ├─ execution.jsonl
   └─ objects.jsonl
```

For a hosted or distributed deployment, the same files may be mirrored to:

* static HTTP storage
* S3-compatible object storage
* IPFS
* Git repositories
* public archive services
* community mirrors
* institutional archives

The protocol should not depend on any single storage provider.

---

## Content addressing

Every canonical object should be content-addressed.

The object ID is derived from the canonical serialized content.

Recommended format:

```text
sha256:<hex-digest>
```

Example:

```text
sha256:9f7b1e4c2a0f4a8e...
```

A content-addressed object should not be mutated.

If the content changes, it becomes a new object with a new hash.

---

## Canonical serialization

To make hashes stable, PNyx needs deterministic serialization.

Recommended rule for JSON objects:

* UTF-8 encoding
* sorted object keys
* no insignificant whitespace
* normalized timestamps
* explicit schema version
* no runtime-only fields in canonical body

Example canonicalization target:

```json
{"body":{"title":"Example"},"created_at":"2026-01-01T00:00:00Z","schema":"proposal.v1","type":"proposal"}
```

The prototype may start with a simple canonical JSON implementation.

Later versions can adopt a formal canonicalization standard.

---

## Base object envelope

Every public object should follow a common envelope.

```json
{
  "id": "sha256:...",
  "type": "proposal",
  "schema": "proposal.v1",
  "created_at": "2026-01-01T00:00:00Z",
  "created_by": "did:pnyx:example",
  "body": {},
  "refs": [],
  "signatures": []
}
```

### Fields

#### `id`

Content-derived identifier.

#### `type`

The civic object type.

Examples:

```text
proposal
ai_review
decision
execution_update
```

#### `schema`

Schema name and version.

Examples:

```text
proposal.v1
ai_review.v1
implementation_fork.v1
```

#### `created_at`

Timestamp in UTC.

Recommended format:

```text
YYYY-MM-DDTHH:mm:ssZ
```

#### `created_by`

Public actor identifier.

For the prototype this may be pseudonymous.

Examples:

```text
did:pnyx:demo-user-1
did:pnyx:ai-worker-legal-reviewer
system:pnyx-demo
```

#### `body`

Object-specific content.

#### `refs`

References to related objects.

#### `signatures`

One or more signatures over the canonical object.

The prototype may leave signatures empty during early development, but the field should exist from the start.

---

## Reference model

Objects link to each other through explicit references.

Example:

```json
{
  "ref": "sha256:...",
  "type": "proposal",
  "relationship": "reviews"
}
```

Common relationships:

```text
revises
reviews
supports
opposes
cites
challenges
responds_to
derived_from
implements
updates
supersedes
belongs_to_manifest
```

This allows the public corpus to form a graph of civic reasoning.

---

## Object types

### Proposal

A structured public proposal.

```json
{
  "type": "proposal",
  "schema": "proposal.v1",
  "body": {
    "title": "Municipal energy upgrade fund for public schools",
    "summary": "Create a fund for insulation, lighting, and heating upgrades.",
    "problem": "Public schools have high energy costs and poor building efficiency.",
    "proposed_action": "Create a dedicated municipal fund.",
    "affected_groups": ["students", "teachers", "municipality", "taxpayers"],
    "status": "submitted",
    "canonical_public_state": "INTAKE"
  }
}
```

Every exported proposal object must carry `canonical_public_state`, derived from the internal state through the mapping in `../80_Runtime/STATE_MACHINE.md` §4.5.
Internal state names may evolve; the canonical public vocabulary is the stable contract for public memory, mirrors, and importers.

### Validation report

A first-pass structured check.

```json
{
  "type": "validation_report",
  "schema": "validation_report.v1",
  "body": {
    "proposal_ref": "sha256:...",
    "is_discussable": true,
    "missing_fields": ["estimated_cost", "funding_source"],
    "clarifying_questions": [],
    "flags": []
  }
}
```

### AI review

A bounded public reasoning artifact produced by a model.

```json
{
  "type": "ai_review",
  "schema": "ai_review.v1",
  "body": {
    "proposal_ref": "sha256:...",
    "role": "economic_reviewer",
    "skill_version": "economic-reviewer/v0.1",
    "model": {
      "provider": "openai-compatible",
      "name": "example-model",
      "version": "unknown"
    },
    "summary": "The proposal is financially plausible but lacks cost estimates.",
    "findings": [],
    "risks": [],
    "missing_information": [],
    "assumptions": [],
    "citations": [],
    "confidence": 0.72
  }
}
```

PNyx does not require exposing a model's hidden chain of thought.

The public artifact is the model's accountable civic output.

### Evidence item

A cited source or submitted evidence object.

```json
{
  "type": "evidence_item",
  "schema": "evidence_item.v1",
  "body": {
    "title": "Energy efficiency report",
    "source_url": "https://example.org/report.pdf",
    "source_type": "public_document",
    "summary": "The report estimates energy savings from school insulation.",
    "retrieved_at": "2026-01-01T00:00:00Z",
    "content_hash": "sha256:..."
  }
}
```

### Decision

A public judgment result.

```json
{
  "type": "decision",
  "schema": "decision.v1",
  "body": {
    "proposal_ref": "sha256:...",
    "status": "accepted",
    "decision_method": "demo_public_judgment",
    "summary": "The proposal is accepted for implementation exploration.",
    "conditions": ["Provide cost estimate", "Identify responsible authority"],
    "created_implementation_fork": true
  }
}
```

### Implementation fork

A decision-to-action object.

```json
{
  "type": "implementation_fork",
  "schema": "implementation_fork.v1",
  "body": {
    "decision_ref": "sha256:...",
    "paths": [
      {
        "type": "institutional_action",
        "title": "Submit formal request to municipality",
        "status": "planned"
      },
      {
        "type": "public_interest_venture",
        "title": "Create open-source school energy dashboard",
        "status": "exploring"
      }
    ]
  }
}
```

### Execution update

A public update on implementation progress.

```json
{
  "type": "execution_update",
  "schema": "execution_update.v1",
  "body": {
    "implementation_path_ref": "sha256:...",
    "status": "in_progress",
    "summary": "A municipal office has been identified as responsible authority.",
    "blockers": [],
    "next_steps": ["Prepare formal submission packet"]
  }
}
```

### Audit event

A public event describing system or civic-state change.

```json
{
  "type": "audit_event",
  "schema": "audit_event.v1",
  "body": {
    "event_type": "object_published",
    "object_ref": "sha256:...",
    "actor": "system:pnyx-demo",
    "summary": "AI review object published."
  }
}
```

### Participation objects

The participation layer (`45_Participation/PARTICIPATION_MODEL.md`, `SORTITION.md`, `ATTENTION_AND_REACH.md`) adopted by `99_Reference/CORE_V03_RECONCILIATION.md` §4.3 registers fourteen new canonical object types. Each follows the base object envelope above. Disclosure classes are noted per type; the default is public, matching the core rule of this document. See the "Participation disclosure classes" table below the Privacy boundary section for the exceptions.

#### ParticipationPolicy

Scope-level, epoch-bound participation defaults.

```json
{
  "type": "participation_policy",
  "schema": "participation_policy.v1",
  "body": {
    "civic_scope": "municipality:example-town",
    "epoch_ref": "sha256:...",
    "default_modes_by_tier": {
      "trivial": [],
      "non_trivial": ["open", "targeted_invitation"],
      "high_impact": ["open", "sortition", "civic_jury"]
    },
    "compensation_eligibility_default": true,
    "attention_budget": {
      "unit": "citizen_hours_per_period",
      "value": 400
    },
    "sortition_disabled": false
  }
}
```

Disclosure: public.

#### ParticipationPlan

Per-case plan; absorbs scope/impact mapping, barrier assessment, accessibility plan, and translation requirements (reconciliation record §5).

```json
{
  "type": "participation_plan",
  "schema": "participation_plan.v1",
  "body": {
    "proposal_ref": "sha256:...",
    "version_no": 1,
    "affected_scopes": ["municipality:example-town"],
    "selected_modes": ["open", "targeted_invitation"],
    "expected_barriers": ["time_poverty", "language"],
    "underrepresented_perspectives": ["renters"],
    "compensation_rules_ref": "sha256:...",
    "accessibility_measures": ["plain_language_summary", "sign_language_briefing"],
    "participation_windows": [{"opens_at": "2026-01-05T00:00:00Z", "closes_at": "2026-01-19T00:00:00Z"}]
  }
}
```

Disclosure: public. Revisions are new objects that `supersedes` the prior plan version; history is preserved.

#### ParticipantBody

Record of a formed deliberation/judgment body.

```json
{
  "type": "participant_body",
  "schema": "participant_body.v1",
  "body": {
    "proposal_ref": "sha256:...",
    "participation_plan_ref": "sha256:...",
    "body_type": "sortition",
    "formation_method": "sortition_draw",
    "sortition_result_ref": "sha256:...",
    "term": {"starts_at": "2026-01-20T00:00:00Z", "ends_at": "2026-02-03T00:00:00Z"},
    "judgment_authority": "advisory_recommendation",
    "declared_conflicts": [],
    "pseudonymization": "named_after_term_ends"
  }
}
```

Disclosure: public. Individual member identity follows the body's declared pseudonymization rule (`SORTITION.md` §11); the formation process and outcome remain publicly verifiable regardless.

#### TargetedInvitation

A declared invitation to a person, group, or role.

```json
{
  "type": "targeted_invitation",
  "schema": "targeted_invitation.v1",
  "body": {
    "proposal_ref": "sha256:...",
    "reason": "Direct experience with affected transit corridor.",
    "perspective_sought": "daily commuter with mobility impairment",
    "identification_method": "affected-party outreach via accessibility office",
    "compensated": true,
    "special_judgment_authority": "none",
    "response": "pending"
  }
}
```

Disclosure: public, at the pseudonymization level declared by the applicable `ParticipationPlan` for the invitee's identity.

#### SortitionConfiguration

Published before the registry snapshot commitment (`SORTITION.md` §8).

```json
{
  "type": "sortition_configuration",
  "schema": "sortition_configuration.v1",
  "body": {
    "proposal_ref": "sha256:...",
    "civic_scope": "municipality:example-town",
    "eligible_population_ref": "sha256:...",
    "selection_window": {"opens_at": "2026-01-10T00:00:00Z", "closes_at": "2026-01-12T00:00:00Z"},
    "selection_method": "verifiable_random_function",
    "seed_governance": {"contributors": 3, "commitment_scheme": "commit_reveal"},
    "body_size": 12,
    "stratification": [],
    "replacement_rules": "ranked_overflow_of_same_draw",
    "privacy": "named_after_term_ends"
  }
}
```

Disclosure: public.

#### SortitionResult

Published after the draw; immutable (`SORTITION.md` §9).

```json
{
  "type": "sortition_result",
  "schema": "sortition_result.v1",
  "body": {
    "sortition_configuration_ref": "sha256:...",
    "registry_snapshot_commitment": "sha256:...",
    "seed_material": "revealed-seed-hex",
    "draw_proof_ref": "sha256:...",
    "selected_set": ["pseudo:member-1", "pseudo:member-2"],
    "replacement_chain": [],
    "participant_body_ref": "sha256:...",
    "verification_instructions": "See draw_proof_ref for reproduction procedure."
  }
}
```

Disclosure: **public, with pseudonymization of selected members per the configuration's privacy rule.** The draw process and its verifiability are always public (`SORTITION.md` §6.3); member identity disclosure follows the body's declared pseudonymity level, never the draw mechanics.

#### CivicBrief

Scope-level periodic brief, not an engagement feed.

```json
{
  "type": "civic_brief",
  "schema": "civic_brief.v1",
  "body": {
    "civic_scope": "municipality:example-town",
    "period": "2026-W03",
    "items": [
      {
        "proposal_ref": "sha256:...",
        "action_class": "optional_review",
        "expected_time_commitment": "15 minutes",
        "why_now": "Public review window opens this week."
      }
    ]
  }
}
```

Disclosure: public.

#### AttentionAllocationPolicy

Scope-level, epoch-bound reach-allocation rules (`ATTENTION_AND_REACH.md` §3).

```json
{
  "type": "attention_allocation_policy",
  "schema": "attention_allocation_policy.v1",
  "body": {
    "civic_scope": "municipality:example-town",
    "epoch_ref": "sha256:...",
    "factor_weights": {"classification_result": 0.4, "affected_population": 0.3, "evidence_strength": 0.3},
    "prohibited_bases": ["clicks", "reactions", "purchased_promotion"]
  }
}
```

Disclosure: public.

#### ReachDecision

Audit record of a material reach grant (`ATTENTION_AND_REACH.md` §4).

```json
{
  "type": "reach_decision",
  "schema": "reach_decision.v1",
  "body": {
    "content_ref": "sha256:...",
    "civic_scope": "municipality:example-town",
    "attention_allocation_policy_ref": "sha256:...",
    "reason": "affected_population",
    "duration": "P7D",
    "target_audience": "scope_subscribers",
    "urgency_evidence_ref": null,
    "appeal_ref": null
  }
}
```

Disclosure: public.

#### AttentionDelegation

Scoped, time-limited, revocable delegation of attention (`ATTENTION_AND_REACH.md` §8).

```json
{
  "type": "attention_delegation",
  "schema": "attention_delegation.v1",
  "body": {
    "delegator": "did:pnyx:demo-user-1",
    "delegate": "did:pnyx:demo-user-2",
    "civic_scope": "municipality:example-town",
    "purpose": "monitor_transit_mandate",
    "created_at": "2026-01-01T00:00:00Z",
    "expires_at": "2026-04-01T00:00:00Z",
    "transferable": false,
    "status": "active"
  }
}
```

Disclosure: **visible to the delegator; aggregate-public.** The full delegation record (delegator, delegate, scope, purpose) is readable by the delegator through `DelegationView` (`80_Runtime/READ_MODELS.md` §19.3). The public projection exposes only aggregate concentration signals per delegate and scope (`GOVERNANCE_HEALTH.md`), never a public list of who delegated to whom.

#### CivicReceipt

Issued after a participant performs a material civic action; lifecycle accepted → included → (corrected | superseded | rejected) (`PARTICIPATION_MODEL.md` §12).

```json
{
  "type": "civic_receipt",
  "schema": "civic_receipt.v1",
  "body": {
    "proposal_ref": "sha256:...",
    "action_type": "sortition_participation",
    "policy_ref": "sha256:...",
    "status": "included",
    "inclusion_proof_ref": "sha256:..."
  }
}
```

Disclosure: **private proof, public aggregate** (reconciliation record §4.3). The holder-only object above, containing the participant's inclusion proof, is retrievable only by the holder (`80_Runtime/API_SPEC.md` §10.2 holder-only endpoints). The public artifact is a separate aggregate object recording only that a receipt of a given action type exists and was included, never how the participant acted, unless the participant chooses disclosure or the process requires public attribution.

#### ParticipantCompensationRecord

Links a compensation payment to participant, proposal, role, and treasury release (`PARTICIPATION_MODEL.md` §7.4).

```json
{
  "type": "participant_compensation_record",
  "schema": "participant_compensation_record.v1",
  "body": {
    "participant_ref": "pseudo:member-1",
    "proposal_ref": "sha256:...",
    "participation_role": "sortition_body_member",
    "basis_of_calculation": "hourly_rate_epoch_parameter",
    "release_authorization_ref": "sha256:..."
  }
}
```

Disclosure: public, at the participant-identity disclosure level declared by the applicable `ParticipationPlan`; amount and basis are public regardless (independence from position or vote must remain auditable, `PARTICIPATION_MODEL.md` §7.3).

#### ParticipationAudit

Per-case, pre-decision audit gate (`PARTICIPATION_MODEL.md` §11).

```json
{
  "type": "participation_audit",
  "schema": "participation_audit.v1",
  "body": {
    "proposal_ref": "sha256:...",
    "depth": "full",
    "auditor": "role:participation-auditor-3",
    "declared_conflicts": [],
    "findings": {
      "absent_groups": [],
      "barriers_encountered": ["time_poverty"],
      "sortition_integrity_summary": "no anomalies",
      "delegation_concentration_summary": "below threshold"
    },
    "status": "published"
  }
}
```

Disclosure: public.

#### GovernanceHealthReport

Scope-level diagnostic report of participation, delegation-concentration, compensation-distribution, and sortition-integrity metrics (`45_Participation/GOVERNANCE_HEALTH.md`).

```json
{
  "type": "governance_health_report",
  "schema": "governance_health_report.v1",
  "body": {
    "civic_scope": "municipality:example-town",
    "period": "2026-Q1",
    "metrics": {
      "participation_distribution": "even",
      "delegation_concentration_flags": [],
      "compensation_position_correlation": "none_detected",
      "sortition_integrity_flags": []
    }
  }
}
```

Disclosure: public.

---

## Manifests

A manifest is a signed public index of objects.

Manifests help clients discover objects without relying on a database.

### Network manifest

```json
{
  "type": "manifest",
  "schema": "manifest.network.v1",
  "network": "pnyx-demo",
  "epoch": 1,
  "previous_manifest": null,
  "objects": [
    {
      "id": "sha256:...",
      "type": "proposal",
      "schema": "proposal.v1",
      "locations": [
        "https://example.org/objects/proposal/sha256-....json",
        "ipfs://example"
      ]
    }
  ],
  "created_at": "2026-01-01T00:00:00Z",
  "signatures": []
}
```

### Proposal manifest

A proposal manifest links the full public reasoning thread for one proposal.

```json
{
  "type": "manifest",
  "schema": "manifest.proposal.v1",
  "proposal_ref": "sha256:...",
  "objects": {
    "proposal": "sha256:...",
    "validations": ["sha256:..."],
    "reviews": ["sha256:..."],
    "evidence": ["sha256:..."],
    "arguments": ["sha256:..."],
    "challenges": ["sha256:..."],
    "decisions": ["sha256:..."],
    "implementation_forks": ["sha256:..."],
    "execution_updates": ["sha256:..."],
    "audit_events": ["sha256:..."],
    "participation_plan_versions": ["sha256:..."],
    "participant_body": "sha256:...",
    "targeted_invitations": ["sha256:..."],
    "sortition_result": "sha256:...",
    "participation_audits": ["sha256:..."],
    "civic_receipt_aggregates": ["sha256:..."]
  },
  "created_at": "2026-01-01T00:00:00Z",
  "signatures": []
}
```

---

## Append-only correction model

Published objects are immutable.

If an error is found, the system publishes a correction object.

The old object remains part of the record.

The correction links to the old object.

```json
{
  "type": "correction",
  "schema": "correction.v1",
  "body": {
    "corrects_ref": "sha256:...",
    "reason": "Incorrect cost estimate.",
    "corrected_object_ref": "sha256:..."
  }
}
```

This protects public memory from silent edits.

Corrections are visible.

History remains inspectable.

---

## Derived read models

PNyx may use databases and indexes for usability.

Examples:

* PostgreSQL for structured queries
* SQLite for local demos
* OpenSearch for full-text search
* graph database for relationship exploration
* vector index for semantic search
* static JSON indexes for lightweight clients

These read models are derived.

They can be deleted and rebuilt.

```text
public corpus → importer → read model
```

A correct implementation should treat imported database rows as projections, not canonical truth.

---

## Importer requirements

An importer should:

1. Read one or more manifests.
2. Fetch referenced objects.
3. Validate object schemas.
4. Verify content hashes.
5. Verify signatures when available.
6. Resolve references.
7. Build or update read models.
8. Record import errors as local diagnostics.

Import failures should not silently rewrite public state.

Invalid objects may be quarantined, but not altered.

---

## Mirror model

Any actor may mirror public objects.

A mirror hosts the same content-addressed objects and optionally publishes a mirror manifest.

```json
{
  "type": "mirror_registry",
  "schema": "mirror_registry.v1",
  "network": "pnyx-demo",
  "mirrors": [
    {
      "name": "Example Mirror",
      "base_url": "https://mirror.example.org/pnyx",
      "operator": "did:pnyx:example-mirror",
      "status": "active",
      "last_seen_at": "2026-01-01T00:00:00Z"
    }
  ]
}
```

Mirrors increase resilience.

They also make censorship, selective deletion, and silent rewriting harder.

---

## Public storage backends

PNyx should support multiple storage backends.

### Local filesystem

Best for prototype and development.

```text
./data/public
```

### Static HTTP hosting

Simple and cheap.

Works with object files and manifests.

### S3-compatible object storage

Useful for production-scale cheap storage.

Objects can be served publicly through static URLs.

### Git repository

Useful for small civic corpora, demo data, schema history, and human review.

Not ideal for very large datasets.

### IPFS or content-addressed networks

Useful for distributed mirroring and content addressing.

Should be optional, not required.

---

## Privacy boundary

PNyx is public by default for civic artifacts, not for private human data.

Public by default:

* proposal content
* AI reviews
* expert reviews
* evidence packets
* arguments
* challenges
* decisions
* implementation paths
* execution updates
* audit events
* model metadata
* skill versions
* object hashes
* participation policies, plans, and bodies
* targeted invitations, sortition configurations and results, civic briefs
* attention allocation policies, reach decisions, participation audits, governance health reports

Protected or minimized:

* legal identity
* private contact details
* credentials
* private keys
* biometric data
* raw session material
* sensitive personal data
* private ballots where secrecy is required
* data that enables doxxing, coercion, or retaliation

Guiding rule:

> **Privacy protects people. Transparency constrains power.**

### Participation disclosure classes

Most participation artifacts are public by default, per the table above. Three types carry a declared exception, per `99_Reference/CORE_V03_RECONCILIATION.md` §4.3:

| Object type | Disclosure class |
|---|---|
| `CivicReceipt` | **Private proof, public aggregate.** The holder retrieves their own inclusion proof; the public sees only that a receipt of a given type exists and was included, not how the holder acted. |
| `SortitionResult` | **Public, with pseudonymization per policy.** The draw, its registry snapshot commitment, and its verification path are always public; the identity of selected members is disclosed only at the pseudonymity level the `SortitionConfiguration` declared for that body. |
| `AttentionDelegation` | **Visible to the delegator; aggregate-public.** The delegator sees the full record and the delegate's outputs; the public sees only per-scope concentration signals, never a public delegator-to-delegate map. |

These exceptions narrow disclosure of *who acted* or *who chose whom*; they never hide *that* an action, draw, or delegation occurred. Mirrors and importers must preserve this distinction — a mirror may decline to serve the private-proof half of a `CivicReceipt`, but it must not suppress the public aggregate.

---

## AI reasoning boundary

PNyx stores AI outputs as public reasoning artifacts.

It does not require hidden chain-of-thought disclosure.

A useful AI review should expose:

* summary
* findings
* assumptions
* risks
* missing information
* confidence
* citations
* model metadata
* skill version
* input references
* output hash

This is enough for public accountability without depending on private model internals.

---

## Signatures

Signatures allow actors to verify who published an object.

For the MVP, signatures may be optional.

However, the object envelope should include signature fields from the beginning.

Future signature model:

```json
{
  "signatures": [
    {
      "type": "ed25519",
      "public_key": "...",
      "signature": "...",
      "signed_at": "2026-01-01T00:00:00Z"
    }
  ]
}
```

Objects may be signed by:

* citizens
* AI workers
* expert reviewers
* system operators
* institutional actors
* mirror operators

---

## Versioning

Every object declares a schema version.

Schemas must be public.

Breaking changes should create new schema versions.

Example:

```text
proposal.v1
proposal.v2
ai_review.v1
implementation_fork.v1
participation_plan.v1
sortition_result.v1
civic_receipt.v1
```

Importers should support multiple schema versions when possible.

Deprecated schemas should remain readable.

---

## Public object lifecycle

```text
created locally
  → validated
  → canonicalized
  → hashed
  → signed
  → stored
  → added to manifest
  → imported into read model
  → displayed in UI
  → mirrored
```

A published object should not be overwritten.

A replacement creates a new object.

A correction creates a new object.

A deletion request creates a new public tombstone or redaction record when legally or ethically required.

---

## Redaction model

Public civic systems must handle mistakes and unsafe disclosures.

PNyx should not pretend that append-only storage removes all ethical obligations.

When content must be removed or hidden, the system should publish a redaction record.

```json
{
  "type": "redaction_record",
  "schema": "redaction_record.v1",
  "body": {
    "redacts_ref": "sha256:...",
    "reason_category": "personal_data_exposure",
    "public_reason": "The object exposed private personal information.",
    "replacement_ref": "sha256:..."
  }
}
```

A mirror may choose not to serve redacted content.

However, the existence of the redaction should remain visible.

This balances transparency with human safety.

---

## MVP implementation

For the first working prototype, keep storage simple.

Recommended MVP stack:

```text
local filesystem public objects
+ JSON schemas
+ SHA-256 hashes
+ latest manifest
+ SQLite or PostgreSQL read model
+ importer script
+ export endpoint
```

Minimum required files:

```text
data/public/
├─ objects/
├─ manifests/latest.json
└─ indexes/objects.jsonl
```

Minimum required commands:

```text
pnyx export
pnyx import
pnyx verify
```

---

## Verification command

The prototype should include a verification command.

Expected checks:

* manifest is valid JSON
* all referenced objects exist
* object hashes match content
* object schemas are valid
* references point to known objects when required
* signatures are valid when present

Example output:

```text
PNyx public corpus verification

Network: pnyx-demo
Manifest: data/public/manifests/latest.json
Objects checked: 128
Hash errors: 0
Schema errors: 0
Missing references: 0
Signature warnings: 12
Status: OK
```

---

## Example local flow

```text
1. User submits proposal
2. API creates proposal object
3. Object is canonicalized and hashed
4. Object is written to data/public/objects/proposal/
5. Manifest is updated
6. Importer updates read model
7. UI displays proposal
8. AI worker creates review objects
9. Reviews are written as public files
10. Manifest is updated again
11. Evidence packet is generated
12. Decision and implementation paths are published
13. Execution updates append over time
```

---

## Anti-patterns

Avoid these patterns:

### Hidden canonical database

Do not make the database the only place where truth exists.

### Mutable public files

Do not overwrite public objects after publishing.

### Private AI review state

Do not let AI outputs influence public judgment without publishing the review artifact.

### Opaque moderation

Do not silently remove civic artifacts without a public redaction or moderation record.

### Provider lock-in

Do not make the storage model depend on one cloud provider, model provider, or blockchain.

### Unbounded transparency

Do not expose private human data in the name of openness.

---

## Design commitments

PNyx public storage commits to:

1. Public civic artifacts by default.
2. Content-addressed objects.
3. Append-only history.
4. Public manifests.
5. Disposable databases.
6. Rebuildable read models.
7. Mirrorable public memory.
8. Explicit correction records.
9. Explicit redaction records.
10. Privacy boundaries for human safety.
11. AI outputs as accountable public artifacts.
12. Storage-provider neutrality.

---

## Open questions

The prototype does not need to solve everything immediately.

Open questions include:

* Which canonical JSON standard should be adopted long term?
* Should manifests be epoch-based, proposal-based, or both?
* What signature scheme should be used first?
* How should mirrors be ranked or trusted?
* How should redaction requests propagate across mirrors?
* How should private identity map to public pseudonymous actors?
* How should very large evidence files be stored and hashed?
* Should public corpora be publishable as Git repositories?
* Should IPFS support be included in the MVP or kept optional?
* How should read model rebuilds be tested deterministically?

---

## Summary

PNyx stores civic truth as public artifacts.

Applications, APIs, and databases are views over that public memory.

This allows the system to be inspected, mirrored, forked, rebuilt, criticized, and improved.

The storage model is not just a technical choice.

It is a civic principle:

> **Public decisions require public memory.**
