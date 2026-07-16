# BOOTSTRAP_PARAMETERS

## 1. Purpose

This document defines which parameters are fixed during bootstrap,
which may be revised with lower friction,
and which may adapt without materially altering legitimacy.

Its purpose is:
- to reduce hidden governance leverage during early deployment,
- to distinguish operational flexibility from legitimacy-critical control,
- to prevent tactical retuning while trust is still being built,
- to make bootstrap constraints explicit,
  public,
  and auditable,
- to define how bootstrap parameters may later be revised or retired.

Bootstrap is the phase in which silent drift is most dangerous.
For that reason,
bootstrap must begin with fewer mutable degrees of freedom than a mature system.

---

## 2. Core Principle

During bootstrap,
any parameter that can materially alter:
- who may participate,
- what counts as sufficient review,
- how fast a proposal may move,
- which governance layer applies,
- what routing paths are available,
- when emergency powers may activate,
- or how the system changes itself

must be fixed *ex ante* and publicly known.

The system may adapt during bootstrap,
but it may not improvise legitimacy.

---

## 3. Scope

This document governs bootstrap treatment for:
- deliberative quorum and panel size rules,
- required skill classes,
- governance-layer escalation criteria,
- review window durations,
- approval thresholds,
- routing availability,
- evidence sufficiency minima,
- audit-log minima,
- emergency-path activation and duration rules,
- bootstrap scope limits,
- permission levers with governance impact,
- rules for changing any of the above.

It does not govern purely presentational,
non-decisive,
or convenience-only features unless they begin to alter public understanding or effective participation.

---

## 4. Bootstrap Parameter Classes

Every parameter active during bootstrap must be assigned exactly one mutability class:
- `hard_fixed`
- `soft_fixed`
- `adaptive`

This classification must be public,
versioned,
and auditable.

### 4.1 Hard-Fixed
A parameter is `hard_fixed` when changing it could materially change legitimacy,
participation,
escalation,
or capture resistance.

### 4.2 Soft-Fixed
A parameter is `soft_fixed` when changing it could affect interpretation,
quality,
or consistency,
but does not directly alter who counts,
what threshold applies,
or what authority is exercised.

### 4.3 Adaptive
A parameter is `adaptive` when changing it improves usability,
clarity,
or accessibility without changing substantive rights,
thresholds,
or control over the civic loop.

---

## 5. Bootstrap Parameter Object

Each bootstrap-controlled parameter should be represented as a first-class object:

- `parameter_id`
- `name`
- `description`
- `domain` (`protocol`, `control`, `information`, `permissions`, `routing`, `evidence`, `audit`, `emergency`, `meta`)
- `current_value`
- `value_type`
- `mutability_class`
- `bootstrap_fixed` (bool)
- `activation_epoch`
- `source_framework_ref`
- `change_path` (`none`, `meta_only`, `ordinary_allowed`)
- `active_case_change_allowed` (must be `false` for all hard-fixed parameters)
- `review_notes`
- `deprecation_status`

The registry of bootstrap parameters must be public and machine-readable.

---

## 6. Hard-Fixed Parameters

The following parameters should be hard-fixed during bootstrap.

### 6.1 Deliberative Quorum and Panel Size
Hard-fixed:
- minimum deliberative quorum,
- allowed default panel sizes,
- escalation thresholds for moving from 5 to 7 or 9 skills,
- rerun triggers related to missing required classes,
- invalid panel conditions.

Default bootstrap stance:
- five is the minimum deliberative quorum,
- seven and nine are escalation sizes,
- no lower quorum may be used for non-trivial proposals.

### 6.2 Required Skill Classes
Hard-fixed:
- mandatory baseline skill classes,
- mandatory inclusion of adversarial critique,
- mandatory inclusion of anti-capture review,
- mandatory inclusion of rights review for non-trivial proposals,
- governance- and constitutional-layer required additions.

### 6.3 Layer Classification and Escalation Criteria
Hard-fixed:
- policy / governance / constitutional boundary rules,
- framework-change recognition rules,
- emergency-eligibility classification rules,
- non-trivial proposal criteria,
- spillover escalation triggers.

