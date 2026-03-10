package com.interweave.businessDaemon.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ApiLogViewerServlet - JSON API for browsing production log files.
 *
 * Reads daily log files from the logs/logs/ directory (production server
 * exports) and returns structured JSON for the React LoggingPage.
 *
 * Endpoints:
 *   GET /api/logs/files          - List all log files with metadata
 *   GET /api/logs/summary        - Error count heatmap data (per day)
 *   GET /api/logs/content?date=YYYY-MM-DD&type=catalina - Full log content
 *
 * All endpoints require authentication.
 */
public class ApiLogViewerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Pattern DATE_PATTERN = Pattern.compile("^(\\w[\\w-]*?)\\.(\\d{4}-\\d{2}-\\d{2})\\.log$");
    private static final Pattern ERROR_PATTERN = Pattern.compile(
        "(?i)(SEVERE|ERROR|FATAL|Exception|WARN|WARNING|java\\.\\w+Exception|at\\s+\\w+\\.)"
    );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        setCorsHeaders(response);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"success\":false,\"error\":\"Authentication required\"}");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "";

        File logsDir = resolveLogsDir();
        if (logsDir == null || !logsDir.isDirectory()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"success\":false,\"error\":\"Log directory not found\"}");
            return;
        }

        if ("/files".equals(pathInfo)) {
            handleFileList(logsDir, response);
        } else if ("/summary".equals(pathInfo)) {
            handleSummary(logsDir, response);
        } else if ("/content".equals(pathInfo)) {
            String date = request.getParameter("date");
            String type = request.getParameter("type");
            handleContent(logsDir, date, type, response);
        } else if ("/live".equals(pathInfo)) {
            int lines = 200;
            try { lines = Integer.parseInt(request.getParameter("lines")); } catch (Exception e) { /* use default */ }
            String filter = request.getParameter("filter");
            handleLive(logsDir, lines, filter, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\":false,\"error\":\"Unknown endpoint. Use /files, /summary, /content, or /live\"}");
        }
    }

    /**
     * GET /api/logs/files - Returns list of all log files with metadata.
     */
    private void handleFileList(File logsDir, HttpServletResponse response) throws IOException {
        File[] files = logsDir.listFiles();
        if (files == null) files = new File[0];
        Arrays.sort(files);

        Map<String, List<String>> typeMap = new LinkedHashMap<String, List<String>>();
        int totalFiles = 0;
        int nonEmptyFiles = 0;
        String firstDate = null;
        String lastDate = null;

        for (File f : files) {
            if (!f.isFile()) continue;
            Matcher m = DATE_PATTERN.matcher(f.getName());
            if (!m.matches()) continue;

            String logType = m.group(1);
            String date = m.group(2);
            totalFiles++;
            if (f.length() > 0) nonEmptyFiles++;

            if (!typeMap.containsKey(logType)) {
                typeMap.put(logType, new ArrayList<String>());
            }
            typeMap.get(logType).add(date);

            if (firstDate == null || date.compareTo(firstDate) < 0) firstDate = date;
            if (lastDate == null || date.compareTo(lastDate) > 0) lastDate = date;
        }

        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"success\":true,\"totalFiles\":").append(totalFiles);
        sb.append(",\"nonEmptyFiles\":").append(nonEmptyFiles);
        sb.append(",\"firstDate\":").append(jsonStr(firstDate));
        sb.append(",\"lastDate\":").append(jsonStr(lastDate));
        sb.append(",\"types\":{");

        boolean firstType = true;
        for (Map.Entry<String, List<String>> entry : typeMap.entrySet()) {
            if (!firstType) sb.append(',');
            firstType = false;
            sb.append(jsonStr(entry.getKey())).append(":{");
            sb.append("\"count\":").append(entry.getValue().size());
            int ne = 0;
            for (String d : entry.getValue()) {
                File check = new File(logsDir, entry.getKey() + "." + d + ".log");
                if (check.length() > 0) ne++;
            }
            sb.append(",\"nonEmpty\":").append(ne);
            sb.append('}');
        }
        sb.append("}}");
        out.write(sb.toString());
    }

    /**
     * GET /api/logs/summary - Returns per-day error counts for heatmap.
     */
    private void handleSummary(File logsDir, HttpServletResponse response) throws IOException {
        File[] files = logsDir.listFiles();
        if (files == null) files = new File[0];
        Arrays.sort(files);

        // Map: date -> { type -> { total, errors, severe, warnings } }
        Map<String, Map<String, int[]>> dayMap = new LinkedHashMap<String, Map<String, int[]>>();
        Map<String, Map<String, Long>> fileSizeMap = new LinkedHashMap<String, Map<String, Long>>();
        Map<String, Map<String, String>> topErrorMap = new LinkedHashMap<String, Map<String, String>>();
        Map<String, Map<String, Integer>> topErrorCountMap = new LinkedHashMap<String, Map<String, Integer>>();

        for (File f : files) {
            if (!f.isFile() || f.length() == 0) continue;
            Matcher m = DATE_PATTERN.matcher(f.getName());
            if (!m.matches()) continue;

            String logType = m.group(1);
            String date = m.group(2);

            int totalLines = 0;
            int errorLines = 0;
            int severeLines = 0;
            int warnLines = 0;
            Map<String, Integer> patternCounts = new LinkedHashMap<String, Integer>();

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    totalLines++;
                    String upper = line.toUpperCase();
                    boolean isError = false;
                    if (upper.contains("SEVERE") || upper.contains("FATAL") || upper.contains("ERROR")) {
                        severeLines++;
                        errorLines++;
                        isError = true;
                    } else if (upper.contains("WARN")) {
                        warnLines++;
                        errorLines++;
                        isError = true;
                    } else if (upper.contains("EXCEPTION")) {
                        errorLines++;
                        isError = true;
                    }
                    if (isError) {
                        String pattern = extractErrorPattern(line);
                        if (pattern != null) {
                            Integer cnt = patternCounts.get(pattern);
                            patternCounts.put(pattern, cnt == null ? 1 : cnt + 1);
                        }
                    }
                }
            } finally {
                if (reader != null) try { reader.close(); } catch (IOException e) { /* ignore */ }
            }

            // Find the top error pattern
            String topPattern = null;
            int topCount = 0;
            for (Map.Entry<String, Integer> pe : patternCounts.entrySet()) {
                if (pe.getValue() > topCount) {
                    topCount = pe.getValue();
                    topPattern = pe.getKey();
                }
            }

            if (!dayMap.containsKey(date)) {
                dayMap.put(date, new LinkedHashMap<String, int[]>());
            }
            dayMap.get(date).put(logType, new int[]{ totalLines, errorLines, severeLines, warnLines });

            if (!fileSizeMap.containsKey(date)) {
                fileSizeMap.put(date, new LinkedHashMap<String, Long>());
            }
            fileSizeMap.get(date).put(logType, f.length());

            if (!topErrorMap.containsKey(date)) {
                topErrorMap.put(date, new LinkedHashMap<String, String>());
            }
            topErrorMap.get(date).put(logType, topPattern);

            if (!topErrorCountMap.containsKey(date)) {
                topErrorCountMap.put(date, new LinkedHashMap<String, Integer>());
            }
            topErrorCountMap.get(date).put(logType, topCount);
        }

        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"success\":true,\"days\":[");

        boolean first = true;
        for (Map.Entry<String, Map<String, int[]>> dayEntry : dayMap.entrySet()) {
            if (!first) sb.append(',');
            first = false;

            String date = dayEntry.getKey();
            int totalErrors = 0;
            int totalSevere = 0;
            int totalLines = 0;
            int totalWarnings = 0;

            sb.append("{\"date\":").append(jsonStr(date));

            // Aggregate across all log types for this day
            for (Map.Entry<String, int[]> typeEntry : dayEntry.getValue().entrySet()) {
                int[] counts = typeEntry.getValue();
                totalLines += counts[0];
                totalErrors += counts[1];
                totalSevere += counts[2];
                totalWarnings += counts[3];
            }

            sb.append(",\"totalLines\":").append(totalLines);
            sb.append(",\"errors\":").append(totalErrors);
            sb.append(",\"severe\":").append(totalSevere);
            sb.append(",\"warnings\":").append(totalWarnings);

            // Per-type breakdown
            sb.append(",\"types\":{");
            boolean firstT = true;
            for (Map.Entry<String, int[]> typeEntry : dayEntry.getValue().entrySet()) {
                if (!firstT) sb.append(',');
                firstT = false;
                int[] c = typeEntry.getValue();
                String tKey = typeEntry.getKey();
                sb.append(jsonStr(tKey)).append(":{");
                sb.append("\"lines\":").append(c[0]);
                sb.append(",\"errors\":").append(c[1]);
                sb.append(",\"severe\":").append(c[2]);
                sb.append(",\"warnings\":").append(c[3]);
                sb.append(",\"fileSize\":").append(fileSizeMap.getOrDefault(date, Collections.<String, Long>emptyMap()).getOrDefault(tKey, 0L));
                String topErr = topErrorMap.getOrDefault(date, Collections.<String, String>emptyMap()).get(tKey);
                sb.append(",\"topError\":").append(jsonStr(topErr));
                int topCnt = topErrorCountMap.getOrDefault(date, Collections.<String, Integer>emptyMap()).getOrDefault(tKey, 0);
                sb.append(",\"topErrorCount\":").append(topCnt);
                sb.append('}');
            }
            sb.append("}}");
        }

        sb.append("]}");
        out.write(sb.toString());
    }

    /**
     * GET /api/logs/content?date=YYYY-MM-DD&type=catalina - Returns log file content.
     */
    private void handleContent(File logsDir, String date, String type, HttpServletResponse response)
            throws IOException {

        if (date == null || date.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\":false,\"error\":\"date parameter required\"}");
            return;
        }
        if (type == null || type.isEmpty()) {
            type = "catalina";
        }

        // Sanitize inputs to prevent path traversal
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\":false,\"error\":\"Invalid date format. Use YYYY-MM-DD\"}");
            return;
        }
        if (!type.matches("[a-zA-Z][a-zA-Z0-9_-]*")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\":false,\"error\":\"Invalid log type\"}");
            return;
        }

        File logFile = new File(logsDir, type + "." + date + ".log");
        if (!logFile.isFile()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"success\":false,\"error\":\"Log file not found: " + type + "." + date + ".log\"}");
            return;
        }

        List<String> lines = new ArrayList<String>();
        int errorCount = 0;
        int severeCount = 0;
        int warnCount = 0;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(logFile), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                String upper = line.toUpperCase();
                if (upper.contains("SEVERE") || upper.contains("FATAL") || upper.contains("ERROR")) {
                    severeCount++;
                    errorCount++;
                } else if (upper.contains("WARN")) {
                    warnCount++;
                    errorCount++;
                } else if (upper.contains("EXCEPTION")) {
                    errorCount++;
                }
            }
        } finally {
            if (reader != null) try { reader.close(); } catch (IOException e) { /* ignore */ }
        }

        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"success\":true");
        sb.append(",\"date\":").append(jsonStr(date));
        sb.append(",\"type\":").append(jsonStr(type));
        sb.append(",\"fileName\":").append(jsonStr(logFile.getName()));
        sb.append(",\"fileSize\":").append(logFile.length());
        sb.append(",\"totalLines\":").append(lines.size());
        sb.append(",\"errorCount\":").append(errorCount);
        sb.append(",\"severeCount\":").append(severeCount);
        sb.append(",\"warnCount\":").append(warnCount);
        sb.append(",\"lines\":[");

        for (int i = 0; i < lines.size(); i++) {
            if (i > 0) sb.append(',');
            String l = lines.get(i);
            String upper = l.toUpperCase();
            String level = "info";
            if (upper.contains("SEVERE") || upper.contains("FATAL") || upper.contains("ERROR")) {
                level = "error";
            } else if (upper.contains("WARN")) {
                level = "warn";
            } else if (upper.contains("EXCEPTION") || upper.startsWith("\tat ") || upper.startsWith("AT ")) {
                level = "error";
            }

            sb.append("{\"num\":").append(i + 1);
            sb.append(",\"level\":").append(jsonStr(level));
            sb.append(",\"text\":").append(jsonStr(l));
            sb.append('}');
        }

        sb.append("]}");
        out.write(sb.toString());
    }

    /**
     * GET /api/logs/live?lines=200&filter=ERROR - Returns last N lines of catalina.out.
     */
    private void handleLive(File logsDir, int lines, String filter, HttpServletResponse response)
            throws IOException {
        // catalina.out lives in the same logs dir
        File catalinaOut = new File(logsDir, "catalina.out");
        if (!catalinaOut.isFile()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"success\":false,\"error\":\"catalina.out not found\"}");
            return;
        }

        if (lines < 1) lines = 50;
        if (lines > 5000) lines = 5000;

        // Read file into a circular buffer of last N lines
        List<String> buffer = new ArrayList<String>(lines + 1);
        BufferedReader reader = null;
        long totalLines = 0;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(catalinaOut), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                totalLines++;
                if (filter != null && !filter.isEmpty() && !line.toUpperCase().contains(filter.toUpperCase())) continue;
                buffer.add(line);
                if (buffer.size() > lines) buffer.remove(0);
            }
        } finally {
            if (reader != null) try { reader.close(); } catch (IOException e) { /* ignore */ }
        }

        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"success\":true");
        sb.append(",\"file\":\"catalina.out\"");
        sb.append(",\"fileSize\":").append(catalinaOut.length());
        sb.append(",\"totalLines\":").append(totalLines);
        sb.append(",\"returnedLines\":").append(buffer.size());
        sb.append(",\"lines\":[");

        for (int i = 0; i < buffer.size(); i++) {
            if (i > 0) sb.append(',');
            String l = buffer.get(i);
            String upper = l.toUpperCase();
            String level = "info";
            if (upper.contains("SEVERE") || upper.contains("FATAL") || (upper.contains("ERROR") && !upper.contains("NOTFOUNDERROR"))) {
                level = "error";
            } else if (upper.contains("WARN")) {
                level = "warn";
            } else if (upper.contains("IW 2.41 TS") || upper.contains("IW 2.41")) {
                level = "ts";
            } else if (upper.contains("EXCEPTION") || upper.startsWith("\tat ") || upper.startsWith("AT ")) {
                level = "error";
            }
            sb.append("{\"num\":").append(totalLines - buffer.size() + i + 1);
            sb.append(",\"level\":").append(jsonStr(level));
            sb.append(",\"text\":").append(jsonStr(l));
            sb.append('}');
        }

        sb.append("]}");
        out.write(sb.toString());
    }

    /**
     * Resolves the Tomcat logs directory.
     */
    private File resolveLogsDir() {
        // Primary: resolve from webapp real path
        // webapps/iw-business-daemon -> webapps -> tomcat -> web_portal -> repo root
        String realPath = getServletContext().getRealPath("/");
        if (realPath != null) {
            File webappDir = new File(realPath);
            // Go up: iw-business-daemon -> webapps -> tomcat -> web_portal -> repo root
            File tomcatDir = webappDir.getParentFile().getParentFile();
            File logsDir = new File(tomcatDir, "logs");
            if (logsDir.isDirectory()) return logsDir;
        }

        // Fallback: catalina.home system property (Tomcat sets this)
        String catalinaHome = System.getProperty("catalina.home");
        if (catalinaHome != null) {
            File logsDir = new File(catalinaHome, "logs");
            if (logsDir.isDirectory()) return logsDir;
        }

        // Last resort: known absolute path
        File fallback = new File("C:/IW_IDE/IW_Launcher/web_portal/tomcat/logs");
        if (fallback.isDirectory()) return fallback;

        return null;
    }

    private static final Pattern EXCEPTION_CLASS_PATTERN = Pattern.compile(
        "\\bjava\\.\\w+\\.([A-Z]\\w*Exception)\\b"
    );
    private static final Pattern PATH_BRACKET_PATTERN = Pattern.compile(
        "\\[[A-Za-z]:\\\\[^\\]]*\\]"
    );

    /**
     * Extracts a normalized error pattern from a log line.
     * Returns null for stack-trace continuation lines.
     */
    private static String extractErrorPattern(String line) {
        if (line == null || line.isEmpty()) return null;

        // Skip stack trace lines
        String trimmed = line.trim();
        if (trimmed.startsWith("at ") || trimmed.startsWith("...") || trimmed.startsWith("Caused by:")) {
            return null;
        }

        // Check for Java exception class name
        Matcher excM = EXCEPTION_CLASS_PATTERN.matcher(line);
        if (excM.find()) {
            return excM.group(1);
        }

        // Check for SEVERE: or ERROR: and extract message after colon
        String upper = line.toUpperCase();
        int idx = -1;
        if (upper.contains("SEVERE:")) {
            idx = upper.indexOf("SEVERE:") + 7;
        } else if (upper.contains("ERROR:")) {
            idx = upper.indexOf("ERROR:") + 6;
        }

        String result;
        if (idx > 0 && idx < line.length()) {
            result = line.substring(idx).trim();
            // Replace file paths in brackets with [...]
            result = PATH_BRACKET_PATTERN.matcher(result).replaceAll("[...]");
        } else {
            result = line;
        }

        if (result.length() > 80) {
            result = result.substring(0, 80);
        }
        return result;
    }

    private static String jsonStr(String s) {
        if (s == null) return "null";
        StringBuilder sb = new StringBuilder("\"");
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"': sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\t': sb.append("\\t"); break;
                default:
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
        return sb.toString();
    }

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
