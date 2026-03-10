# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Mandatory workflow for ALL AI agents (read this first)
All AI tools/agents working in this repo MUST follow `docs/ai/AI_WORKFLOW.md`.
- If you make changes, you MUST append a session entry to `docs/ai/AI_WORKLOG.md`.
- Every response MUST include `What I did (this response)`.
- For tasks involving legacy InterWeave behavior, terminology, user workflows, or vendor mappings, read `docs/ai/INTERWEAVE_PDF_CONTEXT.md` and use the approved PDF corpus listed there as supporting context.
- PDF-derived guidance is additive context only. Do not replace existing repository docs or established behavior unless the user explicitly asks for replacement.

## IMPORTANT: InterWoven concept directory
If a `frontends/InterWoven/` directory exists in this repo, it is a concept/prototype snapshot for potential future IDE launcher + Java form web page improvements.

Do not use, read, or reference application code in `frontends/InterWoven/` unless the user explicitly requests it.

Exception: the mirrored legacy manuals under `frontends/InterWoven/docs/IW_Docs/**` may be consulted when they are explicitly referenced by `docs/ai/INTERWEAVE_PDF_CONTEXT.md` or by the user. Treat those files as historical documentation only, not as the source of truth for the prototype frontend.

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
   - Also deploys `iwtransformationserver` — legacy transformation engine (**operational**: 133 vendor JARs + `iwengine.jar` with 125 engine classes. `/transform` returns real IW XML responses. Engine classes recovered from legacy Tomcat 5.5 install.)
   - Also deploys `iw-portal` — React dashboard (`frontends/iw-portal/` build output)

3. **Database** - Authentication and configuration (MySQL or Postgres)
   - Schemas: `database/postgres_schema.sql` (primary/Supabase), `database/monitoring_schema_postgres.sql` (monitoring), `mysql_schema.sql` (legacy), `schema.sql`
   - Connection via Supabase **pooler** (port 6543, username `postgres.hpodmkchdzwjtlnxjohf`) with RLS on all 14 tables
   - **IMPORTANT (2026-02-26)**: Direct connection (port 5432) is **blocked/unreachable** from this network. Use the **pooler** (port 6543) with `prepareThreshold=0` in the JDBC URL. See `context.xml` for working config.
   - Three connection modes (configured via `.env`):
     - `supabase` - Shared Supabase Postgres (default, verified working via pooler)
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

**⚠️ WARNING (AI agents):** Do NOT set `$env:CATALINA_HOME` / `$env:JRE_HOME` and call Tomcat `.bat` scripts inline from PowerShell — this hangs the terminal. Always use the wrapper scripts above.

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
- Supabase Postgres connectivity via **pooler** (port 6543, `prepareThreshold=0`) — verified from Windows native (2026-02-26)
- Direct connection (port 5432) is **blocked/unreachable** — do not use
- Login, EditProfile save, EditCompanyProfile save — all verified working through pooler
- Must run Tomcat from Windows PowerShell (not WSL2)

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
   - **Source IS available** at `src/main/java/com/interweave/web/ErrorHandlingFilter.java`
   - Full error/validation framework also in `src/main/java/com/interweave/error/` and `src/main/java/com/interweave/validation/`
   - Build: `mvn -DskipTests package` → output at `target/iw-error-framework-1.0.0.jar`
   - Deploy: `cp -r target/classes/com/interweave/ web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes/com/interweave/`
   - Enable: Uncomment ErrorHandlingFilter block in `web.xml`, then restart Tomcat
   - Full steps: `docs/development/BUILD.md` and `docs/NEXT_STEPS.md` item 1

3. **Transformation Server — OPERATIONAL (engine recovered)**
   - `iwtransformationserver` deploys and runs with 133 vendor JARs + `iwengine.jar` (125 engine classes, 15 packages)
   - Engine classes recovered from legacy Tomcat 5.5 install in `InterWoven/docs/IW_Docs/IW_IDE/IW_IDE_Import/`
   - `web.xml` has `metadata-complete="true"` (skips annotation scanning for faster deploy)
   - `/transform`, `/index`, `/iwxml`, `/scheduledtransform`, `/gateway` all return **200** with real IW XML responses
   - `/logging` fixed via `IWLoggingFixed` wrapper servlet — original had NPE when `applicationname`/`loglevel` params missing
   - `interweave-jaxb-compat.jar` (in `tomcat/lib/`) provides JAXB 1.0 classes needed by the engine
   - **Note**: Actual flow execution still requires workspace project files with valid connection credentials and XSLT mappings

