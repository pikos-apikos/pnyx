# SCHEMAS

> **Note:** This document has been updated to reflect the normative rules in [SYSTEM_PATCH_v1](../99_Reference/SYSTEM_PATCH_v1.md) and [SYSTEM_PATCH_v2](../99_Reference/SYSTEM_PATCH_v2.md).


## 1. Purpose

This document defines the canonical schema vocabulary of the system.

Its purpose is to make the runtime contract explicit:
- which objects exist,
- which fields are required,
- which fields are immutable,
- which fields are epoch-bound,
- which fields are advisory versus authoritative,
- and how objects relate to audit, packet generation, classification, skill execution, challenge, funding, and shutdown.

This document complements:
- `DATA_MODEL.md`
- `API_SPEC.md`
- `STATE_MACHINE.md`
- `AUDIT_LOG.md`
- `PACKET_FORMAT.md`
- `CLASSIFICATION.md`
- `SKILLS.md`
- `SKILL_EVALUATION.md`
- `TREASURY.md`
- `PUBLIC_IP_MODEL.md`

This document does not prescribe a storage engine.
It defines canonical payload shapes and validation semantics.

---

## 2. Core Principle

Every runtime-significant action must be representable as typed data.

A schema must distinguish:
- identity from display fields,
- immutable truth from mutable projections,
- human-visible summaries from canonical records,
- active fields from derived fields,
- runtime inputs from review artifacts.

No meaningful governance object should depend on undocumented structure.

---

## 3. Schema Conventions

All canonical objects should follow these conventions unless explicitly exempted.

### 3.1 Common Fields

Most canonical objects should include:
- `id`
- `schema_version`
- `created_at`
- `created_by`
- `updated_at` if mutable
- `status`
- `authoring_mode` (see 3.8)
- `epoch_ref` where applicable
- `audit_ref` or linkable audit trace
- `metadata` for non-load-bearing extensions

### 3.2 Identifiers
Identifiers should be:
- stable,
- opaque,
- unique within their namespace,
- not overloaded with meaning.

### 3.3 Timestamps
Timestamps should be ISO-8601 UTC strings.

### 3.4 Status Fields
Status fields should use explicit enumerations.
Avoid boolean collapse of multi-state objects.

### 3.5 Derived Fields
Derived or convenience fields must be clearly labeled or omitted from canonical storage.

### 3.6 Null vs Missing
- missing = not supplied / not applicable
- null = explicitly empty / known absent

### 3.7 Enumerations
All enums should be explicitly documented.
"Free text but expected values" is not acceptable for canonical objects.

### 3.8 Authoring Mode
Every canonical artifact intended for public memory or future reuse should declare how it was authored:
- `human`
- `model`
- `human_model_collaborative`
- `institutional`
- `automated_system`
- `sensor_or_machine_event`
- `imported_external_source`
- `unknown`

This labeling protects the epistemic feedback loop: future citizens and future models must be able to distinguish human-authored, model-authored, and collaborative content.

### 3.9 Reuse-Status Labeling

Artifacts intended for future reuse may additionally carry an optional labeling cluster (adopted via `../99_Reference/CORE_V03_RECONCILIATION.md` §4.5). These are common optional fields, not per-schema duplicates:

- `statement_status` — epistemic standing of the artifact's principal claims:
  - `verified`
  - `corrected`
  - `disproven`
  - `unresolved`
- `prediction_status` — where the content is forward-looking:
  - `prediction`
  - `observed`
- `participation_reality` — for participation-related artifacts only:
  - `actual`
  - `simulated`

`participation_reality: simulated` content (model-generated personas, synthetic surveys, simulated deliberation) MUST NOT be aggregated with, or presented as, actual civic participation (`../80_Runtime/INVARIANTS.md`).

---

## 4. Shared Envelopes

## 4.1 Command Envelope

```json
{
  "command_id": "cmd_...",
  "command_type": "SubmitProposal",
  "issued_at": "2026-04-06T12:00:00Z",
  "issued_by": {
    "actor_id": "actor_...",
    "actor_type": "member"
  },
  "target_id": "proposal_...",
  "epoch_ref": {
    "framework_epoch_id": "fwe_...",
    "parameter_epoch_id": "pme_..."
  },
  "payload": {},
  "idempotency_key": "idem_...",
  "trace_ref": "trace_..."
}
```

## 4.2 Query Envelope

```json
{
  "query_id": "qry_...",
  "query_type": "GetPacket",
  "issued_at": "2026-04-06T12:00:00Z",
  "issued_by": {
    "actor_id": "actor_...",
    "actor_type": "member"
  },
  "target_id": "packet_...",
  "parameters": {},
  "trace_ref": "trace_..."
}
```

## 4.3 Event Envelope

```json
{
  "event_id": "evt_...",
  "event_type": "PacketPublished",
  "occurred_at": "2026-04-06T12:00:00Z",
  "recorded_at": "2026-04-06T12:00:01Z",
  "actor": {
    "actor_id": "actor_...",
    "actor_type": "system"
  },
  "subject_ref": {
    "type": "Packet",
    "id": "packet_..."
  },
  "epoch_ref": {
    "framework_epoch_id": "fwe_...",
    "parameter_epoch_id": "pme_..."
  },
  "payload": {},
  "trace_ref": "trace_...",
  "sequence_no": 1024,
  "prev_event_hash": "sha256:..."
}
```

---

## 5. Actor Schemas

## 5.1 ActorRef

```json
{
  "actor_id": "actor_...",
  "actor_type": "member",
  "display_name": "optional",
  "role_refs": ["role_member"],
  "status": "active"
}
```

### Allowed `actor_type`
- `member`
- `operator`
- `reviewer`
- `facilitator`
- `auditor`
- `system`
- `service`
- `steward`
- `registry_admin`
- `treasury_admin`
- `verifier`
- `challenge_submitter`

`display_name` is optional and non-authoritative.

---

## 6. Epoch Schemas

## 6.1 EpochRef

```json
{
  "framework_epoch_id": "fwe_...",
  "parameter_epoch_id": "pme_..."
}
```

## 6.2 FrameworkEpoch

```json
{
  "id": "fwe_...",
  "schema_version": "1.0",
  "status": "active",
  "created_at": "2026-04-06T12:00:00Z",
  "activated_at": "2026-04-10T00:00:00Z",
  "supersedes_epoch_id": "fwe_prev_...",
  "change_reason": "meta-governance approved revision",
  "scope": ["governance", "routing", "packet_format"],
  "review_ref": "meta_review_..."
}
```

## 6.3 ParameterEpoch

```json
{
  "id": "pme_...",
  "schema_version": "1.0",
  "status": "active",
  "created_at": "2026-04-06T12:00:00Z",
  "activated_at": "2026-04-10T00:00:00Z",
  "supersedes_epoch_id": "pme_prev_...",
  "parameters": {
    "review_windows": {},
    "thresholds": {},
    "panel_rules": {}
  },
  "review_ref": "meta_review_..."
}
```

Epochs are immutable after activation except for additive metadata.

---

## 7. Proposal Schemas

## 7.1 Proposal

```json
{
  "id": "proposal_...",
  "schema_version": "1.0",
  "title": "Neighborhood traffic calming proposal",
  "summary": "Short neutral summary",
  "full_text": "Canonical proposal text",
  "submitted_at": "2026-04-06T12:00:00Z",
  "submitted_by": {
    "actor_id": "actor_...",
    "actor_type": "member"
  },
  "status": "submitted",
  "proposal_layer": "policy",
  "proposal_kind": "ordinary",
  "community_ref": "community_...",
  "pilot_domain_ref": "pilot_...",
  "affected_parties": [
    {
      "label": "residents on street",
      "impact_type": "direct"
    }
  ],
  "initial_claims": [
    {
      "claim_id": "claim_1",
      "claim_type": "policy_effect",
      "text": "Traffic speed will decrease"
    }
  ],
  "attachments": [],
  "epoch_ref": {
    "framework_epoch_id": "fwe_...",
    "parameter_epoch_id": "pme_..."
  },
  "audit_ref": "evt_submit_...",
  "metadata": {}
}
```

### Immutable after submission
- `id`
- `submitted_at`
- `submitted_by`
- original `full_text`

### Mutable through versioning only
- `status`
- derived summaries
- linked records

## 7.2 ProposalVersion

```json
{
  "id": "proposal_version_...",
  "proposal_id": "proposal_...",
  "version_no": 2,
  "created_at": "2026-04-07T12:00:00Z",
  "created_by": {
    "actor_id": "actor_...",
    "actor_type": "member"
  },
  "change_reason": "clarified scope after challenge",
  "full_text": "Updated canonical text",
  "diff_ref": "diff_...",
  "supersedes_version_id": "proposal_version_prev_..."
}
```

No proposal version may silently overwrite a prior version.

---

## 8. Classification Schemas

## 8.1 ClassificationRecord

```json
{
  "id": "classification_...",
  "proposal_id": "proposal_...",
  "created_at": "2026-04-06T12:30:00Z",
  "classified_by": {
    "actor_id": "classifier_or_review_pool_id",
    "actor_type": "service"
  },
  "classification": {
    "triviality": "non_trivial",
    "layer": "policy",
    "routing_materiality": true,
    "constitutional_spillover": false,
    "framework_change": false,
    "emergency_candidate": false
  },
  "confidence": "medium",
  "rationale": "Touches allocation priorities and affects multiple parties",
  "strongest_layer_rule_applied": false,
  "deterministic_flags": [
    "multiple_affected_parties",
    "resource_commitment_present"
  ],
  "counter_classification_ref": "classification_2",
  "status": "locked",
  "audit_ref": "evt_classified_..."
}
```

