import { useState } from "react";
import { Loader2 } from "lucide-react";
import { StatusBadge } from "@/components/ui/status-badge";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { useToast } from "@/providers/ToastProvider";
import { apiFetch } from "@/lib/api";

// TODO: wire to GET/PUT /api/master/notifications/templates, GET/PUT /api/master/notifications/preferences
const TEMPLATES = [
  { id: "T-01", name: "Welcome Email",           trigger: "User created",         channel: "Email", status: "Active"   },
  { id: "T-02", name: "Payment Failed",          trigger: "Payment exception",    channel: "Email", status: "Active"   },
  { id: "T-03", name: "Subscription Renewed",    trigger: "Renewal success",      channel: "Email", status: "Active"   },
  { id: "T-04", name: "Webinar Reminder",        trigger: "24h before event",     channel: "Email", status: "Active"   },
  { id: "T-05", name: "New Resource Published",  trigger: "Content publish",      channel: "Email", status: "Draft"    },
  { id: "T-06", name: "MFA Enrollment Prompt",   trigger: "7d after user create", channel: "Email", status: "Active"   },
  { id: "T-07", name: "Payment Recovered",       trigger: "Retry success",        channel: "Email", status: "Active"   },
  { id: "T-08", name: "Slack Ops Alert",         trigger: "Error-level event",    channel: "Slack", status: "Active"   },
];

const PREFS = [
  { segment: "Active subscribers",  welcome: true,  renewal: true,  failed: true,  content: true,  webinar: true  },
  { segment: "Past-due",            welcome: false, renewal: true,  failed: true,  content: false, webinar: false },
  { segment: "Delinquent",          welcome: false, renewal: false, failed: true,  content: false, webinar: false },
  { segment: "Staff users",         welcome: true,  renewal: false, failed: false, content: true,  webinar: true  },
];

const CHANNELS = ["Email", "Slack", "In-app"];

