export interface UserProfile {
  email: string;
  firstName: string;
  lastName: string;
  title: string;
  role: string;
  company: string;
  solutionType: string;
}

export interface ProfileResponse {
  success: boolean;
  profile?: UserProfile;
  error?: string;
}

export interface ProfileUpdateRequest {
  firstName: string;
  lastName: string;
  title: string;
}

export interface PasswordChangeRequest {
  oldPassword: string;
  newPassword: string;
  confirmPassword: string;
}

export interface CompanyProfile {
  id: number;
  companyName: string;
  companyEmail: string;
  solutionType: string;
  isActive: boolean;
  licenseKey: string | null;
  licenseExpiry: string | null;
  admin: {
    firstName: string;
    lastName: string;
    email: string;
    title: string;
    role: string;
  };
}

export interface CompanyProfileResponse {
  success: boolean;
  company?: CompanyProfile;
  userCount?: number;
  error?: string;
}

export interface CompanyProfileUpdateRequest {
  firstName: string;
  lastName: string;
  solutionType?: string;
}

export interface ApiMessageResponse {
  success: boolean;
  message?: string;
  error?: string;
}
