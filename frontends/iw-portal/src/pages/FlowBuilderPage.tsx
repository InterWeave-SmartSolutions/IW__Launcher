import { useState, useMemo } from "react";
import { Link } from "react-router-dom";
import {
  ArrowLeft,
  Workflow,
  Loader2,
  RefreshCw,
  HelpCircle,
} from "lucide-react";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { useToast } from "@/providers/ToastProvider";
import { useCompanyProfile } from "@/hooks/useProfile";
import { useWorkspaceProjects, useWorkspaceProject } from "@/hooks/useWorkspace";
import { useEngineFlows, useStartFlow, useStopFlow } from "@/hooks/useFlows";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { FlowList, type FlowListItem } from "@/components/flows/FlowList";
import { FlowDetail } from "@/components/flows/FlowDetail";
import type { WorkspaceTransaction } from "@/types/workspace";

export function FlowBuilderPage() {
  useDocumentTitle("Flow Builder");
  const { showToast } = useToast();

  const { data: companyData } = useCompanyProfile();
  const { data: wsData, isLoading: wsLoading } = useWorkspaceProjects();
  const { data: flowsRes, isLoading: flowsLoading, refetch } = useEngineFlows(true);

  // Auto-select the first workspace project that matches the company's solution type
  const projects = wsData?.projects ?? [];
  const companySolution = companyData?.company?.solutionType;
  const matchingProject = projects.find(
    (p) => companySolution && p.solutionType === companySolution
  ) ?? projects[0];

  const { data: projectDetail, isLoading: detailLoading } = useWorkspaceProject(
    matchingProject?.name ?? null
  );

  const startFlow = useStartFlow();
  const stopFlow = useStopFlow();

  const [selectedFlowId, setSelectedFlowId] = useState<string | null>(null);

  const isLoading = wsLoading || flowsLoading || detailLoading;

  // Build flow list items by merging workspace transactions/queries with engine flows
  const flowItems: FlowListItem[] = useMemo(() => {
    const project = projectDetail?.project;
    if (!project) return [];

    const engineFlows = [
      ...(flowsRes?.data?.scheduledFlows ?? []),
      ...(flowsRes?.data?.utilityFlows ?? []),
    ];

    const items: FlowListItem[] = [];

    // Transactions
    for (const tx of project.transactions) {
      const ef = engineFlows.find((f) => f.flowId === tx.Id);
      items.push({
        id: tx.Id,
        description: tx.Description || "",
        type: "transaction",
        interval: tx.Interval,
        paramCount: tx.parameters?.length ?? 0,
        engineFlow: ef,
        raw: tx,
      });
    }

    // Queries
    for (const q of project.queries) {
      items.push({
        id: q.Id,
        description: q.Description || "",
        type: "query",
        interval: undefined,
        paramCount: q.parameters?.length ?? 0,
        engineFlow: undefined,
        raw: q,
      });
    }

    return items;
  }, [projectDetail, flowsRes]);

  const selectedFlow = flowItems.find((f) => f.id === selectedFlowId) ?? null;
  const allTransactions = (projectDetail?.project?.transactions ?? []) as WorkspaceTransaction[];

  const handleStart = async (flowIndex: number) => {
    try {
      await startFlow.mutateAsync(flowIndex);
      showToast("Flow started", "success");
    } catch (e) {
      showToast(e instanceof Error ? e.message : "Start failed", "error");
    }
  };

  const handleStop = async (flowIndex: number) => {
    try {
      await stopFlow.mutateAsync(flowIndex);
      showToast("Flow stopped", "success");
    } catch (e) {
      showToast(e instanceof Error ? e.message : "Stop failed", "error");
    }
  };

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <Loader2 className="w-8 h-8 animate-spin text-muted-foreground" />
      </div>
    );
  }

  if (!matchingProject) {
    return (
      <div className="space-y-6 max-w-4xl">
        <div className="flex items-center gap-2 text-sm text-muted-foreground">
          <Link to="/company" className="hover:text-foreground transition-colors">
            <ArrowLeft className="w-4 h-4 inline mr-1" />
            Company
          </Link>
          <span>/</span>
          <span className="text-foreground">Flow Builder</span>
        </div>
        <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-12 text-center">
          <Workflow className="w-12 h-12 text-muted-foreground mx-auto mb-4" />
          <h3 className="text-lg font-medium mb-1">No Workspace Projects</h3>
          <p className="text-sm text-muted-foreground mb-4 max-w-md mx-auto">
            No integration projects found in the workspace. Configure a solution type and set up
            connections before building flows.
          </p>
          <Button asChild>
            <Link to="/company/config">Open Configuration</Link>
          </Button>
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      {/* Breadcrumb */}
      <div className="flex items-center gap-2 text-sm text-muted-foreground">
        <Link to="/company" className="hover:text-foreground transition-colors">
          <ArrowLeft className="w-4 h-4 inline mr-1" />
          Company
        </Link>
        <span>/</span>
        <span className="text-foreground">Flow Builder</span>
      </div>

      {/* Header */}
      <div className="flex items-start justify-between gap-4 flex-wrap">
        <div>
          <h1 className="text-2xl font-semibold">Flow Builder</h1>
          <p className="text-sm text-muted-foreground mt-1">
            Transaction flows and queries for{" "}
            <span className="font-medium text-foreground">
              {matchingProject.name.replace(/_/g, " ")}
            </span>
          </p>
        </div>
        <div className="flex items-center gap-2">
          <Badge variant="secondary">
            {flowItems.filter((f) => f.type === "transaction").length} transactions
          </Badge>
          <Badge variant="secondary">
            {flowItems.filter((f) => f.type === "query").length} queries
          </Badge>
          <Button
            variant="outline"
            size="sm"
            onClick={() => void refetch()}
          >
            <RefreshCw className="w-3.5 h-3.5" />
            Refresh
          </Button>
        </div>
      </div>

      {/* Split panel layout */}
      <div className="grid grid-cols-1 lg:grid-cols-[320px_1fr] gap-6 min-h-[500px]">
        {/* Left panel: Flow list */}
        <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-4 overflow-y-auto max-h-[calc(100vh-280px)]">
          <FlowList
            items={flowItems}
            selectedId={selectedFlowId}
            onSelect={setSelectedFlowId}
          />
        </div>

        {/* Right panel: Flow detail */}
        <div className="glass-panel rounded-2xl border border-[hsl(var(--border))] p-6 overflow-y-auto max-h-[calc(100vh-280px)]">
          {selectedFlow ? (
            <FlowDetail
              flow={selectedFlow}
              allTransactions={allTransactions}
              onStart={(idx) => void handleStart(idx)}
              onStop={(idx) => void handleStop(idx)}
              isStarting={startFlow.isPending}
              isStopping={stopFlow.isPending}
            />
          ) : (
            <div className="flex flex-col items-center justify-center h-full text-center py-12">
              <HelpCircle className="w-10 h-10 text-muted-foreground mb-3" />
              <h3 className="text-sm font-medium mb-1">Select a flow</h3>
              <p className="text-xs text-muted-foreground max-w-xs">
                Click on a transaction or query from the left panel to view its details,
                parameters, and chain visualization.
              </p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
