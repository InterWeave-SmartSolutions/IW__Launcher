#!/bin/bash
# =============================================================================
# IW Web Portal - End-to-End Test Script
# Usage: bash test_portal.sh [BASE_URL]
# Default: http://localhost:9090/iw-business-daemon
# =============================================================================

BASE_URL="${1:-http://localhost:9090/iw-business-daemon}"
COOKIE_JAR="/tmp/iw_test_cookies.txt"

TS=$(date +%s)
COMPANY="TestCorp_${TS}"
ADMIN_EMAIL="admin_${TS}@testcorp.com"
USER_EMAIL="user_${TS}@testcorp.com"
PASSWORD="TestPass123"
NEW_PASSWORD="NewPass456"
SOLUTION="SF2QB"

PASS=0; FAIL=0; TOTAL=0
GREEN='\033[0;32m'; RED='\033[0;31m'; YELLOW='\033[1;33m'; NC='\033[0m'

cleanup() { rm -f "$COOKIE_JAR" /tmp/iw_headers.txt /tmp/iw_body.txt; }
trap cleanup EXIT

log_test() {
    TOTAL=$((TOTAL + 1))
    if [ "$2" = "PASS" ]; then
        PASS=$((PASS + 1)); echo -e "  ${GREEN}✓${NC} $1"
    else
        FAIL=$((FAIL + 1)); echo -e "  ${RED}✗${NC} $1"
        [ -n "$3" ] && echo -e "    ${YELLOW}$3${NC}"
    fi
}

do_post() {
    local url="$1"; shift
    curl -s -c "$COOKIE_JAR" -b "$COOKIE_JAR" -D /tmp/iw_headers.txt \
         -o /tmp/iw_body.txt -w "%{http_code}" -X POST "$url" "$@" 2>/dev/null
}
do_get() {
    curl -s -c "$COOKIE_JAR" -b "$COOKIE_JAR" -D /tmp/iw_headers.txt \
         -o /tmp/iw_body.txt -w "%{http_code}" "$1" 2>/dev/null
}
get_redirect() { grep -i "^Location:" /tmp/iw_headers.txt 2>/dev/null | sed 's/Location: //i' | tr -d '\r\n'; }
body_contains() { grep -qi "$1" /tmp/iw_body.txt 2>/dev/null; }

echo "============================================="
echo " IW Web Portal - End-to-End Tests"
echo "============================================="
echo " URL:     $BASE_URL"
echo " Company: $COMPANY"
echo " Admin:   $ADMIN_EMAIL"
echo " User:    $USER_EMAIL"
echo "============================================="

# Connectivity
echo ""; echo "Connectivity Check"
echo "---------------------------------------------"
code=$(do_get "$BASE_URL/IWLogin.jsp")
if [ "$code" = "200" ]; then
    log_test "Tomcat reachable" "PASS"
else
    log_test "Tomcat reachable" "FAIL" "HTTP $code"
    echo -e "\n${RED}Cannot reach Tomcat at $BASE_URL. Aborting.${NC}"; exit 1
fi

# Phase 1: Pages
echo ""; echo "Phase 1: Page Accessibility"
echo "---------------------------------------------"
for p in IWLogin.jsp Registration.jsp CompanyRegistration.jsp EditProfile.jsp \
         EditCompanyProfile.jsp ChangePassword.jsp ChangeCompanyPassword.jsp; do
    code=$(do_get "$BASE_URL/$p")
    [ "$code" = "200" ] && log_test "GET $p" "PASS" || log_test "GET $p" "FAIL" "HTTP $code"
done

# Phase 2: Company Registration
echo ""; echo "Phase 2: Company Registration"
echo "---------------------------------------------"
code=$(do_post "$BASE_URL/CompanyRegistrationServlet" \
    -d "CompanyOrganization=$COMPANY" -d "Email=$ADMIN_EMAIL" \
    -d "FirstName=Admin" -d "LastName=Tester" \
    -d "Password=$PASSWORD" -d "ConfirmPassword=$PASSWORD" -d "SolutionType=$SOLUTION")
redirect=$(get_redirect)
echo "$redirect" | grep -qi "success\|IWLogin" \
    && log_test "Register company" "PASS" \
    || log_test "Register company" "FAIL" "HTTP $code | $redirect"

do_post "$BASE_URL/CompanyRegistrationServlet" \
    -d "CompanyOrganization=$COMPANY" -d "Email=dup@t.com" \
    -d "FirstName=D" -d "LastName=T" \
    -d "Password=$PASSWORD" -d "ConfirmPassword=$PASSWORD" -d "SolutionType=$SOLUTION" >/dev/null
(body_contains "already exists" || ! echo "$(get_redirect)" | grep -qi "success") \
    && log_test "Reject duplicate company" "PASS" \
    || log_test "Reject duplicate company" "FAIL"

