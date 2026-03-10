import { useState, useMemo, useCallback } from "react";
import { ArrowRight, Sparkles, RotateCcw, Check, X, AlertTriangle } from "lucide-react";
import { cn } from "@/lib/utils";

// ─── Types ─────────────────────────────────────────────────────────

interface FieldDef {
  name: string;
  type: string;
  label?: string;
}

interface MappingSuggestion {
  source: FieldDef;
  target: FieldDef;
  confidence: number; // 0-1
  reason: string;
}

interface FieldMapping {
  sourceField: string;
  targetField: string;
  status: "suggested" | "accepted" | "rejected" | "manual";
}

// ─── Sample Schemas (CRM ↔ FS) ────────────────────────────────────
// These would come from the backend in production; hardcoded for the
// prototype to demonstrate the AI mapping concept.

const CRM_FIELDS: FieldDef[] = [
  { name: "AccountName", type: "string", label: "Account Name" },
  { name: "BillingStreet", type: "string", label: "Billing Street" },
  { name: "BillingCity", type: "string", label: "Billing City" },
  { name: "BillingState", type: "string", label: "Billing State" },
  { name: "BillingPostalCode", type: "string", label: "Billing Zip" },
  { name: "BillingCountry", type: "string", label: "Billing Country" },
  { name: "Phone", type: "string", label: "Phone" },
  { name: "Fax", type: "string", label: "Fax" },
  { name: "Website", type: "string", label: "Website" },
  { name: "Description", type: "string", label: "Description" },
  { name: "AnnualRevenue", type: "currency", label: "Annual Revenue" },
  { name: "NumberOfEmployees", type: "integer", label: "Employees" },
  { name: "Industry", type: "string", label: "Industry" },
  { name: "OwnerId", type: "reference", label: "Owner" },
  { name: "CreatedDate", type: "datetime", label: "Created Date" },
  { name: "LastModifiedDate", type: "datetime", label: "Last Modified" },
  { name: "Email", type: "string", label: "Email" },
  { name: "ContactFirstName", type: "string", label: "Contact First Name" },
  { name: "ContactLastName", type: "string", label: "Contact Last Name" },
  { name: "PaymentTerms", type: "string", label: "Payment Terms" },
];

const FS_FIELDS: FieldDef[] = [
  { name: "CompanyName", type: "string", label: "Company Name" },
  { name: "Addr1", type: "string", label: "Address Line 1" },
  { name: "City", type: "string", label: "City" },
  { name: "State", type: "string", label: "State" },
  { name: "PostalCode", type: "string", label: "Postal Code" },
  { name: "Country", type: "string", label: "Country" },
  { name: "Phone", type: "string", label: "Phone" },
  { name: "Fax", type: "string", label: "Fax" },
  { name: "Email", type: "string", label: "Email" },
  { name: "Notes", type: "string", label: "Notes" },
  { name: "Balance", type: "currency", label: "Balance" },
  { name: "CreditLimit", type: "currency", label: "Credit Limit" },
  { name: "TermsRef", type: "reference", label: "Terms" },
  { name: "SalesRepRef", type: "reference", label: "Sales Rep" },
  { name: "TimeCreated", type: "datetime", label: "Time Created" },
  { name: "TimeModified", type: "datetime", label: "Time Modified" },
  { name: "FirstName", type: "string", label: "First Name" },
  { name: "LastName", type: "string", label: "Last Name" },
  { name: "JobTitle", type: "string", label: "Job Title" },
  { name: "AccountNumber", type: "string", label: "Account Number" },
];

// ─── Similarity Engine ─────────────────────────────────────────────

/** Normalize a field name for comparison */
function normalize(name: string): string {
  return name
    .replace(/([a-z])([A-Z])/g, "$1 $2") // camelCase → words
    .replace(/[_\-]/g, " ")
    .toLowerCase()
    .trim();
}

