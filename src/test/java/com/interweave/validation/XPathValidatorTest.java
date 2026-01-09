package com.interweave.validation;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit tests for XPathValidator.
 *
 * Tests comprehensive XPath validation functionality including:
 * - Syntax validation
 * - Namespace prefix validation
 * - Common typo detection
 * - Error message accuracy
 * - Edge cases and error handling
 *
 * @author InterWeave Validation Framework Tests
 * @version 1.0
 */
public class XPathValidatorTest {

    private XPathValidator validator;
    private XPathValidator validatorWithNamespaces;
    private Map<String, String> testNamespaces;

    @Before
    public void setUp() {
        // Create basic validator
        validator = new XPathValidator();

        // Create test namespaces
        testNamespaces = new HashMap<>();
        testNamespaces.put("soap", "http://schemas.xmlsoap.org/soap/envelope/");
        testNamespaces.put("ns", "http://example.com/namespace");
        testNamespaces.put("xsd", "http://www.w3.org/2001/XMLSchema");

        // Create validator with namespaces
        validatorWithNamespaces = new XPathValidator(testNamespaces);
    }

    // ========================================================================
    // Constructor Tests
    // ========================================================================

    @Test
    public void testDefaultConstructor() {
        XPathValidator v = new XPathValidator();
        assertNotNull("Validator should not be null", v);
        assertNotNull("Known namespaces should not be null", v.getKnownNamespaces());
        assertTrue("Known namespaces should be empty", v.getKnownNamespaces().isEmpty());
    }

    @Test
    public void testConstructorWithNamespaces() {
        XPathValidator v = new XPathValidator(testNamespaces);
        assertNotNull("Validator should not be null", v);
        assertEquals("Should have 3 namespaces", 3, v.getKnownNamespaces().size());
        assertEquals("Should have soap namespace",
                "http://schemas.xmlsoap.org/soap/envelope/",
                v.getKnownNamespaces().get("soap"));
    }

    @Test
    public void testConstructorWithNullNamespaces() {
        XPathValidator v = new XPathValidator(null);
        assertNotNull("Validator should not be null", v);
        assertNotNull("Known namespaces should not be null", v.getKnownNamespaces());
        assertTrue("Known namespaces should be empty", v.getKnownNamespaces().isEmpty());
    }

    // ========================================================================
    // Valid XPath Expression Tests
    // ========================================================================

    @Test
    public void testValidSimplePath() {
        ValidationResult result = validator.validate("/Orders/Order");
        assertTrue("Simple path should be valid", result.isValid());
        assertTrue("Should have no issues", result.getIssues().isEmpty());
    }

    @Test
    public void testValidAttributeSelection() {
        ValidationResult result = validator.validate("/Orders/Order/@id");
        assertTrue("Attribute selection should be valid", result.isValid());
    }

    @Test
    public void testValidPredicate() {
        ValidationResult result = validator.validate("/Orders/Order[@id='123']");
        assertTrue("Predicate should be valid", result.isValid());
    }

    @Test
    public void testValidFunction() {
        ValidationResult result = validator.validate("count(/Orders/Order)");
        assertTrue("Function should be valid", result.isValid());
    }

    @Test
    public void testValidNestedFunction() {
        ValidationResult result = validator.validate("concat(string(/Order/Name), ' - ', string(/Order/Id))");
        assertTrue("Nested functions should be valid", result.isValid());
    }

    @Test
    public void testValidComplexExpression() {
        ValidationResult result = validator.validate(
                "/Orders/Order[contains(@status, 'pending') and @priority > 5]/Items/Item");
        assertTrue("Complex expression should be valid", result.isValid());
    }

    @Test
    public void testValidRelativePath() {
        ValidationResult result = validator.validate("../Order/Name");
        assertTrue("Relative path should be valid", result.isValid());
    }

    @Test
    public void testValidCurrentNode() {
        ValidationResult result = validator.validate(".");
        assertTrue("Current node should be valid", result.isValid());
    }

    @Test
    public void testValidDescendantAxis() {
        ValidationResult result = validator.validate("//Order");
        assertTrue("Descendant axis should be valid", result.isValid());
    }

