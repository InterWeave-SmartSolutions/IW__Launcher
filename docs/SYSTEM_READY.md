# 🚀 InterWeave IDE - System Readiness Report

## ✅ SYSTEM STATUS: VERIFIED (2026-02-25)

This repository contains everything needed to run InterWeave IDE. A fresh clone requires
running `START.bat` which handles first-time database configuration via `.env.example`.

---

## 📋 COMPONENT CHECK

| Component               | Status      | Details                                                    |
| ----------------------- | ----------- | ---------------------------------------------------------- |
| **Java Runtime**        | ✅ Ready    | Bundled JRE 8 at `jre/bin/java.exe`                        |
| **IDE Launcher**        | ✅ Ready    | `iw_ide.exe` present                                       |
| **Web Portal (Tomcat)** | ✅ Ready    | Apache Tomcat 9.0.83 configured                            |
| **Web Application**     | ✅ Ready    | 27 servlets deployed + 49 .class files + 4 WEB-INF JARs   |
| **Database Connector**  | ✅ Ready    | PostgreSQL JDBC + mysql-connector-java-8.0.33.jar in tomcat/lib |
| **Database Config**     | ✅ Verified | Supabase Postgres via direct connection (port 5432)         |
| **Runtime Environment** | ⚠️ Note    | **Tomcat must run from Windows PowerShell**, not WSL2       |
| **Startup Scripts**     | ✅ Ready    | `START.bat`, `STOP.bat`, `CHANGE_DATABASE.bat`             |
| **Java Source Code**    | ✅ Ready    | Maven project structure in `src/` + servlet source in `WEB-INF/src/` |
| **Build System**        | ✅ Ready    | `pom.xml` configured for Maven (optional — pre-built classes included) |
| **Network Port**        | ⚠️ Check   | Port 9090 (confirm not in use)                             |

---

## 🔧 DEPLOYMENT STATUS

### Website/Web Forms

- ✅ **Web Portal:** `web_portal/tomcat/webapps/iw-business-daemon/`
- ✅ **JSP Pages Present:**
  - `IWLogin.jsp` - Login page
  - `EditProfile.jsp` - User profile
  - `CompanyConfiguration.jsp` - Company settings
  - `BDConfigurator.jsp` - Business daemon config
  - Plus 20+ additional JSP pages

### Backend Components

- ✅ **Servlet Classes:** 27 active servlets with matching .class files in WEB-INF/classes
- ✅ **Error Framework:** 8 classes (ErrorCode, ErrorCategory, IWError, IWErrorBuilder, etc.)
- ✅ **Core Engine:** BusinessDaemonInit, ConfigContext, TransactionBase, etc.
- ⚠️ **Monitoring API:** 4 servlets disabled (source exists, not yet compiled)
- ⚠️ **ErrorHandlingFilter:** Disabled (requires compiled web filter class)

### Database

- ⚠️ **First-Time Setup:** Run `START.bat` — it creates `.env` from `.env.example` and `context.xml` from `context.xml.mysql`
- ✅ **Schema:** `mysql_schema.sql` included with admin user (`__iw_admin__` / `%iwps%`)
- ✅ **Modes Available:**
  - `supabase` - Supabase Postgres (default)
  - `interweave` - InterWeave hosted MySQL
  - `local` - Offline mode (admin only)

---

## 🚀 HOW TO START

### Option 1: Full Application (Recommended)

**Double-click:** `START.bat` in the project root

This will:

1. ✅ Start Tomcat web server
2. ✅ Launch Eclipse IDE
3. ✅ Open browser to login page
4. ✅ Load all configuration automatically

**Login with:**

- Username: `__iw_admin__`
- Password: `%iwps%`

**Access URL:** `http://localhost:9090/iw-business-daemon/IWLogin.jsp`

---

### Option 2: Web Portal Only

**Run:** `./scripts/start_webportal.bat`

Then open browser to: `http://localhost:9090/iw-business-daemon/`

---

### Option 3: IDE Only

**Run:** `./scripts/start_ide.bat`

This launches just the Eclipse IDE without Tomcat.

---

## ⚠️ TROUBLESHOOTING

### Issue: PowerShell freezes when starting Tomcat

**Cause:** Calling `startup.bat` with `& "...\startup.bat" 2>&1` in PowerShell
prevents the internal `start` command from detaching. PowerShell hangs and
cannot be canceled with Ctrl+C.

**Solution:** Use `Start-Process` or the project's `START.bat`:

```powershell
# Safe — use Start-Process
Start-Process -FilePath "C:\IW__Launcher\web_portal\tomcat\bin\startup.bat"

# Or just use the project launcher
C:\IW__Launcher\START.bat
```

---

### Issue: "Port 9090 already in use"

**Solution:** Either stop the other service or change Tomcat port in:

```
web_portal/tomcat/conf/server.xml
```

Find `<Connector port="9090"...` and change to a different port (e.g., 8081)

---

### Issue: "Java not found"

**Solution:** The bundled JRE is at `jre/bin/java.exe`. If START.bat fails:

1. Verify `jre/bin/java.exe` exists
2. Run: `jre/bin/java.exe -version` to test Java directly

---

### Issue: Login page shows error

**Possible causes:**

1. **Database unreachable** - Change `.env` to `local` mode:

   ```
   DB_MODE=local
   ```

   Then restart via `STOP.bat` → `START.bat`

