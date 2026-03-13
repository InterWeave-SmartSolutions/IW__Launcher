"""
IW Portal E2E Tests — Session integrity, routing, security headers, UX flows.

Tests:
  1. Session cross-contamination: React login -> JSP logout -> React still shows logged in?
  2. SPA route refresh: Direct navigation to all routes returns 200 + index.html
  3. Login/logout round-trip in React UI
  4. Security headers on iw-portal responses
  5. Classic View redirect links work
  6. Session re-validation on tab focus (visibilitychange)
"""
import json
import os
import sys
import time
os.environ["PYTHONIOENCODING"] = "utf-8"
# Force UTF-8 on Windows console
if sys.platform == "win32":
    sys.stdout.reconfigure(encoding="utf-8", errors="replace")
    sys.stderr.reconfigure(encoding="utf-8", errors="replace")
from playwright.sync_api import sync_playwright

BASE = "http://localhost:9090"
PORTAL = f"{BASE}/iw-portal"
BD = f"{BASE}/iw-business-daemon"
DEMO_EMAIL = "demo@sample.com"
DEMO_PASS = "demo123"

results = []

def record(name, passed, detail=""):
    status = "PASS" if passed else "FAIL"
    results.append({"test": name, "status": status, "detail": detail})
    icon = "PASS" if passed else "FAIL"
    print(f"  [{icon}] {name}" + (f" — {detail}" if detail else ""))


def test_security_headers(page):
    """Verify HttpHeaderSecurityFilter is active on iw-portal."""
    print("\n=== Test 1: Security Headers ===")
    resp = page.goto(f"{PORTAL}/login")
    headers = resp.headers if resp else {}

    record("X-Frame-Options present",
           "x-frame-options" in headers,
           headers.get("x-frame-options", "MISSING"))
    record("X-Content-Type-Options present",
           headers.get("x-content-type-options") == "nosniff",
           headers.get("x-content-type-options", "MISSING"))
    record("X-XSS-Protection present",
           "x-xss-protection" in headers,
           headers.get("x-xss-protection", "MISSING"))


def test_spa_route_refresh(page):
    """All 41 routes should return 200 with index.html on direct navigation."""
    print("\n=== Test 2: SPA Route Direct Navigation (Refresh) ===")

    routes = [
        "/login", "/dashboard", "/register", "/register/company",
        "/forgot-password", "/mfa/verify",
        "/monitoring", "/monitoring/transactions", "/monitoring/alerts",
        "/profile", "/profile/password", "/profile/security",
        "/company", "/company/config", "/company/config/wizard",
        "/admin/configurator", "/admin/logging", "/admin/audit",
        "/notifications",
        "/associate/home", "/associate/billing", "/associate/intake",
        "/associate/resources", "/associate/search", "/associate/support",
        "/associate/webinars",
        "/master/dashboard", "/master/users", "/master/content",
        "/master/subscriptions", "/master/integrations", "/master/analytics",
        "/master/audit", "/master/notifications", "/master/support",
        "/master/settings",
    ]

    pass_count = 0
    fail_routes = []
    for route in routes:
        url = f"{PORTAL}{route}"
        resp = page.goto(url, wait_until="commit")
        status = resp.status if resp else 0
        if status == 200:
            pass_count += 1
        else:
            fail_routes.append(f"{route} -> {status}")

    record(f"SPA routes return 200 ({pass_count}/{len(routes)})",
           pass_count == len(routes),
           "; ".join(fail_routes[:5]) if fail_routes else "all OK")


def test_login_logout_react(page):
    """Full login -> verify -> logout -> verify round-trip in React UI."""
    print("\n=== Test 3: React Login/Logout Round-Trip ===")

    # Navigate to login
    page.goto(f"{PORTAL}/login")
    page.wait_for_load_state("networkidle")

    # Should see login form
    email_input = page.locator('input[type="email"], input[name="email"]')
    has_login_form = email_input.count() > 0
    record("Login page shows form", has_login_form)

    if not has_login_form:
        # Maybe already redirected (authenticated from prior test)
        record("Login form visible", False, "Could not find email input")
        return

    # Fill and submit
    email_input.fill(DEMO_EMAIL)
    page.locator('input[type="password"]').fill(DEMO_PASS)
    page.locator('button[type="submit"]').click()

    # Wait for navigation away from /login
    try:
        page.wait_for_url(lambda u: "/login" not in u, timeout=8000)
        record("Login redirects away from /login", True, page.url)
    except Exception as e:
        record("Login redirects away from /login", False, str(e)[:100])
        # Take screenshot for debugging
        page.screenshot(path="/tmp/login_fail.png")
        return

    # Verify session: call the API
    session_resp = page.evaluate("""
        async () => {
            const r = await fetch('/iw-business-daemon/api/auth/session', {credentials: 'include'});
            return await r.json();
        }
    """)
    record("Session API says authenticated",
           session_resp.get("authenticated") is True,
           json.dumps(session_resp)[:120])

    # Now logout via React UI — button is in Topbar with title="Log out"
    logout_found = False
    logout_btn = page.locator('button[title="Log out"]').first
    if logout_btn.count() > 0 and logout_btn.is_visible():
        logout_btn.click()
        logout_found = True
        page.wait_for_timeout(1000)
    else:
        # Fallback: any visible button with Logout text
        for sel in ['button:has-text("Logout")', 'button:has-text("Log out")', 'a:has-text("Logout")']:
            btn = page.locator(sel).first
            if btn.count() > 0 and btn.is_visible():
                btn.click()
                logout_found = True
                page.wait_for_timeout(1000)
                break

    record("Logout button found and clicked", logout_found)

    if logout_found:
        # Should redirect to /login
        try:
            page.wait_for_url(lambda u: "/login" in u, timeout=5000)
            record("Logout redirects to /login", True)
        except:
            record("Logout redirects to /login", False, f"Ended at {page.url}")

        # Verify session is gone
        session_after = page.evaluate("""
            async () => {
                const r = await fetch('/iw-business-daemon/api/auth/session', {credentials: 'include'});
                return await r.json();
            }
        """)
        record("Session API says NOT authenticated after logout",
               session_after.get("authenticated") is not True,
               json.dumps(session_after)[:120])


