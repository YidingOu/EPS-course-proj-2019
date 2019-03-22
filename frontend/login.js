
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
             submitHandler: function(form) {
               form.submit();
             }
           });
         });
         


$(function() {
  
  $("form[name='registration']").validate({
    rules: {
      username: "required",
      email: {
        required: true,
        email: true
      },
      password: {
        required: true,
        minlength: 8
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
  
    submitHandler: function(form) {
      form.submit();
    }
  });
});
