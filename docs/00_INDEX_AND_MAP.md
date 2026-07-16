# PNyx Documentation Index and Map

**System Version:** 2.3 (Post-Core v0.3 Reconciliation — Participation Layer)  
**Last Updated:** July 2026  
**Total Documents:** 60  

---

## Reading Order

This documentation follows a logical dependency chain. Read in numerical order (10 → 20 → 30 → ...) to understand how layers build on each other.

```
10_Constitutional → 20_Protocol_Core → 30_Classification → 40_Identity
       ↓                    ↓              ↓                  ↓
45_Participation → 50_Economics → 60_Skills → 70_Bootstrap → 80_Runtime → 90_Information
       ↓                                                  ↓
95_Emergency ←──────────────────────────────────────────┘
       ↓
99_Reference (optional: analysis & critique)
```

---

## Quick Navigation by Purpose

### 🎯 I want to understand the philosophy
→ Start with `10_Constitutional/GOVERNANCE.md`  
→ Read `10_Constitutional/ARCHITECTURE.md`  
→ Deep dive: `50_Economics/POLITICAL_ECONOMY.md`

### 🎯 I want to build a pilot
→ Start with `70_Bootstrap/PROTOTYPE_PROFILE.md` (compact card)  
→ Full plan: `70_Bootstrap/PROTOTYPE_PLAN.md`  
→ Pilot template: `70_Bootstrap/PILOT_DOMAIN_TEMPLATE.md`  
→ Constitutional floor: `70_Bootstrap/MINIMUM_VIABLE_PNYX.md`  
→ Technical: `80_Runtime/` and `90_Information/`

### 🎯 I want to understand anti-capture
→ `10_Constitutional/THREAT_MODEL.md`  
→ `30_Classification/CLASSIFICATION.md`  
→ `95_Emergency/EMERGENCY_ENFORCEMENT.md`

### 🎯 I want to understand the skill system
→ `60_Skills/SKILLS.md` (contract)  
→ `60_Skills/SKILL_ECONOMICS.md` (funding model)  
→ `60_Skills/SKILL_EVALUATION.md` (quality assurance)  
→ `60_Skills/SKILL_REGISTRY.md` (admission)

### 🎯 I want to understand funding
→ `50_Economics/FUNDING_MODEL.md`  
→ `50_Economics/TREASURY.md`  
→ `50_Economics/POLITICAL_ECONOMY.md` (power analysis)

### 🎯 I want to understand participation
→ `45_Participation/PARTICIPATION_MODEL.md` (core model: modes, plans, audits, receipts)  
→ `45_Participation/SORTITION.md` (verifiable random selection)  
→ `45_Participation/ATTENTION_AND_REACH.md` (attention as public resource, delegation, briefs)  
→ `45_Participation/GOVERNANCE_HEALTH.md` (participation health metrics)  
→ `50_Economics/POLITICAL_ECONOMY.md` §7–8 (participation inequality theory)

---

## Layer 10: Constitutional

**What must always hold.** The bedrock principles that constrain all other layers.

| Document | Purpose | Key Concepts |
|----------|---------|--------------|
| **GOVERNANCE.md** | Sovereignty, legitimacy, civic reasoning | Human sovereignty, values layer, recursive governance, legitimacy sources |
| **ARCHITECTURE.md** | System design principles | Protocol, skill panels, civic packets, audit trails, anti-capture |
| **THREAT_MODEL.md** | Security and capture risks | Capture attempts as normal, procedural drift, anti-capture doctrine |

**Cross-references:** All other documents reference these.

---

## Layer 20: Protocol Core

**How proposals flow through the system.** The essential mechanics of civic reasoning.

| Document | Purpose | Key Concepts |
|----------|---------|--------------|
| **PROTOCOL.md** | Proposal lifecycle and state transitions | Intake → Classification → Panel → Briefing → Decision → Execution |
| **PANEL_SELECTION.md** | Skill panel assembly | Plurality requirement, role coverage, provider diversity, panel lock |
| **ROUTING.md** | Execution path selection | Market thread, state thread, hybrid, anti-capture routing, epoch binding |

