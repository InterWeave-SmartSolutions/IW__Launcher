# IWHostedSolutions Flow Catalog

Authoritative reference of all known flow definitions across production (IWHostedSolutions)
and IW_Launcher config templates.

Sources:
- Production: `docs/production-reference/workspace-projects/IWHostedSolutions/im-config.xml`
- IW_Launcher: `docs/authentication/config.xml.{hosted,supabase,local}.template`

> **Note:** Not all templates contain the same flows. The `hosted` and `supabase` templates
> have the full set (SF2AUTH + CRM2M2 = 17 flows). The `local` template has only CRM2M2
> flows (4 transactions + 1 query = 5 flows) since it was built for offline testing.
> The `standalone` template has all flows commented out as examples.

---

## Summary

| Source | Transactions | Queries | Total |
|--------|-------------|---------|-------|
| Production (IWHostedSolutions) | 37 | 2 | 39 |
| IW_Launcher (SF2AUTH) | 3 | 0 | 3 |
| IW_Launcher (CRM2M2) | 8 | 6 | 14 |
| **Combined unique** | **48** | **8** | **56** |

---

## Production Flows — OMS ↔ QuickBooks (Legacy)

These flows sync between an OMS (Order Management System) and QuickBooks via JDBC/ODBC.
They are the oldest integration pattern and use `sun.jdbc.odbc.JdbcOdbcDriver`.

| Flow ID | Type | Description | Status |
|---------|------|-------------|--------|
| `Next2QBSR` | Transaction | OMS Orders → QB Sales Receipts (by order number range) | Legacy |
| `NextAcct2QBCust` | Transaction | OMS Customers → QB Customers (by customer number range) | Legacy |
| `NextAcct2QBCustInsert` | Transaction | OMS Customers → QB Customers (insert only) | Legacy |
| `NextOrdCust2QBAcctSR` | Transaction | OMS Customers+Orders → QB Accounts+Sales Receipts+POs (scheduled) | Legacy |
| `NextAcct2QBCustOR` | Transaction | OMS Customers → QB (by order modification time) | Legacy |
| `Next2QBSROR` | Transaction | OMS Orders → QB Sales Receipts (by order modification time) | Legacy |
| `Next2QBPOOR` | Transaction | OMS Orders → QB Purchase Orders (by order modification time) | Legacy |
| `NextOrdCust2QBAcct` | Transaction | OMS Customers → QB Accounts (scheduled, time-based) | Legacy |
| `Next2QBPO` | Transaction | (no description — test flow) | Legacy/Test |
| `CVS2QBInvTest` | Transaction | (no description — test flow) | Legacy/Test |

### Production Flows — OMS ↔ QuickBooks (Client "_A" variants)

The `_A` suffix flows are customized variants for specific production clients.

