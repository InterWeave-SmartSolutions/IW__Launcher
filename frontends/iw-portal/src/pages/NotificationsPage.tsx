import { useState, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import {
  Bell,
  AlertTriangle,
  Activity,
  Shield,
  Check,
  Trash2,
  Loader2,
  ChevronLeft,
  ChevronRight,
  CheckCheck,
  Inbox,
} from "lucide-react";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { useToast } from "@/providers/ToastProvider";
import {
  useNotifications,
  useUnreadCount,
  useMarkRead,
  useMarkAllRead,
  useDeleteNotifications,
} from "@/hooks/useNotifications";
import type { Notification, NotificationType } from "@/types/notifications";
import { cn } from "@/lib/utils";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Tabs, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Separator } from "@/components/ui/separator";

const PAGE_SIZE = 20;

const TYPE_CONFIG: Record<
  NotificationType,
  {
    icon: React.ComponentType<{ className?: string }>;
    color: string;
    bg: string;
    label: string;
  }
> = {
  system: {
    icon: Bell,
    color: "text-[hsl(var(--primary))]",
    bg: "bg-[hsl(var(--primary)/0.1)]",
    label: "System",
  },
  alert: {
    icon: AlertTriangle,
    color: "text-[hsl(var(--warning))]",
    bg: "bg-[hsl(var(--warning)/0.1)]",
    label: "Alert",
  },
  "flow-status": {
    icon: Activity,
    color: "text-[hsl(var(--success))]",
    bg: "bg-[hsl(var(--success)/0.1)]",
    label: "Flow Status",
  },
  security: {
    icon: Shield,
    color: "text-[hsl(var(--destructive))]",
    bg: "bg-[hsl(var(--destructive)/0.1)]",
    label: "Security",
  },
};

function formatRelativeTime(isoDate: string): string {
  try {
    const diff = Date.now() - new Date(isoDate).getTime();
    if (diff < 0) return "just now";
    const minutes = Math.floor(diff / 60_000);
    if (minutes < 1) return "just now";
    if (minutes < 60) return `${minutes}m ago`;
    const hours = Math.floor(minutes / 60);
    if (hours < 24) return `${hours}h ago`;
    const days = Math.floor(hours / 24);
    if (days < 30) return `${days}d ago`;
    const months = Math.floor(days / 30);
    return `${months}mo ago`;
  } catch {
    return isoDate;
  }
}

