const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/topic_001', (event) => {
        showEvent(JSON.parse(event.body).content);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#events").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function createEnum(values) {
    const enumObject = {};
    for (const val of values) {
        enumObject[val] = val;
    }
    return Object.freeze(enumObject);
}

var Tag = createEnum(['E', 'A', 'P']);

function sendContent() {
    stompClient.publish({
        destination: "/app/topic_001",
        body: JSON.stringify(
            {
                'id': "ID123",
                'pubkey': "PUBKEY456",
                'created_at': 123456,
                'kind': 0,
                'tags': [Tag.P, Tag.E, Tag.A],
                'sig': "SIG_XXX",
                'content': $("#content").val()
            }
        )
    });
}

function showEvent(content) {
    $("#events").append("<tr><td>" + content + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendContent());
});

