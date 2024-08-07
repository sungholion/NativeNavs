var stompClient = null;
var roomId = [[${roomId}]];

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/ws-stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/room/'+roomId, function (chatMessage) {
            showGreeting(JSON.parse(chatMessage.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/send/"+roomId, {},
        JSON.stringify({
            'roomId': roomId,
            'senderId': $("#sender").val(),
            'senderNickname': $("#sender").val(),  // Assuming senderId and senderNickname are the same
            'senderProfileImage': '',  // Add appropriate field or leave it empty if not used
            'content': $("#message").val(),
            'isRead': false,  // New messages are not read initially
            'sendTime': new Date().getTime()
        }));
}

function showGreeting(chatMessage) {
    console.log(chatMessage.name)
    $("#chatting").append("<tr><td>" + "[" + chatMessage.name + "]" + chatMessage.message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});

