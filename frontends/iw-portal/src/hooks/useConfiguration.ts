import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { apiFetch, ApiError } from "@/lib/api";
import { useAuth } from "@/providers/AuthProvider";
import type {
  WizardConfigResponse,
  WizardConfigSaveRequest,
  WizardConfigConflictResponse,
  CredentialsResponse,
  CredentialSaveRequest,
  CredentialTestRequest,
  CredentialTestResponse,
  ProfilesResponse,
} from "@/types/configuration";
import type { ApiMessageResponse } from "@/types/profile";
import type { SyncActionResponse } from "@/types/sync";

/** Check if an error is a 409 config conflict with diff data */
export function isConfigConflict(
  error: unknown
): error is ApiError & { body: WizardConfigConflictResponse } {
  return error instanceof ApiError && error.status === 409 && error.body?.conflict === true;
}

export function useWizardConfig() {
  const { user } = useAuth();
  return useQuery({
    queryKey: ["config", user?.companyId, "wizard"],
    queryFn: () => apiFetch<WizardConfigResponse>("/api/config/wizard"),
    enabled: !!user,
  });
}

export function useSaveWizardConfig() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (data: WizardConfigSaveRequest) =>
      apiFetch<ApiMessageResponse>("/api/config/wizard", {
        method: "PUT",
        body: JSON.stringify(data),
      }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["config"] });
      queryClient.invalidateQueries({ queryKey: ["company-profile"] });
      // Auto-push to IDE workspace so changes appear immediately
      apiFetch<SyncActionResponse>("/api/sync/push", {
        method: "POST",
        body: JSON.stringify({}),
      })
        .then(() => queryClient.invalidateQueries({ queryKey: ["sync-status"] }))
        .catch(() => {/* sync push is best-effort; wizard save already succeeded */});
    },
  });
}

export function useCredentials() {
  const { user } = useAuth();
  return useQuery({
    queryKey: ["config", user?.companyId, "credentials"],
    queryFn: () => apiFetch<CredentialsResponse>("/api/config/credentials"),
    enabled: !!user,
  });
}

export function useSaveCredential() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (data: CredentialSaveRequest) =>
      apiFetch<ApiMessageResponse>("/api/config/credentials", {
        method: "PUT",
        body: JSON.stringify(data),
      }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["config"] });
    },
  });
}

export function useTestCredential() {
  return useMutation({
    mutationFn: (data: CredentialTestRequest) =>
      apiFetch<CredentialTestResponse>("/api/config/credentials/test", {
        method: "POST",
        body: JSON.stringify(data),
      }),
  });
}

export function useProfiles() {
  const { user } = useAuth();
  return useQuery({
    queryKey: ["config", user?.companyId, "profiles"],
    queryFn: () => apiFetch<ProfilesResponse>("/api/config/profiles"),
    enabled: !!user,
  });
}
