import { useState } from "react";
import { Link } from "react-router-dom";
import {
  Monitor,
  ExternalLink,
  Activity,
  Play,
  Loader2,
  Workflow,
  ArrowRight,
  ArrowLeftRight,
  Key,
  Server,
  HelpCircle,
  CheckCircle,
  XCircle,
  RefreshCw,
  Search,
} from "lucide-react";
import { cn } from "@/lib/utils";
import { useDashboard, useTransactions } from "@/hooks/useMonitoring";
import { useCompanyProfile } from "@/hooks/useProfile";
import { useCredentials } from "@/hooks/useConfiguration";
import { useEngineFlows } from "@/hooks/useFlows";
import { useDocumentTitle } from "@/hooks/useDocumentTitle";
import { deriveFlows, fmtTime, statusColor, parseFlowSystems } from "@/lib/flow-utils";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Tabs, TabsList, TabsTrigger, TabsContent } from "@/components/ui/tabs";
import { useEngineStatus } from "@/hooks/useEngine";
import { useWorkspaceProjects } from "@/hooks/useWorkspace";
import { deriveSolutionMeta } from "@/types/configuration";
import { KpiRow } from "@/components/integrations/KpiRow";
import { FlowDependencyMap } from "@/components/integrations/FlowDependencyMap";
import { DataPipelineVisualization } from "@/components/integrations/DataPipelineVisualization";
import { EngineControlsTab } from "@/components/integrations/EngineControlsTab";
import { CredentialInventory } from "@/components/integrations/CredentialInventory";
import { OnboardingOverlay } from "@/components/integrations/OnboardingOverlay";
import { WorkspaceSyncPanel } from "@/pages/IDESyncPage";

function statusIcon(status: string) {
  switch (status) {
    case "running":
      return <Play className="w-3.5 h-3.5 text-[hsl(var(--primary))]" />;
    case "success":
      return <CheckCircle className="w-3.5 h-3.5 text-[hsl(var(--success))]" />;
    case "failed":
      return <XCircle className="w-3.5 h-3.5 text-[hsl(var(--destructive))]" />;
    default:
      return <Activity className="w-3.5 h-3.5 text-muted-foreground" />;
  }
}

