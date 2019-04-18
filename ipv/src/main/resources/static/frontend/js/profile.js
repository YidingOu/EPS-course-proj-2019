
uid = null;

$(document).ready(function() {
	$("#delete-btn").click(function() {
		deleteAccount();
	});
	$("#pause-btn").click(function() {
		pauseAccount();
	});
	$("#save-btn").click(function() {
		saveChanges();
	});
	uid = getUid();
});

/** Gets the uid of the current user */
function getUid() {
	return 1; 
}

/** Draws the alert confirmation box and sends an ajax request to delete account 
 *  upon confirmation. 
 */
function deleteAccount() {
	var cfm = confirm ("Are you sure you want to delete your account? This action cannot be undone. "); 
	if (cfm) { // User Pressed Yes, delete account 
	    var url = "users/" + uid;
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
	var cfm = confirm ("Are you sure you want to pause your account?"); 
	if (cfm) { // User Pressed Yes, pause account 
		return;
	}
	return;
}

/** Sends an ajax request to save account changes */
function saveChanges() {
    var post_url = "/users/update_pass"
    var request_method = "PUT"; 
    var main_url = "/frontend/src/main.html";
    var form_data = {
		displayName:$("#username").val(),  
		pass:$("#password").val(),
		name:$("#firstname").val() + " " + $("#lastname").val()
    };
    console.log(form_data)
    
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
        	console.log(JSON.stringify(e));
        	alert("Your profile failed to update, please try again.")
        }
    });
	return;
}