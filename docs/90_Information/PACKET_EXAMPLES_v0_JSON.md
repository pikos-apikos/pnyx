# PACKET_EXAMPLES_v0_JSON

> **Note:** This document has been updated to reflect the normative rules in [SYSTEM_PATCH_v2](../99_Reference/SYSTEM_PATCH_v2.md).

## 1. Purpose

This document provides implementation-facing JSON examples for the new schema objects mandated by `SYSTEM_PATCH_v2.md`.

These examples demonstrate the machine-usable structure of the evidence, executor, and routing layers.

---

## 2. ExecutorRecord

```json
{
  "executor_id": "exec_ai_policy_v4",
  "executor_type": "ai_model",
  "status": "active",
  "admitted_at": "2026-01-15T00:00:00Z",
  "capabilities": ["policy_analysis", "evidence_extraction"],
  "contextual_ranking": "A",
  "revalidation_status": "valid"
}
```

---

## 3. SourceRecord

```json
{
  "source_id": "src_001",
  "source_type": "official_document",
  "title": "Municipal Traffic Data Report Q4",
  "retrieved_at": "2026-04-05T10:00:00Z",
  "verification_status": "verified",
  "url": "https://city.gov/data/traffic_q4_2025.pdf"
}
```

---

## 4. MaterialClaim

```json
{
  "claim_id": "claim_001",
  "statement": "Average vehicle speed exceeds the posted limit by 15 mph during school hours.",
  "source_links": ["src_001"],
  "verification_status": "supported",
  "contested": false
}
```

---

## 5. ConfidenceRecord

```json
{
  "confidence_id": "conf_001",
  "target_packet_id": "packet_001",
  "evidence_coverage_score": 80,
  "source_quality_score": 90,
  "contradiction_penalty": 10,
  "derived_score": 80,
  "confidence_band": "high"
}
```

---

## 6. ReviewRoutingSignal

```json
{
  "signal_id": "route_sig_001",
  "target_packet_id": "packet_001",
  "signal_type": "human_review_recommended",
  "trigger_reason": "high_rights_impact",
  "urgency": "normal"
}
```

---

## 7. EvidencePacket

```json
{
  "packet_id": "packet_ev_001",
  "packet_type": "evidence_packet",
  "executor_id": "exec_ai_policy_v4",
  "jurisdiction": "Local Municipal",
  "source_register": ["src_001"],
  "material_claims": ["claim_001"],
  "unknowns": ["Impact on delivery access"],
  "confidence_record_id": "conf_001",
  "routing_signals": ["route_sig_001"]
}
```

---

## 8. SandboxEvaluationRecord

```json
{
  "evaluation_id": "eval_sb_001",
  "executor_id": "exec_ai_experimental_v1",
  "evaluation_date": "2026-03-20T00:00:00Z",
  "benchmark_suite": "civic_reasoning_v2",
  "score": 65,
  "admission_decision": "rejected",
  "notes": "Failed contradiction handling tests."
}
```

---

## 9. RevalidationRecord

```json
{
  "revalidation_id": "reval_001",
  "executor_id": "exec_ai_policy_v4",
  "check_date": "2026-04-01T00:00:00Z",
  "drift_detected": false,
  "action_taken": "renewed",
  "next_check_date": "2026-05-01T00:00:00Z"
}
```

---

## 10. HumanExpertRecord

```json
{
  "expert_id": "hex_992",
  "name": "Jane Doe",
  "credentials": ["Registered Traffic Engineer"],
  "conflict_of_interest_disclosure": "None",
  "status": "active",
  "admitted_at": "2025-11-10T00:00:00Z"
}
```

---

## 11. SynthesisConflictMap

```json
{
  "map_id": "sync_map_001",
  "target_proposal_id": "prop_001",
  "conflicts": [
    {
      "issue": "Emergency Response Delay",
      "claim_a": "claim_002",
      "claim_b": "claim_005",
      "resolution_status": "unresolved",
      "impact": "high"
    }
  ]
}
```