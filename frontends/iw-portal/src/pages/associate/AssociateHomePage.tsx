import { Link } from "react-router-dom";
import { BookOpen, ClipboardCheck, Video, HelpCircle, ArrowRight } from "lucide-react";
import { HeroSection } from "@/components/ui/hero-section";
import { StatusBadge } from "@/components/ui/status-badge";
import { Button } from "@/components/ui/button";

// TODO: wire to /api/associate/home (summary, saved resources, upcoming webinars)
const RECENT_RESOURCES = [
  { id: 1, title: "Recruiting Playbook (v3)",   area: "HR / Recruiting",    type: "Guide"     },
  { id: 2, title: "Monthly KPI Template",        area: "Finance / Reporting",type: "Template"  },
  { id: 3, title: "Operations Checklist",        area: "Operations",         type: "Checklist" },
];

const UPCOMING_WEBINARS = [
  { id: 1, date: "Mar 18", topic: "Social Growth Strategies",    registered: false },
  { id: 2, date: "Mar 25", topic: "QuickBooks Automation 101",   registered: true  },
];

const QUICK_ACTIONS = [
  { label: "Start Business Checkup", to: "/associate/intake",    icon: ClipboardCheck, variant: "default"  as const },
  { label: "Browse Library",         to: "/associate/resources", icon: BookOpen,       variant: "outline"  as const },
  { label: "View Webinars",          to: "/associate/webinars",  icon: Video,          variant: "outline"  as const },
  { label: "Get Support",            to: "/associate/support",   icon: HelpCircle,     variant: "outline"  as const },
];

export function AssociateHomePage() {
  return (
    <div className="space-y-5">
      <HeroSection
        title="Welcome back 👋"
        subtitle="Your associate workspace — resources, checkups, webinars, and support."
        actions={
          <>
            <Button asChild size="sm"><Link to="/associate/intake">Start Business Checkup</Link></Button>
            <Button asChild size="sm" variant="outline"><Link to="/associate/resources">Browse Library</Link></Button>
          </>
        }
      />

      {/* Quick actions */}
      <div className="grid grid-cols-2 sm:grid-cols-4 gap-3">
        {QUICK_ACTIONS.map((a) => (
          <Link
            key={a.to}
            to={a.to}
            className="glass-panel rounded-[var(--radius)] p-4 flex flex-col items-center gap-2 text-center hover:border-[hsl(var(--primary)/0.4)] transition-colors group"
          >
            <a.icon className="w-5 h-5 text-muted-foreground group-hover:text-[hsl(var(--primary))] transition-colors" />
            <span className="text-xs font-medium">{a.label}</span>
          </Link>
        ))}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-5">
        {/* Recent resources */}
        <section className="glass-panel rounded-[var(--radius)] p-4 lg:col-span-2">
          <div className="flex items-center justify-between mb-3">
            <h2 className="text-sm font-semibold">Recent Resources</h2>
            <Link to="/associate/resources" className="text-xs text-[hsl(var(--primary))] flex items-center gap-1 hover:underline">
              View all <ArrowRight className="w-3 h-3" />
            </Link>
          </div>
          <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
            {RECENT_RESOURCES.map((r) => (
              <div key={r.id} className="flex items-center gap-3 px-4 py-3">
                <div className="flex-1 min-w-0">
                  <p className="text-sm font-medium truncate">{r.title}</p>
                  <p className="text-xs text-muted-foreground">{r.area}</p>
                </div>
                <StatusBadge status="neutral" label={r.type} />
                <Button asChild size="sm" variant="ghost" className="text-xs h-7 px-2">
                  <Link to="/associate/resources">Open</Link>
                </Button>
              </div>
            ))}
          </div>
        </section>

        {/* Sidebar: subscription + upcoming */}
        <div className="space-y-4">
          <section className="glass-panel rounded-[var(--radius)] p-4">
            <h2 className="text-sm font-semibold mb-1">Subscription</h2>
            <p className="text-xs text-muted-foreground mb-3">Your current plan status.</p>
            <div className="flex items-center justify-between">
              <span className="text-2xl font-bold">Active</span>
              <StatusBadge status="ok" label="Paid" />
            </div>
            <p className="text-xs text-muted-foreground mt-2">Renews Apr 1, 2026</p>
            <Button asChild size="sm" variant="outline" className="w-full mt-3">
              <Link to="/associate/billing">Manage Billing</Link>
            </Button>
          </section>

          <section className="glass-panel rounded-[var(--radius)] p-4">
            <h2 className="text-sm font-semibold mb-3">Upcoming Webinars</h2>
            <div className="space-y-2">
              {UPCOMING_WEBINARS.map((w) => (
                <div key={w.id} className="flex items-center gap-3">
                  <span className="text-xs text-muted-foreground w-12 shrink-0">{w.date}</span>
                  <span className="text-xs flex-1 min-w-0 truncate">{w.topic}</span>
                  <StatusBadge status={w.registered ? "ok" : "neutral"} label={w.registered ? "Registered" : "Open"} />
                </div>
              ))}
            </div>
            <Button asChild size="sm" variant="outline" className="w-full mt-3">
              <Link to="/associate/webinars">View All</Link>
            </Button>
          </section>
        </div>
      </div>
    </div>
  );
}
