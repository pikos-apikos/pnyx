# PANEL_SELECTION

## 1. Purpose

This document defines how civic skill panels are assembled, validated, escalated, re-run, and challenged.

A panel is the minimum plural reasoning unit required for non-trivial proposals.
Its function is not to manufacture consensus,
but to guarantee that no meaningful proposal is processed through a narrow or convenience-shaped cognitive path.

This document specifies:
- when a panel is required,
- the minimum and expanded panel sizes,
- required skill classes,
- selection constraints,
- quorum and completion rules,
- escalation rules,
- re-run rules,
- handling for missing, suspended, or compromised skills,
- protocol safeguards against panel gaming.

This is a protocol and governance-support specification,
not a vendor-specific orchestration document.

---

## 2. Design Stance

The system prefers plural panels over single authoritative outputs.

The objective is:
**epistemic plurality with bounded coordination cost.**

Panel formation must therefore avoid two failures:
- **under-plurality**: too few or too similar skills, creating hidden authority,
- **over-assembly**: too many skills by default, creating coordination drag without legitimacy gain.

The default design assumption is:
**five is the minimum deliberative quorum for any non-trivial proposal.**

---

## 3. Definition

A **panel** is a locked set of skill assignments bound to:
- a specific proposal,
- a specific proposal revision,
- a specific framework epoch,
- a specific parameter epoch,
- a specific governance-layer classification,
- a declared set of required and optional skill classes.

A panel is not just a list of skills.
A panel is a protocol object with:
- composition rules,
- selection rationale,
- execution constraints,
- completion criteria,
- challengeability,
- audit trace.

---

## 4. When a Panel Is Required

### 4.1 Mandatory Panels

A panel is mandatory for:
- every non-trivial proposal,
- every governance-layer proposal,
- every constitutional-layer proposal,
- every framework-changing proposal,
- every proposal with declared or detected capture risk above minimal threshold,
- every proposal whose routing decision is materially contested,
- every challenge-triggered re-review of a non-trivial proposal.

### 4.2 Optional Panels

A panel may also be used for:
- advisory proposals,
- edge-case technical proposals,
- bootstrap borderline cases,
- proposals escalated by review or public challenge.

### 4.3 No-Panel Cases

A panel is not required where a proposal is conclusively classified as trivial,
purely clerical,
or formatting-only without semantic, civic, governance, or constitutional consequence.

All no-panel classifications must still be logged and challengeable.

---

## 5. Minimum Panel Rule

### 5.1 Default Minimum

Every non-trivial proposal must be reviewed by a panel of at least **five** relevant skills.

This is the default minimum deliberative quorum.

### 5.2 Required Classes at Minimum Five

A minimum valid panel must contain at least one active skill from each of the following classes:
- `rights_constitutional`
- `implementation_feasibility`
- `economic_resource`
- `anti_capture_audit`
- `adversarial_critique`

A panel of five that duplicates one class while omitting another required class is invalid.

### 5.3 Minimum Means Minimum

The minimum of five is a floor, not a target for all cases.
If escalation criteria apply,
the panel must expand.

---

## 6. Expanded Panel Sizes

### 6.1 Escalation Levels

The canonical panel sizes are:
- `5` — minimum non-trivial panel,
- `7` — expanded panel for higher complexity,
- `9` — extended panel for constitutional, high-uncertainty, or high-impact cases.

No other default sizes should be used during bootstrap unless explicitly authorized by active framework rules.

### 6.2 Seven-Skill Panels

A panel should expand from 5 to 7 when one or more of the following apply:
- the proposal spans more than one domain,
- affected populations are meaningfully heterogeneous,
- reversibility is low,
- uncertainty is high,
- routing between market and state is materially contested,
- evidence quality is disputed,
- local impact differs across communities,
- constitutional spillover is plausible but not dominant.

Typical added classes at 7 may include:
- `evidence_quality`
- `local_impact`
- `minority_protection`
- `dependency_mapping`
- `reversibility_assessment`

### 6.3 Nine-Skill Panels

