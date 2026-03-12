import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  Shield,
  Loader2,
  ArrowRight,
  Zap,
  Lock,
  Workflow,
  Eye,
  EyeOff,
  ChevronDown,
  ChevronUp,
  UserPlus,
  Building2,
  Activity,
} from "lucide-react";
import { useAuth } from "@/providers/AuthProvider";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { getRoleHome } from "@/hooks/usePortal";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Badge } from "@/components/ui/badge";

const FEATURES = [
  {
    icon: Workflow,
    title: "Visual Integration Builder",
    desc: "Connect CRM, ERP, and financial systems with drag-and-drop workflows",
  },
  {
    icon: Activity,
    title: "Live Monitoring",
    desc: "Track transactions, success rates, and performance in real time",
  },
  {
    icon: Lock,
    title: "Enterprise-Grade Security",
    desc: "Role-based access, audit trails, and encrypted data flows",
  },
  {
    icon: Zap,
    title: "Automated Sync Engine",
    desc: "Scheduled and on-demand data synchronization between any platform",
  },
];

const PLATFORMS = ["Salesforce", "QuickBooks", "Creatio", "NetSuite", "Magento 2", "Dynamics", "Authorize.Net"];

export function LoginPage() {
  useDocumentTitle("Sign In");
  const { login, isAuthenticated, isLoading: authLoading, user, mfaRequired } = useAuth();
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [showDemo, setShowDemo] = useState(false);

  // Redirect to MFA verification if required
  useEffect(() => {
    if (mfaRequired) {
      navigate("/mfa/verify", { replace: true });
    }
  }, [mfaRequired, navigate]);

  // Redirect to role-appropriate home if already authenticated
  useEffect(() => {
    if (isAuthenticated && !authLoading && user) {
      navigate(getRoleHome(user.role ?? "operator"), { replace: true });
    }
  }, [isAuthenticated, authLoading, user, navigate]);

  if (authLoading) {
    return (
      <div className="app-background min-h-screen grid place-items-center">
        <Loader2 className="w-8 h-8 animate-spin text-muted-foreground" />
      </div>
    );
  }

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!email.trim() || !password) {
      setError("Email and password are required.");
      return;
    }
    setLoading(true);
    setError("");
    try {
      await login({ email: email.trim(), password });
      // Check if MFA is required (AuthProvider sets mfaRequired flag)
      // We need to check via the auth context after login returns
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Login failed. Please try again."
      );
    } finally {
      setLoading(false);
    }
  };

  const fillDemo = (demoEmail: string, demoPass: string) => {
    setEmail(demoEmail);
    setPassword(demoPass);
    setError("");
  };

  return (
    <div className="app-background min-h-screen flex items-center justify-center p-4 sm:p-6">
      <div className="grid grid-cols-1 md:grid-cols-[1.15fr_0.85fr] gap-4 max-w-[960px] w-full">
        {/* ── Left panel: Branding + value proposition ── */}
        <div className="glass-panel rounded-[var(--radius)] p-6 sm:p-8 flex flex-col max-md:order-2">
          {/* Logo + name */}
          <div className="flex items-center gap-3 mb-8">
            <div className="w-11 h-11 rounded-[14px] bg-gradient-to-br from-[hsl(var(--primary))] to-[hsl(var(--success))] shadow-lg shadow-[hsl(var(--primary)/0.3)] grid place-items-center">
              <Zap className="w-5 h-5 text-white" />
            </div>
            <div>
              <h1 className="text-lg font-bold tracking-tight">InterWeave</h1>
              <p className="text-xs text-muted-foreground">
                Integration Platform
              </p>
            </div>
          </div>

          {/* Hero section */}
          <div className="relative rounded-[var(--radius)] border border-[hsl(var(--primary)/0.2)] bg-gradient-to-br from-[hsl(var(--primary)/0.12)] via-[hsl(var(--primary)/0.06)] to-[hsl(var(--success)/0.08)] p-6 mb-8 overflow-hidden">
            {/* Decorative gradient orbs */}
            <div className="absolute -top-12 -right-12 w-32 h-32 rounded-full bg-[hsl(var(--primary)/0.15)] blur-2xl" />
            <div className="absolute -bottom-8 -left-8 w-24 h-24 rounded-full bg-[hsl(var(--success)/0.12)] blur-2xl" />
            <div className="relative">
              <h2 className="text-2xl font-bold leading-tight tracking-tight">
                Connect your business
                <br />
                <span className="text-[hsl(var(--primary))]">systems seamlessly</span>
              </h2>
              <p className="text-sm text-muted-foreground mt-3 leading-relaxed max-w-[380px]">
                Configure, deploy, and monitor enterprise data integrations
                between CRM, ERP, and financial platforms — all from one portal.
              </p>
            </div>
          </div>

          {/* Feature list (vertical — cleaner than grid) */}
          <div className="space-y-3 flex-1">
            {FEATURES.map((f) => (
              <div
                key={f.title}
                className="flex items-start gap-3 group"
              >
                <div className="w-8 h-8 rounded-[10px] border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.2)] grid place-items-center shrink-0 group-hover:border-[hsl(var(--primary)/0.3)] group-hover:bg-[hsl(var(--primary)/0.08)] transition-colors">
                  <f.icon className="w-4 h-4 text-[hsl(var(--primary))]" />
                </div>
                <div className="min-w-0">
                  <p className="text-sm font-medium leading-tight">{f.title}</p>
                  <p className="text-xs text-muted-foreground leading-snug mt-0.5">
                    {f.desc}
                  </p>
                </div>
              </div>
            ))}
          </div>

          {/* Supported platforms */}
          <div className="mt-6 pt-4 border-t border-[hsl(var(--border))]">
            <p className="text-[11px] text-muted-foreground mb-2.5 uppercase tracking-wider font-medium">
              Supported Platforms
            </p>
            <div className="flex flex-wrap gap-1.5">
              {PLATFORMS.map((name) => (
                <Badge
                  key={name}
                  variant="outline"
                  className="text-[10px] px-2.5 rounded-full text-muted-foreground hover:text-foreground hover:border-[hsl(var(--primary)/0.3)] transition-colors"
                >
                  {name}
                </Badge>
              ))}
            </div>
          </div>
        </div>

        {/* ── Right panel: Auth form ── */}
        <div className="glass-panel rounded-[var(--radius)] p-6 sm:p-8 flex flex-col max-md:order-1">
          <div className="flex-1">
            {/* Status pill */}
            <Badge variant="success" className="rounded-full mb-5">
              <span className="w-2 h-2 rounded-full bg-[hsl(var(--success))] animate-pulse" />
              Services online
            </Badge>

            {isAuthenticated ? (
              /* ── Already signed in ── */
              <div className="flex flex-col items-center text-center py-6">
                <div className="w-14 h-14 rounded-2xl bg-[hsl(var(--success)/0.15)] grid place-items-center mb-4">
                  <Shield className="w-7 h-7 text-[hsl(var(--success))]" />
                </div>
                <h2 className="text-xl font-bold tracking-tight">You're signed in</h2>
                <p className="text-sm text-muted-foreground mt-1">
                  Welcome back{user?.userName ? `, ${user.userName}` : ""}. Continue to your dashboard.
                </p>
                <Button
                  onClick={() => navigate(getRoleHome(user?.role ?? "operator"))}
                  className="w-full mt-6"
                >
                  Continue to Dashboard
                  <ArrowRight className="w-4 h-4" />
                </Button>
                <a
                  href="/iw-business-daemon/IWLogin.jsp"
                  className="text-xs text-muted-foreground hover:text-foreground transition-colors mt-4"
                >
                  Return to classic portal
                </a>
              </div>
            ) : (
            /* ── Login form ── */
            <>
            <h2 className="text-xl font-bold tracking-tight">Welcome back</h2>
            <p className="text-sm text-muted-foreground mt-1">
              Sign in to your InterWeave account
            </p>

            <form onSubmit={handleLogin} className="mt-6 space-y-4">
              {/* Email */}
              <div>
                <Label htmlFor="login-email" className="mb-1.5">
                  Email address
                </Label>
                <Input
                  id="login-email"
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  placeholder="you@company.com"
                  autoComplete="email"
                  autoFocus
                />
              </div>

              {/* Password */}
              <div>
                <div className="flex items-center justify-between mb-1.5">
                  <Label htmlFor="login-password">
                    Password
                  </Label>
                  <Link
                    to="/forgot-password"
                    className="text-[11px] text-[hsl(var(--primary))] hover:underline"
                  >
                    Forgot password?
                  </Link>
                </div>
                <div className="relative">
                  <Input
                    id="login-password"
                    type={showPassword ? "text" : "password"}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="pr-10"
                    placeholder="Enter your password"
                    autoComplete="current-password"
                  />
                  <button
                    type="button"
                    onClick={() => setShowPassword(!showPassword)}
                    className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground cursor-pointer"
                    aria-label={showPassword ? "Hide password" : "Show password"}
                  >
                    {showPassword ? (
                      <EyeOff className="w-4 h-4" />
                    ) : (
                      <Eye className="w-4 h-4" />
                    )}
                  </button>
                </div>
              </div>

              {/* Error */}
              {error && (
                <div role="alert" className="text-xs text-[hsl(var(--destructive))] bg-[hsl(var(--destructive)/0.08)] border border-[hsl(var(--destructive)/0.2)] rounded-[10px] px-3 py-2">
                  {error}
                </div>
              )}

              {/* Submit */}
              <Button type="submit" disabled={loading} className="w-full">
                {loading ? (
                  <Loader2 className="w-4 h-4 animate-spin" />
                ) : (
                  <Shield className="w-4 h-4" />
                )}
                {loading ? "Signing in..." : "Sign in"}
                {!loading && <ArrowRight className="w-4 h-4" />}
              </Button>
            </form>

            {/* Demo credentials (collapsible) */}
            <div className="mt-4">
              <Button
                type="button"
                variant="ghost"
                size="sm"
                onClick={() => setShowDemo(!showDemo)}
                className="px-0 text-muted-foreground hover:text-foreground"
              >
                {showDemo ? (
                  <ChevronUp className="w-3.5 h-3.5" />
                ) : (
                  <ChevronDown className="w-3.5 h-3.5" />
                )}
                Demo credentials
              </Button>
              {showDemo && (
                <div className="mt-2 space-y-1.5">
                  <Button
                    type="button"
                    variant="outline"
                    size="sm"
                    onClick={() => fillDemo("admin@sample.com", "admin123")}
                    className="w-full justify-start text-xs h-auto py-1.5"
                  >
                    <div className="text-left">
                      <div><span className="font-medium">Sarah Chen</span> <span className="text-muted-foreground">— Pinnacle Integrations</span></div>
                      <div className="text-muted-foreground">SF2NS · Salesforce → NetSuite · admin</div>
                    </div>
                  </Button>
                  <Button
                    type="button"
                    variant="outline"
                    size="sm"
                    onClick={() => fillDemo("demo@sample.com", "demo123")}
                    className="w-full justify-start text-xs h-auto py-1.5"
                  >
                    <div className="text-left">
                      <div><span className="font-medium">Demo User</span> <span className="text-muted-foreground">— Demo Company Inc.</span></div>
                      <div className="text-muted-foreground">CRM2QB · Creatio → QuickBooks · user</div>
                    </div>
                  </Button>
                  <Button
                    type="button"
                    variant="outline"
                    size="sm"
                    onClick={() => fillDemo("admin@magentocrm.com", "magcrm123")}
                    className="w-full justify-start text-xs h-auto py-1.5"
                  >
                    <div className="text-left">
                      <div><span className="font-medium">Maria Garcia</span> <span className="text-muted-foreground">— MagentoCRM Solutions</span></div>
                      <div className="text-muted-foreground">CRM2MG2 · Creatio ↔ Magento 2 · admin</div>
                    </div>
                  </Button>
                </div>
              )}
            </div>
            </>
            )}
          </div>

          {/* Registration links (unauthenticated only) */}
          {!isAuthenticated && (
          <div className="mt-6 pt-4 border-t border-[hsl(var(--border))] space-y-3">
            <p className="text-xs text-muted-foreground">New to InterWeave?</p>
            <div className="grid grid-cols-2 gap-2">
              <Link
                to="/register"
                className="flex items-center gap-2.5 px-3 py-2.5 rounded-[14px] border border-[hsl(var(--border))] text-sm hover:bg-[hsl(var(--muted)/0.3)] hover:border-[hsl(var(--primary)/0.3)] transition-colors"
              >
                <UserPlus className="w-4 h-4 text-[hsl(var(--primary))] shrink-0" />
                <div className="min-w-0">
                  <p className="text-xs font-medium">Join a team</p>
                  <p className="text-[10px] text-muted-foreground">
                    Register as user
                  </p>
                </div>
              </Link>
              <Link
                to="/register/company"
                className="flex items-center gap-2.5 px-3 py-2.5 rounded-[14px] border border-[hsl(var(--border))] text-sm hover:bg-[hsl(var(--muted)/0.3)] hover:border-[hsl(var(--success)/0.3)] transition-colors"
              >
                <Building2 className="w-4 h-4 text-[hsl(var(--success))] shrink-0" />
                <div className="min-w-0">
                  <p className="text-xs font-medium">New company</p>
                  <p className="text-[10px] text-muted-foreground">
                    Register organization
                  </p>
                </div>
              </Link>
            </div>

            <a
              href="/iw-business-daemon/IWLogin.jsp"
              className="block text-xs text-muted-foreground hover:text-foreground transition-colors text-center"
            >
              Prefer the classic portal? →
            </a>
          </div>
          )}
        </div>
      </div>
    </div>
  );
}
