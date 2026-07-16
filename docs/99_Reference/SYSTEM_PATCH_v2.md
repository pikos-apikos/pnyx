# SYSTEM_PATCH_v2

**Status:** Normative patch  
**Scope:** System-wide  
**Applies to:** Protocol, architecture, governance, schemas, evaluation, inclusion, human execution, evidence discipline  
**Supersedes assumptions:** Any prior reading that treats AI models as default civic reasoners, conflates skills with models, or treats confidence as a self-reported signal  
**Related patch set:** `SYSTEM_PATCH_v1.md`, `EXECUTOR_MODEL.md`, `EVIDENCE_PACKET.md`, `CONFIDENCE_AND_SCORING.md`, `EXECUTOR_RANKING.md`, `MODEL_INCLUSION_SANDBOX.md`, `REVALIDATION_POLICY.md`, `HUMAN_EXPERT_PROTOCOL.md`, `CITATION_AND_SOURCING_POLICY.md`

---

## 1. Purpose

This patch updates the system from an interpretation that can be read as **AI-centric, model-trusting, and output-oriented** into a system that is explicitly **protocol-governed, evidence-backed, executor-agnostic, and continuously reviewable**.

This document has two jobs:

1. to define the new canonical reading of the system, and  
2. to specify **which existing files must be patched** so the new reading propagates through the corpus without contradiction.

This patch should be treated as a **normative override plus migration map**.

---

## 2. Canonical System Reading

The system must now be read as follows:

> Pnyx is not AI-governed.  
> Pnyx is protocol-governed, evidence-backed, executor-agnostic, and challengeable.

This means:

- analysis is not legitimate because an AI produced it,
- expertise is not legitimate because a human asserted it,
- recommendations are not legitimate unless they are evidence-backed and auditable,
- confidence is not legitimate unless it is procedurally derived,
- executors are not trusted by identity alone,
- civic legitimacy depends on bounded mandates, admissible evidence, structured disagreement, and revisable trust.

---

## 3. Core System Shifts

### 3.1 From model-centric to executor-agnostic
The system must no longer treat a skill as if it were inherently bound to a model or model family.

A skill is a protocol-defined analytic role.  
An executor is the actor or system that performs it.

Executors may include:

- AI models,
- human experts,
- human panels,
- hybrid AI-human workflows,
- replicated or adversarial review configurations.

### 3.2 From opinion outputs to evidence packets
The system must no longer treat summaries, memos, fluent answers, or expert opinions as sufficient outputs for non-trivial civic analysis.

Non-trivial outputs must be expressed as structured, auditable, challengeable artifacts grounded in evidence.

### 3.3 From self-confidence to derived confidence
The system must no longer treat confidence as a free-form statement by an executor.

Confidence must be derived from explicit signals such as:

- evidence coverage,
- source quality,
- jurisdiction fit,
- freshness,
- claim traceability,
- contradiction handling,
- unknowns disclosure,
- schema completeness,
- replication signals where relevant.

### 3.4 From static trust to continuous trust maintenance
Admission is not permanent trust.

Executors must be:

- sandboxed before live use,
- ranked in context,
- revalidated over time,
- downgraded or suspended when performance drifts,
- re-admitted only through explicit re-entry paths.

### 3.5 From informal human fallback to formal human execution
Human experts must not appear as extra-procedural overrides when trust in AI is low.

They must operate as formal executors under mandate, evidence, conflict, audit, and review rules.

### 3.6 From hidden analysis to public traceability
The public must be able to inspect the basis of serious civic analysis.

Not every internal implementation detail must be public, but the civic basis must remain visible enough for challenge, scrutiny, and accountability.

---

## 4. Canonical Principles Introduced or Reinforced

### 4.1 No analysis without evidence
No non-trivial skill output is complete unless it is grounded in structured evidence.

### 4.2 No recommendation without citations
A recommendation must identify its supporting basis, opposing evidence where relevant, constraints, unknowns, and review implications.

### 4.3 Skill is a role, not a model
Skills define what analysis is required. Executors are replaceable implementations of that role.

### 4.4 Executor trust is earned, scoped, and revisable
Executors do not receive civic authority by availability, branding, or prestige.

### 4.5 Human sovereignty includes epistemic fallback and challenge
Human sovereignty includes not only final civic judgment, but also the system’s right to escalate analysis toward human review where machine outputs are weak, conflicted, or high-risk.

