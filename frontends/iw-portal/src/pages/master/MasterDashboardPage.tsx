import { useState } from "react";
import { Search, RefreshCw } from "lucide-react";
import { StatusBadge, inferStatus } from "@/components/ui/status-badge";
import { Sparkline } from "@/components/Sparkline";
import { Button } from "@/components/ui/button";
import { useDevMode } from "@/providers/DevModeProvider";

// TODO: wire to GET /api/master/dashboard
const KPIS = [
  { label: "Active Subscribers", value: "1,284", delta: "+4.6% WoW",  spark: [8,9,9.5,10,10.3,10.8,11.4,12.2,12.4], accent: "border-t-[3px] border-t-[hsl(var(--accent))]" },
  { label: "MRR",                value: "$38,520",delta: "Churn 1.1%", spark: [31,31.2,31.8,32.5,33.4,34.0,35.6,37.2,38.5], accent: "border-t-[3px] border-t-[hsl(var(--success))]" },
  { label: "Portal Engagement",  value: "67%",   delta: "DAU/WAU",    spark: [52,54,58,60,62,61,64,66,67], accent: "border-t-[3px] border-t-[hsl(var(--primary))]" },
  { label: "Exceptions",         value: "3",     delta: "Open issues", spark: [1,2,1,3,2,1,2,3,3], bad: true, accent: "border-t-[3px] border-t-[hsl(var(--warning))]" },
];

const QUEUE = [
  { time: "09:14", domain: "Billing",   entity: "Subscription",  action: "Payment failed",    status: "Error",   owner: "Auto"  },
  { time: "09:08", domain: "CRM",       entity: "Contact sync",  action: "Duplicate merged",  status: "Resolved",owner: "Auto"  },
  { time: "08:55", domain: "Scheduling",entity: "Enrollment",    action: "Sync timeout",      status: "Warn",    owner: "Tom D" },
  { time: "08:30", domain: "Billing",   entity: "Invoice",       action: "Issued",            status: "Active",  owner: "Auto"  },
  { time: "07:15", domain: "CRM",       entity: "Account",       action: "Created",           status: "Active",  owner: "Auto"  },
];

export function MasterDashboardPage() {
  const { devMode } = useDevMode();
  const [qFilter, setQFilter] = useState("");

  const filteredQueue = qFilter.trim()
    ? QUEUE.filter((r) => [r.domain, r.entity, r.action, r.owner].join(" ").toLowerCase().includes(qFilter.toLowerCase()))
    : QUEUE;

  return (
    <div className="space-y-5">
      <div className="flex items-center justify-between flex-wrap gap-3">
        <div>
          <h1 className="text-xl font-semibold">Master Dashboard</h1>
          <p className="text-sm text-muted-foreground">Program health, subscription revenue, content adoption, and operational exceptions.</p>
        </div>
        <div className="flex gap-2">
          <Button size="sm"><span>+ Publish Resource</span></Button>
          <Button size="sm" variant="outline"><RefreshCw className="w-3.5 h-3.5 mr-1.5" />Run Sync</Button>
        </div>
      </div>

      {/* KPI cards */}
      <div className="grid grid-cols-2 lg:grid-cols-4 gap-4">
        {KPIS.map((k) => (
          <section key={k.label} className={`glass-panel rounded-[var(--radius)] p-4 ${k.accent}`}>
            <p className="text-xs text-muted-foreground mb-1">{k.label}</p>
            <div className={`text-2xl font-bold ${k.bad ? "text-[hsl(var(--destructive))]" : ""}`}>{k.value}</div>
            <p className="text-xs text-muted-foreground mt-0.5">{k.delta}</p>
            <div className="mt-2 opacity-80">
              <Sparkline data={k.spark} width={120} height={28} />
            </div>
          </section>
        ))}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-[1fr_280px] gap-5">
        {/* Operational queue */}
        <section className="glass-panel rounded-[var(--radius)] p-4">
          <div className="flex items-center justify-between gap-3 mb-3 flex-wrap">
            <h2 className="text-sm font-semibold">Operational Queue</h2>
            <div className="flex items-center gap-2 px-3 py-1.5 rounded-full border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)] text-xs">
              <Search className="w-3.5 h-3.5 text-muted-foreground" />
              <input
                value={qFilter} onChange={(e) => setQFilter(e.target.value)}
                placeholder="Filter events…"
                className="bg-transparent border-none outline-none text-foreground placeholder:text-muted-foreground w-32"
              />
            </div>
          </div>
          <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
            <div className="grid grid-cols-[auto_auto_1fr_1fr_auto_auto] gap-3 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
              <span>Time</span><span>Domain</span><span>Entity</span><span>Action</span><span>Status</span><span>Owner</span>
            </div>
            {filteredQueue.map((row, i) => (
              <div key={i} className="grid grid-cols-[auto_auto_1fr_1fr_auto_auto] gap-3 px-4 py-3 items-center text-sm">
                <span className="font-mono text-xs text-muted-foreground">{row.time}</span>
                <span className="text-xs font-medium">{row.domain}</span>
                <span className="text-xs truncate">{row.entity}</span>
                <span className="text-xs truncate">{row.action}</span>
                <StatusBadge status={inferStatus(row.status)} label={row.status} />
                <span className="text-xs text-muted-foreground">{row.owner}</span>
              </div>
            ))}
          </div>
        </section>

        {/* Quick actions + dev panel */}
        <div className="space-y-4">
          <section className="glass-panel rounded-[var(--radius)] p-4">
            <h2 className="text-sm font-semibold mb-3">Quick Actions</h2>
            <div className="flex flex-col gap-2">
              {["Publish Resource", "Run Sync Now", "Invite Member", "View Exceptions", "Export Report"].map((a) => (
                <Button key={a} variant="outline" size="sm" className="justify-start text-xs">{a}</Button>
              ))}
            </div>
          </section>
          {devMode && (
            <section className="glass-panel rounded-[var(--radius)] p-4 border-[hsl(var(--warning)/0.3)]">
              <h2 className="text-xs font-semibold text-[hsl(var(--warning))] mb-2">⚡ Dev — API Endpoints</h2>
              <div className="text-xs font-mono text-muted-foreground space-y-1">
                <div>GET /api/master/dashboard</div>
                <div>GET /api/master/kpis</div>
                <div>GET /api/master/queue</div>
                <div>POST /api/master/sync</div>
              </div>
            </section>
          )}
        </div>
      </div>
    </div>
  );
}
