import { useState } from "react";
import { BookOpen, Search } from "lucide-react";
import { StatusBadge, inferStatus } from "@/components/ui/status-badge";
import { EmptyState } from "@/components/ui/empty-state";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";

// TODO: wire to GET /api/associate/resources?area=&type=&role=&q=
const ALL_RESOURCES = [
  { id: 1,  title: "Recruiting Playbook (v3)",        area: "HR / Recruiting",      type: "Guide",    role: "Owner",   status: "New"      },
  { id: 2,  title: "Monthly KPI Template",             area: "Finance / Reporting",  type: "Template", role: "Owner",   status: "Updated"  },
  { id: 3,  title: "Operations Checklist",             area: "Operations",           type: "Checklist",role: "All",     status: "Active"   },
  { id: 4,  title: "Social Media Calendar",            area: "Marketing",            type: "Template", role: "Staff",   status: "Active"   },
  { id: 5,  title: "New Hire Onboarding Kit",          area: "HR / Recruiting",      type: "Guide",    role: "Owner",   status: "Active"   },
  { id: 6,  title: "Cash Flow Projection Model",       area: "Finance / Reporting",  type: "Template", role: "Owner",   status: "Active"   },
  { id: 7,  title: "Customer Feedback Survey",         area: "Operations",           type: "Template", role: "All",     status: "Active"   },
  { id: 8,  title: "Brand Guidelines v2",              area: "Marketing",            type: "Guide",    role: "Staff",   status: "Updated"  },
];

const AREAS  = ["All Areas",  "HR / Recruiting", "Finance / Reporting", "Operations", "Marketing"];
const TYPES  = ["All Types",  "Guide", "Template", "Checklist", "Video"];
const ROLES  = ["All Roles",  "Owner", "Staff", "All"];

export function ResourceLibraryPage() {
  const [q, setQ] = useState("");
  const [area, setArea] = useState("All Areas");
  const [type, setType] = useState("All Types");
  const [role, setRole] = useState("All Roles");

  const results = ALL_RESOURCES.filter((r) => {
    const matchQ    = !q.trim() || r.title.toLowerCase().includes(q.toLowerCase()) || r.area.toLowerCase().includes(q.toLowerCase());
    const matchArea = area === "All Areas" || r.area === area;
    const matchType = type === "All Types" || r.type === type;
    const matchRole = role === "All Roles" || r.role === role || r.role === "All";
    return matchQ && matchArea && matchType && matchRole;
  });

  return (
    <div className="space-y-5">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-xl font-semibold">Resource Library</h1>
          <p className="text-sm text-muted-foreground">Browse and save program resources for your role.</p>
        </div>
      </div>

      {/* Filters */}
      <section className="glass-panel rounded-[var(--radius)] p-4">
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-3">
          <div className="relative lg:col-span-1 sm:col-span-2">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-3.5 h-3.5 text-muted-foreground" />
            <Input
              value={q}
              onChange={(e) => setQ(e.target.value)}
              placeholder="Search resources…"
              className="pl-9"
            />
          </div>
          {[
            { value: area, set: setArea, options: AREAS },
            { value: type, set: setType, options: TYPES },
            { value: role, set: setRole, options: ROLES },
          ].map((f, i) => (
            <select
              key={i}
              value={f.value}
              onChange={(e) => f.set(e.target.value)}
              className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-sm rounded-[var(--radius)] px-3 py-2 text-foreground outline-none focus:ring-1 focus:ring-[hsl(var(--primary)/0.5)]"
            >
              {f.options.map((o) => <option key={o}>{o}</option>)}
            </select>
          ))}
        </div>
      </section>

      {/* Results grid */}
      {results.length === 0 ? (
        <EmptyState icon={BookOpen} title="No resources found" description="Try adjusting your filters or search term." />
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
          {results.map((r) => (
            <section key={r.id} className="glass-panel rounded-[var(--radius)] p-4 flex flex-col gap-3">
              <div className="flex items-start justify-between gap-2">
                <div>
                  <p className="text-sm font-semibold">{r.title}</p>
                  <p className="text-xs text-muted-foreground mt-0.5">{r.area} • {r.type}</p>
                </div>
                <StatusBadge status={inferStatus(r.status)} label={r.status} />
              </div>
              <div className="flex items-center gap-2 text-xs text-muted-foreground">
                <StatusBadge status="neutral" label={r.role} />
              </div>
              <div className="flex gap-2 mt-auto">
                <Button size="sm" className="flex-1">Open</Button>
                <Button size="sm" variant="outline">Save</Button>
              </div>
            </section>
          ))}
        </div>
      )}
    </div>
  );
}
