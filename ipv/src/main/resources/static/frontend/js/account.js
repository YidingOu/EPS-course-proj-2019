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
function submitLogin() {
  $("#login_form").submit(function(event){
    event.preventDefault(); //prevent default action 
    var post_url = $(this).attr("action"); //get form action url
    var request_method = $(this).attr("method"); 
    var form_data = $(this).serialize(); //Encode form elements for submission

    $.ajax({
      url : post_url,
      method: request_method,
      data : form_data,
      datatype: 'json'
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
      method: request_method,
      data : form_data
    }).done(function(response){ //
      alert(response)
    });
  });
}

