import { useState } from "react";
import { HelpCircle } from "lucide-react";
import { StatusBadge, inferStatus } from "@/components/ui/status-badge";
import { EmptyState } from "@/components/ui/empty-state";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";

// TODO: wire to GET/POST /api/associate/support/tickets
const MY_TICKETS = [
  { id: "T-1041", category: "Billing",   created: "Mar 8",  status: "Open",     subject: "Invoice not reflecting discount" },
  { id: "T-1028", category: "Resources", created: "Mar 2",  status: "Resolved", subject: "Can't download Operations Checklist" },
  { id: "T-1015", category: "Account",   created: "Feb 25", status: "Closed",   subject: "Change company name" },
];

const CATEGORIES = ["Billing", "Resources", "Account", "Technical", "General"];
const PRIORITIES  = ["Normal", "High", "Urgent"];

export function SupportPage() {
  const [form, setForm] = useState({ category: CATEGORIES[0]!, priority: PRIORITIES[0]!, message: "" });
  const [submitted, setSubmitted] = useState(false);

  const set = (k: keyof typeof form) => (e: React.ChangeEvent<HTMLSelectElement | HTMLTextAreaElement>) =>
    setForm((f) => ({ ...f, [k]: e.target.value }));

  return (
    <div className="space-y-5">
      <div>
        <h1 className="text-xl font-semibold">Support</h1>
        <p className="text-sm text-muted-foreground">View your tickets or submit a new request.</p>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-5">
        {/* My tickets */}
        <section className="glass-panel rounded-[var(--radius)] p-4">
          <h2 className="text-sm font-semibold mb-3">My Tickets</h2>
          {MY_TICKETS.length === 0 ? (
            <EmptyState icon={HelpCircle} title="No tickets yet" description="Submit a request below and we'll get back to you." />
          ) : (
            <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
              <div className="grid grid-cols-[auto_1fr_auto_auto] gap-3 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
                <span>ID</span><span>Subject</span><span>Category</span><span>Status</span>
              </div>
              {MY_TICKETS.map((t) => (
                <div key={t.id} className="grid grid-cols-[auto_1fr_auto_auto] gap-3 px-4 py-3 items-center text-sm">
                  <span className="font-mono text-xs text-muted-foreground">{t.id}</span>
                  <span className="min-w-0 truncate">{t.subject}</span>
                  <span className="text-xs text-muted-foreground">{t.category}</span>
                  <StatusBadge status={inferStatus(t.status)} label={t.status} />
                </div>
              ))}
            </div>
          )}
        </section>

        {/* Create ticket */}
        <section className="glass-panel rounded-[var(--radius)] p-4">
          <h2 className="text-sm font-semibold mb-3">
            {submitted ? "Ticket Submitted" : "Create a Ticket"}
          </h2>
          {submitted ? (
            <div className="flex flex-col items-center gap-3 py-8 text-center">
              <HelpCircle className="w-10 h-10 text-[hsl(var(--success))]" />
              <p className="text-sm">Your ticket has been submitted. We'll respond within 1 business day.</p>
              <Button variant="outline" size="sm" onClick={() => setSubmitted(false)}>New Ticket</Button>
            </div>
          ) : (
            <div className="space-y-3">
              <div className="grid grid-cols-2 gap-3">
                <div className="space-y-1.5">
                  <Label>Category</Label>
                  <select value={form.category} onChange={set("category")}
                    className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-sm rounded-[var(--radius)] px-3 py-2 text-foreground outline-none">
                    {CATEGORIES.map((c) => <option key={c}>{c}</option>)}
                  </select>
                </div>
                <div className="space-y-1.5">
                  <Label>Priority</Label>
                  <select value={form.priority} onChange={set("priority")}
                    className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-sm rounded-[var(--radius)] px-3 py-2 text-foreground outline-none">
                    {PRIORITIES.map((p) => <option key={p}>{p}</option>)}
                  </select>
                </div>
              </div>
              <div className="space-y-1.5">
                <Label>Message</Label>
                <textarea
                  value={form.message} onChange={set("message")} rows={5}
                  placeholder="Describe your issue in detail…"
                  className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-sm rounded-[var(--radius)] px-3 py-2 text-foreground outline-none resize-vertical"
                />
              </div>
              <Button onClick={() => setSubmitted(true)} disabled={!form.message.trim()}>Submit Ticket</Button>
            </div>
          )}
        </section>
      </div>
    </div>
  );
}
