package com.interweave.monitoring.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.interweave.error.ErrorCode;

/**
 * AlertConfigApiServlet - Provides CRUD operations for alert rules and webhook endpoints.
 *
 * This servlet manages alert configuration including:
 * - Alert rules for email and webhook notifications
 * - Webhook endpoint configuration
 * - Alert history tracking
 *
 * Supports multi-tenant filtering by company_id with security enforcement.
 *
 * API Endpoints:
 *
 * 1. Alert Rules Management
 *    - GET    /api/monitoring/alerts           - List all alert rules
 *    - GET    /api/monitoring/alerts/{id}      - Get specific alert rule
 *    - POST   /api/monitoring/alerts           - Create new alert rule
 *    - PUT    /api/monitoring/alerts/{id}      - Update alert rule
 *    - DELETE /api/monitoring/alerts/{id}      - Delete alert rule
 *    - GET    /api/monitoring/alerts/history   - Get alert history
 *
 * 2. Webhook Endpoints Management
 *    - GET    /api/monitoring/webhooks         - List all webhook endpoints
 *    - GET    /api/monitoring/webhooks/{id}    - Get specific webhook endpoint
 *    - POST   /api/monitoring/webhooks         - Create new webhook endpoint
 *    - PUT    /api/monitoring/webhooks/{id}    - Update webhook endpoint
 *    - DELETE /api/monitoring/webhooks/{id}    - Delete webhook endpoint
 *
 * @author InterWeave Monitoring Dashboard
 * @version 1.0
 */
public class AlertConfigApiServlet extends MonitoringApiServlet {

