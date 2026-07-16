# Pnyx — Java Prototype

Spring Boot hexagonal implementation of the [Pnyx Civic Loop](../docs/README.md): proposals → AI validation → skill-panel reviews → evidence packets → public decisions → implementation tracking → audit trail.

## Quick Start

```bash
docker compose up -d postgres
./gradlew :app:bootRun
```

Open `http://localhost:8080`. Submit a proposal, watch the 5-role AI panel deliberate, receive a decision.
CLI: `./gradlew :app:bootRun --args="verify --path ./data/public"`

## Architecture Decisions

Each decision below is recorded with the trade-off it accepts and the alternative it rejects.

### 1. Hexagonal Architecture (Ports & Adapters)

**Decision:** Strict separation of core domain (pure Java, zero framework) from application runtime (Spring Boot).

**Why:**
- Domain logic lives in `modules/core/` with no Spring, Jakarta, or JPA imports. Aggregates, events, and state machines are testable without Spring.
- Application wiring lives in `modules/app/` — Spring Boot, jOOQ, Thymeleaf, Picocli.
- Plugin modules (`skill-openai`, `validation-openai`) depend only on `core` and implement SPI contracts.

**Enforced by:** 11 ArchUnit rules. `domain → framework` is a compile-time barrier, not a convention.

**Trade-off:** More files, more indirection. Rejected: monolithic Spring Boot where services import JPA entities directly — testable only with full context, no compile-time boundary between business rules and infrastructure.

### 2. Event Sourcing Over CRUD

**Decision:** All state changes produce append-only domain events in a hash-chained PostgreSQL stream. The database is a disposable read model.

**Why:**
- Full audit trail by construction. Every state transition is recorded, timestamped, and hash-linked.
- SHA-256 chain (`previous_hash` → `content_hash`) makes tampering detectable without trusting the database.
- Replayability: read models can be rebuilt from the event stream.

**Trade-off:** Writes are more complex than `UPDATE table SET state = 'APPROVED'`. Querying current state requires replay. Rejected: traditional CRUD where audit is bolted on (soft deletes, `updated_by` columns) and history is incomplete by default.

### 3. Content-Addressed Public Artifacts

**Decision:** Public objects are key-sorted, whitespace-stripped canonical JSON files named by their SHA-256 hash. The database mirrors them but files are the source of truth.

**Why:**
- Verifiable independently of the application. Anyone can `sha256sum` a file and verify it matches the manifest.
- Immutable by design. A changed file has a different hash, creating a new artifact rather than overwriting.
- Git-friendly: content-addressable files diff cleanly.

**Trade-off:** Cannot "update" a public artifact in place — must publish a new one. Index maintenance is manual. Rejected: database-as-truth with filesystem as export — loses independent verifiability.

### 4. AI Validation Before Deliberation

**Decision:** Proposals are screened by an LLM for completeness and discussability before triggering the 5-role skill panel. Static validation serves as fallback.

**Why:**
- Deliberation is expensive (5 parallel LLM calls). Filtering incomplete/spam proposals first prevents wasted computation.
- LLM validation catches nuanced issues static rules miss (e.g., internally contradictory proposals, vague action plans).
- Static fallback ensures the system works without an API key on the cold-start path.

**Trade-off:** Adds latency to proposal submission (one extra LLM round-trip). Validation may reject borderline proposals that a human moderator would accept. Rejected: no validation (wastes deliberation on spam) or static-only (misses semantic issues).

### 5. Agentic Deliberation With Tool Calling

**Decision:** Each of 5 skill roles (legal, economic, social, technical, risk) executes as an agentic loop: the LLM can invoke role-specific tools (e.g., `lookup_legislation`, `estimate_cost`) before producing a structured review.

**Why:**
- Reviews grounded in simulated data lookups produce more specific, actionable output than pure-prompt responses.
- Role separation prevents false consensus — each role is independently prompted, tools are role-specific.
- Async execution (virtual threads, `@Async`) with HTMX polling progress UI keeps the UX responsive during 5 parallel LLM calls.

**Trade-off:** 5× cost and latency vs. a single "summarize this proposal" call. Tool simulation requires careful prompt engineering. Rejected: single-prompt review (produces bland, consensus-biased output) or sync execution (blocks the HTTP thread).

### 6. SPI-Driven Plugin Extraction

**Decision:** Adapter implementations that depend on Spring AI live in separate Gradle modules (`:skill-openai`, `:validation-openai`). Core defines SPIs; plugins implement them.

**Why:**
- `:app` has no direct dependency on `spring-ai-openai`. Swapping OpenAI for another provider means swapping one plugin JAR.
- Each plugin can be tested in isolation against the SPI contract.
- New execution backends (Anthropic, local models) are new modules — no changes to core or app.

