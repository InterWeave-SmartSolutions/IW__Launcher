import { useState, useMemo, useRef, useEffect } from "react";
import {
  FileText,
  ExternalLink,
  Server,
  RefreshCw,
  Loader2,
  ArrowLeft,
  Search,
  AlertCircle,
  AlertTriangle,
  Filter,
  ArrowUpDown,
  ChevronRight,
  ChevronLeft,
  Calendar,
  HardDrive,
  Activity,
  Hash,
  Percent,
  Play,
  Square,
  RotateCw,
  CheckCircle2,
  XCircle,
  Clock,
  ListOrdered,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { useLogFiles, useLogSummary, useLogContent, type DaySummary, type LogLine } from "@/hooks/useLogs";
import { useTransactions } from "@/hooks/useMonitoring";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Tabs, TabsList, TabsTrigger, TabsContent } from "@/components/ui/tabs";
import {
  Tooltip,
  TooltipContent,
  TooltipTrigger,
  TooltipProvider,
} from "@/components/ui/tooltip";

/* ─── Types ─── */

type ViewMode = "list" | "detail";
type SeverityFilter = "all" | "issues" | "clean";
type SortField = "date" | "errors" | "errorRate";
type TimeRange = "all" | "1y" | "6m" | "3m" | "1m" | "1w";

const TYPE_LABELS: Record<string, string> = {
  catalina: "Catalina",
  "commons-daemon": "Service Daemon",
  admin: "Admin",
  "host-manager": "Host Manager",
};

const TIME_RANGES: { value: TimeRange; label: string }[] = [
  { value: "all", label: "All" },
  { value: "1y", label: "1 Year" },
  { value: "6m", label: "6 Mon" },
  { value: "3m", label: "3 Mon" },
  { value: "1m", label: "Month" },
  { value: "1w", label: "Week" },
];

function computeRangeStart(range: TimeRange, lastDate: string): string | null {
  if (range === "all") return null;
  const d = new Date(lastDate + "T00:00:00");
  switch (range) {
    case "1y": d.setFullYear(d.getFullYear() - 1); break;
    case "6m": d.setMonth(d.getMonth() - 6); break;
    case "3m": d.setMonth(d.getMonth() - 3); break;
    case "1m": d.setMonth(d.getMonth() - 1); break;
    case "1w": d.setDate(d.getDate() - 7); break;
  }
  const y = d.getFullYear();
  const m = String(d.getMonth() + 1).padStart(2, "0");
  const day = String(d.getDate()).padStart(2, "0");
  return `${y}-${m}-${day}`;
}

/* ─── Contribution strip helpers ─── */

interface StripCell {
  date: string;
  errors: number;
  severe: number;
  warnings: number;
  lines: number;
  hasData: boolean;
}

function stripCellColor(cell: StripCell): string {
  if (!cell.hasData) return "bg-muted/30";
  if (cell.errors === 0) return "bg-[hsl(var(--muted)/0.3)]";
  if (cell.errors <= 2) return "bg-[hsl(var(--warning)/0.4)]";
  if (cell.errors <= 8) return "bg-[hsl(var(--warning)/0.6)]";
  if (cell.errors <= 16) return "bg-[hsl(var(--destructive)/0.5)]";
  return "bg-[hsl(var(--destructive)/0.7)]";
}

function buildContributionStrip(
  days: DaySummary[],
  selectedType: string,
): { weeks: StripCell[][]; monthLabels: { weekIdx: number; label: string }[] } {
  if (days.length === 0) return { weeks: [], monthLabels: [] };

  const lookup = new Map<string, DaySummary>();
  for (const d of days) lookup.set(d.date, d);

  const sorted = [...days].sort((a, b) => a.date.localeCompare(b.date));
  const first = new Date(sorted[0]!.date + "T00:00:00");
  const last = new Date(sorted[sorted.length - 1]!.date + "T00:00:00");

  const startPad = (first.getDay() + 6) % 7;
  const start = new Date(first);
  start.setDate(start.getDate() - startPad);

  const endPad = (6 - ((last.getDay() + 6) % 7)) % 7;
  const end = new Date(last);
  end.setDate(end.getDate() + endPad);

  const weeks: StripCell[][] = [];
  let week: StripCell[] = [];
  const monthLabels: { weekIdx: number; label: string }[] = [];
  const seen = new Set<string>();

  const cur = new Date(start);
  while (cur <= end) {
    const y = cur.getFullYear();
    const m = String(cur.getMonth() + 1).padStart(2, "0");
    const d = String(cur.getDate()).padStart(2, "0");
    const dateStr = `${y}-${m}-${d}`;
    const dayData = lookup.get(dateStr);
    const typeData = dayData?.types[selectedType];

    week.push({
      date: dateStr,
      errors: typeData?.errors ?? 0,
      severe: typeData?.severe ?? 0,
      warnings: typeData?.warnings ?? 0,
      lines: typeData?.lines ?? 0,
      hasData: typeData !== undefined,
    });

    const ym = `${y}-${m}`;
    if (!seen.has(ym) && cur.getDate() <= 7) {
      seen.add(ym);
      monthLabels.push({ weekIdx: weeks.length, label: ym });
    }

    if (week.length === 7) {
      weeks.push(week);
      week = [];
    }
    cur.setDate(cur.getDate() + 1);
  }
  if (week.length > 0) weeks.push(week);

  return { weeks, monthLabels };
}

function shortMonth(ym: string): string {
  const m = parseInt(ym.split("-")[1] ?? "1", 10);
  return ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"][m - 1] ?? "";
}

/* ─── Date formatting ─── */

function formatDate(dateStr: string): { dow: string; display: string } {
  const d = new Date(dateStr + "T00:00:00");
  const dows = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
  const mons = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
  return {
    dow: dows[d.getDay()] ?? "",
    display: `${mons[d.getMonth()]} ${d.getDate()}, ${d.getFullYear()}`,
  };
}

function typeLabel(name: string): string {
  return TYPE_LABELS[name] ?? name.charAt(0).toUpperCase() + name.slice(1).replace(/-/g, " ");
}

/* ─── Log name generator ─── */

function generateLogName(
  type: string,
  td: { errors: number; severe: number; warnings: number; lines: number; topError?: string | null; topErrorCount?: number },
): string {
  if (type === "commons-daemon") {
    if (td.errors > 0) return td.topError ? `Service Error — ${td.topError}` : "Service Error";
    if (td.lines <= 4) return "Service Running";
    if (td.lines >= 12) return "Service Restart Cycle";
    return "Service Lifecycle";
  }
  // catalina or other
  if (td.errors === 0) {
    if (td.lines === 0) return "No Activity";
    return "Normal Operations";
  }
  if (td.topError) {
    if (td.severe > 4) return `Critical — ${td.topError}`;
    return td.topError;
  }
  if (td.severe > 4) return "Critical Errors";
  if (td.severe > 0) return "Server Errors";
  if (td.warnings > 0) return "Warnings Logged";
  return "Issues Detected";
}

/* ─── Insights: Error incident parser ─── */

interface ErrorIncident {
  startLine: number;
  endLine: number;
  headline: string;
  lineCount: number;
  level: "error" | "warn";
}

interface ErrorPattern {
  message: string;
  count: number;
  firstLine: number;
}

