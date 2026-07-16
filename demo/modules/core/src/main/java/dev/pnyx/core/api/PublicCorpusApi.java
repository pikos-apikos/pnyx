package dev.pnyx.core.api;

import java.util.List;

/**
 * Driving port for validating public content-addressed artifacts.
 *
 * @see ../docs/90_Information/PUBLIC_STORAGE_MODEL.md
 * @see ../docs/90_Information/SCHEMAS.md
 */
public interface PublicCorpusApi {

    /**
     * A single verification fault found in the public corpus.
     *
     * @param location path or hash of the artifact with the issue
     * @param message  description of the problem
     */
    record VerificationIssue(String location, String message) { }

    /**
     * Aggregate result of a full public corpus verification pass.
     *
     * @param path           root path of the verified corpus
     * @param objectsChecked total objects inspected
     * @param hashErrors     count of hash integrity failures
     * @param schemaErrors   count of schema validation failures
     * @param indexErrors    count of index consistency failures
     * @param issues         individual verification issues found
     */
    record VerificationReport(String path, long objectsChecked, long hashErrors,
                              long schemaErrors, long indexErrors,
                              List<VerificationIssue> issues) {

        /**
         * Indicates whether all public corpus checks passed.
         *
         * @return true if no errors found
         */
        public boolean ok() {
            return hashErrors == 0 && schemaErrors == 0 && indexErrors == 0 && issues.isEmpty();
        }
    }

    /**
     * Verifies a public artifact corpus rooted at the given path.
     *
     * @param path root directory of the public corpus
     * @return detailed verification report
     */
    VerificationReport verify(String path);
}