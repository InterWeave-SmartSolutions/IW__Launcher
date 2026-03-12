package com.interweave.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple token-bucket rate limiter per client IP address.
 *
 * Configured via web.xml init-params:
 *   maxRequests  — max requests per window (default 100)
 *   windowMs     — window size in milliseconds (default 60000 = 1 minute)
 *
 * Returns 429 Too Many Requests when the limit is exceeded.
 * Stale entries are lazily cleaned up every 5 minutes.
 */
public class RateLimitFilter implements Filter {

    private int maxRequests = 100;
    private long windowMs = 60_000L;
    private static final long CLEANUP_INTERVAL_MS = 5 * 60 * 1000L;

    private final ConcurrentHashMap<String, long[]> requestCounts = new ConcurrentHashMap<>();
    private volatile long lastCleanup = System.currentTimeMillis();

    @Override
    public void init(FilterConfig config) throws ServletException {
        String max = config.getInitParameter("maxRequests");
        if (max != null) {
            try { maxRequests = Integer.parseInt(max); } catch (NumberFormatException ignored) {}
        }
        String window = config.getInitParameter("windowMs");
        if (window != null) {
            try { windowMs = Long.parseLong(window); } catch (NumberFormatException ignored) {}
        }
        config.getServletContext().log(
            "RateLimitFilter initialized: " + maxRequests + " requests per " + (windowMs / 1000) + "s");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        String clientIp = getClientIp(httpReq);
        long now = System.currentTimeMillis();

        // Lazy cleanup of stale entries
        if (now - lastCleanup > CLEANUP_INTERVAL_MS) {
            lastCleanup = now;
            long cutoff = now - windowMs;
            requestCounts.entrySet().removeIf(e -> e.getValue()[1] < cutoff);
        }

        // Check and update rate limit
        long[] bucket = requestCounts.compute(clientIp, (key, existing) -> {
            if (existing == null || (now - existing[1]) > windowMs) {
                // New window: [count=1, windowStart=now]
                return new long[]{1, now};
            }
            // Same window: increment count
            existing[0]++;
            return existing;
        });

        if (bucket[0] > maxRequests) {
            httpRes.setStatus(429);
            httpRes.setContentType("application/json");
            httpRes.setCharacterEncoding("UTF-8");
            httpRes.setHeader("Retry-After", String.valueOf(windowMs / 1000));
            PrintWriter out = httpRes.getWriter();
            out.print("{\"success\":false,\"error\":\"Too many requests. Please try again later.\"}");
            out.flush();
            return;
        }

        chain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        // Check common proxy headers
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isEmpty()) {
            // Take first IP (original client)
            int comma = forwarded.indexOf(',');
            return (comma > 0) ? forwarded.substring(0, comma).trim() : forwarded.trim();
        }
        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !realIp.isEmpty()) {
            return realIp.trim();
        }
        return request.getRemoteAddr();
    }

    @Override
    public void destroy() {
        requestCounts.clear();
    }
}