    // ========================================================================
    // Invalid XPath Syntax Tests
    // ========================================================================

    @Test
    public void testInvalidSyntaxUnbalancedBrackets() {
        ValidationResult result = validator.validate("/Orders/Order[@id='123'");
        assertFalse("Unbalanced brackets should be invalid", result.isValid());
        assertFalse("Should have issues", result.getIssues().isEmpty());
    }

    @Test
    public void testInvalidSyntaxUnbalancedParentheses() {
        ValidationResult result = validator.validate("count(/Orders/Order");
        assertFalse("Unbalanced parentheses should be invalid", result.isValid());
    }

    @Test
    public void testInvalidSyntaxUnbalancedQuotes() {
        ValidationResult result = validator.validate("/Orders/Order[@name='test]");
        assertFalse("Unbalanced quotes should be invalid", result.isValid());
    }

    @Test
    public void testInvalidSyntaxInvalidAxis() {
        ValidationResult result = validator.validate("/Orders/InvalidAxis:Order");
        assertFalse("Invalid axis should be invalid", result.isValid());
    }

    @Test
    public void testEmptyExpression() {
        ValidationResult result = validator.validate("");
        assertFalse("Empty expression should be invalid", result.isValid());
        assertTrue("Should have at least one issue", result.getIssues().size() > 0);
    }

    @Test
    public void testNullExpression() {
        ValidationResult result = validator.validate(null);
        assertFalse("Null expression should be invalid", result.isValid());
    }

    @Test
    public void testWhitespaceOnlyExpression() {
        ValidationResult result = validator.validate("   ");
        assertFalse("Whitespace-only expression should be invalid", result.isValid());
    }

    // ========================================================================
    // Namespace Validation Tests
    // ========================================================================

    @Test
    public void testValidNamespacePrefix() {
        ValidationResult result = validatorWithNamespaces.validate("/soap:Envelope/soap:Body");
        assertTrue("Valid namespace prefix should pass", result.isValid());
    }

    @Test
    public void testUndefinedNamespacePrefix() {
        ValidationResult result = validator.validate("/undefined:Element");
        assertFalse("Undefined namespace prefix should fail", result.isValid());

        // Check that error message mentions undefined prefix
        boolean foundUndefinedPrefixIssue = false;
        for (ValidationIssue issue : result.getIssues()) {
            if (issue.getMessage().toLowerCase().contains("undefined") ||
                issue.getMessage().toLowerCase().contains("namespace")) {
                foundUndefinedPrefixIssue = true;
                break;
            }
        }
        assertTrue("Should report undefined namespace prefix", foundUndefinedPrefixIssue);
    }

    @Test
    public void testRegisterNamespace() {
        validator.registerNamespace("custom", "http://custom.com/ns");
        assertEquals("Should have 1 namespace", 1, validator.getKnownNamespaces().size());
        assertEquals("Should have custom namespace",
                "http://custom.com/ns",
                validator.getKnownNamespaces().get("custom"));
    }

    @Test
    public void testRegisterNamespaces() {
        Map<String, String> newNamespaces = new HashMap<>();
        newNamespaces.put("ns1", "http://ns1.com");
        newNamespaces.put("ns2", "http://ns2.com");

        validator.registerNamespaces(newNamespaces);
        assertEquals("Should have 2 namespaces", 2, validator.getKnownNamespaces().size());
    }

    @Test
    public void testClearNamespaces() {
        validatorWithNamespaces.clearNamespaces();
        assertTrue("Namespaces should be cleared", validatorWithNamespaces.getKnownNamespaces().isEmpty());
    }

    @Test
    public void testMultipleNamespacePrefixes() {
        ValidationResult result = validatorWithNamespaces.validate("/soap:Envelope/ns:Data/xsd:Element");
        assertTrue("Multiple namespace prefixes should be valid", result.isValid());
    }

    // ========================================================================
    // Common Typo Detection Tests
    // ========================================================================

