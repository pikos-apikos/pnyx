# AUDIT_LOG

## 1. Purpose

The audit log is the minimum durable public record required for legitimacy, review, and anti-capture defense.

It exists to ensure that meaningful civic actions leave a trace that can be inspected, challenged, replayed, and compared across time.

The audit log is not an optional observability layer.
It is a constitutional accountability surface.

---

## 2. Core Principle

No non-trivial proposal, routing decision, framework change, emergency action, or execution override may exist without an audit record.

If an action is not audit-logged, it does not count as legitimate system action.

---

## 3. Audit Scope

An audit entry is required for any of the following:
- creation of a non-trivial proposal,
- classification of proposal layer,
- panel selection and panel lock,
- skill execution and structured outputs,
- dissent submission,
- evidence acceptance or rejection,
- routing decision,
- threshold assignment,
- briefing publication,
- vote opening and closure,
- final decision,
- execution start,
- execution deviation,
- challenge filing,
- challenge resolution,
- framework-change proposal,
- bootstrap parameter change,
- emergency invocation,
- emergency expiry,
- suspension, retirement, or replacement of a skill,
- any manual override affecting outcome, timing, or scope.

---

## 4. Required Properties

Every audit entry must be:
- timestamped,
- attributable to an actor or system component,
- linked to a proposal or governing object,
- typed,
- immutable after append,
- publicly inspectable unless explicitly redacted under a valid protection rule,
- serializable into a deterministic machine-readable form,
- replayable in sequence.

---

## 5. Audit Record Levels

The system distinguishes three audit levels:

### 5.1 Event Entry
A single append-only record describing one meaningful event.

### 5.2 Proposal Audit Record
The ordered collection of all audit entries linked to one proposal.

### 5.3 Governance Audit Record
The ordered collection of entries linked to framework, threshold, panel-rule, or constitutional changes.

Governance audit records require stricter completeness than ordinary proposal records.

---

## 6. Minimum Event Schema

Every audit event must contain at least:
- `event_id`
- `event_type`
- `event_timestamp`
- `proposal_id` or equivalent governing object id
- `layer` (`policy`, `governance`, `constitutional`, or `bootstrap-control`)
- `actor_type`
- `actor_id`
- `causal_parent_id` when applicable
- `epoch_id`
- `summary`
- `payload`
- `visibility_status`
- `integrity_hash`

Where applicable, the event must also contain:
- `skill_id`
- `panel_id`
- `challenge_id`
- `framework_id`
- `parameter_id`
- `decision_id`
- `execution_route_id`
- `emergency_id`

---

## 7. Event Types

At minimum, the system must support the following event types:

### Proposal Lifecycle
- `proposal_created`
- `proposal_amended`
- `proposal_classified`
- `proposal_scope_locked`
- `proposal_withdrawn`

### Panel and Skill Events
- `panel_selection_started`
- `panel_selected`
- `panel_locked`
- `panel_invalidated`
- `skill_invoked`
- `skill_output_recorded`
- `skill_output_challenged`
- `skill_suspended`
- `skill_reinstated`
- `skill_retired`
- `skill_replaced`

### Evidence Events
- `evidence_submitted`
- `evidence_accepted`
- `evidence_rejected`
- `evidence_marked_insufficient`
- `evidence_conflict_flagged`

### Deliberation Events
- `dissent_recorded`
- `briefing_generated`
- `briefing_published`
- `review_window_opened`
- `review_window_closed`

### Routing and Decision Events
- `routing_assigned`
- `routing_changed`
- `threshold_assigned`
- `vote_opened`
- `vote_closed`
- `decision_recorded`
- `decision_invalidated`

### Execution Events
- `execution_started`
- `execution_paused`
- `execution_resumed`
- `execution_deviated`
- `execution_completed`
- `execution_reverted`

### Challenge and Review Events
- `challenge_filed`
- `challenge_accepted`
- `challenge_rejected`
- `challenge_resolved`
- `post_decision_review_started`
- `post_decision_review_closed`

### Governance and Framework Events
- `framework_change_proposed`
- `framework_change_classified`
- `framework_change_approved`
- `framework_change_rejected`
- `framework_change_activated`
- `parameter_change_proposed`
- `parameter_change_approved`
- `parameter_change_activated`

### Emergency Events
- `emergency_invoked`
- `emergency_scope_declared`
- `emergency_override_applied`
- `emergency_review_opened`
- `emergency_expired`
- `emergency_closed`

---

## 8. Mandatory Proposal Audit Fields

Every non-trivial proposal audit record must expose at minimum:
- proposal id,
- proposal title,
- proposal layer,
- proposer identity class,
- scope statement,
- affected domains,
- reversibility status,
- panel composition,
- required skill classes,
- all recorded dissents,
- evidence summary,
- capture-risk note,
- routing rationale,
- threshold used,
- review window,
- decision result,
- execution status,
- challenge history,
- final disposition.

---

