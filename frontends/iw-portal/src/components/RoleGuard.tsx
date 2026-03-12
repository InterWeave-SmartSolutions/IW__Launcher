import { ShieldAlert } from "lucide-react";
import { useAuth } from "@/providers/AuthProvider";
import type { UserRole } from "@/types/auth";

interface RoleGuardProps {
  children: React.ReactNode;
  /** Roles allowed to see this content. Admin always passes. */
  roles: UserRole[];
}

/**
 * Inline role guard for individual routes within the already-authenticated AppShell.
 * Unlike ProtectedRoute (which handles auth + redirect), RoleGuard assumes the user
 * IS authenticated and only checks their role.
 */
export function RoleGuard({ children, roles }: RoleGuardProps) {
  const { user } = useAuth();
  const role = user?.role ?? "operator";

  if (role === "admin" || roles.includes(role)) {
    return <>{children}</>;
  }

  return (
    <div className="min-h-[60vh] grid place-items-center">
      <div className="text-center space-y-3">
        <ShieldAlert className="w-12 h-12 text-destructive mx-auto" />
        <h2 className="text-xl font-semibold">Access Denied</h2>
        <p className="text-muted-foreground text-sm max-w-md">
          Your account role does not have permission to access this area.
          Contact your administrator if you believe this is an error.
        </p>
      </div>
    </div>
  );
}
