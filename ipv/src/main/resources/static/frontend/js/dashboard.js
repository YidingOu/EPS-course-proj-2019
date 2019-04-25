
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
        url: "/posts/count_info",
        dataType: 'json',
        cache: false,
        timeout: 600,
        success: function (data) {
            console.log("success");
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
        url: "/users/customers",
        dataType: 'json',
        cache: false,
        timeout: 600,
        success: function (data) {
            console.log("success");
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