import { useRef, useEffect } from "react";
import { NavLink } from "react-router-dom";
import {
  LayoutDashboard, Activity, User, Building2, Settings, FileText, Monitor,
  Bell, Shield, ClipboardList, X, Home, BookOpen, Search,
  Video, ClipboardCheck, HelpCircle, CreditCard, Users, BarChart2,
  Plug, Lock, SlidersHorizontal, PanelLeftClose, PanelLeftOpen, type LucideIcon,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { usePortal, PORTAL_SUBTITLES, type Portal, getAllowedPortals } from "@/hooks/usePortal";
import { useAuth } from "@/providers/AuthProvider";

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
  collapsed?: boolean;
  onClose?: () => void;
  onToggleCollapse?: () => void;
}

export function Sidebar({ mobile, collapsed, onClose, onToggleCollapse }: SidebarProps) {
  const portal = usePortal();
  const { user } = useAuth();
  const allowed = getAllowedPortals(user?.role ?? "operator");
  // If the user navigated to a portal they don't have access to, fall back
  const effectivePortal: Portal = allowed.includes(portal) ? portal : (allowed[0] ?? "operator");
  const { items, groups } = NAV_CONFIG[effectivePortal];
  const navGroups = [...new Set(items.map((i: NavItem) => i.group))];
  const subtitle = PORTAL_SUBTITLES[portal];
  const c = collapsed && !mobile;
  const closeRef = useRef<HTMLButtonElement>(null);
  const asideRef = useRef<HTMLElement>(null);

  // Focus trap + Escape close for mobile sidebar (WCAG 2.4.3)
  useEffect(() => {
    if (!mobile) return;
    closeRef.current?.focus();
    const handleKey = (e: KeyboardEvent) => {
      if (e.key === "Escape") { onClose?.(); return; }
      if (e.key !== "Tab" || !asideRef.current) return;
      const focusable = asideRef.current.querySelectorAll<HTMLElement>(
        'a[href], button:not([disabled]), input, [tabindex]:not([tabindex="-1"])'
      );
      if (focusable.length === 0) return;
      const first = focusable[0]!;
      const last = focusable[focusable.length - 1]!;
      if (e.shiftKey && document.activeElement === first) { e.preventDefault(); last.focus(); }
      else if (!e.shiftKey && document.activeElement === last) { e.preventDefault(); first.focus(); }
    };
    document.addEventListener("keydown", handleKey);
    return () => document.removeEventListener("keydown", handleKey);
  }, [mobile, onClose]);

  const nav = (
    <>
      {/* Brand */}
      <div className={cn("flex items-center justify-between pt-5 pb-3", c ? "px-2" : "px-4")}>
        <div className={cn("flex items-center", c ? "justify-center w-full" : "gap-3")}>
          <div className="w-8 h-8 rounded-lg bg-gradient-to-br from-[#00e5a0] to-[#00b8ff] shadow-lg shadow-[#00b8ff40] shrink-0" />
          {!c && (
            <div>
              <span className="text-sm font-semibold tracking-tight text-white">InterWeave</span>
              <p className="text-xs text-[hsl(var(--sidebar-foreground)/0.6)]">{subtitle}</p>
            </div>
          )}
        </div>
        {mobile && (
          <button
            ref={closeRef}
            onClick={onClose}
            aria-label="Close navigation menu"
            className="p-1.5 rounded-lg text-[hsl(var(--sidebar-foreground)/0.75)] hover:text-white cursor-pointer"
          >
            <X className="w-5 h-5" aria-hidden="true" />
          </button>
        )}
      </div>

      {/* Context pill — hidden when collapsed */}
      {!c && (
        <div className="px-4 pb-3 flex flex-col gap-1.5">
          <div className="text-xs text-[hsl(var(--sidebar-foreground)/0.65)] px-2.5 py-1.5 rounded-full border border-[hsl(var(--sidebar-border))] bg-[hsl(var(--sidebar-foreground)/0.06)]">
            {portal === "operator" && <>Environment: <b className="text-[hsl(var(--sidebar-foreground))]">Local Dev</b></>}
            {portal === "associate" && <>Portal: <b className="text-[hsl(var(--sidebar-foreground))]">Associate</b></>}
            {portal === "master" && <>Console: <b className="text-[hsl(var(--sidebar-foreground))]">Program Admin</b></>}
          </div>
        </div>
      )}

      {/* Navigation */}
      <nav aria-label="Main navigation" className={cn("flex-1 overflow-y-auto pb-4", c ? "px-1.5" : "px-3")}>
        {navGroups.map((group) => (
          <div key={group} role="group" aria-label={groups[group]} className={cn(c ? "mb-2" : "mb-4")}>
            {!c && (
              <p className="text-[11px] font-medium text-[hsl(var(--sidebar-foreground)/0.6)] uppercase tracking-wider px-3 mb-1.5">
                {groups[group]}
              </p>
            )}
            {c && group !== navGroups[0] && (
              <div className="mx-2 my-1.5 border-t border-[hsl(var(--sidebar-border))]" />
            )}
            <div className="flex flex-col gap-1">
              {items.filter((i) => i.group === group).map((item) => (
                <NavLink
                  key={item.to}
                  to={item.to}
                  onClick={onClose}
                  title={c ? item.label : undefined}
                  aria-label={c ? item.label : undefined}
                  end={item.to === "/dashboard"}
                  className={({ isActive }) =>
                    cn(
                      "flex items-center rounded-lg transition-colors",
                      c
                        ? "justify-center p-2.5 border-l-0"
                        : "gap-3 px-3 py-2.5 border-l-2",
                      "text-[hsl(var(--sidebar-foreground)/0.85)] border-l-transparent hover:bg-[hsl(var(--sidebar-accent)/0.08)] hover:text-[hsl(var(--sidebar-foreground))]",
                      isActive && cn(
                        "bg-[hsl(var(--sidebar-accent)/0.12)] text-[hsl(var(--sidebar-accent))] font-semibold",
                        c ? "border-l-0" : "border-l-[hsl(var(--sidebar-accent))]"
                      )
                    )
                  }
                >
                  <item.icon className={cn("shrink-0", c ? "w-5 h-5" : "w-4 h-4")} />
                  {!c && (
                    <div className="min-w-0">
                      <p className="text-sm font-medium truncate">{item.label}</p>
                      <p className="text-xs text-[hsl(var(--sidebar-foreground)/0.6)] truncate">{item.description}</p>
                    </div>
                  )}
                </NavLink>
              ))}
            </div>
          </div>
        ))}
      </nav>

      {/* Collapse toggle + footer */}
      <div className={cn(
        "py-3 border-t border-[hsl(var(--sidebar-border))] flex",
        c ? "justify-center px-2" : "items-center justify-between px-4"
      )}>
        {!c && (
          <span className="text-xs text-[hsl(var(--sidebar-foreground)/0.55)]">
            IW Portal v0.1
          </span>
        )}
        {!mobile && (
          <button
            onClick={onToggleCollapse}
            className="p-1.5 rounded-lg text-[hsl(var(--sidebar-foreground)/0.6)] hover:text-[hsl(var(--sidebar-foreground))] hover:bg-[hsl(var(--sidebar-accent)/0.08)] transition-colors cursor-pointer"
            title={c ? "Expand sidebar" : "Collapse sidebar"}
          >
            {c ? <PanelLeftOpen className="w-4 h-4" /> : <PanelLeftClose className="w-4 h-4" />}
          </button>
        )}
      </div>
    </>
  );

  if (!mobile) {
    return (
      <aside aria-label="Application navigation" className={cn(
        "glass-sidebar sticky top-0 h-screen flex flex-col max-md:hidden transition-all duration-200 overflow-hidden",
        c ? "w-16" : "w-[280px]"
      )}>
        {nav}
      </aside>
    );
  }

  return (
    <>
      <div className="fixed inset-0 bg-black/50 z-40 md:hidden" onClick={onClose} aria-hidden="true" />
      <aside ref={asideRef} role="dialog" aria-modal="true" aria-label="Navigation menu" className="fixed inset-y-0 left-0 w-[280px] glass-sidebar flex flex-col z-50 md:hidden shadow-2xl">
        {nav}
      </aside>
    </>
  );
}
