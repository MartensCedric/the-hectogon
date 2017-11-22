package com.cedricmartens.hectogon.server;

import com.cedricmartens.hectogon.server.match.Match;
import com.cedricmartens.hectogon.server.match.MatchMock;
import com.cedricmartens.hectogon.server.match.MatchService;
import com.cedricmartens.hectogon.server.match.NoMatchFoundException;
import com.esotericsoftware.minlog.Log;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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
        MatchService matchService = new MatchMock();
        this.matches.add(matchService.createMatch());
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        Log.info("Server is listening to connecting sockets on port : " + port);

        while (listening)
        {
            SocketConnection socketConnection = null;
            try {
                Socket socket = serverSocket.accept();
                Log.trace("New connection from " + socket.getInetAddress());
                socketConnection = new SocketConnection(socket, this);
                socketConnections.add(socketConnection);

                SocketConnection finalSocketConnection = socketConnection;
                new Thread(() -> finalSocketConnection.listen(this)).run();
            }catch (SocketException e)
            {
                e.printStackTrace();
                this.removeSocketConnection(socketConnection);
            }
            catch (IOException e) {
                e.printStackTrace();
                listening = false;
                this.removeSocketConnection(socketConnection);
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

        MatchService matchService = new MatchMock();
        Match match = matchService.createMatch();
        matches.add(match);
        return match;
    }

    public synchronized boolean removeSocketConnection(SocketConnection connection)
    {
        for(Match m : matches)
        {
            m.removePlayer(connection.getPlayerId());
        }
        return socketConnections.remove(connection);
    }
}
