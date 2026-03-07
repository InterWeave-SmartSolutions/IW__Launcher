import { useState } from "react";
import {
  Loader2,
  ChevronLeft,
  ChevronRight,
  CheckCircle2,
  XCircle,
  Clock,
  AlertTriangle,
  Search,
} from "lucide-react";
import { useTransactions } from "@/hooks/useMonitoring";
import type { Transaction } from "@/types/monitoring";
import { cn } from "@/lib/utils";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";

const PAGE_SIZE = 20;

const STATUS_CONFIG: Record<
  Transaction["status"],
  { icon: React.ComponentType<{ className?: string }>; color: string; label: string }
> = {
  success: { icon: CheckCircle2, color: "text-[hsl(var(--success))]", label: "Success" },
  failed: { icon: XCircle, color: "text-[hsl(var(--destructive))]", label: "Failed" },
  running: { icon: Clock, color: "text-[hsl(var(--primary))]", label: "Running" },
  timeout: { icon: AlertTriangle, color: "text-[hsl(var(--warning))]", label: "Timeout" },
};

export function TransactionHistoryPage() {
  useDocumentTitle("Transaction History");
  const [page, setPage] = useState(1);
  const [filter, setFilter] = useState("");
  const { data: txRes, isLoading } = useTransactions(page, PAGE_SIZE);

  const transactions = txRes?.data?.transactions ?? [];
  const pagination = txRes?.data?.pagination;
  const totalPages = pagination?.total_pages ?? 1;

  // Client-side filter by flow name
  const filtered = filter
    ? transactions.filter((tx) => tx.flow_name.toLowerCase().includes(filter.toLowerCase()))
    : transactions;

  return (
    <div className="space-y-4">
      {/* Filter bar */}
      <div className="flex items-center gap-3">
        <div className="relative flex-1 max-w-xs">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-3.5 h-3.5 text-muted-foreground" />
          <input
            type="text"
            value={filter}
            onChange={(e) => setFilter(e.target.value)}
            placeholder="Filter by flow name..."
            className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-foreground rounded-[14px] pl-9 pr-3 py-2 text-sm outline-none focus:ring-2 focus:ring-[hsl(var(--primary)/0.5)] transition-shadow"
          />
        </div>
        <span className="text-xs text-muted-foreground">
          {pagination?.total_count ?? 0} total transactions
        </span>
      </div>

      {/* Table */}
      {isLoading ? (
        <div className="flex justify-center py-12">
          <Loader2 className="w-6 h-6 animate-spin text-muted-foreground" />
        </div>
      ) : filtered.length === 0 ? (
        <div className="glass-panel rounded-[var(--radius)] p-8 text-center">
          <p className="text-sm text-muted-foreground">No transactions found.</p>
        </div>
      ) : (
        <div className="glass-panel rounded-[var(--radius)] overflow-hidden">
          <div className="overflow-x-auto">
            <table className="w-full text-sm">
              <thead>
                <tr className="border-b border-[hsl(var(--border))]">
                  <th className="text-left text-xs font-medium text-muted-foreground px-4 py-3">Status</th>
                  <th className="text-left text-xs font-medium text-muted-foreground px-4 py-3">Flow Name</th>
                  <th className="text-left text-xs font-medium text-muted-foreground px-4 py-3">Started</th>
                  <th className="text-right text-xs font-medium text-muted-foreground px-4 py-3">Duration</th>
                  <th className="text-right text-xs font-medium text-muted-foreground px-4 py-3">Records</th>
                  <th className="text-left text-xs font-medium text-muted-foreground px-4 py-3">Error</th>
                </tr>
              </thead>
              <tbody>
                {filtered.map((tx) => {
                  const config = STATUS_CONFIG[tx.status];
                  const Icon = config.icon;
                  return (
                    <tr
                      key={tx.execution_id}
                      className="border-b border-[hsl(var(--border)/0.5)] last:border-b-0 hover:bg-[hsl(var(--muted)/0.15)] transition-colors"
                    >
                      <td className="px-4 py-3">
                        <div className="flex items-center gap-1.5">
                          <Icon className={cn("w-4 h-4", config.color)} />
                          <span className={cn("text-xs", config.color)}>{config.label}</span>
                        </div>
                      </td>
                      <td className="px-4 py-3 font-medium max-w-[200px] truncate">{tx.flow_name}</td>
                      <td className="px-4 py-3 text-muted-foreground text-xs">
                        {formatTimestamp(tx.started_at)}
                      </td>
                      <td className="px-4 py-3 text-right tabular-nums">
                        {tx.duration_ms != null ? formatDuration(tx.duration_ms) : "—"}
                      </td>
                      <td className="px-4 py-3 text-right tabular-nums">
                        {tx.records_processed}
                        {tx.records_failed > 0 && (
                          <span className="text-[hsl(var(--destructive))] ml-1">
                            ({tx.records_failed} err)
                          </span>
                        )}
                      </td>
                      <td className="px-4 py-3 text-xs text-muted-foreground max-w-[200px] truncate">
                        {tx.error_message ?? "—"}
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        </div>
      )}

      {/* Pagination */}
      {totalPages > 1 && (
        <div className="flex items-center justify-center gap-3">
          <button
            onClick={() => setPage((p) => Math.max(1, p - 1))}
            disabled={page <= 1}
            className="p-1.5 rounded-lg border border-[hsl(var(--border))] text-muted-foreground hover:text-foreground disabled:opacity-30 transition-colors cursor-pointer disabled:cursor-not-allowed"
          >
            <ChevronLeft className="w-4 h-4" />
          </button>
          <span className="text-xs text-muted-foreground">
            Page {page} of {totalPages}
          </span>
          <button
            onClick={() => setPage((p) => Math.min(totalPages, p + 1))}
            disabled={page >= totalPages}
            className="p-1.5 rounded-lg border border-[hsl(var(--border))] text-muted-foreground hover:text-foreground disabled:opacity-30 transition-colors cursor-pointer disabled:cursor-not-allowed"
          >
            <ChevronRight className="w-4 h-4" />
          </button>
        </div>
      )}
    </div>
  );
}

function formatTimestamp(iso: string): string {
  try {
    const d = new Date(iso);
    return d.toLocaleString(undefined, {
      month: "short",
      day: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    });
  } catch {
    return iso;
  }
}

function formatDuration(ms: number): string {
  if (ms < 1000) return `${Math.round(ms)}ms`;
  if (ms < 60_000) return `${(ms / 1000).toFixed(1)}s`;
  return `${(ms / 60_000).toFixed(1)}m`;
}
