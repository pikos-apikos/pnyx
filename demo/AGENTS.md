# AGENTS.md

Guidelines for agentic coding agents working in the Pnyx Java repository.

## Project Overview

Spring Boot 3.4 hexagonal prototype implementing the Pnyx Civic Loop: proposals → AI skill-panel reviews → evidence packets → public decisions → implementation tracking → audit trail. The system is event-sourced with an append-only, SHA-256 hash-chained PostgreSQL event store. Public artifacts are content-addressed canonical JSON files — the database is a disposable read model.

**Stack:** Java 25, Gradle 9.5, Kotlin DSL, jOOQ, Spring Data JDBC, Flyway, PostgreSQL 16, Testcontainers, Spring AI (OpenAI), Thymeleaf + HTMX, Picocli, ArchUnit 1.4+, JUnit 5.

## Build/Lint/Test Commands

```bash
# Full build (compile + test)
./gradlew build

# Run all tests
./gradlew test

# Run tests for a specific module
./gradlew :core:test
./gradlew :app:test
./gradlew :skill-openai:test
./gradlew :validation-openai:test

# Run a single test class
./gradlew :core:test --tests "dev.pnyx.core.domain.proposal.ProposalTest"

# Run a single test method
./gradlew :core:test --tests "dev.pnyx.core.domain.proposal.ProposalTest.shouldStartInDraftState"

# Compile only (no tests)
./gradlew compileJava

# Run application locally (start PostgreSQL first)
docker compose up -d postgres
./gradlew :app:bootRun

# Run CLI commands
./gradlew :app:bootRun --args="export"
./gradlew :app:bootRun --args="verify --path ./data/public"

# Stop PostgreSQL
docker compose down
```

## Code Style

### Naming

- **Classes**: `PascalCase` (`ProposalService`, `PostgresEventStoreAdapter`)
- **Methods**: `camelCase` (`submitProposal`, `readStream`)
- **Fields**: `camelCase` (`eventStore`, `publicStore`)
- **Constants**: `UPPER_SNAKE_CASE` (`SKILL_ROLES`, `DEFAULT_NETWORK`)
- **Packages**: lowercase dotted (`dev.pnyx.core.domain.proposal`)
- **Test classes**: `[ClassName]Test` (`ProposalServiceTest`)
- **Test methods**: descriptive (`shouldSubmitAndRetrieveProposal`)

### Lombok

```java
@Slf4j                            // Logging
@RequiredArgsConstructor           // Final-field constructor injection
@Builder                          // Builder for complex objects
```

The project uses constructor injection with `final` fields. Do not use `@Autowired` field injection. Lombok is configured in root `lombok.config`.

### Javadoc and Doc References

Every public class, interface, and method MUST have a Javadoc. The Javadoc MUST reference the relevant `docs/` specification documents using the `{@code docs/.../FILE.md}` convention:

```java
/**
 * Drives the proposal lifecycle: submission, classification, and export.
 * <p>
 * Per {@code ../docs/20_Protocol_Core/PROTOCOL.md §6}: submission is the first
 * transition after draft creation. The event store appends each transition
 * as a hash-chained event per {@code ../docs/80_Runtime/EVENT_MODEL.md}.
 *
 * @see ../docs/20_Protocol_Core/PROTOCOL.md
 * @see ../docs/80_Runtime/EVENT_MODEL.md
 */
public interface ProposalApi { ... }
```

Referenced docs live under `docs/` with a numbered layer structure (`10_Constitutional/`, `20_Protocol_Core/`, `60_Skills/`, `80_Runtime/`, `90_Information/`, etc.). Use the `@see` tag for standalone references and `{@code}` for inline section citations. See `../docs/00_INDEX_AND_MAP.md` for the full document map.

**Rules:**
- Every production class must reference at least one `docs/` document in its Javadoc
- Inline `{@code docs/...}` for specific section references (e.g., `{@code ../docs/80_Runtime/STATE_MACHINE.md §4.1}`)
- `@see docs/...` for general related documents
- Never reference a section that doesn't exist — verify the doc content first
- Do NOT edit any file under `docs/` — the docs are the authoritative specification

### Configuration Properties

Use `@ConfigurationProperties` classes with Lombok `@Getter`/`@Setter` — never inline `@Value`:

```java
// app/infrastructure/config/PnyxProperties.java
@Getter @Setter @Component
@ConfigurationProperties("pnyx")
public class PnyxProperties {
    private String publicStoragePath;
    private String network;
}
```

Inject the properties class, not individual values:

