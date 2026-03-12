import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  UserPlus,
  Loader2,
  ArrowRight,
  ArrowLeft,
  Eye,
  EyeOff,
  CheckCircle2,
} from "lucide-react";
import { apiFetch } from "@/lib/api";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

interface RegisterForm {
  companyName: string;
  email: string;
  firstName: string;
  lastName: string;
  title: string;
  password: string;
  confirmPassword: string;
}

export function RegisterPage() {
  useDocumentTitle("Register");
  const navigate = useNavigate();
  const [form, setForm] = useState<RegisterForm>({
    companyName: "",
    email: "",
    firstName: "",
    lastName: "",
    title: "",
    password: "",
    confirmPassword: "",
  });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  const set = (field: keyof RegisterForm) => (e: React.ChangeEvent<HTMLInputElement>) =>
    setForm((prev) => ({ ...prev, [field]: e.target.value }));

  const validate = (): string | null => {
    if (!form.companyName.trim()) return "Company name is required.";
    if (!form.email.trim()) return "Email is required.";
    if (!form.firstName.trim()) return "First name is required.";
    if (!form.lastName.trim()) return "Last name is required.";
    if (!form.password) return "Password is required.";
    if (form.password.length < 4) return "Password must be at least 4 characters.";
    if (form.password !== form.confirmPassword) return "Passwords do not match.";
    return null;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const err = validate();
    if (err) {
      setError(err);
      return;
    }
    setLoading(true);
    setError("");
    try {
      await apiFetch("/api/register", {
        method: "POST",
        body: JSON.stringify({
          companyName: form.companyName.trim(),
          email: form.email.trim(),
          firstName: form.firstName.trim(),
          lastName: form.lastName.trim(),
          title: form.title.trim(),
          password: form.password,
          confirmPassword: form.confirmPassword,
        }),
      });
      setSuccess(true);
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Registration failed. Please try again."
      );
    } finally {
      setLoading(false);
    }
  };

  if (success) {
    return (
      <div className="app-background min-h-screen flex items-center justify-center p-4">
        <div className="glass-panel rounded-[var(--radius)] p-6 max-w-md w-full text-center">
          <CheckCircle2 className="w-12 h-12 text-[hsl(var(--success))] mx-auto mb-4" />
          <h2 className="text-xl font-bold">Registration Successful</h2>
          <p className="text-sm text-muted-foreground mt-2">
            Your account has been created. You can now sign in with your credentials.
          </p>
          <Button onClick={() => navigate("/login")} className="mt-6 mx-auto">
            Continue to Sign In <ArrowRight className="w-4 h-4" />
          </Button>
        </div>
      </div>
    );
  }

  return (
    <div className="app-background min-h-screen flex items-center justify-center p-4 sm:p-6">
      <div className="glass-panel rounded-[var(--radius)] p-6 max-w-lg w-full">
        {/* Header */}
        <div className="flex items-center gap-3 mb-5">
          <div className="w-10 h-10 rounded-[14px] bg-[hsl(var(--primary)/0.15)] border border-[hsl(var(--primary)/0.3)] grid place-items-center">
            <UserPlus className="w-5 h-5 text-[hsl(var(--primary))]" />
          </div>
          <div>
            <h1 className="text-lg font-bold">Join your team</h1>
            <p className="text-xs text-muted-foreground">
              Register as a user in an existing company
            </p>
          </div>
        </div>

        <form onSubmit={handleSubmit} className="space-y-3.5">
          {/* Company name */}
          <div>
            <Label htmlFor="reg-company" className="mb-1.5">
              Company / Organization *
            </Label>
            <Input
              id="reg-company"
              value={form.companyName}
              onChange={set("companyName")}
              maxLength={255}
              placeholder="Your company name (must already be registered)"
            />
          </div>

          {/* Name row */}
          <div className="grid grid-cols-2 gap-3">
            <div>
              <Label htmlFor="reg-first" className="mb-1.5">
                First Name *
              </Label>
              <Input
                id="reg-first"
                value={form.firstName}
                onChange={set("firstName")}
                maxLength={45}
                placeholder="First"
              />
            </div>
            <div>
              <Label htmlFor="reg-last" className="mb-1.5">
                Last Name *
              </Label>
              <Input
                id="reg-last"
                value={form.lastName}
                onChange={set("lastName")}
                maxLength={45}
                placeholder="Last"
              />
            </div>
          </div>

          {/* Email */}
          <div>
            <Label htmlFor="reg-email" className="mb-1.5">
              Email *
            </Label>
            <Input
              id="reg-email"
              type="email"
              value={form.email}
              onChange={set("email")}
              maxLength={255}
              placeholder="you@company.com"
              autoComplete="email"
            />
          </div>

          {/* Title (optional) */}
          <div>
            <Label htmlFor="reg-title" className="mb-1.5">
              Title <span className="text-muted-foreground/60">(optional)</span>
            </Label>
            <Input
              id="reg-title"
              value={form.title}
              onChange={set("title")}
              maxLength={255}
              placeholder="e.g. Integration Architect"
            />
          </div>

          {/* Password row */}
          <div className="grid grid-cols-2 gap-3">
            <div>
              <Label htmlFor="reg-pass" className="mb-1.5">
                Password *
              </Label>
              <div className="relative">
                <Input
                  id="reg-pass"
                  type={showPassword ? "text" : "password"}
                  value={form.password}
                  onChange={set("password")}
                  maxLength={255}
                  className="pr-10"
                  placeholder="Min. 4 characters"
                  autoComplete="new-password"
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground cursor-pointer"
                  aria-label={showPassword ? "Hide password" : "Show password"}
                >
                  {showPassword ? <EyeOff className="w-4 h-4" /> : <Eye className="w-4 h-4" />}
                </button>
              </div>
            </div>
            <div>
              <Label htmlFor="reg-confirm" className="mb-1.5">
                Confirm Password *
              </Label>
              <Input
                id="reg-confirm"
                type={showPassword ? "text" : "password"}
                value={form.confirmPassword}
                onChange={set("confirmPassword")}
                maxLength={255}
                placeholder="Repeat password"
                autoComplete="new-password"
              />
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
            {loading ? <Loader2 className="w-4 h-4 animate-spin" /> : <UserPlus className="w-4 h-4" />}
            {loading ? "Creating account..." : "Create Account"}
          </Button>
        </form>

        {/* Footer links */}
        <div className="mt-5 pt-4 border-t border-[hsl(var(--border))] flex items-center justify-between">
          <Link
            to="/login"
            className="flex items-center gap-1.5 text-xs text-muted-foreground hover:text-foreground transition-colors"
          >
            <ArrowLeft className="w-3.5 h-3.5" /> Back to sign in
          </Link>
          <Link
            to="/register/company"
            className="text-xs text-[hsl(var(--primary))] hover:underline"
          >
            Register a new company →
          </Link>
        </div>
      </div>
    </div>
  );
}
