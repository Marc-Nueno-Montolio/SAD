package server;

import sockets.MySocket;

public interface Server {
    MySocket connect(String nick, MySocket s);

    MySocket disconnect(String nick);

    String[] getConnectedNicks();

    MySocket get(String nick);

}
