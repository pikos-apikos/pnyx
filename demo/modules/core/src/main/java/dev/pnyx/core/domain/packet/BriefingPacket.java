package dev.pnyx.core.domain.packet;

/**
 * Value object representing a briefing packet.
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public record BriefingPacket(
    EvidencePacket evidence,
    AdversarialSynthesis synthesis,
    String proposalSummary,
    String classificationSummary,
    String panelCompositionSummary
) {}
