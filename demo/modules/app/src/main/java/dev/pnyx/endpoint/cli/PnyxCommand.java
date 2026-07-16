package dev.pnyx.endpoint.cli;

import dev.pnyx.core.api.ExportApi;
import dev.pnyx.core.api.PublicCorpusApi;
import dev.pnyx.core.api.PublicCorpusImportApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Picocli CLI commands for export and verification operations.
 * <p>
 * Per {@code ../docs/80_Runtime/API_SPEC.md}, the CLI provides batch operations for exporting
 * public artifacts, verifying the demo corpus, and integrity-checking the public store.
 *
 * @see ../docs/80_Runtime/API_SPEC.md
 * @see ../docs/90_Information/PUBLIC_STORAGE_MODEL.md
 */
@Component
@RequiredArgsConstructor
@Command(name = "pnyx", subcommands = {
    PnyxCommand.ExportCommand.class,
    PnyxCommand.VerifyCommand.class,
    PnyxCommand.RebuildCommand.class
})
public class PnyxCommand implements Runnable {

    private final ExportApi exportApi;

    @Override
    /**
     * Executes the default command behavior.
     */
    public void run() {
        new CommandLine(this).usage(System.out);
    }

    @Command(name = "export", description = "Export public artifacts")
    @RequiredArgsConstructor
    @Slf4j
    static class ExportCommand implements Runnable {
        private final ExportApi exportApi;

        @Override
        public void run() {
            var manifest = exportApi.exportManifest();
            log.info("Exported manifest: {} epoch {}", manifest.networkName(),
                manifest.epoch());
        }
    }

    @Command(name = "verify", description = "Verify public corpus integrity")
    @RequiredArgsConstructor
    @Slf4j
    static class VerifyCommand implements Runnable {

        private final PublicCorpusApi publicCorpusApi;

        @Option(names = {"--path"}, defaultValue = "./data/public",
            description = "Path to public corpus")
        private String path;

        @Override
        public void run() {
            PublicCorpusApi.VerificationReport report = publicCorpusApi.verify(path);
            log.info("PNyx public corpus verification");
            log.info("Path: {}", report.path());
            log.info("Objects checked: {}", report.objectsChecked());
            log.info("Hash errors: {}", report.hashErrors());
            log.info("Schema errors: {}", report.schemaErrors());
            log.info("Index errors: {}", report.indexErrors());
            report.issues().forEach(issue ->
                log.info("Issue: {} - {}", issue.location(), issue.message()));
            log.info("Status: {}", report.ok() ? "OK" : "FAILED");
        }
    }

    @Command(name = "rebuild", description = "Rebuild disposable read models from public artifacts")
    @RequiredArgsConstructor
    @Slf4j
    static class RebuildCommand implements Runnable {

        private final PublicCorpusImportApi publicCorpusImportApi;

        @Option(names = {"--path"}, defaultValue = "./data/public",
            description = "Path to public corpus")
        private String path;

        @Override
        public void run() {
            PublicCorpusImportApi.RebuildReport report = publicCorpusImportApi.rebuild(path);
            log.info("PNyx public corpus rebuild");
            log.info("Path: {}", report.path());
            log.info("Verified: {}", report.verified());
            log.info("Proposals: {}", report.proposals());
            log.info("Decisions: {}", report.decisions());
            log.info("Implementation forks: {}", report.implementationForks());
            log.info("Skipped objects: {}", report.skippedObjects());
            report.issues().forEach(issue ->
                log.info("Issue: {} - {}", issue.location(), issue.message()));
            log.info("Status: {}", report.ok() ? "OK" : "FAILED");
        }
    }
}
