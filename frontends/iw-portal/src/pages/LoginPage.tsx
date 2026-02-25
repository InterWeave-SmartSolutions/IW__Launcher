import { useState } from "react";
import { Navigate } from "react-router-dom";
import { Shield, Info, Loader2 } from "lucide-react";
import { useAuth } from "@/providers/AuthProvider";

export function LoginPage() {
  const { login, isAuthenticated, isLoading: authLoading } = useAuth();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  // Already logged in — redirect to dashboard
  if (authLoading) {
    return (
      <div className="app-background min-h-screen grid place-items-center">
        <Loader2 className="w-8 h-8 animate-spin text-muted-foreground" />
      </div>
    );
  }
  if (isAuthenticated) return <Navigate to="/dashboard" replace />;

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      await login({ email, password });
      // AuthProvider sets user → isAuthenticated → redirect above fires
    } catch (err) {
      setError(err instanceof Error ? err.message : "Login failed. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app-background min-h-screen grid place-items-center p-6">
      <div className="grid grid-cols-[1.15fr_0.85fr] gap-4 max-w-[940px] w-full max-md:grid-cols-1">
        {/* Left panel — branding (ASSA login pattern) */}
        <div className="glass-panel rounded-[var(--radius)] p-5 space-y-4">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-[14px] bg-gradient-to-br from-[hsl(var(--primary))] to-[hsl(var(--success))] shadow-lg shadow-[hsl(var(--primary)/0.25)]" />
            <div>
              <p className="text-xs text-muted-foreground">InterWeave</p>
              <h1 className="text-xl font-bold">Integration Platform</h1>
            </div>
          </div>
          <hr className="border-[hsl(var(--border))]" />
          <div className="p-4 rounded-[var(--radius)] border border-[hsl(var(--primary)/0.25)] bg-gradient-to-br from-[hsl(var(--primary)/0.14)] to-[hsl(var(--success)/0.08)]">
            <p className="text-base font-bold">Enterprise Data Integration</p>
            <p className="text-sm text-muted-foreground mt-1">
              Monitor transactions, manage configurations, and control your integration workflows.
            </p>
            <div className="flex flex-wrap gap-2 mt-3">
              <span className="inline-flex items-center gap-1.5 text-[11px] px-2 py-1 rounded-full border border-[hsl(var(--border))] text-muted-foreground">
                <span className="w-2 h-2 rounded-full bg-[hsl(var(--success))]" /> Monitoring Dashboard
              </span>
              <span className="inline-flex items-center gap-1.5 text-[11px] px-2 py-1 rounded-full border border-[hsl(var(--border))] text-muted-foreground">
                <span className="w-2 h-2 rounded-full bg-[hsl(var(--success))]" /> Session-based Auth
              </span>
              <span className="inline-flex items-center gap-1.5 text-[11px] px-2 py-1 rounded-full border border-[hsl(var(--border))] text-muted-foreground">
                <span className="w-2 h-2 rounded-full bg-[hsl(var(--success))]" /> Classic View Available
              </span>
            </div>
          </div>
        </div>

        {/* Right panel — login form (ASSA login pattern) */}
        <div className="glass-panel rounded-[var(--radius)] p-5">
          <h1 className="text-xl font-bold">Log in</h1>
          <p className="text-sm text-muted-foreground mt-1">Use your InterWeave credentials.</p>
          <hr className="border-[hsl(var(--border))] my-4" />

          {/* Demo credentials banner */}
          <div className="flex items-start gap-2 p-3 rounded-[14px] border border-[hsl(var(--primary)/0.25)] bg-[hsl(var(--primary)/0.08)] mb-4">
            <Info className="w-4 h-4 text-[hsl(var(--primary))] shrink-0 mt-0.5" />
            <div className="text-xs">
              <p className="font-medium">Demo accounts:</p>
              <p className="text-muted-foreground mt-0.5">Admin: admin@sample.com / admin123</p>
              <p className="text-muted-foreground">User: demo@sample.com / demo123</p>
            </div>
          </div>

          <form onSubmit={handleLogin} className="space-y-3">
            <div>
              <label className="block text-xs text-muted-foreground mb-1.5">Email</label>
              <input
                type="text"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-foreground rounded-[14px] px-3 py-2.5 text-sm outline-none focus:ring-2 focus:ring-[hsl(var(--primary)/0.5)]"
                placeholder="email@company.com"
                autoComplete="email"
              />
            </div>
            <div>
              <label className="block text-xs text-muted-foreground mb-1.5">Password</label>
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="w-full bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-foreground rounded-[14px] px-3 py-2.5 text-sm outline-none focus:ring-2 focus:ring-[hsl(var(--primary)/0.5)]"
                placeholder="••••••••"
                autoComplete="current-password"
              />
            </div>
            {error && <p className="text-xs text-[hsl(var(--destructive))]">{error}</p>}
            <button
              type="submit"
              disabled={loading}
              className="w-full flex items-center justify-center gap-2 px-4 py-2.5 rounded-[14px] bg-[hsl(var(--primary)/0.18)] border border-[hsl(var(--primary)/0.35)] text-sm font-medium hover:bg-[hsl(var(--primary)/0.25)] transition-colors cursor-pointer disabled:opacity-50"
            >
              {loading ? <Loader2 className="w-4 h-4 animate-spin" /> : <Shield className="w-4 h-4" />}
              {loading ? "Signing in..." : "Continue"}
            </button>
          </form>

          <hr className="border-[hsl(var(--border))] my-4" />
          <a
            href="/iw-business-daemon/IWLogin.jsp"
            className="text-xs text-muted-foreground hover:text-foreground"
          >
            Prefer the classic login? →
          </a>
        </div>
      </div>
    </div>
  );
}
