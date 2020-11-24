package com.example.demo.WebSocket;

import com.example.demo.Logic.Field;
import com.example.demo.Messages.Message;
import com.example.demo.Messages.ClickMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.tinylog.Logger;

@Controller
public class MessagingController {

    int width = 5;
    int height = 5;
    int numberOfMines = 5;

    Field field = new Field(width, height, numberOfMines);
    int numberOfClicksDone = 0;

    @MessageMapping("/websocket")
    @SendTo("/topic/messages")
    public Message sendMessage(ClickMessage clickMessage) {
        Logger.info("Clicked: x: " + clickMessage.getX() + ", y: " + clickMessage.getY());

        if(clickMessage.getX() == -1 && clickMessage.getY() == -1) {
            field = new Field(width, height, numberOfMines);
            numberOfClicksDone = 0;
        }

        if(numberOfClicksDone == 1) {
            field.fill(numberOfMines, clickMessage.getX(), clickMessage.getY());
        }

        field.setGameField(field.click(clickMessage.getX(), clickMessage.getY()));
        field.unsafeLog();

        numberOfClicksDone++;
        return new Message(field);
    }

}