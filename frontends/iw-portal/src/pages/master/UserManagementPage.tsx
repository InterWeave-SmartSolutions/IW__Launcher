import { StatusBadge, inferStatus } from "@/components/ui/status-badge";
import { Button } from "@/components/ui/button";

// TODO: wire to GET /api/master/users, GET /api/master/roles
const ROLES = [
  { name: "Owner",        persona: "Business owner",   entitlements: "Full access, billing",          security: "MFA required" },
  { name: "Staff",        persona: "Employee/manager", entitlements: "Resources, webinars",            security: "Password only" },
  { name: "Program Admin",persona: "IW staff",         entitlements: "Master console, all portals",   security: "MFA required" },
  { name: "Read-only",    persona: "Advisor/guest",    entitlements: "Resources only",                 security: "Password only" },
];

const RECENT_ACTIVITY = [
  { ts: "09:14", user: "jane.smith@sunrise.com",    event: "Login",            result: "Success", ip: "73.143.x.x" },
  { ts: "09:02", user: "tom.davis@peaks.com",       event: "Password change",  result: "Success", ip: "98.21.x.x"  },
  { ts: "08:47", user: "maria.l@momentum.com",      event: "Login",            result: "Failed",  ip: "185.x.x.x"  },
  { ts: "08:30", user: "admin@interweave.biz",      event: "User created",     result: "Success", ip: "73.143.x.x" },
  { ts: "07:55", user: "carlos.m@elevate.com",      event: "Resource saved",   result: "Success", ip: "71.90.x.x"  },
];

export function UserManagementPage() {
  return (
    <div className="space-y-5">
      <div className="flex items-center justify-between flex-wrap gap-3">
        <div>
          <h1 className="text-xl font-semibold">Users & Roles</h1>
          <p className="text-sm text-muted-foreground">Manage members, role definitions, and recent activity.</p>
        </div>
        <div className="flex gap-2">
          <Button size="sm">+ Add User</Button>
          <Button size="sm" variant="outline">Manage Roles</Button>
        </div>
      </div>

      {/* Role definitions */}
      <section className="glass-panel rounded-[var(--radius)] p-4">
        <h2 className="text-sm font-semibold mb-3">Role Definitions</h2>
        <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
          <div className="grid grid-cols-4 gap-3 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
            <span>Role</span><span>Typical Persona</span><span>Entitlements</span><span>Security Controls</span>
          </div>
          {ROLES.map((r) => (
            <div key={r.name} className="grid grid-cols-4 gap-3 px-4 py-3 text-sm items-start">
              <span className="font-medium">{r.name}</span>
              <span className="text-muted-foreground text-xs">{r.persona}</span>
              <span className="text-xs">{r.entitlements}</span>
              <StatusBadge status={r.security.includes("MFA") ? "ok" : "neutral"} label={r.security} />
            </div>
          ))}
        </div>
      </section>

      {/* Recent activity */}
      <section className="glass-panel rounded-[var(--radius)] p-4">
        <h2 className="text-sm font-semibold mb-3">Recent User Activity</h2>
        <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
          <div className="grid grid-cols-[auto_1fr_auto_auto_auto] gap-3 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
            <span>Time</span><span>User</span><span>Event</span><span>Result</span><span>IP</span>
          </div>
          {RECENT_ACTIVITY.map((a, i) => (
            <div key={i} className="grid grid-cols-[auto_1fr_auto_auto_auto] gap-3 px-4 py-3 items-center text-sm">
              <span className="font-mono text-xs text-muted-foreground">{a.ts}</span>
              <span className="text-xs truncate">{a.user}</span>
              <span className="text-xs">{a.event}</span>
              <StatusBadge status={inferStatus(a.result)} label={a.result} />
              <span className="text-xs text-muted-foreground font-mono hidden sm:block">{a.ip}</span>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
}
