export interface MfaStatusResponse {
  success: boolean;
  mfaEnabled: boolean;
  verifiedAt: string | null;
}

export interface MfaSetupResponse {
  success: boolean;
  secret: string;
  otpauthUri: string;
}

export interface MfaVerifyResponse {
  success: boolean;
  backupCodes?: string[];
  error?: string;
}

export interface MfaValidateResponse {
  success: boolean;
  error?: string;
}

export interface MfaDisableResponse {
  success: boolean;
  message?: string;
  error?: string;
}

export interface PasswordResetResponse {
  success: boolean;
  message?: string;
  valid?: boolean;
  error?: string;
}
