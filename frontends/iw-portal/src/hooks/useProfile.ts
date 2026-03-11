import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { apiFetch } from "@/lib/api";
import { useAuth } from "@/providers/AuthProvider";
import type {
  ProfileResponse,
  ProfileUpdateRequest,
  CompanyProfileResponse,
  CompanyProfileUpdateRequest,
  PasswordChangeRequest,
  ApiMessageResponse,
} from "@/types/profile";

export function useProfile() {
  const { user } = useAuth();
  return useQuery({
    queryKey: ["profile", user?.userId],
    queryFn: () => apiFetch<ProfileResponse>("/api/profile"),
    enabled: !!user,
  });
}

export function useUpdateProfile() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (data: ProfileUpdateRequest) =>
      apiFetch<ApiMessageResponse>("/api/profile", {
        method: "PUT",
        body: JSON.stringify(data),
      }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["profile"] });
    },
  });
}

export function useChangePassword() {
  return useMutation({
    mutationFn: (data: PasswordChangeRequest) =>
      apiFetch<ApiMessageResponse>("/api/profile", {
        method: "POST",
        body: JSON.stringify(data),
      }),
  });
}

export function useCompanyProfile() {
  const { user } = useAuth();
  return useQuery({
    queryKey: ["company-profile", user?.companyId],
    queryFn: () => apiFetch<CompanyProfileResponse>("/api/company/profile"),
    enabled: !!user,
  });
}

export function useUpdateCompanyProfile() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (data: CompanyProfileUpdateRequest) =>
      apiFetch<ApiMessageResponse>("/api/company/profile", {
        method: "PUT",
        body: JSON.stringify(data),
      }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["company-profile"] });
    },
  });
}

export function useChangeCompanyPassword() {
  return useMutation({
    mutationFn: (data: PasswordChangeRequest) =>
      apiFetch<ApiMessageResponse>("/api/company/profile", {
        method: "POST",
        body: JSON.stringify(data),
      }),
  });
}
