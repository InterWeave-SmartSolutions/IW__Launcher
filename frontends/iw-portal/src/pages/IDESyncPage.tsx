import { useState } from "react";
import { Link } from "react-router-dom";
import {
  RefreshCw,
  Upload,
  Download,
  CheckCircle2,
  AlertCircle,
  Clock,
  WifiOff,
  Activity,
  FileCode2,
  ChevronDown,
  ChevronUp,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { useSyncStatus, useSyncLog, usePushToIDE, usePullFromIDE } from "@/hooks/useSync";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { useToast } from "@/providers/ToastProvider";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import type { SyncStatus, ProfileSyncState } from "@/types/sync";

// ── Status helpers ───────────────────────────────────────────────────────────

function statusMeta(status: SyncStatus) {
  switch (status) {
    case "in_sync":
      return { label: "In Sync", color: "text-[hsl(var(--success))]", bg: "bg-[hsl(var(--success)/0.12)]", icon: CheckCircle2 };
    case "db_ahead":
      return { label: "Portal Ahead", color: "text-[hsl(var(--warning))]", bg: "bg-[hsl(var(--warning)/0.1)]", icon: AlertCircle };
    case "workspace_ahead":
      return { label: "IDE Ahead", color: "text-[hsl(var(--primary))]", bg: "bg-[hsl(var(--primary)/0.12)]", icon: Activity };
    case "not_synced":
      return { label: "Not Synced", color: "text-muted-foreground", bg: "bg-muted/30", icon: WifiOff };
  }
}

function fmtDate(iso: string | null) {
  if (!iso) return "—";
  const d = new Date(iso);
  return d.toLocaleString(undefined, { month: "short", day: "numeric", hour: "2-digit", minute: "2-digit" });
}

// ── Profile card ─────────────────────────────────────────────────────────────

interface ProfileCardProps {
  profile: ProfileSyncState;
  onPush: (profileName: string) => void;
  onPull: (profileName: string) => void;
  pushing: boolean;
  pulling: boolean;
}

function ProfileCard({ profile, onPush, onPull, pushing, pulling }: ProfileCardProps) {
  const meta = statusMeta(profile.syncStatus);
  const StatusIcon = meta.icon;

  const shortName = profile.profileName.includes(":")
    ? profile.profileName.split(":")[0]
    : profile.profileName;
  const emailPart = profile.profileName.includes(":")
    ? profile.profileName.split(":")[1]
    : null;

  return (
    <div className="rounded-xl border border-[hsl(var(--border))] bg-[hsl(var(--card))] shadow-sm p-4 flex flex-col gap-3">
      {/* Header row */}
      <div className="flex items-start justify-between gap-2">
        <div className="min-w-0">
          <div className="font-medium text-sm text-foreground truncate">{shortName}</div>
          {emailPart && (
            <div className="text-xs text-muted-foreground truncate">{emailPart}</div>
          )}
          <div className="text-xs text-muted-foreground mt-0.5">
            {profile.solutionType || "—"}
          </div>
        </div>
        <div className={cn("flex items-center gap-1.5 text-xs font-medium px-2 py-1 rounded-full", meta.bg, meta.color)}>
          <StatusIcon className="w-3 h-3" />
          {meta.label}
        </div>
      </div>

      {/* Timestamps grid */}
      <div className="grid grid-cols-2 gap-2 text-xs">
        <div className="space-y-0.5">
          <div className="text-muted-foreground">Portal saved</div>
          <div className="text-foreground font-mono">{fmtDate(profile.dbUpdatedAt)}</div>
        </div>
        <div className="space-y-0.5">
          <div className="text-muted-foreground">Last pushed to IDE</div>
          <div className="text-foreground font-mono">{fmtDate(profile.workspaceXmlModified) || "Never"}</div>
        </div>
      </div>

      {/* Workspace artifacts */}
      <div className="flex items-center gap-3 text-xs text-muted-foreground">
        <span className={cn("flex items-center gap-1", profile.workspaceXmlExists && "text-[hsl(var(--success))]")}>
          <FileCode2 className="w-3 h-3" />
          IW_Runtime_Sync {profile.workspaceXmlExists ? "✓" : "✗"}
        </span>
        <span className={cn("flex items-center gap-1", profile.generatedProfileExists && "text-[hsl(var(--success))]")}>
          <FileCode2 className="w-3 h-3" />
          GeneratedProfiles {profile.generatedProfileExists ? "✓" : "✗"}
        </span>
      </div>

      {/* Actions */}
      <div className="flex gap-2 pt-1 border-t border-[hsl(var(--border))]">
        <Button
          size="sm"
          variant="outline"
          className="flex-1 text-xs h-7"
          disabled={pushing || pulling}
          onClick={() => onPush(profile.profileName)}
          title="Export portal config → IDE workspace"
        >
          {pushing ? <RefreshCw className="w-3 h-3 mr-1 animate-spin" /> : <Upload className="w-3 h-3 mr-1" />}
          Push to IDE
        </Button>
        <Button
          size="sm"
          variant="outline"
          className="flex-1 text-xs h-7"
          disabled={pushing || pulling || !profile.workspaceXmlExists}
          onClick={() => onPull(profile.profileName)}
          title="Import IDE workspace changes → portal DB"
        >
          {pulling ? <RefreshCw className="w-3 h-3 mr-1 animate-spin" /> : <Download className="w-3 h-3 mr-1" />}
          Pull from IDE
        </Button>
      </div>
    </div>
  );
}

// ── Log panel ────────────────────────────────────────────────────────────────

function LogPanel() {
  const [expanded, setExpanded] = useState(false);
  const { data } = useSyncLog(expanded ? 120 : 30);
  const lines = data?.data?.lines ?? [];

  return (
    <div className="rounded-xl border border-[hsl(var(--border))] bg-[hsl(var(--card))] shadow-sm overflow-hidden">
      <button
        className="w-full flex items-center justify-between px-4 py-3 text-sm font-medium text-foreground hover:bg-muted/30 transition-colors"
        onClick={() => setExpanded((v) => !v)}
      >
        <span className="flex items-center gap-2">
          <Activity className="w-4 h-4 text-muted-foreground" />
          Sync Bridge Log
          {data?.data?.totalLines != null && (
            <span className="text-xs text-muted-foreground font-normal">
              ({data.data.totalLines} entries)
            </span>
          )}
        </span>
        {expanded ? <ChevronUp className="w-4 h-4 text-muted-foreground" /> : <ChevronDown className="w-4 h-4 text-muted-foreground" />}
      </button>

      {expanded && (
        <div className="border-t border-[hsl(var(--border))] bg-muted/40 p-3 max-h-72 overflow-y-auto font-mono text-xs text-muted-foreground space-y-0.5">
          {lines.length === 0 ? (
            <div className="text-center py-4">No log entries</div>
          ) : (
            [...lines].reverse().map((line, i) => {
              const isSync  = line.includes("[SYNC]");
              const isError = line.includes("[ERROR]") || line.includes("[WARN]");
              return (
                <div
                  key={i}
                  className={cn(
                    isSync  && "text-[hsl(var(--primary))]",
                    isError && "text-[hsl(var(--destructive))]"
                  )}
                >
                  {line}
                </div>
              );
            })
          )}
        </div>
      )}
    </div>
  );
}

// ── Embeddable panel (used as tab in IntegrationOverviewPage) ─────────────────

export function WorkspaceSyncPanel() {
  const { showToast } = useToast();

  const { data, isLoading, refetch, isFetching } = useSyncStatus();
  const push = usePushToIDE();
  const pull = usePullFromIDE();

  const [activePush, setActivePush] = useState<string | null>(null);
  const [activePull, setActivePull] = useState<string | null>(null);

  const summary = data?.data;
  const profiles = summary?.profiles ?? [];

  const syncCounts = profiles.reduce(
    (acc, p) => { acc[p.syncStatus] = (acc[p.syncStatus] ?? 0) + 1; return acc; },
    {} as Record<string, number>
  );

  async function handlePush(profileName?: string) {
    const key = profileName ?? "__all__";
    setActivePush(key);
    try {
      const res = await push.mutateAsync(profileName);
      const pushed = res.data?.pushed ?? 0;
      showToast(
        pushed > 0
          ? `Pushed ${pushed} profile${pushed !== 1 ? "s" : ""} to IDE workspace`
          : "Push completed",
        "success"
      );
    } catch (e: unknown) {
      showToast((e as Error).message || "Push failed", "error");
    } finally {
      setActivePush(null);
    }
  }

  async function handlePull(profileName: string) {
    setActivePull(profileName);
    try {
      await pull.mutateAsync({ profileName });
      showToast(`Pulled "${profileName}" from IDE workspace`, "success");
    } catch (e: unknown) {
      showToast((e as Error).message || "Pull failed", "error");
    } finally {
      setActivePull(null);
    }
  }

  return (
    <div className="space-y-4">
      {/* Status bar */}
      <div className="rounded-xl border border-[hsl(var(--border))] bg-[hsl(var(--card))] shadow-sm p-4">
        <div className="flex flex-wrap items-center gap-4">
          {/* Bridge status */}
          <div className="flex items-center gap-2">
            <span
              className={cn(
                "inline-block w-2 h-2 rounded-full",
                isLoading ? "bg-muted" :
                summary?.bridgeRunning ? "bg-[hsl(var(--success))] animate-pulse" : "bg-muted-foreground"
              )}
            />
            <span className="text-sm">
              Sync bridge:{" "}
              <span className={cn("font-medium", summary?.bridgeRunning ? "text-[hsl(var(--success))]" : "text-muted-foreground")}>
                {isLoading ? "…" : summary?.bridgeRunning ? "Running" : "Stopped"}
              </span>
            </span>
          </div>

          <div className="h-4 w-px bg-[hsl(var(--border))]" />

          {/* Summary badges */}
          {!isLoading && profiles.length > 0 && (
            <div className="flex flex-wrap gap-2 text-xs">
              {syncCounts["in_sync"] != null && (
                <Badge variant="outline" className="text-[hsl(var(--success))] border-[hsl(var(--success)/0.4)]">
                  {syncCounts["in_sync"]} in sync
                </Badge>
              )}
              {syncCounts["db_ahead"] != null && (
                <Badge variant="outline" className="text-[hsl(var(--warning))] border-[hsl(var(--warning)/0.4)]">
                  {syncCounts["db_ahead"]} portal ahead
                </Badge>
              )}
              {syncCounts["workspace_ahead"] != null && (
                <Badge variant="outline" className="text-[hsl(var(--primary))] border-[hsl(var(--primary)/0.4)]">
                  {syncCounts["workspace_ahead"]} IDE ahead
                </Badge>
              )}
              {syncCounts["not_synced"] != null && (
                <Badge variant="outline" className="text-muted-foreground">
                  {syncCounts["not_synced"]} not synced
                </Badge>
              )}
            </div>
          )}

          <div className="ml-auto flex items-center gap-2">
            <Button
              size="sm"
              variant="ghost"
              onClick={() => void refetch()}
              disabled={isFetching}
              className="gap-1.5 text-xs"
            >
              <RefreshCw className={cn("w-3.5 h-3.5", isFetching && "animate-spin")} />
            </Button>
            <Button
              size="sm"
              onClick={() => handlePush()}
              disabled={activePush !== null || isLoading || profiles.length === 0}
              className="gap-1.5 text-xs"
            >
              {activePush === "__all__" ? (
                <RefreshCw className="w-3.5 h-3.5 animate-spin" />
              ) : (
                <Upload className="w-3.5 h-3.5" />
              )}
              Push All to IDE
            </Button>
          </div>
        </div>

        <div className="mt-3 pt-3 border-t border-[hsl(var(--border))] text-xs text-muted-foreground space-y-1">
          <p>
            <span className="font-medium text-foreground">Push to IDE</span> — exports portal wizard config to{" "}
            <code className="font-mono">workspace/IW_Runtime_Sync/</code> and compiles a{" "}
            <code className="font-mono">GeneratedProfiles/</code> overlay the IDE can open.
          </p>
          <p>
            <span className="font-medium text-foreground">Pull from IDE</span> — imports workspace edits back into the
            portal DB so the wizard reflects what the IDE engineer configured.
          </p>
          <p>
            <span className="font-medium text-foreground">Sync bridge</span> — a background PowerShell process that
            auto-pulls when it detects workspace file changes. Start/stop via{" "}
            <code className="font-mono">scripts/start_sync_bridge.bat</code>.
          </p>
        </div>
      </div>

      {/* Profile cards */}
      {isLoading ? (
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
          {[1, 2, 3].map((n) => (
            <div key={n} className="rounded-xl border border-[hsl(var(--border))] bg-[hsl(var(--card))] shadow-sm h-44 animate-pulse" />
          ))}
        </div>
      ) : profiles.length === 0 ? (
        <div className="rounded-xl border border-[hsl(var(--border))] bg-[hsl(var(--card))] shadow-sm p-8 text-center text-muted-foreground text-sm">
          No saved configurations found.{" "}
          <Link to="/company/config/wizard" className="text-[hsl(var(--primary))] underline">
            Run the setup wizard
          </Link>{" "}
          to create a profile.
        </div>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
          {profiles.map((p) => (
            <ProfileCard
              key={p.profileName}
              profile={p}
              onPush={(name) => handlePush(name)}
              onPull={handlePull}
              pushing={activePush === p.profileName}
              pulling={activePull === p.profileName}
            />
          ))}
        </div>
      )}

      {/* How sync works */}
      <div className="rounded-xl border border-[hsl(var(--border))] bg-[hsl(var(--card))] shadow-sm p-4 space-y-2">
        <h3 className="text-sm font-medium flex items-center gap-2">
          <Clock className="w-4 h-4 text-muted-foreground" />
          How Sync Works
        </h3>
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 text-xs text-muted-foreground">
          <div className="space-y-1">
            <div className="font-medium text-foreground">Portal → IDE (Push)</div>
            <ol className="list-decimal list-inside space-y-0.5 pl-1">
              <li>Read configuration XML from Supabase DB</li>
              <li>Write to <code className="font-mono">workspace/IW_Runtime_Sync/profiles/</code></li>
              <li>Compile engine overlay into <code className="font-mono">GeneratedProfiles/</code></li>
              <li>IDE can open the generated project to inspect settings</li>
            </ol>
          </div>
          <div className="space-y-1">
            <div className="font-medium text-foreground">IDE → Portal (Pull)</div>
            <ol className="list-decimal list-inside space-y-0.5 pl-1">
              <li>Sync bridge polls workspace files every 1 s</li>
              <li>Detects changes to runtime profile XML/properties</li>
              <li>Calls import to write changes back to DB</li>
              <li>Wizard reflects updated configuration on next load</li>
            </ol>
          </div>
        </div>
      </div>

      {/* Log panel */}
      <LogPanel />
    </div>
  );
}

// ── Standalone page (kept for backward compat, redirects handle it now) ──────

export function IDESyncPage() {
  useDocumentTitle("IDE Sync");
  return (
    <div className="max-w-4xl mx-auto px-4 py-6">
      <WorkspaceSyncPanel />
    </div>
  );
}
