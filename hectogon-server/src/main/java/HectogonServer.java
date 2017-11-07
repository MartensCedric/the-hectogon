import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HectogonServer
{

    public static void main(String[] args)
    {
        boolean listening = true;
        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            while(listening)
            {
                Socket socket = serverSocket.accept();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Hello World!");
    }
}
