import { useState } from "react";
import { Search } from "lucide-react";
import { StatusBadge } from "@/components/ui/status-badge";
import { EmptyState } from "@/components/ui/empty-state";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

// TODO: wire to GET /api/associate/resources/search?q=&area=&type=&role=
const AREA_OPTIONS = ["All", "HR / Recruiting", "Finance / Reporting", "Operations", "Marketing"];
const TYPE_OPTIONS = ["All", "Guide", "Template", "Checklist", "Video"];
const ROLE_OPTIONS = ["All", "Owner", "Staff"];

const MOCK_RESULTS = [
  { id: 1, title: "Recruiting Playbook (v3)",      type: "Guide",    area: "HR / Recruiting",     status: "Active" },
  { id: 2, title: "New Hire Onboarding Kit",        type: "Guide",    area: "HR / Recruiting",     status: "Active" },
  { id: 3, title: "Monthly KPI Template",           type: "Template", area: "Finance / Reporting", status: "Updated" },
  { id: 4, title: "Cash Flow Projection Model",     type: "Template", area: "Finance / Reporting", status: "Active" },
  { id: 5, title: "Operations Checklist",           type: "Checklist",area: "Operations",          status: "Active" },
];

export function SearchPage() {
  const [q, setQ] = useState("");
  const [area, setArea] = useState("All");
  const [type, setType] = useState("All");
  const [role, setRole] = useState("All");
  const [searched, setSearched] = useState(false);

  const results = searched ? MOCK_RESULTS.filter((r) => {
    const matchQ    = !q.trim() || r.title.toLowerCase().includes(q.toLowerCase());
    const matchArea = area === "All" || r.area === area;
    const matchType = type === "All" || r.type === type;
    return matchQ && matchArea && matchType;
  }) : [];

  return (
    <div className="space-y-5 max-w-3xl">
      <div>
        <h1 className="text-xl font-semibold">Search</h1>
        <p className="text-sm text-muted-foreground">Find resources, templates, and guides across the program library.</p>
      </div>

      <section className="glass-panel rounded-[var(--radius)] p-4 space-y-3">
        <div className="space-y-1.5">
          <Label>Keyword</Label>
          <Input value={q} onChange={(e) => setQ(e.target.value)} placeholder="e.g. recruiting, cash flow, marketing…"
            onKeyDown={(e) => e.key === "Enter" && setSearched(true)} />
        </div>
        <div className="grid grid-cols-1 sm:grid-cols-3 gap-3">
          {[
            { label: "Operational Area", value: area, set: setArea, opts: AREA_OPTIONS },
            { label: "Resource Type",    value: type, set: setType, opts: TYPE_OPTIONS },
            { label: "Role Relevance",   value: role, set: setRole, opts: ROLE_OPTIONS },
          ].map((f) => (
            <div key={f.label} className="space-y-1.5">
              <Label>{f.label}</Label>
              <select value={f.value} onChange={(e) => f.set(e.target.value)}
                className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-sm rounded-[var(--radius)] px-3 py-2 text-foreground outline-none">
                {f.opts.map((o) => <option key={o}>{o}</option>)}
              </select>
            </div>
          ))}
        </div>
        <Button onClick={() => setSearched(true)}><Search className="w-4 h-4 mr-2" />Search</Button>
      </section>

      {searched && (
        <section className="glass-panel rounded-[var(--radius)] p-4">
          <h2 className="text-sm font-semibold mb-3">Results {results.length > 0 && <span className="text-muted-foreground font-normal">({results.length})</span>}</h2>
          {results.length === 0 ? (
            <EmptyState icon={Search} title="No results found" description="Try a different keyword or broaden your filters." />
          ) : (
            <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
              <div className="grid grid-cols-[1fr_auto_auto_auto] gap-3 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
                <span>Title</span><span>Type</span><span>Area</span><span></span>
              </div>
              {results.map((r) => (
                <div key={r.id} className="grid grid-cols-[1fr_auto_auto_auto] gap-3 px-4 py-3 items-center text-sm">
                  <span className="font-medium truncate">{r.title}</span>
                  <StatusBadge status="neutral" label={r.type} />
                  <span className="text-xs text-muted-foreground hidden sm:block">{r.area}</span>
                  <Button size="sm" variant="ghost" className="h-7 px-2 text-xs">Open</Button>
                </div>
              ))}
            </div>
          )}
        </section>
      )}
    </div>
  );
}
