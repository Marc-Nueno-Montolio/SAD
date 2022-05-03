package server;

import java.util.*;



import sockets.*;

public class SynchronizedMapImpl implements Server {
    private static volatile HashMap<String, MySocket> map = new HashMap<String, MySocket>();
    private static volatile Map<String, MySocket> connections = Collections.synchronizedMap(map);

    public MySocket connect(String nick, MySocket s) {
            connections.put(nick, s);
            System.out.println("User " + nick + " joined the chat room");
            return s;
    }


    public MySocket disconnect(String nick) {
            // Close socket
            MySocket s = connections.get(nick);
            s.shutdownInput();
            connections.remove(nick);
            System.out.println("User " + nick + " left the chat room");
            return s;
    }


    public String[] getConnectedNicks() {
            return connections.keySet().toArray(new String[0]);
    }


    public MySocket get(String nick) {
            return connections.get(nick);

    }

}