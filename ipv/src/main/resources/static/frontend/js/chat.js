//adapted from https://bootsnipp.com/snippets/ZlkBn

var uid = null;
var postId = null;
var posts = [];
var locationId = null;

$(document).ready(function() {
    uid = getUid();
    if (isFirstVisit()) {
        alert("Worried that someone might be looking over your shoulder? " +
                "Press the Esc key twice for an automatic redirection to google.com");
    }
    $('.send_message').click(function () {
        return sendMessage(getMessageText());
    });
    $('.message_input').keyup(function (e) {
        if (e.which === 13) {
            return sendMessage(getMessageText());
        }
    });
    $(document).keyup(function(e) {
         if (e.key === "Escape") { 
            $(document).keyup(function(e) {
                if (e.key === "Escape") { 
                    redirect();
                }
            })
        }
    });
    $('.send_location').click(function() {
        sendLocation();
    })
    $("#logout").click(function() {
        logout();
    });
    getPosts(uid);
})

/** If the local variable first is not set, set it to 1 and return true;
 *  else return false.
 */
function isFirstVisit() {
    var first = localStorage.getItem("first");
    if (first == null) {
        localStorage.first = 1
        return true;
    }
    return false;
}

/** Defines message object with a draw function that assigns it its position 
 *  based on the user who posted the message (ie if sender is user, message appears
 *  on the right, else message appears on the left.)
 */
Message = function (arg) {
    this.text = arg.text, this.senderId = arg.senderId;
    this.draw = function (_this) {
        return function () {
            var $message;
            message_side = (_this.senderId == uid) ? "right" : "left"
            $message = $($('.message_template').clone().html());
            $message.addClass(message_side).find('.text').html(_this.text);
            $('.messages').append($message);
            return setTimeout(function () {
                return $message.addClass('appeared');
            }, 0);
        };
    }(this);
    return this;
};

/** Returns the uid of the current user (as stored in localstorage) */
function getUid() {
    try {
        return parseInt(localStorage.getItem('uid'));
    } catch(error) {
        alert("Session expired, please login again. ")
        $(location).attr("href", "login.html");
    }
    return;
}

/** Gets the chat history of the user with staff
    Returns: array of messages
*/
function getPosts(userId) {
    var post_url = "/posts/by_user/" + userId;
    var request_method = "GET"; 
    
    $.ajax({
        type: request_method,
        contentType: "application/json",
        url: post_url,
        cache: false,
        timeout: 60000,
        success: function (data) {
            console.log(post_url);
            console.log("success");
            while (data.status == 10) {
                //conversation is paused
                console.log("is paused");
                //TODO
                var pwd = prompt("Please input your password if you would like " +
                "to resume your conversation with the shelter: ");
                if (resumeConversation(pwd)) {
                    getPosts(userId);
                    break;
                }
                else alert("Incorrect password, please try again. ")
            }
            console.log(data);
            postId = data.id;
            getMessages(postId);
            getLocationInfo();
        },
        error: function (e) {
            console.log("fail");
            console.log(e);
            alert("Session expired. Please login again. "); //TO CHANGE?
            $(location).attr("href", "login.html");
        }
    });
    return;
}

/** Resumes the conversation with the shelter by invoking the api */
function resumeConversation(pwd) {
    //TODO
    return false;
}

/** Gets messages from the same conversation (ie same post id) and draws to screen */
function getMessages(postId) {
    var posts = [];
    var post_url = "/conversations/by_post/" + postId;
    var request_method = "GET"; 
    
    $.ajax({
        type: request_method,
        contentType: "application/json",
        url: post_url,
        cache: false,
        timeout: 60000,
        success: function (data) {
            console.log("success");
            console.log(data);
            for (var i=0; i<data.length; i++) {
                msg = new Message({
                    text: data[i].data,
                    senderId : data[i].userId
                });
                posts.push(msg);
            }
            drawPosts(posts);
        },
        error: function (e) {
            console.log("fail");
            console.log(e)
            alert("Session expired. Please login again. "); 
            $(location).attr("href", "login.html");
        }
    });
}

function test() {
    var arr = []
    var msg = new Message({
                            text: "hi",
                            senderId : 1
                        });
    arr.push(msg);

    msg = new Message({
                            text: "bye",
                            senderId : uid
                        });
    arr.push(msg);
    return arr;
}

/** Draws all @param posts, where posts are msg objects */
function drawPosts(posts) {
    for (var i=0; i<posts.length; i++) {
        posts[i].draw();
    }
}

