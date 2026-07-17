package dev.pnyx.infrastructure.test;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Example test demonstrating the fixture pattern.
 * References the "deliberation-simple-panel" fixture scenario.
 */
class FixtureExampleTest {

    @Test
    void shouldLoadDeliberationFixture() {
        var payload = TestFixtureLoader.readString(
            "deliberation-simple-panel", "event_payload.json");
        assertThat(payload).contains("Traffic congestion");

        var expected = TestFixtureLoader.readJson(
            "deliberation-simple-panel", "expected_reviews.json",
            ExpectedReviews.class);
        assertThat(expected.expectedRoles()).contains("legal-reviewer");
    }

    private record ExpectedReviews(
        java.util.List<String> expectedRoles,
        boolean requireFindings
    ) {}
}