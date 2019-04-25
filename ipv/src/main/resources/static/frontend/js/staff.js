var STAFF = 1;      //Constant used to represent a staff user

$(document).ready(function () {
    getStaff();
});


/** Makes an ajax request to the server to get information
 *  on the current staff (username, start date, case assigned).
 *  Populates html. */
function getStaff() {
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
                if (data[i].role != STAFF) continue;
                content_html += '<td> <a href=\'staff1.html?id=' + data[i].id + ' \'> ' + data[i].firstName + " " + data[i].lastName + ' </a></td>' + '\n';
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

function populateCases(staffId) {
    console.log("/posts/by_staff/" + staffId);
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/posts/by_staff/" + staffId,
        dataType: 'json',
        cache: false,
        timeout: 600,
        success: function (data) {
            console.log("success");
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

//  function arrToStrin