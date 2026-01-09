# InterWeave Error Codes Reference

This document provides a comprehensive reference for all error codes in the InterWeave IDE platform. Each error code includes its category, severity, description, common causes, and resolution steps.

## Quick Reference

| Category | Code Range | Description |
|----------|------------|-------------|
| AUTH | AUTH001-AUTH099 | Authentication and authorization errors |
| DB | DB001-DB099 | Database connection and query errors |
| FLOW | FLOW001-FLOW099 | Integration flow execution errors |
| CONFIG | CONFIG001-CONFIG099 | Configuration validation and loading errors |
| VALIDATION | VALIDATION001-VALIDATION099 | Data validation errors |
| XPATH | XPATH001-XPATH099 | XPath expression and XSLT transformation errors |
| CONNECTION | CONNECTION001-CONNECTION099 | External system connection errors |

---

## Authentication/Authorization Errors

### AUTH001 - Invalid Email or Password

**Severity:** ERROR
**Category:** Authentication/Authorization

**Description:**
The provided email address or password is incorrect.

**Common Causes:**
- Incorrect email address
- Wrong password (passwords are case-sensitive)
- Account not yet activated
- Typo in credentials

**Resolution Steps:**
1. Verify email address is correct
2. Check password for typos (password is case-sensitive)
3. If forgotten, contact your administrator to reset password
4. Ensure account has been activated

---

### AUTH002 - User Account is Inactive

**Severity:** ERROR
**Category:** Authentication/Authorization

**Description:**
The user account exists but is not active in the system.

**Common Causes:**
- Account has been deactivated by administrator
- Account activation not completed
- User registration pending approval

**Resolution Steps:**
1. Contact your company administrator to activate your account
2. Check if account activation email was received
3. Verify your user status in the company user management portal

---

### AUTH003 - Company Account is Inactive

**Severity:** ERROR
**Category:** Authentication/Authorization

**Description:**
The company account is inactive, suspended, or expired.

**Common Causes:**
- Company license has expired
- Account suspended due to non-payment
- Company account deactivated

**Resolution Steps:**
1. Contact InterWeave support to verify company account status
2. Check if company license has expired
3. Verify payment and subscription status

---

### AUTH004 - Session Has Expired

**Severity:** WARNING
**Category:** Authentication/Authorization

**Description:**
The user session has expired due to inactivity or timeout.

**Common Causes:**
- Session timeout due to inactivity
- Browser cookies disabled or cleared
- Server restart or session store cleared

**Resolution Steps:**
1. Log in again to create a new session
2. Ensure browser cookies are enabled
3. Check session timeout settings in company configuration

---

### AUTH005 - Access Denied

**Severity:** ERROR
**Category:** Authentication/Authorization

**Description:**
The user does not have permission to access the requested resource.

**Common Causes:**
- Insufficient user permissions
- Resource requires higher privilege level
- Role-based access control restriction

**Resolution Steps:**
1. Contact your administrator to request necessary permissions
2. Verify you are accessing the correct resource
3. Check your user role and permission assignments

---

### AUTH006 - Authentication Credentials Required

**Severity:** ERROR
**Category:** Authentication/Authorization

**Description:**
Authentication credentials are missing from the request.

**Common Causes:**
- Not logged in
- API request missing authentication token
- Session cookie not sent

**Resolution Steps:**
1. Ensure you are logged in before accessing this resource
2. Include authentication token in API requests
3. Check that session has not expired

---

### AUTH007 - Invalid or Expired Token

**Severity:** ERROR
**Category:** Authentication/Authorization

**Description:**
The provided authentication token is invalid or has expired.

**Common Causes:**
- Token has expired (time-based)
- Token format is incorrect
- Token was revoked
- System time mismatch

**Resolution Steps:**
1. Log in again to obtain a new authentication token
2. Verify token format and encoding
3. Check system time synchronization (tokens may be time-based)

---

## Database Errors

### DB001 - Cannot Connect to Database

**Severity:** ERROR
**Category:** Database

**Description:**
The application cannot establish a connection to the database server.

