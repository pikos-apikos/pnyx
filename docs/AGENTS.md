# AGENTS.md — docs/

Guidelines for agentic coding agents working with the Pnyx specification documents.

## What This Directory Is

`docs/` is the **authoritative specification** for the Pnyx civic governance system. The `demo/` Java implementation is a prototype of this spec — the docs are the source of truth, not the code.

**System:** Pnyx — direct democratic governance with AI-assisted civic reasoning under human sovereignty. Event-sourced, hash-chained, content-addressed public artifacts.

**Current version:** 2.3 (Core v0.3 Reconciliation — Participation Layer), July 2026, ~60 documents, ~36,000 lines.

---

## Structure

Eleven numbered layers representing a logical dependency chain. **Read in numerical order** to understand how layers build on each other.

```
10_Constitutional → 20_Protocol_Core → 30_Classification → 40_Identity
       ↓                    ↓              ↓                  ↓
45_Participation → 50_Economics → 60_Skills → 70_Bootstrap → 80_Runtime → 90_Information
       ↓                                                  ↓
95_Emergency ←──────────────────────────────────────────┘
       ↓
99_Reference (optional: analysis & critique)
```

| Layer | Documents | Focus | Key Entry |
|-------|-----------|-------|-----------|
| `10_Constitutional/` | 3 | Bedrock: sovereignty, architecture, threat model | `GOVERNANCE.md` |
| `20_Protocol_Core/` | 3 | Proposal lifecycle, panel selection, routing | `PROTOCOL.md` |
| `30_Classification/` | 1 | Review intensity choke point | `CLASSIFICATION.md` |
| `40_Identity/` | 3 | Privacy-preserving civic identity, ZK credentials | `IDENTITY_AND_MEMBERSHIP.md` |
| `45_Participation/` | 4 | Modes, sortition, attention governance, health | `PARTICIPATION_MODEL.md` |
| `50_Economics/` | 5 | Political economy, funding, treasury, public IP | `POLITICAL_ECONOMY.md` |
| `60_Skills/` | 6 | AI reasoning infra, registry, evaluation, epistemic risk | `SKILLS.md` |
| `70_Bootstrap/` | 10 | Founding, MVP, prototype, community, shutdown | `MINIMUM_VIABLE_PNYX.md` |
| `80_Runtime/` | 7 | API, state machine, invariants, permissions, events | `STATE_MACHINE.md` |
| `90_Information/` | 9 | Data models, schemas, packets, audit, public storage | `SCHEMAS.md` |
| `95_Emergency/` | 1 | Time-locked emergency enforcement | `EMERGENCY_ENFORCEMENT.md` |
| `99_Reference/` | 8 | Critiques, reconciliation records, implementation specs | `REFERENCE_IMPLEMENTATION.md` |

**Navigation map:** `00_INDEX_AND_MAP.md` has the full document list, dependency graph, and change log.

---

## How to Use These Docs

### Finding what you need

| Goal | Start here |
|------|-----------|
| Understand philosophy | `10_Constitutional/GOVERNANCE.md` → `ARCHITECTURE.md` |
| Understand the proposal flow | `20_Protocol_Core/PROTOCOL.md` |
| Build a pilot | `70_Bootstrap/PROTOTYPE_PROFILE.md` → `PROTOTYPE_PLAN.md` |
| Understand anti-capture | `10_Constitutional/THREAT_MODEL.md` → `95_Emergency/` |
| Understand the skill system | `60_Skills/SKILLS.md` |
| Understand funding | `50_Economics/FUNDING_MODEL.md` → `TREASURY.md` |
| Understand participation | `45_Participation/PARTICIPATION_MODEL.md` |
| Implement runtime | `80_Runtime/STATE_MACHINE.md` → `EVENT_MODEL.md` |
| Work with data shapes | `90_Information/SCHEMAS.md` → `PACKET_EXAMPLES_v0_JSON.md` |
| See the spec-to-code gap | `99_Reference/PROTOTYPE_GAP_Critique.md` |

### When implementing in `demo/`

The Java prototype in `demo/` references these docs in Javadoc using the `{@code docs/.../FILE.md}` convention. Before touching `demo/` code, find the relevant spec section here first. See `demo/AGENTS.md` for the implementation-side rules.

### When the spec and code disagree

**The spec wins.** `docs/` is authoritative. If the `demo/` prototype diverges, either fix the code or record the gap in `99_Reference/PROTOTYPE_GAP_Critique.md`. Do not silently edit the spec to match the code.

---

## How to Update These Docs

### Hard rules

