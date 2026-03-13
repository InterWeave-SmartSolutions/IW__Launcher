/**
 * Error documentation search functionality
 * Filters error items based on error code or description text
 */

function searchErrors() {
  var input = document.getElementById('searchInput');
  var filter = input.value.toUpperCase();
  var items = document.getElementsByClassName('error-item');

  for (var i = 0; i < items.length; i++) {
    var code = items[i].getElementsByClassName('error-code')[0];
    var desc = items[i].getElementsByClassName('error-description')[0];
    var txtCode = code.textContent || code.innerText;
    var txtDesc = desc.textContent || desc.innerText;

    if (txtCode.toUpperCase().indexOf(filter) > -1 || txtDesc.toUpperCase().indexOf(filter) > -1) {
      items[i].style.display = "";
    } else {
      items[i].style.display = "none";
    }
  }
}

/**
 * Initialize search input with event listener
 * Called when DOM is ready
 */
function initializeErrorSearch() {
  var searchInput = document.getElementById('searchInput');
  if (searchInput) {
    searchInput.addEventListener('keyup', searchErrors);
  }
}

// Initialize when DOM is ready
if (document.readyState === 'loading') {
  document.addEventListener('DOMContentLoaded', initializeErrorSearch);
} else {
  initializeErrorSearch();
}
