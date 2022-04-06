import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        MyServerSocket ss = new MyServerSocket(null, Integer.parseInt(args[0]));

        // Bucle d'acceptació connexions
        while (true) {
            MySocket s = ss.accept(); // Assignem el socket a un MySocket

            // Creem una classe Thread Anònima
            new Thread() {
                public void run() {
                    String line;
                    while ((line = s.readLine()) != null) {
                        // Fem echo del que hem rebut
                        s.println(line);
                    }
                }
            }.start();

        }
    }
}