### 4.6 Structured disagreement remains mandatory
Dissent, adversarial critique, source conflict, and minority reasoning remain core anti-capture and anti-monoculture safeguards.

### 4.7 Ranking is contextual
There is no universally strong executor. Performance must be remembered in scope.

### 4.8 Admission precedes authority
Executors may participate only after sandbox-based evaluation and scoped admission.

### 4.9 Revalidation is continuous
Trust decays unless maintained through ongoing evidence of responsible performance.

### 4.10 Citation discipline is part of civic legitimacy
Sources and claims must remain linked in ways that can be challenged and audited.

---

## 5. Canonical Consequences for the Civic Loop

The Civic Loop remains intact as the mandatory procedural path. What changes is the **analytic substrate** that powers it.

### 5.1 Submission and intake
Submission still transforms raw demand into bounded proposals.
But downstream analysis must now be routed through executor-aware and evidence-aware rules.

### 5.2 Classification
Classification must now determine not only:

- governance layer,
- urgency,
- triviality,
- thresholds,
- timing,

but also:

- minimum evidence burden,
- required executor type,
- mandatory human review cases,
- replication requirements,
- admissible review paths,
- readiness blockers tied to evidence quality.

### 5.3 Panel specification and lock
A panel lock must now specify not only which skills are required, but also:

- which executor type may perform each skill,
- admissibility requirements,
- independence requirements,
- conflict-of-interest requirements,
- fallback rules,
- escalation rules,
- evidence classes or burden where relevant.

### 5.4 Skill execution
A skill is not complete when it produces an opinion.  
A skill is complete only when it produces a valid evidence-backed artifact under schema.

### 5.5 Synthesis
Synthesis must preserve:

- strongest case in favor,
- strongest case against,
- minority reasoning,
- unresolved factual disputes,
- unresolved legal ambiguities,
- evidence gaps,
- confidence dispersion,
- source conflict,
- capture-risk notes.

### 5.6 Public briefing
The briefing packet must expose the source basis of the analysis sufficiently for meaningful public inspection.

### 5.7 Readiness
Readiness may not be computed merely because a deliberation window closed.

Readiness must include:

- valid panel completion,
- evidentiary sufficiency,
- mandatory human review where required,
- review-trigger handling,
- blocker classification,
- unresolved conflict handling under protocol rules.

### 5.8 Post-decision review
Post-decision review may now be triggered not only by execution failure or constitutional challenge, but also by:

- source invalidation,
- legal change,
- executor defect,
- severe miscalibration,
- jurisdiction mismatch,
- omitted evidence,
- conflict-of-interest discovery.

---

## 6. New or Revised Normative Objects

This patch establishes the following objects as core to the system.

### 6.1 Skill
A protocol-defined analytic function.

### 6.2 Executor
The actor or system authorized to perform a skill.

### 6.3 Evidence Packet
The canonical output unit for non-trivial civic analysis.

### 6.4 Confidence Record
The structured derivation of evidence-based confidence.

### 6.5 Contextual Ranking Record
A performance-memory object that evaluates executor fitness in scope.

### 6.6 Sandbox Evaluation Record
The admission artifact for dry-run and shadow-mode evaluation.

### 6.7 Revalidation Record
The artifact that tracks continuing eligibility, drift, scope change, downgrade, suspension, and re-entry.

### 6.8 Human Expert Protocol Record
The structured registration and governance object for human experts and panels.

### 6.9 Citation and Sourcing Record
The traceability layer linking sources to claims and review outcomes.

---

## 7. Patch Set Introduced by This Reform

The following documents define the new normative layer:

- `SYSTEM_PATCH_v1.md`
- `EXECUTOR_MODEL.md`
- `EVIDENCE_PACKET.md`
- `CONFIDENCE_AND_SCORING.md`
- `EXECUTOR_RANKING.md`
- `MODEL_INCLUSION_SANDBOX.md`
- `REVALIDATION_POLICY.md`
- `HUMAN_EXPERT_PROTOCOL.md`
- `CITATION_AND_SOURCING_POLICY.md`

This `SYSTEM_PATCH_v2.md` does not replace those documents. It integrates them into a single migration view.

---

## 8. Required Patch Map by Existing File

This section defines **which files should be patched** and **what must change in each**.

---

## 9. `PROTOCOL.md` Patch Requirements

### 9.1 Purpose of patch
`PROTOCOL.md` must be updated so the Civic Loop and its invariants are explicitly compatible with:

