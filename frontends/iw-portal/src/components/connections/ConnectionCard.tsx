import {
  Wifi,
  WifiOff,
  Database,
  Zap,
  Settings,
  Loader2,
  Clock,
  ExternalLink,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Tooltip, TooltipContent, TooltipTrigger } from "@/components/ui/tooltip";
import { systemDisplayName, type ConnectionView } from "@/hooks/useConnections";

interface ConnectionCardProps {
  connection: ConnectionView;
  onTest: () => void;
  onEdit: () => void;
  isTesting: boolean;
}

function fmtTestTime(iso: string): string {
  const diff = Date.now() - new Date(iso).getTime();
  const seconds = Math.floor(diff / 1000);
  if (seconds < 60) return `${seconds}s ago`;
  const minutes = Math.floor(seconds / 60);
  if (minutes < 60) return `${minutes}m ago`;
  const hours = Math.floor(minutes / 60);
  if (hours < 24) return `${hours}h ago`;
  const days = Math.floor(hours / 24);
  return `${days}d ago`;
}

export function ConnectionCard({ connection, onTest, onEdit, isTesting }: ConnectionCardProps) {
  const { credential, testResult, relatedProjects } = connection;
  const isReachable = testResult?.reachable === true;
  const isUntested = !testResult;

  const statusColor = isReachable
    ? "border-[hsl(var(--success)/0.3)]"
    : isUntested
      ? "border-[hsl(var(--border))]"
      : "border-[hsl(var(--destructive)/0.3)]";

  const statusBg = isReachable
    ? "bg-[hsl(var(--success)/0.04)]"
    : isUntested
      ? ""
      : "bg-[hsl(var(--destructive)/0.04)]";

  return (
    <div className={cn("glass-panel rounded-2xl border p-5 space-y-4 transition-colors", statusColor, statusBg)}>
      {/* Header: system name + status */}
      <div className="flex items-start justify-between">
        <div className="flex items-center gap-3">
          <div className={cn(
            "w-10 h-10 rounded-xl grid place-items-center shrink-0",
            isReachable
              ? "bg-[hsl(var(--success)/0.1)]"
              : "bg-[hsl(var(--muted)/0.3)]"
          )}>
            {isReachable ? (
              <Wifi className="w-5 h-5 text-[hsl(var(--success))]" />
            ) : isUntested ? (
              <Database className="w-5 h-5 text-muted-foreground" />
            ) : (
              <WifiOff className="w-5 h-5 text-[hsl(var(--destructive))]" />
            )}
          </div>
          <div>
            <h3 className="font-semibold text-sm">{systemDisplayName(credential.credentialType)}</h3>
            <p className="text-xs text-muted-foreground">{credential.credentialName}</p>
          </div>
        </div>
        <Badge
          variant={isReachable ? "success" : isUntested ? "secondary" : "destructive"}
          className="text-[10px]"
        >
          {isReachable ? "Connected" : isUntested ? "Untested" : "Error"}
        </Badge>
      </div>

      {/* Connection details */}
      <div className="space-y-2 text-sm">
        {credential.endpointUrl && (
          <div className="flex items-start gap-2">
            <ExternalLink className="w-3.5 h-3.5 text-muted-foreground mt-0.5 shrink-0" />
            <span className="text-xs text-muted-foreground break-all">{credential.endpointUrl}</span>
          </div>
        )}

        <div className="flex items-center gap-4 text-xs text-muted-foreground">
          {credential.username && (
            <span>User: <span className="text-foreground">{credential.username}</span></span>
          )}
          {credential.hasApiKey && (
            <Badge variant="secondary" className="text-[9px] px-1 py-0">API Key</Badge>
          )}
        </div>

        {/* Test result details */}
        {testResult && (
          <div className="flex items-center gap-2 text-xs">
            <Tooltip>
              <TooltipTrigger asChild>
                <span className={cn(
                  "flex items-center gap-1",
                  isReachable ? "text-[hsl(var(--success))]" : "text-[hsl(var(--destructive))]"
                )}>
                  {isReachable ? `${testResult.statusCode} OK` : "Unreachable"}
                  {testResult.responseTimeMs > 0 && ` (${testResult.responseTimeMs}ms)`}
                </span>
              </TooltipTrigger>
              <TooltipContent>Last tested {fmtTestTime(testResult.testedAt)}</TooltipContent>
            </Tooltip>
            <Clock className="w-3 h-3 text-muted-foreground" />
            <span className="text-muted-foreground">{fmtTestTime(testResult.testedAt)}</span>
          </div>
        )}

        {/* Related projects */}
        {relatedProjects.length > 0 && (
          <div className="flex items-center gap-1 text-[10px] text-muted-foreground pt-1">
            <span>Used by:</span>
            {relatedProjects.map((p) => (
              <Badge key={p} variant="outline" className="text-[9px] px-1 py-0">
                {p.replace(/_/g, " ")}
              </Badge>
            ))}
          </div>
        )}
      </div>

      {/* Actions */}
      <div className="flex gap-2 pt-1 border-t border-[hsl(var(--border))]">
        <Button
          variant="outline"
          size="sm"
          className="flex-1"
          onClick={onTest}
          disabled={!credential.endpointUrl || isTesting}
        >
          {isTesting ? (
            <Loader2 className="w-3.5 h-3.5 animate-spin" />
          ) : (
            <Zap className="w-3.5 h-3.5" />
          )}
          Test
        </Button>
        <Button variant="outline" size="sm" className="flex-1" onClick={onEdit}>
          <Settings className="w-3.5 h-3.5" />
          Edit
        </Button>
      </div>
    </div>
  );
}
