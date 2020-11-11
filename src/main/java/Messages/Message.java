package Messages;

import Logic.Cell;
import Logic.Types;
import lombok.Data;
import org.tinylog.Logger;

@Data
public class Message {

    public Message(int width, int height, int numberOfTotalMines, Cell[][] gameField) {
        this.width = width;
        this.height = height;
        this.numberOfTotalMines = numberOfTotalMines;
        this.messageField = new MessageCell[width][height];

        messageField = initialize(numberOfTotalMines);

        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                if(gameField[i][j].isRevealed()) {
                    messageField[i][j].setType(gameField[i][j].getType());
                    messageField[i][j].setNumber(gameField[i][j].getBombsAround());
                }
            }
        }
    }

    final int height;
    final int width;
    final private int numberOfTotalMines;
    private MessageCell[][] messageField;

    private MessageCell[][] initialize(int numberOfMines) {
        for (int i = 0; i < messageField.length; i++) {
            for (int j = 0; j < messageField[0].length; j++) {
                messageField[i][j] = new MessageCell();
            }
        }

        return messageField;
    }

    public void unsafeLog() {
        String line = "";
        for(int i = 0; i < messageField.length; i++) {
            line = "";
            for(int j = 0; j < messageField[0].length; j++) {
                if(messageField[i][j].getType() == Types.BOMB) {
                    line += "¤ ";
                } else if (messageField[i][j].getType() == Types.EMPTY) {
                    line += messageField[i][j].getNumber() + " ";
                } else if (messageField[i][j].getType() == Types.UNREVEALED){
                    line += ". ";
                }
            }
            Logger.info(line);
        }
    }
}
