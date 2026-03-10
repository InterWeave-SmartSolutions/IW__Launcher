# InterWeave Engine Sync Map

This document maps the verified runtime relationship between the InterWeave IDE, the JSP wizard pages, the web application servlets, the database layer, and the transformation engine expectations.

Use it when the goal is to make the legacy InterWeave stack operate as one coherent system instead of treating the IDE, portal, and database as unrelated pieces.

## Purpose

The InterWeave stack only behaves correctly when these layers stay in sync:

- IDE project design and build output
- Web portal JSP and servlet flow
- Runtime `ConfigContext` state
- Database-backed user and company profiles
- Transformation engine endpoints (`iwtransformationserver`)

This file focuses on the actual wiring present in this repository today.

## Verified Runtime Chain

### 1. Startup sequence (`START.bat`)

`START.bat` is the primary orchestration entry point.

Verified behavior:

1. Ensures `.env` exists by copying `.env.example` on first run.
2. Loads `DB_MODE` and database credentials from `.env`.
3. Chooses the correct Tomcat `context.xml` template and Business Daemon config template.
4. Validates the local JRE, Tomcat, and JDBC driver presence.
5. Prepares the legacy runtime assets (sample flow import plus JAXB compatibility layer).
6. Starts Tomcat.
7. Waits for `http://localhost:9090/iw-business-daemon/IWLogin.jsp`.
8. Opens the browser to `IWLogin.jsp`.
9. Launches `iw_ide.exe`.

Operational implication:

- `START.bat` is intended to bring up the portal and the IDE together, which matches the legacy training material and the original platform model.
- `DB_MODE` and `TS_MODE` are separate controls. `DB_MODE` chooses profile/auth storage, while `TS_MODE` chooses whether flow/query/log URLs point to the local bundled runtime or a legacy InterWeave host.

### 2. Browser and wizard flow

The user-facing startup path is:

1. `IWLogin.jsp`
2. `LoginServlet` (currently mapped to `LocalLoginServlet`)
3. `CompanyConfiguration.jsp` for initial or hosted configuration flows
4. `CompanyConfigurationServletOS`
5. `IMConfig.jsp` / `BDConfigurator.jsp` for flow control and monitoring

Important verified details:

- `LocalLoginServlet` is the current login entry point and uses the database directly.
- `LocalLoginServlet` now binds the authenticated profile into `ConfigContext.profileDescriptors`, `transactionList`, and `queryList`, so `BDConfigurator.jsp` renders the same live flow/query set the runtime can act on.
- Login-time profile binding also normalizes the per-profile transaction/query runtime endpoints to the active `TS_MODE`, which prevents stale legacy log hosts from leaking into fresh local sessions.
- `CompanyConfiguration.jsp` builds the hosted company configuration wizard state from `ConfigContext` and `TransactionThread` parameters.
- `BDConfigurator.jsp` renders the Integration Manager flow controls from `ConfigContext.getTransactionList()`.
- `BDConfigurator.jsp` posts operational commands to `ProductDemoServlet`.
- `ApiLoginServlet` mirrors the same hosted-profile binding, keeping the JSON login path compatible with the classic JSP/runtime state.

### 3. IDE runtime and project model

The running IDE currently exposes a Navigator tree consistent with both the legacy PDFs and the 2024 training transcript.

Verified live Navigator structure:

- `Creatio_QuickBooks_Integration`
  - `Configuration`
    - `Integration Manager`
    - `Transfortmation Server`
  - `Transactions`
  - `Connections`
  - `XSLT`
    - `Templates`
    - `iwp2HTMLTable`
  - `Integration Flows`
    - `Transaction Flows`
    - `Queries`
- `FirstTest`

This matches the legacy training guidance:

- Projects contain configuration, transactions, connections, XSLT, and integration flows.
- Integration flows split into transaction flows, utility flows, and queries.
- The IDE is the authoring environment for those definitions.

### 4. Workspace-to-filesystem mapping

The live Navigator labels map directly to the workspace structure:

- `Configuration`
  - `workspace/<Project>/configuration/im/config.xml`
  - `workspace/<Project>/configuration/ts/config.xml`
- `XSLT`
  - `workspace/<Project>/xslt/**`
- `Integration Flows`
  - Derived from `workspace/<Project>/xslt/Site/new/transactions.xslt`
  - Runtime transaction definitions are emitted into generated config data, not stored as a simple top-level `flows.xml`

Verified current project state:

- Both sample projects (`Creatio_QuickBooks_Integration`, `FirstTest`) contain only `configuration/` and `xslt/` at the filesystem root.
- Project `configuration/im/config.xml` currently stores `BusinessDaemonConfiguration` metadata.
- Project `configuration/ts/config.xml` stores `TransformationServerConfiguration` metadata.
- `xslt/Site/new/xml/transactions.xml` is currently empty in the checked sample project.

## The Critical Runtime Junction: `ConfigContext`

The Business Daemon runtime depends on `ConfigContext` to hold:

- `transactionList`
- `queryList`
- `profileDescriptors`
- hosted DB settings
- user/company registration and update contexts

Verified class-level evidence from the compiled runtime:

- `ConfigContext.class` contains references to:
  - `/WEB-INF/config.xml`
  - `TransactionDescription`
  - `ProfileDescriptor`
  - `getTransactionList`
  - hosted DB fields and runtime save/restore methods

Practical meaning:

- The JSP management pages do not read workspace project files directly.
- They render from the in-memory `ConfigContext` loaded from the deployed webapp's `WEB-INF/config.xml`.
- If `WEB-INF/config.xml` lacks `TransactionDescription` entries, the IM pages have little or no flow state to manage even if the IDE project exists.
- If per-profile `TransactionThread` instances carry stale dedicated TS/log URLs, the IM page can appear healthy while `Runs` links still redirect to an unreachable legacy host. The runtime now corrects those thread-level URLs at login based on `TS_MODE`.

## The Critical Runtime Junction: `ProductDemoServlet`

The Integration Manager control pages depend on `ProductDemoServlet`.

Verified current state:

- `web.xml` maps `/ProductDemoServlet`.
- `ProductDemoServlet.class` exists in `WEB-INF/classes`.
- `ProductDemoServlet.java` source is not present.
- The compiled class contains references to:
  - `CurrentProfile`
  - `command`
  - `Reset Server`
  - `Flow Assignment`
  - `Stop and Save`
  - `Save`
  - `Restore`
  - `Refresh`
  - `getTransactionList`
  - `runTransactionThread`
  - `stopTransactionThread`
  - transaction timing and profile fields

Practical meaning:

- The flow control servlet is present and is still part of the runtime path.
- The current repo can execute the legacy IM control loop even though the Java source for this servlet is unavailable.

## Database Relationship

There are two separate database concerns that must not be conflated:

### 1. Portal identity and configuration database

This is the database used by:

- `LocalLoginServlet`
- registration/profile servlets
- React auth API
- company and user profile management

This is what `START.bat` configures for Tomcat through JNDI/DataSource templates.

### 2. Transformation engine runtime parameters

The Business Daemon runtime also maintains engine configuration inside `ConfigContext`, including hosted DB settings and per-profile runtime state.

Important distinction:

- User/company login working does not automatically mean transaction flows can run.
- Transaction flow execution still depends on the engine configuration and transformation endpoints being available.

## Current Full-Flow Readiness Assessment

### Working now

- `START.bat` can orchestrate the intended startup order.
- The web portal login and wizard path is wired.
- Login now seeds the authenticated profile into the live transaction/query runtime, so `BDConfigurator.jsp` renders the imported sample flow set instead of an empty table.
- The IDE launches and the Navigator is inspectable.
- The Business Daemon webapp has a deployed `WEB-INF/config.xml`.
- `ProductDemoServlet` is present as a compiled runtime class.
- Local database-backed user/company workflows are implemented through the local servlet bridge.
- The sample legacy runtime currently renders `24` transaction descriptions and `46` query entries for the seeded `SF2AuthNet` flow set.

### Still required for true end-to-end transactional flows

- The deployed `WEB-INF/config.xml` must contain the correct runtime flow definitions, not just top-level daemon settings.
- The IDE build output must be propagated into the deployed webapp runtime configuration.
- `iwtransformationserver` must exist and respond at the configured URLs, or an equivalent replacement layer must be implemented.
- Transaction definitions in the workspace must produce non-empty runtime flow metadata.
- The webapp, IDE, and runtime config must agree on profile names and flow IDs.

## What Must Stay In Sync

For the legacy engine to behave cleanly, keep these aligned:

1. Workspace project name
   Used by the IDE Navigator and reflected in profile/flow management context.
2. Deployed `WEB-INF/config.xml`
   This is what the JSP pages and `ConfigContext` actually use at runtime.
3. `ConfigContext.profileDescriptors`
   This drives profile refresh, monitoring, and profile-specific flow assignment.
