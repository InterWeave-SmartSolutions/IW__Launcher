import { useEffect, useRef, useCallback } from "react";
import { useQueryClient } from "@tanstack/react-query";
import { getAuthToken } from "@/lib/api";

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const BASE_URL: string = (import.meta as any).env?.VITE_API_BASE_URL || "/iw-business-daemon";

export interface SyncEvent {
  profileName: string | null;
  source: "portal" | "ide" | "bridge" | "system";
  timestamp: number;
}

type SyncEventType = "connected" | "push-complete" | "pull-complete" | "sync-update";

interface UseSyncSSEOptions {
  /** Set false to disable the SSE connection. Default true. */
  enabled?: boolean;
  /** Called on every sync event (after query invalidation). */
  onEvent?: (type: SyncEventType, data: SyncEvent) => void;
}

/**
 * Opens an SSE connection to /api/sync/events for real-time sync notifications.
 * Automatically invalidates React Query caches when sync events arrive,
 * replacing the 10-second polling latency with sub-second push.
 *
 * Falls back gracefully — if SSE fails or is unavailable, the existing
 * polling in useSyncStatus() continues to work independently.
 */
export function useSyncSSE(options: UseSyncSSEOptions = {}) {
  const { enabled = true, onEvent } = options;
  const qc = useQueryClient();
  const eventSourceRef = useRef<EventSource | null>(null);
  const reconnectTimeoutRef = useRef<ReturnType<typeof setTimeout>>(undefined);

  const invalidateSync = useCallback(() => {
    qc.invalidateQueries({ queryKey: ["sync-status"] });
    qc.invalidateQueries({ queryKey: ["config"] });
    qc.invalidateQueries({ queryKey: ["engine-flows"] });
    qc.invalidateQueries({ queryKey: ["company-profile"] });
  }, [qc]);

  useEffect(() => {
    if (!enabled) return;

    let cancelled = false;

    function connect() {
      if (cancelled) return;

      // Build SSE URL — EventSource doesn't support custom headers,
      // so we pass the auth token as a query param for proxy deployments.
      let url = `${BASE_URL}/api/sync/events`;
      const token = getAuthToken();
      if (token) {
        url += `?token=${encodeURIComponent(token)}`;
      }

      const es = new EventSource(url, { withCredentials: true });
      eventSourceRef.current = es;

      es.addEventListener("connected", () => {
        // Connection established — no action needed
      });

      es.addEventListener("push-complete", (e) => {
        const data = parseData(e);
        invalidateSync();
        onEvent?.("push-complete", data);
      });

      es.addEventListener("pull-complete", (e) => {
        const data = parseData(e);
        invalidateSync();
        onEvent?.("pull-complete", data);
      });

      es.addEventListener("sync-update", (e) => {
        const data = parseData(e);
        invalidateSync();
        onEvent?.("sync-update", data);
      });

      es.onerror = () => {
        // EventSource auto-reconnects on network errors, but if readyState
        // is CLOSED (2), the browser gave up — reconnect manually after delay.
        if (es.readyState === EventSource.CLOSED && !cancelled) {
          es.close();
          eventSourceRef.current = null;
          reconnectTimeoutRef.current = setTimeout(connect, 5000);
        }
      };
    }

    connect();

    return () => {
      cancelled = true;
      if (reconnectTimeoutRef.current) {
        clearTimeout(reconnectTimeoutRef.current);
      }
      if (eventSourceRef.current) {
        eventSourceRef.current.close();
        eventSourceRef.current = null;
      }
    };
  }, [enabled, invalidateSync, onEvent]);

  return {
    /** True if EventSource is currently connected. */
    connected: eventSourceRef.current?.readyState === EventSource.OPEN,
  };
}

function parseData(event: MessageEvent): SyncEvent {
  try {
    return JSON.parse(event.data);
  } catch {
    return { profileName: null, source: "system", timestamp: Date.now() };
  }
}
