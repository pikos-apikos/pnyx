package dev.pnyx.infrastructure.test;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Detects fixture directories that have no corresponding test class.
 *
 * <p>When a fixture scenario directory exists in {@code src/test/resources/fixtures/}
 * but no test file references its scenario name, the fixture is orphaned and should be
 * cleaned up or the missing test written. This prevents test data drift.
 */
class FixtureDriftGuardTest {

    private static final Path FIXTURES_DIR = Path.of(
        "modules/app/src/test/resources/fixtures");

    private static final Path TEST_SOURCES_DIR = Path.of(
        "modules/app/src/test/java");

    @Test
    void noOrphanedFixtureDirectories() {
        var fixtureDirs = listFixtureScenarioDirectories();
        var testSources = readAllTestSources();

        var orphans = new ArrayList<String>();
        for (var dir : fixtureDirs) {
            // Skip .gitkeep and hidden files/dirs
            if (dir.getFileName().toString().startsWith(".")) { continue; }
            String scenarioName = dir.getFileName().toString();
            // Convert kebab-case to searchable: "deliberation-simple-panel"
            // The test file should reference this scenario name
            if (!testSources.contains(scenarioName)) {
                orphans.add(scenarioName);
            }
        }

        assertThat(orphans)
            .as("Fixture directories with no corresponding test reference: %s. "
                + "Either write a test using the fixture, or remove the directory.",
                orphans)
            .isEmpty();
    }

    private static List<Path> listFixtureScenarioDirectories() {
        if (!Files.isDirectory(FIXTURES_DIR)) {
            return List.of();
        }
        try (var files = Files.list(FIXTURES_DIR)) {
            return files.filter(Files::isDirectory).toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String readAllTestSources() {
        if (!Files.isDirectory(TEST_SOURCES_DIR)) { return ""; }
        var sb = new StringBuilder();
        try (var files = Files.walk(TEST_SOURCES_DIR)) {
            files.filter(f -> f.toString().endsWith(".java"))
                .forEach(f -> {
                    try {
                        sb.append(Files.readString(f));
                    } catch (IOException ignored) {}
                });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return sb.toString();
    }
}