4. `ConfigContext.transactionList`
   This drives `BDConfigurator.jsp` and `ProductDemoServlet`.
5. Database company/profile records
   These drive login, hosted flags, and profile ownership context.
6. Transformation server URLs
   These must point to a real running engine if flows are expected to execute.
7. `TS_MODE` and the generated runtime endpoint URLs
   `START.bat` and login-time profile binding should agree on whether the active runtime is local or legacy-hosted.

## IDE And Wizard Sync

The web wizard and the Eclipse-based IDE do not use the same persistence layer for every piece of state:

- The JSP wizard saves per-company configuration XML in the profile database and rebinds it into `ConfigContext` at login.
- The IDE primarily reflects design-time project assets in `workspace/<Project>/configuration/**` and `workspace/<Project>/xslt/**`.

Practical implication:

- Launching through `START.bat` keeps both the web runtime and `iw_ide.exe` on the same `DB_MODE` / `TS_MODE` environment.
- The current team-default operating mode is `DB_MODE=supabase` with `TS_MODE=local`.
- The IDE will reflect the same runtime host mode because it is launched in the same session, but it does not automatically rewrite workspace project files every time a user saves wizard settings.
- Treat the wizard as profile/runtime state and the IDE as design-time flow/project state unless a deliberate export/import bridge is added.

The current bridge uses runtime-profile sidecar files instead of overwriting
`configuration/im/config.xml`:

- `workspace/IW_Runtime_Sync/profiles/<profile>/company_configuration.xml`
- `workspace/<MappedProject>/configuration/runtime_profiles/<profile>.xml`
- `workspace/GeneratedProfiles/<profile>/configuration/im/config.xml`

That keeps wizard-saved state visible in the IDE while avoiding destructive edits
to engine definition files that have a different XML schema.

## IDE Plugin Internals (iw_sdk_1.0.0)

**Confirmed via Session 10 deep dive (2026-03-09):**

The iw_sdk_1.0.0 Eclipse plugin (253 compiled classes, no source available) registers these automation-relevant extension points:

### Commands (callable via Eclipse workbench)
- `iw_sdk.buildProject` — Build Project
- `iw_sdk.compileXSLT` — Compile XSLT
- `iw_sdk.buildIM` — Build Integration Manager config
- `iw_sdk.buildTS` — Build Transformation Server config
- `iw_sdk.openProject` / `iw_sdk.closeProject` — Project lifecycle
- `iw_sdk.openFile` / `iw_sdk.openView` — File/view navigation
- `iw_sdk.newWizard` — New entity wizard

### Key Classes
- `com.inerweave.sdk.Designer` — Main IApplication entry point
- `com.inerweave.sdk.ConfigContext` (73KB) — Central state hub (transactionList, queryList, profileDescriptors, hosted DB settings)
- `com.inerweave.sdk.ProjectActions` — Programmatic project operations
- `com.inerweave.sdk.actions.BuildProjectAction` — Build trigger
- `com.iwtransactions.*` — 9 JAXB-generated XML schema classes (transactionType, transactionFlowType, datamapType, parameterType, etc.)

### What Source Code Would Enable
If the iw_sdk_1.0.0 source were available, these capabilities become possible:
1. **Live workspace refresh** — Add `IResourceChangeListener` to detect portal-synced file changes and trigger `IWorkspace.refreshLocal()`
2. **Automatic reverse sync** — Hook `BuildProjectAction` post-execution to HTTP POST changes back to `WorkspaceProfileSyncServlet?action=importProfile`
3. **Headless mode** — Write alternative `IApplication` that reuses ConfigContext, ProjectActions, and wizard classes without GUI
4. **ConfigContext introspection** — Understand exact state management, file format expectations, and caching behavior
5. **New commands/views** — Add "Push to Portal" / "Pull from Portal" menu items

### Current Limitation
Without source, the IDE is a black box for modification. All sync improvements must work at the filesystem, HTTP, or database layer.

## Bidirectional Sync Gap Analysis (2026-03-09)

### Direction 1: Web Portal → IDE (FULLY WORKING)

Automatic flow on startup and login:
1. User saves wizard → `ApiConfigurationServlet` upserts to `company_configurations` table
2. `WorkspaceProfileSyncServlet?action=exportAll` mirrors DB → workspace sidecar files
3. `WorkspaceProfileCompilerServlet?action=compileAll` generates engine overlay in `GeneratedProfiles/`
4. IDE can read these files from workspace (but does not auto-refresh)

Trigger points: `START.bat` (lines 135-137), `LocalLoginServlet`, `ApiLoginServlet`

