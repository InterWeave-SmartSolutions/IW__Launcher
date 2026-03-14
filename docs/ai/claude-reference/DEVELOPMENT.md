# Development Reference

## Compile Commands

**CRITICAL**: Always use `--release 8` (NOT `-source 1.8 -target 1.8`) when compiling with JDK 9+. The endorsed `jaxb-1.0-ea-trimmed.jar` in `jre/lib/endorsed/` overrides JAXP factories. Without `--release 8`, the compiler resolves method signatures against the host JDK, causing `AbstractMethodError` at runtime on JRE 8.

**Note**: On Windows, use `;` as classpath separator (not `:`).

### Local Servlets
```bash
javac --release 8 -cp "web_portal/tomcat/lib/servlet-api.jar:web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes:web_portal/tomcat/lib/*" -d web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/businessDaemon/config/Local*.java
```

### Auth API Servlets
```bash
javac -source 1.8 -target 1.8 -cp "web_portal/tomcat/lib/servlet-api.jar:web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes:web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/lib/*:web_portal/tomcat/lib/*" -d web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/businessDaemon/api/*.java
```

### API Servlets (all)
```bash
javac --release 8 -cp "web_portal/tomcat/lib/servlet-api.jar;web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes;web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/lib/*;web_portal/tomcat/lib/*" -d web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/businessDaemon/api/*.java
```

### Monitoring
```bash
javac --release 8 -cp "web_portal/tomcat/lib/servlet-api.jar:web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes:web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/lib/*:web_portal/tomcat/lib/*" -d web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/monitoring/service/*.java web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/monitoring/api/*.java
```

### SecurityHeadersFilter
```bash
javac -source 1.8 -target 1.8 -cp "web_portal/tomcat/lib/servlet-api.jar" -d web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/web/SecurityHeadersFilter.java
```

### XSLT Transformer Compile (single)
```bash
java -cp "web_portal/tomcat/webapps/iwtransformationserver/WEB-INF/lib/xsltc.jar;web_portal/tomcat/webapps/iwtransformationserver/WEB-INF/lib/xalan.jar;web_portal/tomcat/webapps/iwtransformationserver/WEB-INF/lib/serializer.jar" org.apache.xalan.xsltc.cmdline.Compile -o <TransformName> -d workspace/<Project>/classes/iwtransformationserver workspace/<Project>/xslt/<TransformName>.xslt
```

### XSLT Transformer Compile (all for a project)
```bash
for xslt in workspace/<Project>/xslt/*.xslt; do
  name=$(basename "$xslt" .xslt)
  java -cp "web_portal/tomcat/webapps/iwtransformationserver/WEB-INF/lib/xsltc.jar;web_portal/tomcat/webapps/iwtransformationserver/WEB-INF/lib/xalan.jar;web_portal/tomcat/webapps/iwtransformationserver/WEB-INF/lib/serializer.jar" org.apache.xalan.xsltc.cmdline.Compile -o "$name" -d workspace/<Project>/classes/iwtransformationserver "$xslt"
done
```

## Maven Source Framework (`src/`)

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

## Local Servlet Bridge (User/Company Management)

The original compiled servlets depend on the `iwtransformationserver` webapp or have critical bugs. All 10 user/company management servlets have been replaced with local SQL-based implementations that query Supabase Postgres directly.

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

## Auth API Servlets (JSON endpoints for React IW Portal)

Three servlets in `com.interweave.businessDaemon.api` provide JSON authentication for the React frontend while sharing the same Tomcat session as the classic JSP login.

- **ApiLoginServlet** — `POST /api/auth/login` — accepts `{"email","password"}`, runs same DB auth as LocalLoginServlet, sets identical session attributes, returns `{"success":true,"user":{...}}` or `{"success":false,"error":"..."}`
- **ApiSessionServlet** — `GET /api/auth/session` — reads session attributes, returns `{"authenticated":true,"user":{...}}` or `{"authenticated":false}`
- **ApiLogoutServlet** — `POST /api/auth/logout` — invalidates the Tomcat session AND clears all Bearer tokens for the user from `ApiTokenStore`. Returns `{"success":true}`. Used by the React UI logout flow.
- **Source**: `WEB-INF/src/com/interweave/businessDaemon/api/Api*.java`
- **Session sharing**: Login via ApiLoginServlet sets the same session attributes as LocalLoginServlet, so users authenticated via React can use classic JSP pages and vice versa.
- **Logout token clearing**: Both `ApiLogoutServlet` and `LocalLogoutServlet` call `ApiTokenStore.removeTokensByAttribute("userEmail", email)` to clear all Bearer tokens for the user, preventing cross-UI session persistence bugs.

**Test credentials**: `demo@sample.com` / `demo123` (user), `admin@sample.com` / `admin123` (admin)

## Known Issues

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

## Eclipse/IDE Specifics

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

## IDE ↔ Web Portal Sync (2026-03-09)

**Portal → IDE:** WORKING — `WorkspaceProfileSyncServlet` exports DB → workspace files, `WorkspaceProfileCompilerServlet` generates engine overlays. Triggered on startup (START.bat) and login.

**IDE → Portal:** MANUAL ONLY — `WorkspaceProfileSyncServlet?action=importProfile` exists but is never called automatically. Schema mismatch: wizard saves `<SF2QBConfiguration>` (flat), IDE uses `<BusinessDaemonConfiguration>` (complex nested XML).

**Sync bridge IMPLEMENTED & TESTED** — `scripts/sync_bridge.ps1` (polling-based, PS 5.1 compatible) watches workspace for IDE changes and auto-calls importProfile + recompile. Launched automatically by START.bat, stopped by STOP.bat. Standalone: `scripts/start_sync_bridge.bat` / `scripts/stop_sync_bridge.bat`. Logs to `logs/sync_bridge.log`. Live-tested 2026-03-09. Full analysis in `docs/development/ENGINE_SYNC_MAP.md`.
