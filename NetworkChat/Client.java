import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import sockets.*;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException {
        MySocket sc = new MySocket(args[0], Integer.parseInt(args[1]));
        // Input Thread
        new Thread() {
            public void run() {
                String line;
                BufferedReader kbd = new BufferedReader(new InputStreamReader(System.in));
                try {
                    while ((line = kbd.readLine()) != null) {
                        // Fem echo del que hem rebut
                        sc.println(line);
                    }
                    // Al xat hauriem de fer el Close for write
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        }.start();

        // Output Thread
        new Thread() {
            public void run() {
                System.out.print("Please insert your nick: ");
                String line;
                while ((line = sc.readLine()) != null) {
                    // Fem echo del que hem rebut
                    System.out.println(line);
                }
                // Al xat hauriem de fer el Close for write

            }
        }.start();

    }
}
