# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.
Detailed reference material is in `docs/ai/claude-reference/` — see the index at the bottom.

## Mandatory workflow for ALL AI agents (read this first)
All AI tools/agents working in this repo MUST follow `docs/ai/AI_WORKFLOW.md`.
- If you make changes, you MUST append a session entry to `docs/ai/AI_WORKLOG.md`.
- Every response MUST include `What I did (this response)`.
- For tasks involving legacy InterWeave behavior, terminology, user workflows, or vendor mappings, read `docs/ai/INTERWEAVE_PDF_CONTEXT.md` and use the approved PDF corpus listed there as supporting context.
- PDF-derived guidance is additive context only. Do not replace existing repository docs or established behavior unless the user explicitly asks for replacement.
- **Model selection**: When spawning subagents, use the cheapest model that fits the task (haiku for searches/reads, sonnet for standard edits, opus for complex architecture/security). See `docs/ai/AI_WORKFLOW.md` §6 for the full decision matrix.

## IMPORTANT: InterWoven concept directory
If a `frontends/InterWoven/` directory exists in this repo, it is a concept/prototype snapshot for potential future IDE launcher + Java form web page improvements.

Do not use, read, or reference application code in `frontends/InterWoven/` unless the user explicitly requests it.

Exception: the mirrored legacy manuals under `frontends/InterWoven/docs/IW_Docs/**` may be consulted when they are explicitly referenced by `docs/ai/INTERWEAVE_PDF_CONTEXT.md` or by the user. Treat those files as historical documentation only, not as the source of truth for the prototype frontend.

---

## Project Overview

**InterWeave IDE (IW_IDE)** is an enterprise data integration platform built on Eclipse that creates synchronization workflows between business applications. Java-based IDE connecting APIs (SOAP, REST/JSON) through an internal XML transformation format.

**Three tiers**: IDE (`iw_ide.exe`, Eclipse 3.1) + Web Portal (Tomcat 9.0.83, port 9090) + Database (Supabase Postgres via pooler).
See `docs/ai/claude-reference/ARCHITECTURE.md` for full architecture details.

## Quick Start

```bash
./START.bat          # First time: auto-creates .env, starts Tomcat + IDE + browser
./STOP.bat           # Shutdown everything
```
- **Login**: `__iw_admin__` / `%iwps%`
- **Test user**: `demo@sample.com` / `demo123`
- **Portal URL**: `http://localhost:9090/iw-business-daemon/`
- **React UI**: `http://localhost:9090/iw-portal/`

Individual components: `scripts/start_webportal.bat`, `scripts/start_ide.bat`, `scripts/stop_webportal.bat`

## Critical Warnings (must know on every task)

### Database
- **Supabase pooler (port 6543) is the ONLY working endpoint** — direct (port 5432) is BLOCKED
- JDBC URL **must** include `prepareThreshold=0` (PgBouncer/Supavisor compatibility)
- Pooler username: `postgres.hpodmkchdzwjtlnxjohf`
- Never commit `.env` — contains production credentials

### Compilation
- **Always use `--release 8`** (NOT `-source 1.8 -target 1.8`) when compiling with JDK 9+
  - The endorsed `jaxb-1.0-ea-trimmed.jar` overrides JAXP factories — without `--release 8`, you get `AbstractMethodError` at runtime
- On Windows, use `;` as classpath separator (not `:`)

### Tomcat / PowerShell
- **NEVER set `$env:CATALINA_HOME` / `$env:JRE_HOME` and call Tomcat `.bat` scripts inline** — hangs the terminal
- Always use the wrapper scripts: `START.bat`, `STOP.bat`, `scripts/start_webportal.bat`
- **Tomcat MUST run from Windows PowerShell**, not WSL2 (Supabase unreachable from WSL2)

### React Portal
- Build output at `web_portal/tomcat/webapps/iw-portal/` IS tracked in git
- After any build, commit: `git add web_portal/tomcat/webapps/iw-portal/ && git commit -m "build: update portal"`
- `npm`/`tsc`/`vite` are NOT on PATH — use `node node_modules/...` paths
- TypeScript strict mode, zero errors before commit: `node node_modules/typescript/bin/tsc -b --noEmit`

### JSP Content Security Policy
- `SecurityHeadersFilter` enforces strict CSP: `script-src 'self'` — **no `'unsafe-inline'`**
- **Do NOT use inline `<script>` or `onclick`/`onload` attributes in JSPs**
- Pattern: pass data via `data-*` attributes, read from external `.js` files, use `addEventListener`

### Fresh Clone Requirements
- `jre/` not in git — download Eclipse Adoptium JRE 8 x86 (32-bit), extract to `jre/`
- `web_portal/tomcat/bin/` + `lib/` not in git — run `scripts\setup\install_tomcat.bat` once
- Git LFS required — run `git lfs install && git lfs pull` after clone

---

## Reference Index

Read these files on-demand when working in the relevant area:

| Topic | File | When to read |
|-------|------|--------------|
| **Architecture** | `docs/ai/claude-reference/ARCHITECTURE.md` | Understanding three-tier system, integration flows, engine lifecycle, adding new projects |
| **Development** | `docs/ai/claude-reference/DEVELOPMENT.md` | Compile commands (6 types), servlet bridge, Maven framework, known issues, IDE specifics, sync bridge |
| **React Portal** | `docs/ai/claude-reference/PORTAL.md` | Route mappings (41 routes), API servlets, hooks, components, build instructions |
| **Database** | `docs/ai/claude-reference/DATABASE.md` | DB config, schema structure, connection modes, setup scripts |
| **Environment** | `docs/ai/claude-reference/ENVIRONMENT.md` | Running the app, fresh clone setup, WSL2 notes, web portal URLs, test results |
| **Security** | `docs/ai/claude-reference/SECURITY.md` | CSP details, credential encryption (AES-256-GCM), security notes |
| **Workspace** | `docs/ai/claude-reference/WORKSPACE.md` | Integration projects, XSLT pipeline, transformer inventory, production server reference |
| **Directory** | `docs/ai/claude-reference/DIRECTORY_STRUCTURE.md` | Full directory tree, roadmap summary |

**Other key docs:**
- `docs/ai/AI_WORKFLOW.md` — mandatory AI agent workflow
- `docs/development/BUILD.md` — full Maven build guide
- `docs/development/LOCAL_SERVLETS.md` — local servlet bridge reference
- `docs/development/AI_MANAGEMENT_ARCHITECTURE.md` — AI management layer design
- `docs/security/CREDENTIAL_ENCRYPTION_DESIGN.md` — encryption design doc
- `docs/security/CVE_AUDIT_2026_03_13.md` — vendor JAR CVE audit
- `docs/SERVER_AUDIT_2026_03_13.md` — production server architecture
- `docs/deployment/STUNNEL_MAPPING.md` — stunnel ↔ direct HTTPS port mapping (production log decoding)
- `docs/deployment/FLOW_CATALOG.md` — all 56 flow definitions (production + IW_Launcher)
- `docs/deployment/PRODUCTION_DEPLOYMENT.md` — side-by-side deployment on production server
- `docs/NEXT_STEPS.md` — current development roadmap
