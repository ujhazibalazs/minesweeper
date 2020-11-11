package Logic;

import Messages.Message;
import org.tinylog.Logger;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {

        Field field = new Field(5, 5, 5);
        Message message = new Message(field.getWidth(), field.getHeight(), field.getNumberOfTotalMines(), field.getGameField());

        message.unsafeLog();

        Logger.info("\n\n>>>This instance of the program is only for testing purposes.<<<\n");
        int x;
        int y;
        Scanner sc = new Scanner(System.in);

        field.unsafeLog();

        while(true) {

            if(field.allEmptyCellsRevealed()) {
                Logger.warn("You Won!");
                System.exit(0);
            }

            Logger.info("Enter your selection's x coordinate: ");
            x = sc.nextInt();
            Logger.info("Enter your selection's y coordinate: ");
            y = sc.nextInt();

            field.click(y-1, x-1);
            field.unsafeLog();
            Logger.trace("--------------------");
            message = new Message(field.getWidth(), field.getHeight(), field.getNumberOfTotalMines(), field.getGameField());
            message.unsafeLog();
        }
    }
}
