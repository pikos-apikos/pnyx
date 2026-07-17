# DATA_MODEL

## 1. Purpose

This document defines the core information model of the system.

It answers:
- what first-class objects exist,
- which fields are required,
- how objects relate to each other,
- which fields are mutable or immutable,
- how revisions, epochs, and audit traces are represented.

This is a logical data model.
It is storage-agnostic, but written so it can be implemented in SQL, document storage, or event-sourced systems.

### Non-Trivial Proposals

A non-trivial proposal is any proposal whose effects are meaningful enough that legitimacy requires structured plurality.

A proposal is considered non-trivial if at least one of the following applies:
- it changes a rule, right, obligation, or access condition,
- it commits public resources or changes allocation priorities,
- it routes execution toward a market, state, or hybrid path,
- it introduces non-negligible capture, exclusion, or dependency risk,
- it affects more than one domain, group, or community,
- it is difficult or costly to reverse,
- it involves significant uncertainty or conflicting trade-offs,
- it operates at the governance or constitutional layer,
- it creates precedent likely to shape future decisions.

A proposal is not non-trivial when it is purely:
- clerical,
- formatting-only,
- wording-only without semantic effect,
- internal technical maintenance without civic, governance, or constitutional consequence.

Non-trivial proposals must undergo the full deliberative minimum, including a plural skill panel and a complete audit record.

---

## 2. Modeling Principles

The data model should preserve the following properties:
- **identity stability**: objects have durable identifiers,
- **revision visibility**: substantive changes create revisions rather than overwrite history,
- **epoch binding**: legitimacy-critical processing binds to explicit framework and parameter epochs,
- **traceability**: all major procedural steps can be reconstructed,
- **separation of reasoning artifacts**: raw skill outputs, synthesis, briefings, and decisions remain distinct,
- **contestability**: challenges and reruns do not erase prior state,
- **bounded mutability**: only the right fields may change after each state transition.

---

## 3. Naming Conventions

Suggested object prefixes:
- `ctx_` civic context
- `actor_` actor
- `elig_` eligibility proof
- `prop_` proposal
- `prev_` proposal revision
- `class_` classification result
- `pset_` parameter set
- `fw_` framework
- `skill_` skill
- `sver_` skill version
- `panel_` panel
- `seat_` panel seat
- `run_` skill run
- `out_` skill output
- `syn_` adversarial synthesis
- `brief_` briefing packet
- `delib_` deliberation window
- `vote_` decision event
- `dec_` decision record
- `route_` execution route
- `audit_` audit record
- `chal_` challenge
- `revw_` review record
- `ev_` evidence item
- `probdef_` problem definition
- `mandate_` execution mandate
- `monrule_` monitoring rule
- `monev_` monitoring event
- `monrep_` monitoring report
- `outcome_` outcome record
- `learn_` learning record
- `partpol_` participation policy
- `pplan_` participation plan
- `pbody_` participant body
- `tinv_` targeted invitation
- `sconf_` sortition configuration
- `sres_` sortition result
- `cbrief_` civic brief
- `attpol_` attention allocation policy
- `rdec_` reach decision
- `attdel_` attention delegation
- `creceipt_` civic receipt
- `pcomp_` participant compensation record
- `paudit_` participation audit
- `ghr_` governance health report
- `svpol_` standing verification policy
- `ccpol_` civic credential policy
- `iqpol_` issuer quorum policy
- `caver_` civic action verification receipt
- `ndom_` nullifier domain

IDs may be UUIDs, ULIDs, or similar monotonic identifiers.

---

## 4. Core Entity Graph

At a high level:

- one `CivicContext` contains many `Proposal` objects,
- one `Proposal` has many `ProposalRevision` objects,
- one `Proposal` has one active `ClassificationResult` per processing cycle,
- one `Proposal` binds to one `ParameterSet` epoch and one `Framework` epoch at submission,
- one `Proposal` may instantiate many `Panel` objects over time,
- one `Panel` contains many `PanelSeat` objects,
- one `PanelSeat` is fulfilled by one `SkillVersion` in a given run,
- one `Panel` produces many `SkillRun` and `SkillOutput` objects,
- one proposal cycle produces one `AdversarialSynthesis` and one `BriefingPacket`,
- one `Proposal` may have many `Challenge` objects,
- one `Proposal` may produce zero or many `DecisionRecord` objects over time,
- one decided proposal may have one active `ExecutionRoute`,
- one `Proposal` has one validated `ProblemDefinition` per processing cycle,
- one active `ExecutionRoute` may issue one `ExecutionMandate`,
- one `ExecutionMandate` has many `MonitoringRule` and `MonitoringEvent` objects and periodic `MonitoringReport` objects,
- one `Proposal` produces one `OutcomeRecord` (or a documented inability to determine one) and one `LearningRecord` before closure,
- one proposal cycle appends to one `AuditRecord` stream,
- one `Proposal` may trigger many `ReviewRecord` objects.
- one `ParticipationPlan` may reference one `StandingVerificationPolicy` version
- one `CivicContext` or `ParticipationPlan` may have many `StandingVerificationPolicy` versions over time
- one credential class may have many `CivicCredentialPolicy` versions over time
- one credential issuance requires one active `IssuerQuorumPolicy`
- one `CivicReceipt` may contain zero or one `CivicActionVerificationReceipt`
- one `ParticipationPlan` may define zero or more `NullifierDomain` objects
- one accepted constrained action produces exactly one nullifier per required `NullifierDomain`

---

## 5. Entity Definitions

## 5.1 CivicContext
Represents the civic scope in which a proposal exists.

**Fields**
- `context_id` (PK)
- `name`
- `scope_type` (`neighborhood|community|municipal|regional|national|transnational|continental|global|custom`)
- `scope_form` (`hierarchical|overlapping|federated|temporary|issue_specific`)
- `parent_context_id` (nullable FK)
- `bootstrap_scope_enabled` (bool)
- `status` (`active|inactive|archived`)
- `created_at`
- `updated_at`

**Aggregation traceability rule**
Any aggregation across contexts must preserve, per source context:
- the source scope identity,
- policy and eligibility differences,
- minority positions and local exceptions,
- uncertainty and missing data.

Aggregated results that cannot be traced back to their source scopes are invalid as public artifacts.
See `../40_Identity/IDENTITY_AND_MEMBERSHIP.md` for scope-bound membership.

