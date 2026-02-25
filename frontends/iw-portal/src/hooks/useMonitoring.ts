import { useQuery } from "@tanstack/react-query";
import { apiFetch } from "@/lib/api";
import type { DashboardResponse, TransactionsResponse } from "@/types/monitoring";

export function useDashboard() {
  return useQuery({
    queryKey: ["monitoring", "dashboard"],
    queryFn: () => apiFetch<DashboardResponse>("/api/monitoring/dashboard"),
    refetchInterval: 30_000, // auto-refresh every 30s
    staleTime: 10_000,
  });
}

export function useTransactions(page = 1, pageSize = 20) {
  return useQuery({
    queryKey: ["monitoring", "transactions", page, pageSize],
    queryFn: () =>
      apiFetch<TransactionsResponse>(
        `/api/monitoring/transactions?page=${page}&page_size=${pageSize}`
      ),
    staleTime: 10_000,
  });
}
