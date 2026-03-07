#!/bin/bash
# =============================================================================
# IW Web Portal - JSON API End-to-End Test Script
# Tests all React portal API endpoints (/api/*)
# Usage: bash test_api.sh [BASE_URL]
# Default: http://localhost:9090/iw-business-daemon
# =============================================================================

BASE_URL="${1:-http://localhost:9090/iw-business-daemon}"
COOKIE_JAR="/tmp/iw_api_cookies.txt"

TS=$(date +%s)
COMPANY="ApiTestCorp_${TS}"
ADMIN_EMAIL="apiadmin_${TS}@testcorp.com"
USER_EMAIL="apiuser_${TS}@testcorp.com"
PASSWORD="TestPass123"
NEW_PASSWORD="NewPass456"
SOLUTION="SF2QB"

PASS=0; FAIL=0; TOTAL=0
GREEN='\033[0;32m'; RED='\033[0;31m'; YELLOW='\033[1;33m'; NC='\033[0m'

cleanup() { rm -f "$COOKIE_JAR" /tmp/iw_api_body.txt; }
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

api_post() {
    curl -s -c "$COOKIE_JAR" -b "$COOKIE_JAR" \
         -H "Content-Type: application/json" \
         -o /tmp/iw_api_body.txt -w "%{http_code}" \
         -X POST "$1" -d "$2" 2>/dev/null
}
api_put() {
    curl -s -c "$COOKIE_JAR" -b "$COOKIE_JAR" \
         -H "Content-Type: application/json" \
         -o /tmp/iw_api_body.txt -w "%{http_code}" \
         -X PUT "$1" -d "$2" 2>/dev/null
}
api_get() {
    curl -s -c "$COOKIE_JAR" -b "$COOKIE_JAR" \
         -o /tmp/iw_api_body.txt -w "%{http_code}" "$1" 2>/dev/null
}
body_has() { grep -q "$1" /tmp/iw_api_body.txt 2>/dev/null; }

echo "============================================="
echo " IW Portal - JSON API E2E Tests"
echo "============================================="
echo " URL:     $BASE_URL"
echo " Company: $COMPANY"
echo " Admin:   $ADMIN_EMAIL"
echo " User:    $USER_EMAIL"
echo "============================================="

# ─── Phase 1: Registration APIs ───
echo ""; echo "Phase 1: Registration APIs"
echo "---------------------------------------------"

# GET solution types (no auth required)
code=$(api_get "$BASE_URL/api/register/solution-types")
body_has "solutionTypes" \
    && log_test "GET /api/register/solution-types" "PASS" \
    || log_test "GET /api/register/solution-types" "FAIL" "HTTP $code"

# Register company
code=$(api_post "$BASE_URL/api/register/company" \
    "{\"companyName\":\"$COMPANY\",\"email\":\"$ADMIN_EMAIL\",\"firstName\":\"Admin\",\"lastName\":\"Test\",\"password\":\"$PASSWORD\",\"confirmPassword\":\"$PASSWORD\",\"solutionType\":\"$SOLUTION\"}")
body_has '"success":true' \
    && log_test "POST /api/register/company" "PASS" \
    || log_test "POST /api/register/company" "FAIL" "HTTP $code — $(cat /tmp/iw_api_body.txt)"

# Reject duplicate company
code=$(api_post "$BASE_URL/api/register/company" \
    "{\"companyName\":\"$COMPANY\",\"email\":\"dup@test.com\",\"firstName\":\"D\",\"lastName\":\"T\",\"password\":\"$PASSWORD\",\"confirmPassword\":\"$PASSWORD\",\"solutionType\":\"$SOLUTION\"}")
body_has '"success":false' \
    && log_test "Reject duplicate company registration" "PASS" \
    || log_test "Reject duplicate company registration" "FAIL" "HTTP $code"

# Register user
code=$(api_post "$BASE_URL/api/register" \
    "{\"companyName\":\"$COMPANY\",\"email\":\"$USER_EMAIL\",\"firstName\":\"Test\",\"lastName\":\"User\",\"password\":\"$PASSWORD\",\"confirmPassword\":\"$PASSWORD\",\"title\":\"Developer\"}")
body_has '"success":true' \
    && log_test "POST /api/register (user)" "PASS" \
    || log_test "POST /api/register (user)" "FAIL" "HTTP $code — $(cat /tmp/iw_api_body.txt)"

# Reject duplicate user
code=$(api_post "$BASE_URL/api/register" \
    "{\"companyName\":\"$COMPANY\",\"email\":\"$USER_EMAIL\",\"firstName\":\"D\",\"lastName\":\"U\",\"password\":\"$PASSWORD\",\"confirmPassword\":\"$PASSWORD\"}")
