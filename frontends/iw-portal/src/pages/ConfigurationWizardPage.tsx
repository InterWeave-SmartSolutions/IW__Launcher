import { useState, useCallback, useEffect, useRef } from "react";
import { Link, useNavigate } from "react-router-dom";
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
  ChevronDown,
  ExternalLink,
  RefreshCw,
  Settings2,
  Clock,
  Mail,
  ShieldAlert,
  Globe,
  Server,
  Zap,
  HelpCircle,
  Download,
  Wifi,
  WifiOff,
  Info,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { envLabel, stopLabel, emailLabel } from "@/lib/config-labels";
import { useAuth } from "@/providers/AuthProvider";
import { useToast } from "@/providers/ToastProvider";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import {
  useWizardConfig,
  useSaveWizardConfig,
  useCredentials,
  useSaveCredential,
  useTestCredential,
} from "@/hooks/useConfiguration";
import {
  buildSyncMappings,
  deriveSolutionMeta,
  MAPPING_DEPENDENCIES,
  RECOMMENDED_DEFAULTS,
  CATEGORY_LABELS,
} from "@/types/configuration";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Badge } from "@/components/ui/badge";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from "@/components/ui/tooltip";
import type { SyncDirection, SyncMapping, MappingCategory } from "@/types/configuration";

// ─── Constants ────────────────────────────────────────────────────────

const SOLUTION_TYPES = [
  { code: "SF2QB",  label: "Salesforce → QuickBooks", description: "Standard unidirectional sync from Salesforce CRM to QuickBooks financials." },
  { code: "SF2QB1", label: "Salesforce → QuickBooks (Extended)", description: "Extended mapping set with vendor, job, purchase order, and credit card charge sync." },
  { code: "SF2QBB", label: "Salesforce → QuickBooks (Full Bi-directional)", description: "Full bi-directional sync including COA, journal entries, time tracking, and statement charges." },
  { code: "SF2NS",  label: "Salesforce → NetSuite", description: "Sync Salesforce accounts, opportunities, and products to NetSuite with subsidiary support." },
  { code: "SF2PT",  label: "Salesforce → Sage", description: "Integrate Salesforce CRM data with Sage accounting platform." },
  { code: "SF2GP",  label: "Salesforce → MS GP", description: "Sync Salesforce CRM to Microsoft Great Plains (Dynamics GP) financials." },
  { code: "CRM2QB", label: "CRM → QuickBooks", description: "Generic CRM to QuickBooks integration for non-Salesforce CRM systems." },
  { code: "QB",     label: "QuickBooks (Standalone)", description: "Standalone QuickBooks configuration for direct API access and data management." },
  { code: "SF",     label: "Salesforce (Standalone)", description: "Standalone Salesforce configuration for direct API access and data management." },
  { code: "GENERIC", label: "Generic / Custom", description: "Custom integration platform for bespoke source and destination system pairs." },
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
  { label: "Execution Settings", icon: Settings2 },
  { label: "Review & Save", icon: Save },
];

// Execution settings keys stored in XML alongside SyncType* fields
const EXEC_DEFAULTS: Record<string, string> = {
  SandBoxUsed: "No",
  Env2Con: "Tst",
  QBVersion: "USA",
  QBLocation: "DEFAULT",
  StopSchedTr: "None",
  SleepStart: "",
  SleepEnd: "",
  TimeZone: "0",
  EmlNtf: "None",
  UseAdmEml: "No",
  CCEmail: "",
  BCCEmail: "",
  LongTimeOut: "No",
  ConFailState: "No",
};

const EXEC_KEYS = new Set(Object.keys(EXEC_DEFAULTS));

const DRAFT_KEY = "iw-wizard-draft";

export type CredFormType = {
  sourceUsername: string;
  sourcePassword: string;
  sourceUrl: string;
  sourceToken: string;
  destUsername: string;
  destPassword: string;
  destCompanyFile: string;
};

// ─── Main Component ──────────────────────────────────────────────────

