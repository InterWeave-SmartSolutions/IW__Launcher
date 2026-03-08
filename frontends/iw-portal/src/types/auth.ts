export interface User {
  userId: string;
  userName: string;
  email: string;
  companyId: number | null;
  companyName: string | null;
  isAdmin: boolean;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  success: boolean;
  user?: User;
  mfaRequired?: boolean;
  error?: string;
}

export interface SessionResponse {
  authenticated: boolean;
  user?: User;
}
