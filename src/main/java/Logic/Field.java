package Logic;

public class Field {

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        this.gameField = initialize();

    }

    private int width;
    private int height;

    private int numberOfMines;

    Cell[][] gameField = new Cell[width][height];

    private Cell[][] initialize() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                gameField[i][j] = new Cell();
            }
        }
        return gameField;
    }

}
