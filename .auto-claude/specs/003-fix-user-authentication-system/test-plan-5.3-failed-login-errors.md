# Test Plan 5.3: Failed Login Error Messages Verification

## Overview
This test verifies that failed login attempts display appropriate error messages with proper error codes, user-friendly messages, and actionable help text. The LocalLoginServlet implements comprehensive error handling that should provide clear feedback for various failure scenarios.

## Pre-Test Verification

### 1. Verify Compiled Classes Are in Place
Confirm LocalLoginServlet.class and dependencies exist:
```bash
ls -la ./web_portal/tomcat/webapps/iw-business-daemon/WEB-INF/classes/com/interweave/businessDaemon/config/LocalLoginServlet*.class
```

Expected output:
- LocalLoginServlet.class (11,361 bytes)
- LocalLoginServlet$AuthenticationResult.class
- LocalLoginServlet$UserInfo.class

### 2. Verify Error Handling Components
The error display system consists of:

**IWLogin.jsp (lines 54-84):** Error code-specific help messages
```jsp
String getErrorHelp(String code) {
    // Returns context-specific help for each error code
}
```

**IWLogin.jsp (lines 94-112):** Error display UI
- Styled error box with red border
- Error code badge in corner
- Error message
- Context-specific help section

**LocalLoginServlet:** Error generation and redirect
- Lines 64-79: VALIDATION001 (empty credentials)
- Lines 190-199: AUTH001 (invalid credentials)
- Lines 216-224: AUTH002 (inactive user)
- Lines 205-213: AUTH003 (inactive company)
- Lines 140-155: DB001 (database error)

### 3. Verify Tomcat is Running
```bash
# Check if Tomcat is running
netstat -an | findstr :8080
```

If not running, start it:
```bash
./_internal/start_webportal.bat
```

## Test Scenarios

### Test 1: Empty Credentials Validation (VALIDATION001)

**Purpose:** Verify that submitting empty email or password displays appropriate validation error.

#### Test 1a: Both Fields Empty

**Steps:**
1. Navigate to: `http://localhost:8080/iw-business-daemon/IWLogin.jsp`
2. Leave both "Username" and "Password" fields empty
3. Click "Login" button

**Expected Result:**
- Page redirects back to IWLogin.jsp with error parameter
- Error code badge displays: `[VALIDATION001]`
- Error title: "⚠ Login Error"
- Error message: "Please enter both email and password"
- Help section displays:
  ```
  What to do next:
  • Both email and password are required to log in
  • Please fill in all fields before clicking Login
  ```
- Email field remains empty (no pre-fill)

**LocalLoginServlet Logic (lines 64-79):**
```java
if (email == null || email.trim().isEmpty() ||
    password == null || password.trim().isEmpty()) {

    redirectToLogin(request, response, ErrorCode.VALIDATION001,
                   "Please enter both email and password",
                   email, portalBrand, portalSolutions);
}
```

#### Test 1b: Empty Email Only

**Steps:**
1. Navigate to: `http://localhost:8080/iw-business-daemon/IWLogin.jsp`
2. Leave "Username" field empty
3. Enter any value in "Password" field (e.g., "test123")
4. Click "Login" button

**Expected Result:**
- Same error display as Test 1a
- Error code: `[VALIDATION001]`
- Error message: "Please enter both email and password"
- Help text displayed

#### Test 1c: Empty Password Only

**Steps:**
1. Navigate to: `http://localhost:8080/iw-business-daemon/IWLogin.jsp`
2. Enter any value in "Username" field (e.g., "test@example.com")
3. Leave "Password" field empty
4. Click "Login" button

**Expected Result:**
- Same error display as Test 1a
- Error code: `[VALIDATION001]`
- Error message: "Please enter both email and password"
- Email field pre-filled with entered email: "test@example.com"

---

### Test 2: Invalid Credentials (AUTH001)

**Purpose:** Verify that invalid email or wrong password displays appropriate error without revealing which is incorrect (security best practice).

