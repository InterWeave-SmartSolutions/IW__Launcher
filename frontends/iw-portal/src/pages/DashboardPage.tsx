import { Activity, CheckCircle, AlertTriangle, Clock, TrendingUp, Zap } from "lucide-react";
import { cn } from "@/lib/utils";

/** Static placeholder dashboard — will be wired to /api/monitoring/dashboard via TanStack Query */
export function DashboardPage() {
  const kpis = [
    { label: "Total Transactions", value: "—", icon: Activity, color: "text-[hsl(var(--primary))]" },
    { label: "Success Rate", value: "—", icon: CheckCircle, color: "text-[hsl(var(--success))]" },
    { label: "Active Alerts", value: "—", icon: AlertTriangle, color: "text-[hsl(var(--warning))]" },
    { label: "Avg Duration", value: "—", icon: Clock, color: "text-muted-foreground" },
  ];

  return (
    <div className="space-y-6">
      {/* Header (ASSA Master Console pattern) */}
      <div className="flex items-start justify-between gap-4 flex-wrap">
        <div>
          <h1 className="text-xl font-bold">Dashboard</h1>
          <p className="text-sm text-muted-foreground">
            Platform health, transaction metrics, and operational status.
          </p>
        </div>
        <div className="flex items-center gap-2">
          <span className="flex items-center gap-1.5 text-xs text-muted-foreground px-2.5 py-1 rounded-full border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)]">
            <span className="w-2 h-2 rounded-full bg-[hsl(var(--success))]" />
            Services Online
          </span>
        </div>
      </div>

      {/* KPI Cards (ASSA 4-column grid) */}
      <div className="grid grid-cols-4 gap-4 max-lg:grid-cols-2 max-sm:grid-cols-1">
        {kpis.map((kpi) => (
          <div
            key={kpi.label}
            className="glass-panel rounded-[var(--radius)] p-4 space-y-2"
          >
            <div className="flex items-center justify-between">
              <p className="text-xs text-muted-foreground">{kpi.label}</p>
              <kpi.icon className={cn("w-4 h-4", kpi.color)} />
            </div>
            <p className="text-2xl font-bold tracking-tight">{kpi.value}</p>
            <p className="text-xs text-muted-foreground">Awaiting data</p>
          </div>
        ))}
      </div>

      {/* Operational Queue placeholder (ASSA Master Console pattern) */}
      <div className="glass-panel rounded-[var(--radius)] p-4">
        <div className="flex items-center justify-between mb-3">
          <h2 className="text-sm font-semibold">Recent Transactions</h2>
          <div className="flex items-center gap-2">
            <span className="flex items-center gap-1.5 text-xs text-muted-foreground">
              <span className="w-2 h-2 rounded-full bg-[hsl(var(--success))]" />
              Monitoring Active
            </span>
          </div>
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
              <tr>
                <td className="px-3 py-6 text-center text-muted-foreground" colSpan={5}>
                  No transaction data yet. Transactions will appear here once flows are instrumented.
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      {/* Quick Actions (ASSA pattern) */}
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
