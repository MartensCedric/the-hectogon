package com.cedricmartens.hectogon.server;

import com.cedricmartens.hectogon.server.match.Match;
import com.cedricmartens.hectogon.server.match.NoMatchFoundException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable
{
    private List<SocketConnection> socketConnections;
    private List<Match> matches;
    private boolean listening;
    private ServerSocket serverSocket;
    private int port;

    public Server(int port)
    {
        this.listening = true;
        this.port = port;
        socketConnections = new ArrayList<>();
        this.matches = new ArrayList<>();
        this.matches.add(new Match(0, new ArrayList<>()));
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
                matches.get(0).addPlayer(socketConnection);

                new Thread(() -> socketConnection.listen(this)).run();
            } catch (IOException e) {
                e.printStackTrace();
                listening = false;
            }
        }
    }

    public List<Match> getMatches()
    {
        return matches;
    }

    public Match getMatchById(int matchId) throws NoMatchFoundException {
        for(Match match : matches)
        {
            if(match.getMatchId() == matchId)
                return match;
        }

        throw new NoMatchFoundException();
    }

    public Match getNextAvailableMatch()
    {
        for(Match match : matches)
        {
            if(match.canJoin())
                return match;
        }

        return null;
    }
}