export function ConfigurationWizardPage() {
  useDocumentTitle("Configuration Wizard");
  const navigate = useNavigate();
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
  const [execSettings, setExecSettings] = useState<Record<string, string>>({ ...EXEC_DEFAULTS });
  const [initialized, setInitialized] = useState(false);
  const initialSnapshot = useRef<Record<string, string>>({});

  // Credential form state
  const [credForm, setCredForm] = useState<CredFormType>({
    sourceUsername: "",
    sourcePassword: "",
    sourceUrl: "",
    sourceToken: "",
    destUsername: "",
    destPassword: "",
    destCompanyFile: "",
  });

  // Initialize from server data once loaded
  useEffect(() => {
    if (initialized || !wizardData?.data || !credData?.data) return;

    // Try restoring draft from sessionStorage first
    const draft = loadDraft();
    if (draft) {
      setSolutionType(draft.solutionType);
      setSyncValues(draft.syncValues);
      setExecSettings(draft.execSettings);
      setCredForm(draft.credForm);
      setStep(draft.step);
      initialSnapshot.current = { ...draft.syncValues, ...draft.execSettings };
      setInitialized(true);
      return;
    }

    const serverSolution = wizardData.data.solutionType || "QB";
    setSolutionType(serverSolution);

    // Split server syncMappings into sync entries and execution entries
    const serverMappings = wizardData.data.syncMappings || {};
    const syncOnly: Record<string, string> = {};
    const execOnly: Record<string, string> = { ...EXEC_DEFAULTS };
    for (const [k, v] of Object.entries(serverMappings)) {
      if (EXEC_KEYS.has(k)) {
        execOnly[k] = v;
      } else {
        syncOnly[k] = v;
      }
    }
    setSyncValues(syncOnly);
    setExecSettings(execOnly);
    initialSnapshot.current = { ...syncOnly, ...execOnly };

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
  }, [initialized, wizardData, credData]);

  // Save draft to sessionStorage on meaningful changes (exclude passwords)
  useEffect(() => {
    if (!initialized) return;
    saveDraft({ solutionType, syncValues, execSettings, credForm: {
      ...credForm, sourcePassword: "", destPassword: "", sourceToken: "",
    }, step });
  }, [initialized, solutionType, syncValues, execSettings, credForm, step]);

  const mappings = buildSyncMappings(solutionType || "QB", syncValues);
  const meta = deriveSolutionMeta(solutionType || "QB");

  const updateSync = useCallback((key: string, value: string) => {
    setSyncValues((prev) => ({ ...prev, [key]: value }));
  }, []);

  const updateExec = useCallback((key: string, value: string) => {
    setExecSettings((prev) => ({ ...prev, [key]: value }));
  }, []);

  const configuredCount = Object.values(syncValues).filter((v) => v && v !== "None").length;

  // Step validation
  const canAdvance = (fromStep: number): boolean => {
    if (fromStep === 0) return !!solutionType;
    return true;
  };

  const handleNext = () => {
    if (!canAdvance(step)) {
      showToast("Please complete this step before proceeding.", "error");
      return;
    }
    setStep((s) => Math.min(STEPS.length - 1, s + 1));
  };

  const handleSave = async () => {
    try {
      // Merge sync mappings + execution settings for save
      const allMappings: Record<string, string> = { ...syncValues };
      for (const [k, v] of Object.entries(execSettings)) {
        if (v !== "") allMappings[k] = v;
      }

      await saveWizard.mutateAsync({
        solutionType,
        syncMappings: allMappings,
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

      clearDraft();
      showToast("Configuration saved successfully", "success");
      navigate("/company/config");
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
    <TooltipProvider delayDuration={300}>
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
                  onClick={() => {
                    if (i < step || (i > step && canAdvance(step))) setStep(i);
                  }}
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
                const prev = solutionType;
                setSolutionType(v);
                // Apply recommended defaults on first selection or type change
                if (v !== prev) {
                  const defaults = RECOMMENDED_DEFAULTS[v];
                  if (defaults && Object.keys(syncValues).filter((k) => syncValues[k] !== "None").length === 0) {
                    setSyncValues({ ...defaults });
                  }
                }
              }}
            />
          )}
          {step === 1 && (
            <StepObjectMapping
              solutionType={solutionType}
              mappings={mappings}
              meta={meta}
              syncValues={syncValues}
              onUpdate={updateSync}
              onBulkUpdate={setSyncValues}
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
            <StepExecutionSettings
              meta={meta}
              settings={execSettings}
              onUpdate={updateExec}
            />
          )}
          {step === 4 && (
            <StepReview
              solutionType={solutionType}
              meta={meta}
              syncValues={syncValues}
              configuredCount={configuredCount}
              mappings={mappings}
              credForm={credForm}
              execSettings={execSettings}
              initialSnapshot={initialSnapshot.current}
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
            <Button onClick={handleNext} disabled={!canAdvance(step)}>
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
    </TooltipProvider>
  );
}

// ─── Draft Persistence ───────────────────────────────────────────────

interface DraftData {
  solutionType: string;
  syncValues: Record<string, string>;
  execSettings: Record<string, string>;
  credForm: CredFormType;
  step: number;
}

function saveDraft(data: DraftData) {
  try { sessionStorage.setItem(DRAFT_KEY, JSON.stringify(data)); } catch { /* quota */ }
}

function loadDraft(): DraftData | null {
  try {
    const raw = sessionStorage.getItem(DRAFT_KEY);
    if (!raw) return null;
    const data = JSON.parse(raw) as DraftData;
    if (data.solutionType && data.syncValues) return data;
    return null;
  } catch { return null; }
}

function clearDraft() {
  try { sessionStorage.removeItem(DRAFT_KEY); } catch { /* ok */ }
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
            <p className="text-xs text-muted-foreground mt-1">{st.description}</p>
            <p className="text-[10px] text-muted-foreground mt-1.5 font-mono opacity-60">{st.code}</p>
          </button>
        ))}
      </div>
      {!value && (
        <div className="flex items-center gap-2 px-3 py-2 rounded-lg border border-[hsl(var(--warning)/0.3)] bg-[hsl(var(--warning)/0.05)] text-xs text-[hsl(var(--warning))]">
          <AlertTriangle className="w-3.5 h-3.5" />
          Select a solution type to continue.
        </div>
      )}
    </div>
  );
}

