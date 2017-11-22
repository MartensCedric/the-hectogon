package com.cedricmartens.hectogon.client.core.game.player;

import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.User;

public class Contestant
{
    private User user;
    private Point position;

    protected boolean movingUp;
    protected boolean movingDown;
    protected boolean movingLeft;
    protected boolean movingRight;

    public Contestant(User user, Point position)
    {
        this.user = user;
        this.position = position;
    }

    public void move(float speed, float direction, float deltaTime)
    {
        position.x += Math.cos(direction) * speed * /* DELTA TIME */ 1;
        position.y += Math.sin(direction) * speed * /* DELTA TIME */ 1;
    }

    public void move(float speed, float deltaTime)
    {
        if(movingLeft)
        {
            addForce(speed, (float) Math.PI, deltaTime);
        }

        if(movingRight)
        {
            addForce(speed, 0, deltaTime);
        }

        if(movingUp)
        {
            addForce(speed, (float) Math.PI / 2, deltaTime);
        }

        if(movingDown)
        {
            addForce(speed, (float) (Math.PI + Math.PI/2), deltaTime);
        }
    }

    private void addForce(float speed, float direction, float deltaTime)
    {
        getPosition().x += Math.cos(direction) * speed * deltaTime;
        getPosition().y += Math.sin(direction) * speed * deltaTime;
    }

    public Point getPosition()
    {
        return position;
    }


}