# IDE ↔ DB Field Mapping Reference

Maps each InterWeave IDE GUI view to the data sources (DB tables, generated workspace files) that populate it.

## Data Flow Pipeline

```
Portal Wizard UI → ApiConfigurationServlet → company_configurations (DB)
                                            ↓
                                   WorkspaceProfileSyncSupport.exportProfile()
                                            ↓
                                   WorkspaceProfileCompiler.compileProfile()
                                            ↓
                          workspace/GeneratedProfiles/<profileName>/
                            ├── configuration/im/config.xml       → IDE TransactionFlowView
                            ├── configuration/ts/config.xml       → IDE TransformationServerView
                            ├── configuration/profile/             → metadata
                            ├── xslt/include/dataconnections.xslt → IDE ConnectionView
                            └── xslt/Site/include/appconstants.xslt → IDE session vars
```

## IDE View → Data Source Mapping

### 1. ConnectionView (`com.interweave.ide.views.ConnectionView`)

**What it shows**: Database/API connection parameters for source and destination systems.

| XSLT Param | Wizard Config Field | DB Source | Description |
|-------------|-------------------|-----------|-------------|
| `iwurl` | Derived from `SandBoxUsed` | SF2QBConfiguration XML | Source API URL (SF login or Creatio endpoint) |
| `iwuser` | `SFIntUsr` | SF2QBConfiguration XML | Source system username |
| `password` | `SFPswd` | SF2QBConfiguration XML | Source system password |
| `iwdriver` | — | (empty, not JDBC-based) | Source JDBC driver class |
| `msurl` | `QDSN0` | SF2QBConfiguration XML | Destination connection string (QB) |
| `msuser` | `QBIntUsr0` | SF2QBConfiguration XML | Destination username |
| `mspassword` | `QBPswd0` | SF2QBConfiguration XML | Destination password |
| `msdriver` | — | (empty) | Destination JDBC driver class |

**Generated file**: `xslt/include/dataconnections.xslt`
**Status**: NOW POPULATED by WorkspaceProfileCompiler (as of session 14)

### 2. TransactionFlowView (`com.interweave.ide.views.TransactionFlowView`)

**What it shows**: List of transaction flows (scheduled backend sync jobs) with their active/inactive status.

| XML Element | Attributes | Source |
|-------------|-----------|--------|
| `TransactionDescription` | `Id`, `Name`, `IsActive`, `RunAtStartUp` | Template im/config.xml filtered by solution type |
| `Query` | `Id`, `Name`, `IsActive` | Template im/config.xml filtered by solution type |

**Generated file**: `configuration/im/config.xml`
**Status**: WORKING — compiler enables/disables flows based on SyncType toggles

### 3. TransactionDetailsView (`com.interweave.ide.views.TransactionDetailsView`)

**What it shows**: Details of a selected transaction — parameters, schedule, endpoints.

| Field | XML Attribute/Element | Source |
|-------|----------------------|--------|
| Name | `TransactionDescription@Name` | im/config.xml |
| Primary TS URL | `TransactionDescription@PrimaryTSURL` | im/config.xml (normalized by compiler) |
| Run At Startup | `TransactionDescription@RunAtStartUp` | im/config.xml |
| Parameters | `TransactionDescription/Parameter@Name,@Value` | im/config.xml (SFURL, TransactionSourceName, ReturnString, TestMode populated by compiler) |

**Generated file**: `configuration/im/config.xml`
**Status**: WORKING — Parameter values (SFURL, TestMode, etc.) set by `applyParameterValues()`

### 4. AccessParameterView (`com.interweave.ide.views.AccessParameterView`)

**What it shows**: Access/authentication parameters for the selected transaction.

| Parameter | Wizard Field | Description |
|-----------|-------------|-------------|
| `SFURL` | `SandBoxUsed` → derived | Salesforce login endpoint |
| `TransactionSourceName` | `SourceName` | Source system identifier |
| `ReturnString` | `Env2Con` | Return URL with environment parameter |
| `TestMode` | `SandBoxUsed` | Whether using sandbox mode |

**Generated file**: `configuration/im/config.xml` (Parameter sub-elements)
**Status**: WORKING

### 5. DataMapView (`com.interweave.ide.views.DataMapView`)

**What it shows**: XSLT data mapping definitions — how fields map from source to destination format.

| File | Purpose | Source |
|------|---------|--------|
| `xslt/Site/new/transactions.xslt` | Master XSLT transformation template | Copied from template project |
| `xslt/Site/new/xml/transactions.xml` | Transaction XML data definitions | Copied from template project |
| Individual `.xslt` files in `xslt/` | Per-transaction XSLT mappings | NOT generated (live in template project) |

**Generated files**: `xslt/Site/new/transactions.xslt`, `xslt/Site/new/xml/transactions.xml`
**Status**: COPIED from template — individual data maps reference template project's XSLT files

### 6. IwConsole (`com.interweave.ide.views.IwConsole`)

**What it shows**: Runtime console output, engine status, log messages.

No direct DB mapping — populated at runtime by engine execution.