export function NotificationTemplatesPage() {
  const { showToast } = useToast();
  const [selected, setSelected] = useState<string | null>(null);
  const [channel, setChannel] = useState(CHANNELS[0]!);
  const [savingTemplate, setSavingTemplate] = useState(false);
  const [savingPrefs, setSavingPrefs] = useState(false);

  const template = TEMPLATES.find((t) => t.id === selected);

  const handleSaveTemplate = async () => {
    setSavingTemplate(true);
    try {
      await apiFetch("/api/master/notifications", { method: "PUT", body: JSON.stringify({ id: selected, channel }) });
      showToast("Template saved", "success");
    } catch (err) {
      showToast(err instanceof Error ? err.message : "Save failed", "error");
    } finally {
      setSavingTemplate(false);
    }
  };

  const handleSavePreferences = async () => {
    setSavingPrefs(true);
    try {
      await apiFetch("/api/master/notifications/preferences", { method: "PUT", body: JSON.stringify({ prefs: PREFS }) });
      showToast("Preferences saved", "success");
    } catch (err) {
      showToast(err instanceof Error ? err.message : "Save failed", "error");
    } finally {
      setSavingPrefs(false);
    }
  };

  return (
    <div className="space-y-5">
      <div className="flex items-center justify-between flex-wrap gap-3">
        <div>
          <h1 className="text-xl font-semibold">Notification Templates</h1>
          <p className="text-sm text-muted-foreground">Message templates, preference center, and delivery channels.</p>
        </div>
        <div className="flex gap-2">
          <Button size="sm">+ New Template</Button>
          <Button size="sm" variant="outline">Test Delivery</Button>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-[1fr_340px] gap-5">
        {/* Template list */}
        <section className="glass-panel rounded-[var(--radius)] p-4">
          <h2 className="text-sm font-semibold mb-3">Templates</h2>
          <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
            <div className="grid grid-cols-[auto_1fr_auto_auto_auto] gap-3 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
              <span>ID</span><span>Name</span><span>Trigger</span><span>Channel</span><span>Status</span>
            </div>
            {TEMPLATES.map((t) => (
              <div
                key={t.id}
                onClick={() => setSelected(t.id === selected ? null : t.id)}
                className={`grid grid-cols-[auto_1fr_auto_auto_auto] gap-3 px-4 py-2.5 items-center text-sm cursor-pointer transition-colors ${
                  selected === t.id ? "bg-[hsl(var(--primary)/0.08)]" : "hover:bg-[hsl(var(--muted)/0.2)]"
                }`}
              >
                <span className="font-mono text-xs text-muted-foreground">{t.id}</span>
                <span className="text-xs font-medium">{t.name}</span>
                <span className="text-xs text-muted-foreground hidden md:block">{t.trigger}</span>
                <StatusBadge status="info" label={t.channel} />
                <StatusBadge status={t.status === "Active" ? "ok" : "neutral"} label={t.status} />
              </div>
            ))}
          </div>
        </section>

        {/* Template editor panel */}
        <section className="glass-panel rounded-[var(--radius)] p-4">
          {template ? (
            <div className="space-y-3">
              <h2 className="text-sm font-semibold">{template.name}</h2>
              <div className="space-y-1.5">
                <Label>Channel</Label>
                <select
                  value={channel}
                  onChange={(e) => setChannel(e.target.value)}
                  className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-sm rounded-[var(--radius)] px-3 py-2 text-foreground outline-none"
                >
                  {CHANNELS.map((c) => <option key={c}>{c}</option>)}
                </select>
              </div>
              <div className="space-y-1.5">
                <Label>Subject</Label>
                <input
                  defaultValue={`[InterWeave] ${template.name}`}
                  className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-sm rounded-[var(--radius)] px-3 py-2 text-foreground outline-none"
                />
              </div>
              <div className="space-y-1.5">
                <Label>Body (Markdown / HTML)</Label>
                <textarea
                  rows={6}
                  defaultValue={`Hi {{firstName}},\n\nThis is a notification regarding your InterWeave account.\n\n— The InterWeave Team`}
                  className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-sm rounded-[var(--radius)] px-3 py-2 text-foreground outline-none resize-none font-mono"
                />
              </div>
              <div className="flex gap-2">
                <Button size="sm" onClick={handleSaveTemplate} disabled={savingTemplate}>
                  {savingTemplate ? <Loader2 className="w-3.5 h-3.5 animate-spin mr-1.5" /> : null}
                  Save Template
                </Button>
                <Button size="sm" variant="outline">Preview</Button>
              </div>
            </div>
          ) : (
            <div className="flex flex-col items-center justify-center h-full min-h-40 text-center gap-2">
              <p className="text-sm text-muted-foreground">Select a template to edit.</p>
            </div>
          )}
        </section>
      </div>

      {/* Preference center */}
      <section className="glass-panel rounded-[var(--radius)] p-4">
        <h2 className="text-sm font-semibold mb-3">Preference Center (by segment)</h2>
        <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
          <div className="grid grid-cols-6 gap-3 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
            <span>Segment</span><span>Welcome</span><span>Renewal</span><span>Failed</span><span>Content</span><span>Webinar</span>
          </div>
          {PREFS.map((p) => (
            <div key={p.segment} className="grid grid-cols-6 gap-3 px-4 py-2.5 items-center text-sm">
              <span className="text-xs font-medium">{p.segment}</span>
              {[p.welcome, p.renewal, p.failed, p.content, p.webinar].map((v, i) => (
                <StatusBadge key={i} status={v ? "ok" : "neutral"} label={v ? "On" : "Off"} />
              ))}
            </div>
          ))}
        </div>
        <div className="flex gap-2 mt-3">
          <Button size="sm" onClick={handleSavePreferences} disabled={savingPrefs}>
            {savingPrefs ? <Loader2 className="w-3.5 h-3.5 animate-spin mr-1.5" /> : null}
            Save Preferences
          </Button>
        </div>
      </section>
    </div>
  );
}
