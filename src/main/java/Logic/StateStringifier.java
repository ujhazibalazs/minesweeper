package Logic;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StateStringifier {
    public StateStringifier(Logic.Field field) {
        this.field = field;
    }

    Logic.Field field;
}
