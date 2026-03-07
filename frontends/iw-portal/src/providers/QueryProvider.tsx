import { useState } from "react";
import { QueryClient, QueryClientProvider, MutationCache } from "@tanstack/react-query";
import type { ReactNode } from "react";
import { useToast } from "@/providers/ToastProvider";
import { ApiError } from "@/lib/api";

export function QueryProvider({ children }: { children: ReactNode }) {
  const { showToast } = useToast();

  const [queryClient] = useState(
    () =>
      new QueryClient({
        defaultOptions: {
          queries: {
            staleTime: 30_000,
            retry: 1,
            refetchOnWindowFocus: false,
          },
        },
        mutationCache: new MutationCache({
          onError: (error) => {
            // Show toast for mutation failures (saves, deletes, etc.)
            // Individual mutations can override by catching the error themselves
            if (error instanceof ApiError) {
              if (error.status === 401) {
                showToast("Session expired — please log in again", "error");
              } else if (error.status >= 500) {
                showToast(`Server error: ${error.message}`, "error");
              }
              // 4xx errors (400, 403, 404, 409) are typically handled by the calling page
            } else if (error instanceof TypeError && error.message === "Failed to fetch") {
              showToast("Network error — server may be offline", "error");
            }
          },
        }),
      })
  );

  return <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>;
}
