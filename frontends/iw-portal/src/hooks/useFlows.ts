import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { apiFetch } from "@/lib/api";
import { useAuth } from "@/providers/AuthProvider";
import type { FlowsResponse, FlowActionResponse, FlowScheduleResponse, FlowPropertiesResponse } from "@/types/flows";

export function useEngineFlows(autoRefresh = true) {
  const { user } = useAuth();
  return useQuery({
    queryKey: ["engine-flows", user?.companyId],
    queryFn: () => apiFetch<FlowsResponse>("/api/flows"),
    enabled: !!user,
    refetchInterval: autoRefresh ? 10_000 : false,
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


export function useFlowProperties(flowId: string | null, isFlow: boolean) {
  return useQuery({
    queryKey: ["flow-properties", flowId, isFlow],
    queryFn: () =>
      apiFetch<FlowPropertiesResponse>(
        `/api/flows/properties?flowId=${encodeURIComponent(flowId!)}&isFlow=${isFlow ? "1" : "0"}`
      ),
    enabled: !!flowId,
  });
}

export function useSaveFlowProperties() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: async (data: {
      flowId: string;
      profileName: string;
      isFlow: boolean;
      properties: Record<string, string>;
    }) => {
      const form = new URLSearchParams();
      form.set("FlowId", data.flowId);
      form.set("ProfileId", data.profileName);
      form.set("IsFlow", data.isFlow ? "1" : "0");
      form.set("IsAdmin", "false");
      form.set("WhoAmI", "");
      form.set("Env2Con", "COM");
      for (const [key, value] of Object.entries(data.properties)) {
        form.set(`PV:${key}`, value);
      }
      // NOTE: legacy servlet name has typo ("Properies" not "Properties") — matches web.xml mapping
      const res = await fetch("/iw-business-daemon/FlowProperiesServlet", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: form.toString(),
        redirect: "manual",
      });
      if (!(res.type === "opaqueredirect" || res.ok || res.status === 302)) {
        throw new Error(`Save failed (HTTP ${res.status})`);
      }

      // Verify persistence: re-fetch properties and compare values
      const verify = await apiFetch<FlowPropertiesResponse>(
        `/api/flows/properties?flowId=${encodeURIComponent(data.flowId)}&isFlow=${data.isFlow ? "1" : "0"}`
      );
      if (!verify.success || !verify.data) {
        throw new Error("Save appeared to succeed but could not verify — re-open to check values");
      }
      // Compare submitted values against what the backend now reports
      for (const [key, expected] of Object.entries(data.properties)) {
        const actual = verify.data.properties.find((p) => p.name === key);
        if (actual && actual.value !== expected) {
          throw new Error(`Property "${key}" was not saved — the flow may be running or read-only`);
        }
      }
      return { success: true, verified: true };
    },
    onSuccess: (_data, vars) => {
      qc.invalidateQueries({ queryKey: ["flow-properties", vars.flowId] });
    },
  });
}

export function useInitializeProfile() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: () =>
      apiFetch<{ success: boolean; message: string; flowCount: number; queryCount: number }>(
        "/api/flows/initialize",
        { method: "POST", body: "{}" }
      ),
    onSuccess: () => qc.invalidateQueries({ queryKey: ["engine-flows"] }),
  });
}

export function useSubmitFlows() {
  return useMutation({
    mutationFn: () =>
      apiFetch<{ success: boolean; message: string }>("/api/flows/submit", {
        method: "POST",
        body: "{}",
      }),
    // No query invalidation — auto-refresh (10s) will pick up changes.
    // Eagerly invalidating here races against the engine's save cycle and
    // causes Start/Stop buttons to briefly flash the wrong state.
  });
}
