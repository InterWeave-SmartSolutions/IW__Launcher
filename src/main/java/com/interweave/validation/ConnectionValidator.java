package com.interweave.validation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ConnectionValidator - Validates connection configurations for completeness and correctness.
 *
 * This validator checks connection configurations used in integration flows for:
 * - Required parameter presence
 * - URL/endpoint format validity
 * - Authentication configuration completeness
 * - Orphaned connection references
 *
 * Connections in InterWeave define how to connect to external systems (SOAP, REST, databases, etc.)
 * and require various parameters depending on the connection type.
 *
 * Features:
 * - Validates connection parameter completeness
 * - Checks URL format and protocol validity
 * - Validates authentication credentials configuration
 * - Detects orphaned or unused connections
 * - Provides helpful suggestions for fixing issues
 *
 * Example usage:
 * <pre>
 * ConnectionValidator validator = new ConnectionValidator();
 *
 * // Register a connection
 * Map&lt;String, String&gt; connectionConfig = new HashMap&lt;&gt;();
 * connectionConfig.put("name", "SalesforceAPI");
 * connectionConfig.put("type", "REST");
 * connectionConfig.put("url", "https://api.salesforce.com");
 * connectionConfig.put("authType", "OAuth");
 * validator.registerConnection("SalesforceAPI", connectionConfig);
 *
 * // Validate connection
 * ValidationResult result = validator.validateConnection("SalesforceAPI");
 * if (!result.isValid()) {
 *     System.out.println("Connection validation failed: " + result.toDisplayString());
 * }
 * </pre>
 *
 * @author InterWeave Validation Framework
 * @version 1.0
 */
public class ConnectionValidator {

    private static final String VALIDATION_CATEGORY = "Connection";

    // Connection types supported
    private static final String[] SUPPORTED_CONNECTION_TYPES = {
        "REST", "SOAP", "HTTP", "HTTPS", "DATABASE", "FTP", "SFTP", "FILE"
    };

    // Authentication types supported
    private static final String[] SUPPORTED_AUTH_TYPES = {
        "NONE", "BASIC", "OAUTH", "OAUTH2", "API_KEY", "TOKEN", "CERTIFICATE"
    };

    // Required parameters for each connection type
    private static final Map<String, String[]> TYPE_REQUIRED_PARAMS = new HashMap<>();
    static {
        TYPE_REQUIRED_PARAMS.put("REST", new String[]{"url", "authType"});
        TYPE_REQUIRED_PARAMS.put("SOAP", new String[]{"url", "wsdlUrl", "authType"});
        TYPE_REQUIRED_PARAMS.put("HTTP", new String[]{"url"});
        TYPE_REQUIRED_PARAMS.put("HTTPS", new String[]{"url", "authType"});
        TYPE_REQUIRED_PARAMS.put("DATABASE", new String[]{"host", "port", "database", "username"});
        TYPE_REQUIRED_PARAMS.put("FTP", new String[]{"host", "port", "username"});
        TYPE_REQUIRED_PARAMS.put("SFTP", new String[]{"host", "port", "username", "authType"});
        TYPE_REQUIRED_PARAMS.put("FILE", new String[]{"path"});
    }

    // Required parameters for each authentication type
    private static final Map<String, String[]> AUTH_REQUIRED_PARAMS = new HashMap<>();
    static {
        AUTH_REQUIRED_PARAMS.put("BASIC", new String[]{"username", "password"});
        AUTH_REQUIRED_PARAMS.put("OAUTH", new String[]{"clientId", "clientSecret", "tokenUrl"});
        AUTH_REQUIRED_PARAMS.put("OAUTH2", new String[]{"clientId", "clientSecret", "tokenUrl"});
        AUTH_REQUIRED_PARAMS.put("API_KEY", new String[]{"apiKey"});
        AUTH_REQUIRED_PARAMS.put("TOKEN", new String[]{"token"});
        AUTH_REQUIRED_PARAMS.put("CERTIFICATE", new String[]{"certificatePath"});
    }

    // Registered connections: name -> configuration map
    private final Map<String, Map<String, String>> connections;

    // Track which connections are referenced in flows
    private final Set<String> referencedConnections;

