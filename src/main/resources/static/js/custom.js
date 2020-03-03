$(function () {
    $.ajaxSetup({
        headers:
            {'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')}
    });

    function retrieveFromForm(formDataArray) {
        var data = {};

        formDataArray.forEach(function (form) {
            if (form.hasOwnProperty('name') && form.hasOwnProperty('value')) {
                data[form['name']] = form['value'];
            }
        });
        return data;
    }

    $(document).on('click', '.add-instance', function () {
        var _this = $(this),
            modalId = _this.attr('data-modal-id'),
            modal = $('#' + modalId);
        if (!modal.length) {
            return false;
        }
        modal.modal('show');
    });

    $(document).on('click', '.save-modal-data', function () {
        var _this = $(this),
            form = _this.closest('.modal').find('form'),
            formData = form.serializeArray(),
            href = _this.attr('data-href');
        if (!href) {
            return false;
        }
        var data = retrieveFromForm(formData);
        $.ajax({
            url: href,
            type: 'post',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: JSON.stringify(data),
            success: function (data) {
                (new Promise(function (resolve) {
                    toastr.success(data['message']);
                    setTimeout(function () {
                        resolve();
                    }, 3000);
                })).then(function () {
                    return location.reload();
                });
            },
            error: function (xhr) {
                toastr.error(xhr['responseJSON']['message']);
            }
        });
    });
});