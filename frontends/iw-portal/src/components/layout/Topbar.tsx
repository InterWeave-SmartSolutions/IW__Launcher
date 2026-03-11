import { useState, useRef, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import { Search, Sun, Moon, Monitor, LogOut, User, Menu, Bell, Zap, BookOpen, LayoutGrid } from "lucide-react";
import { useTheme } from "@/providers/ThemeProvider";
import { useAuth } from "@/providers/AuthProvider";
import { useDevMode } from "@/providers/DevModeProvider";
import { useAlertRules } from "@/hooks/useMonitoring";
import { useUnreadCount } from "@/hooks/useNotifications";
import { usePortal, usePortalSwitch, usePortalVisibility, PORTAL_LABELS, type Portal } from "@/hooks/usePortal";
import { cn } from "@/lib/utils";

interface SearchItem {
  label: string;
  description: string;
  path: string;
  group: string;
}

const SEARCH_ITEMS: SearchItem[] = [
  // Operator
  { label: "Dashboard",        description: "Overview & status",                   path: "/dashboard",             group: "Integration" },
  { label: "Monitoring",       description: "Charts & performance trends",          path: "/monitoring",            group: "Integration" },
  { label: "Transactions",     description: "Transaction history & details",        path: "/monitoring/transactions",group: "Integration" },
  { label: "Alerts",           description: "Alert rules & notifications",          path: "/monitoring/alerts",     group: "Integration" },
  { label: "My Profile",       description: "Account & personal settings",          path: "/profile",               group: "Account"     },
  { label: "Security",         description: "MFA & password security",              path: "/profile/security",      group: "Account"     },
  { label: "Company",          description: "Organization settings",                path: "/company",               group: "Account"     },
  { label: "Configuration",    description: "Configuration status & setup progress",path: "/company/config",        group: "Integration" },
  { label: "Config Wizard",    description: "Object mapping, credentials & sync",   path: "/company/config/wizard", group: "Integration" },
  { label: "Notifications",    description: "Alerts, updates & messages",           path: "/notifications",         group: "Account"     },
  { label: "Integrations",     description: "Integration flows & business daemon",  path: "/admin/configurator",    group: "Integration" },
  { label: "System Logging",   description: "Server & application logs",            path: "/admin/logging",         group: "Integration" },
  { label: "Audit Log",        description: "User activity & event history",        path: "/admin/audit",           group: "Integration" },
  // Associate
  { label: "Resource Library", description: "Browse program resources",             path: "/associate/resources",   group: "Associate"   },
  { label: "Webinars",         description: "Upcoming & recorded webinars",         path: "/associate/webinars",    group: "Associate"   },
  { label: "Business Checkup", description: "Self-assessment intake form",          path: "/associate/intake",      group: "Associate"   },
  { label: "Support",          description: "Submit & track support tickets",       path: "/associate/support",     group: "Associate"   },
  { label: "Billing",          description: "Plan, invoices & payment method",      path: "/associate/billing",     group: "Associate"   },
  // Master
  { label: "Master Dashboard", description: "Program health & KPIs",               path: "/master/dashboard",      group: "Master"      },
  { label: "Users & Roles",    description: "Manage members & permissions",         path: "/master/users",          group: "Master"      },
  { label: "Content Library",  description: "Publish & manage resources",           path: "/master/content",        group: "Master"      },
  { label: "Subscriptions",    description: "Plans, billing & churn",               path: "/master/subscriptions",  group: "Master"      },
  { label: "Analytics",        description: "Engagement, MRR & content adoption",  path: "/master/analytics",      group: "Master"      },
  { label: "Audit & Security", description: "Security events & compliance log",     path: "/master/audit",          group: "Master"      },
  { label: "Tenant Settings",  description: "Program-wide configuration",           path: "/master/settings",       group: "Master"      },
];

const PORTAL_ICONS: Record<Portal, typeof Zap> = {
  operator: Zap,
  associate: BookOpen,
  master:    LayoutGrid,
};

interface TopbarProps {
  onMenuToggle?: () => void;
}

export function Topbar({ onMenuToggle }: TopbarProps) {
  const { theme, setTheme, resolvedTheme } = useTheme();
  const { user, logout } = useAuth();
  const { devMode, toggleDevMode } = useDevMode();
  const navigate = useNavigate();
  const currentPortal = usePortal();
  const switchPortal = usePortalSwitch();
  const { visible: visiblePortals } = usePortalVisibility();

  const [query, setQuery] = useState("");
  const [showResults, setShowResults] = useState(false);
  const [selectedIndex, setSelectedIndex] = useState(0);
  const searchRef = useRef<HTMLDivElement>(null);
  const inputRef = useRef<HTMLInputElement>(null);

  const filtered = query.trim()
    ? SEARCH_ITEMS.filter(
        (item) =>
          item.label.toLowerCase().includes(query.toLowerCase()) ||
          item.description.toLowerCase().includes(query.toLowerCase())
      )
    : [];

  useEffect(() => {
    function handleClick(e: MouseEvent) {
      if (searchRef.current && !searchRef.current.contains(e.target as Node)) {
        setShowResults(false);
      }
    }
    document.addEventListener("mousedown", handleClick);
    return () => document.removeEventListener("mousedown", handleClick);
  }, []);

  useEffect(() => {
    function handleSlash(e: KeyboardEvent) {
      if (e.key === "/" && document.activeElement?.tagName !== "INPUT" && document.activeElement?.tagName !== "TEXTAREA") {
        e.preventDefault();
        inputRef.current?.focus();
      }
    }
    document.addEventListener("keydown", handleSlash);
    return () => document.removeEventListener("keydown", handleSlash);
  }, []);

  useEffect(() => { setSelectedIndex(0); }, [query]);

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (!showResults || filtered.length === 0) return;
    if (e.key === "ArrowDown") { e.preventDefault(); setSelectedIndex((i) => Math.min(i + 1, filtered.length - 1)); }
    else if (e.key === "ArrowUp") { e.preventDefault(); setSelectedIndex((i) => Math.max(i - 1, 0)); }
    else if (e.key === "Enter") { e.preventDefault(); navigateTo(filtered[selectedIndex]!.path); }
    else if (e.key === "Escape") { setShowResults(false); setQuery(""); }
  };

  const navigateTo = (path: string) => {
    navigate(path);
    setQuery("");
    setShowResults(false);
  };

  const cycleTheme = () => {
    const order: Array<"dark" | "light" | "system"> = ["dark", "light", "system"];
    const idx = order.indexOf(theme);
    setTheme(order[(idx + 1) % order.length]!);
  };

  const ThemeIcon = theme === "system" ? Monitor : resolvedTheme === "dark" ? Moon : Sun;
  const themeLabel = theme === "system" ? "System" : theme === "dark" ? "Dark" : "Light";

  return (
    <div className="flex items-center gap-2 px-4 py-3 border-b border-[hsl(var(--border))]">
      {/* Mobile menu toggle */}
      <button
        onClick={onMenuToggle}
        className="md:hidden p-1.5 rounded-lg border border-[hsl(var(--border))] text-muted-foreground hover:text-foreground cursor-pointer"
      >
        <Menu className="w-5 h-5" />
      </button>

      {/* Search bar */}
      <div className="flex-1 relative" ref={searchRef}>
        <div className="flex items-center gap-2 px-3 py-2 rounded-full border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)]">
          <Search className="w-4 h-4 text-muted-foreground shrink-0" />
          <input
            ref={inputRef}
            type="text"
            value={query}
            onChange={(e) => { setQuery(e.target.value); setShowResults(true); }}
            onFocus={() => query.trim() && setShowResults(true)}
            onKeyDown={handleKeyDown}
            placeholder="Search all portals..."
            className="flex-1 bg-transparent border-none outline-none text-sm text-foreground placeholder:text-muted-foreground"
          />
          {!query && (
            <kbd className="hidden sm:inline text-[10px] text-muted-foreground/60 bg-[hsl(var(--muted)/0.5)] border border-[hsl(var(--border))] rounded px-1.5 py-0.5 font-mono">
              /
            </kbd>
          )}
        </div>
        {showResults && filtered.length > 0 && (
          <div className="absolute top-full left-0 right-0 mt-1 rounded-[14px] border border-[hsl(var(--border))] bg-[hsl(var(--card))] shadow-lg overflow-hidden z-50">
            {filtered.map((item, i) => (
              <button
                key={item.path}
                onClick={() => navigateTo(item.path)}
                className={cn(
                  "w-full flex items-start gap-3 px-4 py-2.5 text-left transition-colors cursor-pointer",
                  i === selectedIndex ? "bg-[hsl(var(--primary)/0.1)]" : "hover:bg-[hsl(var(--muted)/0.3)]"
                )}
              >
                <div className="min-w-0">
                  <p className="text-sm font-medium">{item.label}</p>
                  <p className="text-xs text-muted-foreground">{item.description}</p>
                </div>
                <span className="text-[10px] text-muted-foreground/60 ml-auto shrink-0 mt-0.5">{item.group}</span>
              </button>
            ))}
          </div>
        )}
      </div>

      {/* Portal switcher */}
      <div className="hidden sm:flex items-center gap-1 p-1 rounded-full border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.2)]">
        {(["operator", "associate", "master"] as Portal[])
          .filter((p) => visiblePortals.includes(p))
          .map((portal) => {
            const Icon = PORTAL_ICONS[portal];
            const isActive = portal === currentPortal;
            return (
              <button
                key={portal}
                onClick={() => switchPortal(portal)}
                title={PORTAL_LABELS[portal]}
                className={cn(
                  "flex items-center gap-1.5 px-2.5 py-1 rounded-full text-xs transition-all cursor-pointer",
                  isActive
                    ? "bg-[hsl(var(--primary)/0.2)] border border-[hsl(var(--primary)/0.35)] text-foreground"
                    : "text-muted-foreground hover:text-foreground"
                )}
              >
                <Icon className="w-3 h-3" />
                <span className="hidden md:inline">{PORTAL_LABELS[portal]}</span>
              </button>
            );
          })}
      </div>

      {/* User pill */}
      {user && (
        <div className="flex items-center gap-2 px-3 py-1.5 rounded-full border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)] text-xs text-muted-foreground">
          <User className="w-3.5 h-3.5" />
          <span className="max-w-[120px] truncate hidden sm:inline">{user.userName}</span>
        </div>
      )}

      {/* Notifications */}
      <NotificationBadge />

      {/* Alerts */}
      <AlertBadge />

      {/* Dev Mode toggle */}
      <button
        onClick={toggleDevMode}
        title={devMode ? "Developer Mode: ON" : "Developer Mode: OFF"}
        className={cn(
          "flex items-center gap-1.5 px-2.5 py-1.5 rounded-full text-xs transition-colors cursor-pointer",
          "border",
          devMode
            ? "border-[hsl(var(--warning)/0.5)] bg-[hsl(var(--warning)/0.1)] text-[hsl(var(--warning))]"
            : "border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)] text-muted-foreground hover:text-foreground"
        )}
      >
        <Zap className="w-3.5 h-3.5" />
        <span className="hidden lg:inline">Dev</span>
      </button>

      {/* Theme toggle */}
      <button
        onClick={cycleTheme}
        className={cn(
          "flex items-center gap-2 px-3 py-1.5 rounded-full text-xs",
          "border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)]",
          "text-muted-foreground hover:text-foreground transition-colors cursor-pointer"
        )}
        title={`Theme: ${themeLabel}`}
      >
        <ThemeIcon className="w-3.5 h-3.5" />
        <span className="hidden lg:inline">{themeLabel}</span>
      </button>

      {/* Logout */}
      <button
        onClick={() => void logout()}
        className={cn(
          "flex items-center gap-2 px-3 py-1.5 rounded-full text-xs",
          "border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)]",
          "text-muted-foreground hover:text-[hsl(var(--destructive))] transition-colors cursor-pointer"
        )}
        title="Log out"
      >
        <LogOut className="w-3.5 h-3.5" />
        <span className="hidden lg:inline">Logout</span>
      </button>
    </div>
  );
}

