import { Activity, CheckCircle, AlertTriangle, Clock, TrendingUp, Zap, RefreshCw, Loader2, User, Building2, ArrowRight } from "lucide-react";
import { Link } from "react-router-dom";
import { cn } from "@/lib/utils";
import { useDashboard, useTransactions } from "@/hooks/useMonitoring";
import { useAuth } from "@/providers/AuthProvider";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { Sparkline } from "@/components/Sparkline";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import type { Transaction } from "@/types/monitoring";

function fmt(n: number | undefined | null): string {
  if (n == null) return "—";
  return n.toLocaleString();
}

function fmtRate(n: number | undefined | null): string {
  if (n == null) return "—";
  return n.toFixed(1) + "%";
}

function fmtDuration(ms: number | undefined | null): string {
  if (ms == null || ms === 0) return "—";
  if (ms < 1000) return ms + "ms";
  return (ms / 1000).toFixed(1) + "s";
}

function fmtTime(iso: string | null): string {
  if (!iso) return "—";
  try {
    return new Date(iso).toLocaleTimeString();
  } catch {
    return iso;
  }
}

/** Derive hourly transaction counts from recent transactions for sparkline */
function deriveSparkline(transactions: Transaction[], buckets = 8): number[] {
  if (transactions.length === 0) return [];
  const now = Date.now();
  const bucketMs = (24 * 3600_000) / buckets;
  const counts = new Array<number>(buckets).fill(0);
  for (const tx of transactions) {
    const age = now - new Date(tx.started_at).getTime();
    const idx = buckets - 1 - Math.min(Math.floor(age / bucketMs), buckets - 1);
    counts[idx] = (counts[idx] ?? 0) + 1;
  }
  return counts;
}

/** Derive per-bucket success rate (0–100) for sparkline */
function deriveSuccessRateSparkline(transactions: Transaction[], buckets = 8): number[] {
  if (transactions.length === 0) return [];
  const now = Date.now();
  const bucketMs = (24 * 3600_000) / buckets;
  const ok = new Array<number>(buckets).fill(0);
  const total = new Array<number>(buckets).fill(0);
  for (const tx of transactions) {
    const age = now - new Date(tx.started_at).getTime();
    const idx = buckets - 1 - Math.min(Math.floor(age / bucketMs), buckets - 1);
    total[idx] = (total[idx] ?? 0) + 1;
    if (tx.status === "success") ok[idx] = (ok[idx] ?? 0) + 1;
  }
  return total.map((t, i) => (t > 0 ? ((ok[i] ?? 0) / t) * 100 : 0));
}

/** Derive per-bucket average duration (ms) for sparkline */
function deriveDurationSparkline(transactions: Transaction[], buckets = 8): number[] {
  if (transactions.length === 0) return [];
  const now = Date.now();
  const bucketMs = (24 * 3600_000) / buckets;
  const sums = new Array<number>(buckets).fill(0);
  const counts = new Array<number>(buckets).fill(0);
  for (const tx of transactions) {
    if (tx.duration_ms == null) continue;
    const age = now - new Date(tx.started_at).getTime();
    const idx = buckets - 1 - Math.min(Math.floor(age / bucketMs), buckets - 1);
    sums[idx] = (sums[idx] ?? 0) + tx.duration_ms;
    counts[idx] = (counts[idx] ?? 0) + 1;
  }
  return counts.map((c, i) => (c > 0 ? (sums[i] ?? 0) / c : 0));
}

function statusBadge(status: string) {
  const variant = status === "success" ? "success"
    : status === "failed" ? "destructive"
    : status === "running" ? "default"
    : status === "timeout" ? "warning"
    : "secondary" as const;
  return <Badge variant={variant} className="text-[11px]">{status}</Badge>;
}