---

## 5.2 Actor
Represents a participant or system role holder.

**Fields**
- `actor_id` (PK)
- `actor_type` (`citizen|moderator|orchestrator|reviewer|system`)
- `display_name` (nullable)
- `eligibility_ref` (nullable FK)
- `status` (`active|suspended|revoked`)
- `created_at`

Note: a privacy-preserving implementation may externalize person-level identity and store only reference handles here.

---

## 5.3 EligibilityProof
Represents proof that an actor may act in a civic context.

**Fields**
- `eligibility_id` (PK)
- `actor_id` (FK)
- `context_id` (FK)
- `proof_type`
- `proof_ref`
- `issued_at`
- `expires_at` (nullable)
- `status` (`valid|expired|revoked`)

---

## 5.4 Proposal
Represents the stable identity of a civic request.

**Fields**
- `proposal_id` (PK)
- `context_id` (FK)
- `current_revision_id` (FK)
- `submitted_by_actor_id` (FK)
- `submission_parameter_set_id` (FK)
- `submission_framework_epoch` (string)
- `claimed_urgency` (bool)
- `advisory_only` (bool)
- `status` (canonical proposal state — the single authoritative list is `../80_Runtime/STATE_MACHINE.md` §4.1; this document does not maintain its own copy)
- `canonical_public_state` (derived on export via the mapping in `STATE_MACHINE.md` §4.5)
- `submitted_at`
- `closed_at` (nullable)

**Immutability rules**
- `proposal_id`, `context_id`, `submitted_at`, `submission_parameter_set_id`, and `submission_framework_epoch` are immutable after submission.

---

## 5.5 ProposalRevision
Represents a frozen content revision for a proposal.

**Fields**
- `revision_id` (PK)
- `proposal_id` (FK)
- `revision_number`
- `title`
- `problem_statement`
- `requested_change`
- `affected_scope`
- `reversibility_class` (`reversible|costly_to_reverse|effectively_irreversible`)
- `summary` (nullable)
- `created_by_actor_id` (FK)
- `created_at`
- `supersedes_revision_id` (nullable FK)
- `change_reason` (nullable)

**Rule**
Substantive edits after submission create a new revision and must be linked in the audit stream.

---

## 5.6 Framework
Represents a legitimacy- or processing-relevant rule set.

**Fields**
- `framework_id` (PK)
- `framework_type` (`constitutional|governance|routing|evidence|emergency|skill_registry|audit`)
- `name`
- `version`
- `epoch`
- `body_ref`
- `effective_from`
- `effective_to` (nullable)
- `status` (`proposed|active|retired|superseded`)

---

## 5.7 ParameterSet
Represents the active bundle of legitimacy-critical numeric and rule parameters.

**Fields**
- `parameter_set_id` (PK)
- `epoch`
- `policy_review_window_hours`
- `governance_review_window_hours`
- `constitutional_review_window_hours`
- `policy_threshold_rule`
- `governance_threshold_rule`
- `constitutional_threshold_rule`
- `minimum_panel_size`
- `required_skill_classes` (array)
- `classification_rules_ref`
- `emergency_rules_ref`
- `audit_minimum_ref`
- `bootstrap_scope_ref`
- `effective_from`
- `effective_to` (nullable)
- `status` (`active|scheduled|retired`)

**Rule**
A proposal binds to the active parameter set at submission.

---

## 5.8 ClassificationResult
Represents the classification and escalation outcome for a proposal cycle.

**Fields**
- `classification_id` (PK)
- `proposal_id` (FK)
- `proposal_revision_id` (FK)
- `layer` (`policy|governance|constitutional`)
- `framework_change` (bool)
- `emergency_eligible` (bool)
- `advisory_only` (bool)
- `constitutional_spillover` (bool)
- `escalation_level` (`5|7|9|custom`)
- `bootstrap_allowed` (bool)
- `rationale_json`
- `created_at`
- `created_by_actor_id` (nullable FK)

---

## 5.9 Skill
Represents a logical civic reasoning role.

**Fields**
- `skill_id` (PK)
- `skill_class` (`rights_constitutional|implementation_feasibility|economic_resource|anti_capture_audit|adversarial_critique|local_impact|minority_protection|evidence_quality|other`)
- `name`
- `owner_type` (`public_registry|community|institution|other`)
- `description`
- `status` (`active|suspended|retired`)
- `created_at`

---

## 5.10 SkillVersion
Represents a concrete runnable version of a skill.

**Fields**
- `skill_version_id` (PK)
- `skill_id` (FK)
- `version`
- `model_ref`
- `prompt_ref`
- `policy_ref`
- `context_policy_ref`
- `output_schema_ref`
- `effective_from`
- `effective_to` (nullable)
- `status` (`active|scheduled|retired|blocked`)
- `checksum`

---

## 5.11 Panel
Represents the instantiated deliberative assembly for a proposal cycle.

**Fields**
- `panel_id` (PK)
- `proposal_id` (FK)
- `proposal_revision_id` (FK)
- `classification_id` (FK)
- `parameter_set_id` (FK)
- `panel_size`
- `minimum_quorum`
- `status` (`specified|locked|running|complete|invalidated`)
- `created_at`
- `locked_at` (nullable)

**Rule**
After `locked`, `panel_size`, `minimum_quorum`, and `parameter_set_id` are immutable.

---

## 5.12 PanelSeat
Represents one required or optional seat in a panel.

**Fields**
- `seat_id` (PK)
- `panel_id` (FK)
- `skill_class`
- `required` (bool)
- `skill_version_id` (FK)
- `seat_status` (`assigned|missing|completed|invalidated`)
- `assigned_at`

---

## 5.13 EvidenceItem
Represents a bounded piece of evidence referenced in a proposal cycle.
Not to be confused with `EvidencePacket`, which is the analytic output of a skill run (see `EVIDENCE.md` §4.8).

**Fields**
- `evidence_id` (PK)
- `proposal_id` (FK)
- `evidence_class` (canonical taxonomy — defined in exactly one place: `EVIDENCE.md` §5)
- `evidence_subtype` (nullable; from the same taxonomy table)
- `source_ref`
- `evidence_status` (`supported|partially_supported|disputed|insufficient|unknown|inapplicable|superseded` — see `EVIDENCE.md` §8)
- `submitted_by_actor_id` (nullable FK)
- `created_at`
- `status` (`active|withdrawn|superseded`)

