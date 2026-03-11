import {
  Workflow,
  Play,
  CheckCircle,
  AlertTriangle,
  Loader2,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { Sparkline } from "@/components/Sparkline";

interface KpiRowProps {
  flowCount: number;
  runningCount: number;
  successRate: number;
  failedCount: number;
  isLoading: boolean;
  /** Recent running counts for sparkline (newest last) */
  runningHistory?: number[];
  /** Recent success rates for sparkline (newest last) */
  successHistory?: number[];
}

const KPI_DEFS = [
  { key: "flows", label: "Active Flows", icon: Workflow, color: "text-[hsl(var(--primary))]", accent: "border-t-[3px] border-t-[hsl(var(--primary))]" },
  { key: "running", label: "Running Now", icon: Play, color: "text-[hsl(var(--success))]", accent: "border-t-[3px] border-t-[hsl(var(--success))]" },
  { key: "success", label: "24h Success Rate", icon: CheckCircle, color: "text-[hsl(var(--success))]", accent: "border-t-[3px] border-t-[hsl(var(--accent))]" },
  { key: "failures", label: "24h Failures", icon: AlertTriangle, color: "", accent: "border-t-[3px] border-t-[hsl(var(--warning))]" },
] as const;

export function KpiRow({
  flowCount,
  runningCount,
  successRate,
  failedCount,
  isLoading,
  runningHistory,
  successHistory,
}: KpiRowProps) {
  const values: Record<string, string> = {
    flows: String(flowCount),
    running: String(runningCount),
    success: `${successRate.toFixed(1)}%`,
    failures: String(failedCount),
  };

  const sparklines: Record<string, number[] | undefined> = {
    running: runningHistory,
    success: successHistory,
  };

  return (
    <div className="grid grid-cols-4 gap-4 max-lg:grid-cols-2 max-sm:grid-cols-1">
      {KPI_DEFS.map((kpi) => {
        const dynamicColor =
          kpi.key === "failures" && failedCount > 0
            ? "text-[hsl(var(--destructive))]"
            : kpi.key === "failures"
              ? "text-muted-foreground"
              : kpi.color;

        return (
          <div key={kpi.key} className={cn("glass-panel rounded-[var(--radius)] p-4 space-y-2", kpi.accent)}>
            <div className="flex items-center justify-between">
              <p className="text-xs text-muted-foreground">{kpi.label}</p>
              <kpi.icon className={cn("w-4 h-4", dynamicColor)} />
            </div>
            {isLoading ? (
              <Loader2 className="w-5 h-5 animate-spin text-muted-foreground" />
            ) : (
              <div className="flex items-end justify-between gap-2">
                <p className="text-2xl font-bold tracking-tight">{values[kpi.key]}</p>
                {sparklines[kpi.key] && sparklines[kpi.key]!.length >= 2 && (
                  <Sparkline
                    data={sparklines[kpi.key]!}
                    width={64}
                    height={20}
                    color={kpi.key === "success" ? "hsl(var(--success))" : "hsl(var(--primary))"}
                    className="opacity-60"
                  />
                )}
              </div>
            )}
          </div>
        );
      })}
    </div>
  );
}
