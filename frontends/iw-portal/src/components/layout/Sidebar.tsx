import { NavLink } from "react-router-dom";
import {
  LayoutDashboard, Activity, User, Building2, Settings, FileText, Monitor,
  Bell, Shield, ClipboardList, Sparkles, X, Home, BookOpen, Search,
  Video, ClipboardCheck, HelpCircle, CreditCard, Users, BarChart2,
  Plug, Lock, SlidersHorizontal, ArrowLeftRight, type LucideIcon,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { usePortal, PORTAL_SUBTITLES, type Portal } from "@/hooks/usePortal";

interface NavItem {
  to: string;
  label: string;
  icon: LucideIcon;
  description: string;
  group: string;
}

/* ── Operator (integration) nav ── */
const OPERATOR_NAV: NavItem[] = [
  { to: "/dashboard",            label: "Dashboard",      icon: LayoutDashboard, description: "Overview & status",            group: "main"    },
  { to: "/monitoring",           label: "Monitoring",     icon: Activity,        description: "Transactions & metrics",       group: "main"    },
  { to: "/notifications",        label: "Notifications",  icon: Bell,            description: "Alerts & updates",             group: "main"    },
  { to: "/profile",              label: "My Profile",     icon: User,            description: "Account settings",             group: "account" },
  { to: "/profile/security",     label: "Security",       icon: Shield,          description: "MFA & password",               group: "account" },
  { to: "/company",              label: "Company",        icon: Building2,       description: "Organization settings",        group: "account" },
  { to: "/company/config",       label: "Configuration",  icon: Settings,        description: "Company setup",                group: "config"  },
  { to: "/admin/configurator",   label: "Integrations",   icon: Monitor,         description: "Flows & daemon",               group: "admin"   },
  { to: "/admin/logging",        label: "Logging",        icon: FileText,        description: "System logs",                  group: "admin"   },
  { to: "/admin/audit",          label: "Audit Log",      icon: ClipboardList,   description: "Activity history",             group: "admin"   },
  { to: "/admin/field-mapping",  label: "Field Mapping",  icon: Sparkles,        description: "AI mapping suggestions",       group: "admin"   },
  { to: "/admin/sync",           label: "IDE Sync",       icon: ArrowLeftRight,  description: "Portal ↔ IDE workspace sync",  group: "admin"   },
];

const OPERATOR_GROUPS: Record<string, string> = {
  main: "Platform", account: "Account", config: "Configuration", admin: "Administration",
};

/* ── Associate portal nav ── */
const ASSOCIATE_NAV: NavItem[] = [
  { to: "/associate/home",      label: "Home",             icon: Home,           description: "Your workspace",               group: "main"    },
  { to: "/associate/resources", label: "Resource Library", icon: BookOpen,       description: "Browse content",               group: "main"    },
  { to: "/associate/search",    label: "Search",           icon: Search,         description: "Find resources",               group: "main"    },
  { to: "/associate/webinars",  label: "Webinars",         icon: Video,          description: "Upcoming & replays",           group: "main"    },
  { to: "/associate/intake",    label: "Business Checkup", icon: ClipboardCheck, description: "Self-assessment",              group: "main"    },
  { to: "/associate/support",   label: "Support",          icon: HelpCircle,     description: "Get help",                     group: "main"    },
  { to: "/associate/billing",   label: "Billing",          icon: CreditCard,     description: "Plan & invoices",              group: "account" },
  { to: "/profile",             label: "My Profile",       icon: User,           description: "Account settings",             group: "account" },
];

const ASSOCIATE_GROUPS: Record<string, string> = {
  main: "Program", account: "Account",
};

/* ── Master console nav ── */
const MASTER_NAV: NavItem[] = [
  { to: "/master/dashboard",      label: "Dashboard",       icon: LayoutDashboard, description: "Program health",            group: "main"     },
  { to: "/master/users",          label: "Users & Roles",   icon: Users,           description: "Manage members",           group: "main"     },
  { to: "/master/content",        label: "Content Library", icon: BookOpen,        description: "Publish resources",        group: "main"     },
  { to: "/master/subscriptions",  label: "Subscriptions",   icon: CreditCard,      description: "Plans & billing",          group: "main"     },
  { to: "/master/analytics",      label: "Analytics",       icon: BarChart2,       description: "Engagement & MRR",         group: "insights" },
  { to: "/master/integrations",   label: "Integrations",    icon: Plug,            description: "Connector inventory",      group: "insights" },
  { to: "/master/notifications",  label: "Notifications",   icon: Bell,            description: "Templates & routing",      group: "program"  },
  { to: "/master/audit",          label: "Audit & Security",icon: Lock,            description: "Events & compliance",      group: "program"  },
  { to: "/master/support",        label: "Support Queue",   icon: HelpCircle,      description: "Manage tickets",           group: "program"  },
  { to: "/master/settings",       label: "Settings",        icon: SlidersHorizontal, description: "Program configuration", group: "program"  },
];

const MASTER_GROUPS: Record<string, string> = {
  main: "Program", insights: "Insights", program: "Operations",
};

const NAV_CONFIG: Record<Portal, { items: NavItem[]; groups: Record<string, string> }> = {
  operator: { items: OPERATOR_NAV, groups: OPERATOR_GROUPS },
  associate: { items: ASSOCIATE_NAV, groups: ASSOCIATE_GROUPS },
  master:    { items: MASTER_NAV,    groups: MASTER_GROUPS    },
};

interface SidebarProps {
  mobile?: boolean;
  onClose?: () => void;
}

export function Sidebar({ mobile, onClose }: SidebarProps) {
  const portal = usePortal();
  const { items, groups } = NAV_CONFIG[portal];
  const navGroups = [...new Set(items.map((i) => i.group))];
  const subtitle = PORTAL_SUBTITLES[portal];

  const nav = (
    <>
      {/* Brand */}
      <div className="flex items-center justify-between px-4 pt-5 pb-3">
        <div className="flex items-center gap-3">
          <div className="w-9 h-9 rounded-[14px] bg-gradient-to-br from-[hsl(var(--primary))] to-[hsl(var(--success))] shadow-lg shadow-[hsl(var(--primary)/0.25)]" />
          <div>
            <h1 className="text-sm font-semibold tracking-tight">InterWeave</h1>
            <p className="text-xs text-muted-foreground">{subtitle}</p>
          </div>
        </div>
        {mobile && (
          <button
            onClick={onClose}
            className="p-1.5 rounded-lg text-muted-foreground hover:text-foreground cursor-pointer"
          >
            <X className="w-5 h-5" />
          </button>
        )}
      </div>

      {/* Context pill */}
      <div className="px-4 pb-3 flex flex-col gap-1.5">
        <div className="text-xs text-muted-foreground px-2.5 py-1.5 rounded-full border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)]">
          {portal === "operator" && <>Environment: <b className="text-foreground">Local Dev</b></>}
          {portal === "associate" && <>Portal: <b className="text-foreground">Associate</b></>}
          {portal === "master" && <>Console: <b className="text-foreground">Program Admin</b></>}
        </div>
      </div>

      {/* Navigation */}
      <nav className="flex-1 overflow-y-auto px-3 pb-4">
        {navGroups.map((group) => (
          <div key={group} className="mb-4">
            <p className="text-[11px] font-medium text-muted-foreground uppercase tracking-wider px-3 mb-1.5">
              {groups[group]}
            </p>
            <div className="flex flex-col gap-1">
              {items.filter((i) => i.group === group).map((item) => (
                <NavLink
                  key={item.to}
                  to={item.to}
                  onClick={onClose}
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
    </>
  );

  if (!mobile) {
    return (
      <aside className="glass-sidebar sticky top-0 h-screen flex flex-col max-md:hidden">
        {nav}
      </aside>
    );
  }

  return (
    <>
      <div className="fixed inset-0 bg-black/50 z-40 md:hidden" onClick={onClose} />
      <aside className="fixed inset-y-0 left-0 w-[280px] glass-sidebar flex flex-col z-50 md:hidden shadow-2xl">
        {nav}
      </aside>
    </>
  );
}
