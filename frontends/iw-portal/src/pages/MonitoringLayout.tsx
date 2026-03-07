import { Activity, Bell, Clock, RefreshCw } from "lucide-react";
import { NavLink, Outlet } from "react-router-dom";
import { cn } from "@/lib/utils";
import { useQueryClient } from "@tanstack/react-query";
import { useState } from "react";

const TABS = [
  { to: "/monitoring", label: "Overview", icon: Activity, end: true },
  { to: "/monitoring/transactions", label: "Transactions", icon: Clock, end: false },
  { to: "/monitoring/alerts", label: "Alerts", icon: Bell, end: false },
];

export function MonitoringLayout() {
  const queryClient = useQueryClient();
  const [refreshing, setRefreshing] = useState(false);

  const handleRefresh = () => {
    setRefreshing(true);
    queryClient.invalidateQueries({ queryKey: ["monitoring"] }).finally(() => {
      setTimeout(() => setRefreshing(false), 600);
    });
  };

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
          onClick={handleRefresh}
          disabled={refreshing}
          className="flex items-center gap-1.5 text-xs text-muted-foreground px-2.5 py-1 rounded-full border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)] hover:text-foreground transition-colors cursor-pointer disabled:opacity-50"
        >
          <RefreshCw className={cn("w-3 h-3", refreshing && "animate-spin")} />
          Refresh
        </button>
      </div>

      {/* Tab navigation */}
      <div className="flex gap-1 border-b border-[hsl(var(--border))]">
        {TABS.map((tab) => (
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

      {/* Tab content */}
      <Outlet />
    </div>
  );
}
