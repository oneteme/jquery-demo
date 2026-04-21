import { removeSecondClass } from "./utils.js";
function updateStatus(statusClass, buttonText, message) {
    removeSecondClass($(".query-status-btn"));
    $(".query-status-btn").addClass(statusClass);
    $(".query-status-btn").attr("data-status", message);
    $(".query-status-btn").html(buttonText)
    showStatus();
}
export function hideStatus() {
    $(".query-status-btn").hide();
}

export function showStatus() {
    $(".query-status-btn").show();
}
export function showError(message) {
    updateStatus("error", "✗", message);
}

export function showSuccess(message) {
    updateStatus("success", "✓", message);
}

export function showLoading(loader, hiddenElement = null) {
    loader.show();
    if (hiddenElement) {
        hiddenElement.hide();
    }
}

export function hideLoading(loader, showElement = null) {
    loader.hide();
    if (showElement) {
        showElement.show();
    }
}