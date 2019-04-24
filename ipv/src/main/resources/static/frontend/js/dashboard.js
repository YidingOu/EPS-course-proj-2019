$(document).ready(function() {
	getStats();
	getCurrentCases();
});


/** Makes an ajax request to the server to get the number 
 *  of ongoing, paused, and closed cases. Populates html. */
function getStats() {
	return;
}

/** Makes an ajax request to the server to get information
 *  on the current cases (username, start date, last updated 
 *  date, and status of case). Populates html. */
function getCurrentCases() {
	$.ajax({
		type: "GET",
		contentType: "application/json",
		url: "/users",
		// data: JSON.stringify(form_data),
		dataType: 'json',
		cache: false,
		timeout: 600,
		success: function (data) {
			console.log("success");
			console.log(data);

			var role_map = {
				0 : 'User',
				1 : 'Staff',
				2 : 'Admin'
			};

			var content_html = '';
			for (var i = 0; i < data.length; i++) {
				content_html += '<td> <a href="dashboard.html"> '+ data[i].name +' </a></td>' + '\n';
				content_html += '<td> ' + role_map[data[i].role] + ' </td>' + '\n';
				content_html += '</tr>' + '\n';
			}

			document.getElementById("staff-table").innerHTML = content_html;

		},
		error: function (e) {
			console.log(e);
			alert("The username is taken, please try a different username.");
		}
	});
}