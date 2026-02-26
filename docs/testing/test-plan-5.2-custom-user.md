# Test Plan 5.2: Custom User Account Authentication

**Subtask ID:** 5.2
**Phase:** Testing & Verification
**Status:** Ready for Testing
**Date:** 2026-01-09

## Objective

Verify that custom user accounts (demo@sample.com / demo123) can successfully authenticate using the updated LocalLoginServlet implementation.

## Test Scope

- Custom user login with email/password credentials
- Session creation and attribute persistence
- Role-based functionality for non-admin users
- Access to protected pages after successful authentication
- Logout and session cleanup

## Pre-Test Verification

Before starting the test, verify these prerequisites are in place:

### 1. Compiled Classes
```bash
# Check LocalLoginServlet.class exists and is recent
ls -lh ./web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes/com/interweave/businessDaemon/config/LocalLoginServlet.class
# Expected: LocalLoginServlet.class (11,361 bytes) from recent compilation
```

### 2. Database Schema
```sql
-- Verify demo user exists in database
SELECT id, company_id, email, first_name, last_name, role, is_active
FROM users
WHERE email = 'demo@sample.com';

-- Expected result:
-- id: 2
-- company_id: 2
-- email: demo@sample.com
-- first_name: Demo
-- last_name: User
-- role: user
-- is_active: 1

-- Verify password hash is correct (SHA-256 hex of 'demo123')
SELECT password, LENGTH(password) as hash_length
FROM users
WHERE email = 'demo@sample.com';

-- Expected:
-- hash_length: 64 (SHA-256 hex produces 64 character string)
-- password: Should match SHA2('demo123', 256) output
```

### 3. Web Configuration
```bash
# Verify web.xml is configured to use LocalLoginServlet
grep -A 2 "servlet-name>LoginServlet" ./web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/web.xml

# Expected:
# <servlet-class>com.interweave.businessDaemon.config.LocalLoginServlet</servlet-class>
```

## Test Procedure

### Step 1: Restart Tomcat
```bash
# Stop Tomcat (if running)
./scripts/stop_webportal.bat

# Start Tomcat
./scripts/start_webportal.bat

# Wait for Tomcat to fully start (15-20 seconds)
# Monitor logs: tail -f ./web_portal/tomcat/logs/catalina.out
```

### Step 2: Navigate to Login Page
1. Open web browser
2. Navigate to: `http://localhost:9090/iw-business-daemon/IWLogin.jsp`
3. Verify login form displays correctly

### Step 3: Attempt Login with Demo Account
1. Enter credentials:
   - **Email:** `demo@sample.com`
   - **Password:** `demo123`
2. Click "Login" button
3. Observe the response

### Step 4: Verify Successful Redirect
**Expected Behavior:**
- Browser redirects to: `http://localhost:9090/iw-business-daemon/CompanyConfiguration.jsp`
- No error messages displayed
- Page loads successfully

### Step 5: Verify Session Attributes
Check that session contains expected user information:

**Expected Session Attributes:**
```
userId: 2
email: demo@sample.com
firstName: Demo
lastName: User
companyId: 2
companyName: Demo Company Inc.
companyCode: DEMO001
role: user
isAdmin: false
```

**Verification Method:**
- Use browser developer tools > Application/Storage > Cookies
- Look for JSESSIONID cookie
- Check session storage/attributes if available

### Step 6: Test Protected Page Access
1. Navigate to a protected page (e.g., CompanyConfiguration.jsp)
2. Verify page loads without requiring re-authentication
3. Verify user-specific information displays correctly

### Step 7: Check Tomcat Logs
Review the Tomcat logs for authentication confirmation:

```bash
# View recent Tomcat logs
tail -n 50 ./web_portal/tomcat/logs/catalina.out

# Or check for specific log entries
grep "demo@sample.com" ./web_portal/tomcat/logs/catalina.out
```

**Expected Log Entries:**
- `Authentication successful for user: demo@sample.com`
- `User role: user`
- `Company: Demo Company Inc. (DEMO001)`
- No error stack traces
- No authentication failures

### Step 8: Test Logout
1. Click logout button (if available)
2. Verify redirect to login page
3. Attempt to access protected page directly
4. Verify redirect back to login page (session cleared)

## Password Verification Logic

The LocalLoginServlet uses the following verification process:

**Hash Algorithm (Line 285-298):**
- Uses Java MessageDigest with SHA-256
- Converts byte array to lowercase hexadecimal string
- Produces 64-character hex string matching MySQL SHA2() output

**Verification Process (Line 261-278):**
1. Check if stored hash is null/empty → fail
2. Check plain text comparison (for testing) → succeed if match
3. Hash input password with SHA-256
4. Compare hashed input with stored hash → succeed if match

