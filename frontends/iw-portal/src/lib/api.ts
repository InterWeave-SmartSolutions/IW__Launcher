// Allow VITE_API_BASE_URL override for Vercel / external deployments
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const BASE_URL: string = (import.meta as any).env?.VITE_API_BASE_URL || "/iw-business-daemon";

const TOKEN_KEY = "iw_auth_token";

export function getAuthToken(): string | null {
  return localStorage.getItem(TOKEN_KEY);
}

export function setAuthToken(token: string): void {
  localStorage.setItem(TOKEN_KEY, token);
}

export function clearAuthToken(): void {
  localStorage.removeItem(TOKEN_KEY);
}

export class ApiError extends Error {
  /** Full parsed JSON body from the error response (if available). */
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  public body?: any;
  constructor(public status: number, message: string, body?: unknown) {
    super(message);
    this.name = "ApiError";
    this.body = body;
  }
}

export async function apiFetch<T>(
  endpoint: string,
  options?: RequestInit
): Promise<T> {
  const url = endpoint.startsWith("http") ? endpoint : `${BASE_URL}${endpoint}`;

  const headers: Record<string, string> = {
    "Content-Type": "application/json",
    // Bypasses localtunnel's browser confirmation page when Vercel proxies via loca.lt.
    // Forwarded transparently by Vercel's rewrite and ignored by Cloudflare/direct.
    "bypass-tunnel-reminder": "true",
  };

  // Include Bearer token for proxy deployments where cookies don't survive
  const token = getAuthToken();
  if (token) {
    headers["Authorization"] = `Bearer ${token}`;
  }

  const res = await fetch(url, {
    credentials: "include", // still send cookies for direct/local access
    headers: {
      ...headers,
      ...options?.headers,
    },
    ...options,
  });

  if (!res.ok) {
    const body = await res.json().catch(() => ({ message: res.statusText }));
    const msg = typeof body.error === "string" ? body.error : body.error?.message ?? body.message ?? res.statusText;
    throw new ApiError(res.status, msg, body);
  }

  return res.json();
}
