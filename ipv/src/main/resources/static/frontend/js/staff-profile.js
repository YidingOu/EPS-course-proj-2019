$(document).ready(function () {
    init();
    $("#delete-btn").click(function () {
        deleteAccount();
    });
    $("#save-btn").click(function () {
        saveChanges();
    });

});

$.urlParam = function (name) {
    var results = new RegExp('[\?&]' + name + '=([^&#]*)')
        .exec(window.location.search);

    return (results !== null) ? results[1] || 0 : false;
}

function init() {
    var id = $.urlParam('id');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/users/" + id,
        // data: JSON.stringify(form_data),
        dataType: 'json',
        cache: false,
        timeout: 600,
        success: function (data) {
            console.log(data);
            $("#username").val(data.name);
            $("#first-name").val(data.firstName);
            $("#last-name").val(data.lastName);

        },
        error: function (e) {
            console.log(e);
            alert("Wrong.");
        }
    });

}

/** Draws the alert confirmation box and sends an ajax request to delete account
 *  upon confirmation.
 */
function deleteAccount() {
    var cfm = confirm("Are you sure you want to delete your account? This action cannot be undone. ");
    if (cfm) { // User Pressed Yes, delete account
        var url = "users/" + uid;
        var request_method = "DELETE";
        var main_url = "/frontend/src/main.html";

        $.ajax({
            type: request_method,
            contentType: "application/json",
            url: url,
            cache: false,
            timeout: 60000,
            success: function (data) {
                console.log("success");
                $(location).attr("href", main_url);

            },
            error: function (e) {
                console.log("fail");
                console.log(JSON.stringify(e));
                alert("Deletion of account failed, please try again.")
            }
        });
        return;
    }
    return;
}

/** Populates the username field with the username of the current user */
function populateUsername() {
    var url = "users/" + uid;
    var request_method = "GET";

    $.ajax({
        type: request_method,
        contentType: "application/json",
        url: url,
        cache: false,
        timeout: 60000,
        success: function (data) {
            console.log("success");
            $("#username").val(data.name);
        },
        error: function (e) {
            console.log("fail");
            console.log(JSON.stringify(e));
            alert("Error, please refresh page.")
        }
    });
}

/** Performs validation of profile update form, ensures that username
 *  and password fields are not empty, and that passwords provided match.
 */
$(function () {
    $("form[name='staff-profile']").validate({
        rules: {
            first_name: {
                required: true
            },
            last_name: {
                required: true
            },
            username: {
                required: true
            },
            password: {
                required: true,
                minlength: 1		//TODO: change to min length of 8
            },
            password_cfm: {
                equalTo: "#password"
            }
        },
        messages: {
            last_name: "Please provide a last name",
            first_name: "Please provide a first name",
            username: "Please provide a username",
            password: {
                required: "Please provide a password",
                minlength: "Your password must be at least 8 characters long"
            },
            password_cfm: "Passwords must match!"
        },
        submitHandler: saveChanges()
    });
});

/** Sends an ajax request to save account changes */
function saveChanges() {
    var post_url = "/users/update_pass"
    var request_method = "PUT";
    var main_url = "/frontend/src/staff/main.html";
    var form_data = {
        // TODO: first and last name??
        pass: $("#password").val(),
        name: $("#username").val(),
        id: uid
    };
    console.log(form_data);

    $.ajax({
        type: request_method,
        contentType: "application/json",
        url: post_url,
        data: JSON.stringify(form_data),
        dataType: 'json',
        cache: false,
        timeout: 60000,
        success: function (data) {
            console.log("success");
            $(location).attr("href", main_url);
        },
        error: function (e) {
            console.log("fail");
            console.log(JSON.stringify(e));
            alert("Your profile failed to update, please try again.")
        }
    });
};