**Database Stored Value:**
- Demo user password: SHA2('demo123', 256)
- Format: Lowercase 64-character hexadecimal string
- Matches LocalLoginServlet.hashPassword() output

## Expected Results

### Successful Login Indicators:
✅ Login form submits without errors
✅ Browser redirects to CompanyConfiguration.jsp
✅ Session cookie (JSESSIONID) created
✅ User information displayed on page
✅ No error messages in browser or logs
✅ Protected pages accessible without re-login

### Session Data Verification:
✅ userId = 2
✅ email = demo@sample.com
✅ role = user
✅ isAdmin = false
✅ companyId = 2
✅ companyName = Demo Company Inc.

### Tomcat Logs Show:
✅ "Authentication successful for user: demo@sample.com"
✅ No SQLException errors
✅ No password verification failures
✅ Session created successfully

## Troubleshooting

### Issue: Login Fails with "Invalid Credentials"

**Possible Causes:**
1. Password hash mismatch
2. User not found in database
3. User account is inactive

**Resolution Steps:**
```sql
-- 1. Verify user exists and is active
SELECT * FROM users WHERE email = 'demo@sample.com';

-- 2. Check password hash format
SELECT password, LENGTH(password) FROM users WHERE email = 'demo@sample.com';
-- Should be 64 characters (hex SHA-256)

-- 3. Manually verify hash matches
SELECT SHA2('demo123', 256) as expected_hash, password as stored_hash
FROM users WHERE email = 'demo@sample.com';
-- Both values should match exactly

-- 4. Update password if needed
UPDATE users SET password = SHA2('demo123', 256) WHERE email = 'demo@sample.com';
```

### Issue: Login Succeeds but Redirect Fails

**Possible Causes:**
1. CompanyConfiguration.jsp missing or misconfigured
2. Session not properly created
3. Portal parameters malformed

**Resolution Steps:**
1. Check Tomcat logs for redirect errors
2. Verify CompanyConfiguration.jsp exists
3. Check session attributes are set correctly
4. Review LocalLoginServlet lines 107-117 for redirect logic

### Issue: Session Not Persisting

**Possible Causes:**
1. Cookie blocked by browser
2. Session timeout too short
3. Tomcat session configuration issue

**Resolution Steps:**
1. Check browser cookie settings
2. Verify JSESSIONID cookie exists
3. Check Tomcat session timeout in web.xml
4. Review browser console for cookie errors

### Issue: User Shown as Admin (Wrong Role)

**Possible Causes:**
1. Database role field incorrect
2. Role mapping logic error

**Resolution Steps:**
```sql
-- Verify user role is set correctly
SELECT email, role, is_active FROM users WHERE email = 'demo@sample.com';
-- role should be 'user', not 'admin'

-- Update role if needed
UPDATE users SET role = 'user' WHERE email = 'demo@sample.com';
```

## Acceptance Criteria Checklist

Before marking this subtask as complete, verify all criteria are met:

- [ ] **Custom user login succeeds**
  - Demo user (demo@sample.com / demo123) can log in without errors
  - Authentication completes within 2 seconds
  - No error messages displayed to user

- [ ] **Session attributes are properly set**
  - Session contains userId, email, firstName, lastName
  - Session contains companyId, companyName, companyCode
  - Session contains role = 'user' and isAdmin = false
  - Session persists across page navigation

- [ ] **User can access protected pages after login**
  - CompanyConfiguration.jsp loads successfully
  - Other protected pages accessible without re-login
  - Session remains active during normal usage
  - No unexpected redirects to login page

## Test Data Summary

**Test Account:**
- Email: demo@sample.com
- Password: demo123
- Role: user
- Company: Demo Company Inc. (DEMO001)
- Expected Behavior: Standard user access (non-admin)

**Database Records:**
- User ID: 2
- Company ID: 2
- Active Status: Yes (1)
- Password Hash: SHA2('demo123', 256)

## Notes

- This test verifies non-admin user functionality
- Demo user has 'user' role, not 'admin' role
- Redirect behavior is same as admin (CompanyConfiguration.jsp)
- Role-based feature restrictions not tested here (covered in functional tests)
- Password hash format must match MySQL SHA2() output (64-char hex)

## Manual Testing Required

This test plan requires manual execution by a tester who can:
1. Access the web browser
2. View Tomcat logs
3. Query the MySQL database
4. Inspect browser cookies and session data

Follow each step in sequence and document results. If any step fails, use the troubleshooting guide to diagnose and resolve the issue before proceeding.

---

**Test Plan Created:** 2026-01-09
**Related Subtasks:** 5.1 (Admin account), 5.3 (Failed login errors)
**Dependencies:** Database schema (4.1), Compiled servlet (3.1), Web.xml config (2.1)
