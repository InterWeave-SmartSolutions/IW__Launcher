import { Navigate } from "react-router-dom";
import { Loader2, ShieldAlert } from "lucide-react";
import { useAuth } from "@/providers/AuthProvider";
import type { UserRole } from "@/types/auth";

interface ProtectedRouteProps {
  children: React.ReactNode;
  /** If set, only users with one of these roles can access. Admin always passes. */
  allowedRoles?: UserRole[];
}

/**
 * Wraps a route element and redirects to /login if not authenticated.
 * Optionally restricts access by role — shows a 403 page if the user's
 * role doesn't match `allowedRoles`.
 */
export function ProtectedRoute({ children, allowedRoles }: ProtectedRouteProps) {
  const { user, isAuthenticated, isLoading } = useAuth();

  if (isLoading) {
    return (
      <div className="app-background min-h-screen grid place-items-center">
        <Loader2 className="w-8 h-8 animate-spin text-muted-foreground" />
      </div>
    );
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  // Role check: admin always passes, otherwise must be in allowedRoles
  if (allowedRoles && user) {
    const role = user.role ?? "operator";
    if (role !== "admin" && !allowedRoles.includes(role)) {
      return <ForbiddenPage />;
    }
  }

  return <>{children}</>;
}

function ForbiddenPage() {
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
