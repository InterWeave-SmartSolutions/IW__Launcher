package com.interweave.help;

import com.interweave.error.ErrorCategory;
import com.interweave.error.ErrorCode;
import com.interweave.error.ErrorDocumentation;

import java.util.HashMap;
import java.util.Map;

/**
 * HelpLinkService - Context-sensitive help link service for the InterWeave IDE platform.
 *
 * This service provides help links and documentation based on the current context,
 * including:
 * - Current page or screen
 * - Current operation being performed
 * - Error type or code
 * - User's current workflow
 *
 * The help system provides quick access to relevant documentation, examples, and
 * troubleshooting guides without leaving the current workflow.
 *
 * @author InterWeave Error Framework
 * @version 1.0
 */
public class HelpLinkService {

    private static final String BASE_HELP_URL = "http://localhost:8080/iw-business-daemon/help/";
    private static final String HELP_POPUP_URL = BASE_HELP_URL + "help-popup.jsp";

    /**
     * Help context types
     */
    public enum HelpContext {
        LOGIN("login", "Login and Authentication"),
        FLOW_DESIGNER("flow-designer", "Flow Designer"),
        CONNECTION_CONFIG("connection-config", "Connection Configuration"),
        VALIDATION("validation", "Validation and Testing"),
        TRANSFORMATION("transformation", "Data Transformation"),
        XPATH_EDITOR("xpath-editor", "XPath Expression Editor"),
        XSLT_EDITOR("xslt-editor", "XSLT Template Editor"),
        PROJECT_CONFIG("project-config", "Project Configuration"),
        ERROR_TROUBLESHOOTING("error-troubleshooting", "Error Troubleshooting"),
        GENERAL("general", "General Help");

        private final String code;
        private final String displayName;

        HelpContext(String code, String displayName) {
            this.code = code;
            this.displayName = displayName;
        }