---

## 5.14 SkillRun
Represents one execution of one skill version for one panel seat.

**Fields**
- `skill_run_id` (PK)
- `panel_id` (FK)
- `seat_id` (FK)
- `proposal_id` (FK)
- `skill_version_id` (FK)
- `input_bundle_ref`
- `parameter_set_id` (FK)
- `framework_epoch`
- `run_status` (`queued|running|completed|failed|invalidated`)
- `started_at`
- `ended_at` (nullable)

---

## 5.15 SkillOutput
Represents the structured result of a skill run.

**Fields**
- `output_id` (PK)
- `skill_run_id` (FK)
- `proposal_id` (FK)
- `judgment_text`
- `summary_text`
- `reasons_json`
- `constraints_json`
- `unknowns_json`
- `confidence_band` (`low|medium|high`)
- `references_json`
- `output_schema_version`
- `created_at`

---

## 5.16 AdversarialSynthesis
Represents the structured cross-skill synthesis.

**Fields**
- `synthesis_id` (PK)
- `proposal_id` (FK)
- `panel_id` (FK)
- `summary_for_json`
- `summary_against_json`
- `unknowns_json`
- `minority_view_json`
- `capture_risk_note`
- `reversibility_note`
- `implementation_note`
- `evidence_sufficiency_note`
- `route_recommendation` (`market|state|hybrid|advisory|defer`)
- `route_reason_json`
- `created_at`

---

## 5.17 BriefingPacket
Represents the public-facing deliberation packet.

**Fields**
- `briefing_id` (PK)
- `proposal_id` (FK)
- `panel_id` (FK)
- `synthesis_id` (FK)
- `briefing_revision`
- `plain_language_summary`
- `public_argument_map_ref`
- `rights_note`
- `timing_note`
- `threshold_note`
- `audit_record_id` (FK)
- `published_at`

---

## 5.18 DeliberationWindow
Represents the bounded public discussion phase.

**Fields**
- `deliberation_id` (PK)
- `proposal_id` (FK)
- `briefing_id` (FK)
- `opened_at`
- `scheduled_close_at`
- `actual_close_at` (nullable)
- `window_type` (`policy|governance|constitutional|emergency_review`)
- `status` (`open|closed|suspended|extended_by_rule`)

**Rule**
`scheduled_close_at` is derived from the proposal's bound parameter set and is immutable except through rule-defined extensions visible in audit.

---

## 5.19 DecisionEvent
Represents the choice event itself.

**Fields**
- `decision_event_id` (PK)
- `proposal_id` (FK)
- `briefing_id` (FK)
- `decision_mode`
- `opened_at`
- `closed_at`
- `quorum_result`
- `threshold_rule_applied`
- `status` (`pending|resolved|invalidated`)

---

## 5.20 DecisionRecord
Represents the formal outcome.

**Fields**
- `decision_record_id` (PK)
- `proposal_id` (FK)
- `decision_event_id` (FK)
- `outcome` (`approved|rejected|deferred|voided|expired`)
- `outcome_reason`
- `effective_from`
- `effective_to` (nullable)
- `created_at`

---

## 5.21 ExecutionRoute
Represents the selected implementation path along two orthogonal axes: mechanism and executor form (see `../20_Protocol_Core/PROTOCOL.md` Stage L).

**Fields**
- `execution_route_id` (PK)
- `proposal_id` (FK)
- `decision_record_id` (FK)
- `route_type` (`market|state|hybrid|advisory|defer`) — mechanism axis
- `executor_form` (`public_institutional|commons|venture|cooperative|contracted|hybrid`) — executor axis
- `justification_json`
- `implementation_boundary_json`
- `review_trigger_json`
- `created_at`
- `status` (`planned|active|completed|halted`)

---

## 5.22 Challenge
Represents a formal procedural objection.

**Fields**
- `challenge_id` (PK)
- `proposal_id` (FK)
- `raised_by_actor_id` (FK)
- `challenge_type` (`misclassification|missing_skill|evidence_failure|parameter_mutation|audit_gap|conflict_of_interest|emergency_abuse|briefing_omission|other`)
- `body_text`
- `status` (`open|resolved|dismissed|escalated`)
- `created_at`
- `resolved_at` (nullable)
- `resolution_note` (nullable)

---

## 5.23 ReviewRecord
Represents post-decision review or scheduled review.

**Fields**
- `review_id` (PK)
- `proposal_id` (FK)
- `trigger_type` (`scheduled|challenge|execution_failure|new_evidence|rights_impact|capture_risk|sunset`)
- `trigger_ref` (nullable)
- `opened_at`
- `closed_at` (nullable)
- `status` (`open|closed|escalated`)
- `result_note`

---

## 5.24 AuditRecord / AuditEvent
The audit trail is best modeled as an append-only event stream.

### AuditRecord
- `audit_record_id` (PK)
- `proposal_id` (FK)
- `created_at`

### AuditEvent
- `audit_event_id` (PK)
- `audit_record_id` (FK)
- `event_type`
- `event_ref_type`
- `event_ref_id`
- `event_timestamp`
- `actor_id` (nullable FK)
- `payload_json`
- `hash_prev` (nullable)
- `hash_self`

This allows replay, tamper-evidence, and challengeable sequencing.

---

## 5.25 ProblemDefinition
Represents the validated definition of the problem, produced before any solution is promoted (see `PROTOCOL.md` invariant 7.9).

**Fields**
- `problem_definition_id` (PK)
- `proposal_id` (FK)
- `proposal_revision_id` (FK)
- `problem_statement`
- `affected_scope_ref` (FK to CivicContext)
- `affected_public_interest`
- `observable_conditions_json`
- `time_horizon`
- `known_constraints_json`
- `unresolved_questions_json`
- `evaluation_criteria_json` (how society would know the problem improved or worsened)
- `exclusion_boundaries_json` (what this case is explicitly not about)
- `validation_status` (`draft|validated|rejected`)
- `created_at`

**Rule**
Classification may not complete while `validation_status` is not `validated`.

---

## 5.26 ExecutionMandate
Represents the explicit, bounded authority granted for execution.
No decision creates authority beyond its mandate.

