import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * EditableBufferedReader class extends the BufferedReader class giving
 * functionality to read keys.
 * 
 * @author marcnueno
 */
public class EditableBufferedReader extends BufferedReader {

    public EditableBufferedReader(Reader in) {
        super(in);
    }

    /**
     * Sets the terminal in raw mode
     * /bin/bash: change to bash shell
     * -c parameter: read commands from string
     * </dev/tty: get input from stdin into raw mode
     * 
     * @throws InterruptedException
     * @throws IOException
     */
    public void setRaw() throws IOException {
        String[] command = { "bash", "-c", "stty -echo raw </dev/tty" };
        try {
            Runtime.getRuntime().exec(command).waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @throws InterruptedException
     * @throws IOException
     */
    public void unSetRaw() throws IOException {
        String os = System.getProperty("os.name");
        String[] command = { "bash", "-c", "stty sane </dev/tty" };
        switch (os) {
            case "Mac OS X":
                command = new String[] { "bash", "-c", "stty -echo cooked </dev/tty" };
                try {
                    Runtime.getRuntime().exec(command).waitFor();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;

            default:
                try {
                    Runtime.getRuntime().exec(command).waitFor();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * @return String
     * @throws IOException
     */
    @Override
    public String readLine() throws IOException {
        
        Line line = new Line();
        Console console = new Console();
        line.addObserver(console);

        this.setRaw();
        int key = this.read();

        while (key != Keys.RETURN) {
            switch (key) {
                case Keys.BKSP:
                    line.erase();
                    break;

                case Keys.LEFT:
                    line.decreaseCursor();
                    break;

                case Keys.RIGHT:
                    line.increaseCursor();
                    break;

                case Keys.HOME:
                    line.goToHome();
                    break;

                case Keys.END:
                    line.goToEnd();
                    break;

                case Keys.INS:
                    line.toggleInsertMode();
                    break;

                case Keys.DEL:
                    line.delete();
                    break;

                default:
                    if (line.getInsertMode() && line.getCursorPos() < line.length()) {
                         line.insert((char) key);
                    } else {
                        line.add((char) key);

                    }
                    break;
            }
            key = this.read();

        }
        this.unSetRaw();
        return line.toString();

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

