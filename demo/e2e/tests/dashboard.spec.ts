import { test, expect } from '@playwright/test';

/**
 * Dashboard (index page) e2e tests.
 *
 * Verifies the landing page renders correctly, shows the hero section,
 * navigation links, and the proposal list (either proposals or empty state).
 *
 * See ../docs/90_Information/UI_SPEC.md for the dashboard layout.
 */
test.describe('Dashboard', () => {
  test('should render the landing page with hero and navigation', async ({ page }) => {
    await page.goto('/');

    // Hero section
    await expect(page.locator('h1')).toContainText('Public Reasoning');
    await expect(page.locator('.hero p')).toContainText('civic reasoning platform');

    // Navigation
    await expect(page.locator('.header-nav a', { hasText: 'Dashboard' })).toBeVisible();
    await expect(page.locator('.header-nav a', { hasText: 'New Proposal' })).toBeVisible();
    await expect(page.locator('.header-nav a', { hasText: 'Audit Trail' })).toBeVisible();

    // Footer
    await expect(page.locator('.footer')).toContainText('Public Reasoning Protocol');
  });

  test('should display the active proposals section', async ({ page }) => {
    await page.goto('/');

    // Section header
    await expect(page.locator('h2', { hasText: 'Active Proposals' })).toBeVisible();

    // Either empty state or proposal cards
    const emptyState = page.locator('.empty-state');
    const cardGrid = page.locator('.card-grid');

    // One of the two must be visible
    const isEmptyVisible = await emptyState.isVisible().catch(() => false);
    const isGridVisible = await cardGrid.isVisible().catch(() => false);
    expect(isEmptyVisible || isGridVisible).toBeTruthy();
  });

  test('should navigate to the new proposal form', async ({ page }) => {
    await page.goto('/');

    await page.locator('a', { hasText: 'New Proposal' }).first().click();
    await expect(page).toHaveURL(/\/proposals\/new/);
    await expect(page.locator('h1')).toContainText('Submit a Proposal');
  });

  test('should navigate to the audit trail page', async ({ page }) => {
    await page.goto('/');

    await page.locator('a', { hasText: 'Audit Trail' }).first().click();
    await expect(page).toHaveURL(/\/audit/);
    await expect(page.locator('h1')).toContainText('Audit Trail');
  });
});