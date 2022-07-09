let main = {
    init: function () {

        const _this = this;

        $('#btn-save').on('click', function () {

            _this.save();
        });

        $('#btn-update').on('click', function () {

            _this.update();
        });

        $('#btn-delete').on('click', function () {

            _this.delete();
        });
    },

    getTokenHeader: function () {
        let tokenHeader = {};
        tokenHeader[$("meta[name='_csrf_header']").attr("content") || 'X-CSRF-TOKEN'] = $("meta[name='_csrf']").attr("content") || '';

        console.log(tokenHeader);

        return tokenHeader;
    },

    save: function () {

        const _this = this;
        const data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            headers: _this.getTokenHeader(),
            data: JSON.stringify(data)
        }).done(function () {

            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {

            alert(JSON.stringify(error));
        });
    },

    update: function () {

        const _this = this;
        const data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        const id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            headers: _this.getTokenHeader(),
            data: JSON.stringify(data)
        }).done(function () {

            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {

            alert(JSON.stringify(error));
        });
    },

    delete: function () {

        const _this = this;
        const id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            headers: _this.getTokenHeader()
        }).done(function () {

            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {

            alert(JSON.stringify(error));
        });
    }
};

main.init();