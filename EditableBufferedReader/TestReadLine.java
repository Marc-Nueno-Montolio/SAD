import java.io.*;

class TestReadLine {
  public static void main(String[] args) {


    EditableBufferedReader in = new EditableBufferedReader(
      new InputStreamReader(System.in));
    //String str = null;
    Integer num = 0;
    while(true){
      try {
        //str = in.readLine();
        in.setRaw();
              num = in.read();
        in.unSetRaw();
      } catch (IOException e) { e.printStackTrace(); }
      //System.out.println("\nline is: " + str);
      
      System.out.println("\nChar is: " + num);
    }
   
    
  }
}
