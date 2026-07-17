# COMPETITOR_ANALYSIS.md

*Addressing the missing competitor analysis in the PNyx governance specification*

---

## 1. Why This File Exists

PNyx does not compete with chaos; it competes with existing governance channels.

The relevant comparison class is not ideal theory but incumbent institutions:

- legislatures and councils
- courts and regulators
- executives and agencies
- ballot and referendum processes
- unions, NGOs, civic coalitions, and protest networks
- private negotiation and informal influence channels

If PNyx cannot explain why a decision should be routed through it instead of through these channels, it has no adoption story and no legitimacy path.

---

## 2. Incumbent Advantages

Existing channels retain structural advantages that PNyx does not have at founding:

| Advantage | Description |
|-----------|-------------|
| **Legal authority** | Incumbents can tax, regulate, enforce, and coerce. PNyx outputs are advisory. |
| **Enforcement power** | Court orders, agency rules, and legislative statutes have coercive force. PNyx recommendations do not. |
| **Public familiarity** | Citizens know how to use legislatures, referenda, and courts. PNyx requires learning an entirely new procedural grammar. |
| **Institutional continuity** | Existing channels have survived repeated stress. They have organizational memory and established crisis protocols. |
| **Lower switching cost** | Routing a decision through existing channels requires knowing the existing process. Routing through PNyx requires learning a new one. |
| **Recognized outcomes** | A court ruling is final. A legislature vote produces law. PNyx produces a briefing that may or may not influence behavior. |

PNyx begins without these advantages.

---

## 3. Where PNyx Is Weaker

Compared with incumbents, early PNyx is weaker on:

| Weakness | Consequence |
|----------|-------------|
| **Binding force** | PNyx outputs cannot compel action. Incumbents can. |
| **Execution capacity** | PNyx has no treasury, no enforcement arm, no regulatory authority. |
| **Procedural familiarity** | Every PNyx participant must learn a new system. Existing channels have generations of institutional knowledge. |
| **Speed in routine cases** | The 19-state machine handles complexity well but moves slowly. Routine decisions that incumbents handle in days take PNyx weeks. |
| **Public recognition** | "Route it through PNyx" is not yet a recognized civic action. |
| **Low-friction participation** | Submitting a proposal through PNyx requires navigating classification, panel assembly, and briefing — more overhead than calling a representative. |

---

## 4. Where PNyx Can Win

PNyx is strongest where incumbents are weakest:

| PNyx Advantage | Incumbent Failure Mode |
|----------------|----------------------|
| **Transparent adversarial synthesis** | Incumbents rarely surface strong disagreement in structured form. |
| **Audit trail across revisions** | Incumbents often lack longitudinal record of how reasoning evolved. |
| **Minority view preservation** | Incumbents typically record votes, not reasoning. Minority positions are lost. |
| **Unknowns documentation** | Incumbents rarely formally acknowledge what they do not know. |
| **Low-trust environments** | Where incumbents are perceived as captured, PNyx's procedural integrity may be more credible than institutional affiliation. |
| **Adversarially weak processes** | Incumbents often lack structured opposition review. A PNyx panel always includes adversarial critique. |
| **Capture-risk assessment** | Incumbents have no equivalent to the mandatory capture-risk note in each civic packet. |

PNyx should initially target decision classes where transparent adversarial synthesis is more valuable than immediate coercive power.

---

## 5. First-Adopter Wedge

PNyx should not begin by trying to replace all governance.

It should begin where all of the following are true:

- **The decision is real.** There is an actual decision with consequences, not a thought experiment.
- **The stakes are meaningful but bounded.** Important enough to care, not so high that risk aversion prevents trying something new.
- **The current channel is visibly weak.** Participants have direct experience of the incumbent process failing — slow, captured, opaque, or producing poor outcomes.
- **Advisory legitimacy can influence behavior.** The decision doesn't require legal authority — participants will listen to good analysis even without compulsion.
- **Repeated usage can generate track record.** The decision class recurs. Each iteration builds evidence for or against the PNyx value proposition.

**Examples of appropriate first decisions** (to be validated empirically):

- Municipal budget priorities for specific departments
- Organizational governance for new civic institutions
- Community benefit agreements for local development
- Technical standards selection for civic infrastructure
- Local regulatory interpretation questions

---

## 6. Channel Comparison Requirement

Every proposal packet should include a short `channel_comparison` field:

```json
{
  "channel_comparison": {
    "incumbent_channel": "string — what existing governance channel this decision could route through",
    "expected_incumbent_outcome": "string — what outcome the incumbent channel would likely produce",
    "why_pnyx_here": "string — why adversarial deliberation adds value over the incumbent approach",
    "underperformance_criteria": "string — what would count as PNyx clearly underperforming the incumbent"
  }
}
```

