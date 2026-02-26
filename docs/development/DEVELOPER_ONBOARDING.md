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

Before you begin, ensure you have the following installed:

| Tool        | Version   | Purpose                                         |
|-------------|-----------|--------------------------------------------------|
| Git         | 2.30+     | Version control                                  |
| Git LFS     | 3.0+      | Large file storage (required for binary artifacts)|
| JDK         | 8         | Java development (must be Java 8, not newer)     |
| Maven       | 3.6+      | Build automation                                 |
| Windows 10+ | --        | Primary supported OS (WSL2 also works)           |

**Why Java 8?** The Eclipse 3.1 runtime and the InterWeave SDK plugin (`iw_sdk_1.0.0`) are compiled for Java 8. Newer Java versions may introduce class format or API incompatibilities.

**Git LFS is mandatory.** The repository stores binary files (`.exe`, `.jar`, `.war`, `.class`) via Git LFS. Without it, you will get small text pointer files instead of the actual binaries.

### Installing Git LFS

```bash
# Ubuntu/WSL2
sudo apt install git-lfs

# macOS
brew install git-lfs

# Windows (included with Git for Windows 2.x+)
# Verify with:
git lfs version
```

---

## 2. Clone and Setup

### Clone the repository

```bash
git clone <repository-url> IW_Launcher
cd IW_Launcher
```

### Initialize Git LFS and pull binary files

```bash
git lfs install
git lfs pull
```

### Verify binaries were pulled correctly

Run these checks to confirm LFS files are real binaries (not text pointer files):

```bash
# Each of these should be several KB or MB, NOT a ~130-byte text file
ls -la jre/bin/java.exe
ls -la web_portal/tomcat/lib/catalina.jar
ls -la startup.jar
```

If any of these files are very small (under 200 bytes), Git LFS did not pull correctly. Run `git lfs pull` again.

### Create your environment file

```bash
# Option A: Run START.bat (auto-creates .env from .env.example)
./START.bat

# Option B: Copy manually
cp .env.example .env
```

Edit `.env` to set your database credentials. The default mode is `supabase`:

```bash
DB_MODE=supabase
SUPABASE_DB_HOST=your-host-here
SUPABASE_DB_PORT=5432
SUPABASE_DB_NAME=your-db-name
SUPABASE_DB_USER=your-db-user
SUPABASE_DB_PASSWORD=your-db-password
SUPABASE_DB_SSLMODE=require
```

Ask your team lead for the current Supabase credentials if they are not provided in your onboarding materials.

---

## 3. First Run

### Start the full application (Windows)

```cmd
START.bat
```

This script:
1. Creates `.env` from `.env.example` if it does not exist
2. Starts the Tomcat web server (port 9090)
3. Launches the Eclipse IDE (`iw_ide.exe`)
4. Opens your default browser to the login page

### Log in

Open `http://localhost:9090/iw-business-daemon/IWLogin.jsp` and use:

| Field    | Value          |
|----------|----------------|
| Username | `__iw_admin__` |
| Password | `%iwps%`       |

**Note:** Only the admin account works. Custom user accounts fail due to a proprietary password hash in the compiled `LoginServlet.class`. See `docs/adr/002-additive-only-changes.md` for details.

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

This interactive script lets you switch between Supabase, Oracle Cloud, InterWeave server, and local (offline) modes.

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
- [ ] The Eclipse IDE launches and opens the workspace
- [ ] API endpoints return valid JSON (e.g., `GET /api/monitoring`)
- [ ] No new errors in `web_portal/tomcat/logs/catalina.out`
- [ ] Database connectivity works for the configured `DB_MODE`

### Testing different database modes

Switch `DB_MODE` in `.env` and re-run `CHANGE_DATABASE.bat` to verify your changes work across:

- `supabase` -- Primary Postgres database
- `oracle_cloud` -- Legacy MySQL
- `local` -- Offline/admin-only mode

---

## 7. Key Files to Know

| File                     | Purpose                                                       |
|--------------------------|---------------------------------------------------------------|
| `CLAUDE.md`              | AI agent instructions and full project architecture overview  |
| `AI_WORKFLOW.md`         | Mandatory workflow for AI agents working in this repo         |
| `BUILD.md`               | Build system documentation (Maven, servlet compilation)       |
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

### Git LFS not pulled

**Symptom:** The IDE does not start, Tomcat fails, or JAR files appear to be tiny text files.

**Fix:** Run `git lfs install && git lfs pull` and verify binary file sizes as described in the setup section.

### Wrong DB_MODE

**Symptom:** Login fails, pages show database connection errors, or Tomcat logs show JDBC exceptions.

**Fix:** Check `.env` to ensure `DB_MODE` matches a database you have credentials for. Run `CHANGE_DATABASE.bat` to reconfigure.

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
