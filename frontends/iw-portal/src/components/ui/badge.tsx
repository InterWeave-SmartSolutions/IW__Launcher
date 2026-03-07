import * as React from "react";
import { cva, type VariantProps } from "class-variance-authority";
import { cn } from "@/lib/utils";

const badgeVariants = cva(
  "inline-flex items-center gap-1.5 rounded-full px-2.5 py-0.5 text-xs font-medium transition-colors",
  {
    variants: {
      variant: {
        default:
          "bg-[hsl(var(--primary)/0.15)] text-[hsl(var(--primary))]",
        secondary:
          "bg-[hsl(var(--muted))] text-muted-foreground",
        success:
          "bg-[hsl(var(--success)/0.15)] text-[hsl(var(--success))]",
        destructive:
          "bg-[hsl(var(--destructive)/0.15)] text-[hsl(var(--destructive))]",
        warning:
          "bg-[hsl(var(--warning)/0.15)] text-[hsl(var(--warning))]",
        outline:
          "border border-[hsl(var(--border))] text-foreground",
      },
    },
    defaultVariants: {
      variant: "default",
    },
  }
);

function Badge({
  className,
  variant,
  ...props
}: React.HTMLAttributes<HTMLSpanElement> & VariantProps<typeof badgeVariants>) {
  return (
    <span className={cn(badgeVariants({ variant }), className)} {...props} />
  );
}

export { Badge, badgeVariants };
