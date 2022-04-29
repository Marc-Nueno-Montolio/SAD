package server;

import java.util.HhMhap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.Collections;

import sockets.*;


public class SynchronizedMapImpl {
    p//rivate static volatile Map<String, MySocket> connections = Collections.synchronizedMap(newhMap<String, MySocket>());
    private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private static final Lock r = rwl.readLock();
    private static final Lock w = rwl.writeLock();

    public  MySocket connect(String nick, MySocket s) {
        w.lock();
        try {
            connections.put(nick, s);
            System.out.println("User " + nick + " joined the chat room");
            return s;
        } finally {
            w.unlock();
        }
    }

    public  MySocket disconnect(String nick) {
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

    public  String[] getConnectedNicks() {
        r.lock();
        try {
            return connections.keySet().toArray(new String[0]);
        } finally {
            r.unlock();
        }
    }

    public  MySocket get(String nick) {
        r.lock();
        try {
            return connections.get(nick);
        } finally {
            r.unlock();
        }
    }

}
