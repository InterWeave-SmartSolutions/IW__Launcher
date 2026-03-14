import { Link } from "react-router-dom";
import {
  Workflow,
  Clock,
  Play,
  Square,
  Loader2,
  Settings,
  ArrowRight,
  CheckCircle,
  XCircle,
  Search,
} from "lucide-react";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Separator } from "@/components/ui/separator";
import { FlowChainVisualization } from "./FlowChainVisualization";
import type { FlowListItem } from "./FlowList";
import type { WorkspaceTransaction } from "@/types/workspace";

interface FlowDetailProps {
  flow: FlowListItem;
  allTransactions: WorkspaceTransaction[];
  onStart?: (flowIndex: number) => void;
  onStop?: (flowIndex: number) => void;
  isStarting?: boolean;
  isStopping?: boolean;
}

export function FlowDetail({
  flow,
  allTransactions,
  onStart,
  onStop,
  isStarting,
  isStopping,
}: FlowDetailProps) {
  const ef = flow.engineFlow;
  const isRunning = ef?.running === true;
  const isExecuting = ef?.executing === true;
  const isTransaction = flow.type === "transaction";
  const tx = isTransaction ? (flow.raw as WorkspaceTransaction) : null;

  return (
    <div className="space-y-5">
      {/* Header */}
      <div className="flex items-start justify-between gap-3">
        <div>
          <div className="flex items-center gap-2">
            {isTransaction ? (
              <Workflow className="w-5 h-5 text-[hsl(var(--primary))]" />
            ) : (
              <Search className="w-5 h-5 text-[hsl(var(--primary))]" />
            )}
            <h2 className="text-lg font-semibold">{flow.id}</h2>
          </div>
          {flow.description && (
            <p className="text-sm text-muted-foreground mt-1">{flow.description}</p>
          )}
        </div>
        <div className="flex items-center gap-2 shrink-0">
          <Badge variant={isRunning ? "success" : ef ? "secondary" : "outline"}>
            {isRunning ? "Running" : ef ? "Loaded" : "Not Loaded"}
          </Badge>
          {isExecuting && (
            <Badge variant="default" className="animate-pulse">Executing</Badge>
          )}
        </div>
      </div>

      {/* Engine controls */}
      {ef && (
        <div className="flex items-center gap-2">
          {isRunning ? (
            <Button
              variant="outline"
              size="sm"
              onClick={() => onStop?.(ef.index)}
              disabled={isStopping}
            >
              {isStopping ? (
                <Loader2 className="w-3.5 h-3.5 animate-spin" />
              ) : (
                <Square className="w-3.5 h-3.5" />
              )}
              Stop
            </Button>
          ) : (
            <Button
              variant="outline"
              size="sm"
              onClick={() => onStart?.(ef.index)}
              disabled={isStarting}
            >
              {isStarting ? (
                <Loader2 className="w-3.5 h-3.5 animate-spin" />
              ) : (
                <Play className="w-3.5 h-3.5" />
              )}
              Start
            </Button>
          )}
          <Button variant="ghost" size="sm" asChild>
            <Link to={`/monitoring/transactions?flow=${encodeURIComponent(flow.id)}`}>
              <ArrowRight className="w-3.5 h-3.5" /> Transaction History
            </Link>
          </Button>
        </div>
      )}

      <Separator />

      {/* Metadata */}
      <div className="grid grid-cols-2 gap-3 text-sm">
        <MetaItem label="Type" value={isTransaction ? "Transaction" : "Query"} />
        {flow.interval && <MetaItem label="Interval" value={`${flow.interval}s`} />}
        {tx?.Shift && <MetaItem label="Shift" value={`${tx.Shift}s`} />}
        {tx?.Solution && <MetaItem label="Solution" value={tx.Solution} />}
        {tx?.NextTransaction && tx.NextTransaction !== "none" && (
          <MetaItem label="Next" value={tx.NextTransaction} />
        )}
        {ef && (
          <>
            <MetaItem
              label="Successes"
              value={String(ef.successes)}
              icon={<CheckCircle className="w-3 h-3 text-[hsl(var(--success))]" />}
            />
            <MetaItem
              label="Failures"
              value={String(ef.failures)}
              icon={<XCircle className="w-3 h-3 text-[hsl(var(--destructive))]" />}
            />
            <MetaItem label="Counter" value={String(ef.counter)} />
            <MetaItem
              label="Schedule"
              value={`${ef.intervalDisplay} / ${ef.shiftDisplay}`}
              icon={<Clock className="w-3 h-3 text-muted-foreground" />}
            />
          </>
        )}
      </div>

      {/* Parameters */}
      {flow.paramCount > 0 && (
        <>
          <Separator />
          <div>
            <div className="flex items-center gap-2 mb-3">
              <Settings className="w-4 h-4 text-[hsl(var(--primary))]" />
              <h3 className="text-sm font-semibold">Parameters ({flow.paramCount})</h3>
            </div>
            <div className="space-y-1.5">
              {(isTransaction
                ? (flow.raw as WorkspaceTransaction).parameters ?? []
                : (flow.raw as any).parameters ?? []
              ).map((param: { Name: string; Value: string }) => (
                <div
                  key={param.Name}
                  className="flex items-center justify-between px-3 py-2 rounded-lg border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.1)] text-xs"
                >
                  <span className="font-mono font-medium text-foreground">{param.Name}</span>
                  <span className="text-muted-foreground truncate ml-4 max-w-[200px]">
                    {param.Value || <span className="italic">empty</span>}
                  </span>
                </div>
              ))}
            </div>
          </div>
        </>
      )}

      {/* Chain visualization (transactions only) */}
      {tx && allTransactions.length > 0 && (
        <>
          <Separator />
          <div>
            <h3 className="text-sm font-semibold mb-3">Transaction Chain</h3>
            <FlowChainVisualization transaction={tx} allTransactions={allTransactions} />
          </div>
        </>
      )}
    </div>
  );
}

function MetaItem({
  label,
  value,
  icon,
}: {
  label: string;
  value: string;
  icon?: React.ReactNode;
}) {
  return (
    <div className="flex items-center gap-2 px-3 py-2 rounded-lg bg-[hsl(var(--muted)/0.2)]">
      {icon}
      <div>
        <p className="text-[10px] text-muted-foreground">{label}</p>
        <p className="text-xs font-medium">{value}</p>
      </div>
    </div>
  );
}