**Cross-references:** Depends on 10_Constitutional. Referenced by 30_Classification, 70_Bootstrap.

---

## Layer 30: Classification

**Determining review intensity.** The critical choke point that shapes power distribution.

| Document | Purpose | Key Concepts |
|----------|---------|--------------|
| **CLASSIFICATION.md** | Proposal categorization | Conservative classification, ambiguity escalation, strongest-layer rule, counter-classification |

**Cross-references:** Central to the protocol. Referenced by 20_Protocol_Core, 70_Bootstrap.

**Why standalone:** Classification is the most dangerous leverage point. It deserves its own layer.

---

## Layer 40: Identity

**Who participates and how.** Privacy-preserving civic identity.

| Document | Purpose | Key Concepts |
|----------|---------|--------------|
| **IDENTITY_AND_MEMBERSHIP.md** | Civic participation eligibility | Membership types, privacy principles, separation of functions |
| **CRYPTOGRAPHIC_MODEL.md** | Privacy-preserving proofs | Zero-knowledge proofs, nullifiers, scope-binding, minimal disclosure |
| **ZK_CIVIC_CREDENTIALS.md** | Privacy-preserving civic credentials | Blind threshold issuance, scope-specific nullifiers, holder-bound credentials, verification receipts, credential lifecycle, deferred infrastructure |

**Cross-references:** Referenced by 80_Runtime, 90_Information.

**Note:** MVP defers advanced cryptography per MINIMUM_VIABLE_PNYX.md §5.1. ZK_CIVIC_CREDENTIALS.md is recorded as bootstrap debt (BOOTSTRAP_DEBT_REGISTER.md §2.7).

---

## Layer 45: Participation

**Who participates, how they are reached, and how participation quality is audited.** A first-class subsystem introduced by the Core v0.3 reconciliation.

**New Layer (v2.3):** Operationalizes the diagnosis in `POLITICAL_ECONOMY.md` §7–8 that open channels amplify the already-advantaged and that attention is a political resource.  

| Document | Purpose | Key Concepts |
|----------|---------|--------------|
| **PARTICIPATION_MODEL.md** | Core participation mechanics | Design stance, modes (open/affected-party/targeted/jury/institutional/monitoring), ParticipationPlan, compensated participation, civic translation, participant body formation, ParticipationAudit, CivicReceipt, compatibility tiers, failure signals |
| **SORTITION.md** | Verifiable random selection | Eligible-population registry as primary capture surface, registry change log, snapshot commitment, randomness requirements, stratification, SortitionConfiguration/Result artifacts, replacement and opt-out, privacy of selected participants, bootstrap constraints |
| **ATTENTION_AND_REACH.md** | Attention governance | Eight-function separation (submission ≠ reach), AttentionAllocationPolicy, ReachDecision artifacts, Attention Budget with declared unit/owner/breach consequence, intake flooding defense, Civic Briefs with action-class separation, AttentionDelegation with scope/expiry/revocation, urgency discipline |
| **GOVERNANCE_HEALTH.md** | Governance and participation health metrics | Dashboard metrics (latency, classification, participation, emergency, output quality), delegation concentration, compensation distribution, sortition integrity, erosion triggers, structural review lane, GovernanceHealthReport artifact |

**Cross-references:** Depends on 40_Identity (standing, cryptographic model), 30_Classification (proportionality tiers), 50_Economics (compensation treasury rails). Referenced by 20_Protocol_Core (participation stages in lifecycle), 80_Runtime (state machine, invariants, API, events, read models, permissions).

**Why new layer:** Participation is not a button at the end of the Civic Loop. It is the continuous allocation of civic attention, time, voice, responsibility, and influence. The diagnosis existed (`POLITICAL_ECONOMY.md`) but the normative mechanics did not. This layer closes that gap.

