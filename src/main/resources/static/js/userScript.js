function getUsers() {
    $.ajax({
        url: "/api/admin/user",

        success: function (user) {
                $("#userTable").append("<tr> " +
                    "<td>" + user.id + "</td>" +
                    " <td>" + user.username + "</td>" +
                    " <td>" + user.email + "</td>" +
                    " <td>" + user.age + "</td>" +
                    " <td>" + user.roles.map(x => x.name).join(", ") + "</td>" +
                    " </tr> "


            );
            $("#userNavEmail").text(user.email);
            $("#userNavRoles").text(user.roles.map(x => x.name).join(", "));
            if (user.roles.map(x => x.name).indexOf("ROLE_ADMIN") === -1) {
                hideAdminElements();


            }
        }
    });
}
function hideAdminElements() {
    $("#adminHideButton").hide();

}

$(document).ready(function () {
    getUsers();

});