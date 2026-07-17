# MINIMUM_VIABLE_PNYX.md

*A principled definition of the smallest deployable form that preserves PNyx identity*

---

## 1. Why This File Exists

The specification must define the smallest deployable form of PNyx that still preserves its identity.

A bootstrap system that removes the adversarial core is not a smaller PNyx. It is a different system.

The existing `MINIMUM_VIABLE_PNYX.md` in `70_Bootstrap/` defines operational minimums for deployment. This document defines something different: the **identity-preserving minimum** — the conditions under which a running system is still recognizably PNyx and not a simulacrum.

The distinction matters because operational simplifications during bootstrap are acceptable. Identity-changing simplifications are not.

---

## 2. Identity-Preserving Minimum

The minimum viable PNyx must preserve all of the following:

1. **Proposal classification exists.** Every proposal is categorized before review. No proposal flows straight to decision without a classification step.

2. **An adversarial layer exists.** Every non-trivial proposal is reviewed by at least two perspectives with structurally opposed orientations — not one perspective twice.

3. **Audit history exists.** Every material action is recorded in a public, append-only log. No silent overrides, no invisible edits, no unlogged decisions.

4. **Minority, against, and unknowns are first-class outputs.** These are not optional fields, afterthought sections, or supplementary notes. They are structural outputs that the deliberation must produce.

5. **A visible path exists from decision to consequence.** The audit trail shows what was decided and what happened afterward. Decision without follow-through is not PNyx output.

6. **Epoch or rule-binding exists in some minimal form.** Proposals cannot have their evaluation criteria changed mid-case. This may be a full epoch system or a simpler rule-freeze mechanism, but something must prevent retroactive rule gaming.

7. **Structural contestability exists.** Any participant can challenge classification, panel integrity, packet sufficiency, or governance-affecting operator actions through a defined, logged process.

---

## 3. What May Be Reduced During Bootstrap

The following may be reduced, simplified, or staged during bootstrap, provided the reduction is explicit and logged:

| Element | Reduction Allowed |
|---------|-----------------|
| Panel width | Full 5-skill panel reduced to 2-skill adversarial dyad (see §5) |
| Specialized skills | Fewer skill roles, as long as adversarial orientation is preserved |
| Documentation breadth | Narrower document set; full 43-document suite is not required at launch |
| Non-essential views | Simplified audit views; 4 minimum views required, 30+ are aspirational |
| Advanced optimization layers | Deferred routing taxonomies, deferred full delegation infrastructure |
| Optional analytics | Deferred participation dashboards, deferred demographic tracking |
| Advanced cryptography | ZK proofs deferred; minimal disclosure still required |

These reductions are acceptable because they affect operational depth, not structural identity.

---

## 4. What May Not Be Removed

The following may not be removed, reduced, or deferred without changing system identity:

| Element | Why Non-Negotiable |
|---------|-------------------|
| **Adversarial critique** | Without opposition, deliberation becomes advocacy. The against/minority/unknowns outputs require adversarial engagement to be genuine. |
| **Classification discipline** | Without classification, the system cannot route appropriately. Classification gaming becomes uncontrolled. |
| **Public auditability** | Without public logs, the system cannot be held accountable. Closed logs mean closed governance. |
| **Explicit unknowns** | Without structured unknowns, the system performs confidence it doesn't have. This is the epistemic sin the system exists to prevent. |
| **Explicit minority view** | Without minority view as a first-class output, dissent is suppressed or lost. The system's core value is making disagreement visible. |
| **Reversible bootstrap framing** | The system must declare itself incomplete. Without this, bootstrap becomes permanent governance and legitimacy debt becomes hidden debt. |

Removing any of these produces a system that looks like PNyx but isn't.

---

## 5. Minimal Bootstrap Panel

The bootstrap minimum is not a single generalist skill.

**The minimum panel is an adversarial dyad:**

| Role | Orientation |
|------|-------------|
| **synthesis** | Builds the strongest case for the proposal. Proposes route, assesses feasibility, identifies supporting evidence. |
| **adversarial_critique** | Builds the strongest case against. Identifies capture risks, minority concerns, unknowns, and reversibility problems. |

**Optional third role:**

| Role | When Required |
|------|--------------|
| **affected_party** or **implementation** | Required when synthesis and critique disagree on implementation feasibility or distributional impact. |

