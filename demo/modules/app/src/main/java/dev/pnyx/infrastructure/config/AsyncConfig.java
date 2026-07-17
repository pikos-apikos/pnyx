package dev.pnyx.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Spring async configuration for non-blocking skill execution.
 * <p>
 * Per {@code ../docs/60_Skills/EXECUTOR_MODEL.md}, skill panel reviews execute asynchronously,
 * with progress tracked via callback. This configures the thread pool for concurrent reviews.
 */
@Configuration
@EnableAsync
@Profile("!test")
public class AsyncConfig {

    /**
     * Provides the bounded executor used by Spring asynchronous methods.
     *
     * @return a virtual thread executor
     */
    @Bean
    public Executor taskExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