# Phase 3: Admin Login
echo ""; echo "Phase 3: Admin Login"
echo "---------------------------------------------"
rm -f "$COOKIE_JAR"
code=$(do_post "$BASE_URL/LoginServlet" -d "Email=$ADMIN_EMAIL" -d "Password=$PASSWORD")
redirect=$(get_redirect)
echo "$redirect" | grep -qi "CompanyConfiguration" \
    && log_test "Admin login" "PASS" \
    || log_test "Admin login" "FAIL" "HTTP $code | $redirect"

if [ -n "$redirect" ]; then
    code=$(do_get "$BASE_URL/$redirect")
    ([ "$code" = "200" ] && body_contains "configuration\|Object Selection") \
        && log_test "CompanyConfiguration loads after login" "PASS" \
        || log_test "CompanyConfiguration loads after login" "FAIL" "HTTP $code"
fi

do_post "$BASE_URL/LoginServlet" -d "Email=$ADMIN_EMAIL" -d "Password=wrong" >/dev/null
echo "$(get_redirect)" | grep -qi "error" \
    && log_test "Reject wrong password" "PASS" \
    || log_test "Reject wrong password" "FAIL"

# Phase 4: User Registration
echo ""; echo "Phase 4: User Registration"
echo "---------------------------------------------"
code=$(do_post "$BASE_URL/RegistrationServlet" \
    -d "CompanyOrganization=$COMPANY" -d "Email=$USER_EMAIL" \
    -d "FirstName=Test" -d "LastName=User" \
    -d "Password=$PASSWORD" -d "ConfirmPassword=$PASSWORD" -d "Title=Developer")
redirect=$(get_redirect)
echo "$redirect" | grep -qi "success\|IWLogin" \
    && log_test "Register user" "PASS" \
    || log_test "Register user" "FAIL" "HTTP $code | $redirect"

do_post "$BASE_URL/RegistrationServlet" \
    -d "CompanyOrganization=$COMPANY" -d "Email=$USER_EMAIL" \
    -d "FirstName=D" -d "LastName=U" \
    -d "Password=$PASSWORD" -d "ConfirmPassword=$PASSWORD" >/dev/null
(body_contains "already exists" || ! echo "$(get_redirect)" | grep -qi "success") \
    && log_test "Reject duplicate email" "PASS" \
    || log_test "Reject duplicate email" "FAIL"

# Phase 5: User Login
echo ""; echo "Phase 5: User Login"
echo "---------------------------------------------"
rm -f "$COOKIE_JAR"
code=$(do_post "$BASE_URL/LoginServlet" -d "Email=$USER_EMAIL" -d "Password=$PASSWORD")
redirect=$(get_redirect)
echo "$redirect" | grep -qi "CompanyConfiguration" \
    && log_test "User login" "PASS" \
    || log_test "User login" "FAIL" "HTTP $code | $redirect"

# Phase 6: Edit User Profile
echo ""; echo "Phase 6: Edit User Profile"
echo "---------------------------------------------"
code=$(do_post "$BASE_URL/EditProfileServlet" -d "Email=$USER_EMAIL" -d "Password=$PASSWORD")
redirect=$(get_redirect)
echo "$redirect" | grep -qi "EditProfile.jsp.*CurrentProfile" \
    && log_test "Load user profile" "PASS" \
    || log_test "Load user profile" "FAIL" "HTTP $code | $redirect"

do_post "$BASE_URL/EditProfileServlet" -d "Email=$USER_EMAIL" -d "Password=wrong" >/dev/null
body_contains "Invalid password" \
    && log_test "Edit profile rejects bad pw" "PASS" \
    || log_test "Edit profile rejects bad pw" "FAIL"

# Phase 7: Save User Profile
echo ""; echo "Phase 7: Save User Profile"
echo "---------------------------------------------"
code=$(do_post "$BASE_URL/SaveProfileServlet" \
    -d "Email=$USER_EMAIL" -d "FirstName=UpdatedFirst" -d "LastName=UpdatedLast")
redirect=$(get_redirect)
echo "$redirect" | grep -qi "success\|IWLogin" \
    && log_test "Save user profile" "PASS" \
    || log_test "Save user profile" "FAIL" "HTTP $code | $redirect"

# Phase 8: Change User Password
echo ""; echo "Phase 8: Change User Password"
echo "---------------------------------------------"
code=$(do_post "$BASE_URL/ChangePasswordServlet" \
    -d "Email=$USER_EMAIL" -d "Password=$PASSWORD" \
    -d "NewPassword=$NEW_PASSWORD" -d "ConfirmPassword=$NEW_PASSWORD")
redirect=$(get_redirect)
echo "$redirect" | grep -qi "success\|IWLogin" \
    && log_test "Change user password" "PASS" \
    || log_test "Change user password" "FAIL" "HTTP $code | $redirect"

