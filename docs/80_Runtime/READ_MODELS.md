# READ_MODELS

## 1. Purpose

This document defines the read models of the system.

Its purpose is to specify how canonical events become usable views for:
- citizens,
- challengers,
- reviewers,
- auditors,
- operators,
- treasury stewards,
- community facilitators,
- pilot coordinators,
- and shutdown/archive readers.

This document complements:
- `EVENT_MODEL.md`
- `SCHEMAS.md`
- `AUDIT_LOG.md`
- `AUDIT_VIEWS.md`
- `PACKET_FORMAT.md`
- `TREASURY.md`
- `PROTOTYPE_PLAN.md`

The read-model layer exists to make the system readable without making projections sovereign.

---

## 2. Core Principle

Read models are derived views, not canonical truth.

The system must preserve a strict distinction between:
- the append-only event history,
- the projection logic,
- the user-facing representation of current or historical state.

A read model may be:
- useful,
- simplified,
- role-specific,
- cached,
- filtered,
- searchable.

A read model may not:
- silently invent facts,
- silently erase prior states,
- silently override canonical history,
- silently hide governance-significant events.

If a projection is wrong, rebuild must be possible.
If a projection is missing, the event history must still remain intact.

---

## 3. Why Read Models Matter

Without read models, the event stream is too raw for most participants.
Without careful boundaries, read models become hidden governance.

The system therefore needs read models that are:
- human-usable,
- role-appropriate,
- auditable,
- rebuildable,
- explicit about simplification,
- linked back to canonical traces.

The public must not be forced to choose between:
- unreadable truth,
- and readable illusion.

---

## 4. Read Model Classes

The system should distinguish at least these read-model classes:

### 4.1 Current-State Read Models
Show the currently active state of an object.

Examples:
- current proposal status,
- current packet version,
- current challenge status,
- current treasury partition posture.

### 4.2 Timeline Read Models
Show the event history of an object or subject over time.

Examples:
- proposal timeline,
- challenge timeline,
- treasury partition timeline,
- emergency timeline.

### 4.3 Role-Specific Operational Read Models
Show information shaped for a particular role.

Examples:
- citizen packet view,
- operator work queue,
- auditor incident view,
- treasury reconciler dashboard.

### 4.4 Analytical Read Models
Show repeated patterns or aggregate signals.

Examples:
- challenge frequency by pilot,
- skill failure recurrence,
- donor concentration warnings,
- repeat-use metrics,
- epistemic incident patterns.

### 4.5 Archive Read Models
Show preserved historical state after closure, shutdown, or archival mode.

---

## 5. Projection Rules

Every read model should have:
- a stable identifier,
- a declared source event set,
- a projection version,
- a rebuild strategy,
- freshness semantics,
- role visibility rules,
- traceability back to canonical records.

Every read model should be able to answer:
- what does this projection represent,
- which events were used,
- when was it last rebuilt,
- what was redacted or simplified,
- what canonical objects does it summarize.

A projection must not pretend to be the event stream.

---

## 6. Shared Read Model Envelope

All read models should expose a common top-level structure where possible.

```json
{
  "read_model_id": "rm_...",
  "read_model_type": "CurrentProposalView",
  "projection_version": "1.0",
  "generated_at": "2026-04-06T12:00:00Z",
  "source_event_range": {
    "from_sequence": 1,
    "to_sequence": 1024
  },
  "subject_ref": {
    "type": "Proposal",
    "id": "proposal_..."
  },
  "freshness": {
    "status": "current",
    "lag_seconds": 2
  },
  "trace_refs": ["evt_...", "evt_..."],
  "payload": {}
}
```

### Allowed `freshness.status`
- `current`
- `stale`
- `rebuilding`
- `archived`
- `unknown`

---

## 7. Core Current-State Read Models

## 7.1 CurrentProposalView

Purpose:
Show the active state of a proposal.

