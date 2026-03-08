import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { apiFetch } from "@/lib/api";
import type {
  NotificationsResponse,
  NotificationType,
  UnreadCountResponse,
} from "@/types/notifications";

export function useNotifications(
  page = 1,
  pageSize = 20,
  type: NotificationType | "all" = "all",
  unreadOnly = false
) {
  return useQuery({
    queryKey: ["notifications", page, pageSize, type, unreadOnly],
    queryFn: () => {
      const params = new URLSearchParams({
        page: String(page),
        page_size: String(pageSize),
        type,
        unread_only: String(unreadOnly),
      });
      return apiFetch<NotificationsResponse>(
        `/api/notifications?${params.toString()}`
      );
    },
    staleTime: 10_000,
  });
}

export function useUnreadCount() {
  return useQuery({
    queryKey: ["notifications", "unreadCount"],
    queryFn: () =>
      apiFetch<UnreadCountResponse>("/api/notifications/count"),
    refetchInterval: 30_000,
    staleTime: 10_000,
  });
}

export function useMarkRead() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (notificationIds: number[]) =>
      apiFetch<{ success: boolean; updated: number }>(
        "/api/notifications/read",
        {
          method: "PUT",
          body: JSON.stringify({ notificationIds }),
        }
      ),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["notifications"] });
    },
  });
}

export function useMarkAllRead() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: () =>
      apiFetch<{ success: boolean; updated: number }>(
        "/api/notifications/read-all",
        { method: "PUT" }
      ),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["notifications"] });
    },
  });
}

export function useDeleteNotifications() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (notificationIds: number[]) =>
      apiFetch<{ success: boolean; deleted: number }>(
        "/api/notifications",
        {
          method: "DELETE",
          body: JSON.stringify({ notificationIds }),
        }
      ),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["notifications"] });
    },
  });
}