/** Known semantic equivalences */
const EQUIVALENCES: [string[], string[]][] = [
  [["account name", "company name", "name"], ["company name", "account name", "customer name"]],
  [["billing street", "address", "street"], ["addr1", "address line 1", "street"]],
  [["billing city", "city"], ["city"]],
  [["billing state", "state", "province"], ["state", "province"]],
  [["billing postal code", "billing zip", "zip", "postal code"], ["postal code", "zip"]],
  [["billing country", "country"], ["country"]],
  [["description", "notes", "memo"], ["notes", "description", "memo"]],
  [["created date", "date created"], ["time created", "created date"]],
  [["last modified date", "last modified"], ["time modified", "last modified"]],
  [["contact first name", "first name"], ["first name"]],
  [["contact last name", "last name"], ["last name"]],
  [["payment terms", "terms"], ["terms ref", "terms"]],
  [["owner id", "owner", "sales rep"], ["sales rep ref", "sales rep"]],
];

function computeSimilarity(src: FieldDef, tgt: FieldDef): { score: number; reason: string } {
  const sNorm = normalize(src.name);
  const tNorm = normalize(tgt.name);

  // Exact match
  if (sNorm === tNorm) return { score: 1.0, reason: "Exact name match" };

  // Check known equivalences
  for (const [srcSet, tgtSet] of EQUIVALENCES) {
    if (srcSet.includes(sNorm) && tgtSet.includes(tNorm)) {
      return { score: 0.92, reason: "Semantic equivalence" };
    }
  }

  // Type + word overlap
  const sWords = new Set(sNorm.split(" "));
  const tWords = new Set(tNorm.split(" "));
  const overlap = [...sWords].filter((w) => tWords.has(w) && w.length > 2).length;
  const maxWords = Math.max(sWords.size, tWords.size);
  if (overlap > 0 && src.type === tgt.type) {
    const score = 0.5 + (overlap / maxWords) * 0.35;
    return { score: Math.min(score, 0.85), reason: `Word overlap (${overlap}/${maxWords}) + type match` };
  }
  if (overlap > 0) {
    const score = 0.3 + (overlap / maxWords) * 0.3;
    return { score, reason: `Partial word overlap (${overlap}/${maxWords})` };
  }

  // Same type only
  if (src.type === tgt.type && src.type !== "string") {
    return { score: 0.15, reason: "Same data type" };
  }

  return { score: 0, reason: "" };
}

function generateSuggestions(sources: FieldDef[], targets: FieldDef[]): MappingSuggestion[] {
  const suggestions: MappingSuggestion[] = [];
  const usedTargets = new Set<string>();

  // First pass: high-confidence matches
  const scored: { src: FieldDef; tgt: FieldDef; score: number; reason: string }[] = [];
  for (const src of sources) {
    for (const tgt of targets) {
      const { score, reason } = computeSimilarity(src, tgt);
      if (score >= 0.3) {
        scored.push({ src, tgt, score, reason });
      }
    }
  }

  // Sort by confidence (highest first) and greedily assign
  scored.sort((a, b) => b.score - a.score);
  const usedSources = new Set<string>();

  for (const { src, tgt, score, reason } of scored) {
    if (usedSources.has(src.name) || usedTargets.has(tgt.name)) continue;
    suggestions.push({ source: src, target: tgt, confidence: score, reason });
    usedSources.add(src.name);
    usedTargets.add(tgt.name);
  }

  return suggestions;
}

// ─── Components ────────────────────────────────────────────────────

function ConfidenceBadge({ confidence }: { confidence: number }) {
  const pct = Math.round(confidence * 100);
  const color =
    pct >= 90
      ? "text-green-400 bg-green-400/10 border-green-400/30"
      : pct >= 70
        ? "text-blue-400 bg-blue-400/10 border-blue-400/30"
        : pct >= 50
          ? "text-yellow-400 bg-yellow-400/10 border-yellow-400/30"
          : "text-orange-400 bg-orange-400/10 border-orange-400/30";

  return (
    <span className={cn("inline-flex items-center gap-1 px-2 py-0.5 text-xs font-mono rounded-full border", color)}>
      {pct}%
    </span>
  );
}