    private static final long serialVersionUID = 1L;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );
    private static final Pattern URL_PATTERN = Pattern.compile(
        "^https?://[a-zA-Z0-9.-]+(:[0-9]+)?(/.*)?$"
    );

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws ServletException, IOException, SQLException {

        String pathInfo = request.getPathInfo();
        String method = request.getMethod();

        // Route to appropriate handler based on path
        if (pathInfo == null || pathInfo.equals("/")) {
            // Base endpoints: /api/monitoring/alerts or /api/monitoring/webhooks
            String servletPath = request.getServletPath();
            if (servletPath.endsWith("/alerts")) {
                handleAlertRulesRequest(request, response, conn, method, null);
            } else if (servletPath.endsWith("/webhooks")) {
                handleWebhooksRequest(request, response, conn, method, null);
            } else {
                sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND,
                    ErrorCode.VALIDATION001, "Invalid endpoint path");
            }
        } else {
            // Resource-specific endpoints
            String servletPath = request.getServletPath();
            if (servletPath.endsWith("/alerts")) {
                String resourcePath = pathInfo.substring(1);
                if ("history".equals(resourcePath)) {
                    if ("GET".equals(method)) {
                        handleAlertHistory(request, response, conn);
                    } else {
                        sendErrorResponse(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                            ErrorCode.VALIDATION001, "Only GET method is supported for alert history endpoint");
                    }
                } else {
                    handleAlertRulesRequest(request, response, conn, method, resourcePath);
                }
            } else if (servletPath.endsWith("/webhooks")) {
                String resourceId = pathInfo.substring(1);
                handleWebhooksRequest(request, response, conn, method, resourceId);
            } else {
                sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND,
                    ErrorCode.VALIDATION001, "Invalid endpoint path");
            }
        }
    }

    /**
     * Handles alert rules requests (list, get, create, update, delete)
     */
    private void handleAlertRulesRequest(HttpServletRequest request, HttpServletResponse response,
                                        Connection conn, String method, String resourceId)
            throws ServletException, IOException, SQLException {

        switch (method) {
            case "GET":
                if (resourceId == null) {
                    listAlertRules(request, response, conn);
                } else {
                    getAlertRule(request, response, conn, resourceId);
                }
                break;
            case "POST":
                createAlertRule(request, response, conn);
                break;
            case "PUT":
                if (resourceId == null) {
                    sendValidationError(response, "Alert rule ID is required for update");
                } else {
                    updateAlertRule(request, response, conn, resourceId);
                }
                break;
            case "DELETE":
                if (resourceId == null) {
                    sendValidationError(response, "Alert rule ID is required for deletion");
                } else {
                    deleteAlertRule(request, response, conn, resourceId);
                }
                break;
            default:
                sendErrorResponse(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                    ErrorCode.VALIDATION001, "Method " + method + " is not supported for alert rules");
        }
    }

    /**
     * Handles webhook endpoints requests (list, get, create, update, delete)
     */
    private void handleWebhooksRequest(HttpServletRequest request, HttpServletResponse response,
                                      Connection conn, String method, String resourceId)
            throws ServletException, IOException, SQLException {

        switch (method) {
            case "GET":
                if (resourceId == null) {
                    listWebhooks(request, response, conn);
                } else {
                    getWebhook(request, response, conn, resourceId);
                }
                break;
            case "POST":
                createWebhook(request, response, conn);
                break;
            case "PUT":
                if (resourceId == null) {
                    sendValidationError(response, "Webhook ID is required for update");
                } else {
                    updateWebhook(request, response, conn, resourceId);
                }
                break;
            case "DELETE":
                if (resourceId == null) {
                    sendValidationError(response, "Webhook ID is required for deletion");
                } else {
                    deleteWebhook(request, response, conn, resourceId);
                }
                break;
            default:
                sendErrorResponse(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                    ErrorCode.VALIDATION001, "Method " + method + " is not supported for webhooks");
        }
    }

    /**
     * Lists all alert rules for the user's company
     */
    private void listAlertRules(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws ServletException, IOException, SQLException {

        Integer userCompanyId = getCompanyId(request);
        boolean isAdminUser = isAdmin(request);

        // Parse optional company_id filter
        Integer filterCompanyId = parseCompanyIdFilter(request, userCompanyId, isAdminUser, response);
        if (filterCompanyId == null && response.isCommitted()) {
            return;
        }

        StringBuilder json = new StringBuilder();
        json.append("[");

        String sql =
            "SELECT " +
            "  ar.id, " +
            "  ar.company_id, " +
            "  ar.rule_name, " +
            "  ar.description, " +
            "  ar.project_id, " +
            "  p.name AS project_name, " +
            "  ar.flow_name, " +
            "  ar.alert_on_failure, " +
            "  ar.alert_on_timeout, " +
            "  ar.failure_threshold, " +
            "  ar.threshold_window_minutes, " +
            "  ar.notification_type, " +
            "  ar.email_recipients, " +
            "  ar.webhook_endpoint_ids, " +
            "  ar.cooldown_minutes, " +
            "  ar.max_alerts_per_day, " +
            "  ar.is_enabled, " +
            "  ar.last_triggered_at, " +
            "  ar.alerts_sent_today, " +
            "  ar.created_at, " +
            "  ar.updated_at " +
            "FROM alert_rules ar " +
            "LEFT JOIN projects p ON ar.project_id = p.id " +
            "WHERE ar.company_id = ? " +
            "ORDER BY ar.rule_name";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, filterCompanyId);

            try (ResultSet rs = stmt.executeQuery()) {
                boolean first = true;
                while (rs.next()) {
                    if (!first) {
                        json.append(",");
                    }
                    first = false;

                    json.append(buildAlertRuleJson(rs));
                }
            }
        }

        json.append("]");

        String successJson = buildSuccessJson("\"data\":" + json.toString());
        sendJsonResponse(response, successJson);
    }

    /**
     * Gets a specific alert rule by ID
     */
    private void getAlertRule(HttpServletRequest request, HttpServletResponse response,
                             Connection conn, String alertRuleId)
            throws ServletException, IOException, SQLException {

        Integer userCompanyId = getCompanyId(request);
        boolean isAdminUser = isAdmin(request);

        String sql =
            "SELECT " +
            "  ar.*, " +
            "  p.name AS project_name " +
            "FROM alert_rules ar " +
            "LEFT JOIN projects p ON ar.project_id = p.id " +
            "WHERE ar.id = ? " +
            (isAdminUser ? "" : "AND ar.company_id = ?");

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            try {
                int id = Integer.parseInt(alertRuleId);
                stmt.setInt(1, id);
                if (!isAdminUser) {
                    stmt.setInt(2, userCompanyId);
                }
            } catch (NumberFormatException e) {
                sendValidationError(response, "Invalid alert rule ID. Must be a valid integer.");
                return;
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String alertJson = buildAlertRuleJson(rs);
                    String successJson = buildSuccessJson("\"data\":" + alertJson);
                    sendJsonResponse(response, successJson);
                } else {
                    sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND,
                        ErrorCode.VALIDATION001, "Alert rule not found or you do not have permission to view it");
                }
            }
        }
    }

    /**
     * Creates a new alert rule
     */
    private void createAlertRule(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws ServletException, IOException, SQLException {

        Integer userCompanyId = getCompanyId(request);
        Integer userId = getUserId(request);

        // Parse JSON from request body
        AlertRuleData data = parseAlertRuleJson(request);
        if (data == null) {
            sendValidationError(response, "Invalid JSON in request body");
            return;
        }

        // Validate required fields
        if (data.ruleName == null || data.ruleName.trim().isEmpty()) {
            sendValidationError(response, "rule_name is required");
            return;
        }
        if (data.notificationType == null || data.notificationType.trim().isEmpty()) {
            sendValidationError(response, "notification_type is required (email, webhook, or both)");
            return;
        }

        // Validate notification type
        if (!isValidNotificationType(data.notificationType)) {
            sendValidationError(response, "notification_type must be one of: email, webhook, both");
            return;
        }

        // Validate email recipients if notification type includes email
        if (("email".equals(data.notificationType) || "both".equals(data.notificationType)) &&
            (data.emailRecipients == null || data.emailRecipients.trim().isEmpty())) {
            sendValidationError(response, "email_recipients is required when notification_type is email or both");
            return;
        }

        // Validate email addresses
        if (data.emailRecipients != null && !data.emailRecipients.trim().isEmpty()) {
            String[] emails = data.emailRecipients.split(",");
            for (String email : emails) {
                if (!isValidEmail(email.trim())) {
                    sendValidationError(response, "Invalid email address: " + email.trim());
                    return;
                }
            }
        }

        // Validate webhook endpoints if notification type includes webhook
        if (("webhook".equals(data.notificationType) || "both".equals(data.notificationType)) &&
            (data.webhookEndpointIds == null || data.webhookEndpointIds.trim().isEmpty())) {
            sendValidationError(response, "webhook_endpoint_ids is required when notification_type is webhook or both");
            return;
        }

        // Insert new alert rule
        String sql =
            "INSERT INTO alert_rules " +
            "(company_id, created_by_user_id, rule_name, description, project_id, flow_name, " +
            "alert_on_failure, alert_on_timeout, failure_threshold, threshold_window_minutes, " +
            "notification_type, email_recipients, webhook_endpoint_ids, cooldown_minutes, " +
            "max_alerts_per_day, is_enabled) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userCompanyId);
            stmt.setInt(2, userId);
            stmt.setString(3, data.ruleName);
            stmt.setString(4, data.description);
            if (data.projectId != null) {
                stmt.setInt(5, data.projectId);
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }
            stmt.setString(6, data.flowName);
            stmt.setBoolean(7, data.alertOnFailure);
            stmt.setBoolean(8, data.alertOnTimeout);
            stmt.setInt(9, data.failureThreshold);
            stmt.setInt(10, data.thresholdWindowMinutes);
            stmt.setString(11, data.notificationType);
            stmt.setString(12, data.emailRecipients);
            stmt.setString(13, data.webhookEndpointIds);
            stmt.setInt(14, data.cooldownMinutes);
            stmt.setInt(15, data.maxAlertsPerDay);
            stmt.setBoolean(16, data.isEnabled);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        String successJson = buildSuccessJson(
                            "\"data\":{\"id\":" + newId + ",\"message\":\"Alert rule created successfully\"}");
                        sendJsonResponse(response, HttpServletResponse.SC_CREATED, successJson);
                    }
                }
            } else {
                sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    ErrorCode.DB001, "Failed to create alert rule");
            }
        }
    }

    /**
     * Updates an existing alert rule
     */
    private void updateAlertRule(HttpServletRequest request, HttpServletResponse response,
                                Connection conn, String alertRuleId)
            throws ServletException, IOException, SQLException {

        Integer userCompanyId = getCompanyId(request);
        boolean isAdminUser = isAdmin(request);

        int id;
        try {
            id = Integer.parseInt(alertRuleId);
        } catch (NumberFormatException e) {
            sendValidationError(response, "Invalid alert rule ID. Must be a valid integer.");
            return;
        }

        // Verify ownership
        if (!verifyAlertRuleOwnership(conn, id, userCompanyId, isAdminUser)) {
            sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN,
                ErrorCode.AUTH005, "You do not have permission to update this alert rule");
            return;
        }

        // Parse JSON from request body
        AlertRuleData data = parseAlertRuleJson(request);
        if (data == null) {
            sendValidationError(response, "Invalid JSON in request body");
            return;
        }

        // Validate email addresses if provided
        if (data.emailRecipients != null && !data.emailRecipients.trim().isEmpty()) {
            String[] emails = data.emailRecipients.split(",");
            for (String email : emails) {
                if (!isValidEmail(email.trim())) {
                    sendValidationError(response, "Invalid email address: " + email.trim());
                    return;
                }
            }
        }

        // Build dynamic UPDATE statement based on provided fields
        StringBuilder sql = new StringBuilder("UPDATE alert_rules SET ");
        boolean hasUpdates = false;

        if (data.ruleName != null) {
            sql.append("rule_name = ?, ");
            hasUpdates = true;
        }
        if (data.description != null) {
            sql.append("description = ?, ");
            hasUpdates = true;
        }
        if (data.projectId != null || data.projectIdProvided) {
            sql.append("project_id = ?, ");
            hasUpdates = true;
        }
        if (data.flowName != null || data.flowNameProvided) {
            sql.append("flow_name = ?, ");
            hasUpdates = true;
        }
        if (data.alertOnFailureProvided) {
            sql.append("alert_on_failure = ?, ");
            hasUpdates = true;
        }
        if (data.alertOnTimeoutProvided) {
            sql.append("alert_on_timeout = ?, ");
            hasUpdates = true;
        }
        if (data.failureThreshold > 0) {
            sql.append("failure_threshold = ?, ");
            hasUpdates = true;
        }
        if (data.thresholdWindowMinutes > 0) {
            sql.append("threshold_window_minutes = ?, ");
            hasUpdates = true;
        }
        if (data.notificationType != null) {
            if (!isValidNotificationType(data.notificationType)) {
                sendValidationError(response, "notification_type must be one of: email, webhook, both");
                return;
            }
            sql.append("notification_type = ?, ");
            hasUpdates = true;
        }
        if (data.emailRecipients != null || data.emailRecipientsProvided) {
            sql.append("email_recipients = ?, ");
            hasUpdates = true;
        }
        if (data.webhookEndpointIds != null || data.webhookEndpointIdsProvided) {
            sql.append("webhook_endpoint_ids = ?, ");
            hasUpdates = true;
        }
        if (data.cooldownMinutes > 0) {
            sql.append("cooldown_minutes = ?, ");
            hasUpdates = true;
        }
        if (data.maxAlertsPerDay > 0) {
            sql.append("max_alerts_per_day = ?, ");
            hasUpdates = true;
        }
        if (data.isEnabledProvided) {
            sql.append("is_enabled = ?, ");
            hasUpdates = true;
        }

        if (!hasUpdates) {
            sendValidationError(response, "No fields provided for update");
            return;
        }

        // Remove trailing comma and space
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE id = ?");

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (data.ruleName != null) {
                stmt.setString(paramIndex++, data.ruleName);
            }
            if (data.description != null) {
                stmt.setString(paramIndex++, data.description);
            }
            if (data.projectId != null || data.projectIdProvided) {
                if (data.projectId != null) {
                    stmt.setInt(paramIndex++, data.projectId);
                } else {
                    stmt.setNull(paramIndex++, java.sql.Types.INTEGER);
                }
            }
            if (data.flowName != null || data.flowNameProvided) {
                stmt.setString(paramIndex++, data.flowName);
            }
            if (data.alertOnFailureProvided) {
                stmt.setBoolean(paramIndex++, data.alertOnFailure);
            }
            if (data.alertOnTimeoutProvided) {
                stmt.setBoolean(paramIndex++, data.alertOnTimeout);
            }
            if (data.failureThreshold > 0) {
                stmt.setInt(paramIndex++, data.failureThreshold);
            }
            if (data.thresholdWindowMinutes > 0) {
                stmt.setInt(paramIndex++, data.thresholdWindowMinutes);
            }
            if (data.notificationType != null) {
                stmt.setString(paramIndex++, data.notificationType);
            }
            if (data.emailRecipients != null || data.emailRecipientsProvided) {
                stmt.setString(paramIndex++, data.emailRecipients);
            }
            if (data.webhookEndpointIds != null || data.webhookEndpointIdsProvided) {
                stmt.setString(paramIndex++, data.webhookEndpointIds);
            }
            if (data.cooldownMinutes > 0) {
                stmt.setInt(paramIndex++, data.cooldownMinutes);
            }
            if (data.maxAlertsPerDay > 0) {
                stmt.setInt(paramIndex++, data.maxAlertsPerDay);
            }
            if (data.isEnabledProvided) {
                stmt.setBoolean(paramIndex++, data.isEnabled);
            }

            stmt.setInt(paramIndex, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                String successJson = buildSuccessJson(
                    "\"data\":{\"message\":\"Alert rule updated successfully\"}");
                sendJsonResponse(response, successJson);
            } else {
                sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND,
                    ErrorCode.VALIDATION001, "Alert rule not found");
            }
        }
    }

    /**
     * Deletes an alert rule
     */
    private void deleteAlertRule(HttpServletRequest request, HttpServletResponse response,
                                Connection conn, String alertRuleId)
            throws ServletException, IOException, SQLException {

        Integer userCompanyId = getCompanyId(request);
        boolean isAdminUser = isAdmin(request);

        int id;
        try {
            id = Integer.parseInt(alertRuleId);
        } catch (NumberFormatException e) {
            sendValidationError(response, "Invalid alert rule ID. Must be a valid integer.");
            return;
        }

        // Verify ownership
        if (!verifyAlertRuleOwnership(conn, id, userCompanyId, isAdminUser)) {
            sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN,
                ErrorCode.AUTH005, "You do not have permission to delete this alert rule");
            return;
        }

        String sql = "DELETE FROM alert_rules WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                String successJson = buildSuccessJson(
                    "\"data\":{\"message\":\"Alert rule deleted successfully\"}");
                sendJsonResponse(response, successJson);
            } else {
                sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND,
                    ErrorCode.VALIDATION001, "Alert rule not found");
            }
        }
    }

    /**
     * Lists all webhook endpoints for the user's company
     */
    private void listWebhooks(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws ServletException, IOException, SQLException {

        Integer userCompanyId = getCompanyId(request);
        boolean isAdminUser = isAdmin(request);

        // Parse optional company_id filter
        Integer filterCompanyId = parseCompanyIdFilter(request, userCompanyId, isAdminUser, response);
        if (filterCompanyId == null && response.isCommitted()) {
            return;
        }

        StringBuilder json = new StringBuilder();
        json.append("[");

        String sql =
            "SELECT " +
            "  id, company_id, endpoint_name, endpoint_url, http_method, " +
            "  auth_type, payload_format, timeout_seconds, retry_count, retry_delay_seconds, " +
            "  is_enabled, last_success_at, last_failure_at, consecutive_failures, " +
            "  created_at, updated_at " +
            "FROM webhook_endpoints " +
            "WHERE company_id = ? " +
            "ORDER BY endpoint_name";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, filterCompanyId);

            try (ResultSet rs = stmt.executeQuery()) {
                boolean first = true;
                while (rs.next()) {
                    if (!first) {
                        json.append(",");
                    }
                    first = false;

                    json.append(buildWebhookJson(rs, false));
                }
            }
        }

        json.append("]");

        String successJson = buildSuccessJson("\"data\":" + json.toString());
        sendJsonResponse(response, successJson);
    }

    /**
     * Gets a specific webhook endpoint by ID
     */
    private void getWebhook(HttpServletRequest request, HttpServletResponse response,
                           Connection conn, String webhookId)
            throws ServletException, IOException, SQLException {

        Integer userCompanyId = getCompanyId(request);
        boolean isAdminUser = isAdmin(request);

        String sql =
            "SELECT * FROM webhook_endpoints " +
            "WHERE id = ? " +
            (isAdminUser ? "" : "AND company_id = ?");

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            try {
                int id = Integer.parseInt(webhookId);
                stmt.setInt(1, id);
                if (!isAdminUser) {
                    stmt.setInt(2, userCompanyId);
                }
            } catch (NumberFormatException e) {
                sendValidationError(response, "Invalid webhook ID. Must be a valid integer.");
                return;
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String webhookJson = buildWebhookJson(rs, true);
                    String successJson = buildSuccessJson("\"data\":" + webhookJson);
                    sendJsonResponse(response, successJson);
                } else {
                    sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND,
                        ErrorCode.VALIDATION001, "Webhook endpoint not found or you do not have permission to view it");
                }
            }
        }
    }

    /**
     * Creates a new webhook endpoint
     */
    private void createWebhook(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws ServletException, IOException, SQLException {

        Integer userCompanyId = getCompanyId(request);
        Integer userId = getUserId(request);

        // Parse JSON from request body
        WebhookData data = parseWebhookJson(request);
        if (data == null) {
            sendValidationError(response, "Invalid JSON in request body");
            return;
        }

        // Validate required fields
        if (data.endpointName == null || data.endpointName.trim().isEmpty()) {
            sendValidationError(response, "endpoint_name is required");
            return;
        }
        if (data.endpointUrl == null || data.endpointUrl.trim().isEmpty()) {
            sendValidationError(response, "endpoint_url is required");
            return;
        }

        // Validate URL
        if (!isValidUrl(data.endpointUrl)) {
            sendValidationError(response, "Invalid endpoint_url. Must be a valid HTTP or HTTPS URL");
            return;
        }

        // Validate HTTP method
        if (data.httpMethod != null && !isValidHttpMethod(data.httpMethod)) {
            sendValidationError(response, "http_method must be one of: POST, PUT, PATCH");
            return;
        }

        // Validate auth type
        if (data.authType != null && !isValidAuthType(data.authType)) {
            sendValidationError(response, "auth_type must be one of: none, basic, bearer, custom_header");
            return;
        }

        // Validate payload format
        if (data.payloadFormat != null && !isValidPayloadFormat(data.payloadFormat)) {
            sendValidationError(response, "payload_format must be one of: json, form, xml");
            return;
        }

        // Insert new webhook endpoint
        String sql =
            "INSERT INTO webhook_endpoints " +
            "(company_id, created_by_user_id, endpoint_name, endpoint_url, http_method, " +
            "auth_type, auth_username, auth_password, auth_token, custom_headers, " +
            "payload_format, payload_template, timeout_seconds, retry_count, retry_delay_seconds, is_enabled) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userCompanyId);
            stmt.setInt(2, userId);
            stmt.setString(3, data.endpointName);
            stmt.setString(4, data.endpointUrl);
            stmt.setString(5, data.httpMethod != null ? data.httpMethod : "POST");
            stmt.setString(6, data.authType != null ? data.authType : "none");
            stmt.setString(7, data.authUsername);
            stmt.setString(8, data.authPassword);
            stmt.setString(9, data.authToken);
            stmt.setString(10, data.customHeaders);
            stmt.setString(11, data.payloadFormat != null ? data.payloadFormat : "json");
            stmt.setString(12, data.payloadTemplate);
            stmt.setInt(13, data.timeoutSeconds > 0 ? data.timeoutSeconds : 30);
            stmt.setInt(14, data.retryCount >= 0 ? data.retryCount : 3);
            stmt.setInt(15, data.retryDelaySeconds > 0 ? data.retryDelaySeconds : 60);
            stmt.setBoolean(16, data.isEnabled);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        String successJson = buildSuccessJson(
                            "\"data\":{\"id\":" + newId + ",\"message\":\"Webhook endpoint created successfully\"}");
                        sendJsonResponse(response, HttpServletResponse.SC_CREATED, successJson);
                    }
                }
            } else {
                sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    ErrorCode.DB001, "Failed to create webhook endpoint");
            }
        }
    }

    /**
     * Updates an existing webhook endpoint
     */
    private void updateWebhook(HttpServletRequest request, HttpServletResponse response,
                              Connection conn, String webhookId)
            throws ServletException, IOException, SQLException {

        Integer userCompanyId = getCompanyId(request);
        boolean isAdminUser = isAdmin(request);

        int id;
        try {
            id = Integer.parseInt(webhookId);
        } catch (NumberFormatException e) {
            sendValidationError(response, "Invalid webhook ID. Must be a valid integer.");
            return;
        }

        // Verify ownership
        if (!verifyWebhookOwnership(conn, id, userCompanyId, isAdminUser)) {
            sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN,
                ErrorCode.AUTH005, "You do not have permission to update this webhook endpoint");
            return;
        }

        // Parse JSON from request body
        WebhookData data = parseWebhookJson(request);
        if (data == null) {
            sendValidationError(response, "Invalid JSON in request body");
            return;
        }

        // Validate URL if provided
        if (data.endpointUrl != null && !isValidUrl(data.endpointUrl)) {
            sendValidationError(response, "Invalid endpoint_url. Must be a valid HTTP or HTTPS URL");
            return;
        }

        // Build dynamic UPDATE statement
        StringBuilder sql = new StringBuilder("UPDATE webhook_endpoints SET ");
        boolean hasUpdates = false;

        if (data.endpointName != null) {
            sql.append("endpoint_name = ?, ");
            hasUpdates = true;
        }
        if (data.endpointUrl != null) {
            sql.append("endpoint_url = ?, ");
            hasUpdates = true;
        }
        if (data.httpMethod != null) {
            if (!isValidHttpMethod(data.httpMethod)) {
                sendValidationError(response, "http_method must be one of: POST, PUT, PATCH");
                return;
            }
            sql.append("http_method = ?, ");
            hasUpdates = true;
        }
        if (data.authType != null) {
            if (!isValidAuthType(data.authType)) {
                sendValidationError(response, "auth_type must be one of: none, basic, bearer, custom_header");
                return;
            }
            sql.append("auth_type = ?, ");
            hasUpdates = true;
        }
        if (data.authUsername != null || data.authUsernameProvided) {
            sql.append("auth_username = ?, ");
            hasUpdates = true;
        }
        if (data.authPassword != null || data.authPasswordProvided) {
            sql.append("auth_password = ?, ");
            hasUpdates = true;
        }
        if (data.authToken != null || data.authTokenProvided) {
            sql.append("auth_token = ?, ");
            hasUpdates = true;
        }
        if (data.customHeaders != null || data.customHeadersProvided) {
            sql.append("custom_headers = ?, ");
            hasUpdates = true;
        }
        if (data.payloadFormat != null) {
            if (!isValidPayloadFormat(data.payloadFormat)) {
                sendValidationError(response, "payload_format must be one of: json, form, xml");
                return;
            }
            sql.append("payload_format = ?, ");
            hasUpdates = true;
        }
        if (data.payloadTemplate != null || data.payloadTemplateProvided) {
            sql.append("payload_template = ?, ");
            hasUpdates = true;
        }
        if (data.timeoutSeconds > 0) {
            sql.append("timeout_seconds = ?, ");
            hasUpdates = true;
        }
        if (data.retryCount >= 0 && data.retryCountProvided) {
            sql.append("retry_count = ?, ");
            hasUpdates = true;
        }
        if (data.retryDelaySeconds > 0) {
            sql.append("retry_delay_seconds = ?, ");
            hasUpdates = true;
        }
        if (data.isEnabledProvided) {
            sql.append("is_enabled = ?, ");
            hasUpdates = true;
        }

        if (!hasUpdates) {
            sendValidationError(response, "No fields provided for update");
            return;
        }

        // Remove trailing comma and space
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE id = ?");

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (data.endpointName != null) {
                stmt.setString(paramIndex++, data.endpointName);
            }
            if (data.endpointUrl != null) {
                stmt.setString(paramIndex++, data.endpointUrl);
            }
            if (data.httpMethod != null) {
                stmt.setString(paramIndex++, data.httpMethod);
            }
            if (data.authType != null) {
                stmt.setString(paramIndex++, data.authType);
            }
            if (data.authUsername != null || data.authUsernameProvided) {
                stmt.setString(paramIndex++, data.authUsername);
            }
            if (data.authPassword != null || data.authPasswordProvided) {
                stmt.setString(paramIndex++, data.authPassword);
            }
            if (data.authToken != null || data.authTokenProvided) {
                stmt.setString(paramIndex++, data.authToken);
            }
            if (data.customHeaders != null || data.customHeadersProvided) {
                stmt.setString(paramIndex++, data.customHeaders);
            }
            if (data.payloadFormat != null) {
                stmt.setString(paramIndex++, data.payloadFormat);
            }
            if (data.payloadTemplate != null || data.payloadTemplateProvided) {
                stmt.setString(paramIndex++, data.payloadTemplate);
            }
            if (data.timeoutSeconds > 0) {
                stmt.setInt(paramIndex++, data.timeoutSeconds);
            }
            if (data.retryCount >= 0 && data.retryCountProvided) {
                stmt.setInt(paramIndex++, data.retryCount);
            }
            if (data.retryDelaySeconds > 0) {
                stmt.setInt(paramIndex++, data.retryDelaySeconds);
            }
            if (data.isEnabledProvided) {
                stmt.setBoolean(paramIndex++, data.isEnabled);
            }

            stmt.setInt(paramIndex, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                String successJson = buildSuccessJson(
                    "\"data\":{\"message\":\"Webhook endpoint updated successfully\"}");
                sendJsonResponse(response, successJson);
            } else {
                sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND,
                    ErrorCode.VALIDATION001, "Webhook endpoint not found");
            }
        }
    }

    /**
     * Deletes a webhook endpoint
     */
    private void deleteWebhook(HttpServletRequest request, HttpServletResponse response,
                              Connection conn, String webhookId)
            throws ServletException, IOException, SQLException {

        Integer userCompanyId = getCompanyId(request);
        boolean isAdminUser = isAdmin(request);

        int id;
        try {
            id = Integer.parseInt(webhookId);
        } catch (NumberFormatException e) {
            sendValidationError(response, "Invalid webhook ID. Must be a valid integer.");
            return;
        }

        // Verify ownership
        if (!verifyWebhookOwnership(conn, id, userCompanyId, isAdminUser)) {
            sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN,
                ErrorCode.AUTH005, "You do not have permission to delete this webhook endpoint");
            return;
        }

        String sql = "DELETE FROM webhook_endpoints WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                String successJson = buildSuccessJson(
                    "\"data\":{\"message\":\"Webhook endpoint deleted successfully\"}");
                sendJsonResponse(response, successJson);
            } else {
                sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND,
                    ErrorCode.VALIDATION001, "Webhook endpoint not found");
            }
        }
    }

    /**
     * Handles alert history requests
     */
    private void handleAlertHistory(HttpServletRequest request, HttpServletResponse response, Connection conn)
            throws ServletException, IOException, SQLException {

        Integer userCompanyId = getCompanyId(request);
        boolean isAdminUser = isAdmin(request);

        // Parse pagination parameters
        int page = getIntParameter(request, "page", 1);
        if (page < 1) {
            page = 1;
        }

        int pageSize = getIntParameter(request, "page_size", 50);
        if (pageSize < 1) {
            pageSize = 50;
        } else if (pageSize > 100) {
            pageSize = 100;
        }

        int offset = (page - 1) * pageSize;

        // Parse optional alert_rule_id filter
        Integer alertRuleId = null;
        String alertRuleIdParam = getStringParameter(request, "alert_rule_id", null);
        if (alertRuleIdParam != null) {
            try {
                alertRuleId = Integer.parseInt(alertRuleIdParam);
            } catch (NumberFormatException e) {
                sendValidationError(response, "Invalid alert_rule_id parameter. Must be a valid integer.");
                return;
            }
        }

        StringBuilder json = new StringBuilder();
        json.append("[");

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("  ah.id, ah.alert_rule_id, ah.execution_id, ah.alert_type, ah.recipient, ");
        sql.append("  ah.subject, ah.message, ah.status, ah.status_message, ah.sent_at, ");
        sql.append("  ah.delivered_at, ah.retry_count, ah.created_at, ");
        sql.append("  ar.rule_name, te.execution_id AS exec_id, te.flow_name ");
        sql.append("FROM alert_history ah ");
        sql.append("JOIN alert_rules ar ON ah.alert_rule_id = ar.id ");
        sql.append("LEFT JOIN transaction_executions te ON ah.execution_id = te.id ");
        sql.append("WHERE ar.company_id = ? ");

        if (alertRuleId != null) {
            sql.append("AND ah.alert_rule_id = ? ");
        }

        sql.append("ORDER BY ah.created_at DESC ");
        sql.append("LIMIT ? OFFSET ?");

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            stmt.setInt(paramIndex++, userCompanyId);

            if (alertRuleId != null) {
                stmt.setInt(paramIndex++, alertRuleId);
            }

            stmt.setInt(paramIndex++, pageSize);
            stmt.setInt(paramIndex, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                boolean first = true;
                while (rs.next()) {
                    if (!first) {
                        json.append(",");
                    }
                    first = false;

                    json.append("{");
                    json.append("\"id\":").append(rs.getLong("id")).append(",");
                    json.append("\"alert_rule_id\":").append(rs.getInt("alert_rule_id")).append(",");
                    json.append("\"rule_name\":\"").append(escapeJson(rs.getString("rule_name"))).append("\",");

                    Long executionId = rs.getLong("execution_id");
                    if (!rs.wasNull()) {
                        json.append("\"execution_id\":").append(executionId).append(",");
                    } else {
                        json.append("\"execution_id\":null,");
                    }

                    String execId = rs.getString("exec_id");
                    if (execId != null) {
                        json.append("\"execution_identifier\":\"").append(escapeJson(execId)).append("\",");
                    } else {
                        json.append("\"execution_identifier\":null,");
                    }

                    String flowName = rs.getString("flow_name");
                    if (flowName != null) {
                        json.append("\"flow_name\":\"").append(escapeJson(flowName)).append("\",");
                    } else {
                        json.append("\"flow_name\":null,");
                    }

                    json.append("\"alert_type\":\"").append(escapeJson(rs.getString("alert_type"))).append("\",");
                    json.append("\"recipient\":\"").append(escapeJson(rs.getString("recipient"))).append("\",");

                    String subject = rs.getString("subject");
                    if (subject != null) {
                        json.append("\"subject\":\"").append(escapeJson(subject)).append("\",");
                    } else {
                        json.append("\"subject\":null,");
                    }

                    String message = rs.getString("message");
                    if (message != null) {
                        json.append("\"message\":\"").append(escapeJson(message)).append("\",");
                    } else {
                        json.append("\"message\":null,");
                    }

                    json.append("\"status\":\"").append(escapeJson(rs.getString("status"))).append("\",");

                    String statusMessage = rs.getString("status_message");
                    if (statusMessage != null) {
                        json.append("\"status_message\":\"").append(escapeJson(statusMessage)).append("\",");
                    } else {
                        json.append("\"status_message\":null,");
                    }

                    Timestamp sentAt = rs.getTimestamp("sent_at");
                    if (sentAt != null) {
                        json.append("\"sent_at\":\"").append(sentAt).append("\",");
                    } else {
                        json.append("\"sent_at\":null,");
                    }

                    Timestamp deliveredAt = rs.getTimestamp("delivered_at");
                    if (deliveredAt != null) {
                        json.append("\"delivered_at\":\"").append(deliveredAt).append("\",");
                    } else {
                        json.append("\"delivered_at\":null,");
                    }

                    json.append("\"retry_count\":").append(rs.getInt("retry_count")).append(",");
                    json.append("\"created_at\":\"").append(rs.getTimestamp("created_at")).append("\"");

                    json.append("}");
                }
            }
        }

        json.append("]");

        String successJson = buildSuccessJson("\"data\":" + json.toString());
        sendJsonResponse(response, successJson);
    }

    /**
     * Builds JSON for an alert rule from ResultSet
     */
    private String buildAlertRuleJson(ResultSet rs) throws SQLException {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"id\":").append(rs.getInt("id")).append(",");
        json.append("\"company_id\":").append(rs.getInt("company_id")).append(",");
        json.append("\"rule_name\":\"").append(escapeJson(rs.getString("rule_name"))).append("\",");

        String description = rs.getString("description");
        if (description != null) {
            json.append("\"description\":\"").append(escapeJson(description)).append("\",");
        } else {
            json.append("\"description\":null,");
        }

        Integer projectId = rs.getInt("project_id");
        if (!rs.wasNull()) {
            json.append("\"project_id\":").append(projectId).append(",");
        } else {
            json.append("\"project_id\":null,");
        }

        String projectName = rs.getString("project_name");
        if (projectName != null) {
            json.append("\"project_name\":\"").append(escapeJson(projectName)).append("\",");
        } else {
            json.append("\"project_name\":null,");
        }

        String flowName = rs.getString("flow_name");
        if (flowName != null) {
            json.append("\"flow_name\":\"").append(escapeJson(flowName)).append("\",");
        } else {
            json.append("\"flow_name\":null,");
        }

        json.append("\"alert_on_failure\":").append(rs.getBoolean("alert_on_failure")).append(",");
        json.append("\"alert_on_timeout\":").append(rs.getBoolean("alert_on_timeout")).append(",");
        json.append("\"failure_threshold\":").append(rs.getInt("failure_threshold")).append(",");
        json.append("\"threshold_window_minutes\":").append(rs.getInt("threshold_window_minutes")).append(",");
        json.append("\"notification_type\":\"").append(escapeJson(rs.getString("notification_type"))).append("\",");

        String emailRecipients = rs.getString("email_recipients");
        if (emailRecipients != null) {
            json.append("\"email_recipients\":\"").append(escapeJson(emailRecipients)).append("\",");
        } else {
            json.append("\"email_recipients\":null,");
        }

        String webhookEndpointIds = rs.getString("webhook_endpoint_ids");
        if (webhookEndpointIds != null) {
            json.append("\"webhook_endpoint_ids\":\"").append(escapeJson(webhookEndpointIds)).append("\",");
        } else {
            json.append("\"webhook_endpoint_ids\":null,");
        }

        json.append("\"cooldown_minutes\":").append(rs.getInt("cooldown_minutes")).append(",");
        json.append("\"max_alerts_per_day\":").append(rs.getInt("max_alerts_per_day")).append(",");
        json.append("\"is_enabled\":").append(rs.getBoolean("is_enabled")).append(",");

        Timestamp lastTriggeredAt = rs.getTimestamp("last_triggered_at");
        if (lastTriggeredAt != null) {
            json.append("\"last_triggered_at\":\"").append(lastTriggeredAt).append("\",");
        } else {
            json.append("\"last_triggered_at\":null,");
        }

        json.append("\"alerts_sent_today\":").append(rs.getInt("alerts_sent_today")).append(",");
        json.append("\"created_at\":\"").append(rs.getTimestamp("created_at")).append("\",");
        json.append("\"updated_at\":\"").append(rs.getTimestamp("updated_at")).append("\"");

        json.append("}");
        return json.toString();
    }

    /**
     * Builds JSON for a webhook endpoint from ResultSet
     */
    private String buildWebhookJson(ResultSet rs, boolean includeSecrets) throws SQLException {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"id\":").append(rs.getInt("id")).append(",");
        json.append("\"company_id\":").append(rs.getInt("company_id")).append(",");
        json.append("\"endpoint_name\":\"").append(escapeJson(rs.getString("endpoint_name"))).append("\",");
        json.append("\"endpoint_url\":\"").append(escapeJson(rs.getString("endpoint_url"))).append("\",");
        json.append("\"http_method\":\"").append(escapeJson(rs.getString("http_method"))).append("\",");
        json.append("\"auth_type\":\"").append(escapeJson(rs.getString("auth_type"))).append("\",");

        if (includeSecrets) {
            String authUsername = rs.getString("auth_username");
            if (authUsername != null) {
                json.append("\"auth_username\":\"").append(escapeJson(authUsername)).append("\",");
            } else {
                json.append("\"auth_username\":null,");
            }

            String authPassword = rs.getString("auth_password");
            if (authPassword != null) {
                json.append("\"auth_password\":\"").append(escapeJson(authPassword)).append("\",");
            } else {
                json.append("\"auth_password\":null,");
            }

            String authToken = rs.getString("auth_token");
            if (authToken != null) {
                json.append("\"auth_token\":\"").append(escapeJson(authToken)).append("\",");
            } else {
                json.append("\"auth_token\":null,");
            }

            String customHeaders = rs.getString("custom_headers");
            if (customHeaders != null) {
                json.append("\"custom_headers\":\"").append(escapeJson(customHeaders)).append("\",");
            } else {
                json.append("\"custom_headers\":null,");
            }

            String payloadTemplate = rs.getString("payload_template");
            if (payloadTemplate != null) {
                json.append("\"payload_template\":\"").append(escapeJson(payloadTemplate)).append("\",");
            } else {
                json.append("\"payload_template\":null,");
            }
        }

        json.append("\"payload_format\":\"").append(escapeJson(rs.getString("payload_format"))).append("\",");
        json.append("\"timeout_seconds\":").append(rs.getInt("timeout_seconds")).append(",");
        json.append("\"retry_count\":").append(rs.getInt("retry_count")).append(",");
        json.append("\"retry_delay_seconds\":").append(rs.getInt("retry_delay_seconds")).append(",");
        json.append("\"is_enabled\":").append(rs.getBoolean("is_enabled")).append(",");

        Timestamp lastSuccessAt = rs.getTimestamp("last_success_at");
        if (lastSuccessAt != null) {
            json.append("\"last_success_at\":\"").append(lastSuccessAt).append("\",");
        } else {
            json.append("\"last_success_at\":null,");
        }

        Timestamp lastFailureAt = rs.getTimestamp("last_failure_at");
        if (lastFailureAt != null) {
            json.append("\"last_failure_at\":\"").append(lastFailureAt).append("\",");
        } else {
            json.append("\"last_failure_at\":null,");
        }

        json.append("\"consecutive_failures\":").append(rs.getInt("consecutive_failures")).append(",");
        json.append("\"created_at\":\"").append(rs.getTimestamp("created_at")).append("\",");
        json.append("\"updated_at\":\"").append(rs.getTimestamp("updated_at")).append("\"");

        json.append("}");
        return json.toString();
    }

    /**
     * Parses alert rule JSON from request body
     */
    private AlertRuleData parseAlertRuleJson(HttpServletRequest request) throws IOException {
        StringBuilder jsonBuffer = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        String json = jsonBuffer.toString();
        if (json.isEmpty()) {
            return null;
        }

        return parseAlertRuleFromJson(json);
    }

    /**
     * Simple JSON parser for alert rule data
     */
    private AlertRuleData parseAlertRuleFromJson(String json) {
        AlertRuleData data = new AlertRuleData();

        data.ruleName = extractJsonString(json, "rule_name");
        data.description = extractJsonString(json, "description");
        data.projectId = extractJsonInteger(json, "project_id");
        data.projectIdProvided = json.contains("\"project_id\"");
        data.flowName = extractJsonString(json, "flow_name");
        data.flowNameProvided = json.contains("\"flow_name\"");
        data.alertOnFailure = extractJsonBoolean(json, "alert_on_failure", true);
        data.alertOnFailureProvided = json.contains("\"alert_on_failure\"");
        data.alertOnTimeout = extractJsonBoolean(json, "alert_on_timeout", true);
        data.alertOnTimeoutProvided = json.contains("\"alert_on_timeout\"");
        data.failureThreshold = extractJsonInteger(json, "failure_threshold", 1);
        data.thresholdWindowMinutes = extractJsonInteger(json, "threshold_window_minutes", 60);
        data.notificationType = extractJsonString(json, "notification_type");
        data.emailRecipients = extractJsonString(json, "email_recipients");
        data.emailRecipientsProvided = json.contains("\"email_recipients\"");
        data.webhookEndpointIds = extractJsonString(json, "webhook_endpoint_ids");
        data.webhookEndpointIdsProvided = json.contains("\"webhook_endpoint_ids\"");
        data.cooldownMinutes = extractJsonInteger(json, "cooldown_minutes", 30);
        data.maxAlertsPerDay = extractJsonInteger(json, "max_alerts_per_day", 100);
        data.isEnabled = extractJsonBoolean(json, "is_enabled", true);
        data.isEnabledProvided = json.contains("\"is_enabled\"");

        return data;
    }

    /**
     * Parses webhook JSON from request body
     */
    private WebhookData parseWebhookJson(HttpServletRequest request) throws IOException {
        StringBuilder jsonBuffer = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        String json = jsonBuffer.toString();
        if (json.isEmpty()) {
            return null;
        }

        return parseWebhookFromJson(json);
    }

    /**
     * Simple JSON parser for webhook data
     */
    private WebhookData parseWebhookFromJson(String json) {
        WebhookData data = new WebhookData();

        data.endpointName = extractJsonString(json, "endpoint_name");
        data.endpointUrl = extractJsonString(json, "endpoint_url");
        data.httpMethod = extractJsonString(json, "http_method");
        data.authType = extractJsonString(json, "auth_type");
        data.authUsername = extractJsonString(json, "auth_username");
        data.authUsernameProvided = json.contains("\"auth_username\"");
        data.authPassword = extractJsonString(json, "auth_password");
        data.authPasswordProvided = json.contains("\"auth_password\"");
        data.authToken = extractJsonString(json, "auth_token");
        data.authTokenProvided = json.contains("\"auth_token\"");
        data.customHeaders = extractJsonString(json, "custom_headers");
        data.customHeadersProvided = json.contains("\"custom_headers\"");
        data.payloadFormat = extractJsonString(json, "payload_format");
        data.payloadTemplate = extractJsonString(json, "payload_template");
        data.payloadTemplateProvided = json.contains("\"payload_template\"");
        data.timeoutSeconds = extractJsonInteger(json, "timeout_seconds", 0);
        data.retryCount = extractJsonInteger(json, "retry_count", -1);
        data.retryCountProvided = json.contains("\"retry_count\"");
        data.retryDelaySeconds = extractJsonInteger(json, "retry_delay_seconds", 0);
        data.isEnabled = extractJsonBoolean(json, "is_enabled", true);
        data.isEnabledProvided = json.contains("\"is_enabled\"");

        return data;
    }

    /**
     * Extracts a string value from simple JSON
     */
    private String extractJsonString(String json, String key) {
        String pattern = "\"" + key + "\"\\s*:\\s*\"([^\"]*)\"";
        java.util.regex.Matcher matcher = Pattern.compile(pattern).matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }

        String nullPattern = "\"" + key + "\"\\s*:\\s*null";
        if (Pattern.compile(nullPattern).matcher(json).find()) {
            return null;
        }

        return null;
    }

    /**
     * Extracts an integer value from simple JSON
     */
    private Integer extractJsonInteger(String json, String key) {
        return extractJsonInteger(json, key, null);
    }

    /**
     * Extracts an integer value from simple JSON with default
     */
    private int extractJsonInteger(String json, String key, int defaultValue) {
        Integer value = extractJsonInteger(json, key);
        return value != null ? value : defaultValue;
    }

    /**
     * Extracts an integer value from simple JSON
     */
    private Integer extractJsonInteger(String json, String key, Integer defaultValue) {
        String pattern = "\"" + key + "\"\\s*:\\s*(\\d+)";
        java.util.regex.Matcher matcher = Pattern.compile(pattern).matcher(json);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Extracts a boolean value from simple JSON
     */
    private boolean extractJsonBoolean(String json, String key, boolean defaultValue) {
        String pattern = "\"" + key + "\"\\s*:\\s*(true|false)";
        java.util.regex.Matcher matcher = Pattern.compile(pattern).matcher(json);
        if (matcher.find()) {
            return Boolean.parseBoolean(matcher.group(1));
        }
        return defaultValue;
    }

    /**
     * Parses and validates company_id filter with security checks
     */
    private Integer parseCompanyIdFilter(HttpServletRequest request, Integer userCompanyId,
                                         boolean isAdmin, HttpServletResponse response)
            throws IOException {

        String companyIdParam = getStringParameter(request, "company_id", null);
        if (companyIdParam != null && !companyIdParam.isEmpty()) {
            try {
                Integer filterCompanyId = Integer.parseInt(companyIdParam);

                if (!isAdmin && !filterCompanyId.equals(userCompanyId)) {
                    sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN,
                        ErrorCode.AUTH005, "You are not authorized to view data for other companies");
                    return null;
                }

                return filterCompanyId;
            } catch (NumberFormatException e) {
                sendValidationError(response, "Invalid company_id parameter. Must be a valid integer.");
                return null;
            }
        } else {
            return userCompanyId;
        }
    }

    /**
     * Verifies that the user has permission to access the alert rule
     */
    private boolean verifyAlertRuleOwnership(Connection conn, int alertRuleId, Integer userCompanyId, boolean isAdmin)
            throws SQLException {

        if (isAdmin) {
            return true;
        }

        String sql = "SELECT company_id FROM alert_rules WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, alertRuleId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int companyId = rs.getInt("company_id");
                    return companyId == userCompanyId;
                }
            }
        }

        return false;
    }

    /**
     * Verifies that the user has permission to access the webhook endpoint
     */
    private boolean verifyWebhookOwnership(Connection conn, int webhookId, Integer userCompanyId, boolean isAdmin)
            throws SQLException {

        if (isAdmin) {
            return true;
        }

        String sql = "SELECT company_id FROM webhook_endpoints WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, webhookId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int companyId = rs.getInt("company_id");
                    return companyId == userCompanyId;
                }
            }
        }

        return false;
    }

    /**
     * Validates email address format
     */
    private boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validates URL format
     */
    private boolean isValidUrl(String url) {
        return url != null && URL_PATTERN.matcher(url).matches();
    }

    /**
     * Validates notification type
     */
    private boolean isValidNotificationType(String type) {
        return "email".equals(type) || "webhook".equals(type) || "both".equals(type);
    }

    /**
     * Validates HTTP method
     */
    private boolean isValidHttpMethod(String method) {
        return "POST".equalsIgnoreCase(method) ||
               "PUT".equalsIgnoreCase(method) ||
               "PATCH".equalsIgnoreCase(method);
    }

    /**
     * Validates auth type
     */
    private boolean isValidAuthType(String type) {
        return "none".equals(type) || "basic".equals(type) ||
               "bearer".equals(type) || "custom_header".equals(type);
    }

    /**
     * Validates payload format
     */
    private boolean isValidPayloadFormat(String format) {
        return "json".equals(format) || "form".equals(format) || "xml".equals(format);
    }

    /**
     * Data class for alert rule information
     */
    private static class AlertRuleData {
        String ruleName;
        String description;
        Integer projectId;
        boolean projectIdProvided;
        String flowName;
        boolean flowNameProvided;
        boolean alertOnFailure = true;
        boolean alertOnFailureProvided;
        boolean alertOnTimeout = true;
        boolean alertOnTimeoutProvided;
        int failureThreshold = 1;
        int thresholdWindowMinutes = 60;
        String notificationType;
        String emailRecipients;
        boolean emailRecipientsProvided;
        String webhookEndpointIds;
        boolean webhookEndpointIdsProvided;
        int cooldownMinutes = 30;
        int maxAlertsPerDay = 100;
        boolean isEnabled = true;
        boolean isEnabledProvided;
    }

    /**
     * Data class for webhook endpoint information
     */
    private static class WebhookData {
        String endpointName;
        String endpointUrl;
        String httpMethod;
        String authType;
        String authUsername;
        boolean authUsernameProvided;
        String authPassword;
        boolean authPasswordProvided;
        String authToken;
        boolean authTokenProvided;
        String customHeaders;
        boolean customHeadersProvided;
        String payloadFormat;
        String payloadTemplate;
        boolean payloadTemplateProvided;
        int timeoutSeconds;
        int retryCount = -1;
        boolean retryCountProvided;
        int retryDelaySeconds;
        boolean isEnabled = true;
        boolean isEnabledProvided;
    }
}
