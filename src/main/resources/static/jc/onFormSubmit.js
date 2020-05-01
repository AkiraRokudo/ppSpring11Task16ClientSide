$("form").on('submit', function (event) {
    //Итак у нас три варианта.
    // Либо мы тут с созданием
    // Либо с обновлением
    // Либо с удалением
    var submitEvent = event.originalEvent //получили оригинальное событие
    var button = submitEvent.submitter//получили ответственного за это
    var pathToAction = ''
    if ($(button).hasClass('btn-success')) {
        pathToAction = '/admin/create'
    } else if ($(button).hasClass('btn-primary')) {
        pathToAction = '/admin/edit'
    } else if ($(button).hasClass('btn-danger')) {
        pathToAction = '/admin/delete'
    }
    var data = $(this).serializeToJSON();

    //Во всех 3 случаях мы будем слать объект. Но на разные пост пути
    //по итогам все равно хайдим и обновляем страничку
    $.ajax({
        type: "post",
        url: pathToAction,
        contentType: "application/json",
        data: JSON.stringify(data),
        async:false, //чтобы апдейт корректный был
        success: function (e) {
            //alert("Finally" + e);
        },
        error: function (s) {
            alert("Error")
        },
    })


    event.preventDefault() // перестраховка, на случай если глупый прогер забыл убрать экшен.
    if (pathToAction != '/admin/create') {
        $("#userModal").modal('hide')
    } else {
        $.ajax({
            url: '/admin/allusers',         /* Куда пойдет запрос. Забито четко, т.к. в иных местах модалок нет*/
            method: 'get',             /* Метод передачи (post или get) */
            dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
            data: {},     /* Параметры передаваемые в запросе. */
            success: function (listUsers) {
                fillTable($('#userTable'), listUsers)
            }
        });

        //переключимся между вкладками
        var userListTab = $('#userListTab')
        userListTab.click()
        //и очистим форму.
        $(this)[0].reset()
    }
});