## 8.2 ClassificationChallenge

```json
{
  "id": "classification_challenge_...",
  "classification_id": "classification_...",
  "submitted_at": "2026-04-06T13:00:00Z",
  "submitted_by": {
    "actor_id": "actor_...",
    "actor_type": "challenge_submitter"
  },
  "reason_code": "under_escalation",
  "reason_text": "Constitutional spillover not recognized",
  "status": "open",
  "resolution_ref": null
}
```

---

## 9. Panel and Skill Schemas

## 9.1 PanelSelectionRecord

```json
{
  "id": "panel_selection_...",
  "proposal_id": "proposal_...",
  "created_at": "2026-04-06T13:15:00Z",
  "quorum_size": 5,
  "required_classes": [
    "rights_constitutional",
    "adversarial_critique",
    "implementation_feasibility",
    "evidence_discipline",
    "anti_capture"
  ],
  "selected_skills": [
    {
      "skill_id": "skill_...",
      "skill_class": "rights_constitutional",
      "skill_version_id": "skill_version_...",
      "model_profile_ref": "model_profile_..."
    }
  ],
  "selection_mode": "rule_and_registry_based",
  "concentration_flags": [],
  "status": "locked",
  "audit_ref": "evt_panel_selected_..."
}
```

## 9.2 Skill

```json
{
  "id": "skill_...",
  "name": "Rights Constitutional Reviewer",
  "skill_class": "rights_constitutional",
  "tier": "evaluated",
  "status": "admitted",
  "registry_status": "default",
  "owner_ref": "provider_...",
  "model_dependence": "model_tuned",
  "portability_posture": "documented",
  "current_version_id": "skill_version_...",
  "evaluation_status": "passing",
  "audit_ref": "evt_skill_admitted_..."
}
```

### Allowed `tier`
- `template`
- `evaluated`
- `governance_grade`

### Allowed `registry_status`
- `candidate`
- `admitted`
- `default`
- `watchlist`
- `suspended`
- `retired`

## 9.3 SkillVersion

```json
{
  "id": "skill_version_...",
  "skill_id": "skill_...",
  "version_no": 3,
  "created_at": "2026-04-06T10:00:00Z",
  "status": "active",
  "skill_contract": {
    "input_schema_ref": "skill_input_v1",
    "output_schema_ref": "skill_output_v1",
    "prompt_or_scaffold_ref": "artifact_...",
    "supported_models": ["model_profile_1", "model_profile_2"]
  },
  "change_notes": "Added minority harm surfacing check",
  "evaluation_ref": "evaluation_run_...",
  "audit_ref": "evt_skill_version_created_..."
}
```

## 9.4 SkillRun

```json
{
  "id": "skill_run_...",
  "proposal_id": "proposal_...",
  "panel_selection_id": "panel_selection_...",
  "skill_id": "skill_...",
  "skill_version_id": "skill_version_...",
  "model_profile_ref": "model_profile_...",
  "started_at": "2026-04-06T13:20:00Z",
  "completed_at": "2026-04-06T13:21:30Z",
  "status": "completed",
  "input_ref": "skill_input_...",
  "output_ref": "skill_output_...",
  "failure_flags": [],
  "audit_ref": "evt_skill_run_completed_..."
}
```

## 9.5 EvidencePacket

```json
{
  "id": "skill_output_...",
  "skill_run_id": "skill_run_...",
  "proposal_id": "proposal_...",
  "stance_summary": "Explains rights and proportionality concerns",
  "claims": [
    {
      "claim_id": "claim_1",
      "claim_type": "rights_risk",
      "text": "May create unequal burden for mobility-impaired residents",
      "evidence_status": "supported"
    }
  ],
  "dissent_points": [
    "Safety benefit may justify burden if mitigation is added"
  ],
  "unknowns": [
    "No local disability access study attached"
  ],
  "capture_risk_note": "Low direct capture risk, moderate implementation discretion risk",
  "routing_note": "No route recommendation at this stage",
  "packet_section_hints": ["rights", "unknowns", "affected_groups"]
}
```

---

## 10. Packet Schemas

## 10.1 Packet

```json
{
  "id": "packet_...",
  "proposal_id": "proposal_...",
  "packet_type": "briefing",
  "version_no": 1,
  "created_at": "2026-04-06T14:00:00Z",
  "status": "published",
  "synthesis_ref": "packet_synthesis_...",
  "sections": [
    {
      "section_key": "summary",
      "content_ref": "packet_section_1"
    },
    {
      "section_key": "strongest_case_for",
      "content_ref": "packet_section_2"
    }
  ],
  "source_skill_outputs": [
    "skill_output_1",
    "skill_output_2"
  ],
  "challenge_window": {
    "opens_at": "2026-04-06T14:00:00Z",
    "closes_at": "2026-04-08T14:00:00Z"
  },
  "epoch_ref": {
    "framework_epoch_id": "fwe_...",
    "parameter_epoch_id": "pme_..."
  },
  "audit_ref": "evt_packet_published_..."
}
```

### Allowed `packet_type`
- `briefing`
- `challenge`
- `meta`
- `emergency_incident`
- `review`
- `shutdown_notice`

## 10.2 PacketSection

```json
{
  "id": "packet_section_...",
  "packet_id": "packet_...",
  "section_key": "minority_view",
  "title": "Minority View",
  "content": "Some residents support the proposal only if accessibility exceptions are guaranteed.",
  "derived_from": ["skill_output_3"],
  "status": "active"
}
```

### Required section keys for `briefing`
- `summary`
- `strongest_case_for`
- `strongest_case_against`
- `unknowns`
- `minority_view`
- `capture_risk`
- `reversibility`
- `implementation_note`

## 10.3 PacketSynthesisRecord

```json
{
  "id": "packet_synthesis_...",
  "proposal_id": "proposal_...",
  "packet_id": "packet_...",
  "created_at": "2026-04-06T13:55:00Z",
  "synthesized_by": {
    "actor_id": "service_...",
    "actor_type": "service"
  },
  "input_skill_outputs": [
    "skill_output_1",
    "skill_output_2",
    "skill_output_3",
    "skill_output_4",
    "skill_output_5"
  ],
  "framing_limit_note": "Local business-owner perspective weakly evidenced",
  "epistemic_risk_flags": [
    "potential_framing_convergence"
  ],
  "audit_ref": "evt_packet_synthesized_..."
}
```

---

## 11. Challenge and Review Schemas

## 11.1 Challenge

```json
{
  "id": "challenge_...",
  "proposal_id": "proposal_...",
  "packet_id": "packet_...",
  "submitted_at": "2026-04-06T15:00:00Z",
  "submitted_by": {
    "actor_id": "actor_...",
    "actor_type": "challenge_submitter"
  },
  "challenge_type": "packet_omission",
  "reason_code": "minority_harm_omitted",
  "reason_text": "Accessibility issue underrepresented",
  "requested_action": "revise_packet",
  "status": "open",
  "resolution_ref": null,
  "audit_ref": "evt_challenge_submitted_..."
}
```

### Allowed `challenge_type`
- `packet_omission`
- `classification_dispute`
- `routing_dispute`
- `evidence_dispute`
- `operator_intervention_dispute`
- `epistemic_bias_claim`
- `skill_output_dispute`

## 11.2 ReviewCase

```json
{
  "id": "review_case_...",
  "subject_type": "challenge",
  "subject_id": "challenge_...",
  "opened_at": "2026-04-06T15:05:00Z",
  "review_class": "ordinary",
  "status": "open",
  "reviewers": [
    {
      "actor_id": "reviewer_...",
      "actor_type": "reviewer"
    }
  ],
  "resolution_type": null,
  "resolution_text": null,
  "audit_ref": "evt_review_opened_..."
}
```

---

## 12. Evidence Schemas

## 12.1 EvidenceItem

```json
{
  "id": "evidence_...",
  "proposal_id": "proposal_...",
  "source_type": "document",
  "source_ref": "doc_...",
  "title": "Traffic count survey",
  "claim_refs": ["claim_1"],
  "evidence_class": "empirical_structured",
  "retrieved_at": "2026-04-06T12:10:00Z",
  "status": "active"
}
```

### Allowed `evidence_class`
Defined in exactly one place: `EVIDENCE.md` §5 (canonical evidence-class taxonomy).
This document does not maintain its own copy.
An `evidence_subtype` field may carry the finer subtype from the same table.

## 12.2 ClaimRecord

```json
{
  "id": "claim_...",
  "proposal_id": "proposal_...",
  "claim_type": "policy_effect",
  "text": "Traffic speed will decrease",
  "evidence_status": "insufficient",
  "contestation_status": "disputed",
  "evidence_refs": ["evidence_1", "evidence_2"]
}
```

### Allowed `evidence_status`
- `supported`
- `disputed`
- `insufficient`
- `unknown`

---

## 13. Evaluation Schemas

## 13.1 TaskCase

```json
{
  "id": "task_case_...",
  "suite_id": "evaluation_suite_...",
  "skill_class": "anti_capture",
  "task_type": "adversarial",
  "input_context_ref": "fixture_...",
  "expected_properties": [
    "preserves_dissent",
    "names_unknowns",
    "surfaces_capture_risk"
  ],
  "forbidden_behaviors": [
    "false_consensus_compression",
    "hallucinated_certainty"
  ],
  "difficulty_label": "medium",
  "adversarial_flag": true
}
```

## 13.2 EvaluationSuite

```json
{
  "id": "evaluation_suite_...",
  "name": "Anti-Capture Tier 2 Suite",
  "skill_class": "anti_capture",
  "suite_type": "regression_bound",
  "status": "active",
  "task_case_refs": [
    "task_case_1",
    "task_case_2"
  ],
  "created_at": "2026-04-06T09:00:00Z"
}
```

