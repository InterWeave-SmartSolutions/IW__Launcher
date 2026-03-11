import { useState } from "react";
import { Video, Loader2 } from "lucide-react";

import { EmptyState } from "@/components/ui/empty-state";
import { Button } from "@/components/ui/button";
import { useToast } from "@/providers/ToastProvider";
import { apiFetch } from "@/lib/api";

// TODO: wire to GET /api/associate/webinars
const UPCOMING_DATA = [
  { id: 1, date: "Mar 18, 2026", topic: "Social Growth Strategies",      host: "Jane Smith",    duration: "60 min",  spots: 24  },
  { id: 2, date: "Mar 25, 2026", topic: "QuickBooks Automation 101",     host: "Tom Davis",     duration: "45 min",  spots: 8   },
  { id: 3, date: "Apr 3, 2026",  topic: "Recruiting in a Tight Market",  host: "Maria Lopez",   duration: "90 min",  spots: 40  },
];

const REPLAYS = [
  { id: 1, date: "Feb 28, 2026", topic: "Q1 Planning Workshop",          length: "1h 12m",  views: 183 },
  { id: 2, date: "Feb 14, 2026", topic: "AI Tools for Small Business",   length: "47m",     views: 312 },
  { id: 3, date: "Jan 30, 2026", topic: "Pricing Strategy Deep Dive",    length: "1h 05m",  views: 241 },
  { id: 4, date: "Jan 16, 2026", topic: "Operations Efficiency Bootcamp",length: "2h 01m",  views: 97  },
];

export function WebinarsPage() {
  const { showToast } = useToast();
  const [registering, setRegistering] = useState<number | null>(null);
  const [registered, setRegistered] = useState<Set<number>>(new Set());

  const handleRegister = async (w: { id: number; topic: string }) => {
    setRegistering(w.id);
    try {
      await apiFetch("/api/associate/webinars/register", { method: "POST", body: JSON.stringify({ webinarId: w.id }) });
      setRegistered((prev) => new Set([...prev, w.id]));
      showToast("Registered for " + w.topic, "success");
    } catch (e) {
      showToast(e instanceof Error ? e.message : "Registration failed", "error");
    } finally {
      setRegistering(null);
    }
  };

  return (
    <div className="space-y-5">
      <div>
        <h1 className="text-xl font-semibold">Webinars</h1>
        <p className="text-sm text-muted-foreground">Upcoming live sessions and recorded replays.</p>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-5">
        {/* Upcoming */}
        <section className="glass-panel rounded-[var(--radius)] p-4">
          <h2 className="text-sm font-semibold mb-3">Upcoming</h2>
          {UPCOMING_DATA.length === 0 ? (
            <EmptyState icon={Video} title="No upcoming webinars" description="Check back soon for new sessions." />
          ) : (
            <div className="divide-y divide-[hsl(var(--border))]">
              {UPCOMING_DATA.map((w) => (
                <div key={w.id} className="py-3 flex items-start gap-3">
                  <div className="flex-1 min-w-0">
                    <p className="text-sm font-medium">{w.topic}</p>
                    <p className="text-xs text-muted-foreground mt-0.5">{w.date} • {w.duration} • {w.host}</p>
                    <p className="text-xs text-muted-foreground">{w.spots} spots remaining</p>
                  </div>
                  {registered.has(w.id) ? (
                    <Button size="sm" variant="outline" disabled>Registered</Button>
                  ) : (
                    <Button
                      size="sm"
                      variant="outline"
                      onClick={() => handleRegister(w)}
                      disabled={registering === w.id}
                    >
                      {registering === w.id ? <Loader2 className="w-3.5 h-3.5 animate-spin mr-1.5" /> : null}
                      Register
                    </Button>
                  )}
                </div>
              ))}
            </div>
          )}
        </section>

        {/* Replays */}
        <section className="glass-panel rounded-[var(--radius)] p-4">
          <h2 className="text-sm font-semibold mb-3">Replays</h2>
          <div className="divide-y divide-[hsl(var(--border))]">
            {REPLAYS.map((r) => (
              <div key={r.id} className="py-3 flex items-start gap-3">
                <div className="flex-1 min-w-0">
                  <p className="text-sm font-medium">{r.topic}</p>
                  <p className="text-xs text-muted-foreground mt-0.5">{r.date} • {r.length}</p>
                  <p className="text-xs text-muted-foreground">{r.views} views</p>
                </div>
                <Button size="sm" variant="outline">Watch</Button>
              </div>
            ))}
          </div>
        </section>
      </div>
    </div>
  );
}
