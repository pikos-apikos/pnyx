# Civic Workspace and Mandate Runtime RFC

**Status:** Proposal RFC — non-normative, under review  
**Scope:** Civic execution, workspace sovereignty, delegated authority, approval, verification, invalidation, and recovery  
**Intended follow-up:** A separate normative system patch and implementation plan if this proposal is accepted  
**Related documents:**
- [`../10_Constitutional/GOVERNANCE.md`](../10_Constitutional/GOVERNANCE.md)
- [`../10_Constitutional/ARCHITECTURE.md`](../10_Constitutional/ARCHITECTURE.md)
- [`../20_Protocol_Core/PROTOCOL.md`](../20_Protocol_Core/PROTOCOL.md)
- [`../20_Protocol_Core/ROUTING.md`](../20_Protocol_Core/ROUTING.md)
- [`../40_Identity/IDENTITY_AND_MEMBERSHIP.md`](../40_Identity/IDENTITY_AND_MEMBERSHIP.md)
- [`../50_Economics/PUBLIC_IP_MODEL.md`](../50_Economics/PUBLIC_IP_MODEL.md)
- [`../60_Skills/SKILLS.md`](../60_Skills/SKILLS.md)
- [`../80_Runtime/EVENT_MODEL.md`](../80_Runtime/EVENT_MODEL.md)
- [`../80_Runtime/PERMISSIONS.md`](../80_Runtime/PERMISSIONS.md)
- [`../80_Runtime/STATE_MACHINE.md`](../80_Runtime/STATE_MACHINE.md)
- [`../90_Information/PUBLIC_STORAGE_MODEL.md`](../90_Information/PUBLIC_STORAGE_MODEL.md)
- [`SYSTEM_PATCH_v2.md`](SYSTEM_PATCH_v2.md)

---

## 1. Purpose

The Pnyx Civic Loop currently reaches public decision, routing, implementation tracking, and audit. This proposal identifies a missing operational primitive between an approved civic decision and accountable work:

> **The people and systems responsible for a civic mandate need a governed workspace in which that mandate can become bounded, reviewable, verifiable execution.**

A public decision that is handed to disconnected email threads, private folders, ticket systems, procurement portals, ERP systems, contractors, and institutional silos has not yet closed the loop.

Without a workspace:

```text
social request
→ deliberation
→ decision
→ institutional hand-off
→ hope
```

With a workspace:

```text
social request
→ deliberation
→ mandate
→ civic workspace
→ accountable execution
→ independent verification
→ public outcome
→ public memory
```

This RFC proposes four related but distinct concepts:

1. **Civic Workspace** — the sovereign human and machine work environment.
2. **Mandate Runtime** — the policy and state-transition machinery controlling authority.
3. **Civic Execution Ledger** — the append-only causal history of decisions, work, claims, reviews, and consequences.
4. **Federated Provenance Layer** — the verifiable relationship between a workspace and external systems of record.

The proposal does not make these concepts normative yet. It is a decision package for repository review.

---

## 2. Problem Statement

Most participation systems stop at one or more of the following:

- complaint;
- petition;
- consultation;
- proposal;
- public comment;
- vote;
- decision publication.

They rarely provide the people executing the decision with a shared operational environment derived from the public mandate.

Execution then becomes fragmented across:

- email;
- chat;
- spreadsheets;
- project-management tools;
- legal case folders;
- Git repositories;
- document-management systems;
- procurement platforms;
- contractor portals;
- financial and ERP systems;
- private model conversations.

This produces a structural discontinuity:

```text
public legitimacy ≠ accountable execution
```

An approved decision does not automatically establish:

- an accountable owner;
- exact scope and non-goals;
- bounded authority;
- required evidence;
- typed deliverables;
- review responsibilities;
- public/private boundaries;
- conditions on further action;
- a recovery path when evidence later fails;
- a durable public record linking the request to the outcome.

The Civic Workspace is proposed as the execution half of the Civic Loop.

---

## 3. Architectural Position

### 3.1 Civic Workspace

A Civic Workspace is the governed environment in which humans, language models, software agents, and deterministic services work on one civic or professional mandate.

It is simultaneously:

- a mandate container;
- a professional work environment;
- an artifact repository;
- an execution state projection;
- an approval surface;
- a collaboration protocol;
- a public window;
- an audit and provenance boundary.

It is not merely a chat room, shared folder, ticket board, or public dashboard.

### 3.2 Mandate Runtime

The Mandate Runtime enforces:

- what was authorised;
- who may act;
- which capabilities they receive;
- which evidence and artifacts are required;
- which state transitions are permitted;
- which conditions block specific capabilities;
- when human approval is mandatory;
- what happens after invalidation or failure.

