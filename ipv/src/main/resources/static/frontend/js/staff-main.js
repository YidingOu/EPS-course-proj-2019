//adapted from https://bootsnipp.com/snippets/ZlkBn

var LOCKED = 10;          //status value for paused account
var uid = null;           //userId staff
var convos = {};          //dictionary of postId to lists of msgs (message objects)
var users = {};           //dictionary of postId to usernames staff is chatting with
var isLocked = {};        //dictionary of postId to bool (true if post is locked, false otherwise)
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
    $("#logout").click(function() {
        logout();
    });
    uid = getUid();
    //test(0);
    //test(1);
    //users = {0: "u1", 1: "u2"};
    //populateView();
    getChatDetails();
})

/** Gets the current post id that is being viewed (is appended after "#"
 *  in the url). If nothing follows the # sign, current post id is set to -1.
 */
function getPostIdFromUrl() {
    return window.location.hash.substring(1) ? (window.location.hash.substring(1)) : -1;
}

function test(id) {
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
    convos[id] = arr;
}

/** Gets all chat messages and users that the staff is chatting with */
function getChatDetails() {
    var url = "/posts/by_staff/" + uid;
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
            console.log(url);
            console.log(data);
            for (var i=0; i<data.length; i++) {
                var chat = data[i];
                if (chat.user != null) {
                    //valid user returned
                    isLocked[chat.id] = (chat.status == LOCKED) ? true : false;
                    users[chat.id] = chat.user.name;
                }
            }
            populateView(users);
        },
        error: function (e) {
            console.log("fail");
            console.log(e);
            alert("Error, please refresh the page.")
        }
    });
}

/** Get all messages with @param postId and draw them on screen */
function getPostMessages(postId) {
    var url = "/posts/" + postId;
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
            var convo = data.conversations;
            var msgs = [];
            for (var j=0; j<convo.length; j++) {
                var msg = new Message({
                    text: convo[j].data,
                    senderId : convo[j].userId
                });
                msgs.push(msg);
            }
            convos[postId] = msgs;
            drawPosts(msgs);
        },
        error: function (e) {
            console.log("fail");
            console.log(e);
            alert("Error, please refresh the page.")
        }
    });
}

/** Populate the side navigation with @param users staff is chatting with.
 *  A dot icon is used to indicate unread messages
 *  Populates the chat box depending on the current username clicked
 *  on the nav bar.
 *  @param users: dictionary of postId to usernames staff is chatting with
 */
function populateView(users) {
    var htmlString = "";
    for (var postId in users) {
        htmlString += '<li id="user' + postId + '" onclick="populateChat(' + postId + ')">' +
                            '<a href="#' + postId + '">' +
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
    if (currPostId == -1 && getPostIdFromUrl() == -1) {
        //default, staff just logged in without selecting any chat
        if (postIdKeyArr.length >= 1) {
            //if there is at least 1 user, populate with the first chat
            var firstPostId = postIdKeyArr[0];
            $("#chat_user").html(users[firstPostId]);
            populateChat(firstPostId);
        } else {
            //no user is assigned to the staff
            showNoChatsMsg();
        }
    } else {
        //staff clicked onto chat through nav bar, populating using he/she post clicked on
        if (postId in users) populateChat(getPostIdFromUrl());
        //invalid post id in url
        else showNoChatsMsg();
    }
}

/** Display message that staff has no ongoing chat with users */
function showNoChatsMsg() {
    $(".chat_window").html("<div style='text-align:center; padding-top:40%'> " +
    "You do not have any ongoing chats. </div>");
}

/** Populate the chat box with the conversation with the user of @param postId.
 *  If the chat is paused, staff should not be able to view the conversation
 *  but view the text "This chat is currently paused" in the chat box.
 *  @param postId: id of post that the staff is viewing
 */
function populateChat(postId) {
    if (postId == currPostId) return;
    $("#chat_user").html(users[postId]);
    clearPosts();
    $("#user" + currPostId).removeClass("active");
    currPostId = postId;
    $("#user" + postId).addClass("active");
    if (isLocked[postId]) {
        //chat is locked
        $(".chat_window").html("<div style='text-align:center; padding-top:40%'> " +
            "This chat is currently paused by the user. </div>");
        return;
    }
    getPostMessages(postId);
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
    var msg = new Message({
        text: text,
        senderId: uid
    });
    //send message to server, draw on success
    sendMessageToServer(msg);
}

/** Helper function to send the @param msg to the server
 *  Draw message upon success
 */
function sendMessageToServer(msg) {
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
            msg.draw();
            convos[currPostId].push(msg);
            $messages = $('.messages');
            $messages.animate({ scrollTop: $messages.prop('scrollHeight') }, 300);
        },
        error: function (e) {
            console.log("fail");
            console.log(e);
            alert("Error, please try again. ")
        }
    });
}

/** Logout by deleting uid in localstorage and authentication token */
function logout() {
    //TODO
}