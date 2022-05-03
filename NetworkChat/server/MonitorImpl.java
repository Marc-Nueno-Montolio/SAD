package server;

import sockets.*;
public class MonitorImpl implements Server{
    
    private static HashMapMonitor mon;

    public MonitorImpl(){
        mon = new HashMapMonitor();
    }

    public MySocket connect(String nick, MySocket s) {
        return connect(nick, s);
    }

    public MySocket disconnect(String nick) {
        return disconnect(nick);
    }

    public String[] getConnectedNicks() {
        return getConnectedNicks();
        
    }

    public MySocket get(String nick) {
        return get(nick);
        
    }
}
