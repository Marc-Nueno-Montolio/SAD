public class TerminalActions {
    public static final String ESCAPE = "\33",
            MOVE_LEFT = ESCAPE + "[1D",
            MOVE_RIGHT = ESCAPE + "[1C",
            ERASE = ESCAPE + "[1P",
            ERASE_UNTIL_END = ESCAPE + "[0J",
            ERASE_ONE_LEFT = MOVE_LEFT + ERASE,
            ERASE_LINE = ESCAPE + "[2K\r",
            HOME = ESCAPE + "[1G";

}
