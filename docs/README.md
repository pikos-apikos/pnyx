# Pnyx Documentation

A comprehensive specification for civic governance using AI assistance under human sovereignty.

## What is Pnyx?

**Pnyx** (pronounced "p-nix") is a direct democratic governance system designed to help citizens self-govern under conditions of complexity and scale. Named after the ancient Athenian assembly hill.

**Core principle:** AI skills provide structured analysis, but **human sovereignty remains primary**.

**Key innovations:**
- Multi-skill panels (5+ skills per proposal) with mandatory adversarial critique
- Explicit political economy layer (acknowledges power, inequality, and conflict)
- Executor-agnostic, evidence-backed civic analysis (AI, human, hybrid)
- AI epistemic risk framework (treats monoculture and false plurality as first-class threats)
- Structural emergency enforcement (time-locked expiry, not just procedural promises)
- Tiered skill economics (cheap experimentation → rigorous evaluation)
- Anti-enclosure public IP model (commons stays commons)
- Bootstrap debt tracking (honest founding, no false constitutional claims)
- Community formation theory (trust ladder, not audience growth)
- Explicit shutdown and dissolution framework (the system can end honestly)

## Quick Start

### For First-Time Readers

1. **Philosophy:** Read [`10_Constitutional/GOVERNANCE.md`](10_Constitutional/GOVERNANCE.md) for sovereignty and legitimacy
2. **Overview:** Read [`10_Constitutional/ARCHITECTURE.md`](10_Constitutional/ARCHITECTURE.md) for system design
3. **Context:** Read [`50_Economics/POLITICAL_ECONOMY.md`](50_Economics/POLITICAL_ECONOMY.md) for power theory
4. **Critique:** Read [`99_Reference/POST_MITIGATION_Critique.md`](99_Reference/POST_MITIGATION_Critique.md) for honest assessment of persistent weaknesses

### For Builders

Start with:
- [`70_Bootstrap/PROTOTYPE_PROFILE.md`](70_Bootstrap/PROTOTYPE_PROFILE.md) — Condensed implementation card (start here)
- [`70_Bootstrap/PROTOTYPE_PLAN.md`](70_Bootstrap/PROTOTYPE_PLAN.md) — Full prototype rationale
- [`70_Bootstrap/PILOT_DOMAIN_TEMPLATE.md`](70_Bootstrap/PILOT_DOMAIN_TEMPLATE.md) — How to specify a pilot
- [`90_Information/SCHEMAS.md`](90_Information/SCHEMAS.md) — Canonical JSON payload shapes
- [`80_Runtime/EVENT_MODEL.md`](80_Runtime/EVENT_MODEL.md) — Command/event architecture
- [`demo/`](../demo/) — Working Java/Spring Boot reference implementation

### For Researchers

Key documents:
- [`50_Economics/POLITICAL_ECONOMY.md`](50_Economics/POLITICAL_ECONOMY.md) — Power theory contribution
- [`60_Skills/AI_EPISTEMIC_RISK.md`](60_Skills/AI_EPISTEMIC_RISK.md) — AI monoculture and false plurality analysis
- [`95_Emergency/EMERGENCY_ENFORCEMENT.md`](95_Emergency/EMERGENCY_ENFORCEMENT.md) — Structural emergency constraints
- [`99_Reference/POST_MITIGATION_Critique.md`](99_Reference/POST_MITIGATION_Critique.md) — Adversarial critique by Claude Opus 4
- [`99_Reference/PAPER_ARCHITECTURE_Critique.md`](99_Reference/PAPER_ARCHITECTURE_Critique.md) — Critique of procedural complexity and the "paper architecture" flaw
- [`99_Reference/META_CRITIQUE.md`](99_Reference/META_CRITIQUE.md) — Synthesis and meta-analysis of all critiques

## Documentation Structure

This specification is organized in **11 numbered layers** representing logical dependencies:

```
10_Constitutional/      → What must always hold (sovereignty, values, threats)
20_Protocol_Core/       → How proposals flow (lifecycle, panels, routing)
30_Classification/      → Determining review intensity (choke point)
40_Identity/            → Who participates (privacy, membership)
50_Economics/           → Money and power (political economy layer)
60_Skills/              → AI reasoning infrastructure + epistemic risk
70_Bootstrap/           → Founding lifecycle (plan → community → reality check → shutdown)
80_Runtime/             → Implementation (API, state machine, events, read models)
90_Information/         → Data models, schemas, packets, examples
95_Emergency/           → Exception handling (isolated for scrutiny)
99_Reference/           → Analysis, critiques, and implementation specs
```

**Full navigation:** See [`00_INDEX_AND_MAP.md`](00_INDEX_AND_MAP.md)

## Key Design Principles

1. **Human sovereignty is primary** — AI assists, humans decide
2. **Anti-capture by design** — Assumes capture attempts are normal, not exceptional
3. **Structured disagreement** — Dissent preserved, not smoothed into false consensus
4. **Visible procedure** — Every decision auditable and challengeable
5. **Explicit debt** — Bootstrap shortcuts logged, not hidden
6. **Political realism** — Acknowledges power, inequality, and conflict
7. **Epistemic honesty** — Provider diversity is not the same as cognitive diversity
8. **Bootstrap realism** — The system must be smaller than its own theory

