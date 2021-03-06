
import java.io.IOException;

/**
 *
 * @author marcnueno
 */

// Aquesta classe només guarda l'estat
public class Line {
    private String line;
    private int cursor;
    private boolean insertMode;

    public Line() {
        this.line = "";
        this.cursor = 0;
        this.insertMode = false;
    }

    public Boolean getInsertMode() {
        return this.insertMode;
    }

    public void toggleInsertMode() {
        this.insertMode = !this.insertMode;
    }

    public String getLine() {
        return this.line;
    }

    public int length() {
        return this.line.length();
    }

    public int getCursorPos() {
        return this.cursor;
    }

    public String add(char c) {
        if (this.cursor == this.length()) {
            this.line += c;
            this.cursor++;
        } else {
            String end = this.line.substring(this.cursor);
            this.line = this.line.substring(0, this.cursor) + c + end;
            this.cursor++;
        }
        return this.line.substring(this.cursor - 1); // return the inserted character + the end
    }

    public void insert(char c) {
        char[] chars = this.line.toCharArray();
        chars[this.cursor] = c;
        this.line = String.valueOf(chars);
        this.cursor++;
    }

    public void erase() {
        if (this.cursor > 0) {

            if (this.cursor == this.line.length()) {
                // Just remove one from the end
                this.line = this.line.substring(0, this.line.length() - 1);
            } else {
                // When deleting, we have to append the remaining right portion
                String end = this.line.substring(this.cursor, this.line.length());
                this.line = this.line.substring(0, this.cursor - 1) + end;
            }

            this.cursor--;
        }
    }
    public void delete(){
        if(this.cursor < this.length()){
            String end = this.line.substring(this.cursor + 1, this.length());
            this.line = this.line.substring(0, this.cursor) + end;
          
        }else{
            System.out.print("\007");
        }
    }

    public boolean decreaseCursor() {
        if (cursor > 0) {
            this.cursor--;
            return true;
        }
        return false;
    }

    public boolean increaseCursor() {
        // Boolean to avoid infinite right scroll
        if (cursor < line.length()) {
            this.cursor++;
            return true;
        }
        return false;
    }

    public void goToHome() {
        this.cursor = 0;
    }

    public void goToEnd() {
        this.cursor = this.line.length();
    }
}
