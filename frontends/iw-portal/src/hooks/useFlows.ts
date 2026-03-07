import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { apiFetch } from "@/lib/api";
import type { FlowsResponse, FlowActionResponse, FlowScheduleResponse } from "@/types/flows";

export function useEngineFlows() {
  return useQuery({
    queryKey: ["engine-flows"],
    queryFn: () => apiFetch<FlowsResponse>("/api/flows"),
    refetchInterval: 10_000, // Poll every 10s for live state
  });
}

export function useStartFlow() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (flowIndex: number) =>
      apiFetch<FlowActionResponse>("/api/flows/start", {
        method: "POST",
        body: JSON.stringify({ flowIndex }),
      }),
    onSuccess: () => qc.invalidateQueries({ queryKey: ["engine-flows"] }),
  });
}

export function useStopFlow() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (flowIndex: number) =>
      apiFetch<FlowActionResponse>("/api/flows/stop", {
        method: "POST",
        body: JSON.stringify({ flowIndex }),
      }),
    onSuccess: () => qc.invalidateQueries({ queryKey: ["engine-flows"] }),
  });
}

export function useUpdateFlowSchedule() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (data: { flowIndex: number; interval?: number; shift?: number; counter?: number }) =>
      apiFetch<FlowScheduleResponse>("/api/flows/schedule", {
        method: "PUT",
        body: JSON.stringify(data),
      }),
    onSuccess: () => qc.invalidateQueries({ queryKey: ["engine-flows"] }),
  });
}

export function useSubmitFlows() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: () =>
      apiFetch<{ success: boolean; message: string }>("/api/flows/submit", {
        method: "POST",
        body: "{}",
      }),
    onSuccess: () => qc.invalidateQueries({ queryKey: ["engine-flows"] }),
  });
}