### 3.3 Civic Execution Ledger

The Civic Execution Ledger records the causal sequence:

```text
who acted
→ under which role
→ from which mandate
→ using which artifact revisions
→ exercising which capability
→ producing which claim or result
→ reviewed by whom
→ followed by which consequence
```

It does not require blockchain. An append-only, hash-chained event stream and content-addressed artifacts are sufficient for the first implementation.

### 3.4 Federated Provenance Layer

The Pnyx should not initially replace municipal ERP, procurement, banking, document, Git, or registry systems.

External systems may remain authoritative for their own domain actions. The workspace records signed or otherwise verifiable references such as:

```text
external system
external record identifier
operation type
issuer
occurred-at time
payload digest
signature or verification method
retrieval or audit reference
verification status
```

For example, the ERP may execute a payment while the Civic Workspace records a `PaymentEventProof` linking the payment to the approved milestone and acceptance artifact.

---

## 4. Workspace Sovereignty

The canonical workspace must not be inherently owned by a model provider or hosted Pnyx service.

It may live on:

- a person's disk;
- a Git repository;
- a NAS;
- an organisation-controlled server;
- municipal or state infrastructure;
- a community node;
- a hosted Pnyx deployment.

The core invariant is:

> **The workspace is the source of truth. Models are replaceable.**

A user may work today with one model, tomorrow with another provider, and later with a local model. Continuity must come from portable artifacts, state snapshots, role contracts, decisions, and execution receipts rather than provider-owned chat history.

A possible local layout is:

```text
workspace/
├── mandate/
├── context/
├── sources/
├── evidence/
├── commands/
├── decisions/
├── conditions/
├── artifacts/
├── reviews/
├── receipts/
├── provenance/
├── private/
├── collaborative/
├── public/
└── workspace.yaml
```

Publication is a deliberate projection or replica of a canonical artifact. It does not transfer ownership of the workspace to the publishing platform.

```text
local canonical artifact
        ├── publish to a Pnyx node
        ├── mirror to Git
        ├── export to static HTML or PDF
        ├── submit to a public authority
        └── replicate to public storage
```

---

## 5. Human and Agent Collaboration

The human owner should not be required to act as the message bus between models.

Agents may:

- ask bounded questions;
- request work from another role;
- propose solutions;
- challenge assumptions;
- perform adversarial review;
- record objections;
- preserve disagreement;
- produce agreement artifacts;
- convert accepted agreements into proposed commands.

Official communication must be promoted from transient conversation into typed, versioned artifacts such as:

```text
WorkspaceQuestion
WorkspaceAnswer
WorkRequest
Proposal
Objection
DisagreementRecord
ReviewFinding
AgreementArtifact
HumanDecisionRequest
```

The rule is:

> **Conversation is transient. Workspace artifacts are authoritative.**

An agent may request another role, but it does not grant authority directly. The orchestrator validates the request and issues a bounded command if the mandate, permissions, prerequisites, execution envelope, and expected output allow it.

```text
Agent A requests legal review
→ orchestrator checks policy and budget
→ Legal Review role receives bounded command
→ output returns as typed artifact
```

The human exits the operational message-relay loop, not the governance loop.

Human intervention remains mandatory for defined boundaries such as:

- mandate change;
- public publication or withdrawal;
- protected-data disclosure;
- legal or contractual commitment;
- financial commitment;
- irreversible external action;
- role or permission change;
- high-impact unresolved disagreement;
- action beyond the configured trust radius.

---

## 6. Recursive Delegation Graph

The narrative phrase **Fractal Pnyx** describes a useful repeated pattern, but it must not imply that governance, authority, risk, or legitimacy are identical at every scale.

The protocol-level model is a **Recursive Delegation Graph**.

A workspace may create bounded child workspaces or collaborate with peer workspaces. Each workspace can contain the repeated operational pattern:

```text
request
→ context
→ deliberation
→ agreement or decision
→ mandate
→ execution
→ review
→ artifact
→ memory
```

What repeats:

- roles;
- contexts;
- commands;
- artifacts;
- approval gates;
- evidence and review;
- provenance;
- bounded execution.

What does not remain self-similar:

- political legitimacy;
- legal authority;
- evidentiary burden;
- identity assurance;
- risk;
- reversibility;
- public review requirements;
- cryptographic and operational trust assumptions.

The authority model is therefore a graph of delegated capabilities, not an infinitely repeated assembly and not necessarily a strict organisational hierarchy.

### 6.1 Workspace Delegation Contract

A parent-to-child relationship must be represented by a versioned `WorkspaceDelegationContract` containing at least:

```yaml
workspace_delegation_contract:
  id: delegation-883a2b
  parent_workspace: ws-playground
  child_workspace: ws-technical-review

  mandate:
    statement: Produce a technical safety assessment for playground X.
    source_decision: decision-104
    source_revision: 3

  scope:
    allowed_actions:
      - READ_MAINTENANCE_RECORDS
      - ISSUE_INSPECTION_COMMANDS
      - PRODUCE_INSPECTION_REPORT
    prohibited_actions:
      - AUTHORIZE_BUDGET
      - SIGN_CONTRACT
      - MODIFY_PARENT_MANDATE
    non_goals:
      - contractor selection
      - political approval of reconstruction

  inputs:
    - artifact: initial-evidence-set
      revision: 2

  required_outputs:
    - InspectionReport.v1
    - RiskAssessment.v1

  execution_envelope:
    budget: 0
    deadline: 2026-10-01T00:00:00Z
    max_agent_calls: 20

  risk_profile: MEDIUM
  approval_gates:
    - role: MUNICIPAL_ENGINEER
      action: SIGN_FINAL_OUTPUT

  revocation:
    authority: PARENT_WORKSPACE_OWNER
    policy: REVOKABLE_UNTIL_OUTPUT_INGESTION
```

A child workspace may only delegate capabilities that it received and is explicitly permitted to subdelegate.

### 6.2 Workspace Membrane

Child outputs do not automatically mutate the parent workspace.

```text
Parent Workspace
      │
      │ Delegation Gate
      ▼
Child Workspace
      │
      │ evidence, recommendations, receipts, dissent
      ▼
Parent Ingestion Gate
      │
      │ explicit acceptance and state transition
      ▼
Parent Workspace
```

The Parent Ingestion Gate prevents mandate laundering and data contamination. A recommendation from a child remains a recommendation until the parent authorises its use.

---

## 7. Core Invariants

The following invariants define the proposal:

1. **Workspace sovereignty**  
   The canonical workspace remains under the control of its legitimate owner or custodian.

2. **Replaceable executors**  
   Models, providers, agents, and external platforms are replaceable execution participants.

3. **Mandate-bound capability**  
   No capability exists without a traceable delegation chain rooted in a legitimate mandate.

4. **No self-authorisation**  
   A workspace or agent cannot manufacture, widen, or silently reinterpret its own authority.

5. **Exact approval subject**  
   Approval authorises an exact transition over exact artifact revisions, not an ambiguous ticket description.

6. **Dissent visibility**  
   Material disagreement must remain visible and traceable through aggregation and publication.

7. **Claim separation**  
   Authorisation, reported execution, and independent verification are different facts.

8. **Causal correction**  
   Corrections append new events and propagate through causal dependencies; they do not rewrite history.

9. **Bounded delegation**  
   A child workspace receives no more authority than the delegating graph permits.

10. **Proportional governance**  
    Review intensity and approval ceremony must be proportional to risk and reversibility.

11. **Immutable history, fallible claims**  
    The ledger is immutable; its claims are not infallible.

12. **Compensation rather than erasure**  
    Authority can be revoked. History cannot. Consequences must be compensated, not erased.

---

## 8. Approval Gates

An Approval Gate is not a ticket status change.

> **An Approval Gate is the signed, mandate-bound ratification of an exact state transition over exact artifact revisions, with explicit authority, conditions, dissent, and executable consequences.**

### 8.1 Decision Package

Before approval, the iCMS or another workspace compiler produces a `DecisionPackage`:

```yaml
decision_package:
  id: decision-package-104

  mandate:
    id: mandate-12
    revision: 3

  proposed_transition:
    from: TECHNICAL_REVIEW
    to: PROCUREMENT_PREPARATION

  subject_artifacts:
    - id: InspectionReport
      revision: 4
      digest: sha256:...
    - id: RiskAssessment
      revision: 2
      digest: sha256:...

  accepted_facts:
    - Three structures require replacement.
    - The northern entrance fails the accessibility requirement.

  unresolved_disagreements:
    - disagreement-7

  requested_capabilities:
    - CREATE_CHILD_WORKSPACE
    - PUBLISH_INSPECTION_SUMMARY

  prohibited_effects:
    - AUTHORIZE_PAYMENT
    - SIGN_CONTRACT
    - MODIFY_PARENT_MANDATE

  consequences:
    - Technical review will close.
    - A procurement-preparation workspace will be created.
    - No expenditure will yet be authorised.

  risk_level: MEDIUM
  expires_at: 2026-08-15T18:00:00Z
```

### 8.2 Decision Capsule UX

The approval surface should answer six questions clearly:

1. What exactly am I approving?
2. Under which mandate?
3. What changed from the previous revision?
4. Who disagrees, and why?
5. What will happen after approval?
6. Can the resulting action be revoked or compensated?

Supported decisions may include:

```text
APPROVE
APPROVE_WITH_CONDITIONS
REQUEST_CHANGES
REJECT
ESCALATE_FOR_ADDITIONAL_APPROVAL
```

The UI must show a semantic diff, not only a raw file diff, and must surface dissent rather than hide it in comments.

### 8.3 Signed Decision Event

The approval action produces a signed event rather than mutating a status field:

```yaml
approval_decision:
  id: approval-202
  decision_package: decision-package-104
  package_digest: sha256:...
  decision: APPROVE_WITH_CONDITIONS

  actor:
    account: user-18
    role_assignment: role-77
    acting_as: WORKSPACE_OWNER

  conditions:
    - condition-204

  signature:
    method: webauthn
    signed_digest: sha256:...

  decided_at: 2026-07-30T00:40:00Z
```

The policy engine validates:

- active mandate;
- valid role assignment;
- unchanged subject artifact digests;
- gate expiry;
- separation-of-duties rules;
- required signer count;
- requested capability bounds;
- unresolved mandatory blockers.

Only then may the runtime emit `GateSatisfied` and deterministic bounded commands.

---

## 9. Conditional Approval and Capability Escrow

`APPROVE_WITH_CONDITIONS` is not approval plus a comment.

It is partial authority release:

```text
capabilities granted immediately
+
capabilities held in escrow
```

For example:

```text
Granted now:
✓ create procurement workspace
✓ prepare specifications
✓ request second cost estimate

Held in escrow:
✗ publish tender
✗ commit public funds
✗ sign contract
```

### 9.1 Condition Obligation

A condition is a first-class obligation:

```yaml
condition_obligation:
  id: condition-204

  created_by:
    approval_decision: approval-202
    normative_workspace: ws-playground

  statement: >
    A second independent cost estimate is required before tender publication.

  type: HUMAN_VERIFIABLE

  responsible_workspace: ws-procurement
  responsible_role: PROCUREMENT_OFFICER

  required_evidence:
    artifact_type: IndependentCostEstimate
    minimum_count: 1

  verifier:
    role: MUNICIPAL_FINANCIAL_REVIEWER

  blocks:
    capabilities:
      - PUBLISH_TENDER
      - COMMIT_BUDGET
    transitions:
      - PROCUREMENT_PREPARATION_TO_TENDER_PUBLISHED

  does_not_block:
    - PREPARE_SPECIFICATIONS
    - REQUEST_QUOTATIONS
    - PERFORM_MARKET_RESEARCH

  deadline: 2026-08-15T17:00:00Z
  status: OPEN
```

The condition remains normatively owned by the workspace that imposed it. Operational work may occur in a child or peer workspace through a delegated condition reference.

There is one canonical obligation, not independently editable copies.

### 9.2 Condition Classes

At least four classes are required:

- **Entry condition** — required before a workspace or role activates.
- **Operating constraint** — continuously enforced during execution.
- **Transition condition** — blocks one or more defined transitions.
- **Exit condition** — required before a delegated mandate is accepted as complete.

### 9.3 Human Verification

The machine does not need to claim that it understands whether a cost estimate is substantively correct. It verifies that the required evidence exists and that an authorised reviewer made a signed assessment.

```yaml
condition_verification_receipt:
  id: cvr-91
  condition: condition-204

  evidence:
    artifact: cost-estimate-18
    revision: 2
    digest: sha256:...

  verifier:
    account: user-52
    role_assignment: financial-reviewer-9

  assessment:
    result: SATISFIED
    statement: >
      The estimate is independent and covers the approved technical scope.
    reservations:
      - Groundworks contingency is not included.

  signed_at: 2026-08-10T11:30:00Z
```

The runtime records that a legitimate role judged the evidence sufficient. It does not convert human judgment into infallible machine truth.

### 9.4 Capability Release

```text
ConditionVerificationReceipt
→ ConditionSatisfied
→ CapabilityReleased
→ TransitionGateAvailable
```

A capability held in escrow should normally be one-shot or lease-bound:

```yaml
capability_token:
  capability: PUBLISH_TENDER
  subject: procurement-officer-12
  workspace: ws-procurement
  artifact_digest: sha256:...
  condition_set_digest: sha256:...
  max_uses: 1
  expires_at: 2026-08-12T14:00:00Z
```

This applies Zero-Trust principles to governance: the actor receives exactly the authority required for one bounded action over one defined state.

### 9.5 Waiver

A condition cannot be deleted to make a workflow pass.

A waiver requires a new signed `WaiverDecision` recording:

- the condition being waived;
- the authority allowing waiver;
- justification;
- resulting risk;
- compensating controls;
- affected capabilities and transitions.

The history must show that the condition existed and was not satisfied.

---

## 10. Tripartite Receipt Model

The runtime must preserve the ontological difference between permission, claim, and verification.

### 10.1 Approval Receipt

Records that an authorised role permitted a defined state transition or action.

### 10.2 Execution Receipt

Records that an executor reports that the action occurred.

It is a claim, not proof of successful physical reality.

### 10.3 Verification Receipt

Records that an authorised independent reviewer assessed the execution evidence and reached a defined conclusion.

Possible current-state projections include:

```text
AUTHORIZED
REPORTED_AS_EXECUTED
INDEPENDENTLY_VERIFIED
CONTESTED
FAILED_VERIFICATION
```

Example:

```text
ApprovalReceipt:
The playground gate may be locked.

ExecutionReceipt:
The contractor reports that the lock was installed.

VerificationReceipt:
The inspector confirms the physical installation, time, place, and condition.
```

The workspace must not report physical completion merely because execution was authorised or claimed.

---

## 11. Invalidation and the Mandate Saga

A later finding may invalidate evidence or a verification receipt after dependent capabilities have already been released and consumed.

There is no true rollback of physical or legal history.

> **The runtime performs causal invalidation, containment, and compensating execution — not history deletion.**

### 11.1 Receipt Challenge and Invalidation

A challenge appends a new event:

```yaml
receipt_challenge:
  id: challenge-44
  target_receipt: cvr-91
  reason: SUSPECTED_FRAUD
  evidence:
    - fraud-report-12
  raised_by:
    role: FINANCIAL_AUDITOR
  raised_at: 2026-08-17T10:15:00Z
```

The original reviewer may trigger precautionary suspension, but final invalidation requires the authority defined by policy:

```yaml
receipt_invalidation_decision:
  id: invalidation-18
  target_receipt: cvr-91
  decision: INVALIDATED
  grounds: FRAUDULENT_EVIDENCE
  temporal_effect: EX_TUNC
  authority:
    role: MUNICIPAL_AUDIT_AUTHORITY
  signed_at: 2026-08-18T14:00:00Z
```

### 11.2 Temporal Effect

- `EX_NUNC` — invalid from now onward; earlier reliance may remain valid.
- `EX_TUNC` — invalid from the beginning; dependent actions are marked as relying on an invalid precondition.
- `CONTESTED` — unresolved; new dependent authority is suspended pending a decision.

The selection of temporal effect is a domain and often legal judgment. It must not be inferred freely by an LLM.

### 11.3 Causal Impact Report

The policy engine traverses the provenance graph:

```text
ConditionVerificationReceipt
→ ConditionSatisfied
→ CapabilityReleased
→ CapabilityConsumed
→ CommandIssued
→ ExternalExecutionReceipt
→ Downstream workspace and artifacts
```

It produces a `CausalImpactReport`:

```yaml
causal_impact_report:
  root_invalidation: invalidation-18

  affected:
    - node: condition-204
      effect: REOPEN_AFTER_RELIANCE
    - node: capability-grant-82
      effect: REVOKE_UNUSED_AUTHORITY
    - node: capability-use-19
      effect: MARK_CONSUMED_TAINTED
    - node: tender-publication-7
      effect: CONTESTED
    - node: supplier-submissions
      effect: FREEZE_FURTHER_PROCESSING

  required_response:
    - SUSPEND_DEPENDENT_CAPABILITIES
    - OPEN_REMEDIATION_GATE
    - REQUEST_LEGAL_ASSESSMENT
```

### 11.4 Tainted State

When a condition was relied upon before invalidation, the system must not collapse its history back to `OPEN`.

Relevant states include:

```text
Condition:
OPEN
→ SATISFIED
→ RELIED_UPON
→ INVALIDATED_AFTER_RELIANCE

Capability:
AVAILABLE
→ CONSUMED
→ CONSUMED_TAINTED

Transition:
COMMITTED
→ COMMITTED_UNDER_INVALIDATED_PRECONDITION
```

`tainted` means that future consumers must see the invalid causal basis. It does not mean the event or artifact is deleted.

### 11.5 Automatic Containment

The policy engine may automatically:

- revoke unused dependent capabilities;
- return capabilities to escrow;
- freeze new dependent commands;
- suspend child workspaces;
- mark downstream artifacts as contested;
- prevent new financial commitments;
- create an incident or remediation workspace;
- notify affected authorities and participants.

It must not automatically:

- annul a legal contract;
- determine fraud guilt;
- recover money;
- erase an external publication;
- impose sanctions;