**Fields**
- `mandate_id` (PK)
- `execution_route_id` (FK)
- `proposal_id` (FK)
- `authorized_actor_ref`
- `objective`
- `scope_ref` (FK to CivicContext)
- `permitted_actions_json`
- `prohibited_actions_json`
- `resources_json`
- `budget_json`
- `ip_and_licensing_ref` (see `../50_Economics/PUBLIC_IP_MODEL.md`)
- `data_access_rules_json`
- `start_condition`
- `end_condition`
- `milestones_json`
- `reporting_obligations_json`
- `success_criteria_json`
- `failure_criteria_json`
- `suspension_conditions_json`
- `rollback_path`
- `escalation_process`
- `public_return_obligations_json`
- `status` (`issued|active|suspended|completed|revoked`)
- `created_at`

---

## 5.27 MonitoringRule
Represents a deterministic obligation to be monitored for a mandate.

**Fields**
- `monitoring_rule_id` (PK)
- `mandate_id` (FK)
- `monitored_obligation`
- `expected_value`
- `measurement_method`
- `acceptable_deviation`
- `warning_condition`
- `automatic_suspension_condition` (nullable)
- `renewed_judgment_condition` (nullable)
- `schedule`
- `status` (`active|satisfied|breached|retired`)

---

## 5.28 MonitoringEvent
Represents one observation against a monitoring rule.

**Fields**
- `monitoring_event_id` (PK)
- `monitoring_rule_id` (FK)
- `mandate_id` (FK)
- `expected_value`
- `observed_value`
- `evidence_ref`
- `observed_at`
- `reporter_provenance`
- `validation_status` (`unvalidated|validated|disputed`)
- `deviation`
- `exception_reason` (nullable)
- `escalation_status` (`none|warning|suspended|escalated`)

**Rule**
Models may summarize monitoring events; they MUST NOT be the sole authority on whether an objective commitment was satisfied.

---

## 5.29 MonitoringReport
Represents a periodic public summary of monitoring events for a mandate.

**Fields**
- `monitoring_report_id` (PK)
- `mandate_id` (FK)
- `period_start`
- `period_end`
- `events_covered_json`
- `open_deviations_json`
- `escalations_json`
- `summary`
- `published_at`

---

## 5.30 OutcomeRecord
Represents what actually happened, evaluated against the original expectations.
The outcome record MUST NOT rewrite the original expectations.

**Fields**
- `outcome_id` (PK)
- `proposal_id` (FK)
- `mandate_id` (nullable FK)
- `original_objective`
- `expected_result`
- `observed_result`
- `evidence_refs_json`
- `evaluation_period_start`
- `evaluation_period_end`
- `costs_json`
- `benefits_json`
- `harms_json`
- `unintended_effects_json`
- `affected_scopes_json`
- `outcome_class` (`successful|partially_successful|unsuccessful|not_executed|abandoned|superseded|harmful|mixed|undeterminable|still_unfolding`)
- `confidence_band` (`low|medium|high`)
- `unresolved_questions_json`
- `recommendation` (`close|continue|correct|reopen`)
- `created_at`

**Rule**
A proposal may not enter `CLOSED` without an `OutcomeRecord` or a documented inability to determine one (`outcome_class = undeterminable`).

---

## 5.31 LearningRecord
Represents the published learning from a completed case, addressed to future citizens and future models.

**Fields**
- `learning_id` (PK)
- `proposal_id` (FK)
- `outcome_id` (FK)
- `expected_vs_actual_json`
- `predictions_vs_observed_json`
- `failures_json`
- `corrections_json`
- `guidance_for_future_cases`
- `authoring_mode` (see `SCHEMAS.md` common envelope)
- `published_at`

---

## 5.32 PublicReturnReport
Represents the auditable record of whether declared public-return obligations were delivered.
Closes the Public Value Loop (`../50_Economics/PUBLIC_IP_MODEL.md` §9).
The report MUST NOT rewrite the original obligations.

**Fields**
- `public_return_id` (PK)
- `mandate_id` (nullable FK)
- `public_ip_artifact_id` (nullable FK)
- `obligation_ref`
- `period_start`
- `period_end`
- `returns_delivered_json` (entries typed by return type; see `SCHEMAS.md` §25.7)
- `shortfalls_json`
- `settlement_status` (`on_track|settled|shortfall_declared|in_dispute`)
- `published_at`

**Rule**
A mandate with `public_return_obligations_json` may not reach `completed` without at least one `PublicReturnReport` or a documented inability to produce one.

---

## 5.33 ReplicationRecord
Represents the declared-overlap record for replicated executions of the same skill by different executors.
Substantially overlapping executions MUST NOT be presented as independent agreement (`../60_Skills/SKILL_REGISTRY.md` §11.3).

**Fields**
- `replication_id` (PK)
- `skill_id` (FK)
- `skill_version_id` (FK)
- `replicated_run_refs_json` (SkillRun ids)
- `executor_ids_json`
- `overlap_declaration_json` (model family, provider, sources, context, methodology; each `none|partial|substantial`)
- `cross_influence` (boolean)
- `cross_influence_description` (nullable)
- `independence_class` (`independent|partially_independent|not_independent`)
- `divergence_summary`
- `resolution_ref` (nullable)
- `created_at`

---

## 5.34 LicenseRecord
Represents the concrete licensing terms applied to a `PublicIPArtifact`, bounded by per-class rules (`../50_Economics/PUBLIC_IP_MODEL.md` §7.1).

**Fields**
- `license_id` (PK)
- `public_ip_artifact_id` (FK)
- `ip_class`
- `license_identifier`
- `license_terms_ref`
- `commercial_use_conditions`
- `derivative_work_conditions`
- `attribution_requirements`
- `revenue_return_mechanism_ref`
- `release_schedule` (nullable; permitted only for `applied_service` and `local_experimental`)
- `status` (`active|superseded|revoked`)
- `created_at`

---

## 5.35 ContributorAgreement
Represents what a contributor grants and retains when contributing to a public IP asset or mandate.

**Fields**
- `contributor_agreement_id` (PK)
- `contributor_ref`
- `public_ip_artifact_id` (nullable FK)
- `mandate_id` (nullable FK)
- `contribution_scope`
- `rights_granted`
- `rights_retained`
- `attribution_requirements`
- `compensation_ref` (nullable)
- `status` (`active|completed|terminated`)
- `created_at`

---

