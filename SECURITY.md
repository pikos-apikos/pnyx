# Security Policy

## Project status

Pnyx is an experimental civic protocol and prototype. It has not been independently security-audited and must not yet be treated as production-ready election infrastructure, identity infrastructure, a binding public decision system, or a secure custodian of sensitive personal data.

## Supported versions

Security fixes are applied to the latest revision of the default branch. No released version currently receives long-term security support.

## Reporting a vulnerability

Please do **not** open a public issue for a suspected vulnerability.

Preferred reporting path:

1. Use GitHub's private vulnerability reporting feature for this repository when it is enabled.
2. If private vulnerability reporting is unavailable, contact the repository owner privately using the contact information on their GitHub profile.

Include:

- the affected component and revision;
- reproduction steps or a minimal proof of concept;
- the likely impact;
- whether exploitation is known or suspected;
- any suggested mitigation;
- whether the report contains sensitive personal data.

Do not access, modify, retain, or publish data that is not yours. Use the minimum proof necessary to demonstrate the issue.

## Response process

Maintainers will make a best-effort acknowledgment, validate the report, coordinate a fix, and credit the reporter unless anonymity is requested. Response times are not guaranteed during the bootstrap phase.

Public disclosure should be coordinated until a fix or mitigation is available. Maintainers may request additional time when a vulnerability affects identity, provenance, cryptographic continuity, public artifacts, authorization, or civic decision integrity.

## High-impact areas

Reports are especially important when they involve:

- authentication, authorization, or standing proofs;
- event-stream or public-artifact integrity;
- signature, hash-chain, or provenance verification;
- injection, cross-site scripting, or unsafe rendering of public input;
- secret or credential exposure;
- personal-data collection or deanonymization;
- manipulation of panel selection, deliberation, judgment, execution, or audit records;
- supply-chain compromise;
- denial of service that can suppress participation or distort deadlines.

## Security is broader than software exploitation

Pnyx also treats coercion, capture, provenance laundering, selective visibility, and privacy leakage as civic-security risks. These may be reported privately when public disclosure could cause harm.
