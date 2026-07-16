# PNyx

**An open civic-loop prototype for public reasoning, AI-assisted deliberation, and accountable execution.**

PNyx is an open-source experiment in civic infrastructure.

It helps people turn public problems into structured proposals, evidence packets, AI-assisted reviews, public decisions, and trackable execution paths.

It is not a finished governance system.  
It is not an AI government.  
It is not a replacement for public judgment.

It is a working prototype for a different kind of public system:

> **Humans decide. Machines help society remember, reason, compare, audit, and execute.**

---

## Why PNyx exists

Modern societies produce endless information, but very little durable public reasoning.

We have posts, reactions, comments, campaigns, polls, reports, promises, outrage cycles, and institutional silence. But after a decision is made, the reasoning often disappears. Evidence becomes fragmented. Responsibility becomes unclear. Implementation becomes invisible.

At the same time, AI is entering every part of public and private life without a clear civic direction. Most AI systems are optimized for productivity, engagement, automation, persuasion, or extraction.

PNyx explores another direction:

> **AI as civic infrastructure for structured public thought and accountable action.**

The goal is to create a public corpus of proposals, evidence, critique, counterarguments, decisions, implementation updates, and audits — a dataset of democratic reasoning rather than social-media noise.

---

## Core idea

PNyx implements a **Civic Loop**:

```text
Proposal
  → validation
  → AI / expert review
  → evidence packet
  → public judgment
  → decision
  → implementation fork
  → execution tracking
  → audit memory
```

Every important step produces a public artifact.

The system is designed around one rule:

> **Everything that shapes public judgment must be public.**

This means the public should be able to inspect:

- what was proposed
- what evidence was used
- what assumptions were made
- what AI reviewers said
- what human experts said
- what objections were raised
- what decision was made
- which implementation path was selected
- what execution tasks followed
- what changed over time
- what failed, was delayed, or was corrected

PNyx is not only an application. It is also a public artifact protocol.

---

## From decision to action

A public decision should not disappear after deliberation.

PNyx treats implementation as part of the civic loop.

After a decision, the system can create one or more execution paths:

```text
Decision
  → Institutional Action Path
  → Public-Interest Venture Path
  → Community Execution Path
  → Open-Source Implementation Path
  → Grant / Bounty Path
```

This is important because civic proposals can move through more than one channel.

Some proposals require the state.

Others can be implemented by communities, cooperatives, public-interest startups, open-source teams, research groups, foundations, or local organizations.

PNyx makes that fork visible.

---

## Institutional Action Path

The **Institutional Action Path** is used when a decision must be directed toward a public authority.

Examples:

- municipality
- region
- ministry
- public agency
- regulator
- school board
- infrastructure operator
- independent authority

The system can produce:

- formal public request
- responsible institution mapping
- evidence packet
- submission record
- public deadline
- response tracking
- delay tracking
- escalation record
- audit log

The purpose is not only to ask institutions to act.

The purpose is to make public responsibility visible.

---

## Public-Interest Venture Path

The **Public-Interest Venture Path** is used when a proposal can become a sustainable civic service, cooperative, open-source project, or public-benefit business.

Examples:

- open-source transparency dashboard
- citizen-maintained public data mirror
- cooperative energy project
- local infrastructure monitoring tool
- public procurement analysis service
- civic reporting platform
- AI-assisted legal or administrative helper

This path allows a decision to become:

- implementation project
- community service
- cooperative initiative
- grant-funded project
- bounty-funded task
- public-interest startup
- open-source product

The purpose is not to privatize democracy.

The purpose is to prevent public decisions from dying when institutions ignore them.

> **A civic decision can become institutional pressure, community execution, or public-interest enterprise.**

---

## Public-file-first architecture

PNyx treats public files as the source of truth.

The database is not the truth.  
The database is a cache, index, or read model.

Canonical civic objects are stored as signed, content-addressed public files.

```text
public objects → manifests → importers → read models → UI/API
```

This makes the system easier to mirror, audit, archive, fork, and rebuild.

If the database disappears, the civic memory should still be recoverable from the public corpus.

---

## What is public?

PNyx is public by default for civic artifacts.

Examples of public artifacts:

- proposals
- proposal revisions
- structured validations
- evidence items
- AI review reports
- human expert reports
- arguments for and against
- public questions
- challenges
- decisions
- implementation paths
- execution tasks
- status updates
- audit events
- schema versions
- skill definitions
- model metadata
- artifact hashes

PNyx does **not** require exposing private identity, raw credentials, biometric material, private keys, or sensitive personal information.

The principle is:

> **Privacy protects people. Transparency constrains power.**

---

## AI role

AI models in PNyx do not decide.

They act as bounded reviewers, summarizers, validators, translators, comparators, and risk detectors.

A model may produce public reasoning artifacts such as:

