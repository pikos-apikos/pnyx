# THREAT_MODEL

## 1. Purpose

This document identifies the main ways the system can be captured, manipulated, degraded, or silently recentralized.

It is not limited to cybersecurity.
It includes procedural, epistemic, institutional, and socio-technical threats.

The central premise is:
**capture pressure is normal, not exceptional.**

---

## 2. Threat Modeling Stance

The system must assume adversaries may target:
- legitimacy,
- classification,
- timing,
- panel composition,
- evidence quality,
- briefing presentation,
- execution routing,
- framework evolution,
- public memory.

The most dangerous attacks are often not loud system failures.
They are quiet shifts in hidden leverage points.

---

## 3. Protected Assets

The system must protect at least the following assets:
- human political sovereignty,
- fairness of process,
- integrity of governance-layer classification,
- integrity of parameter epochs,
- integrity of the skill panel,
- visibility of disagreement,
- reliability of audit history,
- boundedness of emergency powers,
- contestability of outputs,
- plurality of civic cognition,
- ability to revise frameworks without bypass.

---

## 4. Trust Boundaries

Key trust boundaries include:
- citizen input ↔ intake layer,
- intake layer ↔ classification layer,
- classification layer ↔ orchestration layer,
- orchestration layer ↔ skill runtime,
- skill runtime ↔ synthesis layer,
- synthesis layer ↔ public briefing layer,
- decision layer ↔ execution layer,
- active rules ↔ proposed rule changes,
- audit publication ↔ storage/infrastructure,
- public knowledge input ↔ skill update path.

Any boundary that silently collapses becomes a likely capture site.

---

## 5. Adversary Classes

### 5.1 Tactical Proposer
A participant attempting to shape inputs, scope, or timing for a preferred outcome.

### 5.2 Colluding Panel Maintainer
A maintainer who biases skill configuration, context, or availability.

### 5.3 Platform Operator
An infrastructure operator who can alter logs, suppress outputs, or privilege certain flows.

### 5.4 Concentrated Interest Actor
A corporation, state organ, party, media bloc, or wealthy coalition seeking structural advantage.

### 5.5 Sybil Operator
An actor attempting to multiply participation identity or simulate broad support.

### 5.6 Prompt / Model Poisoner
An actor influencing skill behavior through training data, context payloads, or hidden prompt mutation.

### 5.7 Emergency Opportunist
An actor seeking to convert urgency into permanent exception.

### 5.8 Framework Gamer
An actor who cannot win under current rules and therefore targets the rules themselves.

### 5.9 Knowledge Encloser
An actor attempting to become the exclusive runtime of civic memory or reasoning.

### 5.10 Bureaucratic Exhaustion Attacker
An actor attempting to halt or degrade the civic loop by flooding the system with frivolous challenges, appeals, or procedural objections.

---

## 6. Attack Surfaces by Layer

## 6.1 Intake Layer
Threats:
- spam and overload,
- duplicate proposal flooding,
- ambiguity injection,
- hidden scope expansion,
- urgency laundering,
- bootstrap-boundary probing,
- bureaucratic exhaustion (challenge spam).

Controls:
- required structured fields,
- duplicate clustering,
- explicit scope recording,
- urgency challenge path,
- rejection with audit trace,
- zero-knowledge stratified cost nullifiers (ZK-SCN),
- asynchronous batching of challenges.

## 6.2 Classification Layer
Threats:
- downgrading constitutional proposals to policy,
- suppressing framework-change flags,
- inconsistent layer assignment,
- discretionary escalation abuse.

Controls:
- logged rationale,
- explicit spillover fields,
- challengeable classifications,
- fixed bootstrap escalation rules,
- adversarial review triggers.

## 6.3 Orchestration Layer
Threats:
- panel stuffing,
- omission of required skill classes,
- covert parameter mutation,
- rerouting to weak review paths,
- selective reruns.

Controls:
- required seat classes,
- panel lock,
- parameter epoch binding,
- append-only audit,
- deterministic validation checks.

## 6.4 Skill Runtime Layer
Threats:
- hidden prompt drift,
- context poisoning,
- selective evidence exposure,
- vendor/model monoculture,
- covert skill replacement,
- confidence inflation,
- hidden tool use.

Controls:
- versioned skills,
- logged input bundles,
- controlled context policy,
- comparative runs,
- visible uncertainty fields,
- replaceable skills,
- public registry governance.