// ─── Step 2: Object Mapping ─────────────────────────────────────────

function StepObjectMapping({
  solutionType,
  mappings,
  meta,
  syncValues,
  onUpdate,
  onBulkUpdate,
}: {
  solutionType: string;
  mappings: SyncMapping[];
  meta: ReturnType<typeof deriveSolutionMeta>;
  syncValues: Record<string, string>;
  onUpdate: (key: string, value: string) => void;
  onBulkUpdate: (values: Record<string, string>) => void;
}) {
  const [showExtended, setShowExtended] = useState(false);

  const coreMappings = mappings.filter((m) => m.tier === "core");
  const extendedMappings = mappings.filter((m) => m.tier === "extended");

  // Group extended by category
  const extendedByCategory = extendedMappings.reduce<Record<MappingCategory, SyncMapping[]>>(
    (acc, m) => { (acc[m.category] ??= []).push(m); return acc; },
    {} as Record<MappingCategory, SyncMapping[]>
  );

  // Dependency warnings
  const warnings: string[] = [];
  for (const [dep, prereqs] of Object.entries(MAPPING_DEPENDENCIES)) {
    const depVal = syncValues[dep];
    if (depVal && depVal !== "None") {
      for (const prereq of prereqs) {
        const prereqVal = syncValues[prereq];
        if (!prereqVal || prereqVal === "None") {
          const depMapping = mappings.find((m) => m.key === dep);
          const prereqMapping = mappings.find((m) => m.key === prereq);
          if (depMapping && prereqMapping) {
            warnings.push(`"${depMapping.label}" requires "${prereqMapping.label}" to be enabled.`);
          }
        }
      }
    }
  }

  const handleBulkAction = (action: "enableCore" | "disableAll" | "recommended") => {
    if (action === "enableCore") {
      const next = { ...syncValues };
      coreMappings.forEach((m) => { if (!next[m.key] || next[m.key] === "None") next[m.key] = "SF2QB"; });
      onBulkUpdate(next);
    } else if (action === "disableAll") {
      const next: Record<string, string> = {};
      mappings.forEach((m) => { next[m.key] = "None"; });
      onBulkUpdate(next);
    } else if (action === "recommended") {
      const defaults = RECOMMENDED_DEFAULTS[solutionType] || {};
      onBulkUpdate({ ...defaults });
    }
  };

  return (
    <div className="space-y-4">
      <div className="flex items-start justify-between gap-4 flex-wrap">
        <div>
          <h2 className="text-lg font-semibold">Object Selection</h2>
          <p className="text-sm text-muted-foreground mt-1">
            Configure sync direction for each data object between{" "}
            <span className="font-medium text-foreground">{meta.crmName}</span> and{" "}
            <span className="font-medium text-foreground">{meta.fsName}</span>.
          </p>
        </div>
        {/* Bulk actions */}
        <div className="flex items-center gap-2 flex-wrap">
          <Button variant="outline" size="sm" onClick={() => handleBulkAction("enableCore")}>
            <Zap className="w-3 h-3" /> Enable Core
          </Button>
          <Button variant="outline" size="sm" onClick={() => handleBulkAction("recommended")}>
            <RefreshCw className="w-3 h-3" /> Recommended
          </Button>
          <Button variant="ghost" size="sm" onClick={() => handleBulkAction("disableAll")}>
            Disable All
          </Button>
        </div>
      </div>

      {/* Dependency warnings */}
      {warnings.length > 0 && (
        <div className="space-y-1">
          {warnings.map((w, i) => (
            <div key={i} className="flex items-center gap-2 px-3 py-1.5 rounded-lg border border-[hsl(var(--warning)/0.3)] bg-[hsl(var(--warning)/0.05)] text-xs text-[hsl(var(--warning))]">
              <AlertTriangle className="w-3 h-3 shrink-0" />
              {w}
            </div>
          ))}
        </div>
      )}

      {/* Core mappings */}
      <MappingTable label="Core Mappings" mappings={coreMappings} meta={meta} syncValues={syncValues} onUpdate={onUpdate} />

      {/* Extended mappings toggle */}
      {extendedMappings.length > 0 && (
        <div className="space-y-3">
          <button
            onClick={() => setShowExtended(!showExtended)}
            className="flex items-center gap-2 text-sm font-medium text-[hsl(var(--primary))] hover:underline cursor-pointer"
          >
            <ChevronDown className={cn("w-4 h-4 transition-transform", showExtended && "rotate-180")} />
            {showExtended ? "Hide" : "Show"} Extended Mappings ({extendedMappings.length})
          </button>

          {showExtended && (
            <div className="space-y-4 pl-2 border-l-2 border-[hsl(var(--primary)/0.15)]">
              {(Object.entries(extendedByCategory) as [MappingCategory, SyncMapping[]][]).map(([cat, catMappings]) => (
                <MappingTable
                  key={cat}
                  label={CATEGORY_LABELS[cat]}
                  mappings={catMappings}
                  meta={meta}
                  syncValues={syncValues}
                  onUpdate={onUpdate}
                />
              ))}
            </div>
          )}
        </div>
      )}

      {mappings.length === 0 && (
        <div className="text-center py-8 text-muted-foreground">
          <Workflow className="w-8 h-8 mx-auto mb-2" />
          <p className="text-sm">Select a solution type first to see available mappings.</p>
        </div>
      )}
    </div>
  );
}

