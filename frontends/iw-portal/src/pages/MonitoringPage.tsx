import { Activity, Bell, Clock, Loader2, RefreshCw } from "lucide-react";
import { NavLink } from "react-router-dom";
import { cn } from "@/lib/utils";
import { useDashboard } from "@/hooks/useMonitoring";

export function MonitoringPage() {
  const { data: dashRes, isLoading, refetch, isFetching } = useDashboard();
  const summary = dashRes?.data?.summary;
  const recent = dashRes?.data?.recent_activity?.last_hour;

  const tabs = [
    { to: "/monitoring", label: "Overview", icon: Activity, end: true },
    { to: "/monitoring/transactions", label: "Transactions", icon: Clock, end: false },
    { to: "/monitoring/alerts", label: "Alerts", icon: Bell, end: false },
  ];

  return (
    <div className="space-y-6">
      <div className="flex items-start justify-between gap-4 flex-wrap">
        <div>
          <h1 className="text-xl font-bold">Monitoring</h1>
          <p className="text-sm text-muted-foreground">
            Transaction execution, performance metrics, and alert configuration.
          </p>
        </div>
        <button
          onClick={() => void refetch()}
          disabled={isFetching}
          className="flex items-center gap-1.5 text-xs text-muted-foreground px-2.5 py-1 rounded-full border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)] hover:text-foreground transition-colors cursor-pointer disabled:opacity-50"
        >
          <RefreshCw className={cn("w-3 h-3", isFetching && "animate-spin")} />
          Refresh
        </button>
      </div>

      {/* Tab navigation */}
      <div className="flex gap-1 border-b border-[hsl(var(--border))]">
        {tabs.map((tab) => (
          <NavLink
            key={tab.to}
            to={tab.to}
            end={tab.end}
            className={({ isActive }) =>
              cn(
                "flex items-center gap-2 px-4 py-2 text-sm font-medium border-b-2 -mb-px transition-colors",
                isActive
                  ? "border-[hsl(var(--primary))] text-foreground"
                  : "border-transparent text-muted-foreground hover:text-foreground"
              )
            }
          >
            <tab.icon className="w-4 h-4" />
            {tab.label}
          </NavLink>
        ))}
      </div>

      {/* Summary cards */}
      {isLoading ? (
        <div className="flex justify-center py-8">
          <Loader2 className="w-6 h-6 animate-spin text-muted-foreground" />
        </div>
      ) : (
        <div className="grid grid-cols-3 gap-4 max-md:grid-cols-1">
          <div className="glass-panel rounded-[var(--radius)] p-4">
            <p className="text-xs text-muted-foreground mb-1">24h Summary</p>
            <p className="text-lg font-bold">{summary?.total_count_24h ?? 0} transactions</p>
            <p className="text-xs text-muted-foreground mt-1">
              {summary?.completed_count_24h ?? 0} success • {summary?.failed_count_24h ?? 0} failed
            </p>
          </div>
          <div className="glass-panel rounded-[var(--radius)] p-4">
            <p className="text-xs text-muted-foreground mb-1">7d Summary</p>
            <p className="text-lg font-bold">{summary?.total_count_7d ?? 0} transactions</p>
            <p className="text-xs text-muted-foreground mt-1">
              Success rate: {summary?.success_rate_7d?.toFixed(1) ?? "0.0"}%
            </p>
          </div>
          <div className="glass-panel rounded-[var(--radius)] p-4">
            <p className="text-xs text-muted-foreground mb-1">Last Hour</p>
            <p className="text-lg font-bold">{recent?.total ?? 0} transactions</p>
            <p className="text-xs text-muted-foreground mt-1">
              {recent?.success ?? 0} success • {recent?.failed ?? 0} failed
            </p>
          </div>
        </div>
      )}

      {/* API status indicator */}
      <div className="glass-panel rounded-[var(--radius)] p-4 flex items-center gap-3">
        <span className="w-2.5 h-2.5 rounded-full bg-[hsl(var(--success))]" />
        <div>
          <p className="text-sm font-medium">Monitoring API Connected</p>
          <p className="text-xs text-muted-foreground">
            Endpoints: /api/monitoring/dashboard, /transactions, /metrics, /alerts
            {summary?.last_updated ? ` • Last updated: ${new Date(summary.last_updated).toLocaleTimeString()}` : ""}
          </p>
        </div>
      </div>
    </div>
  );
}
