import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  Lock,
  Loader2,
  CheckCircle2,
  ArrowLeft,
  ArrowRight,
  Mail,
  KeyRound,
  Eye,
  EyeOff,
} from "lucide-react";
import { useRequestPasswordReset, useResetPassword } from "@/hooks/useMfa";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Separator } from "@/components/ui/separator";

type Step = "request" | "requested" | "reset" | "done";

export function ForgotPasswordPage() {
  useDocumentTitle("Forgot Password");
  const navigate = useNavigate();
  const requestReset = useRequestPasswordReset();
  const resetPassword = useResetPassword();

  const [step, setStep] = useState<Step>("request");
  const [email, setEmail] = useState("");
  const [token, setToken] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState("");

  // Step 1: Request reset
  const handleRequestReset = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!email.trim()) {
      setError("Email address is required.");
      return;
    }
    setError("");
    try {
      await requestReset.mutateAsync({ email: email.trim() });
      setStep("requested");
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Failed to request password reset."
      );
    }
  };

  // Step 2: Reset password with token
  const handleResetPassword = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!token.trim()) {
      setError("Reset token is required.");
      return;
    }
    if (!newPassword) {
      setError("New password is required.");
      return;
    }
    if (newPassword.length < 4) {
      setError("Password must be at least 4 characters.");
      return;
    }
    if (newPassword !== confirmPassword) {
      setError("Passwords do not match.");
      return;
    }
    setError("");
    try {
      const res = await resetPassword.mutateAsync({
        token: token.trim(),
        newPassword,
        confirmPassword,
      });
      if (res.success) {
        setStep("done");
      } else {
        setError(res.error ?? "Failed to reset password.");
      }
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Failed to reset password."
      );
    }
  };

  // Success state
  if (step === "done") {
    return (
      <div className="app-background min-h-screen flex items-center justify-center p-4">
        <div className="max-w-md w-full">
          <div className="glass-panel rounded-[var(--radius)] p-6 text-center">
            <CheckCircle2 className="w-12 h-12 text-[hsl(var(--success))] mx-auto mb-4" />
            <h2 className="text-xl font-bold">Password Reset</h2>
            <p className="text-sm text-muted-foreground mt-2">
              Your password has been reset successfully. You can now sign in with
              your new password.
            </p>
            <Button
              onClick={() => navigate("/login")}
              className="mt-6"
            >
              <ArrowRight className="w-4 h-4" />
              Go to Sign In
            </Button>
          </div>
        </div>
      </div>
    );
  }

  // Requested state — show instructions + reset form
  if (step === "requested" || step === "reset") {
    return (
      <div className="app-background min-h-screen flex items-center justify-center p-4">
        <div className="max-w-md w-full space-y-4">
          <Link
            to="/login"
            className="inline-flex items-center gap-1.5 text-sm text-muted-foreground hover:text-foreground transition-colors"
          >
            <ArrowLeft className="w-3.5 h-3.5" />
            Back to Sign In
          </Link>

          {/* Info banner */}
          {step === "requested" && (
            <div className="glass-panel rounded-[var(--radius)] p-4">
              <div className="flex items-start gap-3">
                <Mail className="w-5 h-5 text-[hsl(var(--primary))] mt-0.5 shrink-0" />
                <div>
                  <p className="text-sm font-medium">Check your email</p>
                  <p className="text-xs text-muted-foreground mt-1">
                    If an account exists for <strong>{email}</strong>, a
                    password reset token has been generated. Check the server
                    logs for the token (email delivery is not yet configured).
                  </p>
                  <Button
                    variant="ghost"
                    size="sm"
                    onClick={() => setStep("reset")}
                    className="mt-2 text-[hsl(var(--primary))] px-0"
                  >
                    I have my token
                    <ArrowRight className="w-3.5 h-3.5" />
                  </Button>
                </div>
              </div>
            </div>
          )}

          {/* Reset form */}
          <div className="glass-panel rounded-[var(--radius)] p-6">
            <div className="flex items-center gap-3 mb-5">
              <div className="w-10 h-10 rounded-[14px] bg-[hsl(var(--primary)/0.15)] border border-[hsl(var(--primary)/0.3)] grid place-items-center">
                <KeyRound className="w-5 h-5 text-[hsl(var(--primary))]" />
              </div>
              <div>
                <h1 className="text-lg font-bold">Reset Password</h1>
                <p className="text-xs text-muted-foreground">
                  Enter your token and a new password
                </p>
              </div>
            </div>

            <form onSubmit={handleResetPassword} className="space-y-4">
              <div>
                <Label htmlFor="fp-token" className="mb-1.5">
                  Reset Token
                </Label>
                <Input
                  id="fp-token"
                  type="text"
                  value={token}
                  onChange={(e) => setToken(e.target.value)}
                  placeholder="Paste your reset token"
                  autoComplete="off"
                  autoFocus
                />
              </div>

              <Separator />

              <div>
                <Label htmlFor="fp-new" className="mb-1.5">
                  New Password
                </Label>
                <div className="relative">
                  <Input
                    id="fp-new"
                    type={showPassword ? "text" : "password"}
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    className="pr-10"
                    placeholder="Min. 4 characters"
                    autoComplete="new-password"
                  />
                  <button
                    type="button"
                    onClick={() => setShowPassword(!showPassword)}
                    className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground cursor-pointer"
                    tabIndex={-1}
                  >
                    {showPassword ? (
                      <EyeOff className="w-4 h-4" />
                    ) : (
                      <Eye className="w-4 h-4" />
                    )}
                  </button>
                </div>
              </div>

              <div>
                <Label htmlFor="fp-confirm" className="mb-1.5">
                  Confirm New Password
                </Label>
                <Input
                  id="fp-confirm"
                  type={showPassword ? "text" : "password"}
                  value={confirmPassword}
                  onChange={(e) => setConfirmPassword(e.target.value)}
                  placeholder="Repeat new password"
                  autoComplete="new-password"
                />
              </div>

              {error && (
                <div className="text-xs text-[hsl(var(--destructive))] bg-[hsl(var(--destructive)/0.08)] border border-[hsl(var(--destructive)/0.2)] rounded-[10px] px-3 py-2">
                  {error}
                </div>
              )}

              <Button
                type="submit"
                disabled={resetPassword.isPending}
                className="w-full"
              >
                {resetPassword.isPending ? (
                  <Loader2 className="w-4 h-4 animate-spin" />
                ) : (
                  <Lock className="w-4 h-4" />
                )}
                {resetPassword.isPending
                  ? "Resetting password..."
                  : "Reset Password"}
              </Button>
            </form>
          </div>
        </div>
      </div>
    );
  }

  // Default: Request step
  return (
    <div className="app-background min-h-screen flex items-center justify-center p-4">
      <div className="max-w-md w-full space-y-4">
        <Link
          to="/login"
          className="inline-flex items-center gap-1.5 text-sm text-muted-foreground hover:text-foreground transition-colors"
        >
          <ArrowLeft className="w-3.5 h-3.5" />
          Back to Sign In
        </Link>

        <div className="glass-panel rounded-[var(--radius)] p-6">
          <div className="flex items-center gap-3 mb-5">
            <div className="w-10 h-10 rounded-[14px] bg-[hsl(var(--primary)/0.15)] border border-[hsl(var(--primary)/0.3)] grid place-items-center">
              <Lock className="w-5 h-5 text-[hsl(var(--primary))]" />
            </div>
            <div>
              <h1 className="text-lg font-bold">Forgot Password</h1>
              <p className="text-xs text-muted-foreground">
                Enter your email to receive a reset token
              </p>
            </div>
          </div>

          <form onSubmit={handleRequestReset} className="space-y-4">
            <div>
              <Label htmlFor="fp-email" className="mb-1.5">
                Email Address
              </Label>
              <Input
                id="fp-email"
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="you@company.com"
                autoComplete="email"
                autoFocus
              />
            </div>

            {error && (
              <div className="text-xs text-[hsl(var(--destructive))] bg-[hsl(var(--destructive)/0.08)] border border-[hsl(var(--destructive)/0.2)] rounded-[10px] px-3 py-2">
                {error}
              </div>
            )}

            <Button
              type="submit"
              disabled={requestReset.isPending}
              className="w-full"
            >
              {requestReset.isPending ? (
                <Loader2 className="w-4 h-4 animate-spin" />
              ) : (
                <Mail className="w-4 h-4" />
              )}
              {requestReset.isPending
                ? "Sending..."
                : "Send Reset Token"}
            </Button>
          </form>

          <div className="mt-4 pt-4 border-t border-[hsl(var(--border))]">
            <p className="text-xs text-muted-foreground">
              Already have a reset token?{" "}
              <button
                type="button"
                onClick={() => setStep("reset")}
                className="text-[hsl(var(--primary))] hover:underline cursor-pointer"
              >
                Enter it here
              </button>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
