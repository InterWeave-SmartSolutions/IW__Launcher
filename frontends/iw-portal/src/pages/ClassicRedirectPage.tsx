import { ExternalLink } from "lucide-react";

interface Props {
  title: string;
  classicPath: string;
}

/** Hook page — links to the classic JSP page while the React version is being built */
export function ClassicRedirectPage({ title, classicPath }: Props) {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-xl font-bold">{title}</h1>
        <p className="text-sm text-muted-foreground">
          This page is available in Classic View. A modern version is being built.
        </p>
      </div>

      <div className="glass-panel rounded-[var(--radius)] p-6 flex flex-col items-center gap-4 text-center">
        <div className="w-16 h-16 rounded-full bg-[hsl(var(--primary)/0.1)] flex items-center justify-center">
          <ExternalLink className="w-8 h-8 text-[hsl(var(--primary))]" />
        </div>
        <div>
          <h2 className="text-lg font-semibold mb-1">Open in Classic View</h2>
          <p className="text-sm text-muted-foreground mb-4">
            Full functionality is available through the classic InterWeave web portal.
          </p>
          <a
            href={classicPath}
            className="inline-flex items-center gap-2 px-5 py-2.5 rounded-[14px] bg-[hsl(var(--primary)/0.18)] border border-[hsl(var(--primary)/0.35)] text-sm font-medium hover:bg-[hsl(var(--primary)/0.25)] transition-colors"
          >
            <ExternalLink className="w-4 h-4" />
            Go to {title}
          </a>
        </div>
      </div>
    </div>
  );
}