## 5.36 EconomicConflictDisclosure
Represents a declared material economic interest of an actor in a funding, mandate, or IP context.
Executor-side disclosure remains `ConflictOfInterestDisclosure` (`SCHEMAS.md` §24.10).

**Fields**
- `econ_conflict_id` (PK)
- `actor_ref`
- `role`
- `interest_type` (`financial_interest|employment|ownership|funding_relationship|licensing_benefit|competing_venture|other_declared`)
- `related_refs_json` (proposals, mandates, funding sources, IP artifacts)
- `materiality` (`low|medium|high`)
- `mitigation`
- `declared_at`

---

## 5.37 ParticipationPolicy

Scope-level, epoch-bound participation defaults (`../45_Participation/PARTICIPATION_MODEL.md` §5).
Applies prospectively; changes to it never retune active cases.

**Fields**
- `participation_policy_id` (PK)
- `civic_context_id` (FK)
- `framework_epoch_id` (FK)
- `parameter_epoch_id` (FK)
- `participation_modes_by_tier_json` (default modes per classification tier)
- `sortition_enabled` (boolean)
- `sortition_disabled_declaration` (nullable; required when sortition_enabled=false)
- `default_participation_windows_json`
- `compensation_defaults_json`
- `accessibility_baseline`
- `civic_translation_baseline`
- `delegation_rules_json` (max delegate count, concentration threshold)
- `attention_budget_json` (unit, value, owner)
- `intake_rate_limits_json`
- `uniqueness_requirements_by_action_type_json`
- `audit_depth_defaults_json`
- `brief_cadence`
- `status` (`active|superseded`)
- `supersedes_policy_id` (nullable FK)
- `created_at`

---

## 5.38 ParticipationPlan

Per-proposal participation design (`../45_Participation/PARTICIPATION_MODEL.md` §6).
Absorbs scope-and-impact mapping, barrier assessment, accessibility planning, and civic-translation requirements as sections, not separate artifacts.

**Fields**
- `participation_plan_id` (PK)
- `proposal_id` (FK)
- `version_no`
- `supersedes_plan_id` (nullable FK)
- `affected_civic_scopes_json`
- `eligible_participant_population_ref`
- `expected_participation_barriers_json`
- `known_missing_perspectives_json`
- `selected_participation_modes_json`
- `open_participation_channels_json`
- `targeted_invitation_rules`
- `sortition_ref` (nullable FK to `SortitionConfiguration`)
- `compensation_rules_json`
- `accessibility_measures_json`
- `civic_translation_requirements_json`
- `delegation_rules_in_force`
- `participation_windows_json`
- `expected_time_commitment`
- `judgment_authority_by_group_json`
- `conflict_of_interest_handling`
- `privacy_and_standing_requirements`
- `participation_audit_criteria`
- `status` (`active|superseded`)
- `created_at`

**Rule**
A non-trivial proposal must have an active `ParticipationPlan` before panel selection begins — the exit condition of `PARTICIPATION_DESIGN_PENDING`. The plan is versioned and revisable; `PARTICIPATION_DESIGNED` certifies only that a first complete plan exists.

---

## 5.39 ParticipantBody

Represents a formed body distinct from the skill panel (`../45_Participation/PARTICIPATION_MODEL.md` §3.4, §10).
Skills analyse; participants deliberate and judge.

**Fields**
- `participant_body_id` (PK)
- `proposal_id` (FK)
- `body_type` (`open_public|affected_party|sortition|civic_jury|institutional|monitoring|hybrid`)
- `formation_method_refs_json`
- `member_roster_ref`
- `declared_conflicts_json`
- `mandate`
- `limitations`
- `term_start`
- `term_end`
- `status` (`forming|ready|dissolved`)
- `created_at`

---

## 5.40 TargetedInvitation

A declared invitation to a person, group, or role; invitation patterns are an audit surface (`../45_Participation/PARTICIPATION_MODEL.md` §4.3).

**Fields**
- `targeted_invitation_id` (PK)
- `proposal_id` (FK)
- `invitee_ref`
- `issued_at`
- `reason`
- `perspective_sought`
- `identification_method`
- `compensation_offered` (boolean)
- `judgment_authority` (default: `none`)
- `response_status` (`pending|accepted|declined|no_response`)
- `responded_at` (nullable)

---

## 5.41 SortitionConfiguration

Published before the registry snapshot commitment; the eligible-population registry is the primary capture surface (`../45_Participation/SORTITION.md` §5, §8).

**Fields**
- `sortition_config_id` (PK)
- `proposal_or_function_ref`
- `civic_context_id` (FK)
- `eligible_population_definition_ref`
- `eligibility_criteria_ref`
- `registry_snapshot_commitment` (SHA-256)
- `selection_window_start`
- `selection_window_end`
- `selection_method`
- `selection_algorithm_params_json`
- `seed_governance_json` (sources, independence basis, commitment/reveal schedule, withheld-reveal fallback)
- `body_size`
- `quorum`
- `stratification_rules_json`
- `replacement_rules`
- `opt_out_rules`
- `privacy_protections_json`
- `compensation_ref`
- `accessibility_support_commitments_json`
- `term_start`
- `term_end`
- `mandate`
- `judgment_authority`
- `verification_instructions`
- `status` (`configured|snapshot_committed|draw_executed|certified|voided`)
- `created_at`

**Rule**
A draw whose registry snapshot commitment was published after seed revelation is invalid. Registry changes made inside the selection window are invalid for that draw.

---

## 5.42 SortitionResult

Published after the draw; immutable; corrections follow append-only rules (`../45_Participation/SORTITION.md` §9).

**Fields**
- `sortition_result_id` (PK)
- `sortition_config_id` (FK)
- `registry_snapshot_commitment`
- `revealed_seed_material`
- `seed_verification_data`
- `draw_proof_ref`
- `selected_set_json` (pseudonymous per privacy policy)
- `decline_replacement_chain_json`
- `participant_body_id` (FK)
- `verification_instructions`
- `certification_status` (`certified|anomaly_pending|voided`)
- `anomaly_notes` (nullable)
- `published_at`

**Rule**
Certification requires: draw verifies against committed snapshot, selection window contains no invalid registry changes, material registry challenges resolved, anomaly signals investigated or risk-accepted with rationale.

---

## 5.43 CivicBrief

