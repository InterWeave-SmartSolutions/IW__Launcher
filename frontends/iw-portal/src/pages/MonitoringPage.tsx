import { useState } from "react";
import { Loader2, TrendingUp, Timer, Database, BarChart3 } from "lucide-react";
import { useDashboard, useMetrics } from "@/hooks/useMonitoring";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import {
  ResponsiveContainer,
  AreaChart,
  Area,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
} from "recharts";

type Period = "24h" | "7d" | "30d" | "90d";
type Granularity = "hourly" | "daily" | "weekly";

const PERIOD_OPTIONS: { value: Period; label: string; granularity: Granularity }[] = [
  { value: "24h", label: "24 hours", granularity: "hourly" },
  { value: "7d", label: "7 days", granularity: "daily" },
  { value: "30d", label: "30 days", granularity: "daily" },
  { value: "90d", label: "90 days", granularity: "weekly" },
];

export function MonitoringPage() {
  useDocumentTitle("Monitoring");
  const { data: dashRes, isLoading: dashLoading } = useDashboard();
  const [period, setPeriod] = useState<Period>("7d");
  const granularity = PERIOD_OPTIONS.find((p) => p.value === period)!.granularity;
  const { data: metrics, isLoading: metricsLoading } = useMetrics(granularity, period);

  const summary = dashRes?.data?.summary;
  const recent = dashRes?.data?.recent_activity?.last_hour;

  return (
    <div className="space-y-6">
      {/* Summary cards */}
      {dashLoading ? (
        <div className="flex justify-center py-8">
          <Loader2 className="w-6 h-6 animate-spin text-muted-foreground" />
        </div>
      ) : (
        <div className="grid grid-cols-4 gap-4 max-lg:grid-cols-2 max-sm:grid-cols-1">
          <SummaryCard
            icon={BarChart3}
            label="24h Transactions"
            value={summary?.total_count_24h ?? 0}
            detail={`${summary?.completed_count_24h ?? 0} success · ${summary?.failed_count_24h ?? 0} failed`}
          />
          <SummaryCard
            icon={TrendingUp}
            label="7d Success Rate"
            value={`${(summary?.success_rate_7d ?? 0).toFixed(1)}%`}
            detail={`${summary?.total_count_7d ?? 0} total transactions`}
          />
          <SummaryCard
            icon={Timer}
            label="Avg Duration (24h)"
            value={formatDuration(summary?.avg_duration_ms_24h ?? 0)}
            detail="Average execution time"
          />
          <SummaryCard
            icon={Database}
            label="Last Hour"
            value={recent?.total ?? 0}
            detail={`${recent?.success ?? 0} success · ${recent?.failed ?? 0} failed`}
          />
        </div>
      )}

      {/* Period selector */}
      <div className="flex items-center justify-between">
        <h2 className="text-sm font-semibold">Performance Trends</h2>
        <div className="flex gap-1">
          {PERIOD_OPTIONS.map((opt) => (
            <button
              key={opt.value}
              onClick={() => setPeriod(opt.value)}
              className={`px-2.5 py-1 text-xs rounded-full border transition-colors cursor-pointer ${
                period === opt.value
                  ? "border-[hsl(var(--primary))] bg-[hsl(var(--primary)/0.15)] text-foreground"
                  : "border-[hsl(var(--border))] text-muted-foreground hover:text-foreground"
              }`}
            >
              {opt.label}
            </button>
          ))}
        </div>
      </div>

      {/* Charts */}
      {metricsLoading ? (
        <div className="flex justify-center py-12">
          <Loader2 className="w-6 h-6 animate-spin text-muted-foreground" />
        </div>
      ) : metrics?.points.length ? (
        <div className="grid grid-cols-2 gap-4 max-lg:grid-cols-1">
          {/* Execution trends (stacked area) */}
          <div className="glass-panel rounded-[var(--radius)] p-4">
            <p className="text-xs font-medium text-muted-foreground mb-3">Executions</p>
            <ResponsiveContainer width="100%" height={220}>
              <AreaChart data={metrics.points}>
                <CartesianGrid strokeDasharray="3 3" stroke="hsl(var(--border))" />
                <XAxis
                  dataKey="date"
                  tick={{ fontSize: 10, fill: "hsl(var(--muted-foreground))" }}
                  tickFormatter={formatLabel}
                />
                <YAxis tick={{ fontSize: 10, fill: "hsl(var(--muted-foreground))" }} />
                <Tooltip
                  contentStyle={{
                    background: "hsl(var(--card))",
                    border: "1px solid hsl(var(--border))",
                    borderRadius: 8,
                    fontSize: 12,
                  }}
                />
                <Legend wrapperStyle={{ fontSize: 11 }} />
                <Area
                  type="monotone"
                  dataKey="success"
                  name="Success"
                  stackId="1"
                  stroke="hsl(var(--success))"
                  fill="hsl(var(--success) / 0.3)"
                />
                <Area
                  type="monotone"
                  dataKey="failed"
                  name="Failed"
                  stackId="1"
                  stroke="hsl(var(--destructive))"
                  fill="hsl(var(--destructive) / 0.3)"
                />
              </AreaChart>
            </ResponsiveContainer>
          </div>

          {/* Average Duration (bar chart) */}
          <div className="glass-panel rounded-[var(--radius)] p-4">
            <p className="text-xs font-medium text-muted-foreground mb-3">Avg Duration (ms)</p>
            <ResponsiveContainer width="100%" height={220}>
              <BarChart data={metrics.points}>
                <CartesianGrid strokeDasharray="3 3" stroke="hsl(var(--border))" />
                <XAxis
                  dataKey="date"
                  tick={{ fontSize: 10, fill: "hsl(var(--muted-foreground))" }}
                  tickFormatter={formatLabel}
                />
                <YAxis tick={{ fontSize: 10, fill: "hsl(var(--muted-foreground))" }} />
                <Tooltip
                  contentStyle={{
                    background: "hsl(var(--card))",
                    border: "1px solid hsl(var(--border))",
                    borderRadius: 8,
                    fontSize: 12,
                  }}
                />
                <Bar dataKey="avgDuration" name="Avg Duration" fill="hsl(var(--primary))" radius={[4, 4, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </div>

          {/* Records processed (area chart) */}
          <div className="glass-panel rounded-[var(--radius)] p-4 col-span-2 max-lg:col-span-1">
            <p className="text-xs font-medium text-muted-foreground mb-3">Records Processed</p>
            <ResponsiveContainer width="100%" height={180}>
              <AreaChart data={metrics.points}>
                <CartesianGrid strokeDasharray="3 3" stroke="hsl(var(--border))" />
                <XAxis
                  dataKey="date"
                  tick={{ fontSize: 10, fill: "hsl(var(--muted-foreground))" }}
                  tickFormatter={formatLabel}
                />
                <YAxis tick={{ fontSize: 10, fill: "hsl(var(--muted-foreground))" }} />
                <Tooltip
                  contentStyle={{
                    background: "hsl(var(--card))",
                    border: "1px solid hsl(var(--border))",
                    borderRadius: 8,
                    fontSize: 12,
                  }}
                />
                <Area
                  type="monotone"
                  dataKey="records"
                  name="Records"
                  stroke="hsl(var(--warning))"
                  fill="hsl(var(--warning) / 0.2)"
                />
              </AreaChart>
            </ResponsiveContainer>
          </div>
        </div>
      ) : (
        <div className="glass-panel rounded-[var(--radius)] p-8 text-center">
          <BarChart3 className="w-8 h-8 text-muted-foreground mx-auto mb-2" />
          <p className="text-sm text-muted-foreground">No metrics data available for this period.</p>
          <p className="text-xs text-muted-foreground mt-1">
            Run some integration flows to see performance trends here.
          </p>
        </div>
      )}

      {/* Metrics summary footer */}
      {metrics?.summary && (
        <div className="glass-panel rounded-[var(--radius)] p-4 flex items-center gap-6 text-xs text-muted-foreground flex-wrap">
          <span>
            Period total: <strong className="text-foreground">{metrics.summary.total_executions}</strong> executions
          </span>
          <span>
            Success rate: <strong className="text-foreground">{metrics.summary.success_rate.toFixed(1)}%</strong>
          </span>
          <span>
            Avg duration: <strong className="text-foreground">{formatDuration(metrics.summary.avg_duration_ms)}</strong>
          </span>
          <span>
            Records: <strong className="text-foreground">{metrics.summary.total_records.toLocaleString()}</strong>
          </span>
        </div>
      )}
    </div>
  );
}

function SummaryCard({
  icon: Icon,
  label,
  value,
  detail,
}: {
  icon: React.ComponentType<{ className?: string }>;
  label: string;
  value: string | number;
  detail: string;
}) {
  return (
    <div className="glass-panel rounded-[var(--radius)] p-4">
      <div className="flex items-center gap-2 mb-2">
        <Icon className="w-4 h-4 text-[hsl(var(--primary))]" />
        <p className="text-xs text-muted-foreground">{label}</p>
      </div>
      <p className="text-lg font-bold">{typeof value === "number" ? value.toLocaleString() : value}</p>
      <p className="text-xs text-muted-foreground mt-1">{detail}</p>
    </div>
  );
}

function formatDuration(ms: number): string {
  if (ms === 0) return "0ms";
  if (ms < 1000) return `${Math.round(ms)}ms`;
  if (ms < 60_000) return `${(ms / 1000).toFixed(1)}s`;
  return `${(ms / 60_000).toFixed(1)}m`;
}

function formatLabel(value: string): string {
  // Shorten date labels: "2026-01-08" → "Jan 8", "2026-01-08 14:00" → "14:00"
  if (value.includes(" ")) return value.split(" ")[1] ?? value;
  const parts = value.split("-");
  if (parts.length === 3) {
    const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    return `${months[parseInt(parts[1] ?? "1", 10) - 1]} ${parseInt(parts[2] ?? "1", 10)}`;
  }
  return value;
}
