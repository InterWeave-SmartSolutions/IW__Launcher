import { useQuery } from "@tanstack/react-query";
import { apiFetch } from "@/lib/api";
import type {
  AuditLogResponse,
  AuditStatsResponse,
  AuditUsersResponse,
} from "@/types/audit";

export interface AuditFilters {
  actionType?: string;
  userId?: number;
  dateFrom?: string;
  dateTo?: string;
  search?: string;
}

function buildQueryString(
  page: number,
  pageSize: number,
  filters: AuditFilters
): string {
  const params = new URLSearchParams();
  params.set("page", String(page));
  params.set("page_size", String(pageSize));
  if (filters.actionType) params.set("action_type", filters.actionType);
  if (filters.userId !== undefined) params.set("user_id", String(filters.userId));
  if (filters.dateFrom) params.set("date_from", filters.dateFrom);
  if (filters.dateTo) params.set("date_to", filters.dateTo);
  if (filters.search) params.set("search", filters.search);
  return params.toString();
}

export function useAuditLog(
  page: number,
  pageSize: number,
  filters: AuditFilters
) {
  return useQuery({
    queryKey: ["audit", "entries", page, pageSize, filters],
    queryFn: () =>
      apiFetch<AuditLogResponse>(
        `/api/admin/audit?${buildQueryString(page, pageSize, filters)}`
      ),
    staleTime: 10_000,
  });
}

export function useAuditStats() {
  return useQuery({
    queryKey: ["audit", "stats"],
    queryFn: () =>
      apiFetch<AuditStatsResponse>("/api/admin/audit/stats"),
    staleTime: 60_000,
  });
}

export function useAuditUsers() {
  return useQuery({
    queryKey: ["audit", "users"],
    queryFn: () =>
      apiFetch<AuditUsersResponse>("/api/admin/audit/users"),
    staleTime: 300_000,
  });
}
