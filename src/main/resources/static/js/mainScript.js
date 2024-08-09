function getUsers() {
    $.ajax({
        url: "/api/admin/users",

        success: function (result) {
            $("#adminUsersTable").empty();
            result.forEach(function (user) {
                $("#adminUsersTable").append("<tr> " +
                    "<td>" + user.id + "</td>" +
                    " <td>" + user.username + "</td>" +
                    " <td>" + user.email + "</td>" +
                    " <td>" + user.age + "</td>" +
                    " <td>" + user.roles.map(x => x.name).join(", ") + "</td>" +
                    '<td>' +
                    '<button type="button" id = "edit" class="btn btn-info hide"  style="color: white" data-toggle="modal"  >Изменить  </button> ' +
                    '</td>' +
                    '<td>' +
                    '<button type="button" id = "delete" class="btn btn-danger hide"  style="color: white" data-toggle="modal" >Удалить </button>' +
                    '</td>' +
                    " </tr> "
                )
                ;
            });


        }
    });
}
function hideAdminElements() {
    $(".hide").hide();
    $("#adminPanel").text("Информация юзера");
}

$(document).ready(function () {
    getUsers();

    $.ajax({
        url: "/api/admin/user",
        success: function (result) {
            let user = result;
            $("#userNavEmail").text(user.email);
            $("#userNavRoles").text(user.roles.map(x => x.name).join(", "));
            if (user.roles.map(x => x.name).indexOf("ROLE_ADMIN") === -1) {
                hideAdminElements();


            }
        }

    });

    $('#adminUsersTable').on('click', '#edit', function (event) {
        console.log(event);
        $("#modalEdit").show();
        let id = $(this).closest('tr').find('td:first').text();
        let username = $(this).closest('tr').find('td:nth-child(2)').text();
        let email = $(this).closest('tr').find('td:nth-child(3)').text();
        let age = $(this).closest('tr').find('td:nth-child(4)').text();
        $("#roleUp").empty();
        $.ajax({
            url: "/api/admin/roles",
            success: function (result) {
                result.forEach(function (role) {
                    $("#roleUp").append('<option value="' + role + '">' + role + '</option>');
                });
            }
        })
        $("#idUp").val(id);
        $("#usernameUp").val(username);
        $("#emailUp").val(email);
        $("#ageUp").val(age);
        console.log(id);


    });
    $('.closeEdit').on('click', function (event) {
        console.log(event);
        $("#modalEdit").hide();
        $("#deleteModal").hide();
    });
    $('#submitEdit').on('click', function (event) {
        $.ajax({
            url: "/api/admin/update",
            method: "PATCH",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                id: $("#idUp").val(),
                username: $("#usernameUp").val(),
                password: $("#passwordUp").val(),
                email: $("#emailUp").val(),
                age: $("#ageUp").val(),
                roles: $("#roleUp").val()
            }),
            success: function (result) {
                getUsers();
            }
        });
        $("#modalEdit").hide();
    });
    $('#adminUsersTable').on('click', '#delete', function (event) {
        $("#deleteModal").show();
        let id = $(this).closest('tr').find('td:first').text();
        let username = $(this).closest('tr').find('td:nth-child(2)').text();
        let email = $(this).closest('tr').find('td:nth-child(3)').text();
        let age = $(this).closest('tr').find('td:nth-child(4)').text();
        let roles = $(this).closest('tr').find('td:nth-child(5)').text();
        $("#delRole").empty();
        roles.split(", ").forEach(function (role) {
            $("#delRole").append('<option value="' + role + '">' + role + '</option>');
        });
        $("#idDelete").val(id);
        $("#nameDelete").val(username);
        $("#emailDelete").val(email);
        $("#ageDelete").val(age);
        console.log(id);
    });

    $('#submitDelete').on('click', function (event) {
        $.ajax({
            url: "/api/admin/delete/" + $("#idDelete").val(),
            method: "DELETE",
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                getUsers();
            }
        });
        $("#deleteModal").hide();

    });
    $('.usersTable').on('click', function (event) {
        return show('showUsers', 'addUser');
    })
    $('.newUserPage').on('click', function (event) {
        $("#newRole").empty();
        $.ajax({
            url: "/api/admin/roles",
            success: function (result) {
                result.forEach(function (role) {
                    $("#newRole").append('<option value="' + role + '">' + role + '</option>');
                });
            }
        })
        return show('addUser', 'showUsers');
    });
    $('#addNewUser').on('click', function (event) {
        $.ajax({
            url: "/api/admin/new",
            method: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                username: $("#newUser").val(),
                password: $("#newPassword").val(),
                email: $("#newEmail").val(),
                age: $("#newAge").val(),
                roles: $("#newRole").val()
            }),
            success: function (result) {
                $("#newUser").empty();
                $("#newPassword").empty();
                $("#newEmail").empty();
                $("#newAge").empty();
                $("#newRole").empty();
                getUsers();
                return show('showUsers', 'addUser');
            }
        });
    });
});

function show(shown, hidden) {
    document.getElementById(shown).style.display = 'block';
    document.getElementById(hidden).style.display = 'none';
    return false;
}


