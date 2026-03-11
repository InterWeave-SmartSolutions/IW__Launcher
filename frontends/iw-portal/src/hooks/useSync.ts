import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { apiFetch } from "@/lib/api";
import type {
  SyncStatusResponse,
  SyncLogResponse,
  SyncActionResponse,
} from "@/types/sync";

export function useSyncStatus(autoRefresh = true) {
  return useQuery({
    queryKey: ["sync-status"],
    queryFn: () => apiFetch<SyncStatusResponse>("/api/sync/status"),
    refetchInterval: autoRefresh ? 15_000 : false,
  });
}

export function useSyncLog(lines = 60) {
  return useQuery({
    queryKey: ["sync-log", lines],
    queryFn: () => apiFetch<SyncLogResponse>(`/api/sync/log?lines=${lines}`),
    refetchInterval: 10_000,
  });
}

export function usePushToIDE() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (profileName?: string) =>
      apiFetch<SyncActionResponse>("/api/sync/push", {
        method: "POST",
        body: JSON.stringify(profileName ? { profileName } : {}),
      }),
    onSuccess: () => qc.invalidateQueries({ queryKey: ["sync-status"] }),
  });
}

export function usePullFromIDE() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: ({ profileName, projectName }: { profileName: string; projectName?: string }) =>
      apiFetch<SyncActionResponse>("/api/sync/pull", {
        method: "POST",
        body: JSON.stringify({ profileName, projectName }),
      }),
    onSuccess: () => qc.invalidateQueries({ queryKey: ["sync-status"] }),
  });
}
