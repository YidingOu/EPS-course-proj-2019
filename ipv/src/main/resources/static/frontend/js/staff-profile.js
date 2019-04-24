uid = null
PWD_LEN = 1 //TODO: change after testing

$(document).ready(function() {
	$("#delete-btn").click(function() {
		deleteAccount();
	});
	$("#save-btn").click(function() {
		saveChanges();
	});
	uid = getUid()
	populateFields();
	//validate();
});

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

/** Draws the alert confirmation box and sends an ajax request to delete account 
 *  upon confirmation. 
 */
function deleteAccount() {
	var cfm = confirm ("Are you sure you want to delete your account? This action cannot be undone. "); 
	if (cfm) { // User Pressed Yes, delete account 
	    var url = "/users/" + uid;
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
	        	console.log(e);
	        	alert("Deletion of account failed, please try again.")
	        }
	    });
		return;
	}
	return;
}

/** Populates the username field with the username of the current user */
function populateFields() {
	var url = "/users/" + uid;
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
        	$("#first_name").val(data.firstName);
        	$("#last_name").val(data.lastName);
        },
        error: function (e) {
        	console.log("fail");
        	console.log(e);
        	alert("Error, please refresh page.")
        }
    });
}

/** Checks to ensure valid fields for a profile update:
 *  (i) username, first name, last name, password fields are not empty
 *  (ii) password is the same as the confirmed password
 */
function validFields() {
    if ($("#password").val() == null || $("#username").val() == null
    || $("#first_name") == null || $("#last_name") == null ) return false;
    if ($("#password").val().length < PWD_LEN) return false;
    if ($("#password").val() != $("#password_cfm").val()) return false;
    return true;
}

/** Performs validation of profile update form, ensures that username 
 *  and password fields are not empty, and that passwords provided match.
 */
function validate() {
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

/** Sends an ajax request to save account changes */
function saveChanges() {
	var post_url = "/users/update_pass"
    var request_method = "PUT"; 
    var main_url = "/frontend/src/staff/main.html";
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
        	$(location).attr("href", main_url);
        },
        error: function (e) {
        	console.log("fail");
        	console.log(e);
        	alert("Your profile failed to update, please try again.")
        }
    });
	return;
};

