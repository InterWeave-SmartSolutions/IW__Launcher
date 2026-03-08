import { Link } from "react-router-dom";
import {
  Settings,
  ExternalLink,
  CheckCircle,
  Circle,
  Building2,
  Loader2,
  AlertTriangle,
  ArrowLeft,
  Workflow,
  Database,
  Shield,
  Globe,
  ChevronRight,
  Clock,
  Mail,
  Server,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { useCompanyProfile } from "@/hooks/useProfile";
import { useCredentials, useWizardConfig } from "@/hooks/useConfiguration";
import { useAuth } from "@/providers/AuthProvider";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { deriveSolutionMeta, buildSyncMappings } from "@/types/configuration";
import { envLabel, emailLabel } from "@/lib/config-labels";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";

interface ConfigStep {
  label: string;
  description: string;
  icon: React.ComponentType<{ className?: string }>;
  complete: boolean;
  wizardStep?: number;
  detail?: string;
}

export function CompanyConfigPage() {
  useDocumentTitle("Company Configuration");
  const { user } = useAuth();
  const { data, isLoading: profileLoading, error } = useCompanyProfile();
  const { data: credData } = useCredentials();
  const { data: wizardData } = useWizardConfig();

  const isLoading = profileLoading;
  const company = data?.company;
  const isAdmin = user?.isAdmin === true;

  const credentials = credData?.data?.credentials ?? [];
  const hasCredentials = credentials.length > 0;
  const hasConfiguration = wizardData?.data?.hasConfiguration ?? false;
  const serverSolution = wizardData?.data?.solutionType || company?.solutionType || "";
  const syncMappings = wizardData?.data?.syncMappings || {};

  // Derive real configuration data
  const meta = serverSolution ? deriveSolutionMeta(serverSolution) : null;
  const mappings = serverSolution ? buildSyncMappings(serverSolution, syncMappings) : [];
  const activeMappingCount = mappings.filter((m) => m.value !== "None").length;

  // Credential details
  const credSummary = credentials.map((c) => `${c.credentialType}: ${c.username || "configured"}`).join(", ");

  // Execution settings from syncMappings
  const env = syncMappings.Env2Con;
  const sandbox = syncMappings.SandBoxUsed === "Yes";
  const emailNotif = syncMappings.EmlNtf;
  const sleepStart = syncMappings.SleepStart;
  const sleepEnd = syncMappings.SleepEnd;

  // Derive configuration status from available company data + config APIs
  const steps: ConfigStep[] = [
    {
      label: "Company Registration",
      description: "Organization name and admin account created",
      icon: Building2,
      complete: !!company?.companyName,
      detail: company?.companyName || undefined,
    },
    {
      label: "Solution Type",
      description: serverSolution && serverSolution !== "Not Selected"
        ? `${meta?.crmName} \u2194 ${meta?.fsName}`
        : "Integration platform not selected",
      icon: Workflow,
      complete: !!serverSolution && serverSolution !== "Not Selected",
      wizardStep: 0,
      detail: serverSolution || undefined,
    },
    {
      label: "System Credentials",
      description: hasCredentials ? credSummary : "Source and destination system credentials",
      icon: Database,
      complete: hasCredentials,
      wizardStep: 2,
      detail: hasCredentials ? `${credentials.length} credential(s)` : undefined,
    },
    {
      label: "Security & Licensing",
      description: company?.licenseKey ? "License key configured" : "License key not set",
      icon: Shield,
      complete: !!company?.licenseKey,
    },
    {
      label: "Object Mapping",
      description: hasConfiguration
        ? `${activeMappingCount} active mapping(s) of ${mappings.length} available`
        : "Data object sync direction mapping",
      icon: Globe,
      complete: hasConfiguration,
      wizardStep: 1,
      detail: hasConfiguration ? `${activeMappingCount}/${mappings.length} configured` : undefined,
    },
  ];

  const completedCount = steps.filter((s) => s.complete).length;
  const progressPct = Math.round((completedCount / steps.length) * 100);

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <Loader2 className="w-8 h-8 animate-spin text-muted-foreground" />
      </div>
    );
  }

  if (error) {
    return (
      <div className="glass-panel rounded-[var(--radius)] p-6 border border-[hsl(var(--destructive)/0.3)]">
        <div className="flex items-center gap-2 text-[hsl(var(--destructive))]">
          <AlertTriangle className="w-5 h-5" />
          <span className="font-medium">Failed to load configuration status</span>
        </div>
        <p className="text-sm text-muted-foreground mt-2">
          {error instanceof Error ? error.message : "Unknown error"}
        </p>
      </div>
    );
  }

  return (
    <div className="space-y-6 max-w-4xl">
      {/* Breadcrumb */}
      <div className="flex items-center gap-2 text-sm text-muted-foreground">
        <Link to="/company" className="hover:text-foreground transition-colors">
          <ArrowLeft className="w-4 h-4 inline mr-1" />
          Company
        </Link>
        <span>/</span>
        <span className="text-foreground">Configuration</span>
      </div>

      {/* Header */}
      <div className="flex items-start justify-between gap-4 flex-wrap">
        <div>
          <h1 className="text-2xl font-semibold">Company Configuration</h1>
          <p className="text-sm text-muted-foreground mt-1">
            Configure integration endpoints, connections, and deployment settings for{" "}
            <span className="font-medium text-foreground">{company?.companyName || "your organization"}</span>.
          </p>
        </div>
        {!isAdmin && (
          <Badge variant="warning" className="rounded-full">
            Read-only \u2014 admin access required
          </Badge>
        )}
      </div>

      {/* Progress overview card */}
      <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-6">
        <div className="flex items-center justify-between mb-4">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-xl bg-[hsl(var(--primary)/0.15)] grid place-items-center">
              <Settings className="w-5 h-5 text-[hsl(var(--primary))]" />
            </div>
            <div>
              <h2 className="font-semibold">Setup Progress</h2>
              <p className="text-xs text-muted-foreground">
                {completedCount} of {steps.length} steps completed
              </p>
            </div>
          </div>
          <span className="text-2xl font-bold text-[hsl(var(--primary))]">{progressPct}%</span>
        </div>

        {/* Progress bar */}
        <div className="h-2 rounded-full bg-[hsl(var(--muted))] overflow-hidden">
          <div
            className="h-full rounded-full bg-gradient-to-r from-[hsl(var(--primary))] to-[hsl(var(--success))] transition-all duration-500"
            style={{ width: `${progressPct}%` }}
          />
        </div>
      </div>

      {/* Configuration steps */}
      <div className="space-y-3">
        {steps.map((step, i) => {
          const Icon = step.icon;
          const clickable = step.wizardStep !== undefined && isAdmin;

          const content = (
            <>
              {/* Step number / check */}
              <div
                className={cn(
                  "w-9 h-9 rounded-xl grid place-items-center shrink-0",
                  step.complete
                    ? "bg-[hsl(var(--success)/0.15)]"
                    : "bg-[hsl(var(--muted)/0.5)]"
                )}
              >
                {step.complete ? (
                  <CheckCircle className="w-4 h-4 text-[hsl(var(--success))]" />
                ) : (
                  <Circle className="w-4 h-4 text-muted-foreground" />
                )}
              </div>

              {/* Icon */}
              <div className="w-8 h-8 rounded-lg border border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.2)] grid place-items-center shrink-0">
                <Icon className="w-4 h-4 text-[hsl(var(--primary))]" />
              </div>

              {/* Content */}
              <div className="flex-1 min-w-0">
                <p className={cn("text-sm font-medium", step.complete && "text-[hsl(var(--success))]")}>
                  {step.label}
                </p>
                <p className="text-xs text-muted-foreground mt-0.5 truncate">{step.description}</p>
              </div>

              {/* Detail badge / step indicator */}
              <div className="flex items-center gap-2 shrink-0">
                {step.detail && (
                  <Badge variant={step.complete ? "success" : "secondary"} className="text-[10px]">
                    {step.detail}
                  </Badge>
                )}
                {clickable ? (
                  <ChevronRight className="w-4 h-4 text-muted-foreground" />
                ) : (
                  <span className="text-xs text-muted-foreground">Step {i + 1}</span>
                )}
              </div>
            </>
          );

          const baseClass = cn(
            "glass-panel rounded-2xl border p-4 flex items-center gap-4 transition-colors",
            step.complete
              ? "border-[hsl(var(--success)/0.2)]"
              : "border-[hsl(var(--border))]",
            clickable && "hover:border-[hsl(var(--primary)/0.4)] hover:bg-[hsl(var(--muted)/0.1)] cursor-pointer"
          );

          return clickable ? (
            <Link key={step.label} to="/company/config/wizard" className={baseClass}>
              {content}
            </Link>
          ) : (
            <div key={step.label} className={baseClass}>
              {content}
            </div>
          );
        })}
      </div>

      {/* Execution settings summary (if configuration exists) */}
      {hasConfiguration && (
        <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-5">
          <div className="flex items-center gap-2 mb-3">
            <Server className="w-4 h-4 text-[hsl(var(--primary))]" />
            <h3 className="text-sm font-semibold">Execution Settings</h3>
          </div>
          <div className="grid grid-cols-3 gap-4 text-sm max-sm:grid-cols-1">
            <div>
              <p className="text-xs text-muted-foreground mb-0.5">Environment</p>
              <p className="font-medium">
                {sandbox ? "Sandbox" : "Production"}
                {env ? ` (${envLabel(env)})` : ""}
              </p>
            </div>
            <div>
              <p className="text-xs text-muted-foreground mb-0.5">
                <Clock className="w-3 h-3 inline mr-1" />
                Sleep Window
              </p>
              <p className="font-medium">
                {sleepStart && sleepEnd ? `${sleepStart} \u2013 ${sleepEnd}` : "Not set"}
              </p>
            </div>
            <div>
              <p className="text-xs text-muted-foreground mb-0.5">
                <Mail className="w-3 h-3 inline mr-1" />
                Email Notifications
              </p>
              <p className="font-medium">{emailLabel(emailNotif)}</p>
            </div>
          </div>
        </div>
      )}

      {/* CTA — launch wizard */}
      <div className="glass-panel rounded-2xl border border-[hsl(var(--primary)/0.2)] bg-gradient-to-r from-[hsl(var(--primary)/0.06)] to-transparent p-6">
        <div className="flex items-start gap-4 flex-wrap">
          <div className="flex-1 min-w-[200px]">
            <h3 className="font-semibold">Open Configuration Wizard</h3>
            <p className="text-sm text-muted-foreground mt-1">
              Set up integration mappings, API credentials, execution settings, and sync direction
              for each data object between your source and destination systems.
            </p>
          </div>
          <div className="flex flex-col gap-2 shrink-0">
            <Button variant={isAdmin ? "default" : "outline"} asChild>
              <Link to="/company/config/wizard">
                <Workflow className="w-4 h-4" />
                Launch Wizard
              </Link>
            </Button>
            <Button variant="ghost" size="sm" asChild className="text-muted-foreground">
              <a href="/iw-business-daemon/CompanyConfiguration.jsp">
                <ExternalLink className="w-3 h-3" />
                Classic wizard
              </a>
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
}

