import { execSync, spawn } from 'child_process';
import { existsSync } from 'fs';
import * as path from 'path';

let appProcess: ReturnType<typeof spawn> | null = null;

/**
 * Global setup: starts PostgreSQL via Docker Compose and launches the Spring
 * Boot application. Waits for the server to be ready before handing off to
 * the test runner.
 *
 * If the app is already running on :8080, skips startup.
 */
async function globalSetup() {
  const projectRoot = path.resolve(__dirname, '..');

  // Check if app is already running
  try {
    const res = await fetch('http://localhost:8080/');
    if (res.ok || res.status === 302 || res.status === 404) {
      console.log('[e2e] Application already running — skipping startup.');
      return;
    }
  } catch {
    // Not running — proceed with startup
  }

  // Start PostgreSQL
  console.log('[e2e] Starting PostgreSQL via Docker Compose...');
  try {
    execSync('docker compose up -d postgres', { cwd: projectRoot, stdio: 'inherit' });
  } catch {
    // Already running — ignore
  }

  // Wait for PostgreSQL to be ready
  console.log('[e2e] Waiting for PostgreSQL...');
  await waitFor('http://localhost:5432', 30_000, false);

  // Start the Spring Boot application
  console.log('[e2e] Starting Spring Boot application...');
  const gradlew = path.join(projectRoot, 'gradlew');
  appProcess = spawn(gradlew, [':app:bootRun'], {
    cwd: projectRoot,
    stdio: 'pipe',
    env: {
      ...process.env,
      SPRING_PROFILES_ACTIVE: 'test',
    },
  });

  appProcess.stdout?.on('data', (data: Buffer) => {
    const line = data.toString().trim();
    if (line) console.log(`[app] ${line}`);
  });
  appProcess.stderr?.on('data', (data: Buffer) => {
    const line = data.toString().trim();
    if (line) console.error(`[app] ${line}`);
  });

  // Wait for the app to be ready
  console.log('[e2e] Waiting for application to be ready...');
  await waitFor('http://localhost:8080', 120_000, true);

  console.log('[e2e] Application is ready!');
}

async function waitFor(url: string, timeoutMs: number, httpCheck: boolean): Promise<void> {
  const start = Date.now();
  while (Date.now() - start < timeoutMs) {
    try {
      if (httpCheck) {
        const res = await fetch(url);
        if (res.ok || res.status === 302 || res.status === 404) return;
      } else {
        // TCP check — just try to connect
        const { hostname, port } = new URL(url);
        await new Promise<void>((resolve, reject) => {
          const net = require('net');
          const sock = net.createConnection({ host: hostname, port: parseInt(port) }, () => {
            sock.destroy();
            resolve();
          });
          sock.on('error', reject);
        });
        return;
      }
    } catch {
      // Not ready yet
    }
    await new Promise((r) => setTimeout(r, 1000));
  }
  throw new Error(`Timeout waiting for ${url} after ${timeoutMs}ms`);
}

export default globalSetup;
export { appProcess };