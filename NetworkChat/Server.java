import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Server {
    private static volatile HashMap<String, MySocket> connections = new HashMap<String, MySocket>();
    private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private static final Lock r = rwl.readLock();
    private static final Lock w = rwl.writeLock();

    public static void main(String[] args) throws IOException {
        MyServerSocket ss = new MyServerSocket(null, Integer.parseInt(args[0]));

        // Bucle d'acceptació connexions
        while (true) {
            MySocket s = ss.accept(); // Assignem el socket a un MySocket

            // For each new socket, we create a new thread:
            new Thread() {
                public void run() {
                    String line, nick;
                    nick = s.readLine();
                    Server.connect(nick, s);

                    // While we can write messages, we have to broadcast it to connected clients
                    while ((line = s.readLine()) != null) {
                        // We have to broadcast the messages to everyone except us
                        for (String clientNick : Server.getConnectedNicks()) {
                            if (!nick.equals(clientNick)) {
                                Server.get(clientNick).println( nick + ": " + line);
                            }
                        }

                    }
                    // Close connection

                    Server.disconnect(nick);

                }
            }.start();

        }
    }

    public static MySocket connect(String nick, MySocket s) {
        w.lock();
        try {
            connections.put(nick, s);
            System.out.println("User " + nick + " joined the chat room");
            return s;
        } finally {
            w.unlock();
        }
    }

    public static MySocket disconnect(String nick) {
        w.lock();
        try {
            // Close socket
            MySocket s = connections.get(nick);
            s.shutdownInput();
            connections.remove(nick);
            System.out.println("User " + nick + " left the chat room");
            return s;
        } finally {
            w.unlock();
        }
    }

    public static String[] getConnectedNicks() {
        r.lock();
        try {
            return connections.keySet().toArray(new String[0]);
        } finally {
            r.unlock();
        }
    }

    public static MySocket get(String nick) {
        r.lock();
        try {
            return connections.get(nick);
        } finally {
            r.unlock();
        }
    }

}
