import { removeSecondClass } from "./utils.js";
const ICONS = { success: 'ti-circle-check', error: 'ti-alert-circle', info: 'ti-info-circle' },
    DURATION = 3000;
let timer;

export function showError(message) {
    showToast("error", "Error", message);
}

export function showSuccess(rows) {
    const message = rows <= 0 ? "No entries found" : rows + " rows fetched";
    showToast("success", "Query executed", message);
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

function showToast(type, title, msg) {
    clearTimeout(timer);

    removeSecondClass($('#toast'));
    $('#toast').addClass(type);
    $('#toast-icon').attr('class', 'ti ' + ICONS[type]);
    $('#toast-title').text(title);
    $('#toast-msg').text(msg);

    $('#toast').stop(true).css('display', 'flex').hide().fadeIn(200);
    $('#toast-bar').stop(true).css('width', '100%').animate({ width: '0%' }, DURATION, 'linear');
    timer = setTimeout(hideToast, DURATION);
}

function hideToast() {
    clearTimeout(timer);
    $('#toast').stop(true).fadeOut(200);
}

$('.toast-close').on("click", (e) => {
    hideToast();
})