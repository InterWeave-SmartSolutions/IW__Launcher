import { Link } from "react-router-dom";
import {
  CheckCircle,
  XCircle,
  Clock,
  Activity,
  Loader2,
  ArrowRight,
} from "lucide-react";
import { cn } from "@/lib/utils";
import type { Transaction } from "@/types/monitoring";

interface ActivityFeedProps {
  transactions: Transaction[];
  isLoading: boolean;
  /** Maximum items to show (default 5) */
  limit?: number;
}

function fmtRelativeTime(iso: string): string {
  const diff = Date.now() - new Date(iso).getTime();
  const seconds = Math.floor(diff / 1000);
  if (seconds < 60) return `${seconds}s ago`;
  const minutes = Math.floor(seconds / 60);
  if (minutes < 60) return `${minutes}m ago`;
  const hours = Math.floor(minutes / 60);
  if (hours < 24) return `${hours}h ago`;
  const days = Math.floor(hours / 24);
  return `${days}d ago`;
}

function statusIcon(status: string) {
  switch (status) {
    case "success":
      return <CheckCircle className="w-3.5 h-3.5 text-[hsl(var(--success))]" />;
    case "failed":
    case "error":
      return <XCircle className="w-3.5 h-3.5 text-[hsl(var(--destructive))]" />;
    case "running":
      return <Activity className="w-3.5 h-3.5 text-[hsl(var(--primary))] animate-pulse" />;
    default:
      return <Clock className="w-3.5 h-3.5 text-muted-foreground" />;
  }
}

export function ActivityFeed({ transactions, isLoading, limit = 5 }: ActivityFeedProps) {
  if (isLoading) {
    return (
      <div className="flex items-center justify-center py-6">
        <Loader2 className="w-5 h-5 animate-spin text-muted-foreground" />
      </div>
    );
  }

  if (transactions.length === 0) {
    return (
      <div className="text-center py-4">
        <Activity className="w-6 h-6 text-muted-foreground mx-auto mb-1.5" />
        <p className="text-xs text-muted-foreground">No recent activity</p>
      </div>
    );
  }

  const items = transactions.slice(0, limit);

  return (
    <div className="space-y-1">
      {items.map((tx) => (
        <div
          key={tx.execution_id}
          className={cn(
            "flex items-center gap-2.5 px-2.5 py-2 rounded-md text-sm transition-colors",
            "hover:bg-[hsl(var(--muted)/0.3)]"
          )}
        >
          {statusIcon(tx.status)}
          <div className="flex-1 min-w-0">
            <p className="text-xs font-medium truncate">{tx.flow_name}</p>
            <p className="text-[10px] text-muted-foreground">
              {tx.records_processed != null ? `${tx.records_processed} records` : ""}
              {tx.duration_ms != null ? ` · ${Math.round(tx.duration_ms / 1000)}s` : ""}
            </p>
          </div>
          <span className="text-[10px] text-muted-foreground shrink-0 whitespace-nowrap">
            {fmtRelativeTime(tx.started_at)}
          </span>
        </div>
      ))}

      {transactions.length > limit && (
        <Link
          to="/monitoring/transactions"
          className="flex items-center justify-center gap-1 text-xs text-[hsl(var(--primary))] hover:underline pt-1"
        >
          View all activity
          <ArrowRight className="w-3 h-3" />
        </Link>
      )}
    </div>
  );
}
