package com.cedricmartens.hectogon.server.match;

import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.User;
import com.cedricmartens.commons.chat.ChatType;
import com.cedricmartens.commons.entities.Competitor;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.networking.PacketChat;
import com.cedricmartens.commons.networking.actions.MovementAction;
import com.cedricmartens.commons.storage.inventory.Inventory;
import com.cedricmartens.hectogon.server.SocketConnection;

import java.io.IOException;

public class Player extends Competitor
{
    private static final int DEFAULT_PLAYER_INVENTORY_SIZE = 12;
    private SocketConnection connection;
    private Inventory inventory;

    public Player(SocketConnection connection, User user, Point position)
    {
        super(user, position);
        this.connection = connection;
        this.user = user;
        this.inventory = new Inventory(DEFAULT_PLAYER_INVENTORY_SIZE);
    }

    public void sendPacket(Packet packet) throws IOException {
        connection.sendPacket(packet);
    }

    @Override
    public void move(float speed, float deltaTime) {
        super.move(speed, deltaTime);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
