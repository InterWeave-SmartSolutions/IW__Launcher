# ADR 003: Local Servlet Bridge for User/Company Management

## Status

Implemented

## Context

The InterWeave web portal's original compiled servlets (`RegistrationServlet`, `CompanyRegistrationServlet`, `ChangePasswordServlet`, etc.) depend on the `iwtransformationserver` webapp and InterWeave's `TransactionContext` transformation engine to process user and company management operations. This transformation server is **not deployed** in the standalone IW_IDE environment — only the `iw-business-daemon` webapp is present.

Without the transformation server, all user/company management operations (registration, login, profile editing, password changes) fail at runtime because the original servlets attempt HTTP calls to `iwtransformationserver` endpoints that don't exist.

The `LocalLoginServlet` was previously created (ADR not numbered) to solve the authentication-specific case by querying the database directly. However, the remaining 8 management servlets still pointed to the original compiled classes.

## Decision

Create **local SQL-based servlet replacements** for all 8 user/company management servlets, following the same pattern established by `LocalLoginServlet`:

1. Each local servlet extends a shared `LocalUserManagementServlet` base class
2. Operations execute direct SQL against Supabase Postgres (via the `jdbc/IWDB` JNDI DataSource)
3. The `web.xml` servlet-class entries are swapped to point to the local implementations
4. Original compiled `.class` files are preserved — reverting requires only changing `web.xml` back

### Servlets Created

| Original Servlet | Local Replacement | Purpose |
|---|---|---|
| LoginServlet | LocalLoginServlet | Authentication + session setup |
| RegistrationServlet | LocalRegistrationServlet | User registration under existing company |
| CompanyRegistrationServlet | LocalCompanyRegistrationServlet | Company + admin user registration |
| ChangePasswordServlet | LocalChangePasswordServlet | User password change |
| ChangeCompanyPasswordServlet | LocalChangeCompanyPasswordServlet | Company admin password change |
| EditProfileServlet | LocalEditProfileServlet | Load user profile for editing |
| SaveProfileServlet | LocalSaveProfileServlet | Persist user profile changes |
| EditCompanyProfileServlet | LocalEditCompanyProfileServlet | Load company profile for editing |
| SaveCompanyProfileServlet | LocalSaveCompanyProfileServlet | Persist company profile changes |

### Base Class: `LocalUserManagementServlet`

Provides shared infrastructure:
- JNDI DataSource initialization (`jdbc/IWDB`)
- SHA-256 password hashing (compatible with Postgres `encode(sha256(...))`)
- Password verification (supports both plain text and hashed)
- Error redirect helper (forwards to `ErrorMessage.jsp`)
- `setThreadField()` — reflection-based setter for `TransactionThread` private fields (`firstName`, `lastName`, `company`, `title`) which have getters but no public setters

### Key Technical Details

- **TransactionThread reflection**: The compiled `TransactionThread` class has `getFirstName()`, `getLastName()`, `getCompany()`, `getTitle()` methods reading from private fields, but no corresponding setters. The edit servlets use Java reflection to populate these fields so the JSPs can read them.
- **ConfigContext flags**: `setHosted(true)` and `setAdminLoggedIn(true)` must be set for `CompanyConfiguration.jsp` to render its configuration UI (otherwise it shows error pages).
- **JSP parameter naming**: Forms use `CompanyOrganization` (not `Company`) and `Type` (not `SolutionType`) — the local servlets match these exact names.

## Consequences

### Positive

- **Full standalone operation**: All user/company management flows work without the transformation server
- **Direct SQL**: Operations are faster and more debuggable than the original transformation-engine approach
- **Reversible**: Swapping back to original servlets requires only `web.xml` changes when `iwtransformationserver` is eventually deployed
- **Shared base class**: Consistent patterns across all 9 servlets reduce maintenance burden

### Negative

- **Bypasses transformation engine**: The local servlets don't use InterWeave's data transformation pipeline, meaning any business logic embedded in the transformation flows is skipped
- **Reflection dependency**: Setting `TransactionThread` private fields via reflection is fragile — if the compiled class changes field names, the reflection calls will silently fail
- **Dual maintenance**: Until the transformation server is deployed, changes to user management logic must be made in the local servlets AND eventually reconciled with the original transformation-based approach
- **Static ConfigContext**: `ConfigContext` uses static fields, meaning the servlet bridge is not safe for concurrent multi-user access (same limitation as the original architecture)
