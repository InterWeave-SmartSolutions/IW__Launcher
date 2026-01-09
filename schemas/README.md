# InterWeave Configuration Schemas

This directory contains XML Schema Definition (XSD) files for validating InterWeave IDE configuration files.

## Schema Files

### 1. ts-config.xsd
**Purpose:** Validates Transformation Server configuration files
**Location:** `workspace/{project}/configuration/ts/config.xml`
**Root Element:** `<TransformationServerConfiguration>`

**Key Attributes:**
- `Name` - Server instance identifier
- `LogLevel` - Logging verbosity (LOG_MINIMUM, LOG_NORMAL, LOG_VERBOSE, LOG_DEBUG)
- `IsPrimary` - Primary server flag (0 or 1)
- `IsTimeStamping` - Enable timestamp tracking (0 or 1)
- `IsCompiledMapping` - Use compiled XSLT mappings (0 or 1)
- `BufferSize` - Buffer size in KB (typically 1024)
- `IsHosted` - Hosted mode flag (0 or 1)

### 2. im-config.xsd
**Purpose:** Validates Integration Manager (Business Daemon) configuration files
**Location:** `workspace/{project}/configuration/im/config.xml`
**Root Element:** `<BusinessDaemonConfiguration>`

**Key Attributes:**
- `Name` - Integration manager instance identifier
- `HartbeatInterval` - Health check interval in milliseconds
- `RefreshInterval` - Configuration refresh interval in milliseconds
- `PrimaryTSURL` - Primary transformation server URL
- `SecondaryTSURL` - Secondary (failover) transformation server URL
- `IsPrimary` - Primary manager flag (0 or 1)
- `RunAtStartUp` - Auto-start flows flag (0 or 1)
- `BufferSize` - Buffer size in KB (typically 1024)
- `IsHosted` - Hosted mode flag (0 or 1)

**Database Configuration (when IsHosted="1"):**
- `hostedDriver` - JDBC driver class (e.g., "com.mysql.cj.jdbc.Driver")
- `hostedURL` - JDBC connection URL
- `hostedDBName` - Database name
- `hostedUser` - Database username
- `hostedPassword` - Database password

### 3. transactions.xsd
**Purpose:** Validates transaction mapping configuration files
**Location:** `workspace/{project}/xslt/Site/new/xml/transactions.xml`
**Root Element:** `<iwmappings>`

**Structure:**
- `<iwmappings>` - Root container for all mappings
  - `<mapping>` - Individual transaction mapping
    - `<description>` - Mapping description
    - `<source>` - Source system configuration
    - `<destination>` - Destination system configuration
    - `<fieldMappings>` - Field-level mappings
    - `<transformationRules>` - Business logic rules
    - `<filters>` - Data filtering conditions

## Usage

### Command Line Validation (xmllint)
```bash
# Validate Transformation Server config
xmllint --noout --schema schemas/ts-config.xsd workspace/MyProject/configuration/ts/config.xml

# Validate Integration Manager config
xmllint --noout --schema schemas/im-config.xsd workspace/MyProject/configuration/im/config.xml

# Validate transactions
xmllint --noout --schema schemas/transactions.xsd workspace/MyProject/xslt/Site/new/xml/transactions.xml
```

### Java Validation (using SchemaValidator)
```java
import com.interweave.validation.SchemaValidator;

// Validate TS config
ValidationResult result = SchemaValidator.validateFile(
    new File("workspace/MyProject/configuration/ts/config.xml"),
    new File("schemas/ts-config.xsd")
);

// Check results
if (!result.isValid()) {
    for (ValidationIssue issue : result.getIssues()) {
        System.err.println(issue.getMessage());
    }
}
```

### IDE Integration (Eclipse)
1. Open XML file in Eclipse
2. Right-click → Validate
3. Eclipse will use the XSD schema if properly referenced

To reference a schema in your XML file, add:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<TransformationServerConfiguration
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:noNamespaceSchemaLocation="../../../../../../schemas/ts-config.xsd"
    Name="TS1" ...>
```

## Common Validation Errors

### Missing Required Attributes
**Error:** `attribute 'Name' is required but missing`
**Resolution:** Add the required attribute to the configuration element

### Invalid Boolean Flag
**Error:** `value '2' is not valid for BooleanFlag`
**Resolution:** Use "0" or "1" for boolean attributes (0=false, 1=true)

### Invalid URL Format
**Error:** `value 'htp://invalid' does not match pattern`
**Resolution:** Ensure URLs start with "http://" or "https://", or leave empty ("")

### Invalid Log Level
**Error:** `value 'DEBUG' is not in enumeration`
**Resolution:** Use valid log level: LOG_MINIMUM, LOG_NORMAL, LOG_VERBOSE, or LOG_DEBUG

## Schema Versioning

Current version: **1.0** (created 2026-01-08)

These schemas are designed to validate existing InterWeave IDE configuration files.
They include extensive documentation annotations to help developers understand
each configuration option.

## Integration with Error Framework

These schemas are used by the SchemaValidator class (subtask 4.2) to provide:
- Design-time validation of configuration files
- Clear error messages with line/column numbers
- Suggestions for fixing invalid configurations
- Documentation links for configuration options

## Contributing

When updating schemas:
1. Maintain backward compatibility when possible
2. Add comprehensive `<xs:documentation>` annotations
3. Test against existing valid configuration files
4. Update this README with any new elements or attributes
5. Increment the version number in schema annotations
