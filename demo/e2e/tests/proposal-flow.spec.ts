import { test, expect, type Page } from '@playwright/test';

async function publishIssue(
  page: Page,
  title: string,
  problem: string,
  proposedAction?: string,
): Promise<string> {
  await page.goto('/proposals/new');
  await page.locator('#title').fill(title);
  await page.locator('#problem').fill(problem);
  if (proposedAction) await page.locator('#proposedAction').fill(proposedAction);
  await page.getByRole('button', { name: 'Publish the Issue' }).click();

  await expect(page).toHaveURL(/\/proposals\/[0-9a-f-]{36}/);
  const proposalId = page.url().match(/([0-9a-f-]{36})/)?.[1];
  expect(proposalId).toBeTruthy();
  return proposalId!;
}

/**
 * Proposal flow e2e tests across the citizen intake, citizen brief, and
 * Protocol Workbench views.
 */
test.describe('Proposal Flow', () => {
  test('should render the problem-first issue intake', async ({ page }) => {
    await page.goto('/proposals/new');

    await expect(page.getByRole('heading', { name: 'Raise a Public Issue' })).toBeVisible();
    await expect(page.locator('#title')).toBeVisible();
    await expect(page.locator('#problem')).toBeVisible();
    await expect(page.locator('#proposedAction')).toBeVisible();
    await expect(page.getByRole('button', { name: 'Publish the Issue' })).toBeVisible();
    await expect(page.locator('label[for="title"]')).toContainText('Name the issue');
    await expect(page.locator('label[for="problem"]')).toContainText('What is happening?');
  });

  test('should publish an issue and render the citizen brief', async ({ page }) => {
    const title = `E2E Test Proposal ${Date.now()}`;
    const problem = 'This is a test problem created by the Playwright e2e suite.';
    const action = 'This is the proposed action to address the problem.';

    await publishIssue(page, title, problem, action);

    const brief = page.locator('[data-testid="citizen-brief"]');
    await expect(brief).toBeVisible();
    await expect(brief.getByRole('heading', { name: title })).toBeVisible();
    await expect(brief).toContainText(problem);
    await expect(brief).toContainText(action);
    await expect(page.locator('.detail-body')).toBeHidden();
  });

  test('should preserve browser validation for an empty title', async ({ page }) => {
    await page.goto('/proposals/new');
    await page.getByRole('button', { name: 'Publish the Issue' }).click();
    await expect(page).toHaveURL(/\/proposals\/new/);
  });

  test('should display state and progress in the citizen brief', async ({ page }) => {
    await publishIssue(
      page,
      `Status Check ${Date.now()}`,
      'Test problem for status display verification.',
    );

    const brief = page.locator('[data-testid="citizen-brief"]');
    await expect(brief.locator('.citizen-meta .tag').first()).toBeVisible();
    await expect(brief.getByRole('heading', { name: 'Where this stands' })).toBeVisible();
    await expect(brief.locator('.citizen-state-track')).toBeVisible();
  });

  test('should expose the review panel in the Protocol Workbench', async ({ page }) => {
    const proposalId = await publishIssue(
      page,
      `AI Panel Test ${Date.now()}`,
      'Test problem for AI panel display.',
    );

    await page.goto(`/workbench/proposals/${proposalId}`);

    await expect(page.locator('.detail-body')).toBeVisible();
    await expect(page.locator('#deliberation-panel')).toBeVisible();
    await expect(page.locator('#deliberation-panel h3')).toContainText('AI Review Panel');
  });
});
