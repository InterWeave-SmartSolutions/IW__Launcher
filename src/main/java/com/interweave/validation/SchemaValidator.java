package com.interweave.validation;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SchemaValidator - Validates XML configuration files against XSD schemas with detailed error reporting.
 *
 * This validator performs XML Schema (XSD) validation on InterWeave configuration files including:
 * - Transformation Server configuration (ts-config.xsd)
 * - Business Daemon configuration (im-config.xsd)
 * - Transaction mappings (transactions.xsd)
 *
 * Features:
 * - Schema-based validation with detailed error reporting
 * - Line and column number reporting for precise error location
 * - Element-specific error messages
 * - Suggestions for valid values when attributes/elements are invalid
 * - Automatic schema detection based on root element
 * - Multiple validation modes (strict and lenient)
 *
 * Example usage:
 * <pre>
 * SchemaValidator validator = new SchemaValidator();
 *
 * // Validate a configuration file against a specific schema
 * ValidationResult result = validator.validate(
 *     new File("workspace/MyProject/configuration/ts/config.xml"),
 *     new File("schemas/ts-config.xsd")
 * );
 *
 * if (!result.isValid()) {
 *     System.out.println("Validation failed: " + result.toDisplayString());
 * }
 *
 * // Auto-detect schema and validate
 * ValidationResult autoResult = validator.validateWithAutoDetect(
 *     new File("workspace/MyProject/configuration/ts/config.xml")
 * );
 * </pre>
 *
 * @author InterWeave Validation Framework
 * @version 1.0
 */
public class SchemaValidator {

    private static final String VALIDATION_CATEGORY = "XML Schema";

    // Schema mappings: root element name -> schema file path
    private final Map<String, String> schemaRegistry;

    // Cache compiled schemas for performance
    private final Map<String, Schema> schemaCache;

    /**
     * Creates a new SchemaValidator with default schema mappings
     */
    public SchemaValidator() {
        this.schemaRegistry = new HashMap<>();
        this.schemaCache = new HashMap<>();
        initializeDefaultSchemas();
    }

    /**
     * Initializes default schema mappings for InterWeave configuration files
     */
    private void initializeDefaultSchemas() {
        // Map root element names to schema files
        schemaRegistry.put("TransformationServerConfiguration", "schemas/ts-config.xsd");
        schemaRegistry.put("BusinessDaemonConfiguration", "schemas/im-config.xsd");
        schemaRegistry.put("iwmappings", "schemas/transactions.xsd");
    }

    /**
     * Validates an XML file against a specific XSD schema
     *
     * @param xmlFile The XML file to validate
     * @param schemaFile The XSD schema file to validate against
     * @return ValidationResult containing any validation issues found
     */
    public ValidationResult validate(File xmlFile, File schemaFile) {
        ValidationResult.Builder resultBuilder = ValidationResult.builder()
            .validationType("XML Schema Validation");

        // Check if files exist
        if (xmlFile == null || !xmlFile.exists()) {
            resultBuilder.addIssue(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("XML file does not exist or is null")
                .filePath(xmlFile != null ? xmlFile.getPath() : "null")
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Verify the file path and ensure the file exists")
                .build());
            return resultBuilder.build();
        }

        if (schemaFile == null || !schemaFile.exists()) {
            resultBuilder.addIssue(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Schema file does not exist or is null")
                .filePath(schemaFile != null ? schemaFile.getPath() : "null")
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Verify the schema file path and ensure the file exists")
                .build());
            return resultBuilder.build();
        }

        // Check if files are readable
        if (!xmlFile.canRead()) {
            resultBuilder.addIssue(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Cannot read XML file - check file permissions")
                .filePath(xmlFile.getPath())
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Ensure the application has read permissions for this file")
                .build());
            return resultBuilder.build();
        }

        if (!schemaFile.canRead()) {
            resultBuilder.addIssue(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Cannot read schema file - check file permissions")
                .filePath(schemaFile.getPath())
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Ensure the application has read permissions for the schema file")
                .build());
            return resultBuilder.build();
        }

        // Perform schema validation
        List<ValidationIssue> issues = performSchemaValidation(xmlFile, schemaFile);
        resultBuilder.addIssues(issues);

        return resultBuilder.build();
    }

