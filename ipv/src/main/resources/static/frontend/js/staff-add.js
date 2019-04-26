
PWD_LEN = 1; //TODO CHANGE

/** Performs validation of registration form, ensures that username
 *  and password fields are not empty, and that passwords provided match.
 */
$(function() {
  $("form[name='new-staff']").validate({
    rules: {
      username: {
        required: true
      },
      first_name: {
        required: true
      },
      last_name: {
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
    submitHandler: submitNewStaff()
  });
});

/** Checks to ensure valid fields for a form of @param type
 *  (either "login" or "registration" or "staff"
 */
function validFields(type) {
    if ($("#password").val() == "" || $("#username").val() == "") return false;
    if (type == "registration" || type == "staff") {
        if ($("#password").val().length < PWD_LEN) return false;
        if ($("#password").val() != $("#password_cfm").val()) return false;
    }
    if (type == "staff") {
        if ($("#first_name").val() == "" || $("#last_name").val() == "") return false;
    }
    return true;
}

/** Submits information in registration of new staff form to server */
function submitNewStaff() {
  $("#new-staff").submit(function(event){
    if (!validFields("staff")) return;
    event.preventDefault(); //prevent default action
    var post_url = "/users"
    var request_method = "POST";
    var main_url = "/frontend/src/staff/staff.html";
    var form_data = {
    		name:$("#username").val(),
    		pass:$("#password").val(),
    		firstName:$("#first_name").val(),
            lastName:$("#last_name").val(),
    };
    $.ajax({
        type: request_method,
        contentType: "application/json",
        url: post_url,
        data: JSON.stringify(form_data),
        dataType: 'json',
        cache: false,
        timeout: 600,
        success: function (data) {
        	console.log("success");
            console.log(data);
        	$(location).attr("href", main_url);
        },
        error: function (e) {
            console.log(e);
        	alert("The username is taken, please try a different username.");
        }
    });
  });
}
