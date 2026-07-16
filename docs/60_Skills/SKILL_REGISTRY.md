# SKILL_REGISTRY

## 1. Purpose

The Skill Registry is the institutional layer that determines which skills may participate in civic panels,
under what conditions they may be used,
how they are versioned and audited,
and how the system avoids turning the skill supply layer into a hidden concentration of power.

The registry exists to solve four problems simultaneously:
- quality control,
- provenance and accountability,
- replaceability,
- anti-monopoly resilience.

The registry is not a sovereign body.
It is a constrained public infrastructure for admitting, tracking, challenging, suspending, and retiring skills.

---

## 2. Core Principles

The registry shall operate under the following principles:

- no single provider dependency,
- no hidden skill admission,
- no silent skill replacement,
- no class monopoly,
- no permanent registry immunity,
- public provenance over opaque prestige,
- challengeability over institutional deference,
- diversity of implementation where possible,
- stronger-path bias where registry confidence is weak.

A skill may be useful without being trusted.
A skill may be admitted without being default.
A skill may be default without being irreplaceable.

---

## 3. Registry Scope

The registry governs:
- skill admission,
- skill class assignment,
- skill status,
- skill metadata,
- skill provenance,
- version lineage,
- provider diversity requirements,
- challenge and suspension,
- retirement and replacement,
- bootstrap restrictions on skill supply.

The registry does not govern civic outcomes.
It governs eligibility to participate in civic reasoning.

---

## 4. Definitions

### 4.1 Skill
A bounded public reasoning component operating under the contract defined in `SKILLS.md`.

### 4.2 Skill Class
A functional category such as:
- rights / constitutional,
- adversarial critique,
- implementation / feasibility,
- economic / resource,
- anti-capture / audit,
- evidence quality,
- local impact,
- minority protection.

### 4.3 Provider
The organizational, cooperative, institutional, or technical source responsible for publishing and maintaining a skill.

### 4.4 Registry Entry
A versioned public record declaring that a specific skill version is known to the system,
its class assignment,
its provenance,
its allowed use,
and its current status.

### 4.5 Admission
The process through which a skill version becomes eligible for civic use.

### 4.6 Suspension
A reversible status preventing a skill from being selected for new panels.

### 4.7 Retirement
A terminal status indicating a skill version or provider should no longer be used.

### 4.8 Default Skill Pool
The subset of admitted skills eligible for ordinary panel selection absent a specific reason to widen or constrain the pool.

---

## 5. Registry Objectives

The registry must maintain the following objectives simultaneously:

1. Ensure enough skill supply exists for plural panels.
2. Prevent any one provider from becoming the implicit runtime of civic reason.
3. Preserve traceability from output to skill version and provider lineage.
4. Allow challenge and replacement without collapsing continuity.
5. Preserve class diversity, not merely model count.
6. Bias toward stronger review paths where registry confidence is low or concentration is high.

---

## 6. Registry Entry Schema

Each registry entry must contain at minimum:

- `SkillId`
- `SkillVersionId`
- `SkillName`
- `SkillClass`
- `ProviderId`
- `ProviderType`
- `ImplementationType`
- `ModelLineage`
- `PromptContractHash`
- `ToolingContractHash`
- `EvidencePolicyVersion`
- `AdmissionStatus`
- `DefaultEligibility`
- `BootstrapEligibility`
- `DateAdmitted`
- `AdmittedUnderFrameworkEpoch`
- `DeclaredLimitations`
- `KnownFailureModes`
- `TemporalScope`
- `OutOfMandateQuestions`
- `ConflictOfInterestPolicyRef`
- `ChallengeHistoryRef`
- `SuspensionHistoryRef`
- `ReplacementCandidates`
- `PublicDocumentationRef`
- `ContactOrStewardRef`

`TemporalScope`, `OutOfMandateQuestions`, and `ConflictOfInterestPolicyRef`
mirror the skill-contract fields defined in `SKILLS.md` §7:
the validity window the analysis claims, the questions the role must decline,
and the conflict-of-interest policy applying to the role itself
(as distinct from executor-level conflicts, `EXECUTOR_MODEL.md` §14).

