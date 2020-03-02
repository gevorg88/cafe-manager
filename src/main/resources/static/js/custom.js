$(function () {
    $.ajaxSetup({
        headers:
            {'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')}
    });

    function retrieveFromForm(formDataArray) {
        let asd = {};

        for (var i = 0; i < formDataArray.length; i++) {
            var form = formDataArray[i];
            if (!form.hasOwnProperty("name") || !form.hasOwnProperty("value")) {
                continue;
            }
            asd[form['name']] = asd[form['value']];
            console.log(form['name'], form['value']);
        }
        console.log(asd);
        return asd;
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
        console.log(data, JSON.stringify(data), form.serialize(), JSON.stringify(form.serialize()));
        $.ajax({
            url: href,
            type: 'post',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: JSON.stringify(data),
            success: function (data) {
                console.log(data, arguments, "success");
            },
            error: function (xhr) {
                console.log(xhr['responseJSON']['message']);
            }
        });
    });
});