# AI Management Architecture — Full Bidirectional Control

**Date:** 2026-03-13
**Status:** Architecture Design
**Scope:** How an AI agent, the Eclipse IDE, and the React portal all manage the same InterWeave system simultaneously, with every party seeing every change in real time.

---

## Table of Contents

1. [The Three-Party Problem](#1-the-three-party-problem)
2. [Design Principles](#2-design-principles)
3. [Current Infrastructure (What Exists)](#3-current-infrastructure-what-exists)
4. [Architecture Overview](#4-architecture-overview)
5. [Layer 1: Workspace Management API](#5-layer-1-workspace-management-api)
6. [Layer 2: Build & Compile API](#6-layer-2-build--compile-api)
7. [Layer 3: Change Source Tracking](#7-layer-3-change-source-tracking)
8. [Layer 4: IDE Visibility Guarantees](#8-layer-4-ide-visibility-guarantees)
9. [Layer 5: AI Agent Interface Contract](#9-layer-5-ai-agent-interface-contract)
10. [Operation Matrix](#10-operation-matrix)
11. [Concurrency & Conflict Resolution](#11-concurrency--conflict-resolution)
12. [Data Flow Diagrams](#12-data-flow-diagrams)
13. [Implementation Phases](#13-implementation-phases)
14. [API Reference (New Endpoints)](#14-api-reference-new-endpoints)

---

## 1. The Three-Party Problem

Three actors concurrently manage the same InterWeave workspace:

| Actor | Interface | Can Modify Files? | Receives Notifications? | Closed Source? |
|-------|-----------|-------------------|------------------------|----------------|
| **Eclipse IDE** | SWT GUI | YES (direct filesystem) | On window focus (auto-refresh) | YES — black box |
| **React Portal** | Browser + Tomcat API | YES (via servlets → filesystem) | YES (SSE push, <1s) | No |
| **AI Agent** | HTTP API + filesystem | YES (via new API layer) | YES (SSE push, <1s) | No |

**Why this is hard:** The IDE is a closed-source Windows x86 binary. We cannot:
- Add event listeners inside it
- Call its internal methods
- Hook into its save/load pipeline
- Send it push notifications

**The only shared truth is the filesystem.** All three parties read and write the same `workspace/` directory tree. The architecture must ensure that when ANY party writes, the other two see the change.

---

## 2. Design Principles

1. **Filesystem is the source of truth** — all operations ultimately read/write workspace files
2. **Sync bridge is the nervous system** — detects changes from ANY source (IDE, portal, AI) via filesystem polling
3. **SSE is the real-time channel** — pushes events to React UI and AI consumers in <1 second
4. **IDE refreshes on focus** — Eclipse 3.1's built-in `IResource` change detection handles this automatically
5. **Every operation is atomic** — write all related files, then trigger sync notification as a single unit
6. **Change source tracking** — every modification tagged with origin (`ide`, `portal`, `ai`, `bridge`, `system`)
7. **Non-destructive coexistence** — AI/portal changes never break IDE expectations; IDE changes never break API state

---

## 3. Current Infrastructure (What Exists)

### 3.1 Sync Bridge (`scripts/sync_bridge.ps1`)
- **Mechanism:** PowerShell polling daemon (1-second tick, 2-second debounce)
- **Watches:** `workspace/*/configuration/{im,ts}/config.xml`, `workspace/*/xslt/**/*.xslt`, `workspace/*/configuration/runtime_profiles/*`
- **Excludes:** `GeneratedProfiles/`, `IW_Runtime_Sync/`, `.metadata/`
- **On change:** Calls `WorkspaceProfileSyncServlet?action=importProfile` → `WorkspaceProfileCompilerServlet?action=compileProfile`
- **Post-sync:** 3-second cooldown + re-snapshot to prevent re-triggering on own output
- **Lifecycle:** Auto-launched by `START.bat`, auto-stopped by `STOP.bat`

### 3.2 SSE Push (`SyncEventServlet`)
- **Endpoint:** `GET /api/sync/events`
- **Events:** `connected`, `push-complete`, `pull-complete`, `sync-update`
- **Payload:** `{ profileName, source, timestamp }`
- **Keepalive:** 15-second SSE comment to prevent proxy timeout
- **Static broadcast API:** `SyncEventServlet.broadcast(eventType, profileName, source)` — callable from any servlet

### 3.3 Workspace Sync Servlets
- **WorkspaceProfileSyncServlet** — `exportAll`, `exportProfile`, `importProfile`
- **WorkspaceProfileCompilerServlet** — `compileAll`, `compileProfile`
- Both restricted to loopback (localhost only)

### 3.4 API Servlets (operational)
- **ApiFlowManagementServlet** — list/start/stop/schedule/initialize/properties
- **ApiConfigurationServlet** — wizard CRUD, credentials, profiles, test connection
- **ApiWorkspaceSyncServlet** — sync status, push/pull triggers
- **ApiEngineStatusServlet** — engine health check
- **SyncEventServlet** — SSE real-time push

### 3.5 React Hooks (consumer side)
- **`useSyncSSE`** — opens EventSource, invalidates React Query caches on sync events
- **`useSync`** — sync status polling + push/pull triggers
- **`useFlows`** — flow listing with auto-refresh
- **`useConfiguration`** — wizard/credentials/profiles

---

## 4. Architecture Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                        AI AGENT                                  │
│  (Claude Code / external automation / scheduled tasks)           │
│                                                                  │
│  Interacts via:                                                  │
│    • HTTP API calls to Tomcat servlets                           │
│    • SSE consumption for real-time notifications                 │
│    • Filesystem reads (for XSLT inspection / advanced ops)       │
└─────────┬───────────────────────────────────────┬────────────────┘
          │ HTTP (JSON API)                       │ SSE (events)
          ▼                                       ▲
┌─────────────────────────────────────────────────────────────────┐
│                     TOMCAT (Port 9090)                            │
│                                                                  │
│  ┌──────────────────────────────────────────────────────┐        │
│  │  NEW: ApiWorkspaceManagementServlet                   │        │
│  │    POST /api/workspace/projects                       │        │
│  │    POST /api/workspace/projects/{name}/transactions   │        │
│  │    POST /api/workspace/projects/{name}/queries        │        │
│  │    POST /api/workspace/projects/{name}/connections    │        │
│  │    DELETE, PUT for CRUD operations                    │        │
│  └──────────────────────────────────────────────────────┘        │
│  ┌──────────────────────────────────────────────────────┐        │
│  │  NEW: ApiBuildServlet                                 │        │
│  │    POST /api/build/compile-xslt                       │        │
│  │    POST /api/build/compile-project                    │        │
│  │    GET  /api/build/status/{buildId}                   │        │
│  └──────────────────────────────────────────────────────┘        │
│  ┌──────────────────────────────────────────────────────┐        │
│  │  EXISTING: ApiFlowManagementServlet                   │        │
│  │  EXISTING: ApiConfigurationServlet                    │        │
│  │  EXISTING: ApiWorkspaceSyncServlet                    │        │
│  │  EXISTING: SyncEventServlet (SSE)                     │        │
│  └──────────────────────────────────────────────────────┘        │
│                          │                                       │
│                   writes/reads                                   │
│                          ▼                                       │
└─────────────────────────────────────────────────────────────────┘
                           │
               ┌───────────┼───────────┐
               ▼           ▼           ▼
┌──────────────────┐ ┌──────────┐ ┌──────────────────┐
│  workspace/      │ │ Database │ │ GeneratedProfiles/│
│  (filesystem)    │ │(Supabase)│ │ (engine overlays) │
│                  │ │          │ │                    │
│  THE UNIVERSAL   │ │ Portal   │ │ Compiler output    │
│  TRUTH           │ │ config   │ │ (auto-generated)   │
└────────┬─────────┘ └──────────┘ └────────────────────┘
         │
         │ filesystem polling (1s tick)
         ▼
┌──────────────────────────────────────────────┐
│  SYNC BRIDGE (sync_bridge.ps1)               │
│                                              │
│  Detects changes from ANY source:            │
│    • IDE saves a file → bridge detects       │
│    • AI API writes a file → bridge detects   │
│    • Portal export writes → bridge detects   │
│                                              │
│  Actions on detection:                       │
│    1. importProfile → DB                     │
│    2. compileProfile → GeneratedProfiles/    │
│    3. SSE broadcast → React + AI consumers   │
└──────────────────────────────────────────────┘
         │
         │ reads workspace on focus
         ▼
┌──────────────────────────────────────────────┐
│  ECLIPSE IDE (iw_ide.exe)                    │
│                                              │
│  Sees all changes because:                   │
│    • Files are written to workspace/         │
│    • Eclipse refreshes IResource on focus    │
│    • iw_workspace_init detects new projects  │
│    • NavigationView re-reads config.xml      │
│                                              │
│  BLACK BOX — cannot be modified              │
└──────────────────────────────────────────────┘
```

---

## 5. Layer 1: Workspace Management API

### Purpose
Provide HTTP endpoints for every operation that the IDE's wizards and views perform, so that an AI agent (or the React portal) can create and modify integration projects without the IDE.

### New Servlet: `ApiWorkspaceManagementServlet`

**Mapped to:** `/api/workspace/*`

#### 5.1 Create Project

```
POST /api/workspace/projects
Body: {
  "name": "My_Integration_Project",
  "solutionType": "CRM2QB",
  "description": "Creatio to QuickBooks sync",
  "hosted": false,
  "tsUrl": "http://localhost:9090/iwtransformationserver"
}
```

**What it does (mirrors NewProjectWizard):**
1. Creates directory structure under `workspace/{name}/`:
   ```
   configuration/
     im/config.xml          ← BusinessDaemonConfiguration template
     ts/config.xml          ← TransformationServerConfiguration template
     runtime_profiles/      ← empty dir
   xslt/
     include/dataconnections.xslt  ← connection param template
     Site/
       include/
         appconstants.xslt  ← session variable template
         globals.xslt        ← imports appconstants
         sitetran.xslt       ← copy of sitetran_host or _ent
       new/
         transactions.xslt   ← master stylesheet template
         include/soltran.xslt ← empty soltran skeleton
         xml/transactions.xml ← empty build output
   classes/
     iwtransformationserver/ ← empty dir for compiled output
   ```
2. Adds mapping to `config/workspace-profile-map.properties`
3. Triggers `SyncEventServlet.broadcast("project-created", null, "ai")`

**IDE sees:** New project appears in Navigator on next refresh (focus switch) or when `iw_workspace_init_1.0.0` AutoImportStartup runs.

#### 5.2 Create Transaction

```
POST /api/workspace/projects/{projectName}/transactions
Body: {
  "id": "CRMAcctSync2QB",
  "description": "Sync CRM Accounts to QuickBooks",
  "solution": "CRM2QB",
  "tranname": "CRMAcctLogin",
  "interval": 60000,
  "shift": 0,
  "runAtStartUp": false,
  "isStateful": true,
  "parameters": [
    { "name": "QueryStartTime", "value": "2024-01-01 00:00:00.0" }
  ]
}
```

**What it does (mirrors NewTransactionWizard):**
1. Reads `workspace/{project}/configuration/im/config.xml`
2. Parses XML DOM, appends new `<TransactionDescription>` element with all attributes and `<Parameter>` children
3. Writes updated XML back to file
4. Optionally creates stub XSLT at `workspace/{project}/xslt/{tranname}.xslt`
5. Sync bridge detects file change → importProfile → compileProfile → SSE broadcast

**IDE sees:** New transaction appears in Navigator tree under "Integration Flows > Transaction Flows" on next config.xml reload.

#### 5.3 Create Query

```
POST /api/workspace/projects/{projectName}/queries
Body: {
  "id": "CRMAcct2QBQ",
  "description": "Creatio Account to QuickBooks Customer query",
  "solution": "CRM2QB",
  "tranname": "CRMAcctQuery",
  "parameters": [
    { "name": "UseIdorName", "value": "Id" },
    { "name": "TransactionSourceName", "value": "" }
  ]
}
```

**What it does (mirrors NewQueryWizard):**
1. Same config.xml DOM manipulation, appends `<Query>` element
2. Sync bridge propagates

#### 5.4 Create/Update Connection

```
POST /api/workspace/projects/{projectName}/connections
Body: {
  "target": "source",
  "driver": "com.interweave.adapter.rest.IWRestJson",
  "url": "https://mycrm.creatio.com",
  "user": "admin",
  "password": "secret123"
}
```

**What it does:**
1. Updates XSLT params in `workspace/{project}/xslt/include/dataconnections.xslt`:
   - `target=source` → `$iwdriver`, `$iwurl`, `$iwuser`, `$password`
   - `target=destination` → `$msdriver`, `$msurl`, `$msuser`, `$mspassword`
2. These are XSLT `<xsl:param>` values that get injected at transformation time

#### 5.5 Update Transaction/Query

```
PUT /api/workspace/projects/{projectName}/transactions/{id}
Body: { "interval": 120000, "description": "Updated description" }
```

**What it does:**
1. Finds `<TransactionDescription Id="{id}">` in config.xml DOM
2. Updates specified attributes
3. Writes back, sync bridge propagates

#### 5.6 Delete Transaction/Query

```
DELETE /api/workspace/projects/{projectName}/transactions/{id}
```

**What it does:**
1. Removes `<TransactionDescription Id="{id}">` from config.xml DOM
2. Optionally removes corresponding soltran.xslt `<xsl:when>` block
3. Writes back, sync bridge propagates

#### 5.7 List Projects / Get Project Details

```
GET /api/workspace/projects
GET /api/workspace/projects/{name}
```

**Returns:** Project metadata, transaction list, query list, connection info, XSLT file list, compiled class list, last modified timestamps.

---

## 6. Layer 2: Build & Compile API

### Purpose
Replicate the IDE's `ProjectActions` 4-stage build pipeline as HTTP endpoints.

### New Servlet: `ApiBuildServlet`

**Mapped to:** `/api/build/*`

#### 6.1 Compile XSLT

```
POST /api/build/compile-xslt
Body: {
  "projectName": "Creatio_QuickBooks_Integration",
  "xsltFiles": ["CRMAcctSync2QB.xslt"],  // optional, defaults to all
  "hosted": true
}
```

**What it does (mirrors ProjectActions stage 1):**
1. Copies correct `sitetran` variant (`_host.xslt` or `_ent.xslt`) → `sitetran.xslt`
2. Builds `soltran.xslt` from `soltran_start.dat` + optimized content + `soltran_end.dat`
3. Invokes XSLTC compiler as subprocess:
   ```
   java -cp "xsltc.jar;xalan.jar;serializer.jar"
        org.apache.xalan.xsltc.cmdline.Compile
        -o {TransformName}
        -d workspace/{project}/classes/iwtransformationserver
        workspace/{project}/xslt/{TransformName}.xslt
   ```
4. Returns compilation results (success/failure per file, error messages)
5. Sync bridge detects new `.class` files → propagates

**IDE sees:** Compiled `.class` files appear in `classes/iwtransformationserver/` — same location IDE would write to. No conflict.

#### 6.2 Full Project Build

```
POST /api/build/compile-project
Body: {
  "projectName": "Creatio_QuickBooks_Integration",
  "stages": 3,     // bitmask: 1=XSLT, 2=JAR, 4=IM WAR, 8=TS WAR
  "hosted": true,
  "splitTransactions": false
}
```

**What it does (mirrors ProjectActions full pipeline):**
- Stage 1 (0x1): Compile all XSLT → `.class` files
- Stage 2 (0x2): Package classes → JAR in `.deployables/jar/`
- Stage 3 (0x4): Build IM WAR from template + merged config
- Stage 4 (0x8): Build TS WAR from template + XSLT JAR

**Note:** Stages 3-4 (WAR building) are deployment-time operations. For server-mode operation, only stages 1-2 are typically needed.

#### 6.3 Build Status

```
GET /api/build/status/{buildId}
```

For long-running builds, returns progress and error details.

---

## 7. Layer 3: Change Source Tracking

### Purpose
Every modification must be tagged with its origin so all parties can determine who changed what and when.

### Extended SSE Event Payload

```json
{
  "eventType": "sync-update",
  "profileName": "Demo_Company_Inc:demo@sample.com",
  "source": "ai",
  "operation": "transaction-created",
  "details": {
    "projectName": "Creatio_QuickBooks_Integration",
    "entityType": "TransactionDescription",
    "entityId": "CRMAcctSync2QB"
  },
  "timestamp": 1710345678000
}
```

### Source Values

| Source | Meaning | Triggered By |
|--------|---------|-------------|
| `ide` | Eclipse IDE saved a file | Sync bridge detecting workspace change (non-AI, non-portal origin) |
| `portal` | React portal or JSP wizard | ApiConfigurationServlet, WorkspaceProfileSyncServlet export |
| `ai` | AI agent operation | New ApiWorkspaceManagementServlet, ApiBuildServlet |
| `bridge` | Sync bridge reconciliation | importProfile after IDE change detection |
| `system` | Startup/initialization | START.bat exportAll/compileAll |

### Change Manifest (`.ai_change_manifest`)

When the AI makes changes, it writes a sidecar file alongside the modified file:

```
workspace/{project}/configuration/im/.ai_change_manifest
```

Contents:
```json
{
  "lastModifiedBy": "ai",
  "timestamp": 1710345678000,
  "operation": "transaction-created",
  "entityId": "CRMNewSync",
  "sessionId": "claude-session-abc123"
}
```

The sync bridge reads this manifest to determine the change source before broadcasting. If no manifest exists, the source is assumed to be `ide` (human-initiated).

---

## 8. Layer 4: IDE Visibility Guarantees

### How Eclipse Sees External Changes

Eclipse 3.1 has built-in filesystem change detection:

1. **On window focus:** When the user clicks back to the IDE window, Eclipse calls `IResource.refreshLocal()` on the workspace. This picks up any files changed by the AI or portal while the IDE was in the background.

2. **AutoImportStartup plugin** (`iw_workspace_init_1.0.0`): Detects new project directories in `workspace/` and imports them into the Eclipse workspace on startup.

3. **NavigationView re-read:** When a project's `config.xml` changes and the user opens Configuration, the IDE's `ConfigContext.readIMConfigContext()` re-parses the XML. New `<TransactionDescription>` and `<Query>` elements appear in the tree.

### What the IDE Will See After Each AI Operation

| AI Operation | IDE Visibility | When Visible |
|---|---|---|
| Create Project | New project in Navigator tree | On restart (AutoImportStartup) or manual Import Existing Project |
| Create Transaction | New entry under "Transaction Flows" | On next config.xml read (open project/perspective switch) |
| Create Query | New entry under "Queries" | On next config.xml read |
| Create Connection | Updated `dataconnections.xslt` params | Immediately in XSLT editor (file refresh) |
| Compile XSLT | New `.class` files in `classes/` | Immediately visible in filesystem; IDE doesn't display these |
| Update Transaction | Modified attributes in config view | On next config.xml read |
| Delete Transaction | Entry disappears from tree | On next config.xml read |

### What the IDE Will NOT See (and why it's fine)

- **SSE events** — IDE has no web client. But the IDE user sees file changes on focus, which is the equivalent.
- **Change manifests** — The `.ai_change_manifest` files are invisible to the IDE (it doesn't look for them). They're only consumed by the sync bridge.
- **Build status** — The IDE has its own build pipeline; the AI build servlet produces identical output files.

---

## 9. Layer 5: AI Agent Interface Contract

### How the AI Agent Interacts

The AI agent is any external automation (Claude Code, scheduled tasks, CI/CD, webhooks) that manages the InterWeave system via HTTP.

#### Authentication
- Same as React portal: `POST /api/auth/login` → receives Bearer token
- Token passed in `Authorization: Bearer {token}` header on all API calls
- `ApiTokenAuthFilter` bridges Bearer token to Tomcat session

#### Typical AI Workflow

```
1. AUTHENTICATE
   POST /api/auth/login { "email": "ai-operator@company.com", "password": "..." }
   → { "token": "uuid-xxx" }

2. INSPECT CURRENT STATE
   GET /api/workspace/projects
   → [{ "name": "CRM2QB", "transactions": 7, "queries": 7, "lastModified": "..." }]

   GET /api/flows
   → { "scheduledFlows": [...], "queryFlows": [...] }

3. MAKE CHANGES (write operations)
   POST /api/workspace/projects/CRM2QB/transactions
   { "id": "NewSync", "tranname": "NewSyncLogin", ... }
   → { "success": true, "fileModified": "config.xml" }

4. BUILD (compile XSLT if new transformer created)
   POST /api/build/compile-xslt
   { "projectName": "CRM2QB", "xsltFiles": ["NewSync.xslt"] }
   → { "success": true, "compiled": ["NewSync.class"] }

5. RELOAD ENGINE (bind new flows into runtime)
   POST /api/flows/initialize
   → { "flowCount": 8, "queryCount": 7 }

6. OPERATE (start/stop flows)
   POST /api/flows/start { "flowIndex": 7 }
   → { "running": true }

7. MONITOR (via SSE)
   GET /api/sync/events (EventSource)
   ← SSE: event: sync-update, data: { "source": "ide", "operation": "..." }
```

#### AI Listening for IDE Changes

The AI can subscribe to SSE events to know when the IDE user makes changes:

```javascript
const es = new EventSource('/iw-business-daemon/api/sync/events?token=...');
es.addEventListener('pull-complete', (e) => {
  const data = JSON.parse(e.data);
  if (data.source === 'ide') {
    // IDE user saved something → re-read workspace state
    // AI can inspect changes and react accordingly
  }
});
```

---

## 10. Operation Matrix

Complete mapping of every IDE operation to its server-side equivalent and AI accessibility:

| # | IDE Operation | IDE Class | Server Equivalent | API Endpoint | Status |
|---|---|---|---|---|---|
| 1 | Open Project | `NavigationView` | Filesystem scan | `GET /api/workspace/projects` | **NEW** |
| 2 | Create Project | `NewProjectWizard` | Dir scaffolding + XML templates | `POST /api/workspace/projects` | **NEW** |
| 3 | Create Transaction | `NewTransactionWizard` | config.xml DOM insert | `POST /api/workspace/projects/{p}/transactions` | **NEW** |
| 4 | Create Query | `NewQueryWizard` | config.xml DOM insert | `POST /api/workspace/projects/{p}/queries` | **NEW** |
| 5 | Create Transaction Flow | `NewTransactionFlowWizard` | config.xml + soltran.xslt | `POST /api/workspace/projects/{p}/flows` | **NEW** |
| 6 | Edit Transaction | `TransactionDetailsView` | config.xml DOM update | `PUT /api/workspace/projects/{p}/transactions/{id}` | **NEW** |
| 7 | Edit Connection | `ConnectionView` | dataconnections.xslt update | `PUT /api/workspace/projects/{p}/connections` | **NEW** |
| 8 | Edit IM Config | `ConfigBDView` | config.xml root attributes | `PUT /api/workspace/projects/{p}/config/im` | **NEW** |
| 9 | Edit TS Config | `ConfigTSView` | config.xml root attributes | `PUT /api/workspace/projects/{p}/config/ts` | **NEW** |
| 10 | Compile XSLT | `ProjectActions(1)` | Xalan XSLTC subprocess | `POST /api/build/compile-xslt` | **NEW** |
| 11 | Build JAR | `ProjectActions(2)` | JDK jar command | `POST /api/build/compile-project` | **NEW** |
| 12 | Build IM WAR | `ProjectActions(4)` | ZIP assembly | `POST /api/build/compile-project` | **NEW** |
| 13 | Build TS WAR | `ProjectActions(8)` | ZIP assembly | `POST /api/build/compile-project` | **NEW** |
| 14 | Start Flow | `BDConfigurator` | ConfigContext.runTransactionThread | `POST /api/flows/start` | **EXISTS** |
| 15 | Stop Flow | `BDConfigurator` | ConfigContext.stopTransactionThread | `POST /api/flows/stop` | **EXISTS** |
| 16 | Edit Schedule | `BDConfigurator` | Thread interval/shift | `PUT /api/flows/schedule` | **EXISTS** |
| 17 | Flow Properties | `BDConfigurator` | getVariableParameters | `GET /api/flows/properties` | **EXISTS** |
| 18 | Initialize Profile | `LoginServlet` | bindHostedProfile | `POST /api/flows/initialize` | **EXISTS** |
| 19 | Save All | `BDConfigurator` | adminSaveTransactions | `POST /api/flows/submit` | **EXISTS** |
| 20 | Wizard Config | `CompanyConfig.jsp` | DB CRUD | `GET/PUT /api/config/wizard` | **EXISTS** |
| 21 | Test Credentials | `CompanyConfig.jsp` | HTTP probe | `POST /api/config/credentials/test` | **EXISTS** |
| 22 | Export Profiles | internal | DB → workspace files | `WorkspaceProfileSyncServlet` | **EXISTS** |
| 23 | Import Profiles | internal | workspace → DB | `WorkspaceProfileSyncServlet` | **EXISTS** |
| 24 | Compile Profiles | internal | Generate engine overlays | `WorkspaceProfileCompilerServlet` | **EXISTS** |
| 25 | Edit XSLT (visual) | `XSLTEditorView` | *No server equivalent* | — | **FUTURE** |
| 26 | Edit Template (visual) | `TemplateEditorView` | *No server equivalent* | — | **FUTURE** |
| 27 | Flow Wiring (visual) | `TransactionFlowView` | soltran.xslt + nexttransaction | `POST /api/workspace/.../flow-wiring` | **NEW** |
| 28 | Delete Transaction | `NavigationView` | config.xml DOM remove | `DELETE /api/workspace/projects/{p}/transactions/{id}` | **NEW** |

**Coverage:** 22 of 28 operations are either already implemented or defined for implementation. The remaining 6 (visual XSLT editor, visual template editor, and 4 advanced flow-wiring scenarios) require significant GUI-equivalent work and are deferred to the InterWoven prototype phase.

---

## 11. Concurrency & Conflict Resolution

### Scenario: AI and IDE Edit config.xml Simultaneously

```
Time 0s: AI reads config.xml (7 transactions)
Time 1s: IDE user adds transaction "NewTx" and saves
Time 2s: Sync bridge detects IDE change, imports to DB
Time 3s: AI writes config.xml (adding "AiTx" — but based on stale read)
         ← CONFLICT: IDE's "NewTx" could be lost
```

### Resolution Strategy: Read-Modify-Write with File Locking

1. **API servlet reads config.xml** — gets current DOM
2. **Applies the requested change** to the current DOM (not a template)
3. **Writes atomically** — write to `.tmp`, then rename
4. **No full-file replacement** — always read-modify-write, never overwrite from template

This ensures that even if the IDE added "NewTx" between the AI's initial inspection and its write, the AI's DOM manipulation starts from the latest file state.

### Scenario: Portal Wizard and AI Write Different Config

The portal wizard stores `<SF2QBConfiguration>` (flat params) in the database. The AI writes `<BusinessDaemonConfiguration>` (engine XML) to workspace files. These are **different schemas in different storage locations** — they don't conflict because the sync bridge uses sidecar files, not overwrites.

### Optimistic Locking (Database Side)

Already implemented: `company_configurations` table has `version`, `last_modified_by`, `last_modified_source` columns. PUT returns HTTP 409 with field-level diff on conflict (via `XmlConfigDiffer`).

---

## 12. Data Flow Diagrams

### 12.1 AI Creates a Transaction (full flow)

```
AI Agent                    Tomcat                      Filesystem              Sync Bridge           React UI          IDE
  │                           │                            │                       │                    │                │
  │ POST /api/workspace/      │                            │                       │                    │                │
  │   projects/CRM2QB/        │                            │                       │                    │                │
  │   transactions            │                            │                       │                    │                │
  │ ─────────────────────────>│                            │                       │                    │                │
  │                           │ 1. Read config.xml         │                       │                    │                │
  │                           │ ───────────────────────────>                       │                    │                │
  │                           │                            │                       │                    │                │
  │                           │ 2. DOM: add <TxDescription>│                       │                    │                │
  │                           │                            │                       │                    │                │
  │                           │ 3. Write config.xml        │                       │                    │                │
  │                           │ ───────────────────────────>                       │                    │                │
  │                           │                            │                       │                    │                │
  │                           │ 4. Write .ai_change_manifest                      │                    │                │
  │                           │ ───────────────────────────>                       │                    │                │
  │                           │                            │                       │                    │                │
  │  { "success": true }      │                            │                       │                    │                │
  │ <─────────────────────────│                            │                       │                    │                │
  │                           │                            │ 5. Poll detects       │                    │                │
  │                           │                            │    config.xml change  │                    │                │
  │                           │                            │ ─────────────────────>│                    │                │
  │                           │                            │                       │                    │                │
  │                           │ 6. importProfile (HTTP)    │                       │                    │                │
  │                           │ <──────────────────────────────────────────────────│                    │                │
  │                           │                            │                       │                    │                │
  │                           │ 7. compileProfile (HTTP)   │                       │                    │                │
  │                           │ <──────────────────────────────────────────────────│                    │                │
  │                           │                            │                       │                    │                │
  │                           │ 8. SSE broadcast           │                       │                    │                │
  │                           │    ("sync-update",         │                       │                    │                │
  │                           │     source: "ai")          │                       │                    │                │
  │                           │ ────────────────────────────────────────────────────────────────────────>│                │
  │                           │                            │                       │                    │                │
  │                           │                            │                       │                    │ 9. invalidate  │
  │                           │                            │                       │                    │    queries     │
  │                           │                            │                       │                    │ (auto-refresh) │
  │                           │                            │                       │                    │                │
  │                           │                            │                       │                    │                │ 10. User clicks
  │                           │                            │                       │                    │                │     IDE window
  │                           │                            │                       │                    │                │     → auto-refresh
  │                           │                            │                       │                    │                │     → new TX visible
```

### 12.2 IDE User Edits, AI Sees It

```
IDE                     Filesystem              Sync Bridge           Tomcat                 AI (SSE)
  │                        │                       │                    │                      │
  │ 1. User saves          │                       │                    │                      │
  │    config.xml          │                       │                    │                      │
  │ ──────────────────────>│                       │                    │                      │
  │                        │ 2. Poll detects       │                    │                      │
  │                        │    change             │                    │                      │
  │                        │ ─────────────────────>│                    │                      │
  │                        │                       │                    │                      │
  │                        │                       │ 3. importProfile   │                      │
  │                        │                       │ ──────────────────>│                      │
  │                        │                       │                    │ 4. DB updated        │
  │                        │                       │ 5. compileProfile  │                      │
  │                        │                       │ ──────────────────>│                      │
  │                        │                       │                    │ 6. Overlay generated │
  │                        │                       │                    │                      │
  │                        │                       │                    │ 7. SSE broadcast     │
  │                        │                       │                    │    source: "bridge"   │
  │                        │                       │                    │ ────────────────────>│
  │                        │                       │                    │                      │
  │                        │                       │                    │                      │ 8. AI receives
  │                        │                       │                    │                      │    "pull-complete"
  │                        │                       │                    │                      │    source: "bridge"
  │                        │                       │                    │                      │    → re-reads state
```

---

## 13. Implementation Phases

### Phase 1: Workspace Read API + Build API (LOW effort, HIGH value)

**New servlet:** `ApiWorkspaceManagementServlet` (read-only initially)
- `GET /api/workspace/projects` — list all workspace projects with metadata
- `GET /api/workspace/projects/{name}` — full project details (transactions, queries, connections, XSLT inventory, compiled classes)

**New servlet:** `ApiBuildServlet` (compile only)
- `POST /api/build/compile-xslt` — invoke Xalan XSLTC for specified files

**Effort:** ~200 lines of Java (servlet boilerplate + directory scanning + subprocess exec)
**Value:** AI can inspect and compile any project. Combined with existing flow management APIs, this covers the most common operational tasks.

### Phase 2: Workspace Write API (MEDIUM effort, HIGH value)

Add write operations to `ApiWorkspaceManagementServlet`:
- `POST /api/workspace/projects` — create project
- `POST /api/workspace/projects/{name}/transactions` — create transaction
- `POST /api/workspace/projects/{name}/queries` — create query
- `PUT /api/workspace/projects/{name}/transactions/{id}` — update
- `DELETE /api/workspace/projects/{name}/transactions/{id}` — delete

**Effort:** ~400 lines of Java (DOM manipulation + atomic writes)
**Value:** AI can fully manage integration projects — create, configure, and modify everything the wizards do.

### Phase 3: Connection + Flow Wiring API (MEDIUM effort)

- `POST/PUT /api/workspace/projects/{name}/connections` — manage dataconnections.xslt
- `POST /api/workspace/projects/{name}/flow-wiring` — manage nexttransaction chains in soltran.xslt

**Effort:** ~300 lines of Java (XSLT template manipulation)
**Value:** AI can set up end-to-end integration flows (source→transform→destination with chaining).

### Phase 4: Enhanced Change Tracking (LOW effort)

- Add `.ai_change_manifest` sidecar writes to all new API servlets
- Extend `SyncEventServlet.broadcast()` payload with `operation` and `details` fields
- Update sync bridge to read manifests and pass source to broadcast

**Effort:** ~100 lines of Java + ~50 lines of PowerShell
**Value:** All three parties can distinguish who changed what. React UI can show "AI modified this 30 seconds ago" indicators.

### Phase 5: Full Project Build Pipeline (LOW effort)

- Complete `ApiBuildServlet` with stages 2-4 (JAR, IM WAR, TS WAR)
- Add soltran assembly (soltran_start.dat + optimized content + soltran_end.dat)

**Effort:** ~200 lines of Java (ZIP manipulation + file assembly)
**Value:** AI can produce deployable WAR files for standalone deployment.

---

## 14. API Reference (New Endpoints)

### Workspace Management

| Method | Path | Body | Response | Auth |
|--------|------|------|----------|------|
| `GET` | `/api/workspace/projects` | — | `{ projects: [{ name, transactionCount, queryCount, lastModified }] }` | Bearer |
| `GET` | `/api/workspace/projects/{name}` | — | Full project details | Bearer |
| `POST` | `/api/workspace/projects` | `{ name, solutionType, description, hosted, tsUrl }` | `{ success, projectPath }` | Bearer + Admin |
| `POST` | `/api/workspace/projects/{name}/transactions` | TransactionDescription JSON | `{ success, entityId }` | Bearer + Admin |
| `POST` | `/api/workspace/projects/{name}/queries` | Query JSON | `{ success, entityId }` | Bearer + Admin |
| `PUT` | `/api/workspace/projects/{name}/transactions/{id}` | Partial update JSON | `{ success }` | Bearer + Admin |
| `DELETE` | `/api/workspace/projects/{name}/transactions/{id}` | — | `{ success }` | Bearer + Admin |
| `POST` | `/api/workspace/projects/{name}/connections` | `{ target, driver, url, user, password }` | `{ success }` | Bearer + Admin |
| `POST` | `/api/workspace/projects/{name}/flow-wiring` | `{ transactionName, nextTransactions: [...] }` | `{ success }` | Bearer + Admin |

### Build

| Method | Path | Body | Response | Auth |
|--------|------|------|----------|------|
| `POST` | `/api/build/compile-xslt` | `{ projectName, xsltFiles?, hosted? }` | `{ success, compiled: [], errors: [] }` | Bearer + Admin |
| `POST` | `/api/build/compile-project` | `{ projectName, stages, hosted?, splitTransactions? }` | `{ success, buildId }` | Bearer + Admin |
| `GET` | `/api/build/status/{buildId}` | — | `{ status, progress, errors }` | Bearer |

### Enhanced Sync Events (SSE)

```
event: sync-update
data: {
  "profileName": "Demo:demo@sample.com",
  "source": "ai",
  "operation": "transaction-created",
  "details": { "projectName": "CRM2QB", "entityId": "NewSync" },
  "timestamp": 1710345678000
}
```

---

## Summary

This architecture enables **full AI management of the InterWeave platform** while preserving complete IDE compatibility:

- **AI creates a project** → IDE user opens it in Navigator
- **AI adds a transaction** → IDE user sees it in the tree
- **AI compiles XSLT** → IDE user sees `.class` files
- **IDE user edits XSLT** → AI receives SSE event, can re-read and react
- **IDE user builds** → sync bridge imports to DB, AI sees updated state
- **Portal wizard saves** → AI receives SSE event, workspace files updated

The filesystem remains the universal truth. The sync bridge ensures propagation. SSE ensures real-time notification. And the IDE's built-in refresh-on-focus behavior ensures GUI visibility — no modifications to the closed-source Eclipse IDE are needed.
