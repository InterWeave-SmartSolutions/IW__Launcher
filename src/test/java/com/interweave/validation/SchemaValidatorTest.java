package com.interweave.validation;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Unit tests for SchemaValidator class.
 *
 * Tests comprehensive XML schema validation functionality including:
 * - Schema-based validation
 * - Error reporting with line and column numbers
 * - Auto-detection of schemas
 * - String content validation
 * - Edge case handling
 *
 * @author InterWeave Validation Framework Tests
 * @version 1.0
 */
public class SchemaValidatorTest {

    private SchemaValidator validator;
    private File tempXmlFile;
    private File tempSchemaFile;
    private File tempDir;

    @Before
    public void setUp() throws IOException {
        validator = new SchemaValidator();

        // Create temp directory for test files
        tempDir = new File(System.getProperty("java.io.tmpdir"), "schema-validator-test");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
    }

    @After
    public void tearDown() {
        // Clean up temp files
        if (tempXmlFile != null && tempXmlFile.exists()) {
            tempXmlFile.delete();
        }
        if (tempSchemaFile != null && tempSchemaFile.exists()) {
            tempSchemaFile.delete();
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

    private File createSimpleXml() throws IOException {
        String xmlContent = "<?xml version=\"1.0\"?>\n" +
            "<root>\n" +
            "  <element>value</element>\n" +
            "</root>";
        return createTempFile("test.xml", xmlContent);
    }

    private File createSimpleSchema() throws IOException {
        String schemaContent = "<?xml version=\"1.0\"?>\n" +
            "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
            "  <xs:element name=\"root\">\n" +
            "    <xs:complexType>\n" +
            "      <xs:sequence>\n" +
            "        <xs:element name=\"element\" type=\"xs:string\"/>\n" +
            "      </xs:sequence>\n" +
            "    </xs:complexType>\n" +
            "  </xs:element>\n" +
            "</xs:schema>";
        return createTempFile("test.xsd", schemaContent);
    }

    // ========================================================================
    // Constructor Tests
    // ========================================================================

    @Test
    public void testDefaultConstructor() {
        SchemaValidator v = new SchemaValidator();
        assertNotNull("Validator should not be null", v);
    }

    // ========================================================================
    // File Validation Tests
    // ========================================================================

    @Test
    public void testValidateWithNullXmlFile() {
        try {
            File schema = createSimpleSchema();
            ValidationResult result = validator.validate(null, schema);

            assertFalse("Null XML file should fail", result.isValid());
            assertTrue("Should have error about null file",
                result.getErrorCount() > 0);
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testValidateWithNullSchemaFile() {
        try {
            File xml = createSimpleXml();
            ValidationResult result = validator.validate(xml, null);

            assertFalse("Null schema file should fail", result.isValid());
            assertTrue("Should have error about null schema",
                result.getErrorCount() > 0);
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testValidateWithNonExistentXmlFile() {
        File nonExistent = new File(tempDir, "nonexistent.xml");
        try {
            File schema = createSimpleSchema();
            ValidationResult result = validator.validate(nonExistent, schema);

            assertFalse("Non-existent XML file should fail", result.isValid());
            assertTrue("Should have error about missing file",
                result.getErrorCount() > 0);
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testValidateWithNonExistentSchemaFile() {
        File nonExistent = new File(tempDir, "nonexistent.xsd");
        try {
            File xml = createSimpleXml();
            ValidationResult result = validator.validate(xml, nonExistent);

            assertFalse("Non-existent schema file should fail", result.isValid());
            assertTrue("Should have error about missing schema",
                result.getErrorCount() > 0);
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testValidateValidXmlAgainstSchema() {
        try {
            File xml = createSimpleXml();
            File schema = createSimpleSchema();

            ValidationResult result = validator.validate(xml, schema);

            assertTrue("Valid XML should pass schema validation", result.isValid());
            assertEquals("Should have no errors", 0, result.getErrorCount());
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testValidateInvalidXmlAgainstSchema() {
        try {
            // Create XML that doesn't match schema
            String invalidXml = "<?xml version=\"1.0\"?>\n" +
                "<root>\n" +
                "  <wrongElement>value</wrongElement>\n" +
                "</root>";
            File xml = createTempFile("invalid.xml", invalidXml);
            File schema = createSimpleSchema();

            ValidationResult result = validator.validate(xml, schema);

            assertFalse("Invalid XML should fail schema validation", result.isValid());
            assertTrue("Should have at least one error", result.getErrorCount() > 0);
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    // ========================================================================
    // String Content Validation Tests
    // ========================================================================

    @Test
    public void testValidateStringWithNullContent() {
        try {
            File schema = createSimpleSchema();
            ValidationResult result = validator.validateString(null, schema, "test.xml");

            assertFalse("Null content should fail", result.isValid());
            assertTrue("Should have error about null content",
                result.getErrorCount() > 0);
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testValidateStringWithEmptyContent() {
        try {
            File schema = createSimpleSchema();
            ValidationResult result = validator.validateString("", schema, "test.xml");

            assertFalse("Empty content should fail", result.isValid());
            assertTrue("Should have error about empty content",
                result.getErrorCount() > 0);
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testValidateStringWithValidContent() {
        try {
            String xmlContent = "<?xml version=\"1.0\"?>\n" +
                "<root>\n" +
                "  <element>value</element>\n" +
                "</root>";
            File schema = createSimpleSchema();

            ValidationResult result = validator.validateString(xmlContent, schema, "test.xml");

            assertTrue("Valid XML string should pass", result.isValid());
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testValidateStringWithInvalidContent() {
        try {
            String invalidXml = "<?xml version=\"1.0\"?>\n" +
                "<root>\n" +
                "  <invalidElement>value</invalidElement>\n" +
                "</root>";
            File schema = createSimpleSchema();

            ValidationResult result = validator.validateString(invalidXml, schema, "test.xml");

            assertFalse("Invalid XML string should fail", result.isValid());
            assertTrue("Should have errors", result.getErrorCount() > 0);
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testValidateStringWithNullFilePath() {
        try {
            String xmlContent = "<?xml version=\"1.0\"?>\n" +
                "<root>\n" +
                "  <element>value</element>\n" +
                "</root>";
            File schema = createSimpleSchema();

            ValidationResult result = validator.validateString(xmlContent, schema, null);

            assertTrue("Valid XML should pass regardless of file path", result.isValid());
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    // ========================================================================
    // Auto-Detection Tests
    // ========================================================================

    @Test
    public void testValidateWithAutoDetectNullFile() {
        ValidationResult result = validator.validateWithAutoDetect(null);

        assertFalse("Null file should fail", result.isValid());
        assertTrue("Should have error", result.getErrorCount() > 0);
    }

    @Test
    public void testValidateWithAutoDetectNonExistentFile() {
        File nonExistent = new File(tempDir, "nonexistent.xml");
        ValidationResult result = validator.validateWithAutoDetect(nonExistent);

        assertFalse("Non-existent file should fail", result.isValid());
        assertTrue("Should have error", result.getErrorCount() > 0);
    }

    @Test
    public void testValidateWithAutoDetectUnregisteredRootElement() {
        try {
            String xmlContent = "<?xml version=\"1.0\"?>\n" +
                "<UnregisteredRootElement>\n" +
                "  <element>value</element>\n" +
                "</UnregisteredRootElement>";
            File xml = createTempFile("unregistered.xml", xmlContent);

            ValidationResult result = validator.validateWithAutoDetect(xml);

            // Should have warning about no registered schema
            assertTrue("Should have issues", result.getIssueCount() > 0);
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    // ========================================================================
    // Schema Registration Tests
    // ========================================================================

    @Test
    public void testRegisterSchema() {
        validator.registerSchema("CustomRoot", "schemas/custom.xsd");

        // Registration should not throw exception
        assertTrue("Schema registration should succeed", true);
    }

    @Test
    public void testUnregisterSchema() {
        validator.registerSchema("CustomRoot", "schemas/custom.xsd");
        validator.unregisterSchema("CustomRoot");

        // Unregistration should not throw exception
        assertTrue("Schema unregistration should succeed", true);
    }

    @Test
    public void testClearSchemaCache() {
        validator.clearSchemaCache();

        // Cache clearing should not throw exception
        assertTrue("Schema cache clear should succeed", true);
    }

    @Test
    public void testRegisterNullSchemaElement() {
        validator.registerSchema(null, "schemas/test.xsd");

        // Should handle gracefully
        assertTrue("Should handle null element gracefully", true);
    }

    @Test
    public void testRegisterNullSchemaPath() {
        validator.registerSchema("TestElement", null);

        // Should handle gracefully
        assertTrue("Should handle null path gracefully", true);
    }

    // ========================================================================
    // Error Reporting Tests
    // ========================================================================

    @Test
    public void testErrorReportingWithMissingAttribute() {
        try {
            // Create schema requiring an attribute
            String schemaContent = "<?xml version=\"1.0\"?>\n" +
                "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "  <xs:element name=\"root\">\n" +
                "    <xs:complexType>\n" +
                "      <xs:attribute name=\"required\" type=\"xs:string\" use=\"required\"/>\n" +
                "    </xs:complexType>\n" +
                "  </xs:element>\n" +
                "</xs:schema>";
            File schema = createTempFile("required-attr.xsd", schemaContent);

            // Create XML without the required attribute
            String xmlContent = "<?xml version=\"1.0\"?>\n" +
                "<root/>";
            File xml = createTempFile("missing-attr.xml", xmlContent);

            ValidationResult result = validator.validate(xml, schema);

            assertFalse("Should fail validation", result.isValid());
            assertTrue("Should have error about missing attribute",
                result.getIssues().stream().anyMatch(new java.util.function.Predicate<ValidationIssue>() {
                    public boolean test(ValidationIssue issue) {
                        return issue.getMessage().toLowerCase().contains("attribute") ||
                               issue.getMessage().toLowerCase().contains("required");
                    }
                }));
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testErrorReportingWithInvalidDataType() {
        try {
            // Create schema with integer type
            String schemaContent = "<?xml version=\"1.0\"?>\n" +
                "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "  <xs:element name=\"root\">\n" +
                "    <xs:complexType>\n" +
                "      <xs:sequence>\n" +
                "        <xs:element name=\"number\" type=\"xs:integer\"/>\n" +
                "      </xs:sequence>\n" +
                "    </xs:complexType>\n" +
                "  </xs:element>\n" +
                "</xs:schema>";
            File schema = createTempFile("integer.xsd", schemaContent);

            // Create XML with non-integer value
            String xmlContent = "<?xml version=\"1.0\"?>\n" +
                "<root>\n" +
                "  <number>not-a-number</number>\n" +
                "</root>";
            File xml = createTempFile("invalid-number.xml", xmlContent);

            ValidationResult result = validator.validate(xml, schema);

            assertFalse("Should fail validation", result.isValid());
            assertTrue("Should have errors", result.getErrorCount() > 0);
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
            File xml = createSimpleXml();
            File schema = createSimpleSchema();

            ValidationResult result = validator.validate(xml, schema);

            assertNotNull("Result should not be null", result);
            assertNotNull("Validation type should not be null", result.getValidationType());
            assertTrue("Validation type should mention Schema",
                result.getValidationType().contains("Schema"));
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testValidationIssueProperties() {
        try {
            String invalidXml = "<?xml version=\"1.0\"?>\n" +
                "<root>\n" +
                "  <wrongElement>value</wrongElement>\n" +
                "</root>";
            File xml = createTempFile("invalid.xml", invalidXml);
            File schema = createSimpleSchema();

            ValidationResult result = validator.validate(xml, schema);

            assertFalse("Should have errors", result.isValid());
            ValidationIssue issue = result.getIssues().get(0);

            assertNotNull("Issue should not be null", issue);
            assertNotNull("Message should not be null", issue.getMessage());
            assertNotNull("Severity should not be null", issue.getSeverity());
            assertNotNull("Category should not be null", issue.getValidationCategory());
            assertEquals("Category should be XML Schema", "XML Schema", issue.getValidationCategory());
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testSuggestionPresence() {
        try {
            String invalidXml = "<?xml version=\"1.0\"?>\n" +
                "<root>\n" +
                "  <wrongElement>value</wrongElement>\n" +
                "</root>";
            File xml = createTempFile("invalid.xml", invalidXml);
            File schema = createSimpleSchema();

            ValidationResult result = validator.validate(xml, schema);

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
    public void testValidateMalformedXml() {
        try {
            String malformedXml = "<?xml version=\"1.0\"?>\n" +
                "<root>\n" +
                "  <element>value\n" +  // Missing closing tag
                "</root>";
            File xml = createTempFile("malformed.xml", malformedXml);
            File schema = createSimpleSchema();

            ValidationResult result = validator.validate(xml, schema);

            assertFalse("Malformed XML should fail", result.isValid());
            assertTrue("Should have errors", result.getErrorCount() > 0);
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testValidateComplexSchema() {
        try {
            // Create more complex schema
            String complexSchema = "<?xml version=\"1.0\"?>\n" +
                "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "  <xs:element name=\"order\">\n" +
                "    <xs:complexType>\n" +
                "      <xs:sequence>\n" +
                "        <xs:element name=\"customer\" type=\"xs:string\"/>\n" +
                "        <xs:element name=\"items\">\n" +
                "          <xs:complexType>\n" +
                "            <xs:sequence>\n" +
                "              <xs:element name=\"item\" maxOccurs=\"unbounded\">\n" +
                "                <xs:complexType>\n" +
                "                  <xs:sequence>\n" +
                "                    <xs:element name=\"name\" type=\"xs:string\"/>\n" +
                "                    <xs:element name=\"price\" type=\"xs:decimal\"/>\n" +
                "                  </xs:sequence>\n" +
                "                </xs:complexType>\n" +
                "              </xs:element>\n" +
                "            </xs:sequence>\n" +
                "          </xs:complexType>\n" +
                "        </xs:element>\n" +
                "      </xs:sequence>\n" +
                "    </xs:complexType>\n" +
                "  </xs:element>\n" +
                "</xs:schema>";
            File schema = createTempFile("complex.xsd", complexSchema);

            String validXml = "<?xml version=\"1.0\"?>\n" +
                "<order>\n" +
                "  <customer>John Doe</customer>\n" +
                "  <items>\n" +
                "    <item>\n" +
                "      <name>Product 1</name>\n" +
                "      <price>19.99</price>\n" +
                "    </item>\n" +
                "    <item>\n" +
                "      <name>Product 2</name>\n" +
                "      <price>29.99</price>\n" +
                "    </item>\n" +
                "  </items>\n" +
                "</order>";
            File xml = createTempFile("complex.xml", validXml);

            ValidationResult result = validator.validate(xml, schema);

            assertTrue("Valid complex XML should pass", result.isValid());
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }

    @Test
    public void testMultipleValidationsCaching() {
        try {
            File xml = createSimpleXml();
            File schema = createSimpleSchema();

            // Validate multiple times to test caching
            ValidationResult result1 = validator.validate(xml, schema);
            ValidationResult result2 = validator.validate(xml, schema);
            ValidationResult result3 = validator.validate(xml, schema);

            assertTrue("First validation should pass", result1.isValid());
            assertTrue("Second validation should pass", result2.isValid());
            assertTrue("Third validation should pass", result3.isValid());
        } catch (IOException e) {
            fail("IOException during test setup: " + e.getMessage());
        }
    }
}
