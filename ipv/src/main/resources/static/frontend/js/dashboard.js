$(document).ready(function () {
    getStats();
    getCurrentCases();
});


/** Makes an ajax request to the server to get the number
 *  of ongoing, paused, and closed cases. Populates html. */
function getStats() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/posts",
        dataType: 'json',
        cache: false,
        timeout: 600,
        success: function (data) {
            console.log("success");
            console.log(data);
            var content_html = data.id;
            document.getElementById("ongoing-cases").innerHTML = content_html;

        },
        error: function (e) {
            console.log(e);
            alert("Alert.");
        }
    });

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/posts",
        dataType: 'json',
        cache: false,
        timeout: 600,
        success: function (data) {
            console.log("success");
            console.log(data);

            var content_html = data.id;
            document.getElementById("paused-cases").innerHTML = content_html;

        },
        error: function (e) {
            console.log(e);
            alert("Alert.");
        }
    });

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/posts",
        dataType: 'json',
        cache: false,
        timeout: 600,
        success: function (data) {
            console.log("success");
            console.log(data);

            var content_html = data.id;
            document.getElementById("closed-cases").innerHTML = content_html;

        },
        error: function (e) {
            console.log(e);
            alert("Alert.");
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
        url: "/users",
        dataType: 'json',
        cache: false,
        timeout: 600,
        success: function (data) {
            console.log("success");
            console.log(data);

            var content_html = '';
            for (var i = 0; i < data.length; i++) {
                content_html += '<td> <a href="dashboard.html"> ' + data[i].name + ' </a></td>' + '\n';
                content_html += '<td> ' + data[i].date + ' </td>' + '\n';
                content_html += '<td> ' + data[i].status + ' </td>' + '\n';
                content_html += '</tr>' + '\n';
            }
            document.getElementById("dashboard-table").innerHTML = content_html;

        },
        error: function (e) {
            console.log(e);
            alert("Alert.");
        }
    });
}