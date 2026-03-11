import { useState } from "react";
import { StatusBadge, inferStatus } from "@/components/ui/status-badge";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";

// TODO: wire to GET /api/master/audit, GET /api/master/security/events
const SECURITY_EVENTS = [
  { ts: "09:14", user: "jane.smith@sunrise.com",   event: "Login",            ip: "73.143.x.x",  result: "Success" },
  { ts: "09:02", user: "tom.davis@peaks.com",       event: "Password change",  ip: "98.21.x.x",   result: "Success" },
  { ts: "08:47", user: "maria.l@momentum.com",      event: "Login",            ip: "185.x.x.x",   result: "Failed"  },
  { ts: "08:30", user: "admin@interweave.biz",      event: "User created",     ip: "73.143.x.x",  result: "Success" },
  { ts: "07:55", user: "carlos.m@elevate.com",      event: "MFA enroll",       ip: "71.90.x.x",   result: "Success" },
];

const AUDIT_LOG = [
  { ts: "09:10", actor: "admin@interweave.biz",     action: "Content published",  target: "R-101 Recruiting Playbook", severity: "Info"   },
  { ts: "08:55", actor: "admin@interweave.biz",     action: "Plan created",       target: "Associate Annual",          severity: "Info"   },
  { ts: "08:30", actor: "admin@interweave.biz",     action: "User created",       target: "jane.smith@sunrise.com",    severity: "Info"   },
  { ts: "07:40", actor: "system",                   action: "Payment retry",      target: "sunrise@gym.com",           severity: "Warn"   },
  { ts: "06:22", actor: "system",                   action: "Login brute-force",  target: "185.x.x.x",                severity: "Error"  },
];

const MFA_ROLES = [
  { role: "Owner",        required: "Required",    enrolled: 34,  total: 34,  rate: "100%" },
  { role: "Staff",        required: "Optional",    enrolled: 71,  total: 198, rate: "35.8%" },
  { role: "Program Admin",required: "Required",    enrolled: 4,   total: 4,   rate: "100%" },
  { role: "Read-only",    required: "Not required",enrolled: 0,   total: 12,  rate: "—" },
];

export function AuditSecurityPage() {
  const [filter, setFilter] = useState("");

  const filteredAudit = AUDIT_LOG.filter(
    (a) => !filter || a.actor.includes(filter) || a.action.toLowerCase().includes(filter.toLowerCase()),
  );

  return (
    <div className="space-y-5">
      <div className="flex items-center justify-between flex-wrap gap-3">
        <div>
          <h1 className="text-xl font-semibold">Audit & Security</h1>
          <p className="text-sm text-muted-foreground">MFA policy, security events, and full audit trail.</p>
        </div>
        <div className="flex gap-2">
          <Button size="sm" variant="outline">Export Audit Log</Button>
          <Button size="sm" variant="outline">Security Report</Button>
        </div>
      </div>

      {/* MFA Policy */}
      <section className="glass-panel rounded-[var(--radius)] p-4">
        <h2 className="text-sm font-semibold mb-3">MFA Enrollment by Role</h2>
        <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
          <div className="grid grid-cols-[1fr_auto_auto_auto_auto] gap-3 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
            <span>Role</span><span>Policy</span><span>Enrolled</span><span>Total</span><span>Rate</span>
          </div>
          {MFA_ROLES.map((r) => (
            <div key={r.role} className="grid grid-cols-[1fr_auto_auto_auto_auto] gap-3 px-4 py-2.5 items-center text-sm">
              <span className="text-xs font-medium">{r.role}</span>
              <StatusBadge status={r.required === "Required" ? "ok" : r.required === "Optional" ? "neutral" : "info"} label={r.required} />
              <span className="text-xs font-mono">{r.enrolled}</span>
              <span className="text-xs font-mono text-muted-foreground">{r.total}</span>
              <span className="text-xs font-semibold">{r.rate}</span>
            </div>
          ))}
        </div>
        <div className="flex gap-2 mt-3">
          <Button size="sm">Edit MFA Policy</Button>
          <Button size="sm" variant="outline">Send Enrollment Reminders</Button>
        </div>
      </section>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-5">
        {/* Security events */}
        <section className="glass-panel rounded-[var(--radius)] p-4">
          <h2 className="text-sm font-semibold mb-3">Recent Security Events</h2>
          <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
            <div className="grid grid-cols-[auto_1fr_auto_auto] gap-2 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
              <span>Time</span><span>User</span><span>Event</span><span>Result</span>
            </div>
            {SECURITY_EVENTS.map((e, i) => (
              <div key={i} className="grid grid-cols-[auto_1fr_auto_auto] gap-2 px-4 py-2.5 items-center text-sm">
                <span className="font-mono text-xs text-muted-foreground">{e.ts}</span>
                <span className="text-xs truncate">{e.user}</span>
                <span className="text-xs">{e.event}</span>
                <StatusBadge status={inferStatus(e.result)} label={e.result} />
              </div>
            ))}
          </div>
        </section>

        {/* Audit log */}
        <section className="glass-panel rounded-[var(--radius)] p-4">
          <div className="flex items-center justify-between mb-3 gap-2">
            <h2 className="text-sm font-semibold">Audit Log</h2>
            <div className="space-y-1.5 flex-1 max-w-48">
              <Label className="sr-only">Filter</Label>
              <input
                value={filter}
                onChange={(e) => setFilter(e.target.value)}
                placeholder="Filter by actor or action…"
                className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-xs rounded-[var(--radius)] px-3 py-1.5 text-foreground outline-none"
              />
            </div>
          </div>
          <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
            <div className="grid grid-cols-[auto_1fr_auto] gap-2 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
              <span>Time</span><span>Action / Target</span><span>Sev.</span>
            </div>
            {filteredAudit.map((a, i) => (
              <div key={i} className="grid grid-cols-[auto_1fr_auto] gap-2 px-4 py-2.5 items-start text-sm">
                <span className="font-mono text-xs text-muted-foreground pt-0.5">{a.ts}</span>
                <div>
                  <p className="text-xs font-medium">{a.action}</p>
                  <p className="text-xs text-muted-foreground truncate">{a.target}</p>
                </div>
                <StatusBadge status={inferStatus(a.severity)} label={a.severity} />
              </div>
            ))}
          </div>
        </section>
      </div>
    </div>
  );
}