#### Test 2a: Non-Existent User

**Steps:**
1. Navigate to: `http://localhost:8080/iw-business-daemon/IWLogin.jsp`
2. Enter username: `nonexistent@example.com`
3. Enter password: `anypassword123`
4. Click "Login" button

**Expected Result:**
- Error code badge: `[AUTH001]`
- Error title: "⚠ Login Error"
- Error message: "Invalid email or password"
- Help section displays:
  ```
  What to do next:
  • Verify your email address and password are correct
  • Passwords are case-sensitive
  • If you've forgotten your password, use the "Change User Password" link below
  • If you haven't registered, use the registration link above
  ```
- Email field pre-filled with entered email: "nonexistent@example.com"
- Password field cleared (security best practice)

**LocalLoginServlet Logic (lines 244-251):**
```java
// User not found - return generic invalid credentials error
// (don't reveal that the email doesn't exist - security best practice)
return AuthenticationResult.failure(
    ErrorCode.AUTH001,
    "Invalid email or password",
    "User not found in database",
    "Verify your email address and password are correct..."
);
```

#### Test 2b: Correct Email, Wrong Password

**Steps:**
1. Navigate to: `http://localhost:8080/iw-business-daemon/IWLogin.jsp`
2. Enter username: `demo@sample.com` (valid user)
3. Enter password: `wrongpassword123` (incorrect password)
4. Click "Login" button

**Expected Result:**
- Error code badge: `[AUTH001]`
- Error message: "Invalid email or password"
- Same help text as Test 2a
- Email field pre-filled: "demo@sample.com"
- **Critical:** Error message does NOT reveal that email exists (same message as Test 2a)

**LocalLoginServlet Logic (lines 190-199):**
```java
// Verify password first
if (!verifyPassword(password, storedHash)) {
    // Wrong password - return generic invalid credentials error
    // (don't reveal that the email exists - security best practice)
    return AuthenticationResult.failure(
        ErrorCode.AUTH001,
        "Invalid email or password",
        "Password verification failed",
        "Verify your email address and password are correct..."
    );
}
```

**Security Note:** Tests 2a and 2b should display identical error messages to prevent email enumeration attacks.

#### Test 2c: Case-Sensitive Password

**Steps:**
1. Navigate to: `http://localhost:8080/iw-business-daemon/IWLogin.jsp`
2. Enter username: `demo@sample.com`
3. Enter password: `DEMO123` (correct password but wrong case)
4. Click "Login" button

**Expected Result:**
- Error code: `[AUTH001]`
- Error message: "Invalid email or password"
- Help text reminds: "Passwords are case-sensitive"

---

### Test 3: Inactive User Account (AUTH002)

**Purpose:** Verify that inactive user accounts display appropriate error message.

**Prerequisites:** Create an inactive test user (if not exists)
```sql
-- Run this SQL to create inactive user for testing
INSERT INTO users (company_id, email, password, first_name, last_name, role, is_active)
VALUES (2, 'inactive@sample.com', SHA2('test123', 256), 'Inactive', 'User', 'user', FALSE);
```

**Steps:**
1. Navigate to: `http://localhost:8080/iw-business-daemon/IWLogin.jsp`
2. Enter username: `inactive@sample.com`
3. Enter password: `test123`
4. Click "Login" button

**Expected Result:**
- Error code badge: `[AUTH002]`
- Error title: "⚠ Login Error"
- Error message: "Your user account is inactive"
- Help section displays:
  ```
  What to do next:
  • Contact your company administrator to activate your account
  • Check if you received an account activation email
  • Your account may need to be reactivated after a period of inactivity
  ```
- Email field pre-filled: "inactive@sample.com"

**LocalLoginServlet Logic (lines 216-224):**
```java
// Check if user is inactive
if (!userActive) {
    return AuthenticationResult.failure(
        ErrorCode.AUTH002,
        "Your user account is inactive",
        "User account disabled",
        "Contact your company administrator to activate your account..."
    );
}
```

