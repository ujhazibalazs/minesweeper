package WebSocket;

import Logic.Field;
import Message.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessagingController {

    @MessageMapping("/websocket")
    @SendTo("/topic/messages")
    public Message sendMessage() throws Exception {
        Field field = new Field(10, 10, 25);
        return new Message(field.getWidth(), field.getHeight(), field.getNumberOfTotalMines(), field.getGameField());
    }
}