import { Navigate } from "react-router-dom";
import { useAuth } from "@/providers/AuthProvider";
import { getRoleHome } from "@/hooks/usePortal";

/**
 * Redirects authenticated users to their role-appropriate portal home.
 * Used as the index route (/) element.
 */
export function RoleRedirect() {
  const { user } = useAuth();
  const home = getRoleHome(user?.role ?? "operator");
  return <Navigate to={home} replace />;
}
