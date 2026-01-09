package com.interweave.validation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ValidationService - Unified validation service that coordinates all validators.
 *
 * This service provides a central entry point for validating entire integration projects,
 * coordinating multiple specialized validators (XPath, Connection, Flow Configuration) to
 * provide comprehensive validation results.
 *
 * The service supports two validation modes:
 * - Quick validation: Fast checks for critical errors only
 * - Deep validation: Comprehensive validation including warnings and suggestions
 *
 * Results are cached for performance, with cache invalidation based on file modification times.
 *
 * Features:
 * - Orchestrates multiple validators in optimal order
 * - Aggregates results from all validators into unified reports
 * - Caches validation results for performance
 * - Supports incremental validation of changed files
 * - Provides quick and deep validation modes
 * - Thread-safe for concurrent validation requests
 *
 * Example usage:
 * <pre>
 * ValidationService service = new ValidationService();
 *
 * // Configure project paths
 * service.setProjectRoot("/path/to/project");
 *
 * // Run quick validation
 * ValidationResult quickResult = service.validateQuick();
 * if (!quickResult.isValid()) {
 *     System.out.println("Quick validation failed: " + quickResult.getErrorCount() + " errors");
 * }
 *
 * // Run deep validation
 * ValidationResult deepResult = service.validateDeep();
 * System.out.println(deepResult.toDisplayString());
 *
 * // Validate specific file
 * ValidationResult fileResult = service.validateFile("/path/to/flow.xml");
 * </pre>
 *
 * @author InterWeave Validation Framework
 * @version 1.0
 */
public class ValidationService {

    private static final String VALIDATION_CATEGORY = "Validation Service";

    // Validator instances
    private final XPathValidator xpathValidator;
    private final ConnectionValidator connectionValidator;
    private final FlowConfigValidator flowConfigValidator;

    // Project configuration
    private String projectRoot;
    private boolean cacheEnabled;

    // Result cache: file path -> cached validation result
    private final Map<String, CachedValidationResult> resultCache;

    // File modification time tracking for cache invalidation
    private final Map<String, Long> fileModificationTimes;

    /**
     * Creates a new ValidationService with default configuration
     */
    public ValidationService() {
        this.xpathValidator = new XPathValidator();
        this.connectionValidator = new ConnectionValidator();
        this.flowConfigValidator = new FlowConfigValidator();
        this.resultCache = new ConcurrentHashMap<>();
        this.fileModificationTimes = new ConcurrentHashMap<>();
        this.cacheEnabled = true;
    }

    /**
     * Creates a new ValidationService with specified project root
     *
     * @param projectRoot The root directory of the project to validate
     */
    public ValidationService(String projectRoot) {
        this();
        this.projectRoot = projectRoot;
    }

    /**
     * Sets the project root directory
     *
     * @param projectRoot The root directory path
     */
    public void setProjectRoot(String projectRoot) {
        this.projectRoot = projectRoot;
        clearCache(); // Clear cache when project changes
    }

    /**
     * Gets the current project root directory
     *
     * @return The project root path
     */
    public String getProjectRoot() {
        return projectRoot;
    }

    /**
     * Enables or disables result caching
     *
     * @param enabled true to enable caching, false to disable
     */
    public void setCacheEnabled(boolean enabled) {
        this.cacheEnabled = enabled;
        if (!enabled) {
            clearCache();
        }
    }

    /**
     * Checks if result caching is enabled
     *
     * @return true if caching is enabled
     */
    public boolean isCacheEnabled() {
        return cacheEnabled;
    }

    /**
     * Performs quick validation of the project (errors only, minimal checks)
     *
     * Quick validation focuses on critical errors that would prevent deployment:
     * - Missing required connections
     * - Invalid XPath syntax in critical locations
     * - Missing required flow elements
     * - Circular reference detection
     *
     * @return ValidationResult containing only error-level issues
     */
    public ValidationResult validateQuick() {
        ValidationResult.Builder resultBuilder = ValidationResult.builder()
            .validationType("Quick Project Validation");

        if (projectRoot == null || projectRoot.trim().isEmpty()) {
            resultBuilder.addIssue(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Project root not set")
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Call setProjectRoot() before validating")
                .build());
            return resultBuilder.build();
        }

        List<ValidationIssue> allIssues = new ArrayList<>();

        // Step 1: Discover and register connections
        discoverConnections();

        // Step 2: Validate connections (quick mode - existence only)
        ValidationResult connectionResult = connectionValidator.validateAllConnections();
        // Include only errors in quick mode
        allIssues.addAll(connectionResult.getErrors());

        // Step 3: Validate flow configurations
        List<String> flowFiles = discoverFlowFiles();
        for (String flowFile : flowFiles) {
            ValidationResult flowResult = validateFlowFile(flowFile, true);
            allIssues.addAll(flowResult.getErrors());
        }

        resultBuilder.addIssues(allIssues);
        return resultBuilder.build();
    }

