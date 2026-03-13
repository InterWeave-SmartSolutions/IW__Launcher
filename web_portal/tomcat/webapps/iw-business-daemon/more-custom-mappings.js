/**
 * Custom mappings page functionality
 * Extracted from MoreCustomMappings.jsp for CSP compliance
 */

function initializeCustomMappings() {
  var closeBtn = document.querySelector('[data-action="close-window"]');
  if (closeBtn) {
    closeBtn.addEventListener('click', function () {
      self.close();
    });
  }
}

if (document.readyState === 'loading') {
  document.addEventListener('DOMContentLoaded', initializeCustomMappings);
} else {
  initializeCustomMappings();
}
