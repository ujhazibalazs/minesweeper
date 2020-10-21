package Logic;

import lombok.Data;

@Data
public class Cell {
    Types type;
    int bombsAround;
    boolean isRevealed = false;
    boolean isFlagged = false;
}
