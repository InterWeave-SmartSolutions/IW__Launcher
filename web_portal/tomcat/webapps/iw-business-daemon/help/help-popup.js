/**
 * Help popup functionality
 * Provides window control operations (open docs, print, close)
 * Extracted from help-popup.jsp for CSP compliance
 */

function openFullDocs() {
  var docsLink = document.querySelector('[data-docs-url]');
  if (docsLink) {
    var url = docsLink.getAttribute('data-docs-url');
    if (url) {
      window.open(url, '_blank');
    }
  }
}

function closeHelp() {
  window.close();
}

function printHelp() {
  window.print();
}

/**
 * Initialize help popup event handlers
 * Called when DOM is ready
 */
function initializeHelpPopup() {
  var openDocsBtn = document.querySelector('[data-action="open-docs"]');
  var printBtn = document.querySelector('[data-action="print"]');
  var closeBtn = document.querySelector('[data-action="close"]');

  if (openDocsBtn) {
    openDocsBtn.addEventListener('click', openFullDocs);
  }
  if (printBtn) {
    printBtn.addEventListener('click', printHelp);
  }
  if (closeBtn) {
    closeBtn.addEventListener('click', closeHelp);
  }
}

// Initialize when DOM is ready
if (document.readyState === 'loading') {
  document.addEventListener('DOMContentLoaded', initializeHelpPopup);
} else {
  initializeHelpPopup();
}