1. **Human review required.** All documents require human review. Do not add, modify, or remove documents without explicit human consent. Agents may draft proposed changes, but a human must approve before they land in `docs/`.
2. **Respect the dependency chain.** A lower-numbered layer must never depend on a higher-numbered one. `10_Constitutional` constrains everything; `99_Reference` depends on everything.
2. **Cross-reference dependencies.** Every document must cite the layers it depends on and the layers that depend on it. Use relative markdown links: `[PROTOCOL.md](../20_Protocol_Core/PROTOCOL.md)`.
3. **Update `00_INDEX_AND_MAP.md` when adding or renaming documents.** The index, dependency graph, and document statistics table must stay accurate.
4. **Append a change-log entry** under `00_INDEX_AND_MAP.md` → "Change Log" with version, date, and a summary of Added/Fixed/Rejected items.
5. **Never silently rewrite history.** Spec evolution is recorded in the change log and in `99_Reference/` reconciliation records. Superseded content gets a note, not a deletion, when the change is normative.

### Where new documents go

| Type of document | Location |
|------------------|----------|
| Design specification (normative) | Numbered layer matching its dependency level |
| Critique / adversarial analysis | `99_Reference/` |
| Implementation guide / build spec | `99_Reference/` |
| Reconciliation / decision record | `99_Reference/` |
| Bootstrap / operational doc | `70_Bootstrap/` |
| Runtime architecture doc | `80_Runtime/` |
| Data model / schema / format | `90_Information/` |

### Naming conventions

- **Directories:** `NN_Name/` — two-digit prefix, `Title_Case` with underscores (e.g., `45_Participation/`).
- **Files:** `UPPERCASE.md` with underscores (e.g., `PARTICIPATION_MODEL.md`, `ZK_CIVIC_CREDENTIALS.md`).
- **Critiques:** `NAME_Critique.md` (e.g., `GOVERNANCE_Critique.md`).
- **Reconciliation records:** `CORE_V0X_RECONCILIATION.md`.

### Layer numbering

- `10`–`90` in increments of 5 or 10, ordered by dependency.
- `95` reserved for emergency (isolated for scrutiny).
- `99` reserved for reference / analysis (external perspective, not normative).
- When inserting a new layer between existing ones, use the intermediate number (e.g., `45_Participation` sits between `40_Identity` and `50_Economics`).

### Document quality bar

Every document should have:
- A header stating its purpose and which layer it belongs to
- A "Cross-references" section listing dependencies
- Section anchors that other docs can cite (e.g., `§4.5`, `§7.10`)
- Concrete examples where the spec is non-obvious
- A "Why" note when the design choice is non-obvious (critiques in `99_Reference/` often explain the why)

### What not to do

- ❌ Do not edit a spec to match a code divergence — fix the code or record the gap.
- ❌ Do not add, modify, or remove any document without explicit human consent.
- ❌ Do not add a document without updating `00_INDEX_AND_MAP.md`.
- ❌ Do not break a lower layer to satisfy a higher layer.
- ❌ Do not place normative specs in `99_Reference/` — that layer is for analysis only.
- ❌ Do not delete superseded normative content without a change-log entry and a pointer to the replacement.
- ❌ Do not introduce a new state, artifact, or invariant in one layer without updating the dependent layers (`STATE_MACHINE.md`, `SCHEMAS.md`, `DATA_MODEL.md`, `EVENT_MODEL.md`, `INVARIANTS.md` typically move together).

---

## Coupled Documents (Move Together)

When you change one of these, audit the others in the same edit:

| Change | Also update |
|--------|-------------|
| `STATE_MACHINE.md` (states/transitions) | `PROTOCOL.md`, `DATA_MODEL.md`, `EVENT_MODEL.md`, `READ_MODELS.md` |
| `SCHEMAS.md` (artifact shape) | `DATA_MODEL.md`, `PUBLIC_STORAGE_MODEL.md`, `PACKET_EXAMPLES_v0_JSON.md` |
| `INVARIANTS.md` | `10_Constitutional/`, `STATE_MACHINE.md` |
| `PROTOCOL.md` (lifecycle stage) | `STATE_MACHINE.md`, `API_SPEC.md`, `EVENT_MODEL.md` |
| New artifact type | `DATA_MODEL.md`, `SCHEMAS.md`, `PUBLIC_STORAGE_MODEL.md`, `READ_MODELS.md` |
| New permission class | `PERMISSIONS.md`, `API_SPEC.md` |
| New skill role | `60_Skills/SKILLS.md`, `SKILL_REGISTRY.md`, `EXECUTOR_MODEL.md` |

---

## Quick Orientation for New Agents

1. Read `00_INDEX_AND_MAP.md` first — it's the map.
2. Read `README.md` for the high-level pitch and quick-start links.
3. Find your task's layer in the table above.
4. Read that layer's key entry document.
5. Follow the cross-references to dependent layers.
6. Before editing, check the "Coupled Documents" table to see what else moves.
7. After editing, update `00_INDEX_AND_MAP.md` and append a change-log entry.

---

*The docs are the spec. The code is a prototype of the spec. Keep them in that order.*
