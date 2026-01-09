package com.interweave.validation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * XSLTValidator - Validates XSLT files for common errors, validates XPath expressions,
 * and checks for undefined templates.
 *
 * This validator performs comprehensive validation of XSLT transformation files including:
 * - XSLT syntax validation (well-formed XML and valid XSLT)
 * - Template reference checking (detects calls to undefined templates)
 * - XPath expression validation in select/match/test attributes
 * - Detection of unreachable templates (templates never called)
 * - Validation of import/include references
 * - Common XSLT mistake detection
 *
 * Features:
 * - Detailed error reporting with line numbers
 * - XPath expression validation using XPathValidator
 * - Template dependency analysis
 * - Namespace-aware validation
 * - Suggestions for fixing common issues
 *
 * Example usage:
 * <pre>
 * XSLTValidator validator = new XSLTValidator();
 * ValidationResult result = validator.validate(
 *     new File("workspace/MyProject/xslt/Site/new/transactions.xslt")
 * );
 *
 * if (!result.isValid()) {
 *     System.out.println("XSLT validation failed: " + result.toDisplayString());
 * }
 * </pre>
 *
 * @author InterWeave Validation Framework
 * @version 1.0
 */
public class XSLTValidator {

    private static final String VALIDATION_CATEGORY = "XSLT";
    private static final String XSLT_NAMESPACE = "http://www.w3.org/1999/XSL/Transform";

    // Common XSLT elements that contain XPath expressions
    private static final String[] XPATH_ATTRIBUTES = {
        "select", "match", "test"
    };

    private final XPathValidator xpathValidator;
    private final TransformerFactory transformerFactory;

    /**
     * Creates a new XSLTValidator
     */
    public XSLTValidator() {
        this.xpathValidator = new XPathValidator();
        this.transformerFactory = TransformerFactory.newInstance();
    }

    /**
     * Validates an XSLT file
     *
     * @param xsltFile The XSLT file to validate
     * @return ValidationResult containing any validation issues found
     */
    public ValidationResult validate(File xsltFile) {
        ValidationResult.Builder resultBuilder = ValidationResult.builder()
            .validationType("XSLT Validation");

        // Check if file exists and is readable
        if (xsltFile == null || !xsltFile.exists()) {
            resultBuilder.addIssue(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("XSLT file does not exist or is null")
                .filePath(xsltFile != null ? xsltFile.getPath() : "null")
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Verify the file path and ensure the file exists")
                .build());
            return resultBuilder.build();
        }

        if (!xsltFile.canRead()) {
            resultBuilder.addIssue(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Cannot read XSLT file - check file permissions")
                .filePath(xsltFile.getPath())
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Ensure the application has read permissions for this file")
                .build());
            return resultBuilder.build();
        }

        // Validate XSLT syntax first
        List<ValidationIssue> issues = new ArrayList<>();
        issues.addAll(validateXSLTSyntax(xsltFile));

        // If there are critical syntax errors, skip further validation
        if (issues.stream().anyMatch(ValidationIssue::isError)) {
            resultBuilder.addIssues(issues);
            return resultBuilder.build();
        }

        // Parse the XSLT document for deeper validation
        try {
            Document doc = parseXSLTDocument(xsltFile);

            // Extract namespace prefixes from the document
            extractNamespaces(doc);

            // Validate template references
            issues.addAll(validateTemplateReferences(doc, xsltFile.getPath()));

            // Validate XPath expressions in attributes
            issues.addAll(validateXPathExpressions(doc, xsltFile.getPath()));

            // Check for unreachable templates
            issues.addAll(detectUnreachableTemplates(doc, xsltFile.getPath()));

            // Validate import/include references
            issues.addAll(validateImportsAndIncludes(doc, xsltFile));

            // Check for common XSLT mistakes
            issues.addAll(checkCommonMistakes(doc, xsltFile.getPath()));

        } catch (Exception e) {
            // If document parsing failed, add error
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Failed to parse XSLT document: " + e.getMessage())
                .filePath(xsltFile.getPath())
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Ensure the file is well-formed XML and valid XSLT")
                .build());
        }

