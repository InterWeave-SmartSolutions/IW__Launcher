import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import {
  ArrowLeft,
  Globe,
  Loader2,
  Save,
  Info,
} from "lucide-react";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { useToast } from "@/providers/ToastProvider";
import { useAuth } from "@/providers/AuthProvider";
import { useWizardConfig, useSaveWizardConfig } from "@/hooks/useConfiguration";
import { useCompanyProfile } from "@/hooks/useProfile";
import {
  buildSyncMappings,
  deriveSolutionMeta,
} from "@/types/configuration";
import type { SyncDirection, SyncMapping } from "@/types/configuration";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { ObjectPair } from "@/components/mappings/ObjectPair";
import { FieldMappingPanel } from "@/components/mappings/FieldMappingPanel";
import { lookupSchema } from "@/lib/production-schemas";

export function ObjectMappingPage() {
  useDocumentTitle("Object Mapping");
  const { user } = useAuth();
  const { showToast } = useToast();
  const { data: wizardData, isLoading: wizLoading } = useWizardConfig();
  const { data: companyData, isLoading: compLoading } = useCompanyProfile();
  const saveWizard = useSaveWizardConfig();

  const isAdmin = user?.isAdmin === true;
  const isLoading = wizLoading || compLoading;

  const serverSolution = wizardData?.data?.solutionType || companyData?.company?.solutionType || "";
  const serverSyncMappings = wizardData?.data?.syncMappings || {};
  const version = wizardData?.data?.version;

  // Local state for mapping direction edits
  const [localValues, setLocalValues] = useState<Record<string, string>>({});
  const [dirty, setDirty] = useState(false);

  // Initialize local values from server data
  useEffect(() => {
    if (serverSyncMappings && Object.keys(serverSyncMappings).length > 0) {
      setLocalValues(serverSyncMappings);
      setDirty(false);
    }
  }, [wizardData]); // eslint-disable-line react-hooks/exhaustive-deps

  const meta = serverSolution ? deriveSolutionMeta(serverSolution) : null;
  const mappings: SyncMapping[] = serverSolution
    ? buildSyncMappings(serverSolution, localValues)
    : [];

  const coreMappings = mappings.filter((m) => m.tier === "core");
  const extendedMappings = mappings.filter((m) => m.tier === "extended");
  const activeCount = mappings.filter((m) => m.value !== "None").length;

  const handleDirectionChange = (key: string, direction: SyncDirection) => {
    setLocalValues((prev) => ({ ...prev, [key]: direction }));
    setDirty(true);
  };

  const handleSave = async () => {
    if (!serverSolution) return;
    try {
      await saveWizard.mutateAsync({
        solutionType: serverSolution,
        syncMappings: localValues,
        version,
      });
      showToast("Object mappings saved", "success");
      setDirty(false);
    } catch (err) {
      showToast(err instanceof Error ? err.message : "Save failed", "error");
    }
  };

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <Loader2 className="w-8 h-8 animate-spin text-muted-foreground" />
      </div>
    );
  }

  if (!serverSolution || serverSolution === "Not Selected") {
    return (
      <div className="space-y-6 max-w-4xl">
        <div className="flex items-center gap-2 text-sm text-muted-foreground">
          <Link to="/company" className="hover:text-foreground transition-colors">
            <ArrowLeft className="w-4 h-4 inline mr-1" />
            Company
          </Link>
          <span>/</span>
          <span className="text-foreground">Object Mapping</span>
        </div>

        <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-12 text-center">
          <Globe className="w-12 h-12 text-muted-foreground mx-auto mb-4" />
          <h3 className="text-lg font-medium mb-1">No Solution Type Selected</h3>
          <p className="text-sm text-muted-foreground mb-4 max-w-md mx-auto">
            Select a solution type in the configuration wizard before setting up object mappings.
          </p>
          <Button asChild>
            <Link to="/company/config/wizard">Open Wizard</Link>
          </Button>
        </div>
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
        <span className="text-foreground">Object Mapping</span>
      </div>

      {/* Header */}
      <div className="flex items-start justify-between gap-4 flex-wrap">
        <div>
          <h1 className="text-2xl font-semibold">Object Mapping</h1>
          <p className="text-sm text-muted-foreground mt-1">
            Map data objects between{" "}
            <span className="font-medium text-foreground">{meta?.crmName}</span>
            {" and "}
            <span className="font-medium text-foreground">{meta?.fsName}</span>.
            Expand each mapping to view field-level detail.
          </p>
        </div>
        <div className="flex items-center gap-2">
          <Badge variant="secondary">{activeCount}/{mappings.length} active</Badge>
          {dirty && (
            <Button size="sm" onClick={() => void handleSave()} disabled={saveWizard.isPending}>
              {saveWizard.isPending ? (
                <Loader2 className="w-3.5 h-3.5 animate-spin" />
              ) : (
                <Save className="w-3.5 h-3.5" />
              )}
              Save Changes
            </Button>
          )}
        </div>
      </div>

      {/* Info banner */}
      <div className="flex items-start gap-3 p-3 rounded-xl bg-[hsl(var(--primary)/0.05)] border border-[hsl(var(--primary)/0.15)] text-sm">
        <Info className="w-4 h-4 text-[hsl(var(--primary))] mt-0.5 shrink-0" />
        <div>
          <p className="text-xs text-muted-foreground">
            Each row maps a source object type to a destination object type. Set the sync direction,
            then expand to see AI-suggested field-level mappings. Where available, field schemas
            are sourced from production XSLT transformers.
          </p>
        </div>
      </div>

      {/* Core Mappings */}
      <div>
        <h2 className="text-sm font-semibold text-muted-foreground uppercase tracking-wider mb-3">
          Core Mappings ({coreMappings.length})
        </h2>
        <div className="space-y-2">
          {coreMappings.map((m) => (
            <ObjectPair
              key={m.key}
              mapping={m}
              onDirectionChange={handleDirectionChange}
              disabled={!isAdmin}
              renderFieldPanel={() => {
                const schema = serverSolution ? lookupSchema(serverSolution, m.key) : null;
                return (
                  <FieldMappingPanel
                    sourceFields={schema?.sourceFields}
                    targetFields={schema?.targetFields}
                    sourceLabel={schema?.sourceLabel ?? meta?.crmName}
                    targetLabel={schema?.targetLabel ?? meta?.fsName}
                  />
                );
              }}
            />
          ))}
        </div>
      </div>

      {/* Extended Mappings */}
      {extendedMappings.length > 0 && (
        <div>
          <h2 className="text-sm font-semibold text-muted-foreground uppercase tracking-wider mb-3">
            Extended Mappings ({extendedMappings.length})
          </h2>
          <div className="space-y-2">
            {extendedMappings.map((m) => (
              <ObjectPair
                key={m.key}
                mapping={m}
                onDirectionChange={handleDirectionChange}
                disabled={!isAdmin}
                renderFieldPanel={() => (
                  <FieldMappingPanel
                    sourceLabel={meta?.crmName}
                    targetLabel={meta?.fsName}
                  />
                )}
              />
            ))}
          </div>
        </div>
      )}
    </div>
  );
}