function NotificationBadge() {
  const { data } = useUnreadCount();
  const count = data?.unreadCount ?? 0;
  return (
    <Link
      to="/notifications"
      className={cn(
        "relative p-1.5 rounded-full border transition-colors cursor-pointer",
        count > 0
          ? "border-[hsl(var(--primary)/0.4)] bg-[hsl(var(--primary)/0.08)] text-[hsl(var(--primary))] hover:bg-[hsl(var(--primary)/0.15)]"
          : "border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)] text-muted-foreground hover:text-foreground"
      )}
      title={`${count} unread notifications`}
    >
      <Bell className="w-3.5 h-3.5" />
      {count > 0 && (
        <span className="absolute -top-1 -right-1 w-4 h-4 rounded-full bg-[hsl(var(--primary))] text-white text-[9px] font-bold grid place-items-center">
          {count > 9 ? "9+" : count}
        </span>
      )}
    </Link>
  );
}

function AlertBadge() {
  const { data } = useAlertRules();
  const activeCount = data?.data?.alert_rules?.filter((r) => r.is_enabled).length ?? 0;
  const hasTriggered = data?.data?.alert_rules?.some((r) => r.last_triggered_at) ?? false;
  return (
    <Link
      to="/monitoring/alerts"
      className={cn(
        "relative p-1.5 rounded-full border transition-colors cursor-pointer",
        hasTriggered
          ? "border-[hsl(var(--warning)/0.4)] bg-[hsl(var(--warning)/0.08)] text-[hsl(var(--warning))] hover:bg-[hsl(var(--warning)/0.15)]"
          : "border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)] text-muted-foreground hover:text-foreground"
      )}
      title={`${activeCount} active alert rules`}
    >
      <Bell className="w-3.5 h-3.5" />
      {activeCount > 0 && (
        <span className="absolute -top-1 -right-1 w-4 h-4 rounded-full bg-[hsl(var(--primary))] text-white text-[9px] font-bold grid place-items-center">
          {activeCount > 9 ? "9+" : activeCount}
        </span>
      )}
    </Link>
  );
}
