//https://bootsnipp.com/snippets/z8l2X
 
/** Performs validation of login form, ensures that username 
 *  and password fields are not empty. 
 */
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
        minlength: 1      //TODO: change to min length of 8
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

/**
 * Retrieves input data from a form and returns it as a JSON object.
 * @param  {HTMLFormControlsCollection} elements  the form elements
 * @return {Object}  form data as an object literal
 */
const formToJSON = elements => [].reduce.call(elements, (data, element) => {
  data[element.name] = element.value;
  return data;
}, {});


/** Submits information in login form to server */ 
function submitUserLogin() {
  $("#user_login_form").submit(function(event){
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
        timeout: 60000,
        success: function (data) {
            console.log("success");
            localStorage.uid = data.entity.id;
            //console.log(JSON.stringify(data));
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
    event.preventDefault(); //prevent default action 
//    var post_url = $(this).attr("action"); //get form action url
    var post_url = "/users/staffs/validate"; //get form action url
    var request_method = $(this).attr("method"); 
    var staff_url = "/frontend/src/staff/main.html";
    var admin_url = "/frontend/src/staff/dashboard.html";
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
        success: function (data) {
        	if (data.state == 1) {
        		$(location).attr("href", staff_url);
                localStorage.uid = data.id;
        	} else if (data.state == 2) {
        		$(location).attr("href", admin_url);
            localStorage.uid = data.id;
        	} else {
        		alert("Login failed, please try again.");
        	}
        },
        error: function (e) {
        	console.log("fail");
        	alert("Login failed, please try again.");

        }
    });
  });
}

/** Submits information in registraion form to server */ 
function submitRegistration() {
  $("#reg_form").submit(function(event){
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
        success: function (data) {
        	console.log("success");
          console.log(data);
          localStorage.uid = data.id;
        	$(location).attr("href", main_url);
        },
        error: function (e) {
          console.log(e);
        	alert("The username is taken, please try a different username.");
        }
    });
  });
}

