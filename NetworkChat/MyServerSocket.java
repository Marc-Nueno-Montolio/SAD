import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketImpl;

public class MyServerSocket extends ServerSocket {

    public MyServerSocket(String host, int port) throws IOException {
        super(port);
        // TODO Auto-generated constructor stub
    }

    public MySocket accept() throws IOException {
        MySocket s = new MySocket((SocketImpl) null);
        implAccept(s);
        s.startStreams();
        return s;
    }

}
