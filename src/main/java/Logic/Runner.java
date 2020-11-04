package Logic;

import org.tinylog.Logger;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {

        Field field = new Field(10, 10, 25);

        try {
            Logger.info(field.stringify());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        //Clicks all the cells in order
        for(int i = 0; i < field.getWidth(); i++) {
            for(int j = 0; j < field.getHeight(); j++) {
                field.click(i, j);
                field.log();
            }
        }
        */

        //Asks for user input to reveal the cells
        int x;
        int y;
        Scanner sc = new Scanner(System.in);

        while(true) {

            if(field.allEmptyCellsRevealed()) {
                Logger.warn("You Won!");
                System.exit(0);
            }

            Logger.info("Enter your selection's x coordinate: ");
            x = sc.nextInt();
            Logger.info("Enter your selection's y coordinate: ");
            y = sc.nextInt();

            field.click(x-1, y-1);
            field.log();
        }
    }
}
