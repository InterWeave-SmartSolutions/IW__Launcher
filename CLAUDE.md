# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Mandatory workflow for ALL AI agents (read this first)
All AI tools/agents working in this repo MUST follow `docs/ai/AI_WORKFLOW.md`.
- If you make changes, you MUST append a session entry to `docs/ai/AI_WORKLOG.md`.
- Every response MUST include `What I did (this response)`.

## IMPORTANT: InterWoven concept directory
If a `frontends/InterWoven/` directory exists in this repo, it is a concept/prototype snapshot for potential future IDE launcher + Java form web page improvements.

Do not use, read, or reference anything in `frontends/InterWoven/` unless the user explicitly requests it.

---

## Project Overview

**InterWeave IDE (IW_IDE)** is an enterprise data integration platform built on Eclipse that creates synchronization workflows between business applications. It's a Java-based IDE that enables building integration solutions connecting various APIs (SOAP, REST/JSON) through an internal XML transformation format.

## Key Architecture

### Three-Tier Integration System

1. **IDE (`iw_ide.exe`)** - Eclipse-based development environment
   - Located in root directory
   - Requires bundled JRE at `jre/`
   - Eclipse 3.1 based with custom `iw_sdk_1.0.0` plugin
   - Workspace projects stored in `workspace/`

2. **Web Portal (`web_portal/tomcat/`)** - Apache Tomcat 9.0.83 server
   - Deploys `iw-business-daemon.war` (deployed as expanded directory in `webapps/`)
   - User authentication and company management
   - Hosts JSP interfaces for profile/config management
   - Default port: 9090

3. **Database** - Authentication and configuration (MySQL or Postgres)
   - Schemas: `database/postgres_schema.sql` (primary/Supabase), `database/monitoring_schema_postgres.sql` (monitoring), `mysql_schema.sql` (legacy), `schema.sql`
   - Connection via Supabase direct (port 5432, username `postgres`) with RLS on all 14 tables
   - **IMPORTANT**: Supabase pooler (`pooler.supabase.com:6543`) returns "Tenant or user not found" — use direct connection only
   - Three connection modes (configured via `.env`):
     - `supabase` - Shared Supabase Postgres (default, verified working)
     - `interweave` - InterWeave hosted MySQL (***********)
     - `local` - Offline mode (admin only)

### Integration Flow Architecture

All integration flows follow a decoupling pattern:
```
Source API → IW XML Format → Transformation (XSLT) → Destination API
```

**Flow Types:**
- **Transaction Flows** - Scheduled backend processes
- **Utility Flows** - On-demand flows
- **Queries** - Pseudo-REST API callable via URL (for Salesforce/Creatio buttons)

Projects are stored in `workspace/` and contain:
- Configuration
- Transactions
- Connections (to external systems)
- XSLT transformers
- Integration flows (transaction flows, utility flows, queries)

## Running the Application

### Start Everything (First Time)
```bash
./START.bat  # Windows only - auto-configures .env on first run
```

This:
1. Copies `.env.example` to `.env` if not exists
2. Starts Tomcat web server
3. Launches Eclipse IDE
4. Opens browser to login page

**Login Credentials:**
- Username: `__iw_admin__`
- Password: `%iwps%`

### Start Individual Components
```bash
# Web portal only
./scripts/start_webportal.bat

# IDE only
./scripts/start_ide.bat

# Stop web portal
./scripts/stop_webportal.bat

# Full stop
./STOP.bat
```

### Change Database Connection
```bash
./CHANGE_DATABASE.bat
```
Interactive menu to switch between Supabase, InterWeave server, or offline mode.

## Database Configuration

Database settings are in `.env` (auto-created from `.env.example`):

```bash
# Set DB_MODE to: supabase | interweave | local
DB_MODE=supabase

# Supabase Postgres (default)
SUPABASE_DB_HOST=db.hpodmkchdzwjtlnxjohf.supabase.co
SUPABASE_DB_PORT=5432
SUPABASE_DB_NAME=postgres
SUPABASE_DB_USER=postgres
SUPABASE_DB_PASSWORD={{SUPABASE_DB_PASSWORD}}

# InterWeave Server (alternative)
IW_DB_HOST=148.62.63.8
IW_DB_PORT=3306
IW_DB_NAME=hostedprofiles
```

### Database Schema Structure

Key tables (see `database/mysql_schema.sql`):
- `companies` - Organization profiles with license management
- `users` - User accounts linked to companies
- `user_profiles` - Extended user information
- Authentication uses email + SHA-256 hex password hash (via `LocalLoginServlet`, matches MySQL `SHA2()`)