## 13.3 EvaluationRun

```json
{
  "id": "evaluation_run_...",
  "skill_version_id": "skill_version_...",
  "suite_id": "evaluation_suite_...",
  "run_at": "2026-04-06T16:00:00Z",
  "overall_status": "pass",
  "scores": {
    "dissent_fidelity": 0.82,
    "uncertainty_honesty": 0.91,
    "capture_sensitivity": 0.77
  },
  "failure_ledger_refs": [
    "failure_event_1"
  ],
  "regression_status": "improved"
}
```

## 13.4 FailureEvent

```json
{
  "id": "failure_event_...",
  "evaluation_run_id": "evaluation_run_...",
  "failure_class": "FalsePlurality",
  "severity": "high",
  "task_case_id": "task_case_...",
  "notes": "Critic and generator converged on same frame",
  "status": "open"
}
```

---

## 14. Treasury and Funding Schemas

## 14.1 FundingReceipt

```json
{
  "id": "funding_receipt_...",
  "received_at": "2026-04-06T17:00:00Z",
  "source_category": "member_contribution",
  "amount": {
    "currency": "EUR",
    "value": "25.00"
  },
  "destination_partition": "CoreTreasury",
  "restriction_set_ref": null,
  "concentration_indicator": "low",
  "audit_ref": "evt_funding_received_..."
}
```

## 14.2 AllocationDecision

```json
{
  "id": "allocation_...",
  "created_at": "2026-04-06T17:30:00Z",
  "source_partition": "SkillCommonsTreasury",
  "destination_purpose": "benchmark_maintenance",
  "amount": {
    "currency": "EUR",
    "value": "300.00"
  },
  "allocation_class": "baseline_budget",
  "justification": "Maintain public evaluation harness",
  "status": "approved",
  "audit_ref": "evt_allocation_approved_..."
}
```

## 14.3 ReleaseAuthorization

```json
{
  "id": "release_auth_...",
  "allocation_id": "allocation_...",
  "authorized_at": "2026-04-06T18:00:00Z",
  "authorized_by": [
    {
      "actor_id": "actor_1",
      "actor_type": "treasury_admin"
    },
    {
      "actor_id": "actor_2",
      "actor_type": "reviewer"
    }
  ],
  "release_amount": {
    "currency": "EUR",
    "value": "300.00"
  },
  "status": "authorized",
  "audit_ref": "evt_release_authorized_..."
}
```

## 14.4 RevenueRoutingRecord

```json
{
  "id": "revenue_routing_...",
  "public_ip_artifact_id": "public_ip_...",
  "gross_amount": {
    "currency": "EUR",
    "value": "1000.00"
  },
  "net_amount": {
    "currency": "EUR",
    "value": "930.00"
  },
  "routing": [
    {
      "partition": "CoreTreasury",
      "share_pct": 40
    },
    {
      "partition": "SkillCommonsTreasury",
      "share_pct": 30
    },
    {
      "partition": "LocalPilotTreasury",
      "share_pct": 20
    },
    {
      "partition": "MigrationAndExitReserve",
      "share_pct": 10
    }
  ],
  "audit_ref": "evt_revenue_routed_..."
}
```

## 14.5 EconomicConflictDisclosure

Declares a material economic interest held by an actor in a funding, mandate, or IP context.
Executor-side conflict disclosure remains `ConflictOfInterestDisclosure` (§24.10); this record covers economic actors — donors, contractors, licensees, mandate holders, stewards.

```json
{
  "id": "econ_conflict_...",
  "actor_ref": {
    "actor_id": "actor_...",
    "actor_type": "contractor"
  },
  "role": "mandate_holder",
  "interest_type": "licensing_benefit",
  "related_refs": ["mandate_...", "public_ip_...", "funding_receipt_..."],
  "materiality": "medium",
  "mitigation": "excluded from release authorization for this mandate",
  "declared_at": "2026-04-06T12:00:00Z",
  "audit_ref": "evt_conflict_disclosed_..."
}
```

### Allowed `interest_type`
- `financial_interest`
- `employment`
- `ownership`
- `funding_relationship`
- `licensing_benefit`
- `competing_venture`
- `other_declared`

---

## 15. Public IP Schemas

## 15.1 PublicIPArtifact

```json
{
  "id": "public_ip_...",
  "title": "Civic Packet Generator",
  "ip_class": "civic_utility",
  "created_from_ref": "proposal_...",
  "licensing_posture": "commons_preferred",
  "exclusivity_status": "non_exclusive",
  "stewardship_model": "cooperative_maintenance_group",
  "revenue_eligibility": true,
  "treasury_return_rule_ref": "revenue_routing_...",
  "status": "active",
  "audit_ref": "evt_public_ip_classified_..."
}
```

### Allowed `ip_class`
- `commons_core`
- `civic_utility`
- `applied_service`
- `local_experimental`

## 15.2 LicenseRecord

Declares the concrete licensing terms applied to a `PublicIPArtifact`.
Per-class restrictions in `../50_Economics/PUBLIC_IP_MODEL.md` §7.1 bind this record: open-core and delayed release are prohibited for `commons_core` and `civic_utility`.

```json
{
  "id": "license_...",
  "public_ip_artifact_id": "public_ip_...",
  "ip_class": "civic_utility",
  "license_identifier": "AGPL-3.0-or-later",
  "license_terms_ref": "source_...",
  "commercial_use_conditions": "permitted with treasury return per routing rule",
  "derivative_work_conditions": "share-alike required",
  "attribution_requirements": "attribution to the commons and listed contributors",
  "revenue_return_mechanism_ref": "revenue_routing_...",
  "release_schedule": null,
  "status": "active",
  "audit_ref": "evt_license_recorded_..."
}
```

`release_schedule` (a binding date-bound schedule for delayed release) may be non-null only for `applied_service` and `local_experimental` classes.

## 15.3 ContributorAgreement

Declares what a contributor grants and retains when contributing to a public IP asset or mandate.

```json
{
  "id": "contrib_agreement_...",
  "contributor_ref": {
    "actor_id": "actor_...",
    "actor_type": "member"
  },
  "public_ip_artifact_id": "public_ip_...",
  "mandate_id": "mandate_...",
  "contribution_scope": "packet generator rendering module",
  "rights_granted": "non-exclusive license to the commons under the asset's LicenseRecord",
  "rights_retained": "attribution; reuse of own contribution elsewhere",
  "attribution_requirements": "named in contributor registry",
  "compensation_ref": "allocation_...",
  "status": "active",
  "audit_ref": "evt_contributor_agreement_..."
}
```

---

## 16. Community and Pilot Schemas

## 16.1 CommunityRecord

```json
{
  "id": "community_...",
  "name": "Neighborhood Assembly A",
  "community_type": "neighborhood_group",
  "status": "active_pilot",
  "size_band": "25_100",
  "trust_posture": "mixed",
  "pilot_domain_ref": "pilot_...",
  "audit_ref": "evt_community_registered_..."
}
```

## 16.2 PilotDomainRecord

```json
{
  "id": "pilot_...",
  "title": "Traffic and street safety pilot",
  "community_ref": "community_...",
  "primary_issue_class": "local_prioritization",
  "advisory_or_binding": "advisory",
  "expected_cycle_length_days": 7,
  "current_process_description": "chat threads and monthly meeting",
  "packet_usefulness_hypothesis": "Packet improves option comparison",
  "challenge_use_hypothesis": "At least one real challenge occurs",
  "community_readiness_score": "medium",
  "status": "active"
}
```

---

## 17. Operator and Intervention Schemas

## 17.1 OperatorAction

```json
{
  "id": "operator_action_...",
  "performed_at": "2026-04-06T18:15:00Z",
  "performed_by": {
    "actor_id": "operator_...",
    "actor_type": "operator"
  },
  "action_type": "manual_packet_review",
  "subject_type": "packet",
  "subject_id": "packet_...",
  "reason_text": "Formatting issue in summary section",
  "visibility_status": "publicly_logged",
  "audit_ref": "evt_operator_action_logged_..."
}
```

### Allowed `action_type`
- `manual_packet_review`
- `manual_panel_confirmation`
- `challenge_triage`
- `runtime_pause`
- `emergency_action`
- `treasury_hold`
- `audit_correction_append`

No operator action may be unlogged.

---

## 18. Emergency Schemas

## 18.1 EmergencyRecord

```json
{
  "id": "emergency_...",
  "emergency_class": "runtime_integrity",
  "declared_at": "2026-04-06T19:00:00Z",
  "declared_by": {
    "actor_id": "operator_...",
    "actor_type": "operator"
  },
  "scope": "audit_event_store",
  "expires_at": "2026-04-06T21:00:00Z",
  "fallback_reference": "fallback_1",
  "review_case_id": "review_case_...",
  "status": "active",
  "audit_ref": "evt_emergency_declared_..."
}
```

## 18.2 EmergencyTreasuryAction

```json
{
  "id": "emergency_treasury_action_...",
  "emergency_id": "emergency_...",
  "action_type": "temporary_payout_freeze",
  "affected_partition": "CoreTreasury",
  "started_at": "2026-04-06T19:05:00Z",
  "expires_at": "2026-04-06T21:00:00Z",
  "status": "active",
  "audit_ref": "evt_emergency_treasury_action_..."
}
```

---

## 19. Shutdown Schemas

## 19.1 ShutdownReviewRecord

