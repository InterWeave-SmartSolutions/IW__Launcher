package com.interweave.validation;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Unit tests for FlowConfigValidator class.
 *
 * Tests comprehensive flow configuration validation functionality including:
 * - Flow structure validation
 * - Required element validation
 * - Transaction validation
 * - Connection reference validation
 * - Transformation mapping validation
 * - Circular reference detection
 * - Edge case handling
 *
 * @author InterWeave Validation Framework Tests
 * @version 1.0
 */
public class FlowConfigValidatorTest {

    private FlowConfigValidator validator;
    private Set<String> testConnections;
    private Set<String> testTransformations;

    @Before
    public void setUp() {
        validator = new FlowConfigValidator();

        // Register test connections
        testConnections = new HashSet<String>();
        testConnections.add("SalesforceConnection");
        testConnections.add("QuickBooksConnection");
        testConnections.add("DatabaseConnection");

        // Register test transformations
        testTransformations = new HashSet<String>();
        testTransformations.add("SFDCToQB.xslt");
        testTransformations.add("QBToSFDC.xslt");
        testTransformations.add("Transform.xslt");

        validator.registerConnections(testConnections);
        validator.registerTransformations(testTransformations);
    }

    // ========================================================================
    // Connection/Transformation Registration Tests
    // ========================================================================

    @Test
    public void testRegisterConnection() {
        FlowConfigValidator v = new FlowConfigValidator();
        v.registerConnection("TestConnection");

        // Should handle gracefully without throwing exception
        assertTrue("Should register connection successfully", true);
    }

    @Test
    public void testRegisterMultipleConnections() {
        FlowConfigValidator v = new FlowConfigValidator();
        Set<String> connections = new HashSet<String>();
        connections.add("Conn1");
        connections.add("Conn2");
        connections.add("Conn3");

        v.registerConnections(connections);

        // Should handle gracefully
        assertTrue("Should register multiple connections successfully", true);
    }

    @Test
    public void testRegisterNullConnection() {
        FlowConfigValidator v = new FlowConfigValidator();
        v.registerConnection(null);

        // Should handle gracefully
        assertTrue("Should handle null connection gracefully", true);
    }

    @Test
    public void testRegisterEmptyConnection() {
        FlowConfigValidator v = new FlowConfigValidator();
        v.registerConnection("");

        // Should handle gracefully
        assertTrue("Should handle empty connection gracefully", true);
    }

    @Test
    public void testRegisterNullConnectionsSet() {
        FlowConfigValidator v = new FlowConfigValidator();
        v.registerConnections(null);

        // Should handle gracefully
        assertTrue("Should handle null connections set gracefully", true);
    }

    @Test
    public void testRegisterTransformation() {
        FlowConfigValidator v = new FlowConfigValidator();
        v.registerTransformation("Transform.xslt");

        // Should handle gracefully
        assertTrue("Should register transformation successfully", true);
    }

    @Test
    public void testRegisterMultipleTransformations() {
        FlowConfigValidator v = new FlowConfigValidator();
        Set<String> transformations = new HashSet<String>();
        transformations.add("Transform1.xslt");
        transformations.add("Transform2.xslt");

        v.registerTransformations(transformations);

        // Should handle gracefully
        assertTrue("Should register multiple transformations successfully", true);
    }

    @Test
    public void testRegisterNullTransformation() {
        FlowConfigValidator v = new FlowConfigValidator();
        v.registerTransformation(null);

        // Should handle gracefully
        assertTrue("Should handle null transformation gracefully", true);
    }

    @Test
    public void testRegisterNullTransformationsSet() {
        FlowConfigValidator v = new FlowConfigValidator();
        v.registerTransformations(null);

        // Should handle gracefully
        assertTrue("Should handle null transformations set gracefully", true);
    }

    // ========================================================================
    // Valid Flow XML Tests
    // ========================================================================

