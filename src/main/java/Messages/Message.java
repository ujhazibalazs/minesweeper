package Messages;

import Logic.Cell;
import Logic.Field;
import Logic.Types;
import lombok.Data;
import org.tinylog.Logger;

@Data
public class Message {

    public Message(Field field) {
        this.width = field.getWidth();
        this.height = field.getHeight();
        this.numberOfTotalMines = field.getNumberOfTotalMines();
        this.emptyCellsRevealed = field.getNumberOfRevealedEmptyCells();
        this.messageField = new MessageCell[width][height];

        messageField = initialize(numberOfTotalMines);

        for (int i = 0; i < field.getGameField().length; i++) {
            for (int j = 0; j < field.getGameField()[0].length; j++) {
                if(field.getGameField()[i][j].isRevealed()) {
                    messageField[i][j].setType(field.getGameField()[i][j].getType());
                    messageField[i][j].setNumber(field.getGameField()[i][j].getBombsAround());
                }
            }
        }
    }

    final int height;
    final int width;
    private int emptyCellsRevealed;
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