```json
{
  "id": "shutdown_review_...",
  "opened_at": "2026-04-06T20:00:00Z",
  "initiated_by": {
    "actor_id": "steward_...",
    "actor_type": "steward"
  },
  "shutdown_class": "controlled_wind_down",
  "trigger_refs": [
    "funding_failure",
    "community_non_return"
  ],
  "status": "open",
  "decision_ref": null
}
```

## 19.2 ShutdownDecisionRecord

```json
{
  "id": "shutdown_decision_...",
  "review_id": "shutdown_review_...",
  "decided_at": "2026-04-07T12:00:00Z",
  "decision_type": "pause",
  "effective_at": "2026-04-08T00:00:00Z",
  "active_case_disposition_ref": "active_case_disposition_log_...",
  "treasury_disposition_ref": "treasury_disposition_...",
  "archive_plan_ref": "archive_plan_...",
  "status": "effective",
  "audit_ref": "evt_shutdown_decided_..."
}
```

---

## 20. Enumerations Registry

A production implementation should maintain an enumeration registry for:
- actor types
- proposal layers
- proposal kinds
- canonical proposal states and canonical public states (`STATE_MACHINE.md` §4.1, §4.5)
- challenge types
- packet types
- skill tiers
- registry statuses
- evidence classes (`EVIDENCE.md` §5)
- evidence statuses
- authoring modes (3.8)
- route types and executor forms (`DATA_MODEL.md` §7)
- outcome classes (`DATA_MODEL.md` §5.30)
- failure classes
- treasury partitions
- shutdown classes
- emergency classes
- reuse-status labels (3.9)
- economic interest types (14.5)
- public return types (25.7)
- participant body types (26.3)
- civic brief action classes (26.7)
- attention delegation statuses (26.10)
- civic receipt statuses and disclosure classes (26.11)
- participation audit depths and challengeable statuses (26.13)
- sortition certification statuses (26.6)

Enum drift must be versioned through schema or epoch change.

---

## 21. Validation Rules

At minimum, canonical validation should enforce:

1. required fields present
2. enum values valid
3. immutable fields unchanged after creation
4. epoch references valid for runtime-significant objects
5. linked references exist
6. packet section requirements satisfied by packet type
7. non-trivial proposals may not skip panel selection
8. releases may not exist without allocations
9. operator actions may not exist without audit linkage
10. shutdown decisions may not omit active-case disposition
11. `LicenseRecord.release_schedule` may be non-null only for `applied_service` and `local_experimental` IP classes
12. a mandate with public-return obligations may not reach `completed` without at least one `PublicReturnReport` or a documented inability to produce one
13. a non-trivial proposal may not exit `PARTICIPATION_DESIGN_PENDING` (or begin panel selection) without an active `ParticipationPlan`
14. a non-trivial proposal may not reach decision readiness without a `ParticipationAudit` at the depth its classification requires (full for high-impact, lightweight otherwise); trivial proposals are exempt

Validation failure should block canonical acceptance, not merely warn.

---

## 22. Mutability Rules

Objects should be classified as:

### 22.1 Immutable
- `AuditEvent`
- `FundingReceipt`
- `SkillRun`
- `EmergencyRecord` core fields after declaration
- `ProposalVersion`
- `ReplicationRecord`
- `EconomicConflictDisclosure` (superseded by a new disclosure, never edited)
- `SortitionResult` (corrections follow append-only correction rules; the original remains visible)

### 22.2 Append-Only with New Versions
- `Proposal`
- `Packet`
- `SkillVersion`
- `ClassificationRecord` through superseding record only
- `LicenseRecord` through superseding record only
- `PublicReturnReport`
- `ParticipationPolicy` through superseding record only (prospective only; never retunes an active case)
- `ParticipationPlan` through superseding record only (versioned; supersedes chain)
- `AttentionAllocationPolicy` through superseding record only
- `ParticipationAudit` through superseding record only (an upheld challenge reopens via a new record; history preserved)

### 22.3 Mutable with Audit Trace
- `ReviewCase.status`
- `Challenge.status`
- `PilotDomainRecord.status`
- `CommunityRecord.status`
- `ContributorAgreement.status`
- `ParticipantBody.status`
- `AttentionDelegation.status`
- `CivicReceipt.status`
- `TargetedInvitation.response_status`
- `SortitionConfiguration.status`

If a field changes meaningfully, the change must be audit-linked.

---

## 23. Prototype Profile

For prototype v1, the minimum required canonical schemas are:

- `Proposal`
- `ClassificationRecord`
- `PanelSelectionRecord`
- `Skill`
- `SkillVersion`
- `SkillRun`
- `EvidencePacket`
- `Packet`
- `Challenge`
- `AuditEvent`
- `TaskCase`
- `EvaluationRun`
- `OperatorAction`
- `PilotDomainRecord`

Other schemas may remain documented but unimplemented until needed.

---


## 24. Executor and Evidence Schemas

## 24.1 ExecutorRecord

```json
{
  "id": "executor_...",
  "executor_type": "ai_model",
  "status": "admitted",
  "sandbox_ref": "sandbox_eval_...",
  "revalidation_ref": "reval_...",
  "ranking_ref": "ranking_...",
  "conflict_disclosure_ref": "conflict_...",
  "jurisdiction_layers": ["local", "regional"],
  "visibility": "public"
}
```

## 24.2 MaterialClaim

```json
{
  "id": "claim_...",
  "text": "Traffic speed will decrease",
  "source_refs": ["source_1"],
  "traceability_link": "trace_..."
}
```

## 24.3 SourceRecord

```json
{
  "id": "source_...",
  "title": "Traffic count survey",
  "url": "https://example.com/survey",
  "validation_status": "verified"
}
```

## 24.4 ConfidenceRecord

```json
{
  "id": "confidence_...",
  "derived_score": 0.85,
  "evidence_coverage": 0.9,
  "source_quality": 0.8,
  "jurisdiction_fit": 0.9,
  "freshness": 0.95,
  "scoring_version": "v1.2"
}
```

## 24.5 RankingRecord

```json
{
  "id": "ranking_...",
  "executor_id": "executor_...",
  "context_scope": "traffic_policy",
  "rank_score": 0.92,
  "ranking_version": "v2.0"
}
```

## 24.6 SandboxEvaluationRecord

```json
{
  "id": "sandbox_eval_...",
  "executor_id": "executor_...",
  "suite_id": "eval_suite_...",
  "status": "passed"
}
```

## 24.7 RevalidationRecord

```json
{
  "id": "reval_...",
  "executor_id": "executor_...",
  "last_revalidated_at": "2026-04-06T12:00:00Z",
  "status": "active",
  "downgrade_state": null
}
```

## 24.8 HumanExpertRecord

```json
{
  "id": "human_expert_...",
  "executor_id": "executor_...",
  "expertise_domain": "urban_planning",
  "conflict_disclosure_ref": "conflict_..."
}
```

## 24.9 ReviewRoutingSignal

```json
{
  "id": "routing_signal_...",
  "trigger_type": "low_confidence",
  "target_executor_type": "human_panel"
}
```

## 24.10 ConflictOfInterestDisclosure

```json
{
  "id": "conflict_...",
  "executor_id": "executor_...",
  "disclosure_text": "Consulted for city planning department in 2024"
}
```

## 24.11 PublicBriefingView

```json
{
  "id": "briefing_view_...",
  "packet_id": "packet_...",
  "claim_source_links": ["claim_1:source_1"]
}
```

## 24.12 SynthesisConflictMap

```json
{
  "id": "conflict_map_...",
  "synthesis_id": "packet_synthesis_...",
  "unresolved_disputes": ["dispute_1"]
}
```

## 24.13 ChallengeEvent

```json
{
  "id": "challenge_event_...",
  "challenge_id": "challenge_...",
  "event_type": "analytic_basis_challenged"
}
```

## 24.14 AuditTrace

```json
{
  "id": "audit_trace_...",
  "event_id": "evt_...",
  "claim_source_links": ["claim_1:source_1"]
}
```

## 24.15 ReplicationRecord

Makes independence claims inspectable when the same skill is executed by more than one executor (`EXECUTOR_MODEL.md` §11, §14).
Substantially overlapping executions MUST NOT be presented as independent agreement (`SKILL_REGISTRY.md` §11.3 illusory-diversity ban).

```json
{
  "id": "replication_...",
  "skill_id": "skill_...",
  "skill_version_id": "skill_version_...",
  "replicated_run_refs": ["skill_run_1", "skill_run_2"],
  "executor_ids": ["executor_a", "executor_b"],
  "overlap_declaration": {
    "model_family_overlap": "none",
    "provider_overlap": "none",
    "source_overlap": "partial",
    "context_overlap": "none",
    "methodology_overlap": "substantial"
  },
  "cross_influence": false,
  "cross_influence_description": null,
  "independence_class": "partially_independent",
  "divergence_summary": "Executors disagree on projected maintenance cost by 3x",
  "resolution_ref": "routing_signal_...",
  "audit_ref": "evt_replication_recorded_..."
}
```

### Allowed overlap values
- `none`
- `partial`
- `substantial`

### Allowed `independence_class`
- `independent`
- `partially_independent`
- `not_independent`

## 25. Problem, Judgment, Mandate, and Outcome Schemas

These schemas complete the tail of the civic loop.
Logical field definitions live in `DATA_MODEL.md` §5.25–§5.32; the shapes below are their canonical payload forms.

## 25.1 ProblemDefinition