```json
{
  "type": "ai_review",
  "role": "economic_reviewer",
  "proposal_ref": "sha256:...",
  "skill_version": "economic-reviewer/v0.1",
  "model": {
    "provider": "openai-compatible",
    "name": "example-model",
    "version": "unknown"
  },
  "summary": "...",
  "findings": [],
  "risks": [],
  "missing_information": [],
  "assumptions": [],
  "citations": [],
  "confidence": 0.72
}
```

PNyx does not depend on exposing a model's hidden chain of thought.  
Instead, models submit **public reasoning artifacts** that can be challenged, compared, improved, or ignored.

---

## What the prototype does

The first prototype focuses on a complete vertical slice of the Civic Loop.

```text
Submit proposal
  → create structured proposal object
  → run first-pass validation
  → run AI skill-panel reviews
  → generate evidence packet
  → collect public judgment signals
  → create decision object
  → create implementation paths
  → track execution tasks
  → write audit trail
  → export public artifacts
```

The goal is not to solve governance immediately.

The goal is to make the loop visible, inspectable, repeatable, and executable.

---

## MVP scope

The MVP is intentionally limited.

It includes:

- proposal intake
- structured proposal schema
- first-pass validation
- AI reviewer roles
- evidence packet generation
- public comments / objections / challenges
- simple public judgment states
- decision records
- implementation path selection
- institutional action tracker
- public-interest venture tracker
- execution tracker
- audit log
- public artifact export
- importable read model

It does not yet include:

- production-grade identity
- binding legal authority
- cryptographic voting
- zero-knowledge membership proofs
- full anti-capture mechanisms
- institutional integration
- production moderation workflows
- treasury automation
- bounty escrow
- legal entity formation
- public procurement automation

Those belong to later phases.

---

## Example civic flow

A user submits a proposal:

```text
Create a municipal fund for energy upgrades in public schools.
```

PNyx turns it into a structured object:

```json
{
  "type": "proposal",
  "title": "Municipal fund for energy upgrades in public schools",
  "problem": "Public schools have high energy costs and poor building efficiency.",
  "proposed_action": "Create a dedicated municipal fund for insulation, lighting, and heating upgrades.",
  "affected_groups": ["students", "teachers", "municipality", "taxpayers"],
  "status": "submitted"
}
```

Then the system asks reviewers to examine it from different angles:

- legal feasibility
- economic impact
- social impact
- technical feasibility
- risk and abuse potential

The result is an evidence packet that citizens can inspect before judgment.

If the proposal is accepted, PNyx can create implementation paths:

```json
{
  "type": "implementation_fork",
  "decision_ref": "sha256:...",
  "paths": [
    {
      "type": "institutional_action",
      "title": "Submit formal request to the municipality",
      "status": "planned"
    },
    {
      "type": "public_interest_venture",
      "title": "Create open-source school energy dashboard",
      "status": "exploring"
    }
  ]
}
```

The decision does not end the process.

It creates accountable work.

---

## Repository structure

Expected structure:

```text
pnyx/
├─ README.md
├─ MANIFESTO.md
├─ docker-compose.yml
├─ apps/
│  ├─ web/
│  ├─ api/
│  └─ worker/
├─ packages/
│  ├─ schemas/
│  ├─ civic-loop-core/
│  └─ civic-skills/
├─ docs/
│  ├─ ARCHITECTURE.md
│  ├─ PROTOCOL.md
│  ├─ GOVERNANCE.md
│  ├─ PUBLIC_STORAGE_MODEL.md
│  ├─ IMPLEMENTATION_PATHS.md
│  ├─ THREAT_MODEL.md
│  └─ ROADMAP.md
├─ examples/
│  ├─ school-energy-upgrade/
│  ├─ train-safety-audit/
│  └─ municipal-transparency-dashboard/
└─ data/
   └─ demo/
```

---

## Public object model

PNyx uses append-only civic objects.

Objects are immutable once published.  
Corrections create new objects.  
The past is not overwritten.

Common object types:

```text
proposal
proposal_revision
validation_report
ai_review
human_review
evidence_item
argument
challenge
decision
implementation_fork
implementation_path
execution_task
execution_update
audit_event
manifest
```

Each object should include:

```json
{
  "id": "sha256:...",
  "type": "proposal",
  "schema": "proposal.v1",
  "created_at": "2026-01-01T00:00:00Z",
  "created_by": "did:pnyx:example",
  "body": {},
  "refs": [],
  "signature": "..."
}
```

---

## Manifests

A manifest links public objects into a readable civic memory.

Example:

```json
{
  "network": "pnyx-demo",
  "epoch": 1,
  "previous_manifest": null,
  "objects": [
    {
      "id": "sha256:...",
      "type": "proposal",
      "schema": "proposal.v1",
      "locations": [
        "https://example.org/objects/proposal/example.json",
        "ipfs://example"
      ]
    }
  ],
  "created_at": "2026-01-01T00:00:00Z",
  "signature": "..."
}
```

Mirrors can host the same objects.  
Read models can be rebuilt from manifests.  
The public corpus can outlive any single server.

---

## Running locally

