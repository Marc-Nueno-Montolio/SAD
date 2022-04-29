package server;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.HashMap;

import sockets.*;

public class MonitorImpl {
    private static volatile HashMap<String, MySocket> connections = new HashMap<String, MySocket>();
    private static final ReentrantLock l = new ReentrantLock();
    private static final Condition c = l.newCondition();
    
    public static MySocket connect(String nick, MySocket s) {
        l.lock();
        try {
            connections.put(nick, s);
            System.out.println("User " + nick + " joined the chat room");
            return s;
        } finally {
            l.unlock();
        }
    }

    public static MySocket disconnect(String nick) {
        l.lock();
        try {
            // Close socket
            MySocket s = connections.get(nick);
            s.shutdownInput();
            connections.remove(nick);
            System.out.println("User " + nick + " left the chat room");
            return s;
        } finally {
            l.unlock();
        }
    }

    public static String[] getConnectedNicks() {
        l.lock();
        try {
            return connections.keySet().toArray(new String[0]);
        } finally {
            l.unlock();
        }
    }

    public static MySocket get(String nick) {
        l.lock();
        try {
            return connections.get(nick);
        } finally {
            l.unlock();
        }
    }

}
