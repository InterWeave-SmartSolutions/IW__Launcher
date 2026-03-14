import { useState, useMemo, useCallback } from "react";
import { ArrowRight, Check, X, RotateCcw, Sparkles } from "lucide-react";
import { cn } from "@/lib/utils";
import { Button } from "@/components/ui/button";

// ─── Types ─────────────────────────────────────────────────────────

export interface FieldDef {
  name: string;
  type: string;
  label?: string;
}

interface MappingSuggestion {
  source: FieldDef;
  target: FieldDef;
  confidence: number;
  reason: string;
}

interface FieldMapping {
  sourceField: string;
  targetField: string;
  status: "suggested" | "accepted" | "rejected";
}

// ─── Default Schemas (CRM ↔ FS) ─────────────────────────────────────
// These are used when live schemas aren't available (Phase 3).

const DEFAULT_CRM_FIELDS: FieldDef[] = [
  { name: "AccountName", type: "string", label: "Account Name" },
  { name: "BillingStreet", type: "string", label: "Billing Street" },
  { name: "BillingCity", type: "string", label: "Billing City" },
  { name: "BillingState", type: "string", label: "Billing State" },
  { name: "BillingPostalCode", type: "string", label: "Billing Zip" },
  { name: "BillingCountry", type: "string", label: "Billing Country" },
  { name: "Phone", type: "string", label: "Phone" },
  { name: "Email", type: "string", label: "Email" },
  { name: "Description", type: "string", label: "Description" },
  { name: "ContactFirstName", type: "string", label: "Contact First Name" },
  { name: "ContactLastName", type: "string", label: "Contact Last Name" },
];

const DEFAULT_FS_FIELDS: FieldDef[] = [
  { name: "CompanyName", type: "string", label: "Company Name" },
  { name: "Addr1", type: "string", label: "Address Line 1" },
  { name: "City", type: "string", label: "City" },
  { name: "State", type: "string", label: "State" },
  { name: "PostalCode", type: "string", label: "Postal Code" },
  { name: "Country", type: "string", label: "Country" },
  { name: "Phone", type: "string", label: "Phone" },
  { name: "Email", type: "string", label: "Email" },
  { name: "Notes", type: "string", label: "Notes" },
  { name: "FirstName", type: "string", label: "First Name" },
  { name: "LastName", type: "string", label: "Last Name" },
];

// ─── Similarity Engine ────────────────────────────────────────────────

function normalize(name: string): string {
  return name
    .replace(/([a-z])([A-Z])/g, "$1 $2")
    .replace(/[_\-]/g, " ")
    .toLowerCase()
    .trim();
}

const EQUIVALENCES: [string[], string[]][] = [
  [["account name", "company name", "name"], ["company name", "account name", "customer name"]],
  [["billing street", "address", "street"], ["addr1", "address line 1", "street"]],
  [["billing city", "city"], ["city"]],
  [["billing state", "state", "province"], ["state", "province"]],
  [["billing postal code", "billing zip", "zip", "postal code"], ["postal code", "zip"]],
  [["billing country", "country"], ["country"]],
  [["description", "notes", "memo"], ["notes", "description", "memo"]],
  [["contact first name", "first name"], ["first name"]],
  [["contact last name", "last name"], ["last name"]],
];

function computeSimilarity(src: FieldDef, tgt: FieldDef): { score: number; reason: string } {
  const sNorm = normalize(src.name);
  const tNorm = normalize(tgt.name);

  if (sNorm === tNorm) return { score: 1.0, reason: "Exact match" };

  for (const [srcSet, tgtSet] of EQUIVALENCES) {
    if (srcSet.includes(sNorm) && tgtSet.includes(tNorm)) {
      return { score: 0.92, reason: "Semantic match" };
    }
  }

  const sWords = new Set(sNorm.split(" "));
  const tWords = new Set(tNorm.split(" "));
  const overlap = [...sWords].filter((w) => tWords.has(w) && w.length > 2).length;
  const maxWords = Math.max(sWords.size, tWords.size);
  if (overlap > 0 && src.type === tgt.type) {
    return { score: Math.min(0.5 + (overlap / maxWords) * 0.35, 0.85), reason: "Word + type match" };
  }
  if (overlap > 0) {
    return { score: 0.3 + (overlap / maxWords) * 0.3, reason: "Partial match" };
  }

  return { score: 0, reason: "" };
}

function generateSuggestions(sources: FieldDef[], targets: FieldDef[]): MappingSuggestion[] {
  const scored: { src: FieldDef; tgt: FieldDef; score: number; reason: string }[] = [];
  for (const src of sources) {
    for (const tgt of targets) {
      const { score, reason } = computeSimilarity(src, tgt);
      if (score >= 0.3) scored.push({ src, tgt, score, reason });
    }
  }

  scored.sort((a, b) => b.score - a.score);
  const usedSources = new Set<string>();
  const usedTargets = new Set<string>();
  const suggestions: MappingSuggestion[] = [];

  for (const { src, tgt, score, reason } of scored) {
    if (usedSources.has(src.name) || usedTargets.has(tgt.name)) continue;
    suggestions.push({ source: src, target: tgt, confidence: score, reason });
    usedSources.add(src.name);
    usedTargets.add(tgt.name);
  }

  return suggestions;
}

// ─── Props ─────────────────────────────────────────────────────────

interface FieldMappingPanelProps {
  /** Source system fields. Falls back to defaults if not provided. */
  sourceFields?: FieldDef[];
  /** Destination system fields. Falls back to defaults if not provided. */
  targetFields?: FieldDef[];
  /** Label for the source system */
  sourceLabel?: string;
  /** Label for the destination system */
  targetLabel?: string;
}

