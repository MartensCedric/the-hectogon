package com.cedricmartens.hectogon.server.match;

import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.User;
import com.cedricmartens.commons.networking.Packet;
import com.cedricmartens.commons.storage.inventory.Inventory;
import com.cedricmartens.hectogon.server.SocketConnection;

import java.io.IOException;

public class Player
{
    private static final int DEFAULT_PLAYER_INVENTORY_SIZE = 12;
    private SocketConnection connection;
    private User user;
    private Inventory inventory;
    private Point position;

    public Player(SocketConnection connection, User user)
    {
        this.connection = connection;
        this.user = user;
        this.inventory = new Inventory(DEFAULT_PLAYER_INVENTORY_SIZE);
        this.position = new Point(0, 0);
    }

    public void sendPacket(Packet packet) throws IOException {
        connection.sendPacket(packet);
    }

    public String getUsername()
    {
        return user.getUsername();
    }

    public int getUserId()
    {
        return user.getUserId();
    }
}
