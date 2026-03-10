package com.interweave.validation;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * XPathValidator - Validates XPath expressions for syntax errors and common mistakes.
 *
 * This validator checks XPath expressions used in integration flows and XSLT transformations
 * for syntax errors, undefined namespace prefixes, and common typos. It provides helpful
 * error messages and suggestions for fixing issues.
 *
 * Features:
 * - Syntax validation using Java XPath API
 * - Namespace prefix validation
 * - Detection of common typos and mistakes
 * - Exact error location reporting
 * - Helpful suggestions for fixing issues
 *
 * Example usage:
 * <pre>
 * XPathValidator validator = new XPathValidator();
 * ValidationResult result = validator.validate("/Orders/Order[@id='123']");
 * if (!result.isValid()) {
 *     System.out.println("XPath validation failed: " + result.toDisplayString());
 * }
 * </pre>
 *
 * @author InterWeave Validation Framework
 * @version 1.0
 */
public class XPathValidator {

    private static final String VALIDATION_CATEGORY = "XPath";

    // Common XPath functions for validation
    private static final String[] XPATH_FUNCTIONS = {
        "concat", "contains", "count", "false", "last", "local-name", "name",
        "namespace-uri", "normalize-space", "not", "number", "position",
        "starts-with", "string", "string-length", "substring", "substring-after",
        "substring-before", "sum", "translate", "true"
    };

    // Common XPath typos and corrections
    private static final Map<String, String> COMMON_TYPOS = new HashMap<>();
    static {
        COMMON_TYPOS.put("lenght", "length");
        COMMON_TYPOS.put("postion", "position");
        COMMON_TYPOS.put("concate", "concat");
        COMMON_TYPOS.put("substirng", "substring");
        COMMON_TYPOS.put("strign", "string");
        COMMON_TYPOS.put("normlize", "normalize");
        COMMON_TYPOS.put("nmae", "name");
        COMMON_TYPOS.put("localname", "local-name");
        COMMON_TYPOS.put("namepsace", "namespace");
    }

    private final XPathFactory xPathFactory;
    private final Map<String, String> knownNamespaces;

    /**
     * Creates a new XPathValidator with no predefined namespaces
     */
    public XPathValidator() {
        this.xPathFactory = XPathFactory.newInstance();
        this.knownNamespaces = new HashMap<>();
    }

    /**
     * Creates a new XPathValidator with predefined namespaces
     *
     * @param namespaces Map of namespace prefixes to URIs
     */
    public XPathValidator(Map<String, String> namespaces) {
        this.xPathFactory = XPathFactory.newInstance();
        this.knownNamespaces = new HashMap<>(namespaces != null ? namespaces : new HashMap<String, String>());
    }

    /**
     * Validates an XPath expression
     *
     * @param xpathExpression The XPath expression to validate
     * @return ValidationResult containing any issues found
     */
    public ValidationResult validate(String xpathExpression) {
        return validate(xpathExpression, null);
    }

    /**
     * Validates an XPath expression with file context
     *
     * @param xpathExpression The XPath expression to validate
     * @param filePath The file path where this XPath is used (for error reporting)
     * @return ValidationResult containing any issues found
     */
    public ValidationResult validate(String xpathExpression, String filePath) {
        ValidationResult.Builder resultBuilder = ValidationResult.builder()
            .validationType("XPath Expression Validation");

        // Check for null or empty expression
        if (xpathExpression == null || xpathExpression.trim().isEmpty()) {
            resultBuilder.addIssue(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("XPath expression is empty or null")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Provide a valid XPath expression")
                .build());
            return resultBuilder.build();
        }

        String trimmedExpression = xpathExpression.trim();

        // Perform various validation checks
        List<ValidationIssue> issues = new ArrayList<>();

        // Check for common typos first
        issues.addAll(checkCommonTypos(trimmedExpression, filePath));

        // Check for unbalanced parentheses and brackets
        issues.addAll(checkBalancedDelimiters(trimmedExpression, filePath));

        // Check for undefined namespace prefixes
        issues.addAll(checkNamespacePrefixes(trimmedExpression, filePath));

        // Validate syntax using XPath API (only if no critical issues found so far)
        if (issues.stream().noneMatch(ValidationIssue::isError)) {
            issues.addAll(validateSyntax(trimmedExpression, filePath));
        }

        // Check for potential logical issues (warnings)
        issues.addAll(checkPotentialIssues(trimmedExpression, filePath));

        resultBuilder.addIssues(issues);
        return resultBuilder.build();
    }

