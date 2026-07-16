package dev.pnyx.core.common;

/**
 * Discriminated types for content-addressed public objects.
 * <p>
 * Per {@code ../docs/90_Information/PUBLIC_STORAGE_MODEL.md}, each public object has a type
 * that determines its schema, storage path, and interpretation.
 *
 * @see ../docs/90_Information/PUBLIC_STORAGE_MODEL.md
 * @see ../docs/90_Information/SCHEMAS.md
 */
public enum PublicObjectType {
    PROPOSAL("proposal"),
    DECISION("decision"),
    IMPLEMENTATION_FORK("implementation_fork"),
    PARTICIPATION_PLAN("participation_plan"),
    PARTICIPATION_AUDIT("participation_audit"),
    CIVIC_RECEIPT("civic_receipt"),
    EXECUTION_MANDATE("execution_mandate"),
    MONITORING_RECORD("monitoring_record"),
    LEARNING_ARTIFACT("learning_artifact");

    private final String code;

    PublicObjectType(String code) {
        this.code = code;
    }

    /**
     * Stable storage directory and manifest value for the object family.
     *
     * @return the type code string
     */
    public String value() {
        return code;
    }

    /**
     * Returns the enum constant for the given storage value, or throws if unknown.
     *
     * @param value the storage value string to look up
     * @return the matching enum constant
     * @throws IllegalArgumentException if the value does not match any known type
     */
    public static PublicObjectType fromValue(String value) {
        for (PublicObjectType t : values()) {
            if (t.code.equals(value)) { return t; }
        }
        throw new IllegalArgumentException("Unknown PublicObjectType: " + value);
    }
}