
$(document).ready(function () {
    getStats();
    getCurrentCases();
});

var statusMap = {
                10: 'Locked',
                1: 'Ongoing'
            };

/** Makes an ajax request to the server to get the number
 *  of ongoing, paused, and closed cases. Populates html. */
function getStats() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/posts/count_info",
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
            $("#ongoing-cases").html(data.onGoingPost);
            $("#paused-cases").html(data.pausedPost);
            $("#closed-cases").html(data.closedPost);
        },
        error: function (e) {
            console.log(e);
            alert("Error, please refresh the page.");
        }
    });
}

/** Makes an ajax request to the server to get information
 *  on the current cases (username, start date, last updated
 *  date, and status of case). Populates html. */
function getCurrentCases() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/users/customers",
        dataType: 'json',
        cache: false,
        headers: {
            "JWT_TOKEN_HEADER": getJwt()
        },
        timeout: 600,
        success: function (data, textStatus, xhr) {
            console.log("success");
            localStorage.jwt = xhr.getResponseHeader('JWT_TOKEN_HEADER'));
            console.log(data);

            var content_html = '';
            for (var i = 0; i < data.length; i++) {
                content_html += '<td> <a> ' + data[i].name + ' </a></td>' + '\n';
                var post = data[i].post;
                if (post != null) {
                    content_html += '<td> ' + post.updatedDate + ' </td>' + '\n';
                    content_html += '<td> ' + post.startDate + ' </td>' + '\n';
                    content_html += '<td> ' + statusMap[post.status] + ' </td>' + '\n';
                }
                content_html += '</tr>' + '\n';
            }
            document.getElementById("dashboard-table").innerHTML = content_html;

        },
        error: function (e) {
            console.log(e);
            alert("Error, please refresh the page.");
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