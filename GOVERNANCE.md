# Pnyx Governance

## Purpose

This document describes how the Pnyx open-source project is governed during its bootstrap phase. It governs the repository and project collaboration; it does not itself define the civic decision rules implemented by the Pnyx protocol.

The governance model should evolve as the contributor community becomes broader and more capable of sharing responsibility.

## Principles

1. **Humanity first.** Project decisions should protect human dignity, freedom, responsibility, and the capacity to learn.
2. **Public reasoning.** Significant decisions require a visible rationale, alternatives, risks, and dissent.
3. **One public memory.** Documentation, issues, pull requests, and decision artifacts should remain traceable rather than being silently rewritten.
4. **No hidden sovereignty.** Maintainer, model, expert, funder, or infrastructure power must not be disguised as neutral implementation detail.
5. **Problem before solution.** Major work begins with a clear problem statement and evidence.
6. **Plural review.** High-impact changes require more than one perspective, especially for rights, privacy, security, feasibility, economics, and capture risk.
7. **Revisability.** Decisions may be revisited when evidence, outcomes, or community capacity changes.
8. **Commons reciprocity.** Commercial use is welcome, while improvements to the covered civic commons remain available to the commons under the applicable reciprocal licences.

## Roles

### Contributor

Anyone who contributes code, documentation, research, design, review, translation, evidence, or constructive dissent.

### Reviewer

A contributor trusted to assess changes in a defined area. Review authority is scoped; technical review does not imply authority over civic values or governance.

### Maintainer

A contributor with merge and repository-management authority. Maintainers are expected to review transparently, protect project boundaries, disclose conflicts, and recuse themselves when necessary.

### Project steward

A maintainer responsible for the coherence of the project's mission, public commitments, and governance. During bootstrap, the repository owner acts as founding steward and final merge authority.

This bootstrap authority is practical, not sacred. It should be reduced and distributed as the project develops credible maintainers, review capacity, and public decision procedures.

## Decision classes

### Routine changes

Examples: typo fixes, tests, localized refactoring, dependency maintenance, and implementation changes that do not alter public behavior or protocol meaning.

Routine changes may be merged after appropriate review and checks.

### Significant implementation changes

Examples: architecture, persistence, public APIs, identity, provenance, security, privacy, model orchestration, or changes that materially affect citizen experience.

They require:

- an issue or design note describing the problem;
- alternatives and trade-offs;
- relevant tests;
- at least one maintainer approval;
- explicit consideration of security, privacy, accessibility, and capture risks.

### Protocol or governance changes

Examples: civic-loop states, judgment rules, participation rights, standing, panel composition, public-artifact schemas, execution authority, licensing, trademarks, or repository governance.

They require:

- a public proposal issue;
- a review period appropriate to impact;
- recorded objections and minority positions;
- a decision rationale;
- an implementation or migration plan;
- approval by the project steward during bootstrap.

A protocol change must not be smuggled into a code-only pull request.

## Consensus and disagreement

The project seeks informed agreement, not artificial unanimity.

When consensus is not reached, the responsible maintainer or steward may decide, but must record:

- the decision;
- the strongest alternatives;
- unresolved objections;
- the evidence used;
- the conditions that would justify revisiting it.

A decision may be deferred when the project lacks evidence, review capacity, or a legitimate decision process.

## Maintainer selection

A contributor may become a maintainer after demonstrating sustained, constructive participation, sound judgment, reliable review, respect for project boundaries, and willingness to handle unglamorous maintenance work.

During bootstrap, the project steward appoints maintainers publicly with a stated scope. Future governance should replace unilateral appointment with a documented nomination and consent process.

## Removal and recusal

Maintainer access may be limited or removed for inactivity, compromised credentials, repeated failure to follow project policies, abuse of authority, serious conflicts of interest, or Code of Conduct violations.

A maintainer must recuse themselves from decisions where they have a material personal, financial, organizational, or adversarial conflict.

## Commons and commercial participation

Pnyx welcomes use and contribution by individuals, public bodies, cooperatives, companies, researchers, and civil-society organizations.

Commercial activity is permitted and encouraged when it expands useful implementation, hosting, integration, training, maintenance, or support. Commercial participation does not create a right to privatize covered improvements or to present a private implementation as the official project.

The repository licensing model is defined in [`LICENSING.md`](LICENSING.md):

- software and executable specifications use `EUPL-1.2`;
- protocol documentation, narratives, and diagrams use `CC-BY-SA-4.0`;
- contributions use DCO 1.1 for provenance;
- names and logos are governed separately by [`TRADEMARKS.md`](TRADEMARKS.md).

Licensing obligations are enforced by the licences themselves. Project governance does not grant exceptions silently. Any dual-licensing arrangement, commercial exception, relicensing decision, or trademark permission that could materially affect the commons requires a visible rationale, conflict disclosure, and the protocol-or-governance process.

## Funding and influence

Funding, employment, infrastructure sponsorship, commercial adoption, or model access does not purchase governance authority. Material support and related conditions must be disclosed. Decisions affecting a funder or vendor require independent review where possible.

No sponsor receives additional votes, merge rights, protocol authority, certification rights, or control over public memory merely because they fund development or infrastructure.

## Amendments

Changes to this document follow the protocol-or-governance process above. Each amendment should explain why the previous governance was insufficient and how the change distributes or constrains power.
