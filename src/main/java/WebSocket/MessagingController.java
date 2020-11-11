package WebSocket;

import Logic.Field;
import Messages.Message;
import Messages.ClickMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.tinylog.Logger;

@Controller
public class MessagingController {

    Field field = new Field(10, 10, 25);

    @MessageMapping("/websocket")
    @SendTo("/topic/messages")
    public Message sendMessage(ClickMessage clickMessage) throws Exception {
        Logger.info("x: " + clickMessage.getX() + ", y: " + clickMessage.getY());

        field.setGameField(field.click(clickMessage.getX(), clickMessage.getY()));

        return new Message(field.getWidth(), field.getHeight(), field.getNumberOfTotalMines(), field.getGameField());
    }

}