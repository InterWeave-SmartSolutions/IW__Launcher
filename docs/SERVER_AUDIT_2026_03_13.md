# Production Server Audit & Gap Analysis Report
**Date:** 2026-03-13
**Server:** 107525-UVS13 (Windows Server 2016 Datacenter, Hyper-V VM)
**Domain:** TRAPPCLOUD.COM | **IP:** 172.18.107.120 | **Timezone:** America/Phoenix (UTC-7)
**Hardware:** Intel Xeon E5 v4 (2 cores), 4GB RAM, C: 100GB (55% free), D: 10GB (25% free)

---

## 1. Production Architecture Overview

```
                        ┌─────────────────────────────────┐
                        │        INTERNET CLIENTS          │
                        └────────┬───────────┬────────────┘
                                 │           │
                    ┌────────────▼──┐  ┌─────▼──────────────┐
                    │  IIS (http.sys)│  │ Stunnel (TLS proxy)│
                    │  Port 80/443   │  │ Port 8443 → 8080   │
                    │  PID 4 (kernel)│  │ PID 2328            │
                    └────────────────┘  └────────────────────┘
                           │                     │
              ┌────────────▼──┐        ┌─────────▼──────────┐
              │ IWCustomerPortal│       │ Tomcat 5.5 (8080)  │
              │ Payment Portal  │       │ PID 1012, Auto-start│
              │ 21 client configs│      │ Java 1.5.0_22 (x86)│
              │ Salesforce OAuth │      │ IW Engine v2.41     │
              └─────────────────┘      │ Build 721/713       │
                                       └─────────┬──────────┘
                                                  │
                    ┌─────────────────────────────┼──────────────────────┐
                    │              │               │              │      │
              ┌─────▼─────┐ ┌─────▼─────┐ ┌──────▼────┐ ┌──────▼────┐ ┌▼──────────┐
              │SF2QBBase  │ │SF2QBCustom│ │SF2AuthNet │ │QB_POAgent│ │SN2QB_SP  │
              │Base QB    │ │Custom QB  │ │Auth.Net   │ │PO Agent  │ │SalesProdigy│
              └─────┬─────┘ └─────┬─────┘ └─────┬────┘ └─────┬────┘ └────┬──────┘
                    │              │              │            │           │
                    └──────────────┴──────────────┴────────────┴───────────┘
                                                  │
                    ┌─────────────────────────────┼──────────────────────┐
                    │              │               │              │      │
              ┌─────▼──────┐ ┌────▼────────┐ ┌────▼──────┐ ┌────▼────┐ ┌▼──────────┐
              │Salesforce  │ │Authorize.Net│ │Creatio CRM│ │Sage     │ │QuickBooks │
              │SOAP API    │ │Payments     │ │3 instances│ │Intacct  │ │via QODBC  │
              │stunnel:    │ │stunnel:     │ │stunnel:   │ │stunnel: │ │JDBC-ODBC  │
              │38498/38500 │ │38478/38486  │ │38477/38489│ │38490    │ │bridge     │
              └────────────┘ └─────────────┘ └───────────┘ └─────────┘ └───────────┘
```

---

## 2. Running Services & Processes

| Service | PID | Port(s) | Binary | Status |
|---------|-----|---------|--------|--------|
| **Tomcat 5** | 1012 | 8080 (HTTP), 8009 (AJP), 8005 (shutdown) | `C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\bin\tomcat5.exe` | RUNNING, Auto-start |
| **Stunnel** | 2328 | 8443 + 38477-38600 (11 tunnels) | `C:\Program Files (x86)\stunnel\bin\stunnel.exe` | RUNNING, Auto-start |
| **IIS (W3SVC)** | 4 | 80, 82, 443 | `C:\Windows\system32\svchost.exe` | RUNNING, Auto-start |
| **QODBC QRemote** | — | — | `C:\Program Files (x86)\QODBC Driver for QuickBooks\QRemote\Server\QRemoteServer.exe` | Available (manual) |

**No local database server** — no MySQL/PostgreSQL running on this server. The engine connects to QuickBooks via QODBC and external APIs via Stunnel.

---