---

## Layer 50: Economics

**Money, power, and sustainability.** The political economy of civic infrastructure.

**New Layer (Post-Mitigation):** Acknowledges that funding is a constitutional lever, not neutral plumbing.

| Document | Purpose | Key Concepts |
|----------|---------|--------------|
| **POLITICAL_ECONOMY.md** | Power and inequality theory | Unequal capacity, distributional conflict, group identity, participation inequality, attention as resource |
| **FUNDING_MODEL.md** | Revenue strategy and anti-capture | Treasury partitioning, civic leverage, donor limits, conditional funding rules |
| **TREASURY.md** | Financial operations | Partitions, allocation, release authorization, reserves, emergency treasury actions |
| **GRANTS_AND_BOUNTIES.md** | Incentive instruments | Grants vs. bounties, commons bias, challenge capacity funding |
| **PUBLIC_IP_MODEL.md** | Anti-enclosure framework | Commons-core IP, licensing defaults, forkability, public return principle |

**Cross-references:** References 10_Constitutional, 40_Identity. Supports 60_Skills, 70_Bootstrap.

**Why new layer:** Political economy was the biggest gap in the original system. Now it's a first-class concern.

---

## Layer 60: Skills

**AI reasoning infrastructure.** The tools that assist (but do not replace) human deliberation.

| Document | Purpose | Key Concepts |
|----------|---------|--------------|
| **SKILLS.md** | Skill contract definition | Role framing, decomposition rules, output contracts, evaluation hooks |
| **SKILL_REGISTRY.md** | Skill admission and tracking | Tier system, provider visibility, illusory diversity ban, dependency tracking |
| **SKILL_ECONOMICS.md** | Economic model for skills | 3-tier system (templates → evaluated → governance-grade), funding by tier, commons growth |
| **SKILL_EVALUATION.md** | Quality assurance and testing | Task suites, failure ledgers, regression testing, promotion/demotion rules |
| **AI_EPISTEMIC_RISK.md** | AI-layer epistemic governance | Monoculture sources, false diversity, epistemic failure modes, framing power, legibility bias |
| **TASK_SUITE_v0.md** | First evaluation corpus | 12 task cases in 3 bands, 14 failure classes, regression set, cross-model comparison, scoring dimensions |

**Cross-references:** Depends on 50_Economics for funding model. Referenced by 20_Protocol_Core, 70_Bootstrap.

**Why grouped:** Skill economics, evaluation, and registry are tightly coupled. Separating them creates confusion.

---

## Layer 70: Bootstrap

**Getting started without legitimacy.** The founding phase and its limitations.

| Document | Purpose | Key Concepts |
|----------|---------|--------------|
| **CONSTITUTIONAL_BOOTSTRAP.md** | Founding rules and debt | Constitutional debt, legitimacy-limited roles, first review obligation, bootstrap debt register |
| **BOOTSTRAP_DEBT_REGISTER.md** | Living register of all bootstrap shortcuts | Debt items, review triggers, retirement paths, escalation procedures |
| **BOOTSTRAP_PARAMETERS.md** | Fixed initial settings | Epoch parameters, threshold windows, registry bootstrap profile, emergency powers |
| **MINIMUM_VIABLE_PNYX.md** | MVP specification | What can be deferred vs. non-negotiable, 4-view audit minimum, exit criteria |
| **PROTOTYPE_PLAN.md** | First implementation path | Prototype hypotheses, reduced state flow, skill set, success/failure metrics, pilot criteria |
| **COMMUNITY_FORMATION.md** | How the first community forms | Trust ladder, first-adopter profile, entry problem, legitimacy path, formation metrics, failure conditions |
| **BOOTSTRAP_REALITY_CHECK.md** | Operational viability audit | Assets/constraints inventory, go/no-go gates, complexity budget, founder dependence, stop conditions |
| **SHUTDOWN_AND_DISSOLUTION.md** | How the system ends | Pause, wind-down, dissolution, stewardship transfer, treasury/IP/archive disposition, restart conditions |
| **PILOT_DOMAIN_TEMPLATE.md** | Structured pilot specification | Community fit, falsifiable hypotheses, process baseline, packet expectations, challenge path, exit conditions |
| **PROTOTYPE_PROFILE.md** | Condensed implementation card | v0 services, objects, events, views, skill set, complexity budget, go/no-go gates, success/failure signals |

