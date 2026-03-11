import { useState } from "react";
import { Loader2 } from "lucide-react";
import { StatusBadge } from "@/components/ui/status-badge";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useToast } from "@/providers/ToastProvider";
import { apiFetch } from "@/lib/api";

// TODO: wire to GET/PUT /api/master/settings
const RELEASE_POLICIES = [
  { feature: "Multi-Factor Authentication",  status: "GA",      rollout: "100%",  since: "Jan 2025"  },
  { feature: "Associate Portal",             status: "GA",      rollout: "100%",  since: "Mar 2025"  },
  { feature: "AI Mapping Assistant",         status: "Beta",    rollout: "20%",   since: "Mar 2025"  },
  { feature: "Visual Workflow Builder",      status: "Preview", rollout: "5%",    since: "Apr 2025"  },
  { feature: "OAuth Broker",                status: "Roadmap", rollout: "—",     since: "—"         },
];

const DEFAULT_FORM = {
  programName: "InterWeave Fitness Pro",
  supportEmail: "support@interweave.biz",
  domain: "portal.interweave.biz",
  sessionTimeout: "60",
  maxUsers: "5000",
  timezone: "America/New_York",
  maintenanceWindow: "Sunday 02:00–04:00 UTC",
};

export function TenantSettingsPage() {
  const { showToast } = useToast();
  const [form, setForm] = useState(DEFAULT_FORM);
  const [saving, setSaving] = useState(false);

  const set = (k: keyof typeof form) =>
    (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) =>
      setForm((f) => ({ ...f, [k]: e.target.value }));

  const featureStatus = (s: string) =>
    s === "GA" ? "ok" : s === "Beta" ? "warn" : s === "Preview" ? "info" : "neutral";

  const handleSave = async (e: React.FormEvent) => {
    e.preventDefault();
    setSaving(true);
    try {
      await apiFetch("/api/master/settings", { method: "PUT", body: JSON.stringify(form) });
      showToast("Settings saved", "success");
    } catch (err) {
      showToast(err instanceof Error ? err.message : "Save failed", "error");
    } finally {
      setSaving(false);
    }
  };

  const handleResetToDefaults = () => {
    if (!window.confirm("Reset all settings to defaults?")) return;
    setForm({ ...DEFAULT_FORM });
    showToast("Settings reset to defaults", "success");
  };

  const handleDangerZone = (label: string) => {
    if (!window.confirm(`Confirm: ${label}?`)) return;
    showToast("Operation completed", "success");
  };

  return (
    <div className="space-y-5">
      <div className="flex items-center justify-between flex-wrap gap-3">
        <div>
          <h1 className="text-xl font-semibold">Tenant Settings</h1>
          <p className="text-sm text-muted-foreground">Program-wide configuration, limits, and feature flags.</p>
        </div>
        <div className="flex gap-2">
          <Button size="sm" variant="outline">Export Config</Button>
          <Button size="sm" variant="outline">Audit History</Button>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-[1fr_360px] gap-5">
        {/* General settings */}
        <section className="glass-panel rounded-[var(--radius)] p-4">
          <h2 className="text-sm font-semibold mb-3">Program Configuration</h2>
          <form onSubmit={handleSave}>
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
              <div className="space-y-1.5">
                <Label>Program Name</Label>
                <Input value={form.programName} onChange={set("programName")} />
              </div>
              <div className="space-y-1.5">
                <Label>Support Email</Label>
                <Input type="email" value={form.supportEmail} onChange={set("supportEmail")} />
              </div>
              <div className="space-y-1.5">
                <Label>Portal Domain</Label>
                <Input value={form.domain} onChange={set("domain")} />
              </div>
              <div className="space-y-1.5">
                <Label>Timezone</Label>
                <select value={form.timezone} onChange={set("timezone")}
                  className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-sm rounded-[var(--radius)] px-3 py-2 text-foreground outline-none">
                  {["America/New_York", "America/Chicago", "America/Denver", "America/Los_Angeles", "UTC"].map((tz) => (
                    <option key={tz}>{tz}</option>
                  ))}
                </select>
              </div>
              <div className="space-y-1.5">
                <Label>Session Timeout (min)</Label>
                <Input type="number" value={form.sessionTimeout} onChange={set("sessionTimeout")} min={15} max={480} />
              </div>
              <div className="space-y-1.5">
                <Label>Max Active Users</Label>
                <Input type="number" value={form.maxUsers} onChange={set("maxUsers")} />
              </div>
              <div className="space-y-1.5 sm:col-span-2">
                <Label>Maintenance Window</Label>
                <Input value={form.maintenanceWindow} onChange={set("maintenanceWindow")} />
              </div>
            </div>
            <div className="flex gap-2 mt-3">
              <Button size="sm" type="submit" disabled={saving}>
                {saving ? <Loader2 className="w-3.5 h-3.5 animate-spin mr-1.5" /> : null}
                Save Settings
              </Button>
              <Button size="sm" variant="outline" type="button" onClick={handleResetToDefaults}>Reset to Defaults</Button>
            </div>
          </form>
        </section>

        {/* Quick stats */}
        <div className="space-y-5">
          <section className="glass-panel rounded-[var(--radius)] p-4">
            <h2 className="text-sm font-semibold mb-3">Platform Limits</h2>
            <div className="divide-y divide-[hsl(var(--border))]">
              {[
                { label: "Active subscribers",    value: "1,284 / 5,000" },
                { label: "Storage used",          value: "12.4 GB / 50 GB" },
                { label: "API calls (30d)",       value: "284,210 / 1,000,000" },
                { label: "Email sends (30d)",     value: "4,821 / 25,000" },
                { label: "Connectors enabled",    value: "5 / 10" },
              ].map(({ label, value }) => (
                <div key={label} className="flex items-center justify-between py-2 text-sm">
                  <span className="text-xs text-muted-foreground">{label}</span>
                  <span className="text-xs font-mono font-medium">{value}</span>
                </div>
              ))}
            </div>
          </section>

          <section className="glass-panel rounded-[var(--radius)] p-4">
            <h2 className="text-sm font-semibold mb-3">Danger Zone</h2>
            <div className="space-y-2">
              <Button
                size="sm" variant="outline"
                className="w-full text-[hsl(var(--destructive))] border-[hsl(var(--destructive)/0.4)]"
                onClick={() => handleDangerZone("Purge Analytics Cache")}
              >
                Purge Analytics Cache
              </Button>
              <Button
                size="sm" variant="outline"
                className="w-full text-[hsl(var(--destructive))] border-[hsl(var(--destructive)/0.4)]"
                onClick={() => handleDangerZone("Force Re-sync All Connectors")}
              >
                Force Re-sync All Connectors
              </Button>
              <Button
                size="sm" variant="outline"
                className="w-full text-[hsl(var(--destructive))] border-[hsl(var(--destructive)/0.4)]"
                onClick={() => handleDangerZone("Revoke All Active Sessions")}
              >
                Revoke All Active Sessions
              </Button>
            </div>
          </section>
        </div>
      </div>

      {/* Feature flags / release policy */}
      <section className="glass-panel rounded-[var(--radius)] p-4">
        <h2 className="text-sm font-semibold mb-3">Feature Rollout Policy</h2>
        <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
          <div className="grid grid-cols-[1fr_auto_auto_auto] gap-3 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
            <span>Feature</span><span>Stage</span><span>Rollout</span><span>Since</span>
          </div>
          {RELEASE_POLICIES.map((p) => (
            <div key={p.feature} className="grid grid-cols-[1fr_auto_auto_auto] gap-3 px-4 py-2.5 items-center text-sm">
              <span className="text-xs font-medium">{p.feature}</span>
              <StatusBadge status={featureStatus(p.status)} label={p.status} />
              <span className="text-xs font-mono">{p.rollout}</span>
              <span className="text-xs text-muted-foreground">{p.since}</span>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
}
