package dev.pnyx.core.domain.proposal;

/**
 * Event emitted when a proposal's classification is recorded.
 *
 * @see ../docs/30_Classification/CLASSIFICATION.md
 */
public record ClassificationRecorded(
    ProposalId proposalId,
    ClassificationResult classification
) implements ProposalEvent {}
