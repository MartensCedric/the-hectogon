package com.cedricmartens.hectogon.client.core.game;

import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.User;

public class Contestant
{
    private User user;
    private Point position;

    public Contestant(User user, Point position)
    {
        this.user = user;
        this.position = position;
    }

    public void move(float speed, float direction)
    {
        position.x += Math.cos(direction) * speed * /* DELTA TIME */ 1;
        position.y += Math.sin(direction) * speed * /* DELTA TIME */ 1;
    }
}
