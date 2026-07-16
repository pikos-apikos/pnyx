package dev.pnyx.infrastructure.test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Base class for integration tests that require a live PostgreSQL database.
 * <p>
 * Spins up a disposable PostgreSQL 16 container via Testcontainers and wires
 * Spring's datasource properties to point at it. All tests extending this class
 * share the same container instance for performance.
 * <p>
 * Per {@code ../docs/80_Runtime/EVENT_MODEL.md}, the event store requires PostgreSQL
 * for hash-chained event persistence. This base class ensures tests run against
 * a real database, not an in-memory substitute.
 *
 * @see ../docs/80_Runtime/EVENT_MODEL.md
 */
@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class DatabaseTestBase {

    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine")
        .withDatabaseName("pnyx_test")
        .withUsername("pnyx")
        .withPassword("pnyx_test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }
}