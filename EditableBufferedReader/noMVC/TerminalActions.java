public class TerminalActions {
    public static final String ESCAPE = "\33",
            MOVE_LEFT = ESCAPE + "[1D",
            MOVE_RIGHT = ESCAPE + "[1C",
            ERASE = ESCAPE + "[1P",
            ERASE_ONE_LEFT = MOVE_LEFT + ERASE,
            HOME = ESCAPE + "[#G";	
}