```java
@RequiredArgsConstructor
public class ExportService {
    private final PnyxProperties properties;
    // use: properties.getNetwork()
}
```

## Hexagonal Architecture

The project follows the Ports & Adapters pattern with strict dependency rules enforced by ArchUnit (11 rules across core and app modules).

### Module Structure

```
modules/
├── core/                          ← Pure Java. Zero framework deps.
│   └── dev.pnyx.core/
│       ├── api/                   ← Use case interfaces (driving ports)
│       ├── spi/                   ← Driven port interfaces (SPI contracts)
│       └── domain/                ← Aggregates, events, value objects, state machines (includes execution/)
│
├── app/                           ← Spring Boot runtime
│   └── dev.pnyx/
│       ├── endpoint/              ← REST controllers, Thymeleaf HTML, Picocli CLI
│       ├── infrastructure/        ← PostgreSQL + jOOQ, file store, Flyway
│       └── service/               ← Use case implementations
│
├── skill-openai/                  ← Spring AI skill executor adapter
└── validation-openai/             ← Spring AI validation executor adapter
```

Plugin modules (`skill-openai`, `validation-openai`) follow the same hexagonal pattern: they depend on `:core` and implement an SPI, never directly called from endpoints.

### Dependency Rules (Enforced)

```
core/api    → core/domain     (allowed)
core/spi    → core/domain     (allowed)
app/service → core/api        (allowed — implements use cases)
app/infra   → core/spi        (allowed — implements SPI contracts)
app/endpoint → core/api       (allowed — calls use cases)

core → app         (forbidden — core knows nothing of app)
core → endpoint    (forbidden)
core → infra       (forbidden)
infra → endpoint   (forbidden)
endpoint → infra   (forbidden)
endpoint → spi     (forbidden — no SPI bypass, use api)
domain → api       (forbidden — domain purity)
domain → spi       (forbidden — domain purity)
domain → framework (forbidden — no jakarta/javax/spring in domain)
```

**Before touching any file**, verify its package location against these rules. A `@Component` annotation belongs in infrastructure, never in core.

### Patterns

✅ **Correct — Controller uses API interface:**
```java
@RestController
@RequiredArgsConstructor
public class ProposalController {
    private final ProposalApi proposalApi; // Core interface, not service impl
}
```

✅ **Correct — SPI interface in core, adapter in infrastructure:**
```java
// core/spi/EventStoreSpi.java
public interface EventStoreSpi {
    void append(UUID streamId, List<?> events);
    List<StoredEvent> readStream(UUID streamId);
}
```

✅ **Correct — Domain model is pure:**
```java
// core/domain/proposal/Proposal.java — no Spring, no jakarta, no javax imports
public class Proposal {
    private final UUID proposalId;
    private final ProposalState state;
    // zero framework dependencies
}
```

❌ **Wrong — domain depending on framework:**
```java
// NEVER do this in core/domain/
import org.springframework.stereotype.Component;  // forbidden
import jakarta.persistence.Entity;                // forbidden
```

## Event Sourcing

Pnyx uses an append-only event store. Every state change produces a domain event.

### Event Store Schema

```sql
event_stream(stream_id, stream_version, event_type, event_payload JSONB,
             content_hash, previous_hash, actor_id, occurred_at)
```

- `stream_id` = aggregate UUID (e.g., proposal ID)
- `stream_version` = monotonically increasing per stream
- `content_hash` = SHA-256 of canonical JSON payload (format: `sha256:<hex>`)
- `previous_hash` = links to prior event in stream (null for version 1)
- `event_payload` = key-sorted, whitespace-stripped canonical JSON

### Command → Event Flow

```java
// Service submits a command, aggregate produces events, event store persists
Proposal draft = Proposal.create(title, problem, action);
Proposal submitted = draft.submit();     // aggregate produces ProposalSubmitted event
eventStore.append(submitted.proposalId(), submitted.uncommittedEvents());
publicStore.write("proposal", "proposal.v1", actorId, canonicalJson);
```

### Migrations

Database schema changes use Flyway. Migration files live in `modules/app/src/main/resources/db/migration/`. Naming: `V{yyyyMMddHHmmss}__{description}.sql` (timestamp-based to prevent agent branch collisions). Migrations are append-only — never modify an existing migration, always create a new one.

## Public Storage Model

Public artifacts are content-addressed JSON files on the filesystem. The database is a derived read model.

```
data/public/
├── objects/{type}/{sha256-hash}.json
├── manifests/latest.json
└── indexes/objects.jsonl
```

