package dev.pnyx.core.domain.proposal;

import java.util.UUID;

/**
 * Strongly-typed identifier for a decision aggregate.
 * <p>
 * Prevents {@link java.util.UUID} / {@link String} confusion at the type level.
 * The compiler rejects passing a {@code String} where a {@code DecisionId}
 * is expected.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/PROTOCOL.md}, a decision is rendered after deliberation
 * and locks the execution path for the proposal. The {@code DecisionId} provides type-safe
 * decision identity.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 */
public record DecisionId(UUID value) {

    /**
     * Validates that the wrapped UUID is not null.
     */
    public DecisionId {
        if (value == null) {
            throw new IllegalArgumentException("DecisionId value must not be null");
        }
    }

    public static DecisionId generate() {
        return new DecisionId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
