package dev.pnyx.core.domain.proposal;

import java.util.UUID;

/**
 * Strongly-typed identifier for a proposal aggregate.
 * <p>
 * Prevents {@link java.util.UUID} / {@link String} confusion at the type level.
 * The compiler rejects passing a {@code String} where a {@code ProposalId}
 * is expected, and rejects passing a raw {@code UUID} from one aggregate
 * as another aggregate's typed ID.
 * <p>
 * Per {@code ../docs/40_Identity/IDENTITY_AND_MEMBERSHIP.md}, all civic identifiers derive from
 * cryptographic material. In the prototype, identity is expressed via DID strings; the
 * {@code ProposalId} provides type-safe aggregate identity at compile time.
 *
 * @see ../docs/40_Identity/IDENTITY_AND_MEMBERSHIP.md
 * @see ../docs/80_Runtime/API_SPEC.md
 */
public record ProposalId(UUID value) {

    /**
     * Validates that the wrapped UUID is not null.
     */
    public ProposalId {
        if (value == null) {
            throw new IllegalArgumentException("ProposalId value must not be null");
        }
    }

    public static ProposalId generate() {
        return new ProposalId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
