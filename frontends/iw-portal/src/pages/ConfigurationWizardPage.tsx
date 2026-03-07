import { useState, useCallback } from "react";
import { Link } from "react-router-dom";
import {
  ArrowLeft,
  ArrowRight,
  Check,
  Loader2,
  AlertTriangle,
  Workflow,
  Database,
  Key,
  Save,
  ChevronRight,
  ExternalLink,
  RefreshCw,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { useAuth } from "@/providers/AuthProvider";
import { useToast } from "@/providers/ToastProvider";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { useWizardConfig, useSaveWizardConfig, useCredentials, useSaveCredential } from "@/hooks/useConfiguration";
import { buildSyncMappings, deriveSolutionMeta } from "@/types/configuration";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Badge } from "@/components/ui/badge";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import type { SyncDirection, SyncMapping } from "@/types/configuration";

const SOLUTION_TYPES = [
  { code: "SF2QB",  label: "Salesforce → QuickBooks" },
  { code: "SF2QB1", label: "Salesforce → QuickBooks (Extended)" },
  { code: "SF2QBB", label: "Salesforce → QuickBooks (Full Bi-directional)" },
  { code: "SF2NS",  label: "Salesforce → NetSuite" },
  { code: "SF2PT",  label: "Salesforce → Sage" },
  { code: "SF2GP",  label: "Salesforce → MS GP" },
  { code: "CRM2QB", label: "CRM → QuickBooks" },
  { code: "QB",     label: "QuickBooks (Standalone)" },
  { code: "SF",     label: "Salesforce (Standalone)" },
  { code: "GENERIC", label: "Generic / Custom" },
];

const DIRECTION_OPTIONS: { value: SyncDirection; label: string }[] = [
  { value: "None",  label: "None" },
  { value: "SF2QB", label: "Source → Destination" },
  { value: "QB2SF", label: "Destination → Source" },
  { value: "SFQB",  label: "Bi-directional" },
];

const STEPS = [
  { label: "Solution Type", icon: Workflow },
  { label: "Object Mapping", icon: Database },
  { label: "Credentials", icon: Key },
  { label: "Review & Save", icon: Save },
];

