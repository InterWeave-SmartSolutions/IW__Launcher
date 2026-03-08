import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  Lock,
  Loader2,
  Eye,
  EyeOff,
  CheckCircle2,
  ArrowLeft,
} from "lucide-react";
import { useChangePassword } from "@/hooks/useProfile";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Separator } from "@/components/ui/separator";

export function ChangePasswordPage() {
  useDocumentTitle("Change Password");
  const navigate = useNavigate();
  const changePassword = useChangePassword();
  const [oldPassword, setOldPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [showOld, setShowOld] = useState(false);
  const [showNew, setShowNew] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);

  const validate = (): string | null => {
    if (!oldPassword) return "Current password is required.";
    if (!newPassword) return "New password is required.";
    if (newPassword.length < 4) return "New password must be at least 4 characters.";
    if (newPassword !== confirmPassword) return "New passwords do not match.";
    if (newPassword === oldPassword) return "New password must be different from current password.";
    return null;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const err = validate();
    if (err) {
      setError(err);
      return;
    }
    setError("");
    try {
      const res = await changePassword.mutateAsync({ oldPassword, newPassword, confirmPassword });
      if (res.success) {
        setSuccess(true);
      } else {
        setError(res.error ?? "Failed to change password.");
      }
    } catch (err) {
      setError(
        err instanceof Error ? err.message : "Failed to change password."
      );
    }
  };

  if (success) {
    return (
      <div className="max-w-md mx-auto">
        <div className="glass-panel rounded-[var(--radius)] p-6 text-center">
          <CheckCircle2 className="w-12 h-12 text-[hsl(var(--success))] mx-auto mb-4" />
          <h2 className="text-xl font-bold">Password Changed</h2>
          <p className="text-sm text-muted-foreground mt-2">
            Your password has been updated successfully.
          </p>
          <Button onClick={() => navigate("/profile")} className="mt-6 mx-auto">
            Back to Profile
          </Button>
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-md mx-auto space-y-4">
      {/* Back link */}
      <Button
        variant="ghost"
        size="sm"
        onClick={() => navigate("/profile")}
        className="text-muted-foreground hover:text-foreground"
      >
        <ArrowLeft className="w-3.5 h-3.5" /> Back to Profile
      </Button>

      <div className="glass-panel rounded-[var(--radius)] p-6">
        {/* Header */}
        <div className="flex items-center gap-3 mb-5">
          <div className="w-10 h-10 rounded-[14px] bg-[hsl(var(--primary)/0.15)] border border-[hsl(var(--primary)/0.3)] grid place-items-center">
            <Lock className="w-5 h-5 text-[hsl(var(--primary))]" />
          </div>
          <div>
            <h1 className="text-lg font-bold">Change Password</h1>
            <p className="text-xs text-muted-foreground">
              Update your account password
            </p>
          </div>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          {/* Current password */}
          <div>
            <Label htmlFor="cp-old" className="mb-1.5">
              Current Password
            </Label>
            <div className="relative">
              <Input
                id="cp-old"
                type={showOld ? "text" : "password"}
                value={oldPassword}
                onChange={(e) => setOldPassword(e.target.value)}
                className="pr-10"
                placeholder="Enter current password"
                autoComplete="current-password"
                autoFocus
              />
              <button
                type="button"
                onClick={() => setShowOld(!showOld)}
                className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground cursor-pointer"
                tabIndex={-1}
              >
                {showOld ? <EyeOff className="w-4 h-4" /> : <Eye className="w-4 h-4" />}
              </button>
            </div>
          </div>

          <Separator />

          {/* New password */}
          <div>
            <Label htmlFor="cp-new" className="mb-1.5">
              New Password
            </Label>
            <div className="relative">
              <Input
                id="cp-new"
                type={showNew ? "text" : "password"}
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
                className="pr-10"
                placeholder="Min. 4 characters"
                autoComplete="new-password"
              />
              <button
                type="button"
                onClick={() => setShowNew(!showNew)}
                className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground cursor-pointer"
                tabIndex={-1}
              >
                {showNew ? <EyeOff className="w-4 h-4" /> : <Eye className="w-4 h-4" />}
              </button>
            </div>
          </div>

          {/* Confirm new password */}
          <div>
            <Label htmlFor="cp-confirm" className="mb-1.5">
              Confirm New Password
            </Label>
            <Input
              id="cp-confirm"
              type={showNew ? "text" : "password"}
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              placeholder="Repeat new password"
              autoComplete="new-password"
            />
          </div>

          {/* Error */}
          {error && (
            <div className="text-xs text-[hsl(var(--destructive))] bg-[hsl(var(--destructive)/0.08)] border border-[hsl(var(--destructive)/0.2)] rounded-[10px] px-3 py-2">
              {error}
            </div>
          )}

          {/* Submit */}
          <Button type="submit" disabled={changePassword.isPending} className="w-full">
            {changePassword.isPending ? (
              <Loader2 className="w-4 h-4 animate-spin" />
            ) : (
              <Lock className="w-4 h-4" />
            )}
            {changePassword.isPending ? "Changing password..." : "Change Password"}
          </Button>
        </form>
      </div>
    </div>
  );
}