**Cross-references:** Summarizes constraints from all layers. Start here for implementation.

**Key insight:** Bootstrap is legitimate only if it declares itself incomplete.

---

## Layer 80: Runtime

**Implementation details.** How the system actually runs.

| Document | Purpose | Key Concepts |
|----------|---------|--------------|
| **API_SPEC.md** | Interface specification | RESTful API, versioning, pagination, error handling, rate limiting |
| **STATE_MACHINE.md** | Proposal and emergency state transitions | States, transitions, guards, invariants |
| **INVARIANTS.md** | System invariants | Constitutional violations, invariant checking, rollback triggers |
| **PERMISSIONS.md** | Access control model | Permission classes, capability design, role hierarchy, delegation |
| **OPERATOR_TRUST_MODEL.md** | Operator constraints | Dual control, active-case protection, shadow runtime prohibition, bootstrap trust debt |
| **EVENT_MODEL.md** | Event-sourced runtime model | Commands, events, projections, aggregate boundaries, 13-subsystem event catalog, replay, causality |
| **READ_MODELS.md** | Projection and view specifications | 30+ role-specific views, freshness semantics, rebuildability, redaction, citizen/auditor/operator surfaces |

**Cross-references:** Technical implementation of protocol rules. Referenced by 99_Reference.

---

## Layer 90: Information

**Data models and formats.** The shape of civic information.

| Document | Purpose | Key Concepts |
|----------|---------|--------------|
| **DATA_MODEL.md** | Core data entities | Proposal, Packet, Event, Audit, Identity, Frame, Epoch |
| **EVIDENCE.md** | Evidence handling | Evidence gathering, uncertainty, provenance, sufficiency, contested evidence |
| **AUDIT_LOG.md** | Audit trail specification | Append-only, chained hashing, integrity, verification |
| **AUDIT_VIEWS.md** | Audit projections | 12 view types, redaction rules, dissent preservation, scale strategy |
| **PACKET_FORMAT.md** | Briefing packet structure | Standard shape, strongest case, dissent preservation, unknowns, irreversibility |
| **SCHEMAS.md** | Canonical JSON payload shapes | Shared envelopes, validation rules, mutability contracts, enum registry, prototype profile |
| **PACKET_EXAMPLES.md** | Concrete packet illustrations | 6 packet types exemplified, good/bad comparison, quality/failure checklists, challenge-driven revision |
| **PACKET_EXAMPLES_v0_JSON.md** | v0 JSON packet payloads | 9 JSON examples, schema-aligned, change notices, CitizenPacketView projection, validation checks |
| **PUBLIC_STORAGE_MODEL.md** | Public-file-first storage architecture | Content-addressed objects, manifests, derived read models, mirroring, append-only correction |

**Cross-references:** Data layer for all other components.

---

## Layer 95: Emergency

**Exception handling.** The most dangerous system component.

| Document | Purpose | Key Concepts |
|----------|---------|--------------|
| **EMERGENCY_ENFORCEMENT.md** | Technical emergency constraints | Time-bound tokens, auto-expiry, state machine, renewal prohibition, rollback |

**Cross-references:** Exception to all normal rules. Referenced by 80_Runtime, 10_Constitutional.

**Why standalone:** Emergency powers are the most dangerous feature. They deserve isolation and scrutiny.

---

## Layer 99: Reference

**Analysis, critique, and implementation specs.** External perspective on the system.