The prototype is expected to run locally with Docker Compose.

```bash
git clone https://github.com/YOUR_ORG/pnyx.git
cd pnyx
cp .env.example .env
docker compose up --build
```

Then open:

```text
http://localhost:3000
```

The API should be available at:

```text
http://localhost:8080
```

The worker can use any OpenAI-compatible model endpoint.

Example `.env` values:

```env
OPENAI_BASE_URL=http://localhost:8000/v1
OPENAI_API_KEY=local-dev-key
PNYX_PUBLIC_STORAGE_PATH=./data/public
PNYX_NETWORK=pnyx-demo
```

---

## Local AI support

PNyx should not depend on one AI provider.

The AI layer is designed around OpenAI-compatible endpoints, so it can work with:

- hosted model APIs
- local vLLM
- llama.cpp server
- GPUStack
- OpenRouter-compatible gateways
- future civic model runners

For civic infrastructure, model plurality is a feature, not a bug.

Different models can review the same proposal.  
Their outputs can be compared publicly.

---

## Design principles

### 1. Humans decide

AI supports public reasoning. It does not replace civic authority.

### 2. Public artifacts over private truth

The system should produce inspectable public objects, not hidden backend state.

### 3. Databases are disposable

A database may improve speed and search, but the public corpus is the source of truth.

### 4. Decisions must become action

A decision that cannot be tracked becomes political theater.

PNyx treats implementation as part of the civic loop.

### 5. Institutions are not the only execution channel

Some decisions require public authorities. Others can become community projects, open-source tools, cooperatives, or public-interest ventures.

### 6. Corrections are additive

Mistakes are not erased. They are corrected through new signed artifacts.

### 7. Privacy protects people

Transparency should expose reasoning, power, process, and execution — not endanger citizens.

### 8. Forkability is legitimacy

A civic memory that cannot be mirrored, audited, or forked is not truly public.

---

## Non-goals

PNyx is not:

- a social network
- a polling app
- a petition platform
- a replacement for law
- an AI government
- a blockchain-first governance product
- a moderation-free public forum
- a finished direct-democracy system
- a startup marketplace
- a state procurement platform

PNyx is a prototype for structured public reasoning and accountable civic execution.

---

## Why open source?

A civic system must be inspectable.

The code, schemas, prompts, skill definitions, artifact formats, and import/export logic should be open to public review.

Open source also allows communities to:

- run their own instance
- mirror public artifacts
- test different AI models
- improve civic skills
- create competing interfaces
- audit decision flows
- build local experiments
- preserve public memory
- fork implementation paths
- create public-interest services from accepted decisions

---

## Possible use cases

PNyx can be used as a sandbox for:

- municipal proposals
- public-budget deliberation
- policy analysis
- civic education
- participatory planning
- public infrastructure audits
- citizen assemblies
- community governance
- open-source project governance
- public-interest venture formation
- AI-assisted democratic experiments

---

## Roadmap

### Phase 0 — Demo corpus

- public schemas
- sample proposals
- sample AI reviews
- sample evidence packets
- sample decisions
- sample implementation forks
- sample execution tracking

### Phase 1 — Working local prototype

- proposal intake UI
- API for civic objects
- public file export
- simple manifest generation
- AI worker for review roles
- local read model
- implementation path tracker
- audit log

### Phase 2 — Public mirrors

- static public object hosting
- signed manifests
- import/export tooling
- deterministic rebuild of read models
- mirror registry

### Phase 3 — Governance hardening

- identity and membership model
- anti-abuse mechanisms
- challenge escalation
- human expert protocol
- skill evaluation
- model comparison
- transparency dashboards

### Phase 4 — Sustainability mechanisms

- grant flows
- bounty flows
- public-interest venture templates
- cooperative execution models
- public IP model
- treasury design
- revenue transparency

### Phase 5 — Real pilots

- small communities
- open-source organizations
- local civic experiments
- public-interest research groups
- municipal sandbox deployments

---

## Contribution areas

PNyx needs help with:

- frontend development
- backend/API development
- civic object schemas
- public storage design
- AI skill definitions
- governance research
- UX for deliberation
- implementation path design
- public-interest venture models
- threat modeling
- cryptographic signing
- import/export tools
- documentation
- example civic cases

Contributions should improve clarity, auditability, execution, and public usefulness.

---

## Guiding sentence

> We do not need AI that replaces public judgment.  
> We need AI that helps humans remember, reason, disagree, decide, and execute together.

---

## License

License to be decided.

Recommended options:

- **AGPL-3.0** if the goal is strong public reciprocity for hosted civic infrastructure.
- **Apache-2.0** if the goal is maximum adoption and institutional reuse.

---

## Status

Early prototype.  
Experimental.  
Not production-ready.  
Built for public inspection, critique, and iteration.

PNyx may fail as a product.

But if it produces reusable civic schemas, public reasoning artifacts, implementation patterns, and a better dataset for human-aligned AI, it will still have mattered.

