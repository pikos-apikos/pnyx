# Reference Implementation Requirements

*Technical specification for building a Minimum Viable Pnyx (MVP) reference implementation*

**Version:** 1.0  
**Date:** April 2026  
**Scope:** MVP as defined in MINIMUM_VIABLE_PNYX.md  
**Target:** 10-person team, $100k budget, 6-month timeline  

---

## Table of Contents

1. [Executive Summary](#executive-summary)
2. [System Overview](#system-overview)
3. [Core Runtime Architecture](#core-runtime-architecture)
4. [Data Layer](#data-layer)
5. [API Layer](#api-layer)
6. [Skill Integration Layer](#skill-integration-layer)
7. [Audit System](#audit-system)
8. [Cryptographic Infrastructure](#cryptographic-infrastructure)
9. [Classification Engine](#classification-engine)
10. [Panel Orchestration](#panel-orchestration)
11. [Packet Generation](#packet-generation)
12. [Treasury Module](#treasury-module)
13. [Emergency Subsystem](#emergency-subsystem)
14. [Deployment Architecture](#deployment-architecture)
15. [Testing Strategy](#testing-strategy)
16. [Implementation Roadmap](#implementation-roadmap)
17. [Open Questions](#open-questions)

---

## Executive Summary

This document specifies the technical requirements for building a **Minimum Viable Pnyx (MVP)** reference implementation. The MVP follows the constraints defined in `MINIMUM_VIABLE_PNYX.md`:

- Advisory-only governance (no binding decisions initially)
- 5-skill minimum panels
- 4 audit views (not 12)
- Explicit bootstrap debt tracking
- Narrow scope (local/bounded issues)
- Append-only audit with basic projections
- Conservative classification (ambiguity escalates upward)

### Target Characteristics

| Aspect | Specification |
|--------|---------------|
| **Team Size** | 10 people (2 protocol, 2 backend, 2 frontend, 2 ops/infra, 1 PM, 1 design/research) |
| **Budget** | $100,000 USD |
| **Timeline** | 6 months to MVP pilot |
| **Scale Target** | 100 active proposals, 1,000 users, 10,000 votes |
| **Tech Stack** | TypeScript/Node.js (backend), React (frontend), PostgreSQL (data), Redis (cache), IPFS/Arweave (audit archive) |
| **Infrastructure** | 3-cloud redundancy (AWS + GCP + Azure minimum instances) |

---

## System Overview

### 2.1 High-Level Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        CLIENT LAYER                             │
│  React SPA (public interface) | Voice/SMS (accessibility)      │
└─────────────────────────────────────────────────────────────────┘
                              ↕
┌─────────────────────────────────────────────────────────────────┐
│                         API GATEWAY                               │
│  Rate limiting | Auth | Validation | Routing | Circuit breakers  │
└─────────────────────────────────────────────────────────────────┘
                              ↕
┌─────────────────────────────────────────────────────────────────┐
│                      CORE RUNTIME ENGINE                        │
│  ┌─────────────┐ ┌──────────────┐ ┌──────────────┐             │
│  │ Proposal    │ │ Classification│ │ Panel        │             │
│  │ Intake      │ │ Engine        │ │ Orchestrator │             │
│  └─────────────┘ └──────────────┘ └──────────────┘             │
│  ┌─────────────┐ ┌──────────────┐ ┌──────────────┐             │
│  │ Skill       │ │ Packet        │ │ Decision     │             │
│  │ Integration │ │ Generator     │ │ Recorder     │             │
│  └─────────────┘ └──────────────┘ └──────────────┘             │
│  ┌─────────────┐ ┌──────────────┐ ┌──────────────┐             │
│  │ Treasury    │ │ Emergency   │ │ Audit        │             │
│  │ Manager     │ │ Controller  │ │ Writer       │             │
│  └─────────────┘ └──────────────┘ └──────────────┘             │
└─────────────────────────────────────────────────────────────────┘
                              ↕
┌─────────────────────────────────────────────────────────────────┐
│                        DATA LAYER                               │
│  PostgreSQL (primary) | Redis (cache/queues) | IPFS (audit)    │
└─────────────────────────────────────────────────────────────────┘
                              ↕
┌─────────────────────────────────────────────────────────────────┐
│                      EXTERNAL SERVICES                          │
│  AI Providers (OpenAI/Anthropic/Google) | Identity Verifiers    │
│  Payment Rails | Notification Services | Backup Storage          │
└─────────────────────────────────────────────────────────────────┘
```

### 2.2 Core Principles

1. **Audit-First Design**: Every state change must be append-only and cryptographically signed
2. **Fail-Safe Defaults**: Ambiguity escalates, emergency expires, classification biases conservative
3. **Bootstrap Honesty**: All shortcuts logged as debt, no claims of constitutional maturity
4. **Skill Plurality**: No single AI provider, minimum 5 skills for non-trivial proposals
5. **Public Legibility**: 4 audit views render complex data into citizen-readable form

---

## Core Runtime Architecture

### 3.1 Module Structure

```typescript
// Core runtime types
type SystemState = {
  epoch: FrameworkEpoch;
  proposalCount: number;
  activePanels: Panel[];
  emergencyStatus: EmergencyState | null;
  bootstrapDebt: BootstrapDebtEntry[];
  treasuryPartitions: TreasuryPartition[];
};

type RuntimeContext = {
  operator: OperatorIdentity;
  dualControl?: OperatorIdentity;  // Required for governance-affecting changes
  timestamp: ISO8601;
  epoch: FrameworkEpoch;
  auditChain: HashChain;
};
```

### 3.2 Event-Driven Architecture

The system uses an event-sourced architecture where:
- All state changes are events
- Events are immutable and append-only
- Projections (read models) are derived from event stream
- Event stream is the source of truth

```typescript
interface DomainEvent {
  eventId: string;
  eventType: string;
  aggregateId: string;
  aggregateType: string;
  version: number;
  timestamp: ISO8601;
  operatorId: string;
  payload: unknown;
  signature: CryptographicSignature;
}

// Example events
interface ProposalSubmitted extends DomainEvent {
  eventType: 'ProposalSubmitted';
  aggregateType: 'Proposal';
  payload: {
    proposalId: string;
    title: string;
    body: string;
    proposerId: string;
    submittedAt: ISO8601;
  };
}

interface ProposalClassified extends DomainEvent {
  eventType: 'ProposalClassified';
  aggregateType: 'Proposal';
  payload: {
    proposalId: string;
    classification: ClassificationResult;
    classifiedBy: string;
    counterClassification?: ClassificationResult;
    confidence: 'low' | 'medium' | 'high';
  };
}

interface PanelLocked extends DomainEvent {
  eventType: 'PanelLocked';
  aggregateType: 'Panel';
  payload: {
    panelId: string;
    proposalId: string;
    skills: SkillAssignment[];
    lockedAt: ISO8601;
    lockedBy: string;
  };
}

interface SkillOutputRecorded extends DomainEvent {
  eventType: 'SkillOutputRecorded';
  aggregateType: 'Panel';
  payload: {
    panelId: string;
    skillId: string;
    output: SkillOutput;
    recordedAt: ISO8601;
  };
}

interface EmergencyDeclared extends DomainEvent {
  eventType: 'EmergencyDeclared';
  aggregateType: 'Emergency';
  payload: {
    emergencyId: string;
    emergencyClass: EmergencyClass;
    scope: string;
    expiresAt: ISO8601;
    declaredBy: string;
    coAuthorizedBy: string;
  };
}
```

### 3.3 State Machine Implementation

```typescript
// Proposal state machine
enum ProposalState {
  SUBMITTED = 'submitted',
  UNDER_CLASSIFICATION = 'under_classification',
  CLASSIFIED = 'classified',
  CHALLENGED = 'challenged',
  PANEL_ASSEMBLY = 'panel_assembly',
  PANEL_LOCKED = 'panel_locked',
  UNDER_REVIEW = 'under_review',
  BRIEFING_PUBLISHED = 'briefing_published',
  UNDER_DECISION = 'under_decision',
  DECIDED = 'decided',
  EXECUTED = 'executed',
  INVALIDATED = 'invalidated'
}

// Emergency state machine
enum EmergencyState {
  DECLARED = 'declared',
  AUTHORIZED = 'authorized',
  ACTIVE = 'active',
  EXPIRED = 'expired',
  CLOSED = 'closed',
  UNDER_REVIEW = 'under_review',
  ABUSE_FLAGGED = 'abuse_flagged'
}

// State transitions with guards
const proposalTransitions: Record<ProposalState, { 
  to: ProposalState; 
  guard: (event: DomainEvent, state: ProposalAggregate) => boolean;
}[]> = {
  [ProposalState.SUBMITTED]: [
    { to: ProposalState.UNDER_CLASSIFICATION, guard: () => true }
  ],
  [ProposalState.UNDER_CLASSIFICATION]: [
    { to: ProposalState.CLASSIFIED, guard: (e) => e.eventType === 'ProposalClassified' },
    { to: ProposalState.CHALLENGED, guard: (e) => e.eventType === 'ClassificationChallenged' }
  ],
  // ... etc
};
```

---

## Data Layer

### 4.1 Database Schema (PostgreSQL)

```sql
-- Core aggregates
CREATE TABLE proposals (
    id UUID PRIMARY KEY,
    version INTEGER NOT NULL,
    state VARCHAR(50) NOT NULL,
    title TEXT NOT NULL,
    body TEXT NOT NULL,
    proposer_id UUID NOT NULL,
    submitted_at TIMESTAMP WITH TIME ZONE NOT NULL,
    classification_result JSONB,
    classification_confidence VARCHAR(10),
    panel_id UUID,
    briefing_packet JSONB,
    decision_result JSONB,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE TABLE panels (
    id UUID PRIMARY KEY,
    proposal_id UUID NOT NULL REFERENCES proposals(id),
    version INTEGER NOT NULL,
    state VARCHAR(50) NOT NULL,
    target_size INTEGER NOT NULL,
    required_classes JSONB NOT NULL,
    selected_skills JSONB NOT NULL,
    locked_at TIMESTAMP WITH TIME ZONE,
    locked_by UUID,
    completion_status VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE TABLE skill_outputs (
    id UUID PRIMARY KEY,
    panel_id UUID NOT NULL REFERENCES panels(id),
    skill_id VARCHAR(100) NOT NULL,
    skill_version VARCHAR(50) NOT NULL,
    output JSONB NOT NULL,
    dissent_notes JSONB,
    unknowns JSONB,
    confidence VARCHAR(10),
    recorded_at TIMESTAMP WITH TIME ZONE NOT NULL,
    recorded_by UUID NOT NULL
);

-- Event store (append-only)
CREATE TABLE events (
    id UUID PRIMARY KEY,
    event_type VARCHAR(100) NOT NULL,
    aggregate_id UUID NOT NULL,
    aggregate_type VARCHAR(100) NOT NULL,
    version INTEGER NOT NULL,
    timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    operator_id UUID NOT NULL,
    payload JSONB NOT NULL,
    signature BYTEA NOT NULL,
    previous_hash BYTEA NOT NULL,
    UNIQUE(aggregate_id, version)
);

CREATE INDEX idx_events_aggregate ON events(aggregate_id, version);
CREATE INDEX idx_events_timestamp ON events(timestamp);
CREATE INDEX idx_events_type ON events(event_type);

-- Audit views projections
CREATE TABLE audit_projections (
    id UUID PRIMARY KEY,
    view_type VARCHAR(50) NOT NULL,
    projection_version VARCHAR(50) NOT NULL,
    source_events UUID[] NOT NULL,
    rendered_content JSONB NOT NULL,
    redaction_status JSONB,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Treasury
CREATE TABLE treasury_partitions (
    id UUID PRIMARY KEY,
    partition_type VARCHAR(50) NOT NULL UNIQUE,
    balance DECIMAL(20, 2) NOT NULL DEFAULT 0,
    reserve_minimum DECIMAL(20, 2),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE TABLE treasury_transactions (
    id UUID PRIMARY KEY,
    partition_id UUID NOT NULL REFERENCES treasury_partitions(id),
    transaction_type VARCHAR(50) NOT NULL,
    amount DECIMAL(20, 2) NOT NULL,
    source_or_destination TEXT,
    description TEXT,
    authorization_chain JSONB NOT NULL,
    transaction_hash BYTEA NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Bootstrap debt register
CREATE TABLE bootstrap_debt (
    id UUID PRIMARY KEY,
    debt_type VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    rationale TEXT NOT NULL,
    authorized_by UUID NOT NULL,
    expires_at TIMESTAMP WITH TIME ZONE,
    review_path TEXT,
    status VARCHAR(50) DEFAULT 'active',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Emergency registry
CREATE TABLE emergencies (
    id UUID PRIMARY KEY,
    emergency_class VARCHAR(100) NOT NULL,
    state VARCHAR(50) NOT NULL,
    scope TEXT NOT NULL,
    declared_by UUID NOT NULL,
    co_authorized_by UUID NOT NULL,
    declared_at TIMESTAMP WITH TIME ZONE NOT NULL,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    review_case_id UUID,
    incident_packet JSONB,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Skills registry
CREATE TABLE skills (
    id VARCHAR(100) PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    skill_class VARCHAR(100) NOT NULL,
    provider_id VARCHAR(100) NOT NULL,
    current_version VARCHAR(50) NOT NULL,
    tier VARCHAR(20) NOT NULL,
    status VARCHAR(50) NOT NULL,
    model_dependence VARCHAR(50),
    evaluation_status JSONB,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE TABLE skill_versions (
    id UUID PRIMARY KEY,
    skill_id VARCHAR(100) NOT NULL REFERENCES skills(id),
    version VARCHAR(50) NOT NULL,
    contract_hash VARCHAR(64) NOT NULL,
    prompt_contract JSONB,
    tooling_contract JSONB,
    evaluation_suite_id UUID,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);
```

### 4.2 Event Store Implementation

```typescript
class EventStore {
  constructor(
    private db: PostgreSQLClient,
    private crypto: CryptographicService
  ) {}

  async append(event: DomainEvent): Promise<void> {
    // Get previous event hash for chain
    const previousEvent = await this.db.query(
      'SELECT signature FROM events WHERE aggregate_id = $1 ORDER BY version DESC LIMIT 1',
      [event.aggregateId]
    );
    
    const previousHash = previousEvent.rows[0]?.signature || Buffer.from('genesis');
    
    // Sign event
    const payload = JSON.stringify(event.payload);
    const signature = await this.crypto.sign(payload, previousHash);
    
    // Insert with chain hash
    await this.db.query(
      `INSERT INTO events (id, event_type, aggregate_id, aggregate_type, version, 
                          timestamp, operator_id, payload, signature, previous_hash)
       VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10)`,
      [
        event.eventId,
        event.eventType,
        event.aggregateId,
        event.aggregateType,
        event.version,
        event.timestamp,
        event.operatorId,
        event.payload,
        signature,
        previousHash
      ]
    );
    
    // Publish to event bus for projections
    await this.eventBus.publish(event);
  }

  async getEventsForAggregate(aggregateId: string): Promise<DomainEvent[]> {
    const result = await this.db.query(
      'SELECT * FROM events WHERE aggregate_id = $1 ORDER BY version ASC',
      [aggregateId]
    );
    return result.rows.map(this.deserializeEvent);
  }

  async verifyChain(): Promise<boolean> {
    // Verify cryptographic chain integrity
    const events = await this.db.query('SELECT * FROM events ORDER BY timestamp ASC');
    
    for (let i = 1; i < events.rows.length; i++) {
      const current = events.rows[i];
      const previous = events.rows[i - 1];
      
      if (!current.previous_hash.equals(previous.signature)) {
        throw new Error(`Chain break at event ${current.id}`);
      }
      
      // Verify signature
      const valid = await this.crypto.verify(
        JSON.stringify(current.payload),
        current.signature,
        current.previous_hash
      );
      
      if (!valid) {
        throw new Error(`Invalid signature at event ${current.id}`);
      }
    }
    
    return true;
  }
}
```

### 4.3 Read Model Projections

```typescript
// Projection service rebuilds read models from event stream
class ProjectionService {
  async rebuildProposalProjection(proposalId: string): Promise<Proposal> {
    const events = await this.eventStore.getEventsForAggregate(proposalId);
    
    let proposal: Proposal = { id: proposalId, state: 'submitted' } as Proposal;
    
    for (const event of events) {
      proposal = this.applyEvent(proposal, event);
    }
    
    return proposal;
  }

  private applyEvent(proposal: Proposal, event: DomainEvent): Proposal {
    switch (event.eventType) {
      case 'ProposalSubmitted':
        return {
          ...proposal,
          title: event.payload.title,
          body: event.payload.body,
          proposerId: event.payload.proposerId,
          submittedAt: event.payload.submittedAt,
          state: 'submitted'
        };
        
      case 'ProposalClassified':
        return {
          ...proposal,
          classification: event.payload.classification,
          classificationConfidence: event.payload.confidence,
          state: 'classified'
        };
        
      case 'PanelLocked':
        return {
          ...proposal,
          panelId: event.payload.panelId,
          state: 'panel_locked'
        };
        
      // ... etc
      default:
        return proposal;
    }
  }
}
```

---

## API Layer

### 5.1 API Design Principles

1. **Read/Write Separation**: Commands mutate state, queries read projections
2. **Idempotency**: All write operations are idempotent with client-generated IDs
3. **Versioning**: API versioned, events versioned, projections versioned
4. **Observability**: All endpoints emit structured logs and metrics

### 5.2 Core Endpoints

```typescript
// Proposal lifecycle
POST   /api/v1/proposals                    // Submit proposal
GET    /api/v1/proposals/:id                // Get proposal with current state
GET    /api/v1/proposals/:id/events         // Get event history
POST   /api/v1/proposals/:id/classify      // Trigger classification
POST   /api/v1/proposals/:id/challenge     // Challenge classification

// Panel management
POST   /api/v1/panels                      // Create panel (internal)
POST   /api/v1/panels/:id/lock            // Lock panel composition
GET    /api/v1/panels/:id/skills           // List assigned skills
POST   /api/v1/panels/:id/skills/:skillId/execute  // Execute skill
POST   /api/v1/panels/:id/skills/:skillId/output    // Record skill output

// Audit views
GET    /api/v1/audit/timeline/:proposalId  // Public timeline view
GET    /api/v1/audit/decision/:proposalId  // Decision record view
GET    /api/v1/audit/operator              // Operator action view
GET    /api/v1/audit/incidents             // Incident/challenge view

// Treasury (read-only for public)
GET    /api/v1/treasury/partitions          // List partitions
GET    /api/v1/treasury/transactions        // List transactions (paginated)
GET    /api/v1/treasury/metrics            // Health metrics

// Emergency (restricted)
POST   /api/v1/emergency/declare           // Declare emergency
POST   /api/v1/emergency/:id/authorize    // Co-authorize
GET    /api/v1/emergency/:id              // Get emergency status
POST   /api/v1/emergency/:id/close         // Close emergency

// Skills registry
GET    /api/v1/skills                     // List skills
GET    /api/v1/skills/:id                 // Get skill details
GET    /api/v1/skills/:id/versions        // Get version history
```

### 5.3 API Implementation Example

```typescript
// Proposal submission handler
class ProposalController {
  async submitProposal(req: Request, res: Response) {
    const { title, body, proposerId } = req.body;
    
    // Generate proposal ID client-side for idempotency
    const proposalId = req.body.proposalId || crypto.randomUUID();
    
    // Validate
    if (!title || !body || !proposerId) {
      return res.status(400).json({ error: 'Missing required fields' });
    }
    
    // Create event
    const event: ProposalSubmitted = {
      eventId: crypto.randomUUID(),
      eventType: 'ProposalSubmitted',
      aggregateId: proposalId,
      aggregateType: 'Proposal',
      version: 1,
      timestamp: new Date().toISOString(),
      operatorId: req.operator.id,
      payload: {
        proposalId,
        title,
        body,
        proposerId,
        submittedAt: new Date().toISOString()
      },
      signature: await this.crypto.sign(/* ... */)
    };
    
    // Append to event store
    await this.eventStore.append(event);
    
    // Trigger async classification
    await this.classificationQueue.add('classify', { proposalId });
    
    return res.status(201).json({ 
      proposalId,
      status: 'submitted',
      classificationPending: true 
    });
  }
}
```

---

## Skill Integration Layer

### 6.1 Skill Interface Contract

```typescript
// All skills must implement this interface
interface Skill {
  id: string;
  name: string;
  skillClass: SkillClass;
  version: string;
  providerId: string;
  
  // Execute skill on proposal
  execute(input: SkillInput): Promise<SkillOutput>;
  
  // Get skill metadata
  getMetadata(): SkillMetadata;
}

interface SkillInput {
  proposal: Proposal;
  context: SkillContext;
  epoch: FrameworkEpoch;
  previousOutputs?: SkillOutput[];  // For critique skills
}

interface SkillOutput {
  skillId: string;
  skillVersion: string;
  executedAt: ISO8601;
  
  // Required fields per PACKET_FORMAT.md
  strongestCaseInFavor?: string;
  strongestCaseAgainst?: string;
  unknowns?: string[];
  evidenceSufficiency?: 'sufficient' | 'insufficient' | 'partial';
  captureRiskNote?: string;
  reversibilityNote?: string;
  
  // Additional class-specific outputs
  rightsAnalysis?: RightsAnalysis;  // For rights_constitutional
  feasibilityAssessment?: FeasibilityAssessment;  // For implementation_feasibility
  resourceAnalysis?: ResourceAnalysis;  // For economic_resource
  antiCaptureReview?: AntiCaptureReview;  // For anti_capture_audit
  adversarialCritique?: AdversarialCritique;  // For adversarial_critique
  
  // Uncertainty and dissent
  confidence: 'low' | 'medium' | 'high';
  dissentNotes?: string;
}
```

### 6.2 Skill Adapter Pattern

```typescript
// Adapter for OpenAI/Anthropic/Google models
abstract class AISkillAdapter implements Skill {
  constructor(
    protected modelClient: AIProviderClient,
    protected promptTemplate: PromptTemplate,
    protected outputParser: OutputParser
  ) {}

  async execute(input: SkillInput): Promise<SkillOutput> {
    // Build prompt with proposal context
    const prompt = this.promptTemplate.render({
      proposal: input.proposal,
      context: input.context,
      requiredOutputs: this.getRequiredOutputs()
    });
    
    // Call AI model
    const rawOutput = await this.modelClient.complete(prompt);
    
    // Parse into structured output
    const parsed = this.outputParser.parse(rawOutput);
    
    // Validate output schema
    this.validateOutput(parsed);
    
    return {
      skillId: this.id,
      skillVersion: this.version,
      executedAt: new Date().toISOString(),
      ...parsed,
      confidence: this.assessConfidence(parsed)
    };
  }
  
  protected abstract getRequiredOutputs(): string[];
  protected abstract validateOutput(output: unknown): void;
  protected abstract assessConfidence(output: unknown): 'low' | 'medium' | 'high';
}

// Example: Rights analysis skill
class RightsConstitutionalSkill extends AISkillAdapter {
  id = 'rights_constitutional_v1';
  name = 'Rights and Constitutional Analysis';
  skillClass = 'rights_constitutional';
  version = '1.0.0';
  providerId = 'openai';

  protected getRequiredOutputs() {
    return ['rightsAnalysis', 'strongestCaseAgainst', 'unknowns'];
  }
  
  protected validateOutput(output: unknown) {
    // Ensure required fields present
    // Ensure no fabricated certainty
    // Ensure dissent preserved
  }
}
```

### 6.3 Skill Registry Integration

```typescript
class SkillRegistry {
  private skills: Map<string, Skill> = new Map();
  
  register(skill: Skill): void {
    this.skills.set(skill.id, skill);
  }
  
  async executeSkill(
    skillId: string, 
    input: SkillInput
  ): Promise<SkillOutput> {
    const skill = this.skills.get(skillId);
    if (!skill) {
      throw new Error(`Skill ${skillId} not found`);
    }
    
    // Check skill status
    const skillRecord = await this.db.getSkill(skillId);
    if (skillRecord.status !== 'active') {
      throw new Error(`Skill ${skillId} is not active`);
    }
    
    // Execute with timeout
    const output = await Promise.race([
      skill.execute(input),
      new Promise((_, reject) => 
        setTimeout(() => reject(new Error('Skill execution timeout')), 30000)
      )
    ]);
    
    return output;
  }
  
  getSkillsByClass(skillClass: SkillClass): Skill[] {
    return Array.from(this.skills.values())
      .filter(s => s.skillClass === skillClass);
  }
}
```

---

## Audit System

### 7.1 Audit View Projections

```typescript
// MVP requires only 4 audit views per MINIMUM_VIABLE_PNYX.md

class AuditViewProjector {
  // 1. Public Timeline View
  async generateTimelineView(proposalId: string): Promise<TimelineView> {
    const events = await this.eventStore.getEventsForAggregate(proposalId);
    
    const timeline = events.map(event => ({
      timestamp: event.timestamp,
      eventType: event.eventType,
      actor: event.operatorId,
      summary: this.summarizeEvent(event),
      linkToRaw: `/api/v1/proposals/${proposalId}/events/${event.eventId}`
    }));
    
    return {
      viewType: 'public_timeline',
      proposalId,
      events: timeline,
      generatedAt: new Date().toISOString()
    };
  }
  
  // 2. Decision Record View
  async generateDecisionRecord(proposalId: string): Promise<DecisionRecord> {
    const proposal = await this.projectionService.getProposal(proposalId);
    const panel = proposal.panelId ? 
      await this.projectionService.getPanel(proposal.panelId) : null;
    
    return {
      viewType: 'decision_record',
      proposal: {
        id: proposal.id,
        title: proposal.title,
        summary: this.summarizeProposal(proposal)
      },
      classification: proposal.classification,
      panel: panel ? {
        size: panel.selectedSkills.length,
        skills: panel.selectedSkills.map(s => ({
          id: s.skillId,
          class: s.skillClass,
          provider: s.providerId
        }))
      } : null,
      briefing: proposal.briefingPacket,
      decision: proposal.decisionResult,
      generatedAt: new Date().toISOString()
    };
  }
  
  // 3. Operator Action View
  async generateOperatorActionView(
    startDate: Date, 
    endDate: Date
  ): Promise<OperatorActionView> {
    const events = await this.eventStore.queryEvents({
      eventTypes: [
        'EmergencyDeclared', 'EmergencyAuthorized', 'GovernanceCodeDeployed',
        'KeyRotated', 'ConfigurationChanged', 'OperatorOverrideAttempt'
      ],
      startDate,
      endDate
    });
    
    return {
      viewType: 'operator_action',
      period: { start: startDate, end: endDate },
      actions: events.map(e => ({
        timestamp: e.timestamp,
        actor: e.operatorId,
        action: e.eventType,
        scope: e.payload.scope || 'system-wide',
        dualControl: e.payload.coAuthorizedBy || null,
        link: `/api/v1/audit/events/${e.eventId}`
      })),
      generatedAt: new Date().toISOString()
    };
  }
  
  // 4. Incident/Challenge View
  async generateIncidentView(
    filters?: { status?: string, type?: string }
  ): Promise<IncidentView> {
    const challenges = await this.db.query(
      `SELECT * FROM events 
       WHERE event_type IN ('ClassificationChallenged', 'PanelChallenged', 
                           'EmergencyAbuseFlagged', 'OperatorActionChallenged')
       ${filters.status ? 'AND payload->>status = $1' : ''}
       ORDER BY timestamp DESC
       LIMIT 100`,
      filters.status ? [filters.status] : []
    );
    
    return {
      viewType: 'incident_challenge',
      filters,
      incidents: challenges.rows.map(c => ({
        id: c.eventId,
        type: c.eventType,
        timestamp: c.timestamp,
        challenger: c.payload.challengerId,
        target: c.payload.targetId,
        grounds: c.payload.grounds,
        status: c.payload.status,
        resolution: c.payload.resolution
      })),
      generatedAt: new Date().toISOString()
    };
  }
}
```

### 7.2 Audit Publication

```typescript
// Audit views published to multiple destinations for redundancy
class AuditPublisher {
  async publishView(view: AuditView): Promise<void> {
    // Store in database
    await this.db.query(
      'INSERT INTO audit_projections (id, view_type, projection_version, source_events, rendered_content) VALUES ($1, $2, $3, $4, $5)',
      [crypto.randomUUID(), view.viewType, 'v1.0', view.sourceEvents, view]
    );
    
    // Publish to IPFS for permanent archive
    const ipfsHash = await this.ipfsClient.add(JSON.stringify(view));
    
    // Update index
    await this.db.query(
      'UPDATE audit_projections SET ipfs_hash = $1 WHERE id = $2',
      [ipfsHash, view.id]
    );
    
    // Mirror to backup destinations
    await Promise.all([
      this.s3Client.upload(`audit/${view.viewType}/${view.id}.json`, view),
      this.arweaveClient.store(view)
    ]);
    
    console.log(`Published ${view.viewType} view: ${view.id} (IPFS: ${ipfsHash})`);
  }
}
```

---

## Cryptographic Infrastructure

### 8.1 Key Management

```typescript
// Multi-party key custody using threshold signatures
class KeyManagementService {
  constructor(
    private thresholdScheme: ThresholdSignatureScheme,
    private keyShares: Map<string, KeyShare[]>
  ) {}

  async rotateSigningKey(): Promise<void> {
    // Require m-of-n participants to authorize rotation
    const requiredApprovals = Math.ceil(this.keyShares.size * 0.67);
    
    const rotationRequest = {
      requestedAt: new Date().toISOString(),
      requestedBy: this.currentOperator,
      newKeyCommitment: await this.generateKeyCommitment(),
      requiredApprovals
    };
    
    // Store pending rotation
    await this.db.query(
      'INSERT INTO pending_key_rotations (id, request, status) VALUES ($1, $2, $3)',
      [crypto.randomUUID(), rotationRequest, 'pending']
    );
    
    // Notify key custodians
    await this.notificationService.notifyKeyCustodians(rotationRequest);
  }

  async signWithThreshold(
    data: Buffer,
    purpose: KeyPurpose
  ): Promise<Signature> {
    const shares = this.keyShares.get(purpose);
    const required = Math.ceil(shares.length * 0.51);  // Simple majority
    
    // Collect shares from custodians
    const partialSigs = await this.collectPartialSignatures(
      data, 
      shares, 
      required
    );
    
    // Combine into full signature
    return this.thresholdScheme.combine(partialSigs);
  }
}

enum KeyPurpose {
  AUDIT_INTEGRITY = 'audit_integrity',
  PACKET_PUBLICATION = 'packet_publication',
  EMERGENCY_AUTHORIZATION = 'emergency_authorization',
  TREASURY_RELEASE = 'treasury_release'
}
```

### 8.2 Privacy-Preserving Proofs (Deferred for MVP)

Per MINIMUM_VIABLE_PNYX.md §5.1, advanced cryptographic privacy is deferred. MVP uses plain credentials with explicit debt tracking.

```typescript
// Simplified identity for MVP (debt: privacy not yet ZK)
interface MVPCredential {
  credentialId: string;
  memberId: string;
  scope: string;
  issuedAt: ISO8601;
  expiresAt: ISO8601;
  issuer: string;
  // Note: This is plain text, creating privacy debt
  // Future: Replace with ZK proofs per CRYPTOGRAPHIC_MODEL.md
}
```

---

## Classification Engine

### 9.1 Classification Pipeline

```typescript
class ClassificationEngine {
  async classifyProposal(
    proposalId: string,
    context: RuntimeContext
  ): Promise<ClassificationResult> {
    const proposal = await this.projectionService.getProposal(proposalId);
    
    // Primary classification pass
    const primaryResult = await this.primaryClassifier.classify(proposal);
    
    // Counter-classification (independent second pass)
    const counterResult = await this.counterClassifier.classify(proposal);
    
    // Deterministic rule validation
    const ruleValidation = this.validateAgainstRules(primaryResult);
    
    // Check for disagreement
    if (this.hasMaterialDisagreement(primaryResult, counterResult)) {
      // Escalate to stronger path automatically
      return this.escalateClassification(primaryResult);
    }
    
    // Apply safety-biased ambiguity handling
    if (primaryResult.confidence === 'low' || 
        primaryResult.ambiguities?.length > 0) {
      return this.escalateClassification(primaryResult);
    }
    
    return primaryResult;
  }
  
  private validateAgainstRules(result: ClassificationResult): ValidationResult {
    // Check for impossible triviality
    if (result.triviality === 'trivial' && result.hasConstitutionalSpillover) {
      return { valid: false, reason: 'Impossible triviality: constitutional spillover detected' };
    }
    
    // Check for impossible policy classification
    if (result.primaryLayer === 'policy' && result.frameworkChange) {
      return { valid: false, reason: 'Impossible policy: framework change detected' };
    }
    
    // Bootstrap: stricter validation
    if (this.isBootstrap()) {
      if (result.triviality === 'trivial' && result.affectedDomains?.length > 1) {
        return { valid: false, reason: 'Bootstrap: multi-domain proposals cannot be trivial' };
      }
    }
    
    return { valid: true };
  }
}
```

### 9.2 Rule Engine

```typescript
// Deterministic classification rules per CLASSIFICATION.md
const classificationRules: ClassificationRule[] = [
  {
    id: 'constitutional_spillover',
    condition: (p) => p.affectsRights || p.affectsMembership || p.affectsAntiCapture,
    action: 'escalate_to_constitutional',
    priority: 100
  },
  {
    id: 'framework_change',
    condition: (p) => p.changesProcedure || p.changesThresholds || p.changesRouting,
    action: 'require_governance_layer',
    priority: 90
  },
  {
    id: 'multi_domain',
    condition: (p) => p.affectedDomains?.length > 1,
    action: 'require_7_skill_panel',
    priority: 80
  },
  {
    id: 'reversibility',
    condition: (p) => p.reversibility === 'low' || p.reversibility === 'none',
    action: 'require_9_skill_panel',
    priority: 95
  }
];
```

---

## Panel Orchestration

### 10.1 Panel Assembly

```typescript
class PanelOrchestrator {
  async assemblePanel(
    proposalId: string,
    classification: ClassificationResult,
    context: RuntimeContext
  ): Promise<Panel> {
    // Determine required panel size
    const targetSize = this.determinePanelSize(classification);
    
    // Get required skill classes
    const requiredClasses = this.getRequiredClasses(classification);
    
    // Query registry for eligible skills
    const eligibleSkills = await this.skillRegistry.getEligibleSkills({
      classes: requiredClasses,
      status: 'active',
      bootstrapEligible: this.isBootstrap()
    });
    
    // Ensure provider diversity
    const selectedSkills = this.selectWithDiversity(
      eligibleSkills, 
      targetSize,
      requiredClasses
    );
    
    // Validate panel composition
    if (!this.validateClassCoverage(selectedSkills, requiredClasses)) {
      throw new Error('Panel assembly failed: missing required class coverage');
    }
    
    // Create panel spec
    const panel: Panel = {
      id: crypto.randomUUID(),
      proposalId,
      targetSize,
      requiredClasses,
      selectedSkills,
      status: 'assembled',
      createdAt: new Date().toISOString()
    };
    
    // Emit PanelAssembled event
    await this.eventStore.append({
      eventType: 'PanelAssembled',
      aggregateId: panel.id,
      payload: panel
    });
    
    return panel;
  }
  
  private selectWithDiversity(
    skills: Skill[],
    targetSize: number,
    requiredClasses: SkillClass[]
  ): Skill[] {
    const selected: Skill[] = [];
    
    // First, cover all required classes
    for (const requiredClass of requiredClasses) {
      const classSkills = skills.filter(s => s.skillClass === requiredClass);
      const selectedSkill = this.selectDiverse(classSkills, selected);
      selected.push(selectedSkill);
    }
    
    // Then fill remaining slots with diversity constraint
    while (selected.length < targetSize) {
      const remainingSkills = skills.filter(s => !selected.includes(s));
      const nextSkill = this.selectDiverse(remainingSkills, selected);
      if (nextSkill) {
        selected.push(nextSkill);
      } else {
        break;
      }
    }
    
    return selected;
  }
  
  private selectDiverse(candidates: Skill[], alreadySelected: Skill[]): Skill {
    // Prefer skills from different providers
    const providersInPanel = new Set(alreadySelected.map(s => s.providerId));
    
    const scored = candidates.map(skill => ({
      skill,
      score: providersInPanel.has(skill.providerId) ? 0 : 10  // Diversity bonus
    }));
    
    scored.sort((a, b) => b.score - a.score);
    return scored[0]?.skill;
  }
}
```

### 10.2 Panel Execution

```typescript
class PanelExecutor {
  async executePanel(panelId: string): Promise<SkillOutput[]> {
    const panel = await this.projectionService.getPanel(panelId);
    
    // Lock panel (no further modifications)
    await this.lockPanel(panel);
    
    // Execute all skills in parallel
    const executions = panel.selectedSkills.map(async skill => {
      const input: SkillInput = {
        proposal: await this.projectionService.getProposal(panel.proposalId),
        context: this.buildSkillContext(panel),
        epoch: this.getCurrentEpoch()
      };
      
      try {
        const output = await this.skillRegistry.executeSkill(skill.id, input);
        
        // Record output
        await this.eventStore.append({
          eventType: 'SkillOutputRecorded',
          aggregateId: panel.id,
          payload: {
            panelId: panel.id,
            skillId: skill.id,
            output,
            recordedAt: new Date().toISOString()
          }
        });
        
        return output;
      } catch (error) {
        // Record skill failure
        await this.eventStore.append({
          eventType: 'SkillExecutionFailed',
          aggregateId: panel.id,
          payload: {
            panelId: panel.id,
            skillId: skill.id,
            error: error.message,
            failedAt: new Date().toISOString()
          }
        });
        
        throw error;
      }
    });
    
    const outputs = await Promise.all(executions);
    
    // Verify all required outputs present
    if (!this.verifyCompletion(outputs, panel.requiredClasses)) {
      throw new Error('Panel execution incomplete');
    }
    
    return outputs;
  }
}
```

---

## Packet Generation

### 11.1 Packet Synthesis

```typescript
class PacketGenerator {
  async generateBriefingPacket(
    proposalId: string,
    panelOutputs: SkillOutput[]
  ): Promise<BriefingPacket> {
    const proposal = await this.projectionService.getProposal(proposalId);
    
    // Synthesize panel outputs (preserving dissent)
    const synthesis = this.synthesizeOutputs(panelOutputs);
    
    const packet: BriefingPacket = {
      proposalId,
      proposalTitle: proposal.title,
      proposalSummary: this.summarizeProposal(proposal),
      
      // Required sections per GOVERNANCE.md §8.9
      strongestCaseInFavor: synthesis.strongestCaseInFavor,
      strongestCaseAgainst: synthesis.strongestCaseAgainst,
      unknowns: synthesis.unknowns,
      evidenceSufficiencyNote: synthesis.evidenceSufficiency,
      captureRiskNote: synthesis.captureRisk,
      reversibilityNote: synthesis.reversibility,
      
      // Skill-specific sections
      rightsAnalysis: this.findSkillOutput(panelOutputs, 'rights_constitutional'),
      feasibilityAssessment: this.findSkillOutput(panelOutputs, 'implementation_feasibility'),
      resourceAnalysis: this.findSkillOutput(panelOutputs, 'economic_resource'),
      antiCaptureReview: this.findSkillOutput(panelOutputs, 'anti_capture_audit'),
      adversarialCritique: this.findSkillOutput(panelOutputs, 'adversarial_critique'),
      
      // Dissent preservation
      dissentSummary: synthesis.dissentNotes,
      
      // Routing
      proposedRoute: synthesis.recommendedRoute,
      routeRationale: synthesis.routeRationale,
      
      // Meta
      epoch: this.getCurrentEpoch(),
      thresholds: this.getThresholds(),
      reviewWindow: this.calculateReviewWindow(proposal.classification),
      
      // Panel composition (visible)
      panelComposition: panelOutputs.map(o => ({
        skillId: o.skillId,
        confidence: o.confidence
      })),
      
      generatedAt: new Date().toISOString()
    };
    
    // Publish packet
    await this.publishPacket(packet);
    
    return packet;
  }
  
  private synthesizeOutputs(outputs: SkillOutput[]): SynthesisResult {
    // Per GOVERNANCE.md §9: disagreement is a feature, not a defect
    // Do not average away dissent
    
    const inFavor = outputs
      .filter(o => o.strongestCaseInFavor)
      .map(o => ({ skillId: o.skillId, case: o.strongestCaseInFavor }));
    
    const against = outputs
      .filter(o => o.strongestCaseAgainst)
      .map(o => ({ skillId: o.skillId, case: o.strongestCaseAgainst }));
    
    const allUnknowns = outputs.flatMap(o => o.unknowns || []);
    
    // Aggregate confidence (lowest common denominator)
    const confidenceLevels = outputs.map(o => 
      o.confidence === 'low' ? 1 : o.confidence === 'medium' ? 2 : 3
    );
    const minConfidence = Math.min(...confidenceLevels);
    
    return {
      strongestCaseInFavor: this.selectStrongest(inFavor),
      strongestCaseAgainst: this.selectStrongest(against),
      unknowns: [...new Set(allUnknowns)],  // Deduplicate
      evidenceSufficiency: this.aggregateEvidenceSufficiency(outputs),
      captureRisk: this.aggregateCaptureRisk(outputs),
      reversibility: this.aggregateReversibility(outputs),
      dissentNotes: outputs
        .filter(o => o.dissentNotes)
        .map(o => ({ skillId: o.skillId, note: o.dissentNotes })),
      confidence: minConfidence === 1 ? 'low' : minConfidence === 2 ? 'medium' : 'high'
    };
  }
  
  private selectStrongest(cases: { skillId: string; case: string }[]): string {
    // Select the most comprehensive case (heuristic: longest substantive case)
    // Or present multiple if equally strong
    if (cases.length === 0) return 'No case presented';
    if (cases.length === 1) return cases[0].case;
    
    // For MVP: present all cases labeled by skill
    return cases.map(c => `[${c.skillId}]: ${c.case}`).join('\n\n');
  }
}
```

---

## Treasury Module

### 12.1 Treasury Operations

```typescript
class TreasuryManager {
  async allocate(
    partition: TreasuryPartition,
    amount: Decimal,
    purpose: string,
    authorization: AuthorizationChain
  ): Promise<Allocation> {
    // Validate authorization
    if (!this.validateAuthorization(authorization, partition)) {
      throw new Error('Invalid authorization for treasury allocation');
    }
    
    // Check balance
    const currentBalance = await this.getPartitionBalance(partition);
    if (currentBalance.lessThan(amount)) {
      throw new Error(`Insufficient funds in ${partition}: ${currentBalance} < ${amount}`);
    }
    
    // Create allocation
    const allocation: Allocation = {
      id: crypto.randomUUID(),
      partition,
      amount,
      purpose,
      authorization,
      status: 'allocated',
      allocatedAt: new Date().toISOString()
    };
    
    // Record in event store
    await this.eventStore.append({
      eventType: 'TreasuryAllocated',
      aggregateId: partition,
      payload: allocation
    });
    
    return allocation;
  }
  
  async release(
    allocation: Allocation,
    recipient: string,
    executor: OperatorIdentity
  ): Promise<Release> {
    // Verify dual control for releases above threshold
    if (allocation.amount.greaterThan(this.releaseThreshold)) {
      if (!executor.dualControl) {
        throw new Error('Dual control required for large releases');
      }
    }
    
    const release: TreasuryRelease = {
      id: crypto.randomUUID(),
      allocationId: allocation.id,
      partition: allocation.partition,
      amount: allocation.amount,
      recipient,
      executedBy: executor.id,
      coAuthorizedBy: executor.dualControl?.id,
      executedAt: new Date().toISOString(),
      transactionHash: await this.generateTransactionHash(allocation)
    };
    
    // Execute payment via appropriate rail
    await this.executePayment(release);
    
    // Record
    await this.eventStore.append({
      eventType: 'TreasuryReleased',
      aggregateId: allocation.partition,
      payload: release
    });
    
    return release;
  }
  
  async getPartitionMetrics(): Promise<TreasuryMetrics> {
    const partitions = await this.db.query('SELECT * FROM treasury_partitions');
    
    return {
      totalBalance: partitions.rows.reduce((sum, p) => sum + p.balance, 0),
      partitionBreakdown: partitions.rows.map(p => ({
        partition: p.partition_type,
        balance: p.balance,
        reserveMinimum: p.reserve_minimum,
        health: p.balance >= p.reserve_minimum ? 'healthy' : 'critical'
      })),
      donorConcentration: await this.calculateDonorConcentration(),
      recurringContributionRatio: await this.calculateRecurringRatio(),
      challengeFundingRatio: await this.calculateChallengeRatio()
    };
  }
}
```

---

## Emergency Subsystem

### 13.1 Emergency Controller

```typescript
class EmergencyController {
  async declareEmergency(
    emergencyClass: EmergencyClass,
    scope: string,
    expiresAt: Date,
    declarer: OperatorIdentity
  ): Promise<Emergency> {
    // Require co-authorization
    if (!declarer.coAuthorization) {
      throw new Error('Emergency declaration requires co-authorization');
    }
    
    // Validate emergency class
    if (!this.isValidEmergencyClass(emergencyClass)) {
      throw new Error(`Invalid emergency class: ${emergencyClass}`);
    }
    
    // Check if emergency already active in this class
    const existing = await this.getActiveEmergency(emergencyClass);
    if (existing) {
      throw new Error(`Emergency already active: ${existing.id}`);
    }
    
    const emergency: Emergency = {
      id: crypto.randomUUID(),
      emergencyClass,
      state: EmergencyState.DECLARED,
      scope,
      declaredBy: declarer.id,
      coAuthorizedBy: declarer.coAuthorization.id,
      declaredAt: new Date().toISOString(),
      expiresAt: expiresAt.toISOString(),
      reviewCaseId: crypto.randomUUID()  // Auto-create review case
    };
    
    // Emit event
    await this.eventStore.append({
      eventType: 'EmergencyDeclared',
      aggregateId: emergency.id,
      payload: emergency
    });
    
    // Activate time-based expiry enforcement
    await this.scheduleExpiry(emergency);
    
    // Publish incident packet
    await this.publishIncidentPacket(emergency);
    
    return emergency;
  }
  
  private async scheduleExpiry(emergency: Emergency): Promise<void> {
    // Technical enforcement: schedule automatic state transition
    const delay = new Date(emergency.expiresAt).getTime() - Date.now();
    
    setTimeout(async () => {
      await this.expireEmergency(emergency.id);
    }, delay);
    
    // Also schedule via persistent job queue for reliability
    await this.jobQueue.schedule('emergency_expiry', {
      emergencyId: emergency.id,
      executeAt: emergency.expiresAt
    });
  }
  
  async expireEmergency(emergencyId: string): Promise<void> {
    const emergency = await this.getEmergency(emergencyId);
    
    if (emergency.state !== EmergencyState.ACTIVE) {
      return;  // Already expired or closed
    }
    
    // Force state transition
    await this.eventStore.append({
      eventType: 'EmergencyExpired',
      aggregateId: emergencyId,
      payload: {
        emergencyId,
        expiredAt: new Date().toISOString(),
        automatic: true  // Technical enforcement, not operator decision
      }
    });
    
    // Trigger review
    await this.triggerEmergencyReview(emergency);
    
    console.log(`Emergency ${emergencyId} automatically expired`);
  }
  
  async canExecuteEmergencyAction(
    emergencyId: string,
    action: EmergencyAction
  ): Promise<boolean> {
    const emergency = await this.getEmergency(emergencyId);
    
    // Check expiry
    if (new Date() > new Date(emergency.expiresAt)) {
      return false;
    }
    
    // Check if action is allowed for this class
    if (!this.isAllowedAction(emergency.emergencyClass, action)) {
      return false;
    }
    
    return true;
  }
}
```

---

## Deployment Architecture

### 14.1 Infrastructure Requirements

```yaml
# Docker Compose for MVP deployment
version: '3.8'

services:
  api:
    build: ./api
    ports:
      - "3000:3000"
    environment:
      - NODE_ENV=production
      - DATABASE_URL=postgresql://...
      - REDIS_URL=redis://...
      - IPFS_API_URL=...
    depends_on:
      - postgres
      - redis
    deploy:
      replicas: 3
      resources:
        limits:
          cpus: '1'
          memory: 1G

  worker:
    build: ./worker
    environment:
      - NODE_ENV=production
      - DATABASE_URL=postgresql://...
      - REDIS_URL=redis://...
    depends_on:
      - postgres
      - redis
    deploy:
      replicas: 2

  postgres:
    image: postgres:15
    volumes:
      - postgres_data:/var/lib/postgresql/data
    environment:
      - POSTGRES_DB=pnyx
      - POSTGRES_USER=pnyx
    deploy:
      resources:
        limits:
          cpus: '2'
          memory: 4G

  redis:
    image: redis:7-alpine
    volumes:
      - redis_data:/data
    deploy:
      resources:
        limits:
          cpus: '0.5'
          memory: 512M

  # IPFS node for audit archive
  ipfs:
    image: ipfs/kubo:latest
    volumes:
      - ipfs_data:/data/ipfs
    ports:
      - "4001:4001"
      - "5001:5001"
      - "8080:8080"

volumes:
  postgres_data:
  redis_data:
  ipfs_data:
```

### 14.2 Multi-Cloud Redundancy

```typescript
// Cross-cloud deployment manager
class MultiCloudDeployment {
  constructor(
    private awsProvider: CloudProvider,
    private gcpProvider: CloudProvider,
    private azureProvider: CloudProvider
  ) {}

  async deploy(): Promise<DeploymentStatus> {
    // Deploy to primary (AWS)
    const primary = await this.awsProvider.deploy({
      region: 'us-east-1',
      size: 'full'
    });
    
    // Deploy to secondary (GCP) - hot standby
    const secondary = await this.gcpProvider.deploy({
      region: 'us-central1',
      size: 'full'
    });
    
    // Deploy to tertiary (Azure) - warm standby
    const tertiary = await this.azureProvider.deploy({
      region: 'eastus',
      size: 'minimum'
    });
    
    // Configure failover
    await this.configureFailover(primary, secondary, tertiary);
    
    return {
      primary: primary.endpoint,
      secondary: secondary.endpoint,
      tertiary: tertiary.endpoint,
      healthCheckEndpoint: '/health'
    };
  }
  
  async configureFailover(
    primary: Deployment,
    secondary: Deployment,
    tertiary: Deployment
  ): Promise<void> {
    // Health checks every 30 seconds
    // Auto-failover if primary unhealthy for >2 minutes
    // DNS updates to point to healthy endpoint
    // Audit log of all failover events
  }
}
```

---

## Testing Strategy

### 15.1 Test Categories

```typescript
// Test structure
├── unit/                          # Individual function tests
│   ├── classification.test.ts
│   ├── panel_assembly.test.ts
│   └── packet_generation.test.ts
├── integration/                   # Module interaction tests
│   ├── proposal_lifecycle.test.ts
│   ├── skill_execution.test.ts
│   └── treasury_flows.test.ts
├── e2e/                          # Full system tests
│   ├── happy_path.test.ts
│   ├── emergency_scenario.test.ts
│   └── capture_attempt.test.ts
├── property/                     # Generative/property tests
│   ├── audit_chain.test.ts
│   └── state_machine.test.ts
└── performance/                  # Load tests
    ├── proposal_flood.test.ts
    └── audit_scale.test.ts
```

### 15.2 Critical Test Cases

```typescript
describe('Emergency Enforcement', () => {
  it('should auto-expire emergency after time limit', async () => {
    // Declare emergency with 1-hour expiry
    const emergency = await emergencyController.declareEmergency(
      'runtime_integrity',
      'test_compromise',
      new Date(Date.now() + 60 * 60 * 1000),
      operator
    );
    
    // Fast-forward time
    jest.advanceTimersByTime(60 * 60 * 1000 + 1000);
    
    // Verify auto-expired
    const updated = await emergencyController.getEmergency(emergency.id);
    expect(updated.state).toBe('expired');
    expect(updated.expiredAutomatically).toBe(true);
  });
  
  it('should prevent emergency action after expiry', async () => {
    // ... test that emergency powers cease after expiry
  });
  
  it('should require co-authorization for emergency declaration', async () => {
    // ... test dual control
  });
});

describe('Audit Chain Integrity', () => {
  it('should detect tampered events', async () => {
    // Append event
    await eventStore.append(event);
    
    // Simulate tampering (in test DB)
    await db.query('UPDATE events SET payload = $1 WHERE id = $2', 
      [tamperedPayload, event.eventId]);
    
    // Verify chain broken
    await expect(eventStore.verifyChain()).rejects.toThrow('Chain break');
  });
  
  it('should maintain append-only invariant', async () => {
    // Attempt to delete event
    await expect(
      db.query('DELETE FROM events WHERE id = $1', [event.eventId])
    ).rejects.toThrow();  // Should fail due to DB constraints
  });
});

describe('Classification Safety', () => {
  it('should escalate ambiguous proposals', async () => {
    const ambiguousProposal = createAmbiguousProposal();
    
    const result = await classificationEngine.classifyProposal(
      ambiguousProposal.id
    );
    
    // Should take stronger path
    expect(result.primaryLayer).toBe('governance');  // Not 'policy'
    expect(result.triviality).toBe('non_trivial');
    expect(result.minimumPanelSize).toBeGreaterThanOrEqual(7);
  });
});

describe('Capture Resistance', () => {
  it('should prevent single-provider panel dominance', async () => {
    // Attempt to assemble panel with 4 skills from same provider
    const skills = [
      { id: 's1', provider: 'openai', class: 'rights' },
      { id: 's2', provider: 'openai', class: 'feasibility' },
      { id: 's3', provider: 'openai', class: 'economic' },
      { id: 's4', provider: 'openai', class: 'anti_capture' },
      { id: 's5', provider: 'anthropic', class: 'adversarial' }
    ];
    
    const panel = await panelOrchestrator.assemblePanel(skills);
    
    // Should reject or warn about concentration
    expect(panel.providerDiversity).toBeLessThan(0.5);
    expect(panel.concentrationWarning).toBeDefined();
  });
});
```

---

## Implementation Roadmap

### 16.1 Phase 1: Foundation (Months 1-2)

**Goal:** Core runtime, event store, basic API

**Deliverables:**
- [ ] Event store with chain hashing
- [ ] Proposal submission and basic classification
- [ ] PostgreSQL schema implementation
- [ ] API endpoints for proposal lifecycle
- [ ] Basic audit logging

**Team:** 2 backend, 1 infra
**Budget:** $15k

### 16.2 Phase 2: Skills & Panels (Months 2-3)

**Goal:** Skill integration, panel orchestration

**Deliverables:**
- [ ] Skill adapter framework
- [ ] Integration with 1-2 AI providers
- [ ] 5 skill classes with Tier 1 templates
- [ ] Panel assembly and locking
- [ ] Skill output recording

**Team:** 2 backend, 1 protocol
**Budget:** $20k

### 16.3 Phase 3: Packets & Audit (Months 3-4)

**Goal:** Briefing packets, audit views, emergency framework

**Deliverables:**
- [ ] Packet generation with dissent preservation
- [ ] 4 audit view projections
- [ ] Emergency declaration and auto-expiry
- [ ] IPFS integration for audit archive
- [ ] Frontend for audit views

**Team:** 1 backend, 2 frontend, 1 design
**Budget:** $25k

### 16.4 Phase 4: Treasury & Bootstrap (Months 4-5)

**Goal:** Treasury operations, bootstrap debt tracking

**Deliverables:**
- [ ] Treasury partitions and allocation
- [ ] Basic crowdfunding integration
- [ ] Bootstrap debt register UI
- [ ] Challenge pathway (simplified)
- [ ] Operator controls

**Team:** 2 backend, 1 frontend
**Budget:** $20k

### 16.5 Phase 5: Pilot & Hardening (Months 5-6)

**Goal:** Deploy to pilot community, iterate

**Deliverables:**
- [ ] Multi-cloud deployment
- [ ] Security audit
- [ ] Load testing (100 proposals, 1000 users)
- [ ] Pilot with real advisory issue
- [ ] Documentation and runbooks

**Team:** 1 backend, 1 frontend, 1 ops, 1 PM, 1 research
**Budget:** $20k

### 16.6 Total Resources

| Phase | Duration | Team | Budget |
|-------|----------|------|--------|
| Foundation | 2 months | 3 | $15k |
| Skills & Panels | 1 month | 3 | $20k |
| Packets & Audit | 1 month | 4 | $25k |
| Treasury | 1 month | 3 | $20k |
| Pilot | 1 month | 5 | $20k |
| **Total** | **6 months** | **10** | **$100k** |

---

## Open Questions

### 17.1 Technical Questions

1. **AI Provider Lock-in**: What if OpenAI/Anthropic change pricing/terms? Need portability adapters.

2. **Database at Scale**: PostgreSQL good for MVP, but what about 1M+ proposals? May need sharding/CockroachDB.

3. **IPFS Reliability**: IPFS content availability depends on pinning. Need multiple pinners.

4. **Identity Verification**: MVP uses plain credentials. Real deployment needs cryptographic identity. When to upgrade?

5. **Cryptographic Upgrades**: What happens when quantum computers break current signatures?

### 17.2 Operational Questions

1. **Operator Recruitment**: How to find trustworthy bootstrap operators?

2. **Incident Response**: Who responds to 3am outage? Escalation procedures?

3. **Legal Status**: What entity holds the treasury? Non-profit? Co-op? Foundation?

4. **Jurisdiction**: Which country's laws apply to disputes? How to handle cross-border?

5. **Tax Implications**: Crowdfunding income taxable? Skill developer payments?

### 17.3 Governance Questions

1. **Pilot Selection**: Which community/issue for first pilot? How to ensure diversity?

2. **Skill Development**: Who funds first Tier 3 governance-grade skills? Public procurement?

3. **Constitutional Review**: How to ensure first review actually happens? Technical trigger?

4. **Participation Inequality**: Can technical solutions (mobile apps, voice) reduce inequality, or is it structural?

5. **Cultural Adaptation**: Does Pnyx assume Western democratic values? How to adapt to other contexts?

### 17.4 Economic Questions

1. **Sustainability**: What if crowdfunding dries up? Can public IP revenue sustain operations?

2. **Skill Economics**: Will providers develop skills without clear revenue model?

3. **Treasury Growth**: How fast should treasury grow? What if grows too fast (concentration risk)?

4. **Donor Limits**: What if no donors accept limits? Alternative funding?

5. **Market Competition**: What if proprietary governance tools outcompete on convenience?

---

## Appendix A: Technology Stack Rationale

### Why TypeScript/Node.js?
- Rapid development
- Strong ecosystem
- Type safety
- Easy deployment
- Team familiarity

### Why PostgreSQL?
- ACID guarantees
- JSONB for flexible events
- Proven at scale
- Good ecosystem
- Can migrate to CockroachDB later

### Why IPFS?
- Permanent archiving
- Content-addressed (tamper-evident)
- Decentralized
- Public verifiability

### Why React?
- Component architecture
- Strong ecosystem
- Server-side rendering for SEO
- Accessibility support

---

## Appendix B: Security Considerations

### Threats and Mitigations

| Threat | Mitigation |
|--------|-----------|
| SQL Injection | Parameterized queries, ORM |
| XSS | Content Security Policy, output encoding |
| CSRF | SameSite cookies, CSRF tokens |
| DDoS | Rate limiting, CDN, auto-scaling |
| Key Compromise | Multi-party custody, regular rotation |
| Insider Threat | Dual control, audit logs, no single-admin |
| Supply Chain | Dependency pinning, SBOM, automated scanning |
| Data Breach | Encryption at rest, minimal data collection |

### Security Checklist (Pre-Launch)

- [ ] Penetration test
- [ ] Dependency audit
- [ ] Key custody review
- [ ] Access control audit
- [ ] Backup/recovery test
- [ ] Incident response plan
- [ ] Security runbook

---

## Appendix C: Compliance Notes

### GDPR (if serving EU)
- Data minimization
- Right to erasure (limited by audit requirements)
- Privacy by design
- Data Protection Impact Assessment

### CCPA (if serving California)
- Data transparency
- Opt-out mechanisms
- Data retention limits

### Local Regulations
- Political activity restrictions
- Non-profit status requirements
- Financial reporting obligations

---

*Document Version: 1.0*  
*Last Updated: April 2026*  
*Next Review: Upon pilot launch*
