package server;

import java.io.IOException;
import sockets.*;

public class RunServer {

    private static MyServerSocket ss;

    public static void main(String[] args) throws IOException {
        
        Server server = new ReentrantReadWriteLockImpl();

        ss = new MyServerSocket(null, Integer.parseInt(args[0]));

        // Bucle d'acceptació connexions
        while (true) {
            MySocket s = ss.accept(); // Assignem el socket a un MySocket

            // For each new socket, we create a new thread:
            new Thread() {
                public void run() {
                    String line, nick;
                    nick = s.readLine();
                    server.connect(nick, s);

                    // While we can write messages, we have to broadcast it to connected clients
                    while ((line = s.readLine()) != null) {
                        // We have to broadcast the messages to everyone except us
                        for (String clientNick : server.getConnectedNicks()) {
                            if (!nick.equals(clientNick)) {
                                server.get(clientNick).println(nick + ": " + line);
                            }
                        }

                    }
                    // Close connection
                    server.disconnect(nick);

                }
            }.start();

        }
    }
}