```json
{
  "id": "probdef_...",
  "proposal_id": "proposal_...",
  "proposal_version_id": "proposal_version_...",
  "problem_statement": "Pedestrian injuries on the street have doubled in two years",
  "affected_scope_ref": "ctx_...",
  "affected_public_interest": "street safety",
  "observable_conditions": ["injury statistics", "traffic speed measurements"],
  "time_horizon": "2y",
  "known_constraints": ["municipal budget cycle"],
  "unresolved_questions": ["seasonal variation unmeasured"],
  "evaluation_criteria": ["injury rate per year", "measured 85th-percentile speed"],
  "exclusion_boundaries": ["parking policy is out of scope"],
  "validation_status": "validated",
  "audit_ref": "evt_problem_validated_..."
}
```

Classification may not complete without a `validated` ProblemDefinition (`PROTOCOL.md` invariant 7.9).

## 25.2 JudgmentConfiguration

Declares, before any decision opens, how the judgment will work.
The configuration itself is approved under the governing framework and parameter epoch — it is never chosen ad hoc for an active case (see `../10_Constitutional/GOVERNANCE.md`).

```json
{
  "id": "judgcfg_...",
  "proposal_id": "proposal_...",
  "question": "Adopt the traffic calming plan as specified?",
  "choices": ["approve", "reject", "defer"],
  "eligible_body_ref": "ctx_...",
  "standing_requirements_ref": "elig_policy_...",
  "decision_mode": "direct_vote",
  "quorum_rule": "pset_...",
  "threshold_rule": "pset_...",
  "tie_handling": "defer",
  "abstention_treatment": "not_counted",
  "binding": true,
  "challenge_process_ref": "framework_...",
  "conflict_of_interest_rules_ref": "elig_policy_...",
  "epoch_ref": {
    "framework_epoch_id": "fwe_...",
    "parameter_epoch_id": "pme_..."
  },
  "audit_ref": "evt_judgment_configured_..."
}
```

## 25.3 ExecutionMandate

```json
{
  "id": "mandate_...",
  "execution_route_id": "route_...",
  "proposal_id": "proposal_...",
  "authorized_actor_ref": "actor_...",
  "objective": "Implement traffic calming per approved plan",
  "scope_ref": "ctx_...",
  "permitted_actions": ["..."],
  "prohibited_actions": ["..."],
  "budget": {"currency": "EUR", "value": "40000.00"},
  "ip_and_licensing_ref": "public_ip_...",
  "milestones": [{"key": "m1", "due": "2026-09-01", "deliverable": "..."}],
  "success_criteria": ["..."],
  "failure_criteria": ["..."],
  "suspension_conditions": ["..."],
  "rollback_path": "...",
  "public_return_obligations": ["..."],
  "status": "issued",
  "audit_ref": "evt_mandate_issued_..."
}
```

No decision creates authority beyond its mandate.

## 25.4 MonitoringRule / MonitoringEvent / MonitoringReport

```json
{
  "id": "monrule_...",
  "mandate_id": "mandate_...",
  "monitored_obligation": "milestone m1 delivered",
  "expected_value": "delivery signed by 2026-09-01",
  "measurement_method": "signed_delivery_record",
  "acceptable_deviation": "14d",
  "automatic_suspension_condition": "deviation > 60d",
  "status": "active"
}
```

```json
{
  "id": "monev_...",
  "monitoring_rule_id": "monrule_...",
  "mandate_id": "mandate_...",
  "expected_value": "delivery signed by 2026-09-01",
  "observed_value": "delivery signed 2026-09-10",
  "evidence_ref": "evidence_...",
  "observed_at": "2026-09-10T12:00:00Z",
  "reporter_provenance": "operator_...",
  "validation_status": "validated",
  "deviation": "9d",
  "escalation_status": "none",
  "audit_ref": "evt_monitoring_..."
}
```

Monitoring events must come from deterministic, inspectable mechanisms wherever possible.
Models may summarize monitoring data; they MUST NOT be the sole authority on whether an obligation was satisfied.

## 25.5 OutcomeRecord

```json
{
  "id": "outcome_...",
  "proposal_id": "proposal_...",
  "mandate_id": "mandate_...",
  "original_objective": "...",
  "expected_result": "...",
  "observed_result": "...",
  "evidence_refs": ["evidence_..."],
  "evaluation_period": {"from": "2026-09-01", "to": "2027-09-01"},
  "costs": ["..."],
  "benefits": ["..."],
  "harms": ["..."],
  "unintended_effects": ["..."],
  "outcome_class": "partially_successful",
  "confidence": "medium",
  "unresolved_questions": ["..."],
  "recommendation": "correct",
  "audit_ref": "evt_outcome_recorded_..."
}
```

The outcome record MUST NOT rewrite the original expectations.
No proposal closes without an OutcomeRecord or a documented inability to determine one.

## 25.6 LearningRecord

```json
{
  "id": "learn_...",
  "proposal_id": "proposal_...",
  "outcome_id": "outcome_...",
  "expected_vs_actual": ["..."],
  "predictions_vs_observed": ["..."],
  "failures": ["..."],
  "corrections": ["..."],
  "guidance_for_future_cases": "...",
  "authoring_mode": "human_model_collaborative",
  "published_at": "2027-09-15T12:00:00Z",
  "audit_ref": "evt_learning_published_..."
}
```

## 25.7 PublicReturnReport

Closes the Public Value Loop: the auditable record of whether declared public-return obligations were actually delivered (`../50_Economics/PUBLIC_IP_MODEL.md` §9; treasury movements per `../50_Economics/TREASURY.md`).
The report MUST NOT rewrite the original obligations.

```json
{
  "id": "public_return_...",
  "mandate_id": "mandate_...",
  "public_ip_artifact_id": "public_ip_...",
  "obligation_ref": "mandate_....public_return_obligations",
  "period": {"from": "2026-09-01", "to": "2027-09-01"},
  "returns_delivered": [
    {
      "return_type": "open_source_software",
      "description": "Packet generator released under LicenseRecord license_...",
      "value": null,
      "evidence_refs": ["source_..."]
    },
    {
      "return_type": "treasury_revenue",
      "description": "Licensing revenue routed per revenue_routing_...",
      "value": {"currency": "EUR", "value": "930.00"},
      "evidence_refs": ["revenue_routing_..."]
    }
  ],
  "shortfalls": ["community training sessions not delivered"],
  "settlement_status": "shortfall_declared",
  "published_at": "2027-09-15T12:00:00Z",
  "audit_ref": "evt_public_return_reported_..."
}
```

### Allowed `return_type`
- `open_knowledge`
- `open_source_software`
- `public_infrastructure`
- `free_essential_access`
- `cost_reduction`
- `community_ownership`
- `royalties`
- `treasury_revenue`
- `contributor_compensation`
- `participation_funding`
- `educational_value`

### Allowed `settlement_status`
- `on_track`
- `settled`
- `shortfall_declared`
- `in_dispute`

A mandate with public-return obligations may not reach `completed` without at least one `PublicReturnReport` or a documented inability to produce one (validation rule 12).

---

## 26. Participation Artifacts

The participation subsystem (`../99_Reference/CORE_V03_RECONCILIATION.md` §4.1) is defined normatively in `../45_Participation/PARTICIPATION_MODEL.md`, `../45_Participation/SORTITION.md`, `../45_Participation/ATTENTION_AND_REACH.md`, and `../45_Participation/GOVERNANCE_HEALTH.md`. Logical field definitions live in `DATA_MODEL.md` §5.37–§5.50; the shapes below are their canonical payload forms.

Participation is a transversal concern within the single proposal lifecycle, not a second state machine (`PARTICIPATION_MODEL.md` §2).

## 26.1 ParticipationPolicy

Scope-level, epoch-bound participation defaults (`PARTICIPATION_MODEL.md` §5). Applies prospectively; a change to it never retunes an active case.

```json
{
  "id": "participation_policy_...",
  "context_id": "ctx_...",
  "epoch_ref": {
    "framework_epoch_id": "fwe_...",
    "parameter_epoch_id": "pme_..."
  },
  "participation_modes_by_tier": {
    "trivial": [],
    "non_trivial": ["open", "affected_party"],
    "high_impact": ["open", "affected_party", "sortition", "targeted_invitation"]
  },
  "sortition_enabled": true,
  "sortition_disabled_declaration": null,
  "default_participation_windows": {"policy": "14d", "governance": "21d", "constitutional": "30d"},
  "compensation_defaults": {"basis": "hourly_rate_v1", "eligible_roles": ["sortition_body", "affected_party"]},
  "accessibility_baseline": "wcag_2.2_aa_equivalent",
  "civic_translation_baseline": "plain_language_summary_required",
  "delegation_rules": {
    "max_scope_count_per_delegate": 5,
    "concentration_threshold_pct": 10
  },
  "attention_budget": {
    "unit": "citizen_hours_requested_per_month",
    "value": 4000,
    "owner": "scope_governance"
  },
  "intake_rate_limits": {"submissions_per_credential_per_day": 3},
  "uniqueness_requirements_by_action_type": {"vote": "nullifier_required", "comment": "nullifier_required"},
  "audit_depth_defaults": {"non_trivial": "lightweight", "high_impact": "full"},
  "brief_cadence": "weekly",
  "status": "active",
  "supersedes_policy_id": null,
  "audit_ref": "evt_participation_policy_activated_..."
}
```

`sortition_disabled_declaration` MUST be non-null when `sortition_enabled` is `false`, and must state the reason, the substitute mechanism, and the review epoch (`SORTITION.md` §14). Silence is not a valid disabling.

## 26.2 ParticipationPlan

Per-proposal participation design (`PARTICIPATION_MODEL.md` §6). Absorbs scope-and-impact mapping, barrier assessment, accessibility planning, and civic-translation requirements as sections, not separate artifacts (reconciliation record §5).

