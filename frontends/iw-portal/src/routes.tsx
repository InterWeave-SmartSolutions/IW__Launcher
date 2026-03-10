import { lazy, Suspense } from "react";
import { createBrowserRouter, Navigate } from "react-router-dom";
import { AppShell } from "@/components/layout/AppShell";
import { DashboardPage } from "@/pages/DashboardPage";
import { LoginPage } from "@/pages/LoginPage";
import { RegisterPage } from "@/pages/RegisterPage";
import { CompanyRegisterPage } from "@/pages/CompanyRegisterPage";
import { NotFoundPage } from "@/pages/NotFoundPage";
import { ProfilePage } from "@/pages/ProfilePage";
import { CompanyPage } from "@/pages/CompanyPage";
import { CompanyConfigPage } from "@/pages/CompanyConfigPage";
/* Lazy-load config wizard (admin-only, ~80kB) */
const ConfigurationWizardPage = lazy(() =>
  import("@/pages/ConfigurationWizardPage").then((m) => ({ default: m.ConfigurationWizardPage }))
);
/* Lazy-load integrations page (engine controls + credentials + dep map) */
const IntegrationOverviewPage = lazy(() =>
  import("@/pages/IntegrationOverviewPage").then((m) => ({ default: m.IntegrationOverviewPage }))
);
import { LoggingPage } from "@/pages/LoggingPage";
import { ChangePasswordPage } from "@/pages/ChangePasswordPage";
import { ForgotPasswordPage } from "@/pages/ForgotPasswordPage";
import { MfaVerifyPage } from "@/pages/MfaVerifyPage";
import { ProtectedRoute } from "@/components/ProtectedRoute";
import { Loader2 } from "lucide-react";

/* Lazy-load new feature pages */
const MfaSetupPage = lazy(() =>
  import("@/pages/MfaSetupPage").then((m) => ({ default: m.MfaSetupPage }))
);
const NotificationsPage = lazy(() =>
  import("@/pages/NotificationsPage").then((m) => ({ default: m.NotificationsPage }))
);
const AuditLogPage = lazy(() =>
  import("@/pages/AuditLogPage").then((m) => ({ default: m.AuditLogPage }))
);
const FieldMappingPage = lazy(() =>
  import("@/pages/FieldMappingPage").then((m) => ({ default: m.FieldMappingPage }))
);

/* Lazy-load monitoring pages (recharts is ~380kB) */
const MonitoringLayout = lazy(() =>
  import("@/pages/MonitoringLayout").then((m) => ({ default: m.MonitoringLayout }))
);
const MonitoringPage = lazy(() =>
  import("@/pages/MonitoringPage").then((m) => ({ default: m.MonitoringPage }))
);
const TransactionHistoryPage = lazy(() =>
  import("@/pages/TransactionHistoryPage").then((m) => ({ default: m.TransactionHistoryPage }))
);
const AlertConfigPage = lazy(() =>
  import("@/pages/AlertConfigPage").then((m) => ({ default: m.AlertConfigPage }))
);

function LazyFallback() {
  return (
    <div className="flex justify-center py-12">
      <Loader2 className="w-6 h-6 animate-spin text-muted-foreground" />
    </div>
  );
}

export const router = createBrowserRouter(
  [
    /* ── Public routes (no auth required) ── */
    {
      path: "/login",
      element: <LoginPage />,
    },
    {
      path: "/register",
      element: <RegisterPage />,
    },
    {
      path: "/register/company",
      element: <CompanyRegisterPage />,
    },
    {
      path: "/forgot-password",
      element: <ForgotPasswordPage />,
    },
    {
      path: "/mfa/verify",
      element: <MfaVerifyPage />,
    },

    /* ── Protected routes (auth required, inside AppShell) ── */
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
        {
          path: "monitoring",
          element: (
            <Suspense fallback={<LazyFallback />}>
              <MonitoringLayout />
            </Suspense>
          ),
          children: [
            {
              index: true,
              element: (
                <Suspense fallback={<LazyFallback />}>
                  <MonitoringPage />
                </Suspense>
              ),
            },
            {
              path: "transactions",
              element: (
                <Suspense fallback={<LazyFallback />}>
                  <TransactionHistoryPage />
                </Suspense>
              ),
            },
            {
              path: "alerts",
              element: (
                <Suspense fallback={<LazyFallback />}>
                  <AlertConfigPage />
                </Suspense>
              ),
            },
          ],
        },
        {
          path: "profile",
          element: <ProfilePage />,
        },
        {
          path: "profile/password",
          element: <ChangePasswordPage />,
        },
        {
          path: "company",
          element: <CompanyPage />,
        },
        {
          path: "company/config",
          element: <CompanyConfigPage />,
        },
        {
          path: "company/config/wizard",
          element: (
            <Suspense fallback={<LazyFallback />}>
              <ConfigurationWizardPage />
            </Suspense>
          ),
        },
        {
          path: "admin/configurator",
          element: (
            <Suspense fallback={<LazyFallback />}>
              <IntegrationOverviewPage />
            </Suspense>
          ),
        },
        {
          path: "notifications",
          element: (
            <Suspense fallback={<LazyFallback />}>
              <NotificationsPage />
            </Suspense>
          ),
        },
        {
          path: "profile/security",
          element: (
            <Suspense fallback={<LazyFallback />}>
              <MfaSetupPage />
            </Suspense>
          ),
        },
        {
          path: "admin/logging",
          element: <LoggingPage />,
        },
        {
          path: "admin/audit",
          element: (
            <Suspense fallback={<LazyFallback />}>
              <AuditLogPage />
            </Suspense>
          ),
        },
        {
          path: "admin/field-mapping",
          element: (
            <Suspense fallback={<LazyFallback />}>
              <FieldMappingPage />
            </Suspense>
          ),
        },
        { path: "*", element: <NotFoundPage /> },
      ],
    },
  ],
  { basename: import.meta.env.BASE_URL.replace(/\/$/, "") || "/" }
);
