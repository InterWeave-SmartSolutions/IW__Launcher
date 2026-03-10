import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { apiFetch } from "@/lib/api";

export interface EngineStatus {
  success: boolean;
  engineUp: boolean;
  httpCode: number;
  responseMs: number;
  tsUrl: string;
  error?: string;
  responseExcerpt?: string;
}

export interface EngineTestResult {
  success: boolean;
  flow: string;
  httpCode: number;
  responseMs: number;
  error?: string;
  rawXml?: string;
}

export function useEngineStatus() {
  return useQuery({
    queryKey: ["engine", "status"],
    queryFn: () => apiFetch<EngineStatus>("/api/engine/status"),
    refetchInterval: 30_000,
    staleTime: 0,
  });
}

export interface EngineRecordResult {
  success: boolean;
  executionId: string;
  flow: string;
  status: string;
  httpCode: number;
  responseMs: number;
  error?: string;
}

export interface SeedResult {
  success: boolean;
  inserted: number;
  message: string;
}

export function useEngineTest() {
  return useMutation({
    mutationFn: (flow: string = "sessionvars") =>
      apiFetch<EngineTestResult>(`/api/engine/test?flow=${encodeURIComponent(flow)}`),
  });
}

/** Calls the TS and records the result in transaction_executions */
export function useEngineRecord() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: () =>
      apiFetch<EngineRecordResult>("/api/engine/record", { method: "POST", body: "{}" }),
    onSuccess: () => {
      // Invalidate monitoring queries so the dashboard refreshes
      void queryClient.invalidateQueries({ queryKey: ["monitoring"] });
    },
  });
}

/** Admin-only: seed N synthetic transaction records for dashboard testing */
export function useSeedTransactions() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({ count = 20, days = 1 }: { count?: number; days?: number } = {}) =>
      apiFetch<SeedResult>(`/api/engine/seed?count=${count}&days=${days}`, { method: "POST", body: "{}" }),
    onSuccess: () => {
      void queryClient.invalidateQueries({ queryKey: ["monitoring"] });
    },
  });
}