**New Layer (Post-Mitigation):** Preserves the intellectual evolution of the project.

| Document | Purpose | Type |
|----------|---------|------|
| **REFERENCE_IMPLEMENTATION.md** | Technical build specification | Implementation guide |
| **GOVERNANCE_Critique.md** | Original critique of GOVERNANCE.md | Analysis |
| **MITIGATIONS_Critique.md** | Assessment of mitigation documents | Analysis |
| **POST_MITIGATION_Critique.md** | Post-mitigation residual weaknesses | Adversarial analysis |
| **PROTOTYPE_GAP_Critique.md** | Spec-to-code gap analysis after Phase 1 | Adversarial analysis |
| **PAPER_ARCHITECTURE_Critique.md** | Analysis of procedural over-complexity | Adversarial analysis |
| **META_CRITIQUE.md** | Synthesis and meta-analysis of all critiques | Meta-analysis |
| **CORE_V02_RECONCILIATION.md** | Decision record: reconciliation with external "PNyx Core v0.2" draft | Decision record |
| **CORE_V03_RECONCILIATION.md** | Decision record: reconciliation with external "PNyx Core v0.3" draft — introduces participation layer, two new canonical states, economic invariants, 14 new artifact types | Decision record |

**Cross-references:** Critiques reference all layers. Implementation references 70_Bootstrap, 80_Runtime.

**Note:** These are analysis documents, not specifications. Keep separate to avoid confusion.

---

## Dependencies Graph

```
10_Constitutional
    ├── GOVERNANCE.md
    ├── ARCHITECTURE.md
    └── THREAT_MODEL.md
    ↓
20_Protocol_Core
    ├── PROTOCOL.md
    ├── PANEL_SELECTION.md
    └── ROUTING.md
    ↓
30_Classification
    └── CLASSIFICATION.md
    ↓
40_Identity
    ├── IDENTITY_AND_MEMBERSHIP.md
    ├── CRYPTOGRAPHIC_MODEL.md
    └── ZK_CIVIC_CREDENTIALS.md
    ↓
45_Participation
    ├── PARTICIPATION_MODEL.md
    ├── SORTITION.md
    ├── ATTENTION_AND_REACH.md
    └── GOVERNANCE_HEALTH.md
    ↓
50_Economics
    ├── POLITICAL_ECONOMY.md
    ├── FUNDING_MODEL.md
    ├── TREASURY.md
    ├── GRANTS_AND_BOUNTIES.md
    └── PUBLIC_IP_MODEL.md
    ↓
60_Skills
    ├── SKILLS.md
    ├── SKILL_REGISTRY.md
    ├── SKILL_ECONOMICS.md
    ├── SKILL_EVALUATION.md
    ├── AI_EPISTEMIC_RISK.md
    └── TASK_SUITE_v0.md
    ↓
70_Bootstrap
    ├── CONSTITUTIONAL_BOOTSTRAP.md
    ├── BOOTSTRAP_DEBT_REGISTER.md
    ├── BOOTSTRAP_PARAMETERS.md
    ├── MINIMUM_VIABLE_PNYX.md
    ├── PROTOTYPE_PLAN.md
    ├── COMMUNITY_FORMATION.md
    ├── BOOTSTRAP_REALITY_CHECK.md
    ├── SHUTDOWN_AND_DISSOLUTION.md
    ├── PILOT_DOMAIN_TEMPLATE.md
    └── PROTOTYPE_PROFILE.md
    ↓
80_Runtime
    ├── API_SPEC.md
    ├── STATE_MACHINE.md
    ├── INVARIANTS.md
    ├── PERMISSIONS.md
    ├── OPERATOR_TRUST_MODEL.md
    ├── EVENT_MODEL.md
    └── READ_MODELS.md
    ↓
90_Information
    ├── DATA_MODEL.md
    ├── EVIDENCE.md
    ├── AUDIT_LOG.md
    ├── AUDIT_VIEWS.md
    ├── PACKET_FORMAT.md
    ├── SCHEMAS.md
    ├── PACKET_EXAMPLES.md
    ├── PACKET_EXAMPLES_v0_JSON.md
    └── PUBLIC_STORAGE_MODEL.md
    ↓
95_Emergency
    └── EMERGENCY_ENFORCEMENT.md

99_Reference (external perspective)
    ├── REFERENCE_IMPLEMENTATION.md
    ├── GOVERNANCE_Critique.md
    ├── MITIGATIONS_Critique.md
    ├── POST_MITIGATION_Critique.md
    ├── PROTOTYPE_GAP_Critique.md
    ├── PAPER_ARCHITECTURE_Critique.md
    ├── META_CRITIQUE.md
    ├── CORE_V02_RECONCILIATION.md
    └── CORE_V03_RECONCILIATION.md
```