## 9. Mandatory Governance Audit Fields

Any governance, framework, or constitutional change must additionally expose:
- the exact rule or parameter being changed,
- prior value or prior text,
- proposed value or proposed text,
- constitutional impact note,
- anti-capture analysis,
- prospective activation date,
- statement of non-retroactivity,
- meta-review window,
- approval threshold used,
- explicit list of affected downstream rules.

---

## 10. Payload Discipline

The `payload` field must not be an opaque dump.
It must be structured according to event type.

Each event type should define a typed payload contract.
For example:
- routing events must include route candidate set and final rationale,
- threshold events must include applied rule and justification,
- panel events must include selected skills and missing-class checks,
- emergency events must include duration, scope, and expiry rule.

Free-form text may exist, but never as the only meaningful content.

---

## 11. Visibility and Redaction

Default visibility is public.

Redaction is permitted only when disclosure would directly violate a higher protection rule such as:
- private identifying information,
- protected security-sensitive implementation detail,
- narrowly scoped safety-critical exploit information.

Redaction must never hide:
- that an event occurred,
- who or what class of actor caused it,
- what governance object it affected,
- what decision path changed,
- that a redaction was applied.

Every redacted entry must include:
- `redaction_reason`,
- `redaction_scope`,
- `redaction_authority`,
- `redaction_timestamp`.

---

## 12. Integrity Rules

The audit log must support integrity verification.

At minimum:
- every event must have a deterministic hash,
- ordered event sequences must be hash-chain compatible,
- deletion is forbidden,
- correction must occur only by appending a new correcting event,
- replaced or superseded entries remain visible in history.

The system must prefer verifiable append-only storage semantics.

---

## 13. Correction Model

Audit entries are not edited in place.

If an entry is incomplete, wrong, or misleading, the system must append one of:
- `event_corrected`
- `event_superseded`
- `event_invalidated`

The original entry remains part of the historical record.

Legitimacy requires visible correction, not silent cleanup.

---

## 14. Causality and Replay

Each audit event should be linked to the event or object that caused it.

This enables:
- causal reconstruction,
- dispute review,
- timing abuse detection,
- framework replay,
- full proposal lineage analysis.

The system should be able to replay a proposal record and reconstruct:
- what the system knew,
- what panel was used,
- what evidence was considered,
- what dissent existed,
- what threshold applied,
- why the final route and decision occurred.

---

## 15. Audit Invariants

The following invariants must hold:
- no non-trivial decision without a proposal audit record,
- no panel lock without selected-skill evidence,
- no routing assignment without rationale,
- no final decision without threshold record,
- no execution start without decision record,
- no emergency override without expiry rule,
- no framework change activation without prospective effective date,
- no manual override without named override event.

If any invariant fails, the affected action is procedurally defective.

---

## 16. Bootstrap Requirements

During bootstrap, audit strictness must be higher, not lower.

Bootstrap-fixed requirements include:
- full proposal audit record for every non-trivial proposal,
- mandatory publication of panel composition,
- mandatory capture-risk note,
- mandatory reversibility note,
- mandatory routing rationale,
- mandatory threshold record,
- mandatory explicit event for any override, rerun, or delay,
- mandatory prospective-only logging for parameter changes.

Bootstrap is the phase in which silent drift is most dangerous.

---

## 17. Audit Failure Classes

The system must distinguish at least the following audit failures:
- `missing_event`
- `late_event`
- `orphan_event`
- `payload_incomplete`
- `causal_link_broken`
- `visibility_misclassified`
- `integrity_mismatch`
- `redaction_abuse`
- `silent_override`
- `retroactive_parameter_mutation`

Each failure class should be challengeable and reportable.

---

## 18. Minimal Machine-Readable Shape

A minimal machine-readable event may be represented conceptually as:

```json
{
  "event_id": "evt_...",
  "event_type": "routing_assigned",
  "event_timestamp": "2026-04-05T12:34:56Z",
  "proposal_id": "prop_...",
  "layer": "policy",
  "actor_type": "system" ,
  "actor_id": "orchestrator",
  "causal_parent_id": "evt_...",
  "epoch_id": "epoch_...",
  "summary": "Proposal routed to hybrid execution.",
  "payload": {
    "candidate_routes": ["market", "state", "hybrid"],
    "selected_route": "hybrid",
    "rationale": "Requires baseline guarantee and local experimentation."
  },
  "visibility_status": "public",
  "integrity_hash": "sha256:..."
}
```

This is illustrative, not binding syntax.

---

## 19. Relationship to Civic Packets

The civic packet is the citizen-facing summary surface.
The audit log is the underlying accountability surface.

The packet may compress.
The audit log must preserve.

Nothing in the packet may contradict the audit log.
If there is tension, the audit log is authoritative.

---

## 20. Closing Principle

The audit log is how the system proves that it reasoned in public rather than merely declared outcomes.

Without auditability, governance becomes narration.
With auditability, governance remains challengeable.