```json
{
  "id": "participation_plan_...",
  "proposal_id": "proposal_...",
  "version_no": 1,
  "supersedes_plan_id": null,
  "affected_civic_scopes": ["ctx_..."],
  "eligible_participant_population_ref": "elig_policy_...",
  "expected_participation_barriers": ["time_poverty", "language", "digital_access"],
  "known_missing_perspectives": ["mobility-impaired residents"],
  "selected_participation_modes": ["open", "affected_party", "targeted_invitation"],
  "open_participation_channels": ["public_comment", "evidence_submission"],
  "targeted_invitation_rules": "invite two disability-advocacy groups per barrier assessment",
  "sortition_ref": null,
  "compensation_rules": {"applies_to": ["affected_party"], "basis": "hourly_rate_v1"},
  "accessibility_measures": ["screen-reader-compatible packet", "sign-language briefing"],
  "civic_translation_requirements": ["plain_language_summary", "translation:el"],
  "delegation_rules_in_force": "scope_default",
  "participation_windows": {"opens_at": "2026-04-06T14:00:00Z", "closes_at": "2026-04-20T14:00:00Z"},
  "expected_time_commitment": "2-4 hours over review window",
  "judgment_authority_by_group": {"open_public": "vote", "affected_party_body": "advisory_input"},
  "conflict_of_interest_handling": "declared per PARTICIPATION_MODEL.md §11.2",
  "privacy_and_standing_requirements": "pseudonymous standing proof required for compensation",
  "participation_audit_criteria": "full audit — proposal classified high-impact",
  "status": "active",
  "audit_ref": "evt_participation_plan_activated_..."
}
```

A non-trivial proposal must have an active `ParticipationPlan` before panel selection begins — this is the exit condition of the `PARTICIPATION_DESIGN_PENDING` internal state (`STATE_MACHINE.md` §4.1, §4.4; validation rule 13). The plan is versioned and revisable; `PARTICIPATION_DESIGNED` certifies that a first complete plan exists, not that design is finished.

## 26.3 ParticipantBody

Represents a formed body distinct from the skill panel (`PARTICIPATION_MODEL.md` §3.4, §10).

```json
{
  "id": "participant_body_...",
  "proposal_id": "proposal_...",
  "body_type": "sortition",
  "formation_method_refs": ["sortition_result_..."],
  "member_roster_ref": "roster_pseudonymous_...",
  "declared_conflicts": [],
  "mandate": "advisory deliberation and recommendation to the open public vote",
  "limitations": "does not hold binding judgment authority",
  "term": {"start": "2026-04-13T00:00:00Z", "end": "2026-04-20T00:00:00Z"},
  "status": "ready",
  "audit_ref": "evt_participant_body_formed_..."
}
```

### Allowed `body_type`
- `open_public`
- `affected_party`
- `sortition`
- `civic_jury`
- `institutional`
- `monitoring`
- `hybrid`

When the governing `ParticipationPlan` requires a formed body (jury, sortition body, invited affected-party body), the proposal passes through `PARTICIPANT_BODY_FORMATION` before the state's MUST-form exit guard is satisfied (`STATE_MACHINE.md` §4.4). When no formed body is required, the open public is the participant body and the state is skipped. A person may hold both a `ParticipantBody` seat and a `PanelSeat`, but each role and each conflict must be declared separately.

## 26.4 TargetedInvitation

```json
{
  "id": "targeted_invitation_...",
  "proposal_id": "proposal_...",
  "invitee_ref": "invitee_pseudonymous_...",
  "issued_at": "2026-04-07T09:00:00Z",
  "reason": "systematic absence of mobility-impaired residents from open channel",
  "perspective_sought": "lived experience of street-crossing risk",
  "identification_method": "referral via disability-advocacy registry",
  "compensation_offered": true,
  "judgment_authority": "none",
  "response_status": "accepted",
  "responded_at": "2026-04-08T10:00:00Z",
  "audit_ref": "evt_invitation_issued_..."
}
```

`judgment_authority` defaults to `none`; a non-default grant must be justified and declared in the governing `ParticipationPlan`. Invitation patterns are an audit surface: systematically inviting only aligned voices is a capture pattern (`PARTICIPATION_MODEL.md` §4.3).

## 26.5 SortitionConfiguration

Published before the registry snapshot commitment (`SORTITION.md` §8). The eligible-population registry is treated as the primary capture surface.

```json
{
  "id": "sortition_config_...",
  "proposal_or_function_ref": "proposal_...",
  "civic_scope_ref": "ctx_...",
  "eligible_population_definition_ref": "elig_policy_...",
  "eligibility_criteria_ref": "elig_criteria_...",
  "registry_snapshot_commitment": "sha256:...",
  "selection_window": {"opens_at": "2026-04-08T00:00:00Z", "closes_at": "2026-04-13T00:00:00Z"},
  "selection_method": "commit_reveal_multi_contributor",
  "selection_algorithm_params": {"contributors": 3, "vrf_scheme": "vrf_v1"},
  "seed_governance": {
    "sources": ["contributor_a", "contributor_b", "contributor_c"],
    "independence_basis": "distinct organizations, no shared operator",
    "commitment_schedule": "2026-04-08T00:00:00Z",
    "reveal_schedule": "2026-04-12T00:00:00Z",
    "withheld_reveal_fallback": "re_run_with_new_configuration"
  },
  "body_size": 24,
  "quorum": 18,
  "stratification_rules": "none_declared",
  "replacement_rules": "ranked overflow of the same snapshot and seed",
  "opt_out_rules": "decline without penalty, without loss of standing, without exposure of reason",
  "privacy_protections": {"member_visibility": "pseudonymous_during_term", "post_term_naming": "member_choice"},
  "compensation_ref": "participation_policy_...",
  "accessibility_support_commitments": ["sign-language interpretation", "written and audio briefs"],
  "term": {"start": "2026-04-13T00:00:00Z", "end": "2026-04-20T00:00:00Z"},
  "mandate": "advisory deliberation",
  "judgment_authority": "advisory_input",
  "verification_instructions": "verify_ref://sortition/2026-04-13",
  "status": "snapshot_committed",
  "audit_ref": "evt_sortition_configured_..."
}
```

`stratification_rules`, once declared, must not change between the snapshot commitment and the result. A draw whose registry snapshot commitment was published after seed revelation is invalid.

## 26.6 SortitionResult

```json
{
  "id": "sortition_result_...",
  "sortition_configuration_id": "sortition_config_...",
  "registry_snapshot_commitment": "sha256:...",
  "revealed_seed_material": "seed_reveal_...",
  "seed_verification_data": "verify_payload_...",
  "draw_proof_ref": "proof_...",
  "selected_set": ["member_pseudo_1", "member_pseudo_2"],
  "decline_replacement_chain": [
    {"draw_rank": 3, "outcome": "declined", "replaced_by_rank": 25}
  ],
  "participant_body_ref": "participant_body_...",
  "verification_instructions": "verify_ref://sortition/2026-04-13",
  "certification_status": "certified",
  "anomaly_notes": null,
  "published_at": "2026-04-13T00:00:00Z",
  "audit_ref": "evt_sortition_result_published_..."
}
```

### Allowed `certification_status`
- `certified`
- `anomaly_pending`
- `voided`

A published `SortitionResult` is immutable (`SORTITION.md` §9.2). Certification requires: the draw verifies against the committed snapshot, the selection window contained no invalid registry changes, material registry challenges are resolved, and anomaly signals are investigated or explicitly risk-accepted with rationale.

## 26.7 CivicBrief

Delivers attention through periodic, bounded briefs rather than a continuous engagement feed (`ATTENTION_AND_REACH.md` §7).

```json
{
  "id": "civic_brief_...",
  "civic_scope_ref": "ctx_...",
  "period": {"from": "2026-04-06", "to": "2026-04-13"},
  "items": [
    {
      "content_ref": "proposal_...",
      "action_class": "required_action",
      "summary": "Sortition body seat accepted; deliberation opens Monday",
      "canonical_links": ["proposal_...", "participant_body_..."],
      "dissent_preserved": true,
      "uncertainty_preserved": true
    },
    {
      "content_ref": "proposal_other_...",
      "action_class": "invitation",
      "summary": "Open comment window on parking policy proposal",
      "canonical_links": ["proposal_other_..."],
      "dissent_preserved": true,
      "uncertainty_preserved": true
    }
  ],
  "published_at": "2026-04-06T08:00:00Z",
  "audit_ref": "evt_civic_brief_published_..."
}
```

### Allowed `action_class`
- `information`
- `invitation`
- `optional_review`
- `required_action`
- `urgent_action`

A brief is a projection, never a replacement of the record: it must link to canonical artifacts and preserve visible dissent and unresolved questions (*no summary without source continuity*, `../80_Runtime/INVARIANTS.md`). `urgent_action` items are bound by the urgency discipline of `ATTENTION_AND_REACH.md` §9.

## 26.8 AttentionAllocationPolicy

```json
{
  "id": "attention_policy_...",
  "civic_scope_ref": "ctx_...",
  "version_no": 3,
  "epoch_ref": {
    "framework_epoch_id": "fwe_...",
    "parameter_epoch_id": "pme_..."
  },
  "factors": {
    "mode": "explicit_weights",
    "weights": {
      "classification_result": 0.4,
      "demonstrated_impact": 0.2,
      "affected_population": 0.2,
      "remaining_attention_budget": 0.2
    }
  },
  "prohibited_bases_attestation": "excludes clicks, reactions, outrage, repetition, popularity, purchased promotion, institutional prestige",
  "status": "active",
  "supersedes_policy_id": "attention_policy_prev_...",
  "audit_ref": "evt_attention_policy_activated_..."
}
```

