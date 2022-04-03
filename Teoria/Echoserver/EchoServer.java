public class EchoServer {

    public static void main(String[] args) {
        MyServerSocket ss = new MyServerSocket(Integer.parseInt(args[0]));

        // Bucle d'acceptació connexions
        while (true) {
            MySocket s = ss.accept(); // Assignem el socket a un MySocket
            // Creem una classe Thread Anònima
            
            new Thread(){
                public void run(){
                    String line;
                    while ((line = s.readLine()) != null) {
                        // Fem echo del que hem rebut
                        s.println(line);
                    }
                    s.close();
                }
            }.start();
            
        }
    }

}
