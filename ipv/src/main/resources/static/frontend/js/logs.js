$(document).ready(function () {
    getLogs();
});


/** Makes an ajax request to the server to get information
 *  on the logs (timestamp, action, user, staff, IP).
 *  Populates html. */
function getLogs() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/audits",
        headers: {
            "JWT_TOKEN_HEADER": getJwt()
        },
        dataType: 'json',
        cache: false,
        timeout: 60000,
        success: function (data, textStatus, xhr) {
            console.log("success");
            if (xhr.getResponseHeader('JWT_TOKEN_HEADER') != null) localStorage.jwt = xhr.getResponseHeader('JWT_TOKEN_HEADER');
            console.log(data);

            var role_map = {
                0: 'User',
                1: 'Staff',
                2: 'Admin'
            };

            var content_html = '';
            for (var i = 0; i < data.length; i++) {
                content_html += '<td> ' + data[i].date + ' </td>' + '\n';
                content_html += '<td> ' + data[i].action + ' </td>' + '\n';
                content_html += '<td> ' + data[i].userId + ' </td>' + '\n';
                content_html += '</tr>' + '\n';
            }

            document.getElementById("log-table").innerHTML = content_html;

        },
        error: function (e) {
            console.log(e);
            alert("Error, please login again.");
            logout();
        }
    });
}

/** Gets the jwt of the current user */
function getJwt() {
    try {
        return localStorage.getItem('jwt');
    } catch(error) {
        alert("Session expired, please login again. ")
        logout();
    }
    return;
}

/** Logout by deleting uid in localstorage and authentication token */
function logout() {
    localStorage.clear();
    $(location).attr("href", "../staff/login.html");
}