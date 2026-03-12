# ADR 004 — IDE ↔ Portal Sync Improvements

**Status:** Proposed
**Date:** 2026-03-12
**Decision makers:** Development team
**Context:** Session 18 sync architecture investigation + solution design

## Context

The IDE ↔ Portal bidirectional sync system has four known limitations:

1. **Polling-only** — React polls every 10s, bridge polls every 1s. Worst-case latency: ~13s.
2. **Opaque text import** — IDE→Portal imports entire XML blob, no field-level visibility.
3. **XML schema mismatch** — Wizard saves flat `<SF2QBConfiguration>`, IDE uses hierarchical `<BusinessDaemonConfiguration>`. Investigation revealed these are **fundamentally different documents** (configuration options vs. flow execution plans), not variants of the same schema.
4. **No conflict resolution** — Last write wins. No version tracking or merge capability.

### Current Architecture

| Component | File | Mechanism |
|-----------|------|-----------|
| React hook | `src/hooks/useSync.ts` | `useQuery` polling `/api/sync/status` every 10s |
| JSON API | `ApiWorkspaceSyncServlet.java` | GET status, POST push/pull, GET log |
| Text API | `WorkspaceProfileSyncServlet.java` | exportAll, syncProfile, importProfile (for scripts) |
| Bridge | `scripts/sync_bridge.ps1` | PowerShell 5.1 polling every 1s with 2s debounce |
| State | `.push_epoch` sidecar files | Epoch ms from DB `updated_at` (avoids 32-bit JVM timezone bug) |
| DB table | `company_configurations` | `updated_at` TIMESTAMP, no version column |

### Critical Finding: Two Different XML Documents

The wizard XML and IDE XML serve completely different purposes and cannot be unified:

| Wizard XML | IDE XML |
|-----------|---------|
| `<SF2QBConfiguration>` / `<SF2NSConfiguration>` | `<BusinessDaemonConfiguration>` |
| Flat key-value (16-18 fields) | Hierarchical (root attrs + TransactionDescription + Query + Parameter) |
| Configuration options: credentials, sync direction, environment | Flow execution: scheduling, URL routing, parameters |
| Stored in `company_configurations.configuration_xml` | Stored in `workspace/{Project}/configuration/im/config.xml` |
| Consumed by wizard UI and sync servlets | Consumed by engine `ConfigContext` at startup |

The sync bridge only moves **wizard XML** between DB and workspace. It never converts between formats.

## Decision

Implement four phased improvements, each independently deployable:

### Phase 1: Server-Sent Events (SSE) for Real-Time Push

**Choice: SSE over WebSocket**

Rationale:
- Unidirectional push is sufficient (server→client notifications only)
- HTTP-based — works through Vercel proxies and Cloudflare tunnels
- Browser `EventSource` has built-in auto-reconnection
- No additional JARs needed (Tomcat 9 `websocket-api.jar` present but not required)
- Java 8 compatible via `AsyncContext` (Servlet 3.0+)

Implementation:
- New `SyncEventServlet` at `/api/sync/events` using `AsyncContext`
- Static `Set<AsyncContext>` for connected listeners (thread-safe)
- 15-second keepalive heartbeat to prevent proxy timeouts
- Broadcast from `handlePush()`, `handlePull()`, and `WorkspaceProfileSyncServlet.importProfile()`
- New React hook `useSyncSSE()` wrapping `EventSource`, falling back to existing polling
- Vite proxy entry for `/iw-business-daemon/api/sync/events` with no response buffering

Impact: Latency drops from ~13s to <1s. Zero risk — existing polling remains as fallback.

### Phase 2: Optimistic Locking with Version Column

DB migration (additive, non-breaking):
```sql
ALTER TABLE company_configurations
  ADD COLUMN IF NOT EXISTS version INTEGER DEFAULT 1,
  ADD COLUMN IF NOT EXISTS last_modified_by VARCHAR(255) DEFAULT 'system',
  ADD COLUMN IF NOT EXISTS last_modified_source VARCHAR(20) DEFAULT 'portal'
    CHECK (last_modified_source IN ('portal', 'ide', 'bridge', 'system'));
```

