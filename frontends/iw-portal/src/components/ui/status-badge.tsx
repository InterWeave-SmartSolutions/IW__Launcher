import { cn } from "@/lib/utils";

export type StatusVariant = "ok" | "warn" | "bad" | "info" | "neutral";

const DOT_CLASSES: Record<StatusVariant, string> = {
  ok: "bg-[hsl(var(--success))]",
  warn: "bg-[hsl(var(--warning))]",
  bad: "bg-[hsl(var(--destructive))]",
  info: "bg-[hsl(var(--primary))]",
  neutral: "bg-muted-foreground",
};

interface StatusBadgeProps {
  status: StatusVariant;
  label: string;
  className?: string;
}

export function StatusBadge({ status, label, className }: StatusBadgeProps) {
  return (
    <span
      className={cn(
        "inline-flex items-center gap-1.5 text-xs px-2 py-0.5 rounded-full",
        "border border-[hsl(var(--border))] text-muted-foreground",
        className
      )}
    >
      <span className={cn("w-2 h-2 rounded-full shrink-0", DOT_CLASSES[status])} />
      {label}
    </span>
  );
}

/** Convenience mapping for common label strings → status variant */
export function inferStatus(label: string): StatusVariant {
  const l = label.toLowerCase();
  if (l.includes("active") || l.includes("ok") || l.includes("success") || l.includes("paid") || l.includes("completed")) return "ok";
  if (l.includes("warn") || l.includes("slow") || l.includes("pending") || l.includes("partial") || l.includes("not config")) return "warn";
  if (l.includes("error") || l.includes("fail") || l.includes("bad") || l.includes("past-due") || l.includes("cancel")) return "bad";
  if (l.includes("info") || l.includes("draft") || l.includes("new")) return "info";
  return "neutral";
}
