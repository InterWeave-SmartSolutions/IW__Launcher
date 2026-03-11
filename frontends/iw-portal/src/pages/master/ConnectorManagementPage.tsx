import { useState } from "react";
import { StatusBadge, inferStatus } from "@/components/ui/status-badge";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";

// TODO: wire to GET /api/master/integrations, PUT /api/master/integrations/policy
const CONNECTORS = [
  { name: "CRM",        system: "Salesforce",    auth: "OAuth 2.0",          direction: "Bi-directional", lastSync: "09:08", status: "Active" },
  { name: "Payments",   system: "Stripe",        auth: "API Key + Webhooks", direction: "Inbound events", lastSync: "09:14", status: "Active" },
  { name: "Payments",   system: "Authorize.net", auth: "API Login/Txn Key",  direction: "Inbound events", lastSync: "23:02", status: "Active" },
  { name: "Scheduling", system: "Mindbody",      auth: "OAuth 2.0",          direction: "Optional",       lastSync: "—",     status: "Warn"   },
  { name: "Scheduling", system: "LeagueApps",    auth: "API Token",          direction: "Optional",       lastSync: "—",     status: "Warn"   },
];

const FLOWS   = ["Portal → CRM (Account/Contact)", "Payments → Portal (Subscription Status)", "Portal → Scheduling (Registrations)"];
const VAL_MODES = ["Strict (reject on missing required fields)", "Lenient (default values)"];

export function ConnectorManagementPage() {
  const [flow, setFlow] = useState(FLOWS[0]!);
  const [mode, setMode] = useState(VAL_MODES[0]!);
  const [required, setRequired] = useState("email, companyId, subscriptionStatus");

  return (
    <div className="space-y-5">
      <div className="flex items-center justify-between flex-wrap gap-3">
        <div>
          <h1 className="text-xl font-semibold">Integrations</h1>
          <p className="text-sm text-muted-foreground">Connector inventory, mapping policies, and retry queue.</p>
        </div>
        <div className="flex gap-2">
          <Button size="sm">+ Add Connector</Button>
          <Button size="sm" variant="outline">Test Endpoint</Button>
        </div>
      </div>

      {/* Connector inventory */}
      <section className="glass-panel rounded-[var(--radius)] p-4">
        <h2 className="text-sm font-semibold mb-3">Connector Inventory</h2>
        <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
          <div className="grid grid-cols-[auto_1fr_auto_auto_auto_auto] gap-3 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
            <span>Connector</span><span>System</span><span>Auth</span><span>Direction</span><span>Last Sync</span><span>Status</span>
          </div>
          {CONNECTORS.map((c, i) => (
            <div key={i} className="grid grid-cols-[auto_1fr_auto_auto_auto_auto] gap-3 px-4 py-3 items-center text-sm">
              <span className="text-xs font-medium">{c.name}</span>
              <span className="text-xs">{c.system}</span>
              <span className="text-xs text-muted-foreground hidden sm:block">{c.auth}</span>
              <span className="text-xs text-muted-foreground hidden md:block">{c.direction}</span>
              <span className="font-mono text-xs text-muted-foreground">{c.lastSync}</span>
              <StatusBadge status={inferStatus(c.status)} label={c.status} />
            </div>
          ))}
        </div>
      </section>

      {/* Mapping policy */}
      <section className="glass-panel rounded-[var(--radius)] p-4 max-w-2xl">
        <h2 className="text-sm font-semibold mb-3">Mapping / Validation Policy</h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
          <div className="space-y-1.5">
            <Label>Flow</Label>
            <select value={flow} onChange={(e) => setFlow(e.target.value)}
              className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-sm rounded-[var(--radius)] px-3 py-2 text-foreground outline-none">
              {FLOWS.map((f) => <option key={f}>{f}</option>)}
            </select>
          </div>
          <div className="space-y-1.5">
            <Label>Validation Mode</Label>
            <select value={mode} onChange={(e) => setMode(e.target.value)}
              className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-sm rounded-[var(--radius)] px-3 py-2 text-foreground outline-none">
              {VAL_MODES.map((m) => <option key={m}>{m}</option>)}
            </select>
          </div>
          <div className="space-y-1.5 sm:col-span-2">
            <Label>Required Fields (comma-separated)</Label>
            <textarea value={required} onChange={(e) => setRequired(e.target.value)} rows={2}
              className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-sm rounded-[var(--radius)] px-3 py-2 text-foreground outline-none resize-none" />
          </div>
        </div>
        <div className="flex gap-2 mt-3">
          <Button size="sm">Save Policy</Button>
          <Button size="sm" variant="outline">View Runbook</Button>
        </div>
      </section>
    </div>
  );
}
