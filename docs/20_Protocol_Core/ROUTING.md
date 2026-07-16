# ROUTING

## 1. Purpose

This document defines how approved or approvable proposals are assigned to an execution path.

Its purpose is:
- to prevent ideological defaulting from replacing civic judgment,
- to make execution routing explicit, challengeable, and auditable,
- to distinguish legitimacy from implementation,
- to constrain both market and state concentration risks,
- to ensure that execution design remains subordinate to the shared values layer.

Routing is not a secondary implementation detail.
Routing is a load-bearing governance act.

---

## 2. Core Principle

The system does not ask whether market or state is correct in the abstract.
It asks which execution path best serves the shared values under the present conditions,
with the lowest acceptable combination of exclusion,
dependency,
capture,
and legitimacy risk.

Routing must therefore be:
- explicit,
- justified,
- challengeable,
- layer-aware,
- reversible where possible,
- auditable.

---

## 3. Scope

This document applies to:
- ordinary policy proposals,
- governance proposals with execution consequences,
- constitutional proposals where activation design matters,
- implementation updates,
- routing disputes,
- execution deviations,
- post-decision reviews,
- emergency execution assignments.

It does not define voting thresholds or panel composition.
Those are defined in adjacent documents.

---

## 4. Definitions

### 4.1 Routing
Routing is the protocol act by which a proposal or decision is assigned to an execution path.

### 4.2 Execution Path
An execution path is the institutional mode through which an approved decision is carried into practice.

### 4.3 Market Execution
Execution primarily through decentralized,
plural,
voluntary,
competitive,
or locally adaptive mechanisms.

### 4.4 State Execution
Execution primarily through public authority,
universal rules,
baseline guarantees,
coordination,
and enforceable obligations.

### 4.5 Hybrid Execution
Execution split across market and state components,
with explicit boundary rules,
hand-off conditions,
and oversight responsibilities.

### 4.6 Routing Contest
A routing contest exists when materially credible reasoning supports more than one route,
or when the chosen path materially affects legitimacy,
rights,
or capture risk.

### 4.7 Routing Override
A routing override is any departure from the currently assigned route after decision,
whether by emergency,
review,
implementation failure,
or formal revision.

---

## 5. Canonical Route Set

Routing is defined along two orthogonal axes, and both must be explicit.

**Mechanism axis.** Every routed proposal must be assigned exactly one primary route:
- `market`
- `state`
- `hybrid`
- `advisory_only`
- `defer_pending_evidence`

**Executor-form axis.** Where execution follows, the route must also declare what kind of actor delivers it:
- `public_institutional`
- `commons`
- `venture`
- `cooperative`
- `contracted`
- `hybrid`

The two axes answer different questions — *through which mechanism* versus *by what kind of actor* — and one must not be inferred from the other.
A `state`-mechanism route may be delivered by a contracted executor; a `market`-mechanism route may be delivered by a commons.

The route must be accompanied by:
- a routing rationale,
- a capture-risk note,
- a reversibility note,
- an execution-scope note,
- a review condition set.

Where execution follows, the route is completed by an `ExecutionMandate` (see `../90_Information/DATA_MODEL.md` §5.26) binding the executor to explicit permitted actions, resources, milestones, and public-return obligations.

No meaningful proposal may proceed with an implicit route.

---

## 6. Routing Inputs

Routing must be based on logged inputs.
At minimum,
a valid routing decision must consider:
- governance layer,
- proposal scope,
- affected domains,
- affected populations,
- rights implications,
- reversibility class,
- dependency risk,
- exclusion risk,
- enforcement need,
- universality need,
- local variation need,
- evidence sufficiency state,
- capture-risk assessment,
- implementation complexity,
- constitutional spillover,
- emergency status where applicable.

Routing must not be determined by hidden operator preference,
vendor preference,
or ideology alone.

---

## 7. Routing Questions

Every routing decision must answer,
at minimum,
the following questions:
- Can this be safely decentralized?
- Would a voluntary path create exclusion,
  abandonment,
  or unequal access?
- Does this require a universal baseline guarantee?
- Does implementation require enforceable obligation?
- Would private coordination create a choke point?
- Would public coordination create excessive concentration risk?
- Is local variation desirable or dangerous here?
- Is rapid experimentation preferable to uniformity?
- Is reversibility high enough to tolerate distributed trial?
- Does the proposal create long-term dependency on a single operator,
  platform,
  or administrative center?

These questions must remain visible in the audit path and briefing path.

---

## 8. Market Route Criteria

