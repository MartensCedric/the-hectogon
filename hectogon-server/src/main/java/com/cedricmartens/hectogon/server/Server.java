package com.cedricmartens.hectogon.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable
{
    private List<SocketConnection> socketConnections;
    private boolean listening = true;
    private ServerSocket serverSocket;
    private int port;

    public Server(int port)
    {
        this.port = port;
        socketConnections = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        System.out.println("Server is listening to connecting sockets on port : " + port);

        while (listening)
        {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New connection @" + socket.getInetAddress());
                SocketConnection socketConnection = new SocketConnection(socket);
                socketConnections.add(socketConnection);
                new Thread(() -> socketConnection.listen(this)).run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
