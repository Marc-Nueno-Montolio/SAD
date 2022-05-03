package server;



import java.util.concurrent.ConcurrentHashMap;

import sockets.*;

public class ConcurrentHashMapImpl implements Server {
    private static volatile ConcurrentHashMap<String, MySocket> connections = new ConcurrentHashMap<String, MySocket>();
    

    public  MySocket connect(String nick, MySocket s) {
            connections.put(nick, s);
            System.out.println("User " + nick + " joined the chat room");
            return s;
        
    }

    public  MySocket disconnect(String nick) {
       
            // Close socket
            MySocket s = connections.get(nick);
            s.shutdownInput();
            connections.remove(nick);
            System.out.println("User " + nick + " left the chat room");
            return s;
       
    }

    public  String[] getConnectedNicks() {
       
            return connections.keySet().toArray(new String[0]);
       
    }

    public  MySocket get(String nick) {
        
            return connections.get(nick);
        
    }

}