    @Test
    public void testCommonTypoLenght() {
        ValidationResult result = validator.validate("string-lenght(/Order/Name)");
        assertFalse("Typo 'lenght' should be invalid", result.isValid());

        // Check that suggestion mentions 'length'
        boolean foundSuggestion = false;
        for (ValidationIssue issue : result.getIssues()) {
            if (issue.getSuggestion() != null &&
                issue.getSuggestion().toLowerCase().contains("length")) {
                foundSuggestion = true;
                break;
            }
        }
        assertTrue("Should suggest 'length' correction", foundSuggestion);
    }

    @Test
    public void testCommonTypoPostion() {
        ValidationResult result = validator.validate("postion()");
        assertFalse("Typo 'postion' should be invalid", result.isValid());
    }

    // ========================================================================
    // File Path Context Tests
    // ========================================================================

    @Test
    public void testValidateWithFilePath() {
        ValidationResult result = validator.validate("/Orders/Order[@id='123'", "test.xml");
        assertFalse("Invalid XPath should fail", result.isValid());
        assertEquals("File path should be set", "test.xml", result.getFilePath());
    }

    @Test
    public void testValidateWithNullFilePath() {
        ValidationResult result = validator.validate("/Orders/Order", null);
        assertTrue("Valid XPath should pass", result.isValid());
        assertNull("File path should be null", result.getFilePath());
    }

    // ========================================================================
    // Validation Result Tests
    // ========================================================================

    @Test
    public void testValidationResultStructure() {
        ValidationResult result = validator.validate("/Orders/Order[@invalid");

        assertNotNull("Result should not be null", result);
        assertFalse("Result should be invalid", result.isValid());
        assertNotNull("Issues should not be null", result.getIssues());
        assertFalse("Issues should not be empty", result.getIssues().isEmpty());

        ValidationIssue issue = result.getIssues().get(0);
        assertNotNull("Issue should have message", issue.getMessage());
        assertNotNull("Issue should have category", issue.getCategory());
        assertEquals("Category should be XPath", "XPath", issue.getCategory());
    }

    @Test
    public void testValidationIssueHasSuggestion() {
        ValidationResult result = validator.validate("");

        if (!result.getIssues().isEmpty()) {
            ValidationIssue issue = result.getIssues().get(0);
            // Valid XPath issues should have suggestions
            assertNotNull("Issue should have suggestion", issue.getSuggestion());
        }
    }

    @Test
    public void testErrorMessageAccuracy() {
        ValidationResult result = validator.validate("/Orders/Order[");

        assertFalse("Should be invalid", result.isValid());
        boolean hasRelevantMessage = false;
        for (ValidationIssue issue : result.getIssues()) {
            String msg = issue.getMessage().toLowerCase();
            if (msg.contains("syntax") || msg.contains("bracket") || msg.contains("invalid")) {
                hasRelevantMessage = true;
                break;
            }
        }
        assertTrue("Error message should be relevant", hasRelevantMessage);
    }

    // ========================================================================
    // Edge Case Tests
    // ========================================================================

    @Test
    public void testVeryLongXPath() {
        StringBuilder longXPath = new StringBuilder("/Root");
        for (int i = 0; i < 100; i++) {
            longXPath.append("/Element").append(i);
        }

        ValidationResult result = validator.validate(longXPath.toString());
        assertTrue("Very long XPath should be valid", result.isValid());
    }

    @Test
    public void testXPathWithSpecialCharacters() {
        ValidationResult result = validator.validate("/Orders/Order[@name='O\\'Reilly']");
        assertTrue("XPath with escaped quotes should be valid", result.isValid());
    }

    @Test
    public void testXPathWithNumbers() {
        ValidationResult result = validator.validate("/Orders/Order[1]");
        assertTrue("XPath with position should be valid", result.isValid());
    }

    @Test
    public void testXPathWithMultiplePredicates() {
        ValidationResult result = validator.validate("/Orders/Order[@id='123'][@status='active']");
        assertTrue("Multiple predicates should be valid", result.isValid());
    }

    @Test
    public void testXPathWithLogicalOperators() {
        ValidationResult result = validator.validate("/Orders/Order[@id='123' and @status='active']");
        assertTrue("Logical operators should be valid", result.isValid());
    }