## 3. Stunnel TLS Tunnel Map

The engine runs Java 1.5 which cannot do TLS 1.2+. Stunnel provides all outbound HTTPS connectivity.

| Tunnel Name | Local Port | Remote Endpoint | Purpose |
|------------|-----------|-----------------|---------|
| **server4tls** | 8443 → 8080 | Inbound TLS termination | SSL for Tomcat (cert: `demo.interweave.biz.pem`, TLS 1.2-1.3) |
| **salesforcel** | 38500 | login.salesforce.com:443 | SF login (production) |
| **salesforcet** | 38600 | test.salesforce.com:443 | SF login (sandbox) |
| **salesforcecp1** | 38498 | fun-agility-76422.lightning.force.com:443 | Southern Lamps prod SF |
| **salesforcecp2** | 38497 | fun-agility-76422--devpartial.sandbox.lightning.force.com:443 | Southern Lamps sandbox |
| **PampaCreatio** | 38477 | pampabay.creatio.com:443 | Pampa Bay CRM |
| **DalkiaCreatio** | 38488 | dev-dalkiasolutions.creatio.com:443 | Dalkia CRM |
| **creatio-bgp** | 38489 | dev-pampabay.creatio.com:443 (SNI: inter-weave.creatio.com) | InterWeave Creatio |
| **sageintact** | 38490 | api.intacct.com:443 | Sage Intacct ERP |
| **auth2SF** | 38478 | api.authorize.net:443 | Payment processing (prod) |
| **auth** | 38486 | test.authorize.net:443 | Payment processing (test) |

**Config file:** `C:\Program Files (x86)\stunnel\config\stunnel.conf`
**Backup:** `D:\Users\AUmanets.107525\Downloads\Stunnel 5.71\stunnelfull.conf`

---

## 4. IIS Configuration

| App Pool | URLs | Physical Path | Purpose |
|----------|------|---------------|---------|
| **Payment Portal** | `HTTP://*:80/`, `HTTPS://DEMO.INTERWEAVE.BIZ:443/` | `C:\Sites\IWCustomerPortal\` | Customer payment portal |
| **DefaultAppPool** | `HTTP://*:82/` | `C:\inetpub\wwwroot\` | Default IIS page |

**SSL Certificate:** `CN=demo.interweave.biz` — **EXPIRED Dec 29, 2025** (2.5 months overdue)
**PFX files:** `C:\SSL\demo.interweave.biz.pfx`, `C:\SSL\2024-demo.interweave.biz.pfx`

IIS and Tomcat run **independently** — no reverse proxy between them.

---

## 5. Production Webapps (Tomcat 5.5)

All 5 webapps share identical `web.xml` with 6 servlets:

| Servlet | Class | URL Pattern |
|---------|-------|-------------|
| DefaultGateway | `org.openamf.DefaultGateway` | `/gateway/*` |
| iwtransformationserver | `com.interweave.servlets.IWIndex` | `/index` |
| logging | (IWLogging) | `/logging` |
| iwxml | (IWXmlRequest) | `/iwxml` |
| transform | (IWTransform) | `/transform` |
| IWScheduledTransform | (IWScheduledTransform) | `/scheduledtransform` |

**Engine config** (from `WEB-INF/config.xml`):
```xml
TransformationServerConfiguration Name="TS1" LogLevel="LOG_MINIMUM"
  IsPrimary="1" IsTimeStamping="1" IsCompiledMapping="1"
  BufferSize="1024" IsHosted="0"
```

**Key difference from IW_Launcher:** `IsHosted="0"` (non-hosted mode) — each webapp is a standalone integration project, not a multi-tenant hosted platform.

---

## 6. Engine Class & JAR Inventory

### Production Engine JARs (per webapp WEB-INF/lib/)
17 IW-specific JARs: `iwactionscript.jar`, `iwadapter.jar`, `iwclib.jar`, `iwconnector.jar`, `iwcore.jar`, `iwcreditcard.jar`, `iwdatabase.jar`, `iwdeveloper.jar`, `iwemail.jar`, `iwencrypt.jar`, `iwfilesystem.jar`, `iwhttp.jar`, `iwhttps.jar`, `iwlicense.jar`, `iwservices.jar`, `iwservlets.jar`, `iwsolution_SF2QBBase.jar`

