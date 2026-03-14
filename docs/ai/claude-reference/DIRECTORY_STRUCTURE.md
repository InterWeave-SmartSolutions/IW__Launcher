# Directory Structure & Roadmap

## Full Directory Tree

```
IW_Launcher/
├── START.bat                   # Main startup (auto-configures)
├── STOP.bat                    # Shutdown script
├── CHANGE_DATABASE.bat         # Database connection switcher
├── iw_ide.exe                  # Eclipse IDE executable
├── iw_ide.ini                  # IDE config
├── startup.jar                 # Eclipse startup
├── .env                        # Database config (auto-created)
├── .env.example                # Template for .env
│
├── scripts/                    # Advanced scripts
│   ├── setup/                  # Install & config scripts
│   └── sql/                    # SQL migration scripts
│
├── database/                   # Database schemas
│   ├── mysql_schema.sql        # Primary MySQL schema
│   ├── postgres_schema.sql     # PostgreSQL alternative
│   ├── schema.sql              # Original schema
│   └── schemas/                # XSD schemas
│
├── docs/                       # Documentation
│   ├── ai/                     # AI workflow & worklog
│   ├── assa-specs/             # ASSA specification docs
│   ├── authentication/         # config.xml templates (supabase/hosted/local) with flow definitions
│   ├── development/            # Build, API, contributing guides
│   ├── legacy-pdfs/            # Original PDF documentation
│   ├── production-reference/   # Production server audit (525 files from 107525-UVS13)
│   │   ├── configs/            # stunnel, nginx, QODBC, SSL, services, firewall, env vars
│   │   ├── documentation/      # Customer portal docs (SF, Creatio, Payments)
│   │   ├── engine-jars/        # Production engine JAR inventory
│   │   ├── engine-logs/        # Client-specific .erl files (3 clients, 36 logs)
│   │   ├── iw-ide/             # IDE snapshots comparison, AccpacCom, Data/, PDFs
│   │   ├── sites/              # IWCustomerPortal + Win3 variant (payment portals)
│   │   └── workspace-projects/ # IWHostedSolutions, IW_QBConnector, Templates, TomcatWebapps
│   ├── security/               # Security & credential docs, CVE audit
│   ├── setup/                  # Installation guides
│   ├── testing/                # Test plans
│   ├── tutorials/              # Training materials
│   └── SERVER_AUDIT_2026_03_13.md # 551-line production server architecture + gap analysis
│
├── frontends/                  # Front-end applications
│   ├── iw-portal/              # React dashboard (Vite 7 + React 19 + TS + Tailwind 4 + shadcn/ui)
│   ├── InterWoven/             # React SPA (concept/prototype — do not use per CLAUDE.md rules)
│   ├── IWCustomerPortal        # Legacy payment portal HTML from production server (reference only)
│   └── assa/                   # Static HTML design prototypes (design reference for iw-portal)
│       ├── assa_customer_portal/ # 9 pages: billing, intake, library, profile, resource, search...
│       └── assa_master_console/  # 9 pages: analytics, audit, content, integrations, users...
│
├── docs/ui-ux/                 # UI/UX strategy + prototype HTML
│   ├── iw_associate_portal/    # Associate Portal prototype (9 pages, ASSA tokens, 2026-02-09)
│   ├── iw_master_console/      # Master Console prototype (10 pages, ASSA tokens, 2026-02-06)
│   ├── PORTAL_ARCHITECTURE.md  # Three-portal system architecture + phased adoption plan
│   ├── UI_UX_DESIGN_APPROACH.md # Primary design playbook
│   ├── UI_UX_ANALYSIS.md       # Deep-dive gap analysis
│   ├── COMPETITIVE_LANDSCAPE_EXPANDED.md # 50+ platform research
│   └── IMPLEMENTATION_PLAN.md  # Backend-aware rollout plan
│
├── jre/                        # Bundled Java 8 runtime
├── plugins/                    # Eclipse plugins
│   ├── iw_sdk_1.0.0/           # InterWeave SDK plugin
│   └── org.eclipse.*.jar       # Eclipse core plugins
├── src/                        # Maven project: error framework, validation, ErrorHandlingFilter
│   ├── main/java/com/interweave/error/       # IWError, ErrorCode, ErrorLogger
│   ├── main/java/com/interweave/validation/  # ConnectionValidator, SchemaValidator, etc.
│   ├── main/java/com/interweave/web/         # ErrorHandlingFilter (ACTIVE)
│   └── main/java/com/interweave/help/        # HelpLinkService
│
├── web_portal/                 # Web server
│   ├── tomcat/                 # Apache Tomcat 9.0.83
│   │   ├── bin/                # Tomcat binaries
│   │   ├── conf/               # server.xml, web.xml
│   │   ├── logs/               # Server logs
│   │   └── webapps/            # Deployed apps
│   │       └── iw-business-daemon/
│   ├── start_web_portal.bat    # Windows start
│   ├── stop_web_portal.bat     # Windows stop
│   └── README.md               # Web portal docs
│
├── workspace/                  # IDE workspace
│   ├── .metadata/              # Eclipse metadata
│   ├── SF2AuthNet/             # Salesforce-AuthNet (142 XSLTs, 472 classes)
│   ├── Creatio_QuickBooks_Integration/  # CRM→QB (7 flows + 7 queries)
│   ├── Creatio_Magento2_Integration/    # CRM↔Magento (11 XSLTs, 11 classes)
│   ├── GeneratedProfiles/      # Compiler output (per-profile overlays)
│   └── IW_Runtime_Sync/        # Wizard config mirror (auto-generated)
│
└── configuration/              # Eclipse configuration
    └── org.eclipse.update/     # Update manager config
```

## Roadmap and Next Steps

See `docs/NEXT_STEPS.md` for the current prioritized development queue:
- **Done**: ErrorHandlingFilter ACTIVE, RBAC middleware, cloudflared installed, CSP hardened, bcrypt migration (PasswordHasher + progressive rehash), AI Management Architecture Phase 1 (Workspace Read API + XSLT Build API), credential encryption at rest (AES-256-GCM), vendor JAR CVE audit, engine flow definitions in config templates, production server audit
- **Done (Vercel)**: Cloudflare quick tunnel working, Vercel auto-deploys on push, login via Bearer token verified through tunnel proxy
- **Blocked**: Configure monitoring email (needs SMTP credentials)
- **Future**: AI Management Architecture Phase 2+ (write operations, connections, change tracking), CVE remediation (Tomcat 9.0.98+, Xerces/Xalan upgrade)