function MappingTable({
  label,
  mappings,
  meta,
  syncValues,
  onUpdate,
}: {
  label: string;
  mappings: SyncMapping[];
  meta: ReturnType<typeof deriveSolutionMeta>;
  syncValues: Record<string, string>;
  onUpdate: (key: string, value: string) => void;
}) {
  return (
    <div>
      <p className="text-xs font-semibold text-muted-foreground uppercase tracking-wider mb-2">{label}</p>
      <div className="border border-[hsl(var(--border))] rounded-xl overflow-hidden">
        {/* Desktop table */}
        <table className="w-full text-sm max-sm:hidden">
          <thead>
            <tr className="bg-[hsl(var(--muted)/0.3)]">
              <th className="text-left px-4 py-2.5 text-xs text-muted-foreground font-medium">Object Mapping</th>
              <th className="text-left px-4 py-2.5 text-xs text-muted-foreground font-medium w-52">Sync Direction</th>
            </tr>
          </thead>
          <tbody>
            {mappings.map((m) => (
              <tr key={m.key} className="border-t border-[hsl(var(--border))]">
                <td className="px-4 py-2.5">
                  <p className="text-sm">{m.label}</p>
                </td>
                <td className="px-4 py-2.5">
                  <Select value={syncValues[m.key] || m.value} onValueChange={(v) => onUpdate(m.key, v)}>
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
        {/* Mobile card layout */}
        <div className="sm:hidden divide-y divide-[hsl(var(--border))]">
          {mappings.map((m) => (
            <div key={m.key} className="p-3 space-y-2">
              <p className="text-sm font-medium">{m.label}</p>
              <Select value={syncValues[m.key] || m.value} onValueChange={(v) => onUpdate(m.key, v)}>
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
            </div>
          ))}
        </div>
      </div>
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
  form: CredFormType;
  onChange: (f: CredFormType) => void;
  existingCreds: Array<{ credentialType: string; username: string; endpointUrl: string; isActive: boolean }>;
}) {
  const testCredential = useTestCredential();
  const [testResult, setTestResult] = useState<{ type: string; reachable: boolean; message: string } | null>(null);

  const update = (field: string, value: string) => {
    onChange({ ...form, [field]: value });
  };

  const handleTestConnection = async (type: "source" | "dest") => {
    const url = type === "source" ? form.sourceUrl : "";
    if (!url) return;
    setTestResult(null);
    try {
      const result = await testCredential.mutateAsync({
        credentialType: type === "source" ? meta.crmName : meta.fsName,
        endpointUrl: url,
        username: type === "source" ? form.sourceUsername : form.destUsername,
      });
      setTestResult({ type, reachable: result.reachable, message: result.message });
    } catch {
      setTestResult({ type, reachable: false, message: "Test request failed" });
    }
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

      {/* Test result banner */}
      {testResult && (
        <div className={cn(
          "flex items-center gap-2 px-3 py-2 rounded-lg border text-xs",
          testResult.reachable
            ? "border-[hsl(var(--success)/0.3)] bg-[hsl(var(--success)/0.05)] text-[hsl(var(--success))]"
            : "border-[hsl(var(--destructive)/0.3)] bg-[hsl(var(--destructive)/0.05)] text-[hsl(var(--destructive))]"
        )}>
          {testResult.reachable ? <Wifi className="w-3.5 h-3.5" /> : <WifiOff className="w-3.5 h-3.5" />}
          {testResult.message}
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
        {form.sourceUrl && (
          <Button
            variant="outline"
            size="sm"
            onClick={() => void handleTestConnection("source")}
            disabled={testCredential.isPending}
          >
            {testCredential.isPending ? <Loader2 className="w-3 h-3 animate-spin" /> : <Wifi className="w-3 h-3" />}
            Test Connection
          </Button>
        )}
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

// ─── Step 4: Execution Settings ─────────────────────────────────────

function StepExecutionSettings({
  meta,
  settings,
  onUpdate,
}: {
  meta: ReturnType<typeof deriveSolutionMeta>;
  settings: Record<string, string>;
  onUpdate: (key: string, value: string) => void;
}) {
  const isQB = meta.fsName === "QB";

  return (
    <div className="space-y-6">
      <div>
        <h2 className="text-lg font-semibold">Execution Settings</h2>
        <p className="text-sm text-muted-foreground mt-1">
          Configure environment, scheduling, error handling, and notification settings.
          These values are stored in the configuration XML and used by the transformation engine at runtime.
        </p>
      </div>

      {/* Environment */}
      <fieldset className="space-y-3">
        <legend className="text-sm font-semibold flex items-center gap-2">
          <div className="w-6 h-6 rounded-md bg-[hsl(var(--primary)/0.15)] grid place-items-center">
            <Globe className="w-3.5 h-3.5 text-[hsl(var(--primary))]" />
          </div>
          Environment
        </legend>
        <div className="grid grid-cols-2 gap-3 max-sm:grid-cols-1">
          <div className="space-y-1.5">
            <Label>Use Sandbox / Test Environment</Label>
            <Select value={settings.SandBoxUsed || "No"} onValueChange={(v) => onUpdate("SandBoxUsed", v)}>
              <SelectTrigger><SelectValue /></SelectTrigger>
              <SelectContent>
                <SelectItem value="No">No (Production)</SelectItem>
                <SelectItem value="Yes">Yes (Sandbox)</SelectItem>
              </SelectContent>
            </Select>
          </div>
          <div className="space-y-1.5">
            <div className="flex items-center gap-1.5">
              <Label>Base Environment</Label>
              <FieldTooltip text="Determines which InterWeave server pool processes your transactions. Production A is the default; use Dedicated for high-volume customers." />
            </div>
            <Select value={settings.Env2Con || "Tst"} onValueChange={(v) => onUpdate("Env2Con", v)}>
              <SelectTrigger><SelectValue /></SelectTrigger>
              <SelectContent>
                <SelectItem value="Prd">Production A</SelectItem>
                <SelectItem value="Tst">Production B</SelectItem>
                <SelectItem value="Prd1">Production C</SelectItem>
                <SelectItem value="Tst1">Production D</SelectItem>
                <SelectItem value="Dev">Production E</SelectItem>
                <SelectItem value="Addtnl">Additional Server</SelectItem>
                <SelectItem value="Ddctd">Dedicated Server</SelectItem>
              </SelectContent>
            </Select>
          </div>
        </div>
      </fieldset>

      {/* System settings (conditional on solution type) */}
      <fieldset className="space-y-3">
        <legend className="text-sm font-semibold flex items-center gap-2">
          <div className="w-6 h-6 rounded-md bg-[hsl(var(--muted)/0.5)] grid place-items-center">
            <Server className="w-3.5 h-3.5 text-muted-foreground" />
          </div>
          System Settings
        </legend>
        <div className="grid grid-cols-2 gap-3 max-sm:grid-cols-1">
          {isQB && (
            <>
              <div className="space-y-1.5">
                <Label>{meta.fsName} Version / Locale</Label>
                <Select value={settings.QBVersion || "USA"} onValueChange={(v) => onUpdate("QBVersion", v)}>
                  <SelectTrigger><SelectValue /></SelectTrigger>
                  <SelectContent>
                    <SelectItem value="USA">USA</SelectItem>
                    <SelectItem value="UK">UK</SelectItem>
                    <SelectItem value="CAN">Canada</SelectItem>
                    <SelectItem value="AUS">Australia</SelectItem>
                    <SelectItem value="NZ">New Zealand</SelectItem>
                    <SelectItem value="SEA">South East Asia</SelectItem>
                  </SelectContent>
                </Select>
              </div>
              <div className="space-y-1.5">
                <Label>{meta.fsName} Location</Label>
                <Select value={settings.QBLocation || "DEFAULT"} onValueChange={(v) => onUpdate("QBLocation", v)}>
                  <SelectTrigger><SelectValue /></SelectTrigger>
                  <SelectContent>
                    <SelectItem value="HOST">Hosted Managed</SelectItem>
                    <SelectItem value="HOSTU">Hosted Unmanaged</SelectItem>
                    <SelectItem value="HOUSE">In-House</SelectItem>
                    <SelectItem value="ONLINE">On-Line</SelectItem>
                    <SelectItem value="STANDARD">Standard Only</SelectItem>
                    <SelectItem value="DEFAULT">Default</SelectItem>
                  </SelectContent>
                </Select>
              </div>
            </>
          )}
          <div className="space-y-1.5">
            <div className="flex items-center gap-1.5">
              <Label>Time Zone Shift</Label>
              <FieldTooltip text="Offset in hours from the InterWeave server timezone. Use this when your business system timestamps differ from the server clock." />
            </div>
            <Input
              type="number"
              min={-12}
              max={14}
              value={settings.TimeZone || "0"}
              onChange={(e) => onUpdate("TimeZone", e.target.value)}
              placeholder="0"
            />
          </div>
          {!isQB && <div />}
        </div>
      </fieldset>

      {/* Error handling */}
      <fieldset className="space-y-3">
        <legend className="text-sm font-semibold flex items-center gap-2">
          <div className="w-6 h-6 rounded-md bg-[hsl(var(--destructive)/0.15)] grid place-items-center">
            <ShieldAlert className="w-3.5 h-3.5 text-[hsl(var(--destructive))]" />
          </div>
          Error Handling
        </legend>
        <div className="grid grid-cols-3 gap-3 max-sm:grid-cols-1">
          <div className="space-y-1.5">
            <Label>Stop Scheduled Transaction</Label>
            <Select value={settings.StopSchedTr || "None"} onValueChange={(v) => onUpdate("StopSchedTr", v)}>
              <SelectTrigger><SelectValue /></SelectTrigger>
              <SelectContent>
                <SelectItem value="None">Never</SelectItem>
                <SelectItem value="Con">After Connection Failure</SelectItem>
                <SelectItem value="EveryErr">After Every Error</SelectItem>
              </SelectContent>
            </Select>
          </div>
          <div className="space-y-1.5">
            <div className="flex items-center gap-1.5">
              <Label>Extended Connection Timeout</Label>
              <FieldTooltip text="Increases API connection timeout from 30s to 120s. Enable for slow or overseas endpoints." />
            </div>
            <Select value={settings.LongTimeOut || "No"} onValueChange={(v) => onUpdate("LongTimeOut", v)}>
              <SelectTrigger><SelectValue /></SelectTrigger>
              <SelectContent>
                <SelectItem value="No">No</SelectItem>
                <SelectItem value="Yes">Yes</SelectItem>
              </SelectContent>
            </Select>
          </div>
          <div className="space-y-1.5">
            <Label>Restore State on Failure</Label>
            <Select value={settings.ConFailState || "No"} onValueChange={(v) => onUpdate("ConFailState", v)}>
              <SelectTrigger><SelectValue /></SelectTrigger>
              <SelectContent>
                <SelectItem value="No">No</SelectItem>
                <SelectItem value="Yes">Yes</SelectItem>
              </SelectContent>
            </Select>
          </div>
        </div>
      </fieldset>

      {/* Schedule & Notifications */}
      <fieldset className="space-y-3">
        <legend className="text-sm font-semibold flex items-center gap-2">
          <div className="w-6 h-6 rounded-md bg-[hsl(var(--warning)/0.15)] grid place-items-center">
            <Clock className="w-3.5 h-3.5 text-[hsl(var(--warning))]" />
          </div>
          Schedule & Notifications
        </legend>
        <div className="grid grid-cols-2 gap-3 max-sm:grid-cols-1">
          <div className="space-y-1.5">
            <Label>Sleep Window Start</Label>
            <Input
              value={settings.SleepStart || ""}
              onChange={(e) => onUpdate("SleepStart", e.target.value)}
              placeholder="e.g. 22:00"
            />
            <p className="text-[10px] text-muted-foreground">No integrations during sleep window</p>
          </div>
          <div className="space-y-1.5">
            <Label>Sleep Window End</Label>
            <Input
              value={settings.SleepEnd || ""}
              onChange={(e) => onUpdate("SleepEnd", e.target.value)}
              placeholder="e.g. 06:00"
            />
          </div>
        </div>
        <div className="grid grid-cols-2 gap-3 max-sm:grid-cols-1">
          <div className="space-y-1.5">
            <Label>Email Notification Mode</Label>
            <Select value={settings.EmlNtf || "None"} onValueChange={(v) => onUpdate("EmlNtf", v)}>
              <SelectTrigger><SelectValue /></SelectTrigger>
              <SelectContent>
                <SelectItem value="None">None</SelectItem>
                <SelectItem value="Con">Connection Failures Only</SelectItem>
                <SelectItem value="EveryErr">After Every Error</SelectItem>
                <SelectItem value="DailyEveryErr">Every Error + Full Daily Report</SelectItem>
                <SelectItem value="ConD">Connection Failures + Full Daily Report</SelectItem>
                <SelectItem value="ConDE">Connection Failures + Error Daily Report</SelectItem>
                <SelectItem value="DailyE">Error Daily Report Only</SelectItem>
                <SelectItem value="Daily">Full Daily Report Only</SelectItem>
              </SelectContent>
            </Select>
          </div>
          <div className="space-y-1.5">
            <Label>Use Admin Email for Notifications</Label>
            <Select value={settings.UseAdmEml || "No"} onValueChange={(v) => onUpdate("UseAdmEml", v)}>
              <SelectTrigger><SelectValue /></SelectTrigger>
              <SelectContent>
                <SelectItem value="No">No</SelectItem>
                <SelectItem value="Yes">Yes</SelectItem>
              </SelectContent>
            </Select>
          </div>
        </div>
        {(settings.EmlNtf && settings.EmlNtf !== "None") && (
          <div className="grid grid-cols-2 gap-3 max-sm:grid-cols-1">
            <div className="space-y-1.5">
              <Label>CC Email Addresses</Label>
              <Input
                value={settings.CCEmail || ""}
                onChange={(e) => onUpdate("CCEmail", e.target.value)}
                placeholder="email@example.com"
              />
            </div>
            <div className="space-y-1.5">
              <Label>
                <Mail className="w-3 h-3 inline mr-1" />
                BCC Email Addresses
              </Label>
              <Input
                value={settings.BCCEmail || ""}
                onChange={(e) => onUpdate("BCCEmail", e.target.value)}
                placeholder="email@example.com"
              />
            </div>
          </div>
        )}
      </fieldset>
    </div>
  );
}

function FieldTooltip({ text }: { text: string }) {
  return (
    <Tooltip>
      <TooltipTrigger asChild>
        <HelpCircle className="w-3.5 h-3.5 text-muted-foreground cursor-help" />
      </TooltipTrigger>
      <TooltipContent side="top" className="max-w-64">
        <p>{text}</p>
      </TooltipContent>
    </Tooltip>
  );
}

// ─── Step 5: Review ─────────────────────────────────────────────────

function StepReview({
  solutionType,
  meta,
  syncValues,
  configuredCount,
  mappings,
  credForm,
  execSettings,
  initialSnapshot,
}: {
  solutionType: string;
  meta: ReturnType<typeof deriveSolutionMeta>;
  syncValues: Record<string, string>;
  configuredCount: number;
  mappings: SyncMapping[];
  credForm: CredFormType;
  execSettings: Record<string, string>;
  initialSnapshot: Record<string, string>;
}) {
  const activeMappings = mappings.filter((m) => syncValues[m.key] && syncValues[m.key] !== "None");

  // Compute changed fields for config diff
  const changes: { key: string; from: string; to: string }[] = [];
  const allCurrent = { ...syncValues, ...execSettings };
  for (const [k, v] of Object.entries(allCurrent)) {
    const prev = initialSnapshot[k] || "";
    if (v !== prev && !(v === "None" && prev === "")) {
      changes.push({ key: k, from: prev || "(empty)", to: v });
    }
  }
  // Check removed keys
  for (const [k, v] of Object.entries(initialSnapshot)) {
    if (!(k in allCurrent) && v && v !== "None") {
      changes.push({ key: k, from: v, to: "(removed)" });
    }
  }

  const handleExport = () => {
    const data = {
      solutionType,
      syncMappings: syncValues,
      execSettings,
      exportedAt: new Date().toISOString(),
    };
    const blob = new Blob([JSON.stringify(data, null, 2)], { type: "application/json" });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = `iw-config-${solutionType}-${Date.now()}.json`;
    a.click();
    URL.revokeObjectURL(url);
  };

  return (
    <div className="space-y-6">
      <div className="flex items-start justify-between gap-4 flex-wrap">
        <div>
          <h2 className="text-lg font-semibold">Review Configuration</h2>
          <p className="text-sm text-muted-foreground mt-1">
            Review your settings before saving. You can go back to any step to make changes.
          </p>
        </div>
        <Button variant="outline" size="sm" onClick={handleExport}>
          <Download className="w-3 h-3" /> Export JSON
        </Button>
      </div>

      {/* Summary cards */}
      <div className="grid grid-cols-4 gap-4 max-md:grid-cols-2 max-sm:grid-cols-1">
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
        <div className="rounded-xl border border-[hsl(var(--border))] p-4">
          <p className="text-xs text-muted-foreground">Environment</p>
          <p className="text-lg font-bold mt-1">
            {execSettings.SandBoxUsed === "Yes" ? "Sandbox" : "Production"}
          </p>
          <p className="text-xs text-muted-foreground mt-0.5">
            {envLabel(execSettings.Env2Con || "Tst")}
          </p>
        </div>
      </div>

      {/* Config diff */}
      {changes.length > 0 && (
        <div>
          <h3 className="text-sm font-semibold mb-2 flex items-center gap-2">
            <Info className="w-4 h-4 text-[hsl(var(--primary))]" />
            Changes from Current Configuration ({changes.length})
          </h3>
          <div className="border border-[hsl(var(--border))] rounded-xl overflow-hidden">
            <table className="w-full text-xs">
              <thead>
                <tr className="bg-[hsl(var(--muted)/0.3)]">
                  <th className="text-left px-3 py-2 font-medium text-muted-foreground">Field</th>
                  <th className="text-left px-3 py-2 font-medium text-muted-foreground">Previous</th>
                  <th className="text-left px-3 py-2 font-medium text-muted-foreground">New</th>
                </tr>
              </thead>
              <tbody>
                {changes.slice(0, 20).map((c) => (
                  <tr key={c.key} className="border-t border-[hsl(var(--border))]">
                    <td className="px-3 py-1.5 font-mono">{c.key}</td>
                    <td className="px-3 py-1.5 text-muted-foreground">{c.from}</td>
                    <td className="px-3 py-1.5 text-[hsl(var(--success))] font-medium">{c.to}</td>
                  </tr>
                ))}
              </tbody>
            </table>
            {changes.length > 20 && (
              <p className="text-xs text-muted-foreground px-3 py-2">
                ...and {changes.length - 20} more changes
              </p>
            )}
          </div>
        </div>
      )}

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

      {/* Execution settings summary */}
      <div>
        <h3 className="text-sm font-semibold mb-2">Execution Settings</h3>
        <div className="grid grid-cols-2 gap-x-6 gap-y-1 text-sm max-sm:grid-cols-1">
          <ReviewRow label="Stop on Error" value={stopLabel(execSettings.StopSchedTr)} />
          <ReviewRow label="Extended Timeout" value={execSettings.LongTimeOut === "Yes" ? "Yes" : "No"} />
          <ReviewRow label="Sleep Window" value={
            execSettings.SleepStart && execSettings.SleepEnd
              ? `${execSettings.SleepStart} – ${execSettings.SleepEnd}`
              : "Not set"
          } />
          <ReviewRow label="Email Notifications" value={emailLabel(execSettings.EmlNtf)} />
          <ReviewRow label="Time Zone Shift" value={execSettings.TimeZone || "0"} />
          <ReviewRow label="Restore on Failure" value={execSettings.ConFailState === "Yes" ? "Yes" : "No"} />
        </div>
      </div>
    </div>
  );
}

function ReviewRow({ label, value }: { label: string; value: string }) {
  return (
    <div className="flex justify-between py-1 border-b border-[hsl(var(--border)/0.5)]">
      <span className="text-muted-foreground">{label}</span>
      <span className="font-medium">{value}</span>
    </div>
  );
}
