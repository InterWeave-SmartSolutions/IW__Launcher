import { useState } from "react";
import { Loader2 } from "lucide-react";
import { StatusBadge, inferStatus } from "@/components/ui/status-badge";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { useToast } from "@/providers/ToastProvider";
import { apiFetch } from "@/lib/api";

// TODO: wire to GET /api/master/support/tickets, POST /api/master/support/tickets/:id/reply
const INITIAL_TICKETS = [
  { id: "TK-041", subject: "Cannot access webinar replay",  org: "Sunrise Fitness",  category: "Access",   priority: "High",   age: "2h",  status: "Open"       },
  { id: "TK-040", subject: "Billing discrepancy March",     org: "Peaks Wellness",   category: "Billing",  priority: "High",   age: "5h",  status: "In-progress" },
  { id: "TK-039", subject: "Resource search not returning", org: "Momentum Fit",     category: "Content",  priority: "Medium", age: "1d",  status: "Open"        },
  { id: "TK-038", subject: "Staff user invite not sent",    org: "Elevate Health",   category: "Users",    priority: "Medium", age: "1d",  status: "In-progress" },
  { id: "TK-037", subject: "Portal slow on mobile",        org: "City Athletics",   category: "Platform", priority: "Low",    age: "2d",  status: "Open"        },
  { id: "TK-036", subject: "Integration sync failure",      org: "Sunrise Fitness",  category: "Integrations", priority: "High", age: "3d", status: "Resolved"  },
];

const CATEGORIES = ["Access", "Billing", "Content", "Users", "Platform", "Integrations", "Other"];
const PRIORITIES  = ["Low", "Medium", "High", "Critical"];

