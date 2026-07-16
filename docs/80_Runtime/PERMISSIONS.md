# PERMISSIONS

## 1. Purpose

This document defines the permission model for participation, review, challenge, routing, emergency invocation, and framework modification.

The permission model exists to coordinate action without creating hidden sovereignty.
Permissions grant procedural capability, not political supremacy.

No role may bypass the civic loop merely by possessing elevated permissions.

---

## 2. Design Principles

The permissions system must satisfy the following principles:

- **citizen primacy**: legitimacy remains public, not administrative,
- **minimum necessary authority**: each role receives only the permissions required for its function,
- **no silent privilege**: all elevated permissions must be explicit and auditable,
- **challengeability**: permission use may be challenged where applicable,
- **separation of procedural power from sovereign judgment**,
- **prospective change only** for permission-rule modifications,
- **anti-capture by distribution**: no single actor or office should control proposal intake, panel composition, routing, evidence, and final execution simultaneously.

---

## 3. Permission Model Overview

The system uses a hybrid model:

- **baseline civic permissions** attached to ordinary participation,
- **role-scoped procedural permissions** for bounded functions,
- **contextual permissions** triggered by proposal type, governance layer, and execution stage,
- **time-bounded exceptional permissions** for emergency handling,
- **meta-permissions** for framework-changing procedures.

Permissions must always be interpretable in public terms.
If a permission cannot be explained as a public function, it should not exist.

---

## 4. Core Actor Types

### 4.1 Citizen

The default civic subject.
A citizen may:
- read public packets,
- submit proposals,
- comment during deliberation,
- endorse or oppose proposals,
- vote where voting is authorized,
- file a challenge,
- request clarification,
- inspect audit records,
- participate in framework review where eligible.

A citizen may not:
- silently alter packets,
- directly assign routes,
- directly compose panels outside defined procedures,
- invoke emergency execution without the emergency pathway,
- mutate framework parameters outside meta-governance.

### 4.2 Proposer

A citizen or authorized group that submits a proposal.
The proposer may:
- submit a proposal,
- amend their own draft before panel lock,
- respond to clarification requests,
- withdraw a proposal before certain lock states,
- submit supporting evidence.

The proposer may not:
- select the final panel,
- suppress dissent,
- choose the final route,
- rewrite audit records.

### 4.3 Moderator

A bounded procedural role.
The moderator may:
- validate submission completeness,
- enforce packet completeness rules,
- reject malformed submissions with reason,
- trigger required missing-information requests,
- manage procedural state transitions where explicitly allowed.

The moderator may not:
- determine substantive truth,
- downgrade governance layer classification without rule basis,
- suppress eligible challenges,
- remove required skill classes.

### 4.4 Panel Assembler

A bounded procedural function, whether human, automated, or hybrid.
The panel assembler may:
- generate candidate panel compositions,
- check quorum and required class constraints,
- replace unavailable skills under the selection rules,
- mark a panel as procedurally valid.

The panel assembler may not:
- favor a proposer or faction through ad hoc selection,
- remove mandatory dissent capacity,
- silently alter locked panels,
- select outside the permitted class and version rules.

### 4.5 Skill Operator / Skill Maintainer

A role responsible for a skill runtime, version, or maintenance process.
The skill maintainer may:
- publish new skill versions,
- document changes,
- propose retirement or suspension,
- respond to challenge findings,
- maintain operational availability.

The skill maintainer may not:
- silently replace the active logic of a locked version,
- self-certify a disputed skill,
- hide model drift,
- erase historical outputs.

### 4.6 Auditor

A role responsible for reviewing compliance, completeness, and procedural integrity.
The auditor may:
- inspect logs and records,
- issue audit findings,
- mark procedural violations,
- recommend invalidation or rerun,
- verify whether rules were followed.

The auditor may not:
- rewrite substantive outcomes,
- substitute political judgment for procedural review,
- conceal adverse findings.

### 4.7 Emergency Custodian

A highly constrained role for emergency-path invocation and termination.
The emergency custodian may:
- invoke emergency mode when strict triggering criteria are met,
- initiate temporary emergency routing,
- record emergency justification,
- trigger automatic review on expiration.

The emergency custodian may not:
- extend emergency authority indefinitely,
- convert emergency authority into ordinary governance,
- modify framework rules during emergency mode unless explicitly allowed by constitutional rules.

### 4.8 Meta-Governance Initiator

A role or ordinary citizen path authorized to submit framework-changing proposals.
The initiator may:
- propose changes to rules, thresholds, windows, or framework structures,
- attach meta-impact analysis,
- trigger the meta-governance path.

The initiator may not:
- alter active-case parameters,
- retroactively change thresholds,
- bypass the stricter meta path.

### 4.9 Participation Auditor

A bounded role responsible for the per-case Participation Audit (`45_Participation/PARTICIPATION_MODEL.md`).
The participation auditor may:
- inspect participation plans, invitations, sortition records, body composition, delegation aggregates, and compensation records for the case,
- issue the ParticipationAudit before decision-readiness,
- mark participation deficiencies and systematically excluded perspectives,
- recommend remediation before the case proceeds.

