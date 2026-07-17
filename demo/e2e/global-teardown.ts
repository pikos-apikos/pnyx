import { execSync } from 'child_process';
import * as path from 'path';
import { appProcess } from './global-setup';

/**
 * Global teardown: stops the Spring Boot application and PostgreSQL.
 * Only stops the app if we started it (appProcess is non-null).
 */
async function globalTeardown() {
  const projectRoot = path.resolve(__dirname, '..');

  if (appProcess) {
    console.log('[e2e] Stopping Spring Boot application...');
    appProcess.kill('SIGTERM');
    await new Promise((r) => setTimeout(r, 2000));
    if (!appProcess.killed) {
      appProcess.kill('SIGKILL');
    }
  } else {
    console.log('[e2e] App was already running — skipping shutdown.');
  }

  console.log('[e2e] Stopping PostgreSQL...');
  try {
    execSync('docker compose down', { cwd: projectRoot, stdio: 'inherit' });
  } catch {
    // Ignore
  }

  console.log('[e2e] Teardown complete.');
}

export default globalTeardown;