function showLoading() {
    let $loading = $('<div class="loading" id="loading">' +
        '<img src="img/loading.gif">' +
        '</div>');
    setTimeout(function () {
        $loading.appendTo(document.body);
    }, 200);
}

function hideLoading() {
    setTimeout(function () {
        var child = document.getElementById('loading');
        document.body.removeChild(child);
    }, 500);
}