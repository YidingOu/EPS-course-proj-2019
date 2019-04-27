
var uid = null;
var PWD_LEN = 8;

$(document).ready(function() {
    validate();
	$("#delete-btn").click(function() {
		deleteAccount();
	});
	$("#pause-btn").click(function() {
		pauseAccount();
	});
	$("#logout").click(function() {
        logout();
    });
	uid = getUid();
	//validate();
	getPostId();
	populateUsername();
});

/** Gets the uid of the current user */
function getUid() {
	try {
        return localStorage.getItem('uid');
    } catch(error) {
        alert("Session expired, please login again. ")
        $(location).attr("href", "login.html");
    }
    return;
}

function getPostId() {
    var post_url = "/api/posts/by_user/" + uid;
    var request_method = "GET";

    $.ajax({
        type: request_method,
        contentType: "application/json",
        url: post_url,
        cache: false,
        headers: {
            "JWT_TOKEN_HEADER": getJwt()
        },
        timeout: 60000,
        success: function (data, textStatus, xhr) {
            console.log("success");
            localStorage.jwt = xhr.getResponseHeader('JWT_TOKEN_HEADER'));
            postId = data.id;
        },
        error: function (e) {
            console.log("fail");
            console.log(e);
            alert("Session expired. Please login again. "); //TO CHANGE?
            $(location).attr("href", "login.html");
        }
    });
    return;
}

/** Populates the username field with the username of the current user */
function populateUsername() {
    console.log(uid);
	var url = "/api/users/" + uid;
    var request_method = "GET";

	$.ajax({
        type: request_method,
        contentType: "application/json",
        url: url,
        cache: false,
        timeout: 60000,
        success: function (data, textStatus, xhr) {
            console.log("success");
            localStorage.jwt = xhr.getResponseHeader('JWT_TOKEN_HEADER'));
        	$("#username").val(data.name);
        },
        error: function (e) {
        	console.log("fail");
        	console.log(e);
        	alert("Error, please refresh the page.")
        }
    });
}

/** Checks to ensure valid fields for a profile update:
 *  (i) username and password fields are not empty
 *  (ii) password is the same as the confirmed password
 */
function validFields() {
    if ($("#password").val() == "" || $("#username").val() == "") return false;
    if ($("#password").val().length < PWD_LEN) return false;
    if ($("#password").val() != $("#password_cfm").val()) return false;
    return true;
}

/** Performs validation of profile update form, ensures that name, username 
 *  and password fields are not empty, and that passwords provided match.
 */
function validate() {
    $("#user-profile").validate({
        rules: {
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
          username: "Please provide a username",
          password: {
            required: "Please provide a password",
            minlength: "Your password must be at least 8 characters long"
          },
        },
        submitHandler: saveChanges()
    });
};

/** Draws the alert confirmation box and sends an ajax request to delete account 
 *  upon confirmation. 
 */
function deleteAccount() {
	var cfm = confirm ("Are you sure you want to delete your account? This action cannot be undone. "); 
	if (cfm) { // User Pressed Yes, delete account 
	    var url = "/api/users/" + uid;
	    var request_method = "DELETE";
	    var main_url = "/frontend/src/login.html";
	    
	    $.ajax({
	        type: request_method,
	        contentType: "application/json",
	        url: url,
	        cache: false,
	        headers: {
                "JWT_TOKEN_HEADER": getJwt()
            },
	        timeout: 60000,
	        success: function (data, textStatus, xhr) {
                console.log("success");
                localStorage.jwt = xhr.getResponseHeader('JWT_TOKEN_HEADER'));
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

/** Draws the alert confirmation box and sends an ajax request to pause account 
 *  upon confirmation. 
 */
function pauseAccount() {
	var pwd = prompt("Please re-enter your password if you would like to lock your conversation. ");
	if (pwd && pwd != "") { // User Pressed Yes, pause account
		var url = "/api/posts/pause";
        var request_method = "POST";
        var main_url = "/frontend/src/login.html";
        var post_data = {
            id: postId,
            key: pwd
        }
        console.log(post_data);
        $.ajax({
            type: request_method,
            contentType: "application/json",
            data: JSON.stringify(post_data),
            dataType: 'json',
            url: url,
            headers: {
                "JWT_TOKEN_HEADER": getJwt()
            },
            cache: false,
            timeout: 60000,
            success: function (data, textStatus, xhr) {
                console.log("success");
                localStorage.jwt = xhr.getResponseHeader('JWT_TOKEN_HEADER'));
                alert("Conversation successfully locked! You can sign back in to resume your conversation in future.");
                logout();
                $(location).attr("href", main_url);
            },
            error: function (e) {
                console.log("fail");
                console.log(e);
                alert("Locking of conversation failed, please try again.")
            }
        });
        return;
	}
	return;
}

/** Sends an ajax request to save account changes */
function saveChanges() {
    $("#user-profile").submit(function(event){
        event.preventDefault(); //prevent default action
        if (!validFields()) return;
        var post_url = "/api/users/update_pass"
        var request_method = "PUT";
        var main_url = "/frontend/src/main.html";
        var form_data = {
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
                alert("Profile successfully updated!");
                $(location).attr("href", main_url);
            },
            error: function (e) {
                console.log("fail");
                console.log(JSON.stringify(e));
                alert("Your profile failed to update, please try again.")
            }
        });
    })
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