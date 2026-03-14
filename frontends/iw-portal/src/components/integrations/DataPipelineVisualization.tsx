import {
  ArrowRight,
  Server,
  Wifi,
  WifiOff,
  Database,
  FileCode,
  Loader2,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { Badge } from "@/components/ui/badge";

interface SystemInfo {
  name: string;
  connected: boolean;
  untested: boolean;
}

interface PipelineProps {
  source: SystemInfo;
  destination: SystemInfo;
  engineUp: boolean;
  xsltCount: number;
  transactionCount: number;
  isLoading: boolean;
}

function SystemBox({ system, side }: { system: SystemInfo; side: "source" | "destination" }) {
  const isOk = system.connected;
  const isUnknown = system.untested;

  return (
    <div className={cn(
      "flex-1 rounded-xl border p-4 text-center min-w-[140px]",
      isOk
        ? "border-[hsl(var(--success)/0.3)] bg-[hsl(var(--success)/0.04)]"
        : isUnknown
          ? "border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.1)]"
          : "border-[hsl(var(--destructive)/0.3)] bg-[hsl(var(--destructive)/0.04)]"
    )}>
      <div className="flex justify-center mb-2">
        {isOk ? (
          <Wifi className="w-6 h-6 text-[hsl(var(--success))]" />
        ) : isUnknown ? (
          <Database className="w-6 h-6 text-muted-foreground" />
        ) : (
          <WifiOff className="w-6 h-6 text-[hsl(var(--destructive))]" />
        )}
      </div>
      <p className="text-sm font-semibold">{system.name}</p>
      <p className="text-[10px] text-muted-foreground capitalize mt-0.5">{side}</p>
      <Badge
        variant={isOk ? "success" : isUnknown ? "secondary" : "destructive"}
        className="text-[9px] mt-2"
      >
        {isOk ? "Connected" : isUnknown ? "Untested" : "Error"}
      </Badge>
    </div>
  );
}

export function DataPipelineVisualization({
  source,
  destination,
  engineUp,
  xsltCount,
  transactionCount,
  isLoading,
}: PipelineProps) {
  if (isLoading) {
    return (
      <div className="flex items-center justify-center py-8">
        <Loader2 className="w-6 h-6 animate-spin text-muted-foreground" />
      </div>
    );
  }

  return (
    <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-6">
      <div className="flex items-center gap-3 flex-wrap justify-center">
        {/* Source System */}
        <SystemBox system={source} side="source" />

        {/* Arrow → */}
        <div className="flex flex-col items-center gap-1 shrink-0 px-2">
          <ArrowRight className="w-5 h-5 text-[hsl(var(--primary))]" />
          <span className="text-[9px] text-muted-foreground">{transactionCount} flows</span>
        </div>

        {/* IW Engine */}
        <div className={cn(
          "flex-1 rounded-xl border p-4 text-center min-w-[160px]",
          engineUp
            ? "border-[hsl(var(--primary)/0.3)] bg-[hsl(var(--primary)/0.04)]"
            : "border-[hsl(var(--border))] bg-[hsl(var(--muted)/0.1)]"
        )}>
          <div className="flex justify-center mb-2">
            <Server className={cn("w-6 h-6", engineUp ? "text-[hsl(var(--primary))]" : "text-muted-foreground")} />
          </div>
          <p className="text-sm font-semibold">IW Engine</p>
          <div className="flex items-center justify-center gap-2 mt-1">
            <div className="flex items-center gap-1 text-[10px] text-muted-foreground">
              <FileCode className="w-3 h-3" />
              {xsltCount} XSLTs
            </div>
          </div>
          <Badge
            variant={engineUp ? "success" : "secondary"}
            className="text-[9px] mt-2"
          >
            {engineUp ? "Online" : "Offline"}
          </Badge>
        </div>

        {/* Arrow → */}
        <div className="flex flex-col items-center gap-1 shrink-0 px-2">
          <ArrowRight className="w-5 h-5 text-[hsl(var(--primary))]" />
          <span className="text-[9px] text-muted-foreground">transform</span>
        </div>

        {/* Destination System */}
        <SystemBox system={destination} side="destination" />
      </div>
    </div>
  );
}