Protocol:
- Portal wizard loads `version` with config data
- PUT sends `{version: N, ...fields}`
- Server: `UPDATE ... SET version = N+1 WHERE version = N`
- 0 rows updated → HTTP 409 with current data and diff
- IDE/bridge imports always increment version (no reject path)
- SSE broadcasts version with each sync event

React conflict dialog shows: "Modified by [source] since you loaded. Review changes: [diff]. Overwrite or reload?"

### Phase 3: XML-Aware Field Diff

New utility class `XmlConfigDiffer`:
- `parseConfigFields(xml)` → `Map<String,String>` (flat extraction from wizard XML)
- `diff(base, incoming)` → `DiffResult` with added/modified/removed/unchanged
- HTML-escaped nested XML (e.g. `CRM2MG2Configuration`) handled by unescaping and prefixing field names

Used in:
- Phase 2 conflict responses (show what changed)
- Sync status UI (show modified field count, not just "changed")
- Future: selective merge of non-conflicting fields

### Phase 4: Canonical JSON Normalization

`WizardConfigNormalizer` converts between solution-specific XML wrappers and a canonical JSON:
```
XML: <SF2QBConfiguration><SyncTypeAC>SF2QB</SyncTypeAC>...</SF2QBConfiguration>
JSON: {"solutionType":"CRM2QB3","fields":{"SyncTypeAC":"SF2QB",...},"nested":{...}}
```

Benefits:
- React UI can display/edit config as structured form (not XML textarea)
- Diff engine operates on JSON (simpler than XML comparison)
- Round-trip preserves original XML wrapper element name

Not in scope: Converting between wizard XML and IDE `BusinessDaemonConfiguration`. These remain separate documents consumed by different systems.

## Consequences

### Positive
- Sub-second sync notification (Phase 1)
- No more silent data loss on concurrent edits (Phase 2)
- Visible change tracking in sync UI (Phase 3)
- Foundation for richer config editing experience (Phase 4)

### Negative
- SSE connections consume one Tomcat thread per connected client (mitigated: typical deployment has <10 concurrent users)
- DB migration required (Phase 2) — but additive only, existing code unaffected
- `XmlConfigDiffer` adds parsing overhead — but wizard XML is tiny (<2KB)

### Risks
- Vite/Vercel proxy buffering may delay SSE events — mitigated by keepalive and polling fallback
- PowerShell bridge cannot be an SSE client — mitigated by keeping bridge's HTTP polling as-is (bridge→server is already fast at 1s)
- Optimistic locking may surprise users who expect "just save" — mitigated by clear conflict UI

## Alternatives Considered

1. **WebSocket instead of SSE** — Rejected: bidirectional not needed, more complex state management, requires Vite proxy changes, PowerShell has no built-in WebSocket support
2. **Unified XML schema** — Rejected: wizard and IDE XML serve fundamentally different purposes. Forcing a common schema would break the IDE's `ConfigContext` engine.
3. **File-system watchers (Java `WatchService`)** — Rejected: would require a daemon thread in Tomcat, unreliable on Windows network drives, and the PowerShell bridge already handles this adequately
4. **Three-way merge** — Deferred: requires a "base version" storage mechanism. Two-way diff with manual conflict resolution is sufficient for current user volume.

## Implementation Priority

| Phase | Effort | Dependencies | Value |
|-------|--------|-------------|-------|
| 1 (SSE) | Small | None | High — immediate latency improvement |
| 2 (Optimistic locking) | Medium | DB migration | High — prevents data loss |
| 3 (Field diff) | Medium | Phase 2 (version tracking enables meaningful diffs) | Medium — better UX |
| 4 (Normalization) | Medium | Phase 3 (diff engine consumes normalized format) | Medium — foundation for future |
