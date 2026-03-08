import { useState, useEffect } from "react";
import { Loader2, Save, Eye, EyeOff, Lock } from "lucide-react";
import { useFlowProperties, useSaveFlowProperties } from "@/hooks/useFlows";
import { useToast } from "@/providers/ToastProvider";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter } from "@/components/ui/dialog";

interface FlowPropertiesDialogProps {
  flowId: string | null;
  isFlow: boolean;
  open: boolean;
  onOpenChange: (open: boolean) => void;
}

export function FlowPropertiesDialog({ flowId, isFlow, open, onOpenChange }: FlowPropertiesDialogProps) {
  const { showToast } = useToast();
  const { data: propsRes, isLoading, error } = useFlowProperties(open ? flowId : null, isFlow);
  const saveProps = useSaveFlowProperties();

  const [values, setValues] = useState<Record<string, string>>({});
  const [revealedPasswords, setRevealedPasswords] = useState<Set<string>>(new Set());

  const flowData = propsRes?.data;

  // Sync form values when properties load
  useEffect(() => {
    if (flowData?.properties) {
      const initial: Record<string, string> = {};
      for (const prop of flowData.properties) {
        initial[prop.name] = prop.value;
      }
      setValues(initial);
      setRevealedPasswords(new Set());
    }
  }, [flowData?.flowId]); // eslint-disable-line react-hooks/exhaustive-deps

  const handleSave = async () => {
    if (!flowData) return;
    try {
      await saveProps.mutateAsync({
        flowId: flowData.flowId,
        profileName: flowData.profileName,
        isFlow: flowData.isFlow,
        properties: values,
      });
      showToast(`Properties saved: ${flowData.flowId}`, "success");
      onOpenChange(false);
    } catch (e) {
      showToast(e instanceof Error ? e.message : "Failed to save properties", "error");
    }
  };

  const togglePasswordVisibility = (name: string) => {
    setRevealedPasswords((prev) => {
      const next = new Set(prev);
      if (next.has(name)) next.delete(name);
      else next.add(name);
      return next;
    });
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="max-w-2xl max-h-[85vh] overflow-y-auto">
        <DialogHeader>
          <DialogTitle className="flex items-center gap-2">
            Flow Properties
            {flowId && <Badge variant="default" className="text-xs">{flowId}</Badge>}
          </DialogTitle>
        </DialogHeader>

        {isLoading && (
          <div className="flex justify-center py-8">
            <Loader2 className="w-6 h-6 animate-spin text-muted-foreground" />
          </div>
        )}

        {error && (
          <div className="p-4 rounded-lg bg-[hsl(var(--destructive)/0.1)] text-sm text-[hsl(var(--destructive))]">
            {error instanceof Error ? error.message : "Failed to load properties"}
          </div>
        )}

        {flowData && (
          <div className="space-y-4 py-2">
            {/* Description */}
            {flowData.description && (
              <p className="text-sm text-muted-foreground">{flowData.description}</p>
            )}

            {/* Running warning */}
            {flowData.running && (
              <div className="flex items-center gap-2 p-3 rounded-lg bg-[hsl(var(--warning)/0.1)] text-xs text-[hsl(var(--warning))]">
                <Lock className="w-4 h-4 shrink-0" />
                Properties are read-only while the flow is running. Stop the flow to make changes.
              </div>
            )}

            {/* Properties table */}
            {(flowData.properties?.length ?? 0) > 0 ? (
              <div className="space-y-3">
                <h4 className="text-xs font-semibold text-muted-foreground uppercase tracking-wider">
                  Variable Parameters
                </h4>
                <div className="space-y-2">
                  {(flowData.properties ?? []).filter(p => p.type !== "upload").map((prop) => (
                    <div key={prop.name} className="grid grid-cols-[1fr_2fr] gap-3 items-center">
                      <Label className="text-xs truncate" title={prop.name}>
                        {prop.name}
                      </Label>
                      <div className="flex items-center gap-1">
                        <Input
                          type={prop.type === "password" && !revealedPasswords.has(prop.name) ? "password" : "text"}
                          value={values[prop.name] ?? prop.value}
                          onChange={(e) => setValues((v) => ({ ...v, [prop.name]: e.target.value }))}
                          disabled={flowData.running}
                          className="text-xs h-8"
                        />
                        {prop.type === "password" && (
                          <button
                            type="button"
                            onClick={() => togglePasswordVisibility(prop.name)}
                            className="p-1 rounded hover:bg-muted/50 text-muted-foreground hover:text-foreground transition-colors cursor-pointer"
                            aria-label={revealedPasswords.has(prop.name) ? "Hide" : "Show"}
                          >
                            {revealedPasswords.has(prop.name) ? (
                              <EyeOff className="w-3.5 h-3.5" />
                            ) : (
                              <Eye className="w-3.5 h-3.5" />
                            )}
                          </button>
                        )}
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            ) : (
              <p className="text-sm text-muted-foreground">
                No variable parameters configured for this flow.
              </p>
            )}

            {/* Admin: TS URLs */}
            {flowData.tsUrls && Object.keys(flowData.tsUrls).length > 0 && (
              <div className="space-y-3 pt-2 border-t border-[hsl(var(--border))]">
                <h4 className="text-xs font-semibold text-muted-foreground uppercase tracking-wider">
                  Transformation Server URLs
                </h4>
                <div className="space-y-2">
                  {TS_URL_LABELS.map(([key, label]) => (
                    <div key={key} className="grid grid-cols-[1fr_2fr] gap-3 items-center">
                      <Label className="text-xs">{label}</Label>
                      <Input
                        value={flowData.tsUrls?.[key] ?? ""}
                        disabled
                        className="text-xs h-8 font-mono"
                      />
                    </div>
                  ))}
                </div>
              </div>
            )}
          </div>
        )}

        <DialogFooter>
          <Button variant="outline" onClick={() => onOpenChange(false)}>
            {flowData?.running ? "Close" : "Cancel"}
          </Button>
          {flowData && !flowData.running && (flowData.properties?.length ?? 0) > 0 && (
            <Button
              onClick={() => void handleSave()}
              disabled={saveProps.isPending}
            >
              {saveProps.isPending ? <Loader2 className="w-4 h-4 animate-spin" /> : <Save className="w-4 h-4" />}
              Save Properties
            </Button>
          )}
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}

const TS_URL_LABELS: [string, string][] = [
  ["U1", "Primary TS URL PA"],
  ["U2", "Secondary TS URL PA"],
  ["U1T", "Primary TS URL PB"],
  ["U2T", "Secondary TS URL PB"],
  ["U11", "Primary TS URL PC"],
  ["U21", "Secondary TS URL PC"],
  ["U1T1", "Primary TS URL PD"],
  ["U2T1", "Secondary TS URL PD"],
  ["U1D", "Primary TS URL D"],
  ["U2D", "Secondary TS URL D"],
];
