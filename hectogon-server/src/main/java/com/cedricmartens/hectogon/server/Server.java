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

    public Server(int port)
    {
        socketConnections = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (listening)
        {
            try {
                Socket socket = serverSocket.accept();
                SocketConnection socketConnection = new SocketConnection(socket);
                socketConnections.add(socketConnection);
                new Thread(() -> socketConnection.listen(this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
