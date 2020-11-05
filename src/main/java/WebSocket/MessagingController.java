package WebSocket;

import Logic.Field;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessagingController {

    @MessageMapping("/websocket")
    @SendTo("/topic/messages")
    public Field sendMessage() throws Exception {
        return new Field(10, 10, 25);
    }
}