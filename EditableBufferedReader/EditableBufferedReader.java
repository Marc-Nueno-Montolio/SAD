import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author marcnueno
 */
public class EditableBufferedReader extends BufferedReader {

    // General ASCII Codes
    private final int ESCAPE = 27;
    private final int DEL = 127;
    private final int BKSP = 8;

    // Custom extended ASCII Codes
    private final int LEFT = -1;
    private final int RIGHT = -2;
    private final int HOME = -3;
    private final int END = -4;
    private final int ERASE_ONE = 127;

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

        String[] command = { "bash", "-c", "stty -echo cooked </dev/tty" };
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
        return null;
    }

    @Override
    public int read() throws IOException {
        int key = super.read();
        if (key == ESCAPE) {
            // It is a escape key sequence
            key = super.read();
            if (key != '[') {
                // TODO: Process invalid input
            } else {
                key = super.read();
                switch (key) {
                    case 'D': // left
                        return -1;
                    case 'C': // left
                        return -2;
                    case 'H':
                        return -3;
                }
            }
            return key;
        } else if (key == BKSP) {
            // TODO: Erase one position
        }

        return key;
    }

}
