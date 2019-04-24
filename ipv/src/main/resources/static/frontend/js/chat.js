//adapted from https://bootsnipp.com/snippets/ZlkBn

uid = getUid();
postId = null;

$(document).ready(function() {
    alert("Worried that someone might be looking over your shoulder? " + 
            "Press the Esc key twice for an automatic redirection to google.com");
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
    var posts = getPosts(uid);
    if (posts) {
        drawPosts(posts);
    }
})

/** Defines message object with a draw function that assigns it its position 
 *  based on the user who posted the message (ie if sender is user, message appears
 *  on the right, else message appears on the left.) */
Message = function (arg) {
    this.text = arg.text, this.sender_id = arg.sender_id;
    this.draw = function (_this) {
        return function () {
            var $message;
            message_side = (_this.sender_id == uid) ? "right" : "left"
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
        return localStorage.getItem('uid');
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
            console.log("success");
            //populate messages
            postId = data.id;
            return getMessages(postId);
        },
        error: function (e) {
            console.log("fail");
            alert("Session expired. Please login again. "); //TO CHANGE?
            $(location).attr("href", "login.html");
        }
    });
    return;
}

/** Gets messages from the same conversation (ie same post id) */
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
            for (var i=0; i<data.length; i++) {
                msg = new Message({
                    text: data[i].data,
                    sender_id : data[i].userId
                });
                posts.push(msg);
            }
            return posts;
        },
        error: function (e) {
            console.log("fail");
            console.log(e)
            alert("Session expired. Please login again. "); 
            $(location).attr("href", "login.html");
        }
    });
}

/** Draws all posts */
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
    sender_id = uid
    message = new Message({
        text: text,
        sender_id: uid
    });
    //send message to server, draw on success 
    if (addPost(message)) {
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
    return false;
}

/** Perform a series of redirects to google.com */
function redirect() {
    window.location.href = "http://google.com";
}

/** Prompts user to input location and sends information to server. */ 
function sendLocation() {
    var location = prompt("Please enter the location you wish to share. " +
        "This information is strictly confidential and will be automatically deleted after a week.");
    // TODO 
    return;
}
