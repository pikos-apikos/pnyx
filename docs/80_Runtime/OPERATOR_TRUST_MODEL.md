# OPERATOR_TRUST_MODEL

## 1. Purpose

This document defines the trust model, constraints, and control boundaries for the runtime operators of the system.

Its purpose is to prevent the software and infrastructure layer from silently becoming a hidden sovereignty layer.

The governance model assumes that procedural rules alone are insufficient if runtime control, deployment authority, key custody, or data-plane control are concentrated in a small set of unaccountable actors.

This document therefore defines how operators are bounded, how their powers are separated, and how operator actions remain challengeable, auditable, and unable to mutate active civic processes.

---

## 2. Core Principle

Operators may run the system.
They may not rule the system.

Operational control over infrastructure, deployment, configuration, keys, or recovery does not confer legitimacy to alter governance outcomes, active cases, framework parameters, routing decisions, audit history, or constitutional constraints.

No operational privilege may be interpreted as civic authority.

---

## 3. Threat Assumption

The system assumes that operator compromise, collusion, convenience drift, and emergency overreach are normal risks.

The primary operator risks are:
- hidden configuration drift,
- silent runtime substitution,
- root-level override of governance behavior,
- key concentration,
- undeclared emergency pathways,
- active-case manipulation,
- unlogged deployment of governance-affecting code,
- recovery procedures used as legitimacy bypass,
- separation failure between software maintenance and civic control.

The threat model assumes that a technically capable operator with insufficient constraints can bypass the spirit of governance while preserving the appearance of procedural compliance.

---

## 4. Operator Classes

The system may define operator classes for runtime separation, but these classes are operational roles, not civic roles.

Typical operator classes may include:
- infrastructure operator,
- deployment operator,
- key custodian,
- observability operator,
- incident responder,
- recovery operator,
- release approver,
- audit mirror maintainer.

No single operator class should control the full chain from code to deployment to key use to audit visibility.

---

## 5. Forbidden Concentrations

The following concentrations are forbidden except under explicitly declared and time-bounded bootstrap constraints:
- one actor controlling deployment, runtime configuration, and signing keys,
- one actor controlling audit storage and audit presentation,
- one actor controlling both governance-affecting code release and emergency intervention,
- one actor controlling classification logic, panel selection logic, and route computation,
- one actor controlling recovery, key rotation, and active-case state mutation,
- one actor operating both the public runtime and an undisclosed shadow runtime.

Operational convenience is not a valid reason for violating separation.

---

## 6. Governance-Affecting Changes

A governance-affecting operational change is any deployment, configuration update, key change, feature flag change, model swap, ruleset modification, prompt change, classifier change, or dependency substitution that can materially alter:
- proposal classification,
- panel selection,
- evidence interpretation,
- routing logic,
- packet generation,
- threshold behavior,
- state transitions,
- audit visibility,
- epoch binding,
- emergency activation,
- meta-governance behavior.

Governance-affecting changes must never be treated as ordinary maintenance.

---

## 7. Active-Case Protection

No operator action may alter the effective rules of an active case.

An active case includes any live:
- Proposal,
- MetaProposal,
- Challenge,
- EmergencyAction,
- ReviewCase,
- Vote window,
- Packet publication period,
- panel selection process,
- evidence assessment cycle.

The following are forbidden during an active case if they could affect case outcome:
- model replacement,
- classifier replacement,
- skill routing change,
- threshold change,
- review window change,
- packet template change,
- evidence interpretation change,
- silent dependency upgrade,
- undeclared rollback to prior ruleset.

If such a change is operationally unavoidable, the case must be paused, logged, invalidated, or re-run according to protocol.

---

## 8. Dual Control

All governance-affecting operator actions must require dual control or stronger multi-party control.

This applies at minimum to:
- deployment of governance-affecting code,
- activation of new runtime rules,
- key rotation for signing or verification systems,
- emergency switch activation,
- restoration from backup,
- migration of authoritative audit stores,
- model or skill runtime substitution,
- enabling or disabling policy enforcement guards.

Dual control means that no single operator may both initiate and finalize a governance-affecting action.

---

## 9. Key Separation

Cryptographic material relevant to governance integrity must be separated by purpose.

At minimum, the system should separate:
- deployment signing keys,
- audit integrity keys,
- packet publication keys,
- emergency authorization keys,
- recovery keys,
- verification keys.

No single key should grant full authority across governance, runtime, audit, and recovery domains.

Key custody procedures must be logged, versioned, and challengeable.

---

## 10. Runtime Authenticity

The public must be able to verify that the published runtime corresponds to the declared runtime.

The system should therefore provide verifiable runtime authenticity signals, such as:
- signed release manifests,
- published build provenance,
- public runtime identifiers,
- configuration fingerprints,
- model/skill version disclosures,
- epoch declarations,
- audit mirror consistency proofs.

A public system must not rely on "trust us, this is the code currently running."

---

## 11. Shadow Runtime Prohibition

A shadow runtime is any undeclared runtime, fallback service, hidden model path, hidden scoring engine, hidden classifier, or undeclared operational override path capable of influencing live governance outcomes.

Shadow runtimes are forbidden.

Emergency fallbacks, maintenance runtimes, or degraded-mode runtimes must be:
- explicitly declared,
- functionally constrained,
- auditable,
- time-bounded,
- incapable of silently assuming full governance authority.

---

## 12. Configuration Discipline

