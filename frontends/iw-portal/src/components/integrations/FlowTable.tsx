import { useState } from "react";
import { Link } from "react-router-dom";
import {
  Timer,
  Play,
  Square,
  Pencil,
  MoreHorizontal,
  FileText,
  History,
  Settings2,
  CheckSquare,
  X,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Tooltip, TooltipContent, TooltipTrigger } from "@/components/ui/tooltip";
import {
  DropdownMenu,
  DropdownMenuTrigger,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuLabel,
} from "@/components/ui/dropdown-menu";
import { FlowStateIndicator } from "./FlowStateIndicator";
import { engineFlowRowStyle, logLevelLabel } from "@/lib/flow-utils";
import type { EngineFlow } from "@/types/flows";

interface FlowTableProps {
  title: string;
  flows: EngineFlow[];
  isAdmin: boolean;
  onStartStop: (flow: EngineFlow) => void;
  onBulkStart?: (indices: number[]) => void;
  onBulkStop?: (indices: number[]) => void;
  onEditSchedule?: (flow: EngineFlow) => void;
  onEditProperties?: (flowId: string, isFlow: boolean) => void;
  isPending: boolean;
}

const LEGEND = [
  { label: "Running & Executing", dot: "bg-[hsl(330,80%,60%)]" },
  { label: "Running", dot: "bg-[hsl(185,70%,50%)]" },
  { label: "Executing", dot: "bg-[hsl(45,95%,55%)]" },
  { label: "Stopped", dot: "bg-muted-foreground opacity-40" },
] as const;

