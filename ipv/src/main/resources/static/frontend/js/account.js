//https://bootsnipp.com/snippets/z8l2X
 
/** Performs validation of login form, ensures that username 
 *  and password fields are not empty. 
 */
$(function() {
   $("form[name='login']").validate({
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
     submitHandler: submitLogin()
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
        minlength: 1
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
      email: "Please enter a valid email address",
      password_cfm: "Passwords must match!"
    },
    submitHandler: submitRegistration()
  });
});

/** Submits information in login form to server */ 
function submitLogin() {
  $("#login_form").submit(function(event){
    event.preventDefault(); //prevent default action 
    var post_url = $(this).attr("action"); //get form action url
    var request_method = $(this).attr("method"); 
    var form_data = $(this).serialize(); //Encode form elements for submission
    
    $.ajax({
      url : post_url,
      type: request_method,
      data : form_data
    }).done(function(response){ //
      alert(response)
    });
  });
}

/** Submits information in registraion form to server */ 
function submitRegistration() {
  $("#reg_form").submit(function(event){
    event.preventDefault(); //prevent default action 
    var post_url = $(this).attr("action"); //get form action url
    var request_method = $(this).attr("method"); 
    var form_data = $(this).serialize(); //Encode form elements for submission
    
    $.ajax({
      url : post_url,
      type: request_method,
      data : form_data
    }).done(function(response){ //
      alert(response)
    });
  });
}

