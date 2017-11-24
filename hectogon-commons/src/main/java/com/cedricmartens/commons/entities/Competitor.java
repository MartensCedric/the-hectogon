package com.cedricmartens.commons.entities;

import com.cedricmartens.commons.Point;
import com.cedricmartens.commons.User;

public class Competitor
{
    protected User user;
    protected Point position;

    protected boolean movingUp;
    protected boolean movingDown;
    protected boolean movingLeft;
    protected boolean movingRight;

    public Competitor(User user, Point position)
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

    public User getUser() {
        return user;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Competitor that = (Competitor) o;

        return user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

    public boolean isMovingUp() {
        return movingUp;
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public boolean isMovingDown() {
        return movingDown;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }
}
