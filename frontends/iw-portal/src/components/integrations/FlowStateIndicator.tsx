import { cn } from "@/lib/utils";

interface FlowStateIndicatorProps {
  running: boolean;
  executing: boolean;
  className?: string;
}

/**
 * Four-state animated indicator matching classic JSP color coding:
 *   running+executing = pulsing rose/pink (deeppink)
 *   running only      = slow pulse cyan (aqua)
 *   executing only    = amber with fill animation (yellow)
 *   stopped           = static neutral dot
 */
export function FlowStateIndicator({ running, executing, className }: FlowStateIndicatorProps) {
  if (running && executing) {
    return (
      <span className={cn("relative flex h-3 w-3", className)}>
        <span className="animate-ping absolute inline-flex h-full w-full rounded-full bg-[hsl(330,80%,60%)] opacity-50" />
        <span className="relative inline-flex rounded-full h-3 w-3 bg-[hsl(330,80%,60%)]" />
      </span>
    );
  }
  if (running) {
    return (
      <span className={cn("relative flex h-3 w-3", className)}>
        <span className="animate-ping absolute inline-flex h-full w-full rounded-full bg-[hsl(185,70%,50%)] opacity-40 [animation-duration:2s]" />
        <span className="relative inline-flex rounded-full h-3 w-3 bg-[hsl(185,70%,50%)]" />
      </span>
    );
  }
  if (executing) {
    return (
      <span className={cn("relative flex h-3 w-3", className)}>
        <span className="animate-pulse absolute inline-flex h-full w-full rounded-full bg-[hsl(45,95%,55%)] opacity-50" />
        <span className="relative inline-flex rounded-full h-3 w-3 bg-[hsl(45,95%,55%)]" />
      </span>
    );
  }
  return (
    <span className={cn("relative flex h-3 w-3", className)}>
      <span className="relative inline-flex rounded-full h-3 w-3 bg-muted-foreground opacity-40" />
    </span>
  );
}
