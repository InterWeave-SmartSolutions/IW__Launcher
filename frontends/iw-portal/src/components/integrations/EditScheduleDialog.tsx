import { useState, useEffect } from "react";
import { Loader2, Save } from "lucide-react";
import { useUpdateFlowSchedule } from "@/hooks/useFlows";
import { useToast } from "@/providers/ToastProvider";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter } from "@/components/ui/dialog";
import { Tooltip, TooltipContent, TooltipTrigger } from "@/components/ui/tooltip";
import type { EngineFlow } from "@/types/flows";

interface EditScheduleDialogProps {
  flow: EngineFlow | null;
  open: boolean;
  onOpenChange: (open: boolean) => void;
}

export function EditScheduleDialog({ flow, open, onOpenChange }: EditScheduleDialogProps) {
  const { showToast } = useToast();
  const updateSchedule = useUpdateFlowSchedule();
  const [interval, setInterval] = useState("");
  const [shift, setShift] = useState("");
  const [counter, setCounter] = useState("");

  // Sync form state when a new flow is selected
  useEffect(() => {
    if (flow) {
      setInterval(String(flow.interval));
      setShift(String(flow.shift));
      setCounter(String(flow.counter));
    }
  }, [flow?.flowId]); // eslint-disable-line react-hooks/exhaustive-deps

  const handleSave = async () => {
    if (!flow) return;
    try {
      await updateSchedule.mutateAsync({
        flowIndex: flow.index,
        interval: Number(interval) || undefined,
        shift: Number(shift) || undefined,
        counter: Number(counter) || undefined,
      });
      showToast(`Schedule updated: ${flow.flowId}`, "success");
      onOpenChange(false);
    } catch (e) {
      showToast(e instanceof Error ? e.message : "Failed to update schedule", "error");
    }
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Edit Flow Schedule</DialogTitle>
        </DialogHeader>
        {flow && (
          <div className="space-y-4 py-2">
            <div className="flex items-center gap-2 text-sm">
              <Badge variant="default">{flow.flowId}</Badge>
              <Badge variant={flow.running ? "default" : "secondary"}>
                {flow.state}
              </Badge>
            </div>
            <div className="grid grid-cols-3 gap-3">
              <div className="space-y-1.5">
                <Tooltip>
                  <TooltipTrigger asChild>
                    <Label htmlFor="edit-interval" className="cursor-help border-b border-dotted border-muted-foreground">
                      Interval (s)
                    </Label>
                  </TooltipTrigger>
                  <TooltipContent>
                    Time in seconds between each execution cycle. Use HH:MM:SS format for readability.
                  </TooltipContent>
                </Tooltip>
                <Input
                  id="edit-interval"
                  type="number"
                  min={0}
                  value={interval}
                  onChange={(e) => setInterval(e.target.value)}
                />
              </div>
              <div className="space-y-1.5">
                <Tooltip>
                  <TooltipTrigger asChild>
                    <Label htmlFor="edit-shift" className="cursor-help border-b border-dotted border-muted-foreground">
                      Shift (s)
                    </Label>
                  </TooltipTrigger>
                  <TooltipContent>
                    Offset from heartbeat in seconds. Negative values use time-of-day scheduling.
                  </TooltipContent>
                </Tooltip>
                <Input
                  id="edit-shift"
                  type="number"
                  value={shift}
                  onChange={(e) => setShift(e.target.value)}
                />
              </div>
              <div className="space-y-1.5">
                <Tooltip>
                  <TooltipTrigger asChild>
                    <Label htmlFor="edit-counter" className="cursor-help border-b border-dotted border-muted-foreground">
                      Counter
                    </Label>
                  </TooltipTrigger>
                  <TooltipContent>
                    Number of remaining executions before auto-stop. 0 means unlimited.
                  </TooltipContent>
                </Tooltip>
                <Input
                  id="edit-counter"
                  type="number"
                  min={0}
                  value={counter}
                  onChange={(e) => setCounter(e.target.value)}
                />
              </div>
            </div>
            {(flow.running || flow.executing) && (
              <p className="text-xs text-[hsl(var(--warning))]">
                Schedule changes are blocked while the flow is running. Stop it first.
              </p>
            )}
          </div>
        )}
        <DialogFooter>
          <Button variant="outline" onClick={() => onOpenChange(false)}>
            Cancel
          </Button>
          <Button
            onClick={() => void handleSave()}
            disabled={updateSchedule.isPending || !!(flow && (flow.running || flow.executing))}
          >
            {updateSchedule.isPending ? <Loader2 className="w-4 h-4 animate-spin" /> : <Save className="w-4 h-4" />}
            Save Schedule
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}