function parseIncidents(lines: LogLine[]): ErrorIncident[] {
  const incidents: ErrorIncident[] = [];
  let current: ErrorIncident | null = null;

  for (const line of lines) {
    if (line.level === "error" || line.level === "warn") {
      const text = line.text.trim();
      const isStackTrace = text.startsWith("at ") || text.startsWith("... ") || text.startsWith("Caused by:");

      if (isStackTrace && current) {
        current.endLine = line.num;
        current.lineCount++;
      } else {
        if (current) incidents.push(current);
        current = {
          startLine: line.num,
          endLine: line.num,
          headline: line.text,
          lineCount: 1,
          level: line.level,
        };
      }
    } else {
      if (current) {
        incidents.push(current);
        current = null;
      }
    }
  }
  if (current) incidents.push(current);
  return incidents;
}

function normalizeErrorKey(line: string): string {
  // Extract exception class name if present
  const excMatch = line.match(/(?:[\w.]+\.)?(\w+(?:Exception|Error))/);
  if (excMatch) return excMatch[1]!;
  // Strip timestamps and paths for SEVERE messages
  let msg = line.replace(/^\w{3}\s+\d{1,2},\s+\d{4}\s+[\d:]+\s+[AP]M\s+/, "");
  msg = msg.replace(/^(SEVERE|ERROR|FATAL|WARNING|WARN):\s*/, "");
  msg = msg.replace(/\[[^\]]{10,}\]/g, "[…]");
  return msg.length > 80 ? msg.substring(0, 80) : msg;
}

function extractPatterns(incidents: ErrorIncident[]): ErrorPattern[] {
  const map = new Map<string, { count: number; firstLine: number }>();
  for (const inc of incidents) {
    const key = normalizeErrorKey(inc.headline);
    const existing = map.get(key);
    if (existing) {
      existing.count++;
    } else {
      map.set(key, { count: 1, firstLine: inc.startLine });
    }
  }
  return Array.from(map.entries())
    .map(([message, data]) => ({ message, ...data }))
    .sort((a, b) => b.count - a.count);
}

/* ─── Insights: Commons-daemon service event parser ─── */

interface ServiceEvent {
  time: string;
  type: "start" | "started" | "stop" | "stopped" | "info";
  message: string;
  lineNum: number;
  startupMs?: number;
}

function parseServiceEvents(lines: LogLine[]): ServiceEvent[] {
  const events: ServiceEvent[] = [];
  const pattern = /^\[(\d{4}-\d{2}-\d{2}\s+\d{2}:\d{2}:\d{2})\]\s+\[\w+\]\s+(.+)$/;

  for (const line of lines) {
    const match = line.text.match(pattern);
    if (!match) continue;
    const [, time, message] = match;
    if (!time || !message) continue;

    let type: ServiceEvent["type"] = "info";
    let startupMs: number | undefined;

    if (message.includes("Starting service")) {
      type = "start";
    } else if (message.startsWith("Service started in")) {
      type = "started";
      const msMatch = message.match(/(\d+)\s*ms/);
      if (msMatch) startupMs = parseInt(msMatch[1]!, 10);
    } else if (message.includes("Stopping service")) {
      type = "stop";
    } else if (message.includes("Service stopped")) {
      type = "stopped";
    } else {
      continue; // Skip non-lifecycle events like "procrun started"
    }

    events.push({ time, type, message, lineNum: line.num, startupMs });
  }
  return events;
}

function formatTime(fullTime: string): string {
  // "2026-03-01 00:01:41" → "00:01"
  const parts = fullTime.split(" ");
  if (parts.length >= 2) {
    const timeParts = parts[1]!.split(":");
    return `${timeParts[0]}:${timeParts[1]}`;
  }
  return fullTime;
}

/* ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ */

/* ─── Per-company Activity Logs component ─── */