export function ConfigurationWizardPage() {
  useDocumentTitle("Configuration Wizard");
  const { user } = useAuth();
  const { showToast } = useToast();
  const { data: wizardData, isLoading: wizardLoading } = useWizardConfig();
  const { data: credData, isLoading: credLoading } = useCredentials();
  const saveWizard = useSaveWizardConfig();
  const saveCredential = useSaveCredential();

  const isAdmin = user?.isAdmin === true;
  const isLoading = wizardLoading || credLoading;

  // Wizard state
  const [step, setStep] = useState(0);
  const [solutionType, setSolutionType] = useState<string>("");
  const [syncValues, setSyncValues] = useState<Record<string, string>>({});
  const [initialized, setInitialized] = useState(false);

  // Credential form state
  const [credForm, setCredForm] = useState({
    sourceUsername: "",
    sourcePassword: "",
    sourceUrl: "",
    sourceToken: "",
    destUsername: "",
    destPassword: "",
    destCompanyFile: "",
  });

  // Initialize from server data once loaded
  if (!initialized && wizardData?.data && credData?.data) {
    const serverSolution = wizardData.data.solutionType || "QB";
    setSolutionType(serverSolution);
    setSyncValues(wizardData.data.syncMappings || {});

    const pc = credData.data.profileCredentials;
    if (pc) {
      setCredForm({
        sourceUsername: pc.sfUsername || pc.crmUsername || "",
        sourcePassword: "",
        sourceUrl: pc.sfUrl || pc.crmUrl || "",
        sourceToken: "",
        destUsername: pc.qbUsername || "",
        destPassword: "",
        destCompanyFile: pc.qbCompanyFile || "",
      });
    }
    setInitialized(true);
  }

  const mappings = buildSyncMappings(solutionType || "QB", syncValues);
  const meta = deriveSolutionMeta(solutionType || "QB");

  const updateSync = useCallback((key: string, value: string) => {
    setSyncValues((prev) => ({ ...prev, [key]: value }));
  }, []);

  const configuredCount = Object.values(syncValues).filter((v) => v && v !== "None").length;

  const handleSave = async () => {
    try {
      await saveWizard.mutateAsync({
        solutionType,
        syncMappings: syncValues,
      });

      // Save source credentials
      if (credForm.sourceUsername || credForm.sourceUrl) {
        await saveCredential.mutateAsync({
          credentialType: meta.crmName === "SF" ? "salesforce" : "crm",
          credentialName: meta.crmName + " Credentials",
          username: credForm.sourceUsername,
          password: credForm.sourcePassword,
          endpointUrl: credForm.sourceUrl,
          apiKey: credForm.sourceToken,
        });
      }

      // Save destination credentials
      if (credForm.destUsername || credForm.destCompanyFile) {
        await saveCredential.mutateAsync({
          credentialType: meta.fsName === "QB" ? "quickbooks" : "financial",
          credentialName: meta.fsName + " Credentials",
          username: credForm.destUsername,
          password: credForm.destPassword,
          extraConfig: credForm.destCompanyFile,
        });
      }

      showToast("Configuration saved successfully", "success");
    } catch {
      showToast("Failed to save configuration", "error");
    }
  };

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <Loader2 className="w-8 h-8 animate-spin text-muted-foreground" />
      </div>
    );
  }

  if (!isAdmin) {
    return (
      <div className="space-y-6 max-w-4xl">
        <div className="glass-panel rounded-[var(--radius)] p-6 border border-[hsl(var(--warning)/0.3)]">
          <div className="flex items-center gap-2 text-[hsl(var(--warning))]">
            <AlertTriangle className="w-5 h-5" />
            <span className="font-medium">Admin access required</span>
          </div>
          <p className="text-sm text-muted-foreground mt-2">
            Only administrators can modify the company configuration.
          </p>
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-6 max-w-5xl">
      {/* Breadcrumb */}
      <div className="flex items-center gap-2 text-sm text-muted-foreground">
        <Link to="/company/config" className="hover:text-foreground transition-colors">
          <ArrowLeft className="w-4 h-4 inline mr-1" />
          Configuration
        </Link>
        <ChevronRight className="w-3 h-3" />
        <span className="text-foreground">Wizard</span>
      </div>

      {/* Header */}
      <div className="flex items-start justify-between gap-4 flex-wrap">
        <div>
          <h1 className="text-2xl font-semibold">Configuration Wizard</h1>
          <p className="text-sm text-muted-foreground mt-1">
            Set up integration mappings, credentials, and deployment configuration.
          </p>
        </div>
        <Button variant="outline" size="sm" asChild>
          <a href="/iw-business-daemon/CompanyConfiguration.jsp">
            Classic Wizard <ExternalLink className="w-3 h-3" />
          </a>
        </Button>
      </div>

      {/* Step indicator */}
      <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-4">
        <div className="flex items-center gap-1">
          {STEPS.map((s, i) => {
            const Icon = s.icon;
            const isActive = i === step;
            const isDone = i < step;
            return (
              <button
                key={s.label}
                onClick={() => setStep(i)}
                className={cn(
                  "flex-1 flex items-center gap-2 px-3 py-2.5 rounded-xl text-xs font-medium transition-all cursor-pointer",
                  isActive
                    ? "bg-[hsl(var(--primary)/0.15)] text-[hsl(var(--primary))] border border-[hsl(var(--primary)/0.3)]"
                    : isDone
                    ? "text-[hsl(var(--success))]"
                    : "text-muted-foreground hover:text-foreground"
                )}
              >
                <div
                  className={cn(
                    "w-7 h-7 rounded-lg grid place-items-center shrink-0",
                    isActive
                      ? "bg-[hsl(var(--primary)/0.2)]"
                      : isDone
                      ? "bg-[hsl(var(--success)/0.15)]"
                      : "bg-[hsl(var(--muted)/0.3)]"
                  )}
                >
                  {isDone ? <Check className="w-3.5 h-3.5" /> : <Icon className="w-3.5 h-3.5" />}
                </div>
                <span className="hidden sm:inline">{s.label}</span>
              </button>
            );
          })}
        </div>
      </div>

      {/* Step content */}
      <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-6">
        {step === 0 && (
          <StepSolutionType
            value={solutionType}
            onChange={(v) => {
              setSolutionType(v);
              // Reset sync values when solution type changes
              setSyncValues({});
            }}
          />
        )}
        {step === 1 && (
          <StepObjectMapping
            mappings={mappings}
            meta={meta}
            onUpdate={updateSync}
          />
        )}
        {step === 2 && (
          <StepCredentials
            meta={meta}
            form={credForm}
            onChange={setCredForm}
            existingCreds={credData?.data?.credentials ?? []}
          />
        )}
        {step === 3 && (
          <StepReview
            solutionType={solutionType}
            meta={meta}
            syncValues={syncValues}
            configuredCount={configuredCount}
            mappings={mappings}
            credForm={credForm}
          />
        )}
      </div>

      {/* Navigation */}
      <div className="flex items-center justify-between">
        <Button
          variant="outline"
          onClick={() => setStep((s) => Math.max(0, s - 1))}
          disabled={step === 0}
        >
          <ArrowLeft className="w-4 h-4" /> Back
        </Button>

        {step < STEPS.length - 1 ? (
          <Button onClick={() => setStep((s) => Math.min(STEPS.length - 1, s + 1))}>
            Next <ArrowRight className="w-4 h-4" />
          </Button>
        ) : (
          <Button
            variant="success"
            onClick={() => void handleSave()}
            disabled={saveWizard.isPending || saveCredential.isPending}
          >
            {saveWizard.isPending ? (
              <RefreshCw className="w-4 h-4 animate-spin" />
            ) : (
              <Save className="w-4 h-4" />
            )}
            Save Configuration
          </Button>
        )}
      </div>
    </div>
  );
}