- executor-aware routing,
- evidence-backed outputs,
- derived confidence,
- human review,
- sandboxed admission,
- contextual ranking,
- revalidation-aware trust.

### 9.2 Required changes
`PROTOCOL.md` should be patched to:

1. redefine skill execution as production of a valid evidence-backed artifact rather than a free-form expert output,  
2. clarify that panel specification binds both **skills and executors**,  
3. state that classification affects **executor type, evidence burden, and review routing**,  
4. define readiness as including **evidentiary sufficiency**,  
5. define mandatory review triggers for **low confidence, high conflict, high rights impact, constitutional cases, and emergency justification**,  
6. require synthesis to preserve **disagreement, evidence gaps, and confidence dispersion**,  
7. require public briefing to expose the **claim/source basis** of analysis,  
8. allow post-decision review re-entry for **source invalidation, legal change, miscalibration, jurisdiction mismatch, and executor defect**,  
9. clarify that **elevated governance or emergency status never bypasses audit and evidence discipline**,  
10. clarify that **human experts are protocol-bound executors** rather than extra-procedural exceptions.

### 9.3 Related normative references
`PROTOCOL.md` should explicitly align with:

- `EXECUTOR_MODEL.md`
- `EVIDENCE_PACKET.md`
- `CONFIDENCE_AND_SCORING.md`
- `HUMAN_EXPERT_PROTOCOL.md`
- `CITATION_AND_SOURCING_POLICY.md`

---

## 10. `ARCHITECTURE.md` Patch Requirements

### 10.1 Purpose of patch
`ARCHITECTURE.md` must be updated so the technical system supports the new governance logic rather than assuming “skill = model call.”

### 10.2 Required changes
`ARCHITECTURE.md` should be patched to:

1. introduce an explicit **executor abstraction layer**,  
2. support **multiple executor classes** including AI, human, panel, hybrid, and replicated modes,  
3. include an **evidence retrieval and citation pipeline**,  
4. include **schema-validated Evidence Packet generation**,  
5. include a **confidence derivation engine** rather than self-reported confidence fields,  
6. include **contextual ranking storage and lookup**,  
7. include **sandbox and shadow-mode execution flows**,  
8. include **revalidation, drift detection, downgrade, and suspension logic**,  
9. include **human workflow support** for review, verification, and appeal,  
10. include **claim-level traceability and audit preservation**,  
11. include **routing components** that can trigger human review, replication, or blocking based on confidence and risk,  
12. avoid any architectural language that assumes a skill is inherently a single model invocation.

### 10.3 Related normative references
`ARCHITECTURE.md` should explicitly align with:

- `EXECUTOR_MODEL.md`
- `EVIDENCE_PACKET.md`
- `CONFIDENCE_AND_SCORING.md`
- `EXECUTOR_RANKING.md`
- `MODEL_INCLUSION_SANDBOX.md`
- `REVALIDATION_POLICY.md`

---

## 11. `GOVERNANCE.md` Patch Requirements

### 11.1 Purpose of patch
`GOVERNANCE.md` must be updated so governance covers not only proposals and outcomes, but also the **analytic layer itself**.

### 11.2 Required changes
`GOVERNANCE.md` should be patched to:

1. define who may **admit executors** and under what standards,  
2. define who may **challenge executor inclusion or continued participation**,  
3. define governance over **ranking logic and ranking audits**,  
4. define governance over **benchmark suites and sandbox curation**,  
5. define governance over **human expert pool formation and conflict rules**,  
6. define governance over **revalidation thresholds, downgrade logic, and suspension authority**,  
7. define how **version changes** are governed,  
8. define how **analytic capture risks** are detected and handled,  
9. define appeals that reach not only the decision but also the **analytic basis** of the decision,  
10. define prospective-only effects for ranking and revalidation changes, subject to explicit emergency exceptions.

### 11.3 Related normative references
`GOVERNANCE.md` should explicitly align with:

- `EXECUTOR_MODEL.md`
- `EXECUTOR_RANKING.md`
- `MODEL_INCLUSION_SANDBOX.md`
- `REVALIDATION_POLICY.md`
- `HUMAN_EXPERT_PROTOCOL.md`

---

## 12. `SCHEMAS.md` Patch Requirements

### 12.1 Purpose of patch
`SCHEMAS.md` must be updated so the new system is not only conceptually defined, but machine-usable.

### 12.2 Required changes
`SCHEMAS.md` should be patched to add or revise schemas for at least:

1. `ExecutorRecord`,  
2. `EvidencePacket`,  
3. `MaterialClaim`,  
4. `SourceRecord`,  
5. `ConfidenceRecord`,  
6. `RankingRecord`,  
7. `SandboxEvaluationRecord`,  
8. `RevalidationRecord`,  
9. `HumanExpertRecord`,  
10. `ReviewRoutingSignal`,  
11. `ConflictOfInterestDisclosure`,  
12. `PublicBriefingView`,  
13. `SynthesisConflictMap`,  
14. `ChallengeEvent`,  
15. `AuditTrace` enriched with claim/source links.

### 12.3 Additional schema requirements
`SCHEMAS.md` should also:

- support versioning for scoring and ranking logic,
- support claim-to-source traceability,
- support jurisdiction layers,
- support independence and conflict metadata,
- support downgrade, suspension, and re-entry states,
- support public/private visibility fields where necessary.

### 12.4 Related normative references
`SCHEMAS.md` should explicitly align with all new normative docs, especially:

- `EVIDENCE_PACKET.md`
- `CONFIDENCE_AND_SCORING.md`
- `EXECUTOR_RANKING.md`
- `MODEL_INCLUSION_SANDBOX.md`
- `REVALIDATION_POLICY.md`
- `HUMAN_EXPERT_PROTOCOL.md`
- `CITATION_AND_SOURCING_POLICY.md`

---

## 13. `AI_EPISTEMIC_RISK.md` Patch Requirements

### 13.1 Purpose of patch
`AI_EPISTEMIC_RISK.md` must be updated so the system’s epistemic-risk discussion no longer focuses only on plural models or synthetic disagreement, but on the full executor layer.

### 13.2 Required changes
`AI_EPISTEMIC_RISK.md` should be patched to:

1. distinguish **model diversity** from genuine **executor independence**,  
2. treat false plurality as including shared retrieval, shared providers, shared policy layers, and shared training lineage,  
3. incorporate risks of **fabricated citations, source laundering, and polished false confidence**,  
4. incorporate **human epistemic risks** such as prestige capture, institutional bias, hidden conflict, and forced consensus,  
5. define evidence-backed disagreement as safer than stylistic plurality,  
6. recognize ranking and revalidation as anti-epistemic-fragility mechanisms,  
7. recognize that synthesis itself can become an epistemic distortion layer if it averages away real conflict.

### 13.3 Related normative references
`AI_EPISTEMIC_RISK.md` should explicitly align with:

- `CONFIDENCE_AND_SCORING.md`
- `EXECUTOR_RANKING.md`
- `HUMAN_EXPERT_PROTOCOL.md`
- `CITATION_AND_SOURCING_POLICY.md`

---

## 14. `TASK_SUITE_v0.md` Patch Requirements

### 14.1 Purpose of patch
`TASK_SUITE_v0.md` must be updated so tasks are no longer framed only as model prompts, but as full civic evaluation tasks tied to skills, evidence, and routing outcomes.

### 14.2 Required changes
`TASK_SUITE_v0.md` should be patched to:

1. define tasks in terms of **skill mandates** and expected output schema,  
2. include **jurisdiction-sensitive tasks**,  
3. include **citation and claim-traceability requirements**,  
4. include **confidence scoring expectations**,  
5. include **adversarial cases**,  
6. include **human-review-triggering cases**,  
7. include **synthesis preservation tasks** where disagreement must remain visible,  
8. include **benchmark tasks for ranking and revalidation**,  
9. include **shadow-mode comparable cases**,  
10. ensure tasks evaluate protocol usefulness, not only answer quality.

### 14.3 Related normative references
`TASK_SUITE_v0.md` should explicitly align with:

- `EVIDENCE_PACKET.md`
- `CONFIDENCE_AND_SCORING.md`
- `MODEL_INCLUSION_SANDBOX.md`
- `EXECUTOR_RANKING.md`

---

## 15. `AGENTS.md` or Skill/Agent Layer Patch Requirements

### 15.1 Purpose of patch
Any file that currently treats analytic roles as model-bound agents must be patched so the system no longer conflates skills, agents, and models.

### 15.2 Required changes
Such files should be patched to:

1. redefine agents as **possible implementations of skills**, not the canonical definition of skills themselves,  
2. distinguish **skill contracts** from **executor implementations**,  
3. remove any assumption that legitimacy flows from agent plurality alone,  
4. require that agent outputs be schema-compatible and evidence-backed,  
5. include human and hybrid implementations in the same conceptual space,  
6. route confidence through derived scoring rather than agent self-report.

