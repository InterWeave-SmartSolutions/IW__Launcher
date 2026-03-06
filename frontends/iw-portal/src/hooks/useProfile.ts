import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { apiFetch } from "@/lib/api";
import type {
  ProfileResponse,
  ProfileUpdateRequest,
  CompanyProfileResponse,
  CompanyProfileUpdateRequest,
  PasswordChangeRequest,
  ApiMessageResponse,
} from "@/types/profile";

export function useProfile() {
  return useQuery({
    queryKey: ["profile"],
    queryFn: () => apiFetch<ProfileResponse>("/api/profile"),
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
  return useQuery({
    queryKey: ["company-profile"],
    queryFn: () => apiFetch<CompanyProfileResponse>("/api/company/profile"),
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
