package server;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import sockets.*;

public class MonitorImpl {
    private static volatile HashMap<String, MySocket> connections = new HashMap<String, MySocket>();
    private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private static final Lock r = rwl.readLock();
    private static final Lock w = rwl.writeLock();


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
