import { useState, useEffect, useCallback } from "react";
import { Outlet, useLocation } from "react-router-dom";
import { Sidebar } from "./Sidebar";
import { Topbar } from "./Topbar";
import { ClassicViewBanner } from "./ClassicViewBanner";
import { useSyncStatus } from "@/hooks/useSync";

/* ── Route-change announcer for screen readers ── */
function RouteAnnouncer() {
  const location = useLocation();
  const [announcement, setAnnouncement] = useState("");
  useEffect(() => {
    // Small delay so document.title is updated by useDocumentTitle first
    const id = setTimeout(() => setAnnouncement(`Navigated to ${document.title}`), 100);
    return () => clearTimeout(id);
  }, [location.pathname]);
  return <div aria-live="polite" aria-atomic="true" className="sr-only">{announcement}</div>;
}

const SIDEBAR_KEY = "iw-sidebar-collapsed";

export function AppShell() {
  const location = useLocation();
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const [collapsed, setCollapsed] = useState(() => {
    try { return localStorage.getItem(SIDEBAR_KEY) === "true"; } catch { return false; }
  });

  useEffect(() => {
    try { localStorage.setItem(SIDEBAR_KEY, String(collapsed)); } catch { /* noop */ }
  }, [collapsed]);

  const toggleCollapsed = useCallback(() => setCollapsed((c) => !c), []);

  // Global sync watcher — polls /api/sync/status every 10s and auto-invalidates
  // config/flow queries when the IDE sync bridge imports changes.
  useSyncStatus();

  return (
    <div
      className="app-background min-h-screen grid max-md:grid-cols-1 transition-[grid-template-columns] duration-200"
      style={{ gridTemplateColumns: collapsed ? "64px 1fr" : "280px 1fr" }}
    >
      {/* Skip navigation — WCAG 2.4.1 */}
      <a
        href="#main-content"
        className="sr-only focus:not-sr-only focus:absolute focus:z-[100] focus:top-2 focus:left-2 focus:px-4 focus:py-2 focus:rounded-lg focus:bg-[hsl(var(--primary))] focus:text-white focus:text-sm focus:font-medium focus:shadow-lg"
      >
        Skip to main content
      </a>
      <RouteAnnouncer />
      <Sidebar collapsed={collapsed} onToggleCollapse={toggleCollapsed} />
      {mobileMenuOpen && (
        <Sidebar mobile onClose={() => setMobileMenuOpen(false)} />
      )}
      <main id="main-content" className="flex flex-col min-h-screen overflow-auto">
        <Topbar onMenuToggle={() => setMobileMenuOpen(true)} />
        <ClassicViewBanner currentPath={location.pathname} />
        <div className="flex-1 p-6 max-md:p-4">
          <Outlet />
        </div>
      </main>
    </div>
  );
}
