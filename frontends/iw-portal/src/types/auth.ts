export type UserRole = "operator" | "associate" | "admin";

export interface User {
  userId: string;
  userName: string;
  email: string;
  companyId: number | null;
  companyName: string | null;
  isAdmin: boolean;
  role: UserRole;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  success: boolean;
  user?: User;
  token?: string;
  mfaRequired?: boolean;
  error?: string;
}

export interface SessionResponse {
  authenticated: boolean;
  user?: User;
}