## 6.5 Synthesis Layer
Threats:
- false consensus generation,
- dissent suppression,
- summary laundering,
- normative claims presented as facts,
- evidence laundering.

Controls:
- mandatory dissent field,
- explicit unknowns,
- evidence sufficiency note,
- retained raw outputs,
- synthesis challenge path.

## 6.6 Public Briefing Layer
Threats:
- framing bias,
- misleading simplification,
- selective omission,
- manipulative order or emphasis,
- inaccessible presentation.

Controls:
- plain-language plus full-depth briefing,
- stable packet format,
- visible panel composition,
- visible timing and thresholds,
- audit links.

## 6.7 Decision Layer
Threats:
- quorum manipulation,
- vote timing abuse,
- threshold retuning,
- decision-event invalidation without trace,
- selective participation suppression.

Controls:
- fixed thresholds by epoch,
- immutable review windows,
- visible decision rules,
- independent event logging,
- appeal path.

## 6.8 Execution Layer
Threats:
- route substitution,
- implementation drift,
- capture through contractors or vendors,
- hidden privatization or hidden centralization,
- operational monopoly.

Controls:
- explicit route justification,
- implementation boundaries,
- review triggers,
- dependency mapping,
- anti-capture monitoring.

## 6.9 Audit Layer
Threats:
- log tampering,
- silent deletion,
- audit incompleteness,
- replay impossibility,
- backdated events,
- shadow audit streams.

Controls:
- append-only event stream,
- chained hashes,
- replication,
- public verification tools,
- audit completeness checks.

## 6.10 Meta-Governance Layer
Threats:
- active-case retuning,
- framework bypass,
- parameter compression under urgency,
- constitutional downgrade,
- “temporary” rule becoming permanent.

Controls:
- prospective-only activation,
- higher thresholds,
- meta-delay,
- constitutional impact analysis,
- scheduled review of meta-changes.

---

## 7. Core Threat Categories

## 7.1 Capture of Panel Composition
**Threat:** adversary influences which skills appear in a panel.

**Examples:**
- omitting anti-capture skill,
- replacing rights skill with weak variant,
- inflating panel with redundant proponent skills.

**Mitigations:**
- minimum five-skill quorum,
- mandatory skill classes,
- panel lock,
- public seat list,
- conflict-of-interest challenges.

## 7.2 Classification Gaming
**Threat:** proposal is classified into a weaker review path.

**Examples:**
- governance change presented as ordinary policy,
- constitutional effects hidden under technical wording.

**Mitigations:**
- logged classification rationale,
- constitutional spillover flag,
- challenge protocol,
- anti-capture review.

## 7.3 Timing Manipulation
**Threat:** outcome is influenced by accelerating or delaying the process.

**Examples:**
- shortening review window for a favorable proposal,
- dragging an unwanted proposal until attention collapses,
- opening decision during low-participation period.

**Mitigations:**
- fixed ex ante timing rules,
- epoch-bound windows,
- no active-case retuning,
- visible clocks.

## 7.4 Threshold Manipulation
**Threat:** outcome is influenced by changing approval conditions.

**Examples:**
- lowering threshold for a framework change,
- redefining quorum in a live case.

**Mitigations:**
- fixed threshold rules by epoch,
- prospective-only updates,
- meta-governance safeguards.

## 7.5 Evidence Laundering
**Threat:** weak inference is presented as settled evidence.

**Examples:**
- model-generated synthesis treated as source evidence,
- contested data presented without dispute status,
- missing evidence hidden behind confident prose.

**Mitigations:**
- evidence classes,
- explicit `unknown` state,
- evidence sufficiency note,
- raw evidence references.

## 7.6 Prompt / Context Poisoning
**Threat:** skill behavior is steered through hidden context or malicious prompt edits.

**Examples:**
- injecting slanted context bundles,
- hidden system prompt changes,
- retrieval poisoning.

**Mitigations:**
- versioned prompts,
- logged context bundle refs,
- reproducible runs,
- comparative model checks.

## 7.7 False Consensus
**Threat:** synthesis collapses plurality into a fake unified answer.

**Examples:**
- minority view omitted,
- strong objections softened into “trade-offs”,
- uncertainty summarized away.

**Mitigations:**
- mandatory dissent fields,
- preserved raw outputs,
- challengeable synthesis,
- packet schema requirements.