**Common Causes:**
- Database server is down
- Network connectivity issues
- Incorrect connection credentials
- Firewall blocking connection
- Wrong database host or port

**Resolution Steps:**
1. Verify database server is running and accessible
2. Check network connectivity to database host
3. Verify database credentials in configuration
4. Check firewall rules allow database connections
5. Review database connection settings in .env file

---

### DB002 - Database Query Failed

**Severity:** ERROR
**Category:** Database

**Description:**
A SQL query execution failed.

**Common Causes:**
- SQL syntax error
- Table or column does not exist
- Insufficient database permissions
- Data type mismatch
- Constraint violation

**Resolution Steps:**
1. Review the SQL query for syntax errors
2. Verify table and column names exist in schema
3. Check database user permissions
4. Review full error message and stack trace in logs
5. Verify data types match schema definitions

---

### DB003 - Database Constraint Violation

**Severity:** ERROR
**Category:** Database

**Description:**
A database constraint was violated (unique key, foreign key, check constraint, etc.).

**Common Causes:**
- Duplicate primary key or unique key
- Foreign key reference to non-existent record
- Check constraint failed
- NOT NULL constraint violated

**Resolution Steps:**
1. Check for duplicate key violations (unique constraint)
2. Verify foreign key relationships are valid
3. Review constraint definitions in database schema
4. Ensure required fields are populated
5. Check data integrity rules

---

### DB004 - Database Connection Pool Exhausted

**Severity:** ERROR
**Category:** Database

**Description:**
All available database connections in the pool are in use.

**Common Causes:**
- Too many concurrent requests
- Connection leaks (connections not properly closed)
- Pool size too small for load
- Long-running queries blocking connections

**Resolution Steps:**
1. Increase database connection pool size
2. Check for connection leaks (unclosed connections)
3. Review application connection usage patterns
4. Monitor database server load
5. Consider scaling database resources

---

### DB005 - Database Transaction Rolled Back

**Severity:** WARNING
**Category:** Database

**Description:**
A database transaction was rolled back.

**Common Causes:**
- Data integrity constraint violation
- Deadlock detected
- Application logic triggered rollback
- Timeout during transaction

**Resolution Steps:**
1. Review transaction logs for error details
2. Check data consistency requirements
3. Verify business logic constraints
4. Consider retry logic for transient failures
5. Review transaction isolation levels

---

### DB006 - Database Schema Mismatch

**Severity:** ERROR
**Category:** Database

**Description:**
The database schema does not match the expected structure.

**Common Causes:**
- Database migration not applied
- Table or column renamed or deleted
- Application version doesn't match database version
- Wrong database selected

**Resolution Steps:**
1. Verify database schema is up to date
2. Run database migrations if pending
3. Check table and column names for typos
4. Review schema documentation
5. Ensure application version matches database schema version

---

### DB007 - Database Operation Timed Out

**Severity:** ERROR
**Category:** Database

**Description:**
A database operation took too long and exceeded the timeout limit.

**Common Causes:**
- Slow query performance
- Database server overload
- Missing indexes
- Large dataset operations
- Network latency

**Resolution Steps:**
1. Optimize slow database queries
2. Increase query timeout settings if appropriate
3. Check database server performance and load
4. Review query execution plans
5. Consider adding database indexes

---

## Integration Flow Errors

### FLOW001 - Flow Configuration File Not Found

**Severity:** ERROR
**Category:** Integration Flow

**Description:**
The integration flow configuration file could not be found.

**Common Causes:**
- File does not exist at specified path
- Incorrect file path in configuration
- File was deleted or moved
- Workspace project not loaded

**Resolution Steps:**
1. Verify flow configuration file exists in workspace project
2. Check file path and name for typos
3. Ensure project is properly loaded in IDE
4. Review workspace directory structure
5. Check file permissions

---

### FLOW002 - Flow Execution Failed

**Severity:** ERROR
**Category:** Integration Flow

**Description:**
The integration flow failed during execution.

**Common Causes:**
- Invalid input data
- Missing connection configuration
- Transformation error
- External system error
- Runtime exception in flow logic