unless a prior, explicit, legally authorised remediation policy grants that capability.

### 11.6 Compensating Execution

Already executed consequences require compensating actions:

```text
approval
→ capability release
→ external execution
→ later invalidation
→ containment
→ remediation decision
→ compensating command
→ external compensation receipt
→ independent verification
```

This is the **Mandate Saga**: the distributed-systems Saga Pattern adapted to authority, legitimacy, responsibility, and public execution.

Each high-impact capability should declare its reversibility and compensation policy in advance:

```yaml
capability_definition:
  id: PUBLISH_TENDER
  reversibility: EXTERNALLY_REVERSIBLE

  compensation:
    command: REQUEST_TENDER_SUSPENSION
    requires_human_gate: true

  on_precondition_invalidation:
    - REVOKE_UNUSED_DEPENDENT_CAPABILITIES
    - FREEZE_SUBMISSIONS
    - OPEN_LEGAL_REVIEW
```

---

## 12. Proportionality and Trust Radius

The runtime must not apply the same ceremony to an inexpensive reversible repair and a national financial commitment.

A risk profile may consider:

```text
financial magnitude
+ public safety impact
+ legal binding effect
+ reversibility
+ privacy sensitivity
+ geographic and social reach
+ executor reliability history
+ dependency depth
```

### Low risk

- one signer;
- concise decision capsule;
- no mandatory adversarial review;
- post-execution sampling audit;
- broad operational autonomy within a small envelope.

### Medium risk

- semantic artifact diff;
- independent reviewer;
- explicit dissent acknowledgement;
- bounded capability token;
- defined verification requirement.

### High risk

- separation of duties;
- multiple role approvals;
- strong identity assurance;
- public review window where appropriate;
- cooling-off period;
- strict one-shot capabilities;
- explicit remediation and compensation plan.

The trust radius changes the approval ceremony and autonomy envelope, not the core invariants.

---

## 13. Identity and Authority

The MVP does not require a complete national identity infrastructure.

The immediate requirement is narrower:

> Prove that a specific account acted from a specific, legitimately assigned role in a specific workspace for a specific period.

A `RoleAssignment` may contain:

```yaml
role_assignment:
  id: role-assignment-77
  subject_account: account-18
  organisation: municipality-x
  workspace: ws-playground
  role: MUNICIPAL_ENGINEER
  capabilities:
    - REVIEW_TECHNICAL_REPORT
    - SIGN_INSPECTION_OUTPUT
  issuer: workspace-authority-1
  valid_from: 2026-07-30T00:00:00Z
  valid_until: 2026-10-01T00:00:00Z
  revocation_status: ACTIVE
```

The protocol need not initially claim universal legal identity or professional status. It records that the authorised organisation assigned this role under a defined mandate.

Stronger deployments may later require role-bound verifiable credentials, professional registries, organisational signatures, or privacy-preserving eligibility proofs.

---

## 14. iCMS as Workspace Compiler

The iCMS 2030 may serve as a metadata-driven workspace compiler rather than only a form or content-management system.

The historical pattern:

```text
metadata
→ fields
→ relations
→ UI
→ behaviour
```

can evolve into:

```text
metadata
→ workspace types
→ contexts
→ roles
→ commands
→ artifacts
→ policies
→ approval gates
→ public/private projections
→ receipts
→ remediation paths
```

A `WorkspaceType` might define:

```yaml
workspace_type: public-infrastructure-project

roles:
  - civic-owner
  - municipal-engineer
  - legal-reviewer
  - financial-controller
  - contractor
  - independent-auditor
  - citizen-review-body

artifacts:
  - InspectionReport
  - TechnicalPlan
  - CostEstimate
  - PermitAssessment
  - TenderPackage
  - ProgressReport
  - AcceptanceCertificate

approval_gates:
  - approve-technical-plan
  - release-procurement-capability
  - accept-completed-work

receipt_types:
  - ApprovalReceipt
  - ExecutionReceipt
  - VerificationReceipt

risk_policy: infrastructure-risk-v1
```

The iCMS compiles metadata into policy-bound human and agent work. It does not make political judgment itself.

---

## 15. Example: Unsafe Playground

### 15.1 Parent Workspace

```text
Workspace: Restore Playground X
Mandate: Restore safe and accessible operation within the approved envelope.
```

The parent creates three child workspaces:

```text
Technical Inspection
Legal and Regulatory Review
Procurement Preparation
```

Each receives a bounded delegation contract.

### 15.2 Technical Child

An inspection agent prepares a draft from records, images, standards, and prior maintenance evidence.

A human engineer corrects and signs the final `InspectionReport`.

