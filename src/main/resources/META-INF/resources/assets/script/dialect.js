$(function () {
    $('#db-select-wrapper').on('click', function (e) {
        e.stopPropagation();
        $('#db-select-popup').toggleClass('show');
        $(this).toggleClass('active');
    });

    $('.db-option').on('click', function () {
        const value = $(this).data('value'),
            label = $(this).data('label'),
            icon = $(this).children().first().clone();
        console.log("icon : ",icon)
        $('.db-option').removeClass('active');
        $(this).addClass('active');
        $('#db-select-label').html(label);
        $('#db-select-icon-slot').html(icon);

        // Change SQL or database code
    });

});