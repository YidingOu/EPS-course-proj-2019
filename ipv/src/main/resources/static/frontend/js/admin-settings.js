uid = null
PWD_LEN = 1 //TODO: change after testing
users = {}

$(document).ready(function() {
    validate();
    $("#save-btn").click(function() {
        saveChanges();
    });
    $("#logout").click(function() {
        logout();
    });
    uid = getUid();
    init();
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
    }
  });
};

//TODO: is there a reason why we do this
$.urlParam = function (name) {
    var results = new RegExp('[\?&]' + name + '=([^&#]*)')
        .exec(window.location.search);

    return (results !== null) ? results[1] || 0 : false;
}

/** Gets the jwt of the current user */
function getJwt() {
    try {
        return parseInt(localStorage.getItem('jwt'));
    } catch(error) {
        alert("Session expired, please login again. ")
        logout();
$(location).attr("href", "login.html");
    }
    return;
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
        headers: {
            "JWT_TOKEN_HEADER": getJwt()
        },
        cache: false,
        timeout: 600,
        success: function (data, textStatus, xhr) {
            console.log(data);
            $("#username").val(data.name);
            $("#first_name").val(data.firstName);
            $("#last_name").val(data.lastName);
            localStorage.jwt = xhr.getResponseHeader('JWT_TOKEN_HEADER'));
        },
        error: function (e) {
            console.log(e);
            alert("Error, please refresh page.");
        }
    });
}

/** Gets the uid of the current user */
function getUid() {
    try {
        return parseInt(localStorage.getItem('uid'));
    } catch(error) {
        alert("Session expired, please login again. ")
        logout();
        $(location).attr("href", "login.html");
    }
    return;
}

/** Performs validation of profile update form, ensures that username
 *  and password fields are not empty, and that passwords provided match.
 */
function validate() {
  $("#admin-profile").validate({
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
    $("#admin-profile").submit(function(event){
        if (!validFields()) return;
        var post_url = "/users/update_pass"
        var request_method = "PUT";
        var main_url = "/frontend/src/admin/dashboard.html";
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
            headers: {
                "JWT_TOKEN_HEADER": getJwt()
            },
            data: JSON.stringify(form_data),
            dataType: 'json',
            cache: false,
            timeout: 60000,
            success: function (data, textStatus, xhr) {
                console.log("success");
                localStorage.jwt = xhr.getResponseHeader('JWT_TOKEN_HEADER'));
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
    localStorage.clear();
}