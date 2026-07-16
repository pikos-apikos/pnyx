# PUBLIC_IP_MODEL

## 1. Purpose

This document defines how the system treats intellectual property and other reusable public artifacts produced through the civic process.

Its purpose is to ensure that:
- public proposals may generate reusable public value,
- that value may support the system materially,
- public production does not silently become private enclosure,
- revenue does not become hidden governance power,
- the market thread can execute under the shared values layer without becoming sovereign.

This document complements:
- `GOVERNANCE.md`
- `ARCHITECTURE.md`
- `FUNDING_MODEL.md`
- `POLITICAL_ECONOMY.md`
- `SKILL_ECONOMICS.md`
- `MINIMUM_VIABLE_PNYX.md`

---

## 2. Core Principle

A proposal may generate public IP.
Public IP may produce revenue.
Revenue may support the civic system.

But:
- public IP does not confer governance legitimacy,
- monetizable output does not outrank non-monetizable public good,
- revenue generation must remain downstream of the civic process,
- no actor may privately capture collective civic production by default.

The correct order is:

**civic purpose → public artifact → optional market execution → public return**

Not:

**marketability → governance priority**

---

## 3. What Counts as Public IP

Public IP includes reusable outputs created, funded, or matured through the civic system, including:

- software
- protocols
- standards
- governance methods
- educational material
- translations
- public datasets where lawful and appropriate
- civic interface kits
- evaluation harnesses
- skill templates
- benchmark suites
- process documentation
- local deployment playbooks
- packet formats
- constitutional or procedural toolkits

Not every public artifact must be monetized.
Not every monetizable artifact should be monetized.

---

### 3.1 Terminology note: "Civic Commons IP"

The external "PNyx Core v0.3" draft uses the term **Civic Commons IP** for a single, flat category of publicly produced assets.
In this suite, that term is an alias for the commons-owned classes of the Public IP model below; it is not a separate concept.
The four-class gradation and the licensing restrictions of §7.1 stand unchanged (`../99_Reference/CORE_V03_RECONCILIATION.md`, conflicts 5–6).

---

## 4. Public IP Classes

The system should distinguish at least the following classes:

### 4.1 Commons-Core IP
Artifacts essential to public reasoning and constitutional continuity.

Examples:
- packet schemas
- audit schemas
- protocol definitions
- challenge formats
- benchmark commons
- critical skill templates
- public evaluation harnesses

Default rule:
- strongly open,
- forkable,
- non-exclusive,
- difficult to privatize.

### 4.2 Civic Utility IP
Artifacts useful for broad civic operation but not strictly constitutional.

Examples:
- facilitation software
- translation layers
- local participation tooling
- deployment kits
- analytics tools with public safeguards

Default rule:
- open or commons-preferred,
- commercial support allowed,
- exclusivity disfavored.

### 4.3 Applied Service IP
Artifacts that may support paid implementation, hosting, customization, or integration.

Examples:
- managed deployment services
- enterprise adaptation layers
- support bundles
- federation administration tooling
- specialized training or onboarding packages

Default rule:
- commercialization allowed under public-return constraints,
- must not become hidden control over core governance.

### 4.4 Local or Experimental IP
Artifacts created by local pilots, research sandboxes, or temporary experiments.

Default rule:
- classification required before permanent licensing posture is set.

---

## 5. Ownership Principle

By default, public IP produced through the civic process belongs to the public commons layer, not to individual operators, maintainers, vendors, or donors.

This means:
- no operator may claim private ownership merely because they implemented it,
- no donor may claim ownership merely because they funded it,
- no skill provider may claim exclusive control over shared public scaffolds by default,
- no local pilot may privatize a generally reusable artifact without explicit review.

Ownership must distinguish:
- authorship,
- stewardship,
- maintenance responsibility,
- licensing control,
- revenue entitlement.

These must not be silently collapsed into one role.

---

## 6. Authorship and Attribution