export function NotificationsPage() {
  useDocumentTitle("Notifications");
  const navigate = useNavigate();
  const { showToast } = useToast();

  const [page, setPage] = useState(1);
  const [typeFilter, setTypeFilter] = useState<NotificationType | "all">("all");
  const [selected, setSelected] = useState<Set<number>>(new Set());

  const { data, isLoading } = useNotifications(page, PAGE_SIZE, typeFilter);
  const { data: countData } = useUnreadCount();
  const markRead = useMarkRead();
  const markAllRead = useMarkAllRead();
  const deleteNotifications = useDeleteNotifications();

  const notifications = data?.notifications ?? [];
  const totalCount = data?.totalCount ?? 0;
  const unreadCount = countData?.unreadCount ?? data?.unreadCount ?? 0;
  const totalPages = Math.max(1, Math.ceil(totalCount / PAGE_SIZE));

  const handleTabChange = useCallback(
    (value: string) => {
      setTypeFilter(value as NotificationType | "all");
      setPage(1);
      setSelected(new Set());
    },
    []
  );

  const handleToggleSelect = useCallback((id: number) => {
    setSelected((prev) => {
      const next = new Set(prev);
      if (next.has(id)) {
        next.delete(id);
      } else {
        next.add(id);
      }
      return next;
    });
  }, []);

  const handleSelectAll = useCallback(() => {
    if (selected.size === notifications.length) {
      setSelected(new Set());
    } else {
      setSelected(new Set(notifications.map((n) => n.id)));
    }
  }, [selected.size, notifications]);

  const handleMarkSelectedRead = useCallback(() => {
    const ids = Array.from(selected);
    if (ids.length === 0) return;
    markRead.mutate(ids, {
      onSuccess: (res) => {
        showToast(`Marked ${res.updated} notification${res.updated !== 1 ? "s" : ""} as read`, "success");
        setSelected(new Set());
      },
      onError: () => showToast("Failed to mark notifications as read", "error"),
    });
  }, [selected, markRead, showToast]);

  const handleMarkAllRead = useCallback(() => {
    markAllRead.mutate(undefined, {
      onSuccess: (res) => {
        showToast(`Marked ${res.updated} notification${res.updated !== 1 ? "s" : ""} as read`, "success");
        setSelected(new Set());
      },
      onError: () => showToast("Failed to mark all as read", "error"),
    });
  }, [markAllRead, showToast]);

  const handleDeleteSelected = useCallback(() => {
    const ids = Array.from(selected);
    if (ids.length === 0) return;
    deleteNotifications.mutate(ids, {
      onSuccess: (res) => {
        showToast(`Deleted ${res.deleted} notification${res.deleted !== 1 ? "s" : ""}`, "success");
        setSelected(new Set());
      },
      onError: () => showToast("Failed to delete notifications", "error"),
    });
  }, [selected, deleteNotifications, showToast]);

  const handleNotificationClick = useCallback(
    (notif: Notification) => {
      if (!notif.isRead) {
        markRead.mutate([notif.id]);
      }
      if (notif.link) {
        navigate(notif.link);
      }
    },
    [markRead, navigate]
  );

  const isBusy = markRead.isPending || markAllRead.isPending || deleteNotifications.isPending;

  return (
    <div className="space-y-4">
      {/* Header */}
      <div className="flex items-center justify-between flex-wrap gap-3">
        <div className="flex items-center gap-3">
          <div className="w-9 h-9 rounded-xl bg-[hsl(var(--primary)/0.1)] grid place-items-center">
            <Bell className="w-4 h-4 text-[hsl(var(--primary))]" />
          </div>
          <div className="flex items-center gap-2">
            <h2 className="text-lg font-semibold">Notifications</h2>
            {unreadCount > 0 && (
              <Badge variant="default" className="text-[10px]">
                {unreadCount} unread
              </Badge>
            )}
          </div>
        </div>

        <div className="flex items-center gap-2">
          {selected.size > 0 && (
            <>
              <Button
                variant="outline"
                size="sm"
                onClick={handleMarkSelectedRead}
                disabled={isBusy}
              >
                <Check className="w-3.5 h-3.5" />
                Mark read ({selected.size})
              </Button>
              <Button
                variant="outline"
                size="sm"
                onClick={handleDeleteSelected}
                disabled={isBusy}
              >
                <Trash2 className="w-3.5 h-3.5" />
                Delete ({selected.size})
              </Button>
              <Separator orientation="vertical" className="h-6" />
            </>
          )}
          <Button
            variant="ghost"
            size="sm"
            onClick={handleMarkAllRead}
            disabled={isBusy || unreadCount === 0}
          >
            <CheckCheck className="w-3.5 h-3.5" />
            Mark all read
          </Button>
        </div>
      </div>

      {/* Filter tabs */}
      <Tabs value={typeFilter} onValueChange={handleTabChange}>
        <TabsList>
          <TabsTrigger value="all">All</TabsTrigger>
          <TabsTrigger value="system">System</TabsTrigger>
          <TabsTrigger value="alert">Alerts</TabsTrigger>
          <TabsTrigger value="flow-status">Flow Status</TabsTrigger>
          <TabsTrigger value="security">Security</TabsTrigger>
        </TabsList>
      </Tabs>

      {/* Notification list */}
      {isLoading ? (
        <div className="flex justify-center py-12">
          <Loader2 className="w-6 h-6 animate-spin text-muted-foreground" />
        </div>
      ) : notifications.length === 0 ? (
        <div className="glass-panel rounded-[var(--radius)] p-8 text-center space-y-3">
          <Inbox className="w-8 h-8 text-muted-foreground mx-auto" />
          <p className="text-sm text-muted-foreground">No notifications</p>
          <p className="text-xs text-muted-foreground max-w-md mx-auto">
            {typeFilter !== "all"
              ? `No ${TYPE_CONFIG[typeFilter as NotificationType]?.label.toLowerCase() ?? typeFilter} notifications found.`
              : "You're all caught up. Notifications will appear here when system events, alerts, or flow status changes occur."}
          </p>
        </div>
      ) : (
        <div className="space-y-2">
          {/* Select-all row */}
          {notifications.length > 0 && (
            <div className="flex items-center gap-2 px-1">
              <input
                type="checkbox"
                checked={selected.size === notifications.length && notifications.length > 0}
                onChange={handleSelectAll}
                className="w-3.5 h-3.5 rounded accent-[hsl(var(--primary))] cursor-pointer"
              />
              <span className="text-xs text-muted-foreground">
                {selected.size > 0
                  ? `${selected.size} selected`
                  : `Select all (${notifications.length})`}
              </span>
            </div>
          )}

          {notifications.map((notif) => {
            const config = TYPE_CONFIG[notif.type] ?? TYPE_CONFIG.system;
            const Icon = config.icon;
            const isSelected = selected.has(notif.id);

            return (
              <div
                key={notif.id}
                className={cn(
                  "glass-panel rounded-[var(--radius)] p-4 flex items-start gap-3 transition-all",
                  notif.link && "cursor-pointer hover:bg-[hsl(var(--muted)/0.2)]",
                  !notif.isRead && "border-l-2 border-l-[hsl(var(--primary))]",
                  isSelected && "ring-1 ring-[hsl(var(--primary)/0.5)]"
                )}
              >
                {/* Checkbox */}
                <input
                  type="checkbox"
                  checked={isSelected}
                  onChange={() => handleToggleSelect(notif.id)}
                  onClick={(e) => e.stopPropagation()}
                  className="w-3.5 h-3.5 mt-1 rounded accent-[hsl(var(--primary))] cursor-pointer shrink-0"
                />

                {/* Type icon */}
                <div
                  className={cn(
                    "w-9 h-9 rounded-xl grid place-items-center shrink-0",
                    config.bg
                  )}
                  onClick={() => handleNotificationClick(notif)}
                >
                  <Icon className={cn("w-4 h-4", config.color)} />
                </div>

                {/* Content */}
                <div
                  className="flex-1 min-w-0"
                  onClick={() => handleNotificationClick(notif)}
                >
                  <div className="flex items-center gap-2">
                    <p
                      className={cn(
                        "text-sm truncate",
                        !notif.isRead ? "font-semibold" : "font-medium text-muted-foreground"
                      )}
                    >
                      {notif.title}
                    </p>
                    <Badge
                      variant={
                        notif.type === "alert"
                          ? "warning"
                          : notif.type === "security"
                          ? "destructive"
                          : notif.type === "flow-status"
                          ? "success"
                          : "default"
                      }
                      className="text-[10px] shrink-0"
                    >
                      {config.label}
                    </Badge>
                  </div>
                  <p className="text-xs text-muted-foreground mt-0.5 line-clamp-2">
                    {notif.message}
                  </p>
                </div>

                {/* Time + read indicator */}
                <div className="flex items-center gap-2 shrink-0 mt-0.5">
                  <span className="text-[11px] text-muted-foreground whitespace-nowrap">
                    {formatRelativeTime(notif.createdAt)}
                  </span>
                  {!notif.isRead && (
                    <div className="w-2 h-2 rounded-full bg-[hsl(var(--primary))]" />
                  )}
                </div>
              </div>
            );
          })}
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
