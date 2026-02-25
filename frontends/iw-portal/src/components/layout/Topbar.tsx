import { Search, Sun, Moon, Monitor, LogOut, User } from "lucide-react";
import { useTheme } from "@/providers/ThemeProvider";
import { useAuth } from "@/providers/AuthProvider";
import { cn } from "@/lib/utils";

export function Topbar() {
  const { theme, setTheme, resolvedTheme } = useTheme();
  const { user, logout } = useAuth();

  const cycleTheme = () => {
    const order: Array<"dark" | "light" | "system"> = ["dark", "light", "system"];
    const idx = order.indexOf(theme);
    setTheme(order[(idx + 1) % order.length]!);
  };

  const ThemeIcon = theme === "system" ? Monitor : resolvedTheme === "dark" ? Moon : Sun;
  const themeLabel = theme === "system" ? "System" : theme === "dark" ? "Dark" : "Light";

  return (
    <div className="flex items-center gap-3 px-6 py-3 border-b border-[hsl(var(--border))]">
      {/* Search bar (ASSA pattern) */}
      <div className="flex-1 flex items-center gap-2 px-3 py-2 rounded-full border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)]">
        <Search className="w-4 h-4 text-muted-foreground shrink-0" />
        <input
          type="text"
          placeholder="Search pages, transactions, settings..."
          className="flex-1 bg-transparent border-none outline-none text-sm text-foreground placeholder:text-muted-foreground"
        />
      </div>

      {/* User pill */}
      {user && (
        <div className="flex items-center gap-2 px-3 py-1.5 rounded-full border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.3)] text-xs text-muted-foreground">
          <User className="w-3.5 h-3.5" />
          <span className="max-w-[120px] truncate">{user.userName}</span>
        </div>
      )}

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
        <span>{themeLabel}</span>
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
        <span>Logout</span>
      </button>
    </div>
  );
}