### 7. ConfigBDView (`com.interweave.ide.views.ConfigBDView`)

**What it shows**: Business Daemon configuration — name, TS URLs, heartbeat, refresh interval.

| Field | XML Attribute | Source |
|-------|--------------|--------|
| BD Name | `BusinessDaemonConfiguration@Name` | `BD_<profileName>` (set by compiler) |
| Primary TS URL | `@PrimaryTSURL` | `.env` TS_BASE or default `localhost:9090/iwtransformationserver` |
| Failover URL | `@FailoverURL` | `.env` TS_FAILOVER or empty |
| Heartbeat | `@HartbeatInterval` | From template (default 0) |
| Refresh | `@RefreshInterval` | From template (default 1000) |
| Run At Startup | `@RunAtStartUp` | Set to 0 by compiler |
| Is Hosted | `@IsHosted` | From template (default 0) |

**Generated file**: `configuration/im/config.xml` (root element attributes)
**Status**: WORKING

### 8. ConfigTSView (`com.interweave.ide.views.ConfigTSView`)

**What it shows**: Transformation Server configuration.

| Field | XML Attribute | Source |
|-------|--------------|--------|
| TS Name | `TransformationServerConfiguration@Name` | `TS_<profileName>` (set by compiler) |
| Is Primary | `@IsPrimary` | Set to 1 by compiler |
| Is Hosted | `@IsHosted` | Set to 0 by compiler |

**Generated file**: `configuration/ts/config.xml`
**Status**: WORKING

## DB Tables → Workspace Mapping

### `company_configurations` Table
| Column | Used By | How |
|--------|---------|-----|
| `configuration_xml` | WorkspaceProfileCompiler | Parsed to ProfileValues, drives all generation |
| `solution_type` | WorkspaceProfileSyncSupport | Maps to workspace project name |
| `profile_name` | Compiler | Used as generated profile directory name |

### `company_credentials` Table
| Column | Mapped To | Status |
|--------|-----------|--------|
| `credential_type` | Determines source vs destination | NOT YET wired to compiler |
| `username` | `iwuser` or `msuser` | Stored in wizard XML as SFIntUsr/QBIntUsr0 |
| `password` | `password` or `mspassword` | Stored in wizard XML as SFPswd/QBPswd0 |
| `endpoint_url` | `iwurl` or `msurl` | Stored in wizard XML via SandBoxUsed/QDSN0 |
| `api_key` | — | Not mapped to workspace files (used for REST API auth) |

### `companies` Table
| Column | Used By | How |
|--------|---------|-----|
| `solution_type` | ApiConfigurationServlet | Determines which SyncType keys are allowed |
| `company_name` | Profile naming | Used in profile key: `companyName:userEmail` |

## Wizard Config XML ↔ Engine Config Mapping

The wizard saves flat `<SF2QBConfiguration>` XML. The compiler maps these to engine `<BusinessDaemonConfiguration>` format:

| Wizard XML Element | Engine Target | Generated Where |
|-------------------|--------------|-----------------|
| `SyncTypeAC` | TransactionDescription `IsActive` for account flows | im/config.xml |
| `SyncTypeSO` | TransactionDescription `IsActive` for sales order flows | im/config.xml |
| `SyncTypeInv` | TransactionDescription `IsActive` for inventory flows | im/config.xml |
| `SyncTypeSR` | TransactionDescription `IsActive` for service flows | im/config.xml |
| `SyncTypePrd` | TransactionDescription `IsActive` for product flows | im/config.xml |
| `SFIntUsr` | `iwuser` param | dataconnections.xslt |
| `SFPswd` | `password` param | dataconnections.xslt |
| `QDSN0` | `msurl` param | dataconnections.xslt |
| `QBIntUsr0` | `msuser` param | dataconnections.xslt |
| `QBPswd0` | `mspassword` param | dataconnections.xslt |
| `SandBoxUsed` | `iwurl` (SF sandbox vs prod), `TestMode` param | dataconnections.xslt, im/config.xml |
| `SourceName` | `TransactionSourceName` param | im/config.xml |
| `Env2Con` | `ReturnString` param | im/config.xml |

## Solution Type → Template Project Mapping

| Solution Type Prefix | Workspace Project | im/config.xml Contents |
|---------------------|-------------------|------------------------|
| `SF2AUTH`, `SF2QB` | SF2AuthNet | 24 TransactionDescriptions + 46 Queries (payment processors: AuthNet, Stripe, PayPal, Durango, TSYS, etc.) |
| `CRM2QB` | Creatio_QuickBooks_Integration | 7 TDs + 7 Queries (CRM account/order/invoice/product/SR sync + payment queries) |
| `CRM2M`, `CRM2MG` | Creatio_Magento2_Integration | 8 TDs + 6 Queries (bidirectional CRM↔Magento2 sync including M2→CRM reverse flows) |
| Other | (unmapped) | Falls back to SF2AuthNet |

## Compiler Modules