The system should preserve authorship and contribution visibility.

This may include:
- named contributors where appropriate,
- team attribution,
- locality attribution,
- version lineage,
- source proposal linkage,
- civic process linkage,
- public funding linkage.

Attribution matters for:
- credit,
- accountability,
- reuse lineage,
- fork legitimacy.

Attribution does not imply private enclosure rights over commons-core outputs.

---

## 7. Licensing Default

The default licensing posture should be:

- **commons-preferred**
- **fork-friendly**
- **non-exclusive**
- **publicly inspectable**
- **compatible with public evaluation and audit**

Licensing must be chosen per class, but the default bias is toward public return and anti-capture.

Possible licensing patterns include:
- permissive open licenses,
- copyleft or reciprocity licenses,
- public-use licenses,
- dual licensing with commons floor,
- service commercialization without proprietary ownership of the commons layer.

### 7.1 Restricted patterns by class

Two additional patterns exist but are class-restricted:
- **open core** (open base, proprietary extensions),
- **delayed or timed open release** (temporary exclusivity followed by scheduled open release).

Their availability by IP class:

| IP class | Open core | Delayed / timed release |
|---|---|---|
| Commons-Core | prohibited | prohibited |
| Civic Utility | prohibited | prohibited |
| Applied Service | permitted with declared public-return mechanism | permitted with declared release date and public-return mechanism |
| Local / Experimental | permitted with declared public-return mechanism | permitted with declared release date and public-return mechanism |

Where permitted, these patterns additionally require:
- explicit classification before adoption,
- a declared, treasury-routed public-return mechanism (section 9),
- a binding release schedule for delayed release (no indefinite postponement),
- anti-capture review confirming the proprietary layer cannot become hidden control over core governance.

Public reasoning infrastructure is never the proprietary layer of anyone's business model.

The system should avoid:
- exclusive transfers,
- private-only access to core artifacts,
- licensing that prevents public verification of civic-critical components.

---

## 8. No Exclusive Capture by Default

No public IP produced through the civic process may be exclusively assigned, sold, or enclosed by default.

Exclusive control requires:
- explicit classification,
- public rationale,
- public-return analysis,
- anti-capture review,
- stronger threshold than ordinary operational approval,
- treasury and dependency disclosure,
- review of alternatives to exclusivity.

For Commons-Core IP, exclusivity should generally be prohibited.

---

## 9. Public Return Principle

Where public IP generates revenue, the revenue must produce public return.

Public return may include:
- treasury contributions,
- challenge capacity funding,
- local pilot funding,
- skill commons support,
- accessibility and translation support,
- migration and replacement reserves,
- benchmark commons maintenance,
- contributor support under disclosed rules.

Public return must not be a vague promise.
It must be treasury-routed and auditable.
Each material public-return obligation is closed by a `PublicReturnReport` recording obligation, expected return, observed return, and routing evidence; an obligation without a closing report remains open.

---

## 10. Revenue Channels

Public IP may generate revenue through channels such as:

- hosting
- support contracts
- implementation services
- federation services
- customization
- training and onboarding
- documentation bundles
- certification or compatibility services where appropriate
- public procurement
- licensing of non-core applied layers
- cooperative subscriptions

Revenue channels must be analyzed for:
- dependency risk,
- enclosure risk,
- hidden influence,
- operator concentration,
- divergence from the commons mission.

---

## 11. Revenue Does Not Buy Governance

Revenue generated by public IP may strengthen capacity.
It may not purchase governance privilege.

Revenue must not buy:
- voting weight,
- panel influence,
- registry preference,
- classification leniency,
- routing advantage,
- packet control,
- emergency privilege,
- constitutional interpretive authority.

The treasury may benefit from market-thread outputs.
The market thread may not become the hidden sovereign.

---

## 12. Market Thread Relation

Public IP revenue is a valid market-thread outcome under the shared values layer.

