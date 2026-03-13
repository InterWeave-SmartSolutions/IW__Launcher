# InterWeave IDE Deep Dive — Complete Architecture Analysis

**Date:** 2026-03-13
**Scope:** Full decompilation and analysis of `iw_sdk_1.0.0` (308 classes), workspace config model, build pipeline, runtime engine, and headless feasibility.

---

## Table of Contents

1. [Plugin Architecture](#1-plugin-architecture)
2. [Class Inventory](#2-class-inventory)
3. [ConfigContext — The Central State Hub](#3-configcontext--the-central-state-hub)
4. [Build Pipeline](#4-build-pipeline)
5. [JAXB Transaction Model](#5-jaxb-transaction-model)
6. [Config File Formats](#6-config-file-formats)
7. [IDE GUI Components](#7-ide-gui-components)
8. [IDE ↔ Web Portal Data Flow](#8-ide--web-portal-data-flow)
9. [Headless / Server-Mode Feasibility](#9-headless--server-mode-feasibility)
10. [Gap Analysis](#10-gap-analysis)
11. [Recommended Path Forward](#11-recommended-path-forward)

---

## 1. Plugin Architecture

**Plugin:** `iw_sdk_1.0.0` — Eclipse 3.1 RCP application
**Vendor:** Integration Technologies, Inc.
**Version:** 2.41 (IDE Build 172, IM Build 765, TS Build 712)
**Entry point:** `com.inerweave.sdk.Designer` (IPlatformRunnable)
**Activator:** `iw_sdk.Iw_sdkPlugin`

**Bundle-ClassPath:** 6 Apache XML JARs + `bin/` (compiled classes):
- `lib/resolver.jar`, `lib/serializer.jar`, `lib/xalan.jar`
- `lib/xercesImpl.jar`, `lib/xml-apis.jar`, `lib/xsltc.jar`

**Eclipse dependencies:** `org.eclipse.ui`, `org.eclipse.ui.console`, `org.eclipse.core.runtime`, `org.eclipse.core.resources`, `org.eclipse.ui.ide`

**Extension points registered:**
- 1 application (`iw_sdk_gui_app`)
- 3 perspectives (Configuration, Template/XSLT Editor, Transaction Flow)
- 11 views (NavigationView, ConfigBDView, ConfigTSView, TransactionFlowView, XSLTEditorView, TemplateEditorView, DataMapView, ConnectionView, AccessParameterView, TransactionDetailsView, ProjectView)
- 10 commands (openProject, closeProject, projectProperties, openFile, newWizard, buildProject, compileXSLT, buildIM, buildTS, openView)
- 4 new-entity wizards (New Project, New Transaction, New Transaction Flow, New Query)
- 1 key binding (Ctrl+X = exit)
- 1 product definition (window icon, about text)

---

## 2. Class Inventory

**Total:** 308 classes across 11 packages

| Package | Count | GUI-Dependent? | Purpose |
|---------|-------|---------------|---------|
| `com.inerweave.sdk` (top) | 15 | MIXED | Core: ConfigContext, Designer, ProjectActions, TransactionBase/Context/QueryContext, IwConnection, 3 perspectives |
| `com.inerweave.sdk.actions` | 8 | YES | Eclipse Actions: BuildProjectAction, Open/Close ProjectAction, etc. |
| `com.inerweave.sdk.composites` | 70 | YES | SWT form composites for wizards (NewDataMapWizardComposite=28 inner classes) |
| `com.inerweave.sdk.vews` | 121 | YES | Eclipse Views + inner classes |
| `com.inerweave.sdk.vews.iw_dialogs` | 30 | YES | SWT Dialogs |
| `com.inerweave.sdk.wizards` | 5 | YES | Eclipse Wizard classes |
| `com.inerweave.sdk.wizards.pages` | 4 | YES | Wizard pages |
| `com.iwtransactions` | 10 | NO | JAXB model (transactionType, datamapType, etc.) |
| `com.altova.*` | 41 | NO | Altova XML Schema type library |
| `com.swtdesigner` | 3 | YES | SWT Designer utilities |
| `iw_sdk` | 1 | YES | Plugin activator |

**229 of 308 (74%) are GUI-dependent.** Only ~79 classes have no GUI dependencies.

---

## 3. ConfigContext — The Central State Hub

### TWO SEPARATE ConfigContext classes exist:

**IDE version** (`com.inerweave.sdk.ConfigContext`, 73KB):
- **100% static** — every field and method is static (pure global singleton)
- 51 fields, 110+ methods
- Eclipse-coupled: uses `IProject`, `IFile`, `IWorkspace`, `NavigationView`, `CCombo`
- Used for: design-time project management, clipboard, XSLT editing

**Business Daemon version** (`com.interweave.businessDaemon.ConfigContext`, 55KB):
- Also static
- **Zero GUI dependencies** — pure runtime
- Used for: flow execution, thread management, profile binding, config persistence
- Already deployed and running inside Tomcat

### IDE ConfigContext Key Fields (51 total, all static):

**TS URL fields (10):** `primaryTransformationServerURL`, `secondaryTransformationServerURL`, plus T/1/T1/D variants

**Configuration:** `imName`, `tsName`, `tsLogLevel`, `failoverURL`, `imBufferSize` (1024), `tsBufferSize` (1024), `hartbeatInterval` (0), `refreshInterval` (1000), `imPrimary`, `hosted`, `tsPrimary`, `tsTimeStamping`, `productionPackage`, `runAtStartUp` (true)

**UI state:** `currentTransactionName`, `currentXsltName`, `currentTemplateName`, `currentDataMapName`, `currentConnectionName`, `currentTransactionFlowId`, `currentQueryId`, `currentParameterName`

**Data collections:** `transactionList` (Vector<TransactionContext>), `queryList` (Vector<QueryContext>), `iwmappingsRoot` (iwmappingsType)

**Clipboard:** project/transaction/query/XSLT/template clip and move/copy ArrayLists

**XPath helpers:** `mapName` = `"datamap[@name='"`, `colName` = `"col[@name='"`, `valueTag` = `"<xsl:value-of select=\""`

### Key Methods:

**Config I/O:** `readIMConfigContext(IProject)`, `writeIMConfigContext(IProject)`, `readTSConfigContext(IProject)`, `writeTSConfigContext(IProject)`, `imToXML()`, `tsToXML()`, `imFromXML(DOMParser)`, `tsFromXML(DOMParser)`

**XML root elements:**
- IM: `<BusinessDaemonConfiguration>`
- TS: `<TransformationServerConfiguration>`

**Transaction/Query management:** full CRUD on transactionList/queryList, flow lookup, transaction chain management

**Build helpers:** `optimizeTransactions()` → optimized XSLT string, `loadIwmappingsRoot(IProject)`, `modifyJar()`, `saveTransactions()`

---

## 4. Build Pipeline

### ProjectActions — 4-Stage Bitmask Build

```java
COMPILE_XSLT  = 1  // Compile .xslt → .class bytecode
MAKE_XSLT_JAR = 2  // Package .class files → JAR
BUILD_IM_WAR  = 4  // Build iw-business-daemon.war
BUILD_TS_WAR  = 8  // Build iwtransformationserver.war
```

**Constructor:** `ProjectActions(int mask, IProject current, IProject[] children, String names, boolean defaultStyle, boolean splitTransactions)`

**Stage 1 — compileXSLT:**
1. Copies correct `sitetran` variant (`sitetran_host.xslt` if hosted, `sitetran_ent.xslt` if enterprise) → `sitetran.xslt`
2. Builds `soltran.xslt` from `soltran_start.dat` + optimized transaction content + `soltran_end.dat`
3. If `splitTransactions`: splits soltran at midpoint into two templates (`soltran` + `soltran1`)
4. Compiles each `.xslt` using XSLTC:
   - TransformerFactory → `org.apache.xalan.xsltc.trax.TransformerFactoryImpl`
   - `generate-translet` = true
   - `package-name` = `"iwtransformationserver"`
   - `destination-directory` = `<project>/classes`
   - `enable-inlining` = false
5. Output: `classes/iwtransformationserver/<TransformName>.class`

**Stage 2 — createXsltJar:** JARs compiled classes → `<project>/.deployables/jar/`

**Stage 3 — createImWar:** Template `zip/iwbd_ide.zip` + merged `WEB-INF/config.xml` → `<project>/.deployables/war/iw-business-daemon.war`

**Stage 4 — creatrTSWar:** Template `zip/iwts_ide.zip` + XSLT JAR + `WEB-INF/transactions.xml` + `WEB-INF/config.xml` → TS WAR

### Version Constants (from Designer class):
- `projectVersion` = 220048
- `hostedVersion` = 18

---

## 5. JAXB Transaction Model

```
iwtransactionsDoc (Document root)
  └── iwmappingsType (root element: <iwmappings>)
        └── transactionType[] (<transaction>)
              ├── name: String
              ├── type: String ("sequential", "chain")
              ├── transform: String (XSLT source name)
              ├── classname: String (compiled .class name / adapter class)
              ├── nexttransactionType[] (<nexttransaction>)
              │     ├── name: String (next step reference, semicolon-delimited FlowId:NextStep pairs)
              │     ├── type: String ("Unconditional", "error", etc.)
              │     └── error: String
              └── datamapType[] (<datamap>)
                    ├── name: String
                    ├── driver: String (JDBC driver / adapter class)
                    ├── url: String
                    ├── user: String / password: String
                    └── accessType[] (<access>)
                          ├── type: String ("get", "post", "procedure", "dynamic")
                          ├── statementpre / statementpost: String
                          ├── translatorType (<translator>)
                          │     ├── inputclass / outputclass: String
                          ├── where → parameterType[]
                          ├── values → parameterType[]
                          └── outputs → parameterType[]
```

### Adapter Classes (from soltran.xslt analysis):
- `com.interweave.adapter.rest.IWRestJson` — REST/JSON
- `com.interweave.adapter.http.IWHttpBaseAdaptor` — raw HTTP
- `com.interweave.adapter.http.IWSoapBaseAdaptor` — SOAP
- `com.interweave.adapter.http.IWSoapHierarchicalAdaptor` — hierarchical SOAP
- `com.interweave.adapter.api.IWLocalAPIBaseAdaptor` — local Java API
- `com.interweave.adapter.database.IWSqlBase` — SQL database
- `com.interweave.adapter.IWGetSession` — session retrieval

---

## 6. Config File Formats

### IM Config (`configuration/im/config.xml`)

```xml
<BusinessDaemonConfiguration
    Name="BD1"
    HartbeatInterval="0"
    RefreshInterval="1000"
    PrimaryTSURL="http://127.0.0.1:9090/iwtransformationserver"
    SecondaryTSURL="http://127.0.0.1:9090/iwtransformationserver"
    ... (8 more TS URL variants) ...
    FailoverURL=""
    IsPrimary="1"
    RunAtStartUp="1"
    BufferSize="1024"
    IsHosted="0">

  <TransactionDescription
      Id="CRMAcctSync2QB"
      Description="Sync CRM Accounts to QuickBooks"
      Solution="CRM2QB"
      Interval="60000"
      Shift="0"
      RunAtStartUp="true"
      NumberOfExecutions="0"
      InnerCycles="0"
      IsActive="true"
      IsPublic="false"
      IsStateful="true">
    <Parameter Name="tranname" Value="CRMAcctLogin" Fixed="true"/>
    <Parameter Name="applicationname" Value="iwtransformationserver" Fixed="true"/>
    <Parameter Name="QueryStartTime" Value="2024-01-01 00:00:00.0"/>
  </TransactionDescription>

  <Query Id="CRMAcct2QBQ" Description="..." Solution="CRM2QB" IsActive="true">
    <Parameter Name="tranname" Value="CRMAcct2QBQ" Fixed="true"/>
  </Query>
</BusinessDaemonConfiguration>
```

### TS Config (`configuration/ts/config.xml`)

```xml
<TransformationServerConfiguration
    Name="TS1"
    LogLevel="LOG_MINIMUM"
    IsPrimary="1"
    IsTimeStamping="0"
    IsCompiledMapping="0"
    BufferSize="1024"
    IsHosted="0"/>
```

### XSLT Site Structure (per project)

```
xslt/
  include/dataconnections.xslt     — 8 XSLT params ($iwdriver/$iwurl/$iwuser/$password + $ms*)
  Site/
    include/
      appconstants.xslt            — 30+ session variable XPath extractions
      globals.xslt                  — imports appconstants
      sitetran.xslt                 — infrastructure (index + session)
      sitetran_ent.xslt             — enterprise variant
      sitetran_host.xslt            — hosted variant (10 DB-backed user mgmt transactions)
    new/
      transactions.xslt             — master (imports all, outputs <iwmappings>)
      include/soltran.xslt          — solution flow definitions
      xml/transactions.xml          — pre-built static output
```

### Three-Way Binding

| config.xml | soltran.xslt | transactions.xml |
|---|---|---|
| WHAT flows exist + scheduling | HOW flows execute | Static build output for IDE display |
| `tranname` parameter value | `<transaction name="...">` | Pre-computed `<iwmappings>` |
| `Solution` attribute | Same adapter classes | Same structure, credentials stripped |

---

## 7. IDE GUI Components

### 3 Perspectives:
- **Configuration** — IM/TS config views + NavigationView
- **Template/XSLT Editor** — XSLTEditorView + TemplateEditorView
- **Transaction Flow** — TransactionFlowView (visual flow editor with GC drawing)

### 11 Views:
- **NavigationView** (18 inner classes) — project tree with CRUD operations
- **ConfigBDView** — IM configuration (TS URLs, scheduling, hosted mode)
- **ConfigTSView** — TS configuration (log level, timestamping, compiled mapping mode)
- **TransactionFlowView** (7 inner classes) — visual flow diagram with arrows, SashForm split
- **XSLTEditorView** (25 inner classes) — source/destination field mapping, raw XSLT toggle
- **TemplateEditorView** (37 inner classes) — highest complexity, auto-generates XSLT from templates, supports IWP/DDL import
- **DataMapView** — connection + access type + command editor
- **ConnectionView** — driver/URL/user/password editor
- **AccessParameterView** — parameter editing within data map access points
- **TransactionDetailsView** — individual transaction properties
- **ProjectView** — minimal container shell

### 4 Wizards:
- **NewProjectWizard** — creates workspace directory structure
- **NewTransactionWizard** — creates transaction definition
- **NewTransactionFlowWizard** — creates flow container
- **NewQueryWizard** — creates query endpoint

---

## 8. IDE ↔ Web Portal Data Flow

### Portal → IDE (WORKING):
```
User saves wizard → ApiConfigurationServlet → company_configurations table
  → WorkspaceProfileSyncServlet (exportAll) → workspace sidecar files
  → WorkspaceProfileCompilerServlet (compileAll) → GeneratedProfiles/ overlays
  → IDE reads from workspace (no auto-refresh)
```

### IDE → Portal (BRIDGED via sync_bridge.ps1):
```
User edits in IDE → workspace files change on disk
  → sync_bridge.ps1 detects change (2s debounce)
  → HTTP: WorkspaceProfileSyncServlet?action=importProfile
  → HTTP: WorkspaceProfileCompilerServlet?action=compileProfile
  → SSE: SyncEventServlet pushes event to React UI (<1s latency)
```

### Schema Mismatch:
- Portal stores: `<SF2QBConfiguration>` — flat wizard parameters
- IDE stores: `<BusinessDaemonConfiguration>` — complex nested engine XML
- Bridge uses **sidecar files** (not overwrite) to keep both schemas intact

---

## 9. Headless / Server-Mode Feasibility

### Verdict: The IDE CANNOT run headlessly. But it doesn't need to.

**Why headless fails:**
- `Designer.run()` calls `PlatformUI.createAndRunWorkbench()` — requires SWT Display
- `ProjectActions` uses `IProject`, `IWorkbenchWindow` — Eclipse workspace dependencies
- `iw_ide.exe` is Windows x86 32-bit only (no Linux/Docker)
- 74% of plugin classes (229/308) are GUI-dependent

**Why it doesn't matter:**
- The Business Daemon's `ConfigContext` (55KB, zero GUI deps) already manages all runtime operations
- `WorkspaceProfileCompiler` (925 LOC) replicates the profile compilation pipeline
- `ApiFlowManagementServlet` wraps ConfigContext for start/stop/schedule
- XSLT compilation is a standard CLI command (same Xalan JARs)
- The server is already 80%+ functionally equivalent to the IDE for operations

---

## 10. Gap Analysis

| Capability | Server Status | Difficulty |
|---|---|---|
| Read/write config.xml | DONE | — |
| XSLT compilation | DONE (CLI) | — |
| Profile compilation | DONE (925 LOC servlet) | — |
| Flow start/stop/schedule | DONE (API servlet) | — |
| Engine config loading | DONE (BD ConfigContext) | — |
| Profile binding | DONE (bindHostedProfile) | — |
| Credential test | DONE (API endpoint) | — |
| **Create new project** | NOT DONE | LOW |
| **Create transaction/query** | NOT DONE | MEDIUM |
| **Create XSLT transformer** | NOT DONE | HIGH |
| **Create connection** | NOT DONE | MEDIUM |
| **Visual flow wiring** | NOT DONE | HIGH |
| **Visual XSLT/template editor** | NOT DONE | VERY HIGH |
| Build IM/TS WAR | NOT NEEDED (deployment-time) | — |
| Next-transaction chaining | NOT DONE | MEDIUM |

---

## 11. Recommended Path Forward

### Strategy: Server-side replication + IDE as optional design tool

**Near-term (low effort):**
1. "Create New Project" servlet — generate standard workspace directory structure + XML templates
2. "Compile XSLT" servlet endpoint — invoke Xalan XSLTC as subprocess

**Medium-term:**
3. Transaction/Query CRUD API — DOM manipulation of config.xml
4. Connection management API — generate dataconnections.xslt entries
5. Next-transaction wiring API

**Long-term (if full IDE replacement desired):**
6. Web-based XSLT field mapping editor (most complex feature)
7. Web-based transaction flow visual editor
8. The InterWoven "AI Field Mapping" prototype page is exploring this direction

**Do NOT attempt:**
- Running `iw_ide.exe` headlessly
- Writing a headless Eclipse IApplication
- Docker + Xvfb (Windows x86 binary)
- Extracting IDE ConfigContext (Eclipse dependencies throughout)
