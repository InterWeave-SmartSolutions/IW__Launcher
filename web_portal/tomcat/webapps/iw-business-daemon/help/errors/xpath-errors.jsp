<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=iso-8859-1" %>
<%@ page import="com.interweave.error.*" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <title>XPath/XSLT Errors - InterWeave IDE Portal</title>
  <style>
    body {
      font-family: Arial, Helvetica, sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f5f5f5;
    }
    .header-banner {
      width: 100%;
      border-bottom: 2px solid #ddd;
    }
    .container {
      max-width: 1000px;
      margin: 30px auto;
      padding: 0 20px;
    }
    .content-box {
      background-color: #ffffff;
      border: 1px solid #ddd;
      border-radius: 8px;
      padding: 30px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    }
    .breadcrumb {
      font-size: 12px;
      color: #666;
      margin-bottom: 20px;
    }
    .breadcrumb a {
      color: #0066cc;
      text-decoration: none;
    }
    .breadcrumb a:hover {
      text-decoration: underline;
    }
    h1 {
      color: #333;
      font-size: 28px;
      margin-top: 0;
      border-bottom: 3px solid #0066cc;
      padding-bottom: 10px;
      display: flex;
      align-items: center;
    }
    .page-icon {
      font-size: 36px;
      margin-right: 15px;
    }
    .intro {
      font-size: 14px;
      line-height: 1.6;
      color: #666;
      margin-bottom: 30px;
      padding: 15px;
      background-color: #f9f9f9;
      border-left: 4px solid #0066cc;
      border-radius: 4px;
    }
    .error-entry {
      margin: 30px 0;
      padding: 25px;
      background-color: #ffffff;
      border: 2px solid #e0e0e0;
      border-radius: 8px;
      box-shadow: 0 1px 4px rgba(0,0,0,0.05);
    }
    .error-entry:target {
      border-color: #0066cc;
      background-color: #f0f8ff;
      box-shadow: 0 2px 8px rgba(0,102,204,0.2);
    }
    .error-header {
      display: flex;
      align-items: center;
      margin-bottom: 15px;
      padding-bottom: 12px;
      border-bottom: 2px solid #f0f0f0;
    }
    .error-code {
      font-family: 'Courier New', monospace;
      font-weight: bold;
      color: #cc0000;
      font-size: 20px;
      margin-right: 15px;
    }
    .error-message {
      font-size: 16px;
      color: #333;
      font-weight: bold;
      flex: 1;
    }
    .severity-badge {
      display: inline-block;
      padding: 4px 12px;
      border-radius: 4px;
      font-size: 11px;
      font-weight: bold;
    }
    .severity-ERROR {
      background-color: #ffeeee;
      color: #cc0000;
    }
    .severity-WARNING {
      background-color: #fffbf0;
      color: #ff9900;
    }
    .section-title {
      font-weight: bold;
      font-size: 14px;
      color: #0066cc;
      margin: 20px 0 10px 0;
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }
    .section-content {
      font-size: 13px;
      color: #555;
      line-height: 1.7;
    }
    .resolution-steps {
      background-color: #eef7ff;
      border-left: 4px solid #0066cc;
      padding: 15px 15px 15px 20px;
      margin: 15px 0;
      border-radius: 4px;
    }
    .resolution-steps ol {
      margin: 10px 0;
      padding-left: 20px;
    }
    .resolution-steps li {
      margin: 10px 0;
      line-height: 1.6;
    }
    .example-box {
      background-color: #f9f9f9;
      border: 1px solid #ddd;
      border-radius: 4px;
      padding: 15px;
      margin: 15px 0;
      font-family: 'Courier New', monospace;
      font-size: 12px;
      overflow-x: auto;
    }
    .example-title {
      font-family: Arial, Helvetica, sans-serif;
      font-weight: bold;
      font-size: 12px;
      color: #666;
      margin-bottom: 10px;
    }
    .cause-list {
      list-style-type: disc;
      padding-left: 25px;
      margin: 10px 0;
    }
    .cause-list li {
      margin: 8px 0;
      line-height: 1.5;
    }
    .back-link {
      display: inline-block;
      margin-top: 30px;
      padding: 10px 20px;
      background-color: #f0f0f0;
      color: #333;
      text-decoration: none;
      border-radius: 4px;
      font-size: 14px;
      transition: background-color 0.2s;
    }
    .back-link:hover {
      background-color: #e0e0e0;
    }
  </style>