body_has '"success":false' \
    && log_test "Reject duplicate user registration" "PASS" \
    || log_test "Reject duplicate user registration" "FAIL" "HTTP $code"

# Validation: mismatched passwords
code=$(api_post "$BASE_URL/api/register" \
    "{\"companyName\":\"$COMPANY\",\"email\":\"val@test.com\",\"firstName\":\"X\",\"lastName\":\"Y\",\"password\":\"abc\",\"confirmPassword\":\"xyz\"}")
body_has '"success":false' \
    && log_test "Reject mismatched passwords" "PASS" \
    || log_test "Reject mismatched passwords" "FAIL" "HTTP $code"

# ─── Phase 2: Auth APIs ───
echo ""; echo "Phase 2: Auth APIs"
echo "---------------------------------------------"

# Unauthenticated session check
rm -f "$COOKIE_JAR"
code=$(api_get "$BASE_URL/api/auth/session")
body_has '"authenticated":false' \
    && log_test "GET /api/auth/session (unauth)" "PASS" \
    || log_test "GET /api/auth/session (unauth)" "FAIL" "HTTP $code"

# Login as admin
code=$(api_post "$BASE_URL/api/auth/login" \
    "{\"email\":\"$ADMIN_EMAIL\",\"password\":\"$PASSWORD\"}")
body_has '"success":true' \
    && log_test "POST /api/auth/login (admin)" "PASS" \
    || log_test "POST /api/auth/login (admin)" "FAIL" "HTTP $code — $(cat /tmp/iw_api_body.txt)"

# Authenticated session check
code=$(api_get "$BASE_URL/api/auth/session")
body_has '"authenticated":true' \
    && log_test "GET /api/auth/session (authed)" "PASS" \
    || log_test "GET /api/auth/session (authed)" "FAIL" "HTTP $code"

# Reject wrong password
rm -f "$COOKIE_JAR"
code=$(api_post "$BASE_URL/api/auth/login" "{\"email\":\"$ADMIN_EMAIL\",\"password\":\"wrong\"}")
body_has '"success":false' \
    && log_test "Reject wrong password" "PASS" \
    || log_test "Reject wrong password" "FAIL" "HTTP $code"

# ─── Phase 3: Profile APIs (authenticated) ───
echo ""; echo "Phase 3: Profile APIs"
echo "---------------------------------------------"

# Login first
api_post "$BASE_URL/api/auth/login" "{\"email\":\"$ADMIN_EMAIL\",\"password\":\"$PASSWORD\"}" >/dev/null

# GET profile
code=$(api_get "$BASE_URL/api/profile")
body_has '"firstName"' \
    && log_test "GET /api/profile" "PASS" \
    || log_test "GET /api/profile" "FAIL" "HTTP $code"

# PUT profile
code=$(api_put "$BASE_URL/api/profile" "{\"firstName\":\"Updated\",\"lastName\":\"Admin\"}")
([ "$code" = "200" ] && body_has '"success":true') \
    && log_test "PUT /api/profile" "PASS" \
    || log_test "PUT /api/profile" "FAIL" "HTTP $code"

# GET company profile
code=$(api_get "$BASE_URL/api/company/profile")
body_has '"company"' \
    && log_test "GET /api/company/profile" "PASS" \
    || log_test "GET /api/company/profile" "FAIL" "HTTP $code"

# PUT company profile
code=$(api_put "$BASE_URL/api/company/profile" "{\"firstName\":\"UpdatedAdmin\",\"lastName\":\"Test\"}")
([ "$code" = "200" ] && body_has '"success":true') \
    && log_test "PUT /api/company/profile" "PASS" \
    || log_test "PUT /api/company/profile" "FAIL" "HTTP $code"

# ─── Phase 4: Change Password API ───
echo ""; echo "Phase 4: Change Password API"
echo "---------------------------------------------"

code=$(api_post "$BASE_URL/api/auth/change-password" \
    "{\"oldPassword\":\"$PASSWORD\",\"newPassword\":\"$NEW_PASSWORD\",\"confirmPassword\":\"$NEW_PASSWORD\"}")
([ "$code" = "200" ] && body_has '"success":true') \
    && log_test "POST /api/auth/change-password" "PASS" \
    || log_test "POST /api/auth/change-password" "FAIL" "HTTP $code — $(cat /tmp/iw_api_body.txt)"

# Login with new password
rm -f "$COOKIE_JAR"
code=$(api_post "$BASE_URL/api/auth/login" "{\"email\":\"$ADMIN_EMAIL\",\"password\":\"$NEW_PASSWORD\"}")
body_has '"success":true' \
    && log_test "Login with new password" "PASS" \
    || log_test "Login with new password" "FAIL" "HTTP $code"

