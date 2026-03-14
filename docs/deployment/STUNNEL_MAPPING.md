# Stunnel ↔ Direct HTTPS Mapping Reference

Production server (107525-UVS13, Windows Server 2016) runs stunnel because the legacy
Java 1.5 runtime has no native TLS support. IW_Launcher's Java 8 JRE handles HTTPS
directly — stunnel is unnecessary.

This document maps every production stunnel tunnel to its real endpoint so you can:
- Read production engine logs (`.erl` files) that reference `127.0.0.1:38xxx`
- Port credential configs between environments
- Understand `IWCustomerPortal`'s `interweave_url` field

Source: `docs/production-reference/configs/stunnel/stunnel.conf`

---

## Active Tunnels (uncommented in stunnel.conf)

| Tunnel Name | Direction | Local Port | Real Endpoint | Client | Purpose |
|-------------|-----------|-----------|---------------|--------|---------|
| `server4tls` | **Inbound** | 8443 → 8080 | (TLS termination) | IIS/external | Inbound HTTPS to Tomcat 5.5 |
| `salesforcel` | Outbound | 38500 | `login.salesforce.com:443` | Engine | SF SOAP login (production orgs) |
| `salesforcet` | Outbound | 38600 | `test.salesforce.com:443` | Engine | SF SOAP login (sandbox orgs) |
| `salesforcecp1` | Outbound | 38498 | `fun-agility-76422.lightning.force.com:443` | Engine | Southern Lamps SF (production) |
| `salesforcecp2` | Outbound | 38497 | `fun-agility-76422--devpartial.sandbox.lightning.force.com:443` | Engine | Southern Lamps SF (sandbox) |
| `PampaCreatio` | Outbound | 38477 | `pampabay.creatio.com:443` | Engine | Pampa Bay CRM |
| `DalkiaCreatio` | Outbound | 38488 | `dev-dalkiasolutions.creatio.com:443` | Engine | Dalkia Solutions CRM |
| `creatio-bgp` | Outbound | 38489 | `dev-pampabay.creatio.com:443` | Engine | InterWeave dev Creatio (SNI: inter-weave.creatio.com) |
| `sageintact` | Outbound | 38490 | `api.intacct.com:443` | Engine | Sage Intacct ERP API |
| `auth2SF` | Outbound | 38478 | `api.authorize.net:443` | Engine | Authorize.Net payment processing (production) |
| `auth` | Outbound | 38486 | `test.authorize.net:443` | Engine | Authorize.Net payment processing (test/sandbox) |

## Commented-Out Tunnels (disabled but present in config)

| Category | Port Range | Count | Purpose |
|----------|-----------|-------|---------|
| `salesforcel01`–`salesforcel88` | 38501–38588 | 88 | Multi-tenant SF login (production) per hosted client |
| `salesforcet01`–`salesforcet93` | 38601–38693 | 93 | Multi-tenant SF login (sandbox) per hosted client |
| `salesforcep01`–`salesforcep20` | 38701–38720 | 20 | SF Partner API per hosted client |
| `salesforcee01`–`salesforcee20` | 38801–38820 | 20 | SF Enterprise API per hosted client |
| `auth_cim` | 38485 | 1 | Authorize.Net CIM (deprecated) |
| `salesforcecp` | 38499 | 1 | Generic SF custom portal (template) |

**Total capacity**: 11 active + 223 reserved = 234 tunnel definitions.
The numbered tunnels (01–88, 01–93, etc.) were for hosted multi-tenant mode where each
client got a dedicated stunnel port for Salesforce SOAP login isolation.

---

## IW_Launcher Replacement: Direct HTTPS

IW_Launcher's Java 8 (OpenJDK 1.8.0_382 Temurin) includes native TLS 1.2 support with
SNI, making stunnel unnecessary:

| Production Reference | IW_Launcher Equivalent |
|---------------------|----------------------|
| `http://127.0.0.1:38500/...` | `https://login.salesforce.com/...` |
| `http://127.0.0.1:38600/...` | `https://test.salesforce.com/...` |
| `http://127.0.0.1:38498/...` | `https://fun-agility-76422.lightning.force.com/...` |
| `http://127.0.0.1:38477/...` | `https://pampabay.creatio.com/...` |
| `http://127.0.0.1:38488/...` | `https://dev-dalkiasolutions.creatio.com/...` |
| `http://127.0.0.1:38489/...` | `https://dev-pampabay.creatio.com/...` |
| `http://127.0.0.1:38490/...` | `https://api.intacct.com/...` |
| `http://127.0.0.1:38478/...` | `https://api.authorize.net/...` |
| `http://127.0.0.1:38486/...` | `https://test.authorize.net/...` |

### Key differences

1. **Connection URLs in XSLT files are NOT hardcoded** — `dataconnections.xslt` templates
   have empty params populated at compile time by `WorkspaceProfileCompiler` from the DB.
   Changing from stunnel to direct HTTPS only requires updating the DB connection records.

2. **Multi-tenant stunnel ports are eliminated** — Java 8 SNI means one HTTPS connection
   per endpoint handles all tenants. The 222 reserved ports for multi-tenant isolation are
   replaced by the `IsHosted="1"` profile-binding mechanism.

3. **Inbound TLS** — Production uses `server4tls` (stunnel) + IIS for inbound HTTPS.
   IW_Launcher uses Cloudflare Tunnel or Vercel's edge for the same purpose.

---

## Deploying Alongside Production

If deploying IW_Launcher on the same server as production:

1. **Do NOT modify stunnel.conf** — production depends on it
2. IW_Launcher Tomcat 9 (port 9090) does not use stunnel at all
3. Both can coexist because IW_Launcher uses direct HTTPS while production uses stunnel
4. If IW_Launcher needs to reach the same endpoints, it connects directly (no port conflict)
5. The only shared resource is the network — both hit the same external APIs but via different paths
