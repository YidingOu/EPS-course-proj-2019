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
});

/** Draws the alert confirmation box and sends an ajax request to delete account 
 *  upon confirmation. 
 */
function deleteAccount() {
	var cfm = confirm ("Are you sure you want to delete your account? This action cannot be undone. "); 
	if (cfm) { // User Pressed Yes, delete account 
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
	return;
}