package com.example.demo.Logic;

import com.example.demo.Messages.ClickTypes;
import lombok.Data;
import org.tinylog.Logger;
import java.util.ArrayList;
import java.util.Collections;

@Data
public class Field {

    public Field(int width, int height, int numberOfMines) {
        this.width = width;
        this.height = height;
        this.numberOfTotalMines = numberOfMines;
        this.gameField = new Cell[width][height];
        this.endOfGame = false;

        initialize();

    }

    final private int width;
    final private int height;
    final private int numberOfTotalMines;
    private int numberOfRevealedEmptyCells;
    private Cell[][] gameField;
    private boolean endOfGame;

    public Cell[][] initialize() {

        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                gameField[i][j] = new Cell();
            }
        }

        return gameField;
    }

    public Cell[][] fill(int numberOfMines, int x, int y) {
        ArrayList<Types> cells = new ArrayList<>();
        for(int i = 0; i < numberOfMines; i++) {
            cells.add(Types.BOMB);
        }
        for(int i = 0; i < (width * height - numberOfMines) - 1; i++) {
            cells.add(Types.EMPTY);
        }

        Collections.shuffle(cells);

        cells.add(Types.EMPTY);

        Collections.swap(cells, width * x + y, cells.size() - 1);

        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                gameField[i][j].setType(cells.get((i * height) + j));
            }
        }

        for(int i = 0; i < gameField.length; i++) {
            for(int j = 0; j < gameField[0].length; j++) {
                gameField[i][j].setBombsAround(getNumberOfMines(i, j));
            }
        }

        return gameField;
    }

    public void unsafeLog() {
        String line = "";
        for(int i = 0; i < gameField.length; i++) {
            line = "";
            for(int j = 0; j < gameField[0].length; j++) {
                if(gameField[i][j].getType() == Types.BOMB) {
                    line += "¤ ";
                } else if (gameField[i][j].getType() == Types.EMPTY) {
                    line += getNumberOfMines(i, j) + " ";
                }
            }
            Logger.debug(line);
        }
    }

    public Cell[][] click(int posX, int posY, ClickTypes type) {

        if(!isEndOfGame()) {
            if(type == ClickTypes.RIGHT) {
                if(!gameField[posX][posY].isRevealed()) {
                    gameField[posX][posY].setFlagged(!gameField[posX][posY].isFlagged());
                    return gameField;
                }
            }

            if(posX >= 0 && posX < width && posY >= 0 && posY < height) {
                if(!gameField[posX][posY].isFlagged) {
                    gameField[posX][posY].setRevealed(true);
                    if(gameField[posX][posY].getType() == Types.BOMB) {
                        Logger.info("You Lost! You clicked (" + (posX + 1) + ", "+ (posY + 1) + ")");
                        Logger.info("");
                        setEndOfGame(true);
                    } else {
                        numberOfRevealedEmptyCells = getNumberOfRevealedEmptyCells(this);
                        int numberOfUnrevealedCells = getNumberOfUnrevealedEmptyCells(this);
                        if(numberOfUnrevealedCells == numberOfTotalMines) {
                            setEndOfGame(true);
                            Logger.info("You Won!");
                        }
                    }
                }
            }
        }
        return gameField;
    }

    private int getNumberOfRevealedEmptyCells(Field field) {
        int numberOfRevealedEmptyCells = 0;

        for(int i = 0; i < field.gameField.length; i++) {
            for(int j = 0; j < field.gameField[0].length; j++) {
                if(field.gameField[i][j].isRevealed) {
                    numberOfRevealedEmptyCells++;
                }
            }
        }

        return numberOfRevealedEmptyCells;
    }

    private int getNumberOfUnrevealedEmptyCells(Field field) {
        int numberOfUnrevealedEmptyCells = 0;

        for(int i = 0; i < field.gameField.length; i++) {
            for(int j = 0; j < field.gameField[0].length; j++) {
                if(!field.gameField[i][j].isRevealed) {
                    numberOfUnrevealedEmptyCells++;
                }
            }
        }

        return numberOfUnrevealedEmptyCells;
    }

    public int getNumberOfMines(int x, int y) {
        int numberOfBombs = 0;
        if(x >= 0 && x < width && y >= 0 && y < height) {
            for(int i = x - 1; i <= x + 1; i++) {
                for(int j = y - 1; j <= y + 1; j++) {
                    try {
                        if(gameField[i][j].getType() == Types.BOMB) {
                            numberOfBombs++;
                        }
                    } catch (Exception e) {
                        //Do something probably
                    }
                }
            }
        }
        return numberOfBombs;
    }

    public boolean allEmptyCellsRevealed() {
        int numberOfRevealedEmptyCells = 0;
        for(int i = 0; i < gameField.length; i++) {
            for(int j = 0; j < gameField[0].length; j++) {
                if(gameField[i][j].isRevealed() && gameField[i][j].getType() != Types.BOMB) {
                    numberOfRevealedEmptyCells++;
                }
            }
        }
        return numberOfTotalMines == (width * height) - numberOfRevealedEmptyCells;
    }
}