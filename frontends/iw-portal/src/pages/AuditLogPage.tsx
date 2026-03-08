import { useState, useMemo } from "react";
import {
  ClipboardList,
  Search,
  X,
  ChevronLeft,
  ChevronRight,
  Loader2,
  AlertTriangle,
} from "lucide-react";
import { useAuditLog, useAuditStats, useAuditUsers } from "@/hooks/useAuditLog";
import type { AuditFilters } from "@/hooks/useAuditLog";
import type { AuditEntry } from "@/types/audit";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { cn } from "@/lib/utils";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Badge } from "@/components/ui/badge";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Separator } from "@/components/ui/separator";

const PAGE_SIZE = 50;

const ALL_ACTION_TYPES = [
  "login",
  "logout",
  "login_failed",
  "profile_update",
  "password_change",
  "company_update",
  "flow_start",
  "flow_stop",
  "flow_config_change",
  "config_change",
  "user_register",
  "company_register",
  "mfa_enable",
  "mfa_disable",
  "password_reset",
  "alert_create",
  "alert_update",
  "alert_delete",
  "admin_action",
] as const;

type BadgeVariant = "default" | "secondary" | "success" | "destructive" | "warning" | "outline";

interface ActionBadgeConfig {
  variant: BadgeVariant;
  className: string;
}

function getActionBadgeConfig(actionType: string): ActionBadgeConfig {
  switch (actionType) {
    case "login":
    case "logout":
    case "login_failed":
      return {
        variant: "default",
        className: "bg-blue-500/15 text-blue-400",
      };
    case "profile_update":
    case "password_change":
    case "company_update":
      return {
        variant: "warning",
        className: "bg-amber-500/15 text-amber-400",
      };
    case "flow_start":
    case "flow_stop":
    case "flow_config_change":
      return {
        variant: "success",
        className: "bg-emerald-500/15 text-emerald-400",
      };
    case "config_change":
    case "admin_action":
      return {
        variant: "secondary",
        className: "bg-purple-500/15 text-purple-400",
      };
    case "user_register":
    case "company_register":
      return {
        variant: "outline",
        className: "bg-teal-500/15 text-teal-400",
      };
    case "mfa_enable":
    case "mfa_disable":
    case "password_reset":
    case "alert_create":
    case "alert_update":
    case "alert_delete":
      return {
        variant: "destructive",
        className: "bg-red-500/15 text-red-400",
      };
    default:
      return {
        variant: "secondary",
        className: "",
      };
  }
}

function formatActionType(actionType: string): string {
  return actionType
    .split("_")
    .map((w) => w.charAt(0).toUpperCase() + w.slice(1))
    .join(" ");
}

function formatRelativeTime(isoString: string): string {
  try {
    const date = new Date(isoString);
    const now = new Date();
    const diffMs = now.getTime() - date.getTime();
    const diffSecs = Math.floor(diffMs / 1000);
    const diffMins = Math.floor(diffSecs / 60);
    const diffHours = Math.floor(diffMins / 60);
    const diffDays = Math.floor(diffHours / 24);

    if (diffSecs < 60) return "just now";
    if (diffMins < 60) return `${diffMins}m ago`;
    if (diffHours < 24) return `${diffHours}h ago`;
    if (diffDays < 7) return `${diffDays}d ago`;

    return date.toLocaleDateString(undefined, {
      month: "short",
      day: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    });
  } catch {
    return isoString;
  }
}

const EMPTY_FILTERS: AuditFilters = {};

