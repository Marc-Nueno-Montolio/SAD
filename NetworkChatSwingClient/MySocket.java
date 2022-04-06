import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketImpl;
import java.net.UnknownHostException;

public class MySocket extends Socket {
    private Boolean autoFlush = true;
    private BufferedReader br;
    private PrintWriter pw;

    public MySocket(String host, int port) throws UnknownHostException, IOException {
        super(host, port);
        this.startStreams();
    }

    public MySocket(SocketImpl socketImpl) throws IOException {
        super(socketImpl);
    }

    public void startStreams() throws IOException {
        this.pw = new PrintWriter(this.getOutputStream(), this.autoFlush);
        this.br = new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String readLine() {
        try {
            return this.br.readLine();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return "";
    }

    public void println(String line) {
        this.pw.println(line);
    }

    @Override
    public void shutdownInput(){
        try {
            super.shutdownInput();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
