# Local Servlet Bridge — Technical Reference

> **ADR**: `docs/adr/003-local-servlet-bridge.md`
> **Source**: `web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/businessDaemon/config/Local*.java`
> **Classes**: `web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes/com/interweave/businessDaemon/config/Local*.class`

## Overview

The local servlet bridge replaces 9 original compiled servlets that depend on the missing `iwtransformationserver` webapp. Each local servlet executes direct SQL against Supabase Postgres via the `jdbc/IWDB` JNDI DataSource.

## Architecture

```
JSP Form → Local*Servlet → JDBC (Supabase Postgres) → Redirect/Forward to JSP
                ↓
    LocalUserManagementServlet (base class)
    - DataSource init (jdbc/IWDB)
    - hashPassword() — SHA-256 hex
    - verifyPassword() — supports plain text + hashed
    - redirectToError() → ErrorMessage.jsp
    - redirectToLogin() → IWLogin.jsp
    - setThreadField() — reflection for TransactionThread private fields
    - param() — null-safe request parameter extraction
```

## Servlet Reference

### LocalLoginServlet
- **URL**: `/LoginServlet` (POST)
- **JSP**: `IWLogin.jsp`
- **Parameters**: `Email`, `Password`, `PortalBrand`, `PortalSolutions`
- **Actions**:
  - Authenticates user against `users` + `companies` tables
  - Sets session attributes: userId, userEmail, userName, companyId, companyName, role, solutionType
  - Sets `ConfigContext.setHosted(true)`, `setAdminLoggedIn(true)`, `setLoggedUserType()`
  - Creates TransactionThreads in `companyRegistration`, `updateCompany`, `requestCompany` contexts
- **Success redirect**: `CompanyConfiguration.jsp?CurrentProfile={company}:{email}&Solution={type}`
- **Note**: Does NOT extend `LocalUserManagementServlet` (predates the base class)

### LocalRegistrationServlet
- **URL**: `/RegistrationServlet` (POST)
- **JSP**: `Registration.jsp`
- **Parameters**: `CompanyOrganization`, `Email`, `FirstName`, `LastName`, `Password`, `ConfirmPassword`, `Title`, `Token`
- **Actions**:
  - Looks up company by name (case-insensitive)
  - Checks email uniqueness
  - INSERTs into `users` with role='user'
- **Success redirect**: `IWLogin.jsp?success=...`

### LocalCompanyRegistrationServlet
- **URL**: `/CompanyRegistrationServlet` (POST)
- **JSP**: `CompanyRegistration.jsp`
- **Parameters**: `CompanyOrganization`, `Email`, `FirstName`, `LastName`, `Password`, `ConfirmPassword`, `SolutionType`
- **Actions** (transactional):
  - Checks company name uniqueness
  - Checks email uniqueness
  - INSERTs into `companies`
  - INSERTs admin user into `users` with role='admin'
- **Success redirect**: `IWLogin.jsp?success=...`

### LocalChangePasswordServlet
- **URL**: `/ChangePasswordServlet` (POST)
- **JSP**: `ChangePassword.jsp`
- **Parameters**: `Email`, `Password` (old), `NewPassword`, `ConfirmPassword`
- **Actions**:
  - Verifies old password against `users.password`
  - UPDATEs `users.password` with new hash
- **Success redirect**: `IWLogin.jsp?success=...`

### LocalChangeCompanyPasswordServlet
- **URL**: `/ChangeCompanyPasswordServlet` (POST)
- **JSP**: `ChangeCompanyPassword.jsp`
- **Parameters**: `Company`, `Email`, `Password` (old), `NewPassword`, `ConfirmPassword`
- **Actions**:
  - Verifies admin user via company name + email + role='admin'
  - Verifies old password
  - UPDATEs `companies.password` with new hash
- **Success redirect**: `IWLogin.jsp?success=...`

### LocalEditProfileServlet
- **URL**: `/EditProfileServlet` (POST)
- **JSP**: `EditProfile.jsp`
- **Parameters**: `Email`, `Password`, `PortalBrand`, `PortalSolutions`
- **Actions**:
  - Authenticates user
  - Loads profile from `users` + `companies`
  - Creates TransactionThread in `requestUser` context
  - Sets private fields via reflection: `firstName`, `lastName`, `company`, `title`
  - Sets `ConfigContext.setUserLoggedIn(true)`