The report returns to the parent through an ingestion gate. It does not automatically authorise procurement.

### 15.3 Conditional Procurement Approval

The parent approves procurement preparation with conditions:

```text
Immediate:
- prepare specifications
- request quotations

Escrowed:
- publish tender
- commit funds

Condition:
- obtain a second independent cost estimate
```

A financial reviewer later signs the `ConditionVerificationReceipt`. The runtime releases a one-shot `PUBLISH_TENDER` capability over exact artifact digests.

### 15.4 Physical Execution

The contractor submits an `ExecutionReceipt` claiming completion.

An independent inspector submits a `VerificationReceipt`.

The public view distinguishes:

```text
approved
reported complete
verified complete
```

### 15.5 Later Invalidation

If the cost estimate is later found fraudulent:

1. the receipt becomes `CHALLENGED`;
2. new dependent capabilities freeze;
3. the audit authority decides temporal effect;
4. the causal impact graph marks affected actions;
5. the tender becomes `CONTESTED` rather than deleted;
6. a remediation gate chooses suspension, reissue, continuation under new lawful evidence, or another compensating path;
7. external systems return receipts for actual compensating actions.

The public memory records both the original reliance and the correction.

---

## 16. Proposed Core Objects

This RFC proposes the following candidate objects for later schema work:

```text
CivicWorkspace
WorkspaceType
Mandate
WorkspaceDelegationContract
RoleDefinition
RoleAssignment
CapabilityDefinition
CapabilityToken
DecisionPackage
ApprovalDecision
ConditionObligation
DelegatedConditionReference
ConditionVerificationReceipt
WaiverDecision
ApprovalReceipt
ExecutionReceipt
VerificationReceipt
ReceiptChallenge
ReceiptInvalidationDecision
CausalImpactReport
RemediationDecisionPackage
CompensatingCommand
ExternalEventProof
AgreementArtifact
DisagreementRecord
PublicationRecord
```

This list is intentionally broader than an MVP. Acceptance of the RFC should not imply immediate implementation of every object.

---

## 17. MVP

The first experiment should be a **Git-backed Civic Workspace**, not a federation network, blockchain deployment, national identity platform, or ERP replacement.

### 17.1 Scenario

One parent workspace for a locally observable maintenance request creates two or three child workspaces:

- technical review;
- cost or legal review;
- public acceptance review.

Humans and at least two replaceable LLM execution profiles collaborate through typed artifacts.

### 17.2 Required Capabilities

1. Create a local workspace with an immutable mandate.
2. Spawn a child workspace from a delegation contract.
3. Assign a bounded role to a human or agent executor.
4. Store private, collaborative, and public artifact classes.
5. Issue bounded commands.
6. Produce versioned artifacts with dependency references.
7. Generate a Decision Package.
8. Approve with conditions.
9. Hold one capability in escrow.
10. Satisfy the condition through a human verification receipt.
11. Release and consume a one-shot capability.
12. Record Approval, Execution, and Verification receipts separately.
13. Invalidate one receipt and generate a causal impact report.
14. Export or publish a content-addressed public artifact package.
15. Demonstrate replacement of one model without loss of workspace continuity.

### 17.3 Explicitly Deferred

- IPFS;
- DID and full VC infrastructure;
- national identity integration;
- municipal ERP replacement;
- legally automated contract annulment;
- unrestricted federation;
- autonomous public decision-making;
- general-purpose agent autonomy;
- production cryptographic key management.

---

## 18. Risks and Failure Modes

### 18.1 Bureaucratic Denial of Service

Cheap recursive workspace creation may become a form of filibuster or responsibility avoidance.

Mitigations:

- require an explicit delegation justification;
- charge a declared complexity or attention budget;
- distinguish tasks, commands, sub-workspaces, and full civic loops;
- cap recursion depth by policy rather than technology alone;
- audit workspace creation patterns.

### 18.2 Mandate Laundering

A recommendation may be presented as a binding consequence of the original mandate.

Mitigations:

- capability-bound delegation;
- parent ingestion gates;
- signed scope and non-goals;
- no automatic promotion from recommendation to decision;
- visible delegation chains.

### 18.3 Ontology Capture

Whoever controls workspace metadata may control the rules of action while appearing to perform neutral implementation.

Mitigations:

- treat schema changes as governance-significant changes;
- version and publish workspace definitions;
- prohibit silent active-case schema mutation;
- preserve previous definitions and migration rationale;
- require review proportional to affected authority.

### 18.4 Rubber-Stamping

Approval fatigue can turn a rigorous protocol into ceremonial clicking.

Mitigations:

- concise Decision Capsules;
- semantic diffs;
- visible dissent and consequences;
- proportional gates;
- one-shot authority rather than permanent permission;
- metrics for repeated low-attention approval.