code=$(do_post "$BASE_URL/LoginServlet" -d "Email=$USER_EMAIL" -d "Password=$NEW_PASSWORD")
echo "$(get_redirect)" | grep -qi "CompanyConfiguration" \
    && log_test "Login with new password" "PASS" \
    || log_test "Login with new password" "FAIL"

code=$(do_post "$BASE_URL/LoginServlet" -d "Email=$USER_EMAIL" -d "Password=$PASSWORD")
echo "$(get_redirect)" | grep -qi "error" \
    && log_test "Old password rejected" "PASS" \
    || log_test "Old password rejected" "FAIL"

# Phase 9: Edit Company Profile
echo ""; echo "Phase 9: Edit Company Profile"
echo "---------------------------------------------"
code=$(do_post "$BASE_URL/EditCompanyProfileServlet" \
    -d "Company=$COMPANY" -d "Email=$ADMIN_EMAIL" -d "Password=$PASSWORD")
redirect=$(get_redirect)
echo "$redirect" | grep -qi "EditCompanyProfile.jsp.*CurrentProfile" \
    && log_test "Load company profile" "PASS" \
    || log_test "Load company profile" "FAIL" "HTTP $code | $redirect"

# Phase 10: Save Company Profile
echo ""; echo "Phase 10: Save Company Profile"
echo "---------------------------------------------"
code=$(do_post "$BASE_URL/SaveCompanyProfileServlet" \
    -d "CompanyOrganization=$COMPANY" -d "Email=$ADMIN_EMAIL" \
    -d "FirstName=UpdatedAdmin" -d "LastName=UpdatedName" -d "Type=$SOLUTION")
redirect=$(get_redirect)
echo "$redirect" | grep -qi "CompanyConfiguration" \
    && log_test "Save company profile" "PASS" \
    || log_test "Save company profile" "FAIL" "HTTP $code | $redirect"

# Phase 11: Change Company Password
echo ""; echo "Phase 11: Change Company Password"
echo "---------------------------------------------"
code=$(do_post "$BASE_URL/ChangeCompanyPasswordServlet" \
    -d "Company=$COMPANY" -d "Email=$ADMIN_EMAIL" \
    -d "Password=$PASSWORD" -d "NewPassword=$NEW_PASSWORD" -d "ConfirmPassword=$NEW_PASSWORD")
redirect=$(get_redirect)
echo "$redirect" | grep -qi "success\|IWLogin" \
    && log_test "Change company password" "PASS" \
    || log_test "Change company password" "FAIL" "HTTP $code | $redirect"

# Company password changed, but user password is unchanged — login uses user password
code=$(do_post "$BASE_URL/LoginServlet" -d "Email=$ADMIN_EMAIL" -d "Password=$PASSWORD")
echo "$(get_redirect)" | grep -qi "CompanyConfiguration" \
    && log_test "Admin login still works (user pw unchanged)" "PASS" \
    || log_test "Admin login still works (user pw unchanged)" "FAIL"

# Phase 12: Validation
echo ""; echo "Phase 12: Input Validation"
echo "---------------------------------------------"
do_post "$BASE_URL/LoginServlet" -d "Email=" -d "Password=x" >/dev/null
(body_contains "email" || echo "$(get_redirect)" | grep -qi "error") \
    && log_test "Login rejects empty email" "PASS" \
    || log_test "Login rejects empty email" "FAIL"

do_post "$BASE_URL/RegistrationServlet" \
    -d "CompanyOrganization=$COMPANY" -d "Email=mm@t.com" \
    -d "FirstName=X" -d "LastName=Y" -d "Password=abc" -d "ConfirmPassword=xyz" >/dev/null
body_contains "match\|Passwords" \
    && log_test "Reject mismatched passwords" "PASS" \
    || log_test "Reject mismatched passwords" "FAIL"

do_post "$BASE_URL/CompanyRegistrationServlet" \
    -d "CompanyOrganization=" -d "Email=x@t.com" \
    -d "FirstName=X" -d "LastName=Y" -d "Password=a" -d "ConfirmPassword=a" >/dev/null
body_contains "missing\|required\|Company" \
    && log_test "Reject empty company name" "PASS" \
    || log_test "Reject empty company name" "FAIL"

# Summary
echo ""
echo "============================================="
echo -e " Results: ${GREEN}$PASS passed${NC}, ${RED}$FAIL failed${NC}, $TOTAL total"
echo "============================================="
[ $FAIL -eq 0 ] && echo -e " ${GREEN}All tests passed!${NC}" || echo -e " ${RED}Some tests failed.${NC}"
echo ""
echo "Test data: Company=$COMPANY Admin=$ADMIN_EMAIL User=$USER_EMAIL"
echo "Cleanup:   DELETE FROM users WHERE email IN ('$ADMIN_EMAIL','$USER_EMAIL');"
echo "           DELETE FROM companies WHERE company_name='$COMPANY';"
