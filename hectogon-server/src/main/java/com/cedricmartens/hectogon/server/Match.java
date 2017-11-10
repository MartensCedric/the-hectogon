package com.cedricmartens.hectogon.server;

import com.cedricmartens.commons.networking.Packet;

import java.io.IOException;
import java.util.List;

public class Match
{
    private List<SocketConnection> connections;
    private int matchId;

    public Match(int matchId, List<SocketConnection> connections)
    {
        this.connections = connections;
    }

    public int getMatchId() {
        return matchId;
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
}
