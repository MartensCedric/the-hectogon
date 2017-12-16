package com.cedricmartens.hectogon.server.match;

import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.UserNotFoundException;
import com.cedricmartens.commons.entities.Competitor;
import com.cedricmartens.commons.entities.animal.Animal;
import com.cedricmartens.commons.entities.animal.Rabbit;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.actions.PacketPositionCorrection;
import com.cedricmartens.commons.networking.animal.PacketAnimalUpdate;
import com.cedricmartens.commons.networking.competitor.DeathReason;
import com.cedricmartens.commons.networking.competitor.PacketCompetitor;
import com.cedricmartens.commons.networking.competitor.PacketCompetitorJoin;
import com.cedricmartens.commons.networking.competitor.PacketDeath;
import com.cedricmartens.commons.networking.inventory.PacketInventory;
import com.cedricmartens.commons.networking.inventory.PacketLoot;
import com.cedricmartens.commons.networking.inventory.PacketLootUpdate;
import com.cedricmartens.commons.storage.Lootbag;
import com.cedricmartens.commons.storage.inventory.Inventory;
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
    private long startTime;
    private int tickIndex = 0;
    private int animalId = 0;
    private List<Animal> animals;

    private int lootbagId = 0;
    private List<Lootbag> lootbags;

    public Match(int matchId)
    {
        this.hasStarted = false;
        this.players = new ArrayList<>();
        this.lootbags = new ArrayList<>();
        this.animals = new ArrayList<>();
        for(int i = 0; i < 10; i++)
        {
            Rabbit rabbit = new Rabbit(2500 + i * 100, 30);
            rabbit.setId(animalId--);
            this.animals.add(rabbit);
        }
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
        Log.trace(player.getId() + " has joined!");
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

        Log.trace("Filling up the inventory of player id " + player.getId());
        player.getInventory().addItem(Item.bow_wood, 2);
        player.getInventory().addItem(Item.bomb, 1);
        player.getInventory().addItem(Item.arr_wood, 17);
        player.getInventory().addItem(Item.swd_steel, 2);
        player.getInventory().addItem(Item.carrot, 3);
        PacketInventory packetInventory = new PacketInventory();
        packetInventory.setInventory(player.getInventory());
        try {
            player.sendPacket(packetInventory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.trace("Notifying player id " + player.getId() + " of current animals");
        for(Animal a : animals)
        {
            PacketAnimalUpdate packetAnimalUpdate = new PacketAnimalUpdate();
            packetAnimalUpdate.setAnimal(a);
            try {
                player.sendPacket(packetAnimalUpdate);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        send(p-> (deathReason != DeathReason.DISCONNECT)
                        || p.getUser().getUserId() != playerId
                ,packetDeath);
        players.removeIf(p -> p.getUser().getUserId() == playerId);
        Log.info("Player id : " + playerId + " dies with reason : "+ deathReason.name());
    }

    public Player getPlayerById(int playerId) throws UserNotFoundException {
        for(Player player : players)
        {
            if(player.getUser().getUserId() == playerId)
                return player;
        }

        throw new UserNotFoundException();
    }

    public void tick(float delta)
    {
        for(Player p : players)
           p.move(delta);

        for(Animal a : animals)
        {
            a.update(delta);
            if(a.updateState(players))
            {
                PacketAnimalUpdate animalUpdate = new PacketAnimalUpdate();
                animalUpdate.setAnimal(a);
                sendToEveryone(animalUpdate);
            }
        }

        if(tickIndex % 60 == 0)
            correctPlayerPositions();

        tickIndex++;
    }

    private void correctPlayerPositions()
    {
        for(Player p : players)
        {
            PacketPositionCorrection packetPositionCorrection = new PacketPositionCorrection();
            packetPositionCorrection.setUserId(p.getUser().getUserId());
            packetPositionCorrection.setPosition(p.getPosition());

            sendToEveryone(packetPositionCorrection);
        }
    }

    public String[] getAlivePlayerUsernames()
    {
        String[] names = new String[players.size()];

        for(int i = 0; i < players.size(); i++)
        {
            names[i] = players.get(i).getUser().getUsername();
        }

        return names;
    }

    public void startMatch()
    {
        if(hasStarted)
            throw new IllegalStateException("Cannot start a match if it has already started!");

        hasStarted = true;
        startTime = System.currentTimeMillis();
    }

    public void dropItem(Player player, Item item, int qty)
    {
        if(player == null)
            throw new IllegalArgumentException();

        player.getInventory().removeItem(item, qty);

        Lootbag closestLb = getClosestLootbag(player.getPosition().x, player.getPosition().y);
        if(closestLb == null)
        {
            PacketLoot packetLoot = new PacketLoot();
            packetLoot.setPoint(player.getPosition());
            Inventory inventory = new Inventory(12);
            inventory.addItem(item, qty);

            Lootbag lootbag = new Lootbag(player.getPosition().x,
                                          player.getPosition().y,
                                            inventory);
            lootbag.setId(lootbagId++);
            lootbags.add(lootbag);
            packetLoot.setInventory(inventory);
            packetLoot.setLootId(lootbag.getId());
            sendToEveryone(packetLoot);
        }else{
            closestLb.getInventory().addItem(item, qty);
            PacketLootUpdate packetLootUpdate = new PacketLootUpdate();
            packetLootUpdate.setLootId(closestLb.getId());
            packetLootUpdate.setInventory(closestLb.getInventory());
            sendToEveryone(packetLootUpdate);
        }
    }

    private Lootbag getClosestLootbag(float x, float y)
    {
        Lootbag bestLb = null;
        float bestDis = Float.MAX_VALUE;
        float pickUpRange = 25;

        for(Lootbag lootbag : lootbags)
        {
            float dis = lootbag.getPosition().distanceBetweenPoints(x, y);
            if(dis < pickUpRange && dis < bestDis)
            {
                bestDis = dis;
                bestLb = lootbag;
            }
        }

        return bestLb;
    }
}