Two persistence SPI contracts:
- `EventStoreSpi` — canonical event history (PostgreSQL)
- `PublicStoreSpi` — content-addressed civic objects (filesystem)

Both must remain consistent. A proposal submission appends to the event store AND writes a public object.

## AI Validation

Proposal validation screens submissions for completeness and discussability before deliberation. A `ValidationExecutorSpi` in core decouples validation from AI:

```java
// core/spi/ValidationExecutorSpi.java
public interface ValidationExecutorSpi {
    ValidationResult validate(UUID proposalId, String title, String problem, String action);
}
```

Two adapters implement this:
- `AiValidationExecutorAdapter` in `:validation-openai` — uses ChatClient.Builder + Jackson deserialization
- `StaticValidationExecutor` in `:app` — rule-based fallback (field length checks, spam detection)

`ValidationService` in `:app` orchestrates: try the AI adapter, fall back to static on failure.

Prompts are loaded via `ValidationPromptProviderSpi` from YAML files in `data/prompts/validation.yml`, hot-reloaded on access, and fail at startup if missing/blank.

## AI Skill Panel

Five review roles map to system prompts in `SkillPrompts.java`:
- `legal-reviewer`, `economic-reviewer`, `social-reviewer`, `technical-reviewer`, `risk-reviewer`

A mock adapter (`MockSkillExecutorAdapter`) runs when no OpenAI API key is configured. Spring AI `ChatClient.Builder` is the abstraction.

## Execution Tracking

Post-decision implementation forks are event-sourced. Task transitions (PLANNED → IN_PROGRESS → BLOCKED → COMPLETED) produce domain events:

```
modules/core/src/main/java/com/pnyx/core/domain/execution/
├── ExecutionTaskEvent.java    ← sealed interface
├── TaskAdded.java             ← event: task created
└── TaskTransitioned.java      ← event: status changed
```

`ExecutionService` appends events to the event store for each task creation and status transition, then re-publishes the fork as a public artifact. Invalid transitions (e.g., COMPLETED → IN_PROGRESS) are rejected.

## Demo Corpus

Deterministic sample public artifacts live in `data/public/` — a proposal, decision, and implementation fork demonstrating the full Civic Loop. Files are content-addressed:

```
data/public/
├── objects/proposal/<sha256>.json
├── objects/decision/<sha256>.json
├── objects/implementation_fork/<sha256>.json
├── manifests/latest.json
└── indexes/objects.jsonl
```

Verify the corpus: `./gradlew :app:bootRun --args="verify --path ./data/public"`

## Schema Tests

`SchemaValidationTest` in `:app` validates public artifact schemas against the `PublicSchemaVersion` and `PublicObjectType` enums. Tests cover required fields, enum value enforcement, canonical JSON round-trips, and schema-type alignment.

## Database

- **Development**: PostgreSQL 16 via Docker Compose (`docker compose up -d postgres`)
- **Testing**: Testcontainers with `postgres:16-alpine` image (H2 is NOT used)
- **Migrations**: Flyway auto-runs on app startup
- **Access**: jOOQ for event store (precise SQL), `org.jooq.DSLContext` injected via Spring
- **Read models**: Spring Data JDBC (not JPA — no entities, no `@Entity` annotations)

## Git Workflow

- Work in short-lived feature branches
- Run `./gradlew build` before committing — this runs all tests including ArchUnit
- Keep commits focused and atomic
- Never commit `.env`, credentials, or API keys

File structure artifacts supporting agent workflows (`.superpowers/`, `.worktrees/`, `.opencode/`) are gitignored.

## Completion Contract

When completing agent tasks, report with this structured format to ensure reviewability:

```markdown
## Summary
[1-2 sentence description of what was done]

## Files
- `Modified: path/to/file.java` — [brief change description]
- `Created: path/to/file.java` — [purpose]
- `Deleted: path/to/file.java` — [reason]

## Tests
- `[TestClass.methodName]` — PASS/FAIL
- `[TestClass.methodName]` — PASS/FAIL

## Layers Touched
- [ ] core/domain — [changes]
- [ ] core/api — [changes]
- [ ] core/spi — [changes]
- [ ] app/service — [changes]
- [ ] app/endpoint — [changes]
- [ ] app/infrastructure — [changes]
- [ ] plugin — [which plugin and changes]

## SPIs Modified
- [SPI name]: [what changed]

## Assumptions
- [Any assumptions made during implementation]

## Breaking Changes
- [None, or list breaking changes with migration notes]
```
