package dev.pnyx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Spring Boot application entry point for the Pnyx civic governance prototype.
 * <p>
 * Per {@code ../docs/70_Bootstrap/PROTOTYPE_PROFILE.md}, this prototype implements the full
 * Civic Loop: proposals → AI skill-panel reviews → evidence packets → public decisions →
 * implementation tracking → audit trail. The architecture follows hexagonal (ports & adapters)
 * principles with event sourcing and content-addressed public artifacts.
 *
 * @see ../docs/70_Bootstrap/PROTOTYPE_PROFILE.md
 * @see ../docs/70_Bootstrap/PROTOTYPE_PLAN.md
 * @see ../docs/00_INDEX_AND_MAP.md
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class Application {
    /**
     * Starts the application runtime.
     *
     * @param args command-line arguments passed to Spring Boot
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
