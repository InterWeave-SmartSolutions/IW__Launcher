package com.interweave.validation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * FlowConfigValidator - Validates transaction flow XML configurations for completeness and structural integrity.
 *
 * This validator checks transaction flow configurations used in integration flows for:
 * - Required configuration elements (source, destination, transactions)
 * - Circular references in transaction chains
 * - Transformation mapping completeness
 * - Missing connection references
 * - Structural integrity of flow XML
 *
 * Transaction flows in InterWeave define how data flows from source to destination systems
 * through a series of transactions. Each transaction can reference connections and chain to
 * other transactions, creating complex workflows.
 *
 * Features:
 * - Validates flow structure and required elements
 * - Detects circular transaction chains
 * - Validates connection references
 * - Checks transformation mappings
 * - Validates transaction dependencies
 * - Provides helpful suggestions for fixing issues
 *
 * Example usage:
 * <pre>
 * FlowConfigValidator validator = new FlowConfigValidator();
 *
 * // Register connections (typically from connection validator)
 * validator.registerConnection("SalesforceAPI");
 * validator.registerConnection("QuickBooksAPI");
 *
 * // Validate flow configuration from XML string
 * String flowXml = "..."; // Flow XML content
 * ValidationResult result = validator.validateFlowXml(flowXml, "flows/sync-flow.xml");
 *
 * if (!result.isValid()) {
 *     System.out.println("Flow validation failed: " + result.toDisplayString());
 * }
 * </pre>
 *
 * @author InterWeave Validation Framework
 * @version 1.0
 */
public class FlowConfigValidator {

    private static final String VALIDATION_CATEGORY = "Flow Configuration";

    // Required root-level elements in flow configuration
    private static final String[] REQUIRED_FLOW_ELEMENTS = {
        "transactions"
    };

    // Required elements for each transaction
    private static final String[] REQUIRED_TRANSACTION_ELEMENTS = {
        "name"
    };

    // Valid transaction types
    private static final String[] VALID_TRANSACTION_TYPES = {
        "source", "destination", "transformation", "query", "utility", "custom"
    };

    // Valid schedule types
    private static final String[] VALID_SCHEDULE_TYPES = {
        "interval", "daily", "weekly", "monthly", "single_run", "single_run_configurable"
    };

    // Registered connections available for reference
    private final Set<String> registeredConnections;

    // Registered transformations available for reference
    private final Set<String> registeredTransformations;

    // Track referenced connections to help ConnectionValidator detect orphaned connections
    private final Set<String> referencedConnections;

    /**
     * Creates a new FlowConfigValidator with no predefined connections or transformations
     */
    public FlowConfigValidator() {
        this.registeredConnections = new HashSet<>();
        this.registeredTransformations = new HashSet<>();
        this.referencedConnections = new HashSet<>();
    }

    /**
     * Registers a connection that can be referenced in flows
     *
     * @param connectionName The name of the connection
     */
    public void registerConnection(String connectionName) {
        if (connectionName != null && !connectionName.trim().isEmpty()) {
            registeredConnections.add(connectionName.trim());
        }
    }

    /**
     * Registers multiple connections that can be referenced in flows
     *
     * @param connectionNames Set of connection names
     */
    public void registerConnections(Set<String> connectionNames) {
        if (connectionNames != null) {
            for (String name : connectionNames) {
                registerConnection(name);
            }
        }
    }

    /**
     * Registers a transformation that can be referenced in flows
     *
     * @param transformationName The name of the transformation (XSLT file)
     */
    public void registerTransformation(String transformationName) {
        if (transformationName != null && !transformationName.trim().isEmpty()) {
            registeredTransformations.add(transformationName.trim());
        }
    }

    /**
     * Registers multiple transformations that can be referenced in flows
     *
     * @param transformationNames Set of transformation names
     */
    public void registerTransformations(Set<String> transformationNames) {
        if (transformationNames != null) {
            for (String name : transformationNames) {
                registerTransformation(name);
            }
        }
    }