</head>
<body>

<!-- Header Banner -->
<table border="0" cellpadding="0" cellspacing="0" width="100%" class="header-banner">
  <tr>
    <td><img src="../../images/IT Banner.png" alt="InterWeave IDE" align="left" width="100%" height="94"/></td>
  </tr>
</table>

<!-- Main Content -->
<div class="container">
  <div class="content-box">

    <div class="breadcrumb">
      <a href="index.jsp">&#128214; Error Documentation</a> &gt; XPath/XSLT Errors
    </div>

    <h1>
      <span class="page-icon">&#128220;</span>
      XPath/XSLT Errors
    </h1>

    <div class="intro">
      XPath and XSLT errors occur during data transformation processes when XML path expressions are invalid
      or XSLT templates have syntax errors. These errors are common in integration flows that transform
      data between different formats. Below you'll find detailed information about each XPath/XSLT error code.
    </div>

    <!-- XPATH001 -->
    <div class="error-entry" id="XPATH001">
      <div class="error-header">
        <span class="error-code">XPATH001</span>
        <span class="error-message">XPath expression syntax error</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Unbalanced parentheses or brackets in expression</li>
          <li>Invalid XPath operators or syntax</li>
          <li>Incorrect use of quotes in string literals</li>
          <li>Missing or extra slashes (/, //)</li>
          <li>Invalid function names or arguments</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Validate XPath syntax using XPath evaluator</strong> - Test expression online</li>
          <li><strong>Check for unbalanced parentheses or brackets</strong> - Ensure pairs match</li>
          <li><strong>Verify function names are correct</strong> - Check XPath function reference</li>
          <li><strong>Check operator usage</strong> - Review /, //, ., @, and other operators</li>
          <li><strong>Review XPath syntax documentation</strong> - Consult XPath reference</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Invalid XPath Expression:</div>
        XPath expression syntax error [XPATH001]<br>
        <br>
        Expression: //Customer/Name[text()='John'<br>
        Error: Unclosed bracket at position 30<br>
        <br>
        Corrected expression:<br>
        //Customer/Name[text()='John']<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;^<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Missing closing bracket
      </div>
    </div>

    <!-- XPATH002 -->
    <div class="error-entry" id="XPATH002">
      <div class="error-header">
        <span class="error-code">XPATH002</span>
        <span class="error-message">Undefined namespace prefix in XPath expression</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Namespace prefix used but not declared in XSLT</li>
          <li>Typo in namespace prefix</li>
          <li>Namespace declaration missing from XSLT root</li>
          <li>Incorrect namespace URI</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Define namespace prefix in XSLT file</strong> - Add xmlns:prefix declaration</li>
          <li><strong>Check namespace declarations at top of file</strong> - Verify all prefixes declared</li>
          <li><strong>Verify namespace URI matches XML document</strong> - Check exact URI string</li>
          <li><strong>Use correct prefix in XPath expression</strong> - Match declared prefix</li>
          <li><strong>Review namespace handling documentation</strong> - Understand namespace resolution</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Undefined Namespace Prefix:</div>
        Undefined namespace prefix in XPath expression [XPATH002]<br>
        <br>
        Expression: //sf:Account/sf:Name<br>
        Error: Namespace prefix 'sf' is not defined<br>
        <br>
        Add to XSLT root element:<br>
        &lt;xsl:stylesheet version="1.0"<br>
        &nbsp;&nbsp;xmlns:xsl="http://www.w3.org/1999/XSL/Transform"<br>
        &nbsp;&nbsp;xmlns:sf="http://www.salesforce.com/schemas"&gt;<br>
        &nbsp;&nbsp;...<br>
        &lt;/xsl:stylesheet&gt;
      </div>
    </div>

    <!-- XPATH003 -->
    <div class="error-entry" id="XPATH003">
      <div class="error-header">
        <span class="error-code">XPATH003</span>
        <span class="error-message">XSLT template not found</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>call-template references non-existent template name</li>
          <li>Template name typo (case-sensitive)</li>
          <li>Template is commented out or removed</li>
          <li>Template defined in separate file not imported</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Verify template name exists in XSLT file</strong> - Search for template definition</li>
          <li><strong>Check template name spelling</strong> - Names are case-sensitive</li>
          <li><strong>Ensure template is not commented out</strong> - Check for &lt;!-- --&gt; comments</li>
          <li><strong>Review template definitions in XSLT</strong> - List all named templates</li>
          <li><strong>Check template import/include statements</strong> - Verify external templates loaded</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Template Not Found:</div>
        XSLT template not found [XPATH003]<br>
        <br>
        Template call:<br>
        &lt;xsl:call-template name="FormatPhoneNumber"/&gt;<br>
        <br>
        Error: Template 'FormatPhoneNumber' is not defined<br>
        <br>
        Add template definition:<br>
        &lt;xsl:template name="FormatPhoneNumber"&gt;<br>
        &nbsp;&nbsp;&lt;!-- template content --&gt;<br>
        &lt;/xsl:template&gt;
      </div>
    </div>

    <!-- XPATH004 -->
    <div class="error-entry" id="XPATH004">
      <div class="error-header">
        <span class="error-code">XPATH004</span>
        <span class="error-message">XSLT transformation failed</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Input XML structure doesn't match XSLT expectations</li>
          <li>Required XML elements missing from input</li>
          <li>XSLT syntax errors preventing compilation</li>
          <li>Runtime errors in XSLT logic</li>
          <li>Memory issues with large XML documents</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Review XSLT file for syntax errors</strong> - Validate XSLT structure</li>
          <li><strong>Check input XML structure matches expected format</strong> - Compare schemas</li>
          <li><strong>Verify XPath expressions in transformation</strong> - Test individual expressions</li>
          <li><strong>Test with sample input data</strong> - Use known-good test XML</li>
          <li><strong>Review transformation logs for detailed error</strong> - Check stack trace</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Transformation Failed:</div>
        XSLT transformation failed [XPATH004]<br>
        <br>
        Input: customer_data.xml<br>
        XSLT: customer_transform.xslt<br>
        <br>
        Error: Element 'CustomerID' not found in input XML<br>
        <br>
        The XSLT expects a CustomerID element that is missing<br>
        from the input. Verify input XML structure or update<br>
        XSLT to handle missing elements.
      </div>
    </div>

    <!-- XPATH005 -->
    <div class="error-entry" id="XPATH005">
      <div class="error-header">
        <span class="error-code">XPATH005</span>
        <span class="error-message">XPath expression returned no results</span>
        <span class="severity-badge severity-WARNING">WARNING</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>XPath expression doesn't match any elements in XML</li>
          <li>Element path is incorrect or misspelled</li>
          <li>Wrong context node for relative path</li>
          <li>Optional element is missing from input</li>
          <li>Namespace prefix issue</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Verify XPath expression is correct</strong> - Check path syntax</li>
          <li><strong>Check input XML contains expected elements</strong> - Verify structure</li>
          <li><strong>Review XPath context node</strong> - Understand current position</li>
          <li><strong>Test XPath with sample XML</strong> - Use XPath evaluator tool</li>
          <li><strong>Consider conditional logic for optional elements</strong> - Use xsl:if or xsl:choose</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">No Results Found:</div>
        XPath expression returned no results [XPATH005]<br>
        <br>
        Expression: //Order/ShippingAddress/Phone<br>
        <br>
        The XPath found no matching elements. This may indicate:<br>
        - ShippingAddress element is missing from Order<br>
        - Phone element is not present in ShippingAddress<br>
        - Element names are case-sensitive (check capitalization)<br>
        <br>
        Consider using conditional logic:<br>
        &lt;xsl:if test="ShippingAddress/Phone"&gt;<br>
        &nbsp;&nbsp;...handle phone...<br>
        &lt;/xsl:if&gt;
      </div>
    </div>

    <!-- XPATH006 -->
    <div class="error-entry" id="XPATH006">
      <div class="error-header">
        <span class="error-code">XPATH006</span>
        <span class="error-message">Invalid XPath function call</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Function name is misspelled or doesn't exist</li>
          <li>Wrong number of arguments provided</li>
          <li>Incorrect argument types</li>
          <li>Function not supported in XPath version</li>
          <li>Typo in function name (e.g., "concate" instead of "concat")</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Verify function name is correct</strong> - Check XPath function reference</li>
          <li><strong>Check number and types of function arguments</strong> - Review function signature</li>
          <li><strong>Review XPath function documentation</strong> - Understand usage</li>
          <li><strong>Verify function is supported in XPath version</strong> - Check version compatibility</li>
          <li><strong>Check for typos in function name</strong> - Common mistakes like "concate"</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Invalid Function Call:</div>
        Invalid XPath function call [XPATH006]<br>
        <br>
        Expression: substring(Name, 0, 10)<br>
        Error: Function 'substring' expects 3 arguments but<br>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;XPath substring is 1-indexed, not 0-indexed<br>
        <br>
        Corrected expression:<br>
        substring(Name, 1, 10)  &lt;-- Start at position 1<br>
        <br>
        Common XPath functions:<br>
        - concat(str1, str2, ...)   Concatenate strings<br>
        - substring(str, start, len) Extract substring<br>
        - string-length(str)         Get string length<br>
        - contains(str, substr)      Check if contains
      </div>
    </div>

    <!-- XPATH007 -->
    <div class="error-entry" id="XPATH007">
      <div class="error-header">
        <span class="error-code">XPATH007</span>
        <span class="error-message">Invalid XSLT syntax</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>XSLT file is not well-formed XML</li>
          <li>Missing or incorrect XSLT namespace</li>
          <li>Unclosed XSLT elements</li>
          <li>Invalid XSLT element or attribute names</li>
          <li>Incorrect XSLT element nesting</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Validate XSLT file syntax</strong> - Use XML validator tool</li>
          <li><strong>Check XSLT namespace declaration</strong> - Verify xmlns:xsl is present</li>
          <li><strong>Verify well-formed XML structure</strong> - Check all tags are closed</li>
          <li><strong>Review XSLT elements and attributes</strong> - Ensure valid XSLT syntax</li>
          <li><strong>Use XSLT validator tool</strong> - Test file structure</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Invalid XSLT Syntax:</div>
        Invalid XSLT syntax [XPATH007]<br>
        <br>
        Error at line 15: Unclosed element 'xsl:for-each'<br>
        <br>
        Problem:<br>
        &lt;xsl:for-each select="//Customer"&gt;<br>
        &nbsp;&nbsp;&lt;xsl:value-of select="Name"/&gt;<br>
        &lt;!-- Missing closing tag --&gt;<br>
        <br>
        Corrected:<br>
        &lt;xsl:for-each select="//Customer"&gt;<br>
        &nbsp;&nbsp;&lt;xsl:value-of select="Name"/&gt;<br>
        &lt;/xsl:for-each&gt;  &lt;-- Add closing tag
      </div>
    </div>

    <!-- XPATH008 -->
    <div class="error-entry" id="XPATH008">
      <div class="error-header">
        <span class="error-code">XPATH008</span>
        <span class="error-message">Undefined XPath variable</span>
        <span class="severity-badge severity-ERROR">ERROR</span>
      </div>

      <div class="section-title">&#128269; Common Causes</div>
      <div class="section-content">
        <ul class="cause-list">
          <li>Variable referenced but not declared</li>
          <li>Variable name typo (case-sensitive)</li>
          <li>Variable declared in wrong scope</li>
          <li>Variable used before declaration</li>
        </ul>
      </div>

      <div class="section-title">&#128161; Resolution Steps</div>
      <div class="resolution-steps">
        <ol>
          <li><strong>Define XPath variable before use</strong> - Add xsl:variable declaration</li>
          <li><strong>Check variable name spelling</strong> - Names are case-sensitive</li>
          <li><strong>Verify variable scope</strong> - Ensure variable is accessible</li>
          <li><strong>Review variable declarations in XSLT</strong> - Check xsl:variable elements</li>
          <li><strong>Check variable is not defined in unreachable scope</strong> - Understand scope rules</li>
        </ol>
      </div>

      <div class="section-title">&#128221; Example</div>
      <div class="example-box">
        <div class="example-title">Undefined Variable:</div>
        Undefined XPath variable [XPATH008]<br>
        <br>
        Expression: &lt;xsl:value-of select="$customerName"/&gt;<br>
        Error: Variable 'customerName' is not defined<br>
        <br>
        Add variable declaration before use:<br>
        &lt;xsl:variable name="customerName"<br>
        &nbsp;&nbsp;select="//Customer/Name"/&gt;<br>
        <br>
        Then use the variable:<br>
        &lt;xsl:value-of select="$customerName"/&gt;<br>
        <br>
        Note: Variable names in XPath are prefixed with $ when used
      </div>
    </div>

    <a href="index.jsp" class="back-link">&#8592; Back to Error Documentation Index</a>

  </div>
</div>

</body>
</html>