**Resolution Steps:**
1. Review flow execution logs for detailed error
2. Check all required connections are configured
3. Verify transformation mappings are correct
4. Test individual flow components
5. Check input data format and validity

---

### FLOW003 - Required Flow Component Missing

**Severity:** ERROR
**Category:** Integration Flow

**Description:**
A required component referenced in the flow is missing.

**Common Causes:**
- Component not defined in project
- Component name mismatch
- Required plugin not installed
- Component deleted but still referenced

**Resolution Steps:**
1. Review flow configuration for missing components
2. Check that all referenced components are defined
3. Verify component names match exactly (case-sensitive)
4. Ensure required plugins are installed
5. Review flow dependencies

---

### FLOW004 - Flow Contains Circular References

**Severity:** ERROR
**Category:** Integration Flow

**Description:**
The flow configuration contains circular dependencies.

**Common Causes:**
- Flow A calls Flow B which calls Flow A
- Transformation references itself
- Circular template includes

**Resolution Steps:**
1. Review flow diagram for circular dependencies
2. Restructure flow to eliminate circular references
3. Check transformation chains for loops
4. Use validation service to detect circular references
5. Consult flow design best practices documentation

---

### FLOW005 - Invalid Flow State Transition

**Severity:** ERROR
**Category:** Integration Flow

**Description:**
An attempt was made to transition the flow to an invalid state.

**Common Causes:**
- State transition not allowed by flow definition
- Prerequisites for transition not met
- Concurrent state modification
- Invalid flow state machine configuration

**Resolution Steps:**
1. Review allowed state transitions in flow documentation
2. Check current flow state
3. Verify transition prerequisites are met
4. Review flow state machine configuration
5. Check for concurrent state modifications

---

### FLOW006 - Data Transformation Failed

**Severity:** ERROR
**Category:** Integration Flow

**Description:**
Data transformation failed during flow execution.

**Common Causes:**
- XSLT syntax error
- Invalid input XML structure
- XPath evaluation error
- Missing transformation parameter
- Unsupported data format

**Resolution Steps:**
1. Review XSLT transformation file for errors
2. Verify input XML structure matches expected format
3. Check XPath expressions in transformation
4. Test transformation with sample data
5. Review transformation logs for detailed error

---

### FLOW007 - Flow References Undefined Connection

**Severity:** ERROR
**Category:** Integration Flow

**Description:**
The flow references a connection that is not defined.

**Common Causes:**
- Connection not created in project
- Connection name mismatch (case-sensitive)
- Connection deleted but still referenced
- Connection in different project

**Resolution Steps:**
1. Check that referenced connection is defined in project
2. Verify connection name matches exactly (case-sensitive)
3. Review connection configuration
4. Use validation service to detect missing connections
5. Check connection is not disabled or archived

---

### FLOW008 - Flow Transaction Timed Out

**Severity:** WARNING
**Category:** Integration Flow

**Description:**
The flow transaction exceeded the maximum allowed execution time.

**Common Causes:**
- Slow external system response
- Large data volume processing
- Complex transformation logic
- Network latency
- Insufficient timeout setting

**Resolution Steps:**
1. Increase transaction timeout setting if appropriate
2. Optimize flow performance
3. Check external system response times
4. Review flow complexity and processing time
5. Consider breaking into smaller transactions

---

## Configuration Errors

### CONFIG001 - Invalid XML Syntax

**Severity:** ERROR
**Category:** Configuration

**Description:**
The configuration file contains invalid XML syntax.

**Common Causes:**
- Unclosed XML tags
- Missing or extra brackets/quotes
- Invalid XML characters
- Malformed XML declaration
- Invalid attribute syntax

**Resolution Steps:**
1. Validate XML syntax using XML editor or validator
2. Check for unclosed tags or missing brackets
3. Verify XML special characters are properly escaped
4. Ensure XML declaration is present and correct
5. Review error line number for specific syntax issue

---

### CONFIG002 - Required Configuration Element Missing

**Severity:** ERROR
**Category:** Configuration

