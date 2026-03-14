import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useCredentials, useSaveCredential, useTestCredential } from "@/hooks/useConfiguration";
import { useWorkspaceProjects } from "@/hooks/useWorkspace";
import { apiFetch } from "@/lib/api";
import type { CompanyCredential } from "@/types/configuration";
import type { WorkspaceProject } from "@/types/workspace";

/** Test result cached in localStorage per credential type */
export interface ConnectionTestResult {
  reachable: boolean;
  statusCode: number;
  responseTimeMs: number;
  testedAt: string;
}

export function loadTestResult(credentialType: string): ConnectionTestResult | null {
  try {
    const raw = localStorage.getItem(`iw-cred-test-${credentialType}`);
    if (!raw) return null;
    return JSON.parse(raw);
  } catch {
    return null;
  }
}

export function saveTestResult(credentialType: string, result: ConnectionTestResult): void {
  localStorage.setItem(`iw-cred-test-${credentialType}`, JSON.stringify(result));
}

/** Combined connection view merging credential data with workspace info */
export interface ConnectionView {
  credential: CompanyCredential;
  testResult: ConnectionTestResult | null;
  /** Which workspace project(s) reference this system type */
  relatedProjects: string[];
}

/** Derive a human-readable system name from credential type codes */
export function systemDisplayName(credentialType: string): string {
  const map: Record<string, string> = {
    salesforce: "Salesforce",
    sf: "Salesforce",
    quickbooks: "QuickBooks",
    qb: "QuickBooks",
    creatio: "Creatio CRM",
    crm: "CRM",
    magento: "Magento 2",
    mg: "Magento 2",
    authorizenet: "Authorize.Net",
    auth: "Authorize.Net",
    netsuite: "NetSuite",
    ns: "NetSuite",
    sage: "Sage",
    pt: "Sage",
  };
  const lower = credentialType.toLowerCase();
  return map[lower] ?? credentialType;
}

/** Build combined connection views from credential + workspace data */
export function buildConnectionViews(
  credentials: CompanyCredential[],
  projects: WorkspaceProject[]
): ConnectionView[] {
  return credentials.map((cred) => {
    const testResult = loadTestResult(cred.credentialType);
    // Naive project matching: check if solution type contains the system code
    const lower = cred.credentialType.toLowerCase();
    const relatedProjects = projects
      .filter((p) => p.solutionType.toLowerCase().includes(lower.slice(0, 2)))
      .map((p) => p.name);

    return { credential: cred, testResult, relatedProjects };
  });
}

/** Hook combining credentials + workspace for a unified connection model */
export function useConnections() {
  const credQuery = useCredentials();
  const wsQuery = useWorkspaceProjects();
  const saveCredential = useSaveCredential();
  const testCredential = useTestCredential();

  const credentials = credQuery.data?.data?.credentials ?? [];
  const profileCredentials = credQuery.data?.data?.profileCredentials;
  const projects = wsQuery.data?.projects ?? [];

  const connections = buildConnectionViews(credentials, projects);

  return {
    connections,
    credentials,
    profileCredentials,
    projects,
    isLoading: credQuery.isLoading || wsQuery.isLoading,
    error: credQuery.error || wsQuery.error,
    saveCredential,
    testCredential,
    refetch: () => {
      void credQuery.refetch();
      void wsQuery.refetch();
    },
  };
}

/** Save a connection to the workspace dataconnections.xslt */
export function useSaveWorkspaceConnection() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: (data: {
      projectName: string;
      target: "source" | "destination";
      driver: string;
      url: string;
      user: string;
      password: string;
    }) =>
      apiFetch<{ success: boolean; message?: string }>(
        `/api/workspace/projects/${encodeURIComponent(data.projectName)}/connections`,
        {
          method: "PUT",
          body: JSON.stringify(data),
        }
      ),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ["workspace"] });
      qc.invalidateQueries({ queryKey: ["config"] });
    },
  });
}