// ─── Step 1: Solution Type ──────────────────────────────────────────

function StepSolutionType({
  value,
  onChange,
}: {
  value: string;
  onChange: (v: string) => void;
}) {
  return (
    <div className="space-y-4">
      <div>
        <h2 className="text-lg font-semibold">Select Solution Type</h2>
        <p className="text-sm text-muted-foreground mt-1">
          Choose the integration platform that matches your source and destination systems.
        </p>
      </div>
      <div className="grid grid-cols-2 gap-3 max-sm:grid-cols-1">
        {SOLUTION_TYPES.map((st) => (
          <button
            key={st.code}
            onClick={() => onChange(st.code)}
            className={cn(
              "p-4 rounded-xl border text-left transition-all cursor-pointer",
              value === st.code
                ? "border-[hsl(var(--primary))] bg-[hsl(var(--primary)/0.08)] ring-1 ring-[hsl(var(--primary)/0.3)]"
                : "border-[hsl(var(--border))] hover:border-[hsl(var(--primary)/0.3)] hover:bg-[hsl(var(--muted)/0.2)]"
            )}
          >
            <p className="text-sm font-medium">{st.label}</p>
            <p className="text-xs text-muted-foreground mt-1 font-mono">{st.code}</p>
          </button>
        ))}
      </div>
    </div>
  );
}

// ─── Step 2: Object Mapping ─────────────────────────────────────────

