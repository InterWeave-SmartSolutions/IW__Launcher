import { useQuery } from "@tanstack/react-query";
import { apiFetch } from "@/lib/api";

export interface LogFileSummary {
  totalFiles: number;
  nonEmptyFiles: number;
  firstDate: string;
  lastDate: string;
  types: Record<string, { count: number; nonEmpty: number }>;
}

export interface DaySummary {
  date: string;
  totalLines: number;
  errors: number;
  severe: number;
  warnings: number;
  types: Record<string, {
    lines: number;
    errors: number;
    severe: number;
    warnings: number;
    fileSize?: number;
    topError?: string | null;
    topErrorCount?: number;
  }>;
}

export interface LogLine {
  num: number;
  level: "info" | "warn" | "error" | "ts";
  text: string;
}

export interface LiveLogResponse {
  lines: LogLine[];
  totalLines: number;
  file: string;
}

export interface LogContent {
  date: string;
  type: string;
  fileName: string;
  fileSize: number;
  totalLines: number;
  errorCount: number;
  severeCount: number;
  warnCount: number;
  lines: LogLine[];
}

export function useLogFiles() {
  return useQuery({
    queryKey: ["logs", "files"],
    queryFn: () => apiFetch<LogFileSummary>("/api/logs/files"),
    staleTime: 60_000,
  });
}

export function useLogSummary() {
  return useQuery({
    queryKey: ["logs", "summary"],
    queryFn: () => apiFetch<{ days: DaySummary[] }>("/api/logs/summary"),
    staleTime: 60_000,
  });
}

export function useLogContent(date: string | null, type: string) {
  return useQuery({
    queryKey: ["logs", "content", date, type],
    queryFn: () => apiFetch<LogContent>(`/api/logs/content?date=${date}&type=${type}`),
    enabled: !!date,
    staleTime: Infinity, // log content doesn't change
  });
}

export function useLiveLogs(lines = 100, filter = "") {
  return useQuery({
    queryKey: ["logs", "live", lines, filter],
    queryFn: () => {
      const params = new URLSearchParams({ lines: String(lines) });
      if (filter) params.set("filter", filter);
      return apiFetch<LiveLogResponse>(`/api/logs/live?${params.toString()}`);
    },
    refetchInterval: 5_000,
    staleTime: 0,
  });
}