export function AuditLogPage() {
  useDocumentTitle("Audit Log");
  const [page, setPage] = useState(1);
  const [filters, setFilters] = useState<AuditFilters>(EMPTY_FILTERS);
  const [searchInput, setSearchInput] = useState("");

  const { data: logData, isLoading: logLoading, isError: logError } = useAuditLog(page, PAGE_SIZE, filters);
  const { data: statsData, isLoading: statsLoading } = useAuditStats();
  const { data: usersData } = useAuditUsers();

  const entries = logData?.entries ?? [];
  const totalCount = logData?.totalCount ?? 0;
  const totalPages = Math.max(1, Math.ceil(totalCount / PAGE_SIZE));

  const stats = statsData?.stats;
  const users = usersData?.users ?? [];

  const topActionType = useMemo(() => {
    if (!stats?.byActionType) return "N/A";
    let max = 0;
    let top = "N/A";
    for (const [type, count] of Object.entries(stats.byActionType)) {
      if (count > max) {
        max = count;
        top = type;
      }
    }
    return formatActionType(top);
  }, [stats]);

  const activeDays = stats?.byDay?.length ?? 0;

  const hasFilters =
    filters.actionType !== undefined ||
    filters.userId !== undefined ||
    filters.dateFrom !== undefined ||
    filters.dateTo !== undefined ||
    filters.search !== undefined;

  function updateFilter(key: keyof AuditFilters, value: string | number | undefined) {
    setPage(1);
    setFilters((prev) => {
      const next = { ...prev };
      if (value === undefined || value === "" || value === "all") {
        delete next[key];
      } else {
        // TypeScript needs explicit narrowing per key
        if (key === "userId") {
          (next as Record<string, unknown>)[key] = typeof value === "string" ? parseInt(value, 10) : value;
        } else {
          (next as Record<string, unknown>)[key] = value;
        }
      }
      return next;
    });
  }

  function clearFilters() {
    setPage(1);
    setFilters(EMPTY_FILTERS);
    setSearchInput("");
  }

  function handleSearchSubmit(e: React.FormEvent) {
    e.preventDefault();
    updateFilter("search", searchInput.trim() || undefined);
  }

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex items-center gap-3">
        <div className="p-2 rounded-xl bg-[hsl(var(--primary)/0.15)]">
          <ClipboardList className="w-5 h-5 text-[hsl(var(--primary))]" />
        </div>
        <div>
          <h1 className="text-xl font-semibold">Audit Log</h1>
          <p className="text-sm text-muted-foreground">
            Track all user and system actions across the platform
          </p>
        </div>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
        <div className="glass-panel rounded-[var(--radius)] p-4 space-y-1">
          <p className="text-xs text-muted-foreground font-medium">Total Events (30d)</p>
          {statsLoading ? (
            <Loader2 className="w-4 h-4 animate-spin text-muted-foreground" />
          ) : (
            <p className="text-2xl font-bold tabular-nums">
              {stats?.totalEvents?.toLocaleString() ?? "0"}
            </p>
          )}
        </div>
        <div className="glass-panel rounded-[var(--radius)] p-4 space-y-1">
          <p className="text-xs text-muted-foreground font-medium">Top Action Type</p>
          {statsLoading ? (
            <Loader2 className="w-4 h-4 animate-spin text-muted-foreground" />
          ) : (
            <p className="text-2xl font-bold">{topActionType}</p>
          )}
        </div>
        <div className="glass-panel rounded-[var(--radius)] p-4 space-y-1">
          <p className="text-xs text-muted-foreground font-medium">Active Days (30d)</p>
          {statsLoading ? (
            <Loader2 className="w-4 h-4 animate-spin text-muted-foreground" />
          ) : (
            <p className="text-2xl font-bold tabular-nums">{activeDays}</p>
          )}
        </div>
      </div>

      <Separator className="bg-[hsl(var(--border)/0.5)]" />

      {/* Filter Bar */}
      <div className="flex items-end gap-3 flex-wrap">
        <div className="space-y-1.5">
          <label className="text-xs font-medium text-muted-foreground">From</label>
          <Input
            type="date"
            value={filters.dateFrom ?? ""}
            onChange={(e) => updateFilter("dateFrom", e.target.value || undefined)}
            className="w-[150px]"
          />
        </div>
        <div className="space-y-1.5">
          <label className="text-xs font-medium text-muted-foreground">To</label>
          <Input
            type="date"
            value={filters.dateTo ?? ""}
            onChange={(e) => updateFilter("dateTo", e.target.value || undefined)}
            className="w-[150px]"
          />
        </div>
        <div className="space-y-1.5">
          <label className="text-xs font-medium text-muted-foreground">Action Type</label>
          <Select
            value={filters.actionType ?? "all"}
            onValueChange={(val) => updateFilter("actionType", val === "all" ? undefined : val)}
          >
            <SelectTrigger className="w-[180px]">
              <SelectValue placeholder="All actions" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="all">All actions</SelectItem>
              {ALL_ACTION_TYPES.map((at) => (
                <SelectItem key={at} value={at}>
                  {formatActionType(at)}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>
        <div className="space-y-1.5">
          <label className="text-xs font-medium text-muted-foreground">User</label>
          <Select
            value={filters.userId !== undefined ? String(filters.userId) : "all"}
            onValueChange={(val) => updateFilter("userId", val === "all" ? undefined : val)}
          >
            <SelectTrigger className="w-[200px]">
              <SelectValue placeholder="All users" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="all">All users</SelectItem>
              {users.map((u) => (
                <SelectItem key={u.id} value={String(u.id)}>
                  {u.name || u.email}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>
        <form onSubmit={handleSearchSubmit} className="space-y-1.5">
          <label className="text-xs font-medium text-muted-foreground">Search</label>
          <div className="relative">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-3.5 h-3.5 text-muted-foreground" />
            <Input
              type="text"
              value={searchInput}
              onChange={(e) => setSearchInput(e.target.value)}
              placeholder="Search details..."
              className="pl-9 w-[200px]"
            />
          </div>
        </form>
        {hasFilters && (
          <Button variant="ghost" size="sm" onClick={clearFilters} className="mb-0.5">
            <X className="w-3.5 h-3.5" />
            Clear Filters
          </Button>
        )}
      </div>

      {/* Results Count */}
      <div className="flex items-center justify-between">
        <span className="text-xs text-muted-foreground">
          {totalCount.toLocaleString()} total entries
          {hasFilters ? " (filtered)" : ""}
        </span>
      </div>

      {/* Table */}
      {logLoading ? (
        <div className="flex justify-center py-12">
          <Loader2 className="w-6 h-6 animate-spin text-muted-foreground" />
        </div>
      ) : logError ? (
        <div className="glass-panel rounded-[var(--radius)] p-8 text-center space-y-3">
          <AlertTriangle className="w-8 h-8 text-[hsl(var(--warning))] mx-auto" />
          <p className="text-sm text-muted-foreground">
            Failed to load audit log. You may not have admin access.
          </p>
        </div>
      ) : entries.length === 0 ? (
        <div className="glass-panel rounded-[var(--radius)] p-8 text-center space-y-3">
          <ClipboardList className="w-8 h-8 text-muted-foreground mx-auto" />
          <p className="text-sm text-muted-foreground">
            {hasFilters
              ? "No audit entries match the current filters."
              : "No audit entries recorded yet."}
          </p>
          {!hasFilters && (
            <p className="text-xs text-muted-foreground max-w-md mx-auto">
              Audit logging captures user logins, profile changes, flow operations,
              and administrative actions as they occur.
            </p>
          )}
        </div>
      ) : (
        <div className="glass-panel rounded-[var(--radius)] overflow-hidden">
          <div className="overflow-x-auto">
            <table className="w-full text-sm">
              <thead>
                <tr className="border-b border-[hsl(var(--border))]">
                  <th className="text-left text-xs font-medium text-muted-foreground px-4 py-3">
                    Time
                  </th>
                  <th className="text-left text-xs font-medium text-muted-foreground px-4 py-3">
                    User
                  </th>
                  <th className="text-left text-xs font-medium text-muted-foreground px-4 py-3">
                    Action
                  </th>
                  <th className="text-left text-xs font-medium text-muted-foreground px-4 py-3">
                    Detail
                  </th>
                  <th className="text-left text-xs font-medium text-muted-foreground px-4 py-3">
                    Resource
                  </th>
                  <th className="text-left text-xs font-medium text-muted-foreground px-4 py-3">
                    IP
                  </th>
                </tr>
              </thead>
              <tbody>
                {entries.map((entry) => (
                  <AuditRow key={entry.id} entry={entry} />
                ))}
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

function AuditRow({ entry }: { entry: AuditEntry }) {
  const badgeConfig = getActionBadgeConfig(entry.actionType);
  const resourceLabel =
    entry.resourceType && entry.resourceId
      ? `${entry.resourceType}:${entry.resourceId}`
      : entry.resourceType ?? "\u2014";

  return (
    <tr className="border-b border-[hsl(var(--border)/0.5)] last:border-b-0 hover:bg-[hsl(var(--muted)/0.15)] transition-colors">
      <td className="px-4 py-3 text-xs text-muted-foreground whitespace-nowrap">
        {formatRelativeTime(entry.createdAt)}
      </td>
      <td className="px-4 py-3 text-xs max-w-[160px] truncate">
        {entry.userEmail || "\u2014"}
      </td>
      <td className="px-4 py-3">
        <Badge className={cn("text-[11px]", badgeConfig.className)}>
          {formatActionType(entry.actionType)}
        </Badge>
      </td>
      <td className="px-4 py-3 text-xs text-muted-foreground max-w-[250px] truncate">
        {entry.actionDetail || "\u2014"}
      </td>
      <td className="px-4 py-3 text-xs text-muted-foreground max-w-[140px] truncate">
        {resourceLabel}
      </td>
      <td className="px-4 py-3 text-xs text-muted-foreground tabular-nums whitespace-nowrap">
        {entry.ipAddress ?? "\u2014"}
      </td>
    </tr>
  );
}
