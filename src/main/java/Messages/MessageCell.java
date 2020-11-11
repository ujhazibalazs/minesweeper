package Messages;

import Logic.Types;
import lombok.Data;

@Data
public class MessageCell {
    Types type = Types.UNREVEALED;
    int number;
}
