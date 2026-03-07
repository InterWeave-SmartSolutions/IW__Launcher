import { useState } from "react";
import { Link } from "react-router-dom";
import {
  Monitor,
  ExternalLink,
  Activity,
  CheckCircle,
  XCircle,
  Clock,
  Loader2,
  AlertTriangle,
  Play,
  Pause,
  RefreshCw,
  Workflow,
  ArrowRight,
  Key,
  Settings,
  Database,
  Square,
  Timer,
  Zap,
  Server,
  Save,
  Pencil,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { useDashboard, useTransactions } from "@/hooks/useMonitoring";
import { useCompanyProfile } from "@/hooks/useProfile";
import { useCredentials } from "@/hooks/useConfiguration";
import { useEngineFlows, useStartFlow, useStopFlow, useUpdateFlowSchedule, useSubmitFlows } from "@/hooks/useFlows";
import { useAuth } from "@/providers/AuthProvider";
import { useToast } from "@/providers/ToastProvider";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Tabs, TabsList, TabsTrigger, TabsContent } from "@/components/ui/tabs";
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter } from "@/components/ui/dialog";
import { Tooltip, TooltipContent, TooltipTrigger } from "@/components/ui/tooltip";
import type { Transaction } from "@/types/monitoring";
import type { EngineFlow } from "@/types/flows";

/** Derive unique flow names + stats from transaction history */
function deriveFlows(transactions: Transaction[]) {
  const map = new Map<
    string,
    { name: string; lastRun: string | null; lastStatus: string; totalRuns: number; successCount: number; failedCount: number }
  >();

  for (const tx of transactions) {
    const existing = map.get(tx.flow_name);
    if (!existing) {
      map.set(tx.flow_name, {
        name: tx.flow_name,
        lastRun: tx.started_at,
        lastStatus: tx.status,
        totalRuns: 1,
        successCount: tx.status === "success" ? 1 : 0,
        failedCount: tx.status === "failed" ? 1 : 0,
      });
    } else {
      existing.totalRuns++;
      if (tx.status === "success") existing.successCount++;
      if (tx.status === "failed") existing.failedCount++;
      if (tx.started_at > (existing.lastRun ?? "")) {
        existing.lastRun = tx.started_at;
        existing.lastStatus = tx.status;
      }
    }
  }
  return [...map.values()].sort((a, b) => (b.lastRun ?? "").localeCompare(a.lastRun ?? ""));
}

function statusIcon(status: string) {
  switch (status) {
    case "running":
      return <Play className="w-3.5 h-3.5 text-[hsl(var(--primary))]" />;
    case "success":
      return <CheckCircle className="w-3.5 h-3.5 text-[hsl(var(--success))]" />;
    case "failed":
      return <XCircle className="w-3.5 h-3.5 text-[hsl(var(--destructive))]" />;
    case "timeout":
      return <Clock className="w-3.5 h-3.5 text-[hsl(var(--warning))]" />;
    default:
      return <Pause className="w-3.5 h-3.5 text-muted-foreground" />;
  }
}

function statusColor(status: string) {
  switch (status) {
    case "running":
      return "border-[hsl(var(--primary)/0.3)] bg-[hsl(var(--primary)/0.08)]";
    case "success":
      return "border-[hsl(var(--success)/0.3)] bg-[hsl(var(--success)/0.08)]";
    case "failed":
      return "border-[hsl(var(--destructive)/0.3)] bg-[hsl(var(--destructive)/0.08)]";
    default:
      return "border-[hsl(var(--border))]";
  }
}

function fmtTime(iso: string | null): string {
  if (!iso) return "Never";
  try {
    const d = new Date(iso);
    const now = new Date();
    const diffMs = now.getTime() - d.getTime();
    if (diffMs < 60_000) return "Just now";
    if (diffMs < 3600_000) return `${Math.floor(diffMs / 60_000)}m ago`;
    if (diffMs < 86400_000) return `${Math.floor(diffMs / 3600_000)}h ago`;
    return d.toLocaleDateString();
  } catch {
    return iso;
  }
}