Suggested payload:
```json
{
  "proposal_id": "proposal_...",
  "title": "Neighborhood traffic calming proposal",
  "status": "published",
  "proposal_layer": "policy",
  "current_version_no": 2,
  "classification_summary": {
    "triviality": "non_trivial",
    "layer": "policy",
    "constitutional_spillover": false
  },
  "current_packet_id": "packet_...",
  "open_challenge_count": 1,
  "last_event_at": "2026-04-06T14:00:00Z"
}
```

## 7.2 CurrentPacketView

Purpose:
Show the latest active packet for public use.

Suggested payload:
```json
{
  "packet_id": "packet_...",
  "proposal_id": "proposal_...",
  "packet_type": "briefing",
  "version_no": 2,
  "status": "published",
  "section_refs": [
    "summary",
    "strongest_case_for",
    "strongest_case_against",
    "unknowns",
    "minority_view",
    "capture_risk",
    "reversibility",
    "implementation_note"
  ],
  "challenge_window": {
    "opens_at": "2026-04-06T14:00:00Z",
    "closes_at": "2026-04-08T14:00:00Z"
  },
  "change_notice": "Updated after challenge on accessibility issue"
}
```

## 7.3 CurrentChallengeView

Purpose:
Show the current state of a challenge.

Suggested payload:
```json
{
  "challenge_id": "challenge_...",
  "proposal_id": "proposal_...",
  "packet_id": "packet_...",
  "challenge_type": "packet_omission",
  "status": "open",
  "submitted_at": "2026-04-06T15:00:00Z",
  "reason_code": "minority_harm_omitted",
  "resolution_summary": null
}
```

---

## 8. Timeline Read Models

## 8.1 ProposalTimelineView

Purpose:
Expose the ordered history of a proposal.

Suggested payload:
```json
{
  "proposal_id": "proposal_...",
  "events": [
    {
      "sequence_no": 1,
      "event_type": "ProposalSubmitted",
      "occurred_at": "2026-04-06T12:00:00Z",
      "summary": "Proposal submitted"
    },
    {
      "sequence_no": 2,
      "event_type": "ProposalClassified",
      "occurred_at": "2026-04-06T12:30:00Z",
      "summary": "Classified as non-trivial policy proposal"
    }
  ]
}
```

## 8.2 ChallengeTimelineView
Shows the lifecycle of a challenge from submission to resolution.

## 8.3 TreasuryTimelineView
Shows inflows, allocations, releases, transfers, anomalies, and reconciliations.

## 8.4 EmergencyTimelineView
Shows emergency declaration, actions, expiry, and review linkage.

Timeline models should preserve:
- sequence,
- event summaries,
- references to full canonical records,
- redaction visibility where applicable.

---

## 9. Citizen-Facing Read Models

## 9.1 CitizenPacketView

Purpose:
Primary public-facing packet view for a proposal.

Should include:
- plain-language summary,
- strongest case for,
- strongest case against,
- unknowns,
- minority view,
- capture risk note,
- reversibility note,
- implementation note,
- "what changed" summary when relevant,
- challenge entry point,
- packet provenance summary,
- traceability link.

CitizenPacketView should prioritize readability without removing constitutional meaning.

## 9.2 CitizenChangeView

Purpose:
Explain what changed between packet versions.

Should include:
- previous packet version,
- current packet version,
- sections changed,
- reason for change,
- linked challenge or review cause,
- unchanged sections summary.

## 9.3 CitizenAuditSummaryView

Purpose:
Minimal readable summary of what happened procedurally.

Should include:
- major events,
- who intervened visibly,
- whether challenge occurred,
- whether manual intervention occurred,
- whether the packet was revised,
- whether a participation audit was published and where to read it,
- whether any unresolved issue remains.

Citizen-facing views must not require expertise in event streams.

---

## 10. Challenger and Reviewer Read Models

## 10.1 ChallengeWorkbenchView

Purpose:
Help a challenger understand what can be contested.

Should include:
- current packet,
- linked skill output summaries,
- framing limit note if present,
- known unknowns,
- current classification summary,
- simple challenge categories,
- prior related challenges if any.

## 10.2 ReviewCaseView

Purpose:
Show all material needed to resolve a challenge or review case.