This field is **explanatory, not a gating veto**. Proposals are not rejected for weak channel comparisons. The field exists to:

1. Make the PNyx value proposition explicit for each decision
2. Generate structured evidence over time about where PNyx actually outperforms incumbents
3. Prevent proposals from entering PNyx that would be better handled by existing channels

---

## 7. Adoption Thesis

The path from advisory to influential follows this sequence:

1. **Advisory usefulness** — First outputs are genuinely better analysis than incumbents produce. Citizens notice.
2. **Repeated successful use** — The decision class recurs. Each iteration builds familiarity and trust.
3. **Public trust** — Enough citizens have direct positive experience that PNyx outputs carry social weight.
4. **Soft coordination power** — Actors begin treating PNyx recommendations as credible commitments, even without legal force.
5. **Selective institutional recognition** — At least one institution formally acknowledges PNyx recommendations in a specific domain.
6. **Partial binding integration** — In a limited domain, PNyx recommendations become binding decisions through legal or institutional recognition.

**The path is not linear.** Some decision classes may stall at step 2 or 3. The goal is not universal adoption but domain-specific integration where PNyx demonstrably outperforms incumbents.

---

## 8. Exit Evidence

Every decided proposal should collect structured feedback at closure:

```
{
  "exit_evidence": {
    "channel_preference_retrospective": "Would you have preferred routing this through the incumbent channel? (yes/no/unsure)",
    "incumbent_expected_outcome": "What would the incumbent likely have produced?",
    "pnyx_outcome_assessment": {
      "slower": "boolean — was PNyx meaningfully slower than the incumbent would have been?",
      "clearer": "boolean — was PNyx output clearer about disagreement and reasoning?",
      "fairer": "boolean — did PNyx surface perspectives the incumbent would have suppressed?",
      "more_trusted": "boolean — do you trust this PNyx outcome more than you would trust the incumbent?"
    },
    "return_recommendation": "Should this decision class return to incumbent governance? (yes/no/conditional)",
    "return_conditions": "string — if conditional, what would need to change?"
  }
}
```

This data is:

- **Public** — visible in the audit trail
- **Structured** — aggregatable across decision classes
- **Attributed** — tied to specific participants with credentials
- **Non-anonymous** — accountability requires attribution

Over time, this creates an empirical record of where PNyx actually delivers value — and where it doesn't.

---

## 9. Anti-Delusion Rule

**No section of the specification may assume that good design alone yields legitimacy.**

Legitimacy is earned through:

- **Repeated operation** — the system must work, repeatedly, in real conditions
- **Demonstrated competence** — outputs must be demonstrably better than incumbent alternatives
- **Visible correction under stress** — when the system fails, the failure is visible and corrected

This rule applies to:

- The bootstrap section — bootstrapping is not legitimacy, it is a temporary state
- The adoption thesis — advisory usefulness is not adoption, it is a precondition for it
- The skills layer — governance-grade skills are not trustworthy because they meet evaluation criteria, but because they have demonstrated trustworthy performance under adversarial conditions
- The emergency enforcement — technical constraints do not replace demonstrated operator integrity

**The anti-delusion rule is itself subject to the anti-delusion rule.** This document should be treated as a structural improvement, not as proof that the adoption path will succeed. Evidence from exit_evidence data is required before any claim of demonstrated competence.

---

## 10. Relationship to Other Documents

This document complements:

- `COMMUNITY_FORMATION.md` — addresses how the first community forms; this document addresses why they would choose PNyx over alternatives
- `MINIMUM_VIABLE_PNYX.md` — defines what cannot be deferred; this document defines what should be prioritized first
- `POLITICAL_ECONOMY.md` — analyzes power in governance systems generally; this document applies that analysis to PNyx specifically
- `STRUCTURAL_CRITIQUE.md` — identifies the absence of competitor analysis as a structural gap; this document fills that gap
- `STRUCTURAL_CRITIQUE_SUGGESTIONS.md` — proposes specific mechanisms; this document provides the strategic framing within which those mechanisms operate

---

## 11. Open Questions

The following questions are unresolved and require empirical evidence:

1. Which decision class will be the first successful PNyx application?
2. How many iterations before advisory usefulness generates measurable trust?
3. What threshold of trust is required for soft coordination power?
4. Which institution will be the first to formally recognize PNyx recommendations?
5. What conditions trigger a decision class returning to incumbent governance?
6. How much worse than incumbents can PNyx perform before the adoption thesis fails?

These questions cannot be answered by specification. They require operation.

---

*Document purpose: Fill the competitor analysis gap identified in STRUCTURAL_CRITIQUE.md*
*Status: Proposal — requires integration into main specification*
*Last Updated: April 2026*