4. **Windows-Native Required for Database**
   - **Tomcat MUST run from Windows (PowerShell)**, not WSL2
   - Supabase direct host (`db.*.supabase.co:5432`) is **blocked/unreachable** (connect timeout) — do NOT use
   - Supabase pooler (`aws-0-us-west-2.pooler.supabase.com:6543`) is the **only working endpoint** (verified 2026-02-26)
   - JDBC URL **must** include `prepareThreshold=0` for pooler compatibility (PgBouncer/Supavisor)
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
- **NEVER set `$env:CATALINA_HOME` or `$env:JRE_HOME` in the user's PowerShell session and then call Tomcat bat scripts inline** — this causes the terminal to hang and become unresponsive. Instead, use the project's own scripts: `scripts/start_webportal.bat`, `scripts/stop_webportal.bat`, `START.bat`, `STOP.bat`.

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

- **Stack**: Vite 7 + React 19 + TypeScript (strict) + Tailwind CSS 4 + shadcn/ui + TanStack Query v5 + React Router v7 + Recharts 3
- **Theme**: ASSA dark palette (default) + light mode, toggle in topbar, persisted to localStorage
- **Classic View**: Every React route maps to its JSP equivalent. "Switch to Classic" banner on every page. Users can set "always classic" preference.
- **Hook Page Pattern**: Pages not yet rebuilt in React redirect to the corresponding JSP page. Both apps share Tomcat session cookies (same origin).
- **Dev**: `cd frontends/iw-portal && npm run dev` → Vite on :5173, proxies `/iw-business-daemon` to Tomcat :9090
- **Build**: `npm run build` → outputs to `web_portal/tomcat/webapps/iw-portal/`
- **TypeScript**: strict mode, zero errors required before commit

**Route → Classic JSP mapping**:
- `/login` → `IWLogin.jsp`
- `/register` → `Registration.jsp`
- `/register/company` → `CompanyRegistration.jsp`
- `/dashboard` → `IWLogin.jsp` (post-login landing)
- `/monitoring` → `monitoring/Dashboard.jsp` (charts + transactions + alerts)
- `/profile` → `EditProfile.jsp`
- `/profile/password` → `ChangePassword.jsp`
- `/company` → `EditCompanyProfile.jsp`
- `/company/config` → `CompanyConfiguration.jsp` (progress checklist)
- `/company/config/wizard` → config wizard (5-step: solution type, mappings, credentials, execution settings, review)
- `/admin/configurator` → `BDConfigurator.jsp` (flows, credentials, engine controls)
- `/admin/logging` → `Logging.jsp`

**Key directories**:
- `src/components/layout/` — AppShell, Sidebar, Topbar, ClassicViewBanner
- `src/components/` — ProtectedRoute (auth gate), layout/ (AppShell, Sidebar, Topbar, ClassicViewBanner)
- `src/pages/` — route pages
- `src/providers/` — ThemeProvider, QueryProvider, AuthProvider (session check + login/logout)
- `src/hooks/` — useMonitoring.ts (useDashboard with 30s auto-refresh, useTransactions with pagination), useConfiguration.ts (wizard/credentials/profiles/test), useFlows.ts (flow listing, start/stop, schedule, properties read/write with post-save verification)
- `src/lib/` — api.ts (fetch wrapper with ApiError class), classic-routes.ts, config-labels.ts (shared label formatters), utils.ts
- `src/types/` — TypeScript interfaces for API responses (monitoring.ts, flows.ts, config.ts)
- `src/components/integrations/` — FlowTable, EngineControlsTab, FlowPropertiesDialog, EditScheduleDialog

**API Servlets (JSON, `com.interweave.businessDaemon.api`):**
- **ApiLoginServlet** — `POST /api/auth/login`
- **ApiSessionServlet** — `GET /api/auth/session`
- **ApiProfileServlet** — `GET/PUT /api/profile`
- **ApiCompanyProfileServlet** — `GET/PUT /api/company/profile`
- **ApiRegistrationServlet** — `POST /api/register`
- **ApiCompanyRegistrationServlet** — `POST /api/register/company`
- **ApiChangePasswordServlet** — `POST /api/auth/change-password`
- **ApiConfigurationServlet** — `GET/PUT /api/config/wizard`, `GET/PUT /api/config/credentials`, `GET /api/config/profiles`, `POST /api/config/credentials/test`
- **ApiFlowManagementServlet** — `GET /api/flows` (flow listing), `GET /api/flows/properties` (flow variable parameters), `POST /api/flows/start|stop|submit`, `PUT /api/flows/schedule`
- **ApiLogViewerServlet** — `GET /api/logs/*`

**Compile command (API servlets)**:
```bash
javac -source 1.8 -target 1.8 -cp "web_portal/tomcat/lib/servlet-api.jar;web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes;web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/lib/*;web_portal/tomcat/lib/*" -d web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/businessDaemon/api/*.java
```
Note: On Windows, use `;` as classpath separator (not `:`).

### Maven Source Framework (`src/`)

A Maven project at the repo root (`pom.xml`) provides the error handling, validation, and web filter infrastructure. It is compiled separately from the Tomcat servlet sources.

