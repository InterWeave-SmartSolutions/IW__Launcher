import { CreditCard } from "lucide-react";
import { StatusBadge, inferStatus } from "@/components/ui/status-badge";
import { Button } from "@/components/ui/button";

// TODO: wire to GET /api/associate/billing (Stripe integration)
const PLAN = { name: "Associate Monthly", price: "$29.99/mo", status: "Active", renews: "Apr 1, 2026" };

const INVOICES = [
  { date: "Mar 1, 2026",  id: "INV-2043", amount: "$29.99", status: "Paid"    },
  { date: "Feb 1, 2026",  id: "INV-1987", amount: "$29.99", status: "Paid"    },
  { date: "Jan 1, 2026",  id: "INV-1924", amount: "$29.99", status: "Paid"    },
  { date: "Dec 1, 2025",  id: "INV-1867", amount: "$29.99", status: "Paid"    },
  { date: "Nov 1, 2025",  id: "INV-1802", amount: "$29.99", status: "Past-due" },
];

export function BillingPage() {
  return (
    <div className="space-y-5">
      <div>
        <h1 className="text-xl font-semibold">Billing</h1>
        <p className="text-sm text-muted-foreground">Manage your subscription, view invoices, and update payment method.</p>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-5">
        {/* Current plan */}
        <section className="glass-panel rounded-[var(--radius)] p-4 flex flex-col gap-3">
          <h2 className="text-sm font-semibold">Current Plan</h2>
          <div>
            <div className="text-2xl font-bold">{PLAN.name}</div>
            <div className="text-lg font-semibold text-[hsl(var(--primary))] mt-1">{PLAN.price}</div>
          </div>
          <div className="flex items-center gap-2">
            <StatusBadge status={inferStatus(PLAN.status)} label={PLAN.status} />
            <span className="text-xs text-muted-foreground">Renews {PLAN.renews}</span>
          </div>
          <div className="flex gap-2 mt-auto">
            <Button size="sm" className="flex-1">Upgrade</Button>
            <Button size="sm" variant="outline" className="text-[hsl(var(--destructive))]">Cancel</Button>
          </div>
        </section>

        {/* Invoice history */}
        <section className="glass-panel rounded-[var(--radius)] p-4 lg:col-span-2">
          <h2 className="text-sm font-semibold mb-3">Invoice History</h2>
          <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
            <div className="grid grid-cols-4 gap-3 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
              <span>Date</span><span>Invoice</span><span>Amount</span><span>Status</span>
            </div>
            {INVOICES.map((inv) => (
              <div key={inv.id} className="grid grid-cols-4 gap-3 px-4 py-3 items-center text-sm">
                <span className="text-muted-foreground text-xs">{inv.date}</span>
                <span className="font-mono text-xs">{inv.id}</span>
                <span className="font-medium">{inv.amount}</span>
                <StatusBadge status={inferStatus(inv.status)} label={inv.status} />
              </div>
            ))}
          </div>
        </section>

        {/* Payment method */}
        <section className="glass-panel rounded-[var(--radius)] p-4 lg:col-span-3">
          <h2 className="text-sm font-semibold mb-3">Payment Method</h2>
          <div className="flex items-center gap-4 p-3 border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] bg-[hsl(var(--muted)/0.2)]">
            <CreditCard className="w-5 h-5 text-muted-foreground shrink-0" />
            <div>
              <p className="text-sm font-medium">Visa ending in 4242</p>
              <p className="text-xs text-muted-foreground">Expires 09/2027</p>
            </div>
            <Button size="sm" variant="outline" className="ml-auto">Update</Button>
          </div>
          <p className="text-xs text-muted-foreground mt-2">
            Payments processed securely. {/* TODO: Stripe integration */}
          </p>
        </section>
      </div>
    </div>
  );
}