### 15.3 Related normative references
These files should explicitly align with:

- `EXECUTOR_MODEL.md`
- `EVIDENCE_PACKET.md`
- `CONFIDENCE_AND_SCORING.md`
- `HUMAN_EXPERT_PROTOCOL.md`

---

## 16. `READ_MODELS` or Read-Model Layer Patch Requirements

### 16.1 Purpose of patch
Any read-model layer should be updated so it exposes not only proposal status, but also the state of evidence, review, confidence, and executor routing.

### 16.2 Required changes
Relevant read-model files should be patched to expose views such as:

1. executor assignments by skill,  
2. evidence sufficiency state,  
3. confidence bands and review triggers,  
4. public claim/source visibility,  
5. challenge and escalation state,  
6. revalidation-sensitive notes where relevant to trust.

### 16.3 Related normative references
These files should align with:

- `EVIDENCE_PACKET.md`
- `CONFIDENCE_AND_SCORING.md`
- `EXECUTOR_RANKING.md`

---

## 17. `EVENT_MODEL.md` Patch Requirements

### 17.1 Purpose of patch
`EVENT_MODEL.md` should reflect that the executor layer, evidence layer, and revalidation layer produce governance-relevant events.

### 17.2 Required changes
`EVENT_MODEL.md` should be patched to include event types such as:

- executor admitted,
- executor scoped,
- executor downgraded,
- executor suspended,
- executor revalidated,
- evidence packet published,
- citation validation failed,
- confidence cap triggered,
- human review required,
- replication triggered,
- source invalidated,
- conflict-of-interest disclosed,
- challenge filed against analytic basis.

### 17.3 Related normative references
`EVENT_MODEL.md` should align with:

- `EXECUTOR_MODEL.md`
- `EVIDENCE_PACKET.md`
- `CONFIDENCE_AND_SCORING.md`
- `REVALIDATION_POLICY.md`

---

## 18. `PACKET_EXAMPLES.md` and Packet Example Files Patch Requirements

### 18.1 Purpose of patch
Example packet files must demonstrate the new structure rather than old-style prose summaries.

### 18.2 Required changes
These files should be patched to include examples of:

1. Evidence Packets with explicit source register,  
2. material claims with claim-to-source links,  
3. jurisdiction declaration,  
4. unknowns and contested points,  
5. confidence components and derived confidence,  
6. review-routing signals,  
7. human-expert packet examples,  
8. adversarial or dissent-preserving synthesis examples.

### 18.3 Related normative references
These files should align with:

- `EVIDENCE_PACKET.md`
- `CONFIDENCE_AND_SCORING.md`
- `HUMAN_EXPERT_PROTOCOL.md`
- `CITATION_AND_SOURCING_POLICY.md`

---

## 19. `PACKET_EXAMPLES_v0_JSON.md` Patch Requirements

### 19.1 Purpose of patch
JSON examples must reflect the new machine-usable schema layer.

### 19.2 Required changes
This file should be patched to include example JSON for:

- `ExecutorRecord`,
- `EvidencePacket`,
- `MaterialClaim`,
- `SourceRecord`,
- `ConfidenceRecord`,
- `ReviewRoutingSignal`,
- `SandboxEvaluationRecord`,
- `RevalidationRecord`,
- `HumanExpertRecord`,
- `SynthesisConflictMap`.

### 19.3 Related normative references
This file should align with:

- `SCHEMAS.md`
- `EVIDENCE_PACKET.md`
- `CONFIDENCE_AND_SCORING.md`

---

## 20. `PROTOTYPE_PLAN.md` Patch Requirements

### 20.1 Purpose of patch
The prototype plan must reflect the new minimal viable structure.

### 20.2 Required changes
`PROTOTYPE_PLAN.md` should be patched to prioritize:

1. executor abstraction,  
2. Evidence Packet schema,  
3. citation and source handling,  
4. derived confidence,  
5. routing based on confidence and risk,  
6. minimal ranking store,  
7. basic sandbox flow,  
8. human-review integration,  
9. audit trail with claim/source traceability.

### 20.3 Related normative references
`PROTOTYPE_PLAN.md` should align with the entire new patch set, especially:

- `EXECUTOR_MODEL.md`
- `EVIDENCE_PACKET.md`
- `CONFIDENCE_AND_SCORING.md`

---

