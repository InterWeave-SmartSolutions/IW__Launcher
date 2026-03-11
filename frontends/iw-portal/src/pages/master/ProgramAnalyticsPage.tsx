import { StatusBadge } from "@/components/ui/status-badge";
import { Button } from "@/components/ui/button";

// TODO: wire to GET /api/master/analytics?period=7d
const TOP_CONTENT = [
  { resource: "Recruiting Playbook (v3)",   views: 312, saves: 87,  completion: "78%" },
  { resource: "Monthly KPI Template",       views: 241, saves: 103, completion: "91%" },
  { resource: "Operations Checklist",       views: 198, saves: 61,  completion: "84%" },
  { resource: "Brand Guidelines v2",        views: 144, saves: 28,  completion: "55%" },
  { resource: "Cash Flow Projection",       views: 97,  saves: 44,  completion: "69%" },
];

const DEMAND_SIGNALS = [
  { query: "payroll templates",   count: 34, zeroResults: true  },
  { query: "marketing calendar",  count: 28, zeroResults: false },
  { query: "staff onboarding",    count: 22, zeroResults: false },
  { query: "lease negotiation",   count: 17, zeroResults: true  },
  { query: "social ads budget",   count: 14, zeroResults: false },
];

const FUNNEL = [
  { step: "Login",              users: 1284, conversion: "100%",   notes: "Active subscribers" },
  { step: "Portal visit (7d)", users: 860,  conversion: "67%",    notes: "DAU/WAU proxy"      },
  { step: "Resource opened",   users: 512,  conversion: "59.5%",  notes: "Engaged users"      },
  { step: "Resource saved",    users: 287,  conversion: "56.1%",  notes: "High-intent"        },
  { step: "Webinar registered",users: 143,  conversion: "27.9%",  notes: "Engaged + active"   },
];

export function ProgramAnalyticsPage() {
  return (
    <div className="space-y-5">
      <div className="flex items-center justify-between flex-wrap gap-3">
        <div>
          <h1 className="text-xl font-semibold">Analytics</h1>
          <p className="text-sm text-muted-foreground">Content adoption, search demand signals, and engagement funnel.</p>
        </div>
        <div className="flex gap-2">
          <Button size="sm" variant="outline">Export CSV</Button>
          <Button size="sm" variant="outline">Save Report</Button>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-5">
        {/* Top content */}
        <section className="glass-panel rounded-[var(--radius)] p-4">
          <h2 className="text-sm font-semibold mb-3">Top Content (7d)</h2>
          <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
            <div className="grid grid-cols-[1fr_auto_auto_auto] gap-3 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
              <span>Resource</span><span>Views</span><span>Saves</span><span>Completion</span>
            </div>
            {TOP_CONTENT.map((r, i) => (
              <div key={i} className="grid grid-cols-[1fr_auto_auto_auto] gap-3 px-4 py-2.5 items-center text-sm">
                <span className="text-xs truncate">{r.resource}</span>
                <span className="text-xs font-mono">{r.views}</span>
                <span className="text-xs font-mono">{r.saves}</span>
                <span className="text-xs font-medium">{r.completion}</span>
              </div>
            ))}
          </div>
        </section>

        {/* Search demand */}
        <section className="glass-panel rounded-[var(--radius)] p-4">
          <h2 className="text-sm font-semibold mb-3">Search Demand Signals</h2>
          <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
            <div className="grid grid-cols-[1fr_auto_auto] gap-3 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
              <span>Query</span><span>Count</span><span>Zero Results</span>
            </div>
            {DEMAND_SIGNALS.map((s, i) => (
              <div key={i} className="grid grid-cols-[1fr_auto_auto] gap-3 px-4 py-2.5 items-center text-sm">
                <span className="text-xs font-mono">{s.query}</span>
                <span className="text-xs">{s.count}</span>
                <StatusBadge status={s.zeroResults ? "bad" : "ok"} label={s.zeroResults ? "Gap" : "OK"} />
              </div>
            ))}
          </div>
        </section>
      </div>

      {/* Engagement funnel */}
      <section className="glass-panel rounded-[var(--radius)] p-4">
        <h2 className="text-sm font-semibold mb-3">Engagement Funnel</h2>
        <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
          <div className="grid grid-cols-4 gap-3 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
            <span>Step</span><span>Users</span><span>Conversion</span><span>Notes</span>
          </div>
          {FUNNEL.map((f, i) => (
            <div key={i} className="grid grid-cols-4 gap-3 px-4 py-3 items-center text-sm">
              <span className="font-medium text-sm">{f.step}</span>
              <span className="font-mono text-sm">{f.users.toLocaleString()}</span>
              <span className="font-semibold text-[hsl(var(--primary))]">{f.conversion}</span>
              <span className="text-xs text-muted-foreground">{f.notes}</span>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
}
