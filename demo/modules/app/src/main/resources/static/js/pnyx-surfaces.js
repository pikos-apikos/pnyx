(() => {
  'use strict';

  const params = new URLSearchParams(window.location.search);
  const proposalMatch = window.location.pathname.match(/^\/proposals\/([0-9a-f-]+)\/?$/i);
  const workbench = params.get('view') === 'workbench' || params.get('surface') === 'workbench';

  if (proposalMatch) {
    document.documentElement.dataset.pnyxSurface = workbench ? 'workbench' : 'citizen';
  }

  const stateLabels = {
    DRAFT: 'Draft', SUBMITTED: 'Received', CLASSIFICATION_PENDING: 'Defining the issue',
    REQUIRES_CLARIFICATION: 'Needs clarification', CLASSIFIED: 'Problem defined',
    PARTICIPATION_DESIGN_PENDING: 'Designing participation', PANEL_SELECTION_PENDING: 'Selecting reviewers',
    PANEL_LOCKED: 'Review panel ready', EVIDENCE_ASSEMBLY: 'Gathering evidence',
    DELIBERATION_ACTIVE: 'Specialist analysis', PACKET_DRAFTING: 'Testing the arguments',
    PACKET_PUBLISHED: 'Civic brief published', PARTICIPANT_BODY_FORMATION: 'Forming participant body',
    PUBLIC_REVIEW_OPEN: 'Public review open', CHALLENGED: 'Under challenge', REVIEW_REPAIR: 'Review being repaired',
    READY_FOR_DECISION: 'Judgment ready', DECISION_OPEN: 'Your judgment is requested',
    APPROVED: 'Approved', REJECTED: 'Rejected', DEFERRED: 'Deferred',
    ROUTING_PENDING: 'Selecting execution path', ROUTED: 'Execution path selected',
    EXECUTION_AUTHORIZED: 'Execution authorized', EXECUTION_ACTIVE: 'In execution',
    EXECUTION_PAUSED: 'Execution paused', EXECUTION_COMPLETED: 'Execution completed',
    MONITORING_ACTIVE: 'Measuring outcomes', LEARNING_PUBLISHED: 'Learning published',
    POST_REVIEW_OPEN: 'Outcome review open', CLOSED: 'Closed', INVALIDATED: 'Invalidated'
  };

  document.addEventListener('DOMContentLoaded', () => {
    initHeader();
    initCharacterCounts();
    if (window.location.pathname === '/') initDashboard();
    if (proposalMatch) initProposal(proposalMatch[1]);
  });

  function node(tag, className, text) {
    const element = document.createElement(tag);
    if (className) element.className = className;
    if (text !== undefined && text !== null) element.textContent = text;
    return element;
  }

  function link(label, href, className = 'btn btn-ghost') {
    const element = node('a', className, label);
    element.href = href;
    return element;
  }

  function initHeader() {
    const links = document.querySelectorAll('.header-nav a');
    links.forEach(anchor => anchor.removeAttribute('aria-current'));
    if (workbench) links.forEach(anchor => anchor.classList.remove('active'));

    const selector = workbench
      ? '.header-nav a[data-surface-link="workbench"]'
      : window.location.pathname === '/proposals/new'
        ? '.header-nav a[href="/proposals/new"]'
        : window.location.pathname === '/' || proposalMatch
          ? '.header-nav a[href="/"]'
          : null;
    if (!selector) return;

    const activeLink = document.querySelector(selector);
    activeLink?.setAttribute('aria-current', 'page');
    activeLink?.classList.add('active');
  }

  function initCharacterCounts() {
    document.querySelectorAll('[data-character-count]').forEach(field => {
      const max = Number(field.getAttribute('maxlength')) || 0;
      const output = document.querySelector(`[data-count-for="${field.id}"]`);
      if (!output) return;
      const update = () => { output.textContent = max ? `${field.value.length}/${max}` : `${field.value.length}`; };
      field.addEventListener('input', update);
      update();
    });
  }

  function initDashboard() {
    if (workbench) {
      document.body.classList.add('surface-workbench');
      const hero = document.querySelector('.hero');
      const title = hero?.querySelector('h1');
      const copy = hero?.querySelector('p');
      const action = hero?.querySelector('.btn-primary');
      if (title) title.textContent = 'Protocol Workbench';
      if (copy) copy.textContent = 'Inspect lifecycle states, participation artifacts, execution records, and the verifiable public history.';
      if (action) { action.textContent = 'Open audit trail'; action.href = '/audit'; }
      const heading = document.querySelector('.section-header h2');
      if (heading) heading.textContent = 'Proposal operations';
      document.querySelectorAll('a[href^="/proposals/"]').forEach(anchor => {
        const match = anchor.getAttribute('href')?.match(/^\/proposals\/([0-9a-f-]+)$/i);
        if (!match) return;
        anchor.href = `/workbench/proposals/${match[1]}`;
        if (anchor.classList.contains('btn')) anchor.textContent = 'Open workbench';
      });
      insertSurfaceSwitch(document.querySelector('.hero'), 'Protocol Workbench', 'Citizen home', '/');
    } else {
      document.body.classList.add('surface-citizen');
      humanizeBadges(document);
    }
  }

  function initProposal(proposalId) {
    const state = document.querySelector('.detail-header .badge')?.textContent.trim() || 'UNKNOWN';
    if (workbench) {
      document.body.classList.add('surface-workbench');
      const target = document.querySelector('.detail-header');
      insertSurfaceSwitch(target, 'Protocol Workbench', 'Citizen view', `/proposals/${proposalId}`);
      document.querySelector('a[href="#deliberation"]')?.setAttribute('href', '#deliberation-panel');
      guardDecisionControls(state);
      return;
    }

    document.body.classList.add('surface-citizen');
    buildCitizenBrief(proposalId, state);
  }

  function insertSurfaceSwitch(before, title, actionLabel, actionHref) {
    if (!before || document.querySelector('.surface-switch')) return;
    const bar = node('div', 'surface-switch');
    const copy = node('div', 'surface-switch-copy');
    copy.append(node('span', 'surface-switch-label', 'Current surface'));
    copy.append(node('span', 'surface-switch-title', title));
    const actions = node('div', 'surface-switch-actions');
    actions.append(link(actionLabel, actionHref));
    bar.append(copy, actions);
    before.parentNode.insertBefore(bar, before);
  }

  function buildCitizenBrief(proposalId, state) {
    const existingHeader = document.querySelector('.detail-header');
    if (!existingHeader) return;

    const title = existingHeader.querySelector('h1')?.textContent.trim() || 'Public issue';
    const meta = existingHeader.querySelectorAll('.detail-header-meta > span');
    const classification = meta.length > 1 ? meta[1].textContent.trim() : '';
    const createdAt = meta.length ? meta[meta.length - 1].textContent.trim() : '';
    const contentCards = document.querySelectorAll('#proposal-content > .card');
    const problem = contentCards[0]?.querySelector('p')?.textContent.trim() || 'The public problem statement is not available.';
    const proposedAction = contentCards[1]?.querySelector('p')?.textContent.trim() || 'No response has been proposed yet.';

    const brief = node('article', 'citizen-brief');
    brief.dataset.testid = 'citizen-brief';
    brief.setAttribute('aria-labelledby', 'citizen-title');

    const switchBar = node('div', 'surface-switch');
    const switchCopy = node('div', 'surface-switch-copy');
    switchCopy.append(node('span', 'surface-switch-label', 'Current surface'));
    switchCopy.append(node('span', 'surface-switch-title', 'Citizen brief'));
    const switchActions = node('div', 'surface-switch-actions');
    switchActions.append(link('Protocol Workbench', `/workbench/proposals/${proposalId}`));
    switchBar.append(switchCopy, switchActions);
    brief.append(switchBar);

    brief.append(node('div', 'citizen-kicker', 'Public issue'));
    const heading = node('h1', 'citizen-title', title);
    heading.id = 'citizen-title';
    brief.append(heading);

    const metaRow = node('div', 'citizen-meta');
    metaRow.append(node('span', 'tag', stateLabels[state] || state.replaceAll('_', ' ').toLowerCase()));
    if (classification) metaRow.append(node('span', 'tag tag-muted', classification));
    if (createdAt) metaRow.append(node('span', '', createdAt));
    brief.append(metaRow);

    brief.append(buildAttention(state));
    brief.append(buildTextSection('The issue', problem));
    brief.append(buildTextSection('Possible response', proposedAction));
    brief.append(buildProgress(state));

    const clarification = document.querySelector('.alert-warning');
    if (clarification && state === 'REQUIRES_CLARIFICATION') {
      brief.append(buildTextSection('What needs clarification', clarification.textContent.trim(), 'citizen-section citizen-section-muted'));
    }

    const next = buildTextSection('What happens next', nextStep(state), 'citizen-section citizen-section-muted');
    if (state === 'DECISION_OPEN') appendDecisionActions(next);
    brief.append(next);

    const transparency = buildTextSection(
      'Verify, inspect, challenge',
      'The brief is the human-readable entrance. The complete artifacts, model activity, lifecycle state, and hash-linked history remain publicly inspectable.'
    );
    const links = node('div', 'citizen-links');
    links.append(
      link('Open protocol workbench', `/workbench/proposals/${proposalId}`),
      link('Verify public record', `/audit?streamId=${proposalId}`)
    );
    transparency.append(links);
    brief.append(transparency);

    existingHeader.parentNode.insertBefore(brief, existingHeader);
  }

  function buildAttention(state) {
    const details = attentionFor(state);
    const box = node('section', 'citizen-attention');
    const time = node('div', 'citizen-attention-time');
    time.append(node('strong', '', details.minutes));
    time.append(node('span', '', 'minutes'));
    const copy = node('div');
    copy.append(node('h2', '', details.title));
    copy.append(node('p', '', details.copy));
    box.append(time, copy);
    return box;
  }

  function buildTextSection(title, copy, className = 'citizen-section') {
    const section = node('section', className);
    section.append(node('h2', '', title));
    section.append(node('p', '', copy));
    return section;
  }

  function buildProgress(state) {
    const stages = ['Intake', 'Evidence', 'Deliberation', 'Judgment', 'Execution', 'Learning'];
    const current = stageIndex(state);
    const section = buildTextSection('Where this stands', stateLabels[state] || state, 'citizen-section');
    const track = node('div', 'citizen-state-track');
    stages.forEach((stage, index) => {
      const item = node('div', 'citizen-stage', stage);
      if (index < current) item.classList.add('is-complete');
      if (index === current) item.classList.add('is-current');
      track.append(item);
    });
    section.append(track);
    return section;
  }

  function appendDecisionActions(section) {
    const actions = node('div', 'citizen-decision-actions');
    document.querySelectorAll('form[action*="/decisions"]').forEach(form => {
      actions.append(form.cloneNode(true));
    });
    if (!actions.children.length) return;

    section.append(actions);
    window.htmx?.process(actions);
  }

  function guardDecisionControls(state) {
    if (state === 'DECISION_OPEN') return;
    const forms = document.querySelectorAll('form[action*="/decisions"]');
    if (!forms.length) return;
    const card = forms[0].closest('.card');
    forms.forEach(form => form.hidden = true);
    if (card && !card.querySelector('[data-decision-guard]')) {
      const note = node('p', 'alert alert-info', 'Decision controls are available only after the decision phase is formally opened.');
      note.dataset.decisionGuard = 'true';
      card.prepend(note);
    }
  }

  function humanizeBadges(root) {
    root.querySelectorAll('.badge').forEach(badge => {
      const raw = badge.textContent.trim();
      if (stateLabels[raw]) badge.textContent = stateLabels[raw];
    });
  }

  function attentionFor(state) {
    if (state === 'DECISION_OPEN') return { minutes: '8', title: 'Your judgment is requested', copy: 'Read the issue, inspect the disagreement, and record your judgment.' };
    if (['PUBLIC_REVIEW_OPEN', 'CHALLENGED', 'REVIEW_REPAIR'].includes(state)) return { minutes: '6', title: 'Public review is open', copy: 'Check the civic brief and challenge missing evidence, affected voices, or misleading claims.' };
    if (state === 'REQUIRES_CLARIFICATION') return { minutes: '4', title: 'The issue needs clarification', copy: 'A clearer problem statement is required before public analysis can continue.' };
    if (['APPROVED', 'ROUTING_PENDING', 'ROUTED', 'EXECUTION_AUTHORIZED', 'EXECUTION_ACTIVE', 'EXECUTION_COMPLETED', 'MONITORING_ACTIVE'].includes(state)) return { minutes: '3', title: 'Follow the public outcome', copy: 'No vote is requested now. Check whether execution matches the mandate and whether outcomes are being measured.' };
    if (['LEARNING_PUBLISHED', 'POST_REVIEW_OPEN', 'CLOSED'].includes(state)) return { minutes: '3', title: 'See what society learned', copy: 'Compare the promised outcome with what happened and what should change next time.' };
    return { minutes: '2', title: 'No action is required yet', copy: 'The issue is being prepared. You can follow it now and return when evidence or judgment is ready.' };
  }

  function nextStep(state) {
    const steps = {
      REQUIRES_CLARIFICATION: 'The submitter can answer the public clarification questions. The original submission remains visible.',
      PUBLIC_REVIEW_OPEN: 'Citizens inspect the evidence, expose omissions, and submit challenges before judgment opens.',
      CHALLENGED: 'The challenged part of the review must be repaired or answered before the proposal advances.',
      READY_FOR_DECISION: 'The review is complete. The formal decision period is the next step.',
      DECISION_OPEN: 'Citizens now approve, reject, or defer the proposal. The final judgment belongs to people.',
      APPROVED: 'The approved judgment must be translated into an explicit, constrained execution path.',
      EXECUTION_ACTIVE: 'Execution continues under its public mandate and monitoring obligations.',
      MONITORING_ACTIVE: 'Measured outcomes will be compared with the success, failure, and rollback criteria.',
      LEARNING_PUBLISHED: 'The learning artifact becomes public memory for future citizens and future models.'
    };
    return steps[state] || 'The protocol advances only when the required public artifact and lifecycle guard are satisfied.';
  }

  function stageIndex(state) {
    if (['DRAFT', 'SUBMITTED', 'CLASSIFICATION_PENDING', 'REQUIRES_CLARIFICATION', 'CLASSIFIED', 'PARTICIPATION_DESIGN_PENDING'].includes(state)) return 0;
    if (['PANEL_SELECTION_PENDING', 'PANEL_LOCKED', 'EVIDENCE_ASSEMBLY', 'DELIBERATION_ACTIVE', 'PACKET_DRAFTING'].includes(state)) return 1;
    if (['PACKET_PUBLISHED', 'PARTICIPANT_BODY_FORMATION', 'PUBLIC_REVIEW_OPEN', 'CHALLENGED', 'REVIEW_REPAIR'].includes(state)) return 2;
    if (['READY_FOR_DECISION', 'DECISION_OPEN', 'APPROVED', 'REJECTED', 'DEFERRED'].includes(state)) return 3;
    if (['ROUTING_PENDING', 'ROUTED', 'EXECUTION_AUTHORIZED', 'EXECUTION_ACTIVE', 'EXECUTION_PAUSED', 'EXECUTION_COMPLETED', 'MONITORING_ACTIVE'].includes(state)) return 4;
    return 5;
  }
})();
