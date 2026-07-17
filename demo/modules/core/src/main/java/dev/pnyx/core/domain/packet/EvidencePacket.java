package dev.pnyx.core.domain.packet;

import java.util.List;

/**
 * Value object representing an evidence packet.
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public record EvidencePacket(
    String summary,
    List<String> findings,
    List<String> risks,
    List<String> missingInformation,
    List<String> citations
) {}
