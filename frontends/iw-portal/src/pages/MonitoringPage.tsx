import { Activity, Bell, Clock } from "lucide-react";
import { NavLink } from "react-router-dom";
import { cn } from "@/lib/utils";

export function MonitoringPage() {
  const tabs = [
    { to: "/monitoring", label: "Overview", icon: Activity, end: true },
    { to: "/monitoring/transactions", label: "Transactions", icon: Clock, end: false },
    { to: "/monitoring/alerts", label: "Alerts", icon: Bell, end: false },
  ];

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-xl font-bold">Monitoring</h1>
        <p className="text-sm text-muted-foreground">
          Transaction execution, performance metrics, and alert configuration.
        </p>
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

      {/* Monitoring Overview — static for now */}
      <div className="glass-panel rounded-[var(--radius)] p-4">
        <p className="text-sm text-muted-foreground">
          Monitoring data will be populated from <code className="text-xs px-1 py-0.5 rounded bg-[hsl(var(--muted)/0.5)]">/api/monitoring/dashboard</code> via TanStack Query.
          The API endpoints are live and returning JSON.
        </p>
      </div>
    </div>
  );
}
