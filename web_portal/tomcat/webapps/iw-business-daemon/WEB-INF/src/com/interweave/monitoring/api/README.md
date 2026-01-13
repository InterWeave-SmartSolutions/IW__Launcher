# Monitoring API Servlets

This package contains REST API servlets for the monitoring dashboard.

## Compilation

To compile the servlet classes, use the following command from the `WEB-INF` directory:

```bash
javac -cp "classes:lib/*:../../../../../../web_portal/tomcat/lib/servlet-api.jar" \
      -d classes \
      -sourcepath src \
      src/com/interweave/monitoring/api/*.java
```

Or on Windows:

```cmd
javac -cp "classes;lib/*;..\..\..\..\..\..\web_portal\tomcat\lib\servlet-api.jar" ^
      -d classes ^
      -sourcepath src ^
      src\com\interweave\monitoring\api\*.java
```

## Base Servlet

**MonitoringApiServlet.java** - Abstract base class for all monitoring API endpoints.

Provides:
- JSON response handling with proper content types
- Session-based authentication validation
- Database connection management via JNDI DataSource
- Parameter parsing utilities (string, int, long, boolean)
- Structured error response generation
- HTTP status code handling

All monitoring API servlets extend this class and implement the `processRequest()` method.

## Usage Pattern

```java
public class ExampleApiServlet extends MonitoringApiServlet {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response,
                                  Connection conn)
            throws ServletException, IOException, SQLException {

        // Get user context
        Integer userId = getUserId(request);
        Integer companyId = getCompanyId(request);

        // Parse parameters
        String filter = getStringParameter(request, "filter", "all");
        int limit = getIntParameter(request, "limit", 50);

        // Execute database query
        // ... your logic here ...

        // Send JSON response
        String jsonData = "\"data\":[...]";
        sendJsonResponse(response, buildSuccessJson(jsonData));
    }
}
```