function ActivityLogsTab() {
  const [page, setPage] = useState(1);
  const [statusFilter, setStatusFilter] = useState("all");
  const pageSize = 25;

  const { data, isLoading, isFetching, refetch } = useTransactions(page, pageSize);

  const transactions = data?.data?.transactions ?? [];
  const totalCount = data?.data?.pagination?.total_count ?? 0;
  const totalPages = Math.ceil(totalCount / pageSize);

  function statusBadge(status: string) {
    const s = status?.toLowerCase() ?? "";
    if (s === "success" || s === "completed")
      return <span className="inline-flex items-center gap-1 text-xs text-[hsl(var(--success))]"><CheckCircle2 className="w-3 h-3" />Success</span>;
    if (s === "error" || s === "failed")
      return <span className="inline-flex items-center gap-1 text-xs text-[hsl(var(--destructive))]"><XCircle className="w-3 h-3" />Failed</span>;
    if (s === "running" || s === "in_progress")
      return <span className="inline-flex items-center gap-1 text-xs text-[hsl(var(--primary))]"><Clock className="w-3 h-3" />Running</span>;
    return <span className="text-xs text-muted-foreground">{status ?? "—"}</span>;
  }

  const filtered = statusFilter === "all"
    ? transactions
    : transactions.filter((t) => (t.status ?? "").toLowerCase().startsWith(statusFilter));

  return (
    <div className="space-y-4">
      <div className="flex items-center justify-between gap-3 flex-wrap">
        <div className="flex items-center gap-2">
          <ListOrdered className="w-4 h-4 text-muted-foreground" />
          <span className="text-sm font-medium">
            {totalCount > 0 ? `${totalCount} transaction${totalCount === 1 ? "" : "s"}` : "No transactions"}
          </span>
          <span className="text-xs text-muted-foreground">for your company</span>
        </div>
        <div className="flex items-center gap-2">
          <select
            className="text-xs bg-[hsl(var(--input))] border border-[hsl(var(--border))] rounded px-2 py-1 text-foreground"
            value={statusFilter}
            onChange={(e) => { setStatusFilter(e.target.value); setPage(1); }}
          >
            <option value="all">All statuses</option>
            <option value="success">Success</option>
            <option value="error">Failed</option>
            <option value="running">Running</option>
          </select>
          <Button variant="ghost" size="icon" className="h-7 w-7" onClick={() => refetch()} disabled={isFetching}>
            <RefreshCw className={cn("w-3.5 h-3.5", isFetching && "animate-spin")} />
          </Button>
        </div>
      </div>

      {isLoading ? (
        <div className="flex items-center justify-center py-12 text-muted-foreground gap-2">
          <Loader2 className="w-4 h-4 animate-spin" /><span className="text-sm">Loading…</span>
        </div>
      ) : filtered.length === 0 ? (
        <div className="text-center py-12 text-muted-foreground text-sm">
          No transaction logs found for your account.
        </div>
      ) : (
        <div className="rounded-lg border border-[hsl(var(--border))] bg-[hsl(var(--card))] shadow-sm overflow-hidden">
          <table className="w-full text-xs">
            <thead>
              <tr className="border-b border-[hsl(var(--border))] bg-muted/80">
                <th className="px-3 py-2 text-left font-semibold text-foreground/70">Time</th>
                <th className="px-3 py-2 text-left font-semibold text-foreground/70">Flow</th>
                <th className="px-3 py-2 text-left font-semibold text-foreground/70">Status</th>
                <th className="px-3 py-2 text-left font-semibold text-foreground/70 hidden md:table-cell">Records</th>
                <th className="px-3 py-2 text-left font-semibold text-foreground/70 hidden lg:table-cell">Duration</th>
                <th className="px-3 py-2 text-left font-semibold text-foreground/70 hidden lg:table-cell">Error</th>
              </tr>
            </thead>
            <tbody>
              {filtered.map((tx, i) => {
                const ts = tx.started_at ? new Date(tx.started_at).toLocaleString() : "—";
                const dur = tx.duration_ms != null ? `${(tx.duration_ms / 1000).toFixed(1)}s` : "—";
                return (
                  <tr key={tx.execution_id ?? i}
                    className={cn("border-b border-[hsl(var(--border)/0.5)] hover:bg-muted/30",
                      i % 2 === 1 && "bg-muted/40")}>
                    <td className="px-3 py-2 text-muted-foreground whitespace-nowrap">{ts}</td>
                    <td className="px-3 py-2 font-medium max-w-[180px] truncate">{tx.flow_name ?? "—"}</td>
                    <td className="px-3 py-2">{statusBadge(tx.status)}</td>
                    <td className="px-3 py-2 text-muted-foreground hidden md:table-cell">{tx.records_processed ?? "—"}</td>
                    <td className="px-3 py-2 text-muted-foreground hidden lg:table-cell">{dur}</td>
                    <td className="px-3 py-2 text-[hsl(var(--destructive)/0.8)] hidden lg:table-cell max-w-[200px] truncate">
                      {tx.error_message ?? ""}
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      )}

      {totalPages > 1 && (
        <div className="flex items-center justify-between text-xs text-muted-foreground">
          <span>Page {page} of {totalPages}</span>
          <div className="flex gap-1">
            <Button variant="outline" size="sm" className="h-7 px-2" disabled={page <= 1} onClick={() => setPage(p => p - 1)}>
              <ChevronLeft className="w-3 h-3" />
            </Button>
            <Button variant="outline" size="sm" className="h-7 px-2" disabled={page >= totalPages} onClick={() => setPage(p => p + 1)}>
              <ChevronRight className="w-3 h-3" />
            </Button>
          </div>
        </div>
      )}
    </div>
  );
}

export function LoggingPage() {
  useDocumentTitle("System Logging");

  const [view, setView] = useState<ViewMode>("list");
  const [selectedDate, setSelectedDate] = useState<string | null>(null);
  const [selectedType, setSelectedType] = useState("catalina");
  const [severityFilter, setSeverityFilter] = useState<SeverityFilter>("all");
  const [searchFilter, setSearchFilter] = useState("");
  const [sortBy, setSortBy] = useState<SortField>("date");
  const [timeRange, setTimeRange] = useState<TimeRange>("all");
  const [detailLevelFilter, setDetailLevelFilter] = useState<"all" | "error" | "warn">("all");
  const [detailSearch, setDetailSearch] = useState("");
  const [expandedIncidents, setExpandedIncidents] = useState<Set<number>>(new Set());

  const stripRef = useRef<HTMLDivElement>(null);

  /* ── Data hooks ── */

  const { data: filesData } = useLogFiles();
  const {
    data: summaryData,
    isLoading: summaryLoading,
    refetch: refetchSummary,
    isFetching: summaryFetching,
  } = useLogSummary();
  const { data: contentData, isLoading: contentLoading } = useLogContent(selectedDate, selectedType);

  /* ── Discover available log types dynamically ── */

  const availableTypes = useMemo(() => {
    if (!filesData?.types) return [{ value: "catalina", label: "Catalina" }];
    return Object.entries(filesData.types)
      .filter(([, info]) => info.nonEmpty > 0)
      .map(([name]) => ({ value: name, label: typeLabel(name) }));
  }, [filesData]);

  /* ── Derived data ── */

  const lastDate = useMemo(() => {
    const days = summaryData?.days;
    if (!days || days.length === 0) return "";
    return days.reduce((max, d) => (d.date > max ? d.date : max), days[0]!.date);
  }, [summaryData]);

  const firstDate = useMemo(() => {
    const days = summaryData?.days;
    if (!days || days.length === 0) return "";
    return days.reduce((min, d) => (d.date < min ? d.date : min), days[0]!.date);
  }, [summaryData]);

  const rangeFilteredDays = useMemo(() => {
    const days = summaryData?.days ?? [];
    const rangeStart = computeRangeStart(timeRange, lastDate);
    if (!rangeStart) return days;
    return days.filter((d) => d.date >= rangeStart);
  }, [summaryData, timeRange, lastDate]);

  const stripData = useMemo(
    () => buildContributionStrip(rangeFilteredDays, selectedType),
    [rangeFilteredDays, selectedType],
  );

  useEffect(() => {
    if (stripRef.current && stripData.weeks.length > 0) {
      stripRef.current.scrollLeft = stripRef.current.scrollWidth;
    }
  }, [stripData]);

  /* ── Aggregate stats ── */

  const stats = useMemo(() => {
    let total = 0;
    let withIssues = 0;
    let clean = 0;
    let totalErrors = 0;
    let totalSevere = 0;
    let totalWarnings = 0;
    let totalLines = 0;
    for (const day of rangeFilteredDays) {
      const td = day.types[selectedType];
      if (!td) continue;
      total++;
      totalLines += td.lines;
      totalErrors += td.errors;
      totalSevere += td.severe;
      totalWarnings += td.warnings;
      if (td.errors > 0) withIssues++;
      else clean++;
    }
    return { total, withIssues, clean, totalErrors, totalSevere, totalWarnings, totalLines };
  }, [rangeFilteredDays, selectedType]);

  /* ── Filtered + sorted day list ── */

  const filteredDays = useMemo(() => {
    return rangeFilteredDays
      .filter((day) => {
        const td = day.types[selectedType];
        if (!td) return false;
        if (severityFilter === "issues" && td.errors === 0) return false;
        if (severityFilter === "clean" && td.errors > 0) return false;
        if (searchFilter) {
          const q = searchFilter.toLowerCase();
          const name = generateLogName(selectedType, td).toLowerCase();
          if (!day.date.includes(searchFilter) && !name.includes(q) && !(td.topError?.toLowerCase().includes(q))) return false;
        }
        return true;
      })
      .sort((a, b) => {
        const aType = a.types[selectedType];
        const bType = b.types[selectedType];
        if (sortBy === "errors") {
          return (bType?.errors ?? 0) - (aType?.errors ?? 0);
        }
        if (sortBy === "errorRate") {
          const aRate = aType && aType.lines > 0 ? aType.errors / aType.lines : 0;
          const bRate = bType && bType.lines > 0 ? bType.errors / bType.lines : 0;
          return bRate - aRate;
        }
        return b.date.localeCompare(a.date);
      });
  }, [rangeFilteredDays, selectedType, severityFilter, searchFilter, sortBy]);

  /* ── Detail view: prev/next navigation ── */

  const dayNavigation = useMemo(() => {
    if (!selectedDate) return { prev: null as string | null, next: null as string | null };
    const sorted = rangeFilteredDays
      .filter((d) => d.types[selectedType])
      .sort((a, b) => a.date.localeCompare(b.date));
    const idx = sorted.findIndex((d) => d.date === selectedDate);
    return {
      prev: idx > 0 ? sorted[idx - 1]!.date : null,
      next: idx >= 0 && idx < sorted.length - 1 ? sorted[idx + 1]!.date : null,
    };
  }, [rangeFilteredDays, selectedType, selectedDate]);

  /* ── Filtered log lines (detail raw view) ── */

  const filteredLines = useMemo(() => {
    if (!contentData?.lines) return [];
    return contentData.lines.filter((line) => {
      if (detailLevelFilter === "error" && line.level !== "error") return false;
      if (detailLevelFilter === "warn" && line.level !== "warn" && line.level !== "error") return false;
      if (detailSearch && !line.text.toLowerCase().includes(detailSearch.toLowerCase())) return false;
      return true;
    });
  }, [contentData, detailLevelFilter, detailSearch]);

  /* ── Detail view: parsed insights ── */

  const incidents = useMemo(() => {
    if (!contentData?.lines) return [];
    return parseIncidents(contentData.lines);
  }, [contentData]);

  const patterns = useMemo(() => extractPatterns(incidents), [incidents]);

  const serviceEvents = useMemo(() => {
    if (!contentData?.lines || selectedType !== "commons-daemon") return [];
    return parseServiceEvents(contentData.lines);
  }, [contentData, selectedType]);

  const detailLineCounts = useMemo(() => {
    if (!contentData?.lines) return { total: 0, errors: 0, warns: 0 };
    let errors = 0;
    let warns = 0;
    for (const line of contentData.lines) {
      if (line.level === "error") errors++;
      else if (line.level === "warn") warns++;
    }
    return { total: contentData.lines.length, errors, warns };
  }, [contentData]);

  function openDay(date: string) {
    setSelectedDate(date);
    setDetailSearch("");
    setDetailLevelFilter("all");
    setExpandedIncidents(new Set());
    setView("detail");
  }

  function navigateDay(date: string | null) {
    if (!date) return;
    setSelectedDate(date);
    setDetailSearch("");
    setDetailLevelFilter("all");
    setExpandedIncidents(new Set());
  }

  function backToList() {
    setView("list");
    setSelectedDate(null);
  }

  function toggleIncident(idx: number) {
    setExpandedIncidents((prev) => {
      const next = new Set(prev);
      if (next.has(idx)) next.delete(idx);
      else next.add(idx);
      return next;
    });
  }

  /* ━━━━━━━━━━ RENDER ━━━━━━━━━━ */

  return (
    <TooltipProvider delayDuration={150}>
      <div className="space-y-5">
        {/* ── Header ── */}
        <div className="flex items-start justify-between gap-4 flex-wrap">
          <div className="flex items-center gap-3">
            {view === "detail" && (
              <Button variant="ghost" size="sm" onClick={backToList} className="rounded-full -ml-2">
                <ArrowLeft className="w-4 h-4" />
              </Button>
            )}
            <div>
              <h1 className="text-2xl font-semibold">
                {view === "list" ? "System Logging" : "Log Viewer"}
              </h1>
              <p className="text-sm text-muted-foreground mt-0.5">
                {view === "list"
                  ? "Browse and filter production server logs."
                  : `${selectedType}.${selectedDate}.log`}
              </p>
            </div>
          </div>
          <div className="flex items-center gap-2">
            {view === "list" && (
              <Button
                variant="outline"
                size="sm"
                onClick={() => void refetchSummary()}
                disabled={summaryFetching}
                className="rounded-full"
              >
                <RefreshCw className={cn("w-3 h-3", summaryFetching && "animate-spin")} />
                Refresh
              </Button>
            )}
            {view === "detail" && selectedDate && (
              <div className="flex items-center gap-1">
                <Tooltip>
                  <TooltipTrigger asChild>
                    <Button variant="outline" size="icon" className="rounded-full h-7 w-7" disabled={!dayNavigation.prev} onClick={() => navigateDay(dayNavigation.prev)}>
                      <ChevronLeft className="w-3.5 h-3.5" />
                    </Button>
                  </TooltipTrigger>
                  <TooltipContent side="bottom" className="text-[11px]">{dayNavigation.prev ?? "No earlier logs"}</TooltipContent>
                </Tooltip>
                <Tooltip>
                  <TooltipTrigger asChild>
                    <Button variant="outline" size="icon" className="rounded-full h-7 w-7" disabled={!dayNavigation.next} onClick={() => navigateDay(dayNavigation.next)}>
                      <ChevronRight className="w-3.5 h-3.5" />
                    </Button>
                  </TooltipTrigger>
                  <TooltipContent side="bottom" className="text-[11px]">{dayNavigation.next ?? "No later logs"}</TooltipContent>
                </Tooltip>
              </div>
            )}
            <Button variant="outline" size="sm" className="rounded-full" asChild>
              <a href="/iw-business-daemon/Logging.jsp">
                <FileText className="w-3 h-3" />
                Classic
                <ExternalLink className="w-3 h-3" />
              </a>
            </Button>
          </div>
        </div>

        {/* ── Top-level tab: Activity Logs (per-company) vs Server Logs (admin) ── */}
        <Tabs defaultValue="activity" className="w-full">
          <TabsList className="mb-4">
            <TabsTrigger value="activity" className="flex items-center gap-1.5">
              <Activity className="w-3.5 h-3.5" />
              Activity Logs
            </TabsTrigger>
            <TabsTrigger value="server" className="flex items-center gap-1.5">
              <Server className="w-3.5 h-3.5" />
              Server Logs
            </TabsTrigger>
          </TabsList>

          <TabsContent value="activity">
            <ActivityLogsTab />
          </TabsContent>

          <TabsContent value="server">

        {/* ━━━━━━━━━━ LIST VIEW ━━━━━━━━━━ */}
        {view === "list" && (
          <>
            {summaryLoading ? (
              <div className="flex justify-center py-16">
                <Loader2 className="w-6 h-6 animate-spin text-muted-foreground" />
              </div>
            ) : (
              <>
                {/* ── Contribution heatmap + metadata ── */}
                <div className="glass-panel rounded-[var(--radius)] p-4 space-y-3">
                  <div className="flex items-center justify-between flex-wrap gap-2">
                    <div className="flex items-center gap-2">
                      <Server className="w-4 h-4 text-[hsl(var(--success))]" />
                      <span className="text-xs font-medium">Activity Overview</span>
                      <div className="flex items-center gap-0.5 ml-2">
                        {TIME_RANGES.map((tr) => (
                          <button
                            key={tr.value}
                            onClick={() => setTimeRange(tr.value)}
                            className={cn(
                              "px-2 py-0.5 text-[10px] rounded-full transition-colors",
                              timeRange === tr.value
                                ? "bg-[hsl(var(--primary)/0.15)] text-[hsl(var(--primary))] font-medium"
                                : "text-muted-foreground/60 hover:text-muted-foreground",
                            )}
                          >
                            {tr.label}
                          </button>
                        ))}
                      </div>
                    </div>
                    <div className="flex items-center gap-4 text-[11px] text-muted-foreground">
                      {firstDate && lastDate && (
                        <span className="inline-flex items-center gap-1">
                          <Calendar className="w-3 h-3" />
                          {formatDate(firstDate).display} — {formatDate(lastDate).display}
                        </span>
                      )}
                      {filesData && (
                        <span className="inline-flex items-center gap-1">
                          <HardDrive className="w-3 h-3" />
                          <b className="text-foreground tabular-nums">{filesData.nonEmptyFiles}</b> files with data
                        </span>
                      )}
                    </div>
                  </div>

                  <div className="flex items-center gap-4 text-[11px] text-muted-foreground flex-wrap">
                    <span><b className="text-foreground tabular-nums">{stats.total}</b> days</span>
                    <span><b className="text-[hsl(var(--destructive))] tabular-nums">{stats.withIssues}</b> with issues</span>
                    <span><b className="text-[hsl(var(--success))] tabular-nums">{stats.clean}</b> clean</span>
                    <span className="text-muted-foreground/30">|</span>
                    <span><b className="text-foreground tabular-nums">{stats.totalLines.toLocaleString()}</b> total lines</span>
                    {stats.totalSevere > 0 && (
                      <span><b className="text-[hsl(var(--destructive))] tabular-nums">{stats.totalSevere.toLocaleString()}</b> errors</span>
                    )}
                    {stats.totalWarnings > 0 && (
                      <span><b className="text-[hsl(var(--warning))] tabular-nums">{stats.totalWarnings.toLocaleString()}</b> warnings</span>
                    )}
                  </div>

                  {/* Contribution strip */}
                  <div className="relative">
                    <div ref={stripRef} className="overflow-x-auto pb-4 scrollbar-thin">
                      <div className="inline-flex gap-[2px] min-w-0">
                        <div className="flex flex-col gap-[2px] pr-1 shrink-0">
                          {["", "M", "", "W", "", "F", ""].map((label, i) => (
                            <div key={i} className="h-[14px] text-[9px] text-muted-foreground/50 leading-[14px] flex items-center">{label}</div>
                          ))}
                        </div>
                        {stripData.weeks.map((week, wi) => (
                          <div key={wi} className="flex flex-col gap-[2px]">
                            {week.map((cell, di) => (
                              <Tooltip key={di}>
                                <TooltipTrigger asChild>
                                  <button
                                    onClick={() => cell.hasData && openDay(cell.date)}
                                    className={cn("w-[14px] h-[14px] rounded-[3px] transition-colors", stripCellColor(cell), cell.hasData && "cursor-pointer hover:brightness-150", !cell.hasData && "cursor-default")}
                                  />
                                </TooltipTrigger>
                                <TooltipContent side="top" className="text-[11px] py-1 px-2">
                                  <div className="font-mono">{cell.date}</div>
                                  {cell.hasData ? (
                                    <div className="mt-0.5">
                                      <span className="text-muted-foreground">{cell.lines.toLocaleString()} lines</span>
                                      {cell.errors > 0 ? (
                                        <span className="ml-1.5 text-[hsl(var(--destructive))]">{cell.severe} errors</span>
                                      ) : (
                                        <span className="text-[hsl(var(--success))] ml-1.5">clean</span>
                                      )}
                                    </div>
                                  ) : (
                                    <div className="opacity-50 mt-0.5">no data</div>
                                  )}
                                </TooltipContent>
                              </Tooltip>
                            ))}
                          </div>
                        ))}
                      </div>
                      <div className="flex mt-1.5" style={{ paddingLeft: 20 }}>
                        {stripData.monthLabels.map((ml, i) => {
                          const next = stripData.monthLabels[i + 1];
                          const span = (next ? next.weekIdx - ml.weekIdx : 4) * 16;
                          return <span key={ml.label} className="text-[9px] text-muted-foreground/50 shrink-0" style={{ width: span }}>{shortMonth(ml.label)}</span>;
                        })}
                      </div>
                    </div>
                  </div>

                  <div className="flex items-center gap-3 pt-1 border-t border-[hsl(var(--border)/0.3)]">
                    <span className="text-[9px] text-muted-foreground/50">Less</span>
                    {["bg-[hsl(var(--muted)/0.3)]", "bg-[hsl(var(--warning)/0.4)]", "bg-[hsl(var(--warning)/0.6)]", "bg-[hsl(var(--destructive)/0.5)]", "bg-[hsl(var(--destructive)/0.7)]"].map((cls, i) => (
                      <div key={i} className={cn("w-[14px] h-[14px] rounded-[3px]", cls)} />
                    ))}
                    <span className="text-[9px] text-muted-foreground/50">More</span>
                  </div>
                </div>

                {/* ── Filter bar ── */}
                <div className="flex items-center justify-between gap-3 flex-wrap">
                  <div className="flex items-center gap-2">
                    {availableTypes.map((t) => (
                      <button key={t.value} onClick={() => setSelectedType(t.value)} className={cn("px-3 py-1.5 text-xs rounded-full border transition-colors", selectedType === t.value ? "border-[hsl(var(--primary))] bg-[hsl(var(--primary)/0.15)] text-foreground" : "border-[hsl(var(--border))] text-muted-foreground hover:text-foreground")}>
                        {t.label}
                      </button>
                    ))}
                    <div className="w-px h-5 bg-[hsl(var(--border))] mx-1" />
                    {([
                      { value: "all" as const, label: "All", count: stats.total, color: "primary" },
                      { value: "issues" as const, label: "Issues", count: stats.withIssues, color: "destructive" },
                      { value: "clean" as const, label: "Clean", count: stats.clean, color: "success" },
                    ]).map((s) => (
                      <button
                        key={s.value}
                        onClick={() => setSeverityFilter(s.value)}
                        className={cn(
                          "px-2.5 py-1.5 text-xs rounded-full border transition-colors inline-flex items-center gap-1.5",
                          severityFilter === s.value
                            ? s.color === "destructive" ? "border-[hsl(var(--destructive)/0.5)] bg-[hsl(var(--destructive)/0.1)] text-[hsl(var(--destructive))]"
                              : s.color === "success" ? "border-[hsl(var(--success)/0.5)] bg-[hsl(var(--success)/0.1)] text-[hsl(var(--success))]"
                                : "border-[hsl(var(--primary))] bg-[hsl(var(--primary)/0.15)] text-foreground"
                            : "border-[hsl(var(--border))] text-muted-foreground hover:text-foreground",
                        )}
                      >
                        {s.label}
                        <span className={cn("text-[10px] tabular-nums", severityFilter === s.value ? "opacity-80" : "opacity-40")}>{s.count}</span>
                      </button>
                    ))}
                  </div>
                  <div className="flex items-center gap-3">
                    <button
                      onClick={() => setSortBy((s) => s === "date" ? "errors" : s === "errors" ? "errorRate" : "date")}
                      className="flex items-center gap-1 text-[11px] text-muted-foreground hover:text-foreground transition-colors"
                    >
                      <ArrowUpDown className="w-3 h-3" />
                      {sortBy === "date" ? "By date" : sortBy === "errors" ? "By errors" : "By rate"}
                    </button>
                    <div className="relative">
                      <Search className="absolute left-2.5 top-1/2 -translate-y-1/2 w-3.5 h-3.5 text-muted-foreground" />
                      <input value={searchFilter} onChange={(e) => setSearchFilter(e.target.value)} placeholder="Search logs..." className="w-36 bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-foreground rounded-full pl-8 pr-3 py-1.5 text-xs outline-none focus:ring-2 focus:ring-[hsl(var(--primary)/0.4)] transition-shadow" />
                    </div>
                    <span className="text-[11px] text-muted-foreground tabular-nums">{filteredDays.length} entries</span>
                  </div>
                </div>

                {/* ── Day list table (2 new columns: Error % + Top Issue) ── */}
                <div className="border border-[hsl(var(--border))] rounded-[14px] bg-[hsl(var(--card))] shadow-sm overflow-hidden">
                  <div className="bg-muted/80 border-b border-[hsl(var(--border))]">
                    <div className="grid grid-cols-[minmax(0,1.2fr)_minmax(0,0.8fr)_60px_50px_minmax(0,160px)_auto_28px] max-md:grid-cols-[minmax(0,1fr)_90px_50px_auto_28px] px-4 py-2 text-[11px] font-semibold text-foreground/70">
                      <span>Event</span>
                      <span className="max-md:hidden">Date</span>
                      <span className="text-right">Lines</span>
                      <span className="text-right">Err%</span>
                      <span className="max-md:hidden truncate pl-3">Top Issue</span>
                      <span className="text-right pr-2 min-w-[90px]">Status</span>
                      <span />
                    </div>
                  </div>
                  <div className="max-h-[480px] overflow-y-auto">
                    {filteredDays.length === 0 ? (
                      <div className="py-12 text-center text-sm text-muted-foreground">
                        <Filter className="w-5 h-5 mx-auto mb-2 opacity-40" />
                        No log files match the current filters.
                      </div>
                    ) : (
                      filteredDays.map((day) => {
                        const td = day.types[selectedType]!;
                        const { dow, display } = formatDate(day.date);
                        const logName = generateLogName(selectedType, td);
                        const hasErrors = td.severe > 0;
                        const hasWarnings = td.warnings > 0;
                        const errorRate = td.lines > 0 ? (td.errors / td.lines) * 100 : 0;

                        return (
                          <button
                            key={day.date}
                            onClick={() => openDay(day.date)}
                            className={cn(
                              "w-full grid grid-cols-[minmax(0,1.2fr)_minmax(0,0.8fr)_60px_50px_minmax(0,160px)_auto_28px] max-md:grid-cols-[minmax(0,1fr)_90px_50px_auto_28px] items-center px-4 py-2.5",
                              "border-b border-[hsl(var(--border)/0.5)] text-left",
                              "hover:bg-muted/30 transition-colors group",
                            )}
                          >
                            {/* Name */}
                            <div className="flex items-center gap-2 min-w-0">
                              <Activity className={cn("w-3.5 h-3.5 shrink-0", hasErrors ? "text-[hsl(var(--destructive))]" : "text-[hsl(var(--success)/0.5)]")} />
                              <span className={cn("text-sm truncate", hasErrors && "text-foreground font-medium")}>{logName}</span>
                            </div>

                            {/* Date (hidden on mobile — shown in Name tooltip instead) */}
                            <div className="max-md:hidden flex items-center gap-1.5 min-w-0">
                              <span className="text-[10px] text-muted-foreground/50 w-7 shrink-0">{dow}</span>
                              <span className="text-xs text-muted-foreground tabular-nums">{display}</span>
                            </div>

                            {/* Lines */}
                            <span className="text-right text-xs text-muted-foreground tabular-nums">{td.lines.toLocaleString()}</span>

                            {/* Error % */}
                            <span className={cn("text-right text-xs tabular-nums", errorRate > 0 ? "text-[hsl(var(--destructive))]" : "text-muted-foreground/30")}>
                              {errorRate > 0 ? `${errorRate.toFixed(1)}%` : "—"}
                            </span>

                            {/* Top Issue (hidden on mobile) */}
                            <div className="max-md:hidden pl-3 min-w-0">
                              {td.topError ? (
                                <Tooltip>
                                  <TooltipTrigger asChild>
                                    <span className="text-[11px] text-muted-foreground truncate block max-w-full">
                                      {td.topError}
                                      {td.topErrorCount && td.topErrorCount > 1 && (
                                        <span className="text-muted-foreground/40 ml-1">×{td.topErrorCount}</span>
                                      )}
                                    </span>
                                  </TooltipTrigger>
                                  <TooltipContent side="top" className="text-[11px] max-w-[300px]">
                                    {td.topError} ({td.topErrorCount ?? 1}×)
                                  </TooltipContent>
                                </Tooltip>
                              ) : (
                                <span className="text-xs text-muted-foreground/20">—</span>
                              )}
                            </div>

                            {/* Status */}
                            <div className="flex items-center justify-end gap-1.5 pr-2 min-w-[90px]">
                              {hasErrors && (
                                <Badge variant="destructive" className="text-[10px] px-1.5 py-0 h-[18px]">
                                  {td.severe} {td.severe === 1 ? "error" : "errors"}
                                </Badge>
                              )}
                              {hasWarnings && (
                                <Badge variant="warning" className="text-[10px] px-1.5 py-0 h-[18px]">
                                  {td.warnings}
                                </Badge>
                              )}
                              {!hasErrors && !hasWarnings && (
                                <span className="text-[10px] text-[hsl(var(--success)/0.6)]">clean</span>
                              )}
                            </div>

                            {/* Arrow */}
                            <ChevronRight className="w-3.5 h-3.5 text-muted-foreground/30 group-hover:text-muted-foreground transition-colors" />
                          </button>
                        );
                      })
                    )}
                  </div>
                </div>
              </>
            )}
          </>
        )}

        {/* ━━━━━━━━━━ DETAIL VIEW ━━━━━━━━━━ */}
        {view === "detail" && selectedDate && (
          <>
            {/* Log type tabs */}
            <div className="flex gap-2">
              {availableTypes.map((t) => (
                <button key={t.value} onClick={() => setSelectedType(t.value)} className={cn("px-3 py-1.5 text-xs rounded-full border transition-colors", selectedType === t.value ? "border-[hsl(var(--primary))] bg-[hsl(var(--primary)/0.15)] text-foreground" : "border-[hsl(var(--border))] text-muted-foreground hover:text-foreground")}>
                  {t.label}
                </button>
              ))}
            </div>

            {contentLoading ? (
              <div className="flex justify-center py-16">
                <Loader2 className="w-6 h-6 animate-spin text-muted-foreground" />
              </div>
            ) : contentData ? (
              <Tabs defaultValue="insights">
                <TabsList>
                  <TabsTrigger value="insights">
                    <Activity className="w-3.5 h-3.5" />
                    Insights
                  </TabsTrigger>
                  <TabsTrigger value="raw">
                    <FileText className="w-3.5 h-3.5" />
                    Raw Log
                    <span className="text-[10px] opacity-50 tabular-nums ml-0.5">{contentData.totalLines.toLocaleString()}</span>
                  </TabsTrigger>
                </TabsList>

                {/* ── INSIGHTS TAB ── */}
                <TabsContent value="insights" className="space-y-4">
                  {/* Stats cards */}
                  <div className="grid grid-cols-4 gap-3 max-sm:grid-cols-2">
                    <InsightCard icon={Hash} label="Total Lines" value={contentData.totalLines.toLocaleString()} />
                    <InsightCard
                      icon={AlertCircle}
                      label="Errors"
                      value={contentData.severeCount > 0 ? String(contentData.severeCount) : "0"}
                      variant={contentData.severeCount > 0 ? "destructive" : "success"}
                    />
                    <InsightCard
                      icon={Percent}
                      label="Error Rate"
                      value={contentData.totalLines > 0 ? `${((contentData.errorCount / contentData.totalLines) * 100).toFixed(1)}%` : "0%"}
                      variant={contentData.errorCount > 0 ? "destructive" : "success"}
                    />
                    <InsightCard
                      icon={HardDrive}
                      label="File Size"
                      value={contentData.fileSize < 1024 ? `${contentData.fileSize} B` : `${(contentData.fileSize / 1024).toFixed(1)} KB`}
                    />
                  </div>

                  {selectedType === "commons-daemon" ? (
                    /* ── Service Daemon: Lifecycle timeline ── */
                    <div className="glass-panel rounded-[var(--radius)] p-4 space-y-3">
                      <div className="flex items-center justify-between">
                        <p className="text-xs font-medium">Service Lifecycle</p>
                        <div className="flex items-center gap-3 text-[11px] text-muted-foreground">
                          <span className="inline-flex items-center gap-1"><Play className="w-3 h-3 text-[hsl(var(--success))]" /> {serviceEvents.filter((e) => e.type === "started").length} starts</span>
                          <span className="inline-flex items-center gap-1"><Square className="w-3 h-3 text-[hsl(var(--destructive))]" /> {serviceEvents.filter((e) => e.type === "stopped").length} stops</span>
                          <span className="inline-flex items-center gap-1"><RotateCw className="w-3 h-3 text-[hsl(var(--warning))]" /> {serviceEvents.filter((e) => e.type === "start").length} restarts</span>
                        </div>
                      </div>
                      {serviceEvents.length === 0 ? (
                        <p className="text-xs text-muted-foreground py-4 text-center">No lifecycle events found.</p>
                      ) : (
                        <div className="space-y-0.5">
                          {serviceEvents.map((evt, i) => (
                            <div key={i} className={cn("flex items-center gap-3 py-1.5 px-3 rounded-lg text-xs", evt.type === "started" && "bg-[hsl(var(--success)/0.06)]", evt.type === "stopped" && "bg-[hsl(var(--destructive)/0.04)]")}>
                              <span className="text-muted-foreground/50 tabular-nums font-mono w-12 shrink-0">{formatTime(evt.time)}</span>
                              {evt.type === "start" && <Play className="w-3 h-3 text-[hsl(var(--primary))] shrink-0" />}
                              {evt.type === "started" && <Play className="w-3 h-3 text-[hsl(var(--success))] shrink-0" />}
                              {evt.type === "stop" && <Square className="w-3 h-3 text-[hsl(var(--warning))] shrink-0" />}
                              {evt.type === "stopped" && <Square className="w-3 h-3 text-[hsl(var(--destructive))] shrink-0" />}
                              <span className="text-muted-foreground">{evt.message}</span>
                              {evt.startupMs !== undefined && (
                                <Badge variant="default" className="text-[9px] px-1.5 py-0 h-[16px] ml-auto shrink-0">
                                  {evt.startupMs}ms
                                </Badge>
                              )}
                              <span className="text-muted-foreground/30 tabular-nums text-[10px] shrink-0">L{evt.lineNum}</span>
                            </div>
                          ))}
                        </div>
                      )}
                    </div>
                  ) : (
                    /* ── Catalina: Error patterns + incidents ── */
                    <>
                      {/* Error patterns */}
                      {patterns.length > 0 && (
                        <div className="glass-panel rounded-[var(--radius)] p-4 space-y-3">
                          <div className="flex items-center justify-between">
                            <p className="text-xs font-medium">Error Patterns</p>
                            <span className="text-[11px] text-muted-foreground">{patterns.length} unique</span>
                          </div>
                          <div className="space-y-1">
                            {patterns.slice(0, 8).map((p, i) => (
                              <div key={i} className="flex items-center gap-2 py-1.5 px-3 rounded-lg bg-muted/40 text-xs">
                                <AlertCircle className="w-3 h-3 text-[hsl(var(--destructive))] shrink-0" />
                                <span className="text-muted-foreground truncate min-w-0 flex-1 font-mono text-[11px]">{p.message}</span>
                                <Badge variant="destructive" className="text-[9px] px-1.5 py-0 h-[16px] shrink-0">{p.count}×</Badge>
                                <span className="text-muted-foreground/30 tabular-nums text-[10px] shrink-0">L{p.firstLine}</span>
                              </div>
                            ))}
                            {patterns.length > 8 && (
                              <p className="text-[10px] text-muted-foreground/50 pl-3">+{patterns.length - 8} more patterns</p>
                            )}
                          </div>
                        </div>
                      )}

                      {/* Error incidents (chronological) */}
                      {incidents.length > 0 && (
                        <div className="glass-panel rounded-[var(--radius)] p-4 space-y-3">
                          <div className="flex items-center justify-between">
                            <p className="text-xs font-medium">Error Incidents</p>
                            <span className="text-[11px] text-muted-foreground">{incidents.length} incidents</span>
                          </div>
                          <div className="space-y-1">
                            {incidents.slice(0, 20).map((inc, i) => {
                              const expanded = expandedIncidents.has(i);
                              const hasStack = inc.lineCount > 1;
                              return (
                                <div key={i}>
                                  <button
                                    onClick={() => hasStack && toggleIncident(i)}
                                    className={cn(
                                      "w-full flex items-start gap-2 py-1.5 px-3 rounded-lg text-xs text-left transition-colors",
                                      inc.level === "error" ? "bg-[hsl(var(--destructive)/0.04)] hover:bg-[hsl(var(--destructive)/0.08)]" : "bg-[hsl(var(--warning)/0.04)] hover:bg-[hsl(var(--warning)/0.08)]",
                                      hasStack && "cursor-pointer",
                                    )}
                                  >
                                    <span className="text-muted-foreground/40 tabular-nums font-mono w-10 shrink-0 pt-0.5">
                                      L{inc.startLine}
                                    </span>
                                    {inc.level === "error" ? (
                                      <AlertCircle className="w-3 h-3 text-[hsl(var(--destructive))] shrink-0 mt-0.5" />
                                    ) : (
                                      <AlertTriangle className="w-3 h-3 text-[hsl(var(--warning))] shrink-0 mt-0.5" />
                                    )}
                                    <span className={cn("min-w-0 flex-1 font-mono text-[11px] break-all", inc.level === "error" ? "text-[hsl(var(--destructive)/0.8)]" : "text-[hsl(var(--warning)/0.8)]")}>
                                      {inc.headline.length > 120 ? inc.headline.substring(0, 120) + "…" : inc.headline}
                                    </span>
                                    {hasStack && (
                                      <Badge variant="secondary" className="text-[9px] px-1 py-0 h-[16px] shrink-0">
                                        {inc.lineCount} lines
                                      </Badge>
                                    )}
                                  </button>
                                  {expanded && contentData?.lines && (
                                    <div className="ml-12 mr-3 mb-1 px-3 py-2 rounded-lg bg-muted/40 border-l-2 border-red-500/30">
                                      {contentData.lines
                                        .filter((l) => l.num >= inc.startLine && l.num <= inc.endLine)
                                        .map((l) => (
                                          <div key={l.num} className="font-mono text-[10px] text-muted-foreground/70 leading-[1.6] break-all">
                                            <span className="text-muted-foreground/30 tabular-nums inline-block w-8">{l.num}</span>
                                            {l.text}
                                          </div>
                                        ))}
                                    </div>
                                  )}
                                </div>
                              );
                            })}
                            {incidents.length > 20 && (
                              <p className="text-[10px] text-muted-foreground/50 pl-3">+{incidents.length - 20} more incidents — switch to Raw Log for full view</p>
                            )}
                          </div>
                        </div>
                      )}

                      {/* Clean log message */}
                      {incidents.length === 0 && (
                        <div className="glass-panel rounded-[var(--radius)] p-8 text-center">
                          <Badge variant="success" className="text-xs mb-2">All Clear</Badge>
                          <p className="text-sm text-muted-foreground">No errors or warnings found in this log file.</p>
                        </div>
                      )}
                    </>
                  )}
                </TabsContent>

                {/* ── RAW LOG TAB ── */}
                <TabsContent value="raw" className="space-y-4">
                  {/* File info + filters */}
                  <div className="glass-panel rounded-[var(--radius)] p-4">
                    <div className="flex items-center justify-between flex-wrap gap-3">
                      <div className="flex items-center gap-3 text-xs">
                        <span>
                          <b className="text-foreground">{contentData.totalLines.toLocaleString()}</b> lines
                        </span>
                        {contentData.severeCount > 0 && (
                          <Badge variant="destructive" className="text-[10px]">{contentData.severeCount} SEVERE</Badge>
                        )}
                        {contentData.warnCount > 0 && (
                          <Badge variant="warning" className="text-[10px]">{contentData.warnCount} WARN</Badge>
                        )}
                        {contentData.severeCount === 0 && contentData.warnCount === 0 && (
                          <Badge variant="success" className="text-[10px]">clean</Badge>
                        )}
                        <span className="text-muted-foreground">{(contentData.fileSize / 1024).toFixed(1)} KB</span>
                      </div>
                      <div className="flex items-center gap-2">
                        <div className="flex items-center gap-1">
                          <Filter className="w-3 h-3 text-muted-foreground" />
                          {([
                            { key: "all" as const, label: "All", count: detailLineCounts.total },
                            { key: "warn" as const, label: "Warn+", count: detailLineCounts.errors + detailLineCounts.warns },
                            { key: "error" as const, label: "Errors", count: detailLineCounts.errors },
                          ]).map((l) => (
                            <button
                              key={l.key}
                              onClick={() => setDetailLevelFilter(l.key)}
                              className={cn(
                                "px-2 py-1 text-[10px] rounded-full border transition-colors inline-flex items-center gap-1",
                                detailLevelFilter === l.key
                                  ? "border-[hsl(var(--primary))] bg-[hsl(var(--primary)/0.15)] text-foreground"
                                  : "border-transparent text-muted-foreground hover:text-foreground",
                              )}
                            >
                              {l.label}
                              <span className="opacity-40 tabular-nums">{l.count}</span>
                            </button>
                          ))}
                        </div>
                        <div className="relative">
                          <Search className="absolute left-2.5 top-1/2 -translate-y-1/2 w-3 h-3 text-muted-foreground" />
                          <input value={detailSearch} onChange={(e) => setDetailSearch(e.target.value)} placeholder="Search logs..." className="w-40 bg-[hsl(var(--muted)/0.3)] border border-[hsl(var(--border))] text-foreground rounded-full pl-7 pr-3 py-1 text-xs outline-none focus:ring-2 focus:ring-[hsl(var(--primary)/0.4)] transition-shadow" />
                        </div>
                      </div>
                    </div>
                    {(detailSearch || detailLevelFilter !== "all") && (
                      <p className="text-[10px] text-muted-foreground mt-2">
                        Showing {filteredLines.length} of {contentData.totalLines} lines
                      </p>
                    )}
                  </div>

                  {/* Log content */}
                  <div className="border border-[hsl(var(--border))] rounded-[14px] bg-[hsl(var(--card))] shadow-sm overflow-hidden">
                    <div className="overflow-x-auto max-h-[600px] overflow-y-auto">
                      <table className="w-full text-xs font-mono">
                        <tbody>
                          {filteredLines.length === 0 ? (
                            <tr>
                              <td className="p-8 text-center text-muted-foreground">
                                {contentData.totalLines === 0 ? "Log file is empty." : "No matching lines found."}
                              </td>
                            </tr>
                          ) : (
                            filteredLines.map((line) => (
                              <tr key={line.num} className={cn("border-b border-[hsl(var(--border)/0.3)] hover:bg-muted/30", line.level === "error" && "bg-[hsl(var(--destructive)/0.1)]", line.level === "warn" && "bg-[hsl(var(--warning)/0.1)]")}>
                                <td className="px-3 py-1 text-right text-muted-foreground/30 select-none w-12 align-top tabular-nums">{line.num}</td>
                                <td className="px-1 py-1 w-5 align-top">
                                  {line.level === "error" && <AlertCircle className="w-3 h-3 text-[hsl(var(--destructive))] mt-0.5" />}
                                  {line.level === "warn" && <AlertTriangle className="w-3 h-3 text-[hsl(var(--warning))] mt-0.5" />}
                                </td>
                                <td className={cn("px-2 py-1 whitespace-pre-wrap break-all", line.level === "error" && "text-[hsl(var(--destructive))]", line.level === "warn" && "text-[hsl(var(--warning))]")}>{line.text}</td>
                              </tr>
                            ))
                          )}
                        </tbody>
                      </table>
                    </div>
                  </div>
                </TabsContent>
              </Tabs>
            ) : (
              <div className="glass-panel rounded-[var(--radius)] p-8 text-center text-muted-foreground">
                <AlertTriangle className="w-6 h-6 mx-auto mb-2" />
                <p className="text-sm">Log file not found for this date and type.</p>
              </div>
            )}
          </>
        )}

          </TabsContent>
        </Tabs>

      </div>
    </TooltipProvider>
  );
}

/* ─── Insight stat card ─── */

function InsightCard({
  icon: Icon,
  label,
  value,
  variant,
}: {
  icon: React.ComponentType<{ className?: string }>;
  label: string;
  value: string;
  variant?: "destructive" | "success";
}) {
  return (
    <div className="glass-panel rounded-[var(--radius)] p-3">
      <div className="flex items-center gap-1.5 mb-1">
        <Icon className={cn("w-3.5 h-3.5", variant === "destructive" ? "text-[hsl(var(--destructive))]" : variant === "success" ? "text-[hsl(var(--success))]" : "text-muted-foreground")} />
        <span className="text-[10px] text-muted-foreground">{label}</span>
      </div>
      <p className={cn("text-lg font-bold tabular-nums", variant === "destructive" && "text-[hsl(var(--destructive))]", variant === "success" && "text-[hsl(var(--success))]")}>{value}</p>
    </div>
  );
}
