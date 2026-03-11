import { StatusBadge, inferStatus } from "@/components/ui/status-badge";
import { Button } from "@/components/ui/button";

// TODO: wire to GET /api/master/subscriptions (Stripe integration)
const PLANS = [
  { name: "Associate Monthly", price: "$29.99/mo", status: "Active" },
  { name: "Associate Annual",  price: "$299/yr",   status: "Active" },
  { name: "Staff Add-on",      price: "$9.99/mo",  status: "Active" },
];

const EXCEPTIONS = [
  { subscription: "sunrise@gym.com",   customer: "Sunrise Fitness",  gateway: "Stripe",     attempt: "Mar 10", retry: "Mar 13", status: "Error"   },
  { subscription: "peaks@wellness.com",customer: "Peaks Wellness",   gateway: "Stripe",     attempt: "Mar 8",  retry: "Mar 11", status: "Warn"    },
  { subscription: "momentum@fit.com",  customer: "Momentum Fit",     gateway: "Auth.net",   attempt: "Mar 5",  retry: "—",      status: "Resolved"},
];

const ENTITLEMENT_STATES = [
  { state: "Active",      portalAccess: "Full",    content: "All roles",  notifications: "All" },
  { state: "Past-due",    portalAccess: "Limited", content: "Essentials", notifications: "Billing only" },
  { state: "Delinquent",  portalAccess: "Read-only",content: "None",     notifications: "None" },
  { state: "Canceled",    portalAccess: "None",    content: "None",      notifications: "None" },
];

export function SubscriptionsPage() {
  return (
    <div className="space-y-5">
      <div className="flex items-center justify-between flex-wrap gap-3">
        <div>
          <h1 className="text-xl font-semibold">Subscriptions</h1>
          <p className="text-sm text-muted-foreground">Plan management, payment exceptions, and entitlement enforcement.</p>
        </div>
        <div className="flex gap-2">
          <Button size="sm">+ Create Plan</Button>
          <Button size="sm" variant="outline">Export Reconciliation</Button>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-[280px_1fr] gap-5">
        {/* Plans */}
        <section className="glass-panel rounded-[var(--radius)] p-4">
          <h2 className="text-sm font-semibold mb-3">Plans</h2>
          <div className="divide-y divide-[hsl(var(--border))]">
            {PLANS.map((p) => (
              <div key={p.name} className="py-3 flex items-center justify-between gap-3">
                <div>
                  <p className="text-sm font-medium">{p.name}</p>
                  <p className="text-xs text-muted-foreground">{p.price}</p>
                </div>
                <StatusBadge status={inferStatus(p.status)} label={p.status} />
              </div>
            ))}
          </div>
        </section>

        {/* Payment exceptions */}
        <section className="glass-panel rounded-[var(--radius)] p-4">
          <h2 className="text-sm font-semibold mb-3">Payment Exceptions</h2>
          <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
            <div className="grid grid-cols-[1fr_1fr_auto_auto_auto_auto_auto] gap-2 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
              <span>Subscription</span><span>Customer</span><span>Gateway</span><span>Last Attempt</span><span>Next Retry</span><span>Status</span><span></span>
            </div>
            {EXCEPTIONS.map((e, i) => (
              <div key={i} className="grid grid-cols-[1fr_1fr_auto_auto_auto_auto_auto] gap-2 px-4 py-3 items-center text-sm">
                <span className="text-xs truncate">{e.subscription}</span>
                <span className="text-xs truncate">{e.customer}</span>
                <span className="text-xs text-muted-foreground">{e.gateway}</span>
                <span className="text-xs text-muted-foreground">{e.attempt}</span>
                <span className="text-xs text-muted-foreground">{e.retry}</span>
                <StatusBadge status={inferStatus(e.status)} label={e.status} />
                <Button size="sm" variant="ghost" className="h-7 px-2 text-xs">Retry</Button>
              </div>
            ))}
          </div>
        </section>
      </div>

      {/* Entitlement enforcement */}
      <section className="glass-panel rounded-[var(--radius)] p-4">
        <h2 className="text-sm font-semibold mb-3">Entitlement Enforcement</h2>
        <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
          <div className="grid grid-cols-4 gap-3 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
            <span>State</span><span>Portal Access</span><span>Content</span><span>Notifications</span>
          </div>
          {ENTITLEMENT_STATES.map((e) => (
            <div key={e.state} className="grid grid-cols-4 gap-3 px-4 py-3 items-center text-sm">
              <StatusBadge status={inferStatus(e.state)} label={e.state} />
              <span className="text-xs">{e.portalAccess}</span>
              <span className="text-xs">{e.content}</span>
              <span className="text-xs text-muted-foreground">{e.notifications}</span>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
}
