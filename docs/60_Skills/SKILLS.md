# SKILLS

## 1. Purpose

This document defines the canonical specification for civic skills.

A skill is a bounded public reasoning component used by the system to examine proposals,
produce structured outputs,
expose constraints,
and preserve plural cognitive processing without creating a new sovereign authority.

This document specifies:
- what a skill is,
- what classes of skills must exist,
- what inputs and outputs are allowed,
- how skills are versioned,
- how skills are selected into panels,
- how skills are challenged, replaced, suspended, and retired,
- which constraints prevent skills from becoming hidden governance.

This is a protocol and information contract.
It is not tied to any single model provider, software runtime, or deployment topology.

---

## 2. Design Stance

The system uses skills to compress expertise into civic form.

Skills:
- do not vote,
- do not rule,
- do not possess legitimacy,
- do not silently mutate active governance,
- do not replace public judgment.

Their role is limited and instrumental.
They exist to improve public reasoning,
not to substitute for the public.

The design goal is:
**replaceable cognitive plurality, not authoritative machine consensus.**

---

## 3. Definition

A **skill** is a publicly defined reasoning role with:
- a declared class,
- a bounded scope,
- a stable input contract,
- a stable output contract,
- explicit constraints,
- versioned implementation,
- auditable runtime behavior.

A skill is not just a model.
A skill is the combination of:
- role definition,
- allowed methods,
- input policy,
- output schema,
- version metadata,
- runtime implementation,
- challengeability conditions.

One model may implement multiple skills.
One skill may be implemented by multiple models over time.

---

## 4. Skill Principles

All skills must preserve the following properties:
- **bounded scope**: a skill may reason only within its declared role,
- **public legibility**: the skill's purpose and constraints must be inspectable,
- **replaceability**: no skill may be irreplaceable in principle or in practice,
- **contestability**: outputs may be challenged without breaking protocol continuity,
- **plurality**: system design prefers panels over singular authority,
- **version visibility**: meaningful changes create a new skill version,
- **uncertainty exposure**: unknowns and confidence limits must be explicit,
- **no silent governance**: skills may advise on rules but may not mutate rules by use,
- **no hidden memory monopoly**: a skill may consume public knowledge but may not become the exclusive runtime of civic cognition.

---

## 5. Skill Classes

The system must support a registry of skill classes.

At minimum, the following classes exist:
- `rights_constitutional`
- `implementation_feasibility`
- `economic_resource`
- `anti_capture_audit`
- `adversarial_critique`

Additional supported classes may include:
- `local_impact`
- `minority_protection`
- `evidence_quality`
- `dependency_mapping`
- `reversibility_assessment`
- `historical_precedent`
- `interoperability`
- `operational_risk`
- `civic_translation`
- `participation_accessibility`
- `other`

`civic_translation` and `participation_accessibility` support the participation layer
(`45_Participation/PARTICIPATION_MODEL.md`): plain-language rendering, translation,
accessible formats, and participation support.
They are ordinary classes, not additions to the mandatory minimum in §5.1,
and their outputs must preserve links to canonical sources, dissent, and uncertainty.

### 5.1 Mandatory Minimum for Non-Trivial Proposals

Every non-trivial proposal must be reviewed by a plural panel containing at least one skill from each required class:
- rights / constitutional,
- implementation / feasibility,
- economic / resource,
- anti-capture / audit,
- adversarial critique.

This is the minimum deliberative quorum of classes.

### 5.2 Optional Expansion

Additional classes may be required by:
- proposal domain,
- reversibility class,
- governance layer,
- constitutional spillover,
- routing sensitivity,
- bootstrap constraints,
- challenge outcomes.

---

## 6. Logical Structure of a Skill

A skill has two main levels:

### 6.1 Skill (Logical Role)
Represents the stable public reasoning role.

Examples:
- Constitutional Rights Review
- Implementation Feasibility Review
- Anti-Capture Assessment
- Adversarial Counter-Case