---

## Change Log

### Version 2.3 (July 2026) - Core v0.3 Reconciliation — Participation Layer

Reconciliation with an external "PNyx Core v0.3" draft; full decision record in `99_Reference/CORE_V03_RECONCILIATION.md`. The principal adoption is participation as a first-class normative subsystem.

**Added:**
- New `45_Participation/` layer between Identity and Economics (4 documents):
  - `PARTICIPATION_MODEL.md` — core participation mechanics (modes, plans, compensated participation, participant bodies, audits, receipts, compatibility tiers)
  - `SORTITION.md` — verifiable random selection with registry-as-primary-capture-surface hardening
  - `ATTENTION_AND_REACH.md` — attention governance (eight-function separation, budgets, delegation, civic briefs, urgency discipline)
  - `GOVERNANCE_HEALTH.md` — promoted from `99_Reference/` and extended with delegation, compensation, and sortition integrity metrics
- Two new canonical public states (`STATE_MACHINE.md` §4.5): `PARTICIPATION_DESIGNED`, `PARTICIPANT_BODY_READY`
- Two new internal states with MUST-form guards (`STATE_MACHINE.md` §4.1, §4.4): `PARTICIPATION_DESIGN_PENDING`, `PARTICIPANT_BODY_FORMATION`
- Participation stages in the proposal lifecycle (`PROTOCOL.md` Stages D2, I2)
- Participation Audit gate before decision readiness (`PROTOCOL.md` invariant 7.10, `STATE_MACHINE.md` §4.4 guard)
- 14 new artifact types across `DATA_MODEL.md`, `SCHEMAS.md`, `PUBLIC_STORAGE_MODEL.md`: `ParticipationPolicy`, `ParticipationPlan`, `ParticipantBody`, `TargetedInvitation`, `SortitionConfiguration`, `SortitionResult`, `CivicBrief`, `AttentionAllocationPolicy`, `ReachDecision`, `AttentionDelegation`, `CivicReceipt`, `ParticipantCompensationRecord`, `ParticipationAudit`, `GovernanceHealthReport`
- Participation commands, events, queries, and endpoints (`API_SPEC.md` §8.11, §9.8, §10; `EVENT_MODEL.md` §23)
- Participation read models (`READ_MODELS.md` §19) and new permission classes (`PERMISSIONS.md` §4.9–4.10)
- Economic artifacts from v0.3: `PublicReturnReport`, `ReplicationRecord`, `LicenseRecord`, `ContributorAgreement`, `EconomicConflictDisclosure` (`DATA_MODEL.md` §5.32–§5.36, `SCHEMAS.md` §14.5, §15.2–§15.3, §24.15, §25.7)
- Economic invariants (no hidden treasury movement, no commercial expansion without renewed authority, no purchased civic priority) and participation invariants (no open-door claim of equality, no reach without public rule, no simulation as participation, no summary without source continuity, no engagement metric as legitimacy) (`INVARIANTS.md`)
- Reuse-status labeling for epistemic feedback protection (`SCHEMAS.md` §3.9)
- Skill executor roles: civic translator and participation-accessibility assistant (`EXECUTOR_MODEL.md`)
- MVP participation floor and economic MVP section (`MINIMUM_VIABLE_PNYX.md`)
- `99_Reference/CORE_V03_RECONCILIATION.md` decision record

