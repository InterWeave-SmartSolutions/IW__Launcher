# InterWeave IDE — Next Steps Roadmap

**Last Updated:** 2026-03-06
**Project:** IW_Launcher — Enterprise Data Integration Platform
**Stack:** Eclipse 3.1 IDE + Tomcat 9.0.83 + Supabase Postgres

---

This is a living roadmap, updated after each major development session. It reflects the coordinated outcomes of the full-repository audit completed on 2026-03-06. Items are organized into three tiers by effort and complexity. Each item includes a priority tag, effort estimate, clear rationale, and specific file paths and commands required to complete the work. AI agents and human developers should treat this document as the authoritative source of prioritized next actions.

---

## Table of Contents

1. [Quick Wins (Low Effort, High Value)](#quick-wins-low-effort-high-value)
   - [1. Enable ErrorHandlingFilter](#1-enable-errorhandlingfilter)
   - [2. Fix frontends/InterWoven Submodule Drift](#2-fix-frontendsinterwoven-submodule-drift)
   - [3. Configure Email/Webhook Delivery for Monitoring](#3-configure-emailwebhook-delivery-for-monitoring)
2. [Medium Term (Moderate Effort)](#medium-term-moderate-effort)
   - [4. Phase 2 Step 5 — React Forms for Profile/Company Pages](#4-phase-2-step-5--react-forms-for-profilecompany-pages)
   - [5. Phase 2 Step 6 — Recharts Monitoring Visualizations](#5-phase-2-step-6--recharts-monitoring-visualizations)
   - [6. Populate data/ Directory Structure](#6-populate-data-directory-structure)
3. [Long Term / Complex](#long-term--complex)
   - [7. Phase 1B — TransactionLogger Instrumentation](#7-phase-1b--transactionlogger-instrumentation)
   - [8. SF2AuthNet Workflow Deepening](#8-sf2authnet-workflow-deepening)
   - [9. Integration Manager React Page](#9-integration-manager-react-page)
4. [Verification Commands](#verification-commands)

---

## Quick Wins (Low Effort, High Value)

### 1. Enable ErrorHandlingFilter

**[PRIORITY: HIGH]** **[Effort: ~30 min]**

**Prerequisite:** Maven 3.6+ must be installed and available in PATH. If Maven is not available, this step can be deferred — the application is fully operational without it. The ErrorHandlingFilter is an enhancement that provides structured error pages; all servlets and JSPs have fallback error handling that works without it.

**Rationale:** A complete error-handling and validation framework is already written and compiled. It is not active because the filter registration block in `web.xml` is commented out. Enabling it takes minutes and immediately provides structured error pages and consistent error codes across all servlets, replacing the default Tomcat 500 stack trace pages.

**Source files:**

- `src/main/java/com/interweave/web/ErrorHandlingFilter.java` — the filter entry point
- `src/main/java/com/interweave/error/` — error framework classes
- `src/main/java/com/interweave/validation/` — validation framework classes

**Step 1 — Build the JAR (requires JDK 8 + Maven on Windows PATH, run from repo root):**

```bat
mvn -DskipTests package
```

Full build reference: `docs/development/BUILD.md`

**Step 2 — Deploy (choose one option):**

Option A — Deploy as JAR:

```bat
cp target/iw-error-framework-1.0.0.jar web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/lib/
```

Option B — Deploy compiled classes directly:

```bat
cp -r target/classes/com/interweave/ web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes/com/interweave/
```

**Step 3 — Enable the filter:**

Open `web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/web.xml` and uncomment the `ErrorHandlingFilter` `<filter>` and `<filter-mapping>` blocks.

**Step 4 — Restart Tomcat:**

```bat
scripts\start_webportal.bat
```

---

### 2. ~~Fix frontends/InterWoven Submodule Drift~~ — RESOLVED

**[RESOLVED: 2026-03-06]**

**Resolution:** The submodule reference (mode 160000, no `.gitmodules`) was never properly configured. A Windows directory junction now links `frontends/InterWoven` → `C:\InterWoven` (the separately cloned InterWoven repo). This provides transparent access to all InterWoven source files (5 sample projects, JAXB JARs, transformation server webapps) without submodule complexity. The `enable_legacy_sample_engine.ps1` script also has workspace fallback logic for machines where neither the submodule nor junction is available.

---

### 3. Configure Email/Webhook Delivery for Monitoring

**[PRIORITY: MEDIUM]** **[Effort: ~20 min]**

**Rationale:** `EmailNotificationService` and `WebhookNotificationService` are already compiled, deployed, and start automatically on Tomcat boot. They poll every 30 seconds but deliver nothing because credentials are absent. Filling in the properties template activates alert delivery for all monitoring thresholds already defined in the Supabase schema, with no code changes required.

**Step 1 — Copy the template:**

```bat
cp web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/monitoring.properties.template ^
   web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/monitoring.properties
```

**Step 2 — Edit `monitoring.properties` and fill in:**

- SMTP host, port, username, password
- Webhook URLs for alert delivery targets

**Note:** `monitoring.properties` is gitignored. It must never be committed — it contains secrets.

**Step 3 — Restart Tomcat to reload the properties:**

```bat
scripts\start_webportal.bat
```

---

## Medium Term (Moderate Effort)

### 4. Phase 2 Step 5 — React Forms for Profile/Company Pages

**[PRIORITY: HIGH]** **[Effort: ~4-8 hrs]**

**Rationale:** The routes `/profile`, `/company`, `/company/config`, `/admin/configurator`, and `/admin/logging` all use the `ClassicRedirectPage` hook pattern, which falls through to JSP. The next phase of the portal migration is to replace these with full React forms backed by new JSON API endpoints. The classic JSP view must remain fully functional at all times — never remove `web.xml` servlet-class entries.

**New backend API servlets to create in `WEB-INF/src/com/interweave/businessDaemon/api/`:**

| Servlet | Method | Path |
|---|---|---|
| `ApiSaveProfileServlet.java` | POST | `/api/profile/save` |
| `ApiSaveCompanyProfileServlet.java` | POST | `/api/company/save` |
| `ApiChangePasswordServlet.java` | POST | `/api/auth/change-password` |

**New frontend pages to create in `frontends/iw-portal/src/pages/`:**

| File | Description |
|---|---|
| `ProfilePage.tsx` | Load from `/api/auth/session`, save via `ApiSaveProfileServlet` |
| `CompanyPage.tsx` | Load and save company profile |
| `CompanyConfigPage.tsx` | Configuration wizard form |

**Design reference:** `frontends/assa/assa_customer_portal/pages/profile.html`

**Frontend stack:** React Hook Form + Zod + TanStack Query mutations + shadcn/ui form components

**Compile new servlets (run from repo root):**

```bash
javac --release 8 \
  -cp "web_portal/tomcat/lib/servlet-api.jar:web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes:web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/lib/*:web_portal/tomcat/lib/*" \
  -d web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes \
  web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/businessDaemon/api/Api*.java
```

**Constraint:** The classic JSP fallback must remain intact. Do not remove or comment out existing `<servlet>` or `<servlet-mapping>` entries in `web.xml`.

---

### 5. Phase 2 Step 6 — Recharts Monitoring Visualizations

**[PRIORITY: MEDIUM]** **[Effort: ~2-3 hrs]**

**Rationale:** The monitoring API is live and returning data from the correct endpoints. TanStack Query hooks are already implemented. The missing piece is chart components that visualize the data. This is a pure frontend addition with no backend changes required.

**Available API endpoints:**

- `/api/monitoring/dashboard`
- `/api/monitoring/transactions`
- `/api/monitoring/metrics`
- `/api/monitoring/alerts`

**Existing hooks:** `frontends/iw-portal/src/hooks/useMonitoring.ts`
- `useDashboard` — auto-refreshes every 30 seconds
- `useTransactions` — paginated

**Additions to `DashboardPage.tsx`:**

- Transaction volume over time: `<AreaChart>` or `<BarChart>`
- Success rate trend: `<LineChart>`

**Additions to `MonitoringPage.tsx`:**

- Average duration sparklines per flow
- Alert history table

**Important:** Charts will show empty or zero data until Phase 1B (TransactionLogger, item 7 below) is complete. Display a "No data yet" placeholder state until rows exist in the monitoring tables.

---

### 6. Populate data/ Directory Structure

**[PRIORITY: LOW]** **[Effort: ~1 hr]**

**Rationale:** The following directories were created as placeholders in December 2025 and remain empty. They should be given defined purposes before any feature begins writing to them, to prevent inconsistent usage.

**Directories and intended purposes:**

| Directory | Purpose |
|---|---|
| `data/exports/` | Runtime export artifacts produced by the transformation engine |
| `data/forms/` | Form schema definitions for dynamic form rendering |
| `data/pages/` | Page configuration or CMS-style content definitions |
| `data/projects/` | Project template archives for new workspace project creation |

**Action:** Add a `README.md` to each subfolder documenting its purpose, expected file formats, and which component writes to or reads from it. This work is low priority unless a specific feature immediately requires one of these paths.

---

## Long Term / Complex

### 7. Phase 1B — TransactionLogger Instrumentation

**[PRIORITY: HIGH]** **[Effort: ~8-16 hrs]**

**Rationale:** The monitoring dashboard exists and is accessible at `/monitoring/Dashboard.jsp` and `/api/monitoring/dashboard`. All 6 monitoring tables in Supabase are created but contain 0 rows — the dashboard shows only zeros. The root cause is that no instrumentation has been added to the transformation engine to write transaction data. This item closes that gap.

**Context and constraints:**

- The transformation engine (`iwtransformationserver`) is compiled-only — no Java source is available.
- The engine uses native JNI: `web_portal/tomcat/webapps/iwtransformationserver/WEB-INF/lib/TS_JNI.dll` (Windows) and `TS_JNI.so` (Linux).
- `TransactionExecutionWrapper.java` located at `web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/businessDaemon/config/` was designed as the instrumentation entry point. See also `TRANSACTION_LOGGING_INTEGRATION.md` in that same directory.

**Recommended approach (lowest risk — no decompilation):**

Implement a Tomcat Filter or Valve that intercepts all requests to the following transformation endpoints:

- `/iwtransformationserver/transform`
- `/iwtransformationserver/scheduledtransform`

Capture per-request:

- Timestamp (request start)
- Profile name (from query parameters)
- Response time (milliseconds)
- HTTP status code

Write the captured data to the `transaction_logs` monitoring table via direct JDBC, reusing the existing pooled `DataSource`. This approach captures timing and status without touching any proprietary engine code.

**Alternative approach (higher effort):** Use a Java agent with Byte Buddy to intercept engine class methods at runtime. This provides deeper visibility but is significantly more complex and carries higher risk of incompatibility with the native JNI layer.

**Reference:** `docs/ai/AI_WORKLOG.md` — entries from 2026-02-23 covering Phase 1 monitoring work.

---

### 8. SF2AuthNet Workflow Deepening

**[PRIORITY: MEDIUM]** **[Effort: ~3-4 hrs]**

**Rationale:** The `SF2AuthNet` workspace project (Salesforce to Authorize.net integration) exists and is mapped in the profile configuration, but the compiler has no solution-specific logic for it. It falls back to the generic path, which means profile compilation for `SF2AUTH` profiles may produce incorrect or incomplete output.

**Relevant paths:**

- `workspace/SF2AuthNet/` — workspace project
- `config/workspace-profile-map.properties` — maps `SF2AUTH` to `SF2AuthNet`
- `docs/development/WORKSPACE_PROFILE_SYNC.md` — compiler architecture reference

**Steps:**

1. Add an `SF2AUTH`-specific compiler module in `WorkspaceProfileCompiler.java`, modeled on the existing `CRM2QB3` module.
2. Add a regression corpus file: `tests/compiler-regression/<profilename>.SF2AUTH.expected.properties`
3. Add the new module to `scripts/verify_profile_compiler.ps1` to cover it in regression testing.
4. Wire a test user profile in Supabase with `solution_type = SF2AUTH` to enable end-to-end verification.

---

### 9. Integration Manager React Page

**[PRIORITY: LOW]** **[Effort: ~4-6 hrs]**

**Rationale:** `BDConfigurator.jsp` currently renders 70 live flow and query links (verified 2026-02-27) with START/STOP controls. The goal is a React replacement at `/admin/configurator` that shows live flow status with interactive START/STOP toggles. Because `ProductDemoServlet` is compiled-only with no source, a thin API wrapper servlet is required to bridge it to the React frontend.

**New files required:**

- `WEB-INF/src/com/interweave/businessDaemon/api/ApiFlowControlServlet.java` — wraps `ProductDemoServlet` internally, exposes JSON responses for START/STOP/status
- `frontends/iw-portal/src/pages/ConfiguratorPage.tsx` — React page at `/admin/configurator`

**Implementation notes:**

- `ApiFlowControlServlet` must delegate to `ProductDemoServlet` for actual flow control; do not attempt to replicate its logic.
- The JSP fallback (`BDConfigurator.jsp`) must remain functional during and after the migration.
- Compile the new servlet using the same `javac` command pattern documented in item 4 above.

---

## Verification Commands

Run these after any deployment or configuration change to confirm system state.

**Full 9-point system readiness check:**

```powershell
scripts\system_check.bat
```

**Runtime + iwtransformationserver reachability:**

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\verify_legacy_engine.ps1
```

**Profile compiler regression (Tester1/CRM2QB3):**

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\verify_profile_compiler.ps1
```

**End-to-end portal tests (run from WSL2, 29/29 should pass):**

```bash
bash web_portal/test_portal.sh
```

---

*This document is maintained as part of the IW_Launcher repository. Update the "Last Updated" date and relevant item status after each major development session.*
