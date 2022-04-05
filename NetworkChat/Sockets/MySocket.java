package Sockets;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.net.UnknownHostException;

public class MySocket extends Socket {
    private Boolean autoFlush = true;
    private BufferedReader br;
    private PrintWriter pw;

    public MySocket(String host, int port) throws UnknownHostException, IOException {
        super(host, port);
        // Initialize input & output streams:
        this.pw = new PrintWriter(this.getOutputStream(), this.autoFlush);
        this.br = new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
    
    public String readLine(){
        try {
            return this.br.readLine();
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        return "";
    }

    public void println(String line){
        this.pw.println(line);
    }
    
}
