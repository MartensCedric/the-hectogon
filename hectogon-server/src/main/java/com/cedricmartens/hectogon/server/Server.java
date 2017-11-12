package com.cedricmartens.hectogon.server;

import com.cedricmartens.hectogon.server.command.CommandCenter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable
{
    private List<SocketConnection> socketConnections;
    private List<Match> matches;
    private boolean listening = true;
    private ServerSocket serverSocket;
    private int port;

    public Server(int port)
    {
        this.port = port;
        socketConnections = new ArrayList<>();
        this.matches = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        System.out.println("Server is listening to connecting sockets on port : " + port);

        while (listening)
        {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New connection from" + socket.getInetAddress());
                SocketConnection socketConnection = new SocketConnection(socket, this);
                socketConnections.add(socketConnection);
                new Thread(() -> socketConnection.listen(this)).run();
            } catch (IOException e) {
                e.printStackTrace();
                listening = false;
            }
        }
    }

    public Match getMatchById(int matchId) {
        for(Match match : matches)
        {
            if(match.getMatchId() == matchId)
                return match;
        }

        throw new IllegalStateException();
    }
}