export function FlowTable({
  title,
  flows,
  isAdmin,
  onStartStop,
  onBulkStart,
  onBulkStop,
  onEditSchedule,
  onEditProperties,
  isPending,
}: FlowTableProps) {
  const [selecting, setSelecting] = useState(false);
  const [selected, setSelected] = useState<Set<number>>(new Set());

  const toggleAll = () => {
    if (selected.size === flows.length) {
      setSelected(new Set());
    } else {
      setSelected(new Set(flows.map((_, i) => i)));
    }
  };

  const toggleOne = (idx: number) => {
    setSelected((prev) => {
      const next = new Set(prev);
      if (next.has(idx)) next.delete(idx);
      else next.add(idx);
      return next;
    });
  };

  const exitSelecting = () => {
    setSelecting(false);
    setSelected(new Set());
  };

  const handleBulkStart = () => {
    if (onBulkStart) {
      const indices = [...selected]
        .map((i) => flows[i]?.index)
        .filter((idx): idx is number => idx !== undefined);
      onBulkStart(indices);
      exitSelecting();
    }
  };

  const handleBulkStop = () => {
    if (onBulkStop) {
      const indices = [...selected]
        .map((i) => flows[i]?.index)
        .filter((idx): idx is number => idx !== undefined);
      onBulkStop(indices);
      exitSelecting();
    }
  };

  if (flows.length === 0) return null;

  const gridCols = selecting
    ? "grid-cols-[auto_1fr_44px_80px_80px_90px_70px_60px_44px_80px_40px]"
    : "grid-cols-[1fr_44px_80px_80px_90px_70px_60px_44px_80px_40px]";

  return (
    <div className="relative">
      {/* Table header */}
      <div className="flex items-center gap-2 mb-2">
        <Timer className="w-4 h-4 text-muted-foreground" />
        <h3 className="text-sm font-semibold">{title}</h3>
        <Badge variant="secondary" className="text-xs">
          {flows.length}
        </Badge>
        <div className="ml-auto">
          {isAdmin && !selecting && (
            <Button
              variant="ghost"
              size="sm"
              className="text-xs text-muted-foreground h-7"
              onClick={() => setSelecting(true)}
            >
              <CheckSquare className="size-3" />
              Select
            </Button>
          )}
          {selecting && (
            <Button
              variant="ghost"
              size="sm"
              className="text-xs text-muted-foreground h-7"
              onClick={exitSelecting}
            >
              <X className="size-3" />
              Cancel
            </Button>
          )}
        </div>
      </div>

      {/* Grid table */}
      <div className="rounded-lg border border-[hsl(var(--border))] overflow-hidden">
        {/* Column headers */}
        <div className={cn("grid gap-0 bg-muted/50 px-3 py-2 text-xs font-medium text-muted-foreground items-center", gridCols)}>
          {selecting && (
            <div className="flex justify-center">
              <input
                type="checkbox"
                checked={selected.size === flows.length && flows.length > 0}
                onChange={toggleAll}
                className="rounded border-muted-foreground/30"
                aria-label="Select all flows"
              />
            </div>
          )}
          <Tooltip>
            <TooltipTrigger asChild>
              <span className="cursor-help">Flow ID</span>
            </TooltipTrigger>
            <TooltipContent side="bottom">
              Integration flow identifier. Click to open properties editor.
            </TooltipContent>
          </Tooltip>
          <Tooltip>
            <TooltipTrigger asChild>
              <span className="cursor-help text-center">State</span>
            </TooltipTrigger>
            <TooltipContent side="bottom">
              Current execution state of the flow.
            </TooltipContent>
          </Tooltip>
          <Tooltip>
            <TooltipTrigger asChild>
              <span className="cursor-help text-center">Action</span>
            </TooltipTrigger>
            <TooltipContent side="bottom">
              Start or stop the flow. Admin only.
            </TooltipContent>
          </Tooltip>
          <Tooltip>
            <TooltipTrigger asChild>
              <span className="cursor-help text-center">Mode</span>
            </TooltipTrigger>
            <TooltipContent side="bottom">
              Scheduled = runs on interval. Single = runs once.
            </TooltipContent>
          </Tooltip>
          <Tooltip>
            <TooltipTrigger asChild>
              <span className="cursor-help text-center">Interval</span>
            </TooltipTrigger>
            <TooltipContent side="bottom">
              Time between executions (HH:MM:SS or seconds).
            </TooltipContent>
          </Tooltip>
          <Tooltip>
            <TooltipTrigger asChild>
              <span className="cursor-help text-center">Shift</span>
            </TooltipTrigger>
            <TooltipContent side="bottom">
              Offset from heartbeat. &apos;T&apos; = time-of-day scheduling.
            </TooltipContent>
          </Tooltip>
          <Tooltip>
            <TooltipTrigger asChild>
              <span className="cursor-help text-center">Ctr</span>
            </TooltipTrigger>
            <TooltipContent side="bottom">
              Remaining executions. 0 = unlimited.
            </TooltipContent>
          </Tooltip>
          <Tooltip>
            <TooltipTrigger asChild>
              <span className="cursor-help text-center">Log</span>
            </TooltipTrigger>
            <TooltipContent side="bottom">
              Current log verbosity level.
            </TooltipContent>
          </Tooltip>
          <Tooltip>
            <TooltipTrigger asChild>
              <span className="cursor-help text-center">S/F</span>
            </TooltipTrigger>
            <TooltipContent side="bottom">
              Success/Failure counts. Click to view filtered transaction history.
            </TooltipContent>
          </Tooltip>
          {/* Actions column header — empty */}
          <span />
        </div>

        {/* State legend strip */}
        <div className="flex items-center gap-4 px-3 py-1.5 bg-muted/30 border-b border-[hsl(var(--border))] text-[10px] text-muted-foreground">
          {LEGEND.map((item) => (
            <span key={item.label} className="inline-flex items-center gap-1">
              <span className={cn("inline-block h-2 w-2 rounded-full", item.dot)} />
              {item.label}
            </span>
          ))}
        </div>

        {/* Rows */}
        {flows.map((flow, idx) => {
          const style = engineFlowRowStyle(flow.running, flow.executing);
          const log = logLevelLabel(flow.logLevel);
          const isRunning = flow.running || flow.executing;

          return (
            <div
              key={flow.flowId}
              className={cn(
                "group grid gap-0 px-3 py-2 items-center border-b border-[hsl(var(--border))] last:border-b-0 transition-colors hover:bg-muted/30",
                gridCols,
                style.bg,
                style.border,
              )}
            >
              {/* Checkbox (only in selection mode) */}
              {selecting && (
                <div className="flex justify-center">
                  <input
                    type="checkbox"
                    checked={selected.has(idx)}
                    onChange={() => toggleOne(idx)}
                    className="rounded border-muted-foreground/30 flex-shrink-0"
                    aria-label={`Select ${flow.flowId}`}
                  />
                </div>
              )}

              {/* Flow ID */}
              <div className="min-w-0">
                <button
                  type="button"
                  onClick={() => onEditProperties?.(flow.flowId, flow.type !== "query")}
                  className="text-sm font-medium truncate hover:text-[hsl(var(--primary))] hover:underline transition-colors inline-flex items-center gap-1 cursor-pointer bg-transparent border-none p-0"
                >
                  {flow.flowId}
                  <FileText className="w-2.5 h-2.5 opacity-0 group-hover:opacity-50 transition-opacity flex-shrink-0" />
                </button>
              </div>

              {/* State */}
              <div className="flex justify-center">
                <FlowStateIndicator running={flow.running} executing={flow.executing} />
              </div>

              {/* Start/Stop */}
              <div className="flex justify-center">
                <Button
                  size="sm"
                  variant={isRunning ? "destructive" : "default"}
                  className="h-7 px-2.5 text-xs [&_svg]:size-3"
                  disabled={!isAdmin || isPending}
                  onClick={() => onStartStop(flow)}
                >
                  {isRunning ? (
                    <>
                      <Square />
                      Stop
                    </>
                  ) : (
                    <>
                      <Play />
                      Start
                    </>
                  )}
                </Button>
              </div>

              {/* Mode */}
              <div className="text-center">
                <Badge variant="outline" className="text-[10px] px-1.5">
                  {flow.isScheduled ? "Sched" : "Single"}
                </Badge>
              </div>

              {/* Interval */}
              <div className="flex items-center justify-center gap-1">
                <span className="text-xs text-muted-foreground">
                  {flow.intervalDisplay || flow.interval}
                </span>
                {onEditSchedule && isAdmin && (
                  <button
                    onClick={() => onEditSchedule(flow)}
                    className="opacity-0 group-hover:opacity-70 hover:!opacity-100 transition-opacity cursor-pointer"
                    aria-label={`Edit schedule for ${flow.flowId}`}
                  >
                    <Pencil className="w-3 h-3 text-muted-foreground" />
                  </button>
                )}
              </div>

              {/* Shift */}
              <div className="text-center text-xs text-muted-foreground">
                {flow.shiftDisplay || flow.shift}
              </div>

              {/* Counter */}
              <div className="text-center text-xs text-muted-foreground">
                {flow.counter === 0 ? "\u221E" : flow.counter}
              </div>

              {/* Log */}
              <div className="flex justify-center">
                <span className={cn("text-[10px] font-mono font-semibold", log.color)}>
                  {log.label}
                </span>
              </div>

              {/* S/F */}
              <div className="flex items-center justify-center gap-1">
                <Link
                  to={`/monitoring/transactions?flow=${encodeURIComponent(flow.flowId)}&status=success`}
                  className="text-xs text-[hsl(var(--success))] hover:underline"
                >
                  {flow.successes}
                </Link>
                <span className="text-xs text-muted-foreground">/</span>
                <Link
                  to={`/monitoring/transactions?flow=${encodeURIComponent(flow.flowId)}&status=failed`}
                  className="text-xs text-[hsl(var(--destructive))] hover:underline"
                >
                  {flow.failures}
                </Link>
              </div>

              {/* Row actions menu */}
              <div className="flex justify-center">
                <DropdownMenu>
                  <DropdownMenuTrigger asChild>
                    <button className="p-1 rounded-md hover:bg-muted/50 text-muted-foreground hover:text-foreground transition-colors cursor-pointer">
                      <MoreHorizontal className="w-4 h-4" />
                    </button>
                  </DropdownMenuTrigger>
                  <DropdownMenuContent align="end">
                    <DropdownMenuLabel>{flow.flowId}</DropdownMenuLabel>
                    <DropdownMenuSeparator />
                    <DropdownMenuItem onClick={() => onEditProperties?.(flow.flowId, flow.type !== "query")}>
                      <FileText />
                      Edit Properties
                    </DropdownMenuItem>
                    <DropdownMenuItem asChild>
                      <Link to={`/monitoring/transactions?flow=${encodeURIComponent(flow.flowId)}`}>
                        <History />
                        View Transactions
                      </Link>
                    </DropdownMenuItem>
                    {onEditSchedule && isAdmin && (
                      <DropdownMenuItem onClick={() => onEditSchedule(flow)}>
                        <Settings2 />
                        Edit Schedule
                      </DropdownMenuItem>
                    )}
                    <DropdownMenuSeparator />
                    <DropdownMenuItem
                      onClick={() => onStartStop(flow)}
                      disabled={!isAdmin || isPending}
                      className={isRunning ? "text-[hsl(var(--destructive))]" : "text-[hsl(var(--success))]"}
                    >
                      {isRunning ? <Square /> : <Play />}
                      {isRunning ? "Stop Flow" : "Start Flow"}
                    </DropdownMenuItem>
                  </DropdownMenuContent>
                </DropdownMenu>
              </div>
            </div>
          );
        })}
      </div>

      {/* Floating bulk action bar (only when selecting) */}
      {selecting && selected.size > 0 && (
        <div className="sticky bottom-4 mt-4 flex items-center justify-center gap-3 px-4 py-2.5 rounded-lg border border-[hsl(var(--border))] bg-background/95 backdrop-blur shadow-lg">
          <span className="text-sm text-muted-foreground">
            {selected.size} flow{selected.size !== 1 ? "s" : ""} selected
          </span>
          {onBulkStart && (
            <Button
              size="sm"
              variant="default"
              className="h-8 text-xs"
              disabled={isPending}
              onClick={handleBulkStart}
            >
              <Play />
              Start Selected
            </Button>
          )}
          {onBulkStop && (
            <Button
              size="sm"
              variant="destructive"
              className="h-8 text-xs"
              disabled={isPending}
              onClick={handleBulkStop}
            >
              <Square />
              Stop Selected
            </Button>
          )}
          <Button
            size="sm"
            variant="ghost"
            className="h-8 text-xs"
            onClick={exitSelecting}
          >
            Cancel
          </Button>
        </div>
      )}
    </div>
  );
}
