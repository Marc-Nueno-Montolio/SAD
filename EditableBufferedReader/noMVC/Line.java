package noMVC;
import java.io.IOException;
import java.net.SocketTimeoutException;

import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author marcnueno
 */

// Aquesta classe només guarda l'estat
public class Line {
    private String line;
    private int cursor;

    public Line() {
        line = "";
        cursor = 0;
    }

    public String getLine() {
        return this.line;
    }

    public int getCursorPos() {
        return this.cursor;
    }

    public void add(char c) {
        this.cursor++;
        this.line += c;
    }

    public void delete() {
        if (this.cursor > 0) {
            this.line = this.line.substring(0, this.line.length() - 1);
            this.cursor--;
        }

    }

    public void CursorBegin() {
        cursor = 0;
    }

    public void CursorEnd() {

    }
}