### 6.2 SkillVersion (Runnable Realization)
Represents a concrete implementation of the role.

A `SkillVersion` may specify:
- model or engine reference,
- prompt or instruction package reference,
- tool policy,
- retrieval policy,
- output schema version,
- context policy,
- runtime limits,
- effective dates,
- challenge status.

The logical role should persist even when implementation changes.

---

## 7. Skill Registry Requirements

All active skills must exist in a public or publicly auditable registry.

A skill registry entry must include at least:
- `skill_id`
- `skill_class`
- `name`
- `description`
- `scope_statement`
- `out_of_mandate_questions`
- `temporal_scope`
- `prohibited_behaviors`
- `conflict_of_interest_policy_ref`
- `default_input_contract_ref`
- `default_output_contract_ref`
- `owner_type`
- `current_status`
- `available_versions`
- `created_at`
- `retired_at` (nullable)

Three of these fields sharpen the scope contract:

- `out_of_mandate_questions` — questions the skill is explicitly not authorized to answer.
  Where `scope_statement` says what the role covers, this field names what it must decline,
  so mandate creep is detectable rather than arguable.
- `temporal_scope` — the time window over which the skill's analysis claims validity
  (e.g., current-law-only, historical, forward-projection horizon).
  Outputs that reason outside the declared window are out of contract.