**Database Setup:**
```bash
# Windows
./scripts/SETUP_DB_Windows.bat

# Linux/Mac
./scripts/SETUP_DB_Linux.sh
```

## Web Portal Access

Base URL: `http://localhost:9090/iw-business-daemon/`

Key pages:
- `/IWLogin.jsp` - Login page
- `/EditProfile.jsp` - User profile editor
- `/EditCompanyProfile.jsp` - Company settings
- `/CompanyConfiguration.jsp` - Company config
- `/BDConfigurator.jsp` - Business daemon config
- `/Registration.jsp` - User registration
- `/CompanyRegistration.jsp` - Company registration
- `/monitoring/Dashboard.jsp` - Monitoring dashboard (requires login)

**Change Tomcat Port:**
Edit `web_portal/tomcat/conf/server.xml`:
```xml
<Connector port="9090" ... />
```

**Logs:** `web_portal/tomcat/logs/`
- `catalina.out` - Main server log
- `localhost.*.log` - Application logs

## Development Notes

### Verified (2026-02-24)

- **29/29 E2E tests pass** (`web_portal/test_portal.sh`) — pages, registration, login, profiles, password changes, input validation
- Admin login (`__iw_admin__` / `%iwps%`) — verified
- Demo user login (`demo@sample.com` / `demo123`) — verified
- Company registration + full config workflow — verified
- Supabase Postgres connectivity via direct connection (port 5432) — verified from Windows native
- **WSL2 cannot reach Supabase direct host** (IPv6/network unreachable) — must run Tomcat from Windows PowerShell

### Known Issues

1. **Monitoring System (ENABLED)**
   - 11 Java files compiled and deployed: 5 API servlets + 6 services (incl. MonitoringContextListener)
   - All services start on Tomcat boot: MetricsAggregator, AlertService, EmailNotificationService, WebhookNotificationService
   - API endpoints: `/api/monitoring/dashboard`, `/api/monitoring/transactions/*`, `/api/monitoring/metrics`, `/api/monitoring/alerts/*`, `/api/monitoring/webhooks/*`
   - Auth API endpoints: `POST /api/auth/login`, `GET /api/auth/session` (JSON, shared Tomcat session)
   - Dashboard: `/monitoring/Dashboard.jsp` (requires session)
   - Schema: `database/monitoring_schema_postgres.sql` (6 tables, 3 views, indexes, triggers, RLS)
   - Email config: copy `monitoring.properties.template` → `monitoring.properties`, fill in SMTP credentials
   - **Phase 1B deferred**: TransactionLogger instrumentation (needs engine class decompilation), email delivery testing (needs SMTP credentials)

2. **ErrorHandlingFilter Disabled**
   - Requires compiled error framework web filter class
   - Commented out in `web.xml`

3. **Windows-Native Required for Database**
   - **Tomcat MUST run from Windows (PowerShell)**, not WSL2
   - WSL2 cannot reach Supabase direct host (`db.*.supabase.co:5432`) due to IPv6/networking limitations
   - Supabase pooler (`pooler.supabase.com:6543`) rejects with "Tenant or user not found"
   - Primary scripts are `.bat` files for Windows
   - Linux/Mac scripts available in `scripts/` but less maintained
   - Shell scripts have CRLF issues on Linux; use direct Tomcat `bin/` invocation

### Local Servlet Bridge (User/Company Management)

The original compiled servlets depend on the `iwtransformationserver` webapp (not deployed). All 9 user/company management servlets have been replaced with local SQL-based implementations that query Supabase Postgres directly.

- **Source**: `WEB-INF/src/com/interweave/businessDaemon/config/Local*.java`
- **Base class**: `LocalUserManagementServlet` — DataSource init, SHA-256 hashing, reflection helper
- **ADR**: `docs/adr/003-local-servlet-bridge.md`
- **Full reference**: `docs/development/LOCAL_SERVLETS.md`

Servlets: `LocalLoginServlet`, `LocalRegistrationServlet`, `LocalCompanyRegistrationServlet`, `LocalChangePasswordServlet`, `LocalChangeCompanyPasswordServlet`, `LocalEditProfileServlet`, `LocalSaveProfileServlet`, `LocalEditCompanyProfileServlet`, `LocalSaveCompanyProfileServlet`

