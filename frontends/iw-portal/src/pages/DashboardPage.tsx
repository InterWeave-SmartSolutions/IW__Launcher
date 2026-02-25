import { Activity, CheckCircle, AlertTriangle, Clock, TrendingUp, Zap, RefreshCw, Loader2 } from "lucide-react";
import { cn } from "@/lib/utils";
import { useDashboard, useTransactions } from "@/hooks/useMonitoring";
import type { Transaction } from "@/types/monitoring";

function fmt(n: number | undefined | null): string {
  if (n == null) return "—";
  return n.toLocaleString();
}

function fmtRate(n: number | undefined | null): string {
  if (n == null) return "—";
  return n.toFixed(1) + "%";
}

function fmtDuration(ms: number | undefined | null): string {
  if (ms == null || ms === 0) return "—";
  if (ms < 1000) return ms + "ms";
  return (ms / 1000).toFixed(1) + "s";
}

function fmtTime(iso: string | null): string {
  if (!iso) return "—";
  try {
    return new Date(iso).toLocaleTimeString();
  } catch {
    return iso;
  }
}

function statusBadge(status: string) {
  const colors: Record<string, string> = {
    running: "bg-[hsl(var(--primary)/0.15)] text-[hsl(var(--primary))]",
    success: "bg-[hsl(var(--success)/0.15)] text-[hsl(var(--success))]",
    failed: "bg-[hsl(var(--destructive)/0.15)] text-[hsl(var(--destructive))]",
    timeout: "bg-[hsl(var(--warning)/0.15)] text-[hsl(var(--warning))]",
  };
  return (
    <span className={cn("px-2 py-0.5 rounded-full text-[11px] font-medium", colors[status] ?? "text-muted-foreground")}>
      {status}
    </span>
  );
}