Optional but recommended:
- benchmark summary,
- external audit summary,
- reproducibility instructions,
- funding or sponsorship disclosure,
- dependency disclosures.

---

## 7. Skill Statuses

A skill version must exist in one and only one of the following statuses:

- `candidate`
- `admitted_non_default`
- `admitted_default`
- `bootstrap_allowed`
- `bootstrap_prohibited`
- `restricted_use`
- `suspended`
- `retired`
- `deprecated`

Status changes are public, versioned, and audit-bound.
No status change may be applied silently.

---

## 8. Admission Requirements

A skill version may be admitted only if all minimum conditions are met:

### 8.1 Public Identifiability
The provider, steward, or accountable publisher must be publicly identifiable.
Anonymous skill publication may exist for research,
but anonymous skills shall not be admitted to default civic use.

### 8.2 Reproducible Contract Disclosure
The skill must disclose enough information to make its contract inspectable, including:
- class claim,
- input/output contract,
- version hash or equivalent identifier,
- major dependencies,
- declared limitations,
- challenge contact path.

### 8.3 Class Justification
The claimed skill class must be justified and challengeable.
No provider may self-classify into a protected or sensitive class without registry review.

### 8.4 Minimum Evaluation
Before admission, the skill must undergo a minimum evaluation including:
- contract conformance,
- evidence discipline,
- output traceability,
- adversarial robustness sampling,
- declared limitation sufficiency.

### 8.5 No Silent Dependencies
Material upstream dependencies must be declared.
A skill relying on hidden external services, hidden retrieval, or hidden mutable prompts is not eligible for default admission.

### 8.6 Replaceability Plan
The registry entry must list at least one replacement path or explain why temporary single-supplier reliance is being tolerated.
Single-supplier admission without declared replacement planning is prohibited except under explicit bootstrap exception.

---

## 9. Default Eligibility Rules

Admission alone does not imply default eligibility.
A skill may be admitted but excluded from ordinary default selection if:
- it serves a niche function,
- concentration risk is high,
- reproducibility is weak,
- challenge history is unresolved,
- provider dependence is excessive,
- bootstrap restrictions prohibit its default use.

Default eligibility requires a stronger standard than mere admission.

---

## 10. Class Diversity Requirements

For any class used in ordinary civic panels,
the registry should maintain more than one admitted provider where feasible.

The registry shall track the following per class:
- number of admitted skills,
- number of distinct providers,
- number of distinct implementation lineages,
- number of bootstrap-eligible skills,
- challenge and suspension frequency.

A class is considered **concentrated** if any of the following applies:
- only one provider is admitted,
- more than half of admitted skills in the class depend on the same provider,
- more than half of admitted skills in the class share the same hidden upstream dependency,
- effective panel diversity is illusory because distinct skill labels map to one interpretive source.

Class concentration is not an automatic ban,
but it must trigger stronger-path bias in selection and review.

---

## 11. Provider Concentration Rules

The registry must explicitly monitor provider concentration risk.

### 11.1 Soft Limit
No ordinary panel should rely on a majority of skills from one provider when avoidable.

### 11.2 Hard Concern Threshold
If a provider supplies a decisive share of the default pool,
registry review must consider:
- forced widening of the pool,
- temporary default restrictions,
- targeted recruitment of alternative providers,
- increased challenge rights,
- disclosure of concentration risk in packets.

### 11.3 Illusory Diversity Ban
Distinct skill names or wrappers do not count as diversity if their reasoning runtime, prompt runtime, or dependency chain is materially identical.

---

## 12. Bootstrap-Specific Constraints

During bootstrap,
the registry shall operate under stricter anti-capture assumptions.

### 12.1 Bootstrap Allowed Skills
A skill may be `bootstrap_allowed` only if:
- its provider is disclosed,
- its contract is inspectable,
- its versioning is stable,
- its dependency chain is sufficiently known,
- it does not create hidden remote dependency,
- it can be suspended without collapsing the whole loop.

### 12.2 Bootstrap Single-Provider Exception
Temporary dependence on a limited provider set may be tolerated during bootstrap,
but it must be explicitly declared as constitutional debt,
with a public replacement plan and review trigger.