`factors.mode` must be either `explicit_weights` or `deterministic_ordering` (a lexicographic priority list) — a factor list without one of these is not a policy (`ATTENTION_AND_REACH.md` §3.3). The policy is stable for active cases; no mid-case retuning.

## 26.9 ReachDecision

```json
{
  "id": "reach_decision_...",
  "content_ref": "proposal_...",
  "civic_scope_ref": "ctx_...",
  "policy_version_ref": "attention_policy_...",
  "reason": "affected_population factor: 1,200 distinct affected credentials with standing",
  "duration": "P7D",
  "target_audience": "scope-wide notification",
  "urgency_evidence_ref": null,
  "automated_systems_involved": [{"executor_id": "executor_...", "role": "candidate_ranking"}],
  "appeal_path_ref": "challenge_type:routing_dispute",
  "created_at": "2026-04-08T12:00:00Z",
  "audit_ref": "evt_reach_decision_recorded_..."
}
```

A Reach Decision is material when it targets a non-trivial proposal, expands audience beyond the originating scope, uses notification (interruption), or is contested; high-impact cases always produce material Reach Decisions (`ATTENTION_AND_REACH.md` §4.1). A reach grant that cannot be traced to a policy version and declared inputs is an invariant violation.

## 26.10 AttentionDelegation

```json
{
  "id": "attention_delegation_...",
  "delegator_ref": "delegator_pseudonymous_...",
  "delegate_ref": "delegate_...",
  "civic_scope_ref": "ctx_...",
  "purpose": "monitor execution mandate milestones and summarize progress",
  "created_at": "2026-04-06T12:00:00Z",
  "expires_at": "2026-07-06T12:00:00Z",
  "revoked_at": null,
  "non_transferable": true,
  "status": "active",
  "audit_ref": "evt_attention_delegation_created_..."
}
```

### Allowed `status`
- `active`
- `revoked`
- `expired`

An `AttentionDelegation` is scope-specific, purpose-specific, time-limited with automatic expiry, revocable at any moment by the delegator, and non-transferable by default (`ATTENTION_AND_REACH.md` §8.1). **Delegation of attention never consumes the delegator's civic action, nullifier, or uniqueness budget** — only the delegator's own action, or an explicitly authorized judgment delegation under a `JudgmentConfiguration`, spends the delegator's uniqueness (`CORE_V03_RECONCILIATION.md` §6.10). Concentration per delegate per scope is a standing `GovernanceHealthReport` metric (§2.6).

## 26.11 CivicReceipt

```json
{
  "id": "civic_receipt_...",
  "participant_ref": "participant_pseudonymous_...",
  "case_ref": "proposal_...",
  "action_type": "evidence_submission",
  "policy_applied_ref": "participation_policy_...",
  "status": "included",
  "inclusion_proof_ref": "inclusion_proof_...",
  "disclosure": "private_proof",
  "created_at": "2026-04-07T15:00:00Z",
  "audit_ref": "evt_civic_receipt_issued_..."
}
```

### Allowed `status`
- `accepted`
- `included`
- `corrected`
- `superseded`
- `rejected`

### Allowed `disclosure`
- `private_proof`
- `public_aggregate`

Lifecycle: `accepted` → `included` (→ `corrected` | `superseded` | `rejected`). Built on the receipt/inclusion-proof class of `../40_Identity/CRYPTOGRAPHIC_MODEL.md` §5. Disclosure default is **private proof, public aggregate**: the receipt must not reveal how the participant acted unless the participant chooses disclosure or the process requires public attribution.

## 26.12 ParticipantCompensationRecord

Distinct from contributor compensation (`SCHEMAS.md` §15.3 `ContributorAgreement`); paid through the existing treasury release and `FundingReceipt` rails.

```json
{
  "id": "participant_compensation_...",
  "participant_ref": "participant_pseudonymous_...",
  "case_ref": "participant_body_...",
  "participation_role": "sortition_body_member",
  "compensable_items": ["attendance", "preparation", "deliberation", "lost_working_time"],
  "amount": {"currency": "EUR", "value": "180.00"},
  "basis_of_calculation": "hourly_rate_v1 x 6 hours",
  "independence_of_position_attestation": "compensation fixed prior to and independent of deliberation outcome",
  "treasury_release_ref": "release_auth_...",
  "funding_receipt_ref": "funding_receipt_...",
  "status": "released",
  "audit_ref": "evt_participant_compensation_recorded_..."
}
```

Compensation is a distinct spending class, `ParticipantCompensation`, separate from contributor compensation (`../50_Economics/TREASURY.md`). It must be independent of the participant's position, vote, or agreement and independent of the final judgment; correlation between compensation and expressed position is a capture failure signal (`PARTICIPATION_MODEL.md` §7.3).

## 26.13 ParticipationAudit

Required before every non-trivial proposal reaches decision readiness (`STATE_MACHINE.md` §4.4 `READY_FOR_DECISION` guard; validation rule 14).

```json
{
  "id": "participation_audit_...",
  "case_ref": "proposal_...",
  "auditor_ref": {
    "actor_id": "actor_...",
    "actor_type": "auditor"
  },
  "auditor_conflict_declaration": "no conflicting role held on this proposal",
  "depth": "full",
  "examination_results": {
    "eligible_population_summary": "...",
    "informed_summary": "...",
    "participated_summary": "...",
    "selected_and_declined_summary": "...",
    "absent_groups": ["renters under 25"],
    "barriers_and_measures_taken": ["translated brief issued", "childcare stipend offered"],
    "accessibility_and_translation_delivery": "delivered as planned",
    "compensation_delivery_and_independence": "delivered on schedule; no position correlation detected",
    "sortition_integrity": "no anomalies",
    "delegation_concentration": "below threshold",
    "abnormal_participation_patterns": "none detected",
    "uniqueness_and_standing_guarantees": "verified",
    "systematic_exclusion_finding": "none found"
  },
  "limitations": "renter perspective under-represented despite outreach",
  "challengeable_status": "open",
  "created_at": "2026-04-19T12:00:00Z",
  "audit_ref": "evt_participation_audit_published_..."
}
```

### Allowed `depth`
- `full`
- `lightweight`

### Allowed `challengeable_status`
- `open`
- `challenged`
- `upheld_reopened`
- `resolved`

Depth is proportional: `full` for high-impact proposals, `lightweight` for other non-trivial proposals (participation summary, barrier check, anomaly check); trivial proposals are exempt (reconciliation conflict 13). The auditor must not hold a conflicting role on the same proposal (proposer, panel seat, operator executing the case, body member) and must declare conflicts; a model must not be the sole auditor. An upheld challenge reopens the audit via a new superseding record — history is preserved, not overwritten.

## 26.14 GovernanceHealthReport

Publishes dashboard metrics at least quarterly, per civic scope (`GOVERNANCE_HEALTH.md` §2.9).

```json
{
  "id": "governance_health_report_...",
  "civic_scope_ref": "ctx_...",
  "period": {"from": "2026-01-01", "to": "2026-03-31"},
  "metric_families": {
    "latency": {"average_resolution_time_by_class": {}, "latency_budget_breach_rate": 0.04},
    "classification": {"challenge_rate": 0.06, "overturn_rate": 0.02},
    "participation": {"actor_concentration_hhi": 0.18, "affected_community_participation_rate": 0.31},
    "emergency": {"invocation_frequency": 1},
    "output_quality": {"decision_summary_publication_rate": 0.98},
    "delegation_concentration": {"delegation_concentration_index_hhi": 0.09, "max_delegate_share_pct": 7},
    "compensation_distribution": {"compensation_coverage_rate": 0.93, "compensation_position_correlation": "none_detected"},
    "sortition_integrity": {"draw_verification_failure_rate": 0.0, "pre_draw_registry_churn": "within_baseline"}
  },
  "flags_raised": [],
  "read_model_links": ["read_model_ref_1", "read_model_ref_2"],
  "published_at": "2026-04-05T00:00:00Z",
  "audit_ref": "evt_governance_health_report_published_..."
}
```

A `GovernanceHealthReport` **MUST NOT** reduce governance health to a single legitimacy score (`GOVERNANCE_HEALTH.md` §2.9). Metrics inform structural review; they do not themselves constitute or replace public judgment. Reports preserve links to the underlying read models and measurement methods so results are independently reproducible.

---

## 27. ZK Civic Credentials Schemas

The ZK Civic Credentials subsystem (`../40_Identity/ZK_CIVIC_CREDENTIALS.md`) defines policy, receipt, and lifecycle-event artifacts for privacy-preserving civic standing proofs. These schemas complement the participation artifacts in §26 and connect to `CivicReceipt` (§26.11), `ParticipationPlan` (§26.2), and `ParticipationAudit` (§26.13).

All policy artifacts are public governance records. Private credentials and raw onboarding evidence are never public artifacts.

## 27.1 StandingVerificationPolicy

Defines the eligibility condition, evidence requirements, and issuance authorization rules for a civic scope (`ZK_CIVIC_CREDENTIALS.md` §6.2, §18.1).

```json
{
  "policyId": "standing_policy_...",
  "schemaVersion": "1.0",
  "civicScope": "ctx_...",
  "eligibilityRule": "residence_in_scope",
  "authorizedVerifierClasses": ["government_agency", "qualified_third_party"],
  "acceptedEvidenceCategories": ["government_id", "utility_bill"],
  "prohibitedEvidenceCategories": ["biometric_identifier"],
  "retentionPolicy": "delete_after_issuance",
  "issuanceAuthorizationProfile": "blind_threshold_v1",
  "appealPolicy": "appeal_to_standing_verifier_ombudsperson",
  "validFrom": "2026-04-06T00:00:00Z",
  "validUntil": "2027-04-06T00:00:00Z",
  "governanceArtifactRef": "governance_artifact_..."
}
```

