package com.interweave.businessDaemon.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Server-Sent Events endpoint for real-time sync notifications.
 *
 * GET /api/sync/events — opens an SSE stream. The servlet holds the connection
 * open via AsyncContext and pushes events when sync state changes. A 15-second
 * keepalive prevents proxy timeouts.
 *
 * Other servlets call the static {@link #broadcast(String, String, String)}
 * method to push events to all connected clients.
 */
public class SyncEventServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /** All currently connected SSE clients. Thread-safe. */
    private static final Set<AsyncContext> LISTENERS =
        ConcurrentHashMap.newKeySet();

    /** Keepalive interval — sends SSE comment every 15 seconds to prevent proxy/browser timeout. */
    private static final long KEEPALIVE_INTERVAL_SEC = 15L;

    /** Async timeout — 30 minutes. Clients auto-reconnect via EventSource. */
    private static final long ASYNC_TIMEOUT_MS = 30L * 60L * 1000L;

    private ScheduledExecutorService heartbeatExecutor;

    @Override
    public void init() throws ServletException {
        super.init();
        heartbeatExecutor = Executors.newSingleThreadScheduledExecutor();
        heartbeatExecutor.scheduleAtFixedRate(
            new Runnable() {
                @Override
                public void run() {
                    sendKeepalive();
                }
            },
            KEEPALIVE_INTERVAL_SEC, KEEPALIVE_INTERVAL_SEC, TimeUnit.SECONDS
        );
        log("SyncEventServlet initialized — SSE keepalive every " + KEEPALIVE_INTERVAL_SEC + "s");
    }

    @Override
    public void destroy() {
        if (heartbeatExecutor != null) {
            heartbeatExecutor.shutdownNow();
        }
        for (AsyncContext ctx : LISTENERS) {
            try { ctx.complete(); } catch (Exception ignored) {}
        }
        LISTENERS.clear();
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Auth check — require valid session
        HttpSession session = request.getSession(false);
        if (session == null || !Boolean.TRUE.equals(session.getAttribute("authenticated"))) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"success\":false,\"error\":\"Not authenticated\"}");
            return;
        }

        // SSE headers
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Connection", "keep-alive");
        response.setHeader("X-Accel-Buffering", "no"); // nginx/Vercel — disable response buffering
        response.setHeader("Access-Control-Allow-Origin", "*");

        // Flush headers immediately so the client sees the connection open
        response.flushBuffer();

        // Start async context
        final AsyncContext asyncCtx = request.startAsync();
        asyncCtx.setTimeout(ASYNC_TIMEOUT_MS);

        // Send initial connection event
        try {
            PrintWriter writer = asyncCtx.getResponse().getWriter();
            writer.write("event: connected\ndata: {\"status\":\"connected\"}\n\n");
            writer.flush();
        } catch (IOException e) {
            log("SSE: failed to send initial event", e);
            asyncCtx.complete();
            return;
        }

        // Register cleanup on disconnect/timeout/error
        asyncCtx.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent event) {
                LISTENERS.remove(asyncCtx);
            }
            @Override
            public void onTimeout(AsyncEvent event) {
                LISTENERS.remove(asyncCtx);
                try { asyncCtx.complete(); } catch (Exception ignored) {}
            }
            @Override
            public void onError(AsyncEvent event) {
                LISTENERS.remove(asyncCtx);
                try { asyncCtx.complete(); } catch (Exception ignored) {}
            }
            @Override
            public void onStartAsync(AsyncEvent event) {}
        });

        LISTENERS.add(asyncCtx);
    }

    // ── Static broadcast API (called by other servlets) ─────────────────────

    /**
     * Broadcast a sync event to all connected SSE clients.
     *
     * @param eventType  SSE event name (e.g. "sync-update", "push-complete", "pull-complete")
     * @param profileName  the profile that changed (may be null for broadcast-all events)
     * @param source  who triggered the change: "portal", "ide", "bridge", "system"
     */
    public static void broadcast(String eventType, String profileName, String source) {
        if (LISTENERS.isEmpty()) return;

        String json = "{" +
            "\"profileName\":" + jsonStr(profileName) + "," +
            "\"source\":" + jsonStr(source) + "," +
            "\"timestamp\":" + System.currentTimeMillis() +
            "}";

        String payload = "event: " + eventType + "\ndata: " + json + "\n\n";

        for (AsyncContext ctx : LISTENERS) {
            try {
                PrintWriter writer = ctx.getResponse().getWriter();
                writer.write(payload);
                writer.flush();
                if (writer.checkError()) {
                    // Client disconnected — remove
                    LISTENERS.remove(ctx);
                    try { ctx.complete(); } catch (Exception ignored) {}
                }
            } catch (Exception e) {
                LISTENERS.remove(ctx);
                try { ctx.complete(); } catch (Exception ignored) {}
            }
        }
    }

    /**
     * Returns the number of currently connected SSE clients. Useful for monitoring.
     */
    public static int getListenerCount() {
        return LISTENERS.size();
    }

    // ── Internal ────────────────────────────────────────────────────────────

    private void sendKeepalive() {
        if (LISTENERS.isEmpty()) return;
        for (AsyncContext ctx : LISTENERS) {
            try {
                PrintWriter writer = ctx.getResponse().getWriter();
                writer.write(": keepalive\n\n");
                writer.flush();
                if (writer.checkError()) {
                    LISTENERS.remove(ctx);
                    try { ctx.complete(); } catch (Exception ignored) {}
                }
            } catch (Exception e) {
                LISTENERS.remove(ctx);
                try { ctx.complete(); } catch (Exception ignored) {}
            }
        }
    }

    private static String jsonStr(String s) {
        if (s == null) return "null";
        return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }
}
