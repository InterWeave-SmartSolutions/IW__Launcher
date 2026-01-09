package com.interweave.validation;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Unit tests for XSLTValidator class.
 *
 * Tests comprehensive XSLT validation functionality including:
 * - XSLT syntax validation
 * - Template reference checking
 * - XPath expression validation
 * - Unreachable template detection
 * - Import/include validation
 * - Edge case handling
 *
 * @author InterWeave Validation Framework Tests
 * @version 1.0
 */
public class XSLTValidatorTest {

    private XSLTValidator validator;
    private File tempXsltFile;
    private File tempDir;

    @Before
    public void setUp() throws IOException {
        validator = new XSLTValidator();

        // Create temp directory for test files
        tempDir = new File(System.getProperty("java.io.tmpdir"), "xslt-validator-test");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
    }

    @After
    public void tearDown() {
        // Clean up temp files
        if (tempXsltFile != null && tempXsltFile.exists()) {
            tempXsltFile.delete();
        }
        if (tempDir != null && tempDir.exists()) {
            tempDir.delete();
        }
    }

    // ========================================================================
    // Helper Methods
    // ========================================================================

    private File createTempFile(String name, String content) throws IOException {
        File file = new File(tempDir, name);
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();
        return file;
    }

    private File createSimpleXslt() throws IOException {
        String xsltContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
            "  <xsl:template match=\"/\">\n" +
            "    <html>\n" +
            "      <body>\n" +
            "        <xsl:value-of select=\"//title\"/>\n" +
            "      </body>\n" +
            "    </html>\n" +
            "  </xsl:template>\n" +
            "</xsl:stylesheet>";
        return createTempFile("simple.xslt", xsltContent);
    }

    // ========================================================================
    // Constructor Tests
    // ========================================================================

    @Test
    public void testDefaultConstructor() {
        XSLTValidator v = new XSLTValidator();
        assertNotNull("Validator should not be null", v);
    }

    // ========================================================================
    // File Validation Tests
    // ========================================================================

    @Test
    public void testValidateWithNullFile() {
        ValidationResult result = validator.validate(null);

        assertFalse("Null file should fail", result.isValid());
        assertTrue("Should have error about null file",
            result.getErrorCount() > 0);
    }

    @Test
    public void testValidateWithNonExistentFile() {
        File nonExistent = new File(tempDir, "nonexistent.xslt");
        ValidationResult result = validator.validate(nonExistent);

        assertFalse("Non-existent file should fail", result.isValid());
        assertTrue("Should have error about missing file",
            result.getErrorCount() > 0);
    }

