# PILOT_DOMAIN_TEMPLATE

## 1. Purpose

This template defines how to specify a pilot domain for the first bounded deployments of the system.

Its purpose is to ensure that every pilot candidate is described in a comparable, testable, and operationally honest way.

A pilot domain definition should answer:
- what community this is for,
- what recurring problem it addresses,
- why this is a good bootstrap fit,
- what kind of packet usefulness is expected,
- what kind of challenge path is realistic,
- what risks make this pilot inappropriate or premature.

This template complements:
- `PROTOTYPE_PLAN.md`
- `COMMUNITY_FORMATION.md`
- `BOOTSTRAP_REALITY_CHECK.md`
- `MINIMUM_VIABLE_PNYX.md`
- `PACKET_FORMAT.md`
- `SKILL_EVALUATION.md`

A pilot domain is not just a topic.
It is a bounded civic-use environment.

---

## 2. Pilot Domain Record

Every pilot should produce a `PilotDomainRecord`.

Suggested fields:
- `PilotDomainId`
- `Title`
- `CommunityName`
- `CommunityType`
- `PrimaryIssueClass`
- `ScopeDescription`
- `AdvisoryOrBinding`
- `ExpectedCycleLength`
- `AffectedParties`
- `CurrentProcessDescription`
- `CurrentPainPoints`
- `WhyPnyxMayHelp`
- `PacketUsefulnessHypothesis`
- `ChallengeUseHypothesis`
- `PilotFitAssessment`
- `KnownRisks`
- `SuccessMetrics`
- `FailureMetrics`
- `ExitConditions`
- `OperatorLoadEstimate`
- `SkillProfile`
- `CommunityReadinessScore`
- `BootstrapDebtNotes`

Fields may be extended,
but the core record should remain comparable across pilots.

---

## 3. Template

## 3.1 Pilot Identity

### Title
`<short pilot title>`

### PilotDomainId
`<stable identifier>`

### CommunityName
`<name of community or working label>`

### CommunityType
Choose one or more:
- neighborhood group
- cooperative
- volunteer association
- advocacy group
- local civic network
- member body
- union or labor-related body
- municipal-adjacent experimental group
- other: `<describe>`

### AdvisoryOrBinding
Default:
- advisory-only

If not advisory-only, explain why this is still bootstrap-safe.

---

## 3.2 Community Description

### Who is the community?
Describe:
- approximate size,
- why the members share a common issue space,
- what kind of coordination history they already have,
- whether they are already organized or still loose.

### Why this community?
Explain:
- why this is a strong first or early adopter,
- what recurring pain they actually experience,
- why they might return for a second cycle.

### Trust posture
Describe:
- current trust level inside the group,
- expected skepticism level,
- whether the group can tolerate one imperfect experimental cycle.

---

## 3.3 Issue Domain

### PrimaryIssueClass
Choose one or more:
- local prioritization
- policy draft review
- budgeting discussion
- proposal comparison
- public comment synthesis
- issue triage
- cooperative governance support
- working-group decision support
- other: `<describe>`

### ScopeDescription
Describe the issue space in plain terms.

Good scope:
- bounded,
- recurring,
- understandable,
- important enough to matter,
- not catastrophic if one cycle is weak.

Bad scope:
- constitution-level redesign,
- national emergency decisions,
- highly symbolic identity conflict as a first cycle,
- irreversible high-stakes commitments.

### Example live issues
List 3–5 real issue examples the pilot might process.

---

## 3.4 Current Process Baseline

### CurrentProcessDescription
How does the group handle these issues today?

Examples:
- meetings,
- group chat,
- email threads,
- shared docs,
- informal organizer decisions,
- board review,
- ad hoc polling.

### CurrentPainPoints
Describe current pain points such as:
- thread chaos,
- repetitive arguments,
- poor memory across cycles,
- hidden disagreement,
- weak minority visibility,
- fatigue,
- poor comparison of options,
- unclear rationale,
- overdependence on one organizer.

### Why current process is insufficient
State the specific deficiency the pilot is trying to improve.

---

## 3.5 Why Pnyx May Help

### PacketUsefulnessHypothesis
State a falsifiable hypothesis.

Example structure:
> "If this issue domain is processed through a plural skill packet, participants will better understand trade-offs and disagreements than they do through the current thread-based process."

### ChallengeUseHypothesis
State a falsifiable hypothesis.

Example structure:
> "At least one materially engaged participant will use the challenge path when they feel the packet omitted or distorted something important."

### AuditLegibilityHypothesis
State whether the group can realistically benefit from simplified audit visibility.

### RepeatUseHypothesis
State why you think the group might return for another cycle.

---

## 3.6 Affected Parties

### Directly affected parties
List the people or groups most directly affected by the issue domain.

### Indirectly affected parties
List broader groups touched by the outcome or framing.

### Underrepresented or low-legibility groups
Identify:
- who is likely missing,
- whose harms may be easy to omit,
- whose participation may be structurally weaker,
- what translation or facilitation may be needed.

This section should be explicit.
Do not assume formal openness means affected presence.

---

## 3.7 Pilot Fit Assessment

### Why this is bootstrap-appropriate
Explain why this pilot fits:
- advisory-first deployment,
- bounded scope,
- repeatable issue cycles,
- moderate disagreement,
- manageable stakes,
- clear packet usefulness comparison.