A panel should expand from 7 to 9 when one or more of the following apply:
- the proposal is constitutional,
- the proposal modifies framework rules,
- the proposal changes approval thresholds or review windows,
- the proposal creates strong precedent,
- the proposal is effectively irreversible,
- capture risk is elevated,
- emergency powers are invoked or reviewed,
- the proposal materially redistributes power between public actors.

At 9,
the system should prefer coverage breadth over redundant duplication.

### 6.4 Default Ceiling During Bootstrap

During bootstrap,
`9` is the default maximum ordinary panel size.
Larger panels require explicit meta-governance authorization.

---

## 7. Selection Inputs

Panel selection must be based on logged inputs,
not operator intuition alone.

The canonical selection inputs are:
- proposal id,
- proposal revision id,
- governance layer,
- framework-change flag,
- emergency flag,
- reversibility class,
- evidence sufficiency state,
- routing sensitivity,
- affected domains,
- affected communities,
- declared and detected risks,
- challenge history,
- active framework epoch,
- active parameter epoch.

Panel selection must not depend on hidden case-by-case operator preference.

---

## 8. Selection Algorithm Requirements

The exact implementation may vary,
but any valid selection mechanism must satisfy the following constraints.

### 8.1 Class Coverage First

Required class coverage must be satisfied before any optimization for speed,
latency,
cost,
or convenience.

### 8.2 Relevance Constraint

Every selected skill must have a declared relevance to the proposal domain,
layer,
or risk profile.

### 8.3 Diversity Constraint

The system should avoid panels composed of skills that are functionally too similar.
Selection should prefer cognitive diversity across:
- class,
- method,
- retrieval strategy,
- implementation lineage,
- reasoning posture.

### 8.4 Anti-Capture Constraint

The system must avoid concentration of panel composition around a single:
- model family,
- operator,
- prompt package lineage,
- retrieval source,
- hosting authority,
when such concentration would materially reduce independence.

### 8.5 Stability Constraint

Once locked,
a panel may not be silently modified while the proposal is active.
Any change requires a logged panel revision or full panel re-run.

### 8.6 Prospective Parameter Constraint

Selection rules must use the parameter epoch active at proposal submission or active proposal revision,
not later rules introduced mid-case.

---

## 9. Panel Specification and Locking

### 9.1 PanelSpec

Before execution,
the system must create a `PanelSpec` containing at least:
- `panel_spec_id`
- `proposal_id`
- `proposal_revision_id`
- `framework_epoch`
- `parameter_epoch`
- `required_classes`
- `optional_classes`
- `target_panel_size`
- `escalation_reason_set`
- `selection_rationale`
- `selection_timestamp`

### 9.2 Panel Lock

After skills are assigned,
the system must create a locked `Panel` containing at least:
- `panel_id`
- `panel_spec_id`
- `selected_skill_ids`
- `selected_skill_version_ids`
- `class_coverage_map`
- `lock_timestamp`
- `panel_status`

No skill may be swapped after lock without producing a new logged panel state.

---

## 10. Quorum and Completion Rules

### 10.1 Composition Quorum

A panel has valid composition quorum only if:
- target size is met,
- every required class is present,
- every selected skill is active and eligible,
- no selected skill is disqualified by challenge or conflict rule.

### 10.2 Analysis Completion Quorum

A panel reaches analysis completion only if:
- all required skill outputs are present,
- mandatory output fields are complete,
- at least one adversarial output is valid,
- unknowns are explicitly stated where evidence is insufficient,
- audit entries exist for all runs.

### 10.3 Degraded Completion Is Not Valid by Default

A non-trivial proposal may not proceed under a degraded panel unless a framework rule explicitly allows a narrowly defined degraded path.
During bootstrap,
degraded completion should be disallowed by default.

---

## 11. Missing Skills and Invalid Panels

### 11.1 Missing Required Class

If no eligible skill is available for a required class,
the panel is invalid.
The proposal must move to one of:
- pending panel completion,
- queued registry remediation,
- challengeable hold,
- advisory-only downgrade,
if and only if the active framework allows it.

### 11.2 Skill Failure During Analysis

If a required skill fails during execution,
the system must either:
- re-run that skill under the same panel lock,
- substitute with an eligible equivalent and issue a logged panel revision,
- invalidate the panel and restart analysis.