    /**
     * Validates an XML file by automatically detecting the appropriate schema
     *
     * @param xmlFile The XML file to validate
     * @return ValidationResult containing any validation issues found
     */
    public ValidationResult validateWithAutoDetect(File xmlFile) {
        ValidationResult.Builder resultBuilder = ValidationResult.builder()
            .validationType("XML Schema Validation (Auto-Detect)");

        if (xmlFile == null || !xmlFile.exists()) {
            resultBuilder.addIssue(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("XML file does not exist or is null")
                .filePath(xmlFile != null ? xmlFile.getPath() : "null")
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Verify the file path and ensure the file exists")
                .build());
            return resultBuilder.build();
        }

        // Detect root element
        String rootElement = detectRootElement(xmlFile);
        if (rootElement == null) {
            resultBuilder.addIssue(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Cannot determine root element from XML file")
                .filePath(xmlFile.getPath())
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Ensure the XML file is well-formed and has a valid root element")
                .build());
            return resultBuilder.build();
        }

        // Look up schema for this root element
        String schemaPath = schemaRegistry.get(rootElement);
        if (schemaPath == null) {
            resultBuilder.addIssue(ValidationIssue.builder()
                .severity(ValidationSeverity.WARNING)
                .message("No schema registered for root element: " + rootElement)
                .filePath(xmlFile.getPath())
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Register a schema for this element type or validate manually with a specific schema")
                .build());
            return resultBuilder.build();
        }

        // Validate against detected schema
        File schemaFile = new File(schemaPath);
        return validate(xmlFile, schemaFile);
    }

    /**
     * Validates an XML string against a schema file
     *
     * @param xmlContent The XML content as a string
     * @param schemaFile The XSD schema file to validate against
     * @param filePath Optional file path for error reporting (can be null)
     * @return ValidationResult containing any validation issues found
     */
    public ValidationResult validateString(String xmlContent, File schemaFile, String filePath) {
        ValidationResult.Builder resultBuilder = ValidationResult.builder()
            .validationType("XML Schema Validation (String)");

        if (xmlContent == null || xmlContent.trim().isEmpty()) {
            resultBuilder.addIssue(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("XML content is empty or null")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Provide valid XML content")
                .build());
            return resultBuilder.build();
        }

        if (schemaFile == null || !schemaFile.exists()) {
            resultBuilder.addIssue(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Schema file does not exist or is null")
                .filePath(schemaFile != null ? schemaFile.getPath() : "null")
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Verify the schema file path and ensure the file exists")
                .build());
            return resultBuilder.build();
        }

        // Perform validation on string content
        List<ValidationIssue> issues = performSchemaValidationOnString(xmlContent, schemaFile, filePath);
        resultBuilder.addIssues(issues);

        return resultBuilder.build();
    }

