package dev.pnyx.core.domain.packet;

import java.util.List;

/**
 * Value object representing an adversarial synthesis.
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public record AdversarialSynthesis(
    String strongestCaseFor,
    String strongestCaseAgainst,
    List<String> unknowns,
    List<String> minorityViews,
    String captureRiskNote,
    String reversibilityNote
) {}