A proposal should prefer the `market` route when most of the following are true:
- decentralization is safe,
- local experimentation is valuable,
- voluntary participation does not destroy baseline fairness,
- exclusion risk is low or manageable,
- rights protection does not require central enforcement,
- implementation can remain plural,
- dependency on a single provider is avoidable,
- reversibility is moderate or high,
- failures can be contained locally,
- comparative learning across implementations is beneficial.

Typical market-route examples may include:
- open service experimentation,
- community procurement frameworks,
- modular local delivery,
- plural provider ecosystems,
- non-exclusive infrastructure participation.

The market route is not a default just because something is cheaper,
faster,
or privately operable.

---

## 9. State Route Criteria

A proposal should prefer the `state` route when most of the following are true:
- universal access is required,
- baseline protection is mandatory,
- rights protection depends on enforceable uniformity,
- exclusion risk is materially unacceptable,
- public coordination is necessary,
- free-rider or abandonment risk is high,
- the proposal concerns a shared public floor,
- implementation must be binding across the affected scope,
- concentration risk from private control exceeds concentration risk from public control,
- legitimacy requires common rule application.

Typical state-route examples may include:
- rights guarantees,
- universal minimum service obligations,
- anti-monopoly intervention,
- public registry obligations,
- emergency public coordination,
- baseline civic infrastructure.

The state route is not justified merely by administrative familiarity.

---

## 10. Hybrid Route Criteria

A proposal should prefer the `hybrid` route when:
- universal guarantees are required,
  but implementation can remain plural,
- the state must define floors,
  standards,
  or protections,
  while delivery may remain distributed,
- experimentation is useful,
  but must occur within a protected public boundary,
- local adaptation is valuable,
  but exclusion risk must remain constrained,
- market dynamism and public guarantees are both materially necessary.

A hybrid route is valid only if the boundary is explicit.
A hybrid route must define:
- what the state guarantees,
- what the market or civil layer may vary,
- who audits compliance,
- what triggers escalation from market-side failure,
- what triggers devolution from over-centralized state implementation,
- how participants challenge route drift.

Hybrid must not mean ambiguous.

---

## 11. Advisory and Deferred Routes

### 11.1 Advisory Only
`advisory_only` is appropriate where:
- the proposal is exploratory,
- no binding execution has been approved,
- evidence is not yet strong enough for implementation,
- the system is intentionally collecting civic signal before routing.

### 11.2 Defer Pending Evidence
`defer_pending_evidence` is appropriate where:
- routing is material,
- evidence is insufficient,
- acting under false certainty would create legitimacy or capture risk,
- a time-bounded evidence collection or pilot requirement is preferable.

Deferral must not become a hidden veto.
It must carry a review date or trigger.

---

## 12. Routing by Governance Layer

### 12.1 Policy Layer
Policy routing should optimize for value alignment,
implementation fitness,
and reversibility under uncertainty.

### 12.2 Governance Layer
Governance routing must additionally account for:
- institutional incentives,
- procedural power shifts,
- administrative choke points,
- enforceability asymmetries,
- future precedent effects.

### 12.3 Constitutional Layer
Constitutional routing demands the strongest discipline.
When rights,
anti-capture rails,
or legitimacy structures are implicated,
routing must privilege durable protection over convenience.

The deeper the layer,
the less acceptable casual routing becomes.

---

## 13. Hard Routing Stops

A proposal must not be routed to `market` when:
- baseline rights would become optional,
- exclusion of protected participants would be structurally likely,
- monopoly or lock-in is the probable outcome,
- the proposal creates unavoidable dependency on a single private actor,
- uniform enforceability is required for legitimacy.

A proposal must not be routed to `state` when:
- local experimentation is materially necessary,
- centralized execution would create unnecessary concentration,
- a universal state monopoly is not required for protection,
- the state path would create a stronger choke point than the problem it solves,
- diversity of implementation is itself a protected value in the case.

A proposal must not be routed to `hybrid` unless the boundary conditions are explicit enough to audit.

---

## 14. Routing Contest and Escalation

Routing should be escalated for expanded review when one or more of the following apply:
- the recommended route is materially disputed,
- evidence quality is contested,
- capture-risk notes diverge sharply,
- reversibility is low,
- affected communities are heterogeneous,
- implementation failure would be hard to recover from,
- the route itself changes the distribution of power.

Escalation may require:
- a 7- or 9-skill panel,
- additional evidence-quality review,
- explicit minority-protection review,
- extended review window,
- publication of route alternatives in the civic packet.