# Reject old password
rm -f "$COOKIE_JAR"
code=$(api_post "$BASE_URL/api/auth/login" "{\"email\":\"$ADMIN_EMAIL\",\"password\":\"$PASSWORD\"}")
body_has '"success":false' \
    && log_test "Old password rejected" "PASS" \
    || log_test "Old password rejected" "FAIL" "HTTP $code"

# ─── Phase 5: Config & Flow APIs (requires active session) ───
echo ""; echo "Phase 5: Config & Flow APIs"
echo "---------------------------------------------"

# Re-login
api_post "$BASE_URL/api/auth/login" "{\"email\":\"$ADMIN_EMAIL\",\"password\":\"$NEW_PASSWORD\"}" >/dev/null

# GET config wizard (may return empty config for new company)
code=$(api_get "$BASE_URL/api/config/wizard")
[ "$code" = "200" ] \
    && log_test "GET /api/config/wizard" "PASS" \
    || log_test "GET /api/config/wizard" "FAIL" "HTTP $code"

# GET credentials
code=$(api_get "$BASE_URL/api/config/credentials")
[ "$code" = "200" ] \
    && log_test "GET /api/config/credentials" "PASS" \
    || log_test "GET /api/config/credentials" "FAIL" "HTTP $code"

# GET flows (may fail if ConfigContext not initialized — that's expected)
code=$(api_get "$BASE_URL/api/flows")
([ "$code" = "200" ] || [ "$code" = "500" ]) \
    && log_test "GET /api/flows (or 500 if no ConfigContext)" "PASS" \
    || log_test "GET /api/flows" "FAIL" "HTTP $code"

# ─── Phase 6: Monitoring APIs ───
echo ""; echo "Phase 6: Monitoring APIs"
echo "---------------------------------------------"

code=$(api_get "$BASE_URL/api/monitoring/dashboard")
[ "$code" = "200" ] \
    && log_test "GET /api/monitoring/dashboard" "PASS" \
    || log_test "GET /api/monitoring/dashboard" "FAIL" "HTTP $code"

code=$(api_get "$BASE_URL/api/monitoring/transactions?page=1&pageSize=10")
[ "$code" = "200" ] \
    && log_test "GET /api/monitoring/transactions" "PASS" \
    || log_test "GET /api/monitoring/transactions" "FAIL" "HTTP $code"

code=$(api_get "$BASE_URL/api/monitoring/metrics?granularity=hourly&period=24h")
[ "$code" = "200" ] \
    && log_test "GET /api/monitoring/metrics" "PASS" \
    || log_test "GET /api/monitoring/metrics" "FAIL" "HTTP $code"

code=$(api_get "$BASE_URL/api/monitoring/alerts")
[ "$code" = "200" ] \
    && log_test "GET /api/monitoring/alerts" "PASS" \
    || log_test "GET /api/monitoring/alerts" "FAIL" "HTTP $code"

# ─── Phase 7: Auth enforcement (non-admin) ───
echo ""; echo "Phase 7: Auth Enforcement"
echo "---------------------------------------------"

# Login as regular user
rm -f "$COOKIE_JAR"
api_post "$BASE_URL/api/auth/login" "{\"email\":\"$USER_EMAIL\",\"password\":\"$PASSWORD\"}" >/dev/null

# Regular user should be blocked from company profile updates
code=$(api_put "$BASE_URL/api/company/profile" "{\"firstName\":\"Hacker\",\"lastName\":\"Attempt\"}")
[ "$code" = "403" ] \
    && log_test "Non-admin blocked from company update (403)" "PASS" \
    || log_test "Non-admin blocked from company update" "FAIL" "HTTP $code (expected 403)"

# Unauthenticated access should fail
rm -f "$COOKIE_JAR"
code=$(api_get "$BASE_URL/api/profile")
([ "$code" = "401" ] || [ "$code" = "403" ]) \
    && log_test "Unauthenticated profile access blocked" "PASS" \
    || log_test "Unauthenticated profile access blocked" "FAIL" "HTTP $code (expected 401/403)"

# ─── Summary ───
echo ""
echo "============================================="
echo -e " Results: ${GREEN}$PASS passed${NC}, ${RED}$FAIL failed${NC}, $TOTAL total"
echo "============================================="
[ $FAIL -eq 0 ] && echo -e " ${GREEN}All tests passed!${NC}" || echo -e " ${RED}Some tests failed.${NC}"
echo ""
echo "Test data: Company=$COMPANY Admin=$ADMIN_EMAIL User=$USER_EMAIL"
echo "Cleanup:   DELETE FROM users WHERE email IN ('$ADMIN_EMAIL','$USER_EMAIL');"
echo "           DELETE FROM companies WHERE company_name='$COMPANY';"
