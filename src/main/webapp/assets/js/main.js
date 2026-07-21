// ============================================
// TOAST NOTIFICATION HELPER
// ============================================
function showToast(message, type) {
    var toastContainer = document.getElementById('toastContainer');
    if (!toastContainer) {
        return;
    }

    var toastId = 'toast-' + Date.now();
    var bgClass = type === 'success' ? 'text-bg-success'
        : type === 'error' ? 'text-bg-danger'
        : 'text-bg-primary';

    var toastHtml = '<div id="' + toastId + '" class="toast align-items-center ' + bgClass
        + '" role="alert" aria-live="assertive" aria-atomic="true">'
        + '<div class="d-flex">'
        + '<div class="toast-body">' + message + '</div>'
        + '<button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>'
        + '</div></div>';

    toastContainer.insertAdjacentHTML('beforeend', toastHtml);

    var toastElement = document.getElementById(toastId);
    var toast = new bootstrap.Toast(toastElement, { delay: 4000 });
    toast.show();

    toastElement.addEventListener('hidden.bs.toast', function () {
        toastElement.remove();
    });
}

// ============================================
// CONFIRMATION DIALOGS FOR DESTRUCTIVE ACTIONS
// ============================================
function confirmDelete(message) {
    return confirm(message || 'Are you sure you want to delete this item? This action cannot be undone.');
}

function confirmArchive(message) {
    return confirm(message || 'Are you sure you want to archive this notice?');
}

// ============================================
// BOOKMARK BUTTON HANDLER
// ============================================
function toggleBookmark(noticeId, buttonElement) {
    var isBookmarked = buttonElement.getAttribute('data-bookmarked') === 'true';
    var action = isBookmarked ? 'remove' : 'add';

    var formData = new URLSearchParams();
    formData.append('noticeId', noticeId);
    formData.append('action', action);

    fetch(getContextPath() + '/bookmark-notice', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: formData.toString()
    })
        .then(function (response) { return response.json(); })
        .then(function (data) {
            if (data.success) {
                buttonElement.setAttribute('data-bookmarked', data.bookmarked ? 'true' : 'false');
                var icon = buttonElement.querySelector('i');
                if (icon) {
                    icon.className = data.bookmarked ? 'bi bi-bookmark-fill' : 'bi bi-bookmark';
                }
                showToast(data.bookmarked ? 'Notice saved to bookmarks.' : 'Notice removed from bookmarks.', 'success');
            } else {
                showToast('Unable to update bookmark. Please try again.', 'error');
            }
        })
        .catch(function () {
            showToast('A network error occurred. Please try again.', 'error');
        });
}

// ============================================
// MARK NOTICE AS READ HANDLER
// ============================================
function markNoticeRead(noticeId) {
    var formData = new URLSearchParams();
    formData.append('noticeId', noticeId);

    fetch(getContextPath() + '/mark-notice-read', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: formData.toString()
    }).catch(function () {
        // Fails silently, since this is a background convenience call
    });
}

// ============================================
// CONTEXT PATH HELPER
// Reads the context path stored as a data attribute on the body tag
// ============================================
function getContextPath() {
    return document.body.getAttribute('data-context-path') || '';
}

// ============================================
// AUTO-DISMISS ALERTS AFTER A DELAY
// ============================================
document.addEventListener('DOMContentLoaded', function () {
    var autoDismissAlerts = document.querySelectorAll('.alert-auto-dismiss');
    autoDismissAlerts.forEach(function (alertElement) {
        setTimeout(function () {
            var bsAlert = bootstrap.Alert.getOrCreateInstance(alertElement);
            bsAlert.close();
        }, 5000);
    });
});