## 21. `PROTOTYPE_PROFILE.md` Patch Requirements

### 21.1 Purpose of patch
The prototype profile should reflect the first feasible version of the patched system.

### 21.2 Required changes
`PROTOTYPE_PROFILE.md` should specify which parts of the full patch are present in early form, such as:

- simplified executor classes,
- reduced Evidence Packet schema,
- limited source validation,
- banded confidence instead of full scoring sophistication,
- manual ranking bootstrap,
- basic sandbox suite,
- mandatory human review only for selected case classes.

This file should make clear what remains intentionally incomplete.

---

## 22. `BOOTSTRAP_REALITY_CHECK.md` Patch Requirements

### 22.1 Purpose of patch
This file should be updated to reflect the real-world difficulty of running the patched system.

### 22.2 Required changes
It should now include:

1. human expert scarcity,  
2. evidence retrieval complexity,  
3. jurisdiction mapping cost,  
4. source validation overhead,  
5. ranking cold-start problems,  
6. benchmark curation burden,  
7. revalidation governance burden,  
8. tradeoffs between legitimacy and throughput.

This file should not pretend the patch is free.

---

## 23. `COMMUNITY_FORMATION.md` Patch Requirements

### 23.1 Purpose of patch
This file should be updated because the community is no longer only forming around proposals, but also around the executor and evidence layer.

### 23.2 Required changes
It should include:

- how citizens challenge analytic basis,
- how human expert pools are formed and rotated,
- how benchmark suites may be community-audited,
- how sourcing norms become part of civic culture,
- how legitimacy depends on public inspectability rather than hidden expert trust.

---

## 24. `AI_EPISTEMIC_RISK.md`, `SHUTDOWN_AND_DISSOLUTION`, and Safety/Governance Edge Files

Wherever the system discusses failure, shutdown, or dissolution, those files should be patched to recognize that the analytic layer itself can become a failure point through:

- concentrated executor capture,
- ranking corruption,
- benchmark corruption,
- evidence corruption,
- mass miscalibration,
- collapse of trusted human pools,
- inability to validate current law.

---

## 25. New Files Required by This Patch

In addition to patching existing files, the system now requires the following normative files:

- `EXECUTOR_MODEL.md`
- `EVIDENCE_PACKET.md`
- `CONFIDENCE_AND_SCORING.md`
- `EXECUTOR_RANKING.md`
- `MODEL_INCLUSION_SANDBOX.md`
- `REVALIDATION_POLICY.md`
- `HUMAN_EXPERT_PROTOCOL.md`
- `CITATION_AND_SOURCING_POLICY.md`

These are no longer optional elaborations. They are part of the canonical system reading.

---

## 26. Migration Order

To propagate this patch cleanly, the preferred order is:

### Phase A: Canonical patch layer
1. `SYSTEM_PATCH_v2.md`
2. all new normative files already created in this patch set

### Phase B: Core constitutional files
3. `PROTOCOL.md`
4. `ARCHITECTURE.md`
5. `GOVERNANCE.md`
6. `SCHEMAS.md`

### Phase C: Risk, testing, and examples
7. `AI_EPISTEMIC_RISK.md`
8. `TASK_SUITE_v0.md`
9. `EVENT_MODEL.md`
10. `PACKET_EXAMPLES.md`
11. `PACKET_EXAMPLES_v0_JSON.md`

### Phase D: Prototype and social-operational files
12. `PROTOTYPE_PLAN.md`
13. `PROTOTYPE_PROFILE.md`
14. `BOOTSTRAP_REALITY_CHECK.md`
15. `COMMUNITY_FORMATION.md`
16. related edge files

---

## 27. Canonical Short Summary

This patch establishes the following reading:

1. Civic analysis is not legitimate because AI produced it.  
2. Skills are protocol roles, not model identities.  
3. Executors may be AI, human, panel, hybrid, or replicated.  
4. Non-trivial outputs must be evidence-backed.  
5. Material claims must be source-traceable.  
6. Confidence must be derived from evidence and process signals.  
7. Executor trust must be contextual, ranked, and continuously revalidated.  
8. Admission must pass through sandbox evaluation and scoped production entry.  
9. Human experts are formal, auditable executors.  
10. Structured disagreement remains mandatory and visible.

---

## 28. Closing Principle

> The system should govern analysis the way it seeks to govern public power:  
> through explicit roles, admissible evidence, bounded authority, structured dissent, auditable conduct, and revisable trust. 
