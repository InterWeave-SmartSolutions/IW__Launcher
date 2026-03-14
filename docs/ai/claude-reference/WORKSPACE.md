# Workspace & Integration Projects Reference

## Integration Projects

Workspace projects with full transformer pipelines:
- `SF2AuthNet` - Salesforce to Authorize.Net/payment gateways (142 XSLT transformers, 472 compiled classes)
- `Creatio_Magento2_Integration` - Bidirectional Creatio ↔ Magento 2 (11 XSLT transformers, 11 compiled classes)
- `Creatio_QuickBooks_Integration` - Creatio to QuickBooks (soltran.xslt defined, individual transformers pending)

Common integration patterns documented in `docs/tutorials/`:
- `InterWeave-IDE-Training-1.md` - IDE basics
- `InterWeave-IDE-Training-2.md`
- `InterWeave-IDE-Training-3.md`
- `InterWeave-IDE-Review-4.md`

## XSLT Transformer Build Pipeline

Each workspace project contains XSLT transformer files that define field mappings between source and destination systems. These are compiled to Java bytecode using Apache XSLTC.

**Transformer file structure** (per workspace project):
```
workspace/<Project>/
├── xslt/
│   ├── SyncAccounts_CRM2MG.xslt     ← individual transformer source
│   ├── GetMagentoOrder.xslt          ← query transformer source
│   ├── include/dataconnections.xslt  ← connection credentials (XSLT params)
│   └── Site/
│       ├── include/
│       │   ├── sitetran.xslt         ← shared site transactions (index, session)
│       │   └── appconstants.xslt     ← session variables
│       └── new/
│           ├── transactions.xslt     ← master stylesheet (imports all above)
│           ├── include/soltran.xslt  ← solution-specific flow definitions
│           └── xml/transactions.xml  ← static build output (populates IDE views)
└── classes/iwtransformationserver/
    ├── SyncAccounts_CRM2MG.class     ← compiled transformer bytecode
    └── GetMagentoOrder.class
```

**Current transformer inventory**:
| Project | XSLT Sources | Compiled Classes | Adapter Types |
|---------|-------------|-----------------|---------------|
| SF2AuthNet | 142 | 472 | SOAP, HTTP, SQL |
| Creatio_Magento2_Integration | 11 | 11 | REST/JSON |
| Creatio_QuickBooks_Integration | _(soltran.xslt defined, individual transformers pending)_ | — | REST/JSON, HTTP |

**WorkspaceProfileCompiler** now copies transformer files (`.xslt` sources + `.class` bytecode) from template projects into `GeneratedProfiles/` during profile compilation, ensuring generated profiles are self-contained.

## Production Server Reference

A comprehensive audit of the production server (107525-UVS13, Windows Server 2016) was performed on 2026-03-13. Key artifacts:

- **`docs/SERVER_AUDIT_2026_03_13.md`** — 551-line architecture overview: services, stunnel map, IIS config, webapp inventory, engine classes, client roster, gap analysis
- **`docs/production-reference/`** — 525 files extracted from production (configs, engine logs, workspace projects, IDE snapshots, customer portal, documentation)
- **`docs/production-reference/SWEEP-FINDINGS.txt`** — 11-category sweep summary (what was new vs already covered)
- **`docs/production-reference/iw-ide/IW_IDE-SNAPSHOTS-COMPARISON.txt`** — 8 historical IDE backup comparisons (1 unique item: Charge03.xml from 2024-02-01)

**Production architecture** (for reference when building local equivalents):
- Tomcat 5.5 (Java 1.5) with 5 webapp variants, `IsHosted="0"` (non-hosted standalone mode)
- Stunnel TLS proxy (11 tunnels: Salesforce, Authorize.Net, Creatio, Sage Intacct)
- IIS serving IWCustomerPortal (payment portal) on ports 80/443
- QODBC → QuickBooks integration via JDBC-ODBC bridge
- 3 active clients: amagown (InterWeave), pampabay, southernlampsinc

**Production intelligence (integrated in Session 29):**
- `database/schemas/engine/` — 4 original XSD schemas (TransformationServerConfiguration, iwmappings, IWEMail, iwProtocol)
- `workspace/Templates/` — 7 IWXT templates (SF Account/Contact/Lead/Opportunity upserts, Product creation, Email templates)
- `docs/deployment/STUNNEL_MAPPING.md` — complete stunnel ↔ direct HTTPS port mapping
- `docs/deployment/FLOW_CATALOG.md` — all 56 flow definitions (39 production + 17 IW_Launcher)
- `docs/deployment/PRODUCTION_DEPLOYMENT.md` — side-by-side deployment guide
- `docs/authentication/config.xml.standalone.template` — IsHosted="0" engine config (production-compatible)