**Key gotchas for AI agents**:
- `TransactionThread` fields (`firstName`, `lastName`, `company`, `title`) have getters but NO setters — must use `setThreadField()` reflection
- JSP forms send `CompanyOrganization` (not `Company`) and `Type` (not `SolutionType`)
- `ConfigContext.setHosted(true)` + `setAdminLoggedIn(true)` required before `CompanyConfiguration.jsp`
- To revert to originals: change `web.xml` servlet-class entries back, restart Tomcat

**Compile command (Local servlets)**:
```bash
javac -source 1.8 -target 1.8 -cp "web_portal/tomcat/lib/servlet-api.jar:web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes:web_portal/tomcat/lib/*" -d web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/businessDaemon/config/Local*.java
```

**Compile command (Monitoring)**:
```bash
javac -source 1.8 -target 1.8 -cp "web_portal/tomcat/lib/servlet-api.jar:web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes:web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/lib/*:web_portal/tomcat/lib/*" -d web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/monitoring/service/*.java web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/monitoring/api/*.java
```



### Auth API Servlets (JSON endpoints for React IW Portal)

Two servlets in `com.interweave.businessDaemon.api` provide JSON authentication for the React frontend while sharing the same Tomcat session as the classic JSP login.

- **ApiLoginServlet** — `POST /api/auth/login` — accepts `{"email","password"}`, runs same DB auth as LocalLoginServlet, sets identical session attributes, returns `{"success":true,"user":{...}}` or `{"success":false,"error":"..."}`
- **ApiSessionServlet** — `GET /api/auth/session` — reads session attributes, returns `{"authenticated":true,"user":{...}}` or `{"authenticated":false}`
- **Source**: `WEB-INF/src/com/interweave/businessDaemon/api/Api*.java`
- **Session sharing**: Login via ApiLoginServlet sets the same session attributes as LocalLoginServlet, so users authenticated via React can use classic JSP pages and vice versa.

**Compile command (Auth API)**:
```bash
javac -source 1.8 -target 1.8 -cp "web_portal/tomcat/lib/servlet-api.jar:web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes:web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/lib/*:web_portal/tomcat/lib/*" -d web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/businessDaemon/api/*.java
```

**Test credentials**: `demo@sample.com` / `demo123` (user), `admin@sample.com` / `admin123` (admin)

### IW Portal (Modern React UI)

New React-based portal at `frontends/iw-portal/` — replaces JSP pages incrementally.

- **Stack**: Vite + React 18 + TypeScript (strict) + Tailwind CSS + shadcn/ui + TanStack Query + React Router v7 + Recharts
- **Theme**: ASSA dark palette (default) + light mode, toggle in topbar, persisted to localStorage
- **Classic View**: Every React route maps to its JSP equivalent. "Switch to Classic" banner on every page. Users can set "always classic" preference.
- **Hook Page Pattern**: Pages not yet rebuilt in React redirect to the corresponding JSP page. Both apps share Tomcat session cookies (same origin).
- **Dev**: `cd frontends/iw-portal && npm run dev` → Vite on :5173, proxies `/iw-business-daemon` to Tomcat :9090
- **Build**: `npm run build` → outputs to `web_portal/tomcat/webapps/iw-portal/`
- **TypeScript**: strict mode, zero errors required before commit

**Route → Classic JSP mapping**:
- `/dashboard` → `IWLogin.jsp` (post-login landing)
- `/monitoring` → `monitoring/Dashboard.jsp`
- `/profile` → `EditProfile.jsp`
- `/company` → `EditCompanyProfile.jsp`
- `/company/config` → `CompanyConfiguration.jsp`
- `/admin/configurator` → `BDConfigurator.jsp`
- `/admin/logging` → `Logging.jsp`

**Key directories**:
- `src/components/layout/` — AppShell, Sidebar, Topbar, ClassicViewBanner
- `src/components/` — ProtectedRoute (auth gate), layout/ (AppShell, Sidebar, Topbar, ClassicViewBanner)
- `src/pages/` — route pages
- `src/providers/` — ThemeProvider, QueryProvider, AuthProvider (session check + login/logout)
- `src/hooks/` — useMonitoring.ts (useDashboard with 30s auto-refresh, useTransactions with pagination)
- `src/lib/` — api.ts (fetch wrapper), classic-routes.ts, utils.ts
- `src/types/` — TypeScript interfaces for API responses
### Eclipse/IDE Specifics

- Based on Eclipse 3.1 with custom InterWeave SDK plugin
- Plugin location: `plugins/iw_sdk_1.0.0/`
- Configuration: `configuration/org.eclipse.update/`
- Startup JAR: `startup.jar`
- INI config: `iw_ide.ini`