**Important:** This error should only appear AFTER password verification succeeds (security: don't reveal account status without valid password).

---

### Test 4: Inactive Company Account (AUTH003)

**Purpose:** Verify that users from inactive companies display appropriate error message.

**Prerequisites:** Create a company with inactive status (if not exists)
```sql
-- Run this SQL to create inactive company for testing
INSERT INTO companies (id, company_name, solution_type, is_active)
VALUES (999, 'Inactive Company', 'standard', FALSE);

INSERT INTO users (company_id, email, password, first_name, last_name, role, is_active)
VALUES (999, 'user@inactive-company.com', SHA2('test123', 256), 'Test', 'User', 'user', TRUE);
```

**Steps:**
1. Navigate to: `http://localhost:8080/iw-business-daemon/IWLogin.jsp`
2. Enter username: `user@inactive-company.com`
3. Enter password: `test123`
4. Click "Login" button

**Expected Result:**
- Error code badge: `[AUTH003]`
- Error title: "⚠ Login Error"
- Error message: "Your company account is inactive"
- Help section displays:
  ```
  What to do next:
  • Contact your company administrator for assistance
  • Your company's subscription may have expired
  • Contact InterWeave support if you need immediate access
  ```
- Email field pre-filled: "user@inactive-company.com"

**LocalLoginServlet Logic (lines 205-213):**
```java
// Check if company is inactive
if (!companyActive) {
    return AuthenticationResult.failure(
        ErrorCode.AUTH003,
        "Your company account is inactive",
        "Company account disabled or suspended",
        "Contact your company administrator or InterWeave support..."
    );
}
```

**Important:** This error should only appear AFTER password verification succeeds and user account is active.

---

## Error Display Validation

### Visual Elements Checklist

For each error message, verify:

- [ ] **Error Box Styling** (IWLogin.jsp lines 13-17)
  - Red background (#ffeeee)
  - Red border (1px solid #cc0000)
  - Proper padding (12px)
  - Border radius (4px)

- [ ] **Error Code Badge** (line 99)
  - Displayed in top-right corner of error box
  - Monospace font
  - Format: `[ERRORCODE]` (e.g., `[AUTH001]`)
  - Gray color (#999)

- [ ] **Error Title** (line 101)
  - Warning symbol: "⚠ Login Error"
  - Dark red color (#990000)
  - 14px font size
  - Bold text

- [ ] **Error Message** (line 102)
  - Clear, user-friendly text
  - 12px font size
  - Proper line height (18px)

- [ ] **Help Section** (lines 103-108)
  - Only displays when help text exists for error code
  - White background
  - Left border (3px solid #cc0000)
  - "What to do next:" heading
  - Bulleted list of actionable steps

### Form State Validation

After each error, verify:

- [ ] **Email Persistence**
  - Email field pre-filled with entered value
  - Allows user to correct typos without re-typing

- [ ] **Password Cleared**
  - Password field cleared (security best practice)
  - User must re-enter password

- [ ] **URL Parameters**
  - Error message in URL: `?error=...`
  - Error code in URL: `&errorCode=...`
  - Email in URL: `&Email=...`
  - Portal parameters preserved: `&PortalBrand=...&PortalSolutions=...`

---

## Acceptance Criteria Checklist

### ✅ Invalid Credentials Show Appropriate Error Message

- [ ] **Test 2a (non-existent user):** AUTH001 error displayed
- [ ] **Test 2b (wrong password):** AUTH001 error displayed
- [ ] **Test 2c (case-sensitive):** AUTH001 error displayed
- [ ] Error message is generic and doesn't reveal email existence
- [ ] Help text provides actionable steps (password reset, registration)
- [ ] Error code badge displays correctly
- [ ] Email field pre-filled, password field cleared

### ✅ Empty Credentials Show Validation Error

- [ ] **Test 1a (both empty):** VALIDATION001 error displayed
- [ ] **Test 1b (email empty):** VALIDATION001 error displayed
- [ ] **Test 1c (password empty):** VALIDATION001 error displayed
- [ ] Error message explains both fields are required
- [ ] Help text instructs to fill all fields
- [ ] Error code badge displays correctly

### ✅ Inactive Account Shows Appropriate Message

- [ ] **Test 3 (inactive user):** AUTH002 error displayed
- [ ] Error message clearly states account is inactive
- [ ] Help text directs to company administrator
- [ ] Error only appears after password verification (security)
- [ ] Error code badge displays correctly

- [ ] **Test 4 (inactive company):** AUTH003 error displayed
- [ ] Error message clearly states company account is inactive
- [ ] Help text mentions subscription expiration and support contact
- [ ] Error only appears after password verification (security)
- [ ] Error code badge displays correctly

---

## Tomcat Log Verification

For each failed login, check Tomcat logs for proper error logging:

```bash
tail -50 ./web_portal/tomcat/logs/catalina.out
```

**Expected Log Entries:**

### VALIDATION001
```
INFO: LocalLoginServlet: Error VALIDATION001 logged
```

### AUTH001
```
INFO: Authentication failed: Invalid credentials
```
**Note:** Email should NOT be logged for AUTH001 (security - prevent info leakage)

### AUTH002
```
INFO: Authentication failed for inactive@sample.com: Your user account is inactive
```

### AUTH003
```
INFO: Authentication failed for user@inactive-company.com: Your company account is inactive
```

**Error Logger Integration:**
Each error creates an IWError object logged via ErrorLogger (lines 67-74, 120-127, 142-150).

---

## Security Verification

### Critical Security Checks

- [ ] **Email Enumeration Prevention**
  - AUTH001 error message identical for non-existent user and wrong password
  - No distinction between "user not found" and "wrong password" in UI
  - Logs don't reveal whether email exists (for AUTH001 only)

- [ ] **Account Status Disclosure**
  - AUTH002/AUTH003 only appear AFTER password verification succeeds
  - Prevents attackers from discovering inactive accounts without valid password
  - See LocalLoginServlet lines 189-224 (password checked first, then status)

- [ ] **Password Field Clearing**
  - Password field always cleared after failed login
  - Prevents shoulder-surfing attacks
  - Password never appears in URL or logs

- [ ] **Error Code Information Disclosure**
  - Error codes are informative but not overly revealing
  - Help text guides legitimate users without aiding attackers
  - Balance between usability and security

---

## Troubleshooting

### Issue: No error message appears

**Possible Causes:**
1. Tomcat not restarted after LocalLoginServlet compilation
2. IWLogin.jsp not processing error parameter
3. Servlet not redirecting correctly

**Debug Steps:**
1. Check URL after failed login - should contain `?error=...&errorCode=...`
2. View page source - error display div should be present (lines 94-112)
3. Check Tomcat logs for servlet errors
4. Restart Tomcat: `./_internal/stop_webportal.bat` then `./_internal/start_webportal.bat`

### Issue: Wrong error code displayed

**Debug Steps:**
1. Check LocalLoginServlet logic - verify error conditions (lines 64-255)
2. Verify database user/company status matches test expectations
3. Check Tomcat logs for error details
4. Run SQL to verify test data:
   ```sql
   SELECT u.email, u.is_active as user_active, c.is_active as company_active, u.role
   FROM users u
   JOIN companies c ON u.company_id = c.id
   WHERE u.email = 'test-email@here.com';
   ```

### Issue: Error styling not applied

**Check:**
1. IWLogin.jsp CSS styles present (lines 8-19)
2. Browser cache cleared (Ctrl+F5)
3. JSP error display div structure correct (lines 94-112)

### Issue: Help text not displaying

**Check:**
1. IWLogin.jsp getErrorHelp() function (lines 54-81)
2. Error code matches expected codes: AUTH001, AUTH002, AUTH003, DB001, VALIDATION001
3. JSP conditional logic (line 103): `<% if(errorHelp != null) { %>`

---

## Test Execution Record

### Test Execution: [DATE/TIME]

**Pre-Test Verification:**
- [ ] Compiled classes present (LocalLoginServlet.class - 11,361 bytes)
- [ ] Tomcat running on port 8080
- [ ] Login page accessible
- [ ] Test users created (inactive user, inactive company user)

**Test 1: Empty Credentials (VALIDATION001)**
- [ ] 1a: Both fields empty - PASS / FAIL
- [ ] 1b: Email empty - PASS / FAIL
- [ ] 1c: Password empty - PASS / FAIL

**Test 2: Invalid Credentials (AUTH001)**
- [ ] 2a: Non-existent user - PASS / FAIL
- [ ] 2b: Wrong password - PASS / FAIL
- [ ] 2c: Case-sensitive - PASS / FAIL
- [ ] Security check: Messages identical for 2a and 2b - PASS / FAIL

**Test 3: Inactive User (AUTH002)**
- [ ] Error displays correctly - PASS / FAIL
- [ ] Help text appropriate - PASS / FAIL
- [ ] Only appears after password verification - PASS / FAIL

**Test 4: Inactive Company (AUTH003)**
- [ ] Error displays correctly - PASS / FAIL
- [ ] Help text appropriate - PASS / FAIL
- [ ] Only appears after password verification - PASS / FAIL

**Visual Validation:**
- [ ] Error box styling correct (all tests)
- [ ] Error code badge displayed (all tests)
- [ ] Help section displayed with proper formatting (all tests)
- [ ] Email persistence working (all tests)
- [ ] Password cleared after error (all tests)

**Security Validation:**
- [ ] Email enumeration prevention verified
- [ ] Account status disclosure prevention verified
- [ ] Password field clearing verified
- [ ] Tomcat logs don't leak sensitive info

**Overall Status:** [ ] PASS / [ ] FAIL

**Notes:**
_[Record any observations, issues, or deviations from expected behavior]_

---

## Quick Test Commands

```bash
# 1. Ensure Tomcat is running
./_internal/start_webportal.bat

# 2. Open browser to login page
# http://localhost:8080/iw-business-daemon/IWLogin.jsp

# 3. Test each scenario as documented above

# 4. Check logs if issues
tail -50 ./web_portal/tomcat/logs/catalina.out

# 5. Create test users if needed (run in MySQL/PostgreSQL)
# See "Prerequisites" sections for SQL commands
```

---

## Success Indicators

✅ All error codes display correctly (VALIDATION001, AUTH001, AUTH002, AUTH003)
✅ Error messages are user-friendly and actionable
✅ Help text provides next steps for each error type
✅ Error styling is consistent and visible
✅ Email field persists after errors
✅ Password field cleared after errors
✅ Security best practices followed (no email enumeration, status disclosure)
✅ Tomcat logs show proper error logging without sensitive data leakage

---

## Next Steps

After this test passes:
- All authentication testing complete (subtasks 5.1, 5.2, 5.3)
- Ready for final QA sign-off
- Consider production deployment of authentication fixes

---

## Reference Documentation

**LocalLoginServlet Error Handling:**
- Lines 64-79: VALIDATION001 implementation
- Lines 190-199: AUTH001 for wrong password
- Lines 216-224: AUTH002 for inactive user
- Lines 205-213: AUTH003 for inactive company
- Lines 244-251: AUTH001 for non-existent user
- Lines 140-155: DB001 for database errors
- Lines 319-342: redirectToLogin() method

**IWLogin.jsp Error Display:**
- Lines 8-19: CSS styles for error messages
- Lines 54-84: getErrorHelp() function
- Lines 94-112: Error display HTML structure
- Lines 28-30: Error parameter extraction

**Error Framework:**
- ErrorCode enum: Defines all error codes
- IWError class: Structured error object
- ErrorLogger: Logs errors to Tomcat console
- IWErrorBuilder: Fluent API for error creation
