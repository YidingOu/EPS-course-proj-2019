//adapted from https://bootsnipp.com/snippets/ZlkBn

var uid = null;

$(document).ready(function() {
    $('.send_message').click(function () {
        return sendMessage(getMessageText());
    });
    $('.message_input').keyup(function (e) {
        if (e.which === 13) {
            return sendMessage(getMessageText());
        }
    });
    uid = getUid();
    populateView(["u1", "u2"], [[]]);
})

/** Gets all chat messages and users that the staff is chatting with */
function getChatDetails() {
    var url = "/posts/by_staff/" + uid;
    var request_method = "GET";
    var users = [];
    var convos = [];

    $.ajax({
        type: request_method,
        contentType: "application/json",
        url: url,
        cache: false,
        timeout: 60000,
        success: function (data) {
            console.log("success");
            for (var i=0; i<data.length; i++) {
                var chat = data[i];
                var user = chat.user.name;
                var msgs = chat.conversations;
                users.append(user);
                convos.append(msgs);
            }
            populateView(users, convos);
        },
        error: function (e) {
            console.log("fail");
            console.log(e);
            alert("Error, please refresh the page.")
        }
    });
}

/** Populate the side navigation with users staff is chatting with.
 *  A dot icon is used to indicate unread messages
 *  Populates the chat box depending on the current username clicked
 *  on the nav bar.
 *  @param users: list of usernames the staff is chatting with
 *  @param convos: list of lists of msgs (index aligns with list of users)
 */
function populateView(users, convos) {
    var htmlString = "";
    for (var i=0; i<users.length; i++) {
        htmlString += '<li id="user' + i + '">' +
                        '<a href="#">' +
                            '<i class="now-ui-icons users_single-02"></i>' +
                            '<p>' + users[i] + '</p>' +
                        '</a>' +
                      '</li>';
    }
    htmlString += '<li>' +
                    '<a href="./staff-settings.html">' +
                   	    '<i class="now-ui-icons ui-1_settings-gear-63"></i>' +
                   	     '<p>Account Settings</p>' +
                   	'</a>' +
                  '</li>';

    $("#nav-bar").html(htmlString);
    $("#user0").addClass("active");
     //populate chat box, by default show the first conversation
    if (users.length >= 1) {
        $("#chat_user").html(users[0]);
        console.log(convos);
        populateChat(0, convos);
    }
}

/** Populate the chat box with the conversation with the user of @param uid.
 *  If the chat is paused, staff should not be able to view the conversation
 *  but view the text "This chat is currently paused" in the chat box.
 */
function populateChat(userIndex, convos) {
    var posts = [];
    console.log(convos);
    for (var j=0; j<convos[userIndex].length; j++) {
        var msg = new Message({
                text: convos[userIndex].data,
                sender_id : convos[userIndex].userId
            });
        posts.push(msg);
    }
    drawPosts(posts);
}

/** Defines message object */
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
function getPosts() {
    var posts = [];
    message1 = new Message({
        text: "Hi, i want to find out about something",
        sender_id : 1
    });
    message2 = new Message({
        text: "Hi, I am the staff, what can I help you with?",
        sender_id: 2
    });
    posts.push(message1, message2);
    return posts;
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
    message.draw();
    return $messages.animate({ scrollTop: $messages.prop('scrollHeight') }, 300);
}