// ─── Component ─────────────────────────────────────────────────────

export function FieldMappingPanel({
  sourceFields,
  targetFields,
  sourceLabel = "Source",
  targetLabel = "Destination",
}: FieldMappingPanelProps) {
  const srcFields = sourceFields ?? DEFAULT_CRM_FIELDS;
  const tgtFields = targetFields ?? DEFAULT_FS_FIELDS;

  const suggestions = useMemo(() => generateSuggestions(srcFields, tgtFields), [srcFields, tgtFields]);
  const [mappings, setMappings] = useState<Map<string, FieldMapping>>(new Map());

  const handleAccept = useCallback((sourceField: string, targetField: string) => {
    setMappings((prev) => {
      const next = new Map(prev);
      next.set(sourceField, { sourceField, targetField, status: "accepted" });
      return next;
    });
  }, []);

  const handleReject = useCallback((sourceField: string, targetField: string) => {
    setMappings((prev) => {
      const next = new Map(prev);
      next.set(sourceField, { sourceField, targetField, status: "rejected" });
      return next;
    });
  }, []);

  const handleAcceptAll = useCallback(() => {
    const next = new Map<string, FieldMapping>();
    for (const s of suggestions) {
      next.set(s.source.name, { sourceField: s.source.name, targetField: s.target.name, status: "accepted" });
    }
    setMappings(next);
  }, [suggestions]);

  const handleReset = useCallback(() => setMappings(new Map()), []);

  const accepted = [...mappings.values()].filter((m) => m.status === "accepted").length;
  const usingDefaults = !sourceFields && !targetFields;

  return (
    <div className="space-y-3">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-2 text-xs text-muted-foreground">
          <Sparkles className="w-3.5 h-3.5 text-[hsl(var(--primary))]" />
          <span>
            {suggestions.length} suggested mappings
            {accepted > 0 && <> · <span className="text-[hsl(var(--success))]">{accepted} accepted</span></>}
          </span>
          {usingDefaults && (
            <span className="text-[10px] px-1.5 py-0.5 rounded bg-[hsl(var(--warning)/0.1)] text-[hsl(var(--warning))] border border-[hsl(var(--warning)/0.2)]">
              Sample schema
            </span>
          )}
        </div>
        <div className="flex gap-1">
          <Button variant="ghost" size="sm" onClick={handleAcceptAll} className="h-6 text-[10px] px-2">
            <Check className="w-3 h-3" /> Accept All
          </Button>
          <Button variant="ghost" size="sm" onClick={handleReset} className="h-6 text-[10px] px-2">
            <RotateCcw className="w-3 h-3" /> Reset
          </Button>
        </div>
      </div>

      {/* Column headers */}
      <div className="grid grid-cols-[1fr_auto_1fr_auto] gap-2 text-[10px] font-medium text-muted-foreground uppercase tracking-wider px-2">
        <span>{sourceLabel}</span>
        <span className="w-8 text-center" />
        <span>{targetLabel}</span>
        <span className="w-16" />
      </div>

      {/* Mapping rows */}
      <div className="space-y-1">
        {suggestions.map((s) => {
          const m = mappings.get(s.source.name);
          const status = m?.status ?? "suggested";

          return (
            <div
              key={s.source.name}
              className={cn(
                "grid grid-cols-[1fr_auto_1fr_auto] items-center gap-2 px-2 py-1.5 rounded-lg text-xs transition-all",
                status === "accepted" && "bg-[hsl(var(--success)/0.06)]",
                status === "rejected" && "opacity-40",
                status === "suggested" && "hover:bg-[hsl(var(--muted)/0.3)]"
              )}
            >
              <div className="min-w-0">
                <span className="font-medium truncate block">{s.source.label ?? s.source.name}</span>
              </div>

              <div className="flex flex-col items-center w-8">
                <ArrowRight className="w-3 h-3 text-muted-foreground" />
                <span className={cn(
                  "text-[9px] font-mono",
                  s.confidence >= 0.9 ? "text-[hsl(var(--success))]"
                    : s.confidence >= 0.7 ? "text-[hsl(var(--primary))]"
                    : "text-[hsl(var(--warning))]"
                )}>
                  {Math.round(s.confidence * 100)}%
                </span>
              </div>

              <div className="min-w-0">
                <span className="font-medium truncate block">{s.target.label ?? s.target.name}</span>
              </div>

              <div className="flex items-center gap-0.5 w-16 justify-end">
                {status === "suggested" ? (
                  <>
                    <button
                      onClick={() => handleAccept(s.source.name, s.target.name)}
                      className="p-1 rounded text-[hsl(var(--success))] hover:bg-[hsl(var(--success)/0.1)] cursor-pointer"
                    >
                      <Check className="w-3 h-3" />
                    </button>
                    <button
                      onClick={() => handleReject(s.source.name, s.target.name)}
                      className="p-1 rounded text-[hsl(var(--destructive))] hover:bg-[hsl(var(--destructive)/0.1)] cursor-pointer"
                    >
                      <X className="w-3 h-3" />
                    </button>
                  </>
                ) : (
                  <span className={cn(
                    "text-[9px] font-medium",
                    status === "accepted" ? "text-[hsl(var(--success))]" : "text-[hsl(var(--destructive))]"
                  )}>
                    {status === "accepted" ? "OK" : "Rej"}
                  </span>
                )}
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}
