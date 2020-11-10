function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            var field = JSON.parse(message.body);

            var i, j;
                var table = "";
                for (i = 0; i < 10; i++) {
                    table += "<tr>"
                    for (j = 0; j < 10; j++) {
                        if(field.gameField[i][j].type != "BOMB") {
                            table += "<td>" + field.gameField[i][j].bombsAround + "</td>";
                        } else {
                            table += "<td>" + "¤" + "</td>";
                        }
                    }
                    table += "</tr>"
                }

                document.getElementById("numberOfTotalMines").innerHTML = "Total number of mines: " + field.numberOfTotalMines;
                document.getElementById("gameField").innerHTML = table;
                document.getElementById("emptyCellsRevealed").innerHTML = "Empty cells revealed: " + field.numberOfRevealedEmptyCells;
        });
    });
}

function send() {
    stompClient.send("/app/websocket", {});
}

function start() {

    send();

    document.getElementById("startMessage").innerHTML = "MINESWEEPER STARTED!"
}