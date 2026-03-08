import { cn } from "@/lib/utils";
import type { SystemNode } from "@/lib/flow-utils";

interface FlowDependencyMapProps {
  systems: SystemNode[];
  className?: string;
}

export function FlowDependencyMap({ systems, className }: FlowDependencyMapProps) {
  if (systems.length === 0) {
    return (
      <div className={cn("glass-panel rounded-[var(--radius)] p-4 text-center", className)}>
        <p className="text-xs text-muted-foreground">
          No external systems detected from flow IDs.
        </p>
      </div>
    );
  }

  const cx = 300, cy = 100, hubR = 35;
  const nodeR = 25;
  const orbitR = 75;
  const angleStart = Math.PI; // left
  const angleEnd = 0; // right (top semicircle)
  const n = systems.length;

  function nodeColor(sys: SystemNode) {
    if (sys.failedCount > 0) return { fill: "hsl(var(--destructive))", stroke: "hsl(var(--destructive))", opacity: 0.15 };
    if (sys.runningCount > 0) return { fill: "hsl(var(--success))", stroke: "hsl(var(--success))", opacity: 0.15 };
    return { fill: "hsl(var(--warning))", stroke: "hsl(var(--warning))", opacity: 0.15 };
  }

  return (
    <div className={cn("glass-panel rounded-[var(--radius)] p-4", className)}>
      <h3 className="text-xs font-semibold text-muted-foreground uppercase tracking-wider mb-3">
        Integration Architecture
      </h3>
      <svg
        viewBox="0 0 600 200"
        className="w-full"
        style={{ maxHeight: 200 }}
      >
        {/* Hub */}
        <circle cx={cx} cy={cy} r={hubR} fill="hsl(var(--primary))" fillOpacity={0.15} stroke="hsl(var(--primary))" strokeWidth={1.5} />
        <text x={cx} y={cy - 4} textAnchor="middle" fontSize={9} fontWeight={600} fill="hsl(var(--primary))">
          IW
        </text>
        <text x={cx} y={cy + 8} textAnchor="middle" fontSize={7} fill="hsl(var(--primary))" opacity={0.7}>
          Engine
        </text>

        {/* Spokes + nodes */}
        {systems.map((sys, i) => {
          const angle = n === 1
            ? Math.PI / 2
            : angleStart + (angleEnd - angleStart) * (i / (n - 1));
          const nx = cx + Math.cos(angle) * orbitR * 2.5;
          const ny = cy - Math.sin(angle) * orbitR;
          const colors = nodeColor(sys);

          return (
            <g key={sys.abbrev}>
              {/* Spoke line */}
              <line
                x1={cx} y1={cy} x2={nx} y2={ny}
                stroke={colors.stroke}
                strokeWidth={1.5}
                strokeDasharray="4 2"
                opacity={0.35}
              />
              {/* System node */}
              <circle cx={nx} cy={ny} r={nodeR} fill={colors.fill} fillOpacity={colors.opacity} stroke={colors.stroke} strokeWidth={1.5} opacity={0.8} />
              <text x={nx} y={ny + 1} textAnchor="middle" fontSize={10} fontWeight={600} fill={colors.stroke}>
                {sys.abbrev}
              </text>
              {/* Name + count below */}
              <text x={nx} y={ny + nodeR + 12} textAnchor="middle" fontSize={8} className="fill-muted-foreground">
                {sys.name}
              </text>
              <text x={nx} y={ny + nodeR + 22} textAnchor="middle" fontSize={7} className="fill-muted-foreground" opacity={0.7}>
                {sys.flowCount} flow{sys.flowCount !== 1 ? "s" : ""}
              </text>
            </g>
          );
        })}
      </svg>
    </div>
  );
}
