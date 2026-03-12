import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  Shield,
  Loader2,
  CheckCircle2,
  Copy,
  Eye,
  EyeOff,
  Lock,
  ArrowLeft,
  AlertTriangle,
  ShieldCheck,
  ShieldOff,
} from "lucide-react";
import {
  useMfaStatus,
  useMfaSetup,
  useMfaVerify,
  useMfaDisable,
} from "@/hooks/useMfa";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { useToast } from "@/providers/ToastProvider";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Separator } from "@/components/ui/separator";

type SetupStep = "status" | "setup" | "verify" | "backup" | "disable";

export function MfaSetupPage() {
  useDocumentTitle("Security Settings");
  const navigate = useNavigate();
  const { showToast } = useToast();

  const mfaStatus = useMfaStatus();
  const mfaSetup = useMfaSetup();
  const mfaVerify = useMfaVerify();
  const mfaDisable = useMfaDisable();

  const [step, setStep] = useState<SetupStep>("status");
  const [secret, setSecret] = useState("");
  const [otpauthUri, setOtpauthUri] = useState("");
  const [verifyCode, setVerifyCode] = useState("");
  const [backupCodes, setBackupCodes] = useState<string[]>([]);
  const [disablePassword, setDisablePassword] = useState("");
  const [showDisablePassword, setShowDisablePassword] = useState(false);
  const [error, setError] = useState("");
  const [copiedSecret, setCopiedSecret] = useState(false);
  const [copiedBackup, setCopiedBackup] = useState(false);

  const isEnabled = mfaStatus.data?.mfaEnabled ?? false;

  const handleEnableMfa = async () => {
    setError("");
    try {
      const res = await mfaSetup.mutateAsync();
      if (res.success) {
        setSecret(res.secret);
        setOtpauthUri(res.otpauthUri);
        setStep("setup");
      }
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Failed to start MFA setup."
      );
    }
  };

  const handleVerifyCode = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!verifyCode.trim() || verifyCode.trim().length !== 6) {
      setError("Enter a valid 6-digit code.");
      return;
    }
    setError("");
    try {
      const res = await mfaVerify.mutateAsync({ code: verifyCode.trim() });
      if (res.success && res.backupCodes) {
        setBackupCodes(res.backupCodes);
        setStep("backup");
        showToast("MFA enabled successfully", "success");
      } else {
        setError(res.error ?? "Invalid verification code.");
      }
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Verification failed."
      );
    }
  };

  const handleDisableMfa = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!disablePassword) {
      setError("Password is required to disable MFA.");
      return;
    }
    setError("");
    try {
      const res = await mfaDisable.mutateAsync({
        password: disablePassword,
      });
      if (res.success) {
        setStep("status");
        setDisablePassword("");
        showToast("MFA has been disabled", "success");
      } else {
        setError(res.error ?? "Failed to disable MFA.");
      }
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Failed to disable MFA."
      );
    }
  };

  const copyToClipboard = async (text: string, type: "secret" | "backup") => {
    try {
      await navigator.clipboard.writeText(text);
      if (type === "secret") {
        setCopiedSecret(true);
        setTimeout(() => setCopiedSecret(false), 2000);
      } else {
        setCopiedBackup(true);
        setTimeout(() => setCopiedBackup(false), 2000);
      }
    } catch {
      // Clipboard API may not be available
    }
  };

  // Loading state
  if (mfaStatus.isLoading) {
    return (
      <div className="max-w-md mx-auto">
        <div className="glass-panel rounded-[var(--radius)] p-6 text-center">
          <Loader2 className="w-8 h-8 animate-spin text-muted-foreground mx-auto" />
          <p className="text-sm text-muted-foreground mt-3">
            Loading security settings...
          </p>
        </div>
      </div>
    );
  }

  // Backup codes step — show once after enabling
  if (step === "backup") {
    return (
      <div className="max-w-md mx-auto space-y-4">
        <Button
          variant="ghost"
          size="sm"
          onClick={() => {
            setStep("status");
            setBackupCodes([]);
          }}
          className="text-muted-foreground hover:text-foreground"
        >
          <ArrowLeft className="w-3.5 h-3.5" /> Back to Security
        </Button>

        <div className="glass-panel rounded-[var(--radius)] p-6">
          <div className="flex items-center gap-3 mb-5">
            <div className="w-10 h-10 rounded-[14px] bg-[hsl(var(--success)/0.15)] border border-[hsl(var(--success)/0.3)] grid place-items-center">
              <CheckCircle2 className="w-5 h-5 text-[hsl(var(--success))]" />
            </div>
            <div>
              <h1 className="text-lg font-bold">MFA Enabled</h1>
              <p className="text-xs text-muted-foreground">
                Save your backup codes now
              </p>
            </div>
          </div>

          <div className="bg-[hsl(var(--destructive)/0.06)] border border-[hsl(var(--destructive)/0.2)] rounded-[10px] px-3 py-2 mb-4">
            <div className="flex items-start gap-2">
              <AlertTriangle className="w-4 h-4 text-[hsl(var(--destructive))] mt-0.5 shrink-0" />
              <p className="text-xs text-[hsl(var(--destructive))]">
                These backup codes will only be shown once. Save them in a
                secure location. Each code can be used once as an alternative
                to your authenticator app.
              </p>
            </div>
          </div>

          <div className="bg-[hsl(var(--muted)/0.3)] rounded-[10px] p-4 font-mono text-sm space-y-1.5">
            {backupCodes.map((code, i) => (
              <div key={i} className="flex items-center gap-2">
                <span className="text-muted-foreground text-xs w-4">
                  {i + 1}.
                </span>
                <span className="tracking-wider">{code}</span>
              </div>
            ))}
          </div>

          <Button
            variant="outline"
            size="sm"
            onClick={() =>
              copyToClipboard(backupCodes.join("\n"), "backup")
            }
            className="mt-3 w-full"
          >
            <Copy className="w-3.5 h-3.5" />
            {copiedBackup ? "Copied!" : "Copy All Codes"}
          </Button>

          <Separator className="my-4" />

          <Button
            onClick={() => {
              setStep("status");
              setBackupCodes([]);
            }}
            className="w-full"
          >
            <ShieldCheck className="w-4 h-4" />
            I&apos;ve saved my codes
          </Button>
        </div>
      </div>
    );
  }

  // Disable confirmation step
  if (step === "disable") {
    return (
      <div className="max-w-md mx-auto space-y-4">
        <Button
          variant="ghost"
          size="sm"
          onClick={() => {
            setStep("status");
            setError("");
            setDisablePassword("");
          }}
          className="text-muted-foreground hover:text-foreground"
        >
          <ArrowLeft className="w-3.5 h-3.5" /> Back to Security
        </Button>

        <div className="glass-panel rounded-[var(--radius)] p-6">
          <div className="flex items-center gap-3 mb-5">
            <div className="w-10 h-10 rounded-[14px] bg-[hsl(var(--destructive)/0.15)] border border-[hsl(var(--destructive)/0.3)] grid place-items-center">
              <ShieldOff className="w-5 h-5 text-[hsl(var(--destructive))]" />
            </div>
            <div>
              <h1 className="text-lg font-bold">Disable MFA</h1>
              <p className="text-xs text-muted-foreground">
                Confirm your password to disable MFA
              </p>
            </div>
          </div>

          <div className="bg-[hsl(var(--destructive)/0.06)] border border-[hsl(var(--destructive)/0.2)] rounded-[10px] px-3 py-2 mb-4">
            <p className="text-xs text-[hsl(var(--destructive))]">
              Disabling MFA will remove the extra layer of security from your
              account. You can re-enable it at any time.
            </p>
          </div>

          <form onSubmit={handleDisableMfa} className="space-y-4">
            <div>
              <Label htmlFor="mfa-disable-pw" className="mb-1.5">
                Current Password
              </Label>
              <div className="relative">
                <Input
                  id="mfa-disable-pw"
                  type={showDisablePassword ? "text" : "password"}
                  value={disablePassword}
                  onChange={(e) => setDisablePassword(e.target.value)}
                  className="pr-10"
                  placeholder="Enter your password"
                  autoComplete="current-password"
                  autoFocus
                />
                <button
                  type="button"
                  onClick={() => setShowDisablePassword(!showDisablePassword)}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground cursor-pointer"
                  aria-label={showDisablePassword ? "Hide password" : "Show password"}
                >
                  {showDisablePassword ? (
                    <EyeOff className="w-4 h-4" />
                  ) : (
                    <Eye className="w-4 h-4" />
                  )}
                </button>
              </div>
            </div>

            {error && (
              <div role="alert" className="text-xs text-[hsl(var(--destructive))] bg-[hsl(var(--destructive)/0.08)] border border-[hsl(var(--destructive)/0.2)] rounded-[10px] px-3 py-2">
                {error}
              </div>
            )}

            <Button
              type="submit"
              variant="destructive"
              disabled={mfaDisable.isPending}
              className="w-full"
            >
              {mfaDisable.isPending ? (
                <Loader2 className="w-4 h-4 animate-spin" />
              ) : (
                <ShieldOff className="w-4 h-4" />
              )}
              {mfaDisable.isPending
                ? "Disabling..."
                : "Disable MFA"}
            </Button>
          </form>
        </div>
      </div>
    );
  }

  // Setup step — show secret + verify
  if (step === "setup" || step === "verify") {
    return (
      <div className="max-w-md mx-auto space-y-4">
        <Button
          variant="ghost"
          size="sm"
          onClick={() => {
            setStep("status");
            setError("");
          }}
          className="text-muted-foreground hover:text-foreground"
        >
          <ArrowLeft className="w-3.5 h-3.5" /> Back to Security
        </Button>

        <div className="glass-panel rounded-[var(--radius)] p-6">
          <div className="flex items-center gap-3 mb-5">
            <div className="w-10 h-10 rounded-[14px] bg-[hsl(var(--primary)/0.15)] border border-[hsl(var(--primary)/0.3)] grid place-items-center">
              <Shield className="w-5 h-5 text-[hsl(var(--primary))]" />
            </div>
            <div>
              <h1 className="text-lg font-bold">Set Up MFA</h1>
              <p className="text-xs text-muted-foreground">
                Configure your authenticator app
              </p>
            </div>
          </div>

          {/* Step 1: Secret */}
          <div className="space-y-3 mb-4">
            <p className="text-sm text-muted-foreground">
              Add this account to your authenticator app (Google Authenticator,
              Authy, etc.) using the secret below or the otpauth URI.
            </p>

            <div>
              <Label className="mb-1.5 text-xs font-medium">
                Secret Key (manual entry)
              </Label>
              <div className="flex items-center gap-2">
                <code className="flex-1 bg-[hsl(var(--muted)/0.3)] rounded-[10px] px-3 py-2 text-sm font-mono tracking-wider break-all">
                  {secret}
                </code>
                <Button
                  variant="outline"
                  size="sm"
                  onClick={() => copyToClipboard(secret, "secret")}
                  className="shrink-0"
                >
                  <Copy className="w-3.5 h-3.5" />
                  {copiedSecret ? "Copied" : "Copy"}
                </Button>
              </div>
            </div>

            <div>
              <Label className="mb-1.5 text-xs font-medium">
                OTPAuth URI (for QR code scanners)
              </Label>
              <div className="bg-[hsl(var(--muted)/0.3)] rounded-[10px] px-3 py-2">
                <code className="text-xs font-mono break-all text-muted-foreground">
                  {otpauthUri}
                </code>
              </div>
              <Button
                variant="ghost"
                size="sm"
                onClick={() => copyToClipboard(otpauthUri, "secret")}
                className="mt-1 text-xs px-0 text-muted-foreground"
              >
                <Copy className="w-3 h-3" />
                Copy URI
              </Button>
            </div>
          </div>

          <Separator className="my-4" />

          {/* Step 2: Verify */}
          <form onSubmit={handleVerifyCode} className="space-y-4">
            <div>
              <Label htmlFor="mfa-verify-code" className="mb-1.5">
                Verification Code
              </Label>
              <p className="text-xs text-muted-foreground mb-2">
                Enter the 6-digit code from your authenticator app to verify
                setup.
              </p>
              <Input
                id="mfa-verify-code"
                type="text"
                inputMode="numeric"
                maxLength={6}
                pattern="[0-9]*"
                value={verifyCode}
                onChange={(e) => {
                  const val = e.target.value.replace(/\D/g, "").slice(0, 6);
                  setVerifyCode(val);
                }}
                placeholder="000000"
                className="text-center text-lg tracking-[0.5em] font-mono"
                autoComplete="one-time-code"
                autoFocus={step === "verify"}
              />
            </div>

            {error && (
              <div role="alert" className="text-xs text-[hsl(var(--destructive))] bg-[hsl(var(--destructive)/0.08)] border border-[hsl(var(--destructive)/0.2)] rounded-[10px] px-3 py-2">
                {error}
              </div>
            )}

            <Button
              type="submit"
              disabled={mfaVerify.isPending || verifyCode.length !== 6}
              className="w-full"
            >
              {mfaVerify.isPending ? (
                <Loader2 className="w-4 h-4 animate-spin" />
              ) : (
                <ShieldCheck className="w-4 h-4" />
              )}
              {mfaVerify.isPending ? "Verifying..." : "Verify & Enable MFA"}
            </Button>
          </form>
        </div>
      </div>
    );
  }

  // Default: Status overview
  return (
    <div className="max-w-md mx-auto space-y-4">
      <Button
        variant="ghost"
        size="sm"
        onClick={() => navigate("/profile")}
        className="text-muted-foreground hover:text-foreground"
      >
        <ArrowLeft className="w-3.5 h-3.5" /> Back to Profile
      </Button>

      <div className="glass-panel rounded-[var(--radius)] p-6">
        <div className="flex items-center gap-3 mb-5">
          <div className="w-10 h-10 rounded-[14px] bg-[hsl(var(--primary)/0.15)] border border-[hsl(var(--primary)/0.3)] grid place-items-center">
            <Shield className="w-5 h-5 text-[hsl(var(--primary))]" />
          </div>
          <div>
            <h1 className="text-lg font-bold">Security Settings</h1>
            <p className="text-xs text-muted-foreground">
              Multi-factor authentication
            </p>
          </div>
        </div>

        {/* MFA status card */}
        <div
          className={`rounded-[10px] border p-4 mb-4 ${
            isEnabled
              ? "bg-[hsl(var(--success)/0.06)] border-[hsl(var(--success)/0.2)]"
              : "bg-[hsl(var(--muted)/0.2)] border-[hsl(var(--border))]"
          }`}
        >
          <div className="flex items-center gap-3">
            {isEnabled ? (
              <ShieldCheck className="w-6 h-6 text-[hsl(var(--success))]" />
            ) : (
              <Shield className="w-6 h-6 text-muted-foreground" />
            )}
            <div className="flex-1">
              <p className="text-sm font-medium">
                {isEnabled
                  ? "MFA is enabled"
                  : "MFA is not enabled"}
              </p>
              <p className="text-xs text-muted-foreground">
                {isEnabled
                  ? `Verified ${
                      mfaStatus.data?.verifiedAt
                        ? new Date(mfaStatus.data.verifiedAt).toLocaleDateString()
                        : ""
                    }`
                  : "Add an extra layer of security to your account"}
              </p>
            </div>
            <div
              className={`w-2.5 h-2.5 rounded-full ${
                isEnabled
                  ? "bg-[hsl(var(--success))]"
                  : "bg-muted-foreground/30"
              }`}
            />
          </div>
        </div>

        {/* Action buttons */}
        {isEnabled ? (
          <Button
            variant="outline"
            onClick={() => {
              setStep("disable");
              setError("");
            }}
            className="w-full border-[hsl(var(--destructive)/0.3)] text-[hsl(var(--destructive))] hover:bg-[hsl(var(--destructive)/0.08)]"
          >
            <ShieldOff className="w-4 h-4" />
            Disable MFA
          </Button>
        ) : (
          <Button
            onClick={handleEnableMfa}
            disabled={mfaSetup.isPending}
            className="w-full"
          >
            {mfaSetup.isPending ? (
              <Loader2 className="w-4 h-4 animate-spin" />
            ) : (
              <Shield className="w-4 h-4" />
            )}
            {mfaSetup.isPending ? "Setting up..." : "Enable MFA"}
          </Button>
        )}

        {error && (
          <div role="alert" className="mt-3 text-xs text-[hsl(var(--destructive))] bg-[hsl(var(--destructive)/0.08)] border border-[hsl(var(--destructive)/0.2)] rounded-[10px] px-3 py-2">
            {error}
          </div>
        )}

        <Separator className="my-4" />

        <div className="space-y-2">
          <p className="text-xs text-muted-foreground font-medium uppercase tracking-wider">
            About MFA
          </p>
          <p className="text-xs text-muted-foreground leading-relaxed">
            Multi-factor authentication adds a second step to your login
            process. After entering your password, you will be asked for a
            6-digit code from your authenticator app (Google Authenticator,
            Authy, Microsoft Authenticator, etc.).
          </p>
        </div>

        <Separator className="my-4" />

        <Button
          variant="ghost"
          size="sm"
          onClick={() => navigate("/profile/password")}
          className="w-full text-muted-foreground"
        >
          <Lock className="w-3.5 h-3.5" />
          Change Password
        </Button>
      </div>
    </div>
  );
}
