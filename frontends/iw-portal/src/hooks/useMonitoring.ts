import { useQuery } from "@tanstack/react-query";
import { apiFetch } from "@/lib/api";
import { useAuth } from "@/providers/AuthProvider";
import type {
  DashboardResponse,
  TransactionsResponse,
  MetricsResponse,
  MetricsDataPoint,
  AlertRulesResponse,
} from "@/types/monitoring";

export function useDashboard() {
  const { user } = useAuth();
  return useQuery({
    queryKey: ["monitoring", user?.companyId, "dashboard"],
    queryFn: () => apiFetch<DashboardResponse>("/api/monitoring/dashboard"),
    enabled: !!user,
    refetchInterval: 30_000,
    staleTime: 10_000,
  });
}

export function useTransactions(page = 1, pageSize = 20) {
  const { user } = useAuth();
  return useQuery({
    queryKey: ["monitoring", user?.companyId, "transactions", page, pageSize],
    queryFn: () =>
      apiFetch<TransactionsResponse>(
        `/api/monitoring/transactions?page=${page}&page_size=${pageSize}`
      ),
    enabled: !!user,
    staleTime: 10_000,
  });
}

export function useMetrics(
  granularity: "hourly" | "daily" | "weekly" = "daily",
  period: "24h" | "7d" | "30d" | "90d" = "7d"
) {
  const { user } = useAuth();
  return useQuery({
    queryKey: ["monitoring", user?.companyId, "metrics", granularity, period],
    queryFn: async () => {
      const raw = await apiFetch<MetricsResponse>(
        `/api/monitoring/metrics?granularity=${granularity}&period=${period}`
      );
      // Transform Chart.js shape → Recharts-friendly array
      const points: MetricsDataPoint[] = raw.data.labels.map((label, i) => ({
        date: label,
        success: raw.data.datasets[0]?.data[i] ?? 0,
        failed: raw.data.datasets[1]?.data[i] ?? 0,
        avgDuration: raw.data.datasets[2]?.data[i] ?? 0,
        records: raw.data.datasets[3]?.data[i] ?? 0,
      }));
      return { points, summary: raw.data.summary };
    },
    enabled: !!user,
    staleTime: 30_000,
  });
}

export function useAlertRules() {
  const { user } = useAuth();
  return useQuery({
    queryKey: ["monitoring", user?.companyId, "alerts"],
    queryFn: () => apiFetch<AlertRulesResponse>("/api/monitoring/alerts"),
    enabled: !!user,
    staleTime: 15_000,
  });
}
