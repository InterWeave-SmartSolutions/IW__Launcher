# InterWeave IDE (IW_IDE)

Enterprise data integration platform for creating synchronization workflows between business applications.

---

## How to Use (3 Simple Scripts)

| Script | What It Does |
|--------|--------------|
| `START.bat` | **Start the application** - Double-click this! |
| `STOP.bat` | **Stop the application** |
| `CHANGE_DATABASE.bat` | Change database connection (optional) |

That's it! Just double-click `START.bat` to begin.

---

## First Time?

1. Double-click `START.bat`
2. Wait for browser to open
3. Login with:
   - **Username:** `__iw_admin__`
   - **Password:** `%iwps%`

The first time you run `START.bat`, it automatically configures everything for you.

---

## When You're Done

Either:
- Press any key in the black window to stop, OR
- Double-click `STOP.bat`

---

## Need to Change Database?

Double-click `CHANGE_DATABASE.bat` to switch between:
- **Supabase** (Cloud Postgres) - Recommended
- **InterWeave Server** (MySQL) - May be blocked externally
- **Offline Mode** - No database needed

---

## Troubleshooting

### Nothing happens when I click START.bat
- Right-click → Run as Administrator

### Browser doesn't open
- Wait 30 seconds, then go to: http://localhost:8080/iw-business-daemon/IWLogin.jsp

### Can't login
- Username: `__iw_admin__`
- Password: `%iwps%`

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
