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
        url: "/users",
        // data: JSON.stringify(form_data),
        dataType: 'json',
        cache: false,
        timeout: 600,
        success: function (data) {
            console.log("success");
            console.log(data);

            var role_map = {
                0: 'User',
                1: 'Staff',
                2: 'Admin'
            };

            var content_html = '';
            for (var i = 0; i < data.length; i++) {
                content_html += '<td> ' + data[i].date + ' </td>' + '\n';
                content_html += '<td> ' + data[i].status + ' </td>' + '\n';
                content_html += '<td> ' + role_map[data[i].role] + ' </td>' + '\n';
                content_html += '</tr>' + '\n';
            }

            document.getElementById("log-table").innerHTML = content_html;

        },
        error: function (e) {
            console.log(e);
            alert("Alert.");
        }
    });
}