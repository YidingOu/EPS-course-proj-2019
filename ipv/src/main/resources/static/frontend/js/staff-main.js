//adapted from https://bootsnipp.com/snippets/ZlkBn

var uid = null;           //userId staff
var convos = {};          //dictionary of postId to lists of msgs (message objects)
var users = {};           //dictionary of postId to usernames staff is chatting with
var currPostId = -1;     //postId of current user whose chat is viewed

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
    test(0);
    test(1);
    users = {0: "u1", 1: "u2"};
    populateView();
})

function test(id) {
    var arr = []
    var msg = new Message({
                            text: "hi",
                            senderId : 1
                        });
    arr.push(msg);

    msg = new Message({
                            text: "bye",
                            senderId : 9
                        });
    arr.push(msg);
    convos[id] = arr;
}


/** Gets all chat messages and users that the staff is chatting with */
function getChatDetails() {
    var url = "/posts/by_staff/" + uid;
    var request_method = "GET";

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
                var msgs = chat.conversations;
                var posts = [];
                for (var j=0; j<msgs; j++) {
                    var msg = new Message({
                        text: convo[j].data,
                        senderId : convo[j].userId
                    });
                    posts.push(msg);
                }
                convos[chat.id] = posts;
                users[chat.id] = chat.user.name;
            }
            populateView();
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
 */
function populateView() {
    var htmlString = "";
    for (var postId in users) {
        htmlString += '<li id="user' + postId + '" onclick="populateChat(' + postId + ')">' +
                            '<a href="#">' +
                                '<i class="now-ui-icons users_single-02"></i>' +
                                '<p>' + users[postId] + '</p>' +
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
     //populate chat box, by default show the first conversation
     var postIdKeyArr = Object.keys(users);
    if (postIdKeyArr.length >= 1) {
        var firstPostId = postIdKeyArr[0];
        $("#chat_user").html(users.firstPostId);
        populateChat(firstPostId);
    }
}

/** Populate the chat box with the conversation with the user of @param chatUid.
 *  If the chat is paused, staff should not be able to view the conversation
 *  but view the text "This chat is currently paused" in the chat box.
 */
function populateChat(postId) {
    if (postId == currPostId) return;
    clearPosts();
    $("#user" + currPostId).removeClass("active");
    currPostId = postId;
    $("#user" + postId).addClass("active");
    var posts = convos[postId];
    drawPosts(posts);
}

/** Defines message object */
Message = function (arg) {
    this.text = arg.text, this.senderId = arg.senderId, this.postId = arg.postId;
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

/** Removes all messages displayed in the chat box. */
function clearPosts() {
    $('.messages').html("");
}

/** Draws all posts (where posts are of message objects) */
function drawPosts(posts) {
    for (var i=0; i<posts.length; i++) {
        console.log(posts[i].text);
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
    var msg = new Message({
        text: text,
        senderId: uid
    });
    //send message to server, draw on success \
    msg.draw();
    if (sentMessageToServer(msg)) {
        convos[currPostId].push(msg);
        return $messages.animate({ scrollTop: $messages.prop('scrollHeight') }, 300);
    }
    return;
}

/** Helper function to send the @param msg to the server
 *  Returns true upon success; false otherwise
 */
function sentMessageToServer(msg) {
    var url = "/conversations";
    var request_method = "POST";
    var post_data = {
            data: msg.text,
            userId: uid,
            postId: currPostId
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
            return true;
        },
        error: function (e) {
            console.log("fail");
            console.log(e);
            //alert(e.responseJSON.message);
            alert("Error, please try again. ")
            return false;
        }
    });
    return false;
}