Should include:
- subject under review,
- linked events,
- linked packet versions,
- linked classification records,
- linked skill outputs,
- prior review decisions,
- current open questions.

## 10.3 ClassificationReviewView

Purpose:
Provide a readable view of classification logic, counter-classification, and escalation triggers.

This is especially important because classification is a choke point.

---

## 11. Auditor Read Models

## 11.1 AuditorDecisionRecordView

Purpose:
Show a full decision record for one proposal.

Should include:
- proposal history,
- classification history,
- panel history,
- packet versions,
- challenge history,
- operator interventions,
- relevant evidence summaries,
- linked audit events,
- epoch context.

## 11.2 AuditorIncidentView

Purpose:
Show incidents such as:
- emergency events,
- epistemic incidents,
- treasury anomalies,
- invariant pressure,
- operator exceptions.

## 11.3 AuditorIntegrityView

Purpose:
Show signals of runtime integrity such as:
- projection drift,
- missing events,
- duplicate commands,
- event-sequence anomalies,
- stale projections,
- unlogged action suspicions.

Auditor views must favor completeness over convenience.

---

## 12. Operator Read Models

## 12.1 OperatorQueueView

Purpose:
Show pending operational work without granting hidden authority.

May include:
- proposals awaiting classification,
- challenges awaiting triage,
- packets awaiting publication review,
- projections needing rebuild,
- incidents requiring acknowledgement.

This view must not blur operational convenience with governance discretion.

## 12.2 OperatorInterventionView

Purpose:
Show all manual and visible interventions.

Should include:
- intervention type,
- subject,
- reason,
- timing,
- whether public visibility was triggered,
- linked event and audit refs.

## 12.3 RuntimeHealthView

Purpose:
Show:
- event lag,
- projection lag,
- queue depth,
- failed projections,
- rebuild status,
- storage health,
- integrity warnings.

RuntimeHealthView is operational, not constitutional,
but operational failure can become constitutional risk.

---

## 13. Skill and Evaluation Read Models

## 13.1 SkillRegistryView

Purpose:
Expose admitted, default, watchlisted, suspended, and retired skills.

Should include:
- skill class,
- tier,
- current version,
- model dependence,
- registry status,
- evaluation status,
- portability posture,
- dependency signals.

## 13.2 SkillEvaluationView

Purpose:
Show skill evaluation posture.

Should include:
- suites run,
- latest evaluation run,
- score dimensions,
- regression status,
- failure classes,
- promotion or demotion history.

## 13.3 SkillRunComparisonView

Purpose:
Compare multiple skill outputs or model runs on the same proposal.

Useful for:
- identifying false plurality,
- spotting framing convergence,
- examining cross-model differences.

This view directly supports `AI_EPISTEMIC_RISK.md`.

---

## 14. Treasury Read Models

## 14.1 TreasuryPartitionView

Purpose:
Show the current posture of a treasury partition.

Should include:
- partition name,
- current balance or band,
- reserve posture,
- recent inflows,
- recent allocations,
- recent releases,
- concentration warnings,
- challenge funding posture.

## 14.2 TreasuryAllocationView

Purpose:
Explain a funding decision in readable form.

Should include:
- source partition,
- destination purpose,
- amount or band,
- authorization set,
- reason,
- restrictions,
- linked release status.

## 14.3 FundingConcentrationView

Purpose:
Show dependency and concentration signals.

Should include:
- top-source share,
- source-class mix,
- recurring small-donor share,
- dependency warnings,
- partition vulnerability.

## 14.4 PublicIPRevenueView

Purpose:
Show the flow from public IP artifact to revenue routing to treasury partition.

This is needed so public-return claims are visible, not abstract.

---

## 15. Community and Pilot Read Models

## 15.1 CommunityProfileView

Purpose:
Show a bounded community's current posture in the system.

Should include:
- community type,
- pilot domains,
- issue classes used,
- repeat-cycle count,
- participation indicators,
- challenge usage indicators,
- current active cases.

## 15.2 PilotDomainView

Purpose:
Show the pilot definition and current results.

