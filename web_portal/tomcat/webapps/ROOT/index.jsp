<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>IW_IDE Web Portal</title>
    <meta http-equiv="refresh" content="0;url=/iw-business-daemon/IWLogin.jsp">
    <link href="favicon.ico" rel="icon" type="image/x-icon" />
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 40px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
        }
        .container {
            max-width: 600px;
            margin: 40px auto;
            background: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
        }
        h1 {
            color: #333;
            margin-bottom: 10px;
        }
        .subtitle {
            color: #666;
            margin-bottom: 30px;
        }
        .redirect-msg {
            background: #e8f5e9;
            padding: 15px;
            border-radius: 6px;
            margin-bottom: 30px;
            color: #2e7d32;
        }
        .links {
            margin-top: 20px;
        }
        .links a {
            display: block;
            padding: 14px 20px;
            margin: 10px 0;
            background: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: 500;
            transition: background 0.2s;
        }
        .links a:hover {
            background: #43a047;
        }
        .links a.config {
            background: #2196F3;
        }
        .links a.config:hover {
            background: #1e88e5;
        }
        .links a.test {
            background: #ff9800;
        }
        .links a.test:hover {
            background: #fb8c00;
        }
        .footer {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #eee;
            color: #999;
            font-size: 12px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>IW_IDE Web Portal</h1>
        <p class="subtitle">InterWeave Integration Development Environment</p>

        <div class="redirect-msg">
            Redirecting to login page...
        </div>

        <p>If you are not redirected automatically, select an option below:</p>

        <div class="links">
            <a href="/iw-business-daemon/IWLogin.jsp">Login to IW_IDE</a>
            <a href="/iw-business-daemon/BDConfigurator.jsp" class="config">Business Daemon Configurator</a>
            <a href="/iw-business-daemon/CompanyConfiguration.jsp" class="config">Company Configuration</a>
            <a href="/db_test.jsp" class="test">Database Connection Test</a>
        </div>

        <div class="footer">
            IW_IDE v2.41 | Apache Tomcat 9.0.83
        </div>
    </div>
</body>
</html>
