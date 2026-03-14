import { useState } from "react";
import { ArrowRight, ArrowLeftRight, ChevronDown, ChevronRight } from "lucide-react";
import { cn } from "@/lib/utils";
import { Badge } from "@/components/ui/badge";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import type { SyncDirection, SyncMapping } from "@/types/configuration";

interface ObjectPairProps {
  mapping: SyncMapping;
  onDirectionChange: (key: string, direction: SyncDirection) => void;
  /** Render prop for the expanded field mapping panel */
  renderFieldPanel?: () => React.ReactNode;
  disabled?: boolean;
}

function directionIcon(dir: SyncDirection) {
  if (dir === "SFQB") return <ArrowLeftRight className="w-3.5 h-3.5 text-[hsl(var(--primary))]" />;
  if (dir === "None") return <span className="w-3.5 h-3.5" />;
  return <ArrowRight className={cn("w-3.5 h-3.5", dir === "QB2SF" && "rotate-180")} />;
}

export function ObjectPair({ mapping, onDirectionChange, renderFieldPanel, disabled }: ObjectPairProps) {
  const [expanded, setExpanded] = useState(false);
  const isActive = mapping.value !== "None";

  const directionOptions: { value: SyncDirection; label: string }[] = [
    { value: "None", label: "Disabled" },
    { value: "SF2QB", label: "Source \u2192 Dest" },
    { value: "QB2SF", label: "Dest \u2192 Source" },
  ];
  if (mapping.supportsBidirectional) {
    directionOptions.push({ value: "SFQB", label: "Bi-directional" });
  }

  return (
    <div className={cn(
      "rounded-xl border transition-colors",
      isActive
        ? "border-[hsl(var(--primary)/0.2)] bg-[hsl(var(--primary)/0.02)]"
        : "border-[hsl(var(--border))] opacity-60"
    )}>
      {/* Main row */}
      <div className="flex items-center gap-3 p-4">
        {/* Expand toggle */}
        {renderFieldPanel && isActive ? (
          <button
            onClick={() => setExpanded(!expanded)}
            className="p-1 rounded-md hover:bg-[hsl(var(--muted)/0.5)] transition-colors cursor-pointer shrink-0"
          >
            {expanded ? (
              <ChevronDown className="w-4 h-4 text-muted-foreground" />
            ) : (
              <ChevronRight className="w-4 h-4 text-muted-foreground" />
            )}
          </button>
        ) : (
          <div className="w-6" />
        )}

        {/* Source label */}
        <div className="flex-1 min-w-0">
          <p className="text-sm font-medium truncate">{mapping.sourceLabel}</p>
          <p className="text-[10px] text-muted-foreground">{mapping.tier === "core" ? "Core" : "Extended"}</p>
        </div>

        {/* Direction indicator */}
        <div className="flex items-center gap-2 shrink-0">
          {directionIcon(mapping.value)}
        </div>

        {/* Destination label */}
        <div className="flex-1 min-w-0 text-right">
          <p className="text-sm font-medium truncate">{mapping.destLabel}</p>
          <p className="text-[10px] text-muted-foreground capitalize">{mapping.category}</p>
        </div>

        {/* Direction selector */}
        <div className="w-[160px] shrink-0">
          <Select
            value={mapping.value}
            onValueChange={(v) => onDirectionChange(mapping.key, v as SyncDirection)}
            disabled={disabled}
          >
            <SelectTrigger className="h-8 text-xs">
              <SelectValue />
            </SelectTrigger>
            <SelectContent>
              {directionOptions.map((opt) => (
                <SelectItem key={opt.value} value={opt.value} className="text-xs">
                  {opt.label}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>

        {/* Active badge */}
        <Badge
          variant={isActive ? "success" : "secondary"}
          className="text-[9px] px-1.5 py-0 shrink-0"
        >
          {isActive ? "Active" : "Off"}
        </Badge>
      </div>

      {/* Expandable field mapping panel */}
      {expanded && renderFieldPanel && (
        <div className="border-t border-[hsl(var(--border))] p-4 bg-[hsl(var(--muted)/0.05)]">
          {renderFieldPanel()}
        </div>
      )}
    </div>
  );
}
