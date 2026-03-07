import { useState, useEffect } from "react";
import {
  User,
  Mail,
  Building2,
  Shield,
  Save,
  KeyRound,
  Loader2,
  AlertTriangle,
  Eye,
  EyeOff,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { useProfile, useUpdateProfile, useChangePassword } from "@/hooks/useProfile";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { useToast } from "@/providers/ToastProvider";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Badge } from "@/components/ui/badge";
import { Separator } from "@/components/ui/separator";

function StatusDot({ variant }: { variant: "success" | "warning" | "default" }) {
  const color = variant === "success" ? "bg-[hsl(var(--success))]"
    : variant === "warning" ? "bg-[hsl(var(--warning))]"
    : "bg-[hsl(var(--primary))]";
  return <span className={cn("w-1.5 h-1.5 rounded-full", color)} />;
}

export function ProfilePage() {
  useDocumentTitle("My Profile");
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

  const { showToast } = useToast();

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
      showToast("Profile updated successfully", "success");
    } catch (err) {
      showToast(err instanceof Error ? err.message : "Failed to update profile", "error");
    }
  };

  const handleChangePassword = async (e: React.FormEvent) => {
    e.preventDefault();
    if (newPassword !== confirmPassword) {
      showToast("New passwords do not match", "error");
      return;
    }
    try {
      await changePassword.mutateAsync({ oldPassword, newPassword, confirmPassword });
      showToast("Password changed successfully", "success");
      setOldPassword("");
      setNewPassword("");
      setConfirmPassword("");
      setShowPasswordForm(false);
    } catch (err) {
      showToast(err instanceof Error ? err.message : "Failed to change password", "error");
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
            <Badge variant={profile?.role === "admin" ? "default" : "success"}>
              <StatusDot variant={profile?.role === "admin" ? "default" : "success"} />
              {profile?.role === "admin" ? "Administrator" : "User"}
            </Badge>
            <Badge variant="success">
              <StatusDot variant="success" />
              Active
            </Badge>
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
                <Label htmlFor="firstName">First Name</Label>
                <Input
                  id="firstName"
                  value={firstName}
                  onChange={(e) => setFirstName(e.target.value)}
                  maxLength={45}
                  required
                />
              </div>
              <div className="space-y-1.5">
                <Label htmlFor="lastName">Last Name</Label>
                <Input
                  id="lastName"
                  value={lastName}
                  onChange={(e) => setLastName(e.target.value)}
                  maxLength={45}
                  required
                />
              </div>
            </div>

            <div className="space-y-1.5">
              <Label htmlFor="title">Title</Label>
              <Input
                id="title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                maxLength={255}
                placeholder="e.g. Integration Architect"
              />
            </div>

            <div className="space-y-1.5">
              <Label>Email</Label>
              <div className="flex items-center gap-2 px-3 py-2 rounded-lg bg-[hsl(var(--muted)/0.5)] border border-[hsl(var(--border))] text-sm text-muted-foreground">
                <Mail className="w-4 h-4 shrink-0" />
                {profile?.email}
              </div>
              <p className="text-[11px] text-muted-foreground">Email cannot be changed. Contact an administrator.</p>
            </div>

            <div className="flex justify-end pt-2">
              <Button type="submit" disabled={updateProfile.isPending}>
                {updateProfile.isPending ? <Loader2 className="w-4 h-4 animate-spin" /> : <Save className="w-4 h-4" />}
                Save Profile
              </Button>
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
              <Separator />
              <div className="flex justify-between">
                <span className="text-muted-foreground">Solution</span>
                <span className="font-medium">{profile?.solutionType || "—"}</span>
              </div>
              <Separator />
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
              <Button
                variant="outline"
                className="w-full"
                onClick={() => setShowPasswordForm(true)}
              >
                <KeyRound className="w-4 h-4" />
                Change Password
              </Button>
            ) : (
              <form onSubmit={handleChangePassword} className="space-y-3">
                {[
                  { label: "Current Password", value: oldPassword, set: setOldPassword },
                  { label: "New Password", value: newPassword, set: setNewPassword },
                  { label: "Confirm Password", value: confirmPassword, set: setConfirmPassword },
                ].map(({ label, value, set }) => (
                  <div key={label} className="space-y-1">
                    <Label>{label}</Label>
                    <div className="relative">
                      <Input
                        type={showPasswords ? "text" : "password"}
                        value={value}
                        onChange={(e) => set(e.target.value)}
                        required
                        minLength={4}
                        className="pr-9"
                      />
                      <button
                        type="button"
                        onClick={() => setShowPasswords(!showPasswords)}
                        className="absolute right-2.5 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground cursor-pointer"
                      >
                        {showPasswords ? <EyeOff className="w-3.5 h-3.5" /> : <Eye className="w-3.5 h-3.5" />}
                      </button>
                    </div>
                  </div>
                ))}
                <div className="flex gap-2 pt-1">
                  <Button type="submit" disabled={changePassword.isPending} className="flex-1">
                    {changePassword.isPending ? <Loader2 className="w-4 h-4 animate-spin" /> : <KeyRound className="w-4 h-4" />}
                    Update
                  </Button>
                  <Button
                    type="button"
                    variant="outline"
                    onClick={() => { setShowPasswordForm(false); setOldPassword(""); setNewPassword(""); setConfirmPassword(""); }}
                  >
                    Cancel
                  </Button>
                </div>
              </form>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