    /**
     * Performs the actual schema validation on a file
     */
    private List<ValidationIssue> performSchemaValidation(File xmlFile, File schemaFile) {
        List<ValidationIssue> issues = new ArrayList<>();
        ValidationErrorHandler errorHandler = new ValidationErrorHandler(xmlFile.getPath());

        try {
            // Load or get cached schema
            Schema schema = loadSchema(schemaFile);

            // Create validator
            Validator validator = schema.newValidator();
            validator.setErrorHandler(errorHandler);

            // Validate
            validator.validate(new StreamSource(xmlFile));

            // Collect issues from error handler
            issues.addAll(errorHandler.getIssues());

        } catch (SAXException e) {
            // Schema loading or parsing error
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Schema validation failed: " + cleanErrorMessage(e.getMessage()))
                .filePath(xmlFile.getPath())
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion(getSuggestionFromException(e))
                .build());
        } catch (IOException e) {
            // File reading error
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Cannot read XML file: " + e.getMessage())
                .filePath(xmlFile.getPath())
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Ensure the file exists and is readable")
                .build());
        }

        return issues;
    }

    /**
     * Performs schema validation on XML string content
     */
    private List<ValidationIssue> performSchemaValidationOnString(String xmlContent, File schemaFile, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();
        ValidationErrorHandler errorHandler = new ValidationErrorHandler(filePath);

        try {
            // Load or get cached schema
            Schema schema = loadSchema(schemaFile);

            // Create validator
            Validator validator = schema.newValidator();
            validator.setErrorHandler(errorHandler);

            // Validate
            validator.validate(new StreamSource(new java.io.StringReader(xmlContent)));

            // Collect issues from error handler
            issues.addAll(errorHandler.getIssues());

        } catch (SAXException e) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Schema validation failed: " + cleanErrorMessage(e.getMessage()))
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion(getSuggestionFromException(e))
                .build());
        } catch (IOException e) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Cannot process XML content: " + e.getMessage())
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Ensure the XML content is well-formed")
                .build());
        }

        return issues;
    }

    /**
     * Loads a schema from file, using cache if available
     */
    private Schema loadSchema(File schemaFile) throws SAXException {
        String schemaPath = schemaFile.getAbsolutePath();

        // Check cache first
        if (schemaCache.containsKey(schemaPath)) {
            return schemaCache.get(schemaPath);
        }

        // Load and compile schema
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(schemaFile);

        // Cache for future use
        schemaCache.put(schemaPath, schema);

        return schema;
    }

    /**
     * Detects the root element of an XML file
     */
    private String detectRootElement(File xmlFile) {
        try {
            // Read first few lines to find root element
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(xmlFile));
            String line;
            Pattern rootPattern = Pattern.compile("<([a-zA-Z][a-zA-Z0-9_-]*)(?:\\s|>)");

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Skip XML declaration and comments
                if (line.startsWith("<?xml") || line.startsWith("<!--")) {
                    continue;
                }

                // Find first element tag
                Matcher matcher = rootPattern.matcher(line);
                if (matcher.find()) {
                    reader.close();
                    return matcher.group(1);
                }
            }

            reader.close();
        } catch (IOException e) {
            // Return null if cannot read
            return null;
        }

        return null;
    }

    /**
     * Cleans up error messages from XML parsers
     */
    private String cleanErrorMessage(String errorMessage) {
        if (errorMessage == null) {
            return "Unknown validation error";
        }

        // Remove technical prefixes and clean up
        String cleaned = errorMessage.replaceAll("cvc-[^:]+:\\s*", "");
        cleaned = cleaned.replaceAll("org\\.xml\\.sax\\.[^:]+:\\s*", "");
        cleaned = cleaned.replaceAll("\\s+", " ").trim();

        // Limit length
        if (cleaned.length() > 250) {
            cleaned = cleaned.substring(0, 247) + "...";
        }

        return cleaned;
    }

    /**
     * Generates helpful suggestions based on exception type
     */
    private String getSuggestionFromException(Exception e) {
        String message = e.getMessage();
        if (message == null) {
            return "Review the XML file structure and ensure it matches the schema definition";
        }

        String lowerMessage = message.toLowerCase();

        if (lowerMessage.contains("unexpected element") || lowerMessage.contains("invalid element")) {
            return "Check that all XML elements are spelled correctly and in the correct order as defined in the schema";
        }

        if (lowerMessage.contains("required attribute") || lowerMessage.contains("missing attribute")) {
            return "Add the required attribute to the element. Check schema documentation for required attributes";
        }

        if (lowerMessage.contains("invalid value") || lowerMessage.contains("not facet-valid")) {
            return "Check that the attribute or element value matches the allowed values defined in the schema";
        }

        if (lowerMessage.contains("not complete") || lowerMessage.contains("incomplete content")) {
            return "The element is missing required child elements. Check schema for required child elements";
        }

        if (lowerMessage.contains("namespace")) {
            return "Check XML namespace declarations match the schema's target namespace";
        }

        if (lowerMessage.contains("premature end") || lowerMessage.contains("unexpected end")) {
            return "The XML file appears to be truncated or has unclosed elements. Check for missing closing tags";
        }

        return "Review the XML file structure and ensure it matches the schema definition";
    }

    /**
     * Registers a schema for a specific root element type
     *
     * @param rootElementName The root element name
     * @param schemaPath The path to the schema file
     */
    public void registerSchema(String rootElementName, String schemaPath) {
        if (rootElementName != null && schemaPath != null) {
            schemaRegistry.put(rootElementName, schemaPath);
        }
    }

    /**
     * Unregisters a schema mapping
     *
     * @param rootElementName The root element name to unregister
     */
    public void unregisterSchema(String rootElementName) {
        schemaRegistry.remove(rootElementName);
    }

    /**
     * Gets all registered schema mappings
     *
     * @return Map of root element names to schema file paths
     */
    public Map<String, String> getSchemaRegistry() {
        return new HashMap<>(schemaRegistry);
    }

    /**
     * Clears the schema cache to force reloading on next validation
     */
    public void clearSchemaCache() {
        schemaCache.clear();
    }

    /**
     * Error handler that collects validation errors, warnings, and info messages
     */
    private static class ValidationErrorHandler implements ErrorHandler {

        private final String filePath;
        private final List<ValidationIssue> issues;

        public ValidationErrorHandler(String filePath) {
            this.filePath = filePath;
            this.issues = new ArrayList<>();
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            issues.add(createIssue(ValidationSeverity.ERROR, exception));
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            issues.add(createIssue(ValidationSeverity.ERROR, exception));
        }

        @Override
        public void warning(SAXParseException exception) throws SAXException {
            issues.add(createIssue(ValidationSeverity.WARNING, exception));
        }

        /**
         * Creates a ValidationIssue from a SAXParseException
         */
        private ValidationIssue createIssue(ValidationSeverity severity, SAXParseException exception) {
            String message = cleanSchemaErrorMessage(exception.getMessage());
            String suggestion = generateSuggestion(exception.getMessage());

            // Build location info
            StringBuilder locationInfo = new StringBuilder();
            if (exception.getLineNumber() > 0) {
                locationInfo.append("Line ").append(exception.getLineNumber());
                if (exception.getColumnNumber() > 0) {
                    locationInfo.append(", Column ").append(exception.getColumnNumber());
                }
                locationInfo.append(": ");
            }

            return ValidationIssue.builder()
                .severity(severity)
                .message(locationInfo.toString() + message)
                .filePath(filePath)
                .lineNumber(exception.getLineNumber())
                .validationCategory("XML Schema")
                .suggestion(suggestion)
                .build();
        }

        /**
         * Cleans up schema validation error messages
         */
        private String cleanSchemaErrorMessage(String message) {
            if (message == null) {
                return "Unknown schema validation error";
            }

            // Remove technical error code prefixes
            String cleaned = message.replaceAll("cvc-[a-z-]+\\.[0-9]+(?:\\.[0-9]+)*:\\s*", "");

            // Simplify common messages
            cleaned = cleaned.replace("The value", "Value");
            cleaned = cleaned.replace("The content of element", "Element");

            // Clean up whitespace
            cleaned = cleaned.replaceAll("\\s+", " ").trim();

            return cleaned;
        }

        /**
         * Generates helpful suggestions based on error message
         */
        private String generateSuggestion(String errorMessage) {
            if (errorMessage == null) {
                return "Check the XML structure against the schema definition";
            }

            String lower = errorMessage.toLowerCase();

            // Required attribute missing
            if (lower.contains("attribute") && (lower.contains("required") || lower.contains("must appear"))) {
                Pattern attrPattern = Pattern.compile("attribute[\\s'\"]+([a-zA-Z0-9_-]+)");
                Matcher matcher = attrPattern.matcher(errorMessage);
                if (matcher.find()) {
                    return "Add the required attribute '" + matcher.group(1) + "' to this element";
                }
                return "Add the required attribute to this element (see schema documentation)";
            }

            // Invalid attribute value
            if (lower.contains("not facet-valid") || (lower.contains("invalid") && lower.contains("value"))) {
                if (lower.contains("enumeration")) {
                    return "Use one of the allowed values defined in the schema enumeration";
                }
                return "Check that the value matches the expected type and format (see schema documentation)";
            }

            // Element not allowed
            if (lower.contains("invalid content") || lower.contains("unexpected element")) {
                return "Remove this element or check if it's spelled correctly and in the right location";
            }

            // Element not complete
            if (lower.contains("not complete") || lower.contains("missing child")) {
                return "This element requires additional child elements (check schema for required children)";
            }

            // Type mismatch
            if (lower.contains("not a valid value") && lower.contains("type")) {
                return "Value does not match the expected data type (check if it should be a number, boolean, etc.)";
            }

            return "Check the element structure and attributes against the schema definition";
        }

        public List<ValidationIssue> getIssues() {
            return issues;
        }
    }
}
