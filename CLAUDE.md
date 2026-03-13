# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Mandatory workflow for ALL AI agents (read this first)
All AI tools/agents working in this repo MUST follow `docs/ai/AI_WORKFLOW.md`.
- If you make changes, you MUST append a session entry to `docs/ai/AI_WORKLOG.md`.
- Every response MUST include `What I did (this response)`.
- For tasks involving legacy InterWeave behavior, terminology, user workflows, or vendor mappings, read `docs/ai/INTERWEAVE_PDF_CONTEXT.md` and use the approved PDF corpus listed there as supporting context.
- PDF-derived guidance is additive context only. Do not replace existing repository docs or established behavior unless the user explicitly asks for replacement.
- **Model selection**: When spawning subagents, use the cheapest model that fits the task (haiku for searches/reads, sonnet for standard edits, opus for complex architecture/security). See `docs/ai/AI_WORKFLOW.md` §6 for the full decision matrix.

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

**Engine Flow Lifecycle:**
```
WEB-INF/config.xml (all flow definitions) → ConfigContext at Tomcat startup
  → bindHostedProfile() at login (or POST /api/flows/initialize)
  → Creates TransactionThread per profile per flow
  → POST /api/flows/start|stop to run/halt individual flows
```

**Per-company flow isolation:** `ApiFlowManagementServlet` reads `solutionType` from session, maps it to a workspace project via `config/workspace-profile-map.properties`, parses that project's `im/config.xml` for allowed flow IDs, and filters ConfigContext output. Each user only sees their company's flows.