export function SupportQueuePage() {
  const { showToast } = useToast();
  const [tickets, setTickets] = useState(INITIAL_TICKETS);
  const [selected, setSelected] = useState<string | null>(null);
  const [replyText, setReplyText] = useState("");
  const [replying, setReplying] = useState(false);
  const [creating, setCreating] = useState(false);
  const [form, setForm] = useState({ category: CATEGORIES[0]!, priority: PRIORITIES[1]!, message: "" });
  const set = (k: keyof typeof form) =>
    (e: React.ChangeEvent<HTMLSelectElement | HTMLTextAreaElement>) =>
      setForm((f) => ({ ...f, [k]: e.target.value }));

  const ticket = tickets.find((t) => t.id === selected);

  const priorityStatus = (p: string) =>
    p === "Critical" || p === "High" ? "bad" : p === "Medium" ? "warn" : "neutral";

  const handleSendReply = async () => {
    setReplying(true);
    try {
      await apiFetch("/api/master/support/tickets/reply", {
        method: "POST",
        body: JSON.stringify({ ticketId: selected, message: replyText }),
      });
      showToast("Reply sent", "success");
      setReplyText("");
    } catch (err) {
      showToast(err instanceof Error ? err.message : "Reply failed", "error");
    } finally {
      setReplying(false);
    }
  };

  const handleEscalate = () => {
    showToast("Ticket escalated", "success");
  };

  const handleResolve = async () => {
    setReplying(true);
    try {
      await apiFetch("/api/master/support/tickets/resolve", {
        method: "POST",
        body: JSON.stringify({ ticketId: selected }),
      });
      showToast("Ticket resolved", "success");
      setTickets((prev) => prev.filter((t) => t.id !== selected));
      setSelected(null);
    } catch (err) {
      showToast(err instanceof Error ? err.message : "Resolve failed", "error");
    } finally {
      setReplying(false);
    }
  };

  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();
    setCreating(true);
    try {
      await apiFetch("/api/master/support/tickets", { method: "POST", body: JSON.stringify(form) });
      showToast("Ticket TK-042 created", "success");
      setForm({ category: CATEGORIES[0]!, priority: PRIORITIES[1]!, message: "" });
    } catch (err) {
      showToast(err instanceof Error ? err.message : "Create failed", "error");
    } finally {
      setCreating(false);
    }
  };

  return (
    <div className="space-y-5">
      <div className="flex items-center justify-between flex-wrap gap-3">
        <div>
          <h1 className="text-xl font-semibold">Support Queue</h1>
          <p className="text-sm text-muted-foreground">Open tickets, intake, and escalation management.</p>
        </div>
        <div className="flex gap-2">
          <Button size="sm" variant="outline">Export Queue</Button>
          <Button size="sm" variant="outline">SLA Report</Button>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-[1fr_360px] gap-5">
        {/* Ticket list */}
        <section className="glass-panel rounded-[var(--radius)] p-4">
          <h2 className="text-sm font-semibold mb-3">Open Tickets</h2>
          <div className="border border-[hsl(var(--border))] rounded-[calc(var(--radius)-4px)] overflow-hidden divide-y divide-[hsl(var(--border))]">
            <div className="grid grid-cols-[auto_1fr_auto_auto_auto_auto] gap-2 px-4 py-2 text-xs text-muted-foreground bg-[hsl(var(--muted)/0.3)]">
              <span>ID</span><span>Subject / Org</span><span>Cat.</span><span>Pri.</span><span>Age</span><span>Status</span>
            </div>
            {tickets.map((t) => (
              <div
                key={t.id}
                onClick={() => setSelected(t.id === selected ? null : t.id)}
                className={`grid grid-cols-[auto_1fr_auto_auto_auto_auto] gap-2 px-4 py-2.5 items-center text-sm cursor-pointer transition-colors ${
                  selected === t.id ? "bg-[hsl(var(--primary)/0.08)]" : "hover:bg-[hsl(var(--muted)/0.2)]"
                }`}
              >
                <span className="font-mono text-xs text-muted-foreground">{t.id}</span>
                <div>
                  <p className="text-xs font-medium truncate">{t.subject}</p>
                  <p className="text-xs text-muted-foreground">{t.org}</p>
                </div>
                <span className="text-xs text-muted-foreground hidden sm:block">{t.category}</span>
                <StatusBadge status={priorityStatus(t.priority)} label={t.priority} />
                <span className="text-xs text-muted-foreground">{t.age}</span>
                <StatusBadge status={inferStatus(t.status)} label={t.status} />
              </div>
            ))}
          </div>
        </section>

        {/* Detail / reply panel */}
        <div className="space-y-5">
          {ticket ? (
            <section className="glass-panel rounded-[var(--radius)] p-4 space-y-3">
              <div>
                <h2 className="text-sm font-semibold">{ticket.id} — {ticket.subject}</h2>
                <p className="text-xs text-muted-foreground mt-0.5">{ticket.org} · {ticket.category} · {ticket.priority} priority</p>
              </div>
              <div className="bg-[hsl(var(--muted)/0.2)] rounded-[calc(var(--radius)-4px)] p-3 text-xs text-muted-foreground min-h-16">
                {/* TODO: load thread messages from API */}
                <em>No messages yet — load from /api/master/support/tickets/{ticket.id}/messages</em>
              </div>
              <div className="space-y-1.5">
                <Label>Reply</Label>
                <textarea
                  rows={4}
                  value={replyText}
                  onChange={(e) => setReplyText(e.target.value)}
                  placeholder="Type your reply…"
                  className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-sm rounded-[var(--radius)] px-3 py-2 text-foreground outline-none resize-none"
                />
              </div>
              <div className="flex gap-2">
                <Button size="sm" onClick={handleSendReply} disabled={replying}>
                  {replying ? <Loader2 className="w-3.5 h-3.5 animate-spin mr-1.5" /> : null}
                  Send Reply
                </Button>
                <Button size="sm" variant="outline" onClick={handleEscalate} disabled={replying}>Escalate</Button>
                <Button size="sm" variant="outline" onClick={handleResolve} disabled={replying}>Resolve</Button>
              </div>
            </section>
          ) : (
            <section className="glass-panel rounded-[var(--radius)] p-4 flex items-center justify-center min-h-40">
              <p className="text-sm text-muted-foreground">Select a ticket to view details.</p>
            </section>
          )}

          {/* Intake form */}
          <section className="glass-panel rounded-[var(--radius)] p-4">
            <h2 className="text-sm font-semibold mb-3">Create Ticket (on behalf of org)</h2>
            <form onSubmit={handleCreate} className="space-y-3">
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
              <div className="space-y-1.5">
                <Label>Description</Label>
                <textarea value={form.message} onChange={set("message")} rows={3}
                  placeholder="Describe the issue…"
                  className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-sm rounded-[var(--radius)] px-3 py-2 text-foreground outline-none resize-none" />
              </div>
              <Button size="sm" type="submit" disabled={creating}>
                {creating ? <Loader2 className="w-3.5 h-3.5 animate-spin mr-1.5" /> : null}
                Create Ticket
              </Button>
            </form>
          </section>
        </div>
      </div>
    </div>
  );
}