**Description:**
A required configuration element is missing from the configuration file.

**Common Causes:**
- Element not present in file
- Element name typo
- Element in wrong location
- Configuration file incomplete

**Resolution Steps:**
1. Review schema documentation for required elements
2. Add missing configuration elements
3. Check element names for typos
4. Verify configuration file structure
5. Use configuration template as reference

---

### CONFIG003 - Configuration Element Has Invalid Value

**Severity:** ERROR
**Category:** Configuration

**Description:**
A configuration element contains an invalid value.

**Common Causes:**
- Wrong data type
- Value out of allowed range
- Invalid format (date, URL, etc.)
- Invalid enumeration value
- Special characters in value

**Resolution Steps:**
1. Review allowed values for configuration element
2. Check data type (string, number, boolean, etc.)
3. Verify value format (date, URL, etc.)
4. Check for valid enumeration values
5. Consult configuration documentation

---

### CONFIG004 - Cannot Load Configuration File

**Severity:** ERROR
**Category:** Configuration

**Description:**
The configuration file could not be loaded.

**Common Causes:**
- File does not exist
- Insufficient file permissions
- File locked by another process
- Invalid file path
- File encoding issue

**Resolution Steps:**
1. Verify configuration file exists at expected location
2. Check file path and name
3. Verify file permissions allow reading
4. Check file encoding (should be UTF-8)
5. Review file system access logs

---

### CONFIG005 - Configuration Validation Failed

**Severity:** ERROR
**Category:** Configuration

**Description:**
The configuration file failed validation against schema or business rules.

**Common Causes:**
- Schema validation error
- Missing required fields
- Invalid field combinations
- Business rule violation
- Incompatible configuration values

**Resolution Steps:**
1. Review validation errors in detail
2. Use schema validator to identify specific issues
3. Check configuration against schema definition
4. Verify all required fields are present
5. Test configuration in validation mode

---

### CONFIG006 - Invalid Configuration File Path

**Severity:** ERROR
**Category:** Configuration

**Description:**
The specified configuration file path is invalid.

**Common Causes:**
- Malformed path
- Invalid path separators
- Relative path used when absolute required
- Path contains invalid characters
- Path too long

**Resolution Steps:**
1. Verify file path format is correct
2. Use absolute or relative paths consistently
3. Check path separators (forward vs backward slash)
4. Verify directory structure exists
5. Check for special characters in path

---

### CONFIG007 - Duplicate Configuration Element

**Severity:** WARNING
**Category:** Configuration

**Description:**
A configuration element is defined multiple times.

**Common Causes:**
- Copy-paste error
- Configuration merge conflict
- Multiple configuration sources
- Element defined in included file

**Resolution Steps:**
1. Remove duplicate configuration element
2. Merge duplicate definitions if both needed
3. Check configuration merge logic
4. Verify element uniqueness constraints
5. Review configuration file for copy-paste errors

---

## Validation Errors

### VALIDATION001 - Required Field Missing

**Severity:** ERROR
**Category:** Validation

**Description:**
A required field is missing or empty.

**Common Causes:**
- Field not filled in form
- Empty string provided
- Field hidden or disabled
- Form submission error

**Resolution Steps:**
1. Provide value for required field
2. Check field name in documentation
3. Verify field is not hidden or disabled
4. Review form validation rules
5. Check for client-side validation errors

---

### VALIDATION002 - Invalid Field Format

**Severity:** ERROR
**Category:** Validation

**Description:**
A field value does not match the expected format.

**Common Causes:**
- Wrong data format
- Invalid characters
- Encoding issues
- Missing format components
- Extra whitespace

**Resolution Steps:**
1. Check expected format in documentation
2. Review format examples
3. Verify special characters and encoding
4. Check data type (string, number, date, etc.)
5. Use format validation tools

---

### VALIDATION003 - Field Value Out of Range

**Severity:** ERROR
**Category:** Validation

**Description:**
A field value is outside the allowed range.

**Common Causes:**
- Number too large or too small
- Value below minimum
- Value above maximum
- Invalid business logic value