Configuration changes must be treated according to their governance impact, not according to whether they are "just config."

If a configuration parameter changes:
- classification,
- selection,
- thresholds,
- review timing,
- evidence policy,
- routing,
- packet content,
- redaction behavior,
- emergency triggers,
- audit retention or visibility,
then the change is governance-affecting and subject to full operator constraints.

There must be no hidden governance layer implemented as convenience configuration.

---

## 13. Emergency Operator Powers

Emergency operator powers exist only to preserve system integrity, not to preserve political convenience.

Emergency operator powers may be used only for:
- preventing data loss,
- halting active security compromise,
- preventing integrity destruction,
- containing infrastructure-level failure,
- isolating malicious runtime behavior,
- preserving auditable continuity.

Emergency operator powers may not be used to:
- alter proposal outcome,
- suppress packet publication,
- reroute a proposal for substantive convenience,
- shorten or extend deliberation windows for advantage,
- suppress dissent,
- rewrite history,
- evade audit visibility,
- create a parallel decision path.

All emergency use must auto-enter ex post review.

---

## 14. Recovery and Restore

Backup restore, disaster recovery, rollback, or replay mechanisms are high-risk governance actions.

A recovery action must:
- preserve append-only audit continuity,
- declare the restored state boundary,
- identify any lost or re-derived projections,
- bind restored state to the same declared epoch logic,
- record exactly what was replayed, reconstructed, or invalidated,
- trigger review if any active case was affected.

Recovery must never be used as a disguised framework bypass.

---

## 15. Audit Independence

The authoritative audit record and the public audit view must not collapse into a single operator-controlled surface.

At minimum, the system should maintain:
- authoritative append-only audit storage,
- independently reproducible audit mirrors,
- public-readable audit views,
- integrity proofs linking views to the authoritative event stream.

An operator must not be able to change history merely by changing the UI through which the public sees history.

---

## 16. Observability Boundaries

Operational observability exists to maintain reliability and integrity.
It must not become undeclared civic surveillance.

Logs, metrics, traces, and operator dashboards must not silently expand into behavioral profiling of citizens beyond what is explicitly declared and legitimately required for system operation.

Diagnostic visibility does not authorize political visibility.

---

## 17. Operator Audit Events

Every governance-affecting operator action must produce an operator audit event.

Such events should include at minimum:
- operator action type,
- initiator role,
- approver role,
- affected subsystem,
- affected epoch or runtime version,
- active-case impact assessment,
- integrity risk classification,
- execution time,
- verification artifact,
- rollback availability,
- follow-up review requirement.

Unlogged governance-affecting operator actions are invalid.

---

## 18. Release Classes

Runtime releases should be classified by governance impact.

At minimum:
- non-governance-affecting release,
- governance-adjacent release,
- governance-affecting release,
- emergency integrity release.

The stronger the governance impact, the stronger the approval, announcement, delay, and review requirements.

No release class may be mislabeled for speed.

---

## 19. Delay and Activation Rules

Governance-affecting operator changes should not take immediate effect on live civic processes unless required for integrity preservation.

Default rule:
- changes are announced,
- changes are bound to a future activation point,
- changes do not alter active cases,
- changes remain challengeable before activation.

This operationalizes the principle that the system may change its runtime, but not while a civic decision is already being shaped by that runtime.

---

## 20. Bootstrap Trust Debt

Bootstrap may temporarily require reduced operator separation.
This is constitutional debt, not normal legitimacy.

During bootstrap, the system must explicitly disclose:
- which separations do not yet exist,
- which actors temporarily hold concentrated powers,
- which protections are simulated rather than fully realized,
- what conditions are required to exit bootstrap,
- what timeline or threshold triggers stricter separation,
- what review path exists for early operator concentration.

Bootstrap trust must never be mistaken for solved trust.

---

## 21. Exit Criteria from Bootstrap Operator Concentration

The system should define explicit exit conditions for bootstrap operator concentration.
These may include:
- multi-party key custody in place,
- independent audit mirror established,
- governance-affecting deploy separation established,
- public release provenance verifiable,
- operator classes separated across distinct actors,
- emergency recovery path reviewed under live conditions,
- challengeable runtime authenticity process operational.

No bootstrap concentration should persist merely because it is familiar.

---

## 22. Operator Legitimacy Boundary

Operators are custodians of runtime integrity.
They are not custodians of political meaning.

They may preserve continuity, authenticity, availability, and recoverability.
They may not determine legitimacy, redefine civic categories, alter public reasoning outcomes, or substitute technical discretion for constitutional process.

When in conflict, operational convenience loses to civic legitimacy.

---

## 23. Invariants

The operator trust model preserves the following invariants:
- no hidden governance-affecting runtime substitution,
- no single-actor control over the governance-critical stack,
- no active-case mutation through operational privilege,
- no undeclared shadow runtime,
- no governance-affecting change without dual control,
- no recovery without audit continuity declaration,
- no silent collapse of audit independence,
- no emergency power without ex post review,
- no normalization of bootstrap trust debt.

---

## 24. Closing Principle

The most dangerous concentration of power in a procedural system often sits below the procedure itself.

A governance system that constrains citizens, panels, and frameworks but leaves operators unconstrained has only relocated sovereignty into the runtime.

Therefore, operator power must be treated as constitutional power whenever it can affect what the public sees, what the system computes, what rules are active, or what outcomes remain possible.