Scope-level periodic brief; a projection, never a replacement of the record (`../45_Participation/ATTENTION_AND_REACH.md` §7).

**Fields**
- `civic_brief_id` (PK)
- `civic_context_id` (FK)
- `period_start`
- `period_end`
- `items_json` (each item: content_ref, action_class, summary, canonical_links, dissent_preserved, uncertainty_preserved)
- `published_at`

---

## 5.44 AttentionAllocationPolicy

Scope-level, epoch-bound reach-allocation rules (`../45_Participation/ATTENTION_AND_REACH.md` §3).

**Fields**
- `attention_policy_id` (PK)
- `civic_context_id` (FK)
- `framework_epoch_id` (FK)
- `parameter_epoch_id` (FK)
- `version_no`
- `factors_json` (mode: explicit_weights or deterministic_ordering, with declared factor values)
- `prohibited_bases_attestation`
- `status` (`active|superseded`)
- `supersedes_policy_id` (nullable FK)
- `created_at`

---

## 5.45 ReachDecision

Audit record of a material reach grant (`../45_Participation/ATTENTION_AND_REACH.md` §4).

**Fields**
- `reach_decision_id` (PK)
- `content_ref`
- `civic_context_id` (FK)
- `policy_version_id` (FK to `AttentionAllocationPolicy`)
- `reason`
- `duration`
- `target_audience`
- `urgency_evidence_ref` (nullable)
- `automated_systems_involved_json`
- `appeal_path_ref`
- `created_at`

**Rule**
A reach grant that cannot be traced to a policy version and declared inputs is an invariant violation.

---

## 5.46 AttentionDelegation

Scoped, time-limited, revocable delegation of attention (`../45_Participation/ATTENTION_AND_REACH.md` §8).
Delegation of attention never consumes the delegator's civic action or uniqueness budget.

**Fields**
- `attention_delegation_id` (PK)
- `delegator_ref`
- `delegate_ref`
- `civic_context_id` (FK)
- `purpose`
- `created_at`
- `expires_at`
- `revoked_at` (nullable)
- `non_transferable` (boolean; default true)
- `status` (`active|revoked|expired`)

**Rule**
Attention delegation does not transfer the citizen's final civic judgment unless a `JudgmentConfiguration` explicitly permits it. Delegation concentration per delegate per scope is a standing `GovernanceHealthReport` metric.

---

## 5.47 CivicReceipt

Issued after a participant performs a material civic action (`../45_Participation/PARTICIPATION_MODEL.md` §12).
Disclosure default: private proof, public aggregate.

**Fields**
- `civic_receipt_id` (PK)
- `participant_ref` (pseudonymous)
- `case_ref` (FK to `Proposal`)
- `action_type`
- `policy_applied_ref`
- `status` (`accepted|included|corrected|superseded|rejected`)
- `inclusion_proof_ref`
- `disclosure` (`private_proof|public_aggregate`)
- `created_at`

**Rule**
The receipt must not reveal how the participant acted unless the participant chooses disclosure or the process requires public attribution. The public artifact records only that a receipt exists and was included in an aggregate.

---

## 5.48 ParticipantCompensationRecord

Links a compensation payment to participant, case, role, and treasury release (`../45_Participation/PARTICIPATION_MODEL.md` §7.4).
Distinct from contributor compensation (`ContributorAgreement` §5.35).

**Fields**
- `participant_compensation_id` (PK)
- `participant_ref`
- `case_ref`
- `participation_role`
- `compensable_items_json`
- `amount_json` (currency, value)
- `basis_of_calculation`
- `independence_of_position_attestation`
- `treasury_release_ref` (FK)
- `funding_receipt_ref` (FK)
- `status` (`authorized|released|disputed`)
- `created_at`

**Rule**
Compensation must be independent of the participant's position, vote, or agreement and independent of the final judgment. Correlation between compensation and expressed position is a capture failure signal.

---

## 5.49 ParticipationAudit

Per-case, pre-decision audit gate (`../45_Participation/PARTICIPATION_MODEL.md` §11).
Required before every non-trivial proposal reaches decision readiness.

**Fields**
- `participation_audit_id` (PK)
- `proposal_id` (FK)
- `auditor_ref`
- `auditor_conflict_declaration`
- `depth` (`full|lightweight`)
- `examination_results_json`
- `limitations`
- `challengeable_status` (`open|challenged|upheld_reopened|resolved`)
- `created_at`

**Rule**
Depth is proportional: `full` for high-impact proposals, `lightweight` for other non-trivial proposals; trivial proposals are exempt. The auditor must not hold a conflicting role on the same proposal. An upheld challenge reopens the audit via a superseding record; history is preserved.

**Supplemental ZK credential relationships**
Per `../40_Identity/ZK_CIVIC_CREDENTIALS.md` §19, the audit additionally evaluates:
- one `ParticipationAudit` → evaluates many `CivicReceipt` objects (accepted and rejected)
- one `ParticipationAudit` → evaluates many `CivicActionVerificationReceipt` objects
- one `ParticipationAudit` → references relevant `StandingVerificationPolicy`, `CivicCredentialPolicy`, and `IssuerQuorumPolicy` versions and issuer epochs

---

## 5.50 GovernanceHealthReport

Scope-level diagnostic report of participation, delegation, compensation, and sortition metrics (`../45_Participation/GOVERNANCE_HEALTH.md` §2.9).

**Fields**
- `governance_health_report_id` (PK)
- `civic_context_id` (FK)
- `period_start`
- `period_end`
- `metric_families_json` (latency, classification, participation, emergency, output_quality, delegation_concentration, compensation_distribution, sortition_integrity)
- `flags_raised_json`
- `read_model_links_json`
- `published_at`

**Rule**
A `GovernanceHealthReport` MUST NOT reduce governance health to a single legitimacy score. Metrics inform structural review; they do not themselves constitute or replace public judgment.

---

## 5.51 StandingVerificationPolicy

Defines the public eligibility conditions and verification rules for establishing civic standing in a scope.
Per `../40_Identity/ZK_CIVIC_CREDENTIALS.md` §6.2: this policy determines how standing is established privately.

A `ParticipationPlan` references one applicable `StandingVerificationPolicy` version.

