/**
 * Logging page redirect handler
 * Extracted from Logging.jsp for CSP compliance
 * Preserves URL whitelist validation (done server-side in JSP)
 */

function performRedirect() {
  var redirectUrl = document.body.getAttribute('data-redirect-url');
  if (redirectUrl) {
    top.location.href = redirectUrl;
  }
}

if (document.readyState === 'loading') {
  document.addEventListener('DOMContentLoaded', performRedirect);
} else {
  performRedirect();
}
