package com.cedricmartens.hectogon.server.match;

import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.entities.Competitor;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.competitor.DeathReason;
import com.cedricmartens.commons.networking.competitor.PacketCompetitor;
import com.cedricmartens.commons.networking.competitor.PacketCompetitorJoin;
import com.cedricmartens.commons.networking.competitor.PacketDeath;
import com.cedricmartens.commons.networking.inventory.PacketInventory;
import com.cedricmartens.commons.storage.inventory.Item;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.lang.Math.PI;

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

    public Point getNextPlayerPosition()
    {
        float x = Math.round(2500 * Math.cos((PI * players.size()) / (100 / 2)));
        float y = Math.round(2500 * Math.sin((PI * players.size()) / (100 / 2)));
        return new Point(x, y);
    }

    public void addPlayer(Player player)
    {
        this.players.add(player);
        PacketCompetitorJoin packetCompetitorJoin = new PacketCompetitorJoin();
        packetCompetitorJoin.setCompetitor(new Competitor(player.getUser(), player.getPosition()));
        sendToEveryone(packetCompetitorJoin);

        for(Player p : players)
        {
            if(p != player)
            {
                PacketCompetitor packetCompetitor = new PacketCompetitor();
                packetCompetitor.setCompetitor(p);
                try {
                    player.sendPacket(packetCompetitor);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        player.getInventory().addItem(Item.bow_wood, 2);
        player.getInventory().addItem(Item.bomb, 3);
        PacketInventory packetInventory = new PacketInventory();
        packetInventory.setInventory(player.getInventory());
        try {
            player.sendPacket(packetInventory);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        removePlayer(playerId, DeathReason.DISCONNECT);
    }

    public void removePlayer(int playerId, DeathReason deathReason)
    {
        PacketDeath packetDeath = new PacketDeath();
        packetDeath.setUserId(playerId);
        packetDeath.setDeathReason(deathReason);
        sendToEveryone(packetDeath);
        players.removeIf(p -> p.getUser().getUserId() == playerId);
        Log.info("Player id : " + playerId + " dies with reason : "+ deathReason.name());
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
            p.move(delta);
        }
    }
}