import { useState, useEffect } from "react";
import {
  User,
  Mail,
  Building2,
  Shield,
  Save,
  KeyRound,
  Loader2,
  CheckCircle,
  AlertTriangle,
  Eye,
  EyeOff,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { useProfile, useUpdateProfile, useChangePassword } from "@/hooks/useProfile";

function StatusBadge({ label, variant }: { label: string; variant: "ok" | "warn" | "info" }) {
  const colors = {
    ok: "bg-[hsl(var(--success)/0.15)] text-[hsl(var(--success))]",
    warn: "bg-[hsl(var(--warning)/0.15)] text-[hsl(var(--warning))]",
    info: "bg-[hsl(var(--primary)/0.15)] text-[hsl(var(--primary))]",
  };
  return (
    <span className={cn("px-2.5 py-1 rounded-full text-xs font-medium inline-flex items-center gap-1.5", colors[variant])}>
      <span className={cn("w-1.5 h-1.5 rounded-full", {
        "bg-[hsl(var(--success))]": variant === "ok",
        "bg-[hsl(var(--warning))]": variant === "warn",
        "bg-[hsl(var(--primary))]": variant === "info",
      })} />
      {label}
    </span>
  );
}

function Toast({ message, type, onDismiss }: { message: string; type: "success" | "error"; onDismiss: () => void }) {
  useEffect(() => {
    const timer = setTimeout(onDismiss, 4000);
    return () => clearTimeout(timer);
  }, [onDismiss]);

  return (
    <div className={cn(
      "fixed top-6 right-6 z-50 flex items-center gap-3 px-4 py-3 rounded-xl shadow-lg border text-sm font-medium animate-in slide-in-from-top-2",
      type === "success"
        ? "bg-[hsl(var(--success)/0.12)] border-[hsl(var(--success)/0.3)] text-[hsl(var(--success))]"
        : "bg-[hsl(var(--destructive)/0.12)] border-[hsl(var(--destructive)/0.3)] text-[hsl(var(--destructive))]"
    )}>
      {type === "success" ? <CheckCircle className="w-4 h-4" /> : <AlertTriangle className="w-4 h-4" />}
      {message}
    </div>
  );
}

export function ProfilePage() {
  const { data, isLoading, error: fetchError } = useProfile();
  const updateProfile = useUpdateProfile();
  const changePassword = useChangePassword();

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [title, setTitle] = useState("");
  const [loaded, setLoaded] = useState(false);

  const [oldPassword, setOldPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [showPasswords, setShowPasswords] = useState(false);
  const [showPasswordForm, setShowPasswordForm] = useState(false);

  const [toast, setToast] = useState<{ message: string; type: "success" | "error" } | null>(null);

  const profile = data?.profile;

  useEffect(() => {
    if (profile && !loaded) {
      setFirstName(profile.firstName || "");
      setLastName(profile.lastName || "");
      setTitle(profile.title || "");
      setLoaded(true);
    }
  }, [profile, loaded]);

  const handleSaveProfile = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await updateProfile.mutateAsync({ firstName, lastName, title });
      setToast({ message: "Profile updated successfully", type: "success" });
    } catch (err) {
      setToast({ message: err instanceof Error ? err.message : "Failed to update profile", type: "error" });
    }
  };

  const handleChangePassword = async (e: React.FormEvent) => {
    e.preventDefault();
    if (newPassword !== confirmPassword) {
      setToast({ message: "New passwords do not match", type: "error" });
      return;
    }
    try {
      await changePassword.mutateAsync({ oldPassword, newPassword, confirmPassword });
      setToast({ message: "Password changed successfully", type: "success" });
      setOldPassword("");
      setNewPassword("");
      setConfirmPassword("");
      setShowPasswordForm(false);
    } catch (err) {
      setToast({ message: err instanceof Error ? err.message : "Failed to change password", type: "error" });
    }
  };

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <Loader2 className="w-8 h-8 animate-spin text-muted-foreground" />
      </div>
    );
  }

  if (fetchError) {
    return (
      <div className="glass-panel rounded-2xl p-6 border border-[hsl(var(--destructive)/0.3)]">
        <div className="flex items-center gap-2 text-[hsl(var(--destructive))]">
          <AlertTriangle className="w-5 h-5" />
          <span className="font-medium">Failed to load profile</span>
        </div>
        <p className="text-sm text-muted-foreground mt-2">
          {fetchError instanceof Error ? fetchError.message : "Unknown error"}
        </p>
      </div>
    );
  }

  return (
    <div className="space-y-6 max-w-4xl">
      {toast && <Toast message={toast.message} type={toast.type} onDismiss={() => setToast(null)} />}

      {/* Page Header */}
      <div>
        <h1 className="text-2xl font-semibold">My Profile</h1>
        <p className="text-sm text-muted-foreground mt-1">
          Manage your personal information and account security.
        </p>
      </div>

      {/* Account Status Card (ASSA-inspired) */}
      <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-5">
        <div className="flex items-center justify-between flex-wrap gap-4">
          <div className="flex items-center gap-4">
            <div className="w-12 h-12 rounded-xl bg-[hsl(var(--primary)/0.15)] flex items-center justify-center">
              <User className="w-6 h-6 text-[hsl(var(--primary))]" />
            </div>
            <div>
              <h2 className="font-semibold text-lg">{profile?.firstName} {profile?.lastName}</h2>
              <p className="text-sm text-muted-foreground">{profile?.email}</p>
            </div>
          </div>
          <div className="flex items-center gap-2">
            <StatusBadge label={profile?.role === "admin" ? "Administrator" : "User"} variant={profile?.role === "admin" ? "info" : "ok"} />
            <StatusBadge label="Active" variant="ok" />
          </div>
        </div>
      </div>

      {/* Two-column grid (ASSA-inspired) */}
      <div className="grid grid-cols-1 lg:grid-cols-[1fr_340px] gap-6">

        {/* Personal Information Card */}
        <form onSubmit={handleSaveProfile} className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-6">
          <div className="flex items-center gap-2 mb-5">
            <User className="w-4 h-4 text-[hsl(var(--primary))]" />
            <h3 className="font-semibold">Personal Information</h3>
          </div>

          <div className="space-y-4">
            <div className="grid grid-cols-2 gap-4">
              <div className="space-y-1.5">
                <label className="text-xs font-medium text-muted-foreground uppercase tracking-wide">First Name</label>
                <input
                  type="text"
                  value={firstName}
                  onChange={(e) => setFirstName(e.target.value)}
                  maxLength={45}
                  required
                  className="w-full px-3 py-2 rounded-lg bg-[hsl(var(--muted))] border border-[hsl(var(--border))] text-sm focus:outline-none focus:ring-2 focus:ring-[hsl(var(--primary)/0.4)] transition"
                />
              </div>
              <div className="space-y-1.5">
                <label className="text-xs font-medium text-muted-foreground uppercase tracking-wide">Last Name</label>
                <input
                  type="text"
                  value={lastName}
                  onChange={(e) => setLastName(e.target.value)}
                  maxLength={45}
                  required
                  className="w-full px-3 py-2 rounded-lg bg-[hsl(var(--muted))] border border-[hsl(var(--border))] text-sm focus:outline-none focus:ring-2 focus:ring-[hsl(var(--primary)/0.4)] transition"
                />
              </div>
            </div>

            <div className="space-y-1.5">
              <label className="text-xs font-medium text-muted-foreground uppercase tracking-wide">Title</label>
              <input
                type="text"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                maxLength={255}
                placeholder="e.g. Integration Architect"
                className="w-full px-3 py-2 rounded-lg bg-[hsl(var(--muted))] border border-[hsl(var(--border))] text-sm focus:outline-none focus:ring-2 focus:ring-[hsl(var(--primary)/0.4)] transition"
              />
            </div>

            <div className="space-y-1.5">
              <label className="text-xs font-medium text-muted-foreground uppercase tracking-wide">Email</label>
              <div className="flex items-center gap-2 px-3 py-2 rounded-lg bg-[hsl(var(--muted)/0.5)] border border-[hsl(var(--border))] text-sm text-muted-foreground">
                <Mail className="w-4 h-4 shrink-0" />
                {profile?.email}
              </div>
              <p className="text-[11px] text-muted-foreground">Email cannot be changed. Contact an administrator.</p>
            </div>

            <div className="flex justify-end pt-2">
              <button
                type="submit"
                disabled={updateProfile.isPending}
                className={cn(
                  "inline-flex items-center gap-2 px-5 py-2 rounded-lg text-sm font-medium transition",
                  "bg-[hsl(var(--primary))] text-white hover:bg-[hsl(var(--primary)/0.85)]",
                  "disabled:opacity-50 disabled:cursor-not-allowed"
                )}
              >
                {updateProfile.isPending ? <Loader2 className="w-4 h-4 animate-spin" /> : <Save className="w-4 h-4" />}
                Save Profile
              </button>
            </div>
          </div>
        </form>

        {/* Organization Card (ASSA-inspired sidebar info) */}
        <div className="space-y-5">
          <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-5">
            <div className="flex items-center gap-2 mb-4">
              <Building2 className="w-4 h-4 text-[hsl(var(--primary))]" />
              <h3 className="font-semibold text-sm">Organization</h3>
            </div>
            <div className="space-y-3 text-sm">
              <div className="flex justify-between">
                <span className="text-muted-foreground">Company</span>
                <span className="font-medium">{profile?.company || "—"}</span>
              </div>
              <hr className="border-[hsl(var(--border))]" />
              <div className="flex justify-between">
                <span className="text-muted-foreground">Solution</span>
                <span className="font-medium">{profile?.solutionType || "—"}</span>
              </div>
              <hr className="border-[hsl(var(--border))]" />
              <div className="flex justify-between">
                <span className="text-muted-foreground">Role</span>
                <span className="font-medium capitalize">{profile?.role || "—"}</span>
              </div>
            </div>
          </div>

          {/* Security Section */}
          <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-5">
            <div className="flex items-center justify-between mb-4">
              <div className="flex items-center gap-2">
                <Shield className="w-4 h-4 text-[hsl(var(--primary))]" />
                <h3 className="font-semibold text-sm">Security</h3>
              </div>
            </div>

            {!showPasswordForm ? (
              <button
                onClick={() => setShowPasswordForm(true)}
                className="inline-flex items-center gap-2 px-4 py-2 rounded-lg text-sm font-medium border border-[hsl(var(--border))] hover:bg-[hsl(var(--muted))] transition w-full justify-center"
              >
                <KeyRound className="w-4 h-4" />
                Change Password
              </button>
            ) : (
              <form onSubmit={handleChangePassword} className="space-y-3">
                {[
                  { label: "Current Password", value: oldPassword, set: setOldPassword },
                  { label: "New Password", value: newPassword, set: setNewPassword },
                  { label: "Confirm Password", value: confirmPassword, set: setConfirmPassword },
                ].map(({ label, value, set }) => (
                  <div key={label} className="space-y-1">
                    <label className="text-[11px] font-medium text-muted-foreground uppercase tracking-wide">{label}</label>
                    <div className="relative">
                      <input
                        type={showPasswords ? "text" : "password"}
                        value={value}
                        onChange={(e) => set(e.target.value)}
                        required
                        minLength={4}
                        className="w-full px-3 py-2 pr-9 rounded-lg bg-[hsl(var(--muted))] border border-[hsl(var(--border))] text-sm focus:outline-none focus:ring-2 focus:ring-[hsl(var(--primary)/0.4)] transition"
                      />
                      <button
                        type="button"
                        onClick={() => setShowPasswords(!showPasswords)}
                        className="absolute right-2.5 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground"
                      >
                        {showPasswords ? <EyeOff className="w-3.5 h-3.5" /> : <Eye className="w-3.5 h-3.5" />}
                      </button>
                    </div>
                  </div>
                ))}
                <div className="flex gap-2 pt-1">
                  <button
                    type="submit"
                    disabled={changePassword.isPending}
                    className={cn(
                      "flex-1 inline-flex items-center justify-center gap-2 px-4 py-2 rounded-lg text-sm font-medium transition",
                      "bg-[hsl(var(--primary))] text-white hover:bg-[hsl(var(--primary)/0.85)]",
                      "disabled:opacity-50"
                    )}
                  >
                    {changePassword.isPending ? <Loader2 className="w-4 h-4 animate-spin" /> : <KeyRound className="w-4 h-4" />}
                    Update
                  </button>
                  <button
                    type="button"
                    onClick={() => { setShowPasswordForm(false); setOldPassword(""); setNewPassword(""); setConfirmPassword(""); }}
                    className="px-4 py-2 rounded-lg text-sm border border-[hsl(var(--border))] hover:bg-[hsl(var(--muted))] transition"
                  >
                    Cancel
                  </button>
                </div>
              </form>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
