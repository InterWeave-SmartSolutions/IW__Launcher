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
   - Default port: 8080

3. **Database** - Authentication and configuration (MySQL or Postgres)
   - Schemas: `database/postgres_schema.sql` (primary/Supabase), `mysql_schema.sql` (legacy), `schema.sql`
   - Connection via Supabase pooler (transaction mode, port 6543) with RLS on all 14 tables
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

Base URL: `http://localhost:8080/iw-business-daemon/`

Key pages:
- `/IWLogin.jsp` - Login page
- `/EditProfile.jsp` - User profile editor
- `/EditCompanyProfile.jsp` - Company settings
- `/CompanyConfiguration.jsp` - Company config
- `/BDConfigurator.jsp` - Business daemon config
- `/Registration.jsp` - User registration
- `/CompanyRegistration.jsp` - Company registration

**Change Tomcat Port:**
Edit `web_portal/tomcat/conf/server.xml`:
```xml
<Connector port="8080" ... />
```

**Logs:** `web_portal/tomcat/logs/`
- `catalina.out` - Main server log
- `localhost.*.log` - Application logs

## Development Notes

### Verified (2026-02-23)

- **29/29 E2E tests pass** (`web_portal/test_portal.sh`) — pages, registration, login, profiles, password changes, input validation
- Admin login (`__iw_admin__` / `%iwps%`) — verified
- Demo user login (`demo@sample.com` / `demo123`) — verified
- Company registration + full config workflow — verified
- Supabase Postgres connectivity via pooler (transaction mode, port 6543) — verified

### Known Issues

1. **Monitoring Servlets Disabled**
   - 10 source files in `WEB-INF/src/com/interweave/monitoring/` (5 API + 5 service)
   - No compiled `.class` files; blocked on `javax.mail` + `com.interweave.error.*` dependencies
   - All entries commented out in `web.xml`; ~1-2 hours effort to enable

2. **ErrorHandlingFilter Disabled**
   - Requires compiled error framework web filter class
   - Commented out in `web.xml`

3. **Windows-Centric Design**
   - Primary scripts are `.bat` files for Windows
   - Linux/Mac scripts available in `scripts/` but less maintained
   - Shell scripts have CRLF issues on Linux; use direct Tomcat `bin/` invocation
   - Runs in WSL2 but expects Windows paths

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

**Compile command**:
```bash
javac -source 1.8 -target 1.8 -cp "web_portal/tomcat/lib/servlet-api.jar:web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes:web_portal/tomcat/lib/*" -d web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/businessDaemon/config/Local*.java
```

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
- Works but uses Windows paths (`/mnt/c/IW_IDE/IW_Launcher`)
- Run Windows `.bat` scripts directly

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
