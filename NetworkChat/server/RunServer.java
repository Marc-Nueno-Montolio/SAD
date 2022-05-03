package server;

import java.io.*;
import java.util.*;
import java.lang.*;
import sockets.*;

public class RunServer {

    private static MyServerSocket ss;

    public static void main(String[] args) throws IOException {
        
        int port = Integer.parseInt(args[0]);
        Server server = new SynchronizedMapImpl();
        System.out.println("Please specify a command: ");
        System.out.println(" (1) -> Start Server with Concurrent Hash Map Implementation");
        System.out.println(" (2) -> Start Server with ReentrantReadWriteLock Implementation");
        System.out.println(" (3) -> Start Server with Monitor Implementation");
        System.out.println(" (4) -> Start Server with SynchronizedMap Implementation");
        System.out.println(" (X) -> Close");

        
        System.out.print("\nYour choice: ");

        Scanner sc = new Scanner(System.in);   
        char c = sc.next().charAt(0);   
        switch(c){
            case '1':
                server = new ConcurrentHashMapImpl();
                System.out.println("\nStarted Server on port: "  + port);

                runServer(server, port);
            break;

            case '2':
                server = new ReentrantReadWriteLockImpl();
                System.out.println("\nStarted Server on port: "  + port);
                runServer(server, port);
            break;

            case '3':
                server = new MonitorImpl();
                System.out.println("\nStarted Server on port: "  + port);
                runServer(server, port);
            break;

            case '4':
                server = new SynchronizedMapImpl();
                System.out.println("\nStarted Server on port: "  + port);
                runServer(server, port);
            break;

            case 'x':
                System.out.println("Bye!");
                System.exit(0);
                
            break;

            default:
                System.out.println("Bye!");
                System.exit(0);
            break;
        }


        
    }

    public static void runServer(Server server, int port) throws IOException{
        ss = new MyServerSocket(null, port);

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