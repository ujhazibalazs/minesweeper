connect();

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            var field = JSON.parse(message.body);

            var i, j;
                var table = "";
                for (i = 0; i < field.width; i++) {
                    table += "<tr>"
                    for (j = 0; j < field.height; j++) {
                        if(field.messageField[i][j].type == "EMPTY") {
                            table += "<td style=\"background-color:lightgreen;\" " + "x=\"" + i + "\"" + "y=\"" + j + "\"" + "onclick=\"sendClick(" + i + ", " + j + ")\"" + ">" + field.messageField[i][j].number + "</td>";
                        } else if (field.messageField[i][j].type == "BOMB"){
                            table += "<td style=\"background-color:red;\" " + "x=\"" + i + "\"" + "y=\"" + j + "\"" + "onclick=\"sendClick(" + i + ", " + j + ")\"" + ">" + "¤" + "</td>";
                            document.body.style.backgroundColor = "#ffcccc";
                            document.getElementById("startMessage").innerHTML = "YOU LOST!";
                        } else if (field.messageField[i][j].type == "UNREVEALED") {
                            table += "<td " + "x=\"" + i + "\"" + "y=\"" + j + "\"" + "onclick=\"sendClick(" + i + ", " + j + ")\"" + ">" + " " + "</td>";
                        }
                    }
                    table += "</tr>"
                }

                document.getElementById("numberOfTotalMines").innerHTML = "Total number of mines: " + field.numberOfTotalMines;
                document.getElementById("gameField").innerHTML = table;
                document.getElementById("emptyCellsRevealed").innerHTML = "Empty cells revealed: " + field.emptyCellsRevealed + "/" + (((field.width * field.height) - field.numberOfTotalMines)).toString();
        });
    });
}

function newGame() {
    document.body.style.backgroundColor = "lightgrey";
    var clickMessage = {
        x: -1,
        y: -1
    };
    stompClient.send("/app/websocket", {}, JSON.stringify(clickMessage));
}

function sendClick(i, j) {
    console.log("clicked the cell: " + i + ", " + j);
    var clickMessage = {
        type: "LEFT",
        x: i,
        y: j
    };
    stompClient.send("/app/websocket", {}, JSON.stringify(clickMessage));
}

function start() {
    newGame();
    document.getElementById("startMessage").innerHTML = "MINESWEEPER STARTED!"
}