    /**
     * Validates a flow configuration from XML string
     *
     * @param flowXml The flow XML content to validate
     * @param filePath The file path where this flow is defined (for error reporting)
     * @return ValidationResult containing any issues found
     */
    public ValidationResult validateFlowXml(String flowXml, String filePath) {
        ValidationResult.Builder resultBuilder = ValidationResult.builder()
            .validationType("Flow Configuration Validation");

        List<ValidationIssue> issues = new ArrayList<>();

        // Check for null or empty XML
        if (flowXml == null || flowXml.trim().isEmpty()) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Flow configuration XML is empty or null")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Provide a valid flow configuration XML")
                .build());
            resultBuilder.addIssues(issues);
            return resultBuilder.build();
        }

        // Parse XML
        Document doc;
        try {
            doc = parseXml(flowXml);
        } catch (Exception e) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Failed to parse flow configuration XML: " + e.getMessage())
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Check XML syntax and ensure it is well-formed")
                .build());
            resultBuilder.addIssues(issues);
            return resultBuilder.build();
        }

        // Perform validation checks
        issues.addAll(validateFlowStructure(doc, filePath));
        issues.addAll(validateRequiredElements(doc, filePath));
        issues.addAll(validateTransactions(doc, filePath));
        issues.addAll(validateConnectionReferences(doc, filePath));
        issues.addAll(validateTransformationMappings(doc, filePath));
        issues.addAll(detectCircularReferences(doc, filePath));
        issues.addAll(validateScheduleConfiguration(doc, filePath));

        resultBuilder.addIssues(issues);
        return resultBuilder.build();
    }

    /**
     * Parses XML string into a Document object
     */
    private Document parseXml(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xml)));
    }

    /**
     * Validates the overall structure of the flow configuration
     */
    private List<ValidationIssue> validateFlowStructure(Document doc, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        Element root = doc.getDocumentElement();
        if (root == null) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Flow configuration has no root element")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Ensure the XML has a valid root element (e.g., <flow> or <integration>)")
                .build());
            return issues;
        }

        // Check for common root element names
        String rootName = root.getNodeName();
        if (!rootName.equals("flow") && !rootName.equals("integration") &&
            !rootName.equals("transaction-flow") && !rootName.equals("transactionFlow")) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.WARNING)
                .message("Unexpected root element '" + rootName + "'")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Root element is typically 'flow', 'integration', or 'transaction-flow'")
                .build());
        }

        return issues;
    }

    /**
     * Validates that required elements are present in the flow configuration
     */
    private List<ValidationIssue> validateRequiredElements(Document doc, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        Element root = doc.getDocumentElement();
        if (root == null) {
            return issues; // Already handled in validateFlowStructure
        }

        // Check for required flow-level elements
        for (String requiredElement : REQUIRED_FLOW_ELEMENTS) {
            NodeList nodes = root.getElementsByTagName(requiredElement);
            if (nodes.getLength() == 0) {
                // Try alternative naming conventions
                String camelCase = toCamelCase(requiredElement);
                NodeList altNodes = root.getElementsByTagName(camelCase);
                if (altNodes.getLength() == 0) {
                    issues.add(ValidationIssue.builder()
                        .severity(ValidationSeverity.ERROR)
                        .message("Required element '" + requiredElement + "' is missing from flow configuration")
                        .filePath(filePath)
                        .validationCategory(VALIDATION_CATEGORY)
                        .suggestion("Add <" + requiredElement + "> element to define flow transactions")
                        .build());
                }
            }
        }

        // Check for flow name or ID
        String flowName = root.getAttribute("name");
        String flowId = root.getAttribute("id");
        if ((flowName == null || flowName.trim().isEmpty()) &&
            (flowId == null || flowId.trim().isEmpty())) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.WARNING)
                .message("Flow configuration has no 'name' or 'id' attribute")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Add a 'name' or 'id' attribute to the root element for identification")
                .build());
        }

        return issues;
    }

    /**
     * Validates individual transactions within the flow
     */
    private List<ValidationIssue> validateTransactions(Document doc, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        Element root = doc.getDocumentElement();
        if (root == null) {
            return issues;
        }

        // Find all transaction elements
        NodeList transactionNodes = root.getElementsByTagName("transaction");
        if (transactionNodes.getLength() == 0) {
            // Try alternative naming
            transactionNodes = root.getElementsByTagName("trans");
        }

        if (transactionNodes.getLength() == 0) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.WARNING)
                .message("Flow configuration contains no transactions")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Add <transaction> elements to define flow operations")
                .build());
            return issues;
        }

        // Validate each transaction
        for (int i = 0; i < transactionNodes.getLength(); i++) {
            Node node = transactionNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element transaction = (Element) node;
                issues.addAll(validateTransaction(transaction, filePath, i + 1));
            }
        }

        return issues;
    }

    /**
     * Validates a single transaction element
     */
    private List<ValidationIssue> validateTransaction(Element transaction, String filePath, int transactionIndex) {
        List<ValidationIssue> issues = new ArrayList<>();

        String transactionName = transaction.getAttribute("name");
        String transactionId = transaction.getAttribute("id");
        String locationInfo = "transaction #" + transactionIndex;

        if (transactionName != null && !transactionName.trim().isEmpty()) {
            locationInfo = "transaction '" + transactionName + "'";
        } else if (transactionId != null && !transactionId.trim().isEmpty()) {
            locationInfo = "transaction '" + transactionId + "'";
        }

        // Check for required attributes
        if ((transactionName == null || transactionName.trim().isEmpty()) &&
            (transactionId == null || transactionId.trim().isEmpty())) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.ERROR)
                .message("Transaction at position " + transactionIndex + " has no 'name' or 'id' attribute")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Add a 'name' or 'id' attribute to identify the transaction")
                .build());
        }

        // Validate transaction type if specified
        String type = transaction.getAttribute("type");
        if (type != null && !type.trim().isEmpty()) {
            boolean validType = false;
            for (String validTransType : VALID_TRANSACTION_TYPES) {
                if (validTransType.equalsIgnoreCase(type.trim())) {
                    validType = true;
                    break;
                }
            }
            if (!validType) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.WARNING)
                    .message("Transaction " + locationInfo + " has unknown type '" + type + "'")
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Valid types: " + String.join(", ", VALID_TRANSACTION_TYPES))
                    .build());
            }
        }

        // Check for connection reference
        String connection = transaction.getAttribute("connection");
        if (connection == null || connection.trim().isEmpty()) {
            // Check for connection element
            NodeList connectionNodes = transaction.getElementsByTagName("connection");
            if (connectionNodes.getLength() == 0) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.INFO)
                    .message("Transaction " + locationInfo + " does not specify a connection")
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Add 'connection' attribute or <connection> element if external system access is needed")
                    .build());
            }
        }

        return issues;
    }

    /**
     * Validates connection references in the flow
     */
    private List<ValidationIssue> validateConnectionReferences(Document doc, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        Element root = doc.getDocumentElement();
        if (root == null) {
            return issues;
        }

        // Find all elements with connection attributes
        Set<String> referencedInFlow = new HashSet<>();
        collectConnectionReferences(root, referencedInFlow);

        // Validate each referenced connection
        for (String connectionName : referencedInFlow) {
            if (!registeredConnections.contains(connectionName)) {
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.ERROR)
                    .message("Flow references undefined connection '" + connectionName + "'")
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Define connection '" + connectionName + "' in connections configuration or check for typos")
                    .build());
            } else {
                // Mark as referenced for orphan detection
                referencedConnections.add(connectionName);
            }
        }

        return issues;
    }

    /**
     * Recursively collects all connection references from an element and its children
     */
    private void collectConnectionReferences(Element element, Set<String> connections) {
        // Check connection attribute
        String connection = element.getAttribute("connection");
        if (connection != null && !connection.trim().isEmpty()) {
            connections.add(connection.trim());
        }

        // Check connection-ref attribute
        String connectionRef = element.getAttribute("connection-ref");
        if (connectionRef != null && !connectionRef.trim().isEmpty()) {
            connections.add(connectionRef.trim());
        }

        // Check connection element text content
        NodeList connectionNodes = element.getElementsByTagName("connection");
        for (int i = 0; i < connectionNodes.getLength(); i++) {
            Node node = connectionNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String textContent = node.getTextContent();
                if (textContent != null && !textContent.trim().isEmpty()) {
                    connections.add(textContent.trim());
                }
            }
        }

        // Recursively check child elements
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                collectConnectionReferences((Element) child, connections);
            }
        }
    }

    /**
     * Validates transformation mappings in the flow
     */
    private List<ValidationIssue> validateTransformationMappings(Document doc, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        Element root = doc.getDocumentElement();
        if (root == null) {
            return issues;
        }

        // Find transformation elements
        NodeList transformNodes = root.getElementsByTagName("transformation");
        if (transformNodes.getLength() == 0) {
            transformNodes = root.getElementsByTagName("transform");
        }
        if (transformNodes.getLength() == 0) {
            transformNodes = root.getElementsByTagName("xslt");
        }

        if (transformNodes.getLength() == 0) {
            issues.add(ValidationIssue.builder()
                .severity(ValidationSeverity.INFO)
                .message("Flow configuration contains no transformation mappings")
                .filePath(filePath)
                .validationCategory(VALIDATION_CATEGORY)
                .suggestion("Add transformation elements if data mapping is required between source and destination")
                .build());
            return issues;
        }

        // Validate each transformation
        for (int i = 0; i < transformNodes.getLength(); i++) {
            Node node = transformNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element transform = (Element) node;

                String xsltFile = transform.getAttribute("file");
                if (xsltFile == null || xsltFile.trim().isEmpty()) {
                    xsltFile = transform.getAttribute("xslt");
                }
                if (xsltFile == null || xsltFile.trim().isEmpty()) {
                    xsltFile = transform.getTextContent();
                }

                if (xsltFile == null || xsltFile.trim().isEmpty()) {
                    issues.add(ValidationIssue.builder()
                        .severity(ValidationSeverity.ERROR)
                        .message("Transformation element at position " + (i + 1) + " has no XSLT file specified")
                        .filePath(filePath)
                        .validationCategory(VALIDATION_CATEGORY)
                        .suggestion("Specify XSLT file using 'file' or 'xslt' attribute, or element text content")
                        .build());
                } else {
                    String trimmedFile = xsltFile.trim();
                    // Check if transformation is registered (if we have any registered)
                    if (!registeredTransformations.isEmpty() && !registeredTransformations.contains(trimmedFile)) {
                        issues.add(ValidationIssue.builder()
                            .severity(ValidationSeverity.WARNING)
                            .message("Transformation references unknown XSLT file '" + trimmedFile + "'")
                            .filePath(filePath)
                            .validationCategory(VALIDATION_CATEGORY)
                            .suggestion("Ensure XSLT file '" + trimmedFile + "' exists in transformers directory")
                            .build());
                    }

                    // Check file extension
                    if (!trimmedFile.toLowerCase().endsWith(".xsl") && !trimmedFile.toLowerCase().endsWith(".xslt")) {
                        issues.add(ValidationIssue.builder()
                            .severity(ValidationSeverity.WARNING)
                            .message("Transformation file '" + trimmedFile + "' does not have .xsl or .xslt extension")
                            .filePath(filePath)
                            .validationCategory(VALIDATION_CATEGORY)
                            .suggestion("XSLT files typically use .xsl or .xslt extension")
                            .build());
                    }
                }
            }
        }

        return issues;
    }

    /**
     * Detects circular references in transaction chains
     */
    private List<ValidationIssue> detectCircularReferences(Document doc, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        Element root = doc.getDocumentElement();
        if (root == null) {
            return issues;
        }

        // Build transaction dependency graph
        Map<String, List<String>> transactionGraph = new HashMap<>();
        NodeList transactionNodes = root.getElementsByTagName("transaction");

        for (int i = 0; i < transactionNodes.getLength(); i++) {
            Node node = transactionNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element transaction = (Element) node;
                String transName = transaction.getAttribute("name");
                if (transName == null || transName.trim().isEmpty()) {
                    transName = transaction.getAttribute("id");
                }

                if (transName != null && !transName.trim().isEmpty()) {
                    String trimmedName = transName.trim();
                    List<String> nextTransactions = new ArrayList<>();

                    // Check for next-transaction attribute
                    String nextTrans = transaction.getAttribute("next-transaction");
                    if (nextTrans == null || nextTrans.trim().isEmpty()) {
                        nextTrans = transaction.getAttribute("nextTransaction");
                    }
                    if (nextTrans == null || nextTrans.trim().isEmpty()) {
                        nextTrans = transaction.getAttribute("next");
                    }

                    if (nextTrans != null && !nextTrans.trim().isEmpty()) {
                        nextTransactions.add(nextTrans.trim());
                    }

                    // Check for next-transaction elements
                    NodeList nextNodes = transaction.getElementsByTagName("next-transaction");
                    if (nextNodes.getLength() == 0) {
                        nextNodes = transaction.getElementsByTagName("nextTransaction");
                    }
                    for (int j = 0; j < nextNodes.getLength(); j++) {
                        Node nextNode = nextNodes.item(j);
                        String nextName = nextNode.getTextContent();
                        if (nextName != null && !nextName.trim().isEmpty()) {
                            nextTransactions.add(nextName.trim());
                        }
                    }

                    transactionGraph.put(trimmedName, nextTransactions);
                }
            }
        }

        // Detect cycles using DFS
        Set<String> visited = new HashSet<>();
        Set<String> recursionStack = new HashSet<>();

        for (String transName : transactionGraph.keySet()) {
            List<String> cycle = detectCycle(transName, transactionGraph, visited, recursionStack, new ArrayList<>());
            if (cycle != null && !cycle.isEmpty()) {
                String cycleDescription = String.join(" -> ", cycle);
                issues.add(ValidationIssue.builder()
                    .severity(ValidationSeverity.ERROR)
                    .message("Circular reference detected in transaction chain: " + cycleDescription)
                    .filePath(filePath)
                    .validationCategory(VALIDATION_CATEGORY)
                    .suggestion("Remove circular dependency by breaking the chain at one of the transactions")
                    .build());
                break; // Report only the first cycle found
            }
        }

        return issues;
    }

    /**
     * Detects cycles in transaction dependency graph using DFS
     */
    private List<String> detectCycle(String current, Map<String, List<String>> graph,
                                      Set<String> visited, Set<String> recursionStack,
                                      List<String> path) {
        if (recursionStack.contains(current)) {
            // Found a cycle - build the cycle path
            List<String> cycle = new ArrayList<>();
            int cycleStart = path.indexOf(current);
            if (cycleStart >= 0) {
                for (int i = cycleStart; i < path.size(); i++) {
                    cycle.add(path.get(i));
                }
            }
            cycle.add(current); // Complete the cycle
            return cycle;
        }

        if (visited.contains(current)) {
            return null; // Already processed, no cycle from this node
        }

        visited.add(current);
        recursionStack.add(current);
        path.add(current);

        List<String> neighbors = graph.get(current);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
                List<String> cycle = detectCycle(neighbor, graph, visited, recursionStack, path);
                if (cycle != null) {
                    return cycle;
                }
            }
        }

        path.remove(path.size() - 1);
        recursionStack.remove(current);

        return null;
    }

    /**
     * Validates schedule configuration in the flow
     */
    private List<ValidationIssue> validateScheduleConfiguration(Document doc, String filePath) {
        List<ValidationIssue> issues = new ArrayList<>();

        Element root = doc.getDocumentElement();
        if (root == null) {
            return issues;
        }

        // Check for schedule element
        NodeList scheduleNodes = root.getElementsByTagName("schedule");
        if (scheduleNodes.getLength() > 0) {
            Element schedule = (Element) scheduleNodes.item(0);

            String scheduleType = schedule.getAttribute("type");
            if (scheduleType != null && !scheduleType.trim().isEmpty()) {
                boolean validType = false;
                for (String validSchedType : VALID_SCHEDULE_TYPES) {
                    if (validSchedType.equalsIgnoreCase(scheduleType.trim())) {
                        validType = true;
                        break;
                    }
                }

                if (!validType) {
                    issues.add(ValidationIssue.builder()
                        .severity(ValidationSeverity.WARNING)
                        .message("Unknown schedule type '" + scheduleType + "'")
                        .filePath(filePath)
                        .validationCategory(VALIDATION_CATEGORY)
                        .suggestion("Valid schedule types: " + String.join(", ", VALID_SCHEDULE_TYPES))
                        .build());
                }

                // Validate schedule-specific parameters
                if ("interval".equalsIgnoreCase(scheduleType.trim())) {
                    String interval = schedule.getAttribute("interval");
                    if (interval == null || interval.trim().isEmpty()) {
                        issues.add(ValidationIssue.builder()
                            .severity(ValidationSeverity.ERROR)
                            .message("Schedule type 'interval' requires 'interval' attribute")
                            .filePath(filePath)
                            .validationCategory(VALIDATION_CATEGORY)
                            .suggestion("Add 'interval' attribute with time in seconds (e.g., interval=\"300\" for 5 minutes)")
                            .build());
                    } else {
                        try {
                            int intervalValue = Integer.parseInt(interval.trim());
                            if (intervalValue < 1) {
                                issues.add(ValidationIssue.builder()
                                    .severity(ValidationSeverity.ERROR)
                                    .message("Schedule interval must be positive (got: " + intervalValue + ")")
                                    .filePath(filePath)
                                    .validationCategory(VALIDATION_CATEGORY)
                                    .suggestion("Set interval to a positive number of seconds")
                                    .build());
                            } else if (intervalValue < 60) {
                                issues.add(ValidationIssue.builder()
                                    .severity(ValidationSeverity.WARNING)
                                    .message("Schedule interval is very short (" + intervalValue + " seconds)")
                                    .filePath(filePath)
                                    .validationCategory(VALIDATION_CATEGORY)
                                    .suggestion("Consider using a longer interval to avoid excessive load")
                                    .build());
                            }
                        } catch (NumberFormatException e) {
                            issues.add(ValidationIssue.builder()
                                .severity(ValidationSeverity.ERROR)
                                .message("Schedule interval '" + interval + "' is not a valid number")
                                .filePath(filePath)
                                .validationCategory(VALIDATION_CATEGORY)
                                .suggestion("Specify interval as an integer number of seconds")
                                .build());
                        }
                    }
                }
            }
        }

        return issues;
    }

    /**
     * Converts hyphenated string to camelCase
     */
    private String toCamelCase(String hyphenated) {
        if (hyphenated == null || hyphenated.isEmpty()) {
            return hyphenated;
        }

        String[] parts = hyphenated.split("-");
        if (parts.length == 1) {
            return hyphenated;
        }

        StringBuilder camelCase = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].length() > 0) {
                camelCase.append(parts[i].substring(0, 1).toUpperCase());
                if (parts[i].length() > 1) {
                    camelCase.append(parts[i].substring(1));
                }
            }
        }

        return camelCase.toString();
    }

    /**
     * Gets all connections referenced in validated flows
     *
     * @return Set of referenced connection names
     */
    public Set<String> getReferencedConnections() {
        return new HashSet<>(referencedConnections);
    }

    /**
     * Clears all registered connections, transformations, and references
     */
    public void clear() {
        registeredConnections.clear();
        registeredTransformations.clear();
        referencedConnections.clear();
    }
}