The adversarial dyad is the minimum because:

- One perspective cannot produce genuine against/minority/unknowns outputs
- Two perspectives with opposed orientations can challenge each other
- Three or more perspectives add robustness but are not structurally required for identity

If the deployment cannot support even two opposing perspectives, the affected proposal class must be deferred or routed to advisory-only status.

---

## 6. Bootstrap Sunset Rules

Any bootstrap simplification must include the following attributes, logged as part of the simplification decision:

| Attribute | Required Content |
|-----------|-----------------|
| **Activation condition** | Why this simplification is necessary now |
| **Review cadence** | How often the simplification will be evaluated (minimum: quarterly) |
| **Exit condition** | Specific, measurable criteria for restoring the full mechanism |
| **Named owner** | Individual or role responsible for monitoring and restoring |
| **Maximum duration** | Hard deadline for sunset; no open-ended deferrals |
| **Path-dependency risk note** | Explicit assessment of how this simplification might make the full mechanism harder to restore |

**Example — adversarial dyad instead of full panel:**

```
{
  "simplification": "Reduce 5-skill panel to 2-skill adversarial dyad",
  "activation_condition": "Insufficient Tier-2 skill providers to populate 5-skill panel for all proposal classes",
  "review_cadence": "Quarterly",
  "exit_condition": "At least 5 Tier-2 skills available across required role categories",
  "named_owner": "Skill Registry Steward",
  "maximum_duration": "24 months from bootstrap launch",
  "path_dependency_risk": "Panel habits formed during dyad period may resist expansion to full panels. Early participants may develop institutional preference for faster (dyad) deliberation over richer (full panel) deliberation."
}
```

A simplification without these attributes is not a legitimate bootstrap decision. It is an undeclared concession.

---

## 7. Test of Recognizability

A running system is **not recognizably PNyx** if any of the following are true:

| Failure Mode | Description |
|--------------|-------------|
| **No adversarial engagement** | The system can produce recommendations without any structured opposition. A single synthesis output with no critique layer. |
| **No structured against/minority/unknowns** | The system produces decision outputs but no structured dissent. "Against" and "unknowns" are not first-class fields. |
| **No meaningful challenge path** | Challenges exist on paper but cannot actually change outcomes. Challenge windows are too short, stakes too low, or review too captive to matter. |
| **No public reasoning trace** | The audit log exists but is cryptographically sealed and practically unreadable. Citizens cannot see what happened or why. |
| **No epoch binding** | Rules can be changed mid-case. Proposers can optimize for the rules they want after submission. |
| **No classification step** | Proposals flow directly to deliberation without any routing decision. Classification is bypassed, not just simplified. |

If any of these hold, the system has crossed the identity threshold. It may be a governance system. It is not PNyx.

---

## 8. Relationship to Existing Documents

This document complements rather than replaces `70_Bootstrap/MINIMUM_VIABLE_PNYX.md`:

| Document | Focus |
|----------|-------|
| `70_Bootstrap/MINIMUM_VIABLE_PNYX.md` | Operational minimums for deployment — what must be implemented to run |
| **This document** | Identity minimums — what must be preserved to remain PNyx |

A system can meet every operational minimum in `70_Bootstrap/MINIMUM_VIABLE_PNYX.md` and still fail the recognizability test in §7 if adversarial critique is performative rather than genuine, or if unknowns are optional rather than structural.

---

## 9. Anti-Delusion Reminder

The identity-preserving minimum is not a checklist that, once completed, means the system is PNyx.

The identity-preserving minimum defines the conditions under which the system **can still fail** and be recognizable as a failure of PNyx rather than a different kind of governance system.

The test of recognizability in §7 is a necessary but not sufficient condition. A system that passes every recognizability check can still:

- Produce low-quality adversarial critique
- Surface unknowns that are strategically selected
- Have a challenge path that is technically available but practically inaccessible
- Run adversarial panels that coordinate rather than disagree

The identity-preserving minimum prevents the most egregious identity violations. It does not guarantee genuine anti-capture operation.

---

*Companion document to STRUCTURAL_CRITIQUE.md and COMPETITOR_ANALYSIS.md*
*Purpose: Define when a running system has crossed from "simplified PNyx" to "different system"*
*Status: Proposal — requires integration into main specification*
*Last Updated: April 2026*
