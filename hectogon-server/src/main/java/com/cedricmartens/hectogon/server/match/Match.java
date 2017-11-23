package com.cedricmartens.hectogon.server.match;

import com.cedricmartens.commons.networking.Packet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Match
{
    private static final int MAX_SLOTS = 100;
    private List<Player> players;
    private int matchId;
    private boolean hasStarted;


    public Match(int matchId)
    {
        this.hasStarted = false;
        this.players = new ArrayList<>();
        this.matchId = matchId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void addPlayer(Player player)
    {
        this.players.add(player);
    }

    public void sendToEveryone(Packet packet)
    {
        players.forEach((p) -> {
            try {
                p.sendPacket(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void send(Predicate<Player> player, Packet packet)
    {
        //TODO finish htis
    }

    public boolean canJoin()
    {
        //TODO improve this
        return !hasStarted && players.size() < MAX_SLOTS;
    }

    @Override
    public String toString() {
        return "Id : " + matchId + "\tPlayers : " + players.size() + "\tAvailable : " + canJoin();
    }

    public void removePlayer(int playerId)
    {
        players.removeIf(p -> p.getUser().getUserId() == playerId);
    }

    public Player getPlayerById(int playerId)
    {
        for(Player player : players)
        {
            if(player.getUser().getUserId() == playerId)
                return player;
        }

        throw new IllegalStateException();
    }

    public void tick(float delta)
    {
        for(Player p : players)
        {
            p.move(150, delta);
        }
    }
}
