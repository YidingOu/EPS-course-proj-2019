//adapted from https://bootsnipp.com/snippets/ZlkBn

uid = getUid();

$(document).ready(function() {
    alert("Worried that someone might be looking over your shoulder? Press the Esc key twice for an automatic redirection to google.com");
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
    var posts = getPosts();
    drawPosts(posts);
})

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

/** Returns the uid of the current user (as stored in cookie) */
function getUid() {
    return 1; 
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

/** Perform a series of redirects to google.com */
function redirect() {
    console.log("redirect");
    window.location.href = "http://google.com";
}

/** Prompts user to input location and sends information to server. */ 
function sendLocation() {
    var location = prompt("Please enter the location you wish to share. " +
        "This information is strictly confidential and will be automatically deleted after a week.");
    return;
}

/** Creates 2 messages for testing */
function sendTestMessages() {
    sendMessage('Hello Philip! :)');
        setTimeout(function () {
            return sendMessage('Hi Sandy! How are youHi Sandy! How are you?Hi Sandy! How are youHi Sandy! How are youHi Sandy! How are youHi Sandy! How are youHi Sandy! How are youHi Sandy! How are youHi Sandy! How are youHi Sandy! How are youHi Sandy! How are youHi Sandy! How are youHi Sandy! How are youHi Sandy! How are you');
        }, 1000);
        return setTimeout(function () {
            return sendMessage('I\'m fine, thank you!');
        }, 2000);
}
