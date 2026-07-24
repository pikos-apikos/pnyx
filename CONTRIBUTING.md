# Contributing to Pnyx

Thank you for helping build Pnyx.

Pnyx is both a civic protocol and a reference implementation. Contributions may include code, protocol text, schemas, tests, research, design, accessibility review, documentation, translations, or evidence that challenges an existing assumption.

## Repository scope and maintainer attention

This repository is a public engineering, evidence, and change-control workbench. It is open to inspection and contribution, but it is not an unstructured public forum.

GitHub issues are for:

- reproducible implementation failures;
- challenges to a specific public artifact, claim, assumption, or recorded decision;
- one scoped change to a named protocol artifact or subsystem;
- focused domain review of a named artifact.

GitHub issues are not for:

- general political or philosophical debate without a proposed artifact change;
- personal manifestos;
- raw AI transcripts or bulk-generated proposals;
- repeated argument that does not add evidence, a test, a correction, or a reviewable change;
- transferring an unlimited research or review burden to maintainers.

Publication of an issue does not create an obligation for immediate maintainer attention or a personal response. Maintainers may close submissions that are out of scope, duplicate, unowned, too broad to review, or primarily generated material without accountable human follow-through.

## Start with the public problem

Before proposing a large solution, describe the problem it addresses:

1. What is happening now?
2. Who or what is affected?
3. What observation, source, experience, inference, or bootstrap assumption supports the problem statement?
4. What constraints or risks must not be hidden?
5. How could the proposal be tested, falsified, or revisited?

You do not need to know the final truth before raising a challenge. You must distinguish what was observed, what was sourced, what was inferred, and what remains uncertain.

Small bug fixes and documentation corrections may go directly to a pull request. Non-trivial changes should begin with the appropriate issue form.

## Issue intake

Use exactly one of the structured forms:

- **Bug report:** a reproducible implementation failure.
- **Artifact or claim challenge:** a focused challenge to a named artifact, claim, assumption, event, or decision.
- **Scoped protocol proposal:** one coherent change to a named rule, schema, invariant, module, API, or runtime behavior.
- **Domain review:** focused review of a named artifact from relevant professional, research, or operational experience.

A submitted issue is not automatically accepted as correct, verified, or prioritized. It is a structured artifact that can be inspected without first requiring the maintainer to reconstruct the contributor's intent.

## Human ownership

Every non-trivial submission needs an accountable human owner. The owner is not required to be the final domain authority, but must be prepared to:

- answer focused questions about the submission;
- separate facts, sources, inference, and uncertainty;
- revise or narrow the artifact during review;
- run or help define proportionate checks where possible;
- avoid transferring the entire verification burden to maintainers.

A contribution without active human ownership may be closed without a decision on its substance.

## Ways to contribute

- **Bug:** reproducible implementation failure.
- **Protocol proposal:** scoped change to civic rules, invariants, artifacts, or governance semantics.
- **Documentation:** clarification, example, translation, or correction, normally through a focused pull request.
- **Evidence or challenge:** source-backed or explicitly uncertain disagreement with a specific public claim or design choice.
- **Domain review:** bounded professional or operational review that states its evidence and limitations.

Use the repository issue templates when applicable.

## Development setup

The Java prototype lives in `demo/` and requires Java 25, Docker, and the checked-in Gradle wrapper.

```bash
cd demo
docker compose up -d postgres
./gradlew test
./gradlew :app:bootRun
```

Open `http://localhost:8080`.

Before submitting implementation changes, run the narrowest relevant checks and, when practical:

```bash
cd demo
./gradlew check
```

End-to-end tests live in `demo/e2e/` and may require the complete local runtime.

## Pull request expectations

A pull request should:

- solve one coherent problem;
- link the relevant issue for non-trivial changes;
- explain the public or developer impact;
- identify protocol, privacy, security, accessibility, or capture risks;
- include tests or explain why tests are not applicable;
- update authoritative documentation when behavior changes;
- keep domain logic independent of framework code;
- preserve event history and public-artifact provenance;
- identify the applicable licensing category for new files;
- avoid unrelated formatting, generated prose, or generated-file churn;
- identify known uncertainty rather than hiding it.

Draft pull requests are welcome for early feedback. A pull request should be marked ready for review only when its human owner has reviewed the complete diff and can explain the claims and changes it contains.

## Architecture boundaries

Read `demo/AGENTS.md` before changing the prototype. Important boundaries include:

- `modules/core` contains pure domain logic and framework-free APIs;
- application and infrastructure code belong in adapters;
- state transitions must be explicit and replayable;
- public artifacts are immutable and content-addressed;
- long-term execution monitoring must remain deterministic rather than delegated to an LLM;
- final civic judgment belongs to people.

## Licensing of contributions

Pnyx is a multi-license repository. Read [`LICENSING.md`](LICENSING.md) before submitting new material.

- Software and executable specifications are licensed under `EUPL-1.2` only.
- Protocol documentation, narratives, and diagrams are licensed under `CC-BY-SA-4.0`.
- Mixed contributions may contain separable elements under both licences.
- The Pnyx name and logos are governed separately by [`TRADEMARKS.md`](TRADEMARKS.md).

By submitting a contribution, you agree that an accepted contribution will be licensed under the licence applicable to the work it modifies or creates. Add an explicit SPDX identifier or licensing notice when the default category would be ambiguous.

Do not submit third-party material unless you have verified that it may be redistributed under the applicable Pnyx licence and have preserved all required attribution and notices.

## AI-assisted contributions

AI assistance is welcome, but it does not transfer responsibility, authorship, expertise, evidence, or review away from the human contributor.

Disclose substantive AI assistance in the issue or pull request and state:

- what the model helped produce;
- which files, claims, calculations, sources, or decisions were affected;
- what the human contributor personally reviewed, tested, or verified;
- whether external sources were used;
- what remains uncertain.

Do not submit raw AI transcripts, bulk-generated proposals, fabricated evidence, secrets, personal data, private conversations, or incompatible copyrighted material. Generated claims require the same provenance and verification as human-written claims.

The contributor must be able to state:

> I reviewed this submission and take responsibility for every claim it contains. AI output is not presented as evidence, expertise, attestation, or independent review.

Maintainers may close AI-assisted submissions when the contributor cannot explain, narrow, verify, or maintain the proposed work.

## Commit sign-off and provenance

Pnyx uses the Developer Certificate of Origin instead of a separate contributor licence agreement. Sign each commit with:

```bash
git commit -s -m "Describe the change"
```

The sign-off certifies the contribution under [`DCO`](DCO); it is not a GPG signature. The DCO certifies provenance and your right to contribute. The applicable project licence is determined by [`LICENSING.md`](LICENSING.md).

## Review and decisions

Maintainers may request changes for correctness, security, privacy, accessibility, protocol integrity, evidence quality, licensing compatibility, maintainability, scope, or attention cost. A technically correct change may still be rejected if it obscures civic consequences or creates an unreviewable concentration of power.

Maintainers do not need to determine whether every submitted claim is true before deciding whether it belongs in the repository workflow. They may assess whether the target is specific, the uncertainty is visible, the contribution is owned, and a proportionate verification path exists.

Significant protocol, licensing, trademark, or governance changes should begin as a scoped issue and follow the decision process in `GOVERNANCE.md`.

## Conduct and security

Participation is governed by `CODE_OF_CONDUCT.md`. Do not report vulnerabilities in public issues; follow `SECURITY.md`.