### Why this is not premature
Explain why the pilot does **not** require subsystems that are still deferred.

Examples:
- no treasury dependence,
- no binding constitutional authority,
- no emergency enforcement,
- no large-scale identity mechanism.

### Risks of poor fit
Explain what might make this pilot a bad fit despite initial appeal.

---

## 3.8 Skill Profile

### Proposed minimum skill set
List the initial skills expected for this pilot.

Suggested baseline:
- rights / constitutional caution
- adversarial critic
- implementation / feasibility
- evidence discipline
- anti-capture / power analysis

Optional additions:
- local context skill
- economic/resource skill
- accessibility or translation skill

### Why this skill mix fits
Explain how the chosen skills match the issue domain.

### Known model dependence
State whether the pilot depends heavily on:
- one model family,
- one prompt scaffold,
- one operator-maintained context pack,
- one evaluator.

This should be named early.

---

## 3.9 Packet Expectations

### What a useful packet should do here
Describe what "good enough" packet quality means in this domain.

Examples:
- clearly separate options,
- show strongest disagreement,
- surface unknowns,
- identify minority harm,
- preserve local context,
- avoid fake consensus,
- reduce meeting fatigue.

### What a packet must not do here
Describe unacceptable outcomes.

Examples:
- erase the main conflict,
- produce generic middle-ground language,
- hide who bears the cost,
- overstate evidence,
- flatten local context,
- route toward a preferred answer by tone alone.

---

## 3.10 Challenge Expectations

### Who is likely to challenge?
Name likely challengers by role or group type.

### What would they likely challenge?
Examples:
- omitted issue,
- unfair framing,
- weak minority representation,
- missing local knowledge,
- false certainty,
- wrong classification or escalation.

### What makes the challenge path usable here?
State what low-friction challenge means in this domain.

Examples:
- short response form,
- plain-language objection channel,
- public "what changed" comparison,
- facilitator-supported submission.

---

## 3.11 Operational Load

### ExpectedCycleLength
How long is one full advisory cycle expected to take?

### ExpectedOperatorLoad
Estimate:
- operator time,
- reviewer time,
- facilitation time,
- skill evaluation time,
- challenge handling time.

### ExpectedCommunityLoad
Estimate what is being asked from participants:
- read one packet,
- submit one challenge,
- attend one meeting,
- compare two versions,
- give feedback.

If the community load is too high for the value returned, the pilot is a bad fit.

---

## 3.12 Success Metrics

Define specific success metrics for this pilot.

Possible measures:
- number of participants who used the packet in discussion,
- number of challenges submitted,
- number of packet revisions triggered,
- participant-reported clarity improvement,
- participant-reported fairness of disagreement representation,
- number of issues completed through the loop,
- repeat participation in second cycle,
- reduced reliance on one organizer or thread owner.

Metrics should be realistic and tied to this domain,
not copied abstractly from the full specification.

---

## 3.13 Failure Metrics

Define failure signals for this pilot.

Examples:
- packet not referenced in actual discussion,
- participants find packet harder than current process,
- no one can explain the disagreement map,
- challenges go unused because the path is confusing,
- same operator must manually rescue every cycle,
- the packet repeatedly misses obvious local knowledge,
- community declines a second cycle.

Failure metrics should be visible before the pilot begins.

---

## 3.14 Exit Conditions

State what would cause:
- continuation,
- simplification,
- pause,
- reset,
- stop.

A pilot without predeclared exit logic will drift.

---

## 3.15 Community Readiness

### CommunityReadinessScore
A simple qualitative rating:
- low
- medium
- high

### Why
Explain the score based on:
- issue urgency,
- trust level,
- tolerance for experimentation,
- facilitator presence,
- recurring pain,
- likely participant engagement.

### What would raise readiness
List 2–5 improvements that would make the pilot more viable.

---

## 3.16 Bootstrap Debt Notes

List debts specific to this pilot, such as:
- founder dependence,
- facilitator dependence,
- one-model dependence,
- weak challenge capacity,
- thin local representation,
- missing translation support,
- over-complex packet expectations,
- one-issue overfitting risk.

Every pilot should name its debts before it runs.

---

## 4. Minimal Evaluation Questions

Before approving a pilot domain, answer these questions:

1. Does this community have a real recurring proposal problem?
2. Would a packet plausibly help more than it burdens?
3. Are the stakes bounded enough for advisory-first use?
4. Is at least one challenger likely to appear?
5. Can this issue be compared against the current process?
6. Is the cognitive load survivable?
7. Can the same community realistically run a second cycle?
8. Are we choosing this pilot for fit rather than prestige?

A "no" on several of these should block the pilot.

---

## 5. Pilot Review Use

After each cycle, revisit the same record and append:

- what assumptions held,
- what assumptions failed,
- what the packet got right,
- what the packet missed,
- whether challenge was usable,
- whether the community wants another cycle,
- whether this remains the right domain.

A pilot domain record is a living test artifact,
not a one-time application form.

---

## 6. Closing Principle

A good pilot domain is not the most grand or politically dramatic one.

It is the one where:
- the community has real recurring pain,
- packet usefulness can be felt,
- challenge can realistically happen,
- failure is survivable,
- and repeated use is possible.

The first successful pilot should be boring in all the right ways.