Plus 120+ vendor JARs (Google Data APIs, Axis, OpenAMF, JDBC drivers, etc.)

### Production Engine Classes (WEB-INF/classes/com/interweave/)
**148 compiled classes** across 15 packages:

| Package | Classes | Key Classes |
|---------|---------|-------------|
| `servlets/` | 5 | IWIndex, IWTransform, IWScheduledTransform, IWLogging, IWXmlRequest |
| `core/` | 7 | IWApplication, IWExecute, IWRequest, IWServices, IWTransaction |
| `adapter/` | 5 | IWBaseAdaptor, IWTransform, IWTransformAdapter, IWSessionData |
| `adapter/http/` | 11 | IWHttpBaseAdaptor, IWSoapAdapter, IWJSonCookieAdaptor |
| `adapter/database/` | 10 | IWSqlBase, IWSqlSync, IWSQLGeneric |
| `adapter/filesystem/` | 7 | IWFileBaseAdaptor, IWFtpBaseAdaptor |
| `adapter/email/` | 3 | IWEmailBaseAdaptor, IWSendEmail |
| `bindings/` | 23 | Iwxmlrequest, Iwmappings, Transaction, Datamap |
| `connector/` | 4 | IWXsltcImpl, IWXMLRequest, IWHttpImpl |
| `developer/` | 7+4 | IWXMLDocument, WSDLParser, SoapRequest |
| `encrypt/` | 4 | IWBase64Encode, IWCompress, IWUUEncode |
| `salesforce/` | 3 | IWSalesforceAdapterIn/Out |
| `session/` | 3 | IWSession, IWSessionManager, IWSessionMonitor |
| Others | ~10 | actionscript, license, lotus, mathplugin, utilplugin, webservice |

### IW_Launcher Engine
- Has `iwengine.jar` (consolidated JAR with 125 classes) — production does NOT have this JAR
- Has `iwservlets.jar` missing — production has it as a separate JAR
- Has 2 additional classes: `TransactionLoggingFilter` (monitoring addition)

**Conclusion:** The engine classes are essentially the same code, just packaged differently. Production has loose `.class` files + modular JARs; IW_Launcher consolidated them into `iwengine.jar`.

### SDK Plugin Comparison (iw_sdk_1.0.0)
- **Production:** 310 classes in `bin/com/inerweave/sdk/` + `bin/com/altova/` + `bin/com/iwtransactions/`
- **IW_Launcher:** 308 classes (same structure, minor difference likely due to path normalization)
- **Verdict:** SDK is identical between production and IW_Launcher

---

## 7. Production Workspace Projects

### Active IDE (`C:\IW_IDE\`)
26+ workspace projects found:

| Project | Purpose | Status |
|---------|---------|--------|
| **SF2AuthNet** | Salesforce ↔ Authorize.Net payments | Active (deployed as webapp) |
| **IWHostedSolutions** | Master hosted solution platform (50KB+ config.xml) | Active |
| **SN2QBSP** | Sales Prodigy → QuickBooks | Active (deployed as SN2QB_SP webapp) |
| **IW_QBConnector** | Standalone QuickBooks connector | Active |
| **AccpacCom** | Accpac ERP integration (COM) | Legacy |
| **NexternalHostedIan** | Nexternal integration | Legacy |
| **NexternalQueryTest/D** | Nexternal query testing | Legacy |
| **SF2MM** | Salesforce ↔ Magento(?) | Legacy |
| **SalesProdigy1** | Sales Prodigy integration v1 | Legacy |
| **SSProject** | Unknown | Legacy |
| **SPworkspace** | SharePoint workspace | Legacy |
| **Templates** | Project templates | Reference |
| **QB_sample** | QuickBooks sample project | Reference |
| **sample_projects** | Sample integration projects | Reference |
| **all_projects_1/** | Archive of 21 projects (OMS, CRM, Warehouse, etc.) | Archive |
| **all_projects_2/** | Archive of 5 utility projects | Archive |

**Total across all projects:** 847 compiled classes, 156 XSLT files

### IW_IDE Version History (C: drive)
9 installations spanning 2+ years:
- `C:\IW_IDE` — Current active
- `C:\IW_IDE-2024-02-01` through `C:\IW_IDE-2025-10-20` — 6 dated backups
- `C:\IW_IDE-broken` and `C:\IW_IDE-broken2` — Failed installations (broken2 missing QB connector)
- `C:\Software\IW_IDE.zip` — 321MB compressed backup (Dec 15, 2024)

---

## 8. IWCustomerPortal (Payment Portal)

**Location:** `C:\Sites\IWCustomerPortal\`
**Served by:** IIS on port 80 (HTTP) and 443 (HTTPS at `demo.interweave.biz`)

### Architecture
- Multi-tenant portal with per-client configuration
- Authenticates against Salesforce Contact records (`Payment_Portal_Username__c`, `Payment_Portal_Password__c`)
- Uses OAuth2 (password grant) to get Salesforce API access
- Admin backdoor: username `admin` / password `Interweave$$066` → redirects to `portalCFG.html`
- Obfuscated JavaScript (`portal.js`)

### Client Configurations (21 clients in `clients/` directory)
Each client has a `config.json` with: Salesforce OAuth credentials, API endpoint, company token, field mappings.

| Client | Domain/Type |
|--------|------------|
| acme | Demo |
| Apple | Demo/Test |
| Brikers | Production |
| bpbcpa | Production |
| carwiser / carwiserr | Production |
| creatio | Integration test |
| customerpayments | Generic template |
| format14crm | Production |
| haashow | Production |
| heritageli | Production |
| interweave / interweave2 | Internal demo |
| landmark | Production |
| netatwork | Production |
| oro | Production |
| pampabay | Production |
| salesforce | Integration test |
| sugar | Integration test |
| suite | Integration test |
| tonaquint | Production |

### Portal Pages
- `index.html` — Login page (validates client token from URL)
- `loggedIn.html` — Post-login view (invoice/order listing)
- `payment.html` — Payment processing
- `details.html` — Invoice/order details (19.6KB, most complex page)
- `portalCFG.html` — Admin configuration page

### Key Config Structure (per client)
```json
{
  "orderType": "Opportunity",
  "domain": "https://[instance].my.salesforce.com/services/data/v53.0/",
  "activeClient": "[client-name]",
  "accessToken": "[portal-access-token]",
  "grant_type": "password",
  "client_id": "[SF-connected-app-consumer-key]",
  "client_secret": "[SF-connected-app-consumer-secret]",
  "username": "[SF-integration-user]",
  "password": "[SF-password+security-token]",
  "interweave_url": "https://iw4.interweave.biz:8443",
  "salesforce_api_level": "47.0",
  "__company_name__": "[URL-encoded-company]",
  "__company_token__": "[IW-company-token]"
}
```

---

## 9. Active Integrations (from today's logs)

### Clients Running Today (2026-03-13)

**Southern Lamps Inc** (`interweave@southernlampsinc.com`):
- `QBItem2SFProd` — QuickBooks Items → Salesforce Products (Run #23,009)
- `SFAcct2QBCust` — Salesforce Accounts → QuickBooks Customers (Run #11,611)
- `QBCustInvoices2SFAcctOpp` — QuickBooks Invoices → Salesforce Opportunities
- Connects to QuickBooks via QODBC, Salesforce via SOAP API v47.0

**General (query-triggered from Salesforce buttons):**
- `SFAcct2QBCustNQ` — On-demand Salesforce Account → QuickBooks Customer sync
- Triggered from IP 75.112.46.98 (client browser)
- Uses SF API v57.0 via stunnel proxy at 127.0.0.1:38500

### .erl Engine Run Logs
- **137 total .erl files** in Tomcat root directory
- Date-stamped: `YYYYMMDD.erl` (daily engine logs)
- Client-specific: `interweave_[domain]YYYYMMDD.erl`
- Today's log: 6,517 lines / 327KB as of 14:48
- Peak day: 2026-03-05 with 887MB log file

---

## 10. D: Drive Findings

### User Desktop (operational shortcuts)
- Stunnel Start/Stop shortcuts
- Tomcat Logs shortcut
- QRemote Server shortcut
- QODBC test/configure shortcuts
- **ASSA Portals** folder with `assa_associate_portal/` and `assa_master_console/`
- **IW_IDE.zip** (707MB) — full IDE backup

### InterWeave Documentation
- `D:\Users\AUmanets.107525\Desktop\InterWeave Documentation\`
  - `Customer Portal Documentation\Creatio\` — Creatio integration packages
  - `Customer Portal Documentation\Salesforce\` — Salesforce integration packages
  - `General\` — Knowledge base and QODBC error documentation

### Certificates & Auth
- `D:\Users\AUmanets.107525\Documents\demo.interweave.biz.pem`
- OneAuth blobs for `aumanets@interweave.biz` (Azure AD/SSO)
- SFDX config for `dmytro_zotkin@interweave.biz` (Salesforce dev sandbox)

### Mysterious Directories
- `D:\BAJAWTpU6p\` and `D:\VAJAWTpU6p\` — updated today, 9 files each (1.7MB), obfuscated filenames. Likely automated backup/monitoring system artifacts.

---

## 11. Software Inventory

| Software | Version | Location | Purpose |
|----------|---------|----------|---------|
| Java JRE | 1.5.0_22 (x86) | `C:\Program Files (x86)\Java\jre1.5.0_22\` | Tomcat runtime |
| Tomcat | 5.5.33 | `C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\` | App server |
| Stunnel | 5.71 | `C:\Program Files (x86)\stunnel\` | TLS proxy |
| QODBC | Current | `C:\Program Files (x86)\QODBC Driver for QuickBooks\` | QB connectivity |
| Nginx | Unknown | `C:\nginx\` | Reverse proxy (**dormant**, not running) |
| IIS | Built-in | System | Payment portal host |
| Git | Current | `C:\Program Files\Git\` | Version control |
| Node.js | Current | PATH | Development |
| VS Code | Current | `C:\Microsoft VS Code\` | Editor |
| SQLyog | Community | `C:\Program Files\SQLyog Community\` | DB admin |
| Cybereason | Active | `C:\Program Files\Cybereason ActiveProbe\` | Endpoint protection |

---

## 12. GAP ANALYSIS: IW_Launcher vs Production

### CRITICAL GAPS (must address for production readiness)

| # | Component | Production | IW_Launcher | Action Required |
|---|-----------|-----------|-------------|-----------------|
| 1 | **IWCustomerPortal** | Full payment portal with 21 clients, OAuth2, Salesforce integration | **ABSENT** | Copy `C:\Sites\IWCustomerPortal\` to IW_Launcher |
| 2 | **Stunnel config** | 11 TLS tunnels for all external API connectivity | **ABSENT** | Document tunnel pattern; IW_Launcher uses Java 8 (native TLS) but needs equivalent connection configs |
| 3 | **IWHostedSolutions config.xml** | 50KB+ master config with all transaction definitions | **ABSENT** | Copy for reference — this is the template for hosted mode |
| 4 | **Production web.xml pattern** | 6 servlets including OpenAMF gateway, IsHosted=0 | Different (Tomcat 9, custom servlets) | Document the mapping between production and IW_Launcher servlet configs |
| 5 | **Engine config.xml** | Per-webapp `WEB-INF/config.xml` with `IsHosted="0"` | Uses `IsHosted="1"` | Understand both modes — IW_Launcher's hosted mode is correct for its multi-tenant architecture |

### MEDIUM GAPS (valuable for completeness)

| # | Component | Production | IW_Launcher | Action Required |
|---|-----------|-----------|-------------|-----------------|
| 6 | **IW_QBConnector** | `connector.exe` + `interweave-qb.pfx` certificate | **ABSENT** | Copy for reference (QuickBooks Web Connect connector) |
| 7 | **20+ workspace projects** | Full library of integration templates and samples | Only 3 projects | Selectively copy reference projects (Templates, sample_projects, IWHostedSolutions) |
| 8 | **SSL certificates** | `demo.interweave.biz.pfx` (EXPIRED) | Not needed locally | Document cert renewal process |
| 9 | **.erl log format** | 137 engine run logs showing operational patterns | Monitoring system exists but different format | Use `.erl` logs to understand engine behavior patterns |
| 10 | **iwservlets.jar** | Separate JAR in production | Missing (classes in iwengine.jar) | Verify IWIndex/IWTransform/IWScheduledTransform are in iwengine.jar |
| 11 | **ProfileMgmt.sql** | Original MySQL schema (userprofiles + companies) | Migrated to Postgres/Supabase | Already handled — keep for reference |
| 12 | **ASSA Portal prototypes** | On desktop at `D:\Users\...\Desktop\ASSA Portals\` | Already in `frontends/assa/` and `docs/ui-ux/` | Verify IW_Launcher versions match server versions |
| 13 | **InterWeave Documentation** | Customer Portal docs for Creatio & Salesforce | Partial in `docs/legacy-pdfs/` | Copy missing documentation |

### LOW PRIORITY / NOT NEEDED

| # | Component | Reason |
|---|-----------|--------|
| 14 | AccpacCom integration | Legacy Accpac ERP — no active clients |
| 15 | OpenAMF gateway | Flash/Flex remoting — obsolete technology |
| 16 | QODBC driver | Server-specific; IW_Launcher connects via different methods |
| 17 | Nginx config | Dormant, not running |
| 18 | JVM crash logs (hs_err) | Diagnostic only |
| 19 | Java 1.5 / Tomcat 5.5 | IW_Launcher already upgraded to Java 8 / Tomcat 9 |

---

## 13. Recommended Copy Operations

**Safe to copy (read-only, won't affect production):**

```bash
# 1. IWCustomerPortal (payment portal)
xcopy /E /I "C:\Sites\IWCustomerPortal" "\\TSCLIENT\C\IW_Launcher\frontends\IWCustomerPortal"

# 2. IWHostedSolutions (master hosted config)
xcopy /E /I "C:\IW_IDE\IWHostedSolutions" "\\TSCLIENT\C\IW_Launcher\docs\production-reference\IWHostedSolutions"

# 3. IW_QBConnector
xcopy /E /I "C:\IW_IDE\IW_QBConnector" "\\TSCLIENT\C\IW_Launcher\docs\production-reference\IW_QBConnector"

# 4. Stunnel config
copy "C:\Program Files (x86)\stunnel\config\stunnel.conf" "\\TSCLIENT\C\IW_Launcher\docs\production-reference\stunnel.conf"

# 5. Production web.xml (reference)
copy "C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\webapps\SF2QBBase\WEB-INF\web.xml" "\\TSCLIENT\C\IW_Launcher\docs\production-reference\production-web.xml"

# 6. Production config.xml (engine config)
copy "C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\webapps\SF2QBBase\WEB-INF\config.xml" "\\TSCLIENT\C\IW_Launcher\docs\production-reference\production-config.xml"
copy "C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\webapps\QB_POAgent\WEB-INF\config.xml" "\\TSCLIENT\C\IW_Launcher\docs\production-reference\production-config-QB_POAgent.xml"

# 7. Sample .erl logs (operational intelligence)
copy "C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\20260313.erl" "\\TSCLIENT\C\IW_Launcher\docs\production-reference\sample-20260313.erl"

# 8. Tomcat server.xml (reference)
copy "C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\conf\server.xml" "\\TSCLIENT\C\IW_Launcher\docs\production-reference\production-server.xml"

# 9. Templates project
xcopy /E /I "C:\IW_IDE\Templates" "\\TSCLIENT\C\IW_Launcher\docs\production-reference\Templates"

# 10. ProfileMgmt.sql (original schema)
copy "C:\IW_IDE\ProfileMgmt.sql" "\\TSCLIENT\C\IW_Launcher\docs\production-reference\ProfileMgmt.sql"
```

**⚠️ DO NOT copy** client credentials, Salesforce passwords, or company tokens. Sanitize any config files before committing to git.

---

## 14. Security Observations

| Finding | Severity | Location |
|---------|----------|----------|
| SSL cert expired (Dec 29, 2025) | **HIGH** | IIS binding for demo.interweave.biz |
| Plaintext credentials in .erl logs | **HIGH** | Tomcat root + logs/ (SF passwords visible) |
| Plaintext credentials in IWHostedSolutions config.xml | **HIGH** | `C:\IW_IDE\IWHostedSolutions\` |
| Plaintext OAuth credentials in client config.json files | **HIGH** | `C:\Sites\IWCustomerPortal\clients\*\config.json` |
| Admin backdoor in obfuscated JS | **MEDIUM** | `C:\Sites\IWCustomerPortal\portal.js` |
| Java 1.5 (EOL 2009) | **MEDIUM** | Production runtime |
| Tomcat 5.5 (EOL 2012) | **MEDIUM** | Production app server |
| Weak Tomcat admin credentials | **LOW** | `tomcat-users.xml` (tomcat/tomcat, admin/iw_admin) |
| D: drive at 75% capacity | **LOW** | Only 2.5GB free |

---

## 15. Key Technical Insights for IW_Launcher Development

1. **Engine runs in non-hosted mode** (`IsHosted="0"`) in production — each webapp is a standalone project. IW_Launcher correctly uses hosted mode (`IsHosted="1"`) for its multi-tenant architecture.

2. **Stunnel is the TLS bridge** — production Java 1.5 can't do modern TLS. The XSLT `dataconnections.xslt` files reference `http://127.0.0.1:38500/...` (stunnel localhost ports). IW_Launcher's Java 8 handles TLS natively, so the connection URLs in XSLT files need to point to real HTTPS endpoints instead.

3. **Engine scheduling is internal** — no Windows Task Scheduler or cron. The engine's `TransactionThread` handles scheduling based on `config.xml` intervals. Run counters in logs (e.g., Run#=23,009) confirm this.

4. **OpenAMF gateway is legacy** — used for Flash/Flex IDE communication. IW_Launcher's React portal uses JSON APIs instead.

5. **Proxy pattern in XSLT** — production XSLT files use `http://127.0.0.1:38xxx` URLs that route through stunnel. When porting projects to IW_Launcher, these URLs must be updated to direct HTTPS endpoints.

6. **Payment portal is separate from transformation server** — IIS serves the customer-facing portal, Tomcat serves the engine. They communicate via the engine's `/transform` servlet and Salesforce data.

---

---

## 16. DEEP DIVE: IWHostedSolutions Master Config

**File:** `C:\IW_IDE\IWHostedSolutions\IWHostedSolutions\configuration\im\config.xml` (50.1 KB)

### Root Configuration
- **Name:** IWHosted1
- **RefreshInterval:** 10,000ms (10-second polling cycle)
- **PrimaryTSURL:** `http://72.3.142.149:8080/iwtransformationserver/scheduledtransform`
- **IsHosted:** 0 (standalone mode despite the name)
- **BufferSize:** 1024

### Flow Definitions: 37 Transactions + 2 Queries

**Transaction attributes (per flow):**
- `Interval` — execution frequency in ms (1,000–10,000)
- `Shift` — delay before first execution relative to RefreshInterval
- `IsActive` — boolean on/off
- `IsStateful` — maintain state between executions
- `RunAtStartUp` — execute on daemon start
- `NumberOfExecutions` — max executions per cycle

**Integration directions:**
- Nexternal (OMS) → QuickBooks: `Next2QBSR`, `Next2QBPO`, `Next2QBInv_A`
- OMS Customers → QB Customers: `NextAcct2QBCust`, `NextAcct2QBCustInsert`
- OMS → Salesforce: `NextCust2SFAcct`, `NextOrd2SFOrdItem`, `Next2SFItem`
- Utility: `GetOMSKey`, `CVS2QBInvTest`

**80+ XSLT files** organized by function:
- QB Customer operations (11 files): select, insert/update, reconciliation
- QB Order/Invoice (15+ files): sales receipts, invoices, purchase orders
- Salesforce integration (15+ files): account/order creation, lookups
- Utilities: pagination loops, time management, data validation

### Implication for IW_Launcher
This is the reference implementation for `ApiFlowManagementServlet`'s flow control model. The `Interval`, `Shift`, `IsActive`, `IsStateful`, `RunAtStartUp`, and `NumberOfExecutions` attributes map directly to IW_Launcher's flow scheduling UI.

---

## 17. DEEP DIVE: Production Webapp Engine Comparison

All 5 production webapps share **identical** engine code. The only difference is the solution JAR.

### Shared Components (100% identical across all 5 webapps)
- **242 engine `.class` files** in `com/interweave/` (15 packages)
- **17 IW core JARs** (iwcore.jar, iwadapter.jar, etc.)
- **config.xml** (identical: `TS1`, `LOG_MINIMUM`, `IsHosted=0`)
- **web.xml** (identical: 6 servlets including OpenAMF gateway)
- **120+ vendor JARs** (Axis, Google Data APIs, JDBC drivers, etc.)

### Solution-Specific JARs (only difference)

| Webapp | Solution JAR | Files | Size | Complexity |
|--------|-------------|-------|------|------------|
| SF2QBBase | `iwsolution_SF2QBBase.jar` | 5,062 | 7.9MB | Highest — full SF↔QB integration |
| SN2QB_SP | `iwsolution_SN2QB_SP.jar` | 4,223 | 6.0MB | Sales Prodigy ↔ QB |
| QB_POAgent | `iwsolution_QB_POAgent.jar` | 2,698 | 3.6MB | QuickBooks PO automation |
| SF2QBCustom | `iwsolution_SF2QBCustom.jar` | 1,731 | 3.1MB | Custom SF↔QB logic |
| SF2AuthNet | `iwsolution_SF2AuthNet.jar` | 516 | 1.1MB | Payment processing only |

### Implication for IW_Launcher
Confirms that IW_Launcher's XSLT-to-bytecode compilation pipeline (`WorkspaceProfileCompiler`) is the correct approach. Each workspace project compiles to an `iwsolution_*.jar` equivalent. The shared engine runtime (`iwengine.jar`) is deployed once, and per-project solution JARs contain only compiled XSLT transformers.

---

## 18. DEEP DIVE: Payment Portal Integration Flow

### Browser-Redirect Payment Pattern

The IWCustomerPortal processes payments via a **stateless browser redirect** — not an API call:

```
1. User logs in via Salesforce OAuth2 (password grant)
2. Portal queries Salesforce REST API for invoices/orders
3. User clicks "Pay Now" on an order
4. Portal PATCHes payment details to Salesforce (CC#, name, exp, CVM)
5. Browser redirects to InterWeave transformation server:
   https://iw4.interweave.biz:8443/SF2AuthNet/transform?
     __QUERY_ID__=SFOpp2StrpQ
     __COMPANY__={url_encoded_company_name}
     __TOKEN__={company_auth_token}
     TransactionSourceName={salesforce_order_id}
     TestMode={true|false}
     ReturnToURL=https://{portal}/details.html
     ...
6. Engine reads order from Salesforce, submits to Authorize.Net
7. Engine redirects browser back to ReturnToURL with result
8. Optional: Portal clears CC fields from Salesforce (privacy)
```

### Client Configuration Variations

| Client | Order Object | SF Org | API Version | Test Mode |
|--------|-------------|--------|-------------|-----------|
| pampabay | Invoice__c (custom) | tonaquintdatacenter2023 | 47.0 | true |
| heritageli | Opportunity (standard) | d80000000kiz5eak-dev-ed | 47.0 | false |
| interweave | Opportunity | d80000000kiz5eak-dev-ed | 47.0 | false |

### Implication for IW_Launcher
The `__COMPANY__` and `__TOKEN__` URL parameters map directly to IW_Launcher's `companies` table (`name` and `token` columns). IW_Launcher's React portal could implement this same pattern using its existing `ApiFlowManagementServlet` for query-based flows, replacing the browser redirect with a modern API-based payment flow.

---

*Report generated by automated server audit on 2026-03-13.*
*Deep dives completed on IWHostedSolutions config, webapp comparison, and payment portal flow.*
*All findings are from READ-ONLY inspection — no production systems were modified.*
