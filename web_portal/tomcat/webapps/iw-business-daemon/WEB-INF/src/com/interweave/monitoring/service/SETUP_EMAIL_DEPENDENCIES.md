# JavaMail Dependencies Setup Guide

## Overview

The EmailNotificationService requires JavaMail API libraries to send emails via SMTP. These libraries are not included in the standard Java 8 JRE and must be added separately.

## Required Libraries

You need two JAR files:

1. **javax.mail.jar** (or mail.jar) - JavaMail API implementation
2. **activation.jar** - JavaBeans Activation Framework (required by JavaMail)

## Download Links

### Option 1: Maven Central (Recommended)

Download from Maven Central Repository:

- **JavaMail**: https://repo1.maven.org/maven2/com/sun/mail/javax.mail/1.6.2/javax.mail-1.6.2.jar
- **Activation**: https://repo1.maven.org/maven2/javax/activation/activation/1.1.1/activation-1.1.1.jar

```bash
# Download using wget or curl
cd web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/lib/

# JavaMail API
wget https://repo1.maven.org/maven2/com/sun/mail/javax.mail/1.6.2/javax.mail-1.6.2.jar

# Activation Framework
wget https://repo1.maven.org/maven2/javax/activation/activation/1.1.1/activation-1.1.1.jar
```

### Option 2: Oracle Website

Download from Oracle's JavaMail website:

1. Visit: https://javaee.github.io/javamail/
2. Download JavaMail 1.6.2 or later
3. Extract and find `javax.mail.jar`

## Installation Steps

### Step 1: Download JAR Files

Download the two required JAR files using one of the methods above.

### Step 2: Place JAR Files

Copy the JAR files to the application's library directory:

**Location:** `web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/lib/`

```bash
# Windows
copy javax.mail-1.6.2.jar C:\IW_IDE\IW_Launcher\web_portal\tomcat\webapps\iw-business-daemon\WEB-INF\lib\
copy activation-1.1.1.jar C:\IW_IDE\IW_Launcher\web_portal\tomcat\webapps\iw-business-daemon\WEB-INF\lib\

# Linux/Mac
cp javax.mail-1.6.2.jar ./web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/lib/
cp activation-1.1.1.jar ./web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/lib/
```

### Step 3: Restart Tomcat

Restart the web portal for the libraries to be loaded:

```bash
# Windows
_internal\stop_webportal.bat
_internal\start_webportal.bat

# Linux/Mac
_internal/stop_webportal.sh
_internal/start_webportal.sh
```

## Verification

### Check if JAR Files Are Loaded

After restarting, check the Tomcat logs for EmailNotificationService initialization:

```bash
tail -f web_portal/tomcat/logs/catalina.out | grep EmailNotificationService
```

You should see:
```
[EmailNotificationService] EmailNotificationService initialized - using JNDI DataSource jdbc/IWDB
[EmailNotificationService] Loaded SMTP configuration from monitoring.properties
[EmailNotificationService] Starting EmailNotificationService...
[EmailNotificationService] EmailNotificationService started - polling every 30 seconds
```

### Test Email Functionality

Create a test alert in the database:

```sql
-- Connect to MySQL database
USE iw_ide;

-- Insert a test email alert
INSERT INTO alert_history
(alert_rule_id, execution_id, alert_type, recipient, subject, message, status, created_at)
VALUES
(1, NULL, 'email', 'your-email@example.com',
 'Test Email from EmailNotificationService',
 'This is a test email.\n\nIf you receive this, JavaMail is configured correctly.',
 'pending', NOW());

-- Wait 30 seconds for the polling cycle, then check status
SELECT id, recipient, status, sent_at, status_message
FROM alert_history
WHERE recipient = 'your-email@example.com'
ORDER BY id DESC
LIMIT 1;
```

If the status changes to 'sent' and you receive the email, JavaMail is working correctly.

## Troubleshooting

### Error: ClassNotFoundException: javax.mail.Session

**Cause:** JavaMail JAR not found in classpath

**Solution:**
1. Verify `javax.mail-1.6.2.jar` exists in `WEB-INF/lib/`
2. Restart Tomcat to reload libraries
3. Check file permissions (should be readable)

### Error: NoClassDefFoundError: javax.activation.DataHandler

**Cause:** Activation Framework JAR not found

**Solution:**
1. Verify `activation-1.1.1.jar` exists in `WEB-INF/lib/`
2. Restart Tomcat
3. Both JARs must be present

### Error: java.lang.SecurityException: sealing violation

**Cause:** Duplicate JavaMail JARs in classpath

**Solution:**
1. Remove duplicate JARs from `tomcat/lib/` (keep only in `WEB-INF/lib/`)
2. Ensure only one version of each JAR is present

### Emails Not Sending After JAR Installation

**Cause:** SMTP configuration issue (not related to JARs)

**Solution:**
1. Check `WEB-INF/monitoring.properties` SMTP settings
2. Review logs for SMTP errors: `tail -f tomcat/logs/catalina.out | grep EmailNotificationService`
3. See [README_EMAIL_NOTIFICATION.md](README_EMAIL_NOTIFICATION.md) for SMTP troubleshooting