### Direction 2: IDE → Web Portal (MANUAL ONLY — GAP)

No automatic reverse path exists. The only mechanism is:
- `WorkspaceProfileSyncServlet?action=importProfile&CurrentProfile=...&project=...`
- Must be called explicitly via HTTP (e.g., `scripts/sync_workspace_profiles.bat`)
- Never triggered automatically by the IDE

### Root Cause
Schema mismatch between the two systems:
- Portal stores: `<SF2QBConfiguration>` — flat wizard parameter values
- IDE stores: `<BusinessDaemonConfiguration>` — complex engine XML with nested `<TransactionDescription>` and `<Query>` elements

The sync bridge deliberately uses **sidecar files** (`runtime_profiles/`, `GeneratedProfiles/`) instead of overwriting engine definitions because the schemas are structurally incompatible.

### Solution: Filesystem Watcher Bridge (IMPLEMENTED)

`scripts/sync_bridge.ps1` — a PowerShell polling daemon that closes the IDE→Portal gap:

- **Watches:** `workspace/*/configuration/{im,ts}/config.xml`, `workspace/*/configuration/runtime_profiles/*`, `workspace/*/xslt/**`
- **Excludes:** `GeneratedProfiles/`, `IW_Runtime_Sync/`, `.metadata/` (portal-generated, not IDE)
- **On change:** Calls `WorkspaceProfileSyncServlet?action=importProfile` then `WorkspaceProfileCompilerServlet?action=compileProfile`
- **Debounce:** 2-second quiet period (configurable via `-DebounceSec`) prevents rapid-fire during builds
- **Cooldown:** 3-second post-sync pause + re-snapshot prevents re-triggering on our own compile output
- **Zero dependencies:** Uses built-in Windows PowerShell 5.1 (`Get-ChildItem` timestamp polling)

**Integration:** Auto-launched by `START.bat`, auto-stopped by `STOP.bat`. Standalone: `scripts/start_sync_bridge.bat` / `scripts/stop_sync_bridge.bat`. Logs: `logs/sync_bridge.log`.

**Live-tested 2026-03-09:** Both profiles imported + recompiled successfully. Bridge survives HTTP errors, detects changes within 2 seconds, stops cleanly via PID file.

## Recommended Path To Full Transactional Flow Support

### Phase 1: Keep startup deterministic

- Continue using `START.bat` as the canonical orchestration path.
- Treat `web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/config.xml` as runtime configuration and as a secret-bearing file.
- Verify the chosen `DB_MODE` and generated Tomcat context match the intended database before testing flows.

### Phase 2: Make runtime flow definitions explicit

- Confirm how the IDE's build step exports flow definitions into the deployed Business Daemon config.
- Capture or generate a known-good `WEB-INF/config.xml` that includes `TransactionDescription` data.
- Keep that runtime config under controlled change management; it is the actual source for IM page rendering.

### Phase 3: Close the transformation engine gap

- Deploy the missing `iwtransformationserver` webapp, or
- implement a compatible replacement for the endpoints expected by the Business Daemon runtime.

Without this step, the portal and database can be healthy while actual transformation execution still fails or remains partial.

### Phase 4: Validate with one real profile and one real flow

- Use one test company/profile.
- Use one known project in the IDE.
- Ensure the profile exists in the database.
- Ensure the same profile exists in `ConfigContext`.
- Ensure one flow appears in `BDConfigurator.jsp`.
- Run start/stop/update through `ProductDemoServlet`.
- Confirm logs and runtime counters change.

## Build Compatibility Note

When recompiling Business Daemon servlet sources under `WEB-INF/src`, the output must remain Java 8 compatible.

- The bundled Tomcat runtime uses Java 8 (`class file version 52`).
- Recompiled servlet classes must therefore use a Java 8 target, for example `javac --release 8`.
- If they are compiled with a newer default target, Tomcat marks the servlet unavailable with `UnsupportedClassVersionError`, and the mapped endpoint will fail even though the JSP pages still load.

## Short Version

The InterWeave engine is not just the IDE, and it is not just the JSP site.

It is the coordinated result of:

- `START.bat` orchestration
- Tomcat + `iw-business-daemon`
- `ConfigContext` runtime state from deployed config
- `ProductDemoServlet` and the JSP IM pages
- database-backed login/profile state
- a functioning transformation engine endpoint
- IDE-authored project definitions that are actually exported into runtime config

If any one of those layers is out of sync, the stack can appear partially healthy while real transaction flow execution is still broken.
