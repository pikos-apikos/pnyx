# Suggestions for Improvement: Addressing the Structural Critique

*Companion document to STRUCTURAL_CRITIQUE.md*
*Date: April 2026*

---

## 1. Deliberation Without Execution

**Problem**: PNyx optimizes for deliberation quality at the expense of decision speed. The 19-state proposal machine takes weeks to months.

**Suggestions**:

- **Add a fast-track routing tier**: Define a "routine decision" classification below policy layer that uses a simplified 3-state machine (SUBMITTED → REVIEWED → DECIDED) with 48-72 hour turnaround. Reserve the full adversarial panel for consequential decisions. The threshold for fast-track should be clearly defined (e.g., budget under $X, affected population under Y, no constitutional implications).

- **Establish binding authority**: Rather than purely advisory outputs, negotiate formal recognition with at least one jurisdiction or organization where PNyx recommendations become binding decisions. This is harder than being advisory but eliminates the execution gap. Even a single city council agreeing to route certain decisions through PNyx changes the nature of the output.

- **Define "decision latency budgets"**: Add timing parameters to the state machine. If a proposal exceeds the latency budget for its classification tier, it either escalates automatically or defaults to a designated fallback decision-maker. This creates pressure to maintain deliberation speed, not just deliberation quality.

---

## 2. Bootstrap Paradox

**Problem**: Skills, evaluation infrastructure, funding, community, and proposals all depend on each other simultaneously.

**Suggestions**:

- **Define "PNyx Core" as a single-skill deliberator**: During bootstrap, replace the 5-skill adversarial panel with a single "generalist" skill that provides a structured analysis. This isn't the full anti-capture apparatus, but it produces usable outputs faster. Name this explicitly as a bootstrap mode with a defined sunset — e.g., "Single-Skill Deliberation Mode ends when the first Tier-2 skill achieves governance grade."

- **Sequential bootstrap milestones with hard gates**: Rather than building everything simultaneously, define a sequence: (1) fund one full-time bootstrap team, (2) run first 10 proposals through single-skill deliberation, (3) use those outputs to attract first 100 community members, (4) use community to fund first Tier-2 evaluation, (5) use evaluated skills to run full adversarial panels. Each gate requires demonstrated outcomes, not just funding.

- **Distinguish "constitutional debt" from "constitutional path dependency"**: The current framing implies debt can be paid. Acknowledge explicitly that some bootstrap compromises may be difficult to unwind — a system that starts without adversarial panels may develop institutional habits that resist adding them later. Track which compromises create path dependencies and prioritize unwinding those first.

---

## 3. Classification Gaming

**Problem**: Sophisticated actors will learn the classification grammar faster than ordinary participants and exploit categorical thresholds.

**Suggestions**:

- **Add "classification fingerprinting" to the audit log**: Track how often each actor's proposals cluster near classification thresholds. If a small set of actors consistently drafts proposals just below constitutional thresholds, flag this as a structural pattern in the public audit views. Make classification gaming publicly visible even if not preventable.

- **Require "classification diversity" per actor**: After N proposals from the same actor are classified below constitutional threshold, require the next proposal from that actor to be reviewed by a randomly selected citizen panel before classification rules apply. This adds friction specifically for actors gaming thresholds, without affecting ordinary participants.

- **Make classification criteria publicly negotiated**: Before each epoch, publish the draft classification criteria and allow public comment. Sophisticated actors will game whatever rules exist, but if the rules themselves are subject to gaming, the gaming becomes a political question rather than a technical one — which is appropriate, since classification thresholds are fundamentally political choices.

---

## 4. Schema vs. Deliberation Quality

**Problem**: A well-formatted packet can still be epistemically weak. Format compliance doesn't indicate genuine adversarial engagement.

**Suggestions**:

- **Add "engagement scoring" based on revision history**: Track whether packet fields changed substantially between initial draft and final submission. A packet where all fields stayed nearly identical after panel review is performing compliance, not adversariality. Require that significant packets (above a threshold of impact) show meaningful revision — the strongest case for a position should be harder to write if the position is wrong.

- **Require "goodbye letters" from defeated perspectives**: After a decision, each skill in the panel must submit a one-paragraph statement acknowledging what evidence or reasoning could change their assessment. This makes epistemic humility a structured output rather than an optional field. It doesn't guarantee honesty, but it creates a performance cost for never acknowledging uncertainty.

