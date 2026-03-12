import { lazy, Suspense } from "react";
import { createBrowserRouter, Navigate } from "react-router-dom";

/* ── Associate portal pages (lazy) ── */
const AssociateHomePage = lazy(() =>
  import("@/pages/associate/AssociateHomePage").then((m) => ({ default: m.AssociateHomePage }))
);
const ResourceLibraryPage = lazy(() =>
  import("@/pages/associate/ResourceLibraryPage").then((m) => ({ default: m.ResourceLibraryPage }))
);
const WebinarsPage = lazy(() =>
  import("@/pages/associate/WebinarsPage").then((m) => ({ default: m.WebinarsPage }))
);
const BusinessIntakePage = lazy(() =>
  import("@/pages/associate/BusinessIntakePage").then((m) => ({ default: m.BusinessIntakePage }))
);
const AssociateSupportPage = lazy(() =>
  import("@/pages/associate/SupportPage").then((m) => ({ default: m.SupportPage }))
);
const BillingPage = lazy(() =>
  import("@/pages/associate/BillingPage").then((m) => ({ default: m.BillingPage }))
);
const SearchPage = lazy(() =>
  import("@/pages/associate/SearchPage").then((m) => ({ default: m.SearchPage }))
);

/* ── Master console pages (lazy) ── */
const MasterDashboardPage = lazy(() =>
  import("@/pages/master/MasterDashboardPage").then((m) => ({ default: m.MasterDashboardPage }))
);
const UserManagementPage = lazy(() =>
  import("@/pages/master/UserManagementPage").then((m) => ({ default: m.UserManagementPage }))
);
const ContentManagementPage = lazy(() =>
  import("@/pages/master/ContentManagementPage").then((m) => ({ default: m.ContentManagementPage }))
);
const SubscriptionsPage = lazy(() =>
  import("@/pages/master/SubscriptionsPage").then((m) => ({ default: m.SubscriptionsPage }))
);
const ConnectorManagementPage = lazy(() =>
  import("@/pages/master/ConnectorManagementPage").then((m) => ({ default: m.ConnectorManagementPage }))
);
const ProgramAnalyticsPage = lazy(() =>
  import("@/pages/master/ProgramAnalyticsPage").then((m) => ({ default: m.ProgramAnalyticsPage }))
);
const AuditSecurityPage = lazy(() =>
  import("@/pages/master/AuditSecurityPage").then((m) => ({ default: m.AuditSecurityPage }))
);
const NotificationTemplatesPage = lazy(() =>
  import("@/pages/master/NotificationTemplatesPage").then((m) => ({ default: m.NotificationTemplatesPage }))
);
const SupportQueuePage = lazy(() =>
  import("@/pages/master/SupportQueuePage").then((m) => ({ default: m.SupportQueuePage }))
);
const TenantSettingsPage = lazy(() =>
  import("@/pages/master/TenantSettingsPage").then((m) => ({ default: m.TenantSettingsPage }))
);
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
import { RoleGuard } from "@/components/RoleGuard";
import { RoleRedirect } from "@/components/RoleRedirect";
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
/* FieldMappingPage → now embedded in ConfigurationWizardPage step */
/* IDESyncPage → now embedded as tab in IntegrationOverviewPage */

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
        { index: true, element: <RoleRedirect /> },
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
        /* Old standalone routes → redirect to new homes */
        { path: "admin/field-mapping", element: <Navigate to="/company/config/wizard" replace /> },
        { path: "admin/sync", element: <Navigate to="/admin/configurator" replace /> },

        /* ── Associate portal routes (associate + admin only) ── */
        {
          path: "associate/home",
          element: <RoleGuard roles={["associate", "admin"]}><Suspense fallback={<LazyFallback />}><AssociateHomePage /></Suspense></RoleGuard>,
        },
        {
          path: "associate/resources",
          element: <RoleGuard roles={["associate", "admin"]}><Suspense fallback={<LazyFallback />}><ResourceLibraryPage /></Suspense></RoleGuard>,
        },
        {
          path: "associate/webinars",
          element: <RoleGuard roles={["associate", "admin"]}><Suspense fallback={<LazyFallback />}><WebinarsPage /></Suspense></RoleGuard>,
        },
        {
          path: "associate/intake",
          element: <RoleGuard roles={["associate", "admin"]}><Suspense fallback={<LazyFallback />}><BusinessIntakePage /></Suspense></RoleGuard>,
        },
        {
          path: "associate/support",
          element: <RoleGuard roles={["associate", "admin"]}><Suspense fallback={<LazyFallback />}><AssociateSupportPage /></Suspense></RoleGuard>,
        },
        {
          path: "associate/billing",
          element: <RoleGuard roles={["associate", "admin"]}><Suspense fallback={<LazyFallback />}><BillingPage /></Suspense></RoleGuard>,
        },
        {
          path: "associate/search",
          element: <RoleGuard roles={["associate", "admin"]}><Suspense fallback={<LazyFallback />}><SearchPage /></Suspense></RoleGuard>,
        },

        /* ── Master console routes (admin only) ── */
        {
          path: "master/dashboard",
          element: <RoleGuard roles={["admin"]}><Suspense fallback={<LazyFallback />}><MasterDashboardPage /></Suspense></RoleGuard>,
        },
        {
          path: "master/users",
          element: <RoleGuard roles={["admin"]}><Suspense fallback={<LazyFallback />}><UserManagementPage /></Suspense></RoleGuard>,
        },
        {
          path: "master/content",
          element: <RoleGuard roles={["admin"]}><Suspense fallback={<LazyFallback />}><ContentManagementPage /></Suspense></RoleGuard>,
        },
        {
          path: "master/subscriptions",
          element: <RoleGuard roles={["admin"]}><Suspense fallback={<LazyFallback />}><SubscriptionsPage /></Suspense></RoleGuard>,
        },
        {
          path: "master/integrations",
          element: <RoleGuard roles={["admin"]}><Suspense fallback={<LazyFallback />}><ConnectorManagementPage /></Suspense></RoleGuard>,
        },
        {
          path: "master/analytics",
          element: <RoleGuard roles={["admin"]}><Suspense fallback={<LazyFallback />}><ProgramAnalyticsPage /></Suspense></RoleGuard>,
        },
        {
          path: "master/audit",
          element: <RoleGuard roles={["admin"]}><Suspense fallback={<LazyFallback />}><AuditSecurityPage /></Suspense></RoleGuard>,
        },
        {
          path: "master/notifications",
          element: <RoleGuard roles={["admin"]}><Suspense fallback={<LazyFallback />}><NotificationTemplatesPage /></Suspense></RoleGuard>,
        },
        {
          path: "master/support",
          element: <RoleGuard roles={["admin"]}><Suspense fallback={<LazyFallback />}><SupportQueuePage /></Suspense></RoleGuard>,
        },
        {
          path: "master/settings",
          element: <RoleGuard roles={["admin"]}><Suspense fallback={<LazyFallback />}><TenantSettingsPage /></Suspense></RoleGuard>,
        },

        { path: "*", element: <NotFoundPage /> },
      ],
    },
  ],
  { basename: import.meta.env.BASE_URL.replace(/\/$/, "") || "/" }
);
