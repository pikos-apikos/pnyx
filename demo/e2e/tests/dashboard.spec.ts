import { test, expect } from '@playwright/test';

/**
 * Citizen home e2e tests.
 *
 * Verifies the public entrance, navigation, and public-issue list after the
 * Citizen UI / Protocol Workbench split.
 *
 * See ../UI_SURFACES.md for the surface boundary.
 */
test.describe('Civic Home', () => {
  test('should render the citizen-facing landing page and navigation', async ({ page }) => {
    await page.goto('/');

    await expect(page.getByRole('heading', { name: 'What needs our attention?' })).toBeVisible();
    await expect(page.locator('.hero p')).toContainText('Start with a clear civic brief');

    const navigation = page.getByRole('navigation', { name: 'Primary navigation' });
    await expect(navigation.getByRole('link', { name: 'Civic Home' })).toBeVisible();
    await expect(navigation.getByRole('link', { name: 'Raise an Issue' })).toBeVisible();
    await expect(navigation.getByRole('link', { name: 'Protocol Workbench' })).toBeVisible();
    await expect(navigation.getByRole('link', { name: 'Verify' })).toBeVisible();

    await expect(page.locator('.footer')).toContainText('human judgment, public reasoning, verifiable memory');
  });

  test('should display the public issues section', async ({ page }) => {
    await page.goto('/');

    await expect(page.getByRole('heading', { name: 'Public Issues' })).toBeVisible();

    const emptyState = page.locator('.empty-state');
    const cardGrid = page.locator('.card-grid');
    const isEmptyVisible = await emptyState.isVisible().catch(() => false);
    const isGridVisible = await cardGrid.isVisible().catch(() => false);
    expect(isEmptyVisible || isGridVisible).toBeTruthy();
  });

  test('should navigate to the issue intake', async ({ page }) => {
    await page.goto('/');

    await page.getByRole('link', { name: 'Raise an Issue' }).first().click();
    await expect(page).toHaveURL(/\/proposals\/new/);
    await expect(page.getByRole('heading', { name: 'Raise a Public Issue' })).toBeVisible();
  });

  test('should navigate to verification', async ({ page }) => {
    await page.goto('/');

    await page.getByRole('link', { name: 'Verify' }).click();
    await expect(page).toHaveURL(/\/audit/);
    await expect(page.getByRole('heading', { name: 'Audit Trail' })).toBeVisible();
  });
});
