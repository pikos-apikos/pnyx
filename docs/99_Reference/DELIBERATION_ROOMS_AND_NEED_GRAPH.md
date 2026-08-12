# DELIBERATION_ROOMS_AND_NEED_GRAPH

**Status:** Non-normative design proposal for human review  
**Issue:** [#25 — Design review: deliberation rooms, reproducible AI reviews, simulations, and the need graph](https://github.com/pikos-apikos/pnyx/issues/25)  
**Layer:** 99 — Reference  
**Normative effect:** None. Candidate artifacts, relationships, and rules in this document require later reconciliation into the appropriate numbered layers before implementation can claim protocol conformance.

---

## 1. Purpose

This document proposes a composition of existing PNyx ideas into a public deliberation environment for each civic subject.

The proposed environment should allow people to:

- discuss a proposal, decision, law, public document, or other canonical PNyx object;
- submit an allowed public URL for bounded review;
- inspect the exact public state used by an AI reviewer;
- ask a frozen review to explain its prior result without silently changing history;
- let a researcher introduce theoretical papers or websites into an explicitly exploratory simulation;
- connect simulations to the needs, proposals, decisions, laws, and public functions they would affect if their assumptions became real;
- see who funded the compute used to produce each run and under which terms.

The design is not a replacement for the Civic Loop. It is a public reasoning surface that may produce candidate inputs for the loop.

---

## 2. Existing Normative Ground

This proposal builds on, but does not amend:

- [GOVERNANCE.md](../10_Constitutional/GOVERNANCE.md): human sovereignty and recursive governance;
- [PROTOCOL.md](../20_Protocol_Core/PROTOCOL.md): the proposal lifecycle;
- [FUNDING_MODEL.md](../50_Economics/FUNDING_MODEL.md): money builds capacity but does not confer legitimacy;
- [SKILLS.md](../60_Skills/SKILLS.md): bounded analytic roles and output contracts;
- [EXECUTOR_MODEL.md](../60_Skills/EXECUTOR_MODEL.md): versioned, scoped, replaceable executors;
- [AI_EPISTEMIC_RISK.md](../60_Skills/AI_EPISTEMIC_RISK.md): epistemic and provider-concentration risks;
- [EVIDENCE_PACKET.md](../90_Information/EVIDENCE_PACKET.md): source classes, claim traceability, uncertainty, and provenance;
- [PUBLIC_STORAGE_MODEL.md](../90_Information/PUBLIC_STORAGE_MODEL.md): public-file-first, content-addressed civic memory;
- [AUDIT_LOG.md](../90_Information/AUDIT_LOG.md): append-only audit history.

Where this document conflicts with a normative specification, the normative specification wins.

---

## 3. Design Principles

### 3.1 The room is anchored to a public object

A deliberation room is not a generic chat channel. It is anchored to one canonical subject, such as:

- a PNyx proposal;
- a public decision;
- an article of legislation;
- an official public document;
- a registered dataset;
- a published evidence or research package.

The room must expose the subject reference, version, current public state, and relevant manifests.

### 3.2 The room does not manufacture authority

Discussion, reviews, and simulations may generate useful public artifacts. They do not automatically:

- alter legislation;
- amend a proposal;
- change civic standing;
- create votes;
- satisfy a protocol gate;
- become evidence merely through repetition.

Promotion into the Civic Loop requires the applicable protocol process.

### 3.3 Public reasoning configuration is part of the public record

For a civic AI review, prompts and inference settings are not private implementation details. The review must publish the reasoning contract that shaped the output.

Credentials, private personal data, and unrelated infrastructure secrets remain excluded. Their exclusion does not justify withholding the prompt, model configuration, source scope, tool scope, or material generation settings.

### 3.4 Silence is a valid agent action

An AI participant need not answer every message. It may remain silent when:

- humans are already resolving the point;
- it has no material addition;
- the request falls outside its mandate;
- a response would require a new run that has not been authorized or funded;
- the available state is insufficient.

The AI moderates its own participation through bounded presence, not assumed authority.

### 3.5 History is append-only

A historical review does not silently update itself. New evidence, prompts, settings, models, or tools create a new state linked to the old one.

---

## 4. Deliberation Room

A candidate `DeliberationRoom` binds together:

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

The room may contain ordinary conversation, but any statement that is intended to affect public judgment should be promoted into a typed, citable artifact.

Message volume is not a measure of standing or support.

---

## 5. Controlled URL Review Surface

### 5.1 No unrestricted browsing

A review executor does not receive an open browser or a request such as "search the web."

A person may submit a URL. PNyx must first decide whether the URL can become a reviewable public source.

The candidate flow is:

```text
submitted URL
  -> source-class policy
  -> retrieval by a controlled fetcher
  -> canonical URL and issuer verification
  -> immutable snapshot or content capture
  -> hash and provenance
  -> admitted source package
  -> bounded review task
```

The executor receives the admitted package, not an unrestricted web session.

### 5.2 Ordinary allowed source classes

An initial ordinary-review allowlist may include:

- `pnyx_public_object`;
- `official_legislation`;
- `official_gazette`;
- `official_public_document`;
- `registered_public_dataset`.

Admission should depend on a public source registry, canonical-domain rules, and source-class-specific validation.

### 5.3 Rejection behavior

If a URL is not currently allowed, the system must not open it secretly.

It should return a visible result such as:

- source class not allowed;
- issuer not verified;
- content not retrievable;
- snapshot not stable;
- hash or provenance failure;
- researcher route required.

A separate, challengeable request may propose adding a source or source class to the registry.

### 5.4 Source snapshot

A candidate `SourceSnapshot` may include:

- original and canonical URL;
- source class;
- issuer or author;
- retrieval time;
- publication or version date;
- content hash;
- captured content or archive reference;
- language;
- jurisdiction;
- validation method and result;
- redactions and reasons;
- superseded snapshot references;
- admission policy version.

A URL alone is not sufficient provenance.

---

## 6. Researcher Route

A `researcher` role may propose sources that are not part of the ordinary official-source allowlist, including:

- academic papers;
- theoretical essays;
- research-institution publications;
- standards and technical guidance;
- relevant public websites;
- unconventional but clearly identified theoretical material.

These sources enter a `ResearchPackage`, not the ordinary evidence channel.

A candidate `ResearchPackage` should declare:

- the source snapshots;
- why each source is relevant;
- authorship and institutional origin;
- publication and retrieval dates;
- material claims;
- known limitations and disputes;
- the question the package is intended to explore;
- whether the package is theoretical, empirical, mixed, or speculative;
- conflicts of interest;
- package version and hash.

Research admission means "suitable for bounded exploration." It does not mean "official," "true," or "sufficient for public decision."

---

## 7. Reproducible AI Review Snapshot

### 7.1 Public execution state

Every civic AI review should produce an immutable `ReviewSnapshot` containing at least:

- review ID and version;
- subject and source-package hashes;
- skill and role version;
- executor ID and class;
- model provider, model name, version or dated identifier;
- complete public system, role, and task prompts;
- prompt-template versions and rendered prompt;
- inference settings, including material sampling and context settings;
- allowed tools and tool-policy version;
- exact admitted inputs;
- output and structured claims;
- citations, unknowns, dissent, and limitations;
- validation and review results;
- timestamps;
- token, accelerator-time, or other available usage measures;
- declared monetary cost or valuation method;
- compute funder and funding terms;
- content hash and audit references.

A model name without its prompts, inputs, settings, and tool boundary is not a reproducible public review record.

### 7.2 What remains private

The public record should not expose:

- credentials or API keys;
- private personal data that the protocol is not allowed to publish;
- unrelated infrastructure secrets.

These exclusions must be declared. They must not be used to hide the reasoning contract or material settings.

### 7.3 Calling a historical reviewer into the room

A room may call a prior AI reviewer by its snapshot:

> Explain why Review #42/v1 assigned high risk to Article 7, using only the state available to that review.

The response should become a linked `ReviewExplanation` artifact. It must:

- name the snapshot it explains;
- remain within the snapshot's admitted sources and mandate;
- identify claims that cannot be answered from that state;
- avoid presenting current knowledge as historical knowledge;
- disclose that a new inference occurred and record its own compute provenance.

If a participant adds a new source or changes the task, prompt, settings, tools, or model, PNyx creates a new review state. It does not mutate Review #42/v1.

---

## 8. Simulation

### 8.1 Role

A simulation explores conditional consequences. It does not decide what is true and does not exercise institutional authority.

A candidate `SimulationRecord` should include:

- simulation question;
- subject references;
- input evidence package;
- research package, if any;
- explicit assumptions;
- scenario boundaries;
- model and prompt snapshot;
- method;
- outcomes;
- sensitivity to assumptions;
- failure modes;
- claims the simulation cannot support;
- affected-object candidates;
- compute and funding declaration;
- version, hash, and audit references.

### 8.2 Epistemic label

Simulation output must remain visibly labeled:

- exploratory;
- conditional;
- not observed evidence;
- not a vote;
- not an authorized decision;
- not a change to law or proposal state.

Running the same scenario one hundred times may improve exploration or reveal variance. It does not create one hundred units of political support.

---

## 9. The Need Graph

### 9.1 Purpose

The need graph records how reality, assumptions, and proposed action relate.

It should let a person ask backward:

- Why is this proposal considered necessary?
- Which observed or conditional need produced it?
- Which sources, simulations, and assumptions support that need?

It should also let a person ask forward:

- If this scenario becomes real, which proposals, decisions, laws, or public functions should be reconsidered?
- Which actions might mitigate the resulting need?
- Which dependencies block action?

### 9.2 Candidate node types

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

### 9.3 Candidate relationship types

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

Each edge should record:

- source and target;
- relationship type;
- assertion author or executor;
- basis and supporting artifact references;
- epistemic status;
- confidence inputs rather than ungrounded confidence;
- creation time;
- version;
- challenge status;
- supersession history.

### 9.4 Conditional-to-observed transition

A conditional need must not become an observed need because a model repeats it.

The transition requires an admitted public evidentiary event under a future normative rule. The event should:

1. identify the previously conditional need;
2. cite new evidence;
3. state which assumptions were confirmed, weakened, or falsified;
4. pass the required validation and challenge route;
5. preserve the old state;
6. create a new observed or revised need node.

### 9.5 Waking affected proposals

When new evidence changes a need's epistemic status, PNyx may notify or queue connected proposals for reconsideration.

"Wake" should mean:

- surface the relevant change;
- identify affected objects;
- request review under the applicable protocol;
- preserve human judgment.

It must not mean automatic amendment, automatic approval, or automatic execution.

---

## 10. Compute Cost and Funding Hypotheses

### 10.1 Compute is a material input

Research, review explanation, replication, and simulation consume compute. Even when the user is not directly charged, the cost is borne by someone.

The public record should therefore distinguish:

- compute usage;
- monetary charge;
- estimated or in-kind value;
- funding source;
- subsidy;
- quota or budget;
- provider conditions;
- termination or withdrawal risk.

### 10.2 Subscription is one hypothesis

A subscription or bounded inference budget may be a reasonable way to finance optional exploration because inference has real cost.

At this stage, subscription remains a hypothesis. It is not:

- a constitutional requirement;
- a condition of civic standing;
- a decision-right purchase;
- the only possible sustainability model.

A later pilot should test whether subscription can support compute without turning ability to pay into control over the public agenda.

### 10.3 Alternative funding hypotheses

Candidate alternatives or complements include:

- small recurring member contributions;
- community or public compute pools;
- grants;
- institutional sponsorship;
- universities or public-interest organizations;
- compute supplied or funded by AI labs;
- local or federated infrastructure;
- mixed funding with explicit treasury partitions.

The funding model may vary by environment and over time.

### 10.4 Lab-funded compute changes the economic question

If AI labs provide or fund compute, direct subscription pressure may decrease or disappear for the subsidized surface.

That does not make compute costless. It changes:

- who bears the cost;
- who controls continued availability;
- provider concentration;
- model and policy dependence;
- portability requirements;
- withdrawal risk;
- the possibility of explicit or implicit conditions.

Lab-supplied compute should be recorded as in-kind or sponsored funding with:

- provider identity;
- valuation method or declared unknown value;
- covered models and workloads;
- quotas and rate limits;
- conditions;
- data-use terms;
- duration and renewal terms;
- termination rights;
- portability and replacement plan;
- concentration indicators.

A lab may support public capacity. It may not buy classification outcomes, routing, panel composition, proposal success, review wording, or governance authority.

### 10.5 Public access and public return

A candidate policy for testing is:

- reading public subjects, sources, and already published reviews remains open;
- basic civic participation does not depend on payment;
- new compute-intensive exploration consumes a declared budget;
- publicly released paid or sponsored runs become reusable public artifacts;
- duplicate work may reuse cached public state;
- genuinely new questions, sources, models, or settings create new metered runs.

This tests the principle:

> Funding may purchase computation. It may not purchase sovereignty.

### 10.6 Candidate compute funding declaration

A `ComputeFundingDeclaration` may include:

- run reference;
- measured usage;
- pricing basis;
- charged amount;
- estimated in-kind value;
- payer or sponsor;
- treasury partition;
- subsidy amount or band;
- provider;
- conditions;
- expiry;
- concentration and dependency flags;
- portability status;
- audit reference.

---

## 11. Candidate Artifact Inventory

This reference draft proposes the following artifacts for later review:

| Candidate artifact | Purpose | Likely normative home if adopted |
|---|---|---|
| `DeliberationRoom` | Bind discussion and public artifacts to one subject | Participation / Runtime |
| `SourceSnapshot` | Stabilize an allowed URL with provenance and hash | Information |
| `ResearchPackage` | Admit theoretical material for exploration | Skills / Information |
| `ReviewSnapshot` | Preserve reproducible AI execution state | Skills / Information |
| `ReviewExplanation` | Answer from a frozen review state | Skills / Information |
| `SimulationRecord` | Preserve assumptions, method, and conditional outcomes | Skills / Information |
| `NeedNode` | Represent conditional or observed need | Information |
| `NeedGraphEdge` | Express typed causal or institutional relationships | Information |
| `ComputeFundingDeclaration` | Expose compute cost, payer, subsidy, and dependency | Economics / Information |

This table is not a schema registry. It is a decision surface.

---

## 12. Threats and Failure Modes

Review should specifically test:

- prompt injection inside admitted sources;
- source-registry capture;
- misleading canonical URLs or mutable upstream pages;
- researcher laundering advocacy into "evidence";
- simulation presented as prediction or fact;
- repeated simulations used to monopolize public attention;
- hidden prompt or setting drift;
- model-version ambiguity;
- provider monoculture;
- lab subsidy becoming structural dependence;
- paid compute becoming agenda priority;
- cached review state being mistaken for current knowledge;
- automatic proposal wake-up becoming automatic agenda control;
- need-graph edges presenting contested causality as settled fact;
- privacy leakage through public prompts or captured inputs;
- cost disclosure that is too vague to reveal dependence.

---

## 13. Pilot Questions

A bounded prototype should answer:

1. Can a participant submit an allowed URL and understand why it was admitted or rejected?
2. Can another person reproduce the material state of an AI review?
3. Can a historical reviewer explain its output without importing later knowledge?
4. Can a researcher package a theoretical source without it being confused with official evidence?
5. Can the UI keep simulation, conditional need, and observed need visibly distinct?
6. Can users traverse from a proposal back to its need and forward to affected institutional objects?
7. Does waking a proposal help review without creating automatic authority?
8. Does public compute-cost disclosure remain intelligible?
9. Do subscription, public-pool, local-compute, and lab-sponsored scenarios produce different dependency risks?
10. Can the system survive withdrawal of its largest compute sponsor?

---

## 14. Required Reconciliation Before Normative Adoption

If this design is accepted in principle, a later normative change must audit at least:

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

No implementation should treat candidate artifacts in this document as canonical before that reconciliation is approved.

---

## 15. Closing Principle

A simulation should be free to explore far beyond current institutional reality.

The path from exploration to power should remain difficult, public, and governed.

The need graph preserves that distinction: it lets society remember what might matter, connect it to what would be affected, and revisit it when reality supplies evidence.

Compute funding determines how much exploration is possible. It must never determine whose conclusions become sovereign.