    /**
     * Performs deep validation of the project (all issues, comprehensive checks)
     *
     * Deep validation includes:
     * - All quick validation checks
     * - Warning-level issues (e.g., performance concerns)
     * - Info-level suggestions
     * - Orphaned connection detection
     * - XPath performance analysis
     * - Configuration best practices
     *
     * @return ValidationResult containing all issues (errors, warnings, info)
     */
    public ValidationResult validateDeep() {
        ValidationResult.Builder resultBuilder = ValidationResult.builder()
            .validationType("Deep Project Validation");

        if (projectRoot == null || projectRoot.trim().isEmpty()) {
            resultBuilder.addIssue(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Project root not set")
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Call setProjectRoot() before validating")
                .build());
            return resultBuilder.build();
        }

        List<ValidationIssue> allIssues = new ArrayList<>();

        // Step 1: Discover and register connections
        discoverConnections();

        // Step 2: Validate all connections (with all severity levels)
        ValidationResult connectionResult = connectionValidator.validateAllConnections();
        allIssues.addAll(connectionResult.getIssues());

        // Step 3: Discover and register transformations
        discoverTransformations();

        // Step 4: Validate flow configurations
        List<String> flowFiles = discoverFlowFiles();
        for (String flowFile : flowFiles) {
            ValidationResult flowResult = validateFlowFile(flowFile, false);
            allIssues.addAll(flowResult.getIssues());
        }

        // Step 5: Validate XPath expressions in XSLT files
        List<String> xsltFiles = discoverXSLTFiles();
        for (String xsltFile : xsltFiles) {
            ValidationResult xsltResult = validateXSLTFile(xsltFile);
            allIssues.addAll(xsltResult.getIssues());
        }

        // Step 6: Detect orphaned connections
        // (Already included in connectionValidator.validateAllConnections())

        resultBuilder.addIssues(allIssues);
        return resultBuilder.build();
    }

    /**
     * Validates a specific file
     *
     * @param filePath The path to the file to validate
     * @return ValidationResult for the specified file
     */
    public ValidationResult validateFile(String filePath) {
        return validateFile(filePath, false);
    }

