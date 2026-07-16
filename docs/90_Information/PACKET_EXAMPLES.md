# PACKET_EXAMPLES

> **Note:** This document has been updated to reflect the normative rules in [SYSTEM_PATCH_v2](../99_Reference/SYSTEM_PATCH_v2.md).

## 1. Purpose

This document provides concrete examples of packet types used by the system, specifically focusing on the new **Evidence Packet** structure mandated by the system patch.

Its purpose is to make the packet layer operationally legible by showing:
- how an Evidence Packet looks in practice,
- how material claims are linked to sources,
- how jurisdiction is declared,
- how unknowns and contested points are preserved,
- how confidence is derived from components,
- how review-routing signals are surfaced,
- how human-expert packets are structured,
- and how adversarial or dissent-preserving synthesis is presented.

These examples are illustrative of the required structure and evidence discipline.

---

## 2. Example 1 — Evidence Packet (with Source Register and Claims)

### 2.1 Scenario
A proposal to install traffic-calming measures on Karyotaki Street.

### 2.2 Example Packet

**Packet Type:** Evidence Packet
**Proposal Title:** Traffic calming for Karyotaki Street
**Packet Version:** 1
**Status:** Published
**Executor:** AI_Policy_Analyst_v4 (Contextual Rank: A)

---

### 1. Jurisdiction Declaration
**Jurisdiction:** Local Municipal (Traffic & Safety)
**Applicable Frameworks:** Municipal Code Title 14 (Streets and Sidewalks), Neighborhood Assembly Advisory Guidelines.

---

### 2. Source Register
- **[SRC-001]** Local Resident Petition (Submitted 2026-03-15) - *Primary Testimony*
- **[SRC-002]** Municipal Traffic Data Report (2025 Q4) - *Official Record*
- **[SRC-003]** Local Business Association Letter (2026-04-01) - *Stakeholder Input*

---

### 3. Material Claims
- **Claim 1:** Average vehicle speed exceeds the posted limit by 15 mph during school hours.
  - *Source Link:* [SRC-002] Section 4.2
  - *Verification:* Supported by official data.
- **Claim 2:** Proposed speed bumps will delay emergency response times by 45 seconds.
  - *Source Link:* [SRC-003] Page 2
  - *Verification:* Contested. No official emergency services study provided.

---

### 4. Unknowns and Contested Points
- **Unknown 1:** Exact impact on delivery vehicle access for local businesses.
- **Contested Point 1:** Whether the safety benefits outweigh the potential emergency response delay (Claim 2).

---

### 5. Confidence Components and Derived Confidence
- **Evidence Coverage:** Moderate (Missing emergency services study)
- **Source Quality:** High (Official data + direct testimony)
- **Contradiction Handling:** Explicitly surfaced (Claim 2)
- **Derived Confidence Score:** 72/100 (Banded: Moderate-High)

---

### 6. Review-Routing Signals
- **Signal:** `human_review_recommended`
- **Reason:** High rights impact (safety vs. emergency access) and missing critical evidence (emergency services study).

---

## 3. Example 2 — Human-Expert Packet

### 3.1 Scenario
A human traffic engineer reviews the traffic-calming proposal.

### 3.2 Example Packet

**Packet Type:** Human-Expert Evidence Packet
**Expert ID:** HEX-992 (Registered Traffic Engineer)
**Mandate:** Assess emergency response impact of proposed speed bumps.

---

### 1. Source Register
- **[SRC-004]** National Fire Protection Association (NFPA) Guidelines on Traffic Calming.
- **[SRC-005]** Site visit observations (2026-04-10).

---

### 2. Material Claims
- **Claim 1:** The specific type of speed bump proposed (Type C) typically causes a 5-10 second delay per bump for heavy fire apparatus.
  - *Source Link:* [SRC-004] Chapter 8.
- **Claim 2:** The street layout allows for alternative emergency routing that mitigates this delay.
  - *Source Link:* [SRC-005]

---

### 3. Derived Confidence
- **Derived Confidence Score:** 90/100 (High) - Based on direct professional observation and established guidelines.

---

## 4. Example 3 — Adversarial / Dissent-Preserving Synthesis

### 4.1 Scenario
Synthesizing the AI Evidence Packet and the Human-Expert Packet.

### 4.2 Example Packet

**Packet Type:** Synthesis Packet
**Status:** Published

---

### 1. Strongest Case In Favor (Safety Focus)
Based on [SRC-001] and [SRC-002], there is a documented, severe speeding issue during vulnerable hours. The intervention is necessary for pedestrian safety.

### 2. Strongest Case Against (Access Focus)
Based on [SRC-003], local businesses fear delivery disruption. Furthermore, initial concerns about emergency delays were raised, though partially mitigated by expert review.

### 3. Preserved Disagreement & Evidence Gaps
- **Disagreement:** The business association [SRC-003] claims severe disruption, while the traffic data [SRC-002] suggests alternative loading zones are sufficient. This remains unresolved.
- **Evidence Gap:** No formal accessibility review for mobility-impaired residents has been conducted.

### 4. Synthesis Conclusion
The proposal has strong safety backing but lacks complete stakeholder impact data. It is routed for a conditional pilot phase rather than permanent installation.