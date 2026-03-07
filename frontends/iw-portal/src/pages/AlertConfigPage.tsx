import { Loader2, Bell, BellOff, AlertTriangle, Info, ShieldAlert } from "lucide-react";
import { useAlertRules } from "@/hooks/useMonitoring";
import type { AlertRule } from "@/types/monitoring";
import { cn } from "@/lib/utils";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { apiFetch } from "@/lib/api";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { Badge } from "@/components/ui/badge";
import { Switch } from "@/components/ui/switch";
import { Tooltip, TooltipContent, TooltipTrigger } from "@/components/ui/tooltip";

const SEVERITY_CONFIG: Record<
  AlertRule["severity"],
  { icon: React.ComponentType<{ className?: string }>; color: string; bg: string }
> = {
  critical: {
    icon: ShieldAlert,
    color: "text-[hsl(var(--destructive))]",
    bg: "bg-[hsl(var(--destructive)/0.1)]",
  },
  warning: {
    icon: AlertTriangle,
    color: "text-[hsl(var(--warning))]",
    bg: "bg-[hsl(var(--warning)/0.1)]",
  },
  info: {
    icon: Info,
    color: "text-[hsl(var(--primary))]",
    bg: "bg-[hsl(var(--primary)/0.1)]",
  },
};

export function AlertConfigPage() {
  useDocumentTitle("Alert Configuration");
  const { data: alertsRes, isLoading } = useAlertRules();
  const queryClient = useQueryClient();

  const toggleMutation = useMutation({
    mutationFn: async ({ id, enabled }: { id: number; enabled: boolean }) => {
      await apiFetch(`/api/monitoring/alerts/${id}`, {
        method: "PUT",
        body: JSON.stringify({ is_enabled: enabled }),
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["monitoring", "alerts"] });
    },
  });

  const rules = alertsRes?.data?.alert_rules ?? [];

  return (
    <div className="space-y-4">
      <div className="flex items-center justify-between">
        <p className="text-sm text-muted-foreground">
          Configure alert rules to receive notifications when thresholds are breached.
        </p>
      </div>

      {isLoading ? (
        <div className="flex justify-center py-12">
          <Loader2 className="w-6 h-6 animate-spin text-muted-foreground" />
        </div>
      ) : rules.length === 0 ? (
        <div className="glass-panel rounded-[var(--radius)] p-8 text-center">
          <Bell className="w-8 h-8 text-muted-foreground mx-auto mb-2" />
          <p className="text-sm text-muted-foreground">No alert rules configured.</p>
          <p className="text-xs text-muted-foreground mt-1">
            Alert rules can be created via the monitoring API or the classic portal.
          </p>
        </div>
      ) : (
        <div className="space-y-2">
          {rules.map((rule) => {
            const sev = SEVERITY_CONFIG[rule.severity] ?? SEVERITY_CONFIG.info;
            const Icon = sev.icon;
            return (
              <div
                key={rule.id}
                className={cn(
                  "glass-panel rounded-[var(--radius)] p-4 flex items-center gap-4",
                  !rule.is_enabled && "opacity-50"
                )}
              >
                <div className={cn("w-9 h-9 rounded-xl grid place-items-center shrink-0", sev.bg)}>
                  <Icon className={cn("w-4 h-4", sev.color)} />
                </div>

                <div className="flex-1 min-w-0">
                  <div className="flex items-center gap-2">
                    <p className="text-sm font-medium truncate">{rule.name}</p>
                    <Badge
                      variant={rule.severity === "critical" ? "destructive" : rule.severity === "warning" ? "warning" : "default"}
                      className="text-[10px]"
                    >
                      {rule.severity}
                    </Badge>
                  </div>
                  <p className="text-xs text-muted-foreground mt-0.5">
                    {rule.condition_type} ≥ {rule.threshold_value}
                    {rule.last_triggered_at && (
                      <span className="ml-2">
                        · Last triggered {new Date(rule.last_triggered_at).toLocaleDateString()}
                      </span>
                    )}
                  </p>
                </div>

                {/* Toggle */}
                <Tooltip>
                  <TooltipTrigger asChild>
                    <div>
                      <Switch
                        checked={rule.is_enabled}
                        onCheckedChange={(checked) =>
                          toggleMutation.mutate({ id: rule.id, enabled: checked })
                        }
                        disabled={toggleMutation.isPending}
                      />
                    </div>
                  </TooltipTrigger>
                  <TooltipContent>
                    {rule.is_enabled ? "Disable alert" : "Enable alert"}
                  </TooltipContent>
                </Tooltip>
              </div>
            );
          })}
        </div>
      )}

      {/* Explanation */}
      <div className="glass-panel rounded-[var(--radius)] p-4 flex items-start gap-3">
        <BellOff className="w-4 h-4 text-muted-foreground mt-0.5 shrink-0" />
        <div className="text-xs text-muted-foreground space-y-1">
          <p>
            <strong className="text-foreground">Alert delivery</strong> requires SMTP or webhook
            configuration. Copy{" "}
            <code className="text-[hsl(var(--primary))]">monitoring.properties.template</code> →{" "}
            <code className="text-[hsl(var(--primary))]">monitoring.properties</code> and fill in
            your SMTP credentials or webhook URLs.
          </p>
          <p>
            Alerts are evaluated by the MetricsAggregator service on each aggregation cycle (default: every 5 minutes).
          </p>
        </div>
      </div>
    </div>
  );
}
