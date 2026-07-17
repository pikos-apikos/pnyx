package dev.pnyx.infrastructure.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Loads test fixtures from {@code src/test/resources/fixtures/<scenario>/}.
 *
 * <p>Fixtures are JSON files that represent test inputs and expected outputs.
 * Using file-based fixtures instead of inline strings makes test data reusable,
 * reviewable, and independently verifiable.
 */
public final class TestFixtureLoader {

    private static final String FIXTURES_ROOT = "fixtures";
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .findAndRegisterModules();

    private TestFixtureLoader() {}

    /**
     * Reads a fixture file as a raw JSON string.
     *
     * @param scenario the fixture scenario directory name
     * @param fileName the fixture file name (e.g., "event_payload.json")
     * @return the file contents as a string
     * @throws UncheckedIOException if the fixture cannot be read
     */
    public static String readString(String scenario, String fileName) {
        try {
            var path = resolveFixture(scenario, fileName);
            return Files.readString(path);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load fixture: "
                + scenario + "/" + fileName, e);
        }
    }

    /**
     * Reads and deserializes a fixture file into the given type.
     *
     * @param <T>      the target type
     * @param scenario the fixture scenario directory name
     * @param fileName the fixture file name (e.g., "expected_reviews.json")
     * @param type     the target Java type
     * @return the deserialized object
     * @throws UncheckedIOException if the fixture cannot be read
     */
    public static <T> T readJson(String scenario, String fileName, Class<T> type) {
        try {
            var path = resolveFixture(scenario, fileName);
            return MAPPER.readValue(path.toFile(), type);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load fixture: "
                + scenario + "/" + fileName, e);
        }
    }

    private static Path resolveFixture(String scenario, String fileName) {
        // Try classpath first, then filesystem
        var classpathPath = FIXTURES_ROOT + "/" + scenario + "/" + fileName;
        var resource = TestFixtureLoader.class.getClassLoader().getResource(classpathPath);
        if (resource != null) {
            return Paths.get(resource.getPath());
        }
        // Fallback: resolve relative to project root
        return Path.of("modules/app/src/test/resources", FIXTURES_ROOT, scenario, fileName);
    }
}
