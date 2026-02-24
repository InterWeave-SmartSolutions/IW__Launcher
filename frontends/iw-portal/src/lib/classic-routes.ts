/** Maps React routes to their corresponding classic JSP pages */
export const CLASSIC_ROUTES: Record<string, string> = {
  "/": "/iw-business-daemon/IWLogin.jsp",
  "/dashboard": "/iw-business-daemon/IWLogin.jsp",
  "/monitoring": "/iw-business-daemon/monitoring/Dashboard.jsp",
  "/monitoring/transactions": "/iw-business-daemon/monitoring/TransactionDetail.jsp",
  "/monitoring/alerts": "/iw-business-daemon/monitoring/AlertConfig.jsp",
  "/profile": "/iw-business-daemon/EditProfile.jsp",
  "/profile/password": "/iw-business-daemon/ChangePassword.jsp",
  "/company": "/iw-business-daemon/EditCompanyProfile.jsp",
  "/company/config": "/iw-business-daemon/CompanyConfiguration.jsp",
  "/company/password": "/iw-business-daemon/ChangeCompanyPassword.jsp",
  "/admin/configurator": "/iw-business-daemon/BDConfigurator.jsp",
  "/admin/logging": "/iw-business-daemon/Logging.jsp",
  "/registration": "/iw-business-daemon/Registration.jsp",
  "/company/registration": "/iw-business-daemon/CompanyRegistration.jsp",
};

export function getClassicUrl(reactPath: string): string {
  // Exact match first
  if (CLASSIC_ROUTES[reactPath]) return CLASSIC_ROUTES[reactPath];
  // Prefix match for nested routes
  const prefix = Object.keys(CLASSIC_ROUTES)
    .filter((k) => reactPath.startsWith(k) && k !== "/")
    .sort((a, b) => b.length - a.length)[0];
  return prefix ? CLASSIC_ROUTES[prefix]! : CLASSIC_ROUTES["/"]!;
}

/** Preference key for localStorage */
export const CLASSIC_MODE_KEY = "iw-portal-classic-mode";
