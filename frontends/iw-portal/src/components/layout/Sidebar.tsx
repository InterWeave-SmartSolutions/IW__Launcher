import { NavLink } from "react-router-dom";
import {
  LayoutDashboard,
  Activity,
  User,
  Building2,
  Settings,
  FileText,
  Monitor,
  type LucideIcon,
} from "lucide-react";
import { cn } from "@/lib/utils";

interface NavItem {
  to: string;
  label: string;
  icon: LucideIcon;
  description: string;
  group: string;
}

const NAV_ITEMS: NavItem[] = [
  { to: "/dashboard", label: "Dashboard", icon: LayoutDashboard, description: "Overview & status", group: "main" },
  { to: "/monitoring", label: "Monitoring", icon: Activity, description: "Transactions & metrics", group: "main" },
  { to: "/profile", label: "My Profile", icon: User, description: "Account settings", group: "account" },
  { to: "/company", label: "Company", icon: Building2, description: "Organization settings", group: "account" },
  { to: "/company/config", label: "Configuration", icon: Settings, description: "Company setup", group: "config" },
  { to: "/admin/configurator", label: "BD Configurator", icon: Monitor, description: "Daemon settings", group: "admin" },
  { to: "/admin/logging", label: "Logging", icon: FileText, description: "System logs", group: "admin" },
];

const GROUP_LABELS: Record<string, string> = {
  main: "Platform",
  account: "Account",
  config: "Configuration",
  admin: "Administration",
};

export function Sidebar() {
  const groups = [...new Set(NAV_ITEMS.map((i) => i.group))];

  return (
    <aside className="glass-sidebar sticky top-0 h-screen flex flex-col max-md:hidden">
      {/* Brand */}
      <div className="flex items-center gap-3 px-4 pt-5 pb-3">
        <div className="w-9 h-9 rounded-[14px] bg-gradient-to-br from-[hsl(var(--primary))] to-[hsl(var(--success))] shadow-lg shadow-[hsl(var(--primary)/0.25)]" />
        <div>
          <h1 className="text-sm font-semibold tracking-tight">InterWeave</h1>
          <p className="text-xs text-muted-foreground">Integration Platform</p>
        </div>
      </div>

      {/* Context pills (ASSA pattern) */}
      <div className="px-4 pb-3 flex flex-col gap-1.5">
        <div className="text-xs text-muted-foreground px-2.5 py-1.5 rounded-full border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)]">
          Environment: <b className="text-foreground">Local Dev</b>
        </div>
      </div>

      {/* Navigation */}
      <nav className="flex-1 overflow-y-auto px-3 pb-4">
        {groups.map((group) => (
          <div key={group} className="mb-4">
            <p className="text-[11px] font-medium text-muted-foreground uppercase tracking-wider px-3 mb-1.5">
              {GROUP_LABELS[group]}
            </p>
            <div className="flex flex-col gap-1">
              {NAV_ITEMS.filter((i) => i.group === group).map((item) => (
                <NavLink
                  key={item.to}
                  to={item.to}
                  className={({ isActive }) =>
                    cn(
                      "flex items-center gap-3 px-3 py-2.5 rounded-[14px] border border-transparent transition-colors",
                      "text-muted-foreground hover:bg-[hsl(var(--accent)/0.5)] hover:text-foreground",
                      isActive && "bg-[hsl(var(--primary)/0.14)] border-[hsl(var(--primary)/0.30)] text-foreground"
                    )
                  }
                >
                  <item.icon className="w-4 h-4 shrink-0" />
                  <div className="min-w-0">
                    <p className="text-sm font-medium truncate">{item.label}</p>
                    <p className="text-xs text-muted-foreground truncate">{item.description}</p>
                  </div>
                </NavLink>
              ))}
            </div>
          </div>
        ))}
      </nav>

      {/* Footer */}
      <div className="px-4 py-3 text-xs text-muted-foreground border-t border-[hsl(var(--sidebar-border))]">
        InterWeave IDE • IW Portal v0.1
      </div>
    </aside>
  );
}