**Fixed/Repaired:**
- v0.3's undefined "high-impact" predicate bound to classification output
- v0.3's advisory "MAY enter when" gates hardened to MUST-form guards
- v0.3's attention budget given declared unit, owner, and breach consequence
- v0.3's sortition given registry threat model and integrity rules
- v0.3's participation audit given auditor, permission class, and challenge path
- v0.3's delegation × uniqueness interaction specified
- v0.3's adversarial-review waiver rejected; no waiver path exists
- v0.3's optional treasury vs mandatory auditability resolved
- Classification binding recorded for all "high-impact" predicates

**Rejected:**
- "Civic Case" terminology (suite's `Proposal` stands)
- Flat declared-skill specialist model and SHOULD-strength panel rule
- Disclosure-only licensing (per-class gradation stands)
- v0.3's 26-MUST MVP floor (adopted staged compatibility tiers instead)
- Participation Loop as a second first-class loop (adopted as transversal concern within single lifecycle)

### Version 2.2 (July 2026) - Core v0.2 Reconciliation

Reconciliation with an external "PNyx Core v0.2" draft; full decision record in `99_Reference/CORE_V02_RECONCILIATION.md`.

**Added:**
- Canonical public state vocabulary + mandatory export mapping (`STATE_MACHINE.md` §4.5–4.6)
- Problem-before-solution gate (`PROTOCOL.md` invariant 7.9, `DATA_MODEL.md` §5.25, `SCHEMAS.md` §25.1)
- Monitoring → Outcome → Learning stages and artifacts (`PROTOCOL.md` Stages O/P/Q, `DATA_MODEL.md` §5.26–§5.31, `SCHEMAS.md` §25, `INVARIANTS.md` §12)
- Executor-form axis for execution routing (`ROUTING.md` §5, `PROTOCOL.md` Stage L)
- `JudgmentConfiguration` artifact and authoring modes (`SCHEMAS.md` §25.2, §3.8)
- Economic invariants incl. no-personal-data-economy (`INVARIANTS.md` §13, `FUNDING_MODEL.md` §5)
- Civic scope taxonomy neighborhood→Earth + aggregation traceability (`DATA_MODEL.md` §5.1, `IDENTITY_AND_MEMBERSHIP.md` §4.1)
- Per-class licensing gradation for open core / delayed release (`PUBLIC_IP_MODEL.md` §7.1)
- Consolidated Core Change Proposal schema (`GOVERNANCE.md` §15.1)
- `99_Reference/CORE_V02_RECONCILIATION.md` decision record

**Fixed:**
- Proposal-state lists unified: `PROTOCOL.md` §6 and `DATA_MODEL.md` §5.4/§7 now reference `STATE_MACHINE.md` §4.1 instead of maintaining divergent copies
- Evidence-class taxonomy unified: defined once in `EVIDENCE.md` §5, referenced by `SCHEMAS.md` §12.1 and `DATA_MODEL.md` §5.13
- EvidenceItem vs EvidencePacket terminology guard (`EVIDENCE.md` §4.8)

### Version 2.0 (April 2026) - Major Restructure

**Added:**
- 50_Economics/ layer (5 new documents)
- 60_Skills/ layer consolidated (4 documents)
- 95_Emergency/ standalone layer
- 99_Reference/ layer for analysis documents

**Moved:**
- CLASSIFICATION.md → standalone layer (was in Control/)
- CRYPTOGRAPHIC_MODEL.md → 40_Identity (was in Runtime/)
- POLITICAL_ECONOMY.md → 50_Economics (was in Protocol/)
- All funding docs → 50_Economics
- All skill docs → 60_Skills
- All audit docs → 90_Information
- EMERGENCY_ENFORCEMENT.md → 95_Emergency (was in Protocol/)
- All critiques → 99_Reference

**Removed:**
- Old `core/` directory structure
- Old `pushback/` directory
- Outdated INDEX.md

**Rationale:**
1. Political economy now a first-class layer (major post-mitigation addition)
2. Economics, funding, and IP anti-enclosure grouped logically
3. Skills ecosystem grouped (economics, evaluation, registry)
4. Classification elevated to its own layer (critical choke point)
5. Emergency isolated for scrutiny
6. Implementation specs separated from design specs
7. Critiques preserved but distinct from specifications

---

## Usage Guidelines

### For System Designers
1. Read 10_Constitutional first (bedrock principles)
2. Skim 20_Protocol_Core to understand flow
3. Deep dive 50_Economics (power theory is crucial)
4. Study 70_Bootstrap before proposing implementation

### For Implementers
1. Start with 70_Bootstrap/MINIMUM_VIABLE_PNYX.md
2. Reference 99_Reference/REFERENCE_IMPLEMENTATION.md
3. Use 80_Runtime/ and 90_Information/ for technical details
4. Review 60_Skills/ for AI integration

### For Researchers
1. Read 99_Reference/ critiques first (understand evolution)
2. Compare original 10_Constitutional/GOVERNANCE.md with mitigations
3. Study 50_Economics/POLITICAL_ECONOMY.md (unique contribution)
4. Note 95_Emergency/ structural enforcement (innovation)

### For Contributors
1. Choose a layer based on expertise
2. Respect dependencies (don't break 10_Constitutional invariants)
3. Update this INDEX when adding documents
4. Cross-reference appropriately

---

## Document Statistics

| Layer | Documents | Lines (approx) | Focus |
|-------|-----------|----------------|-------|
| 10_Constitutional | 3 | 2,000 | Principles |
| 20_Protocol_Core | 3 | 2,200 | Mechanics |
| 30_Classification | 1 | 600 | Choke point |
| 40_Identity | 3 | 900 | Participation |
| 45_Participation | 4 | 2,100 | Participation |
| 50_Economics | 5 | 3,200 | Power & money |
| 60_Skills | 6 | 3,500 | AI reasoning |
| 70_Bootstrap | 10 | 4,600 | Founding |
| 80_Runtime | 7 | 4,500 | Implementation |
| 90_Information | 9 | 6,300 | Data & formats |
| 95_Emergency | 1 | 500 | Exceptions |
| 99_Reference | 8 | 4,500 | Analysis |
| **Total** | **61** | **~36,000** | |

**Note:** Line counts include code examples and are approximate.

---

## Next Steps for This Documentation

### Short Term
- [ ] Add README.md at root explaining the project
- [ ] Add CONTRIBUTING.md for document standards
- [ ] Create CHANGELOG.md for tracking spec evolution
- [ ] Add GLOSSARY.md for terms like "epoch," "civic packet," "skill"

### Medium Term
- [ ] Cross-reference audit: ensure all `See X.md` links work
- [ ] Add diagrams to ARCHITECTURE.md and PROTOCOL.md
- [ ] Create decision records (why specific design choices were made)
- [ ] Document known limitations and future work

### Long Term
- [ ] Reference implementation code repository
- [ ] Test vectors and conformance suite
- [ ] Formal specification (TLA+, Alloy, etc.)
- [ ] Academic paper on political economy layer
- [ ] Pilot community documentation

---

## Questions?

**Documentation issues:** Open an issue in the repository  
**Clarifications:** Check 99_Reference/ critiques for common questions  
**Implementation:** Start with 70_Bootstrap/MINIMUM_VIABLE_PNYX.md  
**Philosophy:** Read 50_Economics/POLITICAL_ECONOMY.md §24

---

*This is a living document. The Pnyx system evolves through critique and refinement.*