        public String getCode() {
            return code;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    /**
     * Help topic containing help content details
     */
    public static class HelpTopic {
        private final String title;
        private final String description;
        private final String helpUrl;
        private final String[] examples;
        private final String[] relatedTopics;

        public HelpTopic(String title, String description, String helpUrl,
                        String[] examples, String[] relatedTopics) {
            this.title = title;
            this.description = description;
            this.helpUrl = helpUrl;
            this.examples = examples != null ? examples : new String[0];
            this.relatedTopics = relatedTopics != null ? relatedTopics : new String[0];
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getHelpUrl() {
            return helpUrl;
        }

        public String[] getExamples() {
            return examples;
        }

        public String[] getRelatedTopics() {
            return relatedTopics;
        }
    }

    private static final Map<HelpContext, HelpTopic> contextHelpMap = new HashMap<>();
    private static final Map<String, HelpTopic> operationHelpMap = new HashMap<>();

    static {
        initializeContextHelp();
        initializeOperationHelp();
    }

    /**
     * Initialize context-based help topics
     */
    private static void initializeContextHelp() {
        // Login help
        contextHelpMap.put(HelpContext.LOGIN, new HelpTopic(
            "Login and Authentication",
            "Learn how to log in to the InterWeave IDE Portal and troubleshoot common authentication issues.",
            BASE_HELP_URL + "login-help.jsp",
            new String[] {
                "Ensure your email address is correct",
                "Password is case-sensitive",
                "Check that your account is activated",
                "Contact administrator for password reset"
            },
            new String[] {
                "Forgot Password",
                "Account Activation",
                "User Management"
            }
        ));

        // Flow Designer help
        contextHelpMap.put(HelpContext.FLOW_DESIGNER, new HelpTopic(
            "Flow Designer",
            "Create and configure integration flows with drag-and-drop components, connections, and transformations.",
            BASE_HELP_URL + "flow-designer-help.jsp",
            new String[] {
                "Drag components from palette onto canvas",
                "Connect components to define data flow",
                "Configure component properties in inspector",
                "Validate flow before deployment"
            },
            new String[] {
                "Flow Components",
                "Flow Validation",
                "Flow Deployment",
                "Flow Troubleshooting"
            }
        ));

        // Connection Configuration help
        contextHelpMap.put(HelpContext.CONNECTION_CONFIG, new HelpTopic(
            "Connection Configuration",
            "Configure connections to external systems including databases, APIs, and file systems.",
            BASE_HELP_URL + "connection-config-help.jsp",
            new String[] {
                "Enter connection endpoint URL",
                "Configure authentication credentials",
                "Test connection before saving",
                "Set timeout and retry parameters"
            },
            new String[] {
                "Connection Types",
                "Authentication Methods",
                "Connection Testing",
                "Connection Troubleshooting"
            }
        ));

        // Validation help
        contextHelpMap.put(HelpContext.VALIDATION, new HelpTopic(
            "Validation and Testing",
            "Validate flows and configurations to catch errors before deployment.",
            BASE_HELP_URL + "validation-help.jsp",
            new String[] {
                "Run validation from Tools menu",
                "Review validation results panel",
                "Fix reported errors and warnings",
                "Re-validate after making changes"
            },
            new String[] {
                "Flow Validation",
                "XPath Validation",
                "Configuration Validation",
                "Common Validation Errors"
            }
        ));

        // Transformation help
        contextHelpMap.put(HelpContext.TRANSFORMATION, new HelpTopic(
            "Data Transformation",
            "Transform data between formats using XSLT, field mappings, and custom logic.",
            BASE_HELP_URL + "transformation-help.jsp",
            new String[] {
                "Create field-to-field mappings",
                "Use XSLT for complex transformations",
                "Apply conditional logic and filters",
                "Test transformations with sample data"
            },
            new String[] {
                "XSLT Templates",
                "Field Mappings",
                "Transformation Functions",
                "Testing Transformations"
            }
        ));

        // XPath Editor help
        contextHelpMap.put(HelpContext.XPATH_EDITOR, new HelpTopic(
            "XPath Expression Editor",
            "Write and test XPath expressions for selecting and filtering XML data.",
            BASE_HELP_URL + "xpath-editor-help.jsp",
            new String[] {
                "Use / to select child elements",
                "Use // to select descendants anywhere",
                "Use @ to select attributes",
                "Test expressions with sample XML"
            },
            new String[] {
                "XPath Syntax",
                "XPath Functions",
                "Namespace Handling",
                "XPath Validation Errors"
            }
        ));

        // XSLT Editor help
        contextHelpMap.put(HelpContext.XSLT_EDITOR, new HelpTopic(
            "XSLT Template Editor",
            "Create XSLT templates for transforming XML documents.",
            BASE_HELP_URL + "xslt-editor-help.jsp",
            new String[] {
                "Define templates with match patterns",
                "Use xsl:value-of to output values",
                "Use xsl:for-each for iteration",
                "Test XSLT with sample input XML"
            },
            new String[] {
                "XSLT Elements",
                "Template Rules",
                "XSLT Functions",
                "XSLT Validation Errors"
            }
        ));

        // Project Configuration help
        contextHelpMap.put(HelpContext.PROJECT_CONFIG, new HelpTopic(
            "Project Configuration",
            "Configure project settings including server URLs, database connections, and runtime parameters.",
            BASE_HELP_URL + "project-config-help.jsp",
            new String[] {
                "Edit config.xml for project settings",
                "Configure transformation server URLs",
                "Set database connection parameters",
                "Validate configuration before deployment"
            },
            new String[] {
                "Configuration Files",
                "Server Configuration",
                "Database Configuration",
                "Configuration Validation"
            }
        ));

        // Error Troubleshooting help
        contextHelpMap.put(HelpContext.ERROR_TROUBLESHOOTING, new HelpTopic(
            "Error Troubleshooting",
            "Understand error messages and find solutions to common problems.",
            BASE_HELP_URL + "errors/index.jsp",
            new String[] {
                "Note the error code for reference",
                "Read the error message carefully",
                "Follow suggested resolution steps",
                "Check documentation for detailed guidance"
            },
            new String[] {
                "Common Errors",
                "Error Code Reference",
                "Troubleshooting Guide",
                "Contact Support"
            }
        ));

        // General help
        contextHelpMap.put(HelpContext.GENERAL, new HelpTopic(
            "General Help",
            "General documentation and getting started guide for InterWeave IDE.",
            BASE_HELP_URL + "index.jsp",
            new String[] {
                "Review getting started guide",
                "Explore sample projects",
                "Watch tutorial videos",
                "Read best practices documentation"
            },
            new String[] {
                "Getting Started",
                "Tutorials",
                "Best Practices",
                "FAQ"
            }
        ));
    }

    /**
     * Initialize operation-based help topics
     */
    private static void initializeOperationHelp() {
        // Login operation
        operationHelpMap.put("login", new HelpTopic(
            "Logging In",
            "Enter your email and password to access the InterWeave IDE Portal.",
            HELP_POPUP_URL + "?context=login&operation=login",
            new String[] {
                "Email: your-email@company.com",
                "Password: (case-sensitive)"
            },
            new String[] {"Forgot Password", "Account Issues"}
        ));

        // Create flow operation
        operationHelpMap.put("create-flow", new HelpTopic(
            "Creating a New Flow",
            "Create a new integration flow to define how data moves between systems.",
            HELP_POPUP_URL + "?context=flow-designer&operation=create-flow",
            new String[] {
                "File > New > Integration Flow",
                "Enter flow name and description",
                "Select flow template (optional)",
                "Click Create"
            },
            new String[] {"Flow Components", "Flow Configuration"}
        ));

        // Configure connection operation
        operationHelpMap.put("configure-connection", new HelpTopic(
            "Configuring a Connection",
            "Set up a connection to an external system or database.",
            HELP_POPUP_URL + "?context=connection-config&operation=configure-connection",
            new String[] {
                "Enter connection name",
                "Select connection type (HTTP, Database, FTP, etc.)",
                "Enter endpoint URL or hostname",
                "Configure authentication (username/password, API key, etc.)",
                "Test connection to verify settings"
            },
            new String[] {"Connection Types", "Authentication Methods"}
        ));

        // Validate flow operation
        operationHelpMap.put("validate-flow", new HelpTopic(
            "Validating a Flow",
            "Check your flow for configuration errors before deployment.",
            HELP_POPUP_URL + "?context=validation&operation=validate-flow",
            new String[] {
                "Tools > Validate Flow",
                "Review validation results",
                "Fix any errors or warnings",
                "Re-validate to confirm fixes"
            },
            new String[] {"Validation Errors", "Flow Configuration"}
        ));

        // Edit XPath operation
        operationHelpMap.put("edit-xpath", new HelpTopic(
            "Editing XPath Expressions",
            "Write XPath expressions to select data from XML documents.",
            HELP_POPUP_URL + "?context=xpath-editor&operation=edit-xpath",
            new String[] {
                "/root/element - select child element",
                "//element - select anywhere in document",
                "/root/@attribute - select attribute",
                "//element[condition] - filter by condition"
            },
            new String[] {"XPath Syntax", "XPath Functions"}
        ));

        // Edit XSLT operation
        operationHelpMap.put("edit-xslt", new HelpTopic(
            "Editing XSLT Templates",
            "Create XSLT templates to transform XML data.",
            HELP_POPUP_URL + "?context=xslt-editor&operation=edit-xslt",
            new String[] {
                "<xsl:template match=\"pattern\">",
                "<xsl:value-of select=\"xpath\"/>",
                "<xsl:for-each select=\"xpath\">",
                "<xsl:if test=\"condition\">"
            },
            new String[] {"XSLT Syntax", "Template Rules"}
        ));
    }

    /**
     * Gets help topic for a specific context
     *
     * @param context The help context
     * @return HelpTopic for the context, or general help if not found
     */
    public static HelpTopic getContextHelp(HelpContext context) {
        HelpTopic topic = contextHelpMap.get(context);
        return topic != null ? topic : contextHelpMap.get(HelpContext.GENERAL);
    }

    /**
     * Gets help topic for a specific operation
     *
     * @param operation The operation identifier
     * @return HelpTopic for the operation, or null if not found
     */
    public static HelpTopic getOperationHelp(String operation) {
        return operationHelpMap.get(operation);
    }

    /**
     * Gets help URL for a specific context
     *
     * @param context The help context
     * @return Help URL as a string
     */
    public static String getContextHelpUrl(HelpContext context) {
        HelpTopic topic = getContextHelp(context);
        return topic != null ? topic.getHelpUrl() : BASE_HELP_URL + "index.jsp";
    }

    /**
     * Gets help URL for a specific operation
     *
     * @param operation The operation identifier
     * @return Help URL as a string, or null if operation not found
     */
    public static String getOperationHelpUrl(String operation) {
        HelpTopic topic = getOperationHelp(operation);
        return topic != null ? topic.getHelpUrl() : null;
    }

    /**
     * Gets help URL for an error code
     *
     * @param errorCode The error code
     * @return Documentation URL for the error code, or general error help if not found
     */
    public static String getErrorHelpUrl(ErrorCode errorCode) {
        if (errorCode != null) {
            String docUrl = ErrorDocumentation.getDocumentationUrl(errorCode);
            if (docUrl != null) {
                return docUrl;
            }
        }
        return BASE_HELP_URL + "errors/index.jsp";
    }

    /**
     * Gets help URL for an error category
     *
     * @param category The error category
     * @return Documentation URL for the category
     */
    public static String getCategoryHelpUrl(ErrorCategory category) {
        if (category == null) {
            return BASE_HELP_URL + "errors/index.jsp";
        }

        switch (category) {
            case AUTH:
                return BASE_HELP_URL + "errors/auth-errors.jsp";
            case DB:
                return BASE_HELP_URL + "errors/database-errors.jsp";
            case FLOW:
                return BASE_HELP_URL + "errors/flow-errors.jsp";
            case CONFIG:
                return BASE_HELP_URL + "errors/config-errors.jsp";
            case VALIDATION:
                return BASE_HELP_URL + "errors/validation-errors.jsp";
            case XPATH:
                return BASE_HELP_URL + "errors/xpath-errors.jsp";
            case CONNECTION:
                return BASE_HELP_URL + "errors/connection-errors.jsp";
            case SYSTEM:
                return BASE_HELP_URL + "errors/system-errors.jsp";
            default:
                return BASE_HELP_URL + "errors/index.jsp";
        }
    }

    /**
     * Gets popup help URL with context and operation parameters
     *
     * @param context The help context
     * @param operation The operation identifier (optional)
     * @return Popup help URL with parameters
     */
    public static String getPopupHelpUrl(HelpContext context, String operation) {
        StringBuilder url = new StringBuilder(HELP_POPUP_URL);
        url.append("?context=").append(context.getCode());

        if (operation != null && operation.length() > 0) {
            url.append("&operation=").append(operation);
        }

        return url.toString();
    }

    /**
     * Gets popup help URL for an error code
     *
     * @param errorCode The error code
     * @return Popup help URL with error code parameter
     */
    public static String getErrorPopupUrl(ErrorCode errorCode) {
        if (errorCode == null) {
            return HELP_POPUP_URL + "?context=error-troubleshooting";
        }
        return HELP_POPUP_URL + "?context=error-troubleshooting&errorCode=" + errorCode.getCode();
    }

    /**
     * Gets popup help URL for a page and operation
     *
     * @param page The page identifier
     * @param operation The operation identifier
     * @return Popup help URL with parameters
     */
    public static String getPopupHelpUrl(String page, String operation) {
        StringBuilder url = new StringBuilder(HELP_POPUP_URL);

        if (page != null && page.length() > 0) {
            url.append("?page=").append(page);
        } else {
            url.append("?context=general");
        }

        if (operation != null && operation.length() > 0) {
            url.append("&operation=").append(operation);
        }

        return url.toString();
    }

    /**
     * Checks if help is available for a context
     *
     * @param context The help context
     * @return true if help is available, false otherwise
     */
    public static boolean hasContextHelp(HelpContext context) {
        return contextHelpMap.containsKey(context);
    }

    /**
     * Checks if help is available for an operation
     *
     * @param operation The operation identifier
     * @return true if help is available, false otherwise
     */
    public static boolean hasOperationHelp(String operation) {
        return operationHelpMap.containsKey(operation);
    }

    /**
     * Gets all available help contexts
     *
     * @return Array of all help contexts
     */
    public static HelpContext[] getAllContexts() {
        return HelpContext.values();
    }

    /**
     * Gets base help URL
     *
     * @return Base help URL
     */
    public static String getBaseHelpUrl() {
        return BASE_HELP_URL;
    }

    /**
     * Gets help popup URL (without parameters)
     *
     * @return Help popup base URL
     */
    public static String getHelpPopupUrl() {
        return HELP_POPUP_URL;
    }
}