    /**
     * Creates a new ConnectionValidator with no predefined connections
     */
    public ConnectionValidator() {
        this.connections = new HashMap<>();
        this.referencedConnections = new HashSet<>();
    }

    /**
     * Registers a connection configuration for validation
     *
     * @param connectionName The unique name of the connection
     * @param configuration Map of configuration parameters (type, url, authType, etc.)
     */
    public void registerConnection(String connectionName, Map<String, String> configuration) {
        if (connectionName != null && !connectionName.trim().isEmpty() && configuration != null) {
            connections.put(connectionName.trim(), new HashMap<>(configuration));
        }
    }

    /**
     * Backwards-compatible overload used by older tooling/tests.
     *
     * @param connectionName The unique name of the connection
     * @param type Connection type (e.g., REST, SOAP)
     * @param configuration Map of configuration parameters
     */
    public void registerConnection(String connectionName, String type, Map<String, String> configuration) {
        if (connectionName == null || connectionName.trim().isEmpty()) {
            return;
        }

        Map<String, String> merged = new HashMap<>();
        if (configuration != null) {
            merged.putAll(configuration);
        }
        if (type != null && !type.trim().isEmpty()) {
            merged.put("type", type);
        }

        // Normalize legacy key names used by some call sites.
        if (!merged.containsKey("authType") && merged.containsKey("authenticationType")) {
            merged.put("authType", merged.get("authenticationType"));
        }

        registerConnection(connectionName, merged);
    }

    /**
     * Marks a connection as referenced (used in a flow)
     *
     * @param connectionName The name of the referenced connection
     */
    public void markConnectionAsReferenced(String connectionName) {
        if (connectionName != null && !connectionName.trim().isEmpty()) {
            referencedConnections.add(connectionName.trim());
        }
    }

    /**
     * Validates a specific connection by name
     *
     * @param connectionName The name of the connection to validate
     * @return ValidationResult containing any issues found
     */
    public ValidationResult validateConnection(String connectionName) {
        return validateConnection(connectionName, null);
    }