This means:
- market execution is permitted,
- value can circulate materially,
- the civic system may gain independence from incumbents,
- commons production can fund commons continuity.

But market execution remains subordinate to:
- the values layer,
- anti-capture doctrine,
- public audit,
- public-return obligations,
- no-legitimacy-purchase rules.

The market thread executes.
It does not govern.

---

## 13. Proposal-to-IP Path

A proposal may lead to public IP through paths such as:

1. proposal identifies a public need,
2. civic loop evaluates and clarifies it,
3. execution produces a reusable artifact,
4. artifact is classified into a public IP class,
5. licensing and stewardship are assigned,
6. revenue channels, if any, are declared,
7. treasury routing is specified,
8. audit linkage is maintained.

No artifact should silently move from proposal output to private asset.

### 13.1 Mandate-Time Disclosure

Any execution mandate that may create public IP must declare, before execution begins:

- expected asset type and anticipated IP class,
- initial owner or steward,
- contributor rights (`ContributorAgreement`),
- participant rights (what rights, if any, accrue to civic participants whose contributions shaped the asset),
- attribution requirements,
- licensing model within the class restrictions of §7.1 (`LicenseRecord`),
- derivative-work conditions,
- commercial-use conditions,
- revenue-return mechanism,
- abandonment or transfer rules.

Undeclared items default to the strongest public protection for the anticipated class.
This disclosure list was extended by the v0.3 reconciliation (`../99_Reference/CORE_V03_RECONCILIATION.md` §4.5).

---

## 14. Classification of IP Outputs

Every significant reusable output should receive a `PublicIPClassification` containing:

- `IPClass`
- `CommonsCriticality`
- `LicensingPosture`
- `StewardshipModel`
- `RevenueEligibility`
- `ExclusivityStatus`
- `TreasuryReturnRule`
- `ForkabilityStatus`
- `DependencyRisk`
- `ReviewThreshold`

Classification must favor stronger public protections when ambiguity is material.

The classification links to the mandate-time disclosures of §13.1 (`LicenseRecord`, `ContributorAgreement`) and, once revenue or public value flows, to the `PublicReturnReport` that closes the §9 public-return obligation.

---

## 15. Stewardship Models

A public artifact may be stewarded by:

- a public commons body,
- a local civic chapter,
- a cooperative maintenance group,
- a designated maintainer set,
- a federated stewardship network,
- a hybrid public-private maintenance body under strict limits.

Stewardship is not ownership.
Stewardship means:
- maintenance duty,
- public documentation duty,
- change visibility,
- compatibility obligations where applicable,
- incident responsibility where applicable.

Stewardship concentration must be tracked as a capture surface.

---

## 16. Contributor Compensation

The system may compensate contributors to public IP,
but compensation must remain distinct from sovereign control.

Compensation models may include:
- grants,
- bounties,
- maintenance stipends,
- support revenue sharing,
- cooperative distributions,
- milestone payments.

Compensation must not imply:
- permanent private control,
- veto rights over the commons,
- hidden preferential treatment in governance.

A civic commons may reward builders without becoming a privatized estate.

---

## 17. Public IP and Funding Independence

Public IP revenue can materially strengthen independence by helping fund:

- core infrastructure,
- skill evaluation,
- local pilots,
- challenge capacity,
- migration reserves,
- translation and access,
- legal resilience,
- federation growth.

This makes public IP strategically important.
It can become a pressure mechanism against existing institutions by proving that the civic layer can generate not only arguments but durable value.

However, dependence on IP revenue alone is risky.
A healthy funding model remains plural.

---

## 18. Anti-Capture Safeguards

The system must guard against the following:

- proposals selected because they monetize well rather than because they serve civic need,
- operators steering artifacts toward proprietary channels,
- donors pushing licensing in exchange for support,
- service layers quietly replacing commons-core layers,
- “temporary” exclusivity becoming permanent dependency,
- public contributors losing access to collectively produced tools,
- revenue concentration reinforcing interpretive concentration,
- branding power substituting for public licensing clarity.