## 7.8 Emergency Path Abuse
**Threat:** emergency process becomes shortcut governance.

**Examples:**
- ordinary policy labeled urgent,
- urgent measures left active indefinitely,
- emergency execution expands beyond narrow scope.

**Mitigations:**
- explicit eligibility rules,
- auto-expiry,
- mandatory post-hoc review,
- limited scope fields.

## 7.9 Memory Monopoly / Knowledge Enclosure
**Threat:** one model, vendor, or service becomes the exclusive runtime of civic cognition.

**Examples:**
- proprietary black box becomes the only trusted synthesizer,
- audit and reasoning depend on one unavailable model,
- civic memory exists only inside a vendor stack.

**Mitigations:**
- replaceable skills,
- public registries,
- portable formats,
- audit independence from one runtime,
- no single-model authority.

## 7.10 Audit Subversion
**Threat:** history becomes incomplete or untrustworthy.

**Examples:**
- deleted classification events,
- overwritten skill outputs,
- hidden reruns,
- unlogged route changes.

**Mitigations:**
- append-only audit events,
- cryptographic chaining,
- mirrored storage,
- public replay tools.

---

## 8. Bootstrap-Specific Threats

Bootstrap is especially vulnerable because:
- the human core is small,
- trust is still being formed,
- operational roles are concentrated,
- legitimacy is fragile.

### 8.1 Founder Capture
Early maintainers may hard-code their own worldview into enduring defaults.

**Control:** hard-fixed bootstrap parameters, public review, recursive governance.

### 8.2 Convenience Capture
The system may silently accept shortcuts because the group is small and tired.

**Control:** fixed audit minimums, mandatory packet fields, no silent fast path.

### 8.3 Scope Creep
The bootstrap system may take on decisions it is not ready to govern.

**Control:** explicit bootstrap scope limits, advisory-only mode where needed.

### 8.4 Hidden Operator Privilege
A few technical maintainers may gain unofficial control through infrastructure.

**Control:** public logging, role separation, mirrored audit, open configuration review.

---

## 9. Detection Signals

The system should monitor for signals such as:
- repeated omission of the same skill class,
- sudden drops in dissent frequency,
- abnormal emergency usage,
- repeated same-case parameter change attempts,
- route bias toward one execution thread,
- unexplained increases in high-confidence outputs,
- audit gaps,
- sustained classification disputes,
- recurring dependence on one model/vendor/skill owner,
- low diversity in panel composition.

These are not proof of compromise, but they are risk indicators.

---

## 10. Threat Severity Heuristic

A threat should be treated as highest severity when it can silently change any of the following:
- who is heard,
- what governance layer applies,
- which rules are active,
- how long scrutiny lasts,
- what threshold decides the case,
- what counts as evidence,
- whether dissent remains visible,
- which execution route becomes normal.

This heuristic is more useful here than purely technical exploit scoring.

---

## 11. Control Matrix

| Threat | Primary Control | Secondary Control |
|---|---|---|
| Panel capture | required skill classes | public panel list |
| Misclassification | logged rationale | challenge path |
| Timing abuse | fixed review windows | epoch binding |
| Threshold abuse | prospective-only updates | meta-delay |
| Prompt poisoning | versioned skills | reproducible runs |
| False consensus | mandatory dissent | raw output retention |
| Emergency abuse | auto-expiry | ex post review |
| Audit tampering | append-only chain | mirrored storage |
| Knowledge monopoly | replaceable skills | portable formats |
| Execution capture | explicit route justification | post-decision review |
| Bureaucratic exhaustion | zero-knowledge stratified cost nullifiers | asynchronous batching |

---

## 12. Residual Risk

No architecture eliminates politics, collusion, or concentrated power.

This threat model therefore aims to:
- increase the visibility of attack surfaces,
- make capture more expensive,
- make silent procedural mutation harder,
- preserve enough traceability that abuse can be challenged.

The system should be judged not by whether threats disappear,
but by whether they remain visible, bounded, and contestable.

---

## 13. Closing Rule

The main enemy is not only malicious code.
It is hidden leverage.

The system should be designed so that:
- power cannot move quietly,
- exceptions cannot become defaults invisibly,
- cognition cannot collapse into monopoly,
- and governance cannot be changed without appearing as governance change.