**Trade-off:** More Gradle modules, more boilerplate. Rejected: all adapters in `:app` — couples the application to specific AI providers, hard to test in isolation.

### 7. `@ConfigurationProperties` Over `@Value`

**Decision:** All `pnyx.*` configuration lives in a typed `PnyxProperties` class annotated with `@ConfigurationProperties`. Services inject the properties object, never `@Value`.

**Why:**
- Single source of truth for all config keys. Rename a property and the compiler catches every consumer.
- IDE autocompletion and navigation work on typed getters.
- `@ConfigurationPropertiesScan` auto-discovers the class.

**Trade-off:** One extra class per config namespace. Rejected: scattered `@Value("${pnyx.something}")` annotations — no compile-time safety, no single list of all config keys, easy to typo.

### 8. Jackson Serialization Over String Concatenation

**Decision:** All JSON (public objects, event payloads, tool queries) is produced by Jackson `ObjectMapper.writeValueAsString()` with typed records, never string concatenation.

**Why:**
- Proper escaping of quotes, backslashes, newlines, and Unicode in user-supplied text.
- `ORDER_MAP_ENTRIES_BY_KEYS` produces deterministic canonical output.
- Type-safe: adding a field to a record doesn't silently break serialization.

**Trade-off:** Slightly more verbose than template strings. Rejected: hand-built JSON with `"key\":\"" + value + "\""` — breaks on any user input containing double quotes, fragile to maintain.

### 9. Gradle Multi-Module With Checked-In jOOQ Sources

**Decision:** jOOQ-generated table classes are committed to `src/generated/java/` rather than gitignored.

**Why:**
- CI and new developers don't need a running PostgreSQL to compile. `./gradlew compileJava` works immediately.
- Generated sources are reviewable in PRs — schema changes produce visible diffs in table references.
- Regeneration is deliberate: `docker compose up -d postgres && ./gradlew :app:jooqCodegen`.

**Trade-off:** Generated code in version control. Rejected: gitignoring generated sources — requires a running database for every clean checkout.

### 10. Java 25 With Virtual Threads

**Decision:** Java 25 class major version 69, running on virtual threads for async deliberation.

**Why:**
- Virtual threads make 5 parallel LLM calls lightweight — no thread pool sizing, no `CompletableFuture` chains.
- Java 25 is the current LTS-candidate, aligned with the project's "build for the future" posture.
- Gradle 8.14+ is required for class version 69 bytecode.

**Trade-off:** Requires Gradle 9.5 wrapper, slightly ahead of some CI environments. Rejected: Java 21 LTS (would work but misses virtual thread maturity improvements).

### 11. Testcontainers Over H2

**Decision:** Integration tests use `postgres:16-alpine` via Testcontainers. H2 is not used.

**Why:**
- Tests run against the same database engine as production. PostgreSQL-specific features (JSONB, Flyway migrations) work identically.
- No "works in H2, breaks in Postgres" surprises.

**Trade-off:** Tests are slower to start (container spin-up). Rejected: H2 for tests — JSONB queries, Flyway syntax, and jOOQ dialect behavior differ from PostgreSQL.

## Agentic Development Model

This codebase is developed entirely by AI agents with human review. Every architectural decision above serves a second purpose: making the codebase tractable for a stateless agent with no memory of past sessions and no ability to ask clarifying questions mid-task.

### How Each Decision Serves Agentic Development

**Hexagonal architecture** → Agents can modify a service without understanding Spring wiring. The SPI contract is the entire interface between agent and infrastructure. Agent told "add a `NotificationSpi` and implement it" needs to read only the SPI interface — it doesn't need to understand jOOQ, Flyway, or Thymeleaf.

**Event sourcing + typed events** → Every state change has exactly one Java record. An agent adding a new state transition creates a new event record, appends it, and the hash chain is self-verifying. No agent needs to understand the full lifecycle to add one transition correctly — the compiler rejects mismatched types.

**Content-addressed files** → An agent creating demo data or test fixtures can verify correctness without running the application. `sha256sum` on a file either matches or doesn't. No need to understand Spring context, database state, or API behavior to produce valid artifacts.

**ArchUnit rules checked at build time** → An agent placing a `@Component` in `core/domain/` gets a build failure with a clear message. The rules serve as automated code review — the agent learns the boundary from the error, not from a human catching it post-hoc.

**SPI-driven plugins** → Agents extracting an adapter to a new module follow a mechanical pattern. The `:skill-openai` module is a template — every subsequent plugin copies its `build.gradle.kts` structure, package layout, and SPI implementation pattern. The agent doesn't need to invent module structure.

**`@ConfigurationProperties` over `@Value`** → An agent adding a new config key edits exactly one file (`PnyxProperties.java`). There is no grep-then-pray cycle across 12 files to find all `@Value("${pnyx...}")` annotations. One class, one location, compiler-guaranteed completeness.

