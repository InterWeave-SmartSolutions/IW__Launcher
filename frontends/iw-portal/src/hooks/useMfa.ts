import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { apiFetch } from "@/lib/api";
import type {
  MfaStatusResponse,
  MfaSetupResponse,
  MfaVerifyResponse,
  MfaValidateResponse,
  MfaDisableResponse,
  PasswordResetResponse,
} from "@/types/mfa";

export function useMfaStatus() {
  return useQuery({
    queryKey: ["mfa-status"],
    queryFn: () => apiFetch<MfaStatusResponse>("/api/auth/mfa/status"),
  });
}

export function useMfaSetup() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: () =>
      apiFetch<MfaSetupResponse>("/api/auth/mfa/setup", {
        method: "POST",
      }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["mfa-status"] });
    },
  });
}

export function useMfaVerify() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (data: { code: string }) =>
      apiFetch<MfaVerifyResponse>("/api/auth/mfa/verify", {
        method: "POST",
        body: JSON.stringify(data),
      }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["mfa-status"] });
    },
  });
}

export function useMfaValidate() {
  return useMutation({
    mutationFn: (data: { code?: string; backupCode?: string }) =>
      apiFetch<MfaValidateResponse>("/api/auth/mfa/validate", {
        method: "POST",
        body: JSON.stringify(data),
      }),
  });
}

export function useMfaDisable() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (data: { password: string }) =>
      apiFetch<MfaDisableResponse>("/api/auth/mfa/disable", {
        method: "DELETE",
        body: JSON.stringify(data),
      }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["mfa-status"] });
    },
  });
}

export function useRequestPasswordReset() {
  return useMutation({
    mutationFn: (data: { email: string }) =>
      apiFetch<PasswordResetResponse>("/api/auth/password-reset", {
        method: "POST",
        body: JSON.stringify({ action: "request", email: data.email }),
      }),
  });
}

export function useValidateResetToken() {
  return useMutation({
    mutationFn: (data: { token: string }) =>
      apiFetch<PasswordResetResponse>("/api/auth/password-reset", {
        method: "POST",
        body: JSON.stringify({ action: "validate", token: data.token }),
      }),
  });
}

export function useResetPassword() {
  return useMutation({
    mutationFn: (data: { token: string; newPassword: string; confirmPassword: string }) =>
      apiFetch<PasswordResetResponse>("/api/auth/password-reset", {
        method: "POST",
        body: JSON.stringify({ action: "reset", ...data }),
      }),
  });
}
