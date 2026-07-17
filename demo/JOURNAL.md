# Pnyx — Development Journal

> **Rule:** Entries are append-only. Never edit or delete past entries. If something changes, add a new entry.

## 2026-07-15

### Adopted agentic-java-architecture patterns

**Decision:** Implement 8 adoption items identified in gap analysis between Pnyx and
agentic-java-architecture reference:
1. Result<V,E> pattern for business failures
2. Typed IDs (ProposalId, DecisionId, etc.)
3. STATE.md + JOURNAL.md + verifyContextFreshness
4. Expanded ArchUnit rules
5. Javadoc enforcement in build
6. Completion contract in AGENTS.md
7. Timestamp-based Flyway migrations
8. Fixture-based integration tests with drift guard

**Rationale:** These patterns improve agent safety, code maintainability, and
development workflow consistency.

**Trade-offs considered:**
- Typed IDs require touching many files but prevent UUID confusion at compile time
- Checkstyle enforcement adds build time but ensures agent-facing documentation quality

### Added docs/ references to all Javadocs

**Decision:** Update all 82 production Java classes with `{@code docs/...}` and `@see docs/...`
references in class-level Javadocs, following the convention in AGENTS.md.

**Scope:** Every public class, interface, and enum across core/api, core/spi, core/domain,
app/service, app/endpoint, app/infrastructure, and plugin modules now references the
relevant protocol specification document.

**Rationale:** Agents working on the codebase need to understand which protocol rules
apply to each component. Javadoc references to `docs/` provide that traceability without
requiring agents to search through 56 markdown documents.