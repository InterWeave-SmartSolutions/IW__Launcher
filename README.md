# InterWeave IDE (IW_IDE)

Enterprise data integration platform for creating synchronization workflows between business applications.

---

## Documentation Map

Use the repo entry points below instead of hunting through the tree manually:

- Runtime use: `README.md`
- Full documentation index: `docs/README.md`
- AI operating guidance: `docs/ai/README.md`
- Developer onboarding: `docs/development/DEVELOPER_ONBOARDING.md`
- Setup/install docs: `docs/setup/`
- Legacy manuals and imported historical references: `docs/legacy-pdfs/`

This keeps the repository navigable without changing the underlying legacy file layout.

---

## How to Use (3 Simple Scripts)

| Script | What It Does |
|--------|--------------|
| `START.bat` | **Start the application** - Double-click this! |
| `STOP.bat` | **Stop the application** |
| `CHANGE_DATABASE.bat` | Change database connection (optional) |

That's it! Just double-click `START.bat` to begin.

---

## First Time? (External / Demo users)

Just visit **https://iw-portal.vercel.app** — no install needed.

Log in with `demo@sample.com` / `demo123` to explore the portal.

---

## First Time? (Running locally after git clone)

Two one-time installs are required because large binaries are not stored in git:

**Step 1 — Install Tomcat** (auto-download, ~25 MB):
```
scripts\setup\install_tomcat.bat
```

**Step 2 — Install the JRE** (manual download):

Download **Eclipse Adoptium JRE 8, 32-bit (x86)** from:
https://adoptium.net/temurin/releases/?version=8&arch=x86&os=windows&package=jre

Extract it so that `jre\bin\java.exe` exists in this repository folder.

> **Important:** Must be 32-bit (x86). `iw_ide.exe` is a 32-bit Eclipse app and will not launch with a 64-bit JRE.

**Step 3 — Start:**
```
START.bat
```

`START.bat` handles everything else automatically:
- Creates `.env` from the template (Supabase credentials are pre-filled)
- Generates `context.xml` with the correct database connection
- Prepares the legacy transformation runtime
- Starts Tomcat on port 9090
- Opens your browser to `http://localhost:9090/iw-portal/`

Login with `__iw_admin__` / `%iwps%`.

> **Note:** The React portal build is tracked in git — no `npm install` or `npm run build` needed after cloning.

---

## When You're Done

Double-click `STOP.bat`

---

## Need to Change Database?

Double-click `CHANGE_DATABASE.bat` to switch between:
- **Supabase** (Cloud Postgres) - Recommended
- **InterWeave Server** (MySQL) - May be blocked externally
- **Offline Mode** - No database needed

Runtime endpoint host is controlled separately in `.env`:
- `TS_MODE=local` (default) keeps flow queries/logs on `localhost`
- `TS_MODE=legacy` points runtime flow/log URLs at the historical InterWeave host

Workspace sync is also automatic:
- startup exports saved profile configuration into IDE-visible workspace mirror files
- startup also compiles generated per-profile engine overlays
- successful logins and wizard saves refresh and compile the current profile
- manual sync is available through `scripts\sync_workspace_profiles.bat`
- manual compile is available through `scripts\compile_workspace_profiles.bat`
- manual regression verification is available through `scripts\verify_profile_compiler.bat`

---

## Troubleshooting

### Nothing happens when I click START.bat
- Right-click → Run as Administrator

### Browser doesn't open
- Wait up to 90 seconds on first start, then go to: http://localhost:9090/iw-business-daemon/IWLogin.jsp

### Can't login
- Username: `__iw_admin__`
- Password: `%iwps%`

### Database connection failed / timed out
- Fresh installs now default to `DB_MODE=supabase`.
- If `START.bat` stops on first run, update `SUPABASE_DB_PASSWORD` in `.env`
  with the shared team password, then re-run it.
- If you need to work offline instead, use `CHANGE_DATABASE.bat` to move to
  **Offline Mode** or change `.env` to `DB_MODE=local`.
- Some networks block IPv6. Supabase direct host is often IPv6-only.
- Use the Supabase **pooler** connection in `.env`:
  - Set `SUPABASE_POOLER_HOST`, `SUPABASE_POOLER_PORT=6543`, `SUPABASE_POOLER_USER` (e.g. `postgres.<project_ref>`).
  - Re-run `START.bat`.

### Need to reset everything
1. Delete the `.env` file
2. Run `START.bat` again

---

## Building / Developer Setup (Windows)

The app runtime ships with a bundled Java runtime in `jre/` (so end-users typically do **not** need to install Java just to run `START.bat`).

However, **building** Java components and running the Windows build scripts requires:
- A **JDK** (recommended: **Temurin JDK 8**) so `javac` is available
- **Apache Maven** so `mvn` is available on your **Windows** `PATH`

Important:
- `build.bat` checks for Maven using `where mvn`, so having Maven only inside WSL/Linux will **not** satisfy the Windows script.

### Install prerequisites via winget (recommended)
Open **Windows Terminal → PowerShell** and run:

```powershell
winget source update

winget install -e --id EclipseAdoptium.Temurin.8.JDK --accept-package-agreements --accept-source-agreements
winget install -e --id Apache.Maven --accept-package-agreements --accept-source-agreements
```

Close/reopen the terminal, then verify:

```powershell
java -version
javac -version
mvn -v
where.exe mvn
```

### Alternative: Chocolatey (choco)
If you prefer Chocolatey:

```powershell
choco install -y temurin8jdk maven
```

### Alternative: Scoop
If you prefer Scoop:

```powershell
scoop bucket add java
scoop install temurin8-jdk maven
```

### Build command
From the repository root, you can build with Maven:

```powershell
mvn -DskipTests package
```

If you need Maven to **skip compiling tests** as well, use:

```powershell
mvn -Dmaven.test.skip=true package
```

## React Portal (iw-portal)

The modern React UI is at `frontends/iw-portal/`. The build output (`web_portal/tomcat/webapps/iw-portal/`) **is tracked in git** — no build step needed after cloning. `START.bat` serves it immediately at `http://localhost:9090/iw-portal/`.

Also available at the public Vercel deployment: **https://iw-portal.vercel.app**

### Local development with hot-reload

```bash
cd frontends/iw-portal
npm install
npm run dev   # Vite dev server on :5173, proxies /iw-business-daemon to Tomcat :9090
```

### Building and deploying locally

```bash
cd frontends/iw-portal
node node_modules/vite/bin/vite.js build
# Outputs to web_portal/tomcat/webapps/iw-portal/ (tracked in git)
# Commit the build output after making production changes:
# git add web_portal/tomcat/webapps/iw-portal/ && git commit -m "build: update portal"
```

> **Note:** `tsc` and `npm run build` may fail if the tools aren't on PATH. Use the node module paths above.

Both React and classic JSP pages share the same Tomcat session — users can switch freely between them.

---

## For Technical Users

Advanced scripts are in the `scripts/` folder:
- `SETUP_DB_Windows.bat` - Manual database setup
- `SETUP_DB_Linux.sh` - Linux/Mac setup
- `start_webportal.bat` - Start web server only
- `start_ide.bat` - Start IDE only
- `stop_webportal.bat` - Stop web server only

Setup/install scripts are in `scripts/setup/`:
- `install_tomcat.bat` / `install_tomcat.ps1` - Tomcat installation
- `setup_tomcat.bat` - Tomcat configuration

---

## Directory Structure

```
IW_Launcher/
├── START.bat              # Start application
├── STOP.bat               # Stop application
├── CHANGE_DATABASE.bat    # Switch database
├── iw_ide.exe             # IDE program
├── .env                   # Your settings (auto-created)
│
├── scripts/               # Advanced scripts
│   ├── setup/             # Install & config scripts
│   └── sql/               # SQL migration scripts
├── database/              # Database schemas & definitions
│   └── schemas/           # XSD schemas
├── docs/                  # Documentation
│   ├── ai/                # AI workflow & worklog
│   ├── README.md          # Docs index and placement rules
│   ├── assa-specs/        # ASSA specification docs
│   ├── development/       # Build, API, contributing guides
│   ├── legacy-pdfs/       # Original PDF documentation
│   ├── security/          # Security & credential docs
│   ├── setup/             # Installation guides
│   ├── testing/           # Test plans
│   └── tutorials/         # Training materials
├── frontends/             # Front-end applications
│   ├── iw-portal/         # React dashboard (active development)
│   ├── InterWoven/        # React SPA (concept/prototype)
│   └── assa/              # ASSA portal prototype
├── jre/                   # Bundled Java 8 runtime
├── plugins/               # Eclipse plugins
├── src/                   # Java source code
├── web_portal/            # Web server (Tomcat)
├── workspace/             # IDE workspace & projects
└── configuration/         # Eclipse configuration
```

---

## Known Issues

### Authentication
- The original `LoginServlet` used a proprietary password hash format incompatible with the database schema.
- **Fixed:** Replaced with `LocalLoginServlet` which uses SHA-256 hex hashing matching PostgreSQL `encode(digest(), 'hex')`.
- **All accounts verified working** as of 2026-02-23 (29/29 E2E tests pass).

### Database Accounts
| Account | Email | Password | Status |
|---------|-------|----------|--------|
| Admin | `__iw_admin__` | `%iwps%` | **VERIFIED** |
| Demo | `demo@sample.com` | `demo123` | **VERIFIED** |

---

## Version History

| Date | Change |
|------|--------|
| 2026-03-04 | **Profile Compiler Deepening** - Added `CRM2QB3` semantic compiler selection, generated `compiler-selection` artifacts, and `Tester1` regression verification (`scripts\\verify_profile_compiler.bat`) |
| 2026-02-24 | **Auth API + Live Dashboard** - ApiLoginServlet/ApiSessionServlet (JSON auth, shared Tomcat session), React AuthProvider with protected routes, monitoring dashboard wired to live /api/monitoring/* endpoints via TanStack Query |
| 2026-02-23 | **IW Portal Scaffold** - React 18 + TypeScript + Tailwind + shadcn/ui, ASSA dark/light theme, sidebar nav, classic view toggle, monitoring + dashboard pages |
| 2026-02-23 | **Monitoring Stack Enabled** - 11 Java files compiled, 4 API endpoints, 5 background services, Postgres schema, Dashboard.jsp |
| 2026-02-23 | **E2E Verified** - 29/29 tests pass, all accounts verified working |
| 2026-02-23 | **Supabase Postgres** - Migrated to Supabase cloud Postgres via connection pooler |
| 2026-02-18 | **Local Servlet Bridge** - All 9 user/company servlets replaced with local SQL implementations |
| 2026-02-13 | **Enterprise Docs** - ADRs, onboarding, security, credential rotation docs |
| 2025-12-10 | **Simplified to 3 scripts** - START, STOP, CHANGE_DATABASE |
| 2025-12-10 | **Auto-setup** - START.bat now handles first-time configuration |
| 2025-12-04 | Initial Windows portability release |

---

*Technical documentation available in `docs/` folder.*
