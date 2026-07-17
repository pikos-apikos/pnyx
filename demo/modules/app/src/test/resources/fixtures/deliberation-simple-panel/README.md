# Deliberation: Simple Panel

Tests that the deliberation service runs skill-panel reviews for a submitted proposal.

**Event:** A `ProposalSubmitted` event with title "Test Proposal", problem "Traffic congestion", and action "Build bike lanes".

**Expected:** Two reviews (legal-reviewer, risk-reviewer) with non-empty findings.