/** Returns the value input in the user's textbox */
function getMessageText() {
    var $message_input;
    $message_input = $('.message_input');
    return $message_input.val();
}

/** Sends @param text to server and draws the message sent on screen */
function sendMessage(text) {
    var $messages, message;
    if (text.trim() === '') {
        return;
    }
    $('.message_input').val('');
    $messages = $('.messages');
    senderId = uid
    message = new Message({
        text: text,
        senderId: uid
    });
    console.log("sending");
    //send message to server, draw on success 
    if (addPost(message)) {
        posts.push(message);
        message.draw();
        return $messages.animate({ scrollTop: $messages.prop('scrollHeight') }, 300);
    }
   return;
}

/** Creates a new post using @param message and sends it to the server */
function addPost(message) {
    var url = "/conversations";
    var request_method = "POST"; 
    var post_data = {
        data: message.text,
        postId: postId,
        userId:uid
    };
    
    $.ajax({
        type: request_method,
        contentType: "application/json",
        url: url,
        data: JSON.stringify(post_data),
        dataType: 'json',
        cache: false,
        timeout: 60000,
        success: function (data) {
            console.log("success");
            return true;
        },
        error: function (e) {
            console.log("fail");
            console.log(e);
            alert("Error, please try again."); 
            return false;
        }
    });
    return true;
}

/** Perform a series of redirects to google.com */
function redirect() {
    window.location.href = "http://google.com";
}

/** Prompts user to input location and sends information to server. */ 
function sendLocation() {
    var location = prompt("Please enter the location you wish to share. " +
        "This information is strictly confidential and will be automatically deleted after a week.");
    var url = "/contacts";
    if (location == "") {
        alert("Input cannot be empty!")
        return;
    }
    if (location == null) return;
    var request_method = "POST";
    var post_data = {
        address: location,
        postId: postId
    };

    $.ajax({
        type: request_method,
        contentType: "application/json",
        url: url,
        data: JSON.stringify(post_data),
        dataType: 'json',
        cache: false,
        timeout: 60000,
        success: function (data) {
            console.log("success");
            console.log(data);
            alert("Successfully sent your location information! ")
            displayLocationInfo(location);
        },
        error: function (e) {
            console.log("fail");
            console.log(e);
            alert("Error, please try again.");
        }
    });
    return;
}

/** Get location information that the user corresponding to @param post has sent */
function getLocationInfo() {
    var url = "/contacts/by_post/" + postId;
    var request_method = "GET";
    console.log(url);

    $.ajax({
        type: request_method,
        contentType: "application/json",
        url: url,
        cache: false,
        timeout: 60000,
        success: function (data) {
            console.log("success");
            console.log(data);
            locationId = data.id;
            console.log(locationId);
            displayLocationInfo(data.address);
        },
        error: function (e) {
            console.log("fail");
            console.log(e);
        }
    });
}

function displayLocationInfo(location) {
    $("#location").removeClass("hidden");
    $("#location_info").html(location);
}

function hideLocationInfo() {
    $("#location").addClass("hidden");
}

function editLocation() {
    var location = prompt("Update the location information you wish to share: ")
    var url = "/contacts";
    if (location == "") {
        alert("Input cannot be empty!")
        return;
    }
    if (location == null) return;

    var request_method = "PUT";
    var post_data = {
        address: location,
        postId: postId,
        id: locationId
    };
    console.log(post_data);
    $.ajax({
        type: request_method,
        contentType: "application/json",
        url: url,
        data: JSON.stringify(post_data),
        dataType: 'json',
        cache: false,
        timeout: 60000,
        success: function (data) {
            console.log("success");
            console.log(data);
            alert("Successfully updated your location information! ")
            displayLocationInfo(location);
        },
        error: function (e) {
            console.log("fail");
            console.log(e);
            alert("Error, please try again.");
        }
    });
    return;
}

function deleteLocation() {
    var cfm = confirm("Are you sure you want to delete your location information? ")
    var url = "/contacts/" + locationId;
    if (!cfm) return;

    var request_method = "DELETE";

    $.ajax({
        type: request_method,
        contentType: "application/json",
        url: url,
        cache: false,
        timeout: 60000,
        success: function (data) {
            console.log("success");
            console.log(data);
            alert("Successfully deleted your location information! ")
            hideLocationInfo();
        },
        error: function (e) {
            console.log("fail");
            console.log(e);
            alert("Error, please try again.");
        }
    });
    return;
}

/** Logout by deleting uid in localstorage and authentication token */
function logout() {
    //TODO
}