export function IntegrationOverviewPage() {
  useDocumentTitle("Integrations");
  const { data: dashRes, isLoading: dashLoading, refetch, isFetching } = useDashboard();
  const { data: txRes, isLoading: txLoading } = useTransactions(1, 100);
  const { data: companyData } = useCompanyProfile();
  const { data: credData, isLoading: credLoading } = useCredentials();

  const isLoading = dashLoading || txLoading;
  const summary = dashRes?.data?.summary;
  const running = dashRes?.data?.running_transactions ?? [];
  const transactions = txRes?.data?.transactions ?? [];
  const flows = deriveFlows(transactions);
  const solutionType = companyData?.company?.solutionType;
  const credentials = credData?.data?.credentials ?? [];
  const profileCreds = credData?.data?.profileCredentials;

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex items-start justify-between gap-4 flex-wrap">
        <div>
          <h1 className="text-2xl font-semibold">Integrations</h1>
          <p className="text-sm text-muted-foreground mt-1">
            Integration flows and business daemon status
            {solutionType ? ` — ${solutionType}` : ""}
          </p>
        </div>
        <div className="flex items-center gap-2">
          <Button
            variant="outline"
            size="sm"
            onClick={() => void refetch()}
            disabled={isFetching}
          >
            <RefreshCw className={cn("w-3 h-3", isFetching && "animate-spin")} />
            Refresh
          </Button>
          <Button variant="outline" size="sm" asChild>
            <a href="/iw-business-daemon/BDConfigurator.jsp">
              <Monitor className="w-3 h-3" />
              Classic IM
              <ExternalLink className="w-3 h-3" />
            </a>
          </Button>
        </div>
      </div>

      {/* KPI row */}
      <div className="grid grid-cols-4 gap-4 max-lg:grid-cols-2 max-sm:grid-cols-1">
        {[
          {
            label: "Active Flows",
            value: isLoading ? "—" : String(flows.length),
            icon: Workflow,
            color: "text-[hsl(var(--primary))]",
          },
          {
            label: "Running Now",
            value: isLoading ? "—" : String(summary?.running_count ?? 0),
            icon: Play,
            color: "text-[hsl(var(--success))]",
          },
          {
            label: "24h Success Rate",
            value: isLoading ? "—" : `${(summary?.success_rate_24h ?? 0).toFixed(1)}%`,
            icon: CheckCircle,
            color: "text-[hsl(var(--success))]",
          },
          {
            label: "24h Failures",
            value: isLoading ? "—" : String(summary?.failed_count_24h ?? 0),
            icon: AlertTriangle,
            color:
              (summary?.failed_count_24h ?? 0) > 0
                ? "text-[hsl(var(--destructive))]"
                : "text-muted-foreground",
          },
        ].map((kpi) => (
          <div key={kpi.label} className="glass-panel rounded-[var(--radius)] p-4 space-y-2">
            <div className="flex items-center justify-between">
              <p className="text-xs text-muted-foreground">{kpi.label}</p>
              <kpi.icon className={cn("w-4 h-4", kpi.color)} />
            </div>
            {isLoading ? (
              <Loader2 className="w-5 h-5 animate-spin text-muted-foreground" />
            ) : (
              <p className="text-2xl font-bold tracking-tight">{kpi.value}</p>
            )}
          </div>
        ))}
      </div>

      {/* Tabs — Radix provides ARIA + keyboard navigation */}
      <Tabs defaultValue="flows">
        <TabsList>
          <TabsTrigger value="flows">
            <Workflow className="w-3.5 h-3.5" />
            Flows
          </TabsTrigger>
          <TabsTrigger value="engine">
            <Server className="w-3.5 h-3.5" />
            Engine Controls
          </TabsTrigger>
          <TabsTrigger value="credentials">
            <Key className="w-3.5 h-3.5" />
            Credentials
            {credentials.length > 0 && (
              <Badge variant="default" className="ml-1 text-[10px] px-1.5 py-0">
                {credentials.length}
              </Badge>
            )}
          </TabsTrigger>
        </TabsList>

        <TabsContent value="flows">
          {/* Running flows — live status */}
          {running.length > 0 && (
            <div className="glass-panel rounded-[var(--radius)] p-4 border-l-4 border-l-[hsl(var(--primary))] mb-4">
              <h2 className="text-sm font-semibold mb-3 flex items-center gap-2">
                <Activity className="w-4 h-4 text-[hsl(var(--primary))] animate-pulse" />
                Running Now
              </h2>
              <div className="space-y-2">
                {running.map((r) => (
                  <div key={r.execution_id} className="flex items-center justify-between text-sm">
                    <div className="flex items-center gap-2">
                      <Play className="w-3.5 h-3.5 text-[hsl(var(--primary))]" />
                      <span className="font-medium">{r.flow_name}</span>
                    </div>
                    <div className="flex items-center gap-4 text-xs text-muted-foreground">
                      <span>{r.records_processed} records</span>
                      <span>{Math.floor(r.duration_ms / 1000)}s elapsed</span>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          )}

          {/* Flow list */}
          <div className="glass-panel rounded-[var(--radius)] p-4">
            <div className="flex items-center justify-between mb-3">
              <h2 className="text-sm font-semibold">Integration Flows</h2>
              <Button variant="link" size="sm" asChild>
                <Link to="/monitoring/transactions">
                  Transaction History <ArrowRight className="w-3 h-3" />
                </Link>
              </Button>
            </div>

            {isLoading ? (
              <div className="flex justify-center py-12">
                <Loader2 className="w-6 h-6 animate-spin text-muted-foreground" />
              </div>
            ) : flows.length === 0 ? (
              <div className="text-center py-12">
                <Workflow className="w-8 h-8 text-muted-foreground mx-auto mb-2" />
                <p className="text-sm text-muted-foreground">No integration flows found.</p>
                <p className="text-xs text-muted-foreground mt-1">
                  Flows will appear here once they are configured and executed via the IDE.
                </p>
              </div>
            ) : (
              <div className="space-y-2">
                {flows.map((flow) => {
                  const rate =
                    flow.totalRuns > 0
                      ? Math.round((flow.successCount / flow.totalRuns) * 100)
                      : 0;
                  return (
                    <div
                      key={flow.name}
                      className={cn(
                        "rounded-xl border p-4 flex items-center gap-4",
                        statusColor(flow.lastStatus)
                      )}
                    >
                      <div className="w-9 h-9 rounded-lg bg-[hsl(var(--muted)/0.3)] grid place-items-center shrink-0">
                        {statusIcon(flow.lastStatus)}
                      </div>
                      <div className="flex-1 min-w-0">
                        <p className="text-sm font-medium truncate">{flow.name}</p>
                        <p className="text-xs text-muted-foreground mt-0.5">
                          Last run: {fmtTime(flow.lastRun)} · {flow.totalRuns} executions
                        </p>
                      </div>
                      <div className="shrink-0 text-right">
                        <p className="text-xs font-medium">{rate}% success</p>
                        <div className="w-20 h-1.5 rounded-full bg-[hsl(var(--muted))] mt-1 overflow-hidden">
                          <div
                            className={cn(
                              "h-full rounded-full transition-all",
                              rate >= 90
                                ? "bg-[hsl(var(--success))]"
                                : rate >= 50
                                ? "bg-[hsl(var(--warning))]"
                                : "bg-[hsl(var(--destructive))]"
                            )}
                            style={{ width: `${rate}%` }}
                          />
                        </div>
                      </div>
                      <div className="hidden sm:flex items-center gap-4 text-xs text-muted-foreground shrink-0">
                        <span className="flex items-center gap-1">
                          <CheckCircle className="w-3 h-3 text-[hsl(var(--success))]" />
                          {flow.successCount}
                        </span>
                        <span className="flex items-center gap-1">
                          <XCircle className="w-3 h-3 text-[hsl(var(--destructive))]" />
                          {flow.failedCount}
                        </span>
                      </div>
                    </div>
                  );
                })}
              </div>
            )}
          </div>
        </TabsContent>

        <TabsContent value="engine">
          <EngineControlsTab />
        </TabsContent>

        <TabsContent value="credentials">
          <div className="glass-panel rounded-[var(--radius)] p-4">
            <div className="flex items-center justify-between mb-3">
              <h2 className="text-sm font-semibold">System Credentials</h2>
              <Button variant="link" size="sm" asChild>
                <Link to="/company/config/wizard">
                  <Settings className="w-3 h-3" />
                  Configure <ArrowRight className="w-3 h-3" />
                </Link>
              </Button>
            </div>

            {credLoading ? (
              <div className="flex justify-center py-12">
                <Loader2 className="w-6 h-6 animate-spin text-muted-foreground" />
              </div>
            ) : credentials.length === 0 && !profileCreds ? (
              <div className="text-center py-12">
                <Key className="w-8 h-8 text-muted-foreground mx-auto mb-2" />
                <p className="text-sm text-muted-foreground">No credentials configured yet.</p>
                <Button variant="link" size="sm" asChild className="mt-3">
                  <Link to="/company/config/wizard">
                    Open wizard to add credentials <ArrowRight className="w-3 h-3" />
                  </Link>
                </Button>
              </div>
            ) : (
              <div className="space-y-3">
                {credentials.map((cred) => (
                  <div
                    key={cred.id}
                    className={cn(
                      "rounded-xl border p-4 flex items-center gap-4",
                      cred.isActive
                        ? "border-[hsl(var(--success)/0.2)] bg-[hsl(var(--success)/0.03)]"
                        : "border-[hsl(var(--border))] opacity-60"
                    )}
                  >
                    <div className="w-9 h-9 rounded-lg bg-[hsl(var(--muted)/0.3)] grid place-items-center shrink-0">
                      <Database className="w-4 h-4 text-[hsl(var(--primary))]" />
                    </div>
                    <div className="flex-1 min-w-0">
                      <p className="text-sm font-medium">{cred.credentialName || cred.credentialType}</p>
                      <p className="text-xs text-muted-foreground mt-0.5">
                        {cred.username && `User: ${cred.username}`}
                        {cred.username && cred.endpointUrl && " · "}
                        {cred.endpointUrl && `URL: ${cred.endpointUrl}`}
                      </p>
                    </div>
                    <div className="flex items-center gap-2 shrink-0">
                      {cred.hasApiKey && (
                        <Badge variant="default" className="text-[10px]">API Key</Badge>
                      )}
                      <Badge variant={cred.isActive ? "success" : "secondary"} className="text-[10px]">
                        {cred.isActive ? "Active" : "Inactive"}
                      </Badge>
                    </div>
                  </div>
                ))}

                {profileCreds && (profileCreds.sfUsername || profileCreds.qbUsername || profileCreds.crmUsername) && (
                  <div className="rounded-xl border border-[hsl(var(--border))] p-4">
                    <p className="text-xs text-muted-foreground font-medium mb-2">Profile Credentials</p>
                    <div className="grid grid-cols-3 gap-3 max-sm:grid-cols-1 text-xs">
                      {profileCreds.sfUsername && (
                        <div>
                          <span className="text-muted-foreground">SF:</span>{" "}
                          <span className="font-medium">{profileCreds.sfUsername}</span>
                        </div>
                      )}
                      {profileCreds.qbUsername && (
                        <div>
                          <span className="text-muted-foreground">QB:</span>{" "}
                          <span className="font-medium">{profileCreds.qbUsername}</span>
                        </div>
                      )}
                      {profileCreds.crmUsername && (
                        <div>
                          <span className="text-muted-foreground">CRM:</span>{" "}
                          <span className="font-medium">{profileCreds.crmUsername}</span>
                        </div>
                      )}
                    </div>
                  </div>
                )}
              </div>
            )}
          </div>
        </TabsContent>
      </Tabs>

      {/* BD Configurator CTA */}
      <div className="glass-panel rounded-2xl border border-[hsl(var(--primary)/0.2)] bg-gradient-to-r from-[hsl(var(--primary)/0.06)] to-transparent p-6">
        <div className="flex items-start gap-4 flex-wrap">
          <div className="flex-1 min-w-[200px]">
            <h3 className="font-semibold">Business Daemon Configurator</h3>
            <p className="text-sm text-muted-foreground mt-1">
              Start/stop integration flows, manage schedules, and configure the
              transformation engine. Available through the classic portal.
            </p>
          </div>
          <Button asChild>
            <a href="/iw-business-daemon/BDConfigurator.jsp">
              <ExternalLink className="w-4 h-4" />
              Open Classic IM
            </a>
          </Button>
        </div>
      </div>
    </div>
  );
}

// ─── Engine Controls Tab ──────────────────────────────────────────────

function EngineControlsTab() {
  const { user } = useAuth();
  const { showToast } = useToast();
  const { data: flowsRes, isLoading, error, refetch, isFetching } = useEngineFlows();
  const startFlow = useStartFlow();
  const stopFlow = useStopFlow();
  const submitFlows = useSubmitFlows();
  const [editFlow, setEditFlow] = useState<EngineFlow | null>(null);

  const isAdmin = user?.isAdmin === true;
  const data = flowsRes?.data;

  const handleStartStop = async (flow: EngineFlow) => {
    try {
      if (flow.running || flow.executing) {
        await stopFlow.mutateAsync(flow.index);
        showToast(`Stopped: ${flow.flowId}`, "success");
      } else {
        await startFlow.mutateAsync(flow.index);
        showToast(`Started: ${flow.flowId}`, "success");
      }
    } catch (e) {
      showToast(e instanceof Error ? e.message : "Action failed", "error");
    }
  };

  const handleSaveAll = async () => {
    try {
      await submitFlows.mutateAsync();
      showToast("Configuration saved", "success");
    } catch (e) {
      showToast(e instanceof Error ? e.message : "Save failed", "error");
    }
  };

  if (isLoading) {
    return (
      <div className="flex justify-center py-12">
        <Loader2 className="w-6 h-6 animate-spin text-muted-foreground" />
      </div>
    );
  }

  if (error || !data) {
    return (
      <div className="glass-panel rounded-[var(--radius)] p-6">
        <div className="flex items-start gap-3">
          <AlertTriangle className="w-5 h-5 text-[hsl(var(--warning))] shrink-0 mt-0.5" />
          <div>
            <p className="text-sm font-medium">Engine not available</p>
            <p className="text-xs text-muted-foreground mt-1">
              {flowsRes?.error || (error instanceof Error ? error.message : "Could not connect to the integration engine.")}
            </p>
            <p className="text-xs text-muted-foreground mt-2">
              The engine controls require an active ConfigContext session. Login through the
              classic portal first, or ensure the business daemon has been initialized with
              a workspace profile.
            </p>
            <a
              href="/iw-business-daemon/BDConfigurator.jsp"
              className="inline-flex items-center gap-1.5 mt-3 text-xs text-[hsl(var(--primary))] hover:underline"
            >
              Open classic IM to initialize <ExternalLink className="w-3 h-3" />
            </a>
          </div>
        </div>
      </div>
    );
  }

  const allFlows = [...data.scheduledFlows, ...data.utilityFlows];
  const runningCount = allFlows.filter((f) => f.running || f.executing).length;

  return (
    <div className="space-y-4">
      {/* Server info bar */}
      <div className="glass-panel rounded-[var(--radius)] p-4 flex items-center justify-between flex-wrap gap-3">
        <div className="flex items-center gap-3">
          <div className="w-9 h-9 rounded-xl bg-[hsl(var(--success)/0.15)] grid place-items-center">
            <Server className="w-4.5 h-4.5 text-[hsl(var(--success))]" />
          </div>
          <div>
            <p className="text-sm font-medium">{data.serverName || "Integration Engine"}</p>
            <p className="text-xs text-muted-foreground">
              Profile: {data.profileName} · Heartbeat: {data.heartbeatInterval}s
            </p>
          </div>
        </div>
        <div className="flex items-center gap-2">
          <Button variant="outline" size="sm" onClick={() => void refetch()} disabled={isFetching}>
            <RefreshCw className={cn("w-3 h-3", isFetching && "animate-spin")} />
            Refresh
          </Button>
          {isAdmin && (
            <Button size="sm" onClick={() => void handleSaveAll()} disabled={submitFlows.isPending}>
              {submitFlows.isPending ? (
                <RefreshCw className="w-3 h-3 animate-spin" />
              ) : (
                <Save className="w-3 h-3" />
              )}
              Save All
            </Button>
          )}
        </div>
      </div>

      {/* Status summary */}
      <div className="flex items-center gap-4 text-xs text-muted-foreground">
        <span>{data.scheduledFlows.length} scheduled</span>
        <span>{data.utilityFlows.length} utility</span>
        <span>{data.queryFlows.length} queries</span>
        {runningCount > 0 && (
          <span className="text-[hsl(var(--primary))] font-medium">
            {runningCount} running
          </span>
        )}
      </div>

      {/* Scheduled Transaction Flows */}
      {data.scheduledFlows.length > 0 && (
        <FlowTable
          title="Scheduled Transaction Flows"
          flows={data.scheduledFlows}
          isAdmin={isAdmin}
          onStartStop={handleStartStop}
          onEditSchedule={isAdmin ? setEditFlow : undefined}
          isPending={startFlow.isPending || stopFlow.isPending}
        />
      )}

      {/* Utility Flows */}
      {data.utilityFlows.length > 0 && (
        <FlowTable
          title="Utility Transaction Flows"
          flows={data.utilityFlows}
          isAdmin={isAdmin}
          onStartStop={handleStartStop}
          onEditSchedule={isAdmin ? setEditFlow : undefined}
          isPending={startFlow.isPending || stopFlow.isPending}
        />
      )}

      {/* Query Flows */}
      {data.queryFlows.length > 0 && (
        <div className="glass-panel rounded-[var(--radius)] p-4">
          <h3 className="text-sm font-semibold mb-3 flex items-center gap-2">
            <Zap className="w-4 h-4 text-[hsl(var(--primary))]" />
            Query Flows
          </h3>
          <div className="space-y-2">
            {data.queryFlows.map((q) => (
              <div
                key={q.flowId}
                className="rounded-xl border border-[hsl(var(--border))] p-3 flex items-center gap-4"
              >
                <div className="w-8 h-8 rounded-lg bg-[hsl(var(--muted)/0.3)] grid place-items-center shrink-0">
                  <Zap className="w-3.5 h-3.5 text-[hsl(var(--primary))]" />
                </div>
                <div className="flex-1 min-w-0">
                  <p className="text-sm font-medium truncate">{q.flowId}</p>
                  <p className="text-xs text-muted-foreground">
                    Counter: {q.counter} · Interval: {q.interval}s
                  </p>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {allFlows.length === 0 && data.queryFlows.length === 0 && (
        <div className="text-center py-12">
          <Server className="w-8 h-8 text-muted-foreground mx-auto mb-2" />
          <p className="text-sm text-muted-foreground">No flows loaded in engine.</p>
          <p className="text-xs text-muted-foreground mt-1">
            Flows are loaded when a workspace profile is compiled and the engine is initialized.
          </p>
        </div>
      )}

      {!isAdmin && (
        <Badge variant="warning" className="rounded-lg px-3 py-2 text-xs">
          <AlertTriangle className="w-3.5 h-3.5 shrink-0" />
          Admin access required to start/stop flows or modify schedules.
        </Badge>
      )}

      {/* Schedule editor dialog */}
      <EditScheduleDialog
        flow={editFlow}
        open={!!editFlow}
        onOpenChange={(open) => { if (!open) setEditFlow(null); }}
      />
    </div>
  );
}

// ─── Edit Schedule Dialog ─────────────────────────────────────────────

function EditScheduleDialog({
  flow,
  open,
  onOpenChange,
}: {
  flow: EngineFlow | null;
  open: boolean;
  onOpenChange: (open: boolean) => void;
}) {
  const { showToast } = useToast();
  const updateSchedule = useUpdateFlowSchedule();
  const [interval, setInterval] = useState("");
  const [shift, setShift] = useState("");
  const [counter, setCounter] = useState("");

  // Sync state when flow changes
  const prevFlowRef = useState<string | null>(null);
  if (flow && prevFlowRef[0] !== flow.flowId) {
    prevFlowRef[0] = flow.flowId;
    setInterval(String(flow.intervalDisplay));
    setShift(String(flow.shiftDisplay));
    setCounter(String(flow.counter));
  }

  const handleSave = async () => {
    if (!flow) return;
    try {
      await updateSchedule.mutateAsync({
        flowIndex: flow.index,
        interval: Number(interval) || undefined,
        shift: Number(shift) || undefined,
        counter: Number(counter) || undefined,
      });
      showToast(`Schedule updated: ${flow.flowId}`, "success");
      onOpenChange(false);
    } catch (e) {
      showToast(e instanceof Error ? e.message : "Failed to update schedule", "error");
    }
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Edit Flow Schedule</DialogTitle>
        </DialogHeader>
        {flow && (
          <div className="space-y-4 py-2">
            <div className="flex items-center gap-2 text-sm">
              <Badge variant="default">{flow.flowId}</Badge>
              <Badge variant={flow.running ? "default" : "secondary"}>
                {flow.state}
              </Badge>
            </div>
            <div className="grid grid-cols-3 gap-3">
              <div className="space-y-1.5">
                <Label htmlFor="edit-interval">Interval (s)</Label>
                <Input
                  id="edit-interval"
                  type="number"
                  min={0}
                  value={interval}
                  onChange={(e) => setInterval(e.target.value)}
                />
              </div>
              <div className="space-y-1.5">
                <Label htmlFor="edit-shift">Shift (s)</Label>
                <Input
                  id="edit-shift"
                  type="number"
                  min={0}
                  value={shift}
                  onChange={(e) => setShift(e.target.value)}
                />
              </div>
              <div className="space-y-1.5">
                <Label htmlFor="edit-counter">Counter</Label>
                <Input
                  id="edit-counter"
                  type="number"
                  min={0}
                  value={counter}
                  onChange={(e) => setCounter(e.target.value)}
                />
              </div>
            </div>
            {(flow.running || flow.executing) && (
              <p className="text-xs text-[hsl(var(--warning))]">
                Schedule changes are blocked while the flow is running. Stop it first.
              </p>
            )}
          </div>
        )}
        <DialogFooter>
          <Button variant="outline" onClick={() => onOpenChange(false)}>
            Cancel
          </Button>
          <Button
            onClick={() => void handleSave()}
            disabled={updateSchedule.isPending || !!(flow && (flow.running || flow.executing))}
          >
            {updateSchedule.isPending ? <Loader2 className="w-4 h-4 animate-spin" /> : <Save className="w-4 h-4" />}
            Save Schedule
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}

// ─── Flow Table Component ─────────────────────────────────────────────

function FlowTable({
  title,
  flows,
  isAdmin,
  onStartStop,
  onEditSchedule,
  isPending,
}: {
  title: string;
  flows: EngineFlow[];
  isAdmin: boolean;
  onStartStop: (flow: EngineFlow) => void;
  onEditSchedule?: (flow: EngineFlow) => void;
  isPending: boolean;
}) {
  return (
    <div className="glass-panel rounded-[var(--radius)] overflow-hidden">
      <div className="px-4 py-3 border-b border-[hsl(var(--border))]">
        <h3 className="text-sm font-semibold flex items-center gap-2">
          <Timer className="w-4 h-4 text-[hsl(var(--primary))]" />
          {title}
          <span className="text-xs font-normal text-muted-foreground ml-auto">
            {flows.length} flow{flows.length !== 1 ? "s" : ""}
          </span>
        </h3>
      </div>

      {/* Table header */}
      <div className="hidden md:grid grid-cols-[1fr_90px_80px_80px_90px_80px_70px_90px] gap-2 px-4 py-2 text-[10px] font-medium text-muted-foreground uppercase tracking-wider bg-[hsl(var(--muted)/0.2)]">
        <span>Flow ID</span>
        <span>Start/Stop</span>
        <span>State</span>
        <span>Mode</span>
        <span>Interval</span>
        <span>Shift</span>
        <span>Counter</span>
        <span>Success/Fail</span>
      </div>

      {/* Flow rows */}
      <div className="divide-y divide-[hsl(var(--border))]">
        {flows.map((flow) => {
          const isRunning = flow.running || flow.executing;
          const bgColor = flow.running && flow.executing
            ? "bg-[hsl(var(--destructive)/0.06)]"
            : flow.running
            ? "bg-[hsl(var(--primary)/0.06)]"
            : flow.executing
            ? "bg-[hsl(var(--warning)/0.06)]"
            : "";

          return (
            <div
              key={`${flow.index}-${flow.flowId}`}
              className={cn(
                "grid md:grid-cols-[1fr_90px_80px_80px_90px_80px_70px_90px] gap-2 px-4 py-3 items-center",
                bgColor
              )}
            >
              {/* Flow ID */}
              <div className="min-w-0">
                <p className="text-sm font-medium truncate">{flow.flowId}</p>
                <p className="text-[10px] text-muted-foreground md:hidden">
                  {flow.state} · {flow.intervalDisplay}s
                </p>
              </div>

              {/* Start/Stop */}
              <div>
                {isAdmin ? (
                  <Button
                    variant={isRunning ? "destructive" : "success"}
                    size="sm"
                    onClick={() => onStartStop(flow)}
                    disabled={isPending}
                    className="text-[11px] h-7"
                  >
                    {isRunning ? (
                      <><Square className="w-3 h-3" /> Stop</>
                    ) : (
                      <><Play className="w-3 h-3" /> Start</>
                    )}
                  </Button>
                ) : (
                  <span className="text-xs text-muted-foreground">{flow.command}</span>
                )}
              </div>

              {/* State */}
              <div className="hidden md:block">
                <Tooltip>
                  <TooltipTrigger asChild>
                    <span>
                      <Badge variant={isRunning ? "default" : "secondary"} className="text-[11px]">
                        {flow.state}
                      </Badge>
                    </span>
                  </TooltipTrigger>
                  <TooltipContent>
                    {flow.running && flow.executing
                      ? "Flow is actively running and executing a sync cycle"
                      : flow.running
                      ? "Flow is running (scheduled, waiting for next cycle)"
                      : flow.executing
                      ? "Flow is executing a one-time sync"
                      : "Flow is stopped"}
                  </TooltipContent>
                </Tooltip>
              </div>

              {/* Mode */}
              <div className="hidden md:block">
                <span className="text-xs text-muted-foreground">
                  {flow.isScheduled ? "Scheduled" : "Single Run"}
                </span>
              </div>

              {/* Interval */}
              <div className="hidden md:flex items-center gap-1">
                <span className="text-xs font-mono">{flow.intervalDisplay}</span>
                {isAdmin && onEditSchedule && (
                  <button
                    onClick={() => onEditSchedule(flow)}
                    className="text-muted-foreground hover:text-foreground cursor-pointer p-0.5"
                  >
                    <Pencil className="w-3 h-3" />
                  </button>
                )}
              </div>

              {/* Shift */}
              <div className="hidden md:block">
                <span className="text-xs font-mono">{flow.shiftDisplay}</span>
              </div>

              {/* Counter */}
              <div className="hidden md:block">
                <span className="text-xs font-mono">{flow.counter}</span>
              </div>

              {/* Success/Fail */}
              <div className="hidden md:flex items-center gap-1.5">
                <span className="text-xs text-[hsl(var(--success))]">{flow.successes}</span>
                <span className="text-xs text-muted-foreground">/</span>
                <span className="text-xs text-[hsl(var(--destructive))]">{flow.failures}</span>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}