The participation auditor may not:
- audit a case whose Participation Plan they helped design,
- act with undisclosed conflicts relevant to the case,
- rewrite the plan or body composition,
- suppress deficiency findings.

Participation audit outputs are challengeable like classification results.

### 4.10 Sortition Operator

A bounded procedural role for verifiable random selection (`45_Participation/SORTITION.md`).
The sortition operator may:
- commit the eligible-population registry snapshot,
- execute the declared draw procedure,
- publish the draw proof and SortitionResult,
- process declared replacement and opt-out rules.

The sortition operator may not:
- modify the eligible-population registry within a selection window,
- choose or bias seed material outside the declared procedure,
- alter stratification rules after the snapshot commitment,
- withhold verification material.

### 4.11 Executor

The actor or subsystem that applies an approved outcome.
The executor may:
- apply the routed outcome,
- record execution events,
- report implementation failures,
- mark completion or deviation.

The executor may not:
- reinterpret the approved proposal materially,
- widen scope without authorization,
- hide partial or failed execution.

---

## 5. Permission Categories

### 5.1 Read Permissions

Examples:
- read public packet,
- read challenge packet,
- read audit log,
- read evidence set,
- read routing rationale,
- read framework history.

Read permissions should default to openness unless a redaction rule is explicitly justified.

### 5.2 Submit Permissions

Examples:
- submit proposal,
- submit challenge,
- submit evidence,
- submit dissent note,
- submit meta proposal,
- submit emergency justification.

Submission rights should be broad.
Denial of submission should be exceptional and reviewable.

### 5.3 Procedural Permissions

Examples:
- validate packet completeness,
- classify proposal layer,
- assemble panel,
- lock panel,
- request clarification,
- trigger rerun,
- mark insufficient evidence.

These permissions are tightly bounded and must be logged.

### 5.4 Decision Permissions

Examples:
- vote,
- ratify,
- confirm execution,
- accept or reject challenge outcomes,
- approve meta changes.

Decision permissions require explicit eligibility rules.
They must never be inferred silently from operational roles.

### 5.5 Override Permissions

Examples:
- invoke emergency path,
- temporarily halt execution,
- invalidate a procedurally corrupted panel,
- suspend a skill version.

All override permissions are exceptional.
Every use must trigger immediate audit obligations.

### 5.6 Meta Permissions

Examples:
- propose threshold changes,
- propose review-window changes,
- propose permission-model changes,
- activate next epoch parameters.

Meta permissions must never apply retroactively to an active case.

---

## 6. Stage-Bound Permissions

Permissions vary by lifecycle stage.

### 6.1 Draft Stage

Allowed:
- submit,
- edit by proposer,
- completeness validation,
- request clarification,
- attach evidence.

Not allowed:
- final routing,
- final panel lock,
- binding execution.

### 6.2 Intake and Classification Stage

Allowed:
- classify proposal type,
- mark non-triviality,
- assign required classes,
- reject malformed or out-of-scope submissions.

Not allowed:
- ad hoc lowering of governance level,
- skipping required classification criteria.

### 6.3 Panel Formation Stage

Allowed:
- assemble candidate panel,
- validate quorum,
- ensure required classes,
- resolve unavailable skill replacements.

Not allowed:
- proposer-controlled panel selection,
- route selection before sufficient review.

### 6.4 Deliberation Stage

Allowed:
- produce skill outputs,
- issue dissent,
- mark uncertainty,
- request additional evidence,
- file challenge.

Not allowed:
- packet redaction without rule basis,
- secret replacement of outputs.

### 6.5 Routing Stage

Allowed:
- evaluate routing criteria,
- select market / state / hybrid / advisory_only / defer_pending_evidence,
- issue routing rationale,
- contest routing.

Not allowed:
- route without required evidence status,
- ignore hard routing stops.

### 6.6 Decision Stage

Allowed:
- deliberate publicly,
- vote or ratify where applicable,
- register abstention,
- record final outcome.

Not allowed:
- mutate the packet materially during active vote,
- change thresholds during the decision window.

### 6.7 Execution Stage

Allowed:
- carry out approved action,
- report compliance and deviation,
- log completion.

Not allowed:
- expand execution scope,
- reinterpret routing classification without review.

### 6.8 Review Stage

Allowed:
- challenge,
- audit,
- invalidate on procedural grounds,
- trigger rerun,
- issue correction records.

Not allowed:
- erase historical state,
- rewrite prior outputs silently.

---

## 7. Permission Matrix by Governance Layer

### 7.1 Policy Layer

Baseline rights are broad.
Ordinary proposal, review, challenge, and vote permissions are widely distributed.
Procedural roles remain bounded.

### 7.2 Governance Layer

Framework-related submissions require stricter packet completeness, stronger panel composition, and higher audit obligations.
Procedural permissions are more constrained.

### 7.3 Constitutional Layer