- **Success redirect**: `EditProfile.jsp?CurrentProfile={company}:{email}&Email={email}`

### LocalSaveProfileServlet
- **URL**: `/SaveProfileServlet` (POST)
- **JSP**: `EditProfile.jsp` (save form)
- **Parameters**: `Email`, `FirstName`, `LastName`
- **Actions**:
  - UPDATEs `users.first_name`, `users.last_name`
- **Success redirect**: `IWLogin.jsp?success=...`

### LocalEditCompanyProfileServlet
- **URL**: `/EditCompanyProfileServlet` (POST)
- **JSP**: `EditCompanyProfile.jsp`
- **Parameters**: `Company`, `Email`, `Password`, `PortalBrand`, `PortalSolutions`
- **Actions**:
  - Authenticates admin user via company + email + role='admin'
  - Loads profile from `users` + `companies`
  - Creates TransactionThread in `requestCompany` context
  - Sets private fields via reflection: `firstName`, `lastName`, `company`, `title`
  - Sets `ConfigContext.setUserLoggedIn(true)`
- **Success redirect**: `EditCompanyProfile.jsp?CurrentProfile={company}:{email}&Email={email}&Company={company}`

### LocalSaveCompanyProfileServlet
- **URL**: `/SaveCompanyProfileServlet` (POST)
- **JSP**: `EditCompanyProfile.jsp` (save form)
- **Parameters**: `CompanyOrganization`, `Email`, `FirstName`, `LastName`, `Type`
- **Actions**:
  - UPDATEs admin user's `first_name`, `last_name`
  - UPDATEs `companies.solution_type` if provided
  - Sets `ConfigContext.setAdminLoggedIn(true)`
- **Success redirect**: `CompanyConfiguration.jsp?CurrentProfile=...&Solution=...`
- **⚠️ Note**: JSP sends `CompanyOrganization` not `Company`, and `Type` not `SolutionType`

## ConfigContext Flags

These static flags must be set for JSPs to render correctly:

- `ConfigContext.setHosted(true)` — Required by `CompanyConfiguration.jsp` (line 153). Set during login.
- `ConfigContext.setAdminLoggedIn(true)` — Required by `CompanyConfiguration.jsp` (line 151). Set during login and after save company profile.
- `ConfigContext.setUserLoggedIn(true)` — Required by `EditProfile.jsp` and `EditCompanyProfile.jsp` to enable form fields. Set by edit servlets; the JSP reads then immediately resets to false.

## TransactionThread Reflection

`TransactionThread` has these private fields with getters but **no setters**:
- `firstName` → `getFirstName()`
- `lastName` → `getLastName()`
- `company` → `getCompany()`
- `title` → `getTitle()`

The base class provides `setThreadField(Object tt, String fieldName, String value)` which uses `Field.setAccessible(true)` to write these fields. The `setEmail()` setter exists and is used directly.

## Compilation

```bash
javac -source 1.8 -target 1.8 \
  -cp "web_portal/tomcat/lib/servlet-api.jar:web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes:web_portal/tomcat/lib/*" \
  -d web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes \
  web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/src/com/interweave/businessDaemon/config/Local*.java
```

## Reverting to Original Servlets

When `iwtransformationserver` is deployed, revert by editing `web.xml`:

```xml
<!-- Change FROM: -->
<servlet-class>com.interweave.businessDaemon.config.LocalRegistrationServlet</servlet-class>
<!-- Change TO: -->
<servlet-class>com.interweave.businessDaemon.config.RegistrationServlet</servlet-class>
```

Apply the same pattern for all 8 servlet entries (LoginServlet can remain local or be reverted). The original compiled `.class` files remain in `WEB-INF/classes/` and will be used immediately after the `web.xml` change and Tomcat restart.

## Database Tables Used

- `companies` — id, company_name, email, password, solution_type, is_active
- `users` — id, company_id (FK), email, password, first_name, last_name, role, is_active, last_login

All queries use case-insensitive matching: `LOWER(column) = LOWER(?)`.
Password storage: SHA-256 hex string (64 characters).
