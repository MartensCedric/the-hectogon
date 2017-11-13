package com.cedricmartens.hectogon.server.match;

import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.hectogon.server.SocketConnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Match
{
    private static final int MAX_SLOTS = 100;
    private List<SocketConnection> connections;
    private int matchId;
    private boolean hasStarted;

    public Match(int matchId)
    {
        this(matchId, new ArrayList<>());
    }

    public Match(int matchId, List<SocketConnection> connections)
    {
        this.hasStarted = false;
        this.connections = connections;
    }

    public int getMatchId() {
        return matchId;
    }

    public void addPlayer(SocketConnection connection)
    {
        this.connections.add(connection);
    }

    public void sendToEveryone(Packet packet)
    {
        for(int i = 0; i < connections.size(); i++)
        {
            try {
                connections.get(i).sendPacket(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean canJoin()
    {
        //TODO improve this
        return !hasStarted && connections.size() < MAX_SLOTS;
    }
}
