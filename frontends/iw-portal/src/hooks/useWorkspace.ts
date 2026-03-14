import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { apiFetch } from "@/lib/api";
import { useAuth } from "@/providers/AuthProvider";
import type {
  WorkspaceProjectsResponse,
  WorkspaceProjectDetailResponse,
} from "@/types/workspace";

/** List all workspace integration projects */
export function useWorkspaceProjects() {
  const { user } = useAuth();
  return useQuery({
    queryKey: ["workspace", "projects"],
    queryFn: () => apiFetch<WorkspaceProjectsResponse>("/api/workspace/projects"),
    enabled: !!user,
    staleTime: 30_000,
  });
}

/** Get full details for a single workspace project */
export function useWorkspaceProject(name: string | null) {
  const { user } = useAuth();
  return useQuery({
    queryKey: ["workspace", "projects", name],
    queryFn: () =>
      apiFetch<WorkspaceProjectDetailResponse>(
        `/api/workspace/projects/${encodeURIComponent(name!)}`
      ),
    enabled: !!user && !!name,
    staleTime: 30_000,
  });
}

// ─── Mutations ─────────────────────────────────────────────────

interface CreateTransactionRequest {
  id: string;
  description?: string;
  solution?: string;
  interval?: number;
  shift?: number;
  runAtStartUp?: boolean;
  isActive?: boolean;
  isStateful?: boolean;
  parameters?: Array<{ name: string; value: string; fixed?: boolean }>;
}

interface CreateQueryRequest {
  id: string;
  description?: string;
  solution?: string;
  isActive?: boolean;
  parameters?: Array<{ name: string; value: string; fixed?: boolean }>;
}

interface UpdateTransactionRequest {
  description?: string;
  solution?: string;
  interval?: number;
  shift?: number;
  runAtStartUp?: boolean;
  isActive?: boolean;
  isStateful?: boolean;
  parameters?: Array<{ name: string; value: string; fixed?: boolean }>;
}

interface MutationResponse {
  success: boolean;
  entityId?: string;
  deleted?: string;
  message?: string;
  error?: string;
}

/** Create a new TransactionDescription in a workspace project's config.xml */
export function useCreateTransaction() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: ({ projectName, ...data }: CreateTransactionRequest & { projectName: string }) =>
      apiFetch<MutationResponse>(
        `/api/workspace/projects/${encodeURIComponent(projectName)}/transactions`,
        { method: "POST", body: JSON.stringify(data) }
      ),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ["workspace"] });
      qc.invalidateQueries({ queryKey: ["engine-flows"] });
    },
  });
}

/** Update an existing TransactionDescription */
export function useUpdateTransaction() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: ({ projectName, transactionId, ...data }: UpdateTransactionRequest & { projectName: string; transactionId: string }) =>
      apiFetch<MutationResponse>(
        `/api/workspace/projects/${encodeURIComponent(projectName)}/transactions/${encodeURIComponent(transactionId)}`,
        { method: "PUT", body: JSON.stringify(data) }
      ),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ["workspace"] });
      qc.invalidateQueries({ queryKey: ["engine-flows"] });
    },
  });
}

/** Delete a TransactionDescription from config.xml */
export function useDeleteTransaction() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: ({ projectName, transactionId }: { projectName: string; transactionId: string }) =>
      apiFetch<MutationResponse>(
        `/api/workspace/projects/${encodeURIComponent(projectName)}/transactions/${encodeURIComponent(transactionId)}`,
        { method: "DELETE" }
      ),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ["workspace"] });
      qc.invalidateQueries({ queryKey: ["engine-flows"] });
    },
  });
}

/** Create a new Query element in config.xml */
export function useCreateQuery() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: ({ projectName, ...data }: CreateQueryRequest & { projectName: string }) =>
      apiFetch<MutationResponse>(
        `/api/workspace/projects/${encodeURIComponent(projectName)}/queries`,
        { method: "POST", body: JSON.stringify(data) }
      ),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ["workspace"] });
      qc.invalidateQueries({ queryKey: ["engine-flows"] });
    },
  });
}

/** Delete a Query from config.xml */
export function useDeleteQuery() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: ({ projectName, queryId }: { projectName: string; queryId: string }) =>
      apiFetch<MutationResponse>(
        `/api/workspace/projects/${encodeURIComponent(projectName)}/queries/${encodeURIComponent(queryId)}`,
        { method: "DELETE" }
      ),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ["workspace"] });
      qc.invalidateQueries({ queryKey: ["engine-flows"] });
    },
  });
}