Should include:
- issue class,
- fit assessment,
- current cycle,
- packet usefulness hypothesis,
- challenge hypothesis,
- current success/failure signals,
- bootstrap debt notes.

## 15.3 PilotRetrospectiveView

Purpose:
Show what happened across one or more cycles.

Should include:
- what worked,
- what failed,
- what changed,
- whether the community returned,
- whether this pilot should continue.

---

## 16. Public IP Read Models

## 16.1 PublicIPArtifactView

Purpose:
Show an artifact's current public IP posture.

Should include:
- artifact title,
- class,
- licensing posture,
- exclusivity status,
- stewardship model,
- revenue eligibility,
- linked proposal or pilot source,
- current status.

## 16.2 PublicIPProvenanceView

Purpose:
Show the artifact lineage from proposal to implementation to stewardship to revenue.

This is important because public IP can otherwise become politically opaque.

---

## 17. Emergency Read Models

## 17.1 EmergencyStatusView

Purpose:
Show the current state of an emergency event.

Should include:
- emergency class,
- scope,
- start time,
- expiry time,
- current status,
- active actions,
- linked review case.

## 17.2 EmergencyIncidentView

Purpose:
Readable incident summary for public and auditors.

Should include:
- what happened,
- what was done,
- what expired,
- whether anything remains unresolved,
- what review path exists.

No emergency should be readable only through raw event logs.

---

## 18. Shutdown and Archive Read Models

## 18.1 ShutdownStatusView

Purpose:
Show whether the system or subsystem is:
- active,
- paused,
- winding down,
- dissolved,
- transferred,
- archived.

## 18.2 ArchiveIndexView

Purpose:
Provide a stable map of archived records after pause or shutdown.

Should include:
- preserved proposal records,
- preserved packet sets,
- preserved challenge histories,
- preserved treasury records,
- preserved public IP records,
- privacy-protected or redacted areas,
- successor stewardship if any.

## 18.3 FinalRetrospectiveView

Purpose:
Surface what the system learned before stopping or transferring.

This is necessary so shutdown does not become silent disappearance.

---

## 19. Participation Read Models

These views expose the participation layer (`45_Participation/`) without turning projections into participation authority.

## 19.1 CivicBriefView

Purpose:
Scope-level periodic brief, not an engagement feed.

Should include:
- what changed in the scope since the last brief,
- which cases need attention and why now,
- expected time commitment per item,
- open participation opportunities,
- current judgments and execution changes,
- recently recorded outcomes,
- explicit marking of each item as information, invitation, optional review, required action, or urgent action,
- links back to canonical artifacts, preserved dissent, and unresolved uncertainty.

CivicBriefView must not rank items by engagement metrics.

## 19.2 ParticipationAuditView

Purpose:
Readable per-case participation audit before decision-readiness.

Should include:
- who was eligible, informed, invited, selected, and absent,
- barriers encountered and accessibility measures taken,
- compensation posture,
- sortition integrity summary where used,
- delegation concentration relevant to the case,
- declared limitations and systematically excluded perspectives,
- audit depth applied (full or lightweight, per classification),
- auditor role and declared conflicts,
- challenge entry point.

## 19.3 DelegationView

Purpose:
Delegator-facing view of attention delegations.

Should include:
- my active and expired delegations,
- scope, purpose, and expiry of each,
- delegate outputs produced under each delegation,
- divergence signals between delegate recommendations and my subsequent direct actions,
- one-step revocation entry point.

This view is private to the delegator; only aggregates are public.

## 19.4 ParticipantBodyView

Purpose:
Show how a participant body was formed and what it may decide.

Should include:
- body type (open, affected-party, sortition, jury, institutional, hybrid),
- formation method and plan version,
- selection or invitation provenance,
- declared conflicts,
- judgment authority and limitations,
- term and mandate,
- pseudonymization per policy.

Receipt note: civic receipts follow the private-proof / public-aggregate rule; no read model may expose how an individual participant acted unless the participant chose disclosure or the process requires public attribution.

---

## 20. Analytical Read Models

