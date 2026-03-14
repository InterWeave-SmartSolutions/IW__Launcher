# Production Deployment Guide

How to deploy IW_Launcher alongside the existing production server (107525-UVS13,
Windows Server 2016) without disrupting live operations.

**Live clients**: Southern Lamps, Pampa Bay, amagown

---

## Architecture: Side-by-Side Deployment

```
┌─────────────────────────────────────────────────────────┐
│  Windows Server 2016 (107525-UVS13)                     │
│                                                          │
│  ┌──────────────────────┐  ┌──────────────────────────┐ │
│  │ PRODUCTION (legacy)  │  │ IW_LAUNCHER (modern)     │ │
│  │                      │  │                          │ │
│  │ Tomcat 5.5           │  │ Tomcat 9.0.83            │ │
│  │ Port 8080            │  │ Port 9090                │ │
│  │ Java 1.5             │  │ Java 8 (Temurin)         │ │
│  │ stunnel → HTTPS      │  │ Native HTTPS (no stunnel)│ │
│  │ IsHosted="0"         │  │ IsHosted="1"             │ │
│  │ No database auth     │  │ Supabase Postgres        │ │
│  │                      │  │                          │ │
│  │ Webapps:             │  │ Webapps:                 │ │
│  │  iwtransformationsvr │  │  iwtransformationserver  │ │
│  │  iw-business-daemon  │  │  iw-business-daemon      │ │
│  │  IWCustomerPortal    │  │  iw-portal (React)       │ │
│  └──────────────────────┘  └──────────────────────────┘ │
│                                                          │
│  ┌──────────────────────┐                                │
│  │ stunnel (shared)     │  Serves production only        │
│  │ 8443 → 8080          │  IW_Launcher doesn't use it    │
│  │ 38xxx → HTTPS APIs   │                                │
│  └──────────────────────┘                                │
│                                                          │
│  ┌──────────────────────┐                                │
│  │ IIS (port 80/443)    │  Routes to production only     │
│  │ demo.interweave.biz  │                                │
│  └──────────────────────┘                                │
└─────────────────────────────────────────────────────────┘
```

---

## What Gets Deployed

| Component | Source | Destination | Notes |
|-----------|--------|-------------|-------|
| Tomcat 9.0.83 | `web_portal/tomcat/` | New directory on server | Separate from production Tomcat 5.5 |
| JRE 8 | `jre/` | Bundled with Tomcat 9 | Does NOT replace production Java 1.5 |
| iw-business-daemon | `web_portal/tomcat/webapps/iw-business-daemon/` | Tomcat 9 webapps | Different instance, different port |
| iwtransformationserver | `web_portal/tomcat/webapps/iwtransformationserver/` | Tomcat 9 webapps | Same engine JARs, different config |
| iw-portal | `web_portal/tomcat/webapps/iw-portal/` | Tomcat 9 webapps | React dashboard (new) |

---

## No Shared State

These two Tomcat instances share **nothing**:

| Resource | Production | IW_Launcher |
|----------|-----------|-------------|
| Port | 8080 | 9090 |
| JRE | `C:\jre1.5.0_11` | `<deploy>/jre/` (Java 8) |
| TLS | stunnel (127.0.0.1:38xxx) | Native HTTPS (direct) |
| Database | None (admin-only auth) | Supabase Postgres (remote) |
| Config | `IsHosted="0"` (standalone) | `IsHosted="1"` (multi-tenant) |
| Webapps dir | Production Tomcat 5.5 `webapps/` | IW_Launcher Tomcat 9 `webapps/` |
| Logs | Production log dir | `<deploy>/web_portal/tomcat/logs/` |
| Workspace | `C:\IWIDEWorkspace` | `<deploy>/workspace/` |
| Service name | `iwTomcat` | (manual start or new service name) |

---

## Deployment Steps

### 1. Prerequisites
- Windows Server 2016 with RDP access
- At least 512MB free RAM for Tomcat 9
- Port 9090 available (verify with `netstat -an | findstr 9090`)
- Network access to Supabase pooler (port 6543)

### 2. Copy IW_Launcher
```powershell
# Copy the entire IW_Launcher directory to the server
# e.g., to C:\IW_Launcher or D:\IW_Launcher
xcopy /E /I C:\IW_Launcher \\107525-UVS13\D$\IW_Launcher
```

### 3. Configure Environment
```powershell
# Create .env from example (or copy from dev machine)
copy .env.example .env
# Edit .env with Supabase credentials
notepad .env
```

### 4. Configure Database Connection
- **Option A (recommended):** Set `DB_MODE=supabase` in `.env` and let `START.bat` auto-render
  `config.xml` and `context.xml` from the templates on each startup.
- **Option B (manual):** Copy `docs/authentication/config.xml.supabase.template` to
  `web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/config.xml` and update `context.xml`.

> **Note:** `START.bat` auto-renders `config.xml` and `context.xml` from `.env`/`DB_MODE` on
> every run. If you manually edit these files, `START.bat` will overwrite them. Use `.env` as
> the source of truth for connection settings.

### 5. Start IW_Launcher
```powershell
# Use the startup script — it sets JAVA_HOME and CATALINA_HOME safely
.\START.bat
```

### 6. Verify
- Browse to `http://localhost:9090/iw-business-daemon/` — should show login page
- Browse to `http://localhost:9090/iw-portal/` — should show React dashboard
- Verify production still works: `http://localhost:8080/iw-business-daemon/`

---

## IsHosted Modes

| Mode | Value | Authentication | Flow Loading | Use Case |
|------|-------|---------------|--------------|----------|
| **Standalone** | `IsHosted="0"` | Admin account only (`__iw_admin__`) | All flows visible to admin | Production (legacy), local testing |
| **Multi-tenant** | `IsHosted="1"` | Database-backed (Supabase) | Per-company flow isolation via `solutionType` | IW_Launcher (modern) |

Production uses standalone mode because it was built before multi-tenant support.
IW_Launcher uses hosted mode to support multiple companies with isolated flows.

Both modes use the same engine — the difference is only in authentication and flow visibility.

---

## Safety Checklist

Before deploying, verify:

- [ ] Port 9090 is free (`netstat -an | findstr 9090`)
- [ ] Production Tomcat 5.5 is running normally on port 8080
- [ ] No modifications to production configs (`C:\Program Files\...\Tomcat 5.5\`)
- [ ] No modifications to stunnel config
- [ ] No modifications to IIS configuration
- [ ] `.env` file has correct Supabase credentials
- [ ] `context.xml` has correct database connection string
- [ ] IW_Launcher uses its own JRE (not system Java)

## Warnings

1. **DO NOT** modify production Tomcat 5.5 configs, webapps, or service
2. **DO NOT** modify stunnel.conf — production depends on every tunnel mapping
3. **DO NOT** modify IIS configuration (demo.interweave.biz routing)
4. **DO NOT** stop or restart the production `iwTomcat` service
5. **DO NOT** share workspace directories — each Tomcat uses its own
6. **DO NOT** point IW_Launcher at production's `C:\IWIDEWorkspace`

---

## Rollback

IW_Launcher can be completely removed by:
1. Stopping its Tomcat 9 (`STOP.bat` or kill the Java process on port 9090)
2. Deleting the IW_Launcher directory
3. No production artifacts are modified, so no production rollback is needed
