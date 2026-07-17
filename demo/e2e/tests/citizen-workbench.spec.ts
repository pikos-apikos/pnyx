import { expect, test } from '@playwright/test';

const proposalId = '11111111-1111-1111-1111-111111111111';

function proposalFixture(state = 'CLASSIFIED'): string {
  return `<!DOCTYPE html>
  <html lang="en">
    <head>
      <meta charset="UTF-8">
      <link rel="stylesheet" href="/css/pnyx.css">
      <link rel="stylesheet" href="/css/citizen.css">
      <script src="/js/pnyx-surfaces.js" defer></script>
    </head>
    <body>
      <header class="header"><div class="container"><a class="header-brand" href="/">Pnyx</a><nav class="header-nav"><a href="/">Civic Home</a><a href="/workbench" data-surface-link="workbench">Protocol Workbench</a></nav></div></header>
      <main class="main"><div class="container">
        <a class="back-link" href="/">Back</a>
        <div class="detail-header"><div class="detail-header-meta"><span class="badge">${state}</span><span class="tag">Municipal</span><span>2026-07-17</span></div><h1>Make the playground accessible</h1></div>
        <nav class="detail-nav"><a href="#deliberation">Review Panel</a></nav>
        <div class="detail-body">
          <div id="proposal-content">
            <div class="card"><h3>The Problem</h3><p>Children with mobility difficulties cannot safely use the neighborhood playground.</p></div>
            <div class="card"><h3>Proposed Action</h3><p>Upgrade the entrances, surfacing, and play equipment.</p></div>
          </div>
          <div class="card" id="deliberation-panel">AI Review Panel</div>
          <div class="card"><h3>Actions</h3><form action="/proposals/${proposalId}/decisions"><button>Approve</button></form></div>
        </div>
      </div></main>
    </body>
  </html>`;
}

test.describe('Citizen UI and Protocol Workbench', () => {
  test('civic home uses citizen-facing language', async ({ page }) => {
    await page.goto('/');

    await expect(page.getByRole('heading', { name: 'What needs our attention?' })).toBeVisible();
    await expect(page.getByRole('link', { name: 'Raise an Issue' }).first()).toBeVisible();
  });

  test('proposal defaults to a focused citizen brief', async ({ page }) => {
    await page.route(`**/proposals/${proposalId}`, route => route.fulfill({
      status: 200,
      contentType: 'text/html',
      body: proposalFixture(),
    }));

    await page.goto(`/proposals/${proposalId}`);

    await expect(page.locator('[data-testid="citizen-brief"]')).toBeVisible();
    await expect(page.getByRole('heading', { name: 'The issue' })).toBeVisible();
    await expect(page.getByText('Children with mobility difficulties')).toBeVisible();
    await expect(page.locator('.detail-body')).toBeHidden();
    await expect(page.getByRole('link', { name: 'Protocol Workbench' }).first()).toHaveAttribute(
      'href', `/workbench/proposals/${proposalId}`,
    );
  });

  test('workbench preserves protocol detail and guards premature decisions', async ({ page }) => {
    await page.route(`**/proposals/${proposalId}?view=workbench`, route => route.fulfill({
      status: 200,
      contentType: 'text/html',
      body: proposalFixture('CLASSIFIED'),
    }));

    await page.goto(`/proposals/${proposalId}?view=workbench`);

    await expect(page.getByText('Protocol Workbench').first()).toBeVisible();
    await expect(page.locator('.detail-body')).toBeVisible();
    await expect(page.getByText('AI Review Panel')).toBeVisible();
    await expect(page.getByRole('button', { name: 'Approve' })).toBeHidden();
    await expect(page.getByText('Decision controls are available only')).toBeVisible();
    await expect(page.locator('a[href="#deliberation-panel"]')).toBeVisible();
  });
});
