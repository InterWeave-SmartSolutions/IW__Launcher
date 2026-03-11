import { useEffect, useRef } from "react";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { apiFetch } from "@/lib/api";
import type {
  SyncStatusResponse,
  SyncLogResponse,
  SyncActionResponse,
} from "@/types/sync";

/**
 * Poll sync status and auto-invalidate config/flow queries when IDE
 * pushes changes (i.e. a profile transitions to "in_sync" from another state,
 * meaning the sync bridge just imported an IDE change into the DB).
 */
export function useSyncStatus(autoRefresh = true) {
  const qc = useQueryClient();
  const prevStatuses = useRef<Record<string, string>>({});

  const query = useQuery({
    queryKey: ["sync-status"],
    queryFn: () => apiFetch<SyncStatusResponse>("/api/sync/status"),
    refetchInterval: autoRefresh ? 10_000 : false,
  });

  useEffect(() => {
    if (!query.data?.data?.profiles) return;

    const profiles = query.data.data.profiles;
    const prev = prevStatuses.current;
    let changed = false;

    for (const p of profiles) {
      const oldStatus = prev[p.profileName];
      // Detect when sync bridge imported IDE changes (workspace_ahead → in_sync)
      // or when portal pushed to IDE (db_ahead → in_sync)
      if (oldStatus && oldStatus !== p.syncStatus && p.syncStatus === "in_sync") {
        changed = true;
      }
      prev[p.profileName] = p.syncStatus;
    }

    if (changed) {
      // IDE just synced — refresh config and flow data
      qc.invalidateQueries({ queryKey: ["config"] });
      qc.invalidateQueries({ queryKey: ["engine-flows"] });
      qc.invalidateQueries({ queryKey: ["company-profile"] });
    }
  }, [query.data, qc]);

  return query;
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
