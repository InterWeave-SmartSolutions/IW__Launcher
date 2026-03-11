import type { ReactNode } from "react";

interface HeroSectionProps {
  title: string;
  subtitle?: string;
  actions?: ReactNode;
}

export function HeroSection({ title, subtitle, actions }: HeroSectionProps) {
  return (
    <div className="p-5 rounded-[var(--radius)] border border-[hsl(var(--primary)/0.25)] bg-gradient-to-br from-[hsl(var(--primary)/0.14)] to-[hsl(var(--success)/0.08)]">
      <div className="flex items-start justify-between gap-4 flex-wrap">
        <div>
          <h1 className="text-lg font-semibold">{title}</h1>
          {subtitle && (
            <p className="text-sm text-muted-foreground mt-0.5">{subtitle}</p>
          )}
        </div>
        {actions && (
          <div className="flex items-center gap-2 flex-wrap">{actions}</div>
        )}
      </div>
    </div>
  );
}