### 6.4 Review Windows
Hard-fixed:
- minimum review duration by governance layer,
- minimum public deliberation duration,
- minimum challenge window,
- minimum meta-review delay,
- emergency review expiry and mandatory re-review timing.

Review windows may not be shortened for an active case.

### 6.5 Approval Thresholds
Hard-fixed:
- approval thresholds by governance layer,
- any supermajority rules,
- quorum requirements for final decision,
- meta-governance approval thresholds,
- rollback approval requirements where defined.

### 6.6 Routing Availability and Hard Stops
Hard-fixed:
- whether bootstrap permits `market`, `state`, `hybrid`, `advisory_only`, or `defer_pending_evidence`,
- hard-stop conditions that forbid a route,
- constitutional or anti-capture conditions requiring a route,
- emergency route restrictions.

### 6.7 Evidence Sufficiency Minima
Hard-fixed:
- minimum evidence requirements for non-trivial proposals,
- unknown / insufficient-evidence handling rules,
- prohibition on model output self-validating factual claims,
- minimum rights-analysis requirements,
- minimum meta-impact evidence requirements.

### 6.8 Audit Minimum
Hard-fixed:
- required audit event types,
- mandatory audit fields,
- append-only requirements,
- correction-without-silent-edit rules,
- public verification requirements,
- redaction constraints.

### 6.9 Emergency Rules
Hard-fixed:
- emergency activation criteria,
- maximum emergency duration,
- mandatory auto-expiry,
- mandatory ex post review,
- prohibition on using emergency mode to mutate framework rules unless explicitly permitted,
- emergency routing limitations.

### 6.10 Bootstrap Scope Limits
Hard-fixed:
- which proposal domains bootstrap may govern,
- which domains remain advisory-only,
- which domains are out of scope,
- whether execution authority is real,
  partial,
  or advisory,
- any domain-specific exclusions.

### 6.11 Permission Levers with Legitimacy Impact
Hard-fixed:
- who may submit,
- who may challenge,
- who may invoke emergency pathways,
- who may trigger meta-governance,
- whether moderators or operators may suspend flows,
- whether panel assemblers may substitute unavailable skills.

### 6.12 Parameter-Change Rules
Hard-fixed:
- which parameters require meta-governance,
- no active-case retuning,
- future-epoch activation only,
- meta-delay requirements,
- transition guard requirements,
- rollback declaration requirements.

---

## 7. Soft-Fixed Parameters

The following parameters may be revised during bootstrap,
but only through the civic loop and with audit.

### 7.1 Skill Templates
Examples:
- default context packet shape,
- recommended auxiliary skill classes,
- default ordering of skill execution,
- comparison-run defaults.

### 7.2 Briefing and Packet Defaults
Examples:
- default packet section ordering,
- formatting of summary vs deep detail,
- visual emphasis rules,
- helper views for dissent exploration.

### 7.3 Evidence Weighting Heuristics
Examples:
- ranking or display preferences among evidence classes,
- default ordering of conflicting sources,
- display heuristics for uncertainty.

These may not override hard-fixed sufficiency rules.

### 7.4 Default Routing Heuristics
Examples:
- ordering of route-evaluation questions,
- presentation defaults for route comparisons,
- default recommendation formatting.

These may not alter route availability,
hard stops,
or required justifications.

### 7.5 Non-Decisive Permission Workflow Defaults
Examples:
- how clarification requests are surfaced,
- default moderation queue order,
- notification timing,
- reminder cadence.

These may not alter substantive rights,
thresholds,
or challenge availability.

### 7.6 Participant Compensation Rates

Soft-fixed:
- base hourly rate for participation time,
- differentiated rates by participation role (sortition body member, affected party, targeted invitee, monitoring participant),
- care expense compensation rate,
- transport compensation rate,
- accessibility support compensation rate,
- maximum daily compensation cap,
- compensation eligibility criteria by classification tier.

