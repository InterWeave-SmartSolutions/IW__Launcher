# Environment & Running Reference

## Start Everything (First Time)
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

## Start Individual Components
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

## Fresh Clone — What Is and Isn't in Git

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

## Environment Requirements

**Windows (Primary):**
- Bundled JRE included at `jre/` (Java 8)
- No additional dependencies

**Linux/Mac (Secondary):**
- Java 8+ required (`java` in PATH or `JAVA_HOME` set)
- Scripts available in `scripts/`

**WSL2:**
- Can browse/edit files at `/mnt/c/IW_Launcher/` but **cannot run Tomcat** (Supabase unreachable from WSL2 networking)
- Use WSL2 for code editing, git operations, and file management only
- **Run Tomcat from Windows PowerShell**: `C:\IW_Launcher\web_portal\tomcat\bin\startup.bat`

**Git LFS Requirement (Developers):**
- If cloning this repo, you MUST have Git LFS installed
- Many binary files (`*.exe`, `*.jar`) are stored via Git LFS
- Without LFS, you'll get tiny placeholder files instead of real binaries
- After cloning, run: `git lfs install && git lfs pull`
- Sanity check: Verify `jre/bin/java.exe` and `web_portal/tomcat/lib/catalina.jar` are NOT tiny text files

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

## Verified (2026-02-24)

- **29/29 E2E tests pass** (`web_portal/test_portal.sh`) — pages, registration, login, profiles, password changes, input validation
- **29/29 session/routing E2E tests pass** (`frontends/iw-portal/tests/e2e_session_and_routing.py`) — security headers, SPA routes, login/logout, cross-UI session leak, route guards, page refresh auth (2026-03-13)
- Admin login (`__iw_admin__` / `%iwps%`) — verified
- Demo user login (`demo@sample.com` / `demo123`) — verified
- Company registration + full config workflow — verified
- Supabase Postgres connectivity via **pooler** (port 6543, `prepareThreshold=0`) — verified from Windows native (2026-02-26)
- Direct connection (port 5432) is **blocked/unreachable** — do not use
- Login, EditProfile save, EditCompanyProfile save — all verified working through pooler
- Must run Tomcat from Windows PowerShell (not WSL2)