### 18.5 False Cryptographic Certainty

Signatures and Merkle structures prove integrity and authorship relationships, not the truth or justice of the underlying judgment.

Mitigations:

- preserve the distinction between integrity, evidence, claim, review, and political judgment;
- allow challenge and invalidation;
- never label signed claims as immutable truth.

### 18.6 Physical-State Divergence

A digital ledger may say that an action was authorised or reported while the physical world differs.

Mitigations:

- Tripartite Receipt Model;
- independent verification;
- contested and failed-verification states;
- follow-up monitoring;
- domain-specific evidence rules.

### 18.7 Privacy and Redaction

An immutable history can conflict with legal erasure and privacy duties.

The likely design direction is append-only public provenance with revocable or redactable encrypted payload access, tombstone and correction events, and stable causal placeholders. This remains an open design problem and must not be solved through naive permanent publication of personal data.

### 18.8 Platform Capture

A hosted provider may attempt to become the de facto owner of workspace history and identity.

Mitigations:

- local-first canonical workspaces;
- portable schemas and artifacts;
- replaceable executors;
- open publication formats;
- export and mirror requirements;
- federation without mandatory central custody.

---

## 19. Alternatives Considered

### 19.1 Use Jira or another project-management tool

Insufficient because ticket status does not inherently encode mandate, authority, exact artifact revisions, dissent, causal provenance, verification, or compensation.

### 19.2 Keep the decision public and let institutions execute privately

This preserves existing adoption paths but fails to close the public accountability loop.

### 19.3 Make Pnyx the system of record for all execution

Rejected for the initial architecture. It would require replacing mature institutional systems and would sharply reduce adoption. The proposed model is federated provenance and bounded integration.

### 19.4 Use blockchain as the primary answer

Rejected. The central problems are authority, evidence, judgment, privacy, causal correction, adoption, and governance. Append-only events and content-addressed artifacts can provide integrity without introducing blockchain as a constitutional assumption.

### 19.5 Let one general autonomous agent manage the workspace

Rejected. This recreates hidden sovereignty, excessive context access, unclear role boundaries, and non-reviewable delegation. The runtime requires role-bound executors and bounded commands.

---

## 20. Open Questions

1. Which objects belong in the core protocol and which belong in domain packs?
2. How are multiple mandates attached to one long-lived workspace without confusing authority?
3. How should peer and multi-parent delegation work when legitimacy comes from more than one source?
4. Which temporal invalidation effects can be automated, and which require legal authority?
5. How are privileged artifacts represented in a public causal graph?
6. How are personal data redacted or access-revoked without hiding that a causal artifact existed?
7. What identity assurance levels correspond to each risk tier?
8. How should external systems prove events when they cannot sign native Pnyx receipts?
9. How are public artifacts mirrored long-term without transferring canonical ownership?
10. How does the runtime measure and prevent approval fatigue?
11. What constitutes sufficient evidence of physical execution in different domains?
12. How are workspace-type changes governed and migrated?
13. When should a problem remain a task rather than create a child workspace?
14. How should the project distinguish the narrative phrase `Fractal Pnyx` from the protocol term `Recursive Delegation Graph`?
15. Which existing runtime objects can be extended rather than creating parallel models?

---

## 21. Proposed Review and Adoption Path

This RFC is intentionally non-normative.

If accepted in principle, the next steps should be separate changes:

1. **Normative reconciliation patch**  
   Update constitutional, protocol, routing, runtime, permission, and information documents with explicit migration notes.

2. **Schema and event RFC**  
   Define minimum objects, commands, events, projections, and invariants.

3. **Git-backed MVP**  
   Implement parent/child workspace creation, delegation, approval, condition escrow, receipts, invalidation, and public artifact export.

4. **Empirical pilot**  
   Test the workflow with a real, small, observable problem and record bureaucracy, attention, failure, and adoption costs.

No protocol or governance rule should be smuggled into a code-only implementation PR.

---

## 22. Closing Thesis

The Pnyx should not end when society reaches a decision.

A decision should be capable of creating a sovereign, governed work environment where people and machines can execute bounded mandates, preserve disagreement, publish public artifacts, verify physical outcomes, and recover honestly when evidence or authority later fails.

The proposal can be summarised as:

```text
The Civic Workspace is the human surface.
The Mandate Runtime is the engine.
The Civic Execution Ledger is the memory.
The Federated Provenance Layer is the connection to reality.
```

And by the two invariants at its centre:

> **The workspace is sovereign. Models are replaceable.**

> **Authority can be revoked. History cannot. Consequences must be compensated, not erased.**