    /**
     * Validates a specific connection by name with file context
     *
     * @param connectionName The name of the connection to validate
     * @param filePath The file path where this connection is defined (for error reporting)
     * @return ValidationResult containing any issues found
     */
    public ValidationResult validateConnection(String connectionName, String filePath) {
        ValidationResult.Builder resultBuilder = ValidationResult.builder()
            .validationType("Connection Configuration Validation");

        List<ValidationIssue> issues = new ArrayList<>();

        // Check if connection name is valid
        if (connectionName == null || connectionName.trim().isEmpty()) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Connection name is empty or null")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Provide a valid connection name")
                .build());
            resultBuilder.addIssues(issues);
            return resultBuilder.build();
        }

        String trimmedName = connectionName.trim();

        // Check if connection is registered
        if (!connections.containsKey(trimmedName)) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Connection '" + trimmedName + "' is not defined")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Define the connection configuration or check for typos in the connection name")
                .build());
            resultBuilder.addIssues(issues);
            return resultBuilder.build();
        }

        Map<String, String> config = connections.get(trimmedName);

        // Validate connection configuration
        issues.addAll(validateConnectionType(trimmedName, config, filePath));
        issues.addAll(validateRequiredParameters(trimmedName, config, filePath));
        issues.addAll(validateEndpointUrl(trimmedName, config, filePath));
        issues.addAll(validateAuthentication(trimmedName, config, filePath));

        resultBuilder.addIssues(issues);
        return resultBuilder.build();
    }

    /**
     * Validates all registered connections
     *
     * @return ValidationResult containing issues from all connections
     */
    public ValidationResult validateAllConnections() {
        ValidationResult.Builder resultBuilder = ValidationResult.builder()
            .validationType("All Connections Validation");

        List<ValidationIssue> allIssues = new ArrayList<>();

        // Validate each registered connection
        for (String connectionName : connections.keySet()) {
            ValidationResult connResult = validateConnection(connectionName);
            allIssues.addAll(connResult.getIssues());
        }

        // Check for orphaned connections (defined but never referenced)
        allIssues.addAll(detectOrphanedConnections());

        resultBuilder.addIssues(allIssues);
        return resultBuilder.build();
    }

    /**
     * Backwards-compatible alias for older call sites/tests.
     */
    public ValidationResult validate() {
        return validateAllConnections();
    }

    /**
     * Validates the connection type is specified and supported
     */
    private List<ValidationIssue> validateConnectionType(String connectionName, Map<String, String> config, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        String type = config.get("type");

        if (type == null || type.trim().isEmpty()) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Connection '" + connectionName + "' is missing required 'type' parameter")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Specify connection type (REST, SOAP, DATABASE, FTP, etc.)")
                .build());
            return issues;
        }

        String upperType = type.trim().toUpperCase();
        boolean supported = false;
        for (String supportedType : SUPPORTED_CONNECTION_TYPES) {
            if (supportedType.equals(upperType)) {
                supported = true;
                break;
            }
        }

        if (!supported) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.WARNING)
                .message("Connection '" + connectionName + "' has unsupported type '" + type + "'")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Supported types: " + String.join(", ", SUPPORTED_CONNECTION_TYPES))
                .build());
        }

        return issues;
    }

    /**
     * Validates required parameters are present for the connection type
     */
    private List<ValidationIssue> validateRequiredParameters(String connectionName, Map<String, String> config, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        String type = config.get("type");
        if (type == null) {
            return issues; // Already handled by validateConnectionType
        }

        String upperType = type.trim().toUpperCase();
        String[] requiredParams = TYPE_REQUIRED_PARAMS.get(upperType);

        if (requiredParams != null) {
            for (String param : requiredParams) {
                String value = config.get(param);
                if (value == null || value.trim().isEmpty()) {
                    issues.add(ValidationIssue.builder()
                        .severity(ValidationSeverity.ERROR)
                        .message("Connection '" + connectionName + "' is missing required parameter '" + param + "'")
                        .filePath(filePath)
                        .validationCategory(VALIDATION_CATEGORY)
                        .suggestion("Add '" + param + "' parameter to " + upperType + " connection configuration")
                        .build());
                }
            }
        }

        return issues;
    }

    /**
     * Validates endpoint URL format and accessibility
     */
    private List<ValidationIssue> validateEndpointUrl(String connectionName, Map<String, String> config, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        String url = config.get("url");
        if (url == null || url.trim().isEmpty()) {
            return issues; // Will be caught by validateRequiredParameters if required
        }

        String trimmedUrl = url.trim();

        // Check URL format using Java URL class
        try {
            URL parsedUrl = new URL(trimmedUrl);

            // Validate protocol
            String protocol = parsedUrl.getProtocol().toLowerCase();
            if (!protocol.equals("http") && !protocol.equals("https") && !protocol.equals("ftp") && !protocol.equals("ftps")) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.WARNING)
                    .message("Connection '" + connectionName + "' uses uncommon protocol '" + protocol + "'")
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Verify the protocol is correct. Common protocols: http, https, ftp, ftps")
                    .build());
            }

            // Check for localhost or development URLs
            String host = parsedUrl.getHost();
            if (host != null && (host.equals("localhost") || host.equals("127.0.0.1") || host.endsWith(".local"))) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.INFO)
                    .message("Connection '" + connectionName + "' points to localhost or local network")
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Ensure this is intentional for development/testing. Update for production deployment")
                    .build());
            }

            // Warn about HTTP (non-secure) for production
            if (protocol.equals("http") && !host.equals("localhost") && !host.equals("127.0.0.1")) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.WARNING)
                    .message("Connection '" + connectionName + "' uses insecure HTTP protocol")
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Consider using HTTPS for secure communication, especially in production")
                    .build());
            }

        } catch (MalformedURLException e) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Connection '" + connectionName + "' has invalid URL format: " + e.getMessage())
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Provide a valid URL in format: protocol://host:port/path (e.g., https://api.example.com)")
                .build());
        }

        // Check for common URL mistakes
        if (trimmedUrl.contains(" ")) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Connection '" + connectionName + "' URL contains spaces")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Remove spaces from URL or encode them as %20")
                .build());
        }

        // Validate WSDL URL if present (for SOAP connections)
        String wsdlUrl = config.get("wsdlUrl");
        if (wsdlUrl != null && !wsdlUrl.trim().isEmpty()) {
            try {
                new URL(wsdlUrl.trim());

                if (!wsdlUrl.toLowerCase().endsWith(".wsdl") && !wsdlUrl.contains("?wsdl")) {
                    issues.add(ValidationIssue.builder()
                        .severity(ValidationSeverity.WARNING)
                        .message("Connection '" + connectionName + "' WSDL URL doesn't end with .wsdl or ?wsdl")
                        .filePath(filePath)
                        .validationCategory(VALIDATION_CATEGORY)
                        .suggestion("Verify the WSDL URL is correct. WSDL URLs typically end with .wsdl or ?wsdl")
                        .build());
                }
            } catch (MalformedURLException e) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.ERROR)
                    .message("Connection '" + connectionName + "' has invalid WSDL URL format: " + e.getMessage())
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Provide a valid WSDL URL")
                    .build());
            }
        }

        return issues;
    }

    /**
     * Validates authentication configuration
     */
    private List<ValidationIssue> validateAuthentication(String connectionName, Map<String, String> config, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        String authType = config.get("authType");

        // If no auth type specified, check if it's required for this connection type
        if (authType == null || authType.trim().isEmpty()) {
            String type = config.get("type");
            if (type != null) {
                String upperType = type.trim().toUpperCase();
                // REST, HTTPS, and SFTP typically require authentication
                if (upperType.equals("REST") || upperType.equals("HTTPS") || upperType.equals("SFTP")) {
                    issues.add(ValidationIssue.builder()
                        .severity(ValidationSeverity.WARNING)
                        .message("Connection '" + connectionName + "' does not specify authentication type")
                        .filePath(filePath)
                        .validationCategory(VALIDATION_CATEGORY)
                        .suggestion("Specify authType (NONE, BASIC, OAUTH, API_KEY, etc.) for " + upperType + " connections")
                        .build());
                }
            }
            return issues;
        }

        String upperAuthType = authType.trim().toUpperCase();

        // Check if auth type is supported
        boolean supported = false;
        for (String supportedAuth : SUPPORTED_AUTH_TYPES) {
            if (supportedAuth.equals(upperAuthType)) {
                supported = true;
                break;
            }
        }

        if (!supported) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.WARNING)
                .message("Connection '" + connectionName + "' has unsupported authentication type '" + authType + "'")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Supported authentication types: " + String.join(", ", SUPPORTED_AUTH_TYPES))
                .build());
            return issues;
        }

        // Check for required authentication parameters
        String[] requiredAuthParams = AUTH_REQUIRED_PARAMS.get(upperAuthType);
        if (requiredAuthParams != null) {
            for (String param : requiredAuthParams) {
                String value = config.get(param);
                if (value == null || value.trim().isEmpty()) {
                    issues.add(ValidationIssue.builder()
                        .severity(ValidationSeverity.ERROR)
                        .message("Connection '" + connectionName + "' is missing required authentication parameter '" + param + "'")
                        .filePath(filePath)
                        .validationCategory(VALIDATION_CATEGORY)
                        .suggestion("Add '" + param + "' parameter for " + upperAuthType + " authentication")
                        .build());
                }
            }
        }

        // Validate specific authentication configurations
        issues.addAll(validateAuthenticationDetails(connectionName, config, upperAuthType, filePath));

        return issues;
    }

    /**
     * Validates authentication-specific details
     */
    private List<ValidationIssue> validateAuthenticationDetails(String connectionName, Map<String, String> config,
                                                                String authType, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        switch (authType) {
            case "OAUTH":
            case "OAUTH2":
                // Validate token URL format
                String tokenUrl = config.get("tokenUrl");
                if (tokenUrl != null && !tokenUrl.trim().isEmpty()) {
                    try {
                        new URL(tokenUrl.trim());
                    } catch (MalformedURLException e) {
                        issues.add(ValidationIssue.builder()
                            .severity(ValidationSeverity.ERROR)
                            .message("Connection '" + connectionName + "' has invalid OAuth token URL: " + e.getMessage())
                            .filePath(filePath)
                            .validationCategory(VALIDATION_CATEGORY)
                            .suggestion("Provide a valid token URL for OAuth authentication")
                            .build());
                    }
                }

                // Check for client ID format
                String clientId = config.get("clientId");
                if (clientId != null && clientId.trim().length() < 10) {
                    issues.add(ValidationIssue.builder()
                        .severity(ValidationSeverity.WARNING)
                        .message("Connection '" + connectionName + "' has suspiciously short OAuth client ID")
                        .filePath(filePath)
                        .validationCategory(VALIDATION_CATEGORY)
                        .suggestion("Verify the client ID is correct. OAuth client IDs are typically longer")
                        .build());
                }
                break;

            case "CERTIFICATE":
                // Check certificate path exists indicator
                String certPath = config.get("certificatePath");
                if (certPath != null && !certPath.trim().isEmpty()) {
                    String trimmedPath = certPath.trim();
                    if (!trimmedPath.endsWith(".pem") && !trimmedPath.endsWith(".crt") &&
                        !trimmedPath.endsWith(".cer") && !trimmedPath.endsWith(".p12") &&
                        !trimmedPath.endsWith(".pfx")) {
                        issues.add(ValidationIssue.builder()
                            .severity(ValidationSeverity.WARNING)
                            .message("Connection '" + connectionName + "' certificate path has uncommon file extension")
                            .filePath(filePath)
                            .validationCategory(VALIDATION_CATEGORY)
                            .suggestion("Certificate files typically use .pem, .crt, .cer, .p12, or .pfx extensions")
                            .build());
                    }
                }
                break;

            case "BASIC":
                // Warn about storing passwords in plain text
                String password = config.get("password");
                if (password != null && !password.trim().isEmpty()) {
                    // Check if password looks like it might be plain text (not encrypted/hashed)
                    if (!password.startsWith("${") && !password.startsWith("{") &&
                        !password.contains("encrypted:") && password.length() < 50) {
                        issues.add(ValidationIssue.builder()
                            .severity(ValidationSeverity.INFO)
                            .message("Connection '" + connectionName + "' may contain plain text password")
                            .filePath(filePath)
                            .validationCategory(VALIDATION_CATEGORY)
                            .suggestion("Consider using encrypted passwords or environment variables for security")
                            .build());
                    }
                }
                break;
        }

        return issues;
    }

    /**
     * Detects orphaned connections (defined but never referenced in flows)
     */
    private List<ValidationIssue> detectOrphanedConnections() {
        List<ValidationIssue> issues = new ArrayList<>();

        for (String connectionName : connections.keySet()) {
            if (!referencedConnections.contains(connectionName)) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.INFO)
                    .message("Connection '" + connectionName + "' is defined but not referenced in any flow")
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Remove unused connection or verify it should be used in a flow")
                    .build());
            }
        }

        return issues;
    }

    /**
     * Validates a connection reference (checks if connection exists)
     *
     * @param connectionName The name of the referenced connection
     * @param filePath The file path where this reference occurs (for error reporting)
     * @return ValidationResult containing any issues found
     */
    public ValidationResult validateConnectionReference(String connectionName, String filePath) {
        ValidationResult.Builder resultBuilder = ValidationResult.builder()
            .validationType("Connection Reference Validation");

        List<ValidationIssue> issues = new ArrayList<>();

        if (connectionName == null || connectionName.trim().isEmpty()) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Empty connection reference")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Specify a valid connection name")
                .build());
        } else {
            String trimmedName = connectionName.trim();

            // Mark as referenced
            markConnectionAsReferenced(trimmedName);

            // Check if connection exists
            if (!connections.containsKey(trimmedName)) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.ERROR)
                    .message("Referenced connection '" + trimmedName + "' is not defined")
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Define connection '" + trimmedName + "' or check for typos in the connection name")
                    .build());
            }
        }

        resultBuilder.addIssues(issues);
        return resultBuilder.build();
    }

    /**
     * Gets all registered connection names
     *
     * @return Set of connection names
     */
    public Set<String> getRegisteredConnections() {
        return new HashSet<>(connections.keySet());
    }

    /**
     * Gets all referenced connection names
     *
     * @return Set of referenced connection names
     */
    public Set<String> getReferencedConnections() {
        return new HashSet<>(referencedConnections);
    }

    /**
     * Clears all registered connections and references
     */
    public void clear() {
        connections.clear();
        referencedConnections.clear();
    }
}