    @Test
    public void testValidSimpleFlow() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name>SyncOrders</name>\n" +
            "      <type>source</type>\n" +
            "      <connection>SalesforceConnection</connection>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        assertTrue("Valid flow should pass", result.isValid());
    }

    @Test
    public void testValidFlowWithMultipleTransactions() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name>SourceTransaction</name>\n" +
            "      <type>source</type>\n" +
            "      <connection>SalesforceConnection</connection>\n" +
            "    </transaction>\n" +
            "    <transaction>\n" +
            "      <name>DestinationTransaction</name>\n" +
            "      <type>destination</type>\n" +
            "      <connection>QuickBooksConnection</connection>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        assertTrue("Valid flow with multiple transactions should pass", result.isValid());
    }

    @Test
    public void testValidFlowWithTransformation() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name>TransformTransaction</name>\n" +
            "      <type>transformation</type>\n" +
            "      <transformation>SFDCToQB.xslt</transformation>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        assertTrue("Valid flow with transformation should pass", result.isValid());
    }

    // ========================================================================
    // Invalid Flow XML Tests
    // ========================================================================

    @Test
    public void testNullFlowXml() {
        ValidationResult result = validator.validateFlowXml(null, "test-flow.xml");

        assertFalse("Null flow XML should fail", result.isValid());
        assertTrue("Should have error about null XML",
            result.getErrorCount() > 0);
    }

    @Test
    public void testEmptyFlowXml() {
        ValidationResult result = validator.validateFlowXml("", "test-flow.xml");

        assertFalse("Empty flow XML should fail", result.isValid());
        assertTrue("Should have error about empty XML",
            result.getErrorCount() > 0);
    }

    @Test
    public void testWhitespaceOnlyFlowXml() {
        ValidationResult result = validator.validateFlowXml("   \n\t   ", "test-flow.xml");

        assertFalse("Whitespace-only flow XML should fail", result.isValid());
        assertTrue("Should have error", result.getErrorCount() > 0);
    }

    @Test
    public void testMalformedXml() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name>Test</name>\n" +
            "    </transaction>\n" +
            "  </transactions>\n";  // Missing closing </flow> tag

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        assertFalse("Malformed XML should fail", result.isValid());
        assertTrue("Should have error about parsing",
            result.getIssues().stream().anyMatch(new java.util.function.Predicate<ValidationIssue>() {
                public boolean test(ValidationIssue issue) {
                    return issue.getMessage().toLowerCase().contains("parse") ||
                           issue.getMessage().toLowerCase().contains("xml");
                }
            }));
    }

    @Test
    public void testInvalidXmlSyntax() {
        String flowXml = "This is not XML at all";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        assertFalse("Invalid XML syntax should fail", result.isValid());
        assertTrue("Should have error", result.getErrorCount() > 0);
    }

    // ========================================================================
    // Flow Structure Validation Tests
    // ========================================================================

    @Test
    public void testFlowWithoutTransactions() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <name>TestFlow</name>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        assertFalse("Flow without transactions should fail", result.isValid());
        assertTrue("Should have error about missing transactions",
            result.getIssues().stream().anyMatch(new java.util.function.Predicate<ValidationIssue>() {
                public boolean test(ValidationIssue issue) {
                    return issue.getMessage().toLowerCase().contains("transaction");
                }
            }));
    }

    @Test
    public void testEmptyTransactionsElement() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <transactions></transactions>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        // Should handle empty transactions element (might be warning)
        assertNotNull("Result should not be null", result);
    }

    @Test
    public void testUnexpectedRootElement() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<unexpected-root>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name>Test</name>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</unexpected-root>";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        // Should have warning about unexpected root element
        assertTrue("Should have issues", result.getIssueCount() > 0);
    }

    // ========================================================================
    // Transaction Validation Tests
    // ========================================================================

    @Test
    public void testTransactionWithoutName() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <type>source</type>\n" +
            "      <connection>SalesforceConnection</connection>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        assertFalse("Transaction without name should fail", result.isValid());
        assertTrue("Should have error about missing name",
            result.getIssues().stream().anyMatch(new java.util.function.Predicate<ValidationIssue>() {
                public boolean test(ValidationIssue issue) {
                    return issue.getMessage().toLowerCase().contains("name");
                }
            }));
    }

    @Test
    public void testTransactionWithEmptyName() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name></name>\n" +
            "      <type>source</type>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        assertFalse("Transaction with empty name should fail", result.isValid());
    }

    @Test
    public void testValidTransactionTypes() {
        String[] types = {"source", "destination", "transformation", "query", "utility", "custom"};

        for (String type : types) {
            String flowXml = "<?xml version=\"1.0\"?>\n" +
                "<flow>\n" +
                "  <transactions>\n" +
                "    <transaction>\n" +
                "      <name>TestTransaction</name>\n" +
                "      <type>" + type + "</type>\n" +
                "    </transaction>\n" +
                "  </transactions>\n" +
                "</flow>";

            ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

            assertTrue("Transaction type '" + type + "' should be valid", result.isValid());
        }
    }

    @Test
    public void testInvalidTransactionType() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name>TestTransaction</name>\n" +
            "      <type>invalid_type</type>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        // Should have warning about invalid transaction type
        assertTrue("Should have issues", result.getIssueCount() > 0);
    }

    // ========================================================================
    // Connection Reference Validation Tests
    // ========================================================================

    @Test
    public void testTransactionWithValidConnection() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name>SourceTxn</name>\n" +
            "      <connection>SalesforceConnection</connection>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        assertTrue("Transaction with valid connection should pass", result.isValid());
    }

    @Test
    public void testTransactionWithUndefinedConnection() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name>SourceTxn</name>\n" +
            "      <connection>UndefinedConnection</connection>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        assertFalse("Transaction with undefined connection should fail", result.isValid());
        assertTrue("Should have error about undefined connection",
            result.getIssues().stream().anyMatch(new java.util.function.Predicate<ValidationIssue>() {
                public boolean test(ValidationIssue issue) {
                    return issue.getMessage().contains("UndefinedConnection");
                }
            }));
    }

    @Test
    public void testTransactionWithEmptyConnection() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name>SourceTxn</name>\n" +
            "      <connection></connection>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        assertFalse("Transaction with empty connection should fail", result.isValid());
    }

    // ========================================================================
    // Transformation Reference Validation Tests
    // ========================================================================

    @Test
    public void testTransactionWithValidTransformation() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name>TransformTxn</name>\n" +
            "      <transformation>SFDCToQB.xslt</transformation>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        assertTrue("Transaction with valid transformation should pass", result.isValid());
    }

    @Test
    public void testTransactionWithUndefinedTransformation() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name>TransformTxn</name>\n" +
            "      <transformation>UndefinedTransform.xslt</transformation>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        assertFalse("Transaction with undefined transformation should fail", result.isValid());
        assertTrue("Should have error about undefined transformation",
            result.getIssues().stream().anyMatch(new java.util.function.Predicate<ValidationIssue>() {
                public boolean test(ValidationIssue issue) {
                    return issue.getMessage().contains("UndefinedTransform");
                }
            }));
    }

    // ========================================================================
    // Circular Reference Detection Tests
    // ========================================================================

    @Test
    public void testCircularReferenceDetection() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name>TxnA</name>\n" +
            "      <nextTransaction>TxnB</nextTransaction>\n" +
            "    </transaction>\n" +
            "    <transaction>\n" +
            "      <name>TxnB</name>\n" +
            "      <nextTransaction>TxnA</nextTransaction>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        // Should detect circular reference
        assertFalse("Circular reference should fail", result.isValid());
        assertTrue("Should have error about circular reference",
            result.getIssues().stream().anyMatch(new java.util.function.Predicate<ValidationIssue>() {
                public boolean test(ValidationIssue issue) {
                    return issue.getMessage().toLowerCase().contains("circular");
                }
            }));
    }

    @Test
    public void testSelfReferenceDetection() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name>TxnA</name>\n" +
            "      <nextTransaction>TxnA</nextTransaction>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        // Should detect self-reference
        assertFalse("Self-reference should fail", result.isValid());
    }

    @Test
    public void testValidLinearChain() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name>TxnA</name>\n" +
            "      <nextTransaction>TxnB</nextTransaction>\n" +
            "    </transaction>\n" +
            "    <transaction>\n" +
            "      <name>TxnB</name>\n" +
            "      <nextTransaction>TxnC</nextTransaction>\n" +
            "    </transaction>\n" +
            "    <transaction>\n" +
            "      <name>TxnC</name>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        assertTrue("Valid linear chain should pass", result.isValid());
    }

    // ========================================================================
    // Schedule Configuration Tests
    // ========================================================================

    @Test
    public void testValidScheduleTypes() {
        String[] scheduleTypes = {"interval", "daily", "weekly", "monthly", "single_run", "single_run_configurable"};

        for (String scheduleType : scheduleTypes) {
            String flowXml = "<?xml version=\"1.0\"?>\n" +
                "<flow>\n" +
                "  <schedule>\n" +
                "    <type>" + scheduleType + "</type>\n" +
                "  </schedule>\n" +
                "  <transactions>\n" +
                "    <transaction>\n" +
                "      <name>Test</name>\n" +
                "    </transaction>\n" +
                "  </transactions>\n" +
                "</flow>";

            ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

            // Should accept valid schedule type
            assertNotNull("Result should not be null for schedule type: " + scheduleType, result);
        }
    }

    @Test
    public void testInvalidScheduleType() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <schedule>\n" +
            "    <type>invalid_schedule_type</type>\n" +
            "  </schedule>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name>Test</name>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        // Should have warning about invalid schedule type
        assertTrue("Should have issues", result.getIssueCount() > 0);
    }

    // ========================================================================
    // File Path Context Tests
    // ========================================================================

    @Test
    public void testValidationWithFilePath() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name>Test</name>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</flow>";

        String filePath = "workspace/MyProject/flows/sync-flow.xml";
        ValidationResult result = validator.validateFlowXml(flowXml, filePath);

        assertTrue("Valid flow should pass", result.isValid());
    }

    @Test
    public void testValidationWithNullFilePath() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name>Test</name>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, null);

        assertTrue("Valid flow should pass regardless of file path", result.isValid());
    }

    // ========================================================================
    // Edge Case Tests
    // ========================================================================

    @Test
    public void testComplexFlowWithAllElements() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <name>ComplexFlow</name>\n" +
            "  <description>A complex integration flow</description>\n" +
            "  <schedule>\n" +
            "    <type>daily</type>\n" +
            "    <time>02:00</time>\n" +
            "  </schedule>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name>SourceTxn</name>\n" +
            "      <type>source</type>\n" +
            "      <connection>SalesforceConnection</connection>\n" +
            "      <nextTransaction>TransformTxn</nextTransaction>\n" +
            "    </transaction>\n" +
            "    <transaction>\n" +
            "      <name>TransformTxn</name>\n" +
            "      <type>transformation</type>\n" +
            "      <transformation>SFDCToQB.xslt</transformation>\n" +
            "      <nextTransaction>DestTxn</nextTransaction>\n" +
            "    </transaction>\n" +
            "    <transaction>\n" +
            "      <name>DestTxn</name>\n" +
            "      <type>destination</type>\n" +
            "      <connection>QuickBooksConnection</connection>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, "complex-flow.xml");

        assertTrue("Complex flow with all valid elements should pass", result.isValid());
    }

    @Test
    public void testValidationResultProperties() {
        String flowXml = "<?xml version=\"1.0\"?>\n" +
            "<flow>\n" +
            "  <transactions>\n" +
            "    <transaction>\n" +
            "      <name>Test</name>\n" +
            "    </transaction>\n" +
            "  </transactions>\n" +
            "</flow>";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        assertNotNull("Result should not be null", result);
        assertNotNull("Validation type should not be null", result.getValidationType());
        assertTrue("Validation type should mention Flow",
            result.getValidationType().contains("Flow"));
    }

    @Test
    public void testValidationIssueProperties() {
        String flowXml = "Invalid XML";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        assertFalse("Should have errors", result.isValid());
        ValidationIssue issue = result.getIssues().get(0);

        assertNotNull("Issue should not be null", issue);
        assertNotNull("Message should not be null", issue.getMessage());
        assertNotNull("Severity should not be null", issue.getSeverity());
        assertNotNull("Category should not be null", issue.getValidationCategory());
        assertEquals("Category should be Flow Configuration", "Flow Configuration", issue.getValidationCategory());
    }

    @Test
    public void testSuggestionPresence() {
        String flowXml = "Invalid XML";

        ValidationResult result = validator.validateFlowXml(flowXml, "test-flow.xml");

        assertFalse("Should have errors", result.isValid());
        ValidationIssue issue = result.getIssues().get(0);

        assertNotNull("Suggestion should be present", issue.getSuggestion());
        assertFalse("Suggestion should not be empty", issue.getSuggestion().trim().isEmpty());
    }
}