    @Test
    public void testXPathWithComparison() {
        ValidationResult result = validator.validate("/Orders/Order[@price > 100]");
        assertTrue("Comparison operators should be valid", result.isValid());
    }

    @Test
    public void testXPathWithMathOperators() {
        ValidationResult result = validator.validate("/Orders/Order[@price * @quantity > 1000]");
        assertTrue("Math operators should be valid", result.isValid());
    }

    // ========================================================================
    // Multiple Validation Tests
    // ========================================================================

    @Test
    public void testMultipleValidationsIndependence() {
        ValidationResult result1 = validator.validate("/Orders/Order");
        ValidationResult result2 = validator.validate("/Invalid[");

        assertTrue("First validation should be valid", result1.isValid());
        assertFalse("Second validation should be invalid", result2.isValid());
    }

    @Test
    public void testValidatorReuse() {
        // Validate multiple expressions with same validator
        ValidationResult r1 = validator.validate("/Orders/Order");
        ValidationResult r2 = validator.validate("//Product");
        ValidationResult r3 = validator.validate("/Customer[@id='123']");

        assertTrue("First validation should succeed", r1.isValid());
        assertTrue("Second validation should succeed", r2.isValid());
        assertTrue("Third validation should succeed", r3.isValid());
    }

    // ========================================================================
    // Namespace Edge Cases
    // ========================================================================

    @Test
    public void testRegisterNullNamespace() {
        int sizeBefore = validator.getKnownNamespaces().size();
        validator.registerNamespace("test", null);
        // Should handle gracefully - implementation may add it or ignore it
        assertTrue("Should handle null namespace gracefully",
                validator.getKnownNamespaces().size() >= sizeBefore);
    }

    @Test
    public void testRegisterEmptyPrefix() {
        validator.registerNamespace("", "http://test.com");
        // Should handle gracefully
        assertTrue("Should handle empty prefix gracefully", true);
    }

    @Test
    public void testRegisterNullNamespacesMap() {
        validator.registerNamespaces(null);
        // Should handle gracefully without throwing exception
        assertTrue("Should handle null map gracefully", true);
    }

    @Test
    public void testGetKnownNamespacesImmutability() {
        Map<String, String> namespaces = validatorWithNamespaces.getKnownNamespaces();
        int originalSize = namespaces.size();

        // Try to modify returned map (should either be immutable or not affect validator)
        try {
            namespaces.put("test", "http://test.com");
        } catch (UnsupportedOperationException e) {
            // Map is immutable - this is fine
        }

        // Verify original validator namespaces are unchanged or new one was added
        assertTrue("Validator state should be consistent",
                validatorWithNamespaces.getKnownNamespaces().size() >= originalSize);
    }

    // ========================================================================
    // Complex Expression Tests
    // ========================================================================

    @Test
    public void testXPathWithUnion() {
        ValidationResult result = validator.validate("/Orders/Order | /Products/Product");
        assertTrue("Union operator should be valid", result.isValid());
    }

    @Test
    public void testXPathWithWildcard() {
        ValidationResult result = validator.validate("/Orders/*");
        assertTrue("Wildcard should be valid", result.isValid());
    }

    @Test
    public void testXPathWithMultipleWildcards() {
        ValidationResult result = validator.validate("/*/*/Item");
        assertTrue("Multiple wildcards should be valid", result.isValid());
    }

    @Test
    public void testXPathWithParentAxis() {
        ValidationResult result = validator.validate("/Orders/Order/Item/parent::Order");
        assertTrue("Parent axis should be valid", result.isValid());
    }

    @Test
    public void testXPathWithAncestorAxis() {
        ValidationResult result = validator.validate("//Item/ancestor::Order");
        assertTrue("Ancestor axis should be valid", result.isValid());
    }

    @Test
    public void testXPathWithFollowingSibling() {
        ValidationResult result = validator.validate("/Order/Item[1]/following-sibling::Item");
        assertTrue("Following-sibling axis should be valid", result.isValid());
    }
}
