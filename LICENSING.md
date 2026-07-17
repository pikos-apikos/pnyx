# Pnyx Licensing

Pnyx is a multi-license repository. The applicable license depends on the nature of the work, not merely on its file extension or directory.

The intent is simple:

> Commercial participation is welcome. Improvements to the civic commons must remain part of the civic commons.

## Software and executable specifications — EUPL-1.2

The following are licensed under the **European Union Public Licence, version 1.2 only** (`EUPL-1.2`):

- application and library source code;
- tests and generated source committed to the repository;
- build, deployment, automation, and CI configuration;
- scripts and command-line tools;
- machine-readable schemas and validation rules;
- API specifications intended for implementation;
- protocol state machines and transition definitions;
- executable examples and code snippets intended to be incorporated into software;
- any other specification whose primary purpose is to be executed, validated, compiled, or mechanically enforced.

The full EUPL-1.2 text is in [`LICENSE`](LICENSE).

Unless a file carries a different explicit notice, this category includes the complete `demo/` implementation and machine-executable project infrastructure.

## Protocol documentation, narratives, and diagrams — CC BY-SA 4.0

The following are licensed under **Creative Commons Attribution-ShareAlike 4.0 International** (`CC-BY-SA-4.0`):

- prose protocol documentation;
- conceptual and architectural explanations;
- narratives, examples, and civic scenarios;
- diagrams and illustrations created for Pnyx;
- research summaries and design rationale;
- governance and community documentation;
- README files and other explanatory material.

The full CC BY-SA 4.0 legal code is in [`LICENSES/CC-BY-SA-4.0.txt`](LICENSES/CC-BY-SA-4.0.txt).

## Mixed documents

A document may contain both explanatory prose and executable material.

- The prose, narrative, and diagrammatic portions are licensed under `CC-BY-SA-4.0`.
- Source code, schemas, commands, and executable specifications embedded in that document are additionally licensed under `EUPL-1.2`.

An explicit SPDX identifier or licensing notice inside a file overrides these repository defaults for that file or identified portion.

## Contributions — DCO 1.1

Pnyx uses the [Developer Certificate of Origin 1.1](DCO), not a separate contributor licence agreement.

A contributor signs off each commit to certify that they have the right to submit the contribution. Once accepted, the contribution is licensed under the licence applicable to the work it modifies or creates:

- `EUPL-1.2` for software and executable specifications;
- `CC-BY-SA-4.0` for protocol documentation, narratives, and diagrams;
- both, where a contribution contains clearly separable elements from both categories.

See [`CONTRIBUTING.md`](CONTRIBUTING.md) for the contribution process.

## Names and logos

Copyright licences do not grant permission to use the Pnyx name, logos, or visual identity as branding or to imply endorsement.

Truthful attribution and reasonable references to the project's origin remain permitted. Other uses are governed by [`TRADEMARKS.md`](TRADEMARKS.md).

## Third-party material

Dependencies, quoted sources, datasets, fonts, images, generated artifacts, and other third-party material retain their own licences and notices. A third-party notice or licence takes precedence over these repository defaults for that material.

Contributors must not submit material that cannot lawfully be redistributed under the applicable Pnyx licence.

## No additional governance rights

Using, funding, hosting, distributing, or commercially supporting Pnyx does not grant additional project-governance authority. Governance is defined separately in [`GOVERNANCE.md`](GOVERNANCE.md).

This document describes the project's licensing decision and is not legal advice.
