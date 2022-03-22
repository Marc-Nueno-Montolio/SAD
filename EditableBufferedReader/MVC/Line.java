
import java.io.IOException;
import java.util.Observable;

/**
 *
 * @author marcnueno
 */

// Aquesta classe només guarda l'estat
public class Line extends Observable {
    private String line;
    private int cursor;
    private boolean insertMode;

    public Line() {
        this.line = "";
        this.cursor = 0;
        this.insertMode = true;
    }

    public Boolean getInsertMode() {
        return this.insertMode;
    }

    public void toggleInsertMode() {
        this.insertMode = !this.insertMode;
        setChanged();
        notifyObservers(Keys.INS);
    }

    public int length() {
        return this.line.length();
    }

    public int getCursorPos() {
        return this.cursor;
    }

    public void add(char c) {
        if (this.cursor == this.length()) {
            this.line += c;
            this.cursor++;
        } else {
            String end = this.line.substring(this.cursor);
            this.line = this.line.substring(0, this.cursor) + c + end;
            this.cursor++;
        }

        setChanged();
        notifyObservers();
    }

    public void insert(char c) {
        char[] chars = this.line.toCharArray();
        chars[this.cursor] = c;
        this.line = String.valueOf(chars);
        this.cursor++;

        setChanged();
        notifyObservers();
    }

    public void erase() {
        if (this.cursor > 0) {
            String end = this.line.substring(this.cursor);
            this.line = this.line.substring(0, this.cursor - 1) + end;
            this.cursor --;
            setChanged();
            notifyObservers(Keys.BKSP);
        }

    }

    public void delete() {
        if(this.cursor < this.length()){
            String end = this.line.substring(this.cursor + 1, this.length());
            this.line = this.line.substring(0, this.cursor) + end;
          
        }else{
            System.out.print("\007");
        }
        setChanged();
        notifyObservers(Keys.DEL);
    }

    public void decreaseCursor() {
        if (cursor > 0) {
            this.cursor--;
            setChanged();
            notifyObservers(Keys.LEFT);
        }
    }

    public void increaseCursor() {
        if (cursor < line.length()) {
            this.cursor++;
            setChanged();
            notifyObservers(Keys.RIGHT);
        }
    }

    public void goToHome() {
        this.cursor = 0;
        setChanged();
        notifyObservers(Keys.HOME);
    }

    public void goToEnd() {
        setChanged();
        notifyObservers(Keys.END);
        this.cursor = this.line.length();
    }

    public String toString() {
        return this.line;
    }
}
