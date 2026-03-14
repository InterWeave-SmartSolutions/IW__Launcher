import { ArrowDown, Database, RefreshCw, Upload } from "lucide-react";
import { cn } from "@/lib/utils";
import type { WorkspaceTransaction } from "@/types/workspace";

interface FlowChainVisualizationProps {
  /** The starting transaction */
  transaction: WorkspaceTransaction;
  /** All transactions in the project (for resolving NextTransaction chains) */
  allTransactions: WorkspaceTransaction[];
}

interface ChainStep {
  id: string;
  description: string;
  type: "read" | "transform" | "write" | "generic";
}

/** Infer step type from transaction ID naming conventions */
function inferStepType(id: string): ChainStep["type"] {
  const lower = id.toLowerCase();
  if (lower.includes("login") || lower.includes("read") || lower.includes("query") || lower.includes("get")) return "read";
  if (lower.includes("transform") || lower.includes("convert") || lower.includes("map")) return "transform";
  if (lower.includes("write") || lower.includes("update") || lower.includes("insert") || lower.includes("create") || lower.includes("post")) return "write";
  return "generic";
}

const STEP_CONFIG = {
  read: { icon: Database, color: "text-[hsl(var(--primary))]", bg: "bg-[hsl(var(--primary)/0.1)]", label: "Read" },
  transform: { icon: RefreshCw, color: "text-[hsl(var(--warning))]", bg: "bg-[hsl(var(--warning)/0.1)]", label: "Transform" },
  write: { icon: Upload, color: "text-[hsl(var(--success))]", bg: "bg-[hsl(var(--success)/0.1)]", label: "Write" },
  generic: { icon: Database, color: "text-muted-foreground", bg: "bg-[hsl(var(--muted)/0.3)]", label: "Step" },
};

/** Walk the NextTransaction chain to build a linear step list */
function buildChain(
  start: WorkspaceTransaction,
  allTransactions: WorkspaceTransaction[],
  maxDepth = 10
): ChainStep[] {
  const steps: ChainStep[] = [];
  const visited = new Set<string>();
  let current: WorkspaceTransaction | undefined = start;

  while (current && steps.length < maxDepth) {
    if (visited.has(current.Id)) break; // prevent cycles
    visited.add(current.Id);

    steps.push({
      id: current.Id,
      description: current.Description || current.Id,
      type: inferStepType(current.Id),
    });

    const nextId = current.NextTransaction as string | undefined;
    if (!nextId || nextId === "none" || nextId === "") break;
    current = allTransactions.find((t) => t.Id === nextId);
  }

  return steps;
}

export function FlowChainVisualization({ transaction, allTransactions }: FlowChainVisualizationProps) {
  const chain = buildChain(transaction, allTransactions);

  if (chain.length <= 1) {
    return (
      <div className="text-xs text-muted-foreground text-center py-2">
        Single-step flow (no chain)
      </div>
    );
  }

  return (
    <div className="space-y-0">
      {chain.map((step, i) => {
        const config = STEP_CONFIG[step.type];
        const Icon = config.icon;
        const isLast = i === chain.length - 1;

        return (
          <div key={step.id}>
            <div className="flex items-center gap-3">
              {/* Step number + icon */}
              <div className={cn("w-8 h-8 rounded-lg grid place-items-center shrink-0", config.bg)}>
                <Icon className={cn("w-4 h-4", config.color)} />
              </div>

              {/* Step content */}
              <div className="flex-1 min-w-0">
                <div className="flex items-center gap-2">
                  <span className="text-xs font-medium truncate">{step.id}</span>
                  <span className={cn(
                    "text-[9px] px-1.5 py-0.5 rounded-full border",
                    config.bg, config.color
                  )}>
                    {config.label}
                  </span>
                </div>
                {step.description !== step.id && (
                  <p className="text-[10px] text-muted-foreground truncate">{step.description}</p>
                )}
              </div>
            </div>

            {/* Connector arrow */}
            {!isLast && (
              <div className="ml-4 py-1 flex justify-center">
                <ArrowDown className="w-3 h-3 text-muted-foreground" />
              </div>
            )}
          </div>
        );
      })}
    </div>
  );
}