| Module | Solution Types | Enable/Disable Logic |
|--------|---------------|---------------------|
| `SF2AUTH` | SF2AUTH, SF2QB* | SF-prefixed flow IDs, Sugar disabled, Creatio payment queries |
| `CRM2QB3` | CRM2QB3 | Legacy CRM2QB with BPM + SF transaction IDs |
| `CRM2QB` | CRM2QB*, CRM2M* | CRM-prefixed IDs, M2 reverse sync, Creatio payment queries |
| `GENERIC` | Everything else | All flows enabled |

## IDE Project Discovery

The `AutoImportStartup` plugin (`plugins/iw_workspace_init_1.0.0/`) runs at IDE startup and:
1. Scans all directories under `workspace/` for `.project` files
2. Skips `GeneratedProfiles` and `IW_Runtime_Sync` (infrastructure projects)
3. Auto-creates and opens any unregistered projects in Eclipse

**Build menu commands**: buildProject, buildIM, buildTS, compileXSLT — defined in `iw_sdk_1.0.0/plugin.xml`, handled by `ProjectActions.class`.

## XSLT Transformer Pipeline

Each transaction in `transactions.xml` references a `<transform>` name that maps to an XSLT file and compiled class:

```
xslt/SyncAccounts_CRM2MG.xslt  →  classes/iwtransformationserver/SyncAccounts_CRM2MG.class
                                    ↑ compiled via XSLTC
```

### Transformer File Locations

| File Type | Path | Purpose |
|-----------|------|---------|
| XSLT source | `xslt/<TransformName>.xslt` | Field mapping definitions (Creatio OData → Magento REST, etc.) |
| Compiled class | `classes/iwtransformationserver/<TransformName>.class` | XSLTC-compiled bytecode loaded at runtime |
| Transaction defs | `xslt/Site/new/xml/transactions.xml` | Static build output — all `<transaction>` elements with adapters, datamaps, access params |
| Transaction XSLT | `xslt/Site/new/transactions.xslt` | Master stylesheet that imports sitetran + soltran + dataconnections |
| Solution flows | `xslt/Site/new/include/soltran.xslt` | Solution-specific transaction definitions (XSLT template form) |
| Site flows | `xslt/Site/include/sitetran.xslt` | Shared infrastructure transactions (index, session) |
| Connections | `xslt/include/dataconnections.xslt` | XSLT params for source/destination credentials |

### XSLT Compilation (Build TS)

Transformers are compiled from XSLT to Java bytecode using Apache XSLTC:

```bash
java -cp "xsltc.jar;xalan.jar;serializer.jar" \
  org.apache.xalan.xsltc.cmdline.Compile \
  -o <TransformName> -d classes/iwtransformationserver \
  xslt/<TransformName>.xslt
```

JARs are in `web_portal/tomcat/webapps/iwtransformationserver/WEB-INF/lib/`.

### Transformer XSLT Patterns

All transformers match `<iwtransformationserver>` as root and extract data via XPath:

```xslt
<xsl:template match="iwtransformationserver">
  <xsl:for-each select="iwrecordset/transaction/datamap[@name='creatio_accounts']/data/row">
    <xsl:variable name="Name" select="col[@name='Name']"/>
    <!-- output JSON/XML/text for destination API -->
  </xsl:for-each>
</xsl:template>
```

Output modes: `method="text"` for REST JSON bodies, `method="xml"` for SOAP/IW XML responses.

### Compiler Profile Generation

`WorkspaceProfileCompiler.compileProfile()` now copies the full transformer pipeline from the template project into `GeneratedProfiles/<profile>/`:

| Source (template project) | Destination (GeneratedProfiles) |
|--------------------------|-------------------------------|
| `xslt/*.xslt` | `xslt/*.xslt` (individual transformers) |
| `classes/iwtransformationserver/*.class` | `classes/iwtransformationserver/*.class` |
| `xslt/Site/new/include/soltran.xslt` | `xslt/Site/new/include/soltran.xslt` |
| `xslt/Site/include/sitetran.xslt` | `xslt/Site/include/sitetran.xslt` |
| `xslt/Site/new/xml/transactions.xml` | `xslt/Site/new/xml/transactions.xml` |
| `xslt/Site/new/transactions.xslt` | `xslt/Site/new/transactions.xslt` |
| `xslt/Site/include/appconstants.xslt` | `xslt/Site/include/appconstants.xslt` |
| _(generated)_ | `xslt/include/dataconnections.xslt` (credentials from wizard) |

### Per-Project Transformer Inventory

| Project | Transformer XSLTs | Compiled Classes | Adapter |
|---------|-------------------|-----------------|---------|
| SF2AuthNet | 142 files | 472 classes | `IWSoapBaseAdaptor`, `IWHttpBaseAdaptor`, `IWSoapHierarchicalAdaptor` |
| Creatio_Magento2_Integration | 11 files | 11 classes | `IWRestJson` (REST/JSON bidirectional) |
| Creatio_QuickBooks_Integration | _(pending)_ | _(pending)_ | `IWRestJson`, `IWHttpBaseAdaptor` |