    @Test
    public void testValidateValidXslt() {
        try {
            File xslt = createSimpleXslt();
            ValidationResult result = validator.validate(xslt);

            assertTrue("Valid XSLT should pass", result.isValid());
            assertEquals("Should have no errors", 0, result.getErrorCount());
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testValidateInvalidXsltSyntax() {
        try {
            String invalidXslt = "<?xml version=\"1.0\"?>\n" +
                "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "  <xsl:template match=\"/\">\n" +
                "    <html>\n" +
                "      <body>Test</body>\n" +
                "    </html>\n" +
                "  <!-- Missing closing template tag -->\n" +
                "</xsl:stylesheet>";
            File xslt = createTempFile("invalid.xslt", invalidXslt);

            ValidationResult result = validator.validate(xslt);

            assertFalse("Invalid XSLT syntax should fail", result.isValid());
            assertTrue("Should have errors", result.getErrorCount() > 0);
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    // ========================================================================
    // String Content Validation Tests
    // ========================================================================

    @Test
    public void testValidateStringWithNullContent() {
        ValidationResult result = validator.validateString(null, "test.xslt");

        assertFalse("Null content should fail", result.isValid());
        assertTrue("Should have error about null content",
            result.getErrorCount() > 0);
    }

    @Test
    public void testValidateStringWithEmptyContent() {
        ValidationResult result = validator.validateString("", "test.xslt");

        assertFalse("Empty content should fail", result.isValid());
        assertTrue("Should have error about empty content",
            result.getErrorCount() > 0);
    }

    @Test
    public void testValidateStringWithValidContent() {
        String xsltContent = "<?xml version=\"1.0\"?>\n" +
            "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
            "  <xsl:template match=\"/\">\n" +
            "    <result/>\n" +
            "  </xsl:template>\n" +
            "</xsl:stylesheet>";

        ValidationResult result = validator.validateString(xsltContent, "test.xslt");

        assertTrue("Valid XSLT string should pass", result.isValid());
    }

    @Test
    public void testValidateStringWithInvalidContent() {
        String invalidXslt = "<?xml version=\"1.0\"?>\n" +
            "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
            "  <xsl:template>\n" +  // Missing match attribute
            "    <result/>\n" +
            "  </xsl:template>\n" +
            "</xsl:stylesheet>";

        ValidationResult result = validator.validateString(invalidXslt, "test.xslt");

        assertFalse("Invalid XSLT should fail", result.isValid());
        assertTrue("Should have errors", result.getIssueCount() > 0);
    }

    // ========================================================================
    // Template Reference Tests
    // ========================================================================

    @Test
    public void testValidNamedTemplateCall() {
        try {
            String xslt = "<?xml version=\"1.0\"?>\n" +
                "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "  <xsl:template match=\"/\">\n" +
                "    <xsl:call-template name=\"myTemplate\"/>\n" +
                "  </xsl:template>\n" +
                "  <xsl:template name=\"myTemplate\">\n" +
                "    <result/>\n" +
                "  </xsl:template>\n" +
                "</xsl:stylesheet>";
            File file = createTempFile("named-template.xslt", xslt);

            ValidationResult result = validator.validate(file);

            assertTrue("Valid named template call should pass", result.isValid());
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testUndefinedNamedTemplateCall() {
        try {
            String xslt = "<?xml version=\"1.0\"?>\n" +
                "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "  <xsl:template match=\"/\">\n" +
                "    <xsl:call-template name=\"undefinedTemplate\"/>\n" +
                "  </xsl:template>\n" +
                "</xsl:stylesheet>";
            File file = createTempFile("undefined-template.xslt", xslt);

            ValidationResult result = validator.validate(file);

            assertFalse("Undefined template call should fail", result.isValid());
            assertTrue("Should have error about undefined template",
                result.getIssues().stream().anyMatch(new java.util.function.Predicate<ValidationIssue>() {
                    public boolean test(ValidationIssue issue) {
                        return issue.getMessage().contains("undefinedTemplate") ||
                               issue.getMessage().toLowerCase().contains("template");
                    }
                }));
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testUnreachableNamedTemplate() {
        try {
            String xslt = "<?xml version=\"1.0\"?>\n" +
                "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "  <xsl:template match=\"/\">\n" +
                "    <result/>\n" +
                "  </xsl:template>\n" +
                "  <xsl:template name=\"neverCalled\">\n" +
                "    <unreachable/>\n" +
                "  </xsl:template>\n" +
                "</xsl:stylesheet>";
            File file = createTempFile("unreachable.xslt", xslt);

            ValidationResult result = validator.validate(file);

            // Should have warning about unreachable template
            assertTrue("Should have warnings", result.getWarningCount() > 0 || result.getInfoCount() > 0);
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    // ========================================================================
    // XPath Expression Tests
    // ========================================================================

    @Test
    public void testValidXPathInSelect() {
        try {
            String xslt = "<?xml version=\"1.0\"?>\n" +
                "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "  <xsl:template match=\"/\">\n" +
                "    <xsl:value-of select=\"/order/customer/name\"/>\n" +
                "  </xsl:template>\n" +
                "</xsl:stylesheet>";
            File file = createTempFile("valid-xpath.xslt", xslt);

            ValidationResult result = validator.validate(file);

            assertTrue("Valid XPath in select should pass", result.isValid());
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidXPathInSelect() {
        try {
            String xslt = "<?xml version=\"1.0\"?>\n" +
                "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "  <xsl:template match=\"/\">\n" +
                "    <xsl:value-of select=\"/order[@id=[\"/>\n" +  // Invalid XPath
                "  </xsl:template>\n" +
                "</xsl:stylesheet>";
            File file = createTempFile("invalid-xpath.xslt", xslt);

            ValidationResult result = validator.validate(file);

            assertFalse("Invalid XPath should fail", result.isValid());
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testValidXPathInMatch() {
        try {
            String xslt = "<?xml version=\"1.0\"?>\n" +
                "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "  <xsl:template match=\"/orders/order[@status='active']\">\n" +
                "    <result/>\n" +
                "  </xsl:template>\n" +
                "</xsl:stylesheet>";
            File file = createTempFile("valid-match.xslt", xslt);

            ValidationResult result = validator.validate(file);

            assertTrue("Valid XPath in match should pass", result.isValid());
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testValidXPathInTest() {
        try {
            String xslt = "<?xml version=\"1.0\"?>\n" +
                "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "  <xsl:template match=\"/\">\n" +
                "    <xsl:if test=\"count(//order) > 0\">\n" +
                "      <hasOrders/>\n" +
                "    </xsl:if>\n" +
                "  </xsl:template>\n" +
                "</xsl:stylesheet>";
            File file = createTempFile("valid-test.xslt", xslt);

            ValidationResult result = validator.validate(file);

            assertTrue("Valid XPath in test should pass", result.isValid());
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    // ========================================================================
    // Common Mistake Detection Tests
    // ========================================================================

    @Test
    public void testMissingVersionAttribute() {
        try {
            String xslt = "<?xml version=\"1.0\"?>\n" +
                "<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +  // Missing version
                "  <xsl:template match=\"/\">\n" +
                "    <result/>\n" +
                "  </xsl:template>\n" +
                "</xsl:stylesheet>";
            File file = createTempFile("no-version.xslt", xslt);

            ValidationResult result = validator.validate(file);

            // Should have warning about missing version
            assertTrue("Should have issues about missing version",
                result.getIssueCount() > 0);
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testValueOfMissingSelectAttribute() {
        try {
            String xslt = "<?xml version=\"1.0\"?>\n" +
                "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "  <xsl:template match=\"/\">\n" +
                "    <xsl:value-of/>\n" +  // Missing select attribute
                "  </xsl:template>\n" +
                "</xsl:stylesheet>";
            File file = createTempFile("value-of-no-select.xslt", xslt);

            ValidationResult result = validator.validate(file);

            // Should have error about missing select
            assertFalse("Should fail validation", result.isValid());
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testForEachMissingSelectAttribute() {
        try {
            String xslt = "<?xml version=\"1.0\"?>\n" +
                "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "  <xsl:template match=\"/\">\n" +
                "    <xsl:for-each>\n" +  // Missing select attribute
                "      <item/>\n" +
                "    </xsl:for-each>\n" +
                "  </xsl:template>\n" +
                "</xsl:stylesheet>";
            File file = createTempFile("for-each-no-select.xslt", xslt);

            ValidationResult result = validator.validate(file);

            // Should have error about missing select
            assertFalse("Should fail validation", result.isValid());
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    // ========================================================================
    // Import/Include Validation Tests
    // ========================================================================

    @Test
    public void testImportWithExistingFile() {
        try {
            // Create a referenced XSLT file
            File imported = createTempFile("imported.xslt",
                "<?xml version=\"1.0\"?>\n" +
                "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "  <xsl:template name=\"imported\">\n" +
                "    <result/>\n" +
                "  </xsl:template>\n" +
                "</xsl:stylesheet>");

            String xslt = "<?xml version=\"1.0\"?>\n" +
                "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "  <xsl:import href=\"imported.xslt\"/>\n" +
                "  <xsl:template match=\"/\">\n" +
                "    <xsl:call-template name=\"imported\"/>\n" +
                "  </xsl:template>\n" +
                "</xsl:stylesheet>";
            File file = createTempFile("main.xslt", xslt);

            ValidationResult result = validator.validate(file);

            assertTrue("Import with existing file should pass", result.isValid());
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testImportWithMissingFile() {
        try {
            String xslt = "<?xml version=\"1.0\"?>\n" +
                "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "  <xsl:import href=\"nonexistent.xslt\"/>\n" +
                "  <xsl:template match=\"/\">\n" +
                "    <result/>\n" +
                "  </xsl:template>\n" +
                "</xsl:stylesheet>";
            File file = createTempFile("import-missing.xslt", xslt);

            ValidationResult result = validator.validate(file);

            // Should have warning about missing import
            assertTrue("Should have warnings about missing import",
                result.getWarningCount() > 0 || result.getErrorCount() > 0);
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    // ========================================================================
    // Validation Result Tests
    // ========================================================================

    @Test
    public void testValidationResultProperties() {
        try {
            File xslt = createSimpleXslt();
            ValidationResult result = validator.validate(xslt);

            assertNotNull("Result should not be null", result);
            assertNotNull("Validation type should not be null", result.getValidationType());
            assertTrue("Validation type should mention XSLT",
                result.getValidationType().contains("XSLT"));
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testValidationIssueProperties() {
        try {
            String invalidXslt = "<?xml version=\"1.0\"?>\n" +
                "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "  <xsl:template>\n" +  // Missing match and name
                "    <result/>\n" +
                "  </xsl:template>\n" +
                "</xsl:stylesheet>";
            File file = createTempFile("invalid.xslt", invalidXslt);

            ValidationResult result = validator.validate(file);

            assertFalse("Should have errors", result.isValid());
            ValidationIssue issue = result.getIssues().get(0);

            assertNotNull("Issue should not be null", issue);
            assertNotNull("Message should not be null", issue.getMessage());
            assertNotNull("Severity should not be null", issue.getSeverity());
            assertNotNull("Category should not be null", issue.getValidationCategory());
            assertEquals("Category should be XSLT", "XSLT", issue.getValidationCategory());
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testSuggestionPresence() {
        try {
            String invalidXslt = "<?xml version=\"1.0\"?>\n" +
                "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "  <xsl:template>\n" +  // Missing match and name
                "    <result/>\n" +
                "  </xsl:template>\n" +
                "</xsl:stylesheet>";
            File file = createTempFile("invalid.xslt", invalidXslt);

            ValidationResult result = validator.validate(file);

            assertFalse("Should have errors", result.isValid());

            // At least some issues should have suggestions
            boolean hasSuggestion = result.getIssues().stream().anyMatch(
                new java.util.function.Predicate<ValidationIssue>() {
                    public boolean test(ValidationIssue issue) {
                        return issue.getSuggestion() != null && !issue.getSuggestion().trim().isEmpty();
                    }
                }
            );

            assertTrue("At least one issue should have a suggestion", hasSuggestion);
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    // ========================================================================
    // Edge Case Tests
    // ========================================================================

    @Test
    public void testComplexXsltWithMultipleTemplates() {
        try {
            String xslt = "<?xml version=\"1.0\"?>\n" +
                "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "  <xsl:template match=\"/\">\n" +
                "    <orders>\n" +
                "      <xsl:apply-templates select=\"//order\"/>\n" +
                "    </orders>\n" +
                "  </xsl:template>\n" +
                "  <xsl:template match=\"order\">\n" +
                "    <order id=\"{@id}\">\n" +
                "      <xsl:value-of select=\"customer/name\"/>\n" +
                "    </order>\n" +
                "  </xsl:template>\n" +
                "  <xsl:template name=\"formatDate\">\n" +
                "    <xsl:param name=\"date\"/>\n" +
                "    <formatted>\n" +
                "      <xsl:value-of select=\"$date\"/>\n" +
                "    </formatted>\n" +
                "  </xsl:template>\n" +
                "</xsl:stylesheet>";
            File file = createTempFile("complex.xslt", xslt);

            ValidationResult result = validator.validate(file);

            assertTrue("Complex XSLT should pass", result.isValid());
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testXsltWithNamespace() {
        try {
            String xslt = "<?xml version=\"1.0\"?>\n" +
                "<xsl:stylesheet version=\"1.0\" \n" +
                "  xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\n" +
                "  xmlns:custom=\"http://custom.namespace.com\">\n" +
                "  <xsl:template match=\"/\">\n" +
                "    <xsl:value-of select=\"//custom:element\"/>\n" +
                "  </xsl:template>\n" +
                "</xsl:stylesheet>";
            File file = createTempFile("namespace.xslt", xslt);

            ValidationResult result = validator.validate(file);

            assertTrue("XSLT with namespace should pass", result.isValid());
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testMultipleValidations() {
        try {
            File xslt = createSimpleXslt();

            // Validate multiple times
            ValidationResult result1 = validator.validate(xslt);
            ValidationResult result2 = validator.validate(xslt);
            ValidationResult result3 = validator.validate(xslt);

            assertTrue("First validation should pass", result1.isValid());
            assertTrue("Second validation should pass", result2.isValid());
            assertTrue("Third validation should pass", result3.isValid());
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }
}
