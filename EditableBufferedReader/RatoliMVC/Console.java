import java.util.Observable;
import java.util.Observer;

public class Console implements Observer {

    // Terminal Actions
    public final String ESCAPE = "\33",
            MOVE_LEFT = ESCAPE + "[1D",
            MOVE_RIGHT = ESCAPE + "[1C",
            ERASE = ESCAPE + "[1P",
            ERASE_UNTIL_END = ESCAPE + "[0K",
            ERASE_ONE_LEFT = MOVE_LEFT + ERASE,
            ERASE_LINE = ESCAPE + "[2K\r",
            HOME = ESCAPE + "[1G";

    
    /** 
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {

        Line line = (Line) o;
        // update the console when the observed object changes
        if (arg != null) {
            switch ((int) arg) {
                case Keys.MB1_CLICK:
                    System.out.print(ESCAPE + "[" + (line.getCursorPos() + 1) + "G");
                    break;
                case Keys.BKSP:
                    System.out.print(ERASE_ONE_LEFT);
                    break;
                case Keys.LEFT:
                    System.out.print(MOVE_LEFT);
                    break;

                case Keys.RIGHT:
                    System.out.print(MOVE_RIGHT);
                    break;

                case Keys.HOME:
                    System.out.print(HOME);
                    break;

                case Keys.END:
                    System.out.print(ESCAPE + "[" + (line.length() - line.getCursorPos()) + "C");
                    break;
                case Keys.INS:
                    break;
                case Keys.DEL:
                    System.out.print(ERASE);
                    break;

            }
        } else {
            System.out.print(ERASE_UNTIL_END);
            System.out.print(line.toString().substring(line.getCursorPos() - 1));
            System.out.print(ESCAPE + "[" + (line.getCursorPos() + 1) + "G");

        }
    }

}
