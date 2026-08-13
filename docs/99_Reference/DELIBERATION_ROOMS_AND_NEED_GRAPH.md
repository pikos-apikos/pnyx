# DELIBERATION_ROOMS_AND_NEED_GRAPH

**Status:** Non-normative design proposal for human review  
**Issue:** [#25 — Design review: deliberation rooms, reproducible AI reviews, simulations, and the need graph](https://github.com/pikos-apikos/pnyx/issues/25)  
**Layer:** 99 — Reference  
**Normative effect:** None.

The candidate rules and artifacts require later normative adoption.

An implementation cannot claim protocol conformance from this document.

---

## 1. Purpose

This document proposes one public deliberation room for each civic subject.

Each room links to one proposal, decision, law, public document, dataset, or other canonical PNyx object.

The room must support these functions if PNyx adopts this design:

- admit approved public source packages for review;
- preserve versioned AI review states;
- accept theoretical sources through a separate researcher route;
- keep simulation separate from evidence;
- connect simulations to possible needs;
- connect those needs to affected proposals, decisions, laws, and public functions;
- disclose compute cost and funding.

The design does not replace the Civic Loop.

The design provides candidate inputs to the Civic Loop.

---

## 2. Requirement Terms

This document uses three modal terms:

- `must` identifies required behavior in the proposed design;
- `must not` identifies prohibited behavior in the proposed design;
- `may` identifies permitted behavior in the proposed design.

These terms define the proposal. They do not make the proposal normative.

PNyx must complete human-approved reconciliation before these terms gain normative force.

---

## 3. Existing Normative Ground

This proposal uses the following existing contracts:

- [GOVERNANCE.md](../10_Constitutional/GOVERNANCE.md) defines human sovereignty and recursive governance.
- [PROTOCOL.md](../20_Protocol_Core/PROTOCOL.md) defines the proposal lifecycle.
- [FUNDING_MODEL.md](../50_Economics/FUNDING_MODEL.md) separates funding capacity from civic legitimacy.
- [SKILLS.md](../60_Skills/SKILLS.md) defines bounded analytic roles and output contracts.
- [EXECUTOR_MODEL.md](../60_Skills/EXECUTOR_MODEL.md) defines versioned, scoped, and replaceable Executors.
- [AI_EPISTEMIC_RISK.md](../60_Skills/AI_EPISTEMIC_RISK.md) defines epistemic and provider-concentration risks.
- [EVIDENCE_PACKET.md](../90_Information/EVIDENCE_PACKET.md) defines source classes, claim traceability, uncertainty, and provenance.
- [PUBLIC_STORAGE_MODEL.md](../90_Information/PUBLIC_STORAGE_MODEL.md) defines public, content-addressed civic memory.
- [AUDIT_LOG.md](../90_Information/AUDIT_LOG.md) defines append-only audit history.

This proposal does not amend these contracts.

A normative contract takes priority if this proposal conflicts with that contract.

---

## 4. Design Principles

### 4.1 One room has one public subject

A Deliberation Room is not a generic chat channel.

Each room has one canonical subject.

The subject may be:

- a PNyx Proposal;
- a public Decision;
- an article of legislation;
- an official public document;
- a registered public dataset;
- a published Evidence Package;
- a published Research Package.

The room must display the subject reference, version, public state, and relevant manifests.

### 4.2 A room does not create authority

Discussion, reviews, and simulations may create public artifacts.

These artifacts do not automatically:

- alter legislation;
- amend a Proposal;
- change civic standing;
- create votes;
- satisfy a protocol gate;
- become evidence through repetition.

The applicable protocol must approve promotion into the Civic Loop.

### 4.3 Public reasoning configuration is public evidence

Prompts and inference settings affect civic AI output.

Each AI Review Snapshot must publish the reasoning contract that produced its output.

The public record must include prompts, material settings, source scope, and tool scope.

The public record must exclude credentials, private personal data, and unrelated infrastructure secrets.

These exclusions must not hide any part of the reasoning contract.

### 4.4 Agent silence is valid

An AI participant does not need to answer every message.

The AI participant may remain silent in these conditions:

- people already resolve the point;
- the AI participant has no material information to add;
- the request is outside the assigned mandate;
- the answer requires an unauthorized or unfunded new run;
- the available review state is insufficient.

The AI participant has bounded presence. The AI participant has no assumed authority.

### 4.5 Public history is append-only

A historical review must not update itself.

A new source creates a new review state.

A new prompt, setting, model, tool, or task also creates a new review state.

The new state must link to the previous state.

---

## 5. Deliberation Room

The proposed `DeliberationRoom` artifact contains:

- `room_id`;
- `subject_ref`;
- `subject_version_or_hash`;
- `subject_class`;
- `current_public_state`;
- `room_policy_version`;
- `allowed_source_classes`;
- `allowed_agent_roles`;
- `linked_review_snapshots[]`;
- `linked_research_packages[]`;
- `linked_simulations[]`;
- `linked_need_nodes[]`;
- `created_at`;
- `audit_ref`.

A room may contain ordinary conversation.

A statement must become a typed public artifact before the statement affects public judgment.

The artifact must be citable.

Message volume is not evidence of standing or support.

---

## 6. Controlled URL Review

### 6.1 No unrestricted search

A Review Executor must not use unrestricted web search.

A Review Executor must not use Google Search.

A person may submit one URL for review.

PNyx must apply this process:

```text
submitted URL
  -> source-class policy
  -> controlled retrieval
  -> canonical URL and issuer verification
  -> immutable snapshot or content capture
  -> content hash and provenance
  -> admitted source package
  -> bounded review task
```

The Review Executor receives the admitted source package.

The Review Executor does not receive an unrestricted browser session.

### 6.2 Ordinary source classes

The initial ordinary-review source list contains:

- `pnyx_public_object`;
- `official_legislation`;
- `official_gazette`;
- `official_public_document`;
- `registered_public_dataset`.

A public Source Registry must control admission.

Canonical-domain rules must verify the source issuer.

Source-class rules must verify each source.

### 6.3 Rejected URLs

PNyx must not open a rejected URL through a hidden process.

PNyx must publish one rejection result.

The result must identify one or more reason codes:

- source class is not allowed;
- issuer is not verified;
- content retrieval failed;
- snapshot is not stable;
- hash verification failed;
- provenance verification failed;
- researcher route is required.

A separate request may propose a new source or source class.

The request must be public and challengeable.

### 6.4 Source Snapshot

The proposed `SourceSnapshot` artifact contains:

- original URL;
- canonical URL;
- source class;
- issuer or author;
- retrieval time;
- publication date or version date;
- content hash;
- captured content or archive reference;
- language;
- jurisdiction;
- validation method;
- validation result;
- redactions;
- redaction reasons;
- superseded snapshot references;
- admission-policy version.

A URL alone does not provide sufficient provenance.

---

## 7. Researcher Route

A `researcher` role may propose sources outside the ordinary source list.

These sources may include:

- academic papers;
- theoretical essays;
- research-institution publications;
- standards;
- technical guidance;
- relevant public websites;
- unconventional theoretical material with clear identification.

PNyx must place these sources in a Research Package.

PNyx must not place these sources directly in the official evidence channel.

The proposed `ResearchPackage` artifact contains:

- source snapshots;
- relevance statement for each source;
- authorship;
- institutional origin;
- publication date;
- retrieval date;
- material claims;
- known limitations;
- known disputes;
- exploration question;
- source category;
- conflicts of interest;
- package version;
- package hash.

The source category must use one of these values:

- `theoretical`;
- `empirical`;
- `mixed`;
- `speculative`.

Research admission means that the source is available for bounded exploration.

Research admission does not mean that the source is official.

Research admission does not prove that the source is true.

Research admission does not make the source sufficient for a public Decision.

---

## 8. AI Review Snapshot

### 8.1 Public execution state

Every civic AI review must produce one immutable `ReviewSnapshot`.

The Review Snapshot must contain:

- review ID;
- review version;
- subject hash;
- source-package hashes;
- Skill ID;
- Skill version;
- role version;
- Executor ID;
- Executor class;
- model provider;
- model name;
- model version or dated identifier;
- complete system prompt;
- complete role prompt;
- complete task prompt;
- prompt-template versions;
- rendered prompt;
- material inference settings;
- allowed tools;
- tool-policy version;
- exact admitted inputs;
- structured output;
- material claims;
- citations;
- unknowns;
- dissent;
- limitations;
- validation results;
- review results;
- timestamps;
- measured usage;
- monetary cost or valuation method;
- compute funder;
- funding terms;
- content hash;
- audit references.

A model name alone does not identify a reproducible review.

A reproducible review requires prompts, inputs, settings, tools, and output.

### 8.2 Excluded data

The public record must not expose:

- credentials;
- API keys;
- private personal data;
- unrelated infrastructure secrets.

The Review Snapshot must declare each exclusion.

The declaration must state the exclusion reason.

An exclusion must not hide the reasoning contract.

An exclusion must not hide material settings or source scope.

### 8.3 Historical Review Explanation

A room may request an explanation from one historical Review Snapshot.

Example request:

> Explain why Review #42/v1 assigned high risk to Article 7. Use only the state of Review #42/v1.

The answer must create a linked `ReviewExplanation` artifact.

The Review Explanation must:

- identify the Review Snapshot;
- use only the admitted sources of that Review Snapshot;
- remain inside the original mandate;
- identify questions that the historical state does not answer;
- keep current knowledge separate from historical knowledge;
- disclose the new inference;
- record new compute provenance.

A new source requires a new Review Snapshot.

A changed task, prompt, setting, tool, or model also requires a new Review Snapshot.

PNyx must not mutate Review #42/v1.

---

## 9. Simulation

### 9.1 Function

A Simulation explores conditional consequences.

A Simulation does not determine truth.

A Simulation does not exercise institutional authority.

The proposed `SimulationRecord` artifact contains:

- simulation question;
- subject references;
- input Evidence Package;
- Research Package;
- explicit assumptions;
- scenario boundaries;
- model snapshot;
- prompt snapshot;
- method;
- outcomes;
- assumption sensitivity;
- failure modes;
- unsupported claim classes;
- affected-object candidates;
- compute declaration;
- funding declaration;
- version;
- hash;
- audit references.

### 9.2 Epistemic label

Each Simulation Record must display these labels:

- exploratory;
- conditional;
- not observed evidence;
- not a vote;
- not an authorized Decision;
- not a change to law;
- not a change to Proposal state.

Repeated runs may expose variance.

Repeated runs do not create political support.

One hundred Simulation Records do not create one hundred votes.

---

## 10. Need Graph

### 10.1 Purpose

The Need Graph records relationships between reality, assumptions, needs, and proposed action.

The Need Graph supports backward review.

A backward review answers these questions:

- Why is this Proposal necessary?
- Which observed or conditional need produced this Proposal?
- Which sources, Simulations, and assumptions support this need?

The Need Graph also supports forward review.

A forward review answers these questions:

- Which objects require review if this scenario becomes real?
- Which Proposals, Decisions, laws, or public functions are affected?
- Which actions mitigate the need?
- Which dependencies block action?

### 10.2 Node types

The proposed node types are:

- `observed_condition`;
- `hypothesis`;
- `simulation`;
- `conditional_need`;
- `observed_need`;
- `evidence_package`;
- `research_package`;
- `proposal`;
- `decision`;
- `legislation`;
- `public_function`;
- `expected_outcome`;
- `observed_outcome`.

### 10.3 Relationship types

The proposed relationship types are:

- `tests`;
- `reveals_if_true`;
- `supported_by`;
- `contradicted_by`;
- `would_affect`;
- `requires`;
- `mitigates`;
- `implements`;
- `produces_expected_outcome`;
- `produced_observed_outcome`;
- `supersedes`;
- `revises`;
- `derived_from`.

Each `NeedGraphEdge` must contain:

- source node;
- target node;
- relationship type;
- assertion author or Executor;
- basis;
- supporting artifact references;
- epistemic state;
- confidence inputs;
- creation time;
- version;
- challenge status;
- supersession history.

The edge must not use ungrounded confidence.

### 10.4 Conditional Need and Observed Need

A model statement must not change a Conditional Need into an Observed Need.

An admitted public evidence event must support this change.

The evidence event must:

1. identify the previous Conditional Need;
2. cite the new evidence;
3. identify confirmed assumptions;
4. identify weakened assumptions;
5. identify falsified assumptions;
6. pass the required validation route;
7. pass the required challenge route;
8. preserve the old state;
9. create a new or revised Need Node.

### 10.5 Proposal notification

A change in epistemic state may notify connected Proposals.

PNyx may also place connected Proposals in a review queue.

A notification must identify:

- the changed Need Node;
- the new evidence;
- the affected objects;
- the required review route.

A notification does not amend a Proposal.

A notification does not approve a Proposal.

A notification does not execute a Proposal.

Human judgment remains necessary.

---

## 11. Compute Cost and Funding

### 11.1 Compute is a material input

Research, review explanation, replication, and simulation consume compute.

A person or organization pays each compute cost.

The public record must distinguish:

- measured usage;
- monetary charge;
- estimated value;
- in-kind value;
- funding source;
- subsidy;
- quota;
- budget;
- provider conditions;
- termination risk;
- withdrawal risk.

### 11.2 Subscription is one hypothesis

A subscription or bounded inference budget is one candidate cost-recovery model.

PNyx has not adopted subscription as its business model.

A subscription is not:

- a constitutional requirement;
- a condition of civic standing;
- a condition of public participation;
- a purchase of Decision rights;
- the only sustainability model.

A pilot must test the effect of subscription on public-agenda control.

The pilot must test whether payment creates unequal control.

### 11.3 Alternative funding hypotheses

The design compares these funding models:

- small recurring member contributions;
- community compute pools;
- public compute pools;
- grants;
- institutional sponsorship;
- university support;
- public-interest organization support;
- AI lab support;
- local infrastructure;
- federated infrastructure;
- mixed funding.

PNyx may use different funding models in different environments.

PNyx may change a funding model after public review.

### 11.4 Lab-funded compute

Lab-funded compute changes who bears the cost.

Lab-funded compute does not remove the cost.

Lab-funded compute does not remove the dependency.

Lab support changes these risks:

- provider control;
- provider concentration;
- model dependence;
- policy dependence;
- service withdrawal;
- model withdrawal;
- portability;
- replacement cost;
- explicit conditions;
- implicit conditions.

PNyx must record lab support as sponsored or in-kind funding.

The public record must contain:

- provider identity;
- valuation method;
- declared unknown value when valuation is unavailable;
- covered models;
- covered workloads;
- quotas;
- rate limits;
- funding conditions;
- data-use terms;
- duration;
- renewal terms;
- termination rights;
- portability plan;
- replacement plan;
- concentration indicators.

A lab may support public capacity.

A lab must not buy:

- classification outcomes;
- routing outcomes;
- Panel composition;
- Proposal success;
- review wording;
- governance authority.

### 11.5 Public access and public return

The proposed access policy contains these rules:

- Reading public subjects must remain open.
- Reading admitted public sources must remain open.
- Reading published AI reviews must remain open.
- Basic civic participation must not depend on payment.
- New compute-intensive exploration consumes a declared budget.
- A published paid run becomes a reusable public artifact.
- A published sponsored run becomes a reusable public artifact.
- Duplicate work may reuse cached public state.
- A new question creates a new metered run.
- A new source, model, prompt, tool, or setting creates a new metered run.

> Funding may purchase computation. Funding must not purchase sovereignty.

### 11.6 Compute Funding Declaration

The proposed `ComputeFundingDeclaration` artifact contains:

- run reference;
- measured usage;
- pricing basis;
- charged amount;
- estimated in-kind value;
- payer;
- sponsor;
- Treasury partition;
- subsidy amount or band;
- provider;
- funding conditions;
- expiry;
- concentration flags;
- dependency flags;
- portability status;
- audit reference.

---

## 12. Candidate Artifacts

This proposal identifies these candidate artifacts:

| Candidate artifact | Purpose | Proposed normative layer |
|---|---|---|
| `DeliberationRoom` | Links discussion and public artifacts to one subject | Participation or Runtime |
| `SourceSnapshot` | Preserves an admitted URL with a hash and provenance | Information |
| `ResearchPackage` | Admits theoretical material for exploration | Skills or Information |
| `ReviewSnapshot` | Preserves reproducible AI execution state | Skills or Information |
| `ReviewExplanation` | Explains one frozen review state | Skills or Information |
| `SimulationRecord` | Preserves assumptions, method, and conditional outcomes | Skills or Information |
| `NeedNode` | Represents a Conditional Need or Observed Need | Information |
| `NeedGraphEdge` | Records one typed relationship | Information |
| `ComputeFundingDeclaration` | Exposes compute cost, payer, subsidy, and dependency | Economics or Information |

This table is not a Schema Registry.

Human review must select the accepted artifacts and their normative layers.

---

## 13. Threats and Failure Modes

The design review must examine:

- prompt injection in an admitted source;
- capture of the Source Registry;
- a misleading canonical URL;
- mutable upstream content;
- advocacy presented as evidence;
- a Simulation presented as fact;
- repeated Simulations that capture public attention;
- hidden prompt drift;
- hidden setting drift;
- ambiguous model versions;
- provider monoculture;
- structural dependence on lab support;
- paid compute that controls agenda priority;
- old review state presented as current knowledge;
- automatic notification that controls the agenda;
- contested causality presented as fact;
- private data in public prompts;
- private data in captured inputs;
- vague cost disclosure;
- hidden funding conditions.

---

## 14. Pilot Questions

A bounded pilot must answer these questions:

1. Does URL admission publish a clear approval or rejection reason?
2. Does each AI Review Snapshot preserve its complete material state?
3. Does a Review Explanation remain inside its historical state?
4. Does a Research Package remain separate from official evidence?
5. Does the interface distinguish Simulation, Conditional Need, and Observed Need?
6. Does the Need Graph support backward and forward review?
7. Does Proposal notification avoid automatic authority?
8. Does compute-cost disclosure remain clear to the public?
9. Do funding models expose different dependency risks?
10. Does the system continue after withdrawal of its largest compute sponsor?

---

## 15. Required Reconciliation

Normative adoption requires a human-approved change to the applicable specifications.

The reconciliation must audit these files:

- `20_Protocol_Core/PROTOCOL.md`;
- `45_Participation/PARTICIPATION_MODEL.md`;
- `45_Participation/ATTENTION_AND_REACH.md`;
- `50_Economics/FUNDING_MODEL.md`;
- `50_Economics/TREASURY.md`;
- `60_Skills/SKILLS.md`;
- `60_Skills/EXECUTOR_MODEL.md`;
- `60_Skills/SKILL_REGISTRY.md`;
- `60_Skills/AI_EPISTEMIC_RISK.md`;
- `80_Runtime/STATE_MACHINE.md`;
- `80_Runtime/EVENT_MODEL.md`;
- `80_Runtime/READ_MODELS.md`;
- `80_Runtime/PERMISSIONS.md`;
- `90_Information/DATA_MODEL.md`;
- `90_Information/EVIDENCE.md`;
- `90_Information/EVIDENCE_PACKET.md`;
- `90_Information/SCHEMAS.md`;
- `90_Information/PUBLIC_STORAGE_MODEL.md`;
- `90_Information/AUDIT_LOG.md`;
- `90_Information/AUDIT_VIEWS.md`.

An implementation must not treat these candidate artifacts as canonical before normative adoption.

---

## 16. Closing Principle

A Simulation may explore conditions beyond current institutional reality.

The path from exploration to power must remain difficult, public, and governed.

The Need Graph records possible needs and affected public objects.

Public evidence changes a possible need into an observed need.

Compute funding controls the available exploration capacity.

Compute funding must never control civic judgment.
