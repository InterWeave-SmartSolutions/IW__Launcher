import type { Transaction } from "@/types/monitoring";

/** Derive unique flow names + stats from transaction history */
export function deriveFlows(transactions: Transaction[]) {
  const map = new Map<
    string,
    { name: string; lastRun: string | null; lastStatus: string; totalRuns: number; successCount: number; failedCount: number }
  >();

  for (const tx of transactions) {
    const existing = map.get(tx.flow_name);
    if (!existing) {
      map.set(tx.flow_name, {
        name: tx.flow_name,
        lastRun: tx.started_at,
        lastStatus: tx.status,
        totalRuns: 1,
        successCount: tx.status === "success" ? 1 : 0,
        failedCount: tx.status === "failed" ? 1 : 0,
      });
    } else {
      existing.totalRuns++;
      if (tx.status === "success") existing.successCount++;
      if (tx.status === "failed") existing.failedCount++;
      if (tx.started_at > (existing.lastRun ?? "")) {
        existing.lastRun = tx.started_at;
        existing.lastStatus = tx.status;
      }
    }
  }
  return [...map.values()].sort((a, b) => (b.lastRun ?? "").localeCompare(a.lastRun ?? ""));
}

/** Format a relative time string */
export function fmtTime(iso: string | null): string {
  if (!iso) return "Never";
  try {
    const d = new Date(iso);
    const now = new Date();
    const diffMs = now.getTime() - d.getTime();
    if (diffMs < 60_000) return "Just now";
    if (diffMs < 3600_000) return `${Math.floor(diffMs / 60_000)}m ago`;
    if (diffMs < 86400_000) return `${Math.floor(diffMs / 3600_000)}h ago`;
    return d.toLocaleDateString();
  } catch {
    return iso;
  }
}

/** Status-based Tailwind classes for flow cards (Flows tab) */
export function statusColor(status: string) {
  switch (status) {
    case "running":
      return "border-[hsl(var(--primary)/0.3)] bg-[hsl(var(--primary)/0.08)]";
    case "success":
      return "border-[hsl(var(--success)/0.3)] bg-[hsl(var(--success)/0.08)]";
    case "failed":
      return "border-[hsl(var(--destructive)/0.3)] bg-[hsl(var(--destructive)/0.08)]";
    default:
      return "border-[hsl(var(--border))]";
  }
}

/**
 * Four-state color coding for engine flow rows (matching classic JSP):
 *   running+executing = rose/pink (was deeppink)
 *   running only      = cyan/aqua (was aqua)
 *   executing only    = amber/yellow (was yellow)
 *   stopped           = none
 */
export function engineFlowRowStyle(running: boolean, executing: boolean) {
  if (running && executing)
    return {
      bg: "bg-[hsl(330_80%_60%/0.10)]",
      border: "border-l-4 border-l-[hsl(330,80%,60%)]",
      label: "Running & Executing",
      dot: "bg-[hsl(330,80%,60%)]",
    };
  if (running)
    return {
      bg: "bg-[hsl(185_70%_50%/0.10)]",
      border: "border-l-4 border-l-[hsl(185,70%,50%)]",
      label: "Running (Scheduled)",
      dot: "bg-[hsl(185,70%,50%)]",
    };
  if (executing)
    return {
      bg: "bg-[hsl(45_95%_55%/0.10)]",
      border: "border-l-4 border-l-[hsl(45,95%,55%)]",
      label: "Executing (One-time)",
      dot: "bg-[hsl(45,95%,55%)]",
    };
  return {
    bg: "",
    border: "border-l-4 border-l-transparent",
    label: "Stopped",
    dot: "bg-muted-foreground",
  };
}

/** Log level number to human label */
export function logLevelLabel(level: number): { label: string; color: string } {
  if (level === -1111) return { label: "OFF", color: "text-muted-foreground" };
  if (level <= 0) return { label: "OFF", color: "text-muted-foreground" };
  if (level === 1) return { label: "ERR", color: "text-[hsl(var(--destructive))]" };
  if (level === 2) return { label: "WARN", color: "text-[hsl(var(--warning))]" };
  if (level === 3) return { label: "INFO", color: "text-[hsl(var(--primary))]" };
  if (level === 4) return { label: "DEBUG", color: "text-[hsl(var(--success))]" };
  return { label: "TRACE", color: "text-muted-foreground" };
}

/** Parse flow IDs to detect source and destination systems for hub-and-spoke */
export interface SystemNode {
  name: string;
  abbrev: string;
  flowCount: number;
  runningCount: number;
  failedCount: number;
  direction: "source" | "destination" | "both";
}

const SYSTEM_PATTERNS: Array<{ pattern: RegExp; name: string; abbrev: string }> = [
  { pattern: /\bSF\b|salesforce|sfdc/i, name: "Salesforce", abbrev: "SF" },
  { pattern: /\bQB\b|quickbooks/i, name: "QuickBooks", abbrev: "QB" },
  { pattern: /\bCRM\b|creatio/i, name: "Creatio CRM", abbrev: "CRM" },
  { pattern: /\bAuth\b|authorize/i, name: "Authorize.net", abbrev: "AUTH" },
  { pattern: /\bNS\b|netsuite/i, name: "NetSuite", abbrev: "NS" },
  { pattern: /\bXero\b/i, name: "Xero", abbrev: "XERO" },
  { pattern: /\bStripe\b/i, name: "Stripe", abbrev: "STRIPE" },
  { pattern: /\bMindbody\b|MB\b/i, name: "Mindbody", abbrev: "MB" },
];

export function parseFlowSystems(flows: Array<{ flowId: string; running: boolean; failures: number }>) {
  const systems = new Map<string, SystemNode>();

  for (const flow of flows) {
    const id = flow.flowId;
    for (const sys of SYSTEM_PATTERNS) {
      if (sys.pattern.test(id)) {
        const existing = systems.get(sys.abbrev);
        if (existing) {
          existing.flowCount++;
          if (flow.running) existing.runningCount++;
          existing.failedCount += flow.failures;
        } else {
          systems.set(sys.abbrev, {
            name: sys.name,
            abbrev: sys.abbrev,
            flowCount: 1,
            runningCount: flow.running ? 1 : 0,
            failedCount: flow.failures,
            direction: "both",
          });
        }
      }
    }
  }

  return [...systems.values()];
}
