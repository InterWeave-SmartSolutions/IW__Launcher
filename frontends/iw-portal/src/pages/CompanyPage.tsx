import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import {
  Building2,
  Users,
  Shield,
  Save,
  KeyRound,
  Loader2,
  AlertTriangle,
  Eye,
  EyeOff,
  Settings,
  ExternalLink,
  Activity,
  Workflow,
  Database,
  CheckCircle,
  ArrowRight,
  FileCode,
  Layers,
  Monitor,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { useAuth } from "@/providers/AuthProvider";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { useToast } from "@/providers/ToastProvider";
import {
  useCompanyProfile,
  useUpdateCompanyProfile,
  useChangeCompanyPassword,
} from "@/hooks/useProfile";
import { useCredentials, useWizardConfig } from "@/hooks/useConfiguration";
import { useEngineFlows } from "@/hooks/useFlows";
import { useDashboard } from "@/hooks/useMonitoring";
import { useEngineStatus } from "@/hooks/useEngine";
import { useWorkspaceProjects } from "@/hooks/useWorkspace";
import { buildSyncMappings } from "@/types/configuration";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Badge } from "@/components/ui/badge";
import { ConnectionStatusGrid } from "@/components/integrations/ConnectionStatusGrid";

export function CompanyPage() {
  useDocumentTitle("Company");
  const { user } = useAuth();
  const { data, isLoading, error: fetchError } = useCompanyProfile();
  const updateCompany = useUpdateCompanyProfile();
  const changePassword = useChangeCompanyPassword();

  // Integration data hooks
  const { data: credData, isLoading: credLoading } = useCredentials();
  const { data: flowsRes } = useEngineFlows(false);
  const { data: dashRes } = useDashboard();
  const { data: engineData } = useEngineStatus();
  const { data: wizardData } = useWizardConfig();
  const { data: wsData, isLoading: wsLoading } = useWorkspaceProjects();

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [loaded, setLoaded] = useState(false);

  const [oldPassword, setOldPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [showPasswords, setShowPasswords] = useState(false);
  const [showPasswordForm, setShowPasswordForm] = useState(false);

  const { showToast } = useToast();

  const company = data?.company;
  const userCount = data?.userCount ?? 0;
  const isAdmin = user?.isAdmin === true;
  const credentials = credData?.data?.credentials ?? [];

  // Integration health data
  const summary = dashRes?.data?.summary;
  const allFlows = [
    ...(flowsRes?.data?.scheduledFlows ?? []),
    ...(flowsRes?.data?.utilityFlows ?? []),
  ];
  const runningFlows = allFlows.filter((f) => f.running).length;
  const engineUp = engineData?.engineUp === true;

  // Workspace projects
  const projects = wsData?.projects ?? [];
  const totalTransactions = projects.reduce((sum, p) => sum + p.transactionCount, 0);
  const totalQueries = projects.reduce((sum, p) => sum + p.queryCount, 0);
  const totalXslt = projects.reduce((sum, p) => sum + p.xsltCount, 0);

  // Configuration completeness
  const serverSolution = wizardData?.data?.solutionType || company?.solutionType || "";
  const hasConfiguration = wizardData?.data?.hasConfiguration ?? false;
  const syncMappings = wizardData?.data?.syncMappings || {};
  const mappings = serverSolution ? buildSyncMappings(serverSolution, syncMappings) : [];
  const activeMappingCount = mappings.filter((m) => m.value !== "None").length;

  const configSteps = [
    { label: "Registration", done: !!company?.companyName },
    { label: "Solution Type", done: !!serverSolution && serverSolution !== "Not Selected" },
    { label: "Credentials", done: credentials.length > 0 },
    { label: "Mappings", done: hasConfiguration && activeMappingCount > 0 },
    { label: "Flows", done: allFlows.length > 0 },
  ];
  const completedSteps = configSteps.filter((s) => s.done).length;
  const progressPct = Math.round((completedSteps / configSteps.length) * 100);

  useEffect(() => {
    if (company?.admin && !loaded) {
      setFirstName(company.admin.firstName || "");
      setLastName(company.admin.lastName || "");
      setLoaded(true);
    }
  }, [company, loaded]);

  const handleSave = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await updateCompany.mutateAsync({ firstName, lastName });
      showToast("Company profile updated successfully", "success");
    } catch (err) {
      showToast(err instanceof Error ? err.message : "Update failed", "error");
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
      showToast("Company password changed successfully", "success");
      setOldPassword("");
      setNewPassword("");
      setConfirmPassword("");
      setShowPasswordForm(false);
    } catch (err) {
      showToast(err instanceof Error ? err.message : "Password change failed", "error");
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
          <span className="font-medium">Failed to load company profile</span>
        </div>
        <p className="text-sm text-muted-foreground mt-2">
          {fetchError instanceof Error ? fetchError.message : "Unknown error"}
        </p>
      </div>
    );
  }

  return (
    <div className="space-y-6 max-w-5xl">
      {/* Page Header */}
      <div>
        <h1 className="text-2xl font-semibold">Company Profile</h1>
        <p className="text-sm text-muted-foreground mt-1">
          Integration command center for{" "}
          <span className="font-medium text-foreground">{company?.companyName || "your organization"}</span>.
        </p>
      </div>

      {/* Company Status Card with enhanced KPI bar */}
      <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-5">
        <div className="flex items-center justify-between flex-wrap gap-4">
          <div className="flex items-center gap-4">
            <div className="w-12 h-12 rounded-xl bg-[hsl(var(--primary)/0.15)] flex items-center justify-center">
              <Building2 className="w-6 h-6 text-[hsl(var(--primary))]" />
            </div>
            <div>
              <h2 className="font-semibold text-lg">{company?.companyName || "\u2014"}</h2>
              <p className="text-sm text-muted-foreground">{company?.companyEmail || "\u2014"}</p>
            </div>
          </div>
          <div className="flex items-center gap-3">
            <Badge variant={company?.isActive ? "success" : "destructive"}>
              <span className="w-1.5 h-1.5 rounded-full bg-current" />
              {company?.isActive ? "Active" : "Inactive"}
            </Badge>
            <Badge variant="default">
              <span className="w-1.5 h-1.5 rounded-full bg-current" />
              {company?.solutionType || "\u2014"}
            </Badge>
          </div>
        </div>
      </div>

      {/* ── Integration Health Panel ── */}
      <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-5">
        <div className="flex items-center gap-2 mb-4">
          <Activity className="w-4 h-4 text-[hsl(var(--primary))]" />
          <h3 className="font-semibold text-sm">Integration Health</h3>
          <div className="ml-auto">
            <Badge
              variant={engineUp ? "success" : "secondary"}
              className="text-[10px]"
            >
              <span className={cn("w-1.5 h-1.5 rounded-full mr-1", engineUp ? "bg-[hsl(var(--success))]" : "bg-muted-foreground")} />
              Engine {engineUp ? "Online" : "Offline"}
            </Badge>
          </div>
        </div>
        <div className="grid grid-cols-4 gap-4 max-sm:grid-cols-2">
          <div className="text-center p-3 rounded-lg bg-[hsl(var(--muted)/0.3)]">
            <div className="text-2xl font-bold">{allFlows.length}</div>
            <div className="text-xs text-muted-foreground mt-0.5">Total Flows</div>
          </div>
          <div className="text-center p-3 rounded-lg bg-[hsl(var(--muted)/0.3)]">
            <div className="text-2xl font-bold text-[hsl(var(--primary))]">{runningFlows}</div>
            <div className="text-xs text-muted-foreground mt-0.5">Running</div>
          </div>
          <div className="text-center p-3 rounded-lg bg-[hsl(var(--muted)/0.3)]">
            <div className="text-2xl font-bold text-[hsl(var(--success))]">
              {summary ? `${summary.success_rate_24h.toFixed(0)}%` : "\u2014"}
            </div>
            <div className="text-xs text-muted-foreground mt-0.5">24h Success</div>
          </div>
          <div className="text-center p-3 rounded-lg bg-[hsl(var(--muted)/0.3)]">
            <div className="text-2xl font-bold">{userCount}</div>
            <div className="text-xs text-muted-foreground mt-0.5">Team Members</div>
          </div>
        </div>
      </div>

      {/* Two-column grid: left (admin + workspace), right (connections + config + security) */}
      <div className="grid grid-cols-1 lg:grid-cols-[1fr_380px] gap-6">

        {/* ── Left Column ── */}
        <div className="space-y-6">

          {/* Administrator Form */}
          <form onSubmit={handleSave} className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-6">
            <div className="flex items-center gap-2 mb-5">
              <Users className="w-4 h-4 text-[hsl(var(--primary))]" />
              <h3 className="font-semibold">Administrator</h3>
              {!isAdmin && (
                <span className="text-xs text-muted-foreground ml-auto">(Read-only \u2014 admin access required)</span>
              )}
            </div>

            <div className="space-y-4">
              <div className="grid grid-cols-2 gap-4">
                <div className="space-y-1.5">
                  <Label htmlFor="adminFirstName">First Name</Label>
                  <Input
                    id="adminFirstName"
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    maxLength={45}
                    required
                    disabled={!isAdmin}
                  />
                </div>
                <div className="space-y-1.5">
                  <Label htmlFor="adminLastName">Last Name</Label>
                  <Input
                    id="adminLastName"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                    maxLength={45}
                    required
                    disabled={!isAdmin}
                  />
                </div>
              </div>

              <div className="space-y-1.5">
                <Label>Admin Email</Label>
                <div className="flex items-center gap-2 px-3 py-2 rounded-lg bg-[hsl(var(--muted)/0.5)] border border-[hsl(var(--border))] text-sm text-muted-foreground">
                  {company?.admin?.email || "\u2014"}
                </div>
              </div>

              <div className="space-y-1.5">
                <Label>Company Name</Label>
                <div className="flex items-center gap-2 px-3 py-2 rounded-lg bg-[hsl(var(--muted)/0.5)] border border-[hsl(var(--border))] text-sm text-muted-foreground">
                  <Building2 className="w-4 h-4 shrink-0" />
                  {company?.companyName || "\u2014"}
                </div>
                <p className="text-[11px] text-muted-foreground">Company name cannot be changed after registration.</p>
              </div>

              {isAdmin && (
                <div className="flex justify-end pt-2">
                  <Button type="submit" disabled={updateCompany.isPending}>
                    {updateCompany.isPending ? <Loader2 className="w-4 h-4 animate-spin" /> : <Save className="w-4 h-4" />}
                    Save Changes
                  </Button>
                </div>
              )}
            </div>
          </form>

          {/* Workspace Project Summary */}
          <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-5">
            <div className="flex items-center justify-between mb-4">
              <div className="flex items-center gap-2">
                <Layers className="w-4 h-4 text-[hsl(var(--primary))]" />
                <h3 className="font-semibold text-sm">Workspace Projects</h3>
              </div>
              <Link
                to="/admin/configurator"
                className="text-xs text-[hsl(var(--primary))] hover:underline flex items-center gap-1"
              >
                Integrations <ArrowRight className="w-3 h-3" />
              </Link>
            </div>

            {wsLoading ? (
              <div className="flex justify-center py-4">
                <Loader2 className="w-5 h-5 animate-spin text-muted-foreground" />
              </div>
            ) : projects.length === 0 ? (
              <div className="text-center py-4">
                <FileCode className="w-6 h-6 text-muted-foreground mx-auto mb-1.5" />
                <p className="text-xs text-muted-foreground">No workspace projects found</p>
              </div>
            ) : (
              <div className="space-y-2">
                {projects.map((proj) => (
                  <div
                    key={proj.name}
                    className="flex items-center gap-3 p-3 rounded-lg border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.1)]"
                  >
                    <div className="w-8 h-8 rounded-lg bg-[hsl(var(--primary)/0.1)] grid place-items-center shrink-0">
                      <FileCode className="w-4 h-4 text-[hsl(var(--primary))]" />
                    </div>
                    <div className="flex-1 min-w-0">
                      <p className="text-sm font-medium truncate">{proj.name.replace(/_/g, " ")}</p>
                      <p className="text-[10px] text-muted-foreground">
                        {proj.solutionType && <span className="mr-2">{proj.solutionType}</span>}
                        {proj.transactionCount} txns · {proj.queryCount} queries · {proj.xsltCount} XSLTs
                      </p>
                    </div>
                    {proj.profileCount > 0 && (
                      <Badge variant="secondary" className="text-[10px] px-1.5 py-0 shrink-0">
                        {proj.profileCount} profile{proj.profileCount !== 1 ? "s" : ""}
                      </Badge>
                    )}
                  </div>
                ))}

                {/* Totals bar */}
                <div className="flex gap-4 pt-2 text-center">
                  <div className="flex-1 p-2 rounded-md bg-[hsl(var(--muted)/0.3)]">
                    <div className="text-lg font-bold">{totalTransactions}</div>
                    <div className="text-[10px] text-muted-foreground">Transactions</div>
                  </div>
                  <div className="flex-1 p-2 rounded-md bg-[hsl(var(--muted)/0.3)]">
                    <div className="text-lg font-bold">{totalQueries}</div>
                    <div className="text-[10px] text-muted-foreground">Queries</div>
                  </div>
                  <div className="flex-1 p-2 rounded-md bg-[hsl(var(--muted)/0.3)]">
                    <div className="text-lg font-bold">{totalXslt}</div>
                    <div className="text-[10px] text-muted-foreground">XSLTs</div>
                  </div>
                </div>
              </div>
            )}
          </div>
        </div>

        {/* ── Right Column ── */}
        <div className="space-y-5">

          {/* Connected Systems Panel */}
          <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-5">
            <div className="flex items-center justify-between mb-4">
              <div className="flex items-center gap-2">
                <Database className="w-4 h-4 text-[hsl(var(--primary))]" />
                <h3 className="font-semibold text-sm">Connected Systems</h3>
              </div>
              <Link
                to="/admin/configurator"
                className="text-xs text-[hsl(var(--primary))] hover:underline flex items-center gap-1"
              >
                Manage <ArrowRight className="w-3 h-3" />
              </Link>
            </div>
            <ConnectionStatusGrid credentials={credentials} isLoading={credLoading} />
          </div>

          {/* Configuration Completeness */}
          <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-5">
            <div className="flex items-center justify-between mb-3">
              <div className="flex items-center gap-2">
                <Settings className="w-4 h-4 text-[hsl(var(--primary))]" />
                <h3 className="font-semibold text-sm">Setup Progress</h3>
              </div>
              <span className="text-sm font-bold text-[hsl(var(--primary))]">{progressPct}%</span>
            </div>

            {/* Progress bar */}
            <div className="h-2 rounded-full bg-[hsl(var(--muted))] overflow-hidden mb-3">
              <div
                className="h-full rounded-full bg-gradient-to-r from-[hsl(var(--primary))] to-[hsl(var(--success))] transition-all duration-500"
                style={{ width: `${progressPct}%` }}
              />
            </div>

            {/* Step indicators */}
            <div className="space-y-1.5">
              {configSteps.map((step) => (
                <div key={step.label} className="flex items-center gap-2 text-xs">
                  {step.done ? (
                    <CheckCircle className="w-3.5 h-3.5 text-[hsl(var(--success))] shrink-0" />
                  ) : (
                    <div className="w-3.5 h-3.5 rounded-full border border-[hsl(var(--border))] shrink-0" />
                  )}
                  <span className={step.done ? "text-foreground" : "text-muted-foreground"}>{step.label}</span>
                </div>
              ))}
            </div>

            <div className="mt-3">
              <Button variant="outline" size="sm" className="w-full" asChild>
                <Link to="/company/config">
                  <Settings className="w-3.5 h-3.5" />
                  Open Configuration
                  <ArrowRight className="w-3.5 h-3.5" />
                </Link>
              </Button>
            </div>
          </div>

          {/* Quick Actions */}
          <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-5">
            <div className="flex items-center gap-2 mb-4">
              <Workflow className="w-4 h-4 text-[hsl(var(--primary))]" />
              <h3 className="font-semibold text-sm">Quick Actions</h3>
            </div>
            <div className="space-y-2">
              <Link
                to="/company/config"
                className="flex items-center justify-between px-3 py-2.5 rounded-lg border border-[hsl(var(--border))] hover:bg-[hsl(var(--muted))] transition text-sm"
              >
                <span className="flex items-center gap-2">
                  <Settings className="w-4 h-4 text-muted-foreground" />
                  Company Configuration
                </span>
                <ExternalLink className="w-3.5 h-3.5 text-muted-foreground" />
              </Link>
              <Link
                to="/admin/configurator"
                className="flex items-center justify-between px-3 py-2.5 rounded-lg border border-[hsl(var(--border))] hover:bg-[hsl(var(--muted))] transition text-sm"
              >
                <span className="flex items-center gap-2">
                  <Monitor className="w-4 h-4 text-muted-foreground" />
                  BD Configurator
                </span>
                <ExternalLink className="w-3.5 h-3.5 text-muted-foreground" />
              </Link>
            </div>
          </div>

          {/* Security Section (admin only) */}
          {isAdmin && (
            <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-5">
              <div className="flex items-center gap-2 mb-4">
                <Shield className="w-4 h-4 text-[hsl(var(--primary))]" />
                <h3 className="font-semibold text-sm">Company Security</h3>
              </div>

              {!showPasswordForm ? (
                <Button
                  variant="outline"
                  className="w-full"
                  onClick={() => setShowPasswordForm(true)}
                >
                  <KeyRound className="w-4 h-4" />
                  Change Company Password
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
          )}
        </div>
      </div>
    </div>
  );
}
