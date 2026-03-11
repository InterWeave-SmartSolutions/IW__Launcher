# First Time Setup

This guide covers setup for a fresh git clone. If you are just accessing the portal online, see **Option A** below — no installation needed.

---

## Option A: Use the Live Deployment (No Setup)

The portal is hosted at **https://iw-portal.vercel.app**

| Account | Email | Password |
|---|---|---|
| Demo user | `demo@sample.com` | `demo123` |
| Admin | `admin@sample.com` | `admin123` |

This connects to the same shared Supabase database as a local install. No software needed.

---

## Option B: Run Locally

### Prerequisites

You need two things that are not included in the git repository due to size:

1. **Tomcat 9.0.83** — the web server (auto-installed by a script)
2. **Java JRE 8, 32-bit** — the runtime (one-time manual download)

Everything else — compiled Java servlets, transformation engine, React portal build, configuration templates, database credentials — is already in the repository.

---

### Step 1: Install Tomcat (automatic, ~2 min)

Double-click or run from Command Prompt:

```
scripts\setup\install_tomcat.bat
```

This downloads Tomcat 9.0.83 from the Apache archive (~25 MB) and installs it automatically. You only need to do this once. If `web_portal\tomcat\bin\catalina.bat` already exists, skip this step.

---

### Step 2: Install the Java Runtime (one-time manual download)

The IDE launcher (`iw_ide.exe`) is a **32-bit** Eclipse application. It requires a **32-bit (x86)** JRE 8.

1. Open this URL:
   ```
   https://adoptium.net/temurin/releases/?version=8&arch=x86&os=windows&package=jre
   ```
2. Download the **Windows x86** ZIP file (not the installer)
3. Extract it so the folder structure looks like this:
   ```
   IW_Launcher\
   └── jre\
       ├── bin\
       │   ├── java.exe     <-- this must exist
       │   └── javaw.exe
       └── lib\
   ```

**Why 32-bit?** The `iw_ide.exe` Eclipse launcher is compiled as a 32-bit application. A 64-bit JRE will cause it to fail silently or crash on startup. The web portal (Tomcat) works with either, but uses the same bundled JRE for consistency.

If `jre\bin\java.exe` already exists and is larger than 100 KB, skip this step.

---

### Step 3: Start

Double-click `START.bat` from the repository root.

**What START.bat does automatically:**
- Creates `.env` from `.env.example` (Supabase credentials are pre-filled — no editing needed)
- Generates `web_portal\tomcat\conf\context.xml` from the template with your DB settings
- Tests Supabase connectivity (auto-selects pooler vs. direct connection)
- Starts Tomcat on port 9090
- Syncs workspace profile mirrors
- Launches the Eclipse IDE (`iw_ide.exe`)
- Opens your browser to `http://localhost:9090/iw-portal/`

**First startup takes ~90 seconds.** Subsequent starts are faster (~30 seconds).

---

### Step 4: Log In

| Portal | URL |
|---|---|
| React portal (recommended) | `http://localhost:9090/iw-portal/` |
| Classic JSP login | `http://localhost:9090/iw-business-daemon/IWLogin.jsp` |

| Account | Credentials |
|---|---|
| System admin | `__iw_admin__` / `%iwps%` |
| Demo user | `demo@sample.com` / `demo123` |
| Admin user | `admin@sample.com` / `admin123` |

---

## After Each Git Pull

```
git pull
START.bat
```

START.bat regenerates `context.xml`, syncs workspace files, and starts the server. The React portal build is tracked in git and updates automatically on pull.

---

## Stopping

Double-click `STOP.bat` or run it from Command Prompt.

---

## Troubleshooting

### "[ERROR] Java runtime not found"
The `jre\bin\java.exe` file is missing. Complete Step 2 above.

### "[ERROR] Web server not found"
Tomcat binaries are missing. Run `scripts\setup\install_tomcat.bat` (Step 1).

### "[ERROR] Supabase password placeholder detected"
This normally does not happen — `.env.example` has the team password pre-filled. If it occurs, open `.env` and replace the `SUPABASE_DB_PASSWORD` placeholder with the actual team password.

### "Database error" on portal pages after a long idle period
Transient connection pool issue. Refresh the page — it resolves automatically. If persistent, run STOP.bat then START.bat to reset the connection pool.

### Port 9090 already in use
Edit `web_portal\tomcat\conf\server.xml` and change `port="9090"` to an unused port, then restart.

### Tomcat starts but crashes immediately (60/60 counter then window closes)
Check `web_portal\tomcat\logs\catalina.*.log` for SEVERE errors. Common cause in the past was an incompatible JAR in `web_portal\tomcat\endorsed\` — this has been fixed and the correct JARs are tracked in git.

### React portal shows blank or 404 at /iw-portal/
The build output is tracked in git, but may be stale or missing. Rebuild:
```
cd frontends\iw-portal
npm install
node node_modules\vite\bin\vite.js build
```

### "Failed to fetch dynamically imported module" on Vercel
Hard-refresh the page (Ctrl+Shift+R on Windows, Cmd+Shift+R on Mac). The ErrorBoundary will auto-reload once to fix chunk hash mismatches from recent deployments.

---

## What's In the Repo

After cloning, you immediately have:

| What | Where |
|---|---|
| Eclipse IDE launcher | `iw_ide.exe` |
| All compiled Java servlets (API + JSP) | `web_portal/tomcat/webapps/iw-business-daemon/` |
| Transformation engine + 133 vendor JARs | `web_portal/tomcat/webapps/iwtransformationserver/` |
| React portal (pre-built, no npm needed) | `web_portal/tomcat/webapps/iw-portal/` |
| JDBC drivers (PostgreSQL + MySQL) | `web_portal/tomcat/lib/` |
| All configuration templates | `web_portal/tomcat/conf/` |
| Supabase credentials (team shared) | `.env.example` → auto-copied to `.env` |
| All scripts | `scripts/` |
| Full documentation | `docs/` |
