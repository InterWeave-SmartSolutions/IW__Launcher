# Architecture Reference

## Three-Tier Integration System

1. **IDE (`iw_ide.exe`)** - Eclipse-based development environment
   - Located in root directory
   - Requires bundled JRE at `jre/`
   - Eclipse 3.1 based with custom `iw_sdk_1.0.0` plugin
   - Workspace projects stored in `workspace/`

2. **Web Portal (`web_portal/tomcat/`)** - Apache Tomcat 9.0.83 server
   - Deploys `iw-business-daemon.war` (deployed as expanded directory in `webapps/`)
   - User authentication and company management
   - Hosts JSP interfaces for profile/config management
   - Default port: 9090
   - Also deploys `iwtransformationserver` — legacy transformation engine (**operational**: 133 vendor JARs + `iwengine.jar` with 125 engine classes. `/transform` returns real IW XML responses. Engine classes recovered from legacy Tomcat 5.5 install.)
   - Also deploys `iw-portal` — React dashboard (`frontends/iw-portal/` build output)

3. **Database** - Authentication and configuration (MySQL or Postgres)
   - Schemas: `database/postgres_schema.sql` (primary/Supabase), `database/monitoring_schema_postgres.sql` (monitoring), `mysql_schema.sql` (legacy), `schema.sql`
   - Connection via Supabase **pooler** (port 6543, username `postgres.hpodmkchdzwjtlnxjohf`) with RLS on all 14 tables
   - **IMPORTANT (2026-02-26)**: Direct connection (port 5432) is **blocked/unreachable** from this network. Use the **pooler** (port 6543) with `prepareThreshold=0` in the JDBC URL. See `context.xml` for working config.
   - Three connection modes (configured via `.env`):
     - `supabase` - Shared Supabase Postgres (default, verified working via pooler)
     - `interweave` - InterWeave hosted MySQL
     - `local` - Offline mode (admin only)

## Integration Flow Architecture

All integration flows follow a decoupling pattern:
```
Source API → IW XML Format → Transformation (XSLT) → Destination API
```

**Flow Types:**
- **Transaction Flows** - Scheduled backend processes
- **Utility Flows** - On-demand flows
- **Queries** - Pseudo-REST API callable via URL (for Salesforce/Creatio buttons)

Projects are stored in `workspace/` and contain:
- Configuration
- Transactions
- Connections (to external systems)
- XSLT transformers
- Integration flows (transaction flows, utility flows, queries)

## Engine Flow Lifecycle

```
WEB-INF/config.xml (all flow definitions) → ConfigContext at Tomcat startup
  → bindHostedProfile() at login (or POST /api/flows/initialize)
  → Creates TransactionThread per profile per flow
  → POST /api/flows/start|stop to run/halt individual flows
```

**Per-company flow isolation:** `ApiFlowManagementServlet` reads `solutionType` from session, maps it to a workspace project via `config/workspace-profile-map.properties`, parses that project's `im/config.xml` for allowed flow IDs, and filters ConfigContext output. Each user only sees their company's flows.

## Adding a New Integration Project

1. Create workspace project with `im/config.xml` (TransactionDescription + Query elements)
2. Add flows to `WEB-INF/config.xml` (engine won't load flows not defined here)
3. Add `SOLUTION_TYPE=ProjectName` mapping to `config/workspace-profile-map.properties`
4. Add company record in DB with matching `solution_type`
5. Restart Tomcat (config.xml is loaded once at startup)

## Standalone Mode (IsHosted="0")

The engine supports standalone mode for testing without the multi-tenant layer:
- `IsHosted="0"` — admin-only login (`__iw_admin__`), no database required
- All flows visible to admin (no per-company isolation)
- Flow parameters (credentials, URLs) are inline in config.xml, not from DB
- Production server (107525-UVS13) runs in this mode
- Template: `docs/authentication/config.xml.standalone.template`
- See also: `docs/deployment/PRODUCTION_DEPLOYMENT.md` for side-by-side deployment

## Engine XML Schemas

Authoritative XSD schemas for the engine's XML formats:
- `database/schemas/engine/iwtransformationserver.xsd` — TransformationServerConfiguration element
- `database/schemas/engine/iwtransactions.xsd` — iwmappings/transaction flow definitions
- `database/schemas/engine/iwemail.xsd` — IWEMail notification format
- `database/schemas/engine/iwprotocol.xsd` — IW Protocol (iwp:) data exchange format

These are the original schemas from the production server (authored by Blockade Systems Corp.).
The project also has modernized schemas in `database/schemas/` (ts-config.xsd, im-config.xsd, transactions.xsd).
