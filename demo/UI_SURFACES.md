# Pnyx UI Surfaces

The Pnyx runtime exposes two interfaces over the same civic objects, lifecycle, and public record.

## Citizen UI

The Citizen UI is the default public entrance.

It answers, in this order:

1. What is the public issue?
2. Why might it require attention?
3. Where is it in the civic loop?
4. What is required from the citizen now?
5. How much time should that require?
6. What happens next?
7. Where can the full evidence and audit record be inspected?

Routes:

- `/` — civic home
- `/proposals/new` — problem-first intake
- `/proposals/{proposalId}` — citizen brief by default

Internal lifecycle names are translated into human-facing language. The citizen may always open the underlying workbench and public record.

## Protocol Workbench

The Protocol Workbench is the operator, auditor, researcher, and implementation surface.

It exposes the complete protocol machinery, including participation plans, specialist activity, civic receipts, execution mandates, monitoring, learning, and the hash-linked audit trail.

Routes:

- `/workbench` — workbench dashboard
- `/workbench/proposals/{proposalId}` — full proposal workbench
- `/audit` — event-stream verification

## Shared truth

The two surfaces must never become two civic systems.

They share:

- the same proposal aggregate;
- the same public artifacts;
- the same event stream;
- the same lifecycle guards;
- the same human judgment;
- the same audit trail.

Presentation may differ. Civic state and public memory may not.

## Current implementation boundary

The first split deliberately reuses the existing server-rendered proposal detail and selects the surface through stable Java redirects plus a browser presenter.

The next refinement should introduce an explicit Java `CitizenBriefView` containing affected groups, strongest disagreement, unknowns, evidence summary, requested attention, and estimated participation time. That view should remain derived from canonical artifacts rather than becoming a second source of truth.
