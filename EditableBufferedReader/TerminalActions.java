public class TerminalActions {
    public static final String ESCAPE = "\33",
            MOVE_LEFT = ESCAPE + "[1D",
            ERASE = ESCAPE + "[1P",
            ERASE_ONE_LEFT = MOVE_LEFT + ERASE;
}