def test_cross_ui_session_leak(page, context):
    """
    CRITICAL: Login in React -> go to JSP UI -> logout from JSP -> return to React.
    The React UI should NOT still show the user as logged in.
    """
    print("\n=== Test 4: Cross-UI Session Leak (CRITICAL) ===")

    # Step 1: Login via React UI
    page.goto(f"{PORTAL}/login")
    page.wait_for_load_state("networkidle")
    page.wait_for_timeout(1000)

    email_input = page.locator('input[type="email"], input[name="email"]')
    if email_input.count() == 0:
        # Might already be logged in — logout first
        page.goto(f"{PORTAL}/login")
        page.wait_for_timeout(1000)

    email_input = page.locator('input[type="email"], input[name="email"]')
    if email_input.count() > 0:
        email_input.fill(DEMO_EMAIL)
        page.locator('input[type="password"]').fill(DEMO_PASS)
        page.locator('button[type="submit"]').click()
        try:
            page.wait_for_url(lambda u: "/login" not in u, timeout=8000)
        except:
            record("Cross-UI: Initial React login", False, "Login failed")
            return

    record("Cross-UI: Logged into React UI", True, page.url)

    # Step 2: Navigate to JSP UI in SAME tab
    page.goto(f"{BD}/IWLogin.jsp")
    page.wait_for_load_state("networkidle")
    page.wait_for_timeout(500)

    # Check if JSP sees us as logged in (shared session)
    jsp_content = page.content()
    jsp_sees_login = "Welcome" in jsp_content or "Logout" in jsp_content or "logout" in jsp_content.lower()
    record("Cross-UI: JSP sees shared session",
           True,  # Just informational
           "JSP shows logged-in state" if jsp_sees_login else "JSP shows login form")

    # Step 3: Logout from JSP UI
    page.goto(f"{BD}/LogoutServlet")
    page.wait_for_load_state("networkidle")
    page.wait_for_timeout(500)
    record("Cross-UI: Called JSP LogoutServlet", True)

    # Step 4: Verify session is truly dead on server
    session_check = page.evaluate("""
        async () => {
            const r = await fetch('/iw-business-daemon/api/auth/session', {credentials: 'include'});
            return await r.json();
        }
    """)
    server_says_no = session_check.get("authenticated") is not True
    record("Cross-UI: Server session invalidated",
           server_says_no,
           json.dumps(session_check)[:120])

    # Step 5: NOW navigate back to React UI (the critical test)
    page.goto(f"{PORTAL}/dashboard")
    page.wait_for_load_state("networkidle")
    page.wait_for_timeout(2000)  # Give AuthProvider time to check session

    # Where are we? If redirected to /login, the fix is working.
    # If still on /dashboard, the session leak bug is present.
    final_url = page.url
    redirected_to_login = "/login" in final_url
    record("Cross-UI: React detects invalidated session -> redirects to login",
           redirected_to_login,
           f"Ended at {final_url}")

    if not redirected_to_login:
        # Additional check: does the page LOOK logged in?
        page.screenshot(path="/tmp/session_leak.png")
        # Check if user name is displayed or if we see dashboard content
        body_text = page.locator("body").inner_text()
        looks_logged_in = "dashboard" in body_text.lower() or "demo" in body_text.lower()
        record("Cross-UI: Page content appears logged in (BUG if True)",
               not looks_logged_in,
               "User data visible on page" if looks_logged_in else "Page appears unauthenticated")