### 11.3 Disqualification After Lock

If a selected skill becomes suspended,
conflicted,
or materially compromised after panel lock but before briefing publication,
the panel must be re-evaluated.
The default response is panel revision or full re-run,
not silent continuation.

---

## 12. Escalation Rules

Escalation from 5 to 7 or 9 must be rule-bound,
not improvised.

The system must maintain an auditable `escalation_reason_set`.
Canonical reasons include:
- `multi_domain_scope`
- `heterogeneous_impact`
- `high_uncertainty`
- `low_reversibility`
- `routing_contested`
- `evidence_disputed`
- `constitutional_spillover`
- `framework_change`
- `precedent_heavy`
- `elevated_capture_risk`
- `emergency_review`

Multiple escalation reasons may accumulate.

---

## 13. Re-Run Rules

A panel re-run is required when one or more of the following occur:
- substantive proposal revision after panel lock,
- panel composition invalidation,
- parameter epoch mismatch discovered post hoc,
- required class omission,
- challenge upheld against one or more outputs,
- evidence bundle materially changed,
- hidden-context violation discovered,
- audit incompleteness invalidates protocol continuity.

A re-run must bind to:
- a new `panel_id`,
- the relevant proposal revision,
- the same or newer framework epoch as allowed by protocol,
- a new audit segment linked to prior history.

---

## 14. Challenge and Contestation

Panel selection is challengeable.
A valid challenge may target:
- missing required class,
- improper escalation,
- improper non-escalation,
- concentrated model lineage,
- conflict of interest,
- hidden operator intervention,
- silent mid-case panel mutation,
- use of suspended or outdated skill versions.

Challenge results must produce one of:
- panel confirmed,
- panel revised,
- panel voided and re-run,
- challenge denied with rationale.

---

## 15. Bootstrap Constraints

During bootstrap,
the following constraints apply by default:
- the minimum non-trivial panel size is fixed at `5`,
- the standard escalations are limited to `7` and `9`,
- required classes are fixed ex ante,
- panel-sizing rules may not be changed for an active case,
- degraded completion is disallowed by default,
- no ad hoc single-skill exception is permitted for non-trivial proposals,
- selection criteria changes apply prospectively only.

These rules may later be revised through meta-governance,
but never retroactively for already submitted proposals.

---

## 16. Anti-Gaming Requirements

The panel system must resist tactical manipulation.
At minimum,
it must prevent:
- selecting easier skills for politically favored proposals,
- shrinking panels to accelerate desired outcomes,
- inflating panels to stall undesired outcomes,
- replacing adversarial skills with polite duplicates,
- clustering all skills under one implementation lineage,
- using emergency logic to bypass plurality,
- mutating panel rules mid-case,
- hiding missing-class failures behind convenience language.

Every panel selection system must be auditable for these abuse patterns.

---

## 17. Required Audit Fields

Every panel event must append to the audit record.
At minimum,
panel-related audit fields include:
- `proposal_id`
- `proposal_revision_id`
- `panel_id`
- `panel_spec_id`
- `framework_epoch`
- `parameter_epoch`
- `target_panel_size`
- `selected_classes`
- `selected_skills`
- `selected_skill_versions`
- `escalation_reason_set`
- `selection_rationale`
- `lock_timestamp`
- `panel_revision_reason` (nullable)
- `completion_status`
- `challenge_refs`

---

## 18. Invariants

The following invariants must hold:
- every non-trivial proposal has a valid plural panel,
- every required class is represented,
- five is the minimum deliberative quorum,
- panel rules are not retuned for active cases,
- panel mutation after lock is never silent,
- missing-class states are visible,
- adversarial review is mandatory,
- panel completion is auditable,
- challenge paths remain open.

---

## 19. Closing Principle

The panel is where plurality becomes protocol.

Its purpose is not to make the system look thoughtful.
Its purpose is to prevent civic-grade proposals from being laundered through narrow cognition,
hidden convenience,
or concentrated machine authority.

A valid panel does not guarantee truth.
It guarantees that legitimacy-critical reasoning passed through structured plurality rather than private compression.
