import { ExternalLink, MonitorSmartphone } from "lucide-react";
import { getClassicUrl, CLASSIC_MODE_KEY } from "@/lib/classic-routes";
import { useState } from "react";
import { cn } from "@/lib/utils";

interface Props {
  currentPath: string;
}

export function ClassicViewBanner({ currentPath }: Props) {
  const [dismissed, setDismissed] = useState(false);
  const classicUrl = getClassicUrl(currentPath);

  // Check if user prefers classic mode
  const preferClassic = localStorage.getItem(CLASSIC_MODE_KEY) === "true";
  if (preferClassic) {
    window.location.href = classicUrl;
    return null;
  }

  if (dismissed) return null;

  return (
    <div className={cn(
      "flex items-center justify-between gap-3 px-6 py-2",
      "bg-[hsl(var(--primary)/0.08)] border-b border-[hsl(var(--primary)/0.15)]",
      "text-xs text-muted-foreground"
    )}>
      <div className="flex items-center gap-2">
        <MonitorSmartphone className="w-3.5 h-3.5" />
        <span>New portal UI — some features are still being built.</span>
      </div>
      <div className="flex items-center gap-3">
        <a
          href={classicUrl}
          className="flex items-center gap-1 text-[hsl(var(--primary))] hover:underline font-medium"
        >
          <ExternalLink className="w-3 h-3" />
          Switch to Classic View
        </a>
        <button
          onClick={() => setDismissed(true)}
          className="text-muted-foreground hover:text-foreground cursor-pointer"
        >
          ✕
        </button>
      </div>
    </div>
  );
}
