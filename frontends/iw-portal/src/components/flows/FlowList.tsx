import { useState } from "react";
import {
  Search,
  Workflow,
  Play,
  HelpCircle,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { Badge } from "@/components/ui/badge";
import type { WorkspaceTransaction, WorkspaceQuery } from "@/types/workspace";
import type { EngineFlow } from "@/types/flows";

export interface FlowListItem {
  id: string;
  description: string;
  type: "transaction" | "query";
  interval?: string;
  paramCount: number;
  /** Matched engine flow if the engine has this flow loaded */
  engineFlow?: EngineFlow;
  /** Raw workspace data */
  raw: WorkspaceTransaction | WorkspaceQuery;
}

interface FlowListProps {
  items: FlowListItem[];
  selectedId: string | null;
  onSelect: (id: string) => void;
}

export function FlowList({ items, selectedId, onSelect }: FlowListProps) {
  const [search, setSearch] = useState("");

  const filtered = search
    ? items.filter(
        (item) =>
          item.id.toLowerCase().includes(search.toLowerCase()) ||
          item.description.toLowerCase().includes(search.toLowerCase())
      )
    : items;

  const transactions = filtered.filter((i) => i.type === "transaction");
  const queries = filtered.filter((i) => i.type === "query");

  return (
    <div className="space-y-3">
      {/* Search */}
      {items.length > 5 && (
        <div className="relative">
          <Search className="absolute left-2.5 top-1/2 -translate-y-1/2 w-3.5 h-3.5 text-muted-foreground pointer-events-none" />
          <input
            type="text"
            placeholder="Filter flows..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            className="w-full pl-8 pr-3 py-2 text-sm rounded-lg border border-[hsl(var(--border))] bg-[hsl(var(--card))] focus:outline-none focus:ring-1 focus:ring-[hsl(var(--primary))] placeholder:text-muted-foreground"
          />
        </div>
      )}

      {/* Transactions */}
      {transactions.length > 0 && (
        <div>
          <p className="text-[10px] font-medium text-muted-foreground uppercase tracking-wider mb-1.5 px-1">
            Transactions ({transactions.length})
          </p>
          <div className="space-y-1">
            {transactions.map((item) => (
              <FlowListRow
                key={item.id}
                item={item}
                isSelected={selectedId === item.id}
                onSelect={() => onSelect(item.id)}
              />
            ))}
          </div>
        </div>
      )}

      {/* Queries */}
      {queries.length > 0 && (
        <div>
          <p className="text-[10px] font-medium text-muted-foreground uppercase tracking-wider mb-1.5 px-1">
            Queries ({queries.length})
          </p>
          <div className="space-y-1">
            {queries.map((item) => (
              <FlowListRow
                key={item.id}
                item={item}
                isSelected={selectedId === item.id}
                onSelect={() => onSelect(item.id)}
              />
            ))}
          </div>
        </div>
      )}

      {filtered.length === 0 && (
        <div className="text-center py-6">
          <HelpCircle className="w-6 h-6 text-muted-foreground mx-auto mb-1.5" />
          <p className="text-xs text-muted-foreground">
            {search ? "No flows match your search" : "No flows defined"}
          </p>
        </div>
      )}
    </div>
  );
}

function FlowListRow({
  item,
  isSelected,
  onSelect,
}: {
  item: FlowListItem;
  isSelected: boolean;
  onSelect: () => void;
}) {
  const isRunning = item.engineFlow?.running === true;
  const isLoaded = !!item.engineFlow;

  return (
    <button
      onClick={onSelect}
      className={cn(
        "w-full text-left px-3 py-2.5 rounded-lg border transition-colors cursor-pointer",
        isSelected
          ? "border-[hsl(var(--primary)/0.4)] bg-[hsl(var(--primary)/0.08)]"
          : "border-transparent hover:bg-[hsl(var(--muted)/0.3)]"
      )}
    >
      <div className="flex items-center gap-2">
        <div className={cn(
          "w-6 h-6 rounded-md grid place-items-center shrink-0",
          isRunning
            ? "bg-[hsl(var(--success)/0.15)]"
            : isLoaded
              ? "bg-[hsl(var(--primary)/0.1)]"
              : "bg-[hsl(var(--muted)/0.3)]"
        )}>
          {isRunning ? (
            <Play className="w-3 h-3 text-[hsl(var(--success))]" />
          ) : item.type === "query" ? (
            <Search className="w-3 h-3 text-muted-foreground" />
          ) : (
            <Workflow className="w-3 h-3 text-muted-foreground" />
          )}
        </div>
        <div className="flex-1 min-w-0">
          <p className="text-xs font-medium truncate">{item.id}</p>
          {item.description && (
            <p className="text-[10px] text-muted-foreground truncate">{item.description}</p>
          )}
        </div>
        <div className="flex items-center gap-1 shrink-0">
          {isRunning && (
            <Badge variant="success" className="text-[8px] px-1 py-0">Running</Badge>
          )}
          {item.paramCount > 0 && (
            <Badge variant="secondary" className="text-[8px] px-1 py-0">{item.paramCount}p</Badge>
          )}
        </div>
      </div>
    </button>
  );
}
