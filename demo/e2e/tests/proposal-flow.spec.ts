import { test, expect } from '@playwright/test';

/**
 * Proposal flow e2e tests.
 *
 * Tests the full proposal lifecycle: form rendering, submission, detail view,
 * and state display. Uses the HTML/Thymeleaf endpoints with HTMX.
 *
 * See ../docs/20_Protocol_Core/PROTOCOL.md §6 for the proposal lifecycle.
 */
test.describe('Proposal Flow', () => {
  test('should render the proposal submission form with all fields', async ({ page }) => {
    await page.goto('/proposals/new');

    await expect(page.locator('h1')).toContainText('Submit a Proposal');

    // Form fields
    await expect(page.locator('#title')).toBeVisible();
    await expect(page.locator('#problem')).toBeVisible();
    await expect(page.locator('#proposedAction')).toBeVisible();

    // Submit button
    await expect(page.locator('button[type="submit"]', { hasText: 'Submit Proposal' })).toBeVisible();

    // Labels
    await expect(page.locator('label[for="title"]')).toContainText('Title');
    await expect(page.locator('label[for="problem"]')).toContainText('Problem');
  });

  test('should submit a proposal and redirect to detail page', async ({ page }) => {
    await page.goto('/proposals/new');

    const title = `E2E Test Proposal ${Date.now()}`;
    const problem = 'This is a test problem created by the Playwright e2e suite.';
    const action = 'This is the proposed action to address the problem.';

    await page.locator('#title').fill(title);
    await page.locator('#problem').fill(problem);
    await page.locator('#proposedAction').fill(action);
    await page.locator('button[type="submit"]', { hasText: 'Submit Proposal' }).click();

    // Should redirect to the proposal detail page
    await expect(page).toHaveURL(/\/proposals\/[0-9a-f-]{36}/);

    // Detail page should show the title and problem
    await expect(page.locator('h1')).toContainText(title);
    await expect(page.locator('.detail-body')).toContainText(problem);
    await expect(page.locator('.detail-body')).toContainText(action);

    // Should show a state badge
    await expect(page.locator('.badge')).toBeVisible();
  });

  test('should show validation error for empty title', async ({ page }) => {
    await page.goto('/proposals/new');

    // Submit without filling required fields
    await page.locator('button[type="submit"]', { hasText: 'Submit Proposal' }).click();

    // Browser should block submission due to required attribute
    // Stay on the form page
    await expect(page).toHaveURL(/\/proposals\/new/);
  });

  test('should display proposal state and status info on detail page', async ({ page }) => {
    // Create a proposal first
    await page.goto('/proposals/new');

    const title = `Status Check ${Date.now()}`;
    await page.locator('#title').fill(title);
    await page.locator('#problem').fill('Test problem for status display verification.');
    await page.locator('button[type="submit"]', { hasText: 'Submit Proposal' }).click();

    await expect(page).toHaveURL(/\/proposals\/[0-9a-f-]{36}/);

    // State badge should be visible
    const badge = page.locator('.badge').first();
    await expect(badge).toBeVisible();
    const stateText = await badge.textContent();
    expect(stateText).toBeTruthy();

    // Status section should exist
    await expect(page.locator('.detail-body .card h3', { hasText: 'Status' })).toBeVisible();
  });

  test('should show the AI review panel section with HTMX loading', async ({ page }) => {
    // Create a proposal
    await page.goto('/proposals/new');
    await page.locator('#title').fill(`AI Panel Test ${Date.now()}`);
    await page.locator('#problem').fill('Test problem for AI panel display.');
    await page.locator('button[type="submit"]', { hasText: 'Submit Proposal' }).click();

    await expect(page).toHaveURL(/\/proposals\/[0-9a-f-]{36}/);

    // The deliberation panel section should exist
    await expect(page.locator('#deliberation-panel')).toBeVisible();
    await expect(page.locator('#deliberation-panel h3')).toContainText('AI Review Panel');
  });
});