function MappingRow({
  suggestion,
  mapping,
  onAccept,
  onReject,
}: {
  suggestion: MappingSuggestion;
  mapping?: FieldMapping;
  onAccept: () => void;
  onReject: () => void;
}) {
  const status = mapping?.status ?? "suggested";

  return (
    <div
      className={cn(
        "grid grid-cols-[1fr_auto_1fr_auto] items-center gap-3 px-4 py-3 rounded-xl border transition-all",
        status === "accepted" && "bg-green-400/5 border-green-400/20",
        status === "rejected" && "bg-red-400/5 border-red-400/20 opacity-50",
        status === "suggested" && "bg-card border-border hover:border-primary/30"
      )}
    >
      {/* Source field */}
      <div className="min-w-0">
        <p className="text-sm font-medium truncate">{suggestion.source.label ?? suggestion.source.name}</p>
        <p className="text-xs text-muted-foreground font-mono">{suggestion.source.name}</p>
      </div>

      {/* Arrow + confidence */}
      <div className="flex flex-col items-center gap-1">
        <ArrowRight className="w-4 h-4 text-muted-foreground" />
        <ConfidenceBadge confidence={suggestion.confidence} />
      </div>

      {/* Target field */}
      <div className="min-w-0">
        <p className="text-sm font-medium truncate">{suggestion.target.label ?? suggestion.target.name}</p>
        <p className="text-xs text-muted-foreground font-mono">{suggestion.target.name}</p>
      </div>

      {/* Actions */}
      <div className="flex items-center gap-1.5">
        {status === "suggested" ? (
          <>
            <button
              onClick={onAccept}
              className="p-1.5 rounded-lg text-green-400 hover:bg-green-400/10 transition-colors cursor-pointer"
              title="Accept mapping"
            >
              <Check className="w-4 h-4" />
            </button>
            <button
              onClick={onReject}
              className="p-1.5 rounded-lg text-red-400 hover:bg-red-400/10 transition-colors cursor-pointer"
              title="Reject mapping"
            >
              <X className="w-4 h-4" />
            </button>
          </>
        ) : status === "accepted" ? (
          <span className="text-xs text-green-400 font-medium">Accepted</span>
        ) : (
          <span className="text-xs text-red-400 font-medium">Rejected</span>
        )}
      </div>
    </div>
  );
}

// ─── Page ──────────────────────────────────────────────────────────

