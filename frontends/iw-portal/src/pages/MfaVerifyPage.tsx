import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  Shield,
  Lock,
  Loader2,
  KeyRound,
} from "lucide-react";
import { useMfaValidate } from "@/hooks/useMfa";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

export function MfaVerifyPage() {
  useDocumentTitle("Verify MFA");
  const navigate = useNavigate();
  const mfaValidate = useMfaValidate();

  const [code, setCode] = useState("");
  const [backupCode, setBackupCode] = useState("");
  const [useBackup, setUseBackup] = useState(false);
  const [error, setError] = useState("");

  const handleVerify = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");

    if (useBackup) {
      if (!backupCode.trim()) {
        setError("Backup code is required.");
        return;
      }
    } else {
      if (!code.trim() || code.trim().length !== 6) {
        setError("Enter a valid 6-digit code.");
        return;
      }
    }

    try {
      const payload = useBackup
        ? { backupCode: backupCode.trim() }
        : { code: code.trim() };

      const res = await mfaValidate.mutateAsync(payload);
      if (res.success) {
        navigate("/dashboard", { replace: true });
      } else {
        setError(res.error ?? "Invalid code. Please try again.");
      }
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Verification failed."
      );
    }
  };

  const handleCancel = async () => {
    // Clear session by navigating to login
    try {
      await fetch("/iw-business-daemon/LogoutServlet", {
        credentials: "include",
      });
    } catch {
      // Ignore — navigate regardless
    }
    navigate("/login", { replace: true });
  };

  return (
    <div className="app-background min-h-screen flex items-center justify-center p-4">
      <div className="max-w-sm w-full">
        <div className="glass-panel rounded-[var(--radius)] p-6">
          {/* Header */}
          <div className="flex flex-col items-center text-center mb-6">
            <div className="w-14 h-14 rounded-2xl bg-[hsl(var(--primary)/0.15)] border border-[hsl(var(--primary)/0.3)] grid place-items-center mb-4">
              <div className="relative">
                <Shield className="w-7 h-7 text-[hsl(var(--primary))]" />
                <Lock className="w-3 h-3 text-[hsl(var(--primary))] absolute -bottom-0.5 -right-0.5" />
              </div>
            </div>
            <h1 className="text-xl font-bold tracking-tight">
              Two-Factor Authentication
            </h1>
            <p className="text-sm text-muted-foreground mt-1">
              {useBackup
                ? "Enter one of your backup codes"
                : "Enter the 6-digit code from your authenticator app"}
            </p>
          </div>

          <form onSubmit={handleVerify} className="space-y-4">
            {useBackup ? (
              /* Backup code input */
              <div>
                <Label htmlFor="mfa-backup" className="mb-1.5">
                  Backup Code
                </Label>
                <Input
                  id="mfa-backup"
                  type="text"
                  value={backupCode}
                  onChange={(e) => setBackupCode(e.target.value)}
                  placeholder="Enter backup code"
                  className="font-mono"
                  autoComplete="off"
                  autoFocus
                />
              </div>
            ) : (
              /* TOTP code input */
              <div>
                <Label htmlFor="mfa-code" className="mb-1.5">
                  Authentication Code
                </Label>
                <Input
                  id="mfa-code"
                  type="text"
                  inputMode="numeric"
                  maxLength={6}
                  pattern="[0-9]*"
                  value={code}
                  onChange={(e) => {
                    const val = e.target.value.replace(/\D/g, "").slice(0, 6);
                    setCode(val);
                  }}
                  placeholder="000000"
                  className="text-center text-2xl tracking-[0.5em] font-mono"
                  autoComplete="one-time-code"
                  autoFocus
                />
              </div>
            )}

            {/* Error */}
            {error && (
              <div className="text-xs text-[hsl(var(--destructive))] bg-[hsl(var(--destructive)/0.08)] border border-[hsl(var(--destructive)/0.2)] rounded-[10px] px-3 py-2">
                {error}
              </div>
            )}

            {/* Submit */}
            <Button
              type="submit"
              disabled={
                mfaValidate.isPending ||
                (!useBackup && code.length !== 6) ||
                (useBackup && !backupCode.trim())
              }
              className="w-full"
            >
              {mfaValidate.isPending ? (
                <Loader2 className="w-4 h-4 animate-spin" />
              ) : (
                <Shield className="w-4 h-4" />
              )}
              {mfaValidate.isPending ? "Verifying..." : "Verify"}
            </Button>
          </form>

          {/* Toggle backup / TOTP */}
          <div className="mt-4 pt-4 border-t border-[hsl(var(--border))] flex flex-col items-center gap-2">
            <button
              type="button"
              onClick={() => {
                setUseBackup(!useBackup);
                setError("");
                setCode("");
                setBackupCode("");
              }}
              className="text-xs text-[hsl(var(--primary))] hover:underline cursor-pointer inline-flex items-center gap-1.5"
            >
              <KeyRound className="w-3 h-3" />
              {useBackup
                ? "Use authenticator app instead"
                : "Use a backup code"}
            </button>

            <Link
              to="/login"
              onClick={(e) => {
                e.preventDefault();
                handleCancel();
              }}
              className="text-xs text-muted-foreground hover:text-foreground transition-colors"
            >
              Cancel and return to sign in
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}
