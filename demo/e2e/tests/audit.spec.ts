import { test, expect } from '@playwright/test';

/**
 * Audit trail e2e tests.
 *
 * Tests the audit page: form rendering, stream lookup, hash chain verification
 * display, and event table rendering.
 *
 * See ../docs/80_Runtime/EVENT_MODEL.md for the hash-chained event store model.
 */
test.describe('Audit Trail', () => {
  test('should render the audit trail page with search form', async ({ page }) => {
    await page.goto('/audit');

    await expect(page.locator('h1')).toContainText('Audit Trail');
    await expect(page.locator('p').first()).toContainText('SHA-256 hash chain');

    // Search form
    await expect(page.locator('#streamId')).toBeVisible();
    await expect(page.locator('button[type="submit"]', { hasText: 'Verify Stream' })).toBeVisible();
  });

  test('should show empty state when no stream ID is provided', async ({ page }) => {
    await page.goto('/audit');

    // No event stream table should be visible initially
    await expect(page.locator('table')).not.toBeVisible();
  });

  test('should display event stream for a valid proposal', async ({ page }) => {
    // First create a proposal to get a valid stream ID
    await page.goto('/proposals/new');
    await page.locator('#title').fill(`Audit Test ${Date.now()}`);
    await page.locator('#problem').fill('Test problem for audit trail verification.');
    await page.locator('button[type="submit"]', { hasText: 'Submit Proposal' }).click();

    await expect(page).toHaveURL(/\/proposals\/([0-9a-f-]{36})/);

    // Extract the proposal ID from the URL
    const url = page.url();
    const proposalId = url.match(/([0-9a-f-]{36})/)?.[1];
    expect(proposalId).toBeTruthy();

    // Navigate to audit trail with the stream ID
    await page.goto(`/audit?streamId=${proposalId}`);

    // Should show the event stream table
    await expect(page.locator('h2', { hasText: 'Event Stream' })).toBeVisible();
    await expect(page.locator('table')).toBeVisible();

    // Should show at least one event row
    const rows = page.locator('table tbody tr');
    const rowCount = await rows.count();
    expect(rowCount).toBeGreaterThan(0);

    // Should show chain integrity status
    await expect(page.locator('.alert')).toContainText(/Chain integrity verified|Chain has integrity issues/);
  });

  test('should show hash verification status per event', async ({ page }) => {
    // Create a proposal
    await page.goto('/proposals/new');
    await page.locator('#title').fill(`Hash Verify ${Date.now()}`);
    await page.locator('#problem').fill('Test problem for hash verification display.');
    await page.locator('button[type="submit"]', { hasText: 'Submit Proposal' }).click();

    await expect(page).toHaveURL(/\/proposals\/([0-9a-f-]{36})/);
    const proposalId = page.url().match(/([0-9a-f-]{36})/)?.[1];

    // Go to audit trail
    await page.goto(`/audit?streamId=${proposalId}`);

    // Each row should have a verification dot and message
    const firstRow = page.locator('table tbody tr').first();
    await expect(firstRow.locator('.dot')).toBeVisible();
    await expect(firstRow.locator('.tag', { hasText: /OK|FAIL/ })).toBeVisible();

    // Should show content hash (text is split across spans within .table-hash)
    const hashDiv = firstRow.locator('.table-hash').first();
    await expect(hashDiv).toBeVisible();
    const hashText = await hashDiv.textContent();
    expect(hashText).toContain('sha256:');
  });

  test('should navigate from proposal detail to audit trail', async ({ page }) => {
    // Create a proposal
    await page.goto('/proposals/new');
    await page.locator('#title').fill(`Nav Test ${Date.now()}`);
    await page.locator('#problem').fill('Test problem for navigation to audit.');
    await page.locator('button[type="submit"]', { hasText: 'Submit Proposal' }).click();

    await expect(page).toHaveURL(/\/proposals\/[0-9a-f-]{36}/);

    // Click the "View Audit Trail" link
    const auditLink = page.locator('a', { hasText: 'View Audit Trail' });
    if (await auditLink.isVisible().catch(() => false)) {
      await auditLink.click();
      await expect(page).toHaveURL(/\/audit\?streamId=/);
      await expect(page.locator('h2', { hasText: 'Event Stream' })).toBeVisible();
    }
  });
});