**Adding a new integration project requires:**
1. Create workspace project with `im/config.xml` (TransactionDescription + Query elements)
2. Add flows to `WEB-INF/config.xml` (engine won't load flows not defined here)
3. Add `SOLUTION_TYPE=ProjectName` mapping to `config/workspace-profile-map.properties`
4. Add company record in DB with matching `solution_type`
5. Restart Tomcat (config.xml is loaded once at startup)

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

### Fresh Clone — What Is and Isn't in Git

**In git (works immediately after clone):**
- `iw_ide.exe`, `startup.jar`, `plugins/`
- `web_portal/tomcat/webapps/iw-business-daemon/` — all compiled Java servlets + JSPs
- `web_portal/tomcat/webapps/iwtransformationserver/` — transformation engine + vendor JARs
- `web_portal/tomcat/webapps/iw-portal/` — React portal build output (tracked as of 2026-03-11)
- `web_portal/tomcat/conf/*.xml` templates + JDBC drivers
- `.env.example` with team Supabase credentials pre-filled

**NOT in git (must be installed once):**
- `jre/` — 90 MB JRE not in git. Users must download Eclipse Adoptium JRE 8 x86 (32-bit) and extract to `jre/`. START.bat checks for this and shows a clear error.
- `web_portal/tomcat/bin/` + `lib/` — Tomcat binaries. Run `scripts\setup\install_tomcat.bat` once. START.bat checks for this and shows a clear error pointing to the script.

**AI agent instructions when modifying the React portal:**
After any production build (`node node_modules/vite/bin/vite.js build`), commit the output:
```bash
git add web_portal/tomcat/webapps/iw-portal/
git commit -m "build: update portal"
```
This keeps the build in sync for all users who pull.

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
- Authentication uses email + bcrypt password hash (via `PasswordHasher` utility, jBCrypt 0.4). Progressive migration from SHA-256/plaintext → bcrypt on login.

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
- **29/29 session/routing E2E tests pass** (`frontends/iw-portal/tests/e2e_session_and_routing.py`) — security headers, SPA routes, login/logout, cross-UI session leak, route guards, page refresh auth (2026-03-13)
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
   - Auth API endpoints: `POST /api/auth/login`, `GET /api/auth/session`, `POST /api/auth/logout` (JSON, shared Tomcat session)
   - Dashboard: `/monitoring/Dashboard.jsp` (requires session)
   - Schema: `database/monitoring_schema_postgres.sql` (6 tables, 3 views, indexes, triggers, RLS)
   - Email config: copy `monitoring.properties.template` → `monitoring.properties`, fill in SMTP credentials
   - **Phase 1B deferred**: TransactionLogger instrumentation (needs engine class decompilation), email delivery testing (needs SMTP credentials)

2. **ErrorHandlingFilter — ACTIVE**
   - Filter is deployed and enabled in `web.xml` (lines 35-50, mapped to `/*`)
   - Source: `src/main/java/com/interweave/web/ErrorHandlingFilter.java` (300 lines)
   - Compiled class + 9 error framework classes + 4 HelpLinkService classes deployed to `WEB-INF/classes/com/interweave/`
   - Handles exceptions for both API (JSON 500) and browser (forwards to `ErrorMessage.jsp`) requests
   - Error classification maps to `ErrorCode` enums (DB001, SYSTEM001, SYSTEM005, XPATH004, CONFIG001, SYSTEM003)

3. **Transformation Server — OPERATIONAL (engine recovered)**
   - `iwtransformationserver` deploys and runs with 133 vendor JARs + `iwengine.jar` (125 engine classes, 15 packages)
   - Engine classes recovered from legacy Tomcat 5.5 install in `InterWoven/docs/IW_Docs/IW_IDE/IW_IDE_Import/`
   - `web.xml` has `metadata-complete="true"` (skips annotation scanning for faster deploy)
   - `/transform`, `/index`, `/iwxml`, `/scheduledtransform`, `/gateway` all return **200** with real IW XML responses
   - `/logging` fixed via `IWLoggingFixed` wrapper servlet — original had NPE when `applicationname`/`loglevel` params missing
   - `interweave-jaxb-compat.jar` (in `tomcat/lib/`) provides JAXB 1.0 classes needed by the engine
   - **Note**: Actual flow execution still requires workspace project files with valid connection credentials and XSLT mappings

4. **Content Security Policy — STRICT (iw-business-daemon)**
   - `SecurityHeadersFilter` (in `web.xml`, mapped to `/*`) sets strict CSP on all responses
   - `script-src 'self' https://cdn.jsdelivr.net` — **no `'unsafe-inline'`** — all inline scripts extracted to external `.js` files
   - `style-src 'self' 'unsafe-inline'` — still needed for legacy JSP inline `<style>` blocks
   - CDN allowlist: `cdn.jsdelivr.net` for Chart.js on `monitoring/Dashboard.jsp`
   - **Pattern for new JSPs**: Do NOT use inline `<script>` or `onclick`/`onload` attributes. Instead:
     - Pass server data via `data-*` attributes on a hidden `<div>`
     - Read data in an external `.js` file: `el.getAttribute('data-...')`
     - Use `addEventListener` / event delegation instead of inline handlers
   - Source: `WEB-INF/src/com/interweave/web/SecurityHeadersFilter.java`
   - Compile: `javac -source 1.8 -target 1.8 -cp "web_portal/tomcat/lib/servlet-api.jar" -d web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/web/SecurityHeadersFilter.java`

5. **Windows-Native Required for Database**
   - **Tomcat MUST run from Windows (PowerShell)**, not WSL2
   - Supabase direct host (`db.*.supabase.co:5432`) is **blocked/unreachable** (connect timeout) — do NOT use
   - Supabase pooler (`aws-0-us-west-2.pooler.supabase.com:6543`) is the **only working endpoint** (verified 2026-02-26)
   - JDBC URL **must** include `prepareThreshold=0` for pooler compatibility (PgBouncer/Supavisor)
   - Primary scripts are `.bat` files for Windows
   - Linux/Mac scripts available in `scripts/` but less maintained
   - Shell scripts have CRLF issues on Linux; use direct Tomcat `bin/` invocation

### Local Servlet Bridge (User/Company Management)

The original compiled servlets depend on the `iwtransformationserver` webapp (not deployed) or have critical bugs. All 10 user/company management servlets have been replaced with local SQL-based implementations that query Supabase Postgres directly.

- **Source**: `WEB-INF/src/com/interweave/businessDaemon/config/Local*.java`
- **Base class**: `LocalUserManagementServlet` — DataSource init, bcrypt hashing (via `PasswordHasher`), reflection helper
- **ADR**: `docs/adr/003-local-servlet-bridge.md`
- **Full reference**: `docs/development/LOCAL_SERVLETS.md`

Servlets: `LocalLoginServlet`, `LocalLogoutServlet`, `LocalRegistrationServlet`, `LocalCompanyRegistrationServlet`, `LocalChangePasswordServlet`, `LocalChangeCompanyPasswordServlet`, `LocalEditProfileServlet`, `LocalSaveProfileServlet`, `LocalEditCompanyProfileServlet`, `LocalSaveCompanyProfileServlet`

**LocalLogoutServlet** — replaces the original compiled `LogoutServlet` which did NOT call `session.invalidate()`. The local version properly invalidates the Tomcat session AND clears all Bearer tokens for the user from `ApiTokenStore` (prevents cross-UI session leak). Mapped to `/LogoutServlet` in `web.xml`.

**Key gotchas for AI agents**:
- `TransactionThread` fields (`firstName`, `lastName`, `company`, `title`) have getters but NO setters — must use `setThreadField()` reflection
- JSP forms send `CompanyOrganization` (not `Company`) and `Type` (not `SolutionType`)
- `ConfigContext.setHosted(true)` + `setAdminLoggedIn(true)` required before `CompanyConfiguration.jsp`
- To revert to originals: change `web.xml` servlet-class entries back, restart Tomcat
- **NEVER set `$env:CATALINA_HOME` or `$env:JRE_HOME` in the user's PowerShell session and then call Tomcat bat scripts inline** — this causes the terminal to hang and become unresponsive. Instead, use the project's own scripts: `scripts/start_webportal.bat`, `scripts/stop_webportal.bat`, `START.bat`, `STOP.bat`.

**Compile command (Local servlets)**:
```bash
javac --release 8 -cp "web_portal/tomcat/lib/servlet-api.jar:web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes:web_portal/tomcat/lib/*" -d web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/businessDaemon/config/Local*.java
```

**Compile command (Monitoring)**:
```bash
javac --release 8 -cp "web_portal/tomcat/lib/servlet-api.jar:web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes:web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/lib/*:web_portal/tomcat/lib/*" -d web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/monitoring/service/*.java web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/monitoring/api/*.java
```



### Auth API Servlets (JSON endpoints for React IW Portal)

Three servlets in `com.interweave.businessDaemon.api` provide JSON authentication for the React frontend while sharing the same Tomcat session as the classic JSP login.

- **ApiLoginServlet** — `POST /api/auth/login` — accepts `{"email","password"}`, runs same DB auth as LocalLoginServlet, sets identical session attributes, returns `{"success":true,"user":{...}}` or `{"success":false,"error":"..."}`
- **ApiSessionServlet** — `GET /api/auth/session` — reads session attributes, returns `{"authenticated":true,"user":{...}}` or `{"authenticated":false}`
- **ApiLogoutServlet** — `POST /api/auth/logout` — invalidates the Tomcat session AND clears all Bearer tokens for the user from `ApiTokenStore`. Returns `{"success":true}`. Used by the React UI logout flow.
- **Source**: `WEB-INF/src/com/interweave/businessDaemon/api/Api*.java`
- **Session sharing**: Login via ApiLoginServlet sets the same session attributes as LocalLoginServlet, so users authenticated via React can use classic JSP pages and vice versa.
- **Logout token clearing**: Both `ApiLogoutServlet` and `LocalLogoutServlet` call `ApiTokenStore.removeTokensByAttribute("userEmail", email)` to clear all Bearer tokens for the user, preventing cross-UI session persistence bugs.

**Compile command (Auth API)**:
```bash
javac -source 1.8 -target 1.8 -cp "web_portal/tomcat/lib/servlet-api.jar:web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes:web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/lib/*:web_portal/tomcat/lib/*" -d web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/businessDaemon/api/*.java
```

**Test credentials**: `demo@sample.com` / `demo123` (user), `admin@sample.com` / `admin123` (admin)

### XSLT Transformer Build Pipeline

Each workspace project contains XSLT transformer files that define field mappings between source and destination systems. These are compiled to Java bytecode using Apache XSLTC.

**Transformer file structure** (per workspace project):
```
workspace/<Project>/
├── xslt/
│   ├── SyncAccounts_CRM2MG.xslt     ← individual transformer source
│   ├── GetMagentoOrder.xslt          ← query transformer source
│   ├── include/dataconnections.xslt  ← connection credentials (XSLT params)
│   └── Site/
│       ├── include/
│       │   ├── sitetran.xslt         ← shared site transactions (index, session)
│       │   └── appconstants.xslt     ← session variables
│       └── new/
│           ├── transactions.xslt     ← master stylesheet (imports all above)
│           ├── include/soltran.xslt  ← solution-specific flow definitions
│           └── xml/transactions.xml  ← static build output (populates IDE views)
└── classes/iwtransformationserver/
    ├── SyncAccounts_CRM2MG.class     ← compiled transformer bytecode
    └── GetMagentoOrder.class
```

**Compile command (XSLT → .class)**:
```bash
java -cp "web_portal/tomcat/webapps/iwtransformationserver/WEB-INF/lib/xsltc.jar;web_portal/tomcat/webapps/iwtransformationserver/WEB-INF/lib/xalan.jar;web_portal/tomcat/webapps/iwtransformationserver/WEB-INF/lib/serializer.jar" org.apache.xalan.xsltc.cmdline.Compile -o <TransformName> -d workspace/<Project>/classes/iwtransformationserver workspace/<Project>/xslt/<TransformName>.xslt
```

**Compile all transformers for a project**:
```bash
for xslt in workspace/<Project>/xslt/*.xslt; do
  name=$(basename "$xslt" .xslt)
  java -cp "web_portal/tomcat/webapps/iwtransformationserver/WEB-INF/lib/xsltc.jar;web_portal/tomcat/webapps/iwtransformationserver/WEB-INF/lib/xalan.jar;web_portal/tomcat/webapps/iwtransformationserver/WEB-INF/lib/serializer.jar" org.apache.xalan.xsltc.cmdline.Compile -o "$name" -d workspace/<Project>/classes/iwtransformationserver "$xslt"
done
```

**Current transformer inventory**:
| Project | XSLT Sources | Compiled Classes | Adapter Types |
|---------|-------------|-----------------|---------------|
| SF2AuthNet | 142 | 472 | SOAP, HTTP, SQL |
| Creatio_Magento2_Integration | 11 | 11 | REST/JSON |
| Creatio_QuickBooks_Integration | _(soltran.xslt defined, individual transformers pending)_ | — | REST/JSON, HTTP |

**WorkspaceProfileCompiler** now copies transformer files (`.xslt` sources + `.class` bytecode) from template projects into `GeneratedProfiles/` during profile compilation, ensuring generated profiles are self-contained.

### IW Portal (Modern React UI)

New React-based portal at `frontends/iw-portal/` — replaces JSP pages incrementally.

- **Stack**: Vite 7 + React 19 + TypeScript (strict) + Tailwind CSS 4 + shadcn/ui + TanStack Query v5 + React Router v7 + Recharts 3
- **Theme**: ASSA dark palette (default) + light mode, toggle in topbar, persisted to localStorage
- **Accessibility (WCAG 2.2 AA)**: Skip nav, route announcer, ARIA combobox search, focus traps on mobile sidebar, aria-live toast notifications, label bindings, `prefers-reduced-motion` support. Color contrast verified: `--primary` 5:1 on white (light), `--muted-foreground` 4.7:1 on dark bg. All Button/Input/Select components have `focus-visible:ring-2`.
- **Security Headers**: `iw-portal/WEB-INF/web.xml` has Tomcat `HttpHeaderSecurityFilter` (X-Frame-Options: DENY, X-Content-Type-Options: nosniff, X-XSS-Protection). HSTS disabled until HTTPS.
- **Classic View**: Every React route maps to its JSP equivalent. "Switch to Classic" banner on every page. Users can set "always classic" preference.
- **Hook Page Pattern**: Pages not yet rebuilt in React redirect to the corresponding JSP page. Both apps share Tomcat session cookies (same origin).
- **Dev**: `cd frontends/iw-portal && npm run dev` → Vite on :5173, proxies `/iw-business-daemon` to Tomcat :9090
- **Build**: `node node_modules/vite/bin/vite.js build` → outputs to `web_portal/tomcat/webapps/iw-portal/`
  - **IMPORTANT**: The build output at `web_portal/tomcat/webapps/iw-portal/` IS tracked in git. After any production build, commit it: `git add web_portal/tomcat/webapps/iw-portal/ && git commit`
  - Fresh clones get the React portal working immediately (no npm install needed for users)
  - `npm` / `tsc` / `vite` are not on PATH — use `node node_modules/...` paths instead
- **TypeScript**: strict mode, zero errors required before commit (`node node_modules/typescript/bin/tsc -b --noEmit`)

**SPA route mappings** in `iw-portal/WEB-INF/web.xml` — all 41 routes mapped via `spa-fallback` servlet (returns HTTP 200 with `index.html`): `/login`, `/dashboard`, `/monitoring/*`, `/profile/*`, `/company/*`, `/admin/*`, `/register`, `/register/*`, `/forgot-password`, `/mfa/*`, `/notifications`, `/associate/*`, `/master/*`. Unknown routes fall through to 404→`index.html` error-page.

**Route → Classic JSP mapping** (22 operator routes + 9 associate + 10 master):
- `/login` → `IWLogin.jsp`
- `/register` → `Registration.jsp`
- `/register/company` → `CompanyRegistration.jsp`
- `/forgot-password` → password reset flow (React-only)
- `/mfa/verify` → TOTP verification (React-only)
- `/dashboard` → `IWLogin.jsp` (post-login landing)
- `/monitoring` → `monitoring/Dashboard.jsp` (charts + transactions + alerts)
- `/monitoring/transactions` → transaction history with filtering/pagination
- `/monitoring/alerts` → alert configuration
- `/profile` → `EditProfile.jsp`
- `/profile/password` → `ChangePassword.jsp`
- `/profile/security` → MFA setup (React-only)
- `/company` → `EditCompanyProfile.jsp`
- `/company/config` → `CompanyConfiguration.jsp` (progress checklist)
- `/company/config/wizard` → config wizard (5-step: solution type, mappings, credentials, execution settings, review)
- `/admin/configurator` → `BDConfigurator.jsp` (flows, credentials, engine controls with toggleable live log panel)
- `/admin/logging` → `Logging.jsp`
- `/admin/audit` → audit log (React-only)
- `/notifications` → notification inbox (React-only)
- `/associate/*` → Associate Portal pages (home, resources, webinars, intake, support, billing, search)
- `/master/*` → Master Console pages (dashboard, users, content, subscriptions, integrations, analytics, audit, notifications, support, settings)

**Key directories**:
- `src/components/layout/` — AppShell, Sidebar, Topbar, ClassicViewBanner
- `src/components/` — ProtectedRoute (auth gate), layout/ (AppShell, Sidebar, Topbar, ClassicViewBanner)
- `src/pages/` — route pages
- `src/providers/` — ThemeProvider, QueryProvider, AuthProvider (session check + login/logout)
- `src/hooks/` — useMonitoring.ts (useDashboard with 30s auto-refresh, useTransactions with pagination), useConfiguration.ts (wizard/credentials/profiles/test), useFlows.ts (flow listing, start/stop, schedule, properties read/write with post-save verification)
- `src/lib/` — api.ts (fetch wrapper with ApiError class), classic-routes.ts, config-labels.ts (shared label formatters), utils.ts
- `src/types/` — TypeScript interfaces for API responses (monitoring.ts, flows.ts, config.ts)
- `src/components/integrations/` — FlowTable, EngineControlsTab (with toggleable LiveLogPanel — bottom/side/collapsed modes), FlowPropertiesDialog, EditScheduleDialog

**API Servlets (JSON, `com.interweave.businessDaemon.api`):**
- **ApiLoginServlet** — `POST /api/auth/login`
- **ApiSessionServlet** — `GET /api/auth/session`
- **ApiLogoutServlet** — `POST /api/auth/logout`
- **ApiProfileServlet** — `GET/PUT /api/profile`
- **ApiCompanyProfileServlet** — `GET/PUT /api/company/profile`
- **ApiRegistrationServlet** — `POST /api/register`
- **ApiCompanyRegistrationServlet** — `POST /api/register/company`
- **ApiChangePasswordServlet** — `POST /api/auth/change-password`
- **ApiConfigurationServlet** — `GET/PUT /api/config/wizard`, `GET/PUT /api/config/credentials`, `GET /api/config/profiles`, `POST /api/config/credentials/test`
- **ApiFlowManagementServlet** — `GET /api/flows` (flow listing, filtered by company solution type), `GET /api/flows/properties` (flow variable parameters), `POST /api/flows/start|stop|submit|initialize`, `PUT /api/flows/schedule`
- **ApiLogViewerServlet** — `GET /api/logs/*`
- **ApiWorkspaceManagementServlet** — `GET /api/workspace/projects` (list all), `GET /api/workspace/projects/{name}` (detail), `GET /api/workspace/projects/{name}/config` (raw config.xml)
- **ApiBuildServlet** — `POST /api/build/compile-xslt` (compile XSLT→bytecode), `GET /api/build/inventory/{name}` (transformer inventory with stale detection)

**Compile command (API servlets)**:
```bash
javac --release 8 -cp "web_portal/tomcat/lib/servlet-api.jar;web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes;web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/lib/*;web_portal/tomcat/lib/*" -d web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/businessDaemon/api/*.java
```
Note: On Windows, use `;` as classpath separator (not `:`).

**⚠️ CRITICAL**: Always use `--release 8` (NOT `-source 1.8 -target 1.8`) when compiling with JDK 9+. The endorsed `jaxb-1.0-ea-trimmed.jar` in `jre/lib/endorsed/` overrides JAXP factories. Without `--release 8`, the compiler resolves method signatures against the host JDK, causing `AbstractMethodError` at runtime on JRE 8.

### Maven Source Framework (`src/`)

A Maven project at the repo root (`pom.xml`) provides the error handling, validation, and web filter infrastructure. It is compiled separately from the Tomcat servlet sources.

**Source packages** (`src/main/java/com/interweave/`):
- `error/` — `IWError`, `IWErrorBuilder`, `ErrorCode`, `ErrorCategory`, `ErrorSeverity`, `ErrorLogger`, `ErrorDocumentation`
- `validation/` — `ConnectionValidator`, `FlowConfigValidator`, `SchemaValidator`, `XPathValidator`, `XSLTValidator`, `ValidationResult`, `ValidationIssue`, `ValidationSeverity`, `ValidationService`
- `web/` — `ErrorHandlingFilter` (ACTIVE in `web.xml`, mapped to `/*`)
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

Workspace projects with full transformer pipelines:
- `SF2AuthNet` - Salesforce to Authorize.Net/payment gateways (142 XSLT transformers, 472 compiled classes)
- `Creatio_Magento2_Integration` - Bidirectional Creatio ↔ Magento 2 (11 XSLT transformers, 11 compiled classes)
- `Creatio_QuickBooks_Integration` - Creatio to QuickBooks (soltran.xslt defined, individual transformers pending)

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
├── docs/ui-ux/                 # UI/UX strategy + prototype HTML
│   ├── iw_associate_portal/    # Associate Portal prototype (9 pages, ASSA tokens, 2026-02-09)
│   ├── iw_master_console/      # Master Console prototype (10 pages, ASSA tokens, 2026-02-06)
│   ├── PORTAL_ARCHITECTURE.md  # Three-portal system architecture + phased adoption plan
│   ├── UI_UX_DESIGN_APPROACH.md # Primary design playbook
│   ├── UI_UX_ANALYSIS.md       # Deep-dive gap analysis
│   ├── COMPETITIVE_LANDSCAPE_EXPANDED.md # 50+ platform research
│   └── IMPLEMENTATION_PLAN.md  # Backend-aware rollout plan
│
├── jre/                        # Bundled Java 8 runtime
├── plugins/                    # Eclipse plugins
│   ├── iw_sdk_1.0.0/           # InterWeave SDK plugin
│   └── org.eclipse.*.jar       # Eclipse core plugins
├── src/                        # Maven project: error framework, validation, ErrorHandlingFilter
│   ├── main/java/com/interweave/error/       # IWError, ErrorCode, ErrorLogger
│   ├── main/java/com/interweave/validation/  # ConnectionValidator, SchemaValidator, etc.
│   ├── main/java/com/interweave/web/         # ErrorHandlingFilter (ACTIVE)
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
│   ├── SF2AuthNet/             # Salesforce-AuthNet (142 XSLTs, 472 classes)
│   ├── Creatio_QuickBooks_Integration/  # CRM→QB (7 flows + 7 queries)
│   ├── Creatio_Magento2_Integration/    # CRM↔Magento (11 XSLTs, 11 classes)
│   ├── GeneratedProfiles/      # Compiler output (per-profile overlays)
│   └── IW_Runtime_Sync/        # Wizard config mirror (auto-generated)
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
- **Done**: ErrorHandlingFilter ACTIVE, RBAC middleware, cloudflared installed, CSP hardened, bcrypt migration (PasswordHasher + progressive rehash), AI Management Architecture Phase 1 (Workspace Read API + XSLT Build API)
- **Blocked**: Configure monitoring email (needs SMTP credentials)
- **Active**: Cloudflare tunnel (quick tunnel working, named tunnel needs account setup), Vercel auto-deploy (GitHub integration), credential encryption at rest, vendor JAR CVE audit
- **Future**: AI Management Architecture Phase 2+ (write operations, connections, change tracking)