### 12.3 No Hidden Upgrade During Bootstrap
Bootstrap-allowed skills may not silently change model lineage,
prompt contract,
or tooling contract.
Any material change requires new registry entry or explicit successor linkage.

---

## 13. Challenge Process

Any registry entry must be challengeable.

Challenges may be raised on grounds including:
- false class claim,
- undeclared dependency,
- concentration concealment,
- systematic evidence laundering,
- prompt poisoning suspicion,
- reproducibility failure,
- conflict of interest,
- degraded performance on required contract,
- hidden remote execution,
- misleading public documentation.

A challenge does not automatically invalidate past outputs,
but it may:
- mark the skill as under review,
- remove default eligibility,
- force stronger-path selection,
- suspend bootstrap eligibility,
- trigger retrospective audit sampling.

---

## 14. Suspension Rules

A skill may be suspended if:
- it no longer satisfies admission requirements,
- material hidden changes are detected,
- challenge evidence crosses suspension threshold,
- provider accountability disappears,
- severe contract breach is confirmed,
- security or integrity failure is credible and unresolved.

Suspension affects future use only unless a separate retrospective invalidation process is triggered.

A suspended skill:
- may not be selected for new panels,
- remains in historical audit trails,
- may be restored only through explicit re-admission or reinstatement path.

---

## 15. Retirement and Deprecation

### 15.1 Deprecation
A skill version may be deprecated when a better or safer successor exists.
Deprecated skills may remain historically valid but should not be selected for ordinary future use.

### 15.2 Retirement
A skill version or provider may be retired when it is no longer suitable for any civic use.
Retirement does not erase historical participation.

### 15.3 Replacement Continuity
Where possible,
retirement should identify successor candidates,
but no successor inherits trust automatically.
Each successor must earn its own admission status.

---

## 16. Registry Governance

The Skill Registry itself is a governance object,
not a private vendor service.

The registry must therefore have:
- public admission criteria,
- public status changes,
- public challenge path,
- public concentration metrics,
- public historical lineage,
- meta-governance hooks for changing registry rules.

Registry rule changes are governed by `META_GOVERNANCE.md` and must not be altered during active-case handling.

---

## 17. Registry Decisions and Audit

Every material registry action must generate an audit record, including:
- action type,
- affected skill version,
- rationale,
- evidence basis,
- concentration impact,
- bootstrap impact,
- framework epoch,
- effective date,
- challenge path.

Registry actions include at minimum:
- admission,
- denial,
- default eligibility change,
- class reassignment,
- suspension,
- retirement,
- bootstrap eligibility change,
- concentration warning issuance.

---

## 18. Registry Invariants

The following invariants apply:

- no unregistered skill may participate in ordinary civic panels,
- no skill may become default without explicit registry status,
- no silent skill replacement,
- no hidden provider concentration counting as diversity,
- no active-case admission or suspension used as tactical selection manipulation,
- no registry action without audit record,
- no bootstrap dependence hidden as normal operation,
- no provider prestige standing in for public provenance.

---

## 19. Registry Failure Modes

The registry must explicitly watch for the following failure modes:
- registry capture by a provider cartel,
- nominal diversity with shared hidden dependencies,
- status inflation for politically convenient skills,
- over-strict admission causing artificial scarcity,
- under-strict admission causing panel pollution,
- challenge flooding as denial tactic,
- stealth deprecation of inconvenient skills,
- skill churn used to evade accountability,
- benchmark theater replacing real contract discipline.

Where failure mode ambiguity exists,
the registry shall prefer stronger-path bias over convenience.

---

## 20. Minimal Bootstrap Registry

At minimum,
a bootstrap registry must publish:
- all admitted skills,
- provider identity or steward identity,
- class assignment,
- version identifier,
- bootstrap eligibility,
- known limitations,
- concentration warnings,
- replacement plan where concentration is tolerated.

If the registry cannot provide this minimum,
its outputs may be used for research,
but not for binding civic panel selection.

---

## 21. Closing Principle

The Skill Registry exists to ensure that civic reasoning does not become dependent on an invisible priesthood of models,
prompts,
or vendors.

Skills may assist public judgment,
but the supply of skills must itself remain public,
challengeable,
diverse,
and replaceable.