**Fields**
- `policy_id` (PK)
- `schema_version`
- `civic_scope`
- `eligibility_rule`
- `authorized_verifier_classes` (array)
- `accepted_evidence_categories` (array)
- `prohibited_evidence_categories` (array)
- `retention_policy`
- `issuance_authorization_profile`
- `appeal_policy`
- `valid_from`
- `valid_until` (nullable)
- `governance_artifact_ref`

**Rule**
The verifier must not publish private identity evidence or the basis of standing. Evidence retention must be minimized, explicitly governed, time-bounded, and auditable.

---

## 5.52 CivicCredentialPolicy

Defines the rules for a credential class: permitted scopes, attributes, issuance, expiry, revocation, and cryptographic profile.
Per `../40_Identity/ZK_CIVIC_CREDENTIALS.md` §6.3.

**Fields**
- `policy_id` (PK)
- `schema_version`
- `credential_class`
- `permitted_civic_scopes` (array)
- `private_attributes` (array)
- `selectively_disclosed_attributes` (array)
- `allowed_action_types` (array)
- `holder_binding_profile`
- `cryptographic_profile_id`
- `issuance_epoch`
- `expiry_rule`
- `revocation_profile`
- `recovery_profile`
- `governance_artifact_ref`

**Cardinality**
- one credential class → many policy versions over time

---

## 5.53 IssuerQuorumPolicy

Defines the issuer quorum: authorized issuers, threshold, key governance, and compromise procedures.
Per `../40_Identity/ZK_CIVIC_CREDENTIALS.md` §6.4: no single organization should issue a valid consequential credential unilaterally.

**Fields**
- `policy_id` (PK)
- `schema_version`
- `quorum_id`
- `issuer_set` (array)
- `threshold`
- `key_generation_profile`
- `key_rotation_policy`
- `issuer_replacement_policy`
- `compromise_policy`
- `audit_policy`
- `valid_from`
- `valid_until` (nullable)
- `governance_artifact_ref`

**Rule**
Credential issuance requires one active `IssuerQuorumPolicy`. A threshold of issuer shares is required to create a valid civic credential.

---

## 5.54 CivicActionVerificationReceipt

The cryptographic verification component of a `CivicReceipt`. Records the proof verification outcome, nullifier status, and applicable policy references without revealing the participant's identity or private credential.
Per `../40_Identity/ZK_CIVIC_CREDENTIALS.md` §6.6, §13.

**Fields**
- `verification_receipt_id` (PK)
- `schema_version`
- `protocol_version`
- `civic_scope`
- `loop_id`
- `action_type`
- `epoch`
- `participation_policy_id`
- `standing_policy_id`
- `credential_policy_id`
- `issuer_quorum_policy_id`
- `cryptographic_profile_id`
- `canonical_artifact_hash`
- `nullifier` (nullable; present when uniqueness is required)
- `verification_outcome` (see `../40_Identity/ZK_CIVIC_CREDENTIALS.md` §8 for outcome values)
- `rejection_reason` (nullable)
- `revocation_commitment_ref` (nullable)
- `ingress_identifier`
- `verification_timestamp`
- `challenge_ref` (nullable)

**Rule**
The receipt must not contain legal identity, identity document, address, standing basis, holder secret, complete credential, raw onboarding evidence, or any global participant identifier. The receipt is optional on a `CivicReceipt` — some low-risk or bootstrap participation profiles may not use ZK credentials.

**Cardinality**
- one accepted constrained action → one `CivicReceipt`
- one `CivicReceipt` → zero or one `CivicActionVerificationReceipt`

---

## 5.55 NullifierDomain

Declares a bounded uniqueness scope for constrained civic actions.
Per `../40_Identity/ZK_CIVIC_CREDENTIALS.md` §12: provides constrained uniqueness and must never become a universal citizen identifier.

A `NullifierDomain` is a logical tuple defined by the following components:

**Components**
- `protocol_version`
- `civic_scope`
- `loop_id`
- `action_type`
- `participation_policy_id`
- `epoch`

**Relationships**
- one `ParticipationPlan` → defines zero or more `NullifierDomain` objects
- one accepted constrained action → produces exactly one nullifier per required domain
- one nullifier → may appear in only one accepted receipt inside its domain

**Rule**
The same credential produces the same nullifier in the same domain. Different domains produce unlinkable nullifiers. The holder secret cannot be recovered from a nullifier. Observers cannot calculate nullifiers for other actions. No global cross-scope correlation identifier is created.

---

## 5.56 ParticipationAudit — ZK Credential Extensions

The existing `ParticipationAudit` entity (§5.49) is extended by the ZK credential subsystem with the following supplemental context.

Per `../40_Identity/ZK_CIVIC_CREDENTIALS.md` §19:
- one `ParticipationAudit` evaluates many `CivicReceipt` objects (both accepted and rejected)
- one `ParticipationAudit` evaluates many `CivicActionVerificationReceipt` objects
- one `ParticipationAudit` references relevant `StandingVerificationPolicy`, `CivicCredentialPolicy`, and `IssuerQuorumPolicy` versions and issuer epochs

---

## 6. Cardinality Notes

