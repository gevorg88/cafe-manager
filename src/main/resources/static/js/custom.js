$(function () {
    $.ajaxSetup({
        headers:
            {'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')}
    });

    var _document = $(document);
    var swalDeleteConf = {
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes!'
        },
        retrieveFromForm = function (formDataArray) {
            var data = {};

            formDataArray.forEach(function (form) {
                if (form.hasOwnProperty('name') && form.hasOwnProperty('value')) {
                    data[form['name']] = form['value'];
                }
            });
            return data;
        },
        processPromiseResponse = function (message, timeout, _callback) {
            var _timeout = timeout || 2000;
            return new Promise(function (resolve) {
                if (_callback) {
                    _callback();
                }
                toastr.success(message);
                setTimeout(function () {
                    resolve();
                }, _timeout);
            })
        };

    _document.on('click', '.add-instance', function () {
        var _this = $(this),
            modalId = _this.attr('data-modal-id'),
            modal = $('#' + modalId);
        if (!modal.length) {
            return false;
        }
        modal.modal('show');
    })

        .on('click', '.save-modal-data', function () {
            var _this = $(this),
                modal = _this.closest('.modal'),
                form = modal.find('form'),
                formData = form.serializeArray(),
                href = _this.attr('data-href');
            if (!href) {
                return toastr.error("Wrong url!");
            }
            var data = retrieveFromForm(formData);
            $.ajax({
                url: href,
                method: 'POST',
                contentType: 'application/json',
                mimeType: 'application/json',
                data: JSON.stringify(data),
                success: function (data) {
                    processPromiseResponse(data['message'], 2000, function () {
                        modal.modal('hide');
                    }).then(function () {
                        return location.reload();
                    });
                },
                error: function (xhr) {
                    toastr.error(xhr['responseJSON']['message']);
                }
            });
        })

        .on('change', '#user-assign', function () {
            var _this = $(this),
                tableId = _this.attr('data-table-id'),
                userId = _this.val();

            if (!userId) {
                return toastr.error('Please Select user');
            }

            if (!tableId) {
                return toastr.error('Something goes wrong!');
            }
            _this.attr('disable', true);
            $.ajax({
                url: '/tables/manager/assign/' + tableId + '/' + userId,
                method: 'POST',
                success: function (data) {
                    processPromiseResponse(data['message'], 2000).then(function () {
                        return location.reload();
                    });
                },
                error: function (xhr) {
                    return toastr.error(xhr['responseJSON']['message']);
                }
            }).done(function () {
                _this.removeAttr('disable');
            });
        })

        .on('click', '.instance-delete', function () {
            var _this = $(this),
                url = _this.attr('data-href');
            if (!url) {
                return toastr.error('Wrong url');
            }
            Swal.fire(swalDeleteConf).then((result) => {
                if (result.value) {
                    $.ajax({
                        url: url,
                        method: 'DELETE',
                        success: function (data) {
                            processPromiseResponse(data['message'], 2000).then(function () {
                                return location.reload();
                            });
                        },
                        error: function (xhr) {
                            return toastr.error(xhr['responseJSON']['message']);
                        }
                    });
                }
            });
        })

        .on('click', '.edit-instance', function () {
            var _this = $(this),
                url = _this.attr('data-href'),
                data = {},
                elements = _this.closest('tr').find('input');

            elements.each(function (index, input) {
                var _input = $(input);
                data[_input.attr('name')] = _input.val();
            });

            if (!url) {
                return toastr.error("Wrong url!");
            }
            Swal.fire(swalDeleteConf).then((result) => {
                if (result.value) {
                    $.ajax({
                        url: url,
                        method: 'PUT',
                        contentType: 'application/json',
                        mimeType: 'application/json',
                        data: JSON.stringify(data),
                        success: function (data) {
                            processPromiseResponse(data['message'], 2000).then(function () {
                                return location.reload();
                            });
                        },
                        error: function (xhr) {
                            return toastr.error(xhr['responseJSON']['message']);
                        }
                    });
                }
            });

        })

        .on('click', '#product-cloner', function () {
            var form = $(this).closest('form'),
                productRow = form.find('.product-row');

            var cloned = productRow.clone();
            cloned.find('[data-type="input"]').each(function (index, item) {
                $(item).val('');
            });
            form.append(cloned);
        });
    // product-cloner
});