    /**
     * Validates a specific file with cache support
     *
     * @param filePath The path to the file to validate
     * @param quickMode true for quick validation (errors only)
     * @return ValidationResult for the specified file
     */
    public ValidationResult validateFile(String filePath, boolean quickMode) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return ValidationResult.builder()
                .validationType("File Validation")
                .addIssue(ValidationIssue.builder()
                    .severity(ValidationSeverity.ERROR)
                    .message("File path is null or empty")
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Provide a valid file path")
                    .build())
                .build();
        }

        // Check cache if enabled
        if (cacheEnabled) {
            CachedValidationResult cached = getCachedResult(filePath);
            if (cached != null) {
                return cached.result;
            }
        }

        // Determine file type and validate accordingly
        ValidationResult result;
        String lowerPath = filePath.toLowerCase();

        if (lowerPath.endsWith(".xml")) {
            // Could be flow configuration or other XML
            result = validateFlowFile(filePath, quickMode);
        } else if (lowerPath.endsWith(".xsl") || lowerPath.endsWith(".xslt")) {
            result = validateXSLTFile(filePath);
        } else {
            result = ValidationResult.builder()
                .validationType("File Validation")
                .addIssue(ValidationIssue.builder()
                    .severity(ValidationSeverity.INFO)
                    .message("Unknown file type: " + filePath)
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Validation service supports .xml, .xsl, and .xslt files")
                    .build())
                .build();
        }

        // Cache the result if enabled
        if (cacheEnabled) {
            cacheResult(filePath, result);
        }

        return result;
    }

    /**
     * Validates an XPath expression
     *
     * @param xpath The XPath expression to validate
     * @param filePath Optional file path context (can be null)
     * @return ValidationResult for the XPath expression
     */
    public ValidationResult validateXPath(String xpath, String filePath) {
        return xpathValidator.validate(xpath, filePath);
    }

    /**
     * Validates a connection configuration
     *
     * @param connectionName The name of the connection to validate
     * @param filePath Optional file path context (can be null)
     * @return ValidationResult for the connection
     */
    public ValidationResult validateConnection(String connectionName, String filePath) {
        return connectionValidator.validateConnection(connectionName, filePath);
    }

    /**
     * Registers a connection for validation
     *
     * @param connectionName The connection name
     * @param configuration Connection configuration parameters
     */
    public void registerConnection(String connectionName, Map<String, String> configuration) {
        connectionValidator.registerConnection(connectionName, configuration);
    }

    /**
     * Registers namespace prefixes for XPath validation
     *
     * @param prefix The namespace prefix
     * @param uri The namespace URI
     */
    public void registerNamespace(String prefix, String uri) {
        xpathValidator.registerNamespace(prefix, uri);
    }

    /**
     * Invalidates cache for a specific file
     *
     * @param filePath The file path to invalidate
     */
    public void invalidateCache(String filePath) {
        if (filePath != null) {
            resultCache.remove(filePath);
            fileModificationTimes.remove(filePath);
        }
    }

    /**
     * Clears the entire validation result cache
     */
    public void clearCache() {
        resultCache.clear();
        fileModificationTimes.clear();
    }

    /**
     * Gets cache statistics
     *
     * @return Map containing cache statistics (size, hits, etc.)
     */
    public Map<String, Object> getCacheStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("cacheSize", resultCache.size());
        stats.put("cacheEnabled", cacheEnabled);
        stats.put("trackedFiles", fileModificationTimes.size());
        return stats;
    }

    /**
     * Discovers connection configurations in the project
     */
    private void discoverConnections() {
        if (projectRoot == null) {
            return;
        }

        // Look for connections.xml or config.xml files
        Path projectPath = Paths.get(projectRoot);
        File connectionsFile = new File(projectPath.toFile(), "connections.xml");
        File configFile = new File(projectPath.toFile(), "config.xml");

        // In a real implementation, parse these files and register connections
        // For now, this is a placeholder for the discovery logic

        // Example: If we find config files, we would parse them and call:
        // connectionValidator.registerConnection(name, configMap);
    }

    /**
     * Discovers transformation (XSLT) files in the project
     */
    private void discoverTransformations() {
        if (projectRoot == null) {
            return;
        }

        List<String> xsltFiles = discoverXSLTFiles();
        for (String xsltFile : xsltFiles) {
            String fileName = new File(xsltFile).getName();
            flowConfigValidator.registerTransformation(fileName);
        }
    }

    /**
     * Discovers flow configuration XML files in the project
     *
     * @return List of flow file paths
     */
    private List<String> discoverFlowFiles() {
        List<String> flowFiles = new ArrayList<>();

        if (projectRoot == null) {
            return flowFiles;
        }

        Path projectPath = Paths.get(projectRoot);

        // Look in common flow directories
        String[] flowDirs = {"transactions", "flows", "integrations"};

        for (String dir : flowDirs) {
            Path flowDir = projectPath.resolve(dir);
            if (Files.exists(flowDir) && Files.isDirectory(flowDir)) {
                try {
                    Files.walk(flowDir)
                        .filter(path -> path.toString().toLowerCase().endsWith(".xml"))
                        .forEach(path -> flowFiles.add(path.toString()));
                } catch (IOException e) {
                    // Log error but continue
                }
            }
        }

        return flowFiles;
    }

    /**
     * Discovers XSLT transformation files in the project
     *
     * @return List of XSLT file paths
     */
    private List<String> discoverXSLTFiles() {
        List<String> xsltFiles = new ArrayList<>();

        if (projectRoot == null) {
            return xsltFiles;
        }

        Path projectPath = Paths.get(projectRoot);

        // Look in common transformation directories
        String[] xsltDirs = {"transformers", "xslt", "transformations"};

        for (String dir : xsltDirs) {
            Path xsltDir = projectPath.resolve(dir);
            if (Files.exists(xsltDir) && Files.isDirectory(xsltDir)) {
                try {
                    Files.walk(xsltDir)
                        .filter(path -> {
                            String lower = path.toString().toLowerCase();
                            return lower.endsWith(".xsl") || lower.endsWith(".xslt");
                        })
                        .forEach(path -> xsltFiles.add(path.toString()));
                } catch (IOException e) {
                    // Log error but continue
                }
            }
        }

        return xsltFiles;
    }

    /**
     * Validates a flow configuration file
     *
     * @param filePath The path to the flow file
     * @param quickMode true for quick validation (errors only)
     * @return ValidationResult for the flow file
     */
    private ValidationResult validateFlowFile(String filePath, boolean quickMode) {
        try {
            // Read file content
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            // Pass registered connections to flow validator
            Set<String> connections = connectionValidator.getRegisteredConnections();
            flowConfigValidator.registerConnections(connections);

            // Validate the flow
            ValidationResult result = flowConfigValidator.validateFlowXml(content, filePath);

            // If quick mode, filter to errors only
            if (quickMode) {
                ValidationResult.Builder filteredBuilder = ValidationResult.builder()
                    .validationType(result.getValidationType())
                    .validationTimestamp(result.getValidationTimestamp());
                filteredBuilder.addIssues(result.getErrors());
                return filteredBuilder.build();
            }

            return result;

        } catch (IOException e) {
            return ValidationResult.builder()
                .validationType("Flow File Validation")
                .addIssue(ValidationIssue.builder()
                    .severity(ValidationSeverity.ERROR)
                    .message("Failed to read flow file: " + e.getMessage())
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Ensure the file exists and is readable")
                    .build())
                .build();
        }
    }

    /**
     * Validates an XSLT transformation file
     *
     * @param filePath The path to the XSLT file
     * @return ValidationResult for the XSLT file
     */
    private ValidationResult validateXSLTFile(String filePath) {
        ValidationResult.Builder resultBuilder = ValidationResult.builder()
            .validationType("XSLT File Validation");

        List<ValidationIssue> issues = new ArrayList<>();

        try {
            // Read file content
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            // Basic XSLT structure validation
            if (!content.contains("<xsl:stylesheet") && !content.contains("<xsl:transform")) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.ERROR)
                    .message("File does not appear to be a valid XSLT file (missing root element)")
                    .filePath(filePath)
                    .validationCategory("XSLT")
                    .suggestion("Ensure file starts with <xsl:stylesheet> or <xsl:transform> root element")
                    .build());
            }

            // Extract and validate XPath expressions from select, match, and test attributes
            // This is a simplified implementation - real implementation would use XML parsing
            String[] xpathAttributes = {"select=\"", "match=\"", "test=\""};

            for (String attr : xpathAttributes) {
                int index = 0;
                while ((index = content.indexOf(attr, index)) != -1) {
                    int start = index + attr.length();
                    int end = content.indexOf("\"", start);
                    if (end != -1) {
                        String xpath = content.substring(start, end);
                        if (!xpath.trim().isEmpty()) {
                            ValidationResult xpathResult = xpathValidator.validate(xpath, filePath);
                            issues.addAll(xpathResult.getIssues());
                        }
                    }
                    index = start;
                }
            }

        } catch (IOException e) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Failed to read XSLT file: " + e.getMessage())
                .filePath(filePath)
                .validationCategory("XSLT")
                .suggestion("Ensure the file exists and is readable")
                .build());
        }

        resultBuilder.addIssues(issues);
        return resultBuilder.build();
    }

    /**
     * Gets a cached validation result if available and still valid
     *
     * @param filePath The file path to check
     * @return Cached result if valid, null otherwise
     */
    private CachedValidationResult getCachedResult(String filePath) {
        CachedValidationResult cached = resultCache.get(filePath);
        if (cached == null) {
            return null;
        }

        // Check if file has been modified since cache time
        File file = new File(filePath);
        if (!file.exists()) {
            resultCache.remove(filePath);
            return null;
        }

        long currentModTime = file.lastModified();
        Long cachedModTime = fileModificationTimes.get(filePath);

        if (cachedModTime == null || currentModTime > cachedModTime) {
            // File has been modified, invalidate cache
            resultCache.remove(filePath);
            fileModificationTimes.remove(filePath);
            return null;
        }

        return cached;
    }

    /**
     * Caches a validation result
     *
     * @param filePath The file path
     * @param result The validation result to cache
     */
    private void cacheResult(String filePath, ValidationResult result) {
        File file = new File(filePath);
        if (file.exists()) {
            long modTime = file.lastModified();
            fileModificationTimes.put(filePath, modTime);
            resultCache.put(filePath, new CachedValidationResult(result, System.currentTimeMillis()));
        }
    }

    /**
     * Internal class to hold cached validation results with metadata
     */
    private static class CachedValidationResult {
        final ValidationResult result;
        final long cacheTime;

        CachedValidationResult(ValidationResult result, long cacheTime) {
            this.result = result;
            this.cacheTime = cacheTime;
        }
    }

    /**
     * Gets the XPath validator instance
     *
     * @return The XPath validator
     */
    public XPathValidator getXPathValidator() {
        return xpathValidator;
    }

    /**
     * Gets the connection validator instance
     *
     * @return The connection validator
     */
    public ConnectionValidator getConnectionValidator() {
        return connectionValidator;
    }

    /**
     * Gets the flow configuration validator instance
     *
     * @return The flow configuration validator
     */
    public FlowConfigValidator getFlowConfigValidator() {
        return flowConfigValidator;
    }
}