export function DashboardPage() {
  const { data: dashRes, isLoading, error, refetch, isFetching } = useDashboard();
  const { data: txRes } = useTransactions(1, 10);

  const summary = dashRes?.data?.summary;
  const running = dashRes?.data?.running_transactions ?? [];
  const recent = dashRes?.data?.recent_activity?.last_hour;
  const transactions = txRes?.data?.transactions ?? [];

  const kpis = [
    {
      label: "Transactions (24h)",
      value: fmt(summary?.total_count_24h),
      sub: `${fmt(summary?.completed_count_24h)} completed, ${fmt(summary?.failed_count_24h)} failed`,
      icon: Activity,
      color: "text-[hsl(var(--primary))]",
    },
    {
      label: "Success Rate (24h)",
      value: fmtRate(summary?.success_rate_24h),
      sub: `7d: ${fmtRate(summary?.success_rate_7d)}`,
      icon: CheckCircle,
      color: "text-[hsl(var(--success))]",
    },
    {
      label: "Running Now",
      value: fmt(summary?.running_count),
      sub: running.length > 0 ? `${running[0]!.flow_name}...` : "No active flows",
      icon: AlertTriangle,
      color: "text-[hsl(var(--warning))]",
    },
    {
      label: "Avg Duration (24h)",
      value: fmtDuration(summary?.avg_duration_ms_24h),
      sub: recent ? `Last hour: ${recent.total} txns` : "—",
      icon: Clock,
      color: "text-muted-foreground",
    },
  ];

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex items-start justify-between gap-4 flex-wrap">
        <div>
          <h1 className="text-xl font-bold">Dashboard</h1>
          <p className="text-sm text-muted-foreground">
            Platform health, transaction metrics, and operational status.
          </p>
        </div>
        <div className="flex items-center gap-2">
          <button
            onClick={() => void refetch()}
            disabled={isFetching}
            className="flex items-center gap-1.5 text-xs text-muted-foreground px-2.5 py-1 rounded-full border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)] hover:text-foreground transition-colors cursor-pointer disabled:opacity-50"
          >
            <RefreshCw className={cn("w-3 h-3", isFetching && "animate-spin")} />
            Refresh
          </button>
          <span className="flex items-center gap-1.5 text-xs text-muted-foreground px-2.5 py-1 rounded-full border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)]">
            <span className="w-2 h-2 rounded-full bg-[hsl(var(--success))]" />
            Services Online
          </span>
        </div>
      </div>

      {/* Error banner */}
      {error && (
        <div className="p-3 rounded-[var(--radius)] border border-[hsl(var(--destructive)/0.3)] bg-[hsl(var(--destructive)/0.08)] text-sm text-[hsl(var(--destructive))]">
          Failed to load dashboard data: {error.message}
        </div>
      )}

      {/* KPI Cards */}
      <div className="grid grid-cols-4 gap-4 max-lg:grid-cols-2 max-sm:grid-cols-1">
        {kpis.map((kpi) => (
          <div key={kpi.label} className="glass-panel rounded-[var(--radius)] p-4 space-y-2">
            <div className="flex items-center justify-between">
              <p className="text-xs text-muted-foreground">{kpi.label}</p>
              <kpi.icon className={cn("w-4 h-4", kpi.color)} />
            </div>
            {isLoading ? (
              <Loader2 className="w-5 h-5 animate-spin text-muted-foreground" />
            ) : (
              <>
                <p className="text-2xl font-bold tracking-tight">{kpi.value}</p>
                <p className="text-xs text-muted-foreground truncate">{kpi.sub}</p>
              </>
            )}
          </div>
        ))}
      </div>

      {/* Recent Transactions table */}
      <div className="glass-panel rounded-[var(--radius)] p-4">
        <div className="flex items-center justify-between mb-3">
          <h2 className="text-sm font-semibold">Recent Transactions</h2>
          <span className="flex items-center gap-1.5 text-xs text-muted-foreground">
            <span className="w-2 h-2 rounded-full bg-[hsl(var(--success))]" />
            Auto-refreshes every 30s
          </span>
        </div>
        <div className="border border-[hsl(var(--border))] rounded-[14px] overflow-hidden">
          <table className="w-full text-sm">
            <thead>
              <tr className="bg-[hsl(var(--muted)/0.3)]">
                <th className="text-left px-3 py-2 text-xs text-muted-foreground font-medium">Time</th>
                <th className="text-left px-3 py-2 text-xs text-muted-foreground font-medium">Flow</th>
                <th className="text-left px-3 py-2 text-xs text-muted-foreground font-medium">Status</th>
                <th className="text-left px-3 py-2 text-xs text-muted-foreground font-medium">Records</th>
                <th className="text-left px-3 py-2 text-xs text-muted-foreground font-medium">Duration</th>
              </tr>
            </thead>
            <tbody>
              {isLoading ? (
                <tr>
                  <td className="px-3 py-6 text-center text-muted-foreground" colSpan={5}>
                    <Loader2 className="w-5 h-5 animate-spin mx-auto" />
                  </td>
                </tr>
              ) : transactions.length === 0 ? (
                <tr>
                  <td className="px-3 py-6 text-center text-muted-foreground" colSpan={5}>
                    No transaction data yet. Transactions will appear here once flows are executed.
                  </td>
                </tr>
              ) : (
                transactions.map((tx: Transaction) => (
                  <tr key={tx.execution_id} className="border-t border-[hsl(var(--border))]">
                    <td className="px-3 py-2 text-xs">{fmtTime(tx.started_at)}</td>
                    <td className="px-3 py-2 text-xs font-medium">{tx.flow_name}</td>
                    <td className="px-3 py-2">{statusBadge(tx.status)}</td>
                    <td className="px-3 py-2 text-xs">{tx.records_processed}</td>
                    <td className="px-3 py-2 text-xs">{fmtDuration(tx.duration_ms)}</td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>

      {/* Quick Actions */}
      <div className="glass-panel rounded-[var(--radius)] p-4">
        <h2 className="text-sm font-semibold mb-3">Quick Actions</h2>
        <div className="grid grid-cols-3 gap-3 max-md:grid-cols-1">
          <a
            href="/iw-business-daemon/BDConfigurator.jsp"
            className="flex items-center gap-2 px-3 py-2.5 rounded-[14px] border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)] text-sm hover:bg-[hsl(var(--accent)/0.5)] transition-colors"
          >
            <Zap className="w-4 h-4 text-[hsl(var(--primary))]" />
            Open BD Configurator
          </a>
          <a
            href="/iw-business-daemon/CompanyConfiguration.jsp"
            className="flex items-center gap-2 px-3 py-2.5 rounded-[14px] border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)] text-sm hover:bg-[hsl(var(--accent)/0.5)] transition-colors"
          >
            <TrendingUp className="w-4 h-4 text-[hsl(var(--success))]" />
            Company Config
          </a>
          <a
            href="/iw-business-daemon/monitoring/Dashboard.jsp"
            className="flex items-center gap-2 px-3 py-2.5 rounded-[14px] border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)] text-sm hover:bg-[hsl(var(--accent)/0.5)] transition-colors"
          >
            <Activity className="w-4 h-4 text-[hsl(var(--warning))]" />
            Classic Monitoring
          </a>
        </div>
      </div>
    </div>
  );
}
