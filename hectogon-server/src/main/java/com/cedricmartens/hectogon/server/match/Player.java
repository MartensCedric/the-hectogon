package com.cedricmartens.hectogon.server.match;

import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.User;
import com.cedricmartens.commons.entities.Competitor;
import com.cedricmartens.commons.networking.Packet;
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

    public int getUserId()
    {
        return user.getUserId();
    }

    @Override
    public void move(float speed, float deltaTime) {
        super.move(speed, deltaTime);
    }

    public void processMovement(MovementAction movementAction)
    {
        switch (movementAction) {
            case UP_KEY_PRESS:
                movingUp = true;
                break;
            case RIGHT_KEY_PRESS:
                movingRight = true;
                break;
            case DOWN_KEY_PRESS:
                movingDown = true;
                break;
            case LEFT_KEY_PRESS:
                movingLeft = true;
                break;
            case UP_KEY_RELEASED:
                movingUp = false;
                break;
            case RIGHT_KEY_RELEASED:
                movingRight = false;
                break;
            case DOWN_KEY_RELEASED:
                movingDown = false;
                break;
            case LEFT_KEY_RELEASED:
                movingLeft = false;
                break;
            case ROLL:
                break;
        }
    }
}