function StepObjectMapping({
  mappings,
  meta,
  onUpdate,
}: {
  mappings: SyncMapping[];
  meta: ReturnType<typeof deriveSolutionMeta>;
  onUpdate: (key: string, value: string) => void;
}) {
  return (
    <div className="space-y-4">
      <div>
        <h2 className="text-lg font-semibold">Object Selection</h2>
        <p className="text-sm text-muted-foreground mt-1">
          Configure sync direction for each data object between{" "}
          <span className="font-medium text-foreground">{meta.crmName}</span> and{" "}
          <span className="font-medium text-foreground">{meta.fsName}</span>.
        </p>
      </div>

      <div className="border border-[hsl(var(--border))] rounded-xl overflow-hidden">
        <table className="w-full text-sm">
          <thead>
            <tr className="bg-[hsl(var(--muted)/0.3)]">
              <th className="text-left px-4 py-2.5 text-xs text-muted-foreground font-medium w-8">#</th>
              <th className="text-left px-4 py-2.5 text-xs text-muted-foreground font-medium">Object Mapping</th>
              <th className="text-left px-4 py-2.5 text-xs text-muted-foreground font-medium w-52">Sync Direction</th>
            </tr>
          </thead>
          <tbody>
            {mappings.map((m, i) => (
              <tr key={m.key} className="border-t border-[hsl(var(--border))]">
                <td className="px-4 py-2.5 text-xs text-muted-foreground">{i + 1}</td>
                <td className="px-4 py-2.5">
                  <p className="text-sm">{m.label}</p>
                </td>
                <td className="px-4 py-2.5">
                  <Select value={m.value} onValueChange={(v) => onUpdate(m.key, v)}>
                    <SelectTrigger>
                      <SelectValue />
                    </SelectTrigger>
                    <SelectContent>
                      {DIRECTION_OPTIONS.filter(
                        (d) => d.value !== "SFQB" || m.supportsBidirectional
                      ).map((d) => (
                        <SelectItem key={d.value} value={d.value}>
                          {d.value === "SF2QB"
                            ? `${meta.crmName} → ${meta.fsName}`
                            : d.value === "QB2SF"
                            ? `${meta.fsName} → ${meta.crmName}`
                            : d.label}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {mappings.length === 0 && (
        <div className="text-center py-8 text-muted-foreground">
          <Workflow className="w-8 h-8 mx-auto mb-2" />
          <p className="text-sm">Select a solution type first to see available mappings.</p>
        </div>
      )}
    </div>
  );
}

// ─── Step 3: Credentials ────────────────────────────────────────────

function StepCredentials({
  meta,
  form,
  onChange,
  existingCreds,
}: {
  meta: ReturnType<typeof deriveSolutionMeta>;
  form: _credFormType;
  onChange: (f: typeof form) => void;
  existingCreds: Array<{ credentialType: string; username: string; endpointUrl: string; isActive: boolean }>;
}) {
  const update = (field: string, value: string) => {
    onChange({ ...form, [field]: value });
  };

  return (
    <div className="space-y-6">
      <div>
        <h2 className="text-lg font-semibold">System Credentials</h2>
        <p className="text-sm text-muted-foreground mt-1">
          Enter API credentials for your source and destination systems. Passwords are stored
          encrypted and never returned in API responses.
        </p>
      </div>

      {/* Existing credentials indicator */}
      {existingCreds.length > 0 && (
        <div className="flex items-center gap-2 px-3 py-2 rounded-lg border border-[hsl(var(--success)/0.3)] bg-[hsl(var(--success)/0.05)]">
          <Badge variant="success">
            <Check className="w-3 h-3" />
            {existingCreds.length} configured
          </Badge>
          <span className="text-xs text-muted-foreground">Fill in fields below to update.</span>
        </div>
      )}

      {/* Source system */}
      <fieldset className="space-y-3">
        <legend className="text-sm font-semibold flex items-center gap-2">
          <div className="w-6 h-6 rounded-md bg-[hsl(var(--primary)/0.15)] grid place-items-center">
            <Database className="w-3.5 h-3.5 text-[hsl(var(--primary))]" />
          </div>
          {meta.crmName} (Source System)
        </legend>
        <div className="grid grid-cols-2 gap-3 max-sm:grid-cols-1">
          <div className="space-y-1.5">
            <Label htmlFor="srcUser">Username / Email</Label>
            <Input id="srcUser" value={form.sourceUsername} onChange={(e) => update("sourceUsername", e.target.value)} placeholder={`${meta.crmName} login`} />
          </div>
          <div className="space-y-1.5">
            <Label htmlFor="srcPass">Password</Label>
            <Input id="srcPass" type="password" value={form.sourcePassword} onChange={(e) => update("sourcePassword", e.target.value)} placeholder="Leave blank to keep existing" />
          </div>
          <div className="space-y-1.5">
            <Label htmlFor="srcUrl">Endpoint URL</Label>
            <Input id="srcUrl" value={form.sourceUrl} onChange={(e) => update("sourceUrl", e.target.value)} placeholder={meta.crmName === "SF" ? "https://login.salesforce.com" : "https://..."} />
          </div>
          {meta.crmName === "SF" && (
            <div className="space-y-1.5">
              <Label htmlFor="srcToken">Security Token</Label>
              <Input id="srcToken" value={form.sourceToken} onChange={(e) => update("sourceToken", e.target.value)} placeholder="Salesforce security token" />
            </div>
          )}
        </div>
      </fieldset>

      {/* Destination system */}
      <fieldset className="space-y-3">
        <legend className="text-sm font-semibold flex items-center gap-2">
          <div className="w-6 h-6 rounded-md bg-[hsl(var(--success)/0.15)] grid place-items-center">
            <Key className="w-3.5 h-3.5 text-[hsl(var(--success))]" />
          </div>
          {meta.fsName} (Destination System)
        </legend>
        <div className="grid grid-cols-2 gap-3 max-sm:grid-cols-1">
          <div className="space-y-1.5">
            <Label htmlFor="destUser">Username</Label>
            <Input id="destUser" value={form.destUsername} onChange={(e) => update("destUsername", e.target.value)} placeholder={`${meta.fsName} login`} />
          </div>
          <div className="space-y-1.5">
            <Label htmlFor="destPass">Password</Label>
            <Input id="destPass" type="password" value={form.destPassword} onChange={(e) => update("destPassword", e.target.value)} placeholder="Leave blank to keep existing" />
          </div>
          {meta.fsName === "QB" && (
            <div className="space-y-1.5 col-span-2 max-sm:col-span-1">
              <Label htmlFor="destFile">Company File</Label>
              <Input id="destFile" value={form.destCompanyFile} onChange={(e) => update("destCompanyFile", e.target.value)} placeholder="Path to QB company file" />
            </div>
          )}
        </div>
      </fieldset>
    </div>
  );
}

// Placeholder type export for the credential form (used by StepCredentials props)
export type _credFormType = {
  sourceUsername: string;
  sourcePassword: string;
  sourceUrl: string;
  sourceToken: string;
  destUsername: string;
  destPassword: string;
  destCompanyFile: string;
};

// ─── Step 4: Review ─────────────────────────────────────────────────

function StepReview({
  solutionType,
  meta,
  syncValues,
  configuredCount,
  mappings,
  credForm,
}: {
  solutionType: string;
  meta: ReturnType<typeof deriveSolutionMeta>;
  syncValues: Record<string, string>;
  configuredCount: number;
  mappings: SyncMapping[];
  credForm: _credFormType;
}) {
  const activeMappings = mappings.filter((m) => syncValues[m.key] && syncValues[m.key] !== "None");

  return (
    <div className="space-y-6">
      <div>
        <h2 className="text-lg font-semibold">Review Configuration</h2>
        <p className="text-sm text-muted-foreground mt-1">
          Review your settings before saving. You can go back to any step to make changes.
        </p>
      </div>

      {/* Summary cards */}
      <div className="grid grid-cols-3 gap-4 max-sm:grid-cols-1">
        <div className="rounded-xl border border-[hsl(var(--border))] p-4">
          <p className="text-xs text-muted-foreground">Solution Type</p>
          <p className="text-lg font-bold mt-1">{solutionType || "—"}</p>
          <p className="text-xs text-muted-foreground mt-0.5">
            {meta.crmName} ↔ {meta.fsName}
          </p>
        </div>
        <div className="rounded-xl border border-[hsl(var(--border))] p-4">
          <p className="text-xs text-muted-foreground">Active Mappings</p>
          <p className="text-lg font-bold mt-1">{configuredCount}</p>
          <p className="text-xs text-muted-foreground mt-0.5">
            of {mappings.length} available
          </p>
        </div>
        <div className="rounded-xl border border-[hsl(var(--border))] p-4">
          <p className="text-xs text-muted-foreground">Credentials</p>
          <p className="text-lg font-bold mt-1">
            {(credForm.sourceUsername ? 1 : 0) + (credForm.destUsername ? 1 : 0)} / 2
          </p>
          <p className="text-xs text-muted-foreground mt-0.5">systems configured</p>
        </div>
      </div>

      {/* Active mappings list */}
      {activeMappings.length > 0 && (
        <div>
          <h3 className="text-sm font-semibold mb-2">Active Sync Mappings</h3>
          <div className="space-y-1.5">
            {activeMappings.map((m) => (
              <div
                key={m.key}
                className="flex items-center justify-between px-3 py-2 rounded-lg border border-[hsl(var(--success)/0.2)] bg-[hsl(var(--success)/0.05)] text-sm"
              >
                <span>{m.label}</span>
                <Badge variant="success">
                  {syncValues[m.key] === "SFQB"
                    ? "Bi-directional"
                    : syncValues[m.key] === "SF2QB"
                    ? `${meta.crmName} → ${meta.fsName}`
                    : `${meta.fsName} → ${meta.crmName}`}
                </Badge>
              </div>
            ))}
          </div>
        </div>
      )}

      {activeMappings.length === 0 && (
        <div className="flex items-center gap-2 px-3 py-2 rounded-lg border border-[hsl(var(--warning)/0.3)] bg-[hsl(var(--warning)/0.05)] text-xs text-[hsl(var(--warning))]">
          <AlertTriangle className="w-3.5 h-3.5" />
          No sync mappings configured. Go back to Step 2 to select at least one mapping.
        </div>
      )}
    </div>
  );
}

