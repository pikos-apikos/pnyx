package dev.pnyx.core.common;

/**
 * Discriminates execution routing paths for approved proposals.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/ROUTING.md}, routing paths determine how implementation
 * is assigned — market, state, hybrid, or anti-capture channels.
 *
 * @see ../docs/20_Protocol_Core/ROUTING.md
 */
public enum ExecutionPathType {
    INSTITUTIONAL_ACTION("institutional_action"),
    PUBLIC_INTEREST_VENTURE("public_interest_venture"),
    COMMUNITY_ACTION("community_action");

    private final String code;

    ExecutionPathType(String code) {
        this.code = code;
    }

    /**
     * Stable value written into public execution artifacts.
     *
     * @return the routing code string
     */
    public String value() {
        return code;
    }
}