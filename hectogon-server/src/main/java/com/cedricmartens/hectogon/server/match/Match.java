package com.cedricmartens.hectogon.server.match;

import com.cedricmartens.commons.chat.ChatType;
import com.cedricmartens.commons.entities.Competitor;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.PacketChat;
import com.cedricmartens.commons.networking.authentification.PacketCompetitorJoin;
import com.esotericsoftware.minlog.Log;

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
        PacketCompetitorJoin packetCompetitorJoin = new PacketCompetitorJoin();
        packetCompetitorJoin.setCompetitor(new Competitor(player.getUser(), player.getPosition()));
        sendToEveryone(packetCompetitorJoin);
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
        Log.info(packet.getClass().getSimpleName() + " sent to everyone");
    }

    public void send(Predicate<Player> playerPredicate, Packet packet)
    {
        for(Player p : players)
        {
            if(playerPredicate.test(p))
            {
                try {
                    p.sendPacket(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