**Jackson over string concatenation** → An agent instructed to "serialize this record" writes `objectMapper.writeValueAsString(obj)`. It doesn't need to understand JSON escaping rules, key ordering, or edge cases in user-supplied text. The library handles correctness; the agent handles structure.

**Checked-in jOOQ sources** → An agent can read `EventStream.java` to discover available columns, their types, and generated constants (`E.STREAM_ID`, `E.CONTENT_HASH`). No need to connect to a database, run code generation, or reverse-engineer the schema from migration files.

**jOOQ DSL over string SQL** → An agent writes `dsl.selectFrom(EVENT_STREAM).where(EVENT_STREAM.STREAM_ID.eq(streamId))`. The compiler catches column name typos, type mismatches, and missing `orderBy` clauses. String SQL would produce runtime errors the agent can't see until tests run.

**Virtual threads + `@Async`** → An agent adding parallel execution writes `@Async` on a method and returns `void`. It doesn't need to manage thread pools, handle `Future` composition, or reason about executor lifecycle. The framework absorbs concurrency complexity.

**Testcontainers over H2** → An agent writing an integration test uses the same SQL dialect the migration files use. No mental model of "this works in H2 but not Postgres." One database, one SQL dialect, zero translation bugs.

**`AGENTS.md` as living convention** → Every new pattern, discovered footgun, or fixed bug gets documented in `AGENTS.md`. The next agent session inherits accumulated knowledge. The file evolves with the codebase — it's the agent's institutional memory.

**Typed records over `Map<String, Object>`** → An agent serializing a public artifact instantiates `CanonicalProposalObject(type, schema, ...)`. The IDE and compiler enforce field names and types. With raw maps, typos like `createdAt` vs `created_at` produce silent schema violations.

### Anti-Patterns That Break Agentic Development

These have been avoided intentionally:

| Anti-pattern | Why it fails for agents |
|---|---|
| Implicit conventions not in `AGENTS.md` | Agent has no tribal knowledge. If it's not written, it doesn't exist. |
| `@Value` annotation scattered across codebase | Agent can't find all config consumers without grep. Renaming a property silently breaks things. |
| Manually constructed JSON strings | Agent doesn't escape special characters. Every user input is a potential bug. |
| H2 for tests, PostgreSQL for prod | Agent must maintain two mental models of SQL. Inevitable drift. |
| Generated sources in `.gitignore` | Agent can't read schema without running codegen. Compilation fails on clean checkout. |
| Anemic domain model (getters/setters, service does everything) | Agent adds logic to the wrong layer. No compiler feedback on misplaced responsibility. |
| Long methods with mixed concerns | Agent modifying one concern accidentally breaks another. Method-level isolation is the smallest safe edit unit. |
| No ArchUnit tests | Agent places `@Service` in `core/`. No build failure. Human catches it days later in PR review. |

## Module Map

```
modules/
├── core/                  Pure Java. Zero Spring. api/, spi/, domain/
├── app/                   Spring Boot. endpoint/, infrastructure/, service/
├── skill-openai/          Spring AI skill executor (plugin)
└── validation-openai/     Spring AI validation executor (plugin)
```

### Dependency Flow

```
endpoint → core/api ← service        (services implement use cases)
service  → core/spi ← infrastructure  (adapters implement SPI contracts)
infra    → core/spi                   (adapters depend on contracts)
endpoint → infra                      (forbidden — ArchUnit enforced)
domain   → framework                  (forbidden — ArchUnit enforced)
```

## Data Layout

```
data/
├── prompts/
│   ├── skill-panel.yml      Role-specific system prompts
│   └── validation.yml       Proposal validation prompt
└── public/
    ├── objects/{type}/{sha256}.json    Content-addressed artifacts
    ├── manifests/latest.json           Pointer to latest object
    └── indexes/objects.jsonl            Object index for verification
```

## Testing

```bash
./gradlew test                    # all modules
./gradlew :core:test              # domain + ArchUnit
./gradlew :app:test               # integration + schema + ArchUnit
./gradlew :skill-openai:test      # skill plugin
./gradlew :validation-openai:test # validation plugin
```

45 tests across 4 modules: domain unit tests, ArchUnit architecture enforcement, event store integration, public store integration, schema validation, end-to-end REST, prompt validation, and service-level tests.

## Documentation

- [Pnyx Specification](../docs/README.md) — Full governance system spec (11 layers, 62 documents)
- [AGENTS.md](AGENTS.md) — Coding conventions for AI agents working in this repo
- [Design Spec](../docs/superpowers/specs/2026-05-26-pnyx-java-design.md) — Architecture approval doc
- [Implementation Plan](../docs/superpowers/plans/2026-05-26-pnyx-java-plan.md) — 18-task implementation history

## License

Apache 2.0 — public governance infrastructure should not be enclosable.
