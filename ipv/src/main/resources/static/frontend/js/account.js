//https://bootsnipp.com/snippets/z8l2X
 
/** Performs validation of login form, ensures that username 
 *  and password fields are not empty. 
 */

PWD_LEN = 8;

$(function() {
   /*Quick solution Should be optimized later*/
   $("form[name='user_login']").validate({
     rules: {
       username: {
         required: true,
       },
       password: {
         required: true,
       }
     },
      username: {
        required: "Please enter your username",
      
       password: {
        required: "Please enter password",
       }
       
     },
     submitHandler: submitUserLogin()
   });
   
 });

$(function() {
   /*Quick solution Should be optimized later*/
   $("form[name='staff_login']").validate({
         rules: {
           username: {
             required: true,
           },
           password: {
             required: true,
           }
         },
          username: {
            required: "Please enter your username",

           password: {
            required: "Please enter password",
           }

         },
         submitHandler: submitStaffLogin()
       });
 });

/** Performs validation of registration form, ensures that username 
 *  and password fields are not empty, and that passwords provided match.
 */
$(function() {
  $("form[name='registration']").validate({
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
      password_cfm: "Passwords must match!"
    },
    submitHandler: submitRegistration()
  });
});

/** Checks to ensure valid fields for a form of @param type
 *  (either "login" or "registration"
 */
function validFields(type) {
    if ($("#password").val() == "" || $("#username").val() == "") return false;
    if (type == "registration") {
        if ($("#password").val().length < PWD_LEN) return false;
        if ($("#password").val() != $("#password_cfm").val()) return false;
    }
    return true;
}

/** Submits information in login form to server */ 
function submitUserLogin() {
  $("#user_login_form").submit(function(event){
    if (!validFields("login")) return;
    event.preventDefault(); //prevent default action 
    var post_url = $(this).attr("action"); //get form action url
    var request_method = $(this).attr("method");
    var main_url = "/frontend/src/main.html";
    var form_data = {
    		name:$("#username").val(),  
    		pass:$("#password").val()
    };
    console.log(post_url);
    $.ajax({
        type: request_method,
        contentType: "application/json",
        url: post_url,
        data: JSON.stringify(form_data),
        dataType: 'json',
        cache: false,
        timeout: 60000,
        success: function (data, textStatus, xhr) {
            console.log("success");
            localStorage.uid = data.entity.id;
            localStorage.jwt = xhr.getResponseHeader('JWT_TOKEN_HEADER');
        	$(location).attr("href", main_url);
        },
        error: function (e) {
        	console.log("fail");
        	console.log(e);
        	alert("Login failed, please try again.");
        }
    });
  });
}

/** Submits information in login form to server */ 
function submitStaffLogin() {
  $("#staff_login_form").submit(function(event){
    if (!validFields("login")) return;
    event.preventDefault(); //prevent default action
    var post_url = "/api/users/staffs/validate"; //get form action url
    var request_method = $(this).attr("method"); 
    var staff_url = "/frontend/src/staff/chat.html";
    var admin_url = "/frontend/src/admin/dashboard.html";
    var form_data = {
    		name:$("#username").val(),  
    		pass:$("#password").val()
    };
    $.ajax({
        type: request_method,
        contentType: "application/json",
        url: post_url,
        data: JSON.stringify(form_data),
        dataType: 'json',
        cache: false,
        timeout: 600,
        success: function (data, textStatus, xhr) {
            localStorage.jwt = xhr.getResponseHeader('JWT_TOKEN_HEADER');
        	if (data.state == 1) {
        		$(location).attr("href", staff_url);
                localStorage.uid = data.entity.id;
        	} else if (data.state == 2) {
        		$(location).attr("href", admin_url);
                localStorage.uid = data.entity.id;
        	} else {
        		alert("Login failed, please try again.");
        	}
        },
        error: function (e) {
        	console.log("fail");
        	console.log(e);
        	alert("Login failed, please try again.");

        }
    });
  });
}

/** Submits information in registration form to server */
function submitRegistration() {
  $("#reg_form").submit(function(event){
    if (!validFields("registration")) return;
    event.preventDefault(); //prevent default action
    var post_url = $(this).attr("action"); //get form action url
    var request_method = $(this).attr("method");
    var main_url = "/frontend/src/main.html";
    var form_data = {
    		name:$("#username").val(),
    		pass:$("#password").val()
    };
    $.ajax({
        type: request_method,
        contentType: "application/json",
        url: post_url,
        data: JSON.stringify(form_data),
        dataType: 'json',
        cache: false,
        timeout: 600,
        success: function (data, textStatus, xhr) {
        	console.log("success");
            console.log(data);
            localStorage.uid = data.id;
            localStorage.jwt = xhr.getResponseHeader('JWT_TOKEN_HEADER');
        	$(location).attr("href", main_url);
        },
        error: function (e) {
            console.log("fail");
            console.log(e);
        	alert("The username is taken, please try a different username.");
        }
    });
  });
}