### Standing verification invariants

- The verifier must never publish the private evidence or the basis of standing (`ZK_CIVIC_CREDENTIALS.md` §5.2).
- The verifier must not observe the citizen's later civic actions.
- The `issuanceAuthorizationProfile` determines the cryptographic protocol for blind threshold issuance.
- `authorizedVerifierClasses` must be non-empty; each class must be governed and auditable.

**Cross-references:**
- Referenced by `ParticipationPlan` (§26.2) as the standing-verification policy.
- Referenced by `CivicActionVerificationReceipt` (§27.4) as `standingPolicyId`.

## 27.2 CivicCredentialPolicy

Defines the credential class, permitted scopes, attributes, issuance rules, expiry, revocation, and recovery semantics (`ZK_CIVIC_CREDENTIALS.md` §6.3, §18.2).

```json
{
  "policyId": "credential_policy_...",
  "schemaVersion": "1.0",
  "credentialClass": "civic_participation",
  "permittedCivicScopes": ["ctx_..."],
  "privateAttributes": ["holder_secret", "standing_basis"],
  "selectivelyDisclosedAttributes": ["age_threshold_met", "body_membership"],
  "allowedActionTypes": ["vote", "comment", "evidence_submission"],
  "holderBindingProfile": "holder_secret_binding_v1",
  "cryptographicProfileId": "crypto_profile_v1",
  "issuanceEpoch": "fwe_...",
  "expiryRule": "max_validity_365_days",
  "revocationProfile": "epoch_accumulator_v1",
  "recoveryProfile": "credential_replacement_v1",
  "governanceArtifactRef": "governance_artifact_..."
}
```

### Allowed `credentialClass`
- `civic_participation`
- `affected_party`
- `public_role`
- `bootstrap`

`privateAttributes` are never disclosed directly. `selectivelyDisclosedAttributes` are disclosed only when required by an action policy (`ZK_CIVIC_CREDENTIALS.md` §10).

**Cross-references:**
- Referenced by `IssuerQuorumPolicy` (§27.3) through the issuance flow.
- Referenced by `CivicActionVerificationReceipt` (§27.4) as `credentialPolicyId`.
- The `cryptographicProfileId` must refer to a registered, audited cryptographic profile.

## 27.3 IssuerQuorumPolicy

Defines the issuer set, threshold, key governance, and compromise procedures for blind threshold credential issuance (`ZK_CIVIC_CREDENTIALS.md` §6.4, §18.3).

```json
{
  "policyId": "issuer_quorum_policy_...",
  "schemaVersion": "1.0",
  "quorumId": "issuer_quorum_main_v1",
  "issuerSet": [
    {"issuerId": "issuer_a", "governanceRef": "issuer_gov_a"},
    {"issuerId": "issuer_b", "governanceRef": "issuer_gov_b"},
    {"issuerId": "issuer_c", "governanceRef": "issuer_gov_c"}
  ],
  "threshold": 2,
  "keyGenerationProfile": "distributed_key_gen_v1",
  "keyRotationPolicy": "annual_rotation_with_governance_approval",
  "issuerReplacementPolicy": "supermajority_of_remaining_issuers",
  "compromisePolicy": "emergency_suspension_and_rekey",
  "auditPolicy": "quarterly_key_ceremony_audit",
  "validFrom": "2026-04-06T00:00:00Z",
  "validUntil": "2027-04-06T00:00:00Z",
  "governanceArtifactRef": "governance_artifact_..."
}
```

### Issuer quorum invariants

- `threshold` must be at least 2 and strictly less than the size of `issuerSet` (`ZK_CIVIC_CREDENTIALS.md` §3.5).
- No single issuer may control credential issuance unilaterally.
- Each issuer must be independently governed; overlapping governance is a capture risk.
- All key ceremonies and replacements must be publicly auditable.

**Cross-references:**
- Referenced by `CivicActionVerificationReceipt` (§27.4) as `issuerQuorumPolicyId`.
- Referenced by `CredentialLifecycleEvent` (§27.5) as `issuerQuorumId`.

## 27.4 CivicActionVerificationReceipt

The cryptographic verification component of a `CivicReceipt` (`ZK_CIVIC_CREDENTIALS.md` §6.6, §13, §18.4). Records the outcome of a civic action proof verification without revealing the participant's identity or private credential.

```json
{
  "receiptId": "verification_receipt_...",
  "schemaVersion": "1.0",
  "protocolVersion": "zk_civic_v1",
  "civicScope": "ctx_...",
  "loopId": "proposal_...",
  "actionType": "vote",
  "epoch": "fwe_...",
  "participationPolicyId": "participation_policy_...",
  "standingPolicyId": "standing_policy_...",
  "credentialPolicyId": "credential_policy_...",
  "issuerQuorumPolicyId": "issuer_quorum_policy_...",
  "cryptographicProfileId": "crypto_profile_v1",
  "canonicalArtifactHash": "sha256:...",
  "nullifier": "nullifier_...",
  "verificationOutcome": "ACCEPTED",
  "rejectionReason": null,
  "revocationCommitmentRef": null,
  "ingressIdentifier": "ingress_service_a",
  "verificationTimestamp": "2026-04-07T15:00:00Z",
  "challengeRef": null
}
```

### Allowed `verificationOutcome`
- `ACCEPTED`
- `REJECTED_INVALID_PROOF`
- `REJECTED_DUPLICATE_NULLIFIER`
- `REJECTED_EXPIRED_CREDENTIAL`
- `REJECTED_REVOKED_CREDENTIAL`
- `REJECTED_WRONG_SCOPE`
- `REJECTED_WRONG_ACTION_TYPE`
- `REJECTED_POLICY_MISMATCH`
- `REJECTED_EPOCH_MISMATCH`
- `REJECTED_ARTIFACT_HASH_MISMATCH`
- `REJECTED_UNSUPPORTED_CRYPTOGRAPHIC_PROFILE`

### Optional fields
- `nullifier`: absent when the action type does not require uniqueness.
- `rejectionReason`: required when `verificationOutcome` is not `ACCEPTED`.
- `revocationCommitmentRef`: present when the verification checked a revocation accumulator commitment.
- `challengeRef`: present when the receipt is associated with an active challenge.

### Verification receipt invariants

- The receipt must never contain legal identity, the holder secret, the complete credential, or the standing basis (`ZK_CIVIC_CREDENTIALS.md` §13).
- A rejected action should still produce an accountable rejection receipt where doing so does not create a privacy or denial-of-service vulnerability.
- `nullifier` may appear in only one accepted receipt per nullifier domain (`ZK_CIVIC_CREDENTIALS.md` §19).

**Cross-references:**
- Included as the `verification` component of `CivicReceipt` (§26.11).
- Aggregated by `ParticipationAudit` (§26.13) for uniqueness and participation analysis.

## 27.5 CredentialLifecycleEvent

Accountable infrastructure record for credential lifecycle transitions (`ZK_CIVIC_CREDENTIALS.md` §7, §18.5). Must never expose citizen identity.

```json
{
  "eventId": "credential_event_...",
  "schemaVersion": "1.0",
  "credentialClass": "civic_participation",
  "lifecycleState": "CREDENTIAL_ACTIVE",
  "policyId": "credential_policy_...",
  "issuerQuorumId": "issuer_quorum_main_v1",
  "epoch": "fwe_...",
  "publicCommitment": null,
  "reasonCode": null,
  "timestamp": "2026-04-06T14:00:00Z",
  "governanceRef": null
}
```

### Allowed `lifecycleState`
- `ISSUANCE_REQUESTED`
- `STANDING_VERIFIED`
- `ISSUANCE_AUTHORIZED`
- `PARTIAL_CREDENTIALS_ISSUED`
- `CREDENTIAL_ACTIVE`
- `CREDENTIAL_SUSPENDED`
- `CREDENTIAL_EXPIRED`
- `CREDENTIAL_REVOKED`
- `CREDENTIAL_REPLACED`

### Optional fields
- `publicCommitment`: present for `ISSUANCE_REQUESTED` (holder commitment) and `ISSUANCE_AUTHORIZED` (authorization commitment).
- `reasonCode`: required for `CREDENTIAL_SUSPENDED`, `CREDENTIAL_REVOKED`, and `CREDENTIAL_REPLACED`.
- `governanceRef`: present when the event is linked to a governance action (e.g., policy change, appeal resolution).

### Lifecycle event invariants

- A lifecycle event must not contain a stable holder identifier unless an implementation profile proves that the identifier cannot be used to correlate civic actions (`ZK_CIVIC_CREDENTIALS.md` §18.5).
- `STANDING_VERIFIED` is a private protocol state; its event must not become a public identity record (`ZK_CIVIC_CREDENTIALS.md` §7.2).
- Lifecycle states are credential subsystem states, not proposal lifecycle states (`ZK_CIVIC_CREDENTIALS.md` §7).
- An event with `lifecycleState: CREDENTIAL_REVOKED` must have a `reasonCode` and `governanceRef`.

**Cross-references:**
- `policyId` refers to a `CivicCredentialPolicy` (§27.2).
- Referenced by `ParticipationAudit` (§26.13) during revocation and expiry evaluation.
- Lifecycle states complement, but are separate from, proposal state machine states.

---

## 28. Closing Principle

A civic runtime becomes real when its important actions become explicit objects with explicit constraints.

Good schemas do not guarantee good governance.
But bad or missing schemas guarantee hidden drift.

The point of this document is therefore simple:
if the system matters, its objects must be named,
its fields must be bounded,
and its state must be reconstructable.
