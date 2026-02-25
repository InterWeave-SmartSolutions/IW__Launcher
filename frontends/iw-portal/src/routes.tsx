import { createBrowserRouter, Navigate } from "react-router-dom";
import { AppShell } from "@/components/layout/AppShell";
import { DashboardPage } from "@/pages/DashboardPage";
import { MonitoringPage } from "@/pages/MonitoringPage";
import { LoginPage } from "@/pages/LoginPage";
import { NotFoundPage } from "@/pages/NotFoundPage";
import { ClassicRedirectPage } from "@/pages/ClassicRedirectPage";
import { ProtectedRoute } from "@/components/ProtectedRoute";

export const router = createBrowserRouter(
  [
    {
      path: "/login",
      element: <LoginPage />,
    },
    {
      path: "/",
      element: (
        <ProtectedRoute>
          <AppShell />
        </ProtectedRoute>
      ),
      children: [
        { index: true, element: <Navigate to="/dashboard" replace /> },
        { path: "dashboard", element: <DashboardPage /> },
        { path: "monitoring", element: <MonitoringPage /> },
        {
          path: "monitoring/transactions",
          element: <div className="p-6 text-muted-foreground">Transaction History — coming soon. Use Classic View for now.</div>,
        },
        {
          path: "monitoring/alerts",
          element: <div className="p-6 text-muted-foreground">Alert Configuration — coming soon. Use Classic View for now.</div>,
        },
        {
          path: "profile",
          element: <ClassicRedirectPage title="User Profile" classicPath="/iw-business-daemon/EditProfile.jsp" />,
        },
        {
          path: "company",
          element: <ClassicRedirectPage title="Company Profile" classicPath="/iw-business-daemon/EditCompanyProfile.jsp" />,
        },
        {
          path: "company/config",
          element: <ClassicRedirectPage title="Company Configuration" classicPath="/iw-business-daemon/CompanyConfiguration.jsp" />,
        },
        {
          path: "admin/configurator",
          element: <ClassicRedirectPage title="Business Daemon Configurator" classicPath="/iw-business-daemon/BDConfigurator.jsp" />,
        },
        {
          path: "admin/logging",
          element: <ClassicRedirectPage title="System Logging" classicPath="/iw-business-daemon/Logging.jsp" />,
        },
        { path: "*", element: <NotFoundPage /> },
      ],
    },
  ],
  { basename: "/iw-portal" }
);