Permissions affecting rights, shared values, or core anti-capture protections must follow the highest scrutiny.
No constitutional actor may bypass the full meta-governance path.

---

## 8. Eligibility and Identity Constraints

The system may impose eligibility conditions for particular actions, such as:
- local membership,
- stake of effect,
- participation recency,
- anti-duplication checks,
- conflict-of-interest declarations,
- challenge standing rules.

Such constraints must be:
- explicit,
- reviewable,
- proportionate,
- non-discriminatory within the constitutional frame.

Eligibility constraints may not be used to secretly narrow the demos.

---

## 9. Conflict-of-Interest Rules

Any actor exercising procedural, audit, routing, or emergency permissions must disclose conflicts relevant to the active case.

A conflict may require:
- recusal,
- replacement,
- extra adversarial review,
- visible annotation in the packet and audit log.

Undisclosed conflict affecting outcome validity is a serious procedural failure.

---

## 10. Delegation and Proxy Rules

The system may allow limited delegation for bounded functions.

Delegation must be:
- explicit,
- time-bounded,
- scope-bounded,
- revocable,
- auditable.

Delegation may apply to:
- packet drafting assistance,
- evidence submission support,
- accessibility support,
- execution assistance,
- attention delegation (`45_Participation/ATTENTION_AND_REACH.md`): reading updates, reviewing evidence, following a subject, producing summaries, monitoring a mandate, recommending direct examination.

Attention delegation is additionally:
- scope-specific and purpose-specific,
- time-limited with automatic expiry,
- non-transferable unless explicitly permitted,
- revocable by the delegator at any time,
- auditable by the delegator.

Attention delegation never consumes the delegator's civic action or uniqueness credential; only the delegator's own action does. Delegation of attention does not transfer the citizen's final civic judgment unless a JudgmentConfiguration explicitly permits it.

Delegation may not apply to unrestricted sovereign substitution unless separately and explicitly governed.

---

## 11. Suspension and Revocation

Permissions may be suspended or revoked when:
- procedural abuse is verified,
- conflict-of-interest rules are violated,
- emergency powers are misused,
- a skill maintainer causes silent drift,
- audit obstruction occurs,
- malicious manipulation is established.

Suspension and revocation must:
- state reason,
- state scope,
- state duration,
- provide review path,
- be recorded in the audit log.

No silent de-permissioning is allowed.

---

## 12. Emergency Permissions

Emergency permissions are exceptional and narrow.

They must satisfy all of the following:
- explicit triggering criteria,
- explicit time limit,
- explicit scope limit,
- automatic expiry,
- mandatory ex post review,
- prohibition on silent normalization.

Emergency permissions may suspend ordinary timing rules only within constitutionally permitted bounds.
They may not erase auditability.

---

## 13. Meta-Permission Constraints

Changes to the permission model itself are meta-governance actions.

Therefore:
- permission-rule changes may only apply prospectively,
- no permission expansion may affect an active case,
- no actor may grant itself new powers inside the same running case,
- permission changes require explicit meta-impact analysis,
- bootstrap-fixed permission levers require heightened scrutiny.

The system may revise its permissions, but never while those permissions are actively deciding the same case.

---

## 14. Bootstrap-Fixed Permission Rules

During bootstrap, the following permission rules should be hard-fixed:
- who may classify proposal layer,
- who may assemble and lock panels,
- who may trigger emergency paths,
- who may suspend skills,
- who may invalidate a case on procedural grounds,
- who may activate next-epoch framework parameters,
- who may issue participation audits,
- who may operate sortition draws and commit registry snapshots,
- what challenge rights are guaranteed to ordinary participants,
- what minimum read access exists for public audit.

These rules may later be revised only through meta-governance and only prospectively.

---

## 15. Logging Requirements

Every elevated permission use must emit an audit event.
This includes at minimum:
- actor type,
- permission exercised,
- target proposal or framework object,
- time,
- reason,
- resulting state change,
- whether the action is challengeable,
- applicable rule basis.

Any permission use that changes state without an audit trace is invalid by default.

---

## 16. Forbidden Permission Patterns

The following patterns are prohibited:
- silent superuser roles,
- hidden administrative bypass,
- retroactive threshold changes,
- active-case permission mutation,
- invisible packet editing,
- unlogged emergency invocation,
- proposer-controlled panel lock,
- skill maintainer control over challenge adjudication,
- executor reinterpretation of approved scope,
- audit suppression.

---

## 17. Minimum Public Guarantees

Regardless of implementation details, the system must guarantee that ordinary citizens can:
- submit a proposal,
- access the public packet,
- view the routing rationale,
- view dissent,
- inspect audit records subject to constitutional redaction rules,
- file a challenge,
- know who exercised elevated permissions and why,
- know which permission rule governed the case.

Without these guarantees, the system ceases to function as civic governance and degrades into managed administration.

---

## 18. Closing Principle

Permissions coordinate procedure.
They do not create sovereignty.

A valid permission system is one in which elevated function remains bounded, explicit, contestable, and auditable,
while the public remains the final source of legitimacy.
