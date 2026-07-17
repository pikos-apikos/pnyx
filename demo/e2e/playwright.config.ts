import { defineConfig, devices } from '@playwright/test';

/**
 * Playwright configuration for Pnyx e2e tests.
 *
 * The globalSetup starts PostgreSQL (via Docker Compose) and the Spring Boot
 * application before tests run. globalTeardown stops both.
 *
 * See ../docs/70_Bootstrap/PROTOTYPE_PLAN.md for the runtime architecture.
 */
export default defineConfig({
  testDir: './tests',
  fullyParallel: false,
  forbidOnly: !!process.env.CI,
  retries: process.env.CI ? 1 : 0,
  workers: 1,
  reporter: [
    ['html', { outputFolder: 'playwright-report' }],
    ['list'],
  ],
  timeout: 60_000,
  expect: { timeout: 10_000 },
  use: {
    baseURL: 'http://localhost:8080',
    trace: 'on-first-retry',
    screenshot: 'only-on-failure',
    video: 'retain-on-failure',
    actionTimeout: 15_000,
    navigationTimeout: 30_000,
  },
  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },
  ],
  globalSetup: './global-setup.ts',
  globalTeardown: './global-teardown.ts',
});