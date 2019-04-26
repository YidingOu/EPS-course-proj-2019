uid = null
PWD_LEN = 1 //TODO: change after testing
users = {}

$(document).ready(function() {
    validate();
    $("#logout").click(function() {
        logout();
    });
    uid = getUid();
    init();
    getUsernames();
});

/** Performs validation of profile update form, ensures that username
 *  and password fields are not empty, and that passwords provided match.
 */
function validate() {
  $("#staff-profile").validate({
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
        minlength: PWD_LEN
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
};

//TODO: is there a reason why we do this
$.urlParam = function (name) {
    var results = new RegExp('[\?&]' + name + '=([^&#]*)')
        .exec(window.location.search);

    return (results !== null) ? results[1] || 0 : false;
}

/** Populates the first name, last name, username fields of the current user */
function init() {
    //var id = $.urlParam('id');
    console.log("/users/" + uid);
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/users/" + uid,
        dataType: 'json',
        cache: false,
        timeout: 600,
        success: function (data) {
            console.log(data);
            $("#username").val(data.name);
            $("#first_name").val(data.firstName);
            $("#last_name").val(data.lastName);
        },
        error: function (e) {
            console.log(e);
            alert("Error, please refresh page.");
        }
    });
}

/** Populate the side navigation with users staff is chatting with.
 *  A dot icon is used to indicate unread messages
 *  Populates the chat box depending on the current username clicked
 *  on the nav bar.
 */
function populateView() {
    var htmlString = "";
    console.log(users);
    for (var postId in users) {
        htmlString += '<li id="user' + postId + '")">' +
                            '<a href="chat.html#' + postId + '">' +
                                '<i class="now-ui-icons users_single-02"></i>' +
                                '<p>' + users[postId] + '</p>' +
                            '</a>' +
                          '</li>';
    }
    htmlString += '<li class="active">' +
                    '<a href="./staff-settings.html">' +
                   	    '<i class="now-ui-icons ui-1_settings-gear-63"></i>' +
                   	     '<p>Account Settings</p>' +
                   	'</a>' +
                  '</li>';

    $("#nav-bar").html(htmlString);
}


/** Gets all usernames of users that the staff is chatting with
 *  and populates the nav bar */
function getUsernames() {
    var url = "/posts/by_staff/" + uid;
    var request_method = "GET";

    $.ajax({
        type: request_method,
        contentType: "application/json",
        url: url,
        cache: false,
        timeout: 60000,
        success: function (data) {
            console.log("success");
            for (var i=0; i<data.length; i++) {
                var chat = data[i];
                console.log(chat);
                if (chat.user != null) users[chat.id] = chat.user.name;
            }
            populateView();
        },
        error: function (e) {
            console.log("fail");
            console.log(e);
            alert("Error, please try again.")
            $(location).attr("href", "chat.html");
        }
    });
}

/** Gets the uid of the current user */
function getUid() {
    try {
        return parseInt(localStorage.getItem('uid'));
    } catch(error) {
        alert("Session expired, please login again. ")
        $(location).attr("href", "login.html");
    }
    return;
}

/** Checks to ensure valid fields for a profile update:
 *  (i) username, first name, last name, password fields are not empty
 *  (ii) password is the same as the confirmed password
 */
function validFields() {
    if ($("#password").val() == "" || $("#username").val() == ""
    || $("#first_name") == "" || $("#last_name") == "" ) return false;
    if ($("#password").val().length < PWD_LEN) return false;
    if ($("#password").val() != $("#password_cfm").val()) return false;
    return true;
}

/** Sends an ajax request to save account changes */
function saveChanges() {
    $("#staff-profile").submit(function(event){
        event.preventDefault(); //prevent default action
        if (!validFields()) return;
        var post_url = "/users/update_pass"
        var request_method = "PUT";
        var main_url = "/frontend/src/staff/chat.html";
        var form_data = {
            firstName:$("#first_name").val(),
            lastName:$("#last_name").val(),
            pass:$("#password").val(),
            name:$("#username").val(),
            id:uid
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
                alert("Profile successfully updated!");
                $(location).attr("href", main_url);

            },
            error: function (e) {
                console.log("fail");
                console.log(e);
                alert("Your profile failed to update, please try again.")
            }
        });
    })
};

/** Logout by deleting uid in localstorage and authentication token */
function logout() {
    //TODO
}