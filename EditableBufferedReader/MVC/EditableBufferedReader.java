import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author marcnueno
 */
public class EditableBufferedReader extends BufferedReader {

    public EditableBufferedReader(Reader in) {
        super(in);
    }

    public void setRaw() {
        String[] command = { "bash", "-c", "stty -echo raw </dev/tty" };
        // /bin/bash: change to bash shell
        // -c parameter: read commands from string
        // </dev/tty: get input from stdin into raw mode
        try {
            Runtime.getRuntime().exec(command).waitFor(); // Esperem a que el terminal canvi el mode
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void unSetRaw() {
        String os = System.getProperty("os.name");
        String[] command = { "bash", "-c", "" };
        if (os.equals("Mac OS X")) {
            command[2] = "stty -echo cooked </dev/tty";

            try {
                Runtime.getRuntime().exec(command).waitFor();
            } catch (InterruptedException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {
            try {
                String[] commanda = { "bash", "-c", "stty sane </dev/tty" };
                Runtime.getRuntime().exec(commanda).waitFor();
            } catch (InterruptedException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try {
            Runtime.getRuntime().exec(command).waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String readLine() throws IOException {
        Line line = new Line();
        this.setRaw();

        int key = this.read();

        while (key != Keys.RETURN) {
            switch (key) {
                case Keys.BKSP:
                    line.erase();
                    System.out.print(TerminalActions.ERASE_ONE_LEFT);
                    break;
                case Keys.LEFT:
                    if (line.decreaseCursor())
                        System.out.print(TerminalActions.MOVE_LEFT);
                    break;

                case Keys.RIGHT:
                    if (line.increaseCursor())
                        System.out.print(TerminalActions.MOVE_RIGHT);
                    break;

                case Keys.HOME:
                    System.out.print(TerminalActions.HOME);
                    line.goToHome();
                    break;

                case Keys.END:
                    if (line.getCursorPos() < line.length()) {
                        System.out.print(TerminalActions.ESCAPE + "[" + (line.length() - line.getCursorPos()) + "C");
                        line.goToEnd();
                    }
                    break;

                case Keys.INS:
                    line.toggleInsertMode();
                    break;

                case Keys.DEL:
                    System.out.print(TerminalActions.ERASE);
                    line.delete();
                    break;

                default:
                    if (line.getInsertMode() && line.getCursorPos() < line.length()) {
                        line.insert((char) key);
                        System.out.print((char) key);
                    } else {
                        System.out.print(TerminalActions.ERASE_UNTIL_END);
                        System.out.print(line.add((char) key));
                        System.out.print(TerminalActions.ESCAPE + "[" + (line.getCursorPos() + 1) + "G");

                    }
            }
            key = this.read();

        }
        this.unSetRaw();
        return line.getLine();

    }

    @Override
    public int read() throws IOException {
        int key = super.read();
        if (key == Keys.ESCAPE) {
            // It is a escape key sequence
            key = super.read();
            if (key == '[') {
                key = super.read();
                switch (key) {
                    case 'D': // left
                        return Keys.LEFT;
                    case 'C': // left
                        return Keys.RIGHT;
                    case 'H':
                        return Keys.HOME;
                    case 'F':
                        return Keys.END;
                    case '2':
                        super.read();
                        return Keys.INS;
                    case '3':
                        super.read();
                        return Keys.DEL;
                    default:
                        key = super.read();
                        break;
                }
            }
        } else if (key == Keys.EXIT || key == Keys.EOT) {
            this.unSetRaw(); // Avoid leaving the terminal in Raw mode
            System.exit(0);
        }
        return key;
    }

}
