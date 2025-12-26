<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>IW_IDE Database Connection Test</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .success { color: green; }
        .error { color: red; }
        table { border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #4CAF50; color: white; }
    </style>
</head>
<body>
    <h1>IW_IDE Database Connection Test</h1>

    <%
    Connection conn = null;
    try {
        // Get DataSource from JNDI
        Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource) envContext.lookup("jdbc/IWDB");

        conn = ds.getConnection();

        out.println("<p class='success'>&#10004; Database connection successful!</p>");

        // Get database info
        DatabaseMetaData meta = conn.getMetaData();
        out.println("<p><strong>Database:</strong> " + meta.getDatabaseProductName() + " " + meta.getDatabaseProductVersion() + "</p>");
        out.println("<p><strong>JDBC Driver:</strong> " + meta.getDriverName() + " " + meta.getDriverVersion() + "</p>");
        out.println("<p><strong>Connection URL:</strong> " + meta.getURL() + "</p>");

        // Query settings table
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM settings");

        out.println("<h2>Settings Table Contents:</h2>");
        out.println("<table>");
        out.println("<tr><th>Key</th><th>Value</th><th>Description</th><th>Updated At</th></tr>");

        while (rs.next()) {
            out.println("<tr>");
            out.println("<td>" + rs.getString("key") + "</td>");
            out.println("<td>" + rs.getString("value") + "</td>");
            out.println("<td>" + rs.getString("description") + "</td>");
            out.println("<td>" + rs.getString("updated_at") + "</td>");
            out.println("</tr>");
        }
        out.println("</table>");

        rs.close();
        stmt.close();

        // Count tables
        ResultSet tablesRs = conn.getMetaData().getTables(null, "public", "%", new String[]{"TABLE"});
        int tableCount = 0;
        out.println("<h2>Database Tables:</h2><ul>");
        while (tablesRs.next()) {
            out.println("<li>" + tablesRs.getString("TABLE_NAME") + "</li>");
            tableCount++;
        }
        out.println("</ul>");
        out.println("<p><strong>Total tables:</strong> " + tableCount + "</p>");
        tablesRs.close();

    } catch (NamingException e) {
        out.println("<p class='error'>&#10008; JNDI Lookup Error: " + e.getMessage() + "</p>");
        out.println("<pre>" + e.toString() + "</pre>");
    } catch (SQLException e) {
        out.println("<p class='error'>&#10008; Database Error: " + e.getMessage() + "</p>");
        out.println("<pre>" + e.toString() + "</pre>");
    } finally {
        if (conn != null) {
            try { conn.close(); } catch (SQLException e) {}
        }
    }
    %>

    <hr>
    <p><em>Test performed at: <%= new java.util.Date() %></em></p>
</body>
</html>
