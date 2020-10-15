package Logic;

import lombok.Data;

@Data
public class Cell {

    public Cell() {
        this.type = Types.UNKNOWN;
    }

    private enum Types {
        BOMB,
        EMPTY,
        UNKNOWN
    }

    Types type;
    int bombsAround;
    boolean isFlagged;
}