**Resolution Steps:**
1. Check minimum and maximum allowed values
2. Review range constraints in documentation
3. Verify value is within business rules
4. Check for numeric overflow
5. Ensure value makes sense in context

---

### VALIDATION004 - Invalid Email Address Format

**Severity:** ERROR
**Category:** Validation

**Description:**
The email address format is invalid.

**Common Causes:**
- Missing @ symbol
- Invalid domain
- Missing TLD (top-level domain)
- Extra spaces
- Invalid characters

**Resolution Steps:**
1. Verify email format: user@domain.com
2. Check for typos in email address
3. Ensure @ symbol is present
4. Verify domain has valid TLD
5. Remove extra spaces or special characters

---

### VALIDATION005 - Invalid URL Format

**Severity:** ERROR
**Category:** Validation

**Description:**
The URL format is invalid.

**Common Causes:**
- Missing protocol (http:// or https://)
- Invalid domain name
- Invalid URL characters
- Malformed URL structure
- Missing host name

**Resolution Steps:**
1. Verify URL format: http://domain.com or https://domain.com
2. Include protocol (http:// or https://)
3. Check for typos in URL
4. Verify domain name is valid
5. Test URL in browser

---

### VALIDATION006 - Invalid Date/Time Format

**Severity:** ERROR
**Category:** Validation

**Description:**
The date or time format is invalid.

**Common Causes:**
- Wrong date format
- Invalid date (e.g., February 30)
- Missing date components
- Timezone format error
- Invalid time value

**Resolution Steps:**
1. Check expected date/time format in documentation
2. Use ISO-8601 format: YYYY-MM-DD or YYYY-MM-DDTHH:mm:ss
3. Verify date is valid (e.g., not February 30)
4. Check timezone handling
5. Use date picker if available

---

### VALIDATION007 - Field Length Exceeds Maximum

**Severity:** ERROR
**Category:** Validation

**Description:**
The field value length exceeds the maximum allowed.

**Common Causes:**
- Text too long
- Copy-paste of large content
- Database field size constraint
- Business rule limit

**Resolution Steps:**
1. Check maximum length constraint
2. Shorten field value
3. Review length requirements in documentation
4. Check for accidental extra content
5. Use text truncation if appropriate

---

### VALIDATION008 - Field Value Does Not Match Required Pattern

**Severity:** ERROR
**Category:** Validation

**Description:**
The field value does not match the required pattern or regex.

**Common Causes:**
- Format mismatch
- Invalid characters
- Missing pattern components
- Case sensitivity issue
- Extra whitespace

**Resolution Steps:**
1. Review required pattern in documentation
2. Check pattern examples
3. Verify value matches regex pattern
4. Look for typos or format errors
5. Use pattern validation tools

---

## XPath/XSLT Errors

### XPATH001 - XPath Expression Syntax Error

**Severity:** ERROR
**Category:** XPath/XSLT

**Description:**
The XPath expression contains a syntax error.

**Common Causes:**
- Unbalanced parentheses or brackets
- Invalid operator usage
- Typo in function name
- Invalid path syntax
- Missing quotes

**Resolution Steps:**
1. Validate XPath syntax using XPath evaluator
2. Check for unbalanced parentheses or brackets
3. Verify function names are correct
4. Check operator usage (/, //, ., @, etc.)
5. Review XPath syntax documentation

---

### XPATH002 - Undefined Namespace Prefix

**Severity:** ERROR
**Category:** XPath/XSLT

**Description:**
An XPath expression uses a namespace prefix that is not defined.

**Common Causes:**
- Namespace not declared
- Typo in namespace prefix
- Namespace declaration in wrong location
- Missing namespace URI

**Resolution Steps:**
1. Define namespace prefix in XSLT file
2. Check namespace declarations at top of file
3. Verify namespace URI matches XML document
4. Use correct prefix in XPath expression
5. Review namespace handling documentation

---

### XPATH003 - XSLT Template Not Found

**Severity:** ERROR
**Category:** XPath/XSLT

**Description:**
A referenced XSLT template could not be found.

**Common Causes:**
- Template not defined in XSLT file
- Template name mismatch
- Template in different file not imported
- Template commented out
- Case sensitivity issue

**Resolution Steps:**
1. Verify template name exists in XSLT file
2. Check template name spelling (case-sensitive)
3. Ensure template is not commented out
4. Review template definitions in XSLT
5. Check template import/include statements

---

### XPATH004 - XSLT Transformation Failed

**Severity:** ERROR
**Category:** XPath/XSLT

**Description:**
An XSLT transformation failed during execution.

**Common Causes:**
- XSLT syntax error
- Invalid input XML
- XPath evaluation error
- Missing template or function
- Runtime exception

**Resolution Steps:**
1. Review XSLT file for syntax errors
2. Check input XML structure matches expected format
3. Verify XPath expressions in transformation
4. Test with sample input data
5. Review transformation logs for detailed error

---

### XPATH005 - XPath Expression Returned No Results

**Severity:** WARNING
**Category:** XPath/XSLT

**Description:**
An XPath expression evaluation returned no results.

**Common Causes:**
- Path does not exist in XML document
- Context node incorrect
- XPath expression too specific
- Element is optional and missing
- Namespace mismatch

**Resolution Steps:**
1. Verify XPath expression is correct
2. Check input XML contains expected elements
3. Review XPath context node
4. Test XPath with sample XML
5. Consider using conditional logic for optional elements

---

### XPATH006 - Invalid XPath Function Call

**Severity:** ERROR
**Category:** XPath/XSLT

**Description:**
An XPath function is called with invalid arguments.

**Common Causes:**
- Wrong number of arguments
- Invalid argument type
- Function name typo
- Function not supported in XPath version
- Missing function namespace

**Resolution Steps:**
1. Verify function name is correct
2. Check number and types of function arguments
3. Review XPath function documentation
4. Verify function is supported in XPath version
5. Check for typos in function name

---

### XPATH007 - Invalid XSLT Syntax

**Severity:** ERROR
**Category:** XPath/XSLT

**Description:**
The XSLT file contains invalid syntax.

**Common Causes:**
- Malformed XML in XSLT
- Invalid XSLT elements
- Missing namespace declaration
- Incorrect attribute usage
- Unclosed tags

**Resolution Steps:**
1. Validate XSLT file syntax
2. Check XSLT namespace declaration
3. Verify well-formed XML structure
4. Review XSLT elements and attributes
5. Use XSLT validator tool

---

### XPATH008 - Undefined XPath Variable

**Severity:** ERROR
**Category:** XPath/XSLT

**Description:**
An XPath expression references an undefined variable.

**Common Causes:**
- Variable not declared
- Variable name typo
- Variable out of scope
- Variable defined after use
- Case sensitivity issue

**Resolution Steps:**
1. Define XPath variable before use
2. Check variable name spelling (case-sensitive)
3. Verify variable scope
4. Review variable declarations in XSLT
5. Check variable is not defined in unreachable scope

---

## Connection Errors

### CONNECTION001 - Cannot Connect to External System

**Severity:** ERROR
**Category:** Connection

**Description:**
Cannot establish connection to external system.

**Common Causes:**
- External system is down
- Network connectivity issue
- Incorrect endpoint URL
- Firewall blocking connection
- DNS resolution failure

**Resolution Steps:**
1. Verify external system is online and accessible
2. Check network connectivity
3. Verify connection URL/endpoint is correct
4. Test connection with ping or curl
5. Check firewall and proxy settings

---

### CONNECTION002 - Connection Timed Out

**Severity:** ERROR
**Category:** Connection

**Description:**
Connection attempt to external system timed out.

**Common Causes:**
- External system slow to respond
- Network latency
- Timeout setting too short
- External system overloaded
- Network congestion

**Resolution Steps:**
1. Check external system response time
2. Increase timeout setting if appropriate
3. Verify network latency is acceptable
4. Check for network congestion
5. Contact external system administrator

---

### CONNECTION003 - Invalid Connection Configuration

**Severity:** ERROR
**Category:** Connection

**Description:**
The connection configuration is invalid or incomplete.

**Common Causes:**
- Missing required parameters
- Invalid parameter values
- Incorrect configuration format
- Configuration schema violation
- Incompatible parameter combination

**Resolution Steps:**
1. Review connection configuration settings
2. Verify all required parameters are set
3. Check parameter values and formats
4. Consult connection setup documentation
5. Use connection configuration template

---

### CONNECTION004 - Required Connection Parameter Missing

**Severity:** ERROR
**Category:** Connection

**Description:**
A required connection parameter is missing.

**Common Causes:**
- Parameter not set in configuration
- Parameter name mismatch
- Configuration incomplete
- Default value not available

**Resolution Steps:**
1. Check which parameters are required
2. Add missing connection parameter
3. Verify parameter name is correct
4. Review connection type documentation
5. Check parameter is not hidden or conditional

---

### CONNECTION005 - Network Error

**Severity:** ERROR
**Category:** Connection

**Description:**
A network error occurred during communication.

**Common Causes:**
- Network outage
- DNS failure
- Routing issue
- Network interface down
- ISP problem

**Resolution Steps:**
1. Check network connectivity
2. Verify DNS resolution
3. Check for network outages
4. Review network logs
5. Contact network administrator

---

### CONNECTION006 - SSL/TLS Certificate Error

**Severity:** ERROR
**Category:** Connection

**Description:**
SSL/TLS certificate validation failed.

**Common Causes:**
- Certificate expired
- Certificate not trusted
- Certificate hostname mismatch
- Self-signed certificate
- Incomplete certificate chain

**Resolution Steps:**
1. Verify SSL/TLS certificate is valid
2. Check certificate expiration date
3. Install or update trusted certificates
4. Verify certificate chain
5. Contact external system administrator for certificate info

---

### CONNECTION007 - Authentication Failed with External System

**Severity:** ERROR
**Category:** Connection

**Description:**
Authentication with external system failed.

**Common Causes:**
- Invalid credentials
- Credentials expired
- Wrong authentication method
- API key revoked
- Account locked

**Resolution Steps:**
1. Verify authentication credentials are correct
2. Check username/password or API key
3. Verify authentication method matches external system
4. Check if credentials have expired
5. Review external system authentication documentation

---

### CONNECTION008 - API Endpoint Not Found

**Severity:** ERROR
**Category:** Connection

**Description:**
The requested API endpoint was not found (HTTP 404).

**Common Causes:**
- Incorrect endpoint URL
- API version mismatch
- Endpoint deprecated or removed
- Typo in endpoint path
- API documentation outdated

**Resolution Steps:**
1. Verify API endpoint URL is correct
2. Check for typos in endpoint path
3. Review API documentation for correct endpoints
4. Verify API version in URL if required
5. Check external system API changes or deprecations

---

### CONNECTION009 - External System Returned Error

**Severity:** ERROR
**Category:** Connection

**Description:**
The external system returned an error response.

**Common Causes:**
- External system error (5xx)
- Bad request (4xx)
- Business logic error
- Invalid request data
- External system unavailable

**Resolution Steps:**
1. Review error response from external system
2. Check external system status page
3. Verify request parameters and format
4. Review external system logs if available
5. Contact external system support

---

### CONNECTION010 - Request Rate Limit Exceeded

**Severity:** WARNING
**Category:** Connection

**Description:**
The request rate limit for the external system has been exceeded.

**Common Causes:**
- Too many requests in time window
- Rate limit configuration
- Multiple concurrent flows accessing same system
- Burst request pattern

**Resolution Steps:**
1. Reduce request frequency
2. Implement rate limiting in integration
3. Check rate limit details from external system
4. Consider batch processing requests
5. Contact external system to increase rate limit

---

## Additional Resources

- **InterWeave IDE Documentation:** http://localhost:8080/iw-business-daemon/help/
- **Support:** Contact your system administrator or InterWeave support
- **Training Materials:** See `docs/tutorials/` directory

---

**Document Version:** 1.0
**Last Updated:** 2026-01-08
**Generated By:** InterWeave Error Framework