- **Add "representative challenge" as a skill role**: Create a new mandatory skill role — "representative_challenge" — whose job is specifically to identify when other skills are performing compliance rather than genuinely engaging. This is harder to perform than actual critique because it requires identifying specific failures in specific other outputs.

---

## 5. Epoch Timing Games

**Problem**: Epoch binding creates incentives for strategic submission timing around epoch transitions.

**Suggestions**:

- **Add a "proposal submission lockout" period**: Two weeks before an epoch boundary, no new proposals can be submitted. This creates a buffer zone where epoch transition timing doesn't advantage late or early submissions. Proposals submitted in the lockout period are automatically assigned to the next epoch.

- **Make epoch timing public and predictable**: If epoch boundaries are predictable (e.g., fixed calendar dates with 6 months notice), strategic timing becomes less advantageous because everyone can anticipate the same transitions. The current specification implies epoch timing can be influenced — make it as predictable as a fiscal year.

- **Randomize assignment for near-boundary submissions**: Proposals submitted within the last 30 days of an epoch have their epoch assignment randomized (with bias toward the next epoch). This eliminates the advantage of precision timing without banning submissions.

---

## 6. Accountability Theater

**Problem**: The system is technically legible but cognitively inaccessible. Verification is possible but comprehension is rare.

**Suggestions**:

- **Create "decision summaries" as first-class outputs**: For every decided proposal, generate a 200-word decision summary written at an 8th-grade reading level, covering: what was decided, what the main disagreement was, what the panel's minority view was, and what happens next. This is a human-written (or human-edited) summary, not an AI-generated extraction. It is the public face of the decision, separate from the full audit trail.

- **Establish "civic translation" as a funded role**: Create a budget line for community intermediaries — journalists, advocates, community organizers — who translate PNyx outputs into accessible forms for specific communities. Fund this through the SkillCommons or a dedicated translation partition. The specification already mentions this possibility; make it a funded line item with explicit success metrics.

- **Add "explainability requirements" to skill outputs**: Each skill in a panel must submit, alongside the formal packet, a one-paragraph "plain language explanation" of their assessment in under 200 words. These explanations are aggregated into the decision summary. Skills that produce incomprehensible outputs are flagged for evaluation review.

---

## 7. Emergency Resolves Wrong Crisis

**Problem**: The emergency system handles acute crises but is blind to slow erosion.

**Suggestions**:

- **Add a "governance health dashboard" as a first-class read view**: Track metrics like: average proposal resolution time, challenge rate by actor, classification stability (how often classifications are challenged and overturned), emergency renewal frequency, and participation by demographic cohort. Present this dashboard publicly as a "system health" view. Slow emergencies become visible as trends, not just acute events.

- **Add a "governance erosion" trigger**: Define specific patterns that trigger automatic escalation even without an acute emergency — e.g., if average proposal resolution time increases by more than 50% quarter-over-quarter for three consecutive quarters, require an automatic structural review. This is harder to abuse than emergency declarations because the trigger is quantitative and visible.

- **Separate "emergency response" from "structural review"**: The current system conflates acute emergency handling with slow-erosion response. Add a separate "structural review" process that is slower than emergency response but faster than normal governance — 30-day turnaround with mandatory panel review — triggered by governance health metrics, not just acute crisis.

---

## 8. Skills Evaluation Recursion

**Problem**: Every evaluation layer has a "who evaluates the evaluators" problem. The recursion doesn't terminate.

**Suggestions**:

- **Define a "founding evaluator" cohort with named individuals**: Rather than trying to solve the recursion, acknowledge it terminates at human judgment and make the termination explicit. Name the founding evaluators. Publish their credentials, their reasoning for skill tier assignments, and their evaluation criteria. They bear personal reputation for the quality of early tier assignments. This is the same way professional licensure works — it terminates at licensed professionals who bear personal liability.

- **Add "evaluation contestation" as a parallel track**: Any skill that is assigned Tier 2 or Tier 3 can be contested by any community member with a recognized civic token. Contestation requires a structured argument and a small token stake. If contestation succeeds, the evaluation is reopened. This creates a community-reviewed correction mechanism without requiring constant re-evaluation.

