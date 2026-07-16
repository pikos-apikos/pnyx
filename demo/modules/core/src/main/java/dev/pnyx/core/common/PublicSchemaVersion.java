package dev.pnyx.core.common;

/**
 * Schema versions for public object validation.
 * <p>
 * Per {@code ../docs/90_Information/SCHEMAS.md}, each public artifact type has a versioned schema
 * that governs its structure and required fields.
 *
 * @see ../docs/90_Information/SCHEMAS.md
 */
public enum PublicSchemaVersion {
    PROPOSAL_V1(PublicObjectType.PROPOSAL, "proposal.v1"),
    DECISION_V1(PublicObjectType.DECISION, "decision.v1"),
    IMPLEMENTATION_FORK_V1(PublicObjectType.IMPLEMENTATION_FORK, "implementation_fork.v1"),
    PARTICIPATION_PLAN_V1(PublicObjectType.PARTICIPATION_PLAN, "participation_plan.v1"),
    PARTICIPATION_AUDIT_V1(PublicObjectType.PARTICIPATION_AUDIT, "participation_audit.v1"),
    CIVIC_RECEIPT_V1(PublicObjectType.CIVIC_RECEIPT, "civic_receipt.v1"),
    EXECUTION_MANDATE_V1(PublicObjectType.EXECUTION_MANDATE, "execution_mandate.v1"),
    MONITORING_RECORD_V1(PublicObjectType.MONITORING_RECORD, "monitoring_record.v1"),
    LEARNING_ARTIFACT_V1(PublicObjectType.LEARNING_ARTIFACT, "learning_artifact.v1");

    private final PublicObjectType typeCode;
    private final String code;

    PublicSchemaVersion(PublicObjectType typeCode, String code) {
        this.typeCode = typeCode;
        this.code = code;
    }

    /**
     * Object family that owns this schema version.
     *
     * @return the object type for this schema
     */
    public PublicObjectType objectType() {
        return typeCode;
    }

    /**
     * Stable schema identifier written into public JSON artifacts.
     *
     * @return the schema version code string
     */
    public String value() {
        return code;
    }
}