import { useState } from "react";
import { Outlet, useLocation } from "react-router-dom";
import { Sidebar } from "./Sidebar";
import { Topbar } from "./Topbar";
import { ClassicViewBanner } from "./ClassicViewBanner";

export function AppShell() {
  const location = useLocation();
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  return (
    <div className="app-background min-h-screen grid grid-cols-[260px_1fr] max-md:grid-cols-1">
      <Sidebar />
      {mobileMenuOpen && (
        <Sidebar mobile onClose={() => setMobileMenuOpen(false)} />
      )}
      <main className="flex flex-col min-h-screen overflow-auto">
        <Topbar onMenuToggle={() => setMobileMenuOpen(true)} />
        <ClassicViewBanner currentPath={location.pathname} />
        <div className="flex-1 p-6 max-md:p-4">
          <Outlet />
        </div>
      </main>
    </div>
  );
}