- one `Proposal` → many `ProposalRevision`
- one `Proposal` → many `ClassificationResult` over time, but only one active per cycle
- one `Proposal` → many `Panel`
- one `Panel` → many `PanelSeat`
- one `PanelSeat` → one active `SkillVersion` for a given run
- one `SkillRun` → one `SkillOutput`
- one `Panel` → one `AdversarialSynthesis` per completed cycle
- one `Proposal` → many `BriefingPacket` revisions over time
- one `Proposal` → many `Challenge`
- one `Proposal` → many `ReviewRecord`
- one `Proposal` → one validated `ProblemDefinition` per processing cycle
- one `ExecutionRoute` → zero or one `ExecutionMandate`
- one `ExecutionMandate` → many `MonitoringRule`, `MonitoringEvent`, `MonitoringReport`
- one `Proposal` → one `OutcomeRecord` and one `LearningRecord` before closure
- one `Proposal` → one append-only `AuditRecord`
- one `ExecutionMandate` → many `PublicReturnReport` objects over its obligation periods
- one set of replicated `SkillRun` objects → one `ReplicationRecord`
- one `PublicIPArtifact` → many `LicenseRecord` and `ContributorAgreement` objects
- one `Actor` → many `EconomicConflictDisclosure` objects
- one `CivicContext` → one active `ParticipationPolicy` per epoch
- one `CivicContext` → one active `AttentionAllocationPolicy` per epoch
- one `Proposal` → many `ParticipationPlan` versions over time, but only one active per cycle
- one `ParticipationPlan` → zero or one `SortitionConfiguration`
- one `Proposal` → zero or one `ParticipantBody`
- one `SortitionConfiguration` → one `SortitionResult`
- one `Proposal` → many `TargetedInvitation` objects
- one `Proposal` → one `ParticipationAudit` per processing cycle (before decision readiness)
- one `Proposal` → many `CivicReceipt` objects (one per participant action)
- one `ParticipantCompensationRecord` → one treasury `ReleaseAuthorization`
- one `CivicContext` → many `CivicBrief` objects over time
- one `CivicContext` → many `ReachDecision` objects over time
- one `Actor` → many `AttentionDelegation` objects (as delegator)
- one `CivicContext` → many `GovernanceHealthReport` objects (quarterly)
- one `ParticipationPlan` → references zero or one `StandingVerificationPolicy` version
- one civic scope → many `StandingVerificationPolicy` versions over time
- one credential class → many `CivicCredentialPolicy` versions over time
- one `IssuerQuorumPolicy` → many quorum epochs; one quorum → many issuers
- one credential issuance → requires one active `IssuerQuorumPolicy`
- one `CivicReceipt` → zero or one `CivicActionVerificationReceipt`
- one `ParticipationPlan` → defines zero or more `NullifierDomain` objects
- one accepted constrained action → produces exactly one nullifier per required `NullifierDomain`
- one nullifier → appears in at most one accepted receipt inside its domain
- one `ParticipationAudit` → evaluates many `CivicReceipt` and `CivicActionVerificationReceipt` objects

---

## 7. Suggested Enums

### Proposal Status
Defined in exactly one place: `../80_Runtime/STATE_MACHINE.md` §4.1 (internal states) and §4.5 (canonical public states).
This document does not maintain its own copy.

### Governance Layer
- `policy`
- `governance`
- `constitutional`

### Route Type (mechanism axis)
- `market`
- `state`
- `hybrid`
- `advisory`
- `defer`

### Executor Form (executor axis)
- `public_institutional`
- `commons`
- `venture`
- `cooperative`
- `contracted`
- `hybrid`

### Reuse-Status Labels
Defined in exactly one place: `SCHEMAS.md` §3.9 (`statement_status`, `prediction_status`, `participation_reality`).
This document does not maintain its own copy.

### Public Return Type
Defined in exactly one place: `SCHEMAS.md` §25.7.
This document does not maintain its own copy.

---

## 8. Mutability Rules

### Immutable After Submission
- proposal id
- submitting context
- submission time
- bound parameter set
- bound framework epoch

### Immutable After Panel Lock
- panel size
- minimum quorum
- selected skill versions for that cycle
- active timing rules

### Immutable After Deliberation Opens
- review window end unless rule-defined extension exists,
- threshold rule applied,
- route alternatives shown in briefing,
- published dissent content except by explicit revision event.

### Append-Only
- audit events,
- decision history,
- challenge history,
- review history.

---

## 9. Revision and Epoch Semantics

The model must distinguish between:
- **content revision**: proposal body changes,
- **processing cycle**: a new classification/panel/briefing run,
- **parameter epoch**: active legitimacy rule set,
- **framework epoch**: active governing logic set.

A proposal may have multiple revisions and multiple processing cycles, but each cycle binds to one parameter epoch and one framework epoch.

---

## 10. Minimal Relational Shape

A practical relational implementation could begin with these tables:
- `civic_contexts`
- `actors`
- `eligibility_proofs`
- `proposals`
- `proposal_revisions`
- `frameworks`
- `parameter_sets`
- `classification_results`
- `skills`
- `skill_versions`
- `panels`
- `panel_seats`
- `evidence_items`
- `skill_runs`
- `skill_outputs`
- `adversarial_syntheses`
- `briefing_packets`
- `deliberation_windows`
- `decision_events`
- `decision_records`
- `execution_routes`
- `problem_definitions`
- `execution_mandates`
- `monitoring_rules`
- `monitoring_events`
- `monitoring_reports`
- `outcome_records`
- `learning_records`
- `challenges`
- `review_records`
- `audit_records`
- `audit_events`
- `participation_policies`
- `participation_plans`
- `participant_bodies`
- `targeted_invitations`
- `sortition_configurations`
- `sortition_results`
- `civic_briefs`
- `attention_allocation_policies`
- `reach_decisions`
- `attention_delegations`
- `civic_receipts`
- `participant_compensation_records`
- `participation_audits`
- `governance_health_reports`
- `public_return_reports`
- `replication_records`
- `license_records`
- `contributor_agreements`
- `economic_conflict_disclosures`
- `standing_verification_policies`
- `civic_credential_policies`
- `issuer_quorum_policies`
- `civic_action_verification_receipts`

---

## 11. Minimal JSON Example

```json
{
  "proposal": {
    "proposal_id": "prop_01",
    "status": "PACKET_PUBLISHED",
    "canonical_public_state": "DELIBERATION",
    "submission_parameter_set_id": "pset_bootstrap_v1",
    "submission_framework_epoch": "fw_epoch_1"
  },
  "revision": {
    "revision_id": "prev_01",
    "title": "Public audit portal",
    "reversibility_class": "reversible"
  },
  "classification": {
    "layer": "governance",
    "framework_change": false,
    "escalation_level": 5
  },
  "panel": {
    "panel_id": "panel_01",
    "panel_size": 5,
    "seats": [
      "rights_constitutional",
      "implementation_feasibility",
      "economic_resource",
      "anti_capture_audit",
      "adversarial_critique"
    ]
  },
  "briefing": {
    "briefing_id": "brief_01",
    "summary": "Create a public portal for audit records.",
    "route": "state"
  }
}
```

---

## 12. Design Warning

The most important data-model mistake would be collapsing these into one table or one opaque blob:
- proposal text,
- skill outputs,
- synthesis,
- briefing,
- decision,
- audit.

If these are not distinct objects, the system will lose contestability and replayability.

---

## 13. Closing Rule

The data model should make power legible.

Anything that changes:
- who is heard,
- what counts,
- how proposals are classified,
- which rules are active,
- how decisions are executed,
- how history is reconstructed,

must exist as an explicit object rather than hidden application state.
