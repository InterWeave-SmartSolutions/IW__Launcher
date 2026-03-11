import { useState } from "react";
import { ClipboardCheck } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

// TODO: wire to POST /api/associate/intake
const CHALLENGES = ["Staffing", "Marketing", "Operations", "Finance", "Technology", "Customer Retention"];
const HORIZONS   = ["30 days", "90 days", "6 months", "12 months"];

export function BusinessIntakePage() {
  const [form, setForm] = useState({
    centerName: "", enrollment: "", challenge: CHALLENGES[0]!, horizon: HORIZONS[1]!, goals: "",
  });
  const [submitted, setSubmitted] = useState(false);

  const set = (k: keyof typeof form) => (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) =>
    setForm((f) => ({ ...f, [k]: e.target.value }));

  if (submitted) {
    return (
      <div className="flex flex-col items-center justify-center py-24 gap-4 text-center">
        <div className="w-14 h-14 rounded-full bg-[hsl(var(--success)/0.15)] flex items-center justify-center">
          <ClipboardCheck className="w-7 h-7 text-[hsl(var(--success))]" />
        </div>
        <h2 className="text-lg font-semibold">Checkup submitted!</h2>
        <p className="text-sm text-muted-foreground max-w-xs">
          Your advisor will review your responses and reach out within 2 business days.
        </p>
        <Button variant="outline" onClick={() => setSubmitted(false)}>Start a New Checkup</Button>
      </div>
    );
  }

  return (
    <div className="space-y-5 max-w-2xl">
      <div>
        <h1 className="text-xl font-semibold">Business Checkup</h1>
        <p className="text-sm text-muted-foreground">Help us understand your current situation so we can point you to the right resources.</p>
      </div>

      <section className="glass-panel rounded-[var(--radius)] p-5 space-y-4">
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div className="space-y-1.5">
            <Label>Center / Business Name</Label>
            <Input value={form.centerName} onChange={set("centerName")} placeholder="Sunrise Fitness" />
          </div>
          <div className="space-y-1.5">
            <Label>Monthly Enrollment (approx.)</Label>
            <Input value={form.enrollment} onChange={set("enrollment")} placeholder="150" type="number" />
          </div>
          <div className="space-y-1.5">
            <Label>Primary Challenge</Label>
            <select
              value={form.challenge} onChange={set("challenge")}
              className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-sm rounded-[var(--radius)] px-3 py-2 text-foreground outline-none"
            >
              {CHALLENGES.map((c) => <option key={c}>{c}</option>)}
            </select>
          </div>
          <div className="space-y-1.5">
            <Label>Goal Horizon</Label>
            <select
              value={form.horizon} onChange={set("horizon")}
              className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-sm rounded-[var(--radius)] px-3 py-2 text-foreground outline-none"
            >
              {HORIZONS.map((h) => <option key={h}>{h}</option>)}
            </select>
          </div>
          <div className="space-y-1.5 sm:col-span-2">
            <Label>Top Goals (describe in your own words)</Label>
            <textarea
              value={form.goals} onChange={set("goals")}
              rows={4}
              placeholder="e.g., Reduce staff turnover, increase member retention to 85%, launch a second location…"
              className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-sm rounded-[var(--radius)] px-3 py-2 text-foreground outline-none resize-vertical"
            />
          </div>
        </div>
        <div className="flex gap-2 pt-2">
          <Button onClick={() => setSubmitted(true)} disabled={!form.centerName.trim()}>Submit Checkup</Button>
          <Button variant="outline" onClick={() => setForm({ centerName: "", enrollment: "", challenge: CHALLENGES[0]!, horizon: HORIZONS[1]!, goals: "" })}>
            Clear
          </Button>
        </div>
      </section>
    </div>
  );
}
