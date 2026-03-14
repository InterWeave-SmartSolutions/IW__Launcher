import { useQuery } from "@tanstack/react-query";
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
