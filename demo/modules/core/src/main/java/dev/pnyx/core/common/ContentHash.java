package dev.pnyx.core.common;

/**
 * SHA-256 content hash for canonical JSON payloads.
 * <p>
 * Per {@code ../docs/90_Information/PUBLIC_STORAGE_MODEL.md}, public artifacts are content-addressed
 * by their SHA-256 hash. The hash format is {@code sha256:<hex>}.
 *
 * @see ../docs/90_Information/PUBLIC_STORAGE_MODEL.md
 */
public record ContentHash(String value) {
    /**
     * Validates the hash format and rejects null or malformed values.
     */
    public ContentHash {
        if (value == null || !value.matches("^sha256:[a-f0-9]{64}$")) {
            throw new IllegalArgumentException("Invalid content hash: " + value);
        }
    }

    /**
     * Builds a content hash from a raw hexadecimal SHA-256 digest.
     */
    public static ContentHash of(String hexDigest) {
        return new ContentHash("sha256:" + hexDigest);
    }

    /**
     * Returns the digest portion without the algorithm prefix.
     */
    public String hexDigest() {
        return value.substring("sha256:".length());
    }
}
