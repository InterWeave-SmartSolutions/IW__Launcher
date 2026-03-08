import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { apiFetch } from "@/lib/api";
import type {
  WizardConfigResponse,
  WizardConfigSaveRequest,
  CredentialsResponse,
  CredentialSaveRequest,
  CredentialTestRequest,
  CredentialTestResponse,
  ProfilesResponse,
} from "@/types/configuration";
import type { ApiMessageResponse } from "@/types/profile";

export function useWizardConfig() {
  return useQuery({
    queryKey: ["config", "wizard"],
    queryFn: () => apiFetch<WizardConfigResponse>("/api/config/wizard"),
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
      queryClient.invalidateQueries({ queryKey: ["config", "wizard"] });
      queryClient.invalidateQueries({ queryKey: ["company-profile"] });
    },
  });
}

export function useCredentials() {
  return useQuery({
    queryKey: ["config", "credentials"],
    queryFn: () => apiFetch<CredentialsResponse>("/api/config/credentials"),
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
      queryClient.invalidateQueries({ queryKey: ["config", "credentials"] });
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
  return useQuery({
    queryKey: ["config", "profiles"],
    queryFn: () => apiFetch<ProfilesResponse>("/api/config/profiles"),
  });
}