**Bootstrap default rates (epoch v1):**

> **Note:** The following rates are illustrative examples for post-beta operation. During the beta phase, participation will rely on volunteer contributions. The compensation infrastructure (ParticipantCompensationRecord artifacts, treasury rails, audit mechanisms) is being designed and tested, but actual compensation payments will not be activated until the system transitions beyond beta. These rates serve as planning targets for when the treasury and funding mechanisms are operational.

| Parameter | Value | Rationale |
|-----------|-------|-----------|
| Base hourly rate | €25/hour | Minimum wage equivalent for civic work; compensates lost working time |
| Sortition body member | €35/hour | Higher rate for bounded deliberative responsibility and preparation time |
| Affected party | €30/hour | Compensates lived experience and emotional labor of participation |
| Targeted invitee | €30/hour | Compensates specialized knowledge or perspective contribution |
| Monitoring participant | €20/hour | Lower rate for ongoing but less intensive review work |
| Care expense compensation | €15/hour | Covers childcare, eldercare, or dependent care during participation |
| Transport compensation | €0.50/km | Standard mileage rate for travel to in-person participation |
| Accessibility support | Actual cost | Covers sign language, translation, assistive technology, or other accessibility needs |
| Maximum daily cap | €200/day | Prevents excessive compensation that could create perverse incentives |
| Trivial proposal compensation | None | Trivial proposals use open participation without formed bodies |
| Non-trivial proposal compensation | Base rate + role differential | All non-trivial proposals must declare compensation rules in ParticipationPlan |
| High-impact proposal compensation | Enhanced rates | High-impact proposals may use sortition body rates and enhanced care/transport |

**Rules:**
- Compensation must be declared in the ParticipationPlan before participation begins
- Compensation is independent of participant position, vote, or agreement
- Compensation is independent of final judgment outcome
- All compensation payments produce auditable ParticipantCompensationRecord artifacts
- Compensation rates may be adjusted through the civic loop with logged rationale
- Rate changes apply prospectively to new proposals, not active cases
- Compensation coverage rate is a standing Governance Health metric

**Rationale:**
Uncompensated participation is a regressive filter that excludes time-poor, care-responsible, and economically vulnerable populations. Compensation pays for time and civic work, not agreement. The rates are soft-fixed to allow adjustment through the civic loop while maintaining audit and transparency requirements.

---

## 8. Adaptive Parameters

Adaptive parameters may change with lower friction because they do not directly alter legitimacy.

Examples include:
- wording,
- UX labels,
- translation defaults,
- accessibility enhancements,
- browsing and discovery aids,
- summarization tone,
- visualization style,
- non-binding convenience automations.

An adaptive parameter must be reclassified upward if it begins to affect:
- effective participation,
- visibility of dissent,
- visibility of unknowns,
- challenge discoverability,
- or perceived decisional weight.

---

## 9. Bootstrap Default Registry

Bootstrap should publish a canonical parameter registry before ordinary proposals begin moving through the system.

At minimum,
the registry must include:
- parameter name,
- current value,
- mutability class,
- rationale,
- activation epoch,
- allowed change path,
- whether challenge is permitted,
- whether delayed activation is required.

No bootstrap-controlled parameter should exist only as implementation convention.

---

## 10. Change Rules by Mutability Class

### 10.1 Hard-Fixed Change Rules
A hard-fixed parameter may change only:
- through meta-governance,
- with explicit classification as a bootstrap-parameter change where applicable,
- with prospective effect only,
- with delayed activation,
- with transition guards,
- with full audit,
- and never for an already submitted proposal.

### 10.2 Soft-Fixed Change Rules
A soft-fixed parameter may change:
- through the civic loop,
- with logged rationale,
- with visible before/after state,
- prospectively where needed,
- and without overriding any hard-fixed constraint.

### 10.3 Adaptive Change Rules
An adaptive parameter may change through a lighter-weight process,
provided that:
- the change is logged,
- the change does not alter rights,
  thresholds,
  routing,
  or challengeability,
