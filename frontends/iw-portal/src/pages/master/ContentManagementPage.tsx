import { useState } from "react";
import { Loader2 } from "lucide-react";
import { StatusBadge, inferStatus } from "@/components/ui/status-badge";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useToast } from "@/providers/ToastProvider";
import { apiFetch } from "@/lib/api";

// TODO: wire to GET/POST /api/master/content
const RESOURCES = [
  { id: "R-101", title: "Recruiting Playbook (v3)",   type: "Guide",    area: "HR",     roles: "Owner",     version: "3.0", release: "Mar 1",  status: "Active"    },
  { id: "R-097", title: "Monthly KPI Template",        type: "Template", area: "Finance",roles: "Owner",     version: "2.1", release: "Feb 15", status: "Active"    },
  { id: "R-088", title: "Operations Checklist",        type: "Checklist",area: "Ops",    roles: "All",       version: "1.4", release: "Feb 1",  status: "Active"    },
  { id: "R-076", title: "Social Media Calendar 2025",  type: "Template", area: "Mktg",   roles: "Staff",     version: "1.0", release: "Jan 10", status: "Deprecated"},
  { id: "R-071", title: "Brand Guidelines v2",         type: "Guide",    area: "Mktg",   roles: "Staff",     version: "2.0", release: "Jan 3",  status: "Draft"     },
];

const RELEASE_MODES = ["Immediate", "Scheduled"];
const EMPTY_FORM = { resourceId: "", version: "", mode: RELEASE_MODES[0]!, date: "", notes: "" };

export function ContentManagementPage() {
  const { showToast } = useToast();
  const [form, setForm] = useState(EMPTY_FORM);
  const [saving, setSaving] = useState(false);
  const set = (k: keyof typeof form) => (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) =>
    setForm((f) => ({ ...f, [k]: e.target.value }));

  const submitContent = async (action: "publish" | "draft" | "deprecate") => {
    if (action !== "deprecate" && (!form.resourceId || !form.version)) {
      showToast("Resource ID and version are required", "error");
      return;
    }
    setSaving(true);
    try {
      await apiFetch("/api/master/content", { method: "POST", body: JSON.stringify({ ...form, action }) });
      if (action === "publish") {
        showToast("Resource published", "success");
        setForm(EMPTY_FORM);
      } else if (action === "draft") {
        showToast("Draft saved", "success");
      } else {
        showToast("Resource deprecated", "success");
      }
    } catch (err) {
      showToast(err instanceof Error ? err.message : "Operation failed", "error");
    } finally {
      setSaving(false);
    }
  };

  const handlePublish = async (e: React.FormEvent) => {
    e.preventDefault();
    await submitContent("publish");
  };

  return (
    <div className="space-y-5">
      <div className="flex items-center justify-between flex-wrap gap-3">
        <div>
          <h1 className="text-xl font-semibold">Content Library</h1>
          <p className="text-sm text-muted-foreground">Publish, version, and manage program resources.</p>
        </div>
        <div className="flex gap-2">
          <Button size="sm">+ New Resource</Button>
          <Button size="sm" variant="outline">Taxonomy</Button>
        </div>
      </div>

      {/* Resource inventory */}
      <section className="glass-panel rounded-[var(--radius)] p-4">
        <h2 className="text-sm font-semibold mb-3">Resource Inventory</h2>
        <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
          <div className="grid grid-cols-[auto_1fr_auto_auto_auto_auto_auto_auto] gap-2 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
            <span>ID</span><span>Title</span><span>Type</span><span>Area</span><span>Roles</span><span>Ver</span><span>Release</span><span>Status</span>
          </div>
          {RESOURCES.map((r) => (
            <div key={r.id} className="grid grid-cols-[auto_1fr_auto_auto_auto_auto_auto_auto] gap-2 px-4 py-2.5 items-center text-sm">
              <span className="font-mono text-xs text-muted-foreground">{r.id}</span>
              <span className="text-xs font-medium truncate">{r.title}</span>
              <span className="text-xs text-muted-foreground">{r.type}</span>
              <span className="text-xs text-muted-foreground">{r.area}</span>
              <span className="text-xs text-muted-foreground">{r.roles}</span>
              <span className="text-xs font-mono">{r.version}</span>
              <span className="text-xs text-muted-foreground">{r.release}</span>
              <StatusBadge status={inferStatus(r.status)} label={r.status} />
            </div>
          ))}
        </div>
      </section>

      {/* Publish workflow */}
      <section className="glass-panel rounded-[var(--radius)] p-4 max-w-2xl">
        <h2 className="text-sm font-semibold mb-3">Publish / Version Workflow</h2>
        <form onSubmit={handlePublish}>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
            <div className="space-y-1.5">
              <Label>Resource ID</Label>
              <Input value={form.resourceId} onChange={set("resourceId")} placeholder="R-101" />
            </div>
            <div className="space-y-1.5">
              <Label>New Version</Label>
              <Input value={form.version} onChange={set("version")} placeholder="3.1" />
            </div>
            <div className="space-y-1.5">
              <Label>Release Mode</Label>
              <select value={form.mode} onChange={set("mode")}
                className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-sm rounded-[var(--radius)] px-3 py-2 text-foreground outline-none">
                {RELEASE_MODES.map((m) => <option key={m}>{m}</option>)}
              </select>
            </div>
            {form.mode === "Scheduled" && (
              <div className="space-y-1.5">
                <Label>Release Date</Label>
                <Input value={form.date} onChange={set("date")} type="date" />
              </div>
            )}
            <div className="space-y-1.5 sm:col-span-2">
              <Label>Release Notes</Label>
              <textarea value={form.notes} onChange={set("notes")} rows={3}
                placeholder="What changed in this version…"
                className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-sm rounded-[var(--radius)] px-3 py-2 text-foreground outline-none resize-vertical" />
            </div>
          </div>
          <div className="flex gap-2 mt-3">
            <Button size="sm" type="submit" disabled={saving}>
              {saving ? <Loader2 className="w-3.5 h-3.5 animate-spin mr-1.5" /> : null}
              Publish
            </Button>
            <Button size="sm" variant="outline" type="button" disabled={saving} onClick={() => submitContent("draft")}>
              Save Draft
            </Button>
            <Button size="sm" variant="outline" type="button" className="text-[hsl(var(--destructive))]" disabled={saving} onClick={() => submitContent("deprecate")}>
              Deprecate
            </Button>
          </div>
        </form>
      </section>
    </div>
  );
}
