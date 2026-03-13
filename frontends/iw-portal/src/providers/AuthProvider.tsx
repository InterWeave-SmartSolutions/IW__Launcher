import { createContext, useContext, useState, useEffect, useCallback, useRef, type ReactNode } from "react";
import { useQueryClient } from "@tanstack/react-query";
import { apiFetch, getAuthToken, setAuthToken, clearAuthToken } from "@/lib/api";
import type { User, LoginRequest, LoginResponse, SessionResponse } from "@/types/auth";

interface AuthState {
  user: User | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  error: string | null;
  mfaRequired: boolean;
  login: (creds: LoginRequest) => Promise<void>;
  logout: () => Promise<void>;
}

const AuthContext = createContext<AuthState | null>(null);

export function AuthProvider({ children }: { children: ReactNode }) {
  const queryClient = useQueryClient();
  const [user, setUser] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [mfaRequired, setMfaRequired] = useState(false);
  const checkingRef = useRef(false);

  async function checkSession() {
    // Prevent concurrent checks (e.g. rapid tab switches)
    if (checkingRef.current) return;
    checkingRef.current = true;

    // Gate on Bearer token: if there's no token in localStorage, the user
    // hasn't logged in via the React UI. Don't inherit JSP-only sessions —
    // classic UI login sets a JSESSIONID but never stores a Bearer token.
    const token = getAuthToken();
    if (!token) {
      queryClient.clear();
      setUser(null);
      checkingRef.current = false;
      setIsLoading(false);
      return;
    }

    const controller = new AbortController();
    const timer = setTimeout(() => controller.abort(), 5000);
    try {
      const res = await apiFetch<SessionResponse>("/api/auth/session", {
        signal: controller.signal,
      });
      if (res.authenticated && res.user) {
        setUser(res.user);
      } else {
        clearAuthToken();
        queryClient.clear();
        setUser(null);
      }
    } catch {
      clearAuthToken();
      setUser(null);
    } finally {
      clearTimeout(timer);
      checkingRef.current = false;
      setIsLoading(false);
    }
  }

  // Check session on mount
  useEffect(() => {
    checkSession();
  }, []);

  // Re-validate session when the tab regains focus. This catches external
  // logouts (e.g. user logged out from the classic JSP UI in another tab).
  useEffect(() => {
    function handleVisibility() {
      if (document.visibilityState === "visible" && user) {
        checkSession();
      }
    }
    document.addEventListener("visibilitychange", handleVisibility);
    return () => document.removeEventListener("visibilitychange", handleVisibility);
  }, [user]);

  const login = useCallback(async (creds: LoginRequest) => {
    setError(null);
    setMfaRequired(false);
    const res = await apiFetch<LoginResponse>("/api/auth/login", {
      method: "POST",
      body: JSON.stringify(creds),
    });
    if (res.success && res.mfaRequired) {
      setMfaRequired(true);
      return;
    }
    if (res.success && res.user) {
      // Clear any cached data from a previous session before setting new user,
      // so different accounts never see each other's data.
      queryClient.clear();
      // Store token for proxy deployments (Vercel) where cookies don't survive
      if (res.token) {
        setAuthToken(res.token);
      }
      setUser(res.user);
    } else {
      throw new Error(res.error ?? "Login failed");
    }
  }, [queryClient]);

  const logout = useCallback(async () => {
    try {
      // Use the new API logout that properly calls session.invalidate()
      await apiFetch("/api/auth/logout", { method: "POST" });
    } catch {
      // Ignore network errors — we clear local state regardless
    }
    try {
      // Also hit the legacy LogoutServlet for the iw-business-daemon JSESSIONID
      await fetch("/iw-business-daemon/LogoutServlet", {
        credentials: "include",
      }).catch(() => {});
    } catch {
      // Ignore — belt-and-suspenders
    }
    clearAuthToken();
    queryClient.clear();
    setUser(null);
  }, [queryClient]);

  return (
    <AuthContext.Provider
      value={{
        user,
        isAuthenticated: user !== null,
        isLoading,
        error,
        mfaRequired,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth(): AuthState {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("useAuth must be used within <AuthProvider>");
  return ctx;
}