2. **Tomcat not starting** - Check logs:

   ```
   web_portal/tomcat/logs/catalina.out
   ```

3. **Browser can't connect** - Ensure port 9090 is accessible:
   - Check firewall settings
   - Verify Tomcat started (no errors in logs)
   - Try: `http://localhost:9090/iw-business-daemon/`

---

### Issue: "Can't connect to database"

**Options:**

1. **Use Offline Mode** - Run `CHANGE_DATABASE.bat` and select "Offline (admin only)"
2. **Check Network** - Ensure you can reach `129.153.47.225:3306`
3. **Check Firewall** - MySQL port 3306 must not be blocked

---

## 📊 SYSTEM SPECIFICATIONS

### Minimum Requirements (Met ✅)

- ✅ Java 8 (bundled JRE included)
- ✅ Tomcat 9.0.83 (included)
- ✅ 500 MB disk space (available)
- ✅ 512 MB RAM (Eclipse standard)

### Network Requirements

- ✅ Internet (for Supabase Postgres database) - OR use offline mode
- ✅ Port 9090 (Tomcat web server)
- ✅ Port 5432 (Supabase Postgres) - direct connection from Windows only
- ⚠️ If IPv6 is blocked, use Supabase pooler (set SUPABASE_POOLER_* in .env)
- ⚠️ WSL2 cannot reach Supabase direct host (IPv6/networking limitation)
- ⚠️ Supabase pooler (port 6543) returns "Tenant or user not found" — do not use

---

## ✨ SECURITY & CVE STATUS

| CVE            | Status       | Version                                   |
| -------------- | ------------ | ----------------------------------------- |
| CVE-2022-34169 | ⚠️ Note     | xalan.jar in WEB-INF/lib (version TBD)    |
| Java 8 EOL     | ⚠️ Note      | Still supported, Java 11+ roadmap planned |

---

## 📚 DOCUMENTATION

All components are documented:

- **[README.md](README.md)** - Quick start guide
- **[BUILD.md](BUILD.md)** - Build instructions
- **[CLAUDE.md](CLAUDE.md)** - Architecture overview
- **[docs/tutorials/](docs/tutorials/)** - Training materials

---

## 🔄 NEXT STEPS

### Immediate (get it running)

1. Run `START.bat`
2. Login with admin credentials
3. Explore the IDE and web portal

### Short term (test functionality)

1. Open sample projects in workspace
2. Test Salesforce/QuickBooks integration flows
3. Run available test cases

### Medium term (customize)

1. Change database mode to local (if offline needed)
2. Create custom integration flows
3. Deploy to production

### Long term (modernize - from roadmap)

1. Upgrade to Java 11/17/21
2. Modernize Eclipse platform
3. Add monitoring dashboard
4. Implement RBAC

---

## 📍 PROJECT STRUCTURE REFERENCE

```
IW__Launcher/
├── START.bat              ← Double-click to launch everything
├── STOP.bat               ← Stop all services
├── CHANGE_DATABASE.bat    ← Switch database mode
├── iw_ide.exe             ← IDE launcher
├── jre/                   ← Java 8 runtime
├── web_portal/tomcat/     ← Web server & app
│   └── webapps/iw-business-daemon/  ← JSP pages
├── src/main/java/         ← Java source code
├── pom.xml                ← Build configuration
└── database/              ← SQL schemas
```

---

## ✅ VERIFICATION CHECKLIST

Before launching, verify:

- [ ] Double-click `START.bat` (or run `cmd /c START.bat`)
- [ ] Wait 30 seconds for server to start
- [ ] Browser opens to login page
- [ ] No error messages in console window
- [ ] Can login with `__iw_admin__` / `%iwps%`
- [ ] Web portal loads CompanyConfiguration.jsp
- [ ] Eclipse IDE launches (may take 30+ seconds)

---

## 🆘 SUPPORT

If you encounter issues:

1. **Check logs:**

   ```
   web_portal/tomcat/logs/catalina.out
   ```

2. **Test Java directly:**

   ```
   jre/bin/java.exe -version
   ```

3. **Test Tomcat separately:**

   ```
   ./scripts/start_webportal.bat
   ```

4. **Review error documentation:**
   - See `docs/errors/` for error codes
   - Check `test-plan-5.2-custom-user.md` for known issues

---

## 📞 QUICK COMMANDS

| Task                  | Command                                    |
| --------------------- | ------------------------------------------ |
| **Launch Full App**   | `START.bat`                                |
| **Stop All Services** | `STOP.bat`                                 |
| **Change Database**   | `CHANGE_DATABASE.bat`                      |
| **Web Portal Only**   | `./scripts/start_webportal.bat`            |
| **IDE Only**          | `./scripts/start_ide.bat`                  |
| **Clean Build**       | `mvn clean package`                        |
| **View Tomcat Logs**  | `type web_portal\tomcat\logs\catalina.out` |

---

## 🎯 SUCCESS INDICATORS

✅ **You're all set when you see:**

1. Windows console showing "IW_IDE IS RUNNING"
2. Browser opens to http://localhost:9090/iw-business-daemon/IWLogin.jsp
3. Login form displays without errors
4. Eclipse IDE window appears (may take 30-60 seconds)
5. "Welcome to CompanyConfiguration.jsp" after login

---

**Updated:** February 25, 2026  
**Status:** ✅ VERIFIED — Supabase Postgres direct connection (port 5432) from Windows native. Tomcat must run from PowerShell, not WSL2.