### Integration Projects

Example workspace projects:
- `Creatio_QuickBooks_Integration` - Creatio to QuickBooks flows
- Sample projects copy older versions (not latest)

Common integration patterns documented in `docs/tutorials/`:
- `InterWeave-IDE-Training-1.md` - IDE basics
- `InterWeave-IDE-Training-2.md`
- `InterWeave-IDE-Training-3.md`
- `InterWeave-IDE-Review-4.md`

### Building from Source

For developers modifying Java code, see `docs/development/BUILD.md` for:
- Maven build configuration
- Compilation commands
- Test execution
- Deployment to Tomcat
- IDE integration (Eclipse, IntelliJ, VS Code)

## Directory Structure

```
IW_Launcher/
├── START.bat                   # Main startup (auto-configures)
├── STOP.bat                    # Shutdown script
├── CHANGE_DATABASE.bat         # Database connection switcher
├── iw_ide.exe                  # Eclipse IDE executable
├── iw_ide.ini                  # IDE config
├── startup.jar                 # Eclipse startup
├── .env                        # Database config (auto-created)
├── .env.example                # Template for .env
│
├── scripts/                    # Advanced scripts
│   ├── setup/                  # Install & config scripts
│   └── sql/                    # SQL migration scripts
│
├── database/                   # Database schemas
│   ├── mysql_schema.sql        # Primary MySQL schema
│   ├── postgres_schema.sql     # PostgreSQL alternative
│   ├── schema.sql              # Original schema
│   └── schemas/                # XSD schemas
│
├── docs/                       # Documentation
│   ├── ai/                     # AI workflow & worklog
│   ├── assa-specs/             # ASSA specification docs
│   ├── development/            # Build, API, contributing guides
│   ├── legacy-pdfs/            # Original PDF documentation
│   ├── security/               # Security & credential docs
│   ├── setup/                  # Installation guides
│   ├── testing/                # Test plans
│   └── tutorials/              # Training materials
│
├── frontends/                  # Front-end applications
│   ├── InterWoven/             # React SPA (concept/prototype)
│   └── assa/                   # ASSA portal prototype
│
├── jre/                        # Bundled Java 8 runtime
├── plugins/                    # Eclipse plugins
│   ├── iw_sdk_1.0.0/           # InterWeave SDK plugin
│   └── org.eclipse.*.jar       # Eclipse core plugins
├── src/                        # Java source code
│
├── web_portal/                 # Web server
│   ├── tomcat/                 # Apache Tomcat 9.0.83
│   │   ├── bin/                # Tomcat binaries
│   │   ├── conf/               # server.xml, web.xml
│   │   ├── logs/               # Server logs
│   │   └── webapps/            # Deployed apps
│   │       └── iw-business-daemon/
│   ├── start_web_portal.bat    # Windows start
│   ├── stop_web_portal.bat     # Windows stop
│   └── README.md               # Web portal docs
│
├── workspace/                  # IDE workspace
│   ├── .metadata/              # Eclipse metadata
│   ├── Creatio_QuickBooks_Integration/
│   └── FirstTest/
│
└── configuration/              # Eclipse configuration
    └── org.eclipse.update/     # Update manager config
```

## Environment Requirements

**Windows (Primary):**
- Bundled JRE included at `jre/` (Java 8)
- No additional dependencies

**Linux/Mac (Secondary):**
- Java 8+ required (`java` in PATH or `JAVA_HOME` set)
- Scripts available in `scripts/`

**WSL2:**
- Can browse/edit files at `/mnt/c/IW__Launcher/` but **cannot run Tomcat** (Supabase unreachable from WSL2 networking)
- Use WSL2 for code editing, git operations, and file management only
- **Run Tomcat from Windows PowerShell**: `C:\IW__Launcher\web_portal\tomcat\bin\startup.bat`

**Git LFS Requirement (Developers):**
- If cloning this repo, you MUST have Git LFS installed
- Many binary files (`*.exe`, `*.jar`) are stored via Git LFS
- Without LFS, you'll get tiny placeholder files instead of real binaries
- After cloning, run: `git lfs install && git lfs pull`
- Sanity check: Verify `jre/bin/java.exe` and `web_portal/tomcat/lib/catalina.jar` are NOT tiny text files

## Security Notes

- `.env` contains production database credentials (excluded from git)
- Never commit `.env` file
- Supabase Postgres credentials are shared across all team members
- Admin password `%iwps%` is hardcoded in authentication system
