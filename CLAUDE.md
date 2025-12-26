# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

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
   - Deploys `iw-business-daemon.war`
   - User authentication and company management
   - Hosts JSP interfaces for profile/config management
   - Default port: 8080

3. **Database** - MySQL-based authentication and configuration
   - Schemas: `database/mysql_schema.sql` (primary), `postgres_schema.sql`, `schema.sql`
   - Three connection modes (configured via `.env`):
     - `oracle_cloud` - Shared Oracle Cloud MySQL (129.153.47.225)
     - `interweave` - InterWeave hosted server (148.62.63.8)
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
./_internal/start_webportal.bat

# IDE only
./_internal/start_ide.bat

# Stop web portal
./_internal/stop_webportal.bat

# Full stop
./STOP.bat
```

### Change Database Connection
```bash
./CHANGE_DATABASE.bat
```
Interactive menu to switch between Oracle Cloud, InterWeave server, or offline mode.

## Database Configuration

Database settings are in `.env` (auto-created from `.env.example`):

```bash
# Set DB_MODE to: oracle_cloud | interweave | local
DB_MODE=oracle_cloud

# Oracle Cloud MySQL (default)
ORACLE_DB_HOST=129.153.47.225
ORACLE_DB_PORT=3306
ORACLE_DB_NAME=iw_ide
ORACLE_DB_USER=iw_admin
ORACLE_DB_PASSWORD=I6Yq8B6p0tF4YbrrabRn6ek66lRda40L

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
- Authentication uses email + password (custom format/hash in LoginServlet)

**Database Setup:**
```bash
# Windows
./_internal/SETUP_DB_Windows.bat

# Linux/Mac
./_internal/SETUP_DB_Linux.sh
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

### Known Issues

1. **Custom User Authentication Not Working**
   - Admin account (`__iw_admin__` / `%iwps%`) works
   - Demo user (`demo@sample.com` / `demo123`) fails authentication
   - Root cause: Compiled `LoginServlet.class` uses proprietary password hash format
   - Workaround: Use admin account only
   - Future fix: Decompile and modify LoginServlet or create replacement servlet

2. **Windows-Centric Design**
   - Primary scripts are `.bat` files for Windows
   - Linux/Mac scripts available in `_internal/` but less maintained
   - Runs in WSL2 but expects Windows paths

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
├── _internal/                  # Advanced scripts
│   ├── SETUP_DB_Windows.bat    # Manual DB setup (Windows)
│   ├── SETUP_DB_Linux.sh       # Manual DB setup (Linux/Mac)
│   ├── start_webportal.bat     # Web server only
│   ├── start_ide.bat           # IDE only
│   ├── stop_webportal.bat      # Stop web server
│   └── sql/                    # SQL migration scripts
│
├── database/                   # Database schemas
│   ├── mysql_schema.sql        # Primary MySQL schema
│   ├── postgres_schema.sql     # PostgreSQL alternative
│   └── schema.sql              # Original schema
│
├── docs/                       # Documentation
│   └── tutorials/              # Training materials
│
├── jre/                        # Bundled Java 8 runtime
├── plugins/                    # Eclipse plugins
│   ├── iw_sdk_1.0.0/           # InterWeave SDK plugin
│   └── org.eclipse.*.jar       # Eclipse core plugins
│
├── web_portal/                 # Web server
│   ├── tomcat/                 # Apache Tomcat 9.0.83
│   │   ├── bin/                # Tomcat binaries
│   │   ├── conf/               # server.xml, web.xml
│   │   ├── logs/               # Server logs
│   │   └── webapps/            # Deployed apps
│   │       └── iw-business-daemon.war
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
- Scripts available in `_internal/`

**WSL2:**
- Works but uses Windows paths (`/mnt/c/IW_IDE/IW_Launcher`)
- Run Windows `.bat` scripts directly

## Security Notes

- `.env` contains production database credentials (excluded from git)
- Never commit `.env` file
- Oracle Cloud MySQL credentials are shared across all users
- Admin password `%iwps%` is hardcoded in authentication system
