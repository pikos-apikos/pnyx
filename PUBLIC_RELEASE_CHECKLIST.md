# Public Repository Release Checklist

This checklist covers repository settings and operational checks that cannot be completed by merging files alone.

## Before changing visibility

- [ ] Review the complete Git history for secrets, credentials, private URLs, personal data, proprietary material, and internal-only comments.
- [ ] Rotate any credential that has ever appeared in a commit, even if it was later removed.
- [ ] Confirm that model names, local paths, sample data, and configuration do not expose private infrastructure or personal information.
- [ ] Confirm that every third-party dependency, copied text, image, dataset, and generated artifact may be redistributed.
- [ ] Review `LICENSE`, `NOTICE`, and contribution terms with qualified legal counsel if the project will receive institutional funding, trademarks, patents, or binding public-sector adoption.
- [ ] Verify the repository from a clean unauthenticated clone.
- [ ] Run the full Gradle and end-to-end test suites.
- [ ] Review open branches and pull requests for content that will become visible when the repository is made public.

## Recommended GitHub settings

### General

- [ ] Set a concise repository description and `https://pnyx.dev` as the homepage.
- [ ] Add topics such as `civic-tech`, `democracy`, `public-interest-technology`, `java`, `spring-boot`, `event-sourcing`, and `ai-governance`.
- [ ] Enable Issues.
- [ ] Enable Discussions and create categories for Questions, Ideas, Protocol, Research, and Show and Tell.
- [ ] Keep Wikis disabled unless they become an intentionally governed documentation surface.
- [ ] Prefer squash merging for normal pull requests.
- [ ] Enable automatic branch deletion after merge.
- [ ] Enable auto-merge when required checks and reviews are configured.

### Security

- [ ] Enable private vulnerability reporting.
- [ ] Enable Dependabot alerts and security updates.
- [ ] Enable secret scanning and push protection where available.
- [ ] Enable dependency graph and code scanning after validating the workflows.
- [ ] Confirm that `SECURITY.md` appears in the repository Security tab.

### Default branch protection

Protect `main` with rules appropriate to the current contributor capacity:

- [ ] Require pull requests before merging.
- [ ] Require at least one approval.
- [ ] Dismiss stale approvals when new commits are pushed.
- [ ] Require review from Code Owners when applicable.
- [ ] Require conversation resolution.
- [ ] Require status checks once CI is stable.
- [ ] Require linear history if squash/rebase is the project policy.
- [ ] Block force pushes and branch deletion.
- [ ] Allow emergency bypass only for the founding steward during bootstrap, with a public follow-up explanation.

## Collaboration launch

- [ ] Confirm the private contact path for conduct reports.
- [ ] Install or enable a DCO check if signed-off commits will be enforced automatically.
- [ ] Create the labels referenced by issue forms and collaboration documentation.
- [ ] Identify at least one backup maintainer or reviewer before inviting broad participation.
- [ ] Publish a small initial roadmap with tasks suitable for first-time contributors.
- [ ] Mark high-impact protocol and governance issues clearly.
- [ ] Prepare a short public announcement explaining prototype status and what the project is **not** yet safe to do.

## After publication

- [ ] Verify the GitHub Community Standards profile.
- [ ] Open each issue form and the pull request template as an external contributor would see them.
- [ ] Verify links from `README.md`, community files, and documentation.
- [ ] Confirm that license detection identifies Apache-2.0.
- [ ] Review the first external contribution using the documented process and update confusing guidance.
- [ ] Record public-release lessons as a project artifact rather than silently changing process.