| Flow ID | Type | Description | Status |
|---------|------|-------------|--------|
| `NextAcct2QBCustOR_A` | Transaction | OMS Customers → QB (order modification time, client A) | Active |
| `NextOrdCust2QBAcct_A` | Transaction | OMS Customers+Orders → QB (accounts, SRs, invoices) | Active |
| `Next2QBSRInvOR_A` | Transaction | OMS Orders → QB Sales Receipts + Invoices (modification time) | Active |
| `NextOR_Atest` | Transaction | (test flow) | Test |
| `NextAcct2QBCust_A` | Transaction | OMS Customers → QB (customer number range, client A) | Active |
| `NextAcct2QBCust_ATest` | Transaction | (test variant) | Test |
| `Next2QBSR_A` | Transaction | OMS Orders → QB Sales Receipts + Invoices (modification time) | Active |
| `Next2QBInv_A` | Transaction | OMS Orders → QB Invoices (modification time) | Active |
| `NextOSE_A` | Transaction | OMS Orders → QB (scheduled, modification time) | Active |
| `NextOrd2QBSRInv_A` | Transaction | OMS Orders → QB Sales Receipts + Invoices (order number range) | Active |
| `NextAcct2QBCustCompare_A` | Transaction | Compare OMS vs QB customers (generates miss file) | Active |
| `NextOrd2QBSRInvCompare_A` | Transaction | Compare OMS vs QB orders (generates miss file) | Active |
| `NextAcct2QBCustCompare_ATest` | Transaction | (test variant of compare) | Test |
| `NextMissAcct2QBCust_A` | Transaction | Sync missed customers from compare file | Active |
| `NextMissAcct2QBCust_ATest` | Transaction | (test variant) | Test |
| `NextMissOrd2QBSRInv_A` | Transaction | Sync missed orders from compare file | Active |
| `NextOrd2QBSRInvCompare_ATest` | Transaction | (test variant of order compare) | Test |
| `NextOrd2QBSRInvCompare_AOD` | Transaction | (on-demand order compare) | Active |
| `NextAcct2QBCustDR_A` | Transaction | OMS Customers → QB (modification timestamp) | Active |
| `NextOrd2QBSRInvDR_A` | Transaction | OMS Orders → QB SRs + Invoices (modification timestamp) | Active |
| `NextOrd2QBSRInvDR_ATest` | Transaction | (test variant) | Test |
| `NextOrdCust2QBAcct_ATest` | Transaction | (test variant) | Test |
| `NextMissOrd2QBSRInv_A_Test` | Transaction | (test variant) | Test |

### Production Flows — OMS ↔ Salesforce

| Flow ID | Type | Description | Status |
|---------|------|-------------|--------|
| `Next2SFAcct_old` | Transaction | OMS Customers → SF Accounts (by customer number range) | Legacy |
| `Next2SFItem` | Transaction | OMS Orders → SF Opportunities + Line Items (by order number range) | Legacy |
| `NextOrd2SFOrdItem` | Transaction | OMS Orders → SF Purchase Orders + Line Items (scheduled) | Legacy |
| `NextCust2SFAcct` | Transaction | OMS Customers → SF Accounts (scheduled, time-based) | Legacy |

### Production Queries

| Flow ID | Type | Description | Status |
|---------|------|-------------|--------|
| `GetOMSKey` | Query | Retrieve OMS API key | Active |
| `GetOMSKey_A` | Query | Retrieve OMS API key (client A variant) | Active |

---

## IW_Launcher Flows — SF2AUTH (Salesforce ↔ Authorize.Net)

Workspace project: `SF2AuthNet` | Solution type: `SF2AUTH`

| Flow ID | Type | Description | Stateful | Status |
|---------|------|-------------|----------|--------|
| `SFTransactions2Auth` | Transaction | SF scheduled transaction sync to Authorize.Net | Yes | **Supported** |
| `SFTransactions2AuthN` | Transaction | SF on-demand transaction to Authorize.Net | No | **Supported** |
| `SFTransactions2AuthDRS` | Transaction | SF data reconciliation sync to Authorize.Net | No | **Supported** |

---

## IW_Launcher Flows — CRM2M2 (Creatio ↔ Magento 2)

Workspace project: `Creatio_Magento2_Integration` | Solution type: `CRM2M2`

### Transactions

| Flow ID | Type | Description | Stateful | Status |
|---------|------|-------------|----------|--------|
| `BPMTransactions2Magento` | Transaction | Creatio scheduled transaction sync to Magento 2 | Yes | **Supported** |
| `CRMAcctSync2M2` | Transaction | Creatio Account → Magento 2 Customer | Yes | **Supported** |
| `CRMOrderSync2M2` | Transaction | Creatio Sales Order → Magento 2 Order | Yes | **Supported** |
| `CRMInvSync2M2` | Transaction | Creatio Invoice → Magento 2 Invoice | Yes | **Supported** |
| `CRMProdSync2M2` | Transaction | Creatio Product → Magento 2 Product/SKU | Yes | **Supported** |
| `CRMSRSync2M2` | Transaction | Creatio Service Request → Magento 2 RMA | Yes | **Supported** |
| `M2OrderSync2CRM` | Transaction | Magento 2 Order → Creatio (reverse sync) | Yes | **Supported** |
| `M2InventorySync2CRM` | Transaction | Magento 2 Inventory → Creatio (reverse sync) | Yes | **Supported** |

