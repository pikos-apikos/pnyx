# Collaboration Model

Pnyx should be built using the same values it asks of civic systems: visible reasoning, explicit roles, bounded authority, preserved provenance, and revisable decisions.

## The collaboration loop

```text
problem or observation
        ↓
public issue
        ↓
evidence, constraints, disagreement
        ↓
proposal or pull request
        ↓
plural review
        ↓
decision with rationale
        ↓
implementation and verification
        ↓
outcome, learning, and documentation
```

Not every contribution needs every step. The depth of process should be proportional to impact.

## Choosing the right artifact

- Use an **issue** to describe a problem, bug, question, protocol change, or design uncertainty.
- Use a **discussion** for exploratory conversations that are not yet actionable, once GitHub Discussions is enabled.
- Use a **pull request** for a concrete, reviewable change.
- Use an **architecture or protocol document** when a decision needs durable rationale beyond the pull request.
- Use a **security report** for vulnerabilities or civic-security risks that should not initially be public.

## Issue lifecycle

A useful issue contains:

- the current situation;
- affected people, components, or public artifacts;
- evidence or reproducible observations;
- known constraints;
- risks and possible unintended consequences;
- a clear completion or learning condition.

Maintainers may classify an issue as:

- `needs-triage`;
- `needs-evidence`;
- `ready`;
- `blocked`;
- `deferred`;
- `duplicate`;
- `not-planned`.

Closing an issue should include a reason. Rejected, duplicate, or deferred ideas remain part of project memory.

## Pull request lifecycle

1. Open a focused pull request, preferably as draft while the design is still moving.
2. Link the issue or explain why no issue was needed.
3. Complete the pull request template.
4. Disclose substantive AI assistance and external sources.
5. Request review from the relevant areas.
6. Address findings or record why a suggestion was not adopted.
7. Merge only when required checks and reviews pass.
8. Update documentation and follow-up issues for accepted limitations.

Prefer squash merging for a coherent public history unless preserving individual commits adds meaningful provenance.

## Review roles

A change may need different reviewers:

- **domain/protocol:** civic meaning and invariants;
- **technical:** implementation correctness and architecture;
- **security/privacy:** abuse, exposure, standing, provenance, and authorization;
- **accessibility/UX:** whether real people can understand and complete the intended role;
- **operations:** deployment, monitoring, migration, and rollback;
- **adversarial:** strongest case against the proposed change.

One person may cover more than one role, but high-impact changes should not rely on one perspective alone.

## Review etiquette

- State whether a comment is blocking, strongly recommended, optional, or a question.
- Explain the risk or invariant behind requested changes.
- Prefer concrete examples and small patches over vague rejection.
- Challenge claims and designs, not motives or identities.
- Preserve meaningful disagreement in the final rationale.
- Do not use review volume, automation, or model-generated comments to exhaust contributors.

## AI agents and models

AI agents may draft, analyze, test, or review contributions. They do not become accountable project participants by themselves.

Every AI-assisted contribution needs a human sponsor who:

- understands the intended change;
- checks generated claims and sources;
- verifies tests and limitations;
- accepts responsibility for the submitted contribution;
- discloses material model use in the pull request.

Model outputs should be treated as artifacts with provenance, not as authority. Multiple models producing the same answer are not independent evidence if they share training data, prompts, tools, or upstream sources.

## Collaboration without capture

The project welcomes individuals, public bodies, companies, researchers, and civil-society groups. Participation does not create an entitlement to control.

Contributors must disclose material conflicts when a change affects their employer, funding source, product, contract, political interest, or infrastructure position. Maintainers may request independent review or recusal.

## Language and accessibility

English is the authoritative repository language during bootstrap so the project can collaborate internationally. Translations are welcome and should identify their source version and translation status.

Documentation and interfaces should use plain language before protocol vocabulary, support progressive disclosure, and avoid assuming unlimited time, technical literacy, or physical ability.

## Learning from outcomes

Merged code is not proof that a decision was correct. When a change produces unexpected effects, create a follow-up issue or learning artifact that records:

- what was expected;
- what happened;
- who benefited or was burdened;
- which warning signs were missed;
- what should be changed next.