## System Evolution

**Version 1.0 (Original):** ~2,500 lines, 17 documents
**Version 2.0 (Post-Mitigation):** ~9,500 lines, 28 documents
**Version 2.1 (Current):** ~29,400 lines, 42 documents + working reference implementation

**Major changes in 2.2:**
- Applied `SYSTEM_PATCH_v1` enforcing executor-agnostic, evidence-backed civic analysis
- Formalized distinction between `Skill` (mandate) and `Executor` (AI/human actor)
- Replaced `SkillOutput` with structured `EvidencePacket` containing explicit citations

**Major changes in 2.1:**
- Built a working end-to-end civic loop reference implementation
- Added adversarial post-mitigation critique identifying 10 persistent weaknesses
- Created AI epistemic risk framework (monoculture, false plurality, framing convergence)
- Developed complete bootstrap lifecycle (community formation → reality check → shutdown)
- Defined canonical JSON schemas, event model, and read model specifications
- Created first evaluation corpus (12 task cases, 14 failure classes, 3-band structure)
- Added concrete packet examples (narrative + JSON) for all packet types
- Added pilot domain template for structured pilot selection

**See:**
- [`99_Reference/SYSTEM_PATCH_v1.md`](99_Reference/SYSTEM_PATCH_v1.md) — Normative shift to evidence-backed, executor-agnostic analysis
- [`99_Reference/POST_MITIGATION_Critique.md`](99_Reference/POST_MITIGATION_Critique.md) — Post-mitigation adversarial critique
- [`99_Reference/GOVERNANCE_Critique.md`](99_Reference/GOVERNANCE_Critique.md) — Original critique
- [`99_Reference/MITIGATIONS_Critique.md`](99_Reference/MITIGATIONS_Critique.md) — Mitigation assessment

## Document Statistics

| Layer | Documents | Approximate Lines |
|-------|-----------|-------------------|
| Constitutional | 3 | 1,500 |
| Protocol Core | 3 | 1,650 |
| Classification | 1 | 600 |
| Identity | 2 | 850 |
| Economics | 5 | 3,050 |
| Skills | 13 | 7,600 |
| Bootstrap | 9 | 4,550 |
| Runtime | 7 | 4,250 |
| Information | 8 | 5,800 |
| Emergency | 1 | 500 |
| Reference | 9 | 5,350 |
| **Total** | **62** | **~35,700** |

## Reference Implementation

A working Java reference implementation exists in [`demo/`](../demo/).

**Stack:** Java 25, Spring Boot, PostgreSQL, jOOQ, Thymeleaf/HTMX, Gradle

**Run:**

```bash
cd demo
docker compose up -d postgres
./gradlew :app:bootRun
```

Open `http://localhost:8080`.

The reference implementation covers the civic loop through:
- Proposal submission, clarification, and validation
- 5-role skill-panel deliberation
- Evidence-backed public artifacts
- Public decisions and implementation tracking
- Append-only, SHA-256 hash-chained event streams
- Content-addressed public storage and audit views
- Chain-integrity verification

**Status:** Working prototype and reference implementation; not production-ready.

## Contributing

This is a living specification. Improvements welcome.

**Standards:**
- All documents must cross-reference dependencies
- Critiques and analysis go in `99_Reference/`
- Implementation specs go in `99_Reference/`
- Design specifications go in numbered layers
- Update `00_INDEX_AND_MAP.md` when adding documents
- New bootstrap/operational documents go in `70_Bootstrap/`
- New runtime architecture documents go in `80_Runtime/`

## Status

**Specification:** 44 documents across 11 layers (~30,200 lines)
**Reference implementation:** Java/Spring Boot demo with an end-to-end civic loop
**Next step:** Select first pilot community and test the loop with real participants
**Critique status:** 4 of 10 identified issues addressed; 6 require empirical testing or external review

## Contact & Community

- **Issues:** Open an issue in this repository
- **Discussions:** Use GitHub Discussions for questions
- **Contribute:** See CONTRIBUTING.md

## License

This specification follows the repository's documented multi-license model. See [`../LICENSING.md`](../LICENSING.md).

**Public IP Note:** Per [`50_Economics/PUBLIC_IP_MODEL.md`](50_Economics/PUBLIC_IP_MODEL.md), outputs from this system should default to commons-preferred licensing.

---

*"A democratic system fails when it mistakes visible procedure for balanced power."* — [`50_Economics/POLITICAL_ECONOMY.md`](50_Economics/POLITICAL_ECONOMY.md)

*"The greatest danger of the AI layer is not that it will always be obviously wrong. The greater danger is that it may become calmly, fluently, procedurally, and repeatedly narrow in the same direction."* — [`60_Skills/AI_EPISTEMIC_RISK.md`](60_Skills/AI_EPISTEMIC_RISK.md)

*"A civic system is not born when its rules are written. It is born when a real group uses it twice."* — [`70_Bootstrap/COMMUNITY_FORMATION.md`](70_Bootstrap/COMMUNITY_FORMATION.md)

---

**Last Updated:** July 2026
**System Version:** 2.2
**Documentation Version:** 2.2