export function DashboardPage() {
  useDocumentTitle("Dashboard");
  const { user } = useAuth();
  const { data: dashRes, isLoading, error, refetch, isFetching } = useDashboard();
  const { data: txRes } = useTransactions(1, 50);

  const summary = dashRes?.data?.summary;
  const running = dashRes?.data?.running_transactions ?? [];
  const recent = dashRes?.data?.recent_activity?.last_hour;
  const transactions = txRes?.data?.transactions ?? [];
  const firstName = user?.userName?.split(" ")[0] ?? "there";
  const spark = deriveSparkline(transactions);
  const sparkRate = deriveSuccessRateSparkline(transactions);
  const sparkDuration = deriveDurationSparkline(transactions);

  const kpis = [
    {
      label: "Transactions (24h)",
      value: fmt(summary?.total_count_24h),
      sub: `${fmt(summary?.completed_count_24h)} completed, ${fmt(summary?.failed_count_24h)} failed`,
      icon: Activity,
      color: "text-[hsl(var(--primary))]",
      sparkData: spark,
      sparkColor: "hsl(var(--primary))",
    },
    {
      label: "Success Rate (24h)",
      value: fmtRate(summary?.success_rate_24h),
      sub: `7d: ${fmtRate(summary?.success_rate_7d)}`,
      icon: CheckCircle,
      color: "text-[hsl(var(--success))]",
      sparkData: sparkRate,
      sparkColor: "hsl(var(--success))",
    },
    {
      label: "Running Now",
      value: fmt(summary?.running_count),
      sub: running.length > 0 ? `${running[0]!.flow_name}...` : "No active flows",
      icon: AlertTriangle,
      color: "text-[hsl(var(--warning))]",
      sparkData: [] as number[],
      sparkColor: "hsl(var(--warning))",
    },
    {
      label: "Avg Duration (24h)",
      value: fmtDuration(summary?.avg_duration_ms_24h),
      sub: recent ? `Last hour: ${recent.total} txns` : "—",
      icon: Clock,
      color: "text-muted-foreground",
      sparkData: sparkDuration,
      sparkColor: "hsl(var(--muted-foreground))",
    },
  ];

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex items-start justify-between gap-4 flex-wrap">
        <div>
          <h1 className="text-xl font-bold">Welcome back, {firstName}</h1>
          <p className="text-sm text-muted-foreground">
            {user?.companyName ? `${user.companyName} — ` : ""}Platform health and operational status
          </p>
        </div>
        <div className="flex items-center gap-2">
          <Button
            variant="outline"
            size="sm"
            onClick={() => void refetch()}
            disabled={isFetching}
            className="rounded-full"
          >
            <RefreshCw className={cn("w-3 h-3", isFetching && "animate-spin")} />
            Refresh
          </Button>
          <Badge variant="success" className="rounded-full">
            <span className="w-2 h-2 rounded-full bg-[hsl(var(--success))]" />
            Services Online
          </Badge>
        </div>
      </div>

      {/* Error banner */}
      {error && (
        <div className="p-3 rounded-[var(--radius)] border border-[hsl(var(--destructive)/0.3)] bg-[hsl(var(--destructive)/0.08)] text-sm text-[hsl(var(--destructive))]">
          Failed to load dashboard data: {error.message}
        </div>
      )}

      {/* KPI Cards */}
      <div className="grid grid-cols-4 gap-4 max-lg:grid-cols-2 max-sm:grid-cols-1">
        {kpis.map((kpi) => (
          <div key={kpi.label} className="glass-panel rounded-[var(--radius)] p-4 space-y-2">
            <div className="flex items-center justify-between">
              <p className="text-xs text-muted-foreground">{kpi.label}</p>
              <kpi.icon className={cn("w-4 h-4", kpi.color)} />
            </div>
            {isLoading ? (
              <Loader2 className="w-5 h-5 animate-spin text-muted-foreground" />
            ) : (
              <>
                <div className="flex items-end justify-between gap-2">
                  <p className="text-2xl font-bold tracking-tight">{kpi.value}</p>
                  {kpi.sparkData.length > 1 && (
                    <Sparkline data={kpi.sparkData} color={kpi.sparkColor} width={64} height={20} />
                  )}
                </div>
                <p className="text-xs text-muted-foreground truncate">{kpi.sub}</p>
              </>
            )}
          </div>
        ))}
      </div>

      {/* Recent Transactions table */}
      <div className="glass-panel rounded-[var(--radius)] p-4">
        <div className="flex items-center justify-between mb-3">
          <h2 className="text-sm font-semibold">Recent Transactions</h2>
          <div className="flex items-center gap-3">
            <span className="flex items-center gap-1.5 text-xs text-muted-foreground">
              <span className="w-2 h-2 rounded-full bg-[hsl(var(--success))]" />
              Auto-refreshes every 30s
            </span>
            <Link
              to="/monitoring/transactions"
              className="flex items-center gap-1 text-xs text-[hsl(var(--primary))] hover:underline font-medium"
            >
              View all <ArrowRight className="w-3 h-3" />
            </Link>
          </div>
        </div>
        <div className="border border-[hsl(var(--border))] rounded-[14px] overflow-hidden">
          <table className="w-full text-sm">
            <thead>
              <tr className="bg-[hsl(var(--muted)/0.3)]">
                <th className="text-left px-3 py-2 text-xs text-muted-foreground font-medium">Time</th>
                <th className="text-left px-3 py-2 text-xs text-muted-foreground font-medium">Flow</th>
                <th className="text-left px-3 py-2 text-xs text-muted-foreground font-medium">Status</th>
                <th className="text-left px-3 py-2 text-xs text-muted-foreground font-medium">Records</th>
                <th className="text-left px-3 py-2 text-xs text-muted-foreground font-medium">Duration</th>
              </tr>
            </thead>
            <tbody>
              {isLoading ? (
                <tr>
                  <td className="px-3 py-6 text-center text-muted-foreground" colSpan={5}>
                    <Loader2 className="w-5 h-5 animate-spin mx-auto" />
                  </td>
                </tr>
              ) : transactions.length === 0 ? (
                <tr>
                  <td className="px-3 py-6 text-center text-muted-foreground" colSpan={5}>
                    No transaction data yet. Transactions will appear here once flows are executed.
                  </td>
                </tr>
              ) : (
                transactions.map((tx: Transaction) => (
                  <tr key={tx.execution_id} className="border-t border-[hsl(var(--border))]">
                    <td className="px-3 py-2 text-xs">{fmtTime(tx.started_at)}</td>
                    <td className="px-3 py-2 text-xs font-medium">{tx.flow_name}</td>
                    <td className="px-3 py-2">{statusBadge(tx.status)}</td>
                    <td className="px-3 py-2 text-xs">{tx.records_processed}</td>
                    <td className="px-3 py-2 text-xs">{fmtDuration(tx.duration_ms)}</td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>

      {/* Quick Actions */}
      <div className="glass-panel rounded-[var(--radius)] p-4">
        <h2 className="text-sm font-semibold mb-3">Quick Actions</h2>
        <div className="grid grid-cols-5 gap-3 max-lg:grid-cols-3 max-md:grid-cols-2 max-sm:grid-cols-1">
          <Link
            to="/monitoring"
            className="flex items-center gap-2 px-3 py-2.5 rounded-[14px] border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)] text-sm hover:bg-[hsl(var(--accent)/0.5)] transition-colors"
          >
            <Activity className="w-4 h-4 text-[hsl(var(--warning))]" />
            Monitoring
          </Link>
          <Link
            to="/admin/configurator"
            className="flex items-center gap-2 px-3 py-2.5 rounded-[14px] border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)] text-sm hover:bg-[hsl(var(--accent)/0.5)] transition-colors"
          >
            <Zap className="w-4 h-4 text-[hsl(var(--primary))]" />
            Integrations
          </Link>
          <Link
            to="/company/config"
            className="flex items-center gap-2 px-3 py-2.5 rounded-[14px] border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)] text-sm hover:bg-[hsl(var(--accent)/0.5)] transition-colors"
          >
            <TrendingUp className="w-4 h-4 text-[hsl(var(--success))]" />
            Company Config
          </Link>
          <Link
            to="/profile"
            className="flex items-center gap-2 px-3 py-2.5 rounded-[14px] border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)] text-sm hover:bg-[hsl(var(--accent)/0.5)] transition-colors"
          >
            <User className="w-4 h-4 text-muted-foreground" />
            My Profile
          </Link>
          <Link
            to="/company"
            className="flex items-center gap-2 px-3 py-2.5 rounded-[14px] border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)] text-sm hover:bg-[hsl(var(--accent)/0.5)] transition-colors"
          >
            <Building2 className="w-4 h-4 text-muted-foreground" />
            Company Profile
          </Link>
        </div>
      </div>
    </div>
  );
}
