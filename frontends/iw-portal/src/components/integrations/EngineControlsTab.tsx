import { useState } from "react";
import { Link } from "react-router-dom";
import {
  Server,
  RefreshCw,
  Save,
  Loader2,
  AlertTriangle,
  ExternalLink,
  Zap,
  Play,
  FileText,
  History,
  MoreHorizontal,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { useEngineFlows, useStartFlow, useStopFlow, useSubmitFlows } from "@/hooks/useFlows";
import { useAuth } from "@/providers/AuthProvider";
import { useToast } from "@/providers/ToastProvider";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Switch } from "@/components/ui/switch";
import { Label } from "@/components/ui/label";
import {
  DropdownMenu,
  DropdownMenuTrigger,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuLabel,
} from "@/components/ui/dropdown-menu";
import { FlowTable } from "./FlowTable";
import { EditScheduleDialog } from "./EditScheduleDialog";
import { FlowPropertiesDialog } from "./FlowPropertiesDialog";
import type { EngineFlow } from "@/types/flows";

export function EngineControlsTab() {
  const { user } = useAuth();
  const { showToast } = useToast();
  const [autoRefresh, setAutoRefresh] = useState(true);
  const { data: flowsRes, isLoading, error, refetch, isFetching } = useEngineFlows(autoRefresh);
  const startFlow = useStartFlow();
  const stopFlow = useStopFlow();
  const submitFlows = useSubmitFlows();
  const [editFlow, setEditFlow] = useState<EngineFlow | null>(null);
  const [propsTarget, setPropsTarget] = useState<{ flowId: string; isFlow: boolean } | null>(null);

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

  const handleBulkStart = async (indices: number[]) => {
    for (const idx of indices) {
      try {
        await startFlow.mutateAsync(idx);
      } catch {
        // continue with remaining
      }
    }
    showToast(`Started ${indices.length} flow(s)`, "success");
  };

  const handleBulkStop = async (indices: number[]) => {
    for (const idx of indices) {
      try {
        await stopFlow.mutateAsync(idx);
      } catch {
        // continue with remaining
      }
    }
    showToast(`Stopped ${indices.length} flow(s)`, "success");
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
          <div className="space-y-2">
            <p className="text-sm font-medium">Engine not available</p>
            <p className="text-xs text-muted-foreground">
              {flowsRes?.error || (error instanceof Error ? error.message : "Could not connect to the integration engine.")}
            </p>
            <div className="glass-panel rounded-[var(--radius)] p-3 text-xs text-muted-foreground space-y-1">
              <p className="font-medium text-foreground">To initialize the engine:</p>
              <ol className="list-decimal list-inside space-y-0.5">
                <li>Log into the classic portal via BDConfigurator.jsp</li>
                <li>Select your workspace profile from the dropdown</li>
                <li>Click &quot;Initialize&quot; to load the profile into the engine</li>
                <li>Return to this page — flows will appear automatically</li>
              </ol>
            </div>
            <a
              href="/iw-business-daemon/BDConfigurator.jsp"
              className="inline-flex items-center gap-1.5 mt-1 text-xs text-[hsl(var(--primary))] hover:underline"
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
  const isPending = startFlow.isPending || stopFlow.isPending;

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
        <div className="flex items-center gap-3">
          {/* Auto-refresh toggle */}
          <div className="flex items-center gap-2">
            <Switch
              id="auto-refresh"
              checked={autoRefresh}
              onCheckedChange={setAutoRefresh}
            />
            <Label htmlFor="auto-refresh" className="text-xs text-muted-foreground cursor-pointer">
              Auto refresh
            </Label>
          </div>
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
          onBulkStart={isAdmin ? handleBulkStart : undefined}
          onBulkStop={isAdmin ? handleBulkStop : undefined}
          onEditSchedule={isAdmin ? setEditFlow : undefined}
          onEditProperties={(flowId, isFlow) => setPropsTarget({ flowId, isFlow })}
          isPending={isPending}
        />
      )}

      {/* Utility Flows */}
      {data.utilityFlows.length > 0 && (
        <FlowTable
          title="Utility Transaction Flows"
          flows={data.utilityFlows}
          isAdmin={isAdmin}
          onStartStop={handleStartStop}
          onBulkStart={isAdmin ? handleBulkStart : undefined}
          onBulkStop={isAdmin ? handleBulkStop : undefined}
          onEditSchedule={isAdmin ? setEditFlow : undefined}
          onEditProperties={(flowId, isFlow) => setPropsTarget({ flowId, isFlow })}
          isPending={isPending}
        />
      )}

      {/* Query Flows */}
      {data.queryFlows.length > 0 && (
        <div className="glass-panel rounded-[var(--radius)] p-4">
          <h3 className="text-sm font-semibold mb-3 flex items-center gap-2">
            <Zap className="w-4 h-4 text-[hsl(var(--primary))]" />
            Query Flows
            <Badge variant="secondary" className="text-xs">
              {data.queryFlows.length}
            </Badge>
          </h3>
          <div className="space-y-2">
            {data.queryFlows.map((q) => (
              <div
                key={q.flowId}
                className="group rounded-xl border border-[hsl(var(--border))] p-3 flex items-center gap-4 hover:bg-muted/20 transition-colors"
              >
                <div className="w-8 h-8 rounded-lg bg-[hsl(var(--muted)/0.3)] grid place-items-center shrink-0">
                  <Zap className="w-3.5 h-3.5 text-[hsl(var(--primary))]" />
                </div>
                <div className="flex-1 min-w-0">
                  <button
                    type="button"
                    onClick={() => setPropsTarget({ flowId: q.flowId, isFlow: false })}
                    className="text-sm font-medium truncate hover:text-[hsl(var(--primary))] hover:underline transition-colors inline-flex items-center gap-1 cursor-pointer bg-transparent border-none p-0"
                  >
                    {q.flowId}
                    <FileText className="w-2.5 h-2.5 opacity-0 group-hover:opacity-50 transition-opacity flex-shrink-0" />
                  </button>
                  <p className="text-xs text-muted-foreground">
                    Counter: {q.counter === 0 ? "\u221E" : q.counter} · Interval: {q.interval}s
                  </p>
                </div>
                <div className="flex items-center gap-1.5">
                  {q.httpGetQuery && (
                    <Button variant="outline" size="sm" className="text-xs h-7" asChild>
                      <a href={q.httpGetQuery} target="_blank" rel="noopener noreferrer">
                        <Play />
                        GO
                      </a>
                    </Button>
                  )}
                  <DropdownMenu>
                    <DropdownMenuTrigger asChild>
                      <button className="p-1 rounded-md hover:bg-muted/50 text-muted-foreground hover:text-foreground transition-colors cursor-pointer">
                        <MoreHorizontal className="w-4 h-4" />
                      </button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent align="end">
                      <DropdownMenuLabel>{q.flowId}</DropdownMenuLabel>
                      <DropdownMenuSeparator />
                      <DropdownMenuItem onClick={() => setPropsTarget({ flowId: q.flowId, isFlow: false })}>
                        <FileText />
                        Edit Properties
                      </DropdownMenuItem>
                      <DropdownMenuItem asChild>
                        <Link to={`/monitoring/transactions?flow=${encodeURIComponent(q.flowId)}`}>
                          <History />
                          View Transactions
                        </Link>
                      </DropdownMenuItem>
                    </DropdownMenuContent>
                  </DropdownMenu>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {allFlows.length === 0 && data.queryFlows.length === 0 && (
        <div className="glass-panel rounded-[var(--radius)] p-8 text-center space-y-3">
          <Server className="w-8 h-8 text-muted-foreground mx-auto" />
          <div>
            <p className="text-sm text-muted-foreground">No flows loaded in engine.</p>
            <p className="text-xs text-muted-foreground mt-1">
              Flows are loaded when a workspace profile is compiled and the engine is initialized
              through the classic Business Daemon Configurator.
            </p>
          </div>
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

      {/* Flow properties dialog */}
      <FlowPropertiesDialog
        flowId={propsTarget?.flowId ?? null}
        isFlow={propsTarget?.isFlow ?? true}
        open={!!propsTarget}
        onOpenChange={(open) => { if (!open) setPropsTarget(null); }}
      />
    </div>
  );
}