    /**
     * Validates syntax using Java XPath API
     */
    private List<ValidationIssue> validateSyntax(String expression, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        try {
            XPath xpath = xPathFactory.newXPath();
            XPathExpression compiledExpr = xpath.compile(expression);
        } catch (XPathExpressionException e) {
            // Parse the exception message to provide helpful error information
            String errorMsg = e.getMessage();
            int errorPosition = findErrorPosition(errorMsg);

            String suggestion = getSyntaxErrorSuggestion(errorMsg, expression);

            ValidationIssue.Builder issueBuilder = ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("XPath syntax error: " + cleanErrorMessage(errorMsg))
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY);

            if (suggestion != null) {
                issueBuilder.suggestion(suggestion);
            }

            issues.add(issueBuilder.build());
        }

        return issues;
    }

    /**
     * Checks for common typos in XPath expressions
     */
    private List<ValidationIssue> checkCommonTypos(String expression, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        for (Map.Entry<String, String> typo : COMMON_TYPOS.entrySet()) {
            if (expression.contains(typo.getKey())) {
                int position = expression.indexOf(typo.getKey());
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.WARNING)
                    .message("Possible typo detected: '" + typo.getKey() + "'")
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Did you mean '" + typo.getValue() + "'? (at position " + position + ")")
                    .build());
            }
        }

        return issues;
    }

    /**
     * Checks for unbalanced parentheses, brackets, and quotes
     */
    private List<ValidationIssue> checkBalancedDelimiters(String expression, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        // Check parentheses
        int parenCount = 0;
        int parenPosition = -1;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '(') {
                parenCount++;
                if (parenPosition == -1) parenPosition = i;
            } else if (c == ')') {
                parenCount--;
                if (parenCount < 0) {
                    issues.add(ValidationIssue.builder()
                        .severity(ValidationSeverity.ERROR)
                        .message("Unbalanced parentheses: extra closing parenthesis")
                        .filePath(filePath)
                        .validationCategory(VALIDATION_CATEGORY)
                        .suggestion("Remove extra ')' at position " + i + " or add missing '('")
                        .build());
                    return issues;
                }
            }
        }

        if (parenCount > 0) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Unbalanced parentheses: missing closing parenthesis")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Add " + parenCount + " closing parenthesis ')' (first opened at position " + parenPosition + ")")
                .build());
        }

        // Check brackets
        int bracketCount = 0;
        int bracketPosition = -1;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '[') {
                bracketCount++;
                if (bracketPosition == -1) bracketPosition = i;
            } else if (c == ']') {
                bracketCount--;
                if (bracketCount < 0) {
                    issues.add(ValidationIssue.builder()
                        .severity(ValidationSeverity.ERROR)
                        .message("Unbalanced brackets: extra closing bracket")
                        .filePath(filePath)
                        .validationCategory(VALIDATION_CATEGORY)
                        .suggestion("Remove extra ']' at position " + i + " or add missing '['")
                        .build());
                    return issues;
                }
            }
        }

        if (bracketCount > 0) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Unbalanced brackets: missing closing bracket")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Add " + bracketCount + " closing bracket ']' (first opened at position " + bracketPosition + ")")
                .build());
        }

        // Check quotes (both single and double), skipping backslash-escaped ones
        int singleQuoteCount = 0;
        int doubleQuoteCount = 0;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '\'') {
                if (i > 0 && expression.charAt(i - 1) == '\\') {
                    continue;
                }
                singleQuoteCount++;
            } else if (c == '"') {
                if (i > 0 && expression.charAt(i - 1) == '\\') {
                    continue;
                }
                doubleQuoteCount++;
            }
        }

        if (singleQuoteCount % 2 != 0) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Unbalanced single quotes")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Add missing single quote (') to close string literal")
                .build());
        }

        if (doubleQuoteCount % 2 != 0) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Unbalanced double quotes")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Add missing double quote (\") to close string literal")
                .build());
        }

        return issues;
    }

    /**
     * Checks for undefined namespace prefixes in the expression
     */
    private List<ValidationIssue> checkNamespacePrefixes(String expression, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        // Pattern to match namespace prefixes (e.g., ns:element, soap:Body)
        Pattern namespacePattern = Pattern.compile("([a-zA-Z_][a-zA-Z0-9_-]*):");
        Matcher matcher = namespacePattern.matcher(expression);

        while (matcher.find()) {
            String prefix = matcher.group(1);

            // Skip XPath axis names (they use colons but aren't namespace prefixes)
            if (isXPathAxis(prefix)) {
                continue;
            }

            // Check if this prefix is in our known namespaces
            if (!knownNamespaces.containsKey(prefix)) {
                int position = matcher.start();
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.ERROR)
                    .message("Namespace prefix '" + prefix + "' is not defined")
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Define namespace prefix '" + prefix + "' in your XSLT file or remove it if not needed (found at position " + position + ")")
                    .build());
            }
        }

        return issues;
    }

    /**
     * Checks for potential logical issues that might cause problems
     */
    private List<ValidationIssue> checkPotentialIssues(String expression, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        // Check for double slashes at the start (performance warning)
        if (expression.startsWith("//")) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.INFO)
                .message("Expression starts with '//' which searches entire document")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Consider using a more specific path starting with '/' for better performance")
                .build());
        }

        // Check for * wildcard (performance warning)
        if (expression.contains("//*") || expression.contains("/*")) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.INFO)
                .message("Expression contains wildcard '*' which may impact performance")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Use specific element names instead of wildcards when possible")
                .build());
        }

        // Check for common mistakes: using = instead of == in predicates
        Pattern equalityPattern = Pattern.compile("\\[.*?=[^=].*?\\]");
        if (equalityPattern.matcher(expression).find()) {
            // This is actually correct in XPath (single = is used for equality)
            // But warn if we see == which is wrong
            if (expression.contains("==")) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.ERROR)
                    .message("Invalid operator '==' in XPath expression")
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Use single '=' for equality comparison in XPath, not '=='")
                    .build());
            }
        }

        // Check for trailing or multiple slashes
        if (expression.contains("///")) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Invalid triple slash '///' in expression")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Use single '/' for child axis or '//' for descendant axis")
                .build());
        }

        return issues;
    }

    /**
     * Checks if a string is an XPath axis name
     */
    private boolean isXPathAxis(String name) {
        String[] axes = {
            "ancestor", "ancestor-or-self", "attribute", "child", "descendant",
            "descendant-or-self", "following", "following-sibling", "namespace",
            "parent", "preceding", "preceding-sibling", "self"
        };

        for (String axis : axes) {
            if (axis.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Extracts error position from exception message if available
     */
    private int findErrorPosition(String errorMessage) {
        // Try to extract position from error message
        Pattern posPattern = Pattern.compile("position\\s+(\\d+)|column\\s+(\\d+)|at\\s+(\\d+)");
        Matcher matcher = posPattern.matcher(errorMessage);
        if (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (matcher.group(i) != null) {
                    try {
                        return Integer.parseInt(matcher.group(i));
                    } catch (NumberFormatException e) {
                        // Continue to next group
                    }
                }
            }
        }
        return -1;
    }

    /**
     * Generates a helpful suggestion based on the syntax error
     */
    private String getSyntaxErrorSuggestion(String errorMessage, String expression) {
        String lowerError = errorMessage.toLowerCase();

        if (lowerError.contains("unexpected") || lowerError.contains("invalid character")) {
            return "Check for special characters or operators that may be misplaced";
        }

        if (lowerError.contains("function") || lowerError.contains("unknown function")) {
            return "Verify the function name is spelled correctly and is a valid XPath function";
        }

        if (lowerError.contains("axis")) {
            return "Check that the axis name is valid (child, parent, ancestor, descendant, etc.)";
        }

        if (lowerError.contains("predicate")) {
            return "Check the expression inside square brackets [...]";
        }

        if (lowerError.contains("unexpected end") || lowerError.contains("premature end")) {
            return "The expression appears to be incomplete. Check for missing closing delimiters";
        }

        return "Review XPath syntax documentation and check for typos or malformed expressions";
    }

    /**
     * Cleans up the error message from XPathExpressionException
     */
    private String cleanErrorMessage(String errorMessage) {
        // Remove Java class names and technical details
        String cleaned = errorMessage.replaceAll("javax\\.xml\\.xpath\\.[^:]+:\\s*", "");
        cleaned = cleaned.replaceAll("\\s+", " ").trim();

        // Limit length
        if (cleaned.length() > 200) {
            cleaned = cleaned.substring(0, 197) + "...";
        }

        return cleaned;
    }

    /**
     * Registers a namespace prefix for validation
     *
     * @param prefix The namespace prefix
     * @param uri The namespace URI
     */
    public void registerNamespace(String prefix, String uri) {
        if (prefix != null && uri != null) {
            knownNamespaces.put(prefix, uri);
        }
    }

    /**
     * Registers multiple namespaces for validation
     *
     * @param namespaces Map of namespace prefixes to URIs
     */
    public void registerNamespaces(Map<String, String> namespaces) {
        if (namespaces != null) {
            knownNamespaces.putAll(namespaces);
        }
    }

    /**
     * Gets all registered namespaces
     *
     * @return Map of namespace prefixes to URIs
     */
    public Map<String, String> getKnownNamespaces() {
        return new HashMap<>(knownNamespaces);
    }

    /**
     * Clears all registered namespaces
     */
    public void clearNamespaces() {
        knownNamespaces.clear();
    }
}
