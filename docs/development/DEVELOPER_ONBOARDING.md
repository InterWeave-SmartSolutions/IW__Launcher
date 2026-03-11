# InterWeave IDE -- Developer Onboarding Guide

Welcome to the InterWeave IDE project. This guide walks you through setting up your development environment, running the application, and contributing code.

## Table of Contents

1. [Prerequisites](#1-prerequisites)
2. [Clone and Setup](#2-clone-and-setup)
3. [First Run](#3-first-run)
4. [Development Workflow](#4-development-workflow)
5. [Building](#5-building)
6. [Testing](#6-testing)
7. [Key Files to Know](#7-key-files-to-know)
8. [Common Pitfalls](#8-common-pitfalls)

---

## 1. Prerequisites

| Tool        | Version   | Required for                                     | Notes |
|-------------|-----------|--------------------------------------------------|-------|
| Git         | 2.30+     | Version control                                  | |
| Git LFS     | any       | Pulling legacy `.doc` files                      | Only 2 files use LFS; not needed for binaries |
| JDK 8 (x86) | 1.8       | Compiling Java servlets / running IDE            | Must be 32-bit for `iw_ide.exe` |
| Maven       | 3.6+      | Building the error/validation framework          | Optional for most contributors |
| Node.js     | 18+       | React portal development                         | Optional — build output is tracked in git |
| Windows 10+ | --        | Running the application natively                 | WSL2 can edit files but cannot run Tomcat |

**Why 32-bit JRE?** `iw_ide.exe` is a 32-bit Eclipse 3.1 launcher. It requires a 32-bit (x86) JRE. A 64-bit JRE will fail silently or crash.

**Git LFS clarification:** Only two legacy `.doc` files use Git LFS. Application binaries (`.exe`, compiled `.class`, `.jar`) are stored directly in git or must be installed via setup scripts. You do NOT need `git lfs pull` to get the application working — just the setup steps below.

---

## 2. Clone and Setup

### Clone the repository

```bash
git clone <repository-url> IW_Launcher
cd IW_Launcher
```

### Step A — Install Tomcat (one-time)

Tomcat binaries are not in git. Run the install script:

```cmd
scripts\setup\install_tomcat.bat
```

This downloads Apache Tomcat 9.0.83 (~25 MB) from the official Apache archive and installs it into `web_portal\tomcat\bin\` and `web_portal\tomcat\lib\`.

### Step B — Install the JRE (one-time)

The JRE is not in git (90 MB, gitignored). Download and install manually:

1. Go to: https://adoptium.net/temurin/releases/?version=8&arch=x86&os=windows&package=jre
2. Download the **Windows x86** (32-bit) JRE 8 ZIP
3. Extract it so that `jre\bin\java.exe` exists in the repository root

> **Must be 32-bit (x86).** The IDE launcher (`iw_ide.exe`) is a 32-bit Eclipse binary.

### Step C — Verify the environment file

`START.bat` auto-creates `.env` from `.env.example` on first run. The team Supabase credentials are pre-filled in `.env.example`, so no manual editing is required. If you need to inspect or override:

```bash
# .env is auto-created — check it if something seems wrong
cat .env
```

The important settings:
```
DB_MODE=supabase       # Use shared Supabase database
TS_MODE=local          # Use bundled local transformation server
```

---

## 3. First Run

### Start the full application (Windows)

```cmd
START.bat
```

This script:
1. Creates `.env` from `.env.example` if it does not exist
2. Loads the active `DB_MODE` / `TS_MODE`
3. Prepares the legacy runtime assets
4. Starts the Tomcat web server (port 9090)
5. Exports and compiles saved workspace profile overlays
6. Launches the Eclipse IDE (`iw_ide.exe`)
7. Opens your default browser to the login page

### Log in

Open `http://localhost:9090/iw-portal/` (React portal) or `http://localhost:9090/iw-business-daemon/IWLogin.jsp` (classic JSP) and use:

| Field    | Value          |
|----------|----------------|
| Username | `__iw_admin__` |
| Password | `%iwps%`       |

**Current behavior:** The admin account works, and DB-backed users also work
through `LocalLoginServlet` / `ApiLoginServlet` when their credentials exist in
the active database. The seeded regression profile is `Tester1`
(`amagown@interweave.biz`) in `CRM2QB3`.

### Start individual components

```cmd
:: Web portal only
scripts\start_webportal.bat

:: IDE only
scripts\start_ide.bat

:: Stop web portal
scripts\stop_webportal.bat

:: Stop everything
STOP.bat
```

### Change database connection

```cmd
CHANGE_DATABASE.bat
```

This interactive script lets you switch between Supabase, InterWeave hosted, and local (offline) modes.

The runtime flow/log host is controlled separately by `TS_MODE` in `.env`:

- `TS_MODE=local` keeps flow/query/log traffic on the bundled local runtime
- `TS_MODE=legacy` points those runtime URLs at the historical InterWeave host

---

## 4. Development Workflow

### Branching strategy

1. Create a feature branch from `main`:
   ```bash
   git checkout main
   git pull origin main
   git checkout -b feature/your-feature-name
   ```

2. Make your changes following the **additive-only policy** (see `docs/adr/002-additive-only-changes.md`):
   - Add new files; do not modify protected directories (`plugins/`, `jre/`, `configuration/`)
   - New servlets get new URL mappings
   - New SQL migrations extend the existing numbering

3. Commit with clear, descriptive messages:
   ```bash
   git add <specific-files>
   git commit -m "feat: add monitoring dashboard endpoint"
   ```

4. Push and open a pull request:
   ```bash
   git push -u origin feature/your-feature-name
   ```

### Commit conventions

- Use conventional commit prefixes: `feat:`, `fix:`, `docs:`, `chore:`, `refactor:`, `test:`
- Reference issue numbers when applicable
- Keep commits focused on a single change

### Pull request process

- Ensure all tests pass before requesting review
- Include a description of what changed and why
- Reference the relevant ADR if the change involves an architectural decision
- See `CONTRIBUTING.md` for full contribution guidelines

---

## 5. Building

### Full Maven build

```bash
mvn clean compile
```

### Run tests during build

```bash
mvn clean test
```

### Compile individual servlets

For servlets that are deployed directly to Tomcat (outside the Maven build), use the servlet compilation script:

```cmd
scripts\compile_servlet.bat
```

This compiles Java servlet source files and deploys the resulting `.class` files to the Tomcat webapps directory. See `BUILD.md` for full details on:

- Maven build configuration
- Compilation commands and classpath setup
- Deployment to Tomcat
- IDE integration (Eclipse, IntelliJ, VS Code)

### Database setup

```cmd
:: Windows
scripts\SETUP_DB_Windows.bat

:: Linux/Mac
scripts/SETUP_DB_Linux.sh
```

These scripts create the required tables and seed data in whichever database is configured in `.env`.

---

## 6. Testing

### Automated tests

```bash
# Run standard test suite
mvn test

# Run all tests including integration tests
mvn test -P all-tests
```

### Manual verification checklist

After making changes, verify the following manually:

- [ ] `START.bat` launches without errors
- [ ] Tomcat starts and `http://localhost:9090/iw-business-daemon/IWLogin.jsp` loads
- [ ] Admin login (`__iw_admin__` / `%iwps%`) succeeds
- [ ] `scripts\verify_legacy_engine.ps1` passes
- [ ] `scripts\verify_profile_compiler.ps1` passes
- [ ] The Eclipse IDE launches and opens the workspace
- [ ] API endpoints return valid JSON (e.g., `GET /api/monitoring`)
- [ ] No new errors in `web_portal/tomcat/logs/catalina*.log`
- [ ] Database connectivity works for the configured `DB_MODE`

### Testing different database modes

Switch `DB_MODE` in `.env` and re-run `CHANGE_DATABASE.bat` to verify your changes work across:

- `supabase` -- Primary Postgres database
- `interweave` -- Legacy hosted MySQL
- `local` -- Offline/admin-only mode

---

## 7. Key Files to Know

| File                     | Purpose                                                       |
|--------------------------|---------------------------------------------------------------|
| `CLAUDE.md`              | AI agent instructions and full project architecture overview  |
| `docs/ai/AI_WORKFLOW.md` | Mandatory workflow for AI agents working in this repo         |
| `docs/development/BUILD.md` | Build system documentation (Maven, servlet compilation)   |
| `CONTRIBUTING.md`        | Contribution guidelines and coding standards                  |
| `README.md`              | Project introduction and quick-start guide                    |
| `.env.example`           | Template for database and environment configuration           |
| `.env`                   | Your local environment config (never commit this)             |
| `START.bat`              | Main startup script                                           |
| `STOP.bat`               | Shutdown script                                               |
| `CHANGE_DATABASE.bat`    | Interactive database connection switcher                      |
| `docs/API.md`            | Web portal endpoint reference                                 |
| `docs/adr/`              | Architecture Decision Records                                 |

---

## 8. Common Pitfalls

### JRE or Tomcat missing after clone

**Symptom:** `START.bat` exits immediately with "[ERROR] Java runtime not found" or "[ERROR] Web server not found".

**Fix:**
- Tomcat: Run `scripts\setup\install_tomcat.bat` (auto-downloads, one-time).
- JRE: Download Eclipse Adoptium JRE 8 x86 and extract to `jre\`. See Step B in Section 2 above.

These are not in git (too large). The install scripts handle Tomcat automatically; the JRE is a one-time manual download.

### React portal not loading at /iw-portal/

**Symptom:** `http://localhost:9090/iw-portal/` shows 404 or blank page after a fresh clone.

**Fix:** The build output is tracked in git. If it's missing, someone may have cleaned it. Rebuild:
```bash
cd frontends/iw-portal
npm install
node node_modules/vite/bin/vite.js build
```
Then commit the result: `git add web_portal/tomcat/webapps/iw-portal/ && git commit`

### Wrong DB_MODE

**Symptom:** Login fails, pages show database connection errors, or Tomcat logs show JDBC exceptions.

**Fix:** Check `.env` to ensure `DB_MODE` matches a database you have credentials for. Run `CHANGE_DATABASE.bat` to reconfigure.

Also verify `TS_MODE` matches your intended flow/log host:

- `local` for the bundled runtime
- `legacy` for the historical hosted runtime

### Modifying protected directories

**Symptom:** The IDE crashes on startup, plugins fail to load, or authentication breaks entirely.

**Fix:** Revert any changes to `plugins/`, `jre/`, or `configuration/`. Read `docs/adr/002-additive-only-changes.md` to understand the additive-only policy.

### Java version mismatch

**Symptom:** `UnsupportedClassVersionError` or other class loading errors.

**Fix:** Ensure you are using Java 8 (JDK 1.8). The bundled JRE at `jre/` is Java 8. If building with Maven, set `JAVA_HOME` to a JDK 8 installation.

### Port 9090 already in use

**Symptom:** Tomcat fails to start with "Address already in use" error.

**Fix:** Either stop the other process using port 9090, or change the Tomcat port in `web_portal/tomcat/conf/server.xml`.

### .env not created

**Symptom:** Startup scripts fail with errors about missing configuration.

**Fix:** Copy `.env.example` to `.env` manually, or run `START.bat` which does this automatically on first run.

---

## Next Steps

- Read the training tutorials in `docs/tutorials/` for IDE usage
- Review the Architecture Decision Records in `docs/adr/`
- Explore sample workspace projects in `workspace/`
- Check `web_portal/tomcat/logs/` if anything goes wrong