export function IntegrationOverviewPage() {
  useDocumentTitle("Integrations");

  const { data: dashRes, isLoading: dashLoading, refetch, isFetching } = useDashboard();
  const { data: txRes, isLoading: txLoading } = useTransactions(1, 100);
  const { data: companyData } = useCompanyProfile();
  const { data: credData, isLoading: credLoading } = useCredentials();
  const { data: flowsRes } = useEngineFlows(false); // one-shot for dependency map
  const { data: engineData } = useEngineStatus();
  const { data: wsData } = useWorkspaceProjects();

  const [showOnboarding, setShowOnboarding] = useState(true);
  const [flowSearch, setFlowSearch] = useState("");

  const isLoading = dashLoading || txLoading;
  const summary = dashRes?.data?.summary;
  const running = dashRes?.data?.running_transactions ?? [];
  const transactions = txRes?.data?.transactions ?? [];
  const flows = deriveFlows(transactions);
  const solutionType = companyData?.company?.solutionType;
  const credentials = credData?.data?.credentials ?? [];
  const profileCreds = credData?.data?.profileCredentials;

  // Hub-and-spoke: parse systems from engine flows if available
  const engineAllFlows = [
    ...(flowsRes?.data?.scheduledFlows ?? []),
    ...(flowsRes?.data?.utilityFlows ?? []),
  ];
  const systems = parseFlowSystems(engineAllFlows);

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex items-start justify-between gap-4 flex-wrap">
        <div>
          <h1 className="text-2xl font-semibold">Integrations</h1>
          <p className="text-sm text-muted-foreground mt-1">
            Integration flows and business daemon status
            {solutionType ? ` — ${solutionType}` : ""}
          </p>
        </div>
        <div className="flex items-center gap-2">
          <Button
            variant="ghost"
            size="sm"
            onClick={() => setShowOnboarding(true)}
            className="text-muted-foreground"
          >
            <HelpCircle className="w-4 h-4" />
          </Button>
          <Button
            variant="outline"
            size="sm"
            onClick={() => void refetch()}
            disabled={isFetching}
          >
            <RefreshCw className={cn("w-3 h-3", isFetching && "animate-spin")} />
            Refresh
          </Button>
          <Button variant="outline" size="sm" asChild>
            <a href="/iw-business-daemon/BDConfigurator.jsp">
              <Monitor className="w-3 h-3" />
              Classic IM
              <ExternalLink className="w-3 h-3" />
            </a>
          </Button>
        </div>
      </div>

      {/* KPI row */}
      <KpiRow
        flowCount={flows.length}
        runningCount={summary?.running_count ?? 0}
        successRate={summary?.success_rate_24h ?? 0}
        failedCount={summary?.failed_count_24h ?? 0}
        isLoading={isLoading}
      />

      {/* Data Pipeline Visualization */}
      {solutionType && (() => {
        const meta = deriveSolutionMeta(solutionType);
        const projects = wsData?.projects ?? [];
        const matchingProject = projects.find((p) => p.solutionType === solutionType) ?? projects[0];
        const creds = credentials;
        const srcCred = creds.find((c) => c.credentialType.toLowerCase().includes(meta.crmName.toLowerCase().slice(0, 2)));
        const dstCred = creds.find((c) => c.credentialType.toLowerCase().includes(meta.fsName.toLowerCase().slice(0, 2)));

        function loadTestResult(type: string): boolean | null {
          try {
            const raw = localStorage.getItem(`iw-cred-test-${type}`);
            if (!raw) return null;
            return JSON.parse(raw).reachable ?? null;
          } catch {
            return null;
          }
        }

        const srcTestResult = srcCred ? loadTestResult(srcCred.credentialType) : null;
        const dstTestResult = dstCred ? loadTestResult(dstCred.credentialType) : null;

        return (
          <DataPipelineVisualization
            source={{
              name: meta.crmName,
              connected: srcTestResult === true,
              untested: srcTestResult === null,
            }}
            destination={{
              name: meta.fsName,
              connected: dstTestResult === true,
              untested: dstTestResult === null,
            }}
            engineUp={engineData?.engineUp === true}
            xsltCount={matchingProject?.xsltCount ?? 0}
            transactionCount={matchingProject?.transactionCount ?? 0}
            isLoading={false}
          />
        );
      })()}

      {/* Hub-and-spoke dependency map */}
      {systems.length > 0 && <FlowDependencyMap systems={systems} />}

      {/* Tabs */}
      <Tabs defaultValue="flows">
        <TabsList>
          <TabsTrigger value="flows">
            <Workflow className="w-3.5 h-3.5" />
            Flows
          </TabsTrigger>
          <TabsTrigger value="engine">
            <Server className="w-3.5 h-3.5" />
            Engine Controls
          </TabsTrigger>
          <TabsTrigger value="credentials">
            <Key className="w-3.5 h-3.5" />
            Credentials
            {credentials.length > 0 && (
              <Badge variant="default" className="ml-1 text-[10px] px-1.5 py-0">
                {credentials.length}
              </Badge>
            )}
          </TabsTrigger>
          <TabsTrigger value="sync">
            <ArrowLeftRight className="w-3.5 h-3.5" />
            Workspace Sync
          </TabsTrigger>
        </TabsList>

        {/* Quick links to new pages */}
        <div className="flex items-center gap-2 flex-wrap mt-2 mb-2">
          <Button variant="outline" size="sm" asChild>
            <Link to="/company/flows">
              <Workflow className="w-3 h-3" />
              Flow Builder
            </Link>
          </Button>
          <Button variant="outline" size="sm" asChild>
            <Link to="/company/connections">
              <Key className="w-3 h-3" />
              Connection Manager
            </Link>
          </Button>
          <Button variant="outline" size="sm" asChild>
            <Link to="/company/mappings">
              <ArrowLeftRight className="w-3 h-3" />
              Object Mapping
            </Link>
          </Button>
        </div>

        {/* ── Flows Tab ── */}
        <TabsContent value="flows">
          {/* Running flows — live status */}
          {running.length > 0 && (
            <div className="glass-panel rounded-[var(--radius)] p-4 border-l-4 border-l-[hsl(var(--primary))] mb-4">
              <h2 className="text-sm font-semibold mb-3 flex items-center gap-2">
                <Activity className="w-4 h-4 text-[hsl(var(--primary))] animate-pulse" />
                Running Now
              </h2>
              <div className="space-y-2">
                {running.map((r) => (
                  <div key={r.execution_id} className="flex items-center justify-between text-sm">
                    <div className="flex items-center gap-2">
                      <Play className="w-3.5 h-3.5 text-[hsl(var(--primary))]" />
                      <span className="font-medium">{r.flow_name}</span>
                    </div>
                    <div className="flex items-center gap-4 text-xs text-muted-foreground">
                      <span>{r.records_processed} records</span>
                      <span>{Math.floor(r.duration_ms / 1000)}s elapsed</span>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          )}

          {/* Flow list (derived from transaction history) */}
          <div className="glass-panel rounded-[var(--radius)] p-4">
            <div className="flex items-center justify-between mb-3 flex-wrap gap-2">
              <div className="flex items-center gap-2">
                <h2 className="text-sm font-semibold">Integration Flows</h2>
                {flows.length > 5 && (
                  <div className="relative ml-2">
                    <Search className="absolute left-2 top-1/2 -translate-y-1/2 w-3 h-3 text-muted-foreground pointer-events-none" />
                    <input
                      type="text"
                      placeholder="Filter flows…"
                      value={flowSearch}
                      onChange={(e) => setFlowSearch(e.target.value)}
                      className="pl-7 pr-3 py-1 text-xs rounded-md border border-[hsl(var(--border))] bg-[hsl(var(--card))] focus:outline-none focus:ring-1 focus:ring-[hsl(var(--primary))] placeholder:text-muted-foreground w-44"
                    />
                  </div>
                )}
              </div>
              <Button variant="link" size="sm" asChild>
                <Link to="/monitoring/transactions">
                  Transaction History <ArrowRight className="w-3 h-3" />
                </Link>
              </Button>
            </div>

            {isLoading ? (
              <div className="flex justify-center py-12">
                <Loader2 className="w-6 h-6 animate-spin text-muted-foreground" />
              </div>
            ) : flows.length === 0 ? (
              <div className="text-center py-12">
                <Workflow className="w-8 h-8 text-muted-foreground mx-auto mb-2" />
                <p className="text-sm text-muted-foreground">No integration flows found.</p>
                <p className="text-xs text-muted-foreground mt-1">
                  Flows will appear here once they are configured and executed via the IDE.
                </p>
              </div>
            ) : (
              <div className="space-y-2">
                {flows.filter((f) => !flowSearch || f.name.toLowerCase().includes(flowSearch.toLowerCase())).map((flow) => {
                  const rate =
                    flow.totalRuns > 0
                      ? Math.round((flow.successCount / flow.totalRuns) * 100)
                      : 0;
                  return (
                    <Link
                      key={flow.name}
                      to={`/monitoring/transactions?flow=${encodeURIComponent(flow.name)}`}
                      className={cn(
                        "rounded-xl border bg-[hsl(var(--card))] shadow-sm p-4 flex items-center gap-4 transition-colors hover:bg-muted/30",
                        statusColor(flow.lastStatus)
                      )}
                    >
                      <div className="w-9 h-9 rounded-lg bg-[hsl(var(--muted)/0.3)] grid place-items-center shrink-0">
                        {statusIcon(flow.lastStatus)}
                      </div>
                      <div className="flex-1 min-w-0">
                        <p className="text-sm font-medium truncate">{flow.name}</p>
                        <p className="text-xs text-muted-foreground mt-0.5">
                          Last run: {fmtTime(flow.lastRun)} · {flow.totalRuns} executions
                        </p>
                      </div>
                      <div className="shrink-0 text-right">
                        <p className="text-xs font-medium">{rate}% success</p>
                        <div className="w-20 h-1.5 rounded-full bg-[hsl(var(--muted))] mt-1 overflow-hidden">
                          <div
                            className={cn(
                              "h-full rounded-full transition-all",
                              rate >= 90
                                ? "bg-[hsl(var(--success))]"
                                : rate >= 50
                                  ? "bg-[hsl(var(--warning))]"
                                  : "bg-[hsl(var(--destructive))]"
                            )}
                            style={{ width: `${rate}%` }}
                          />
                        </div>
                      </div>
                      <div className="hidden sm:flex items-center gap-4 text-xs text-muted-foreground shrink-0">
                        <span className="flex items-center gap-1">
                          <CheckCircle className="w-3 h-3 text-[hsl(var(--success))]" />
                          {flow.successCount}
                        </span>
                        <span className="flex items-center gap-1">
                          <XCircle className="w-3 h-3 text-[hsl(var(--destructive))]" />
                          {flow.failedCount}
                        </span>
                      </div>
                    </Link>
                  );
                })}
              </div>
            )}
          </div>
        </TabsContent>

        {/* ── Engine Controls Tab ── */}
        <TabsContent value="engine">
          <EngineControlsTab />
        </TabsContent>

        {/* ── Credentials Tab ── */}
        <TabsContent value="credentials">
          <CredentialInventory
            credentials={credentials}
            profileCredentials={profileCreds}
            isLoading={credLoading}
          />
        </TabsContent>

        {/* ── Workspace Sync Tab ── */}
        <TabsContent value="sync">
          <WorkspaceSyncPanel />
        </TabsContent>
      </Tabs>

      {/* Onboarding overlay */}
      <OnboardingOverlay
        show={showOnboarding}
        onDismiss={() => setShowOnboarding(false)}
      />
    </div>
  );
}