Analytical models should be clearly marked as non-canonical but useful.

Examples include:
- challenge-rate by community,
- packet revision frequency,
- model dependence by skill class,
- repeated epistemic failure classes,
- donor concentration trends,
- operator intervention frequency,
- community repeat-use rate,
- proposal drop-off by stage,
- classification challenge frequency,
- participation distribution and repeated absence,
- delegation concentration per scope,
- compensation-position correlation signals.

Participation analytics feed the `GovernanceHealthReport` (`45_Participation/GOVERNANCE_HEALTH.md`); they are diagnostic evidence, never a legitimacy score.

Analytical models help detect drift.
They must not quietly become constitutional decision engines.

---

## 21. Freshness, Lag, and Staleness

Read models must expose freshness.

A view should be able to say:
- current,
- stale,
- rebuilding,
- archived,
- unknown.

Where lag matters, a view should expose:
- projection lag,
- last event sequence included,
- rebuild time,
- known missing slices if any.

A stale view is not necessarily wrong.
It is dangerous only when it claims to be current.

---

## 22. Rebuildability

Every important read model must be rebuildable from:
- canonical events,
- projection code or declared projection logic,
- schema version,
- source event range.

A rebuildable projection should also support:
- full rebuild,
- subject-specific rebuild,
- range-limited rebuild,
- projection version migration where needed.

If a read model cannot be rebuilt, it is too close to hidden truth.

---

## 23. Redaction and Role Visibility

Read models may differ by role visibility,
but must preserve a stable public meaning.

Examples:
- public view may hide sensitive personal details,
- auditor view may reveal more incident detail,
- operator view may show queue state not visible to citizens.

However:
- the existence of a governance-significant event must not disappear,
- redaction must be declared,
- traceability to canonical records must remain,
- redaction must not become soft deletion.

---

## 24. Projection Failure Modes

The system should watch for:
- projection drift from event history,
- missing records in current-state views,
- stale but unlabeled packet views,
- mismatched challenge counts,
- duplicate items caused by replay bugs,
- operator dashboards showing states not backed by events,
- redaction logic hiding major actions,
- archive indices missing linked artifacts.

Projection failures are not merely UX defects.
They can become governance defects.

---

## 25. Prototype Profile

Prototype v1 should implement at minimum:

### Public-facing
- `CitizenPacketView`
- `CitizenChangeView`
- `CitizenAuditSummaryView`

### Internal / reviewer
- `CurrentProposalView`
- `CurrentChallengeView`
- `ReviewCaseView`
- `OperatorQueueView`

### Audit / evaluation
- `ProposalTimelineView`
- `OperatorInterventionView`
- `SkillEvaluationView`
- `PilotDomainView`
- `ParticipationAuditView`

Everything else may be documented now and added later.

---

## 26. Cross-References

This document imposes requirements on:

### 26.1 EVENT_MODEL
Must define the source events and projection boundaries.

### 26.2 SCHEMAS
Must define canonical payloads for projection subjects.

### 26.3 AUDIT_VIEWS
Must align readable audit surfaces with read-model strategy.

### 26.4 PACKET_FORMAT
Must define what packet content is required in citizen-facing projections.

### 26.5 PROTOTYPE_PLAN
Must constrain the first projection surface to what the pilot can sustain.

### 26.6 SHUTDOWN_AND_DISSOLUTION
Must provide archival read models after pause or dissolution.

### 26.7 AI_EPISTEMIC_RISK
Must inform read models that summarize skill outputs and packet framing.

### 26.8 PARTICIPATION LAYER
`45_Participation/PARTICIPATION_MODEL.md`, `ATTENTION_AND_REACH.md`, and `GOVERNANCE_HEALTH.md` must define the rules, artifacts, and metrics the section 19 views project.

---

## 27. Closing Principle

A civic runtime becomes publicly real through the views people can actually use.

The read-model layer must therefore do two things at once:
- simplify the event history into readable surfaces,
- and refuse to let readability become a new hidden sovereign.

A good read model is readable, rebuildable, and bounded.

Anything else is either raw noise or disguised power.