- **Make evaluation criteria publicly authored**: The task suites, adversarial tasks, and regression tests for Tier 2 evaluation should be proposed publicly, commented on, and revised before being applied. Skills being evaluated know what they will be evaluated on. This doesn't eliminate gaming but makes the gaming criteria visible and negotiable.

---

## 9. Participation Inequality

**Problem**: The system documents participation inequality but has no structural features that change who participates.

**Suggestions**:

- **Add sortition to the deliberation layer**: For proposals above a threshold (e.g., affecting more than 1,000 citizens or involving constitutional implications), require that at least 2 of the 5+ skill slots be filled by randomly selected citizens, not skills. Citizens serve as "epistemic counters" — their role is to represent lived experience that skills may not capture. They are compensated for their time.

- **Establish "delegated review" with accountability**: Allow any citizen to delegate their review slot to a trusted representative. The representative's review is attributed to them, but the original citizen's delegation is also recorded. This creates a network of representation without requiring everyone to engage deeply with every proposal. Representatives who consistently misalign with their delegators are flagged.

- **Add compensated participation for affected communities**: If a proposal disproportionately affects a specific demographic or geographic community, that community receives a budget for compensated participation — members can claim tokens for time spent on PNyx deliberation related to that proposal. This addresses the time-poverty problem directly.

---

## 10. No Competitor Analysis

**Problem**: The specification assumes the alternative to PNyx is no governance, not existing governance channels.

**Suggestions**:

- **Add a "channel comparison" field to every proposal**: Before a proposal enters deliberation, the submitter must identify: what existing governance channel this decision could be routed through, what the expected outcome of that channel would be, and why PNyx is expected to produce a better outcome. This isn't a gate — proposals don't get rejected for having poor channel comparisons — but it makes the value proposition of PNyx explicit for each decision.

- **Target a specific decision type where PNyx has clear advantages**: Rather than competing with existing channels generally, identify the specific governance decisions where PNyx's model (adversarial deliberation, transparent reasoning, epoch binding) has the clearest advantage. Early proposals should be limited to this decision type. As the system proves itself, expand to harder cases. "Win the easy ones first" is a more tractable strategy than "compete with all existing governance."

- **Build an "exit survey" for every decided proposal**: After each decision, ask participants: would you have routed this through an existing governance channel instead? What would have been different? Track this data. It provides evidence for the actual value proposition rather than assuming it.

---

## 11. General: Simplification

**Problem**: The specification has 43 documents and ~29,500 lines. The complexity itself is a barrier.

**Suggestions**:

- **Create a "core PNyx" definition of 5 documents**: GOVERNANCE.md, PROTOCOL.md, STATE_MACHINE.md, CLASSIFICATION.md, and MINIMUM_VIABLE_PNYX.md. Declare these the canonical specification. All other documents are "extended reference." This doesn't reduce the information but makes the entry point tractable.

- **Add a decision tree to the index**: Instead of a dependency graph, add a "how do I understand X?" section with a decision tree: if you want to understand X, read Y first, then Z, then skip to Q. Different paths for implementers, researchers, citizens, and critics.

- **Target a "one-page summary" per layer**: Each layer (10, 20, 30, etc.) should have a one-page summary that a motivated citizen can read in 10 minutes. The full documents are available for those who want them, but the system should be explainable in aggregate at a summary level. If the one-page summary can't be written, the layer is not yet understood well enough to be implemented.

---

## Cross-Cutting Themes

Three themes emerge across these suggestions:

1. **Visibility is not comprehension** — the system produces evidence of good process but not understanding by participants. Bridging this gap requires investment in translation, summarization, and compensated intermediaries. This is a budget line, not a design choice.

2. **Speed matters** — the system optimizes for deliberation quality at the expense of decision speed. Some decisions need to be fast. The distinction between "fast" and "slow" decisions should be structural, not ad hoc.

3. **Bootstrap is not a phase** — it is a permanent condition for a system that starts without legal authority or established track record. The bootstrap concessions that make sense at founding may become structural dysfunction if not explicitly managed. Build sunset clauses into bootstrap compromises.

---

*Suggestions completed: April 2026*
*Companion to: STRUCTURAL_CRITIQUE.md*
