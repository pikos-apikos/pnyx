# Contributing to Pnyx

Thank you for helping build Pnyx.

Pnyx is both a civic protocol and a reference implementation. Contributions may include code, protocol text, schemas, tests, research, design, accessibility review, documentation, translations, or evidence that challenges an existing assumption.

## Start with the public problem

Before proposing a large solution, describe the problem it addresses:

1. What is happening now?
2. Who or what is affected?
3. What evidence supports the problem statement?
4. What constraints or risks must not be hidden?
5. How will we know whether the change helped?

Small bug fixes and documentation corrections may go directly to a pull request.

## Ways to contribute

- **Bug:** reproducible implementation failure.
- **Protocol proposal:** change to civic rules, invariants, artifacts, or governance semantics.
- **Feature:** new capability within the existing protocol.
- **Documentation:** clarification, example, translation, or correction.
- **Evidence or challenge:** source-backed disagreement with a public claim or design choice.

Use the repository issue templates when possible.

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
- explain the public or developer impact;
- identify protocol, privacy, security, accessibility, or capture risks;
- include tests or explain why tests are not applicable;
- update authoritative documentation when behavior changes;
- keep domain logic independent of framework code;
- preserve event history and public-artifact provenance;
- identify the applicable licensing category for new files;
- avoid unrelated formatting or generated-file churn.

Draft pull requests are welcome for early feedback.

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

AI assistance is welcome, but it does not transfer responsibility away from the human contributor.

Disclose substantive AI assistance in the pull request and state:

- what the model helped produce;
- which files or decisions were affected;
- how the output was reviewed or tested;
- whether external sources were used;
- whether any generated content remains uncertain.

Do not submit secrets, personal data, private conversations, incompatible copyrighted material, or fabricated evidence. Generated claims require the same provenance and verification as human-written claims.

## Commit sign-off and provenance

Pnyx uses the Developer Certificate of Origin instead of a separate contributor licence agreement. Sign each commit with:

```bash
git commit -s -m "Describe the change"
```

The sign-off certifies the contribution under [`DCO`](DCO); it is not a GPG signature. The DCO certifies provenance and your right to contribute. The applicable project licence is determined by [`LICENSING.md`](LICENSING.md).

## Review and decisions

Maintainers may request changes for correctness, security, privacy, accessibility, protocol integrity, evidence quality, licensing compatibility, or maintainability. A technically correct change may still be rejected if it obscures civic consequences or creates an unreviewable concentration of power.

Significant protocol, licensing, trademark, or governance changes should begin as an issue and follow the decision process in `GOVERNANCE.md`.

## Conduct and security

Participation is governed by `CODE_OF_CONDUCT.md`. Do not report vulnerabilities in public issues; follow `SECURITY.md`.
