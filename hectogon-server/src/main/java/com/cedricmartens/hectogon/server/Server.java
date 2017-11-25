package com.cedricmartens.hectogon.server;

import com.cedricmartens.hectogon.server.db.DatabaseManager;
import com.cedricmartens.hectogon.server.match.Match;
import com.cedricmartens.hectogon.server.match.MatchMock;
import com.cedricmartens.hectogon.server.match.MatchService;
import com.cedricmartens.hectogon.server.match.NoMatchFoundException;
import com.esotericsoftware.minlog.Log;

import javax.xml.crypto.Data;
import java.awt.image.DataBuffer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable
{
    private List<SocketConnection> socketConnections;
    private List<Match> matches;
    private boolean serverUp;
    private ServerSocket serverSocket;
    private int port;
    private DatabaseManager dbManager;

    public Server(int port)
    {
        this.serverUp = true;
        this.port = port;
        socketConnections = new ArrayList<>();
        this.matches = new ArrayList<>();

        try {
            DatabaseManager.initDatabaseManager("jdbc:mysql://localhost:3306/hectogon", "hectogon_user", "P@ssw0rd");
            dbManager = DatabaseManager.getDatabaseManager();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
        Log.info("Server is serverUp to connecting sockets on port : " + port);
        new Thread(() -> {
            long time = System.currentTimeMillis();
            int targetTPS = 60;
            while(serverUp)
            {
                long timeNow = System.currentTimeMillis();
                long delta = timeNow - time;
                time = timeNow;
                float deltaTime = delta/1000.0f;
                tick(deltaTime);
                long timeAfterTick = System.currentTimeMillis();
                float newDelta = timeAfterTick - time;

                if(newDelta < 1000/targetTPS) {
                    try {
                        Thread.sleep((long) ((1000 / targetTPS) - newDelta));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        while (serverUp)
        {
            SocketConnection socketConnection = null;
            try {
                Socket socket = serverSocket.accept();
                Log.trace("New connection from " + socket.getInetAddress());
                socketConnection = new SocketConnection(socket, this);
                socketConnections.add(socketConnection);

                SocketConnection finalSocketConnection = socketConnection;
                new Thread(() -> finalSocketConnection.listen(this)).start();
            }catch (SocketException e)
            {
                e.printStackTrace();
                this.removeSocketConnection(socketConnection);
            }
            catch (IOException e) {
                e.printStackTrace();
                serverUp = false;
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

    public void tick(float delta)
    {
        for(Match m : matches)
            m.tick(delta);
    }
}