### Queries

| Flow ID | Type | Description | Status |
|---------|------|-------------|--------|
| `CRMAcct2M2Q` | Query | Creatio Account → Magento 2 Customer (on-demand) | **Supported** |
| `CRMOrder2M2Q` | Query | Creatio Order → Magento 2 Order (on-demand) | **Supported** |
| `CRMInv2M2Q` | Query | Creatio Invoice → Magento 2 Invoice (on-demand) | **Supported** |
| `CRMProd2M2Q` | Query | Creatio Product → Magento 2 Product/SKU (on-demand) | **Supported** |
| `M2Order2CRMQ` | Query | Magento 2 Order → Creatio (on-demand) | **Supported** |
| `M2Inv2CRMQ` | Query | Magento 2 Inventory → Creatio (on-demand) | **Supported** |

---

## Flow Parameter Reference

### Common Parameters (all flows)

| Parameter | Description | Fixed |
|-----------|-------------|-------|
| `applicationname` | Always `iwtransformationserver` | Yes |
| `tranname` | Entry-point transaction name in `soltran.xslt` | Yes |
| `ReturnString` | Empty placeholder for engine return value | Yes |
| `QueryStartTime` | Timestamp for incremental sync (where to resume) | No |

### OMS-Specific Parameters (production)

| Parameter | Description |
|-----------|-------------|
| `OMS_ACCOUNT_NAME` | OMS account identifier |
| `OMS_KEY` | OMS API key (credential) |
| `USER` / `PASSWORD` | OMS API credentials |
| `QBDriver` | QuickBooks JDBC/ODBC driver class |
| `QBURL` | QuickBooks JDBC/ODBC connection URL |
| `QBUser` / `QBPassword` | QuickBooks database credentials |
| `ORDER_START` / `ORDER_END` | Order number range for batch processing |
| `Counter` | Position in missed-records file for reconciliation flows |

### CRM2M2-Specific Parameters (IW_Launcher)

| Parameter | Description |
|-----------|-------------|
| `AccountObjectName` | Creatio entity name for account queries |
| `ObjectName` | Generic object name for query flows |
| `UseIdorName` | Query by `Id` or `Name` field |
| `TransactionSourceName` | Source identifier for query routing |
| `TestMode` | Enable test mode for dry-run queries |

---

## Coverage Analysis

### What IW_Launcher supports that production doesn't have:
- Full CRM ↔ Magento 2 bidirectional sync (8 transactions + 6 queries)
- Query-based on-demand flows (CRM2M2 queries callable via URL)
- `Solution` attribute for per-company flow isolation

### What production has that IW_Launcher doesn't support yet:
- OMS ↔ QuickBooks integration (37 flows) — requires JDBC/ODBC driver (`sun.jdbc.odbc.JdbcOdbcDriver`)
  which was removed in Java 8. Would need a JDBC-ODBC bridge library or direct QB API.
- OMS ↔ Salesforce integration (4 flows) — would need OMS API adaptor
- Reconciliation/compare flows (`*Compare*`, `*Miss*`) — pattern for detecting sync gaps
- `GetOMSKey` queries — OMS credential retrieval

### Migration path for production flows:
1. **SF2AUTH flows**: Already in IW_Launcher. Southern Lamps uses these.
2. **CRM2M2 flows**: Already in IW_Launcher. Pampa Bay / InterWeave use these.
3. **OMS→QB flows**: Require workspace project creation + QB connectivity solution.
   The JDBC/ODBC bridge issue makes this the hardest migration target.
4. **OMS→SF flows**: Similar to OMS→QB but output goes to Salesforce (already connected).