## Alternative: System-Wide Installation

Instead of placing JARs in `WEB-INF/lib/`, you can install them system-wide in Tomcat's lib directory:

**Location:** `web_portal/tomcat/lib/`

**Pros:**
- Available to all web applications
- Single copy for multiple apps

**Cons:**
- May conflict with other applications using different JavaMail versions
- Requires Tomcat restart for all apps

**Recommendation:** Use `WEB-INF/lib/` for application-specific dependencies.

## Maven/Gradle Dependencies

If you're building from source using Maven or Gradle, add these dependencies:

### Maven (pom.xml)
```xml
<dependencies>
    <!-- JavaMail API -->
    <dependency>
        <groupId>com.sun.mail</groupId>
        <artifactId>javax.mail</artifactId>
        <version>1.6.2</version>
    </dependency>

    <!-- Activation Framework -->
    <dependency>
        <groupId>javax.activation</groupId>
        <artifactId>activation</artifactId>
        <version>1.1.1</version>
    </dependency>
</dependencies>
```

### Gradle (build.gradle)
```gradle
dependencies {
    // JavaMail API
    implementation 'com.sun.mail:javax.mail:1.6.2'

    // Activation Framework
    implementation 'javax.activation:activation:1.1.1'
}
```

## Version Compatibility

| JavaMail Version | Java Version | Status      |
|------------------|--------------|-------------|
| 1.6.2            | Java 8+      | Recommended |
| 1.6.0            | Java 7+      | Supported   |
| 1.5.6            | Java 7+      | Legacy      |
| 1.4.7            | Java 5+      | Deprecated  |

**Current Project:** Java 8 (bundled JRE at `jre/`)
**Recommended:** JavaMail 1.6.2

## Security Notes

### JAR Verification

Verify JAR integrity by checking SHA-256 checksums:

**javax.mail-1.6.2.jar:**
```
SHA-256: 6b2534f5dc4af5fe99e8f4dd0a33432e59ee3f93d1f6d0bebe6e55bc3c3e03c5
```

**activation-1.1.1.jar:**
```
SHA-256: 485de3a253e23f645037828c07f1d7f1af40763304fc5c8e67a2c71a3e3c70b7
```

### Verify Checksums (Linux/Mac)
```bash
sha256sum javax.mail-1.6.2.jar
sha256sum activation-1.1.1.jar
```

### Verify Checksums (Windows PowerShell)
```powershell
Get-FileHash javax.mail-1.6.2.jar -Algorithm SHA256
Get-FileHash activation-1.1.1.jar -Algorithm SHA256
```

## License Information

### JavaMail API
- **License:** CDDL 1.1 + GPLv2 with Classpath Exception
- **Vendor:** Oracle (formerly Sun Microsystems)
- **Commercial Use:** Permitted

### Activation Framework
- **License:** CDDL 1.0
- **Vendor:** Oracle
- **Commercial Use:** Permitted

Both libraries are free to use in commercial and open-source projects.

## Quick Setup Script

### Windows (setup_javamail.bat)
```batch
@echo off
echo Downloading JavaMail dependencies...

cd web_portal\tomcat\webapps\iw-business-daemon\WEB-INF\lib

echo Downloading javax.mail-1.6.2.jar...
powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/sun/mail/javax.mail/1.6.2/javax.mail-1.6.2.jar' -OutFile 'javax.mail-1.6.2.jar'"

echo Downloading activation-1.1.1.jar...
powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/javax/activation/activation/1.1.1/activation-1.1.1.jar' -OutFile 'activation-1.1.1.jar'"

echo Done! Libraries installed.
echo Please restart Tomcat.
```

### Linux/Mac (setup_javamail.sh)
```bash
#!/bin/bash
echo "Downloading JavaMail dependencies..."

cd web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/lib

echo "Downloading javax.mail-1.6.2.jar..."
wget https://repo1.maven.org/maven2/com/sun/mail/javax.mail/1.6.2/javax.mail-1.6.2.jar

echo "Downloading activation-1.1.1.jar..."
wget https://repo1.maven.org/maven2/javax/activation/activation/1.1.1/activation-1.1.1.jar

echo "Done! Libraries installed."
echo "Please restart Tomcat."
```

## Next Steps

After installing JavaMail libraries:

1. Configure SMTP settings in `WEB-INF/monitoring.properties` (see [README_EMAIL_NOTIFICATION.md](README_EMAIL_NOTIFICATION.md))
2. Create alert rules in the database (see [README_ALERT_SERVICE.md](README_ALERT_SERVICE.md))
3. Test email functionality with sample alerts
4. Monitor logs for any errors

## Support

If you encounter issues:

1. Check Tomcat logs: `web_portal/tomcat/logs/catalina.out`
2. Verify JAR files are in correct location
3. Ensure proper file permissions
4. Review [README_EMAIL_NOTIFICATION.md](README_EMAIL_NOTIFICATION.md) troubleshooting section
5. Check SMTP configuration and network connectivity
