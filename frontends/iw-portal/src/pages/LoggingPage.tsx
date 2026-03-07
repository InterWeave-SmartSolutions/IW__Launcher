import {
  FileText,
  ExternalLink,
  Server,
  Clock,
  HardDrive,
  AlertTriangle,
  RefreshCw,
  Loader2,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { useDashboard } from "@/hooks/useMonitoring";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { Button } from "@/components/ui/button";

interface LogTarget {
  name: string;
  description: string;
  path: string;
  icon: React.ComponentType<{ className?: string }>;
}

const LOG_TARGETS: LogTarget[] = [
  {
    name: "catalina.out",
    description: "Main Tomcat server log — startup, shutdown, and application output",
    path: "web_portal/tomcat/logs/catalina.out",
    icon: Server,
  },
  {
    name: "localhost.*.log",
    description: "Application-level logs from deployed webapps (iw-business-daemon)",
    path: "web_portal/tomcat/logs/localhost.*.log",
    icon: FileText,
  },
  {
    name: "catalina.*.log",
    description: "Daily rotated Tomcat container logs",
    path: "web_portal/tomcat/logs/catalina.*.log",
    icon: Clock,
  },
  {
    name: "host-manager / manager",
    description: "Tomcat manager access logs (if enabled)",
    path: "web_portal/tomcat/logs/host-manager.*.log",
    icon: HardDrive,
  },
];

export function LoggingPage() {
  useDocumentTitle("System Logging");
  const { data: dashRes, isLoading, refetch, isFetching } = useDashboard();

  const summary = dashRes?.data?.summary;
  const lastUpdated = summary?.last_updated;

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex items-start justify-between gap-4 flex-wrap">
        <div>
          <h1 className="text-2xl font-semibold">System Logging</h1>
          <p className="text-sm text-muted-foreground mt-1">
            Server logs, application diagnostics, and system health information.
          </p>
        </div>
        <div className="flex items-center gap-2">
          <Button
            variant="outline"
            size="sm"
            onClick={() => void refetch()}
            disabled={isFetching}
            className="rounded-full"
          >
            <RefreshCw className={cn("w-3 h-3", isFetching && "animate-spin")} />
            Refresh
          </Button>
          <Button variant="outline" size="sm" className="rounded-full" asChild>
            <a href="/iw-business-daemon/Logging.jsp">
              <FileText className="w-3 h-3" />
              Classic Logging
              <ExternalLink className="w-3 h-3" />
            </a>
          </Button>
        </div>
      </div>

      {/* System status bar */}
      <div className="glass-panel rounded-[var(--radius)] p-4">
        <div className="flex items-center justify-between flex-wrap gap-3">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-xl bg-[hsl(var(--success)/0.15)] grid place-items-center">
              <Server className="w-5 h-5 text-[hsl(var(--success))]" />
            </div>
            <div>
              <p className="text-sm font-medium">Tomcat 9.0.83</p>
              <p className="text-xs text-muted-foreground">Port 9090 · iw-business-daemon</p>
            </div>
          </div>
          <div className="flex items-center gap-4 text-xs text-muted-foreground">
            {isLoading ? (
              <Loader2 className="w-4 h-4 animate-spin" />
            ) : (
              <>
                <span>
                  24h transactions: <b className="text-foreground">{summary?.total_count_24h ?? 0}</b>
                </span>
                <span>
                  Success rate: <b className="text-foreground">{(summary?.success_rate_24h ?? 0).toFixed(1)}%</b>
                </span>
                {lastUpdated && (
                  <span>
                    Last metric: {new Date(lastUpdated).toLocaleTimeString()}
                  </span>
                )}
              </>
            )}
          </div>
        </div>
      </div>

      {/* Log file targets */}
      <div className="glass-panel rounded-[var(--radius)] p-4">
        <h2 className="text-sm font-semibold mb-3">Log Files</h2>
        <div className="space-y-2">
          {LOG_TARGETS.map((target) => {
            const Icon = target.icon;
            return (
              <div
                key={target.name}
                className="flex items-center gap-4 p-3 rounded-xl border border-[hsl(var(--border))] hover:bg-[hsl(var(--muted)/0.2)] transition-colors"
              >
                <div className="w-8 h-8 rounded-lg bg-[hsl(var(--muted)/0.3)] grid place-items-center shrink-0">
                  <Icon className="w-4 h-4 text-[hsl(var(--primary))]" />
                </div>
                <div className="flex-1 min-w-0">
                  <p className="text-sm font-medium">{target.name}</p>
                  <p className="text-xs text-muted-foreground mt-0.5">{target.description}</p>
                </div>
                <code className="text-[10px] text-muted-foreground/60 hidden sm:block truncate max-w-[200px]">
                  {target.path}
                </code>
              </div>
            );
          })}
        </div>
      </div>

      {/* Info panel */}
      <div className="glass-panel rounded-[var(--radius)] p-4 flex items-start gap-3">
        <AlertTriangle className="w-4 h-4 text-muted-foreground mt-0.5 shrink-0" />
        <div className="text-xs text-muted-foreground space-y-1">
          <p>
            <strong className="text-foreground">Log viewing</strong> — Full log file access requires
            direct filesystem access or the classic Logging page. The monitoring API provides aggregated
            transaction metrics but not raw log content.
          </p>
          <p>
            Logs are located at{" "}
            <code className="text-[hsl(var(--primary))]">web_portal/tomcat/logs/</code>. Use{" "}
            <code className="text-[hsl(var(--primary))]">tail -f catalina.out</code> for real-time monitoring.
          </p>
        </div>
      </div>

      {/* CTA */}
      <div className="glass-panel rounded-2xl border border-[hsl(var(--primary)/0.2)] bg-gradient-to-r from-[hsl(var(--primary)/0.06)] to-transparent p-6">
        <div className="flex items-start gap-4 flex-wrap">
          <div className="flex-1 min-w-[200px]">
            <h3 className="font-semibold">Classic Log Viewer</h3>
            <p className="text-sm text-muted-foreground mt-1">
              The classic portal provides full server log viewing with filtering and search.
              Access log files directly through the JSP-based viewer.
            </p>
          </div>
          <Button asChild className="shrink-0">
            <a href="/iw-business-daemon/Logging.jsp">
              <ExternalLink className="w-4 h-4" />
              Open Log Viewer
            </a>
          </Button>
        </div>
      </div>
    </div>
  );
}