**Source packages** (`src/main/java/com/interweave/`):
- `error/` — `IWError`, `IWErrorBuilder`, `ErrorCode`, `ErrorCategory`, `ErrorSeverity`, `ErrorLogger`, `ErrorDocumentation`
- `validation/` — `ConnectionValidator`, `FlowConfigValidator`, `SchemaValidator`, `XPathValidator`, `XSLTValidator`, `ValidationResult`, `ValidationIssue`, `ValidationSeverity`, `ValidationService`
- `web/` — `ErrorHandlingFilter` (disabled in `web.xml` — deploy to enable, see Known Issues #2)
- `help/` — `HelpLinkService`

**Tests** (`src/test/java/`): Unit tests for error, validation, and integration scenarios.

**Build and deploy (Windows):**
```powershell
mvn -DskipTests package
# Option A: copy JAR
cp target\iw-error-framework-1.0.0.jar web_portal\tomcat\webapps\iw-business-daemon\WEB-INF\lib\
# Option B: copy classes
cp -r target\classes\com\interweave web_portal\tomcat\webapps\iw-business-daemon\WEB-INF\classes\com\interweave
```

See `docs/development/BUILD.md` for complete Maven build instructions, profiles, and IDE integration.

### Eclipse/IDE Specifics

- Based on Eclipse 3.1 with custom InterWeave SDK plugin
- Plugin location: `plugins/iw_sdk_1.0.0/`
- Configuration: `configuration/org.eclipse.update/`
- Startup JAR: `startup.jar`
- INI config: `iw_ide.ini`
- Main entry point: `com.inerweave.sdk.Designer` (IApplication)
- Central state: `ConfigContext.class` (73KB) — holds transactionList, queryList, profileDescriptors
- Plugin version: 2.41, IDE Build 172, IM Build 765, TS Build 712
- **No headless mode** — GUI-only Eclipse RCP application
- **Source code not available** — 253 compiled classes, see `docs/development/ENGINE_SYNC_MAP.md` for what source would enable

### IDE ↔ Web Portal Sync (2026-03-09)

**Portal → IDE:** WORKING — `WorkspaceProfileSyncServlet` exports DB → workspace files, `WorkspaceProfileCompilerServlet` generates engine overlays. Triggered on startup (START.bat) and login.

**IDE → Portal:** MANUAL ONLY — `WorkspaceProfileSyncServlet?action=importProfile` exists but is never called automatically. Schema mismatch: wizard saves `<SF2QBConfiguration>` (flat), IDE uses `<BusinessDaemonConfiguration>` (complex nested XML).

**Sync bridge IMPLEMENTED & TESTED** — `scripts/sync_bridge.ps1` (polling-based, PS 5.1 compatible) watches workspace for IDE changes and auto-calls importProfile + recompile. Launched automatically by START.bat, stopped by STOP.bat. Standalone: `scripts/start_sync_bridge.bat` / `scripts/stop_sync_bridge.bat`. Logs to `logs/sync_bridge.log`. Live-tested 2026-03-09. Full analysis in `docs/development/ENGINE_SYNC_MAP.md`.

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
│   ├── iw-portal/              # React dashboard (Vite 7 + React 19 + TS + Tailwind 4 + shadcn/ui)
│   ├── InterWoven/             # React SPA (concept/prototype — do not use per CLAUDE.md rules)
│   └── assa/                   # Static HTML design prototypes (design reference for iw-portal)
│       ├── assa_customer_portal/ # 9 pages: billing, intake, library, profile, resource, search...
│       └── assa_master_console/  # 9 pages: analytics, audit, content, integrations, users...
│
├── jre/                        # Bundled Java 8 runtime
├── plugins/                    # Eclipse plugins
│   ├── iw_sdk_1.0.0/           # InterWeave SDK plugin
│   └── org.eclipse.*.jar       # Eclipse core plugins
├── src/                        # Maven project: error framework, validation, ErrorHandlingFilter
│   ├── main/java/com/interweave/error/       # IWError, ErrorCode, ErrorLogger
│   ├── main/java/com/interweave/validation/  # ConnectionValidator, SchemaValidator, etc.
│   ├── main/java/com/interweave/web/         # ErrorHandlingFilter (deploy to enable)
│   └── main/java/com/interweave/help/        # HelpLinkService
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

## Roadmap and Next Steps

See `docs/NEXT_STEPS.md` for the current prioritized development queue:
- **Blocked**: Enable ErrorHandlingFilter (needs Maven install), configure monitoring email (needs SMTP credentials)
- **Medium term**: Code-split ConfigurationWizardPage (React.lazy), sparklines on dashboard KPIs, ViewLog React page
- **Future**: InterWoven features (AI mapping, visual workflow, OAuth broker), MFA, audit log