- and the parameter remains correctly classified.

---

## 11. Forbidden Bootstrap Moves

The following are forbidden during bootstrap:
- reducing the non-trivial panel below five,
- removing adversarial critique from required baseline review,
- shortening an active proposal's review window,
- changing a live proposal's approval threshold,
- reclassifying a live proposal in order to weaken scrutiny,
- silently expanding bootstrap scope,
- allowing emergency mode to become a standing governance mode,
- allowing implementation operators to mutate audit history,
- changing parameter mutability classes without audit and review,
- treating presentation-only changes as a cover for legitimacy change.

---

## 12. Prospective Activation and Epoch Binding

Any change to a bootstrap-controlled parameter must bind to a future epoch:
- `ParameterEpoch` for parameter changes,
- `FrameworkEpoch` where framework structure is affected.

The active epoch at submission time remains binding for the active proposal unless a previously authorized transition guard says otherwise.

Bootstrap parameters may not be hot-swapped mid-flow.

---

## 13. Transition Guards

Every accepted change to a hard-fixed parameter must define transition guards.

At minimum,
a transition guard should answer:
- When does the new value activate?
- Which proposals remain under the old value?
- Are any replay or rerun rules triggered?
- Do packets require dual display during transition?
- Is rollback possible,
  and under what conditions?

A hard-fixed parameter without transition guards must not activate.

---

## 14. Audit Requirements

Every bootstrap parameter change must emit audit records for:
- proposal submission,
- classification,
- current value,
- proposed value,
- mutability class,
- impact statement,
- decision outcome,
- activation schedule,
- transition guard,
- rollback condition if defined.

Parameter state must be replayable from the audit stream.

---

## 15. Bootstrap Completion and Parameter Retirement

Bootstrap does not end merely because the system has been running for some time.
Bootstrap exit should itself be a governed act.

A parameter may cease to be bootstrap-fixed only if:
- bootstrap exit or phase transition is explicitly proposed,
- the system has sufficient audit and trust history,
- scope expansion is deliberate,
- the change is reviewed through meta-governance,
- and the resulting mutability class is publicly justified.

Bootstrap-fixed parameters should not silently dissolve into ordinary operator discretion.

---

## 16. Reclassification Rules

A parameter may be reclassified only by explicit proposal.

Typical reclassification directions include:
- `hard_fixed` → `soft_fixed` after institutional maturity,
- `soft_fixed` → `hard_fixed` if abuse appears,
- `adaptive` → `soft_fixed` if it begins affecting real participation,
- `adaptive` → `hard_fixed` if it becomes a hidden governance lever.

Reclassification is itself a meta-governance act when legitimacy-critical impact is present.

---

## 17. Failure Modes

Common bootstrap parameter failures include:
- **silent drift**: implementation no longer matches the published registry,
- **classification laundering**: a hard-fixed parameter is mislabeled as adaptive,
- **timing abuse**: windows or thresholds are changed to favor an active case,
- **scope creep**: bootstrap begins deciding domains it was not authorized to govern,
- **operator convenience capture**: defaults become de facto law without review,
- **exception normalization**: emergency or temporary workarounds become standard.

These failures should trigger:
- audit alerts,
- challenge eligibility,
- rerun or suspension where applicable,
- and possible rollback to the prior active parameter epoch.

---

## 18. Implementation Guidance

During bootstrap,
software and operations should treat the bootstrap parameter registry as a first-class dependency.

Systems should:
- validate every proposal against the active bootstrap parameter set,
- prevent submission-time ambiguity about active values,
- reject unauthorized changes at runtime,
- expose the active parameter set in public packets,
- and preserve historical parameter references for replay.

Implementation convenience is never a valid reason to weaken bootstrap controls.

---

## 19. Closing Principle

Bootstrap should begin with fewer adjustable levers than a mature polity,
not because rigidity is ideal,
but because invisible discretion is most dangerous before trust exists.

The system may loosen selected controls later,
but it must first prove that it can govern its own parameters without quietly governing the people behind them.
