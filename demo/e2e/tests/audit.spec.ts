import { test, expect, type Page } from '@playwright/test';

async function createProposal(page: Page, title: string, problem: string): Promise<string> {
  await page.goto('/proposals/new');
  await page.locator('#title').fill(title);
  await page.locator('#problem').fill(problem);
  await page.getByRole('button', { name: 'Publish the Issue' }).click();

  await expect(page).toHaveURL(/\/proposals\/([0-9a-f-]{36})/);
  const proposalId = page.url().match(/([0-9a-f-]{36})/)?.[1];
  expect(proposalId).toBeTruthy();
  return proposalId!;
}

/**
 * Audit trail e2e tests.
 *
 * Tests the audit page: form rendering, stream lookup, hash-chain verification,
 * event rendering, and navigation from the citizen brief.
 */
test.describe('Audit Trail', () => {
  test('should render the audit trail page with search form', async ({ page }) => {
    await page.goto('/audit');

    await expect(page.getByRole('heading', { name: 'Audit Trail' })).toBeVisible();
    await expect(page.locator('p').first()).toContainText('SHA-256 hash chain');
    await expect(page.locator('#streamId')).toBeVisible();
    await expect(page.getByRole('button', { name: 'Verify Stream' })).toBeVisible();
  });

  test('should show empty state when no stream ID is provided', async ({ page }) => {
    await page.goto('/audit');
    await expect(page.locator('table')).not.toBeVisible();
  });

  test('should display event stream for a valid proposal', async ({ page }) => {
    const proposalId = await createProposal(
      page,
      `Audit Test ${Date.now()}`,
      'Test problem for audit trail verification.',
    );

    await page.goto(`/audit?streamId=${proposalId}`);

    await expect(page.getByRole('heading', { name: 'Event Stream' })).toBeVisible();
    await expect(page.locator('table')).toBeVisible();
    expect(await page.locator('table tbody tr').count()).toBeGreaterThan(0);
    await expect(page.locator('.alert')).toContainText(/Chain integrity verified|Chain has integrity issues/);
  });

  test('should show hash verification status per event', async ({ page }) => {
    const proposalId = await createProposal(
      page,
      `Hash Verify ${Date.now()}`,
      'Test problem for hash verification display.',
    );

    await page.goto(`/audit?streamId=${proposalId}`);

    const firstRow = page.locator('table tbody tr').first();
    await expect(firstRow.locator('.dot')).toBeVisible();
    await expect(firstRow.locator('.tag', { hasText: /OK|FAIL/ })).toBeVisible();

    const hashText = await firstRow.locator('.table-hash').first().textContent();
    expect(hashText).toContain('sha256:');
  });

  test('should navigate from citizen brief to the public record', async ({ page }) => {
    const proposalId = await createProposal(
      page,
      `Nav Test ${Date.now()}`,
      'Test problem for navigation to audit.',
    );

    await page.getByRole('link', { name: 'Verify public record' }).click();
    await expect(page).toHaveURL(`/audit?streamId=${proposalId}`);
    await expect(page.getByRole('heading', { name: 'Event Stream' })).toBeVisible();
  });
});