- `conflict_of_interest_policy_ref` — the conflict-of-interest policy that applies to the
  *role itself* (e.g., a funding-review skill's relationship to treasury actors),
  complementing the executor-level conflict rules in `EXECUTOR_MODEL.md` §14.

A skill registry may also include:
- domain tags,
- preferred proposal types,
- challenge history,
- reliability indicators,
- comparative benchmark references,
- public commentary references.

The registry must not require trust in a single vendor or operator.

---

## 8. Input Contract

Every skill must declare a stable input contract.

A canonical skill input bundle may include:
- proposal id,
- proposal revision id,
- governance layer,
- current framework epoch,
- current parameter epoch,
- relevant evidence bundle,
- prior challenge bundle,
- required questions for this skill class,
- allowed context attachments,
- prohibited context attachments,
- runtime configuration metadata.

### 8.1 Required Input Properties

Every skill input must preserve:
- **proposal identity binding**,
- **revision binding**,
- **epoch binding**,
- **context completeness declaration**,
- **evidence provenance visibility**.

### 8.2 Input Limits

A skill must not receive:
- hidden operator instructions that materially change its public role,
- undisclosed side-channel context,
- future-state parameters not yet active for the proposal,
- private instructions that contradict the registry definition,
- selective evidence bundles without disclosure.

### 8.3 Input Policy Declaration

Each `SkillVersion` must declare:
- whether retrieval is allowed,
- whether external tools are allowed,
- which context windows are normative,
- what truncation policy applies,
- what evidence ranking policy applies.

---

## 9. Output Contract

Every skill must emit a structured output.

At minimum, a canonical `SkillOutput` contains:
- `skill_output_id`
- `proposal_id`
- `proposal_revision_id`
- `skill_id`
- `skill_version_id`
- `skill_class`
- `output_timestamp`
- `position_summary`
- `core_findings`
- `constraints`
- `unknowns`
- `assumptions`
- `confidence_statement`
- `capture_risk_note` (required for classes where relevant, especially anti-capture)
- `rights_note` (required for constitutional relevance)
- `reversibility_note`
- `recommended_questions`
- `evidence_refs`
- `dissent_hooks`
- `runtime_metadata_ref`

### 9.1 Output Constraints

A skill output must not:
- present itself as final public judgment,
- suppress declared uncertainty,
- silently merge normative claims into factual claims,
- overwrite prior outputs,
- erase dissent,
- imply legitimacy it does not possess.

### 9.2 Class-Specific Mandatory Fields

#### rights_constitutional
Must include:
- rights touched,
- likely conflicts,
- constitutional uncertainty,
- minimum rights floor.

#### implementation_feasibility
Must include:
- execution prerequisites,
- failure modes,
- operational dependencies,
- implementation complexity note.

#### economic_resource
Must include:
- resource implications,
- cost assumptions,
- distributional concerns,
- budget uncertainty note.

#### anti_capture_audit
Must include:
- concentration risks,
- chokepoint mapping,
- dependency risks,
- enclosure risks,
- asymmetry note.

#### adversarial_critique
Must include:
- strongest counter-case,
- edge-case failure,
- hidden premise critique,
- what supporters may be overlooking.

---

## 10. Required Behavior by Class

The system should not treat skill classes as stylistic labels.
Each class carries substantive obligations.

### 10.1 rights_constitutional
Purpose:
- identify rights implications,
- detect constitutional spillover,
- articulate floors that ordinary preference cannot override.

### 10.2 implementation_feasibility
Purpose:
- expose practical constraints,
- identify execution prerequisites,
- distinguish aspirational language from actionable pathways.

### 10.3 economic_resource
Purpose:
- identify costs, opportunity costs, allocation shifts, and dependency effects.

### 10.4 anti_capture_audit
Purpose:
- detect where power, dependency, interpretive control, or runtime monopoly may concentrate.

### 10.5 adversarial_critique
Purpose:
- prevent false consensus,
- generate the strongest critique compatible with the proposal record,
- stress-test hidden assumptions.

---

## 11. Skill Selection into Panels

Skill selection is governed by `PANEL_SELECTION.md`, but the following rules apply here.

### 11.1 Minimum Panel Size
For non-trivial proposals, the minimum active panel size is **5**.

### 11.2 One Seat, One SkillVersion, One Output
A panel seat in a proposal cycle is fulfilled by one declared `SkillVersion`.
A rerun creates a new `SkillRun` and new `SkillOutput`; it does not silently replace prior output.

### 11.3 Class Coverage Over Brand Preference
Panels are selected to satisfy class coverage and deliberative plurality,
not to privilege particular vendors, models, or institutions.

### 11.4 Escalation Beyond Five
A panel may escalate to **7** or **9** when required by:
- constitutional spillover,
- strong reversibility concerns,
- multi-domain impact,
- elevated uncertainty,
- challenge outcomes,
- bootstrap-defined escalation rules.

### 11.5 Missing Class Rule
A proposal may not advance as fully analyzed if a required class is missing,
except under explicit emergency protocol rules.
A missing required class must be logged as a blocking condition.

---

## 12. Versioning Rules

Every meaningful change to a skill implementation must create a new `SkillVersion`.

Meaningful changes include:
- model replacement,
- prompt contract change,
- retrieval policy change,
- tool access change,
- output schema change,
- evidence ranking change,
- context policy change,
- uncertainty handling change,
- challenge remediation that affects behavior.

### 12.1 Version Binding
Each proposal cycle binds to the exact `SkillVersion` used for each panel seat.
This binding is immutable in the audit history.

### 12.2 No Silent Drift
A skill must not change effective behavior for active proposal cycles without a new version identifier.

### 12.3 Comparative Runs
The system may allow comparative or adversarial multi-version runs,
but such runs must be explicitly labeled and logged.

---

## 13. Runtime Metadata

Every skill run must produce runtime metadata sufficient for replay, comparison, or review.

The metadata should include at least:
- input bundle reference,
- evidence bundle reference,
- context policy reference,
- tool usage declaration,
- retrieval usage declaration,
- token or runtime budget declaration,
- start timestamp,
- end timestamp,
- runtime status,
- error status if applicable.

The goal is not operational voyeurism.
The goal is civic auditability.

---

## 14. Challengeability

Skills are challengeable at multiple levels:
- role definition,
- input completeness,
- evidence selection,
- runtime integrity,
- output sufficiency,
- class assignment,
- version suitability,
- panel seat fit.

### 14.1 Skill Challenge Types

Supported challenge types may include:
- `missing_required_field`
- `scope_violation`
- `hidden_assumption`
- `evidence_omission`
- `class_mismatch`
- `runtime_irregularity`
- `undeclared_policy_change`
- `false_consensus`
- `confidence_overstatement`
- `capture_risk_understatement`

### 14.2 Effects of a Challenge
A successful challenge may trigger:
- annotation,
- rerun,
- addition of extra panel seats,
- temporary suspension of a `SkillVersion`,
- replacement of a skill version for future cycles,
- escalation into meta-review.

Challenges do not erase prior outputs.
They layer new process over visible history.

---

## 15. Suspension and Retirement

A skill or skill version may be:
- active,
- challenged,
- restricted,
- suspended,
- retired.

### 15.1 Suspension Grounds
Suspension may occur when there is evidence of:
- undeclared behavior drift,
- repeated scope violations,
- repeated runtime integrity failure,
- systematic omission of required fields,
- persistent hidden bias incompatible with declared role,
- operator tampering,
- capture through exclusive dependency.

### 15.2 Retirement
Retirement ends future use,
but does not erase historical use.
Retired skills remain visible in historical records.

---

## 16. Replaceability Requirements

No skill should be structurally irreplaceable.

To preserve replaceability:
- role definitions must remain separate from implementation,
- output schemas must be public,
- required classes must be satisfiable by more than one potential implementation over time,
- panel selection must not depend on a single vendor,
- audit and challenge mechanisms must survive implementation replacement.

This is a constitutional anti-capture requirement,
not merely an engineering preference.

---

## 17. Public Constraints

A skill must operate under declared public constraints.

These should include at least:
- scope statement,
- prohibited claims,
- confidence discipline,
- evidence discipline,
- disagreement preservation requirement,
- no-final-authority rule,
- no-hidden-memory-monopoly rule,
- no-active-case-rule-mutation rule.

A skill that cannot operate under public constraints is unfit for civic use.

---

## 18. Relationship to Public Knowledge

Skills may consume public knowledge,
public comments,
public evidence,
and prior civic outputs.

However:
- they may not silently rewrite public memory,
- they may not become the sole executable representation of civic history,
- they may not privatize the reasoning substrate,
- they may not treat their own prior outputs as unchallengeable truth.

The system may ingest public knowledge continuously,
but no skill may become the exclusive runtime of civic cognition.

---

## 19. Bootstrap Constraints

During bootstrap, stricter constraints should apply.

Bootstrap-specific rules may include:
- narrower allowed skill classes,
- fixed minimum panel size,
- fixed output schema,
- stricter challenge thresholds for active skill versions,
- restricted external retrieval,
- mandatory comparative runs for certain classes,
- temporary prohibition on automatic skill self-upgrade.

Bootstrap exists to reduce hidden degrees of freedom while trust is still being built.

---

## 20. Failure Modes

Common skill failure modes include:
- role bleed across classes,
- false consensus,
- hidden instruction drift,
- evidence laundering,
- confidence inflation,
- rhetorical overreach,
- capture-risk blindness,
- version monoculture,
- panel class omission,
- audit-incomplete runs.

The system should treat these as expected engineering and governance risks,
not as rare anomalies.

---

## 21. Minimal Viable Skill Set

A minimal viable civic system should be able to instantiate at least:
- one active rights / constitutional skill,
- one active implementation / feasibility skill,
- one active economic / resource skill,
- one active anti-capture / audit skill,
- one active adversarial critique skill.

Anything less than this cannot produce the minimum deliberative plurality required for non-trivial proposals.

---

## 22. Normative Summary

A civic skill is not an oracle.
It is a bounded public reasoning instrument.

Its legitimacy comes from:
- declared role,
- visible constraints,
- versioned behavior,
- contestable output,
- replaceable implementation,
- inclusion inside a plural civic panel.

The system should therefore optimize not for singular brilliance,
but for structured plurality under audit.