        resultBuilder.addIssues(issues);
        return resultBuilder.build();
    }

    /**
     * Validates XSLT string content
     *
     * @param xsltContent The XSLT content as a string
     * @param filePath Optional file path for error reporting (can be null)
     * @return ValidationResult containing any validation issues found
     */
    public ValidationResult validateString(String xsltContent, String filePath) {
        ValidationResult.Builder resultBuilder = ValidationResult.builder()
            .validationType("XSLT Validation (String)");

        if (xsltContent == null || xsltContent.trim().isEmpty()) {
            resultBuilder.addIssue(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("XSLT content is empty or null")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Provide valid XSLT content")
                .build());
            return resultBuilder.build();
        }

        List<ValidationIssue> issues = new ArrayList<>();
        issues.addAll(validateXSLTSyntaxFromString(xsltContent, filePath));

        // If there are critical syntax errors, skip further validation
        if (issues.stream().anyMatch(ValidationIssue::isError)) {
            resultBuilder.addIssues(issues);
            return resultBuilder.build();
        }

        // Parse and perform deeper validation
        try {
            Document doc = parseXSLTString(xsltContent);
            extractNamespaces(doc);

            issues.addAll(validateTemplateReferences(doc, filePath));
            issues.addAll(validateXPathExpressions(doc, filePath));
            issues.addAll(detectUnreachableTemplates(doc, filePath));
            issues.addAll(checkCommonMistakes(doc, filePath));

        } catch (Exception e) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Failed to parse XSLT content: " + e.getMessage())
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Ensure the content is well-formed XML and valid XSLT")
                .build());
        }

        resultBuilder.addIssues(issues);
        return resultBuilder.build();
    }

    /**
     * Validates XSLT syntax using TransformerFactory
     */
    private List<ValidationIssue> validateXSLTSyntax(File xsltFile) {
        List<ValidationIssue> issues = new ArrayList<>();
        XSLTErrorHandler errorHandler = new XSLTErrorHandler(xsltFile.getPath());

        try {
            Source xsltSource = new StreamSource(xsltFile);
            transformerFactory.setErrorListener(errorHandler);
            Templates templates = transformerFactory.newTemplates(xsltSource);
            issues.addAll(errorHandler.getIssues());

        } catch (TransformerConfigurationException e) {
            // Add error from compilation failure
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("XSLT compilation failed: " + cleanErrorMessage(e.getMessage()))
                .filePath(xsltFile.getPath())
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion(getSuggestionFromException(e))
                .build());
        }

        return issues;
    }

    /**
     * Validates XSLT syntax from string content
     */
    private List<ValidationIssue> validateXSLTSyntaxFromString(String xsltContent, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();
        XSLTErrorHandler errorHandler = new XSLTErrorHandler(filePath);

        try {
            Source xsltSource = new StreamSource(new StringReader(xsltContent));
            transformerFactory.setErrorListener(errorHandler);
            Templates templates = transformerFactory.newTemplates(xsltSource);
            issues.addAll(errorHandler.getIssues());

        } catch (TransformerConfigurationException e) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("XSLT compilation failed: " + cleanErrorMessage(e.getMessage()))
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion(getSuggestionFromException(e))
                .build());
        }

        return issues;
    }

    /**
     * Parses XSLT file into a DOM Document
     */
    private Document parseXSLTDocument(File xsltFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(xsltFile);
    }

    /**
     * Parses XSLT string into a DOM Document
     */
    private Document parseXSLTString(String xsltContent) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new org.xml.sax.InputSource(new StringReader(xsltContent)));
    }

    /**
     * Extracts namespace prefixes from the document and registers them with XPath validator
     */
    private void extractNamespaces(Document doc) {
        Element root = doc.getDocumentElement();
        org.w3c.dom.NamedNodeMap attributes = root.getAttributes();

        for (int i = 0; i < attributes.getLength(); i++) {
            Node attr = attributes.item(i);
            String attrName = attr.getNodeName();
            if (attrName.startsWith("xmlns:")) {
                String prefix = attrName.substring(6);
                String uri = attr.getNodeValue();
                xpathValidator.registerNamespace(prefix, uri);
            }
        }
    }

    /**
     * Validates template references - checks that called templates are defined
     */
    private List<ValidationIssue> validateTemplateReferences(Document doc, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        // Collect all defined template names
        Set<String> definedTemplates = new HashSet<>();
        NodeList templateNodes = doc.getElementsByTagNameNS(XSLT_NAMESPACE, "template");
        for (int i = 0; i < templateNodes.getLength(); i++) {
            Element template = (Element) templateNodes.item(i);
            String name = template.getAttribute("name");
            if (name != null && !name.trim().isEmpty()) {
                definedTemplates.add(name.trim());
            }
        }

        // Check all call-template references
        NodeList callNodes = doc.getElementsByTagNameNS(XSLT_NAMESPACE, "call-template");
        for (int i = 0; i < callNodes.getLength(); i++) {
            Element call = (Element) callNodes.item(i);
            String calledName = call.getAttribute("name");

            if (calledName == null || calledName.trim().isEmpty()) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.ERROR)
                    .message("call-template element missing 'name' attribute")
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Add a 'name' attribute specifying which template to call")
                    .build());
            } else if (!definedTemplates.contains(calledName.trim())) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.ERROR)
                    .message("Template '" + calledName + "' is called but not defined")
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Define a template with name='" + calledName + "' or check for typos in template name")
                    .build());
            }
        }

        // Check apply-templates with mode
        NodeList applyNodes = doc.getElementsByTagNameNS(XSLT_NAMESPACE, "apply-templates");
        Set<String> usedModes = new HashSet<>();
        for (int i = 0; i < applyNodes.getLength(); i++) {
            Element apply = (Element) applyNodes.item(i);
            String mode = apply.getAttribute("mode");
            if (mode != null && !mode.trim().isEmpty()) {
                usedModes.add(mode.trim());
            }
        }

        // Check that templates with mode are actually used
        for (int i = 0; i < templateNodes.getLength(); i++) {
            Element template = (Element) templateNodes.item(i);
            String mode = template.getAttribute("mode");
            String match = template.getAttribute("match");
            if (mode != null && !mode.trim().isEmpty() && !usedModes.contains(mode.trim())) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.WARNING)
                    .message("Template with mode='" + mode + "' is defined but never used")
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Either use apply-templates with mode='" + mode + "' or remove the mode attribute")
                    .build());
            }
        }

        return issues;
    }

    /**
     * Validates XPath expressions in select, match, and test attributes
     */
    private List<ValidationIssue> validateXPathExpressions(Document doc, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        // Get all elements
        NodeList allElements = doc.getElementsByTagName("*");

        for (int i = 0; i < allElements.getLength(); i++) {
            Element element = (Element) allElements.item(i);

            // Skip non-XSLT elements
            if (!XSLT_NAMESPACE.equals(element.getNamespaceURI())) {
                continue;
            }

            // Check each XPath attribute
            for (String attrName : XPATH_ATTRIBUTES) {
                String xpathExpr = element.getAttribute(attrName);

                if (xpathExpr != null && !xpathExpr.trim().isEmpty()) {
                    // Validate the XPath expression
                    ValidationResult xpathResult = xpathValidator.validate(xpathExpr, filePath);

                    // Add any issues found, but mark them as XSLT-related
                    for (ValidationIssue issue : xpathResult.getIssues()) {
                        issues.add(ValidationIssue.builder()
                            .severity(issue.getSeverity())
                            .message("In " + element.getLocalName() + "/@" + attrName + ": " + issue.getMessage())
                            .filePath(filePath)
                            .validationCategory(VALIDATION_CATEGORY)
                            .suggestion(issue.getSuggestion())
                            .build());
                    }
                }
            }
        }

        return issues;
    }

    /**
     * Detects templates that are defined but never called (unreachable)
     */
    private List<ValidationIssue> detectUnreachableTemplates(Document doc, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        // Collect all called template names
        Set<String> calledTemplates = new HashSet<>();
        NodeList callNodes = doc.getElementsByTagNameNS(XSLT_NAMESPACE, "call-template");
        for (int i = 0; i < callNodes.getLength(); i++) {
            Element call = (Element) callNodes.item(i);
            String name = call.getAttribute("name");
            if (name != null && !name.trim().isEmpty()) {
                calledTemplates.add(name.trim());
            }
        }

        // Check all defined named templates
        NodeList templateNodes = doc.getElementsByTagNameNS(XSLT_NAMESPACE, "template");
        for (int i = 0; i < templateNodes.getLength(); i++) {
            Element template = (Element) templateNodes.item(i);
            String name = template.getAttribute("name");
            String match = template.getAttribute("match");

            // Only check named templates (templates with name attribute)
            // Templates with only match attribute can be called implicitly
            if (name != null && !name.trim().isEmpty() &&
                (match == null || match.trim().isEmpty())) {

                if (!calledTemplates.contains(name.trim())) {
                    issues.add(ValidationIssue.builder()
                        .severity(ValidationSeverity.INFO)
                        .message("Template '" + name + "' is defined but never called")
                        .filePath(filePath)
                        .validationCategory(VALIDATION_CATEGORY)
                        .suggestion("Remove unused template or add call-template to use it. Note: This may be called from imported/included files.")
                        .build());
                }
            }
        }

        return issues;
    }

    /**
     * Validates import and include references
     */
    private List<ValidationIssue> validateImportsAndIncludes(Document doc, File xsltFile) {
        List<ValidationIssue> issues = new ArrayList<>();
        File parentDir = xsltFile.getParentFile();

        // Check imports
        NodeList importNodes = doc.getElementsByTagNameNS(XSLT_NAMESPACE, "import");
        for (int i = 0; i < importNodes.getLength(); i++) {
            Element importElem = (Element) importNodes.item(i);
            String href = importElem.getAttribute("href");

            if (href == null || href.trim().isEmpty()) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.ERROR)
                    .message("import element missing 'href' attribute")
                    .filePath(xsltFile.getPath())
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Add 'href' attribute with path to XSLT file to import")
                    .build());
            } else {
                // Check if the referenced file exists (relative to current file)
                File referencedFile = new File(parentDir, href);
                if (!referencedFile.exists()) {
                    issues.add(ValidationIssue.builder()
                        .severity(ValidationSeverity.WARNING)
                        .message("Imported file not found: " + href)
                        .filePath(xsltFile.getPath())
                        .validationCategory(VALIDATION_CATEGORY)
                        .suggestion("Verify the path is correct relative to this XSLT file")
                        .build());
                }
            }
        }

        // Check includes
        NodeList includeNodes = doc.getElementsByTagNameNS(XSLT_NAMESPACE, "include");
        for (int i = 0; i < includeNodes.getLength(); i++) {
            Element includeElem = (Element) includeNodes.item(i);
            String href = includeElem.getAttribute("href");

            if (href == null || href.trim().isEmpty()) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.ERROR)
                    .message("include element missing 'href' attribute")
                    .filePath(xsltFile.getPath())
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Add 'href' attribute with path to XSLT file to include")
                    .build());
            } else {
                File referencedFile = new File(parentDir, href);
                if (!referencedFile.exists()) {
                    issues.add(ValidationIssue.builder()
                        .severity(ValidationSeverity.WARNING)
                        .message("Included file not found: " + href)
                        .filePath(xsltFile.getPath())
                        .validationCategory(VALIDATION_CATEGORY)
                        .suggestion("Verify the path is correct relative to this XSLT file")
                        .build());
                }
            }
        }

        return issues;
    }

    /**
     * Checks for common XSLT mistakes
     */
    private List<ValidationIssue> checkCommonMistakes(Document doc, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        // Check for missing version attribute on stylesheet
        Element root = doc.getDocumentElement();
        if ("stylesheet".equals(root.getLocalName()) || "transform".equals(root.getLocalName())) {
            String version = root.getAttribute("version");
            if (version == null || version.trim().isEmpty()) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.WARNING)
                    .message("XSLT stylesheet missing 'version' attribute")
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Add version='1.0' or version='2.0' to xsl:stylesheet element")
                    .build());
            }
        }

        // Check for value-of without select
        NodeList valueOfNodes = doc.getElementsByTagNameNS(XSLT_NAMESPACE, "value-of");
        for (int i = 0; i < valueOfNodes.getLength(); i++) {
            Element valueOf = (Element) valueOfNodes.item(i);
            String select = valueOf.getAttribute("select");
            if (select == null || select.trim().isEmpty()) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.ERROR)
                    .message("value-of element missing 'select' attribute")
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Add 'select' attribute with XPath expression to specify what value to output")
                    .build());
            }
        }

        // Check for for-each without select
        NodeList forEachNodes = doc.getElementsByTagNameNS(XSLT_NAMESPACE, "for-each");
        for (int i = 0; i < forEachNodes.getLength(); i++) {
            Element forEach = (Element) forEachNodes.item(i);
            String select = forEach.getAttribute("select");
            if (select == null || select.trim().isEmpty()) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.ERROR)
                    .message("for-each element missing 'select' attribute")
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Add 'select' attribute with XPath expression to specify what nodes to iterate")
                    .build());
            }
        }

        // Check for templates without match or name
        NodeList templateNodes = doc.getElementsByTagNameNS(XSLT_NAMESPACE, "template");
        for (int i = 0; i < templateNodes.getLength(); i++) {
            Element template = (Element) templateNodes.item(i);
            String match = template.getAttribute("match");
            String name = template.getAttribute("name");

            if ((match == null || match.trim().isEmpty()) &&
                (name == null || name.trim().isEmpty())) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.ERROR)
                    .message("template element must have 'match' or 'name' attribute")
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Add 'match' attribute to match nodes or 'name' attribute to create named template")
                    .build());
            }
        }

        // Check for variable/param without name
        NodeList variableNodes = doc.getElementsByTagNameNS(XSLT_NAMESPACE, "variable");
        for (int i = 0; i < variableNodes.getLength(); i++) {
            Element variable = (Element) variableNodes.item(i);
            String name = variable.getAttribute("name");
            if (name == null || name.trim().isEmpty()) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.ERROR)
                    .message("variable element missing 'name' attribute")
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Add 'name' attribute to define variable name")
                    .build());
            }
        }

        NodeList paramNodes = doc.getElementsByTagNameNS(XSLT_NAMESPACE, "param");
        for (int i = 0; i < paramNodes.getLength(); i++) {
            Element param = (Element) paramNodes.item(i);
            String name = param.getAttribute("name");
            if (name == null || name.trim().isEmpty()) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.ERROR)
                    .message("param element missing 'name' attribute")
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Add 'name' attribute to define parameter name")
                    .build());
            }
        }

        return issues;
    }

    /**
     * Cleans up error messages
     */
    private String cleanErrorMessage(String errorMessage) {
        if (errorMessage == null) {
            return "Unknown XSLT error";
        }

        String cleaned = errorMessage.replaceAll("javax\\.xml\\.transform\\.[^:]+:\\s*", "");
        cleaned = cleaned.replaceAll("\\s+", " ").trim();

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
            return "Review the XSLT syntax and ensure it follows XSLT 1.0 or 2.0 specification";
        }

        String lowerMessage = message.toLowerCase();

        if (lowerMessage.contains("xsl:") || lowerMessage.contains("element")) {
            return "Check that all XSLT elements are spelled correctly and properly closed";
        }

        if (lowerMessage.contains("namespace")) {
            return "Verify XSLT namespace declaration: xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"";
        }

        if (lowerMessage.contains("attribute")) {
            return "Check that all required attributes are present and have valid values";
        }

        if (lowerMessage.contains("xpath") || lowerMessage.contains("expression")) {
            return "Validate XPath expressions in select, match, and test attributes";
        }

        if (lowerMessage.contains("template")) {
            return "Check template definitions and references";
        }

        return "Review the XSLT file structure and ensure it follows XSLT specification";
    }

    /**
     * Error handler for XSLT compilation errors
     */
    private static class XSLTErrorHandler implements javax.xml.transform.ErrorListener {

        private final String filePath;
        private final List<ValidationIssue> issues;

        public XSLTErrorHandler(String filePath) {
            this.filePath = filePath;
            this.issues = new ArrayList<>();
        }

        @Override
        public void error(javax.xml.transform.TransformerException exception) {
            issues.add(createIssue(ValidationSeverity.ERROR, exception));
        }

        @Override
        public void fatalError(javax.xml.transform.TransformerException exception) {
            issues.add(createIssue(ValidationSeverity.ERROR, exception));
        }

        @Override
        public void warning(javax.xml.transform.TransformerException exception) {
            issues.add(createIssue(ValidationSeverity.WARNING, exception));
        }

        private ValidationIssue createIssue(ValidationSeverity severity, javax.xml.transform.TransformerException exception) {
            String message = exception.getMessage();
            if (message == null) {
                message = "Unknown XSLT error";
            }

            // Clean up message
            message = message.replaceAll("javax\\.xml\\.transform\\.[^:]+:\\s*", "");
            message = message.replaceAll("\\s+", " ").trim();

            ValidationIssue.Builder issueBuilder = ValidationIssue.builder()
                .severity(severity)
                .message(message)
                .filePath(filePath)
                .validationCategory("XSLT");

            // Try to extract line number
            if (exception.getLocator() != null) {
                int lineNumber = exception.getLocator().getLineNumber();
                if (lineNumber > 0) {
                    issueBuilder.lineNumber(lineNumber);
                }
            }

            // Add suggestion based on message
            String suggestion = generateSuggestion(message);
            if (suggestion != null) {
                issueBuilder.suggestion(suggestion);
            }

            return issueBuilder.build();
        }

        private String generateSuggestion(String errorMessage) {
            if (errorMessage == null) {
                return "Check XSLT syntax and structure";
            }

            String lower = errorMessage.toLowerCase();

            if (lower.contains("element") && lower.contains("not allowed")) {
                return "Check that the element is valid in this context and properly namespaced";
            }

            if (lower.contains("attribute") && lower.contains("not allowed")) {
                return "Check that the attribute is valid for this element";
            }

            if (lower.contains("required")) {
                return "Add the required attribute or element";
            }

            if (lower.contains("namespace")) {
                return "Verify namespace declarations and prefixes";
            }

            return "Review XSLT specification for correct usage";
        }

        public List<ValidationIssue> getIssues() {
            return issues;
        }
    }
}
