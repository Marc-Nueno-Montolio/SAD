import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author marcnueno
 */
public class EditableBufferedReader extends BufferedReader {


    private Constants keys = new Constants();

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
        if (key == keys.ESCAPE) {
            // It is a escape key sequence
            key = super.read();
            if (key != '[') {
                // TODO: Process invalid input
            } else {
                key = super.read();
                switch (key) {
                    case 'D': // left
                        return keys.LEFT;
                    case 'C': // left
                        return keys.RIGHT;
                    case 'H':
                        return keys.HOME;
                    case 'F': 
                        return keys.END;
                    case '2': 
                        return keys.INS;
                }
            }
            return key;
        } else if (key == keys.BKSP) {
            // TODO: Erase one position
        }

        return key;
    }

}