Where such patterns emerge, the issue is not only economic.
It is constitutional.

---

## 19. Forkability and Exit

Public IP must preserve forkability wherever constitutionally relevant.

This means:
- source or method visibility where required,
- migration paths,
- version lineage,
- public documentation,
- non-exclusive base rights,
- compatibility or conversion information where applicable.

If a public artifact cannot realistically be forked or migrated,
its dependency risk must be treated as high.

Forkability is one of the main anti-capture guarantees of the public IP layer.

---

## 20. Local Outputs and Upstream Return

Local communities may produce useful public IP through pilots or issue-specific work.

The system should allow:
- local authorship visibility,
- local stewardship where appropriate,
- local revenue participation under declared rules,
- upstream contribution of reusable artifacts to the wider commons.

This avoids both:
- local extraction by the center,
- private hoarding of publicly matured artifacts.

A federated commons should let value circulate both upward and downward.

---

## 21. Treasury Routing

Revenue derived from public IP should be routed explicitly.

Possible routing patterns include:
- a share to `CoreTreasury`,
- a share to `ChallengeTreasury`,
- a share to `SkillCommonsTreasury`,
- a share to `LocalPilotTreasury`,
- a share to designated maintenance pools,
- a share to migration or resilience reserves.

Routing rules must be:
- public,
- auditable,
- class-aware,
- non-arbitrary,
- challengeable.

No operator should privately decide where public IP revenue goes.

---

## 22. Public Audit Requirements

Every significant public IP artifact should preserve auditable linkage to:

- source proposal or project,
- funding sources,
- licensing decision,
- stewardship assignment,
- contributor record,
- revenue flows,
- treasury routing,
- exclusivity status,
- fork or migration events,
- retirement or archival decisions.

Public IP must remain legible as public production.

---

## 23. Bootstrap Profile

During bootstrap, the system should prefer:

- clearly open commons-core artifacts,
- minimal exclusivity surface,
- visible public-return rules,
- simple stewardship models,
- limited commercialization complexity,
- strong forkability,
- conservative licensing changes,
- explicit debt if dependence on one service provider becomes material.

Bootstrap should not begin by financializing every artifact.
It should begin by establishing trustworthy commons habits.

---

## 24. Failure Signals

The following indicate likely distortion or capture:

- monetizable proposals quietly receiving priority,
- core public artifacts becoming difficult to inspect or fork,
- contributors assuming ownership because they built the first version,
- operators routing revenue outside declared treasury partitions,
- local communities losing rights to artifacts they helped create,
- exclusive deals expanding around core infrastructure,
- service convenience replacing commons guarantees,
- revenue used to justify governance influence,
- public IP language masking ordinary privatization.

Repeated failure signals require redesign, not just better contracts.

---

## 25. Cross-References

This document imposes requirements on:

### 25.1 FUNDING_MODEL
Must define how public IP revenue enters treasury partitions.

### 25.2 POLITICAL_ECONOMY
Must treat licensing and revenue as power structures, not only legal choices.

### 25.3 AUDIT_LOG
Must preserve revenue, stewardship, and licensing traceability.

### 25.4 AUDIT_VIEWS
Should expose public-IP provenance and public-return visibility.

### 25.5 CLASSIFICATION
Should classify reusable outputs and ambiguity in favor of stronger public protections.

### 25.6 MINIMUM_VIABLE_PNYX
Should keep bootstrap public IP simple, open, and low-enclosure.

---

## 26. Closing Principle

A civic system should be able to turn public intelligence into durable public value.

That value may circulate through markets.
It may generate revenue.
It may strengthen independence.

But it must return to the commons without quietly turning the commons into a firm.

The system should therefore aim for this rule:

**publicly generated value may finance civic autonomy, but it may never become the hidden owner of civic legitimacy.**