Routing disagreement must be surfaced,
not compressed.

---

## 15. Routing Output Contract

Every routing decision must produce a structured routing record containing at least:
- `routing_id`
- `proposal_id`
- `proposal_revision_id`
- `framework_epoch`
- `parameter_epoch`
- `governance_layer`
- `candidate_routes`
- `selected_route`
- `executor_form`
- `routing_rationale`
- `rights_note`
- `capture_risk_note`
- `dependency_note`
- `reversibility_note`
- `universality_note`
- `local_variation_note`
- `evidence_status`
- `contested_route_flag`
- `review_trigger_set`
- `activation_conditions`
- `audit_refs`

No route assignment is complete without this record.

---

## 16. Execution Conditions

A routing decision must define the conditions under which execution may begin.
At minimum,
these conditions should specify:
- whether a public decision threshold has been satisfied,
- whether the review window has closed,
- whether any challenge is unresolved,
- whether required evidence or implementation readiness is present,
- whether deferred activation is required,
- whether post-decision guardrails are active.

Execution may not begin simply because a route exists.
A route is a path,
not a blank check.

---

## 17. Route Drift and Override

### 17.1 Route Drift
Route drift occurs when implementation behavior no longer matches the assigned route.
Examples include:
- a nominally plural market route collapsing into a de facto monopoly,
- a state route quietly outsourcing core legitimacy functions,
- a hybrid route blurring responsibility so that no actor is accountable.

Route drift must be logged,
challengeable,
and reviewable.

### 17.2 Overrides
Routing overrides are permitted only through:
- emergency procedure,
- formal review,
- approved proposal revision,
- post-decision implementation correction.

Overrides must be:
- explicit,
- time-bounded where emergency-based,
- auditable,
- non-silent,
- challengeable.

---

## 18. Emergency Routing

Emergency routing may temporarily shorten ordinary execution design,
but may not erase accountability.

Emergency routing must define:
- emergency scope,
- emergency duration,
- why ordinary routing was insufficient,
- what rights limitations are implicated,
- what review opens automatically,
- what expiry condition ends the emergency route.

Emergency routing must default to the narrowest effective intervention.
The emergency path is a constrained exception,
not a standing shortcut.

---

## 19. Bootstrap Routing Rules

During bootstrap,
the following routing controls should be fixed ex ante:
- canonical route set,
- hard routing stops,
- minimum routing record fields,
- escalation triggers for contested routing,
- prospective-only activation of routing-rule changes,
- emergency routing limits.

During bootstrap,
routing-rule changes:
- must not apply to an active case,
- must activate only prospectively,
- must be explicitly audit-logged,
- should receive meta-governance review when they alter power distribution,
- should not be hidden inside unrelated framework changes.

Bootstrap should prefer clarity over optimization.

---

## 20. Post-Decision Routing Review

After execution begins,
routing remains reviewable.
A post-decision review may be triggered by:
- implementation failure,
- exclusion outcomes,
- concentration drift,
- rights violations,
- evidence changes,
- challenge by affected groups,
- mismatch between expected and observed dependency patterns.

Review outcomes may include:
- route confirmation,
- route correction,
- route suspension,
- route reversal,
- shift to hybrid,
- shift from hybrid to a single clearer path,
- proposal re-opening where routing error undermines legitimacy.

---

## 21. Audit Requirements

Every routing act,
routing contest,
routing override,
and routing review must appear in the audit log.

At minimum,
a routing audit trail must show:
- what routes were considered,
- what criteria were applied,
- which risks dominated,
- why alternatives were rejected,
- who or what produced the recommendation,
- what challenges were filed,
- what execution conditions were attached,
- whether route drift later occurred.

Routing must be replayable as public reasoning,
not remembered as administrative folklore.

---

## 22. Invariants

The routing subsystem must preserve the following invariants:
- no meaningful proposal without explicit route or explicit deferral,
- no routing without rationale,
- no hidden ideology as routing logic,
- no silent route drift,
- no emergency route without auto-review,
- no retroactive routing-rule mutation for active cases,
- no hybrid route without explicit boundary conditions,
- no execution before route conditions are satisfied.

---

## 23. Closing Principle

Routing is where political judgment becomes institutional behavior.

For that reason,
the route must never be treated as a mere implementation afterthought.
It is the bridge between legitimacy and execution,
and therefore one of the places where capture,
exclusion,
and hidden power are most likely to appear.

The system must route consciously,
publicly,
and under audit.
