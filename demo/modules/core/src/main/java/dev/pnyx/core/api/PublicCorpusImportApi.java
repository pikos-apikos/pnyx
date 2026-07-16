package dev.pnyx.core.api;

import java.util.List;

/**
 * Driving port for rebuilding disposable read models from public artifacts.
 *
 * @see ../docs/90_Information/PUBLIC_STORAGE_MODEL.md
 * @see ../docs/90_Information/SCHEMAS.md
 */
public interface PublicCorpusImportApi {

    /**
     * Summary of public artifacts imported during a rebuild pass.
     */
    record RebuildReport(String path, boolean verified, long proposals,
                         long decisions, long implementationForks,
                         long skippedObjects, List<PublicCorpusApi.VerificationIssue> issues) {

        /**
         * Indicates whether the corpus was verified and all supported objects were parsed.
         *
         * @return true if verified and no issues
         */
        public boolean ok() {
            return verified && issues.isEmpty();
        }
    }

    /**
     * Rebuilds disposable read-model state from the public corpus rooted at the given path.
     *
     * @param path root directory of the public corpus to import
     * @return rebuild report with counts of imported artifacts
     */
    RebuildReport rebuild(String path);
}