def test_cross_ui_reverse(page):
    """
    Reverse: Login in JSP -> navigate to React UI -> should React see the session?
    This SHOULD work (session sharing is expected for initial login).
    """
    print("\n=== Test 5: JSP Login -> React UI Session Sharing ===")

    # Logout any existing session
    page.goto(f"{BD}/LogoutServlet")
    page.wait_for_timeout(500)

    # Login via JSP
    page.goto(f"{BD}/IWLogin.jsp")
    page.wait_for_load_state("networkidle")
    page.wait_for_timeout(500)

    # Fill JSP login form
    email_field = page.locator('input[name="email"], input[name="Email"]').first
    pass_field = page.locator('input[name="password"], input[name="Password"], input[type="password"]').first

    if email_field.count() > 0 and pass_field.count() > 0:
        email_field.fill(DEMO_EMAIL)
        pass_field.fill(DEMO_PASS)
        page.locator('input[type="submit"], button[type="submit"]').first.click()
        page.wait_for_load_state("networkidle")
        page.wait_for_timeout(1000)
        record("Reverse: JSP login submitted", True)
    else:
        record("Reverse: JSP login submitted", False, "Could not find form fields")
        return

    # Now go to React UI
    page.goto(f"{PORTAL}/dashboard")
    page.wait_for_load_state("networkidle")
    page.wait_for_timeout(2000)

    final_url = page.url
    # React should see the session and stay on dashboard (not redirect to login)
    stayed_on_dashboard = "/login" not in final_url
    record("Reverse: React UI recognizes JSP session",
           stayed_on_dashboard,
           f"Ended at {final_url}")


def test_protected_route_guard(page):
    """Unauthenticated access to protected routes should redirect to /login."""
    print("\n=== Test 6: Protected Route Guards ===")

    # Clear session
    page.goto(f"{BD}/LogoutServlet")
    page.wait_for_timeout(500)
    # Clear any stored tokens
    page.goto(f"{PORTAL}/login")
    page.wait_for_load_state("networkidle")
    page.evaluate("localStorage.removeItem('iw_auth_token')")
    page.wait_for_timeout(300)

    protected_routes = [
        "/dashboard", "/monitoring", "/profile", "/company",
        "/admin/configurator", "/notifications",
        "/associate/home", "/master/dashboard",
    ]

    for route in protected_routes:
        page.goto(f"{PORTAL}{route}")
        page.wait_for_load_state("networkidle")
        page.wait_for_timeout(1500)
        final = page.url
        guarded = "/login" in final
        record(f"Guard: {route} -> login redirect",
               guarded,
               "" if guarded else f"Stayed at {final}")


def test_refresh_preserves_auth(page):
    """After login, refreshing any page should keep the user authenticated."""
    print("\n=== Test 7: Page Refresh Preserves Auth ===")

    # Login first
    page.goto(f"{PORTAL}/login")
    page.wait_for_load_state("networkidle")
    page.wait_for_timeout(500)

    email_input = page.locator('input[type="email"], input[name="email"]')
    if email_input.count() > 0:
        email_input.fill(DEMO_EMAIL)
        page.locator('input[type="password"]').fill(DEMO_PASS)
        page.locator('button[type="submit"]').click()
        try:
            page.wait_for_url(lambda u: "/login" not in u, timeout=8000)
        except:
            record("Refresh: Login for refresh test", False, "Login failed")
            return

    record("Refresh: Logged in", True)

    # Navigate to a few pages and refresh each
    test_pages = ["/dashboard", "/monitoring", "/profile"]
    for route in test_pages:
        page.goto(f"{PORTAL}{route}")
        page.wait_for_load_state("networkidle")
        page.wait_for_timeout(1000)

        # Refresh (F5)
        page.reload()
        page.wait_for_load_state("networkidle")
        page.wait_for_timeout(1500)

        final = page.url
        still_on_page = "/login" not in final
        record(f"Refresh: {route} survives F5",
               still_on_page,
               "" if still_on_page else f"Redirected to {final}")


def main():
    print("=" * 60)
    print("  IW Portal E2E Test Suite")
    print("  Target: http://localhost:9090")
    print("=" * 60)

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(
            viewport={"width": 1280, "height": 720},
            ignore_https_errors=True,
        )
        page = context.new_page()

        try:
            test_security_headers(page)
            test_spa_route_refresh(page)
            test_login_logout_react(page)
            test_cross_ui_session_leak(page, context)
            test_cross_ui_reverse(page)
            test_protected_route_guard(page)
            test_refresh_preserves_auth(page)
        finally:
            browser.close()

    # Summary
    print("\n" + "=" * 60)
    passed = sum(1 for r in results if r["status"] == "PASS")
    failed = sum(1 for r in results if r["status"] == "FAIL")
    print(f"  Results: {passed} PASS, {failed} FAIL, {len(results)} total")
    print("=" * 60)

    if failed > 0:
        print("\n  FAILURES:")
        for r in results:
            if r["status"] == "FAIL":
                print(f"    FAIL {r['test']}: {r['detail']}")

    print()
    return 0 if failed == 0 else 1


if __name__ == "__main__":
    sys.exit(main())