export function FieldMappingPage() {
  const [mappings, setMappings] = useState<Map<string, FieldMapping>>(new Map());
  const [showRejected, setShowRejected] = useState(false);

  const suggestions = useMemo(() => generateSuggestions(CRM_FIELDS, FS_FIELDS), []);

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

  const handleReset = useCallback(() => {
    setMappings(new Map());
  }, []);

  const handleAcceptAll = useCallback(() => {
    setMappings(() => {
      const next = new Map<string, FieldMapping>();
      for (const s of suggestions) {
        next.set(s.source.name, {
          sourceField: s.source.name,
          targetField: s.target.name,
          status: "accepted",
        });
      }
      return next;
    });
  }, [suggestions]);

  const stats = useMemo(() => {
    let accepted = 0;
    let rejected = 0;
    let pending = 0;
    for (const s of suggestions) {
      const m = mappings.get(s.source.name);
      if (!m || m.status === "suggested") pending++;
      else if (m.status === "accepted") accepted++;
      else rejected++;
    }
    return { accepted, rejected, pending, total: suggestions.length };
  }, [suggestions, mappings]);

  const filtered = showRejected
    ? suggestions
    : suggestions.filter((s) => {
        const m = mappings.get(s.source.name);
        return !m || m.status !== "rejected";
      });

  const unmappedSource = CRM_FIELDS.filter(
    (f) => !suggestions.some((s) => s.source.name === f.name)
  );
  const unmappedTarget = FS_FIELDS.filter(
    (f) => !suggestions.some((s) => s.target.name === f.name)
  );

  return (
    <div className="space-y-6">
      {/* Header */}
      <div>
        <h1 className="text-2xl font-semibold tracking-tight flex items-center gap-2">
          <Sparkles className="w-6 h-6 text-primary" />
          AI Field Mapping
        </h1>
        <p className="text-muted-foreground mt-1">
          Intelligent field mapping suggestions between your CRM and Financial System schemas.
        </p>
      </div>

      {/* Stats bar */}
      <div className="grid grid-cols-2 sm:grid-cols-4 gap-3">
        {[
          { label: "Suggested", value: stats.total, color: "text-primary" },
          { label: "Accepted", value: stats.accepted, color: "text-green-400" },
          { label: "Rejected", value: stats.rejected, color: "text-red-400" },
          { label: "Pending", value: stats.pending, color: "text-yellow-400" },
        ].map((s) => (
          <div key={s.label} className="p-3 rounded-xl border border-border bg-card">
            <p className="text-xs text-muted-foreground">{s.label}</p>
            <p className={cn("text-2xl font-semibold", s.color)}>{s.value}</p>
          </div>
        ))}
      </div>

      {/* Actions */}
      <div className="flex items-center gap-3 flex-wrap">
        <button
          onClick={handleAcceptAll}
          className="inline-flex items-center gap-2 px-4 py-2 text-sm font-medium rounded-xl bg-primary text-primary-foreground hover:bg-primary/90 transition-colors cursor-pointer"
        >
          <Check className="w-4 h-4" />
          Accept All
        </button>
        <button
          onClick={handleReset}
          className="inline-flex items-center gap-2 px-4 py-2 text-sm font-medium rounded-xl border border-border hover:bg-accent transition-colors cursor-pointer"
        >
          <RotateCcw className="w-4 h-4" />
          Reset
        </button>
        <label className="inline-flex items-center gap-2 text-sm text-muted-foreground ml-auto cursor-pointer">
          <input
            type="checkbox"
            checked={showRejected}
            onChange={(e) => setShowRejected(e.target.checked)}
            className="rounded"
          />
          Show rejected
        </label>
      </div>

      {/* Mapping suggestions */}
      <div className="space-y-2">
        <h2 className="text-sm font-medium text-muted-foreground uppercase tracking-wider">
          Mapping Suggestions ({filtered.length})
        </h2>
        {filtered.map((s) => (
          <MappingRow
            key={s.source.name}
            suggestion={s}
            mapping={mappings.get(s.source.name)}
            onAccept={() => handleAccept(s.source.name, s.target.name)}
            onReject={() => handleReject(s.source.name, s.target.name)}
          />
        ))}
      </div>

      {/* Unmapped fields */}
      {(unmappedSource.length > 0 || unmappedTarget.length > 0) && (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {unmappedSource.length > 0 && (
            <div className="p-4 rounded-xl border border-border bg-card">
              <h3 className="text-sm font-medium flex items-center gap-2 mb-3">
                <AlertTriangle className="w-4 h-4 text-yellow-400" />
                Unmapped CRM Fields ({unmappedSource.length})
              </h3>
              <div className="space-y-1.5">
                {unmappedSource.map((f) => (
                  <div key={f.name} className="text-sm">
                    <span className="text-foreground">{f.label ?? f.name}</span>
                    <span className="text-muted-foreground font-mono text-xs ml-2">{f.name}</span>
                  </div>
                ))}
              </div>
            </div>
          )}
          {unmappedTarget.length > 0 && (
            <div className="p-4 rounded-xl border border-border bg-card">
              <h3 className="text-sm font-medium flex items-center gap-2 mb-3">
                <AlertTriangle className="w-4 h-4 text-yellow-400" />
                Unmapped FS Fields ({unmappedTarget.length})
              </h3>
              <div className="space-y-1.5">
                {unmappedTarget.map((f) => (
                  <div key={f.name} className="text-sm">
                    <span className="text-foreground">{f.label ?? f.name}</span>
                    <span className="text-muted-foreground font-mono text-xs ml-2">{f.name}</span>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      )}
    </div>
  );
}
