var STAFF = 1;      //Constant used to represent a staff user

$(document).ready(function () {
    getStaff();
});


/** Makes an ajax request to the server to get information
 *  on the current staff (username, Date Created, Last Update, case assigned).
 *  Populates html.
 */
function getStaff() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/users",
        dataType: 'json',
        headers: {
            "JWT_TOKEN_HEADER": getJwt()
        },
        cache: false,
        timeout: 600,
        success: function (data, textStatus, xhr) {
            console.log("success");
            localStorage.jwt = xhr.getResponseHeader('JWT_TOKEN_HEADER'));
            console.log(data);

            var content_html = '';
            for (var i = 0; i < data.length; i++) {
                if (data[i].role != STAFF) continue;
                content_html += '<td> <a href=\'view-staff.html#' + data[i].id + ' \'> ' + data[i].firstName + " " + data[i].lastName + ' </a></td>' + '\n';
                content_html += '<td> ' + data[i].name + ' </td>' + '\n';
                content_html += '<td id="staff' + data[i].id + '"> </td>' + '\n';
                content_html += '</tr>' + '\n';
                populateCases(data[i].id);
            }
            document.getElementById("staff-table").innerHTML = content_html;

        },
        error: function (e) {
            console.log("fail");
            console.log(e);
            alert("Error, please refresh the page. ");
        }
    });
}

/** Gets the username of users that the staff of @param staffId is chatting with.
 *  Populates the table accordingly.
 */
function populateCases(staffId) {
    console.log("/api/posts/by_staff/" + staffId);
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/posts/by_staff/" + staffId,
        dataType: 'json',
        cache: false,
        headers: {
            "JWT_TOKEN_HEADER": getJwt()
        },
        timeout: 600,
        success: function (data, textStatus, xhr) {
            console.log("success");
            localStorage.jwt = xhr.getResponseHeader('JWT_TOKEN_HEADER'));
            var usernames = [];
            for (var i=0; i<data.length; i++) {
                var user = data[i].user;
                if (user != null) {
                    usernames.push(user.name);
                }
            }
            console.log(usernames);
            if (usernames.length == 0) {
                $("#staff" + staffId).html('None');
            } else {
                $("#staff" + staffId).html(usernames.toString());
            }

        },
        error: function (e) {
            console.log(e);
            alert("Error, please refresh the page. ");
        }
    });
}


/** Gets the jwt of the current user */
function getJwt() {
    try {
        return parseInt(localStorage.getItem('jwt'));
    } catch(error) {
        alert("Session expired, please login again. ")
        $(location).attr("href", "login.html");
    }
    return;
}

/** Logout by deleting uid in localstorage and authentication token */
function logout() {
    localStorage.clear();
}