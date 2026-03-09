import { useState, useCallback, useEffect, useRef, Fragment } from "react";
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
  SlidersHorizontal,
  CheckCircle2,
  XCircle,
  Layers,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { envLabel, stopLabel, emailLabel } from "@/lib/config-labels";
import {
  OBJECT_DETAIL_SCHEMAS,
  resolveTemplate,
  countConfiguredDetails,
  CONFIG_KEY_LABELS,
  categorizeKey,
  REVIEW_CATEGORY_LABELS,
  syncDirectionLabel,
  type ObjectDetailSchema,
  type DetailField,
  type ReviewCategory,
} from "@/lib/object-detail-schema";
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
import type { SyncDirection, SyncMapping, MappingCategory, SolutionMeta } from "@/types/configuration";

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

const EXEC_DEFAULTS: Record<string, string> = {
  SandBoxUsed: "No", Env2Con: "Tst", QBVersion: "USA", QBLocation: "DEFAULT",
  StopSchedTr: "None", SleepStart: "", SleepEnd: "", TimeZone: "0",
  EmlNtf: "None", UseAdmEml: "No", CCEmail: "", BCCEmail: "",
  LongTimeOut: "No", ConFailState: "No",
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

  const [step, setStep] = useState(0);
  const [solutionType, setSolutionType] = useState<string>("");
  const [syncValues, setSyncValues] = useState<Record<string, string>>({});
  const [execSettings, setExecSettings] = useState<Record<string, string>>({ ...EXEC_DEFAULTS });
  const [initialized, setInitialized] = useState(false);
  const initialSnapshot = useRef<Record<string, string>>({});

  const [credForm, setCredForm] = useState<CredFormType>({
    sourceUsername: "", sourcePassword: "", sourceUrl: "", sourceToken: "",
    destUsername: "", destPassword: "", destCompanyFile: "",
  });

  // Initialize from server data
  useEffect(() => {
    if (initialized || !wizardData?.data || !credData?.data) return;
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
    const serverMappings = wizardData.data.syncMappings || {};
    const syncOnly: Record<string, string> = {};
    const execOnly: Record<string, string> = { ...EXEC_DEFAULTS };
    for (const [k, v] of Object.entries(serverMappings)) {
      if (EXEC_KEYS.has(k)) execOnly[k] = v;
      else syncOnly[k] = v;
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

  // Draft persistence
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
      const allMappings: Record<string, string> = { ...syncValues };
      for (const [k, v] of Object.entries(execSettings)) {
        if (v !== "") allMappings[k] = v;
      }
      await saveWizard.mutateAsync({ solutionType, syncMappings: allMappings });
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
            <StepExecutionSettings meta={meta} settings={execSettings} onUpdate={updateExec} />
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
          <Button variant="outline" onClick={() => setStep((s) => Math.max(0, s - 1))} disabled={step === 0}>
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
              {saveWizard.isPending ? <RefreshCw className="w-4 h-4 animate-spin" /> : <Save className="w-4 h-4" />}
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

function StepSolutionType({ value, onChange }: { value: string; onChange: (v: string) => void }) {
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

// ─── Step 2: Object Mapping (with Detail Panels) ─────────────────────

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
  meta: SolutionMeta;
  syncValues: Record<string, string>;
  onUpdate: (key: string, value: string) => void;
  onBulkUpdate: (values: Record<string, string>) => void;
}) {
  const [showExtended, setShowExtended] = useState(false);
  const [expandedObjects, setExpandedObjects] = useState<Set<string>>(new Set());

  const coreMappings = mappings.filter((m) => m.tier === "core");
  const extendedMappings = mappings.filter((m) => m.tier === "extended");

  const extendedByCategory = extendedMappings.reduce<Record<MappingCategory, SyncMapping[]>>(
    (acc, m) => { (acc[m.category] ??= []).push(m); return acc; },
    {} as Record<MappingCategory, SyncMapping[]>
  );

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

  const toggleExpanded = (key: string) => {
    setExpandedObjects((prev) => {
      const next = new Set(prev);
      if (next.has(key)) next.delete(key); else next.add(key);
      return next;
    });
  };

  return (
    <div className="space-y-4">
      <div className="flex items-start justify-between gap-4 flex-wrap">
        <div>
          <h2 className="text-lg font-semibold">Object Mapping</h2>
          <p className="text-sm text-muted-foreground mt-1">
            Configure sync direction and field-level properties for each data object between{" "}
            <span className="font-medium text-foreground">{meta.crmName}</span> and{" "}
            <span className="font-medium text-foreground">{meta.fsName}</span>.
          </p>
        </div>
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
      <MappingSection
        label="Core Mappings"
        mappings={coreMappings}
        meta={meta}
        syncValues={syncValues}
        onUpdate={onUpdate}
        expandedObjects={expandedObjects}
        onToggleExpanded={toggleExpanded}
      />

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
                <MappingSection
                  key={cat}
                  label={CATEGORY_LABELS[cat]}
                  mappings={catMappings}
                  meta={meta}
                  syncValues={syncValues}
                  onUpdate={onUpdate}
                  expandedObjects={expandedObjects}
                  onToggleExpanded={toggleExpanded}
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

/** A section of mappings with expandable detail panels per row */
function MappingSection({
  label,
  mappings,
  meta,
  syncValues,
  onUpdate,
  expandedObjects,
  onToggleExpanded,
}: {
  label: string;
  mappings: SyncMapping[];
  meta: SolutionMeta;
  syncValues: Record<string, string>;
  onUpdate: (key: string, value: string) => void;
  expandedObjects: Set<string>;
  onToggleExpanded: (key: string) => void;
}) {
  return (
    <div>
      <p className="text-xs font-semibold text-muted-foreground uppercase tracking-wider mb-2">{label}</p>
      <div className="border border-[hsl(var(--border))] rounded-xl overflow-hidden">
        {/* Desktop */}
        <div className="max-sm:hidden">
          <table className="w-full text-sm">
            <thead>
              <tr className="bg-[hsl(var(--muted)/0.3)]">
                <th className="text-left px-4 py-2.5 text-xs text-muted-foreground font-medium">Object Mapping</th>
                <th className="text-left px-4 py-2.5 text-xs text-muted-foreground font-medium w-52">Sync Direction</th>
                <th className="text-center px-2 py-2.5 text-xs text-muted-foreground font-medium w-28">Details</th>
              </tr>
            </thead>
            <tbody>
              {mappings.map((m) => {
                const direction = syncValues[m.key] || m.value;
                const isActive = direction !== "None";
                const hasSchema = !!OBJECT_DETAIL_SCHEMAS[m.key];
                const isExpanded = expandedObjects.has(m.key);
                const detailCount = isActive ? countConfiguredDetails(m.key, syncValues) : 0;
                return (
                  <Fragment key={m.key}>
                    <tr className={cn(
                      "border-t border-[hsl(var(--border))]",
                      isActive && "bg-[hsl(var(--success)/0.02)]"
                    )}>
                      <td className="px-4 py-2.5">
                        <div className="flex items-center gap-2">
                          {isActive && <div className="w-1.5 h-1.5 rounded-full bg-[hsl(var(--success))]" />}
                          <p className="text-sm">{m.label}</p>
                        </div>
                      </td>
                      <td className="px-4 py-2.5">
                        <Select value={direction} onValueChange={(v) => onUpdate(m.key, v)}>
                          <SelectTrigger className={cn(isActive && "border-[hsl(var(--success)/0.4)]")}>
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
                      <td className="px-2 py-2.5 text-center">
                        {isActive && hasSchema ? (
                          <button
                            onClick={() => onToggleExpanded(m.key)}
                            className={cn(
                              "inline-flex items-center gap-1.5 px-2.5 py-1.5 rounded-lg text-xs font-medium transition-all cursor-pointer",
                              isExpanded
                                ? "bg-[hsl(var(--primary)/0.15)] text-[hsl(var(--primary))] border border-[hsl(var(--primary)/0.3)]"
                                : "text-muted-foreground hover:text-foreground hover:bg-[hsl(var(--muted)/0.3)] border border-transparent"
                            )}
                          >
                            <SlidersHorizontal className="w-3 h-3" />
                            {isExpanded ? "Close" : "Configure"}
                            {detailCount > 0 && (
                              <span className="ml-0.5 w-4 h-4 rounded-full bg-[hsl(var(--primary)/0.2)] text-[hsl(var(--primary))] grid place-items-center text-[9px] font-bold">
                                {detailCount}
                              </span>
                            )}
                          </button>
                        ) : isActive ? (
                          <span className="text-xs text-muted-foreground">—</span>
                        ) : null}
                      </td>
                    </tr>
                    {/* Expanded detail panel */}
                    {isExpanded && isActive && hasSchema && (
                      <tr>
                        <td colSpan={3} className="p-0">
                          <ObjectDetailPanel
                            schema={OBJECT_DETAIL_SCHEMAS[m.key]!}
                            meta={meta}
                            values={syncValues}
                            direction={direction as SyncDirection}
                            onUpdate={onUpdate}
                          />
                        </td>
                      </tr>
                    )}
                  </Fragment>
                );
              })}
            </tbody>
          </table>
        </div>
        {/* Mobile */}
        <div className="sm:hidden divide-y divide-[hsl(var(--border))]">
          {mappings.map((m) => {
            const direction = syncValues[m.key] || m.value;
            const isActive = direction !== "None";
            const hasSchema = !!OBJECT_DETAIL_SCHEMAS[m.key];
            const isExpanded = expandedObjects.has(m.key);
            return (
              <div key={m.key} className={cn("p-3 space-y-2", isActive && "bg-[hsl(var(--success)/0.02)]")}>
                <div className="flex items-center justify-between">
                  <p className="text-sm font-medium">{m.label}</p>
                  {isActive && hasSchema && (
                    <button
                      onClick={() => onToggleExpanded(m.key)}
                      className="text-xs text-[hsl(var(--primary))] font-medium cursor-pointer"
                    >
                      {isExpanded ? "Close" : "Configure"}
                    </button>
                  )}
                </div>
                <Select value={direction} onValueChange={(v) => onUpdate(m.key, v)}>
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
                {isExpanded && isActive && hasSchema && (
                  <ObjectDetailPanel
                    schema={OBJECT_DETAIL_SCHEMAS[m.key]!}
                    meta={meta}
                    values={syncValues}
                    direction={direction as SyncDirection}
                    onUpdate={onUpdate}
                  />
                )}
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
}

// ─── Object Detail Panel ─────────────────────────────────────────────

function ObjectDetailPanel({
  schema,
  meta,
  values,
  direction,
  onUpdate,
}: {
  schema: ObjectDetailSchema;
  meta: SolutionMeta;
  values: Record<string, string>;
  direction: SyncDirection;
  onUpdate: (key: string, value: string) => void;
}) {
  const r = (t: string) => resolveTemplate(t, meta);

  const isGroupVisible = (group: typeof schema.groups[0]): boolean => {
    if (!group.showForDirections) return true;
    return group.showForDirections.includes(direction);
  };

  const isFieldVisible = (field: DetailField): boolean => {
    if (field.showForDirections && !field.showForDirections.includes(direction)) return false;
    if (field.showWhen) {
      const depVal = values[field.showWhen.key] || "";
      if (!field.showWhen.values.includes(depVal)) return false;
    }
    return true;
  };

  const visibleGroups = schema.groups.filter(isGroupVisible);
  if (visibleGroups.length === 0) return null;

  return (
    <div className="bg-[hsl(var(--muted)/0.15)] border-t border-[hsl(var(--primary)/0.15)]">
      <div className="px-5 py-4 space-y-5">
        <div className="flex items-center gap-2 text-xs">
          <Layers className="w-3.5 h-3.5 text-[hsl(var(--primary))]" />
          <span className="font-semibold text-[hsl(var(--primary))]">
            {r(schema.sectionLabel)}
          </span>
        </div>

        {visibleGroups.map((group, gi) => {
          const visibleFields = group.fields.filter(isFieldVisible);
          if (visibleFields.length === 0) return null;

          return (
            <div key={gi} className="space-y-3">
              <div className="flex items-center gap-2">
                <p className="text-xs font-semibold text-muted-foreground">{r(group.label)}</p>
                {group.helpText && (
                  <Tooltip>
                    <TooltipTrigger asChild>
                      <HelpCircle className="w-3 h-3 text-muted-foreground cursor-help" />
                    </TooltipTrigger>
                    <TooltipContent side="top" className="max-w-64">
                      <p>{r(group.helpText)}</p>
                    </TooltipContent>
                  </Tooltip>
                )}
              </div>
              <div className="grid grid-cols-2 gap-x-4 gap-y-3 max-sm:grid-cols-1">
                {visibleFields.map((field) => (
                  <DetailFieldInput
                    key={field.key}
                    field={field}
                    value={values[field.key] ?? ""}
                    meta={meta}
                    onUpdate={onUpdate}
                  />
                ))}
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}

function DetailFieldInput({
  field,
  value,
  meta,
  onUpdate,
}: {
  field: DetailField;
  value: string;
  meta: SolutionMeta;
  onUpdate: (key: string, value: string) => void;
}) {
  const r = (t: string) => resolveTemplate(t, meta);
  const effectiveValue = value || field.defaultValue;

  return (
    <div className="space-y-1">
      <div className="flex items-center gap-1.5">
        <Label className="text-xs">{r(field.label)}</Label>
        {field.helpText && (
          <Tooltip>
            <TooltipTrigger asChild>
              <HelpCircle className="w-3 h-3 text-muted-foreground cursor-help" />
            </TooltipTrigger>
            <TooltipContent side="top" className="max-w-64">
              <p>{r(field.helpText)}</p>
            </TooltipContent>
          </Tooltip>
        )}
      </div>
      {field.type === "select" && field.options ? (
        <Select value={effectiveValue} onValueChange={(v) => onUpdate(field.key, v)}>
          <SelectTrigger className="h-8 text-xs">
            <SelectValue />
          </SelectTrigger>
          <SelectContent>
            {field.options.map((opt) => (
              <SelectItem key={opt.value} value={opt.value}>
                {r(opt.label)}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
      ) : (
        <Input
          className="h-8 text-xs"
          value={value}
          onChange={(e) => onUpdate(field.key, e.target.value)}
          placeholder={field.placeholder ? r(field.placeholder) : undefined}
          maxLength={field.maxLength}
        />
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
  meta: SolutionMeta;
  form: CredFormType;
  onChange: (f: CredFormType) => void;
  existingCreds: Array<{ credentialType: string; username: string; endpointUrl: string; isActive: boolean }>;
}) {
  const testCredential = useTestCredential();
  const [testResult, setTestResult] = useState<{ type: string; reachable: boolean; message: string } | null>(null);

  const update = (field: string, value: string) => onChange({ ...form, [field]: value });

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

      {existingCreds.length > 0 && (
        <div className="flex items-center gap-2 px-3 py-2 rounded-lg border border-[hsl(var(--success)/0.3)] bg-[hsl(var(--success)/0.05)]">
          <Badge variant="success">
            <Check className="w-3 h-3" />
            {existingCreds.length} configured
          </Badge>
          <span className="text-xs text-muted-foreground">Fill in fields below to update.</span>
        </div>
      )}

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
          <Button variant="outline" size="sm" onClick={() => void handleTestConnection("source")} disabled={testCredential.isPending}>
            {testCredential.isPending ? <Loader2 className="w-3 h-3 animate-spin" /> : <Wifi className="w-3 h-3" />}
            Test Connection
          </Button>
        )}
      </fieldset>

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
  meta: SolutionMeta;
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
        </p>
      </div>

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
              <FieldTooltip text="Determines which InterWeave server pool processes your transactions." />
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
              <FieldTooltip text="Offset in hours from the server timezone. Use when business system timestamps differ." />
            </div>
            <Input type="number" min={-12} max={14} value={settings.TimeZone || "0"} onChange={(e) => onUpdate("TimeZone", e.target.value)} placeholder="0" />
          </div>
          {!isQB && <div />}
        </div>
      </fieldset>

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
              <FieldTooltip text="Increases API timeout from 30s to 120s. Enable for slow endpoints." />
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
            <Input value={settings.SleepStart || ""} onChange={(e) => onUpdate("SleepStart", e.target.value)} placeholder="e.g. 22:00" />
            <p className="text-[10px] text-muted-foreground">No integrations during sleep window</p>
          </div>
          <div className="space-y-1.5">
            <Label>Sleep Window End</Label>
            <Input value={settings.SleepEnd || ""} onChange={(e) => onUpdate("SleepEnd", e.target.value)} placeholder="e.g. 06:00" />
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
              <Input value={settings.CCEmail || ""} onChange={(e) => onUpdate("CCEmail", e.target.value)} placeholder="email@example.com" />
            </div>
            <div className="space-y-1.5">
              <Label><Mail className="w-3 h-3 inline mr-1" />BCC Email Addresses</Label>
              <Input value={settings.BCCEmail || ""} onChange={(e) => onUpdate("BCCEmail", e.target.value)} placeholder="email@example.com" />
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

// ─── Step 5: Review & Save (Improved) ────────────────────────────────

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
  meta: SolutionMeta;
  syncValues: Record<string, string>;
  configuredCount: number;
  mappings: SyncMapping[];
  credForm: CredFormType;
  execSettings: Record<string, string>;
  initialSnapshot: Record<string, string>;
}) {
  const [expandedSections, setExpandedSections] = useState<Set<string>>(new Set(["mappings"]));
  const activeMappings = mappings.filter((m) => syncValues[m.key] && syncValues[m.key] !== "None");

  const toggleSection = (id: string) => {
    setExpandedSections((prev) => {
      const next = new Set(prev);
      if (next.has(id)) next.delete(id); else next.add(id);
      return next;
    });
  };

  // Compute changes grouped by category
  const allCurrent = { ...syncValues, ...execSettings };
  const changes: { key: string; from: string; to: string; category: ReviewCategory }[] = [];
  for (const [k, v] of Object.entries(allCurrent)) {
    const prev = initialSnapshot[k] || "";
    if (v !== prev && !(v === "None" && prev === "")) {
      changes.push({ key: k, from: prev || "(empty)", to: v, category: categorizeKey(k) });
    }
  }
  for (const [k, v] of Object.entries(initialSnapshot)) {
    if (!(k in allCurrent) && v && v !== "None") {
      changes.push({ key: k, from: v, to: "(removed)", category: categorizeKey(k) });
    }
  }

  const changesByCategory = changes.reduce<Record<ReviewCategory, typeof changes>>((acc, c) => {
    (acc[c.category] ??= []).push(c);
    return acc;
  }, {} as Record<ReviewCategory, typeof changes>);

  // Count detail properties across all objects
  const totalDetailProps = activeMappings.reduce(
    (sum, m) => sum + countConfiguredDetails(m.key, syncValues),
    0,
  );

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
            Review your settings before saving. Click any section to expand details.
          </p>
        </div>
        <Button variant="outline" size="sm" onClick={handleExport}>
          <Download className="w-3 h-3" /> Export JSON
        </Button>
      </div>

      {/* Summary cards */}
      <div className="grid grid-cols-5 gap-3 max-lg:grid-cols-3 max-sm:grid-cols-2">
        <SummaryCard
          label="Solution"
          value={solutionType || "—"}
          sub={`${meta.crmName} ↔ ${meta.fsName}`}
        />
        <SummaryCard
          label="Active Mappings"
          value={`${configuredCount}`}
          sub={`of ${mappings.length} available`}
        />
        <SummaryCard
          label="Detail Properties"
          value={`${totalDetailProps}`}
          sub="configured"
        />
        <SummaryCard
          label="Credentials"
          value={`${(credForm.sourceUsername ? 1 : 0) + (credForm.destUsername ? 1 : 0)} / 2`}
          sub="systems"
        />
        <SummaryCard
          label="Environment"
          value={execSettings.SandBoxUsed === "Yes" ? "Sandbox" : "Production"}
          sub={envLabel(execSettings.Env2Con || "Tst")}
        />
      </div>

      {/* Active mappings section */}
      <ReviewSection
        id="mappings"
        title="Active Sync Mappings"
        count={activeMappings.length}
        icon={<Database className="w-4 h-4" />}
        expanded={expandedSections.has("mappings")}
        onToggle={() => toggleSection("mappings")}
        status={activeMappings.length > 0 ? "success" : "warning"}
      >
        {activeMappings.length > 0 ? (
          <div className="space-y-2">
            {activeMappings.map((m) => {
              const dir = syncValues[m.key];
              const detailCount = countConfiguredDetails(m.key, syncValues);
              return (
                <div key={m.key} className="flex items-center justify-between px-3 py-2 rounded-lg bg-[hsl(var(--muted)/0.15)] border border-[hsl(var(--border)/0.5)]">
                  <div className="flex items-center gap-2">
                    <CheckCircle2 className="w-3.5 h-3.5 text-[hsl(var(--success))]" />
                    <span className="text-sm">{m.label}</span>
                    {detailCount > 0 && (
                      <Badge variant="outline" className="text-[10px] h-5">
                        {detailCount} detail{detailCount !== 1 ? "s" : ""}
                      </Badge>
                    )}
                  </div>
                  <Badge variant="success">
                    {syncDirectionLabel(dir ?? "", meta)}
                  </Badge>
                </div>
              );
            })}
          </div>
        ) : (
          <div className="flex items-center gap-2 text-xs text-[hsl(var(--warning))]">
            <AlertTriangle className="w-3.5 h-3.5" />
            No sync mappings configured. Go back to Step 2 to select at least one mapping.
          </div>
        )}
      </ReviewSection>

      {/* Credentials section */}
      <ReviewSection
        id="credentials"
        title="System Credentials"
        icon={<Key className="w-4 h-4" />}
        expanded={expandedSections.has("credentials")}
        onToggle={() => toggleSection("credentials")}
        status={(credForm.sourceUsername || credForm.destUsername) ? "success" : "neutral"}
      >
        <div className="grid grid-cols-2 gap-4 max-sm:grid-cols-1">
          <div className="space-y-1.5">
            <p className="text-xs font-semibold text-muted-foreground">{meta.crmName} (Source)</p>
            <ReviewRow label="Username" value={credForm.sourceUsername || "Not set"} />
            <ReviewRow label="Password" value={credForm.sourcePassword ? "••••••••" : "Unchanged"} />
            <ReviewRow label="Endpoint" value={credForm.sourceUrl || "Not set"} />
            {meta.crmName === "SF" && (
              <ReviewRow label="Security Token" value={credForm.sourceToken ? "••••••••" : "Unchanged"} />
            )}
          </div>
          <div className="space-y-1.5">
            <p className="text-xs font-semibold text-muted-foreground">{meta.fsName} (Destination)</p>
            <ReviewRow label="Username" value={credForm.destUsername || "Not set"} />
            <ReviewRow label="Password" value={credForm.destPassword ? "••••••••" : "Unchanged"} />
            {meta.fsName === "QB" && (
              <ReviewRow label="Company File" value={credForm.destCompanyFile || "Not set"} />
            )}
          </div>
        </div>
      </ReviewSection>

      {/* Execution settings section */}
      <ReviewSection
        id="execution"
        title="Execution Settings"
        icon={<Settings2 className="w-4 h-4" />}
        expanded={expandedSections.has("execution")}
        onToggle={() => toggleSection("execution")}
        status="neutral"
      >
        <div className="grid grid-cols-2 gap-x-6 gap-y-1 text-sm max-sm:grid-cols-1">
          <ReviewRow label="Environment" value={envLabel(execSettings.Env2Con || "Tst")} />
          <ReviewRow label="Sandbox Mode" value={execSettings.SandBoxUsed === "Yes" ? "Yes" : "No"} />
          <ReviewRow label="Stop on Error" value={stopLabel(execSettings.StopSchedTr)} />
          <ReviewRow label="Extended Timeout" value={execSettings.LongTimeOut === "Yes" ? "Yes" : "No"} />
          <ReviewRow label="Sleep Window" value={
            execSettings.SleepStart && execSettings.SleepEnd
              ? `${execSettings.SleepStart} – ${execSettings.SleepEnd}`
              : "Not set"
          } />
          <ReviewRow label="Email Notifications" value={emailLabel(execSettings.EmlNtf)} />
          <ReviewRow label="Time Zone Shift" value={`${execSettings.TimeZone || "0"} hrs`} />
          <ReviewRow label="Restore on Failure" value={execSettings.ConFailState === "Yes" ? "Yes" : "No"} />
          {execSettings.CCEmail && <ReviewRow label="CC Email" value={execSettings.CCEmail} />}
          {execSettings.BCCEmail && <ReviewRow label="BCC Email" value={execSettings.BCCEmail} />}
        </div>
      </ReviewSection>

      {/* Changes diff section */}
      {changes.length > 0 && (
        <ReviewSection
          id="changes"
          title={`Changes from Saved Configuration (${changes.length})`}
          icon={<Info className="w-4 h-4" />}
          expanded={expandedSections.has("changes")}
          onToggle={() => toggleSection("changes")}
          status="info"
        >
          <div className="space-y-4">
            {(Object.entries(changesByCategory) as [ReviewCategory, typeof changes][]).map(([cat, catChanges]) => (
              <div key={cat}>
                <p className="text-xs font-semibold text-muted-foreground uppercase tracking-wider mb-1.5">
                  {REVIEW_CATEGORY_LABELS[cat]}
                </p>
                <div className="border border-[hsl(var(--border))] rounded-lg overflow-hidden">
                  <table className="w-full text-xs">
                    <thead>
                      <tr className="bg-[hsl(var(--muted)/0.3)]">
                        <th className="text-left px-3 py-1.5 font-medium text-muted-foreground">Setting</th>
                        <th className="text-left px-3 py-1.5 font-medium text-muted-foreground">Previous</th>
                        <th className="text-left px-3 py-1.5 font-medium text-muted-foreground">New</th>
                      </tr>
                    </thead>
                    <tbody>
                      {catChanges.map((c) => (
                        <tr key={c.key} className="border-t border-[hsl(var(--border))]">
                          <td className="px-3 py-1.5">
                            <span className="font-medium">{CONFIG_KEY_LABELS[c.key] || c.key}</span>
                            <span className="text-muted-foreground ml-1 text-[10px] font-mono">({c.key})</span>
                          </td>
                          <td className="px-3 py-1.5">
                            <span className="text-muted-foreground flex items-center gap-1">
                              <XCircle className="w-3 h-3 text-[hsl(var(--destructive)/0.5)]" />
                              {c.from}
                            </span>
                          </td>
                          <td className="px-3 py-1.5">
                            <span className="text-[hsl(var(--success))] font-medium flex items-center gap-1">
                              <CheckCircle2 className="w-3 h-3" />
                              {c.to}
                            </span>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>
            ))}
          </div>
        </ReviewSection>
      )}
    </div>
  );
}

function SummaryCard({ label, value, sub }: { label: string; value: string; sub: string }) {
  return (
    <div className="rounded-xl border border-[hsl(var(--border))] p-3">
      <p className="text-[10px] text-muted-foreground uppercase tracking-wider">{label}</p>
      <p className="text-lg font-bold mt-0.5 leading-tight">{value}</p>
      <p className="text-[10px] text-muted-foreground mt-0.5">{sub}</p>
    </div>
  );
}

function ReviewSection({
  id: _id,
  title,
  count,
  icon,
  expanded,
  onToggle,
  status,
  children,
}: {
  id: string;
  title: string;
  count?: number;
  icon: React.ReactNode;
  expanded: boolean;
  onToggle: () => void;
  status: "success" | "warning" | "info" | "neutral";
  children: React.ReactNode;
}) {
  const borderColor = {
    success: "border-[hsl(var(--success)/0.3)]",
    warning: "border-[hsl(var(--warning)/0.3)]",
    info: "border-[hsl(var(--primary)/0.3)]",
    neutral: "border-[hsl(var(--border))]",
  }[status];

  const iconColor = {
    success: "text-[hsl(var(--success))]",
    warning: "text-[hsl(var(--warning))]",
    info: "text-[hsl(var(--primary))]",
    neutral: "text-muted-foreground",
  }[status];

  return (
    <div className={cn("rounded-xl border", borderColor)}>
      <button
        onClick={onToggle}
        className="w-full flex items-center justify-between px-4 py-3 cursor-pointer hover:bg-[hsl(var(--muted)/0.1)] transition-colors rounded-xl"
      >
        <div className="flex items-center gap-2.5">
          <span className={iconColor}>{icon}</span>
          <span className="text-sm font-semibold">{title}</span>
          {count !== undefined && (
            <Badge variant="outline" className="text-[10px] h-5">{count}</Badge>
          )}
        </div>
        <ChevronDown className={cn("w-4 h-4 text-muted-foreground transition-transform", expanded && "rotate-180")} />
      </button>
      {expanded && (
        <div className="px-4 pb-4 pt-1">
          {children}
        </div>
      )}
    </div>
  );
}

function ReviewRow({ label, value }: { label: string; value: string }) {
  return (
    <div className="flex justify-between py-1 border-b border-[hsl(var(--border)/0.5)]">
      <span className="text-muted-foreground text-xs">{label}</span>
      <span className="font-medium text-xs">{value}</